The library AimmsPLET
======================

The **AimmsPLET** library contains functions an AIMMS application with service for long running tasks
could use to record interesting events.
This library uses the prefix **plet**, and therefore often referenced to as **plet** instead of **AimmsPLET**.

.. image:: images/Historische_Steinwalzeim_Aboretum_Ellerhoop_large.jpg
    :align: center


Installation
^^^^^^^^^^^^

The following steps are recommended to add the AimmsPLET library to your AIMMS application with 
a service for long running tasks:

#.  Add the library AimmsPLET by cloning from: 

#.  Add libraries AimmsPLET depends on, currently only Stopwatch, see 

#.  Optionally add AimmsPLET and Stopwatch to the .gitignore of your AIMMS app.

Using
^^^^^^^^^^^^^^^^^^ 

Start and finish recording
"""""""""""""""""""""""""""

To start and finish recording events, you should use the functions ``plet::pr_initTask()`` and ``plet::pr_finishTask()``
which record the events of starting and finishing a task.

.. code-block:: aimms 
    :linenos:
    :emphasize-lines: 3,13,14,18,22

    Procedure pr_solveFlowshopNjobsMmachinesService {
        Body: {
            plet::pr_initTask();
            
            block
                _sp_inp := dex::api::RequestAttribute( 'request-data-path'  ) ;
                _sp_out := dex::api::RequestAttribute( 'response-data-path' ) ;
            
                pr_implementSolveNjobsMmachinesFlowshopService( _sp_inp, _sp_out );
            
            onerror _ep_err do
            
                plet::pr_errorHandler(_ep_err);
                errh::MarkAsHandled(_ep_err);
            
            endblock ;
            
            plet::pr_finishTask();
            
            return 1;
        }
        dex::ServiceName: solveFlowshopNjobsMmachinesService;
        StringParameter _sp_inp;
        StringParameter _sp_out;
        ElementParameter _ep_err {
            Range: errh::PendingErrors;
        }
    }

Remarks:

#.  Line 3: 
    Initialize recording of events for the task at hand.  
    It will also record administrative info for that task, such as its starting time, user, app, and service.

#.  Lines 5, 10, 13, 14, 16
    Error handling to ensure that any warnings or error encountered during the 
    execution of the task at hand are properly recorded; including a stack trace.

#.  Line 18: 
    Finish recording the task, it will also fill in the stop time for that task   
    signaling any followers of that task to stop.

#.  Line 22: 
    Mark this procedure as a service.

Reference
^^^^^^^^^^^^^^^^^^

The provided global identifiers are:

*   ``sp_pletODBCDatabaseConnection`` -- The ODBC connection string to the **plet** database.
    With this connection, the application can write events to that database.
    Initialized in ``plet::LibraryInitialization`` and closed in ``plet::LibraryTermination``

The provided functions are:

*   ``plet::pr_initTask``

	Record starting of task, and start recording events for that task.
	
*   ``plet::pr_finishTask``

	Record task finished.
	
*   ``plet::pr_log(sp_msg,p_lev)``

	* ``sp_msg`` (input) The message to be logged.
	
	* ``p_lev`` (optional, default 3 (info)). The level of importance of the message.

*   ``plet::pr_errorHandler(ep_err)``

	Record the incident, including stack trace, in the plet database.

	* ``ep_err`` (input in errh::PendingErrors) a reference to the incident.

*   ``plet::spl::pr_watchProgress(ep_gmp)``

	* ``ep_gmp`` (input in AllGeneratedMathematicalPrograms), the mathematical program to be followed.


.. spelling:word-list::

   gitignore
   AimmsPLET
   plet
   errh
   gmp
   PendingErrors
