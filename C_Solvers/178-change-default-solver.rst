How to change the default solver
===========================================================================

.. meta::
   :description: How to change the default solver used for each type of mathematical program.
   :keywords: mathematics, solver, mathematical program

.. image:: ../Resources/C_Solvers/Images/178/solverselection.png

Introduction
--------------

Depending on which solvers are allowed by your AIMMS license, you might have multiple solvers capable of solving a given type of mathematical program (e.g. an LP problem). In this case, you can instruct AIMMS in a variety of ways which solver should be used to solve a problem of a specific type.

Solver configuration
----------------------

The trivial approach to change which solver should be used for each type of problem is to make use of the Solver Configuration screen. You can access this configuration screen via *Menu > Settings > Solver Configuration*

The picture above shows an example screenshot, where CPLEX 12.6.3 is used as the default solver for problems of the type LP. By double-clicking on any of the X's in the LP column, you can make the corresponding solver the default for LP problems. Any changes you make to the default solvers via this configuration screen are persistent between your AIMMS sessions: if you close your AIMMS application and start it again, the same default settings are used.

Default Solver option in AIMMS Procedures
---------------------------------------------

Alternatively, you can modify the default solvers programmatically in an AIMMS procedure. You can do so by modifying the contents of the predefined AIMMS element parameter ``CurrentSolver``. This element parameter is indexed over the ``IndexMathematicalProgrammingTypes`` and has the range ``AllSolvers``. This means that for each mathematical program type, you can select one solver.

Changes made to this element parameter are not persistent, meaning you will loose any changes you made to it after closing AIMMS if you do not store it yourself. 

.. warning::
    
    Note that the element parameter ``CurrentSolver`` is not stored when saving a case. This might have an impact on your communication with the AIMMS PRO server.

To change the default solver for LP problems to GUROBI 8.0 for all subsequent solve statements, you can use the following code in a procedure::

    if StringToElement(AllSolvers, "GUROBI 8.0") then
        CurrentSolver('LP') := StringToElement(AllSolvers, "GUROBI 8.0") ; 
    else
        raise error "Solver GUROBI 8.0 is not present" ;
    endif ; 

.. tip:: 

    Note that I am using the function ``StringToElement`` to avoid using single quoted elements (e.g. 'GUROBI 8.0'). This way, opening the AIMMS project will not result in a compilation error in case GUROBI 8.0 is not present in the set ``AllSolvers``.

Please keep in mind that the values of ``CurrentSolver`` are used as defaults: You can also still override these defaults for each separate solve statement in the following way

.. code-block:: aimms 

    !Solve TransportModel Math Program with the solver CPLEX 12.2 regardless
    !of what the default solver for this problem type is.
    solve TransportModel where solver:='CPLEX 12.2' ;


.. include:: ../includes/form.def

