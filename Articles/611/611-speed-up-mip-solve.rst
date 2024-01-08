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

Note that this is only possible when you use CPLEX, GUROBI or CBC. 

Go to :menuselection:`Settings > Project Options` set the following option: 

* CPLEX X.X: :menuselection:`Specific solvers > CPLEX X.X > General > Advanced start > Use advanced basis`

* GUROBI X.X: :menuselection:`Specific solvers > GUROBI X.X > MIP > MIP Start > Yes`

* CBC X.X: :menuselection:`Specific solvers > CBC X.X > MIP > MIP Start > On`