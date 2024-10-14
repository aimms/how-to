Horizon
========

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: LP model, Horizon, Composite table
   :description: This AIMMS project illustrates the basic behavior of horizons in AIMMS.

Direct download AIMMS Project :download:`Horizon.zip <model/Horizon.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Horizon

This AIMMS project illustrates the basic behavior of horizons in AIMMS. Through this example project you can learn how to create a horizon which consists of three separate sub-intervals
- a past interval
- a planning interval
- a beyond interval

On the demo page you can modify the length of the entire horizon and of each of the separate sub-intervals by modifying data defining the horizon.

Through some simple arithmetic computations defined over horizons illustrated in the model you learn 
- how you can bind an index to a specific interval of the horizon
- the corresponding interpretation of the AIMMS lag and lead operators

In addition, the model contains a very simple horizon-dependent optimization model. By looking at the variable and constraint declarations and viewing the resulting solution, you can see how AIMMS will 
- restrict constraint generation to the planning interval of a horizon
- consider data of variables outside of the planning interval of the horizon as fixed

Keywords:
LP model, Horizon, Composite table


