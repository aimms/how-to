Implicit Dantzig-Fulkerson-Johnson formulation
==============================================
The library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**) contains four different formulation options. They have different methods of eliminating subtours. In this article the Implicit Dantzig-Fulkerson-Johnson formulation is discussed. 

Lazy constraints
----------------
The constraints are the same as for the Explicit Dantzig-Fulkerson-Johnson formulation (link). Subtours are eliminated using subsets and the constraints are formulated as follows:

.. math:: \sum_{k = 1}^{p}{\sum_{i,j \in S}{x_{ijk}}} \leq |S|-1 \qquad S \subset \{2,...,n\}, \enspace 2 \leq |S| \leq n - 2

The difference however, is when the constraints are formulated. When explicitly formulating constraints, all the possible subsets have to be generated beforehand. With ... nodes, already ... subsets can be formed. 



A CVRP can be formulated as a linear integer programming model. The four different formulations in the CVRP Library all have the same objective function. Most of the constraints are also the same for all formulations. These constraints are located in the section ``Common constraints and variables``. 







to read more about lazy constraints: link to TSP lazy constraints