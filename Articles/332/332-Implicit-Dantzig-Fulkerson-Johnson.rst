Implicit Dantzig-Fulkerson-Johnson formulation
==============================================
The library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**) contains four different formulation options. They have different methods of eliminating subtours. In this article the Implicit Dantzig-Fulkerson-Johnson formulation is discussed. 

Lazy constraints
----------------
The constraints are the same as for the Explicit Dantzig-Fulkerson-Johnson formulation (link). Subtours are eliminated using subsets and the constraints are formulated as follows:

.. math:: \sum_{k = 1}^{p}{\sum_{i,j \in S}{x_{ijk}}} \leq |S|-1 \qquad S \subset \{2,...,n\}, \enspace 2 \leq |S| \leq n - 2

The difference however, is when the constraints are formulated. When explicitly formulating constraints, all the possible subsets have to be generated beforehand. With ... nodes, already ... subsets can be formed. 



A CVRP can be formulated as a linear integer programming model. The four different formulations in the CVRP Library all have the same objective function. Most of the constraints are also the same for all formulations. These constraints are located in the section ``Common constraints and variables``. 





-> A CVRP can be formulated as a linear integer programming model. 
-> For all the different formulations the same objective function is being used
-> also the ``common constraints and variables`` are the same for all formulations
-> The only thing that differs is the Subtour Elimination constraints
-> all formulations have different ways to eliminate subtours. 

Implicit Dantzig-Fulkerson-Johnson
----------------------------------

-> Another formulation in the CVRP Library is the Explicit Dantzig-Fulkerson-Johnson formulation
-> The Constraints are the same for the implicit Dantzig-Fulkerson-Johnson formulation
-> Constraints 
-> However, the difference is when constraints are formulated 
-> EDFJ must first create all possible subsets (a lot)
-> amount of subsets increases a lot with more nodes
-> it is likely that a lot of subtours wouldn't have ever occurred

-> implictly formulating constraints is also called lazy constraints
-> first generate a tour with all common constraints and variables
-> everytime a tour is generated, it should be checked for subsets. 
-> if the tour contains a subset -> add a constraint about that subset
-> if it doesn't contain a subset -> the solution is correct
-> go on until you have a solution without any subsets

AIMMS
-----














to read more about lazy constraints: link to TSP lazy constraints