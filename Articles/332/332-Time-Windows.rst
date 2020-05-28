Time Windows
============
The library in AIMMS that solves a Capacitated Vehicle Routing Problem (CVRP) has four different formulation options. One of them is called Time Windows and is an extension of the CVRP. Every costumer then has a different time frame in which the goods have to be deliverd. The constraints for this formulation automatically eliminate subtours. That is why is isn't nessecary to formulate any Subtour Elimination Constraints for a Vehicle Routing Problem with Time Windows (VRPTW). This article discusses the constraints for a VRPTW.

Picture 

Constraints
-----------

[a_i, b_i] is the time window of costumer i. A vehicle must arrive at costumer i at least at a_i and at most at b_i. The time it takes to deliver the goods is irrelevant. t_{ij} denotes the time is takes to get from costumer i to costumer j. Any service time at costumer i is included. The variable s_i denotes the time that a vehicle starts serving costumer i (which must be between a_i and b_i). 
These constraints can be formulated as follows:

si+tij-M* 1- xijk≤sj   (∀i∈V,j∈V\{1},k∈{1,…,p})     #41
ai≤si≤bi   ∀i∈{1,…,n}#42

*M=max {bi+tij- ai}           i,j∈{1,…,n} 

Constraints (42) ensures that a vehicle can start serving a costumer in the time window of that costumer. Constraint (41) keeps track of the duration of the routes. If the arc x_{ijk} is in the route, the constraint can be rewritten to  s_i+t_{ij}\le s_j. The start of the service time at costumer j must be at least t_{ij} later than the start of the service time at costumer i.  
If the arc x_{ijk} is not in the route, constraint (41) is still valid. The constraint can then be rewritten to{\ s}_i+t_{ij}\le s_j+\ M. The value of M is the maximum value of b_i+t_{ij}-{\ a}_i, which is most amount of time possible between s_i and s_j.  
These constraints are slightly similar to the constraints of the Miller-Tucker-Zemlin formulation. It is also not possible to return to a previously served costumer. That’s because the time a costumer is being served is always later than the previously served costumers. So for a VRPTW it isn’t necessary to formulate subtour elimination constraints, while the time window constraints eliminate subtours automatically. 












