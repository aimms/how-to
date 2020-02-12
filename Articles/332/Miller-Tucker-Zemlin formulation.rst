Miller-Tucker-Zemlin formulation
================================
A Capacitated Vehicle Routing Problem (CVRP) can be formulated as a linear integer program model. 

- Every costumer is entered and left once
- The depot is entered and left once by each vehicle 
- Every node is entered and left by the same vehicle 
- The sum of the demand of the costumers shouldn't exceed the capacity of the vehicle. 

It is possible that a subtour occurs. 

.. image:: images/subtour.png

- a way to eliminate subtours is with the miller tucker zemlin formulation 

.. math:: u_{jk} - u_{ik}  \geq D_{j} - C_{k} (1-x_{ijk}) \qquad \forall (i,j) \in V, i \neq j \quad s.t. \enspace D_{i} + D_{j} \leq C_{k} 
.. math:: 0 \leq u_{jk} \leq C_{k} - D_{j} \qquad \forall i \in V 




- how is a CVRP formulated (without SEC) -> mathematically
- now it is possible that a subtour occurs 
- picture of a subtour 
- there are more ways to formulate subtour elimination constraints 
- one way is the miller tucker zemlin formulation

- Mathematic formulation Miller-Tucker-Zemlin formulation
- Explain were it comes from 

- In the CVRP library this formulation can be selected 
- Variable U
- SubtourEliminationConstraints 

- This formulation generated ... subsets and ... constraints