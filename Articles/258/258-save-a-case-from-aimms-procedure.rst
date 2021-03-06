Save a Case from an AIMMS Procedure
====================================

.. meta::
   :description: How to programmatically save a case file from a procedure.
   :keywords: case, procedure, CaseFind, CaseCreate, CaseSetCurrent

It can be convenient to save a case from within a procedure. 

For example, let's say you want to run multiple scenarios and 
store the result of each one to a separate case. One
possible solution is to manually save a case
after running each scenario. However, for several long-running scenarios, this is very tedious. 

Instead, you can save cases automatically with a procedure. This way you can run
a procedure that solves all your scenarios and saves cases after each solve, and let it run unattended.

Writing the procedure
-----------------------------------

You can save a case in AIMMS using predefined case related functions. 

To make it easier to save a case with any given name,
you can introduce a new procedure, say ``SaveCase``, with a string parameter
``CaseName`` as an input argument. 

This procedure requires a local element parameter named ``CaseReference`` with the
range :any:`AllCases`. 

The body argument of the procedure should
contain the following code:


.. code-block:: aimms
    :linenos:

    Procedure SaveCase {
        Arguments: (sp_CaseName);
        Body: {
            OptionGetString("Data Management Style", sp_dms);
            if sp_dms = "Disk Files and Folders" then
            
                ! Save the case in the folder "data".
                if not DirectoryExists( "data" ) then
                    DirectoryCreate("data");
                endif ;
                CaseFileSave( "data\\" + sp_CaseName, AllIdentifiers );
            
            else
                ! First try to find a case with the name indicated by CaseName. 
                ! If AIMMS can find this, it will store a reference to this case 
                ! in the element parameter ep_CaseReference
                if ( not CaseFind( sp_CaseName, ep_CaseReference ) ) then
            
                    ! If no case with the name indicated by CaseName could be found, then
                    ! we try to create a case with this name. After creating the case, AIMMS
                    ! will store a reference in the ep_CaseReference element parameter to the
                    ! newly created case
                    if ( not CaseCreate( sp_CaseName, ep_CaseReference ) ) then
            
                        ! If there was an error while creating the case, notify the developer
                        ! by raising an error. If the raised error is not caught, AIMMS will
                        ! display it in the error window.
                        raise error "Could not create case with name " + sp_CaseName ;
            
                    endif;
            
                endif;
            
                ! If we got here, it means either a case with the indicated case name could be
                ! found, or it was created. 
                ! Now instruct AIMMS to set this case to be the current case
                CaseSetCurrent( ep_CaseReference );
            
                ! And then instruct AIMMS to save the case
                CaseSave( 0 );
            
            endif;
        }
        StringParameter sp_CaseName {
            Property: Input;
        }
        ElementParameter ep_CaseReference {
            Range: AllCases;
        }
        StringParameter sp_dms;
    }


Calling the procedure
----------------------
To save a case with the name "Case 1" from within any of your procedures, you can call your procedure as follows:

.. code-block:: aimms
    :linenos:

    SaveCase("Case 1") ; 


:download:`AIMMS project download <model/ms.zip>` 

More about case related functions
---------------------------------------------
To access contextual help from within AIMMS, 

1. Right-click any predefined functions in the AIMMS editor.
2. Select *Help* in the context menu.
3. Select the function name. 

The AIMMS Function Reference will open at the page corresponding to the function.

Related Topics
---------------

In the `AIMMS Function Reference <https://documentation.aimms.com/functionreference/data-management/case-management/index.html>`_, you can find more details about the case related functions used. 






