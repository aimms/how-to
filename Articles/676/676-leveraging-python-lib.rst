Orchestrating the ``searoute`` Python Library from AIMMS
==============================================================================

.. meta::
   :keywords: Python-Bridge, searoute, maritime routing, vessel scheduling, distance matrix, GeoJSON, aimmspy, pandas, pyproject.toml, Haversine
   :description: Demonstrates integrating the Python searoute library with the AIMMS Python-Bridge to compute accurate maritime distances and visualize realistic sea routes on a map widget.

The vast and growing Python ecosystem offers powerful libraries that can add
valuable functionality to your Operations Research applications developed in AIMMS.
The **AIMMS Python-Bridge** connects these two environments, providing a crucial two-way link:

#.  Using an AIMMS model from a Python application.
#.  **Using Python libraries from an AIMMS application.**

This article focuses on the second, more common scenario, demonstrating how to use
the open-source ``searoute`` library to enhance a strategic maritime application. 

Please refer to the :doc:`Vessel Scheduling<../590/590-vessel-scheduling>` example
to follow along with this article.


Exploring the ``searoute`` Library
-------------------------------------

The running example for this article is the :doc:`Vessel Scheduling<../590/590-vessel-scheduling>` application,
which uses the Python `searoute <https://pypi.org/project/searoute/>`_ library for two key purposes:

#.  To calculate **more accurate distances** between harbors, significantly improving optimization quality.
#.  To visualize **realistic vessel routes** on a map, providing better user insight.

The Python library ``searoute`` accepts coordinates in the order of ``[longitude, latitude]`` and
returns a standard ``geojson`` object containing the route information.

A typical call within a Python script looks like this:

.. code-block:: python
    :linenos:
    :emphasize-lines: 3

    origin = [-44.37, -2.565] # Ponta da Madeira, Brazil
    destination = [-74.22, 11.07] # Puerto Prodeco, Colombia
    route = sr.searoute(origin,destination)


The resulting ``geojson`` object contains the useful data within its ``properties`` and
``geometry`` attributes:

* ``properties.length``: The accurate **distance in kilometers**.
* ``geometry.coordinates``: The list of ``[lon, lat]`` coordinates that describe the **route's waypoints**.

In the :doc:`Vessel Scheduling<../590/590-vessel-scheduling>` application, we are
primarily interested in the distance (from ``properties.length``) and the actual
route waypoints (from ``geometry.coordinates``).

Integrating with AIMMS
---------------------------

Utilizing the ``searoute`` functionality from AIMMS requires two main setup steps:
specifying the necessary Python packages and establishing the AIMMS-specific linkage.

#.  **Dependency Specification**

    The ``pyproject.toml`` file ensures that the correct versions of the required
    libraries, including the core ``searoute`` library, are loaded automatically:

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

#.  **AIMMS Project Linkage**

    The AIMMS project must link to the Python code containing the calls to ``searoute``.
    Crucially, the `pyaimms repository library <https://documentation.aimms.com/aimmspy/pyaimms/pyaimms.html>`_
    is essential, as it provides the Python client allowing your script to access
    and interact with the AIMMS model's data.

    To enable the AIMMS compiler to link and scan the Python code (in this case,
    ``leverage-sea-route.py``), you use the ``py::run_python_script`` statement:

    .. code-block:: aimms
        :linenos:

        ! Execute the specified Python script
        py::run_python_script("leverage-sea-route.py");

Using ``searoute`` to Compute Distances
-----------------------------------------

The difference between a simple **geometric Haversine distance** and the
``searoute`` distance can be dramatic. For a real-world example, the straight-line
Haversine distance between Caldera (Chile) and Bahia Blanca (Argentina) is only 1520 km,
whereas ``searoute`` calculates a more realistic route of **5180 km**: a crucial difference
for accurate optimization.

To compute an entire **distance matrix**, the Python script first retrieves location data from AIMMS,
iterates through all pairs to calculate the routes, and then sends the results back via a pandas ``DataFrame``.

.. code-block:: python
    :linenos:

    def searoute_distance_matrix():
        """Calculates a distance matrix for all locations retrieved from AIMMS and assigns it back."""
        
        # Step 1: Retrieve location data from the AIMMS model
        # Gets location index ('i_loc'), latitude, and longitude for all known locations.
        df_location_overview = aimms_model.multi_data(["i_loc","p_latitude","p_longitude"])
        distance_mat_list = []
        
        # ... code for iterating through locations and calculating distances ...

        # Step 2: Build a DataFrame with the calculated distances
        # Structure columns to match the AIMMS 2D parameter: indices first, then the value.
        distance_columns = ['i_loc_from','i_loc_to','p_distance_searoute']
        distance_df = pd.DataFrame(distance_mat_list,columns=distance_columns)

        # Step 3: Pass the DataFrame back to the AIMMS model
        # Assigns the distance matrix to the parameter 'p_distance_searoute'.
        aimms_model.p_distance_searoute.assign(distance_df)        


