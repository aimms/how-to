Get Log Files
========================================================================
.. meta::
   :description: This article explains where you can find various log files in your project.
   :keywords: log, troubleshoot

Things don't always go according to plan. Sometimes the log files can answer **What happened?** 

This article explains where you can find various log files in your project.


Logs for the AIMMS IDE
-------------------------

The project log folder is a good place to start the search. 

This folder is by default the subfolder ``log`` of the project folder. This folder can be redirected by setting the project option *Listing and temporary files*.

The ``project/log`` folder contains the following files:

*   File ``aimms.err`` 
    This file is a recording of the error and warning messages sent to the error and warning window of the AIMMS IDE.
    This file is refreshed when the AIMMS project is started.

*   File ``messages.log`` 
    This file is a recording of the messages sent to the message window of the AIMMS IDE. 
    You can increase the amount of information by activating the options ``major messages`` and ``time messages``.
    This file is refreshed when the AIMMS project is started.

*   File ``<modelName>.lis`` 
    Constraint and solution listings are written to this file. 
    In addition, if no FILE is opened, then the output of PUT and DISPLAY statements is written to this file as well.
    This file is refreshed when the AIMMS project is started.

*   File ``<solver>.sta`` 
    This file is a recording of the messages issued by the solver. 
    See also :doc:`solver logging<../13/13-Solver-Logging-IDE>`. 
    This file is refreshed when the AIMMS project is started. 

*   Folder ``ErrorReports`` 
    This folder contains so-called ``.dmp`` files.  
    A ``.dmp`` file is a recording of the state of the running threads during a **severe internal error**. 
    Note that this folder is not emptied automatically by AIMMS. 
    When zipping your project leads to large archives, it might help to delete old ``.dmp`` files. 

Logs for deployed AIMMS app via AIMMS PRO
-----------------------------------------------

App launch
^^^^^^^^^^^^^^^^^^

When the incident happened while launching an app, look at:

* ``%LOCALAPPDATA%\\AIMMS\PRO\\Launcher\\<Pro version no>\\AimmsPROLauncher.log.txt``

* ``%LOCALAPPDATA%\\AIMMS\PRO\\AppLauncher\\2.6.1.0\\ProWebLink.log``

App running
^^^^^^^^^^^^^^^^^^

When the incident happens during an AIMMS verify, client, or server session, look at the session log.

Download button for log files can be found at on tab ``configuration``, click ``Log Management``. 
Note that this tab is only available to users in the ``Admin`` group.
You will find the session logs (verify sessions, data sessions and server sessions) in the folder ``log\\Sessions`` after unzipping the download.

.. warning:: The AIMMS PRO log files are designed to be interpreted by AIMMS staff only.
             Please do not be alarmed if (parts of it) don't make sense.

The download button is available since AIMMS PRO 2.11. 
For older AIMMS PRO systems, you can look at the folders:
``C:\ProgramData\AimmsPRO\Log\Sessions\`` and ``C:\ProgramData\AimmsPRO\Log\`` on the machine running AIMMS PRO.

further reading
^^^^^^^^^^^^^^^^

See also https://documentation.aimms.com/pro/logging.html


Browser logs
-------------------------

The front end of the WebUI actually shows your User Interface and runs in a supported browser.  
The logging information of this component is also kept in the browser.
To obtain this log information, we need to obtain this from the browser as follows:

Chrome
^^^^^^

* Ctrl-Shift-J or F12

* mark all messages

* right click and select "save as"

Edge
^^^^

* Ctrl-shift-J or right click and select "Inspect Element"

* Switch to tab "Console"

* Mark and copy all (relevant) text

* Paste this text to a text editor, and save it as a ``.log`` file.


Internet Explorer 11
^^^^^^^^^^^^^^^^^^^^

* Right click in the browser window

* Select "Inspect Element"

* Select tab console

* Right click in this tab, and select Copy All

* Paste the copied text to a text editor and save it as a ``.log`` file.

License server logs
---------------------

The licensing log files can be found in the folder ``C:\\ProgramData\\AIMMS\\Logging\\`` 
and the log files are called ``LicenseServer.log`` ``LicenseServer.log.1`` thru ``LicenseServer.log.10`` 

The license logging can be configured using ``LicenseServerLogging.xml`` in that same folder.

In this configuration file, you may want to change 

.. code-block:: none

    <param name="ConversionPattern" value="%r %5p [%t] %m%n"/>

to 

.. code-block:: none

    <param name="ConversionPattern" value="%d{ISO8601} %r %5p [%t] %m%n"/>

such that the timestamps of the incidents are more easily interpreted.

Related Topics
----------------

* **AIMMS Documentation**: `PRO Log Management <https://documentation.aimms.com/pro/admin-config-2.html>`_