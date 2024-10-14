Stochastic Programming
=======================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Stochastic Programming, Scenario Tree, Benders Decomposition, Network Object
   :description: This example illustrates AIMMS capabilities for stochastic programming support.

Direct download AIMMS Project :download:`Stochastic Programming.zip <model/Stochastic Programming.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Stochastic%20Programming

This example illustrates AIMMS capabilities for stochastic programming support. Starting from an existing deterministic LP or MIP model, AIMMS can create a stochastic model automatically, without the need to reformulate constraint definitions. One only needs to provide a scenario tree and stochastic input data as well as to assign decisions to different stages. Once these elements have been specified, any changes made in the deterministic formulation propagate automatically to its stochastic counterpart.

The basic problem illustrated in this example is a simple multi-period production and inventory planning model. In the deterministic case demand is known with certainty for each period and production and stock levels for all periods are decided upon at once. In the stochastic case however, demand for time periods is uncertain and crystallizes in a number of stages. The stages may naturally coincide with the time periods from the deterministic model, but this does not have to be the case. In general, one stage in the stochastic model may consist of multiple periods from the deterministic model.

In this example periods are mapped to stages. For instance, 

.. code-block:: none

   period-1   period-2   period-3   period-4
   stage-1    stage-2    stage-3    stage-4 

may be such a mapping. However, the following mapping is also meaningful:

.. code-block:: none

   period-1   period-2   period-3   period-4
   stage-1    stage-2    stage-2    stage-3

This means that decisions for periods 2 and 3 are taken in the same stage 2, so we have a 4-period deterministic model, but a 3-stage stochastic model.

In this example we assume that a stage must consist of a number of consecutive time periods and that the period to stage mapping is non-decreasing. At the same time, the set of all stages is the set of ALL integers between 1 and the maximum stage number assigned to a period. This means that each stage number between 1 and the largest stage number should appear at least once in the mapping. 

This example also illustrates the use of the Scenario Tree Generation Library, which is an extension of the Scenario Generation system module. This library provides support both for building and for viewing the scenario tree.

Building the tree can be achieved either by incremental branching at each stage (called 'Scenario Generation By Tree') or by building a number of scenarios for the entire horizon and bundling them at every tree level (called "Scenario Generation By Data"). 

For the first method, this example illustrates data sampling in two manners: common random sampling or descriptive sampling. In descriptive sampling  the number of values to be considered is given and these values are then computed as fixed quantiles of the distribution by using the pre-defined function DistributionInverseCumulative. On the Demo Page, the option "Create Tree By Branching" refers to scenario generation by tree with random sampling, while the option "Create Tree By Descriptive" refers to scenario generation by tree with descriptive sampling.

In particular, we assume that stochastic demand can take values in three confidence intervals (L = low, M = medium, H = high) depending on the deterministic demand. These ranges are given in terms of lower and upper fraction of deterministic demand. A probability (chance) is associated with each of these ranges. Within a range, demand is assumed to be uniformly distributed.

From within the graphical interface one can modify deterministic input data, the mapping of periods to stages (along the guidelines provided above) or the ranges (and chances) for different confidence intervals in order to study their effect on the model outcomes. 
When the period to stage mapping is changed, the procedure that assigns decisions to stages should be subsequently run as well (before creating the scenario tree). 
Also, after experimenting with different values, data can be re-initialized using procedures behind initialization buttons.

After a scenario tree has been created, it can be viewed on the "Scenario Tree View" page. 
After creating scenario trees using at least two methods, one tree can be selected for viewing at a time.

The stochastic model is generated in its extensive form (deterministic equivalent) and can be solved in two ways:

- using a standard solver: button "Solve Stochastic Model", or
- using the Benders decomposition approach: button "Solve Stochastic Benders".

Support for Benders decomposition is provided through the Stochastic Decomposition Module. 

Keywords:
Stochastic Programming, Scenario Tree, Benders Decomposition, Network Object


