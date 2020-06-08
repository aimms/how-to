Initialize MIP Solution
=========================
.. meta::
  :description: What to do when your MIP solver does not use initial solution.

You can initialize the solution of a MIP when solving it with CPLEX, GUROBI or CBC.

In some cases, you have an initial solution, but the solver does not seem to use it.

There are a few things you can check when this happens.

Check solver support
---------------------
Make sure that you use a solver that supports the use of initial solutions (CPLEX, GUROBI or CBC). 

Check project settings
-------------------------

Go to *Settings > Project Options* and set the following option:

* CPLEX X.X: *Specific solvers – CPLEX X.X - General - Advanced start -> Use advanced basis*

* GUROBI X.X: *Specific solvers – GUROBI X.X - MIP - MIP Start -> Yes*

* CBC X.X: *Specific solvers – CBC X.X - MIP - MIP Start -> On*

Assign initial solution to variables
------------------------------------------

Assign the values of your solution to the decision variables in your project and solve the model.