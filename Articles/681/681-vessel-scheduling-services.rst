Exposing the Vessel Scheduling as a REST Service
==================================================

.. meta::
   :keywords: REST service, dex::ServiceName, dex::ReadFromFile, dex::WriteToFile, statelessness, pr_initTask, AimmsCmd, AIMMS Cloud, Python requests, task polling
   :description: Explains how to expose an AIMMS vessel scheduling procedure as a REST service via dex::ServiceName, implement stateless task reset, manage service lifecycle, and test with a Python polling client.

Many Operations Research applications employ complex, powerful algorithms that perform sequential tasks, such as generating candidate data, formulating a mathematical program, and finding an optimal solution.

This core algorithmic process can be easily exposed as a **REST API service**, allowing external client applications—whether internal systems, web frontends, or other services—to submit input data and receive optimal solutions.

A service in an AIMMS model is defined simply by associating a service name with an AIMMS procedure. A key feature is the flexibility in data formats (like JSON, XML, or Excel) used for the input (request body) and output (response body).

This article details the general process of service implementation in AIMMS:

#. Defining and implementing the service procedure.
#. Understanding the procedure's logic, focusing on achieving statelessness for reliable, concurrent execution.
#. Running and controlling the service in different environments (AIMMS IDE, ``AimmsCmd``, AIMMS Cloud).


Defining and Implementing the Service
---------------------------------------

Coding the Service Procedure
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

A service is formally defined by setting the ``dex::ServiceName`` property on an AIMMS procedure. The following code snippet shows the main procedure, which acts as the service entry point:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 1,7,8,21

    Procedure pr_solveVesselSchedulingExcel {
        Body: {
            block
            
                pr_initTask();
            
                _sp_inp := dex::api::RequestAttribute( 'request-data-path' ) ;
                _sp_out := dex::api::RequestAttribute( 'response-data-path' ) ;
            
                pr_actuallySolveVesselSchedulingExcel( _sp_inp, _sp_out );
            
            onerror _ep_err do
            
                _sp_msg := errh::Message( _ep_err );
                display _sp_msg ;
            
            endblock ;
            
            return 1 ;
        }
        dex::ServiceName: solveVesselSchedulingExcel;  ! Line 21: The service name for external calls
        StringParameter _sp_inp;
        StringParameter _sp_out;
        ElementParameter _ep_err {
            Range: errh::PendingErrors;
        }
        StringParameter _sp_msg;
    }

Remarks:

* Procedure Name (Line 1): ``pr_solveVesselSchedulingExcel`` is executed when the service is called.
* Service Name (Line 21): ``solveVesselSchedulingExcel`` is the external endpoint name used by clients.
* Data Paths (Lines 7, 8): The ``dex::api::RequestAttribute`` function is used to retrieve the temporary file paths where the **request body** (input) and **response body** (output) data reside.

Ensuring Statelessness with ``pr_initTask``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

When an application runs as a service, it's crucial that each request (or "task") runs independently. The procedure ``pr_initTask`` is executed at the start of every task to enforce statelessness by resetting the model identifiers.

The term **"Data Model"** refers to the core AIMMS identifiers (sets, parameters, variables, and constraints) that represent the objects in the real-world problem being modeled (e.g., ``s_cargoes``, ``s_vessels``). 
These must be cleared before a new task begins. In contrast, "application management identifiers" (e.g., WebUI and PRO library identifiers, logging paths) should be left untouched.

.. code-block:: aimms
    :linenos:

    Procedure pr_initTask {
        Body: {
            ! Reset the data model (clear model data)
            empty s_cargoes, s_vessels, s_locations, s_calc_feasibleRoutes ;

            ! Clean up any dynamically generated mathematical programs.
            _ep_gmp := first( AllGeneratedMathematicalPrograms );
            while _ep_gmp do
                gmp::Instance::Delete( _ep_gmp );
            endwhile ;

            ! Other cleanups
            StringGarbageCollect();
            CleanDependents();
        }
        ElementParameter _ep_gmp {
            Range: AllGeneratedMathematicalPrograms;
        }
    }

