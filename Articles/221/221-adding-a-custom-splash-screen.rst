Add a Custom Startup Image in WinUI
=============================================================

.. meta::
   :description: How to include a custom loading screen for your AIMMS application.
   :keywords: splash screen, load, startup, winui

.. note::

	This article was originally posted to the AIMMS Tech Blog.

..       <link>https://berthier.design/aimmsbackuptech/2012/04/04/adding-a-custom-splashscreen-to-your-aimms-application/</link>
..       <pubDate>Wed, 04 Apr 2012 12:03:19 +0000</pubDate>
               
When you deploy your application, AIMMS uses a standard splash screen at startup as shown below. You have the option to customize this startup image for WinUI apps. 

.. image:: images/aimms-default-splash-screen.png
   :align: center

To use a custom startup image, create a BMP file with the same name as the ``.aimms`` project file, and save it in the project folder.

Please note that you must use the bitmap (BMP) format, as AIMMS will only look for this extension (and not other image file formats).

Example
--------

A custom startup image is used in the :doc:`Gate Assignment example project <../389/389-gate-assignment>`:

.. image:: images/GateAssignment.bmp
   :align: center

The name of the ``.aimms`` file of the gate assignment project is ``Gate Assignment.aimms``. By saving the picture from the example as ``Gate Assignment.bmp`` in the same folder as the ``Gate Assignment.aimms`` file, AIMMS displays the new splash screen. 







