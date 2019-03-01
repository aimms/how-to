Share an AIMMS Project
======================

.. meta::
   :keywords:
   :description: Overview of files included in the project folder and how to share your AIMMS project with others, such as AIMMS developers or the AIMMS Support Team.


This article gives you an overview of what is contained in your project folder, and how to share your project with other developers.


AIMMS Project Folder Structure
----------------------------------
An AIMMS project consists of multiple folders and files.

.. image:: images/new-project-folders.png
   :align: center

When you create a new AIMMS project, the below three files are always created. 

#. ``.aimms``: this is a clickable (if the AIMMS Launcher is installed) file which will launch the AIMMS Developer IDE. 
#. ``.ams``: the model source file which contains all the identifier declarations created in an AIMMS project. This is the file tracked by version control systems to keep track of changes in an AIMMS model. 
#. ``Project.xml``: contains information about the AIMMS version of your project and a reference to the ``.ams`` file. 

.. code-block:: xml
   :linenos:

   <?xml version="1.0"?>
   <Project AimmsVersion="4.63.2.5 unicode x64" ProjectUUID="D3D989F2-FB95-4F29-98E6-A56D9DD245A7">
      <ModelFileName>Demo.ams</ModelFileName>
      <AutoSaveAndBackup>
         <DataBackup AtRegularInterval="true" EveryNMinutes="15" NumBackupsDatedToday="3" NumDaysBeforeToday="3" />
      </AutoSaveAndBackup>
   </Project>

The ``.aimms`` and ``.ams`` take the name specified when a new project is created and the ``.aimms`` file will always look for the ``.ams`` file specified in the ``Project.xml`` file. So, the ``<ModelFileName>`` in line #3 of the ``Project.xml`` file should always be same as the name of the ``.ams`` file, both of which are inside the MainProject folder. 

If any libraries are added to the project, they will have an associated folder either in the project root folder or inside the MainProject folder. 

Sharing a Project
--------------------
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