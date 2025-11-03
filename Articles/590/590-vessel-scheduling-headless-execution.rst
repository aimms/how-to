Headless Execution with AIMMS Application
============================================

.. meta::
    :keywords: AIMMS, headless execution, AimmsCmd, Docker, REST service, automation, batch processing, SessionArgument, AIMMS Cloud
    :description: Comprehensive guide to setting up AIMMS headless execution using AimmsCmd on a local machine, within a Docker container, and via the automated AIMMS Cloud Tasks environment.

**Headless execution** refers to running an application without a Graphical User Interface (GUI). 
This mode is essential for automation, batch processing, and deploying applications as services.

Depending on your deployment environment, you can choose the best method for your AIMMS application:

#. On a local machine (Windows/Linux) using ``AimmsCmd``.
#. Inside a **Docker container** for portability and isolation.
#. In the **AIMMS Cloud** for managed, scalable execution.


Headless Execution on Your Laptop
----------------------------------

When your project files and data reside on the same Windows machine, directly using the ``AimmsCmd`` executable is often the simplest approach.

Directly Using ``AimmsCmd``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The ``AimmsCmd`` command line follows a specific structure:

.. code-block:: bat

    AimmsCmd [options for AimmsCmd] projectName.aimms [options for AIMMS application] < aimmscmdfile > logfile 2> errfile

This structure maintains a clear order of arguments:

1.  **AIMMS Command Options:** Optional, but critical for defining the execution. A common example is ``--run-only <procedure name>``.
2.  **Project Name:** Mandatory (e.g., ``VesselScheduling.aimms``).
3.  **Session Arguments:** Arguments passed to the AIMMS model, which are retrieved inside the application using the `SessionArgument <https://documentation.aimms.com/functionreference/system-interaction/invoking-actions/sessionargument.html>`_ function.
4.  **Input/Output Redirection:** Used for sending commands to ``AimmsCmd`` and capturing logs.

**Example A: Single Run Procedure with Session Arguments**

This example demonstrates using ``--run-only`` to execute a specific procedure (``pr_solveModelSessionArguments``) and passing input/output file paths as session arguments.

.. code-block:: bat
    :linenos:

    echo on

    rem Use AimmsCMD to start Vessel Scheduling with session arguments.

    set AIMMS_VERSION=25.3.2.6-x64-VS2022
    set AIMMS_EXECUTABLE=%localappdata%\AIMMS\IFA\Aimms\%AIMMS_VERSION%\Bin\AimmsCMD.exe

    pushd ..\AIMMSProject

    %AIMMS_EXECUTABLE% --run-only pr_solveModelSessionArguments "VesselScheduling.aimms" data\VS_7Vessel_20Cargo.xlsx data\VS_7Vessel_20Cargo_Results.xlsx

    popd

    echo Current time: %time%

    pause

Remarks:

* Line 10: The procedure ``pr_solveModelSessionArguments`` handles the entire workflow (loading data, solving, saving results) using the two file paths provided as session arguments.
* ``pushd`` / ``popd``: These commands ensure the AIMMS session's working directory is the project folder, simplifying file path management.

**Example B: Using an ``AimmsCmd`` Script and File Redirection**

This method uses file redirection (``<`` and ``>``) along with an ``AimmsCmd`` script (sometimes called a properties file) to control execution.

.. code-block:: bat
    :linenos:

    echo on

    rem Use AimmsCMD to start Vessel Scheduling via an AimmsCmd script

    set AIMMS_VERSION=25.3.2.6-x64-VS2022
    set AIMMS_EXECUTABLE=%localappdata%\AIMMS\IFA\Aimms\%AIMMS_VERSION%\Bin\AimmsCMD.exe

    pushd ..\AIMMSProject

    %AIMMS_EXECUTABLE% "VesselScheduling.aimms" < single-run.properties > log/single-run.log 2> log/single-run.err

    popd

    echo Current time: %time%

    pause

