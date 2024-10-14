Refinery Pooling Planning
============================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Pooling, Global optimization, Nonlinear Programming, NLP, Pivot table, Network object
   :description: This exampl eis a refinery-pooling problem, which illustrates the effect of selecting different initial values in supporting the solution finding process.

Direct download AIMMS Project :download:`Refinery Pooling Planning.zip <model/Refinery Pooling Planning.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Modeling%20Book/Refinery%20Pooling%20Planning

The model is a refinery-pooling problem, which maximizes the sales value of end products by deciding how much of each intermediate stream, within limits, is to be placed in each of the pool tanks.

This example illustrates the effect of selecting different initial values in supporting the solution finding process and also in determining different locally optimal solutions of a nonlinear programming model.  

If the BARON solver is available in your AIMMS system, then this example also shows the BARON's capability in finding a global optimal solution.

An experiment is conducted with different initial values to find at least two different local optima for this example, namely 3624.0 and 4714.4.

Details about this example can be read in Chapter 12 of the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ book. An electronic version of this book is available through the 'Help' menu.

Keywords:
Pooling, Global optimization, Nonlinear Programming, NLP, Pivot table, Network object




