Error and Profiling Results as Data
========================================

Error and profiler data can be stored in AIMMS' sets and parameters; then serialized and thereby shared between members of the same team.
This is also featured by the ``GuardServerSession`` library. This article discusses how the ``GuardServerSession`` library achieves sharing of this data.

Data flow of error information
--------------------------------

The data flow of errors to support this user interface is organized as follows:

.. image:: images/error-data-flow.png
    :align: center
    :scale: 80
    
The red triangles are a stream of warnings and errors. 
These errors move via various steps upwards towards the blue box of errors shown in the User Interface.

There are two paths from "red" to "blue" for this error information, depending on whether the errors are generated inside the data session or in a solver session.

Data session errors
""""""""""""""""""""""

#. Errors occurring in the data session are collected by the error handler ``gss::pr_appendError`` in ``gss::dataSessionProfilerErrorData`` which represents the orange box "Data session errors"

#.  When the WebUI page ``GSS Session History Management`` is opened, the data in the orange box is copied to the blue box, making the error data visible to the end-user.

Solver session errors
"""""""""""""""""""""""""

#. Errors in solver sessions are collected by the error handler ``gss::pr_appendError`` in ``gss::serverSessionErrorData`` which represents the purple box "Solver session errors".

#.  When the output case is loaded, this error information is loaded in the data session (to identifiers with the same name).

#.  Immediately after, this error information is copied to a slice in the green big box "Solver sessions and uploaded sessions" by the procedure ``gss::pr_saveTrackedSessionData``.

#.  In the WebUI page ``GSS Session History Management``, the selection widget controls which session's error data is displayed. When you select a sessionID in the drop down, the corresponding error information is copied to the blue box and displayed.

Data flow of profiler information
---------------------------------

The profiler information is captured at the end of a solver session, or at the start of opening the ``GSS Session History Management``.
Then it follows the same path as the error information through the application before it is shown.


Converting errors and profiling info to data
------------------------------------------------------

Use the procedures ``pr_appendError`` and ``pr_appendMessage`` to convert errors and messages respectively to data in AIMMS identifiers.
The identifiers in which this data is stored are located in:
    #. ``gss::dataSessionProfilerErrorData`` for data sessions
    #. ``gss::serverSessionErrorData`` for server sessions

.. Both the procedures, the relevant collector is for the data session: `gss::dataSessionProfilerErrorData`, for a server session: `gss::serverSessionErrorData`.

#.  The procedure ``pr_appendError(ep_err,sp_prefix)`` appends the error to the open error collector.

    Arguments:

    #.  Input element parameter ``ep_err`` with range ``errh::PendingErrors``.  This is a reference to the error to be handled.

    #.  Input string parameter ``sp_prefix``.  This can be used to prefix the error message stored from the message of the error referenced by ``ep_err``

    Throws exceptions: None

    Return value: None.

#.  The procedure ``pr_appendMessage(sp_msg,ep_lev)`` Appends the message ``sp_msg`` to the open error collector.

    Arguments:

    #.  Input string parameter ``sp_msg``.  The message to be stored.

    #.  Input element parameter ``ep_lev`` with range ``gss::s_messageLevels`` and default ``'info'``.  The importance level of the message ``sp_msg``.

    Throws exceptions: None

    Return value: None.


