The input script ``single-run.properties`` contains a sequence of commands executed by ``AimmsCmd``:

.. code-block:: none

    let sp_theExcelInput := "data/VS_7Vessel_20Cargo.xlsx" ;
    let sp_theExcelOutput := "data/VS_7Vessel_20Cargo_Results.xlsx" ;
    run pr_solveTheModel ;
    quit ;

**Example C: Starting the Application as a Service**

AIMMS can be run headless as a REST service using its built-in API handler. This makes the application available for remote, asynchronous calls.

.. code-block:: none
    :linenos:

    echo on

    rem Use AimmsCMD to start Vessel Scheduling as a service.

    set AIMMS_VERSION=25.3.2.6-x64-VS2022
    set AIMMS_EXECUTABLE=%localappdata%\AIMMS\IFA\Aimms\%AIMMS_VERSION%\Bin\AimmsCMD.exe

    set VS_ROOT=C:\u\s\examples\application-examples\vessel-scheduling
    set VS_PROJECT=%VS_ROOT%\AIMMSProject

    pushd %VS_PROJECT%

    %AIMMS_EXECUTABLE% --run-only dex::api::RESTServiceHandler "VesselScheduling.aimms" --dex::serviceTimeOut 30000

    popd

    echo Current time: %time%

    pause

Remarks:

* Line 10: uses the procedure ``dex::api::RESTServiceHandler`` with the ``--run-only`` option to activate the service endpoint. The session arguments ``--dex::serviceTimeOut 30000`` override the default timeout to 30 seconds.


Headless Execution in a Docker Container
-----------------------------------------------

Docker provides a portable, consistent environment for running AIMMS. Preparing an image involves packaging the application's dependencies and defining how data is exchanged.

Preparing the Docker Image
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The setup for a Docker image requires four core components:

* **AIMMS Executable/Installation:** Based on the official `aimms-eo <https://github.com/aimms/aimms-eo>`_ Docker image, which is parameterized for the AIMMS version and includes necessary software components.
* **AIMMS License:** Must be accessible in the ``/data`` folder, usually via mounting or inclusion. For a network license, the ``license.cfg`` file contains the server information: ``1      network      <your license server>:3400``.
* **AIMMS Application (Model):** Copied or mounted to the image's designated working folder, typically ``/model``. This folder is the standard location for the AIMMS project inside the Docker image and is set as the working directory using the Docker option ``-w /model``.
* **Data (Input/Output & Logs):** Typically handled by mounted volumes as data varies per run.
    * **Input Data** (e.g., CSV, Excel) is mapped to the ``/inputs`` volume.
    * **Output Data** and **Logs** are mapped to the ``/outputs`` volume.
    * **Logging Note:** If you include a custom ``LoggerConfig.xml``, ensure the file path redirects logs to the mounted volume, e.g., changing the file path value from ``log/aimms-log.txt`` to ``/outputs/aimms-log.txt``.

Building the Image
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The build script uses the ``aimms-eo`` base image and adds the license/model content.

.. code-block:: bat
    :linenos:

    echo on

    rem Builds a docker image with:
    rem 1. license info in sub-folder "data" of aimms-eo checkout
    rem 2. AIMMS application in sub-folder "model" of aimms-eo checkout
    rem 3. AIMMS 25.3.1 installation

    set VS_ROOT=C:\u\s\examples\application-examples\vessel-scheduling

    pushd %VS_ROOT%\aimms-eo

    docker build -f Dockerfile.WithLicenseAndModel -t vesselscheduling:1.0.2.1 --build-arg AIMMS_VERSION_MAJOR=25.3 --build-arg AIMMS_VERSION_MINOR=1.0 .

    popd

    pause

Running AIMMS Inside the Image
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Assuming the license and model are baked into the image, and data is managed via mounted volumes, here are the three execution scenarios:

**Example 1: Running a Single Procedure Directly**

