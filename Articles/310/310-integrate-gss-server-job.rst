Integrate GuardServerSession library with your AIMMS Application
==================================================================

This article is a companion article to :doc:`Incident Handling for Organizations<../310/310-incident-handling-for-organizations>`


This article describes how to use the `GuardServerSession` library to delegate jobs from your AIMMS app. 
The following steps need to be considered:

    #.  Adding the library

    #.  Change in delegation

    #.  Change in input and output cases.

    #.  Change in actions 

Adding the library
--------------------

A copy of the library can obtained from here: :download:`GSS Library download <model/GuardServerSession.zip>`.
See also :doc:`AIMMS How-To: Add AIMMS Libraries<../84/84-using-libraries>`.

This library is dependent on the libraries:

#.  AimmsProLibrary

#.  AimmsWebUI


Change in delegation
--------------------

We will use the attached AIMMS project as an example for this section:  :download:`FlowShop.zip <model/FlowShop.zip>` 

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
    :emphasize-lines: 3,8

    if pro::GetPROEndPoint() then
        if pro::DelegateToServer( waitForCompletion  :  1, 
                      completionCallback :  'gss::LoadResultsCallBack' ) then  
            return 1;
        endif ;
    endif ;

    gss::pr_GuardAndProfileServerJob( 'pr_WorkSolve' );

* Line 3: The procedure ``gss::LoadResultsCallBack`` will call ``pro::session::LoadResultsCallBack(sp_requestID)`` and 
  subsequently copy the error and profiling information to the tracked sessions container.

* Line 8: Instead of directly calling the workhorse, we guard it with the procedure ``gss::pr_GuardAndProfileServerJob``.
  This procedure will handle any errors during execution and at the end, collect profiling information.

Change in input and output cases
---------------------------------

The set of identifiers saved in a case by the data session as input for the solver session is stored in ``pro::ManagedSessionInputCaseIdentifierSet``.
The set of identifiers saved in a case by the solver session for the data session is stored in ``pro::ManagedSessionOutputCaseIdentifierSet``.

If your application does not adapt these sets from their default contents (as described in :doc:`AIMMS How-To: Reduce Exchange Between Client and Solver Sessions<../reduce-client-server-exchange/reduce-client-server-exchange>`) before integrating with the ``GuardServerSession`` library,  
you do not need to do so after the integration either.

if your application does modify these sets from their default contents, then please add:

    #.  ``s_inputCaseIdentifiers`` to ``pro::ManagedSessionInputCaseIdentifierSet`` in the data session.

    #.  ``s_outputCaseIdentifiers`` to ``pro::ManagedSessionOutputCaseIdentifierSet``  in each solver session.

Change in actions
-------------------- 

The WebUI provides various ways to invoke AIMMS procedures, including status bar, buttons, upload button, download button, item menus, widget menus, and page open.
Each such invoked procedure should have the following pattern:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 3,4,6-10

    Procedure pr_actionTemplate {
        Body: {
            pr_enter(sp_gssTime, p_gssMiU, ep_logLev: 'info');
            block 
                ! Call procedure to do the actual work.
            onerror ep_err do
                gss::pr_appendError( ep_err );
                errh::MarkAsHandled( ep_err );
            endblock ;
            pr_leave(sp_gssTime, p_gssMiU, ep_logLev: 'info');
        }
        Comment: "Sample action procedure";
        DeclarationSection gss_logging_declarations {
            StringParameter sp_gssTime;
            Parameter p_gssMiU;
        }
        DeclarationSection error_reference_declaration {
            ElementParameter ep_err {
                Range: errh::PendingErrors;
            }
        }
    }

Remarks:

* Lines 3 and 10: ``pr_enter`` and ``pr_leave`` these are used to generate contents for the ``.actionLog`` File. 
  
  :doc:`AIMMS How-to: Tracing Procedures <../497/497-tracing-procedures>` explains the workings of these procedures.
  
* Lines 4, 6, and 9 delineate the business logic (line 5) from the error handling logic (lines 7,8).

* Line 7: The procedure ``gss::pr_appendError`` stores the information of each error in the error container of the active session.

* Line 8: Mark the error as handled; the action procedure is usually the bottom of an execution stack - so it is the bottom of the error handling stack as well.

Some optional recommended application changes
---------------------------------------------------

#.  Include the function :aimms:func:`ProfilerStart` at the top of your ``MainInitialization`` procedure.
    This will ensure that profiling information can be gathered and shared.

#.  Set the option ``communicate_warnings_to_end_users`` to ``on``.
    One of the purposes of the GuardServerSession is to share error information,
    which includes all warnings.

    As an aside, the default of the option ``communicate_warnings_to_end_users`` makes 
    sense if extensive error handling measures are not taken in the application.
    Best practice is still to add extensive checking and careful error catching to your application.

#.  The option ``maximal_number_of_warnings_reported`` is switched to a high setting, like 1000.

Further Reading
---------------------

This article is part of the series :doc:`Indicident Handling for Organizations<../310/310-incident-handling-for-organizations>`.

The next article in this series discusses how to build a user interface to use the ``GuardServerSession`` library: :doc:`UI for GSS<../310/310-install-gss-ui-for-specialists>`



