Get More Log Information
=========================

.. meta::
   :description: This article explains how to increase the amount of log information.
   :keywords: log, troubleshoot

After reporting an incident and analyzing the associated log files, more detailed logging may be needed. 
To provide more detailed logging, AIMMS comes with so-called loggers, explained below. 
This logging facility is somewhat similar to the `log4j <https://logging.apache.org/log4j/2.x/>`_ technology.
In this article, we explore how this facility can be used for initial troubleshooting in AIMMS projects.

But first, the log files are there to help diagnose an issue in the application. 
The reverse is not true: an alarming message in the log files does not necessarily indicate a problem in the application. 
For instance, connections are often maintained at several layers, thereby facilitating automatic recovery. 
When a connection fails at a lower level, this sounds alarming, but there may be a recovery at a higher level. 
In short:

.. warning:: The AIMMS log files are designed to be interpreted by AIMMS staff.
             Please do not be alarmed if (parts of it) don't make sense.

This article provides two files that are templates in creating this information:

#.  ``LoggerConfig.xml`` This file configures how much output several loggers provide during an AIMMS session.
    You can tailor it to either write ``.xml`` or ``.txt`` output.

#.  ``RunWithExtraLogging.bat`` This is a small utility to start up AIMMS with ``LoggerConfig.xml`` activated.

These two files can be downloaded :download:`here <model.zip>` 

Terminology
-------------

#.  **Logger** A logger is a tracing facility built in a software component.

#.  **Logger naming** A logger is usually named after the component in which it is built. 
    When the main component has a sub-component, the tracing facility of the sub-component is usually named 
    ``<main component>.<sub component>``. And so on recursively.

#.  **Log level** Both a logger and a message have a level associated with it. 
    When the message level is equal to or greater than the level of the logger, it is written to file.
    Here is the list of log levels used (from low to high):

    #.  *Trace* Typically intermediate results, and indications of where execution is.

    #.  *Debug* Typically input echo-ing and computed results

    #.  *Info* Summaries of what is computed, such as a matrix size overview.

    #.  *Warn* Exceptions that can be continued from

    #.  *Error* Exceptions that should be handled by the modeler or user
    
    Clearly: the lower the level of the logger, the higher the volume of output!
    
    .. note:: We caution you to not use the levels Trace and Debug unless instructed to do so by AIMMS Staff; 
              not only do  these log levels generate a high volume of output, the actual output text generated 
              typically only makes sense with knowledge of the implementation of the corresponding 
              software component.

#.  **appender** There are two appenders available; one to generate ``.txt`` files and one to generate ``.xml`` files.
    In the configuration file provided, they are both activated.
    In the next two sections, the corresponding output formats are discussed in more detail.

Using a text editor to analyze ``.txt`` log files
----------------------------------------------------

Use your favorite text editor to open the log file ``log/aimms-log.txt``. 
BTW, my favorite text editor is `notepad++ <https://notepad-plus-plus.org/>`_. 
A couple of lines may look like this:

.. code-block:: none
    :linenos:

    2019-12-23 10:12:28,689 0x0000598c [WARN] {AIMMS.Compiler.ceattr.AimmsBCIncidentHandler} "guipro::progress::NextCheck" is not present in the interface of its containing library and therefore cannot be referenced from outside this library.
    2019-12-23 10:15:28,986 0x00006358 [DEBUG] {AIMMS.Trace.Procedure} Starting Procedure  MainInitialization
    2019-12-23 10:15:28,986 0x00006358 [DEBUG] {AIMMS.Trace.Procedure} Starting Procedure  gss::pr_SeenErrorsAreHandled
    2019-12-23 10:15:29,010 0x00006358 [DEBUG] {AIMMS.Trace.Procedure} Finishing Procedure gss::pr_SeenErrorsAreHandled
    
Selected remarks:

*   Line 1: I clearly made the mistake to reference the procedure ``guipro::progress::NextCheck`` outside the library ``AimmsProGUI``.
    Don't worry, I got this as an error message in the AIMMS IDE as well ;-).

*   Lines 2-4 are obtained by setting the level of the logger ``AIMMS.Trace.Procedure`` to trace; 
    and the pattern of the lines generated is visualized. 
    This pattern is ``%d{yyyy-MM-dd HH:mm:ss,SSS} %t [%p] {%c} %m%n``.  
    The components in this pattern are:
    
    *   ``%d`` The date up to millisecond precision

    *   ``%t`` The number of the execution thread (hexadecimal)

    *   ``%p`` The priority of the message

    *   ``%c`` The name of the logger

    *   ``%m`` The message itself

    *   ``%n`` A newline

 
Using ``Log4View`` to analyze ``.xml`` log files
-----------------------------------------------------

``Log4View`` is a nifty utility to analyze ``.xml`` log files. It can be downloaded `here <https://www.log4view.com/download-en>`_.
The community edition of ``Log4View`` is sufficient to analyze one ``.xml`` log file at a time.

.. image:: images/log4view.png
    :align: center

With the utility `Log4View <https://www.log4view.com/>`_ you can filter away the output of selected loggers. 
The screenshot shown above shows that the output of the logger ``AimmsSession`` is hidden, and the output of the logger ``CubeEngineLink`` is about to be hidden, by right-clicking on such a logger.


About ``LoggerConfig.xml``
--------------------------

There are three sections in the file ``LoggerConfig.xml``

#.  **Appenders** This section defines how and where the output can be sent to.
    In practice, there are only two appenders used:

    #.  *MyFileAppender* A text file appender, which sent output to the local file ``log/aimms-log.txt``.

    #.  *MyXMLFileAppender* An XML text file appender, which sents its output to the file ``c:/temp/aimms-log.xml``

#.  **loggers**

    There are various loggers, and each logger has its own default level.

#.  **Final configuration**

    This section is used to select the appenders to be used.  Normally, you'll just use one, and comment out the other.

About ``RunWithExtraLogging.bat``
---------------------------------

To activate the ``LoggerConfig.xml`` the command line option ``--logcfg`` should be used. 
A straightforward way to do this is to enter the entire command from the command prompt.
A batch file quickly becomes more convenient, if you do this more than once.  Consider the following .bat file:

.. code-block:: none
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
    copy c:\temp\aimms-log.xml log
    del c:\temp\aimms-log.xml
    
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

*   Line 28,29 The XML file appender only writes to a file with an absolute path. 
    Therefore we copy the XML log file to the place it belongs: the log folder of the project.

*   Line 31: By pausing we can see the log in the command window opened. 
    It might be useful to check whether the ``.bat`` execution worked properly on your system.
    You may want to remove this line after a while.


Further reading
---------------

* Get log files :doc:`The parent article<../313/313-get-log-files>`

* Guard server session :doc:`Investigating behavior server session<../310/310-investigate-behavior-pro-job>`

* Save state  :doc:`Data state server session<../321/321-state-server-session>`

* The AIMMS Debugger, see AIMMS The User's Guide: Chapter "Debugging and Profiling an Aimms Model"

* Command-line options, see AIMMS The User's Guide: Chapter "Calling Aimms"