The Core Logic: ``pr_actuallySolveVesselSchedulingExcel``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This procedure handles the business logic: reading the input file, executing the optimization, and writing the results to the output file.

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 10,29

    Procedure pr_actuallySolveVesselSchedulingExcel {
        Arguments: (sp_inp,sp_out);
        Body: {
            dex::AddMapping(
                mappingName : "ImportDataSet",
                mappingFile : "Mappings/Generated/ImportDataSet-Excel.xml");
            
            p_vesselVelocity := 37.04 [km/hour]; ! Line 10: Example of model parameter setting
            
            if dex::ReadFromFile( ! Line 12: Read request data from temporary file
                dataFile    : sp_inp, 
                mappingName : "Generated/ImportDataSet-Excel") then
            
                ! Activate all master data
                bp_activeCargoes(i_cargo):= 1;
                bp_activeVessels(i_vessel) := 1;
                bp_activeLocations(i_loc) := 1;
            endif ;
            
            pr_calculateRoutesAndCost(ep_routeCalculationImplementation: ep_selectedRouteCalculationImplementation );
            
            solve mm::mp_vesselScheduling;
            
            ! Post-Execution: Process and structure results
            mm::pr_post_vesselResults();
            mm::pr_post_cargoResults();
            mm::pr_post_routeResults();
            
            dex::WriteToFile( ! Line 29: Write response data to temporary file
                dataFile    :  sp_out, 
                mappingName :  "Generated/ExportDataSet-Excel", 
                pretty      :  1);
        }
        DeclarationSection Argument_declarations {
            StringParameter sp_inp { Property: Input; }
            StringParameter sp_out { Property: Input; }
        }
    }

Remarks:

* Data In (Line 12): The ``dex::ReadFromFile`` function uses the input path (``sp_inp``) and a defined data mapping (``ImportDataSet-Excel``) to load the request data into the model identifiers.
* Data Out (Line 29): The ``dex::WriteToFile`` function uses the output path (``sp_out``) and a second data mapping (``ExportDataSet-Excel``) to write the solution data back, which is then sent as the response.

Service Management and Execution
--------------------------------

Starting and Stopping the Service
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

How you manage the service depends on the execution environment:

.. list-table:: Service Management Across Environments
   :widths: 10 55 35
   :header-rows: 1

   * - Environment
     - Management Method
     - Purpose
   * - **AIMMS IDE**
     - Manually call ``dex::api::StartAPIService()`` and ``dex::api::StopAPIService()``.
     - For development and testing purposes.
   * - **AimmsCmd / Docker**
     - Use ``AimmsCmd --run-only dex::api::RESTServiceHandler ...``
     - Starts the service headless, providing fine control over resource management.
   * - **AIMMS Cloud**
     - **Automatic.** No manual action is needed.
     - The service is automatically provisioned and started when a task is posted.

Controlling Resources in Headless Mode
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

When using ``AimmsCmd`` or Docker via the ``dex::api::RESTServiceHandler``, service execution is automatically managed by timeout or maximum request limits. You can control this behavior using the following session arguments:

* ``dex::api::RESTServiceMaxRequests``: Sets the maximum number of requests before the service shuts down.
* ``dex::api::RestServiceMinTimeout``: The minimum amount of time the service will run (in seconds).
* ``dex::api::RESTServiceTimeout``: The maximum amount of time the service will run (in seconds).


Testing the Service using Python
-----------------------------------

Testing a service involves sending a request to the exposed endpoint. A common and robust approach is to use a Python script leveraging the popular ``requests`` library.

Since AIMMS services, particularly on the Cloud, often involve asynchronous processing (meaning the solution takes time), the client logic typically follows three steps:

1.  Submit the Task (POST): Send the input data to the service endpoint. The server responds immediately with a unique Task ID.
2.  Poll the Status (GET): Repeatedly check the status of the Task ID until the state is "completed" or "failed."
3.  Obtain the Response (GET): Once completed, retrieve the final results using the Task ID.

