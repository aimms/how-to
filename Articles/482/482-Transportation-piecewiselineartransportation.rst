Transportation: Piecewise linear transportation
==================================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

Direct download AIMMS Project :download:`PiecewiseLinearTransportation.zip <model/PiecewiseLinearTransportation.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Transportation/PiecewiseLinearTransportation

Problem type:
MIP (small)

Keywords:
Piecewise linear, special ordered set, SOS2, network object.

Description:
The Piecewise Linear Transportation problem models a simple transportation model
with one complication, namely the objective function representing the cost is a
piecewise linear function. This piecewise linear cost function is used to model
price discounts. The piecewise linear function is modelled using the integer
linear programming trick of Chapter 7.6 of the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_
book.

Note:
The Piecewise Linear Transportation problem is described by Christensen and
Labbe (2015) but the model formulation in this project differs from their
formulation.

References:
Christensen, T.R.L., M. Labbe, A branch-cut-and-price algorithm for the
piecewise linear transportation problem, European Journal of Operational
Research 245(3), pp. 645-655 (2015)

.. meta::
   :keywords: Piecewise linear, special ordered set, SOS2, network object.