This is analogous to Example 'a' for local execution, using mounted volumes for data I/O.

.. code-block:: bat

    echo on

    rem Script to execute a single procedure in an AIMMS app stored in a docker image.

    set VS_ROOT=C:\u\s\examples\application-examples\vessel-scheduling
    set VS_INPUTS=%VS_ROOT%\inputs
    set VS_OUTPUTS=%VS_ROOT%\outputs

    docker run --rm -i -v "%VS_INPUTS%:/inputs" -v "%VS_OUTPUTS%:/outputs" -w /model vesselscheduling:1.0.2.1 AimmsCmd --run-only pr_solveModelSessionArguments "VesselScheduling.aimms" /inputs/VS_70Vessel_100Cargo.xlsx /outputs/VS_70Vessel_100Cargo_results.xlsx

    pause

**Example 2: Executing an ``AimmsCmd`` Script**

To correctly apply I/O redirection *inside* the container (not on the host), the ``AimmsCmd`` call must be wrapped in a shell command (e.g., using ``/bin/bash -c``).

.. code-block:: bat

    echo on

    rem Run vessel-scheduling by redirecting the AimmsCmd script as input to AimmsCmd in a docker container.

    set VS_ROOT=C:\u\s\examples\application-examples\vessel-scheduling
    set VS_INPUTS=%VS_ROOT%\inputs
    set VS_OUTPUTS=%VS_ROOT%\outputs

    docker run -i -v "%VS_INPUTS%:/inputs" -v "%VS_OUTPUTS%:/outputs" -w /model vesselscheduling:1.0.2.1 /bin/bash -c "/usr/local/Aimms/Bin/AimmsCmd VesselScheduling.aimms </inputs/single-run.properties >/outputs/single-run.log 2>/outputs/single-run.err"

    pause

Alternatively, you can place the wrapped command in a script file, like ``aimmscmdrun.sh``, and run that file:

.. code-block:: bash

    #!/bin/sh
    /usr/local/Aimms/Bin/AimmsCmd VesselScheduling.aimms </inputs/single-run.properties >/outputs/single-run.log 2>/outputs/single-run.err

Then run it from the host:
``docker run -i -v "%VS_INPUTS%:/inputs" -v "%VS_OUTPUTS%:/outputs" -w /model vesselscheduling:1.0.2.1 /bin/bash -c /model/aimmscmdrun.sh``

**Example 3: Running as a REST Service**

This starts the application as a long-running service inside the container, mapping the internal service port (default 12003) to a host port using the ``-p`` flag.

.. code-block:: bat

    echo on

    rem Start the VesselScheduling app with a REST API Service, handling tasks to solve the VesselScheduling problem.

    set VS_ROOT=C:\u\s\examples\application-examples\vessel-scheduling
    set VS_INPUTS=%VS_ROOT%\inputs
    set VS_OUTPUTS=%VS_ROOT%\outputs

    docker run --rm -i -v "%VS_INPUTS%:/inputs" -v "%VS_OUTPUTS%:/outputs" -p 12003:12003 -w /model vesselscheduling:1.0.2.1 AimmsCmd --run-only dex::api::RESTServiceHandler "VesselScheduling.aimms"

    pause

Remarks:
    
* The option ``-p 12003:12003`` is essential as it `publishes the container port <https://docs.docker.com/reference/cli/docker/container/run/#publish>`_ so external clients can access the REST service.


Headless Execution in the AIMMS Cloud
-----------------------------------------

When an AIMMS application is published to the AIMMS Cloud, headless execution and service management are fully automated.

You do not need to explicitly start a service (as with Docker or locally). When a task is posted to the application endpoint, the AIMMS Cloud automatically provisions resources, starts the project, handles the task, and manages the execution lifecycle.

More information on this streamlined approach can be found in the `AIMMS Cloud Tasks documentation <https://documentation.aimms.com/cloud/tasks.html>`_.
