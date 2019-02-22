Upload and Download Files
================================

.. meta::
   :description: Modeling for secure apps deployed on AIMMS PRO – Part 2: Uploading and Downloading files.
   :keywords: secure, upload, download

.. note::

	This article was originally posted to the AIMMS Tech Blog.

This blog post is the second post in a series of three to enable AIMMS app developers to model necessary file sharing in a secure manner. In our 
:doc:`../120/120-pro-user-groups`, we covered AIMMS PRO User groups and how they can be used to authorize access to information within your AIMMS Apps. This blog post explains how you can upload and download files to and from AIMMS PRO Storage.


Uploading and downloading files
-------------------------------

First, I’d like to explain what AIMMS PRO storage is. AIMMS PRO storage is a disk area managed by AIMMS PRO to share files within an AIMMS application and between AIMMS applications. This disk area is separate from the disk area in which AIMMS apps are actually executing. Among other things, it handles the cases created during various (solver) sessions, which are then shared between users of the same app.

Within AIMMS PRO storage, folders are referred to as *buckets* and files are referred to as *objects*. When you look at the AIMMS PRO API, as presented in the AIMMS PRO library, you will see the terms buckets and objects. In this blog post, I will use the terms folders and files.

AIMMS PRO storage has the following folder layout, and it is best practice to follow it:

.. image:: /images/Default-folder-layout-of-AIMMS-PRO-Storage.png

In this overview, user specific data is stored in ``/userdata``, and the user is identified first by his environment and then by his or her name.

Data that is shared among users is stored in ``/publicdata``. Cases that are intended to be shared by all users are stored in ``/publicdata``, and organized per app. If data is to be shared per group, it is to be stored in ``/publicdata/groupname`` and there are no further restrictions.

By following this layout, the default access rights are as expected. For instance, all files created within a group folder, unless specified otherwise, get the access rights of that group folder.

To detail the building blocks for the exchange of files between an AIMMS app and its environment, we need to distinguish two situations:

#. The AIMMS app is an AIMMS WinUI app. In this situation, the client side of the AIMMS app runs on the client device itself. The app has direct access to the user files via the file and directory functions such as FileCopy and FileDelete. For an overview of those and related functions, see AIMMS The Function Reference, Chapter “File and Directory Functions”. This function reference is available via the Help menu of AIMMS.

#. The AIMMS app is an AIMMS WebUI app. In this situation, the app user interacts with the app via a Chrome Web browser, but the AIMMS app actually runs on a machine in the AIMMS PRO cluster. The AIMMS app has only direct access to the following files:

    #.   Those files that were packed in the corresponding ``.aimmspack`` 

    #.   Those files that have been explicitly uploaded from the client computer that runs the Chrome Browser 

    #.   Those files that have been explicitly downloaded from AIMMS PRO storage 

In a way, the second situation can be viewed as an extension of the first situation. We will detail the first situation first.

Exchanging files for an AIMMS WinUI app
----------------------------------------

.. image:: /images/win-ui-file-exchange-e1479732007875.png

This is not so difficult. Just use the functions ``pro::SaveFileToCentralStroage`` and ``pro::RetrieveFileFromCentralStorage``. For instance, as follows:

.. code-block:: aimms

    Pro::SaveFileToCentralStorage("c:\\Inputs\\data.txt", "pro:/publicdata/myapp/input/data.txt" );

This function will copy the file data.txt from the folder ``c:\inputs`` on the client Windows Desktop to the folder ``/publicdata/myapp/input`` in the AIMMS PRO storage. Note that ``Pro::SaveFileToCentralStorage`` requires the filename to be present in the second argument. In addition, note that ``Pro::SaveFileToCentralStorage`` has a third optional argument to specify access rights for the file at hand on the AIMMS PRO storage system.  The default behavior is to use the access rights as indicated in the encompassing bucket.

In order to warn the end-user that a file in AIMMS PRO storage is about to be overwritten, you want to check the existence of the file first. You can do this via a self-defined procedure (proFileExists) as follows:

.. code-block:: aimms

    Procedure proFileExists {
        Arguments: (spStoragePath);
        Body: {
            pro::NormalizeStoragePath(spStoragePath);
            pro::SplitStoragePath(spStoragePath,spStorageBucketPath, spStorageFileWithoutPath);
            ret := pro::storage::GetObjectInfo(spStorageBucketPath,
                             spStorageFileWithoutPath, tmpLocalFileName,
                             tmpType, tmpNum, tmpAuth, tmpVersion );
            if ret = 0 or tmpNum <= 0 then
                return 0 ;
            endif ;
            return 1 ;
        }
        StringParameter spStoragePath {
            Property: Input;
        }
        StringParameter spStorageBucketPath;
        StringParameter spStorageFileWithoutPath;
        StringParameter tmpLocalFileName;
        StringParameter tmpType;
        Parameter tmpNum;
        StringParameter tmpAuth;
        Parameter tmpVersion;
        Parameter ret;
    }

If we continue exploring the "Central Storage" section of the PRO API in the library ``AimmProLibrary``, we see that there are also functions to create folders and to delete files and folders.

In this section, we’ve illustrated the use of the AIMMS PRO storage from within an AIMMS WinUI application. We build on this knowledge in the next section, where we illustrate how to use the AIMMS PRO storage in a WebUI application.

Exchanging files for an AIMMS WebUI app
--------------------------------------------------

In a manner of speaking, an AIMMS WebUI application is like an AIMMS WinUI application whereby the visualization and interaction is done on a separate device running Chrome, and the Client Side execution of AIMMS procedures is done on a machine in the AIMMS PRO cluster. This is illustrated in more detail in the picture below.

.. image:: /images/download-upload.png

The picture above illustrates the file communication architecture for a WebUI AIMMS application.

#.   The Chrome Browser handles the interaction with the end-user. As you know, the Chrome browser can run on a device such as a smartphone, tablet, laptop, or desktop.

#.   The AIMMS Client session running on a node in the AIMMS PRO Cluster. This AIMMS Client Session handles the execution of the statements in the model.

#.   The AIMMS PRO storage, disk space available to exchange files.

The AIMMS WebUI provides the `Upload Widget <https://documentation.aimms.com/webui/upload-widget.html?highlight=upload#upload-widget>`_ to transfer files from your device to the folder in which the AIMMS client session runs. In addition, it provides the `Download Widget <https://documentation.aimms.com/webui/download-widget.html#download-widget>`_ to transfer files the other way around.

Now that we discussed file exchanges between the user device and AIMMS PRO storage, thus allowing for file sharing between AIMMS app users, we want to be able to arrange file sharing in a secure manner. This is the topic for the last blog post in this series.

Further reading:
----------------

*  `WebUI <https://documentation.aimms.com/webui/index.html#webui>`_

*  `AIMMS PRO <https://documentation.aimms.com/pro/index.html#pro-platform>`_


.. include:: /includes/form.def
 