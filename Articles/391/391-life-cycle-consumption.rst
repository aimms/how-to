Life Cycle Consumption
==========================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Utility Function, Discount Factor, Present Value, Future Value, Nonlinear Programming, Nonlinear Solvers, Curve Object
   :description: In this example a life-cycle consumption optimization problem has been modeled, including labor and assets.

Direct download AIMMS Project :download:`Life Cycle Consumption.zip <model/Life Cycle Consumption.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Application%20Examples/Life%20Cycle%20Consumption

In this example a life-cycle consumption optimization problem has been modeled, including labor and assets.

Users of this application can make changes to the model from the user interface. Two types of utility functions are available:

* Exponential utility: :math:`Utility = - e^{-Consumption} - Labor^2`

* Square root utility: :math:`Utility = \sqrt{Consumption}  - Labor^2`

In addition, the user can choose to allow borrowing up to a chosen level, adding explicit modeling of assets to the model.

Please use the navigation in the left column to browse this example application.

Keywords:
Utility Function, Discount Factor, Present Value, Future Value, Nonlinear Programming, Nonlinear Solvers, Curve Object


