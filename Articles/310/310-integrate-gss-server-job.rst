Integrate GSS server Job
=========================

.. todo:: remarks about input case identifier and output case identifiers from GSS.

We will use the attached AIMMS project as an example for this section. 

* :download:`FlowShop.zip <model/FlowShop.zip>` 

We assume here that your ``MainInitialization`` procedure, still starts with  ``ProfilerStart();``
In addition, we assume here that delegation is done as follows:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 8

    if pro::GetPROEndPoint() then
        if pro::DelegateToServer( waitForCompletion  :  1, 
                      completionCallback :  'pro::session::LoadResultsCallBack' ) then  
            return 1;
        endif ;
    endif ;

    pr_WorkSolve();

We change this to:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 8

    if pro::GetPROEndPoint() then
        if pro::DelegateToServer( waitForCompletion  :  1, 
                      completionCallback :  'pro::session::LoadResultsCallBack' ) then  
            return 1;
        endif ;
    endif ;

    gss::pr_GuardAndProfileServerJob( 'pr_WorkSolve' );

``gss::pr_GuardAndProfileServerJob();`` is defined as:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 8

    Procedure pr_GuardAndProfileServerJob {
        Arguments: (ep_Work);
        Body: {
            block
                empty s_JobErrorMessageNumbers ;
            
                ! Ensure that the error recorded and profiler data are transmitted via the output case:
                pro::ManagedSessionOutputCaseIdentifierSet += ServerSessionProfilerErrorData ;
            
                ! Actual work.
                apply( ep_Work);
            
                ! Ensure that the definitions of all defined sets and defined parameters in 
                ! output case identifier set are profiled.
                update pro::ManagedSessionOutputCaseIdentifierSet;
            
            onerror ep_err do
            
                ! Increase the number of recorded error messages.
                s_JobErrorMessageNumbers += card(s_JobErrorMessageNumbers) + 1;
                ep_JobErrorMessageNumber := last(s_JobErrorMessageNumbers);
            
                ! Record the message and severity.
                sp_JobErrorMessages(ep_JobErrorMessageNumber) := errh::Message( ep_err);
                sp_JobErrorMoments( ep_JobErrorMessageNumber) := CurrentToString("%c%y-%m-%d %H:%M:%S:%T%TZ('UTC')");
                ep_JobErrorSeverity(ep_JobErrorMessageNumber) := errh::Severity(ep_err);
            
                ! Record the entire stack of error positions
                p_errStackHeight := errh::NumberOfLocations(ep_err);
                p_errStackPos := 1 ;
                while p_errStackPos <= p_errStackHeight do
                    ep_StackPosition := p_errStackPos;
                    ep_JobErrorNodes(ep_JobErrorMessageNumber,ep_StackPosition) := errh::Node(ep_err,ep_StackPosition);
                    p_JobErrorLines( ep_JobErrorMessageNumber,ep_StackPosition) := errh::Line(ep_err,ep_StackPosition);
                    p_errStackPos += 1 ;
                endwhile ;
            
                ! Don't let the server job be halted by this error; 
                ! we still want the result case including the profiled data.
                errh::MarkAsHandled(ep_err);
            
            endblock ;
            ProfilerCollectAllData(
                ProfilerData       :  p_JobProfilerData, 
                GrossTimeThreshold :  0, 
                NetTimeThreshold   :  0);
        }
        ElementParameter ep_err {
            Range: errh::PendingErrors;
        }
        Parameter p_errStackHeight;
        Parameter p_errStackPos;
        ElementParameter ep_StackPosition {
            Range: s_StackPositions;
        }
        ElementParameter ep_JobErrorMessageNumber {
            Range: s_JobErrorMessageNumbers;
        }
        ElementParameter ep_Work {
            Range: AllProcedures;
            Default: 'gss::pr_SampleProc';
            Property: Input;
        }
    }

.. note::
     :any:`ProfilerCollectAllData` is available since AIMMS 4.68

In addition, we also set the option ``communicate_warnings_to_end_users`` to ``on``.

Run the optimization via the widget action of the Gantt Chart:

.. image:: images/StartOptimization.png
    :align: center

Switch to the profile and error page of the app:

.. image:: images/ErrorProfilerDataServerJob.png
    :align: center

The flow shop project contains a custom library ``GuardServerSession``, which you can download and add to your project. 
See :doc:`add libraries to your project<../84/84-using-libraries>`.

* :download:`GuardServerSession.zip <model/GuardServerSession.zip>` 
