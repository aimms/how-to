.. include:: /includes/icons.def


Generate random numbers
================================
.. meta::
	:description: How to generate a sequence of random numbers from scratch.
	:keywords: random, multidimensional,

|aimms| has a number of distribution functions included, in order for you to be able to generate random numbers very efficiently. 
This capability might be extremely useful when you want to quickly populate a multidimensional parameter.

Knapsack example uses this 

Say I declare a parameter |par| called ``Traffic``, over 2 |index| indices from the same set |set| ``Locations`` with a size of 200 elements. That's pretty much ``200 * 200 = 40 000`` numbers that you want to generate roughly between 0 and 200 vehicles.

Let's create a set |set| ``Locations`` in AIMMS, specify 2 indices ``l1`` and ``l2``, and define it as ``elementrange(1,201,1,"Location-")``. 

.. code-block:: aimms 

	Set s_items {
		Index: i_item;
		Parameter: ep_selectedItem;
		Definition: ElementRange(1, p_itemQuantity, prefix:"item-", fill:1);
	}
			
Then let's create the new parameter that we want to populate with data, over those 2 indices. To do so, just index this parameter over ``l1`` and ``l2``, and put ``uniform(0,200)`` in his definition. Check the data of this parameter ( **CTRL + D** ), and see the result:

.. aimms:procedure:: pr_randomizeData

.. code-block:: aimms 
	:lineos::

	empty p_itemValue, p_itemWeight, p_itemBound, p_itemRangeMax;

	p_itemValue(i_item) := uniform(0, 200)*1[$];
	p_itemWeight(i_item) := uniform(0[lb], p_maxWeightKnapsack/3);
	p_itemBound(i_item) := ceil(uniform(0, 10));

	!not random
	p_itemRangeMax(i_item) := 1;
	sp_itemDescription(i_item) := i_item;


If we would plot our parameter distribution we would end up with a uniform distribution. 
 
.. seealso::
    
    AIMMS proposes a lot of other different distributions, listed below:

	* |doc| https://documentation.aimms.com/functionreference/elementary-computational-operations/distribution-and-combinatoric-functions/index.html 
    

    


    
