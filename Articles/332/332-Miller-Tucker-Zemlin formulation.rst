Miller-Tucker-Zemlin formulation
================================
The library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**) contains four different formulation options. The formulations have different methods of eliminating subtours. In this article the Miller-Tucker-Zemlin formulation is discussed. 

Subtour Elimination Constraints
-------------------------------
The Miller-Tucker-Zemlin (MTZ) formulation uses an extra variable to eliminate subtours. This variable ( :math:`u_{i}` ) increases each time a new node is visited. If vehicle k drives from node i to node j, the value of u_j should be higher than the value of u_i. This makes it impossible to return to a previously visited node that already has a lower value for u_i. 

These constraints should apply to every node except for the depot (n = 1), since every vehicle should return there. The Miller-Tucker-Zemlin constraints can be formulated as follows:

uj-ui ≥qj- Q1-xij   ∀i,j∈V∖{1}#28 
qi≤ui≤Q   ∀i ∈V∖{1}#29

If the arc x_{ij} is in the route, constraint (28) can be rewritten to u_j\geq{u_i+q}_j. This ensure that the value of u_j is at least q_j more than u_i. 
If the arc x_{ij} is not in the route, the constraint is still valid. The equation could then be rewritten to u_j-q_j\gequ_i-Q. Constraint (29) states that q_j is the lowest possible value of u_j and Q is the greatest possible value of u_i. So u_j-q_j will at least be 0 and u_i-Q will at most be 0. So u_j-q_j is greater than or equal to u_i-Q


duration
--------

- in aimms hoef je alleen maar een variable te maken en die twee constraints toe te voegen 
- hoe lang duurt dit














