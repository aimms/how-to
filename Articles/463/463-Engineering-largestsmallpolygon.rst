Engineering: Largest small polygon
==================================

.. warning::
   This article references outdated technology and is provided for historical purposes only.
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :description: Finds the polygon with maximum area among all n-sided polygons with diameter at most 1, an NLP with many local minima where general solvers typically find only local solutions.
   :keywords: largest small polygon, NLP, area maximization, diameter constraint, local minima, nonlinear programming

Direct download AIMMS Project :download:`LargestSmallPolygon.zip <model/LargestSmallPolygon.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Engineering/LargestSmallPolygon

Problem type:
NLP (small)

Description:
Find the polygon with maximal area, among polygons with n sides and diameter
:math:`d <= 1`. This problem has many local minima and therefore general nonlinear
solvers are usually expected to find only local solutions.

References:
Dolan, E.D., J.J. More, Benchmarking Optimization Software with COPS, 2000.
