Generate Random Numbers
================================
.. meta::
	:description: How to generate a sequence of random numbers from scratch.
	:keywords: random, multidimensional, uniform, distribution

AIMMS has a number of distribution functions included, in order for you to be able to generate random numbers very efficiently. 
This capability might be extremely useful when you want to quickly populate a multidimensional parameter. 
This functonality was used to randomize input values for the `Knapsack example <https://how-to.aimms.com/Articles/390/390-knapsack-problem.html>`_, example that we will be using as an example here. 

.. seealso::
	You can find the complete `list of functions available <https://documentation.aimms.com/functionreference/elementary-computational-operations/distribution-and-combinatoric-functions/index.html>`_. 
	A few of them are:
		- `Normal Distribution <https://en.wikipedia.org/wiki/Log-normal_distribution>`_
		- `Log Normal Distribution <https://en.wikipedia.org/wiki/Log-normal_distribution>`_
		- `Uniform <https://en.wikipedia.org/wiki/Continuous_uniform_distribution>`_

Example
-------

On `Knapsack example <https://how-to.aimms.com/Articles/390/390-knapsack-problem.html>`_ 
all input parameters are over ``s_items`` set. And this set is defined by ``p_itemQuantity`` as shown:

.. code-block:: aimms 

	Set s_items {
		Index: i_item;
		Parameter: ep_selectedItem;
		Definition: ElementRange(1, p_itemQuantity, prefix:"item-", fill:1);
	}
			
``p_itemQuantity`` can be changed to any integer value from the example IDE, starting as 14 default. 
For this model, we would like to generate numbers for 3 parameters, so it would be from 3 generated values (for 1 item) to up to 42 values (for 14 items) or even more. 

To populate this data, the following procedure was created:

.. aimms:procedure:: pr_randomizeData

.. code-block:: aimms 
	:lineos::

	empty p_itemValue, p_itemWeight, p_itemBound;

	p_itemValue(i_item) := uniform(0, 200)*1[$];
	p_itemWeight(i_item) := uniform(0[lb], p_maxWeightKnapsack/3);
	p_itemBound(i_item) := ceil(uniform(0, 10));


- where ``p_maxWeightKnapsack`` is the maximum weight of the knapsack. 

If we would plot our parameter distribution we would end up with a uniform distribution. 
 

    
