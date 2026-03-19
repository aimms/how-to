Providing an Initial Solution to MIP Solvers
====================================================
.. meta::
   :description: Explains how to configure CPLEX, Gurobi, and CBC to accept a warm-start MIP solution by setting solver options and pre-assigning variable values before solving.
   :keywords: MIP start, warm start, CPLEX, Gurobi, CBC, solver options, initial solution, advanced basis, Project Options

When solving MIP models, it is possible to provide an initial solution as a starting point for supported solvers. 
CPLEX, Gurobi and CBC are the solvers available in AIMMS that support this. 

You can do this by changing the following options for the corresponding solvers (X.X represents the version number).

Go to :menuselection:`Settings > Project Options`:

* CPLEX X.X: :menuselection:`Specific solvers > CPLEX X.X > General > Advanced start > Use advanced basis`

* GUROBI X.X: :menuselection:`Specific solvers > GUROBI X.X > MIP > MIP Start > Yes`

* CBC X.X: :menuselection:`Specific solvers > CBC X.X > MIP > MIP Start > On`

Now, assign the values of your solution to the variables before calling the ``solve`` statement.