Orchestrating an AIMMS App from Python
============================================

This guide shows you how to use the AIMMS Python Bridge to control 
an AIMMS application from a Python script. 
This approach is ideal for running an AIMMS model as a "headless" optimization service 
within a larger data pipeline.


We'll use a Vessel Scheduling example to illustrate the process, 
which consists of the following steps:

#.  Prepare the link between Python and the AIMMS app.

#.  Pass data from Python to the AIMMS app.

#.  Run the solver.

#.  Retrieve the solution.

#.  Write the results back to Excel.

Preparation: Setting up the AIMMS Python-Bridge
----------------------------------------------------------------

First, ensure you have the aimmspy library installed. You can install it using pip:

.. code-block:: none

    pip install aimmspy

To use the ``aimmspy`` library, import it as follows:

.. code-block:: python
    :linenos:

    from aimmspy.project.project import DataReturnTypes, Project, AimmsException, AimmsPyException, Model
    from aimmspy.utils import find_aimms_path

Next, initialize the Project and get a reference to your AIMMS model. This sets up the communication link.

.. code-block:: python
    :linenos:
    :emphasize-lines: 4,7,10,13

    # Initialize the AIMMS project
    project = Project(
        # path to the AIMMS Bin folder (on linux the Lib folder)
        aimms_path=find_aimms_path("25.4"),

        # path to the AIMMS project file
        aimms_project_file=os.path.join('..', 'AIMMSProject', 'VesselScheduling.aimms'),

        # the name of an aimms set containing identifiers. 
        exposed_identifier_set_name="AllIdentifiers",  # Limit access to specific identifiers,

        # default data type when retrieving multi-dimensional data
        data_type_preference=DataReturnTypes.PANDAS,
        
        # Fill license URL if needed.
    )
    aimms_model : Model = project.get_model(__file__)

Remarks:

*   Line 4: ``aimms_path`` The path to the AIMMS installation. 
    The ``find_aimms_path`` utility helps to locate your AIMMS installation. 

*   Line 7: ``aimms_project_file`` The path to your AIMMS project file.  
    In this example it happens to be relative to the Python script:

    .. code-block:: none

        .
        ├── AIMMSProject
        │   ├── MainProject
        │   ├── ....
        │   └── VesselScheduling.aimms
        │
        └── python-bridge
            ├── main.py
            └── requirements.txt

*   Line 10: ``exposed_identifier_set_name``  Controls which model identifiers the Python script can access.
    ``AllIdentifiers`` provides full access.

*   Line 13: ``data_type_preference`` Specifies the default data structure for data exchange. 
    We use PANDAS as it also provides seamless integration with Excel files. 
    Python POLARS also provide seamless integration with Excel files and might have been used as well.


Step 1: Passing input data from Python to AIMMS 
----------------------------------------------------------

Example: Importing Cargo Data
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This example reads data from a sheet called CargoData and 
renames the columns to match the corresponding AIMMS identifiers.

.. code-block:: python
    :linenos:
    :emphasize-lines: 17

    # Load data from the Excel workbook
    datainput_pd_cargo=pd.read_excel(datainput,sheet_name='CargoData')

    # Rename the columns obtained from the Excel Sheet to 
    # the corresponding names in the AIMMS model:
    datainput_pd_cargo.rename(columns={
        'Cargo'                : 'i_cargo',                 
        'Loading Port'         : 'ep_loadingPortsCargo',    
        'Delevering Port'      : 'ep_deliveringPortsCargo', 
        'Spot Cost'            : 'p_spotCostVessel',        
        'Minimum Loading Time' : 'ep_minTimeWindow',        
        'Maximum Loading Time' : 'ep_maxTimeWindow',        
        'Fixed Cost'           : 'p_cargoCost'              
        }, inplace=True)

    # Actually assign to AIMMS identifiers:
    aimms_model.multi_assign(datainput_pd_cargo)

Remarks:

*   Line 3: Reading the data using the Panda's 
    `read_excel <https://pandas.pydata.org/docs/reference/api/pandas.read_excel.html>`_ method. 

*   Line 6-14: Usually, the column names of tables are not the 
    same as the identifier names in an AIMMS model.  In this example too.  Therefor, Panda's 
    `rename <https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.rename.html>`_ is used.

*   Line 17: The multi_assign method efficiently passes the entire DataFrame to 
    the AIMMS model in a single, concise statement.


Note that the data for locations and vessels are read in and passed to AIMMS similarly.


Step 2: Running the optimization inside the AIMMS app
-------------------------------------------------------

After loading all data (for cargo, locations, and vessels), you can execute the optimization logic 
within your AIMMS model by calling the corresponding procedure.

.. code-block:: python

    aimms_model.pr_GenRoutesSolve()

This single line tells the AIMMS model to run its ``pr_GenRoutesSolve`` procedure, 
which typically contains the solver calls and other logic.

Step 3: Retrieving the Solution and Writing to Excel
------------------------------------------------------

Once the solver finishes, you can retrieve the results from the AIMMS model and 
write them to a new Excel workbook.

Example: Retrieving Cargo Data
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The multi_data method retrieves data from multiple AIMMS identifiers at once. 

.. code-block:: python
    :linenos:
    :emphasize-lines: 3-5

    # Retrieving the Cargo overview:
    # Getting data from AIMMS model:
    df_cargo_overview = aimms_model.multi_data(["i_act_cargo","mm::ep_calc_vesselOfCargo",
        "mm::p_calc_totalCostPerCargo","mm::sp_calc_loadingTimePerCargo",
        "mm::sp_calc_deleveringTimePerCargo"])

The rename function then prepares the data for export.

.. code-block:: python
    :linenos:

    # Renaming columns Cargo overview for Excel Sheet:
    df_cargo_overview.rename(columns={
        'i_act_cargo'                        : 'Cargo',
        'mm::ep_calc_vesselOfCargo'          : 'Vessel Used',
        'mm::p_calc_totalCostPerCargo'       : 'Cargo Cost',
        'mm::sp_calc_loadingTimePerCargo'    : 'Loading Time',
        'mm::sp_calc_deleveringTimePerCargo' : 'Delivery Time'
        },inplace=True)

And similarly for the vessel and route information.

Finally, use the ExcelWriter from Pandas to save all your result DataFrames into a single Excel file, 
with each DataFrame on its own sheet.

.. code-block:: python
    :linenos:

    with pd.ExcelWriter(excel_file_path, engine='openpyxl') as writer:
        df_vessel_overview.to_excel(writer, sheet_name='Vessel Overview', index=False)
        df_cargo_overview.to_excel( writer, sheet_name='Cargo Overview',  index=False)
        df_route_overview.to_excel( writer, sheet_name='Route overview',  index=False)


Summary
-------------

This comprehensive approach shows how to build a complete optimization workflow from start to finish 
using Python as the main orchestrator.


References:
-------------

* `Python Bridge <https://documentation.aimms.com/aimmspy>`_

* `PyPI aimmspy <https://pypi.org/project/aimmspy/>`_

.. spelling:word-list::

    aimmspy
    orchestrator