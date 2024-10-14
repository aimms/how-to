Uncertainty: Production Planning - Robust Optimization
========================================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Uncertain data, robust optimization
   :description: The goal is to find the production plan for two products that maximizes the profit of the company. 

Direct download AIMMS Project :download:`ProductionPlanningRO.zip <model/ProductionPlanningRO.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Uncertainty/ProductionPlanningRO

Problem type:
LP (small)

Keywords:
Uncertain data, robust optimization

Description:
A company produces two kinds of drugs, DrugI and DrugII, containing a specific active
agent A, which is extracted from raw materials purchased on the market. There are two
kinds of raw materials, RawI and RawII, which can be used as sources of the active
agent. The related production, cost, and resource data are given. The goal is to
find the production plan that maximizes the profit of the company.

This is Example 1.1.1 from the book Robust Optimization by Aharon Ben-Tal, Laurent El
Ghaoui and Arkadi Nemirovski (2009).

Note:
The objective function used in this model is 'total profit' instead of 'minus
total profit' as used in the book. The total profit is maximized in this model.

References:
Ben-Tal, A., L. El Ghaoui, and A. Nemirovski, Robust Optimization, Princeton University
Press, 2009

