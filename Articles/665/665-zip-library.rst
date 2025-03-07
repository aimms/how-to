Zip Library
===================

.. See also git: https://gitlab.aimms.com/aimms/customer-support/toolkit/zip-library

.. meta::
   :description: How to measure efficiency of procedures with StopWatch function.
   :keywords: efficient, time, execute, stopwatch, watch, clock

.. image:: https://img.shields.io/badge/AIMMS_4.87-ZIP:_Stopwatch_Library-blue
   :target: https://github.com/aimms/stopwatch-library/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_4.87-Github:_Stopwatch_Library-blue
   :target: https://github.com/aimms/stopwatch-library

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-developer-12/stopwatch-library-1426

.. image:: images/zip-library.png
   :scale: 30
   :align: right
   :alt: Measure Execution Time

Introduction
-------------

This toolkit library is used to facilitate compressing folders to files and reverting.

Instructions
--------------

This chapter is divided into three sections:

#.  Adding the Library

#.  Main Procedures

#.  Example of Usage


Adding the Library
~~~~~~~~~~~~~~~~~~~~~~

To add and use this library to your project, first download the code and after, 
follow these steps on `how to add an existing library to a project <https://how-to.aimms.com/Articles/84/84-using-libraries.html#add-aimms-libraries>`_.

Main Procedures 
~~~~~~~~~~~~~~~~

.. aimms:procedure:: zip::pr_zipFolderToFile(sp_folderName,sp_destinationFile)
    
    Parses :token:`mappingFile` to create a mapping called :token:`mappingName`. The function will return 1 on success, or 0 on failure.

    :param mappingName: the name of the mapping to be created
    :param mappingFile: the relative path to the mapping file to be parsed.

    *   ``sp_folderName`` The folder name to compress.
    *   ``sp_destinationFile`` The file name to store the archive into.

.. aimms:procedure:: zip::pr_unzipFileToFolder(sp_fileName,sp_destinationFolderName)

    *   ``sp_fileName`` The file that contains the archive.

    *   ``sp_destinationFolderName`` The folder to store the uncompressed contents.

Minimal Requirements
----------------------

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example on Windows.  
To use the Linux version, you will need an AIMMS License for using on Linux, or allowed to deploy on AIMMS Cloud.

Release Notes
--------------

`v1.0 <https://github.com/aimms/stopwatch-library/releases/tag/1.3>`_ (07/03/2025)
   Added ``fnc_now``, ``pr_scheduleOver``, and the stopwatch optional argument to ``pr_start``, and ``pr_elapsed``.


Earlier versions of zip Library is available via :doc:`previous article<../580/580-download-compressed-files>`.


.. spelling:word-list:: 

    proc
    func



