How to interface with CSV files?
=================================
.. Edit the title of your article
.. No need to extend/delete the = in the next line

Introduction
------------
The introduction / background of your answer. Try to limit this block to a single paragraph of max 4-5 lines. 

Solution
--------

Enter the answer to your article question here. Depending on the topic, this could be multiple paragraphs but try to be precise. 

To insert images, use the following code.

.. image:: Resources/ArticleName/Images/aimmslogo.PNG
	:align: center

To include an AIMMS procedural code, update the following code snippet with your AIMMS code. 

.. code-block:: none

		if not ProjectDeveloperMode() then
			if pro::DelegateToServer( waitForCompletion  :  1,
				completionCallback :  'pro::session::LoadResultsCallBack' )  
			then return 1;
			endif ;
		endif ;

		prDoSolve();

Copy paste the code block section and replace the code if you need multiple code sections.

.. Duplicate the Template folder inside in the Resources folder and give an appropriate name related to your article. Place any images or ZIP files inside the Images and Downloads folders. Take care to not have any spaces in between your folder and file names. 

To include a download link, use the following code snippet. 

Click here to download the :download:`AIMMS Language Reference Document <Resources/ArticleName/Downloads/AIMMS3_LR.pdf>`
 
.. :download: triggers the download link and the `text inside` the reverse quotes show up on the page, and the file path to be downloaded is written inside the <>

To insert a hyperlink to another page, use the following code snippet. 

Click here to download `the latest AIMMS version  <https://aimms.com/english/developers/downloads/download-aimms/>`_

.. The _ at the end is very important, it triggers the hyperlink syntax. 

Use Cases
---------

Include any example project for explanation purposes. 

This is only a general guide for your reference, and you are not required to stick to the exact same order of sections. If appropriate, have the Use Cases section before the solution. Go with your intuition. 

.. include:: includes/form.def

.. do not delete this last line

If you want to make an image a hyperlink, use the following code snippet. 

.. ImageLink is just a variable name, use any name you would want. You are creating a variable, and linking it with an image and a web address in the next two lines. 

|ImageLink|_

.. |ImageLink| image:: Resources/ArticleName/Images/aimmslogo.PNG

.. _ImageLink: https://aimms.com/


.. I made some changes to the templates to test the proposed workflow. 







