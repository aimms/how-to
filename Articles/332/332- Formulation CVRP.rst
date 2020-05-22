Capacitated Vehicle Routing Problem formulation
===============================================
There is a library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**). It contains four different options of formulating the problem. However, the only difference is the way that Subtour Elimination Constraints (SEC) are formulated. The objective function and most of the constraints are the same for all four options and will be explained in this article.

Linear integer programming model
--------------------------------
A CVRP can be formulated as a linear integer programming model. The total distance of the route, where all costumers demands are met, should be minimized. The binary variable :math:`x_{ijk}` has a value of :math:`1` if the arc from node i to node j is in the optimal route and is driven by vehicle k. The variable :math:`d_{ij}` discribes the distance from node i to node j. There are n nodes (depot = 1) and p vehicles. The objective function can be formulated as follows:

.. math:: Min \sum_{k = 1}^{p}{\sum_{i = 1}^{n}{\sum_{j = 1, i \neq j}^{n}{d_{ij}x_{ijk}}}}

Every node should be entered and left once (expect for the depot) and by the same vehicle. The depot should be left and entered once by each vehicle. :math:`q_{i}` describes the demand of each costumer and :math:`Q` is the capacity of the vehicles. The sum of the demands of all costumers that vehicle k will serve, shouldn't exceed the capacity of vehicle k. All these constraints can be formulated as follows:

.. math:: \sum_{k = 1}^{p}{\sum_{i = 1, i \neq j}^{n}{x_{ijk}}} = 1  \qquad \forall j \in \{2,...,n\}
.. math:: \sum_{j = 2}^{n}{x_{1jk}} = 1 \qquad \forall k \in \{1,...,p\}
.. math:: \sum_{i = 1, i \neq j}^{n}{x_{ijk}} = \sum_{i = 1}^{n}{x_{jik}} \qquad \forall j \in \{1,...,n\}, \enspace k \in \{1,...,p\}
.. math:: \sum_{i = 1}^{n}{\sum_{j = 2}^{n}{q_{j} x_{ijk}}} \leq Q \qquad \forall k \in \{1,...,p\}
.. math:: x_{ijk} \in \{0,1\} \qquad \forall k \in \{1,...,p\},\enspace i,j \in \{1,...,n\}, \enspace i \neq j

* The constraints (1) ensure that every node is entered once. The constraints (3) denote that every node is entered and left by the same vehicle, the same amount of times. So if every node is entered once, it is also left once.
* The constraints (2) denote that the depot is left once by vehicle k. In combination with constraints (3) it is made sure that the depot is also entered once by vehicle k.

All these constraints are formulated in the ``Common Constraints and Variables`` section in the CVRP Library.

Subtour Elimination Constraints 
-------------------------------
However, a route that satisfies all these constraints could still be infeasible. Namely when the route contains a subtour (see image). 

.. image:: images/subtour.png
   :scale: 35%
   :align: center

The four different formulations in the library are all different ways of eliminating these subtours. 
