Errors as data
==============

The end-user is a specialist in the application domain for which the application is designed. 
An application is built by specialists with other skills, including AIMMS and Operations Research.
This is why the error messages that occur in AIMMS applications may be better tailored for the perusal of the specialists in the team of the end-user.
This article discusses how to transfer error messages that occured in a deployed AIMMS application to the specialists in the team of the end-user.

.. image:: images/errors-as-data.png
    :align: center

This article discusses the "errors as data" functionality of the GuardServerSession library.  
The prime purpose of this functionality is:

#.  The end-user can view and download error reports, both of data session and of solver sessions.

#.  Specialists can upload these same error reports and inspect them as if they occurred in the AIMMS Developer environment they are running.

User Interface
---------------

The primary user interface for the "errors as data" functionality is shown in the GSS page below:

.. image:: images/gss-page.png
    :align: center

Remarks:

#.  The upper table on the left shows the error messages, with severity and creation moment.

#.  The lower table on the left shows, for the selected error message, the stack of procedures that was active at the moment of creation. 

    .. image:: images/right-click-model-explorer.png
        :align: center

    When right-clicking on an entry in that table, with AIMMS Developer, you can open the attribute window of the indicated procedure.
    
#.  The selection drop-down in the green rectangle is used to select the session shown. The collection of sessions to choose from consists of:

    #.  The data session

    #.  The server sessions running

    #.  The uploaded sessions

#.  In the lower right there is an upload and a download button for error data.

    #.  The download button is intended to be used by the end-user that wants to share an error report with the data and model specialists in her team.

    #.  The upload button is intended to be used by a data or model specialist that wants to investigate the errors reported by the end-user.

Data flow of error information
--------------------------------

The user interface shows only one of the sessions that can be selected. 
The data flow of errors to support this user interface is organized as follows:

.. image:: images/error-data-flow.png
    :align: center
    
The red triangles are a stream of warnings and errors. 
These errors move via various steps upwards towards the blue box of shown errors. 
The blue box symbolizes the list of errors shown to the user on the `GSS page`.

There are two paths from "red" to "blue" for this error information, depending on whether the errors are generated inside the data session or in a solver session.

Data flow for errors created in a data session
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

#.  Data session errors are collected by the error handler `gss::pr_appendError` in the orange box "Data session errors" `gss::dataSessionProfilerErrorData`.

#.  When the WebUI page `GSS page` is opened, the yellow box is copied to the blue box, making the errors visible to the end-user.

Data flow for errors created in a solver session
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

#.  Solver session errors are collected by the error handler `gss::pr_appendError` in the purple box "Solver session errors" `gss::serverSessionErrorData`.

#.  When the output case is loaded, this error information is loaded in the data session (to identifiers with the same name).

#.  Immediately after, this error information is copied to a slice in the green big box "Solver sessions and uploaded sessions".

#.  When the WebUI page `GSS page` is opened, and the selection drop down selects this solver session, this error information is copied to the blue box (and shown).





