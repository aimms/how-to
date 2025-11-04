Orchestrating Contract Allocation AIMMS App from Python
==========================================================

.. meta::
    :keywords: AIMMS, Python-Bridge, aimmspy, headless optimization, data pipeline, pandas, Contract Allocation, solver, data exchange
    :description: A guide on orchestrating a headless AIMMS optimization application from a Python script using the AIMMS Python-Bridge. Learn to pass data, run the solver, and retrieve results for a data pipeline.

This guide shows you how to use the AIMMS Python-Bridge to control an AIMMS application from a Python script. 
This approach is ideal for running an AIMMS model as a "headless" optimization service within a larger data pipeline.

Please refer to the :doc:`Contract Allocation<../383/383-contract-allocation>` example
to follow along with this article. This article illustrates the process, which consists of the following steps:

#.  Prepare the link between Python and the AIMMS app.
#.  Pass data from Python to the AIMMS app.
#.  Run the solver.
#.  Retrieve the solution.
#.  Write the results back to Excel.

Prepare Link Between Python and AIMMS 
-----------------------------------------------

First, ensure you have the ``aimmspy`` library installed. You can install it using pip:

.. code-block:: none

    pip install aimmspy

To use the ``aimmspy`` library, import it as follows:

.. code-block:: python
    :linenos:

    from aimmspy.project.project import DataReturnTypes, Project, AimmsException, AimmsPyException, Model
    from aimmspy.utils import find_aimms_path

Next, initialize the ``Project`` and get a reference to your AIMMS model. This sets up the communication link.

.. code-block:: python
    :linenos:
    :emphasize-lines: 4,7,10,13

    # Initialize the AIMMS project connection.
    project = Project(
        # Path to the AIMMS Bin folder (required for API connection).
        aimms_path=find_aimms_path("25"),

        # Path to the AIMMS project file to be opened.
        aimms_project_file=os.path.join(projectroot, 'AIMMS-project', 'ContractAllocation.aimms'),

        # The name of the AIMMS set containing all identifiers exposed to Python.
        exposed_identifier_set_name="AllIdentifiers", 

        # Default data type for multi-dimensional data retrieval (Pandas DataFrame is preferred).
        data_type_preference=DataReturnTypes.PANDAS,
        
        # license_url: Fill license URL if using a network license.
    )

    # Get a reference to the active AIMMS model instance for data transfer and execution.
    aimms_model : Model = project.get_model(__file__)

Remarks:

*   Line 4: ``aimms_path`` is the path to the AIMMS installation. 
    The ``find_aimms_path`` utility helps to locate your AIMMS installation. 

*   Line 7: ``aimms_project_file`` is the path to your AIMMS project file.  
    In this example it happens to be relative to the Python script:

    .. code-block:: none

        .
        ├── AIMMS-project
        │   ├── MainProject
        │   ├── ....
        │   └── ContractAllocation.aimms
        │
        └── python-bridge
            ├── main.py
            └── requirements.txt

*   Line 10: ``exposed_identifier_set_name`` controls which model identifiers the Python script can access.
    ``AllIdentifiers`` provides full access.

*   Line 13: ``data_type_preference`` specifies the default data structure for data exchange. 
    We use ``pandas`` as it also provides seamless integration with Excel files. 
    Python ``polars`` also provide seamless integration with Excel files and might have been used as well.


Passing Input Data  
------------------------------

Importing Producers Data
^^^^^^^^^^^^^^^^^^^^^^^^^

This example reads data from a sheet called ``Producers`` and 
renames the columns to match the corresponding AIMMS identifiers.

.. code-block:: python
    :linenos:
    :emphasize-lines: 12

    # 1. Load and Assign Producer Data (i_producer, capacities)
    datainput_pd_producer = pd.read_excel(datainput, sheet_name='Producers')

    # Rename columns to match exact AIMMS identifiers for seamless assignment.
    datainput_pd_producer.rename(columns={         
        'Producers'             : 'i_producer',                 
        'Available Capacity'    : 'p_availableCapacity',            
        'Minimal Delivery'      : 'p_minimalDelivery'
        }, inplace=True)

    # Assign data to the corresponding AIMMS identifiers.
    aimms_model.multi_assign(datainput_pd_producer)

Remarks:

*   Line 3: Reading the data using the Panda's 
    `read_excel <https://pandas.pydata.org/docs/reference/api/pandas.read_excel.html>`_ method. 

*   Line 6-14: Usually, the column names of tables are not the 
    same as the identifier names in an AIMMS model.  In this example too.  Therefor, Panda's 
    `rename <https://pandas.pydata.org/docs/reference/api/pandas.DataFrame.rename.html>`_ is used.

*   Line 17: The ``multi_assign`` method efficiently passes the entire ``DataFrame`` to 
    the AIMMS model in a single, concise statement.


Note that the data for the other sheets are read in and passed to AIMMS similarly.


Running the Optimization Inside the AIMMS App
-------------------------------------------------------

After loading all data, you can execute the optimization logic 
within your AIMMS model by calling the corresponding procedure.

.. code-block:: python
    :linenos:
    
    # 4. Execute the AIMMS Optimization
    # Calls the main procedure in AIMMS to solve the optimization problem.
    aimms_model.MainExecution()

This single line tells the AIMMS model to run its ``MainExecution`` procedure, 
which typically contains the solver calls and other logic.

Retrieving the Solution and Writing to Excel
--------------------------------------------------------

Once the solver finishes, you can retrieve the results from the AIMMS model and 
write them to a new Excel workbook.

Retrieving Contract Allocation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The ``multi_data`` method retrieves data from multiple AIMMS identifiers at once. 

.. code-block:: python
    :linenos:
    :emphasize-lines: 2

    # Get the total contract fulfillment results.
    df_contract_allocation = aimms_model.multi_data(["i_contractExport","p_totalGeneration"])

The ``rename`` function then prepares the data for export.

.. code-block:: python
    :linenos:

    # Rename columns for user-friendly export to Excel.
    df_contract_allocation.rename(columns={
        'i_contractExport'      : 'Contract',
        'p_totalGeneration'     : 'Total Generation'
        }, inplace=True)


Finally, use the ``ExcelWriter`` from ``pandas`` to save all your result ``DataFrames`` into a single Excel file, 
with each ``DataFrame`` on its own sheet.

.. code-block:: python
    :linenos:

    # Use ExcelWriter to write multiple DataFrames to separate sheets in a single file.
    with pd.ExcelWriter(excel_file_path, engine='openpyxl') as writer:
        df_producer_allocation.to_excel(writer, sheet_name='Allocation per Producer', index=False)
        df_contract_allocation.to_excel(writer, sheet_name='Contract Allocation', index=False)


.. seealso::
        
    * `Python Bridge <https://documentation.aimms.com/aimmspy>`_.

    * `PyPI aimmspy <https://pypi.org/project/aimmspy/>`_.

.. spelling:word-list::

    aimmspy
    orchestrator