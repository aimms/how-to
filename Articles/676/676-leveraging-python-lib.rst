Orchestrating the searoute Python lib from AIMMS
==============================================================================

Python has a vast and growing ecosystem of libraries that can add 
valuable functionality to your Operations Research applications developed in AIMMS. 
The AIMMS Python Bridge connects the two environments, providing a crucial two-way link::

#.  A Python application to use an AIMMS model.

#.  An AIMMS application to use Python libraries.

This article focuses on the second scenario, demonstrating how to use 
the open-source ``searoute`` library to enhance a strategic maritime application.

Exploring the searoute library
--------------------------------

Running example of this article is  the :doc:`Vessel Scheduling<../590/590-vessel-scheduling>` application
that uses the Python `searoute <https://pypi.org/project/searoute/>`_ library, for the following two purposes:

#.  To calculate more accurate distances between harbors, improving optimization quality.

#.  To visualize realistic routes on a map, enhancing user insight.

searoute accepts coordinates in the order of [longitude, latitude] and 
returns a geojson object containing the route information.  

A typical call in a Python script looks like:

.. code-block:: python
    :linenos:
    :emphasize-lines: 3

    origin = [-44.37, -2.565] # Ponta da Madeira, Brazil 
    destination = [-74.22, 11.07] # Puerto Prodeco, Colombia
    route = sr.searoute(origin,destination)


The ``geojson`` object contains the useful data within its ``properties`` and ``geometry`` attributes:

*   ``properties.length``: The accurate distance in kilometers.

*   ``geometry.coordinates``: The list of ``[lon, lat]`` coordinates that describe the route.

.. After this call, route is of type ``<class 'geojson.feature.Feature'>`` and looks (somewhat pretty printed):
.. .. code-block:: json
..     :linenos:
.. 
..     {
..         "geometry": {
..             "coordinates": [
..                 [ -43.9,      -1.4      ],
..                 [ -48,        -1E-05    ],
..                 [ -51.9,       5.4      ],
..                 [ -56.4243,    8.2122   ],
..                 [ -61,        11        ],
..                 [ -61.8055,   10.6839   ],
..                 [ -63.773623, 11.613686 ],
..                 [ -65.62372,  12.487713 ],
..                 [ -65.942,    12.5294   ],
..                 [ -70,        13        ],
..                 [ -75.25,     11.7      ]
..             ],
..             "type": "LineString"
..         },
..         "properties": {
..             "duration_hours": 90.5268642136653,
..             "length": 4023.738060569,
..             "units": "km"
..         },
..         "type": "Feature"
..     }

in the Vessel Scheduling application we are interested in the: 

*   distance, which is given by ``properties.length``, and

*   in the actual route, which is given in ``geometry.coordinates`` as a list of ``[lon, lat]`` tuples.


Integrating with AIMMS
-----------------------------------------------


Required Libraries and Installation:

.. First, you'll need to ensure the necessary libraries are there:
.. * `pandas installation <https://pypi.org/project/pandas/>`_, and `pandas documentation <https://pandas.pydata.org/docs/>`_
.. 
.. * `aimmspy installation <https://pypi.org/project/aimmspy/>`_, and `aimmspy documentation <https://documentation.aimms.com/aimmspy/aimmspy.html>`_
.. 
.. * `searoute <https://pypi.org/project/searoute/>`_  
.. Fortunately, these necessary libraries are loaded on demand, 
.. based on the `toml <https://docs.astral.sh/uv/concepts/configuration-files/>`_ file:

To utilize the searoute functionality, the necessary Python libraries must be available. 
The ``pyproject.toml`` file ensures that the correct versions of the libraries are loaded automatically:

.. code-block:: toml
    :linenos:

    [project]
    name = "vessel_scheduling_with_sea_route"
    version = "0.1.0"
    description = "Use Python library Sea Route to compute usable sea routes for large vessels"
    requires-python = "==3.13.*"
    dependencies = [
        "aimmspy",
        "pandas>=2.3.2",
        "searoute",
    ]

