Share an AIMMS Project
======================

.. meta::
   :keywords:
   :description: Overview of files included in the project folder and how to share your AIMMS project with others, such as AIMMS developers or the AIMMS Support Team.


This article gives you an overview of what is contained in your project folder, and how to share your project with other developers.


Overview of project files
--------------------------
An AIMMS project consists of multiple folders and files.

.. image:: images/new-project-folders.png

Every AIMMS application initially consists of these components:

* ``.aimms`` project file contains references to the main application project and all library projects contained in your
application

* ``MainProject`` folder contains source files for the main project
   
   * ``.ams`` model source file contains identifier declarations, procedures and functions for the project
   * ``Project.xml`` contains references to the .ams file 

* ``WebUI`` library folder (if selected)

Additional files are created during development or deployment, such as library sources, settings, cases, logs, and others. These are also in the project folder.

Sharing a project
------------------
To share your project with other developers, you need to zip the entire project folder (not just the ``.aimms`` file). 

.. need to add a quick procedure for that

It is also helpful to share a data case file if you have imported data from external sources, such as Excel files or databases.

To save a data case go to *Data > Save Case as*.

For details see **AIMMS Documentation**: `Case Management <https://download.aimms.com/aimms/download/manuals/AIMMS3UG_CaseManagement.pdf>`_


Related Topics
--------------

* **AIMMS Documentation**: `Getting Started <https://download.aimms.com/aimms/download/manuals/AIMMS3UG_GettingStarted.pdf>`_

* **AIMMS Knowledge**: :doc:`../151/151-version-control-aimmspack-backup`

* **AIMMS Knowledge**: :doc:`../145/145-import-export-section`