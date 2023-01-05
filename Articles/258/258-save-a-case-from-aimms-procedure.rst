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
This is exemplified on `Employee Scheduling example <https://how-to.aimms.com/Articles/387/387-employee-scheduling.html>`_ . 

Writing the procedure
-----------------------------------

You can save a case in AIMMS using predefined case related functions. 

To make it easier to save a case with any given name,
you can introduce a new procedure, say ``SaveCase``, with a string parameter
``CaseName`` as an input argument. 

The body argument of the procedure should
contain the following code:


.. code-block:: aimms
    :linenos:

    Procedure SaveCase {
        Arguments: (sp_in_caseName);
        Body: {
            ! Save the case in the folder "data".
            if not DirectoryExists( "data" ) then
                DirectoryCreate("data");
            endif ;
            CaseFileSave("data\\" + sp_CaseName + ".data", AllIdentifiers);
        }
        StringParameter sp_in_caseName {
            Property: Input;
        }
    }


Calling the procedure
----------------------
To save a case with the name "Case 1" from within any of your procedures, you can call your procedure as follows:

.. code-block:: aimms
    :linenos:

    SaveCase("Case 1") ; 

Upgrading an AIMMS project to a newer AIMMS release
----------------------------------------------------

If you still work with ``.dat`` files, please convert to ``.data`` first. You may want to follow the instructions in
`our conversation guide <https://how-to.aimms.com/Articles/314/314-from-dat-to-data.html>`_.


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






