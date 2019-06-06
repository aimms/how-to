Standardize Random Data in Distribution Functions
=================================================

.. meta::
   :description: How create a reproducible random sequence in distribution functions.
   :keywords: seed, random, distribution, uniform, binomial, normal

.. note::

	This article was originally posted to the AIMMS Tech Blog.

.. sidebar:: Repeating seemingly random patterns

    .. image:: images/Random-Number-generator.png
    		:align: center

The other day I got a model from a user and every time that I solved the model the results were different. At first I thought that some of the outputs were used as input, but that wasn't the case. After some analysis I found out that this statement was the culprit: 

.. code-block:: aimms

    ShippingDuration(o,d) := uniform(22,34); 

The user was prototyping and did not have all the data yet. In reality, you will use the known (average) durations.

The function "uniform", but also functions like "Binomial", and "Normal", generate numbers in a random sequence and every time that you execute this statement the produced numbers will be different. On the one hand that is nice, as you want random numbers. On the other hand, it makes debugging very hard because the result will be different every time.  

As the title suggests, there is a solution for this problem. The AIMMS option "seed" determines the sequence of the random numbers that AIMMS generates. So, by adding this statement before the call to "uniform", or any other distribution function, the random number sequence will be the same every time:  

.. code-block:: aimms

    Option seed = 122113; 

(You can freely pick any number here.) 

Of course, I mentioned this to the user and he said that he had set the value of this option in the option dialog, but that it had no effect. The problem with that approach is that it only determines the random numbers that are generated from the beginning. It does not mean that the numbers will be the same every time this function is called. 

If your model contains multiple calls to these functions, you will need to have this "Option Seed" statement only before the very first call and not all the other calls. 