Python Client Code Flow
~~~~~~~~~~~~~~~~~~~~~~~~~

A conceptual Python client script using the ``requests`` library looks like this:

.. code-block:: python
    :linenos:

    import requests
    import json
    import time

    # --- Configuration ---
    BASE_URL = 'https://[your-account].aimms.cloud/pro-api/v1'
    API_KEY = 'YOUR_SECRET_API_KEY'
    APP_NAME = 'VesselSchedulingApp'
    SERVICE_NAME = 'solveVesselSchedulingExcel' 

    headers = {'Authorization': f'Bearer {API_KEY}'} # Or {'apiKey': API_KEY} depending on PRO version
    
    # 1. Submit the Task (POST)
    url_submit = f'{BASE_URL}/tasks/{APP_NAME}/latest/{SERVICE_NAME}'
    
    # Assuming 'input_data.json' is the file containing the vessel/cargo data
    with open('input_data.json', 'r') as f:
        request_body = json.load(f)

    print("Submitting task...")
    response_submit = requests.post(url_submit, json=request_body, headers=headers)
    response_submit.raise_for_status() # Check for HTTP errors (4xx or 5xx)
    task_id = response_submit.json()['id']
    print(f"Task submitted. ID: {task_id}")

    # 2. Poll the Status (GET)
    url_poll = f'{BASE_URL}/tasks/{task_id}'
    status = ""
    while status not in ['completed', 'failed']:
        time.sleep(5)
        response_poll = requests.get(url_poll, headers=headers)
        response_poll.raise_for_status()
        status = response_poll.json()['state']
        print(f"Current status: {status}")

    # 3. Obtain the Response (GET)
    if status == 'completed':
        url_response = f'{BASE_URL}/tasks/{task_id}/response'
        response_final = requests.get(url_response, headers=headers)
        response_final.raise_for_status()
        
        # The response body is the data written by AIMMS's dex::WriteToFile
        solution_data = response_final.json()
        print("\n--- Solution Retrieved ---")
        # Process and display results (e.g., solution_data['OptimalRoutes'])
    else:
        print("Task failed. Check AIMMS Cloud logs for details.")


Deployment and Testing on AIMMS Cloud
------------------------------------------

The AIMMS Cloud offers a fully managed PRO environment, simplifying the entire deployment lifecycle.

Deployment
~~~~~~~~~~~~~

Once your AIMMS project is complete, you deploy it using the AIMMS Developer environment by creating an end-user package (an ``.aimmspack`` file) and uploading it to the Cloud Portal.

1.  Export: Use the AIMMS Developer menu (:menuselection:`File > Export End User Project...`) to create the ``.aimmspack`` file.
2.  Publish: Log into the AIMMS Cloud. In the Apps section, publish the new application version, providing the ``.aimmspack`` file.
3.  Service Activation: Because the procedure ``pr_solveVesselSchedulingExcel`` has the ``dex::ServiceName`` attribute, the REST service is automatically exposed upon successful publication. The Cloud handles all necessary infrastructure setup, including load balancing and routing.

Testing on the Cloud
~~~~~~~~~~~~~~~~~~~~

Testing is achieved by calling the endpoint using the **AIMMS PRO REST API**. The base URL structure for calling a published service endpoint is:

``https://[Your-Account].aimms.cloud/pro-api/v1/tasks/[AppName]/[AppVersion]/[ServiceName]``

By posting your input data to this unique URL (as demonstrated in the Python client example), the Cloud automatically:

* Queues the task.
* Launches a dedicated AIMMS session (a containerized environment, or "pod").
* Executes the associated procedure (e.g., ``pr_solveVesselSchedulingExcel``).
* Manages the session lifecycle ensuring it stops after the task is complete, and making the final output available via the task ID.

This automated management eliminates the need for manual service start/stop commands or complex resource configuration, 
making the Cloud the preferred environment for production-scale service execution.

.. spelling:word-list::

    initTask
    frontends
    scalable