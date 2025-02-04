Zip Library
===================

.. See also git: https://gitlab.aimms.com/aimms/customer-support/toolkit/zip-library

Introduction
-------------

This toolkit library is used to facilitate compressing folders to files and reverting.

Instructions
--------------

This chapter is divided into three sections:

#.  Adding the Library

#.  Main Procedures

#.  Example of Usage


Adding the library
~~~~~~~~~~~~~~~~

To add and use this library to your project, first download the code and after, 
follow these steps on `how to add an existing library to a project <https://how-to.aimms.com/Articles/84/84-using-libraries.html#add-aimms-libraries>`_.

Main Procedures 
~~~~~~~~~~~~~~~~

.. aimms:procedure:: zip::pr_zipFolderToFile(sp_folderName,sp_destinationFile)

    *   ``sp_folderName`` The folder name to compress.

    *   ``sp_destinationFile`` The file name to store the archive into.

.. aimms:procedure:: zip::pr_unzipFileToFolder(sp_fileName,sp_destinationFolderName)

    *   ``sp_fileName`` The file that contains the archive.

    *   ``sp_destinationFolderName`` The folder to store the uncompressed contents.

Minimal Requirements
----------------------

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example on Windows.  

To use the Linux version, you will need a License for using on Linux, or allowed to deploy on AIMMS Cloud.

Release Notes
--------------

Earlier versions of these zip commands were available via :doc:`previous article<../580/580-download-compressed-files>`


.. spelling:word-list:: 

    proc
    func



