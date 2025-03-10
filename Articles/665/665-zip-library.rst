Zip Library
===================

.. See also git: https://gitlab.aimms.com/aimms/customer-support/toolkit/zip-library

.. meta::
   :description: This toolkit library is used to facilitate compressing folders to files and reverting.
   :keywords: zip, unzip, compress, files, revert

.. image:: https://img.shields.io/badge/AIMMS_24.6-ZIP:_Zip_Library-blue
   :target: https://github.com/aimms/zip-library/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.6-Github:_Zip_Library-blue
   :target: https://github.com/aimms/zip-library

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/
   
.. image:: images/zip-library.png
   :scale: 30
   :align: right

Introduction
-------------

This toolkit library is used to facilitate compressing folders to files and reverting.

Instructions
--------------

This chapter is divided into three sections:

#.  Adding the Library
#.  Main Procedures


Adding the Library
~~~~~~~~~~~~~~~~~~~~~~

To add and use this library to your project, first download the code and after, 
follow these steps on `how to add an existing library to a project <https://how-to.aimms.com/Articles/84/84-using-libraries.html#add-aimms-libraries>`_.

Main Procedures 
~~~~~~~~~~~~~~~~

.. aimms:procedure:: zip::pr_zipFolderToFile(sp_folderName,sp_destinationFile)
    
    :param sp_folderName: the folder name to compress.
    :param sp_destinationFile: the file name to store the archive into.

.. aimms:procedure:: zip::pr_unzipFileToFolder(sp_fileName,sp_destinationFolderName)

    :param sp_fileName: the file that contains the archive.
    :param sp_destinationFolderName: the folder to store the uncompressed contents.

Minimal Requirements
----------------------

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example on Windows.  
To use the Linux version, you will need an AIMMS License for using on Linux, or allowed to deploy on AIMMS Cloud.

Release Notes
--------------

`v1.0 <https://github.com/aimms/zip-library/releases/tag/1.0>`_ (07/03/2025)
   First official release as a toolkit library.

Earlier versions of zip Library is available via :doc:`previous article<../580/580-download-compressed-files>`.


.. spelling:word-list:: 

   proc
   func



