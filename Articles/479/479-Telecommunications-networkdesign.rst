Telecommunications: Capacitated network design problem
=======================================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

Direct download AIMMS Project :download:`NetworkDesign.zip <model/NetworkDesign.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Telecommunications/NetworkDesign

Problem type:
MIP (hard)

Keywords:
Benders Decomposition, GMP, XML

Description:
Network design problems deal with the selection of a subset of edges in
an existing network, such that for a set of commodities the given demand
for each commodity can be transported from its origin to its destination,
at minimum cost. In the capacitated network design problem the edges have
(fixed) capacities. Network design problems arise in telecommunications
and transportation planning.

The data of the instances are read using XML files.

References:
Raack, C., A.M.C.A. Koster, S. Orlowski, R. Wessaly, On cut-based inequalities
for capacitated network design polyhedra, Networks 57(2) (2011), pp. 141-156.

SNDlib: http://sndlib.zib.de/home.action

.. meta::
   :keywords: Benders Decomposition, GMP, XML