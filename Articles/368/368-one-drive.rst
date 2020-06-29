Storing project in One-Drive: A file cannot be created when it already exists
==============================================================================

Diagnosis: 
-----------

When you create and use a project in One-Drive, then you get the error:

"awf.state: Problem writing 'MainProject/webui.json' on the server (500: boost::filesystem::create_directory: a file cannot be created when it already exists"

When you move that project to a folder not managed by One-Drive, you do not get this error.


Explanation:
------------

Default settings of OneDrive is to download files as you use them, so there is a physical copy of only those files which it thinks are being used on your laptop. 
This does not always work well with an AIMMS project which is a collection of files and folders. 

If you change your settings to always keep copies on your laptop, you should not have any problem. 
Below is my OneDrive settings and I work on AIMMS projects in this directory. 
However, it is recommended that you don’t start a git repo in a OneDrive folder. 

Solution:
----------

In your One-Drive sync settings, please uncheck the option "Save space and download files as you use them".

.. image:: images/OneDriveSynSettings.png
    :align: center

