Speed Up MIP Solve
===================

When you have a weak LP relaxation, or a hard LP problem that needs to be solved for your MIP problem, solving the problem can take a long time.

There are several reasons why the MIP algorithm can take a long time. Some of them are:

* The MIP solver cannot handle this math program very well.
* The LP relaxation is very weak and therefore there are only a few cuts in the branch-and-bound tree.
* Solving the LP problems is relatively hard.
* If the branch-and-bound tree becomes very large, the solver needs to swap memory, which decreases the performance drastically.

Solver
-------
You could try to run your model with a different MIP solver. For many MIP models CPLEX and GUROBI perform better than CBC and XA. 

Reformulation
-------------
If you have a weak LP-relaxation, you should look again at your model formulation. Maybe you can decrease the use of Big M coefficients in your model, or maybe you can add some cuts.

If you have a very large LP that takes a relatively long time to solve, you could try to solve the LPs on the nodes using the Barrier algorithm. 

Go to :menuselection:`Settings > Project Options` set the following option: :menuselection:`Specific solvers > CPLEX X.X > MIP > MIP Method: Barrier`.

Priorities
--------------
If your model includes natural priorities (because some decisions follow from or follow up other decisions), you could decide to make use of these priorities. See also :any:`sec:var.var.solver-attr`.

Starting Solution
-----------------
When you are able to create a good starting solution (e.g. using a heuristic), you can provide the solver with this solution to improve the solution process. 

Note that this is only possible when you use :ref:`SolverCPLEX`, :ref:`SolverGurobi`, :ref:`SolverCOPT` or :ref:`SolverCBC`. 

Go to :menuselection:`Settings > Project Options` to set the following option: 

* CPLEX: :ref:`option-CPLEX-advanced_start` to 'Use advanced basis'

* Gurobi: :ref:`option-GUROBI-mip_start` to 'Yes'

* COPT: :ref:`option-COPT-mip_start` to 'Use full solutions' or 'Use partial solutions'

* CBC: :ref:`option-CBC-mip_start` to 'On'

By enabling solver logging you can check whether the solver is accepting the MIP start (except for CBC). To do so, set the Solvers General option
:ref:`option-AIMMS-solver_listing_messages` to 'All' and the following solver option:

* CPLEX: :ref:`option-CPLEX-mip_display` to 'Display each nth node'

* Gurobi: :ref:`option-GUROBI-output_file` to 'Yes'

* COPT: :ref:`option-COPT-output_file` to 'Yes'

If CPLEX accepts the MIP starts then it will show in its status file:

.. code-block:: text

    1 of 1 MIP starts provided solutions.
    MIP start 'm1' defined initial solution with objective 21.0000.


If Gurobi accepts the MIP starts then it will show in its log file:

.. code-block:: text

    Loaded user MIP start with objective 21

If COPT accepts the MIP starts then it will show in its log file:

.. code-block:: text

    Loading 1 initial MIP solution
    Initial MIP solution # 1 with objective value 21 was accepted

**Note**: CPLEX and Gurobi can use multiple MIP starts, see:

* CPLEX: :ref:`CPLEX_Multiple_MIP_Starts`

* Gurobi: :ref:`GUROBI_Multiple_MIP_Starts`
