Implicit Dantzig-Fulkerson-Johnson formulation
==============================================
The library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**) contains four different formulation options. They have different methods of eliminating subtours. In this article the Implicit Dantzig-Fulkerson-Johnson formulation is discussed. 

Dantzig-Fulkerson-Johnson formulation
-------------------------------------
The CVRP Library also has the option Explicit Dantzig-Fulkerson-Johnson (DFJ). For that option, the same formulation of the constraint is used. It uses subsets to eliminate subtours. The idea behind the formulation is that, for every subset that could form a subtour, at least two arcs should connect nodes from the subsets to nodes outside of the subset. This article (EDFJ) elaborates on this formulation.

V is the set of all nodes from 1 to n (depot is n = 1). S is a subset of V. The binary variable :math:`x_{ijk}` has a value of 1 if vehicle k drives from node i to node j. The constraint can be formulated as follows:

.. math:: \sum_{i \in S, j \notin S}{x_{ijk}}} \geq 2 \qquad S \subset V \setminus \{1\}, \enspace 2 \leq |S| \leq n - 2

Lazy constraints
----------------
The difference, however, is when these constraints are formulated. The Explicit DFJ formulation generates all subsets that could be subtours, in advance. This way, a constraint can be formulated about all these subsets to eliminate any possible subtour. So for example, we have the following set of nodes:

.. image:: images/IDFJ1.png
   :scale: 35%
   :align: center

All subsets with at least two elements, that do not contain the depot, should be generated. The number of subsets of a set with 10 elements = 10^2. The number of subsets thereof that contain 0 elements or all elements = 2. The number of subsets thereof that contain 1 element (or all but 1) = 20. So the number of generated subsets = 10^2 – 2 – 22 = 1000.

However, most of these subtours are unlikely to be formed when looking for an optimal solution. For example, subset S = {10, 8, 4} is not likely to form a subtour. So most of the subsets generated beforehand are unnecessary.

It is possible to search for an optimal solution without any subtour elimination constraints. The following route could then be formed:

.. image:: images/Subtour.png
   :scale: 35%
   :align: center

This route should then be checked for subtours. If a subtour is found, a constraint (lazy constraint) about that subset is formulated. Now, when searching for an optimal solution again, this subtour cannot be formed. This continues until an optimal solution without subtours is found. This way, far less subsets need to be generated which saves a lot of time. Especially with larger sets of nodes.  

In the CVRP Library, these lazy constraints are implemented in the section `Implicit Dantzig Fulkerson Johnson section`.



