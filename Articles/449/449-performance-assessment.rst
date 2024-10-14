Performance Assessment
=======================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Linear Program, What-If Analysis
   :description: In this example the performance of a set of comparable organizations is determined, while introducing the concept of relative efficiency from organizational unit to another. 

Direct download AIMMS Project :download:`Performance Assessment.zip <model/Performance Assessment.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Modeling%20Book/Performance%20Assessment

In this example the performance of a set of comparable organizations is determined, while introducing the concept of relative efficiency from organizational unit to another. 

Based on this concept, the underlying problem can be translated into a collection of linear programming models using relative efficiency as the optimization criteria. The efficient organizations are identified and form a reference for the other organizations. 

We present an example with seven input-output categories (i.e. total cost, staff cost, non-staff cost, age of store, competition, customer service, and revenue) and 30 related organizations (DMU's) to illustrate the purposes.
 
Efficiency can be described as the use made of resources (inputs) in the attainment of outputs. A DMU (Decision Making Unit) is 100% absolute efficient if none of its outputs can be increased without either increasing other input(s) or decreasing other output(s). A 100% relative efficiency is attained by a particular DMU once any comparison with other relevant DMU's does not provide evidence of inefficiency in the use of any input or output. Such an assessment of comparable organizations is often put under the general hading of Data Development Analysis (DEA).

Details about this example can be read in Chapter 13 of the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ book. An electronic version of this book is available through the 'Help' menu.

Keywords:
Linear Program, What-If Analysis

