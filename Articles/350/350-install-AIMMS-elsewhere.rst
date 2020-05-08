Install AIMMS perhaps elsewhere
=================================

To install AIMMS on your own laptop is usually easy, just download from `page <https://www.aimms.com/english/developers/downloads/download-aimms/>`_ and run the self-extracting file, for instance ``Aimms-4.73.1.3-x64-VS2017.exe``.
It will extract the actual program in ``%localappdata%\AIMMS\IFA\Aimms\`` and start it.

It is recommended to also download the `AIMMS launcher <https://download.aimms.com/aimms/download/data/AIMMSLauncher/AIMMSLauncher-1.0.0.55.exe>`_. 
The latest version can be downloaded from the same page. 
The functionalities of the App Launcher are:

* Double click on a ``.aimms`` file will open the AIMMS project, with the AIMMS release that was used to create that project, or, if that AIMMS release cannot be found, using the most recent AIMMS release.

* Right click on a ``.aimms`` file, this will provide you with a choice of AIMMS releases you have installed, to open the project. 

* You can also manage the AIMMS installs; to save disk space.

The above is sufficient for many people. In the remainder of this article we will discuss how to handle advanced requirements.

Installing elsewhere
----------------------

For the following reasons, the default installation location may not be the preferred one:

#.  Some organizations have a software policy that prohibits their users to install software in ``%localappdata%``

#.  The available disk space on the drive with the ``users`` folder may be limited.

You can then install AIMMS in a different location, namely ``C:\Program Files (x86)\AIMMS\IFA``

Obtain recent App launcher as preparation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Before you install elsewhere it is strongly recommended to install a recent AIMMS App Launcher.
If you have already installed an AIMMS app launcher, check the version by asking its properties:
On one of the machines I'm using, I had one that was too old, namely version: ``1.0.0.50``:

.. image:: images/LauncherTooOld.png
    :align: center

Downloading and installing the App launcher again gave me:

.. image:: images/LauncherRefreshed.png
    :align: center
    
Also, you can install the AIMMS Launcher with elevated rights. It will then be installed in ``C:\Program Files (x86)\AIMMS\IFA\AIMMSLauncher``

Installing on another drive
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

For reasons of disk space, you may want to install AIMMS on another disk. 

The AIMMS installer does not have built in functionality to install on another drive, but using a symbolic link, it is easy to realize.

First create a folder to install AIMMS in on the other drive, in the example here drive ``D:``, and we use the folder ``D:\Program Files (x86)\AIMMS\IFA\Aimms``.

Next, using the MS DOS command prompt with elevated (administrator) rights:

Navigate to ``C:\Program Files (x86)\AIMMS\IFA``.  
If this folder does not exist, please create it.

Then create a symbolic link as follows:

.. code-block:: none
    :linenos:
    :emphasize-lines: 1

    mklink /D Aimms "D:\Program Files (x86)\AIMMS\IFA\Aimms"

This should give the following output:

.. code-block:: none
    :linenos:

    symbolic link created for Aimms <<===>> D:\Program Files (x86)\AIMMS\IFA\Aimms

More on symbolic links can be found `here <https://www.computerhope.com/mklink.htm>`_

Actually installing elsewhere
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

With the above preparations, we should now be ready to install AIMMS in a different location, just launch the installer "as administrator" by right clicking and selecting "as administrator".
It will install on the D Drive and make AIMMS available to all licensed AIMMS users of that machine.



.. Figure out APP Luancher version.
.. see image 
.. 
.. Download latest
.. https://www.aimms.com/english/developers/downloads/download-aimms/
.. 
.. 
.. Create folder D:\Program Files (x86)\AIMMS\IFA\Aimms
.. 
.. Cmd prompt, elevated, nav to c:\Program Files (x86)\AIMMS\IFA
.. 
.. Microsoft Windows [Version 10.0.18363.815]
.. (c) 2019 Microsoft Corporation. All rights reserved.
.. 
.. C:\WINDOWS\system32>cd "C:\Program Files (x86)\AIMMS\IFA"
.. 
.. C:\Program Files (x86)\AIMMS\IFA>mklink /D Aimms "D:\Program Files (x86)\AIMMS\IFA\Aimms"
.. symbolic link created for Aimms <<===>> D:\Program Files (x86)\AIMMS\IFA\Aimms
.. 
.. C:\Program Files (x86)\AIMMS\IFA>