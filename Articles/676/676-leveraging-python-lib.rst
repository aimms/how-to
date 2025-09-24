Orchestrating a Python lib from AIMMS
==============================================================================

Python has an extensive and growing set of libraries.
The functionality of such a library can be useful for your Operations Research application developed using AIMMS.
The Python Bridge connects the AIMMS and Python interpreters such that 
one application may use functionality from the other.

In this article, the :doc:`Vessel Scheduling<../590/590-vessel-scheduling>` application uses the searoute Python library
for two purposes:

#.  To find more accurate distances between harbors.

#.  To find routes on a map that a vessel can travel.

This article enumerates the steps done to achieve both these purposes.

Explore Sea Route
-----------------

The sea route library can be obtained via `Pypi: <https://pypi.org/project/searoute/>`_

Note that searoute uses order [lon, lat] for locations.

A typical call:

.. code-block:: python
    :linenos:
    :emphasize-lines: 3

    origin = [-44.3699999999999, -2.565] # Ponta da Madeira, Brazil 
    destination = [-74.215606, 11.069476] # Puerto Prodeco, Colombia
    route = sr.searoute(origin,destination)

After this call, route is of type ``<class 'geojson.feature.Feature'>`` and looks (somewhat pretty printed):

.. code-block:: json
    :linenos:

    {
        "geometry": {
            "coordinates": [
                [ -43.9,      -1.4      ],
                [ -48,        -1E-05    ],
                [ -51.9,       5.4      ],
                [ -56.4243,    8.2122   ],
                [ -61,        11        ],
                [ -61.8055,   10.6839   ],
                [ -63.773623, 11.613686 ],
                [ -65.62372,  12.487713 ],
                [ -65.942,    12.5294   ],
                [ -70,        13        ],
                [ -75.25,     11.7      ]
            ],
            "type": "LineString"
        },
        "properties": {
            "duration_hours": 90.5268642136653,
            "length": 4023.738060569,
            "units": "km"
        },
        "type": "Feature"
    }

in the Vessel Scheduling application we are interested in the: 

* distance, which is given by ``properties.length``, and

* in the actual route, which is given in ``geometry.coordinates`` as a list of [lon, lat] tuples.


Making Python libraries available to AIMMS app
-----------------------------------------------

First, you'll need to ensure the necessary libraries are there:

* `pandas installation <https://pypi.org/project/pandas/>`_, and `pandas documentation <https://pandas.pydata.org/docs/>`_

* `aimmspy installation <https://pypi.org/project/aimmspy/>`_, and `aimmspy documentation <https://documentation.aimms.com/aimmspy/aimmspy.html>`_

* `searoute <https://pypi.org/project/searoute/>`_  

Fortunately, these libraries are loaded on demand, based on the toml file:

.. code-block:: none

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

To enable access to the relevant python code, the link and functions need to be scanned.
This is done using the statement:

.. code-block:: aimms

    py::run_python_script("leverage-sea-route.py");



Using searoute to compute distances
---------------------------------------

Consider the distance between Caldera, Chile, and Bahia Blanca, Argentine - 
two harbors on the east and west coast of South America.
The haversine distance between these two locations is 1520 km, 
but the searoute distance is 5180 km.
Clearly, the accuracy of distance influences the accuracy of the optimal routes found.

With 175 harbors available in the Vessel Scheduling example, the distance matrix is 175 x 175.
To avoid having to call 15K times the upper half of the matrix is stored in a Pandas dataframe
before passing this information to AIMMS.

.. code-block:: python
    :linenos:
    :emphasize-lines: 4,18,25

    def searoute_distance_matrix():
        # Getting data from AIMMS model:
        distance_mat_list = []
        df_location_overview = aimms_model.multi_data(["i_loc","p_latitude","p_longitude"])
        location_overview_no_rows = len( df_location_overview )
        for from_pos in range(location_overview_no_rows):
            for to_pos in range(from_pos+1,location_overview_no_rows):
                from_location_row = df_location_overview.iloc[from_pos]
                to_location_row   = df_location_overview.iloc[  to_pos]
                fromLoc =  from_location_row['i_loc']
                fromLon =  from_location_row['p_longitude']
                fromLat =  from_location_row['p_latitude']
                toLoc   =  to_location_row['i_loc']
                toLon   =  to_location_row['p_longitude']
                toLat   =  to_location_row['p_latitude']
                origin=[fromLon,fromLat]
                destination=[toLon,toLat]
                route = sr.searoute(origin,destination)
                route_length = route.properties["length"]
                route_tuple = (fromLoc, toLoc, route_length)
                distance_mat_list.append( route_tuple )

        distance_columns = ['i_loc_from','i_loc_to','p_distance_searoute']
        distance_df = pd.DataFrame(distance_mat_list,columns=distance_columns)
        aimms_model.p_distance_searoute.assign(distance_df)

Remarks:

*   Line 4: Passing the GPS data of the locations from the AIMMS model 
    into the local dataframe ``df_location_overview`` of 
    the Python function ``searoute_distance_matrix``.

*   Line 18: The call to the Searoute library for computing the distance.

*   Line 25: Passing the computed matrix back to the AIMMS model.


Efficiency consideration: 
Clearly, computing routes over sea takes more time that just a haversine computation; typically a couple of minutes.
This is why the AIMMS model caches the distance data in a parquet file: ``distanceMatrixFromSeaRoute.parquet``
and uses functions from `AimmsDEX <https://documentation.aimms.com/dataexchange/index.html>`_ to write/read that cache.
Details about this caching can be found in the Vessel Scheduling app, procedures: ``pr_saveSeaRouteDistanceMatrixToParquet`` and 
``pr_readSeaRouteDistanceMatrixFromParquet``.  Detailing these functions is outside the scope of this article.


Using searoute to obtain detailed routes
-----------------------------------------

Supplying just the locations a vessel visits, delivers for the route ``vessel15_c20`` (from the ``VS_15Vessel_30Cargo`` input data):

.. figure:: images/route-direct.png
    :align: center

Supplying the waypoints provided by the Python library searoute, delivers for the same route:

.. figure:: images/route-from-searoute.png
    :align: center

The AIMMS procedure: ``ui::pr_openPageSeaRouteMap`` concatenates the waypoints computed by the searoute library
for each of the routes in the optimal solution.


References:
-------------

*   `uv <https://docs.astral.sh/uv/>`_







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
    