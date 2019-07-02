Save a case from an AIMMS procedure====================================
.. meta::
   :description: How to programmatically manipulate a case file from a procedure.
   :keywords: case file, procedure, CaseFind, CaseCreate, CaseSetCurrent

.. note::

    This article was originally posted to the AIMMS Tech Blog.
Why would one save a case from an AIMMS procedure ?  
+++++++++++++++++++++++++++++++++++++++++++++++++++++

There are situations where you want to save a case from within a procedure. 
A typical example of this is when you need to run a lot of scenarios and 
store the result of each scenario to a separate case. Onepossible solution is to separately start each scenario and save manually a case
when it is finished.

However, when you have a lot of scenarios or when each scenario takes a
very long time to solve, you don't want to sit behind the computer all
the time and do the start/save of each scenario manually. Instead, you
want to do this automatically with a procedure. This way you can start
a procedure that solves all your scenarios and let it run unattended
over night.How to save a case from an AIMMS procedure ?
+++++++++++++++++++++++++++++++++++++++++++++++++

You can save a case in AIMMS by making use of some predefined case
related functions. To make it easier to save a case with any given name,
you can introduce a new procedure, say `SaveCase`, with one string parameter
`CaseName` as an input argument. Furthermore, this procedure must also
have a local element parameter with the name `CaseReference` with the
range `AllCases`. Finally, the body argument of the procedure should
contain the following code:

.. code::
    Procedure SaveCase {
        Arguments: (CaseName);
        Body: {

           !First try to find a case with the name indicated by CaseName. If AIMMS
           !can find this, it will store a reference to this case in the element
           !parameter CaseReference
           if ( not CaseFind( CaseName, CaseReference ) ) then

               !If no case with the name indicated by CaseName could be found, then
               !we try to create a case with this name. After creating the case, AIMMS
               !will store a reference in the CaseReference element parameter to the
               !newly created case
               if ( not CaseCreate( CaseName, CaseReference ) ) then

                   !If there was an error while creating the case, notify the developer
                   !by raising an error. If the raised error is not caught, AIMMS will
                   !display it in the error window.
                   raise error "Could not create case with name " + CaseName ;
               endif;
           endif;

           !If we got here, it means either a case with the indicated case name could be
           !found, or it was created. Now instruct AIMMS to set this case to be the
           !current case
           CaseSetCurrent( CaseReference );
           !And then instruct AIMMS to save the case
           CaseSave( 0 );
        }
        
        StringParameter CaseName {
            Property: Input;
            InitialData: "";
        }

        ElementParameter CaseReference {
            Range: AllCases;
        }
    }
When you now want to save a case with the name "name for case" from
within any of your procedures, you may just call the above procedure as
follows:

.. code::

   SaveCase("name for case") ; 

Going further with case related functions
++++++++++++++++++++++++++++++++++++++++++

In the `AIMMS Function Reference <http://download.aimms.com/aimms/download/manuals/AIMMS_func.pdf>`_, you can find more details about the
case related functions used. If you right-click any of these predefined
functions in the AIMMS editor, you can select Help on in the context
menu and then select the function name. This will open the AIMMS
Function Reference at the page corresponding to the function.

