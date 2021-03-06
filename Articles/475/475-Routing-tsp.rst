Routing: Traveling Salesman Problem 
======================================

.. meta::
   :keywords: Lazy constraint callback, subtour elimination constraints, GMP, network object
   :description: In this example the (symmetric) Traveling Salesman Problem (TSP) is formulated using subtour elimination constraints.

Direct download AIMMS Project :download:`TSP.zip <model/TSP.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Routing/TSP

Problem type:
MIP (medium - hard)

Keywords:
Lazy constraint callback, subtour elimination constraints, GMP, network object

Description:
In this example the (symmetric) Traveling Salesman Problem (TSP) is formulated
using subtour elimination constraints. The amount of subtour elimination constraints
is exponential, and therefore they are added using a lazy constraint callback. Lazy
constraints are constraints that should be satisfied by any solution to the problem,
but they are not generated upfront. The lazy constraint callback checks whether the
incumbent solution found by the solver contains subtours. If yes, then subtour
elimination constraints are added that forbid these subtours. If not, then the
incumbent solution forms a true solution of the TSP problem, as it contains only one
tour.

This example contains several euclidean TSP instances from TSPLIB at:
http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/

.. Note:

   The lazy constraint callback is only supported by CPLEX and Gurobi.

References:
Applegate, D.L., R. E. Bixby, V. Chvátal, and W. J. Cook, The Traveling Salesman
Problem: A Computational Study, Princeton University Press, Princeton, 2007