In addition, the AIMMS project requires the  
`the pyaimms repository library <https://documentation.aimms.com/aimmspy/pyaimms/pyaimms.html>`_
is essential as it is the Python client that allows your script to access and interact with the AIMMS model's data

To enable the AIMMS compiler to link and scan the Python code (in our case, leverage-sea-route.py), 
you use the ``py::run_python_script`` statement:

.. code-block:: aimms

    py::run_python_script("leverage-sea-route.py");

Using searoute to compute distances
---------------------------------------

In the Vessel Scheduling example, 
the difference between the geometric haversine distance and 
the searoute distance can be significant. 
For instance, the haversine distance between Caldera (Chile) and Bahia Blanca (Argentina) is 1520 km, 
whereas searoute calculates a more realistic route of 5180 km.

To compute an entire distance matrix, the Python script retrieves location data from AIMMS, 
calculates the distances, and sends the results back via a pandas DataFrame.

.. code-block:: python
    :linenos:

    def searoute_distance_matrix():
        # Step 1: Retrieve data from the AIMMS model and store in a pandas DataFrame
        distance_mat_list = []
        df_location_overview = aimms_model.multi_data(["i_loc","p_latitude","p_longitude"])
        
        # ... code for iterating and calculating distances ...
        
        # Step 2: Build a DataFrame with the calculated distances
        distance_columns = ['i_loc_from','i_loc_to','p_distance_searoute']
        distance_df = pd.DataFrame(distance_mat_list,columns=distance_columns)

        # Step 3: Pass the DataFrame back to the AIMMS model
        aimms_model.p_distance_searoute.assign(distance_df)


..         location_overview_no_rows = len( df_location_overview )
..         for from_pos in range(location_overview_no_rows):
..             for to_pos in range(from_pos+1,location_overview_no_rows):
..                 from_location_row = df_location_overview.iloc[from_pos]
..                 to_location_row   = df_location_overview.iloc[  to_pos]
..                 fromLoc =  from_location_row['i_loc']
..                 fromLon =  from_location_row['p_longitude']
..                 fromLat =  from_location_row['p_latitude']
..                 toLoc   =  to_location_row['i_loc']
..                 toLon   =  to_location_row['p_longitude']
..                 toLat   =  to_location_row['p_latitude']
..                 origin=[fromLon,fromLat]
..                 destination=[toLon,toLat]
..                 route = sr.searoute(origin,destination)
..                 route_length = route.properties["length"]
..                 route_tuple = (fromLoc, toLoc, route_length)
..                 distance_mat_list.append( route_tuple )


.. Remarks:
.. 
.. *   Line 4: Passing the GPS data of the locations from the AIMMS model 
..     into the local dataframe ``df_location_overview`` of 
..     the Python function ``searoute_distance_matrix``.
.. 
.. *   Line 18: The call to the Searoute library for computing the distance.
.. 
.. *   Line 25: Passing the computed matrix back to the AIMMS model.


Efficiency consideration: 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Calculating routes can take some time. 
To avoid unnecessary recalculations, the distance matrix is cached in a 
.parquet file within the AIMMS model.

Visualizing Routes
-----------------------------------------

.. figure:: images/route-direct.png
    :align: center

By using the waypoints provided by searoute, the application can display a much more realistic route on a map, 
offering better visual insights than a simple line.

.. figure:: images/route-from-searoute.png
    :align: center

The AIMMS procedure: ``ui::pr_openPageSeaRouteMap`` concatenates the waypoints computed by the searoute library
for each of the routes in the optimal solution.


References:
-------------

*   `uv <https://docs.astral.sh/uv/>`_

*   `pandas <https://pandas.pydata.org/docs/>`_

*   `pyaimms <https://documentation.aimms.com/pyaimms/pyaimms.html>`_

*   `aimmspy <https://documentation.aimms.com/aimmspy/aimmspy.html>`_

*   `searoute <https://pypi.org/project/searoute/>`_




.. 676-leveraging-python-lib.rst

.. spelling:word-list::

    uv
    aimmspy
    searoute
    lon
    lat
    toml
    haversine
    dataframe
    