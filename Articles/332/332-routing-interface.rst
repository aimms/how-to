Capacitated Vehicle Routing Problem Library
===========================================

There is a library in AIMMS that solves a Capacitated Vehicle Routing Problem (CVRP). (download link). This article explains how to use that library in your own model. There are different ways to formulate a CVRP. In the CVRP library there are four options to choose from, which will be mentioned in this article. 

Capacitated Vehicle Routing Problem
-----------------------------------
A **Capacitated Vehicle Routing Problem** (CVRP) deals with the following problem: A set is given with a depot and multiple costumers. The distances between their locations are known. A number of vehicles is available to serve the costumers. All costumers have a certain demand and the vehicles have the same maximum capacity. The shortest route for the vehicles must be found where all costumers get their demand. The vehicles all start and end at the depot. 

.. image:: images/CVRP.png
   :scale: 35%
   :align: center

How to use the Library
----------------------
1. You should add the library to your model. (This article explains how to do that: https://how-to.aimms.com/Articles/84/84-using-libraries.html)
2. One of the required input arguments is ``s_Formulations``. You should make this set a subset of ``cvrpl::PossibleFormulations``. Which is a set inside the library with all possible formulations.
3. When you choose a formulation without time windows, the library can be called with the procedure ``cvrpl::pr_NoTimeWindows``. If you choose the formulation with time windows, the library can be called with the procedure ``cvrpl::pr_CVRPLibrary``. They have the following input and output arguments:

.. code-block:: aimms

	cvrp::pr_NoTimeWindows(s_Formulations, s_Nodes, p_NumberOfVehicles, p_Distance, p_Demand, p_Capacity, 
	p_TotalDistance, bp_x, p_BoundTotalDist);

.. code-block:: aimms

	cvrpl::pr_CVRPLibrary(s_Formulations, s_Nodes, p_NumberOfVehicles, p_Distance, p_Demand, p_Capacity,
	p_TWLowerBound, p_TWUpperBound, p_ServiceTime, p_TotalDistance, bp_x, p_StartServing, 
	p_BoundTotalDist);


Input and output arguments
--------------------------

======================  ==================  =====  ==================    
Input Arguments         Type                Index  Index Domain    
======================  ==================  =====  ==================    
**s_Formulations**      Set                      
**s_Nodes**             Set                 i, j       
**p_NumberOfVehicles**  Parameter                    
**p_Distance**          Parameter                  (i, j)       
**p_Demand**            Parameter                  ( i )         
**p_Capacity**          Parameter                  ( k )
**p_TWLowerBound** *	Parameter				   ( i )
**p_TWUpperBound** *	Parameter				   ( i )
**p_ServiceTime** * 	Parameter				   (i, j)
======================  ==================  =====  ==================    

``s_Formulations`` is a set containing the formulation you want to use to solve the problem. The set ``s_Nodes`` contains the depot and all costumers. ``p_Distance`` discribes the distance between two nodes. It's possible that there is no road at all between two nodes. Then you can just leave the value for that distance blank. 

* These input arguments are only necessary when you use time windows. ``p_TWLowerBound`` and ``p_TWUpperBound`` indicate when a vehicle should arrive at node i. ``p_ServiceTime`` denotes the time it takes to get from node i to node j. It may include the service time at node i. 

======================  ==================  =====  ==================
Output Arguments        Type                Index  Index Domain
======================  ==================  =====  ==================
**p_TotalDistance**     Parameter                
**bp_x**                Parameter (binair)         (i, j, k) | i <> j  
**p_StartServing** *    Parameter        		   ( i )
**p_BoundTotalDist**	Parameter
======================  ==================  =====  ==================

``p_TotalDistance`` is the total distance of the shortest route. ``bp_x`` is a binary variable with a value of ``1`` if the road from ``i`` to ``j`` is in the shortest route and is driven by vehicle ``k``. ``p_BoundTotalDist`` is the lower bound of the total distance. 

* The output argument ``p_StartServing`` is only necessary when you use time windows. It denotes the time that a vehicle should arrive at node i. 

Formulations
------------
The input argument ``s_Formulations`` should contain the name(s) of the formulation(s) you want to use to solve the problem. So far there are four options:

- **Explicit Dantzig-Fulkerson-Johnson**
- **Explicit Miller-Tucker-Zemlin**
- **Implicit Dantzig-Fulkerson-Johnson**
- **Time Windows**





		
