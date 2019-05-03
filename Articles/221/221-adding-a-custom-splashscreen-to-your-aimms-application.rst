Adding a custom splash screen to your AIMMS application
=============================================================

.. meta::
   :description: How to interrupt a long running assignment.
   :keywords: interrupt, long running 

.. note::

	This article was originally posted to the AIMMS Tech Blog.

..       <link>https://berthier.design/aimmsbackuptech/2012/04/04/adding-a-custom-splashscreen-to-your-aimms-application/</link>
..       <pubDate>Wed, 04 Apr 2012 12:03:19 +0000</pubDate>
               
After you are finished with creating your AIMMS application, you can deploy it to the people who will use your application. 
Instead of using the standard AIMMS splash screen displayed below, 

.. image:: images/Default-AIMMS-Splashscreen-300x195.png

you have the option to provide your own custom splash screen.

Please note that the splash screen depicted is the splash screen of the developer version of AIMMS. 

.. image:: images/GateAssignment.bmp

You can instruct AIMMS to not display the standard splash screen, but a custom one by creating a BMP file with the same name as the project file (i.e. the ``.aimms`` file). 
If you open the Gate Assignment example from the index of all examples, you will see that splash screen shown above.

The name of the ``.aimms`` file of the gate assignment project is ``Gate Assignment.aimms``. 
By saving the above picture as ``Gate Assignment.bmp`` in the same folder as the ``Gate Assignment.aimms`` file, AIMMS will now display the new splash screen. 
Please note that you must use the bitmap (BMP) format, as AIMMS will only look for this extension (and not other image file formats).


.. include:: /includes/form.def


