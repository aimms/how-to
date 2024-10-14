Chance Constraints
==================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Portfolio Selection, Chance Constraints, Safe Approximation, Robust Optimization
   :description: This example implements a portfolio selection model with uncertain investment returns, covering a single time period.

Direct download AIMMS Project :download:`Chance Constraints.zip <model/Chance Constraints.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Chance%20Constraints

This example implements a portfolio selection model with uncertain investment returns, covering a single time period.

There are 200 assets. Asset number 200 ("money in the bank") has yearly return 1.05 and zero variability. The yearly returns of assets from 1 to 199 are independent random variables taking values in the segments of the form [mean - deviation, mean + deviation], with mean and deviation values provided on the "Input Data" page.

The goal is to distribute $10,000 between the assets in order to maximize the value-at-risk of the resulting portfolio. This means, for example, with the required risk level being 0.5%, a guarantee of 99.5% must be provided that the total portfolio return is at least as high as the value-at-risk. At the same time, the maximum value-at-risk for which such a guarantee can be given must be determined together with investment decisions. The constraint describing this requirement is a so-called Chance Constraint.

AIMMS handles uncertainty in input data through the safe approximation of chance constraints by a suitably chosen Robust Optimization counterpart, as explained in :doc:`optimization-modeling-components/robust-optimization/chance-constraints`. In a Robust Optimization model certain constraints are required to hold for every realization of the data within a specified uncertainty set. When a Robust Counterpart (RC) is used as a safe approximation of a chance constraint, an uncertainty set is considered based on the chance level and on the (partial) information known on the randomly distributed data. The approximations supported by AIMMS are Ball, Box, Ball-box, Budgeted and Automatic. Whether or not a particular approximation type is possible, depends on the characteristics of the distributions used in the chance constraint.

In this example, it is assumed that the random perturbations of the asset returns are symmetric and unimodal random variables with zero mean and support the interval [-1,1]. Thus by setting the approximation type to 'Automatic', AIMMS can apply the most accurate approximation. Other than letting AIMMS decides, you can also choose a certain approximation. This is illustrated in the "Portfolio Selection" page, together with explanation for each approximation and some discussion on the results.

For a more detailed discussion of this example we refer to Chapter 2 of the book "Robust Optimization" by Aharon Ben-Tal, Laurent El Ghaoui, and Arkadi Nemirovski, Princeton Series in Applied Mathematics, 2009.

Keywords:
Portfolio Selection, Chance Constraints, Safe Approximation, Robust Optimization


