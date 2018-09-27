Add a library to your project help
===========================

Conditions
----------

Overview
--------

This article describes:

* How to add a new library to your project
* What library types are available
* Best practices for setting up libraries

Definition
----------

In the context of AIMMS,

Benefit:
library projects divide a large Aimms project into smaller sub-projects
useful for sharing parts of an application between multiple projects
enabling multiple developers to work in a single project

Prefixes:
a default library prefix is assigned When you create a new library project 
the prefix has to be used to access the library objects
you can view and edit a library's prefix in the library manager
.. How is a library project different from project calling on libraries?
.. What does this look like?

Pages:
Pages defined in a library can access the library’s private identifiers
paged defined outside of the library only have access to identifiers in the interface of the library.
Pages outside of the library are not affected by changes to the library’s internal implementation
.. what are pages in this context?

Procedure
---------

To add a new libary:

#. Within a project, go to File > Library manager
#. Select which library to add

Library types available:

* New library
* System library
* 

Using the library manager Aimms allows you to
* create new libraries,
* add existing libraries to your project,
* edit library properties, and
* remove libraries from your project.

Each library project in Aimms contains:
* the Project.xml file holding a reference to the project’s main model source
file (with an .ams extension), and additional model source files containing all relevant identifier declarations, procedures and functions
* PageManager.xml, TemplateManager.xml and MenuBuilder.xml files describing the page, template and menu tree defined in the project, with pages and templates being stored in subfolders,
* Settings and Tools subfolders containing the saved settings for appearance and tools in the Aimms IDE, and
* User Files folder for storing all user files within the project.

.. END DOCUMENT