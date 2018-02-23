.. |doc| image:: Images/icons/Documentation.png
.. |aimmsIcon| image:: Images/icons/favicon.png
			:scale: 15 %

.. |aimms| image:: Images/aimms-logo-s-rgb.png
				:scale: 10 %
				:target: https://aimms.com/
.. |set| image:: Images/icons/set.png
.. |par| image:: Images/icons/parameter.png
.. |var| image:: Images/icons/variable.png
.. |cons| image:: Images/icons/constraint.png
.. |index| image:: Images/icons/index.png
.. |sp| image:: Images/icons/StringParameter.png

				
				
.. This text will not be shown 				
	.. figure:: Images/aimms-logo-s-rgb.png
					:scale: 70 %
					:align: center
					:target: https://aimms.com/

How do I generate random numbers ?
=====================================

|aimms| has a number of distribution functions included, in order for you to be able to generate random numbers very efficiently. This capability might be extremely useful when you want to quickly populate a multidimensoionnal parameter.

1/ Generate a sequence of random numbers from scratch
------------------------------------------------------

 Say I declare a parameter |par| called "Traffic", over 2 |index| indices from the same set |set| "Locations" with a size of 200 elements. That's pretty much ``200*200 = 40.000 numbers`` that you want to generate roughly between 0 and 200 vehicules.

 Alright, let's create a set |set| "Locations" in AIMMS, specify 2 indices "l1" and "l2", and define it as ``elementrange(1,201,1,"Location-")``. 

 .. image:: Images/Locations.png
			:scale: 50 %
			
 Then let's create the new parameter that we want to populate with data, over those 2 indices. To do so, just index this parameter over `l1` and `l2`, and put ``uniform(0,200)`` in his definition. Check the data of this parameter ( **CTRL + D** ), and see the result:

 .. image:: Images/Data.png
			:scale: 40 %

 That's good. But if we would plot our parameter distribution we would end up with a uniform distribution. |aimms| propose a lot of other different distributions:


.. sidebar:: Normal and Log Normal Distributions
		
		.. image:: http://mathworld.wolfram.com/images/eps-gif/NormalDistribution_651.gif
		.. image:: http://mathworld.wolfram.com/images/eps-gif/LogNormalDistribution_800.gif
	
   
.. topic:: Normal Distribution

	:math:`f(x)={\tfrac  {1}{\sigma {\sqrt  {2\pi }}}}\;\;{\mathrm  {e}}^{{-{\frac  {1}{2}}\left({\frac  {x-\mu }{\sigma }}\right)^{2}}}`
	
	https://en.wikipedia.org/wiki/Log-normal_distribution
	
.. topic:: Log Normal Distribution

	:math:`{\displaystyle f(x;\mu ,\sigma )={\frac {1}{x\sigma {\sqrt {2\pi }}}}\exp \left(-{\frac {(\ln x-\mu )^{2}}{2\sigma ^{2}}}\right)={\frac {1}{x}}f_{X}(\ln(x);\mu ,\sigma )}`
	
	https://en.wikipedia.org/wiki/Log-normal_distribution
	
This is :red:`red !` And :blue:`this part is blue`.

Code example (javascript):

.. code-block:: javascript

   // factory.js
	AWF.Bus.subscribe({
		onCollectTypes: function(collectedTypes, contextElQ) {
			if(!contextElQ || contextElQ.awf.tags("placeable-widget-container")) {
				collectedTypes.push("my-widget2");
			}
		},

		onInitializeTags: function(elQ, type) {
			if (type === "my-widget2") {
				elQ.awf.tags(["placeable"], 'add');
			}
		},

		onDecorateElement: function(elQ, type) {
			if(type === "my-widget2") {
				elQ.aimms_my_widget2();
			}
		},

		onInitializeOptionTypes: function(elQ, type) {
			if (type === "my-widget2") {
				elQ.awf.optionTypes("contents", AWF.OptionUtil.createOptionType("string"));
			}
		},
	});

   
.. warning::

	Chris, isn't it wonderful ?
	
.. seealso::

   
	* |doc| http://images.aimms.com/aimms/download/manuals/aimms3fr_distributionandcombinatoric.pdf 
	* |aimms| htt

.. note::

		You can also develop your own |aimmsIcon| color box of course, with css... once again, but now we are prepared :)

