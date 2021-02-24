Integrate GuardServerSession library with your application
===========================================================

.. todo:: remarks about input case identifier and output case identifiers from GSS.

This article describes how to integrate the delegation of your jobs with the `GuardServerSession` library.

The following needs to be considered:

#.  Adding library

#.  Change in delegation

#.  Change in input and output cases.

#.  Change in actions 

#.  Change in User Interface, see :doc:`instructions<../310/310-install-ui-gss>`

Adding library
--------------

A copy of the library can obtained :download:`GSS Library download <model/GuardServerSession.zip>`.
See :doc:`how to add a library to your project<../84/84-using-libraries>`

This library is dependent on the libraries:

#.  AimmsProLibrary

#.  AimmsWebUI


Change in delegation
--------------------

We will use the attached AIMMS project as an example for this section. 

* :download:`FlowShop.zip <model/FlowShop.zip>` 

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

Remarks on the highlighted lines:

* Line 3: The procedure `gss::LoadResultsCallBack` will call `pro::session::LoadResultsCallBack(sp_requestID)` and 
  subsequently copy the error and profiling information to the tracked sessions container.
* Line 8: Instead of directly calling the workhorse, we guard with the procedure `gss::pr_GuardAndProfileServerJob`.
  This procedure will handle all error during execution and at the end collect profiling information.

Change in input and output cases
---------------------------------

The set of identifiers saved in a case by the data session as input for the solver session is stored in `pro::ManagedSessionInputCaseIdentifierSet`.
The set of identifiers saved in a case by the solver session for the data session is stored in `pro::ManagedSessionOutputCaseIdentifierSet`.

When your application does not adapt these sets from their default contents before integrating with the `GuardServerSession` library; 
you do not need to do so after the integration either.

When your application does adapt these sets from their default contents, then please add

#.  `s_inputCaseIdentifiers` to `pro::ManagedSessionInputCaseIdentifierSet` in the data session.

#.  `s_outputCaseIdentifiers` to `pro::ManagedSessionOutputCaseIdentifierSet` in each solver session.

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

* Lines 3 and 10: `pr_enter` and `pr_leave` these are used to generate contents for the `.actionLog` File. 
  :doc:`This article<../497/497-tracing-procedures>` explains the workings of these procedures.
  
* Lines 4, 6, and 9 delineate the business logic (line 5) from the error handling logic (lines 7,8).

* Line 7: The procedure `gss::pr_appendError` stores the information of each error in the error container of the active session.

* Line 8: Mark the error as handled; the action procedure is usually the bottom of an execution stack - 
          so it is the bottom of the error handling stack as well.

Some optional recommended application changes
---------------------------------------------------

#.  That your ``MainInitialization`` procedure, starts with  ``ProfilerStart();``
    This will ensure that profiling information can be gathered and shared.

#.  The option ``communicate_warnings_to_end_users`` is turned ``on``.
    One of the purposes of the GuardServerSession is to share error information; 
    which includes all warnings.

    As an aside, the default of the option ``communicate_warnings_to_end_users`` makes 
    sense if extensive error handling measures are not taken in the application.
    Best practice is still to add extensive checking and careful error catching to your application.


