:orphan:

.. include:: /includes/icons.def


Generate random numbers
================================
.. meta::
	:description: How to generate a sequence of random numbers from scratch.
	:keywords: random, multidimensional,

|aimms| has a number of distribution functions included, in order for you to be able to generate random numbers very efficiently. This capability might be extremely useful when you want to quickly populate a multidimensional parameter.




Say I declare a parameter |par| called ``Traffic``, over 2 |index| indices from the same set |set| ``Locations`` with a size of 200 elements. That's pretty much ``200 * 200 = 40 000`` numbers that you want to generate roughly between 0 and 200 vehicles.

Let's create a set |set| ``Locations`` in AIMMS, specify 2 indices ``l1`` and ``l2``, and define it as ``elementrange(1,201,1,"Location-")``. 

 .. image:: images/Locations.png
			:scale: 50 %
			
Then let's create the new parameter that we want to populate with data, over those 2 indices. To do so, just index this parameter over ``l1`` and ``l2``, and put ``uniform(0,200)`` in his definition. Check the data of this parameter ( **CTRL + D** ), and see the result:

 .. image:: images/Data.png
			:scale: 40 %


If we would plot our parameter distribution we would end up with a uniform distribution. 
 
.. seealso::
    
    |aimms| proposes a lot of other different distributions, listed `here <http://images.aimms.com/aimms/download/manuals/aimms3fr_distributionandcombinatoric.pdf>`_

	* |doc| http://images.aimms.com/aimms/download/manuals/aimms3fr_distributionandcombinatoric.pdf 
    
    See the AIMMS example project :download:`GenerateRandomNumbers.zip <downloads/GenerateRandomNumbers.zip>`.
	
   
.. topic:: Normal Distribution

	:math:`f(x)={\tfrac  {1}{\sigma {\sqrt  {2\pi }}}}\;\;{\mathrm  {e}}^{{-{\frac  {1}{2}}\left({\frac  {x-\mu }{\sigma }}\right)^{2}}}`
	
	https://en.wikipedia.org/wiki/Log-normal_distribution
	
.. topic:: Log Normal Distribution

	:math:`{\displaystyle f(x;\mu ,\sigma )={\frac {1}{x\sigma {\sqrt {2\pi }}}}\exp \left(-{\frac {(\ln x-\mu )^{2}}{2\sigma ^{2}}}\right)={\frac {1}{x}}f_{X}(\ln(x);\mu ,\sigma )}`
	
	https://en.wikipedia.org/wiki/Log-normal_distribution
	


    


    