.. hint::

    Calculating many routes can be time-consuming. To prevent unnecessary recalculations
    during development or multiple runs, the distance matrix is often **cached** in a
    ``.parquet`` file within the AIMMS model's environment.

Visualizing Routes
-----------------------------------------

To obtain the actual path as a list of GPS waypoints between two harbors, an AIMMS procedure is used.
This procedure has the following prototype:

.. code-block:: aimms
    :linenos:

    Procedure pr_getRoute {
        Arguments: (ep_from, ep_to, p_routeLength, s_waypoints, p_latWP, p_lonWP);

Here, the input is ``ep_from`` and ``ep_to`` (element parameters from the set of named locations ``s_locations``).

The output parameters are:

* ``p_routeLength``: The total length of the computed route.
* ``s_waypoints``, ``p_latWP``, ``p_lonWP``: The **unnamed GPS waypoints** (index, latitude, longitude) that define the path.

This procedure, which manages the communication, is typically placed in a module. 
The actual call to the Python function ``searoute_route2`` is made using the ``py::run_python_statement``:

.. code-block:: aimms
    :linenos:

    ! --- External Python Route Calculation ---
    ! Prototype searoute_route2 (for reference)
    ! def searoute_route2(fromLat:float,fromLon:float,toLat:float,toLon:float,
    !     indexName:str,latParName:str,lonParName:str,lenParName:str):

    ! Prepare Python call string, injecting coordinates and AIMMS output identifiers
    ! Five decimals (%12.5n) provides a location precision of roughly 1 meter, sufficient for maritime routes.
    _sp_pyCall := formatString("searoute_route2(%12.5n,%12.5n,%12.5n,%12.5n,\"lpa::i_globWayPoint\",\"lpa::p_globLat\",\"lpa::p_globLon\",\"lpa::p_globRouteLength\")",
        _p_fromLat, _p_fromLon, _p_toLat, _p_toLon );
    pr_scanPythonSource();        ! Ensure Python environment is ready
    py::run_python_statement(_sp_pyCall); ! Execute the external route calculation


The Python function ``searoute_route2`` performs the route calculation 
and uses two separate ``aimms_model.multi_assign`` calls to send the results back: the scalar distance and the list of waypoints.

.. code-block:: python
    :linenos:

    def searoute_route2(fromLat: float, fromLon: float, toLat: float, toLon: float,
                        indexName: str, latParName: str, lonParName: str, lenParName: str):
        """Calculates sea route and assigns results to dynamically named AIMMS parameters."""

        origin = [fromLon, fromLat]
        destination = [toLon, toLat]
        route = sr.searoute(origin, destination)

        # --- Assign Dynamic Length Parameter ---
        route_length = route.properties["length"]
        route_length_df = pd.DataFrame({lenParName: [route_length]})
        aimms_model.multi_assign(route_length_df, options={"return_type": DataReturnTypes.PANDAS})

        # --- Assign Dynamic Waypoint Parameters ---
        route_columns = [lonParName, latParName]
        route_df = pd.DataFrame(route.geometry.coordinates, columns=route_columns)
        
        # Add dynamic index column.
        route_df[indexName] = route_df.index
        
        # Reorder columns: Index column must be first.
        route_columns = route_df.columns.tolist()
        route_df = route_df[route_columns[-1:] + route_columns[:-1]]
        
        # Assign waypoints DataFrame to AIMMS.
        aimms_model.multi_assign(route_df, options={"return_type": DataReturnTypes.PANDAS})

Once the waypoints for all legs of the optimal solution are computed and concatenated
using a utility AIMMS procedure (e.g., ``ui::pr_openPageSeaRouteMap``),
the application can display a far more **realistic route on a map**,
offering superior visual insights into the optimized schedule.

.. grid:: 2

    .. grid-item-card::  

        .. figure:: images/route-direct.png
            :align: center

            Straight-Line Haversine Route


    .. grid-item-card::  

        .. figure:: images/route-from-searoute.png
            :align: center

            Realistic ``searoute`` Route


The AIMMS procedure ``ui::pr_openPageSeaRouteMap`` concatenates the waypoints computed by the searoute library
for each of the routes in the optimal solution.

.. seealso::

    * `uv <https://docs.astral.sh/uv/>`_ documentation.
    * `PyData pandas <https://pandas.pydata.org/docs/>`_.
    * `pyaimms <https://documentation.aimms.com/pyaimms/pyaimms.html>`_ documentation.
    * `aimmspy <https://documentation.aimms.com/aimmspy/aimmspy.html>`_ documentation.
    * `PyPI searoute <https://pypi.org/project/searoute/>`_.


.. spelling:word-list::

    uv
    aimmspy
    searoute
    lon
    lat
    toml
    haversine
    dataframe
    geojson