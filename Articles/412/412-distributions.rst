Distributions
=============

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Probability Distribution, Extreme Value Distribution Approximation, Distribution Operators, 2D Chart
   :description: This example shows the four distributions with shape parameter and lower bound (only): Pareto, Lognormal, Weibull and Gamma Distribution.

Direct download AIMMS Project :download:`Distributions.zip <model/Distributions.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Distributions

This example shows the four distributions with shape parameter and lower bound (only): Pareto, Lognormal, Weibull and Gamma Distribution. The distribution operators for the distribution density, cumulative and inverse cumulative distribution, and density of the inverse cumulative distribution are shown for those distributions, in addition to sample density of samples from those distributions.

All distributions are first shifted and stretched to a mean of 0 and a standard deviation of 1. The shape parameter of each distribution is set accordingly, and the location and scale parameters are set to 0 and 1. Then the mean m and standard deviation d is supplied by the distribution operators. The results are used to scale the distribution, which is shown and compared to other distributions.

In addition, for some distributions with shape parameter, this parameter is used as a free parameter to find the best approximation to the ExtremeValue distribution. The used skewness and kurtosis operators are displayed.

Keywords:
Probability Distribution, Extreme Value Distribution Approximation, Distribution Operators, 2D Chart



