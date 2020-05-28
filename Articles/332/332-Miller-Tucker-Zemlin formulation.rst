Miller-Tucker-Zemlin formulation
================================
The library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**) contains four different formulation options. The formulations have different methods of eliminating subtours. In this article the Miller-Tucker-Zemlin formulation is discussed. 
This is an example of a subtour in a route for a CVRP:

.. image:: images/subtour.png
   :scale: 35%
   :align: center

Idea behind the formulation
---------------------------
The Miller-Tucker-Zemlin (MTZ) formulation uses an extra variable. The variable is called :math:`u_{i}` and gets a value for each node, except for the depot. If a vehicle drives from node i to node j, the value of :math:`u_{j}` has to be bigger than the value of :math:`u_{i}`.

.. image:: images/MTZ1.png
   :scale: 35%
   :align: center

So each time a new node is being visited, the value for :math:`u_{i}` increases. 

.. image:: images/MTZ2.png
   :scale: 35%
   :align: center

The node that the vehicle will visit after node 5, should again have a larger value of :math:`u_{i}`. It would not be possible to go from node 5 to node 2, because that node already has a lower value of :math:`u_{i}`. This ensures that a vehicle will not drive in a circle. Since that would make it impossible for every value of :math:`u_{i}` to be larger than the previous one.  
Since the depot does not get a value of :math:`u_{i}`, it is possible to drive in a circle if the vehicle starts and ends at the depot. 

.. image:: images/MTZ3.png
   :scale: 35%
   :align: center

The vehicle can now drive from node 5 back to the depot and :math:`u_{j}` is always larger than :math:`u_{i}`.
So the only circles permitted to be driven are the ones passing the depot. All the other circles would be subtours and are eliminated by this formulation. 


Subtour Elimination Constraints
-------------------------------
The binary variable :math:`x_{ijk}` has a value of 1 if vehicle k drives from node i to node j. Q is the capacity of the vehicles and :math:`q_{i}` is the demand of node i. V is a set containing all the nodes, and the depot is n=1. The constraints can be formulated as follows:


















