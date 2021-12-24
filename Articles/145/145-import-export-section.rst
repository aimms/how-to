Export Code to Another Project
==================================

.. meta::
   :description: How to reuse parts of your code in another AIMMS model.
   :keywords: import, export, link, share, reuse, re-use

      .. note::

	This article was originally posted to the AIMMS Tech Blog.

.. sidebar:: Re-using AIMMS source code.

    .. image:: images/code.png
    		    	:align: center


Sometimes there are parts of a model that you would like to re-use in another AIMMS model. 
If it is a very generic component, you could choose to create an AIMMS library or an AIMMS module out of it. 
Please see :doc:`creating-and-managing-a-model/the-model-explorer/creating-and-managing-models`. 
In the cases that you only want to quickly export/import a set of identifiers once, you can also use the export/import functionality in AIMMS.

Also, on this blog we will provide the AIMMS code where applicable as ``.ams`` files. You  can import these into your existing projects with the instructions found below. In some cases where the whole project is needed (and not only some snippets), we will provide the whole project as an a .zip file.

Exporting a section
-------------------

Before you start exporting, you should place all the identifiers you wish to export from your current model into one section in your AIMMS model. When you have done this, there are two possible ways of exporting this section:

* Select the section in the model tree and select Export... in the edit menu
* Use the source attribute of the section identifier to store the section in a separate file

The first option has the advantage that it requires less steps.
However, the second approach has the advantage that you can share code among projects.

If you do not perform the last two steps, all identifiers declared in the section will not be stored in the main project file anymore, but separately in the .aim or ``.ams`` file that you selected.

To export a copy of a section to a ``.ams`` file, just highlight the section in the model editor and then, via the AIMMS Menu, select export.

To make a section available for sharing among projects, please use the following steps:

* Open attribute window of the section you wish to export
* Select wizard button of the source file attribute
* Select Write in the menu
* Select the location you want to store the ``.ams`` file
* Set the filename to what you want and press Save



Importing a section
--------------------

To import an ``.ams`` file, please use the following steps:

* Create a new section in your model that will hold the identifiers that will be imported
* Select this section identifier in the model tree
* In the Edit menu, select Import...
* Select the ``.ams`` file that you want to import

After this, AIMMS will present you with a dialog that shows which identifiers will be imported and which ones are conflicting with already existing identifiers in your model.

.. note::

    When using the source file attribute of a section, you can store the contents of the section in a separate file (if you do not use the "Remove (keep subtree)..."). Please note that if you use an ``.ams`` file for the source file attribute, changes that are made to this text file with any other program (e.g. a text editor or version management system) while the AIMMS project is open will not be picked up by AIMMS! Only after you close and re-open the project, will these changes be visible in your project.





