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

   See :download:`↓ this example AIMMS project <_AIMMSProjects/GenerateRandomNumbers.zip>`.

_______________________________________________________________________________


.. raw:: html

	<style>
	.whatever input[type=text], select {
		width: 100%;
		padding: 12px 20px;
		margin: 8px 0;
		display: inline-block;
		border: 1px solid #ccc;
		border-radius: 4px;
		box-sizing: border-box;
	}
	.whatever input[type=email], select {
		width: 100%;
		padding: 12px 20px;
		margin: 8px 0;
		display: inline-block;
		border: 1px solid #ccc;
		border-radius: 4px;
		box-sizing: border-box;
	}
	.whatever textarea {
		width: 100%;

	}
	
	.whatever input[type=submit] {
		width: 100%;
		background-color: #4CAF50;
		color: white;
		padding: 14px 20px;
		margin: 8px 0;
		border: none;
		border-radius: 4px;
		cursor: pointer;
	}

	.whatever input[type=submit]:hover {
		background-color: #45a049;
	}

	div .whatever {
		border-radius: 5px;
		background-color: #f2f2f2;
		padding: 20px;
	}
	</style>
	<div class="whatever">
		<h1> Is your question answered ? Please reach out to us</h1>
		<form action="https://formspree.io/howto@aimms.com" method="POST">
			<label>Your Email</label>
			<input type="email" name="_replyto" size="50" placeholder="you@yourorganization.com" required>
			<label>Your whishlist to this documentation</label>
			<textarea name="MessageText" cols="40" rows="5" size="50" placeholder="Type your message here..."></textarea>
			<input type="checkbox" name="WebUI" value="Yes"> I use the AIMMS WebUI<br>
			<input type="checkbox" name="WinUI" value="Yes"> I use the AIMMS WinUI<br>
			<input type="submit" value="Send">
			<input type="hidden" name="_next" value="https://how-to.aimms.com/FormThanksPage.html" />
			<input type="hidden" name="_cc" value="arthur.dherbemont@aimms.com" />

		</form>
	</div>