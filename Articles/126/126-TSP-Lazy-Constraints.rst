Solve with Lazy Constraints
==============================

.. meta::
   :description: An example solving a Traveling Salesman Problem using lazy constraints.
   :keywords: lazy, constraints, salesman, tsp


The famous **Travelling Salesman Problem** (TSP) deals with the following problem: given a list of cities and the distances between each pair of cities, a salesman has to find the shortest possible route to visit each city exactly once while returning to the origin city. One way to formulate the TSP is as follows:

.. math:: min sum( (i,j), c(i,j)*x(i,j) )
.. math:: s.t. sum( i, x(i,j) + x(j,i) ) = 1 \forall j (1)
.. math:: x(i,j) binary \forall i > j


.. math:: \left\{ \begin{array}{ll} sum( i, x(i,j) + x(j,i) ) = 1 & \forall j \\ x(i,j) & \mathrm{binary\} \forall i > j \end{array} \right

Here :math:`x(i,j)` equals 1 if the route from city :math:`i` to city :math:`j` is in the tour, 
and 0 otherwise. Note that this is the formulation for the symmetric TSP in which the distance from :math:`i` to :math:`j` equals the 
distance from :math:`j` to :math:`i`. This formulation is not complete as it allows for subtours. 
One way to exclude these subtours is by using **subtour elimination constraints** (SECs for short):

.. math:: sum( (i,j) | i in S and not j in S, x(i,j) + x(j,i) ) >= 2   \foralll S, 1 < |S| < n


Here :math:`S` is a subset of cities while :math:`n` denotes the number of cities. 
This SEC enforces that at least one route is going from a city in set :math:`S` to a city outside :math:`S`.

.. figure:: images/ch130.png
    :align: center

    First solution with subtours for instance ch130

The problem with the SECs is that the number of these constraints is exponential in the number of cities. 
For a TSP with 50 cities the number of SECs exceeds :math:`10^{15}`. Generating all SECs is therefore impossible.

Fortunately, we can use SECs as **lazy constraints**. Unlike normal constraints, lazy constraints are not generated upfront. 
Instead, they are only generated when needed. Typically lazy constraints are unlikely to be violated. 
In a TSP with 50 cities usually at most 100 SECs will be "active" which is only a fraction of the total number of SECs. 
Lazy constraints are supported by CPLEX and Gurobi.

The new AIMMS example `TSP <https://raw.githubusercontent.com/aimms/examples/master/Practical%20Examples/Routing/TSP/MainProject/TSP.ams>`_, 
which can be found in :doc:`examples <../../C_Examples/index>`, uses the above formulation. The SECs are added as lazy constraints inside a callback. 
This callback is called whenever the solver finds a new candidate incumbent solution, i.e., a solution that satisfies formulation (1) 
plus the SECs that have been added before as lazy constraints. The callback procedure checks whether the candidate solution forms one tour. 
If it does, the solver found a true solution for the TSP and the solution is accepted; otherwise a SEC is added for each subtour. 
Finding a violated SEC, the so-called separation problem, can be solved in polynomial time despite the exponential number of SECs. 
The separation algorithm implemented in this example (inside the procedure ``DetermineSubtours``) has a worst-case running time of :math:`O(n^2)`.

The example comes with several symmetric instances from `TSPLIB <http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/>`_. 
More information on the TSP can be found on `this <http://www.math.uwaterloo.ca/tsp/>`_ very nice page maintained by William Cook.

**Remark**: SECs are often used as cutting planes in a branch-and-cut algorithm. 
In that case the separation problem is more difficult as it is applied to a fractional :math:`x`. 
Currently its fastest separation algorithm has a worst-case running time of :math:`O(nm + n^2 \log n)` where :math:`m` denotes the number of nonzero :math:`x` 
in the fractional solution. In our case, using SECs as lazy constraints, the separation problem is applied to a binary :math:`x`. 
Although the separation problem becomes easier if SECs are used as lazy constraints, it does not mean that TSPs are solved more efficiently that way.




