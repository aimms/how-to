.. warning:: 
   Please note that the documentation you are currently viewing is for an older version of our technology. 
   While it is still functional, we recommend upgrading to our latest and more efficient option to take advantage of all the improvements we've made.
   
More Logging of WinUI Published Applications
===================================================

To obtain more logging from a published WinUI application, the following steps are needed:

#.  Run the application without logging, when this isn't done yet.
    This will ensure that an encrypted copy of the published project is on the laptop of the end-user.

#.  Ensure that the folder ``c:\temp`` exists.  This is the folder in which the log files will be placed.

#.  An example configuration file can be :download:`downloaded here <model/LoggerConfig.zip>` .

#.  Copy this ``LoggerConfig.xml`` file to the folder ``%localappdata%\AIMMS\PRO\<pro name>\<app name>\<UUID>\``
    Such a folder should now look as follows:
    
    .. image:: images/WinUIAppCacheFolder.png
        :align: center
    
#.  Run the application again by launching it from the AIMMS Portal.

#.  After closing the application, the session log files are:

    #.  ``c:\temp\aimms-log.txt``

    #.  ``C:\temp\aimms-log.xml``

.. putting a loggerConfig file in an .aimmspack may conflict with settings from the log management system of AIMMS Cloud.
