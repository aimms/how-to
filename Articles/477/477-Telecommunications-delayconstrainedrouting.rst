Telecommunications: Delay constrained routing
=============================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

Direct download AIMMS Project :download:`DelayConstrainedRouting.zip <model/DelayConstrainedRouting.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Telecommunications/DelayConstrainedRouting

Problem type:
MINLP (small)

Keywords:
Outer Approximation, GMP-AOA, convex, lazy constraint callback

Description:
The Delay-Constrained Routing Problem is a problem in telecommunications.
Services are routed through a network, from source to destination, such
that the delay of each service satisfies a certain guarantee. The objective
is to minimize the total routing cost. This problem is solved using the
AIMMS Outer Approximation (AOA) algorithm.

References:
Hijazi, H., P. Bonami, G. Cornu�jols, A. Ouorou, Mixed-integer nonlinear
programs featuring "on/off" constraints, Computational Optimization and
Applications 52(2) (2012), pp. 537-558

.. meta::
   :description: Solves the Delay-Constrained Routing Problem as a MINLP using the AIMMS Outer Approximation algorithm to minimize routing cost subject to per-service delay guarantees.
   :keywords: Outer Approximation, GMP-AOA, convex, lazy constraint callback, MINLP, telecommunications, delay-constrained routing

