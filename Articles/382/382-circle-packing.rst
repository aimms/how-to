Circle Packing
================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Circle Packing, Multistart Algorithm, Network Object
   :description: In this AIMMS project different circle packing problems are solved.

Direct download link :download:`CirclePacking.zip <model/CirclePacking.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Application%20Examples/Circle%20Packing


In this AIMMS project different circle packing problems are solved. 

An object packing is a non-overlapping arrangement of a collection of objects in a container set; an optimized packing meets a specific optimality criterion. This general problem statement includes the frequently studied special cases in which identical circles (with an unknown, maximized radius) are to be packed into the unit square or the unit circle. A similar - but generally more difficult - problem is the packing of an arbitrary collection of circles in an optimized circle (with minimal radius).

To solve object packing problems numerically, we often need nonlinear optimization tools. In the case of the circle packing listed above, we have to handle non-convex models, therefore the multi-start algorithm can be used as a suitable tool.

In this illustrative AIMMS project, three different circle packing problems are solved: 

1) Packing equal size circles in the unit square; the objective is to maximize the radius of the packed circles.
2) Packing equal size circles in the unit circle; the objective is again to maximize the radius of the packed circles.
3) Packing a set of non-uniform size circles in an optimized circle; the objective is to minimize the radius of the container circle.

These models have been developed based on, and thanks to, reference materials by János D. Pintér and Frank J. Kampas.

The Multistart module can be added via menu "Settings" - "Install System Module...". More details of the algorithm can be found in :doc:`optimization-modeling-components/advanced-methods-for-nonlinear-programs/index`.
           
Keywords:
Circle Packing, Multistart Algorithm, Network Object


