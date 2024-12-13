.. |sp| image:: /Images/icons/StringParameter.png
.. |p| image:: /Images/icons/Parameter.png
.. |s| image:: /Images/icons/Set.png

Capacitated Vehicle Routing Problem Library
===========================================

There is a library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**). This article explains how to use that library in your own model. There are different ways to formulate a CVRP. In the CVRP library there are four options to choose from, which will be mentioned in this article. 

    :download:`AIMMS library download <model/CVRP library.zip>` 


Capacitated Vehicle Routing Problem
-----------------------------------
A **CVRP** deals with the following problem: a set is given with a depot and multiple costumers. The distances between their locations are known. A number of vehicles is available to serve the costumers. All costumers have a certain demand and the vehicles have the same maximum capacity. The shortest route for the vehicles must be found where all costumers get their demand. The vehicles all start and end at the depot. 

.. image:: images/CVRP.png
   :scale: 35%
   :align: center

How to Use the Library
----------------------

#.  You should add the library to your model, see more `here <https://how-to.aimms.com/Articles/84/84-using-libraries.html>`_.

#.  One of the required input arguments is ``s_Formulations``. You should make this set a subset of ``cvrpl::PossibleFormulations``. Which is a set inside the library with all possible formulations.

#.  When you choose a formulation without time windows, the library can be called with the procedure ``cvrpl::pr_NoTimeWindows``. If you choose the formulation with time windows, the library can be called with the procedure ``cvrpl::pr_CVRPLibrary``. They have the following input and output arguments:

.. code-block:: aimms
    
    cvrpl::pr_CapacitatedVehicleRoutingProblem
        (s_Formulations, s_Nodes, p_NumberOfVehicles, p01_MaxorExact, 
        p_Distance, p_Demand, p_Capacity, p_TotalDistance, p01_x, p_BoundTotalDist, 
        sp_SolverStatus, sp_ProgramStatus, p_SolverTime);

.. code-block:: aimms
    
    cvrpl::pr_CapacitatedVehicleRoutingProblemTimeWindows(
        s_Formulations, s_Nodes, p_NumberOfVehicles, p01_MaxorExact, 
        p_Distance, p_Demand, p_Capacity, p_TWLowerBound, p_TWUpperBound, p_ServiceTime, 
        p_TotalDistance, p01_x, p_StartServing, p_BoundTotalDist, sp_SolverStatus, 
        sp_ProgramStatus, p_SolverTime);


Input and Output Arguments
--------------------------

The input identifiers are:

* |s| ``s_Formulations``
* |s| ``s_Nodes`` with ``i`` and ``j`` as indexes
* |p| ``p_NumberOfVehicles``
* |p| ``p01_MaxorExact`` as a binary parameter
* |p| ``p_Distance(i, j)``       
* |p| ``p_Demand(i)``         
* |p| ``p_Capacity(k)``
* |p| ``p_TWLowerBound(i)`` [1]
* |p| ``p_TWUpperBound(i)`` [1]
* |p| ``p_ServiceTime(i, j)`` [1]

``s_Formulations`` should contain the formulation you want to use to solve the problem, choosing from:
**Explicit Dantzig-Fulkerson-Johnson**, **Miller-Tucker-Zemlin**, **Implicit Dantzig-Fulkerson-Johnson** or **Time Windows**
The set ``s_Nodes`` contains the depot and all costumers. ``p_MaxorExact`` is a binary parameter that indicates whether ``p_NumberOfVehicles`` is a maximum or an exact amount. 
If ``p_MaxorExact`` is 0, then a maximum of ``p_NumberOfVehicles`` can be used. If ``p_MaxorExact`` is 1, 
then exactly ``p_NumberOfVehicles`` should be used. ``p_Distance`` describes the distance between two nodes. 
When there is no road between two nodes, you can just leave the value for that distance empty. 

The output identifiers are: 

* |p| ``p_TotalDistance``                
* |p| ``p01_x(i, j, k)`` where ``i <> j``  as a binary parameter
* |p| ``p_StartServing(i)`` [2]
* |p| ``p_BoundTotalDist``
* |sp| ``sp_SolverStatus``
* |sp| ``sp_ProgramStatus``
* |p| ``p_SolverTime``

``p_TotalDistance`` is the total distance of the shortest route. ``p01_x`` is a binary variable with a value of ``1`` if the road from ``i`` to ``j`` is in the shortest route and is driven by vehicle ``k``. ``p_BoundTotalDist`` is the lower bound of the total distance. The last three arguments provide information on how the program was executed. 

.. note::
    * [1] These input arguments are only necessary when you use time windows. ``p_TWLowerBound`` and ``p_TWUpperBound`` indicate the time in between which a vehicle should arrive at node ``i``. ``p_ServiceTime`` denotes the time it takes to get from node ``i`` to node ``j``. It may include the service time at node ``i``. 
    * [2] The output argument ``p_StartServing`` is only necessary when you use time windows. It denotes the time that a vehicle should arrive at node ``i``. 

.. seealso::

    * The general formulation of a CVRP used in the library is described in :doc:`332-Formulation-CVRP`
    * The four different formulations are explained in the following articles: :doc:`332-Explicit-Dantzig-Fulkerson-Johnson-formulation`, :doc:`332-Miller-Tucker-Zemlin-formulation`, :doc:`332-Implicit-Dantzig-Fulkerson-Johnson` and :doc:`332-Time-Windows`.
    * The formulations comparison in :doc:`332-Comparing-Formulations`



