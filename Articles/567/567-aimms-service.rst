Publishing an AIMMS Service
=============================

This article illustrates how to create a service from an AIMMS procedure. 
It requires the following steps:

#.  Create procedure that implements a task 

#.  Publish the application

Please use the following project to follow this article:

    :download:`AIMMS 4.90 project download <model/MachineSchedulingMIPTimeIndexedSolver.zip>` 

Procedure Implementing Task
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

#.  Line 8: The request data is read from a file.
    The string ``dex::api::RequestAttribute( 'request-data-path' )`` is the path to that file. 

#.  Line 17: Similarly, the response is written to a file.
    The string ``dex::api::RequestAttribute( 'response-data-path' )`` is the path to that file. 

Deploying an Application on AIMMS Cloud
----------------------------------------

For an AIMMS Cloud with Tasks enabled, it suffices to publish the ``.aimmspack`` on that cloud.

.. seealso::

    * :doc:`../567/567-python-client` 

    * `More on the new REST service for 'Tasks' <https://community.aimms.com/product-updates/more-on-the-new-rest-service-for-tasks-1354>`_

    * `REST Service for running solve jobs and other asynchronous jobs <https://community.aimms.com/product-updates/rest-service-for-running-solve-jobs-and-other-asynchronous-jobs-1345>`_



