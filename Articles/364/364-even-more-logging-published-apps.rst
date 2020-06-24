Even more logging for Published Applications
=============================================

For WinUI apps
--------------

The end user session of a WinUI app hardly provides any log information.  

This can be changes as follows:

An :download:`example configuration file can be downloaded here <model/LoggerConfig.xml>`.

You will need to place this configuration file in the folder ``%localappdata%\AIMMS\PRO\<pro name>\<app name>\<UUID>\``

When there are multiple UUID's associated with the app, then use the latest folder.

The logging will be placed in:

#.  ``c:\temp\aimms-log.txt``

#.  ``C:\temp\aimms-log.xml``

Please make sure that the folder ``c:\temp`` exists.