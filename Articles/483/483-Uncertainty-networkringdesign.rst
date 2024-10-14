Uncertainty: Synchronous Optical Network Ring Design
====================================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Synchronous Optical Network, SONET, ring assignment, stochastic programming, stochastic integer programming, uncertain data, Benders decomposition, networkobject
   :description: In this problem we consider the assignment of rings to nodes in a network.

Direct download AIMMS Project :download:`NetworkRingDesign.zip <model/NetworkRingDesign.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Uncertainty/NetworkRingDesign

Problem type:
MIP (medium - hard)

Keywords:
Synchronous Optical Network, SONET, ring assignment, stochastic programming,
stochastic integer programming, uncertain data, Benders decomposition, network
object.

Description:
To prevent failures of a single optical telecommunication fiber, selfhealing rings (SHR)
are utilized to connect client nodes by a ring of fibers. These rings automatically
reroute telecommunication traffic in the case of equipment failure, providing essential
survivability to high-bandwidth networks.

In this problem we consider the assignment of rings to nodes in a network. The objective
is to minimize the total cost of the network subject to a ring capacity limit. Demands
are given for each pair of nodes. If one of the nodes is not assigned to a ring then
the corresponding demand is unmet, and penalized in the objective.

In practice, the demand may depend on several unknown factors. In such cases, a network
design that considers this uncertainty may perform better than one that does not. In
this project we deal with this uncertainty by using stochastic programming, which
results in this case in a 2-stage stochastic integer program. One way to solve the
stochastic integer program is by using the Benders decomposition algorithm of CPLEX.

This example uses the network structure of several instances used by Goldschmidt, Laugier
and Olinick (2003). These instances can be found at:
https://lyle.smu.edu/~olinick/papers/srap/srap.html

Note:
To run the Benders decomposition algorithm with CPLEX, version 12.7 or higher is
required.

References:
Smith, J.C., A. J. Schaefer, J. W. Yen, A Stochastic Integer Programming Approach to
Solving a Synchronous Optical Network Ring Design Problem, NETWORKS 44(1) (2004),
pp. 12-26.

Goldschmidt, O., A. Laugier, E.V. Olinick, SONET/SDH ring assignment with capacity
constraints, Discrete Applied Mathematics 129 (2003), pp. 99-128

