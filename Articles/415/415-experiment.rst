Experiment
===========

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Histogram, statistical functions, distribution functions, bar chart, composite table, table, scalar object
   :description: This AIMMS project illustrates how to perform and visualize an experiment in AIMMS.

Direct download AIMMS Project :download:`Experiment.zip <model/Experiment.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Experiment

This AIMMS project illustrates how to perform and visualize an experiment in AIMMS. The experiment is based on a simple transport model, in which the demand is distributed according to a given distribution. The objective of the experiment is to observe the distribution of the profit.

The demand is randomized using one of the distribution functions available in AIMMS. This particular example uses the LogNormal distribution, where the average demand and demand deviation of every customer is given as input data.

To visualize the distribution of profit, we use AIMMS' histogram support functions to compute the frequencies and moments of the resulting distribution. The frequencies are displayed graphically, using AIMMS' common bar chart object.

You can use the resulting distribution, for instance, to assess the risk of losing money, given an uncertain demand and the desired margin on the commodity.

Keywords:
Histogram, statistical functions, distribution functions, bar chart, composite table, table, scalar object



