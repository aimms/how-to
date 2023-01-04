Publishing an AIMMS service
=============================

:download:`AIMMS 4.90 project download <model/MachineSchedulingMIPTimeIndexedSolver.zip>` 

This how-to is a Hello World type example illustrating how to create a service from an AIMMS procedure. 
It requires the following steps:

#.  Create procedure that implements a task 

#.  Publish the application


Procedure implementing task
---------------------------

.. code-block:: aimms 
    :linenos:
    :emphasize-lines: 8,17,30

    Procedure pr_runTask {
        Body: {
            block 
                ! Initialize
                pr_initializeTaskData();

                ! Read the request body.
                pr_readData( dex::api::RequestAttribute( 'request-data-path' ) );

                ! Actual solving of Operations Research problem at hand.
                pr_actuallySolveEqualParallelMachineAsMIP();

                ! Transform solution
                pr_postProcessSolution();

                ! write response body
                pr_writeSolution( dex::api::RequestAttribute( 'response-data-path' ) );

            onerror ep_err do

                sp_taskStatus := errh::Message( ep_err );
                errh::MarkAsHandled( ep_err );

            endblock ;

            ! the application-specific returncode that will be returned 
            ! via the task status of the job
            return 1;
        }
        dex::ServiceName: equalParallelMachineTimeIndexedMIP;
        ElementParameter ep_err {
            Range: errh::PendingErrors;
        }
    }


Some remarks about the highlighted lines:

#.  Line 30: By specifying this line, the service ``equalParallelMachineTimeIndexedMIP`` is linked to the procedure ``pr_runTask``.

#.  Line 8: The data is read from a file, using DEX functions.
    The ``dex::api::RequestAttribute( 'request-data-path' )`` is the file path to the request body. 

#.  Line 17: Similarly, the data is written to a file.
    The ``dex::api::RequestAttribute( 'request-data-path' )`` is the file path to the request body. 

Deploying an application on AIMMS Cloud
----------------------------------------

For AIMMS Clouds with Tasks enabled, it suffices to publish the .aimmspack on that cloud.

Further reading
-----------------

* :doc:`../567/567-python-client` 

* `More on the new REST service for 'Tasks' <https://community.aimms.com/product-updates/more-on-the-new-rest-service-for-tasks-1354>`_

* `REST Service for running solve jobs and other asynchronous jobs <https://community.aimms.com/product-updates/rest-service-for-running-solve-jobs-and-other-asynchronous-jobs-1345>`_



