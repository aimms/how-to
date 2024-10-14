Energy: Water Distribution Network Design
==============================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

Direct download AIMMS Project :download:`WaterDistribution.zip <model/WaterDistribution.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Energy/WaterDistribution

Problem type:
MINLP (small)

Keywords:
Branch-and-Bound, generic algorithm, GMP, sections

Description:
The optimal design of a Water Distribution Network consists of the choice of
a diameter for each pipe, while other design properties are considered to be
fixed (e.g., the topology and pipe lengths).

The Water Distribution Network is formulated as MINLP problem and solved using
a generic Branch-and-Bound algorithm that was created using GMP functionality.
The algorithm is available in the file 'BB.ams' and can be included in any
AIMMS project as a Section.

References:
Bragalli, C., C. D'Ambrosio, J. Lee, A. Lodi and P. Toth, On the optimal design
of water distribution networks: a practical MINLP approach, Optimization and
Engineering 13(2), 219-246 (2012).

CMU-IBM Cyber-Infrastructure for MINLP collaborative site, at:
http://www.minlp.org/index.php

Nemhauser, G.L. and L.A. Wolsey, Integer and Combinatorial Optimization, Wiley,
1999

.. meta::
   :keywords: Branch-and-Bound, generic algorithm, GMP, sections
