Investment Portfolio Selection
===============================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Mathematical Derivation, Mathematical Reformulation, Parametric Curve, Bar Chart, Mixed-Integer Nonlinear Programming, MINLP, BARON, Knitro
   :description: This example models how top management could spread an overall budget over several investment categories.

Direct download AIMMS Project :download:`Investment Portfolio Selection.zip <model/Investment Portfolio Selection.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Modeling%20Book/Investment%20Portfolio%20Selection

This example is based on the Chapter 'Portfolio Selection Problem' (Chapter 18) from the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ book (an electronic version of the book is available through the 'Help' menu).

In this example the strategic portfolio selection model has been implemented. It models how top management could spread an overall budget over several investment categories. Once their budget allocation becomes available, tactical investment decisions at the next decision level must be made concerning individual securities within each investment category. Such a two-phase approach supports hierarchical decision making which is typical in large financial institutions.

The model can be extended with a cost budget. In this case, there are nonlinear cost associated with the investments. The total cost cannot exceed the cost budget. The model becomes a MINLP, which can be solved with AOA (GMP), BARON (if available) and Knitro (if available).

Keywords:
Mathematical Derivation, Mathematical Reformulation, Parametric Curve, Bar Chart, Mixed-Integer Nonlinear Programming, MINLP, BARON, Knitro

