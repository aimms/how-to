:orphan:

.. include:: includes/icons.def

:abbr:`LIFO (last-in, first-out)`


How to generate random numbers ?
================================

|aimms| has a number of distribution functions included, in order for you to be able to generate random numbers very efficiently. This capability might be extremely useful when you want to quickly populate a multidimensoionnal parameter.

1/ Generate a sequence of random numbers from scratch
------------------------------------------------------

 Say I declare a parameter |par| called "Traffic", over 2 |index| indices from the same set |set| "Locations" with a size of 200 elements. That's pretty much ``200*200 = 40.000 numbers`` that you want to generate roughly between 0 and 200 vehicules.

 Let's create a set |set| "Locations" in AIMMS, specify 2 indices "l1" and "l2", and define it as ``elementrange(1,201,1,"Location-")``. 

 .. image:: Images/Locations.png
			:scale: 50 %
			
 Then let's create the new parameter that we want to populate with data, over those 2 indices. To do so, just index this parameter over `l1` and `l2`, and put ``uniform(0,200)`` in his definition. Check the data of this parameter ( **CTRL + D** ), and see the result:

 .. image:: Images/Data.png
			:scale: 40 %


 That's good. But if we would plot our parameter distribution we would end up with a uniform distribution. 
 
 |aimms| propose a lot of other different distributions. Help yourself!


.. sidebar:: Normal and Log Normal Distributions

		.. image:: http://mathworld.wolfram.com/images/eps-gif/NormalDistribution_651.gif
		.. image:: http://mathworld.wolfram.com/images/eps-gif/LogNormalDistribution_800.gif
	
   
.. topic:: Normal Distribution

	:math:`f(x)={\tfrac  {1}{\sigma {\sqrt  {2\pi }}}}\;\;{\mathrm  {e}}^{{-{\frac  {1}{2}}\left({\frac  {x-\mu }{\sigma }}\right)^{2}}}`
	
	https://en.wikipedia.org/wiki/Log-normal_distribution
	
.. topic:: Log Normal Distribution

	:math:`{\displaystyle f(x;\mu ,\sigma )={\frac {1}{x\sigma {\sqrt {2\pi }}}}\exp \left(-{\frac {(\ln x-\mu )^{2}}{2\sigma ^{2}}}\right)={\frac {1}{x}}f_{X}(\ln(x);\mu ,\sigma )}`
	
	https://en.wikipedia.org/wiki/Log-normal_distribution
	
.. seealso::

   
	* |doc| http://images.aimms.com/aimms/download/manuals/aimms3fr_distributionandcombinatoric.pdf 

.. only:: builder_html

   See :download:`this example AIMMS project <Resources/Other/GenerateRandomNumbers/GenerateRandomNumbers.zip>`.

.. note::
	
	See me, feel me
   
.. todo::
  
   Test

.. raw:: html

	<p>An <dfn title="onomasticon"><button disabled class="dfn-tooltip"><p>Another word for <strong>thesaurus</strong></p><p><img src="http://i.imgur.com/G0bl4k7.png/"></p></button></dfn> is not a dinosaur.</p>

.. comment:
	.. productionlist::

	   try_stmt: try1_stmt | try2_stmt
	   try1_stmt: "try" ":" `suite`
	            : ("except" [`expression` ["," `target`]] ":" `suite`)+
	            : ["else" ":" `suite`]
	            : ["finally" ":" `suite`]
	   try2_stmt: "try" ":" `suite`
	            : "finally" ":" `suite`

:menuselection:`Start --> Programs --> Execute`

.. code-block:: aimms
    
    P_demand :=$ 

:samp:`{variable = 2} print 1+{variable}`

.. literalinclude:: ./Resources/Modelling/CSVFiles/Downloads/CSVInterface/MainProject/CSVInterface.ams
    :caption: CSVInterface.ams
    :name: CSVInterface.ams
    
.. include:: includes/form.def