Traffic Equilibrium
====================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Braess's paradox, Non-cooperative Game, Mixed Complementarity Problem (MCP), Stackelberg Game, Mathematical Programs with Complementarity Constraints (MPCC), PATH, KNITRO, Network Object
   :description: This AIMMS demo describes an example of a mixed complementarity problem (MCP).

Direct download AIMMS Project :download:`Traffic Equilibrium.zip <model/Traffic Equilibrium.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Application%20Examples/Traffic%20Equilibrium

This AIMMS demo describes an example of a mixed complementarity problem (MCP). The problem described here is a so-called traffic equilibrium model. The goal is to find a traffic distribution over the roads, that follows from the behaviour of the drivers.

In this example, we assume that every driver wants to minimize his own travel time in a noncooperative way, his noncooperative behaviour means that he will change his route only when he can find a faster route for himself. There is always a faster route when somebody else with the same origin and destination has a faster route. This means that in the equilibrium situation, all the drivers with the same origin and destination will have the same travel time. This travel time is the minimum travel time for that origin and destination pair. This has been formulated in the complementarity variable "Traffic".

Beside the complementarity variable, it is also allowed to use normal variables and constraints.

This model has been extended with the possibility of using toll roads and maximize the income from the toll fees. This problem is formulated as a mathematical program with complementarity constraints (MPCC). This is a special case of mathematical program with Equilibrium Constraints (MPEC).

This example contains a small dataset because this formulation demands that every route between every origin destination pair is given. For larger dataset this is almost impossible. In that case a path generation method needs to be implemented, which is beyond the scope of this example.

In AIMMS currently the PATH and KNITRO solvers are available for solving mixed complementarity problems (MCP). The KNITRO solver also supports mathematical programs with complementarity constraints (MPCC).

Keywords:
Braess's paradox, Non-cooperative Game, Mixed Complementarity Problem (MCP), Stackelberg Game, Mathematical Programs with Complementarity Constraints (MPCC), PATH, KNITRO, Network Object

