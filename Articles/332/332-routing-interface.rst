Using the Routing Library 
=========================

.. image:: images/CVRP.png
   :scale: 25 %

A **Capacitated Vehicle Routing Problem** (CVRP) deals with the following problem: A set is given with a depot and 
multiple costumers. The distances* between those locations are known. A number of vehicles is available 
to serve the costumers. All costumers have a certain demand and the vehicles have a maximum capacity. The shortest 
route for the vehicles must be found where all costumers get their demand. The vehicles all start and end at the 
depot. 


This CVRP Library (link to the library) can be added to any model to solve this problem. It is a 
reusable library that requires some input argumentsmunts and gives back some output documents. This article 
explains how to add a library to your model: https://how-to.aimms.com/Articles/84/84-using-libraries.html


The library is called with the following code:
cvrpl::Solve_CVRP(p_NumberOfVehicles1, p_Distance1, p_Demand1, p_Capacity1, p_TotalDistance1, bp_x1);

These are all arguments you have to include in your own model. It doesn't matter what you call them, 
as long as you do add them in the same order and they have the same amount of arguments. The first
four arguments are input arguments and the last two are output arguments. 

**Input:**

- **p_NumberOfVehicles1:** This should be an integer that says how many vehicles are available. 
- **p_Distance1:** The distance between node i and j (Or cost if you're trying to find the cheapest route instead of the shortest)
- **p_Demand1:** The Demand of costumer i.
- **p_Capacity1:** Which is the same for each vehicle 
- **s_Formulations:** The formulations you want to choose.

**Output:**

- **p_TotalDistance1:** The total distance the chosen route
- **bp_x1:** the paths that are in the chosen route





There are two different ways of formulating the subtour elimination constraints.
 
- **Dantzig-Fulkerson-Johnson**
- **Miller-Tucker-Zemlin**

The **Dantzig-Fulkerson-Johnson** (DFJ) formulation of subtour elimination constraints is:

.. code-block:: none

        sum((i, j) | i and j in S, x(i, j, k) ) <= |S| – 1 			for all S, 1 < |S| < n


This is based on the idea that the amount of edges in a subset must be lower than the amount of nodes in that subset. 
Otherwise it would make a subtour. If there is a set of n costumers, this formulation would make ... subsets and 
... constraints. 

The **Miller-Tucker-Zemlin** (MTZ) formulation of subtour elimination constraints is:

.. code-block:: none

		u(i,k) - u(j,k) + Capacity * x(i,j,k) <= Capacity - Demand(j)
		Demand(i) <= u(i,k) <= Capacity 

U is variable that goes up each time the tour visits a new costumer. The constraint says that if you go from 
costumer i to costumer j, u(j) must be bigger than u(i). That way it isn't possible to go back to a costumer that 
has already been visited (because that costumer already has a lower value for u) and create a subtour. If the set of
costumers contains n costumers, this formulation would make ... variables and ... constraints.






* Literature / further reading
* eg https://how-to.aimms.com/Articles/126/126-TSP-Lazy-Constraints.html

-> tip van Chris: Het is handig om als plaatje een voorbeeld te gebruiken zodat het voor de gebruikers 
extra makkelijk te begijpen is.