Dantzig-Fulkerson-Johnson formulation
=====================================
A Capacitated Vehicle Routing Problem (CVRP) can be formulated as a linear integer program model. 

- Every costumer is entered and left once
- The depot is entered and left once by each vehicle 
- Every node is entered and left by the same vehicle 
- The sum of the demand of the costumers shouldn't exceed the capacity of the vehicle. 

It is possible that a subtour occurs. 

.. image:: images/subtour.png

- a way to eliminate subtours is with the dantzig fulkerson johnson formulation 

.. math:: \sum_{i,j \in S}{x_{ijk}} \leq |S|-1 \qquad (S \subset V \setminus \{1\}, \quad 2 \leq |S| \leq n - 2)

-> it is based on subsets 
-> amount of sub




- how is a CVRP formulated (without SEC) -> mathematically 
- now it is possible that a subtour occurs 
- picture of a subtour 
- there are more ways to formulate subtour elimination constraints 
- one way is the dantzig fulkerson johnson formulation

- Mathematic formulation Dantzig-Fulkerson-Johnson formulation
- Explain were it comes from 

- In the CVRP library this formulation can be selected 
- Subtoureliminationconstraints
- how are the subsets generated 

- This formulation generated ... subsets and ... constraints



- link naar hoe subsets zijn gemaakt 
- 