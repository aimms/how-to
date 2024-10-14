Routing: Capacitated Vehicle Routing Problem
=================================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Subtour elimination, Miller-Tucker-Zemlin, incumbent callback, network object
   :description: The Vehicle Routing Problem (VRP) deals with the distribution of goods between depots and customers using vehicles.

Direct download AIMMS Project :download:`CVRP.zip <model/CVRP.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Routing/CVRP

Problem type:
MIP (medium)

Keywords:
Subtour elimination, Miller-Tucker-Zemlin, incumbent callback, network object

Description:
The Vehicle Routing Problem (VRP) deals with the distribution of goods between depots
and customers using vehicles. In the Capacitated VRP the vehicles have a limited
capacity. The model formulation in this project uses the three-index vehicle flow
model of Toth and Vigo (2002), denoted by VRP4 on pp. 15-16. In this project two
variants on this formulation are used. In the first variant the constraint (1.33) is
replaced by the Miller-Tucker-Zemlin constraints (1.37) and (1.38). In the second
variant it is replaced by the subtour elimination constraints (1.36).

This project uses the data instance 'E-n23-k3' from the Christofides and Eilon
set at https://neo.lcc.uma.es/vrp/

References:
Toth, P., and D. Vigo, An Overview of Vehicle Routing Problems, In: The Vehicle
Routing Problem, P. Toth and D. Vigo (eds), SIAM Monographs on Discrete Mathematics
and Applications, Philadelphia, 2002, pp. 1-26



