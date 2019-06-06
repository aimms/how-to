Share an AIMMS Project
========================

.. meta::
   :keywords:
   :description: Overview of files included in the project folder and how to share your AIMMS project with others, such as AIMMS developers or the AIMMS Support Team.


This article gives you an overview of the folder structure of an AIMMS project, and how to share your project with other developers.


AIMMS project folder structure
----------------------------------

When you create a new AIMMS project, multiple folders and files are initialized. The project folder, or the root folder will have the name you specified (*Demo* in the below example). 

.. image:: images/new-project-folders.png
   :align: center

The below three files are always created and necessary to open an AIMMS project. 

#. ``Demo.aimms`` : this is an executable (if the AIMMS Launcher is installed) file which launches the AIMMS Developer IDE. 
#. ``Demo.ams`` : the model source file which contains all the identifier declarations created in an AIMMS project. Version control systems track this file to track changes made in your model. 
#. ``Project.xml`` : contains information about the version of the AIMMS project and links the ``Demo.aimms`` file to the ``Demo.ams`` file. 

.. code-block:: xml
   :linenos:

   <?xml version="1.0"?>
   <Project AimmsVersion="4.63.2.5 unicode x64" ProjectUUID="D3D989F2-FB95-4F29-98E6-A56D9DD245A7">
      <ModelFileName>Demo.ams</ModelFileName>
      <AutoSaveAndBackup>
         <DataBackup AtRegularInterval="true" EveryNMinutes="15" NumBackupsDatedToday="3" NumDaysBeforeToday="3" />
      </AutoSaveAndBackup>
   </Project>

So, the ``<ModelFileName>`` in line #3 of the ``Project.xml`` file should always be same as the name of the ``.ams`` file, both of which are inside the MainProject folder. When you click on ``Demo.aimms`` file, it will load the ``Demo.ams`` file into the IDE as specified in the ``Project.xml`` file. 

If any libraries are added to the project, additional files and folders will be created in the root folder or inside the MainProject folder. 

Sharing a project
--------------------
To share your project with other developers, you need to zip the entire project folder (not just the ``.aimms`` file). 

Right-click on the project folder and go to *Send to > Compressed (zipped) folder*. 

The resulting ZIP file will contain all of the project files in a more portable format.

.. note::

   If you have imported data from external sources, such as Excel files or databases, it is also helpful to share a data case file in your project.

   To save a data case go to *Data > Save Case as*.

   For details see **AIMMS Documentation**: `Case Management <https://download.aimms.com/aimms/download/manuals/AIMMS3UG_CaseManagement.pdf>`_


Related Topics
----------------

* **AIMMS Documentation**: `Getting Started <https://download.aimms.com/aimms/download/manuals/AIMMS3UG_GettingStarted.pdf>`_

* **AIMMS Knowledge**: :doc:`../151/151-version-control-aimmspack-backup`

* **AIMMS Knowledge**: :doc:`../145/145-import-export-section`

* **AIMMS Knowledge**: :doc:`../95/95-change-default-ui`




