What happened?
========================================================================

**What happened** is a question modelers and users often ask **after the fact**.
Sometimes the answer is in log files, but where to look for log files?


When using the AIMMS IDE
-------------------------

The project log folder is a good place to start the search. This folder is by default the sub folder ``log`` of the project folder. 
By setting the project option ``Listing and temporary files``, this folder can be redirected.
The project log folder contains the following files:

#.  File ``aimms.err`` 
    This file is a recording of the error and warning messages sent to the error and warning window of the AIMMS IDE.
    This file is refreshed when the AIMMS project is started.

#.  File ``messages.log`` 
    This file is a recording of the messages sent to the message window of the AIMMS IDE. 
    You can increase the amount of information by activating the options ``major messages`` and ``time messages``.
    This file is refreshed when the AIMMS project is started.

#.  File ``<modelName>.lis`` 
    Constraint and solution listings are written to this file. 
    In addition, if no FILE is opened, then the output of PUT and DISPLAY statements is written to this file as well.
    This file is refreshed when the AIMMS project is started.

#.  File ``<solver>.sta`` 
    This file is a recording of the messages issued by the solver. 
    See also :doc:`solver logging<../13/13-Solver-Logging-IDE>`. 
    This file is refreshed when the AIMMS project is started. 

#.  Folder ``ErrorReports`` 
    This folder contains so-called ``.dmp`` files.  
    A ``.dmp`` file is a recording of the state of the running threads during a **severe internal error**. 
    Note that this folder is not emptied automatically by AIMMS. 
    When zipping your project leads to large archives, it might help to delete old ``.dmp`` files. 

When running a deployed AIMMS app via AIMMS PRO
-----------------------------------------------

During app launch
^^^^^^^^^^^^^^^^^^

When the incident happened during the launching of an app, please look at:

#. ``%LOCALAPPDATA%\\AIMMS\PRO\\Launcher\\<Pro version no>\\AimmsPROLauncher.log.txt``

#. ``%LOCALAPPDATA%\\AIMMS\PRO\\AppLauncher\\2.6.1.0\\ProWebLink.log``

During app running
^^^^^^^^^^^^^^^^^^

When the incident happens during an AIMMS client or server session, look at the session log.

Download button for log files can be found at on tab ``configuration``, click ``Log Management``. 
Note that this tab only is available to users in the ``Admin`` group.
You will find the session logs (verify sessions, data sessions and server sessions) in the folder ``log\\Sessions`` after unzipping the download.


The above download button is available since AIMMS PRO 2.11. 
For older AIMMS PRO systems, you may want to look at the folders:
``C:\ProgramData\AimmsPRO\Log\Sessions\`` and ``C:\ProgramData\AimmsPRO\Log\`` on the machine running AIMMS PRO.
