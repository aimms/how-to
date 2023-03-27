Nonlinear Presolve Algorithm in AIMMS
========================================

This paper describes the AIMMS presolve algorithm for nonlinear problems. This presolve algorithm uses standard techniques like removing singleton rows, deleting fixed variables and redundant constraints, and tightening variable bounds by using linear constraints. Our algorithm also uses the expression tree of nonlinear constraints to tighten variable bounds.

View the PDF file: 
:download:`AIMMS-Whitepaper-Presolver.pdf`

Introduction
------------

The AIMMS modeling language [3] is linked to many first class solvers for solving optimization problems. All
linear solvers in AIMMS (CPLEX, XA, XPRESS, MOSEK) use a presolve algorithm whereby the problem input
is examined for logical reduction opportunities. The goal is to reduce the size of the problem. A reduction in
problem size in general leads to a reduction in total run time (including the time spent in the presolve
algorithm itself).

Of all nonlinear solvers in AIMMS (CONOPT, KNITRO, MINOS, SNOPT, BARON, LGO, AOA) only CONOPT
and BARON use preprocessing techniques. When CONOPT [5] solves a model, it tries to detect recursive
or triangular equations that can be solved before the optimization is started. The equations identified in
this way can be solved independent of the optimization, and they can therefore be removed from the
optimization process. In BARON [14] range reduction is part of the branch and reduce algorithm and used
at every node in the search tree.

Preprocessing for linear problems has been studied extensively; see e.g., [1,4,7,12]. Preprocessing for
quadratic problems is discussed in, e.g., [8,9]. In global optimization, preprocessing mainly focuses on bound
tightening techniques; see [2] and its references. Some of these techniques have been applied to the primal
presolve algorithm for nonlinear problems in the modeling language AMPL [6].

We have developed a nonlinear presolve algorithm in AIMMS with the goal to reduce the size of the problem
and to tighten the variable bounds, which may help the solver to solve the problem faster. Besides BARON,
all nonlinear solvers in AIMMS are local solvers, i.e. the solution found by the solver is a local solution and
cannot be guaranteed to be a global solution. The presolve algorithm may help the solver in finding
a better solution.

A local solver might sometimes fail to find a solution and then it is often not clear whether that is caused by
the problem being infeasible or by the solver failing to find a solution for a feasible problem. The presolve
algorithm may reveal inconsistent constraints and/or variable bounds and hence identify a problem as infeasible.

Presolve Techniques
---------------------

We consider the following constrained nonlinear optimization problem:

+-----+----------------------------------------------------+
| **Minimize:**                                            |
+-----+----------------------------------------------------+
|  1  | :math:`f(x)`                                       |
+-----+----------------------------------------------------+
| **Constraints:**                                         |
+-----+----------------------------------------------------+
|  2  | :math:`g(x) \leq d`                                |
+-----+----------------------------------------------------+
|  3  | :math:`Ax \leq b`                                  |
+-----+----------------------------------------------------+
|  4  | :math:`l \leq x \leq u`                            |
+-----+----------------------------------------------------+

where :math:`x \in \mathbb{R}_{n}`, :math:`f: \mathbb{R}_{n} \rightarrow \mathbb{R}`, :math:`g: \mathbb{R}_{n} \rightarrow \mathbb{R}_{p}`, 
and :math:`d \in \mathbb{R}_{p}`, :math:`b \in \mathbb{R}_{m}`, :math:`A \in \mathbb{R}_{nxm}`. The constraints (2)
represent the nonlinear constraints in the problem and the constraints (3) the linear constraints. The objective function in (1) might be
linear or nonlinear. In this paper we focus on problems that contain only continuous variables, although our presolve algorithm can also be
used for problems that have integer variables.

The nonlinear presolve algorithm will:

-  Remove singleton rows by moving the bounds to the variables;

-  Tighten bounds of (primal) variables using linear and nonlinear constraints;

-  Delete fixed variables;

-  Remove one variable of a doubleton; 

-  Delete redundant constraints.

Singleton Rows
~~~~~~~~~~~~~~~~~

A singleton row is a linear constraint that contains only one variable. An equality singleton row fixes the
variable to the right-hand-side value of the row, and unless this value conflicts with the current bounds of the
variable in which case the problem is infeasible, we can remove both the row and variable from the problem.
An inequality singleton row introduces a new bound on the variable which can be redundant, tighter than 
an existing bound in which case we update the bound, or infeasible. Our presolve algorithm will remove all singleton rows.

If a variable is fixed then sometimes another row becomes a singleton row, and if that row is an equality row
we can fix the remaining variable and remove it from the problem. By repeating this process we can solve
any triangular system of linear equations that is part of the problem.

Bounds Tightening Using Linear Constraints
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

In the following analysis we use a linear “less than or equal to” constraint. A similar analysis applies to
other constraint types. The technique described here is known in the global optimization literature as
feasibility-based bounds tightening.

Assume we have a linear constraint i that originally has the form:

+-----+----------------------------------------------------+
|  5  | :math:`\sum_{j}a_{ij}x_{j} \leq b_{i}`             |
+-----+----------------------------------------------------+

If we assume that all variables in this constraint have finite bounds then we can determine the following lower and upper limits on constraint :math:`i`:

+-----+-----------------------------------------------------------------------------------+
|  6  | :math:`\underline{b_i} = \sum_{j \in P_i}a_{ij}l_j + \sum_{j \in N_i}a_{ij}u_j`   |
+-----+-----------------------------------------------------------------------------------+

and

+-----+-----------------------------------------------------------------------------------+
|  7  | :math:`\overline{b_i} = \sum_{j \in P_i}a_{ij}u_j + \sum_{j \in N_i}a_{ij}l_j`    |
+-----+-----------------------------------------------------------------------------------+

where :math:`P_i = {j: a_{ij} > 0}` and :math:`N_i = {j: a_{ij} < 0}` define the sets of variables with a positive and a negative
coefficient in constraint :math:`i` respectively. By comparing the lower and upper limits of a constraint with the
right-hand-side value we obtain one of the following situations:

-  Constraint (5) cannot be satisfied and is infeasible.

-  Constraint (5) can only be satisfied if all variables in the constraint are fixed on their lower bound if they have a positive
   coefficient, or fixed on their upper bound if they have a negative coefficient. The constraint and all its variables can be removed from the problem.

-  Constraint (5) is redundant, i.e. will always be satisfied, and can be removed from the problem.

-  Constraint (5) cannot be eliminated but can often be used to improve the bounds of one or more variables as we will see below.

.. spelling:word-list::
    doubleton