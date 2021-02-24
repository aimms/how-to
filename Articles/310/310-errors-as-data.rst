Error and Profiling Results as Data
========================================

Error and profiler data can be stored in AIMMS' sets and parameters; then serialized and thereby shared between members of the same team.
This is also featured by the `GuardServerSession` library. This article discusses:

#.  How the `GuardServerSession` library achieves sharing of this data.

Data flow of error information
--------------------------------

The data flow of errors to support this user interface is organized as follows:

.. image:: images/error-data-flow.png
    :align: center
    
The red triangles are a stream of warnings and errors. 
These errors move via various steps upwards towards the blue box of errors shown in the User Interface.

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

Data flow of profiler information
---------------------------------

The profiler information is captured at the end of a solver session, or at the start of opening the "GSS page".
Then it follows the same path as the error information through the application before it is shown.


