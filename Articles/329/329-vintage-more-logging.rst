:orphan:

Get More Log Information for AIMMS 4.79 and older
==================================================

.. meta::
   :description: This article explains how to increase the amount of log information.
   :keywords: log, troubleshoot


The article is a companion article to :doc:`Get more log information <../329/329-more-logging>`, and provides materials that should **only** be used for AIMMS 4.79 and older.

This article provides two files that are templates in creating this information:

*  ``LoggerConfig.xml`` This file configures how much output several loggers provide during an AIMMS session.  You can tailor it to write XML or TXT output.  It is not suited for using on AIMMS Cloud.

*  ``RunWithExtraLogging.bat`` This is a small utility to start up AIMMS with ``LoggerConfig.xml`` activated.

These two files can be downloaded from the link below

* :download:`model.zip <model.zip>` 

As there is no automatic applying of logging settings using ``LoggerConfig.xml``, you will need to somehow add ``--logcfg LoggerConfig.xml`` to the AIMMS Command Line. One way of achieving that is using an MSDOS Batch script, such as the one below:

About ``RunWithExtraLogging.bat``
---------------------------------

To activate the ``LoggerConfig.xml`` the command line option ``--logcfg`` should be used. 
You can enter the entire command from the command prompt.

A batch file quickly becomes more convenient, if you do this more than once.  Consider the following ``.bat`` file:

.. code-block:: winbatch
    :linenos:

    echo on
    
    rem Modify this to select the proper AIMMS Version.
    rem set AIMMSVERSION=4.70.2.4-x64-VS2017

    rem Select the latest AIMMS version
    rem Get the latest AIMMS release installed by pushing/popping both drive and path, 
    rem and then switching to the folder of installed AIMMS versions.
    rem Assumption: the users folder is on the C drive.
    set DRIVEUSEDATSTART=%~d0%
    pushd
    c:
    cd %localappdata%\AIMMS\IFA\Aimms\
    for /f "usebackq delims=|" %%f in (`dir /b `) do set AIMMSVERSION=%%f
    popd
    %DRIVEUSEDATSTART%
    
    set AIMMSEXECUTABLE="%localappdata%\AIMMS\IFA\Aimms\%AIMMSVERSION%\Bin\aimms.exe"
    
    rem Assuming there is precisely one .aimms file in the current folder, 
    rem the following command will select that .aimms file and start it with the AIMMSEXECUTABLE selected.
    rem Logging is turned on by --logcfg LoggerConfig.xml on the command line.
    for /f "usebackq delims=|" %%f in (`dir /b *.aimms`) do %AIMMSEXECUTABLE% --logcfg LoggerConfig.xml %%f
    
    rem Assuming here LoggerConfig.xml still writes to the fixed folder c:\temp,
    rem we move the create logfile from this folder to the current folder.
    rem Because of this fixed location, we can only debug one AIMMS project at a time!
    rem copy c:\temp\aimms-log.xml log
    rem del c:\temp\aimms-log.xml
    
    pause

Selected remarks about the about this ``.bat`` file:

*   The file provided is just a template to be tailored as needed.

*   Line 1: We keep the echo-ing on; such that we can see afterward whether the ``.bat`` file worked as expected.

*   Lines 10-16: Search the folder ``%localappdata%\AIMMS\IFA\Aimms\`` for the latest AIMMS installed. 
    For me, this doesn't work as I still have AIMMS 4.9 installed. 
    That is why I uncommented line 4, and commented out lines 10-16 for the projects I'm working on.

*   Line 23: Here AIMMS is started using the command line option ``--logcfg``. 
    The trick in this statement is that it is applied to every ``.aimms`` file in the folder. 
    As there is usually only one such file, this works out nicely.

*   Line 28,29 The XML file appender may be configured to write to a file with an absolute path. 
    In that case, uncomment these two lines, such that we copy the XML log file to the place it belongs: 
    the log folder of the project.

*   Line 31: By pausing we can see the log in the command window opened. 
    It might be useful to check whether the ``.bat`` execution worked properly on your system.
    You may want to remove this line after a while.


 

