The action log
===============

This article is a companion article to :doc:`Incident Handling for Organizations<../310/310-incident-handling-for-organizations>`

The action log is part of the Session History. 
Where the Profiling information and errors as data provide deep insight into an incident, 
the action log provides insight into what happened before.

The action log is like any other log file, it contains the output of tracing statements.
The ``GuardServersession`` library manages these log files.

The action log is stored in .actionLog files in the sub folder tracings of the project folder. 
At the end of a solver session saved in AIMMS PRO storage at ``/userdata/<env>/<user>/<appname>/tracings/<session-id>.actionLog``.

The following is a sample small action log:

.. code-block:: none
    :linenos:
    :emphasize-lines: 3,9

    Opening log file tracings/d942c7adf9460ee3f4f2a0de1ed0833b.actionLog at 2021-03-08 14:25:20
    This file contains the log of a data session on behalf of chris@AIMMS
    2021-03-08 14:25:24:72 [info ] Enter pr_btnSolve() [51.125 Mb] in use
    2021-03-08 14:25:27:55 [debug] Enter gss::LoadResultsCallBack() [51.457 Mb] in use
    2021-03-08 14:25:27:60 [trace] s_trackedSessions = { d942c7adf9460ee3f4f2a0de1ed0833b, c78864a3-babf-4015-ae41-38de854fa0a3 }
    2021-03-08 14:25:27:61 [debug] Leave gss::LoadResultsCallBack() [51.770 Mb] in use. Duration is 0.060 [seconds] and memory increase is 0.313 Mb.
    2021-03-08 14:25:27:62 [warn ] 2021-03-08 14:25:24:00: Warning: Don't look down.
    2021-03-08 14:25:27:62 [warn ] 2021-03-08 14:25:27:00: Warning: Look up, it is raining ;-).
    2021-03-08 14:25:27:63 [info ] Leave pr_btnSolve() [51.895 Mb] in use. Duration is 2.910 [seconds] and memory increase is 0.770 Mb.

Remarks:

#.  First two lines summarizing which session is logged.

#.  The actual logging consists of three columns:

    #.  Date time

    #.  Importance level of messages

    #.  The actual message

#.  ``[warn]`` Lines 7, 8: Warnings first issued, then handled.  The timestamp in the date time column is the time the warning was handled. 
    The timestamp in the message column is the timestamp the warning was created.

#.  ``[info]`` Lines 3, 9: An action - a procedure invoked via the WebUI by the end-user. These lines are marked here.

#.  ``[debug]`` Lines 4, 6: The procedure is invoked by program logic (call back server session).
    
#.  ``[trace]`` Line 5: requesting and printing a bit of state is typically done at message importance level ``'trace'``



Relevant identifiers for logging
--------------------------------

#.  Set `gss::s_messageLevels`

    .. code-block:: aimms
        :linenos:

        Set s_messageLevels {
            Index: i_messageLevel;
            Definition: {
                data { trace, debug, info, warn, error, fatal } ;
            }
            Comment: "The message levels for tracing";
        }

    Here the message level ``'trace'`` is the lowest level, and the level ``'fatal'`` is the highest level.

#.  Element parameter ``gss::ep_tracingFilterlevel`` with range ``gss::s_messageLevels``

    The tracing filter level: any log message with an importance below this filter level will not be logged.
    At the beginning of a session, this parameter is initialized to `'info'`. 
    So when this tracing filter level is kept at its default, 
    messages with importance 'debug' or 'trace' will not be logged.

    .. note:: 

        #.  This parameter is part of the input case sent from the data session to a solver session.
            Therefore, setting the logging level in the data session, will affect the logging in the solver sessions.

        #.  At the beginning of a session, this parameter is initialized to ``'info'``. 
            So when this tracing filter level is kept at its default, 
            messages with importance ``'debug'`` or ``'trace'`` will not be logged.

#.  Procedure ``gss::pr_setTracinglevel(ep_newTracingFilterLevel)``

    Use this procedure to set the `gss::ep_tracingFilterlevel`

    Arguments:

    #.  Element parameter ``ep_newTracingFilterLevel``, with range ``gss::s_messageLevels``

    Throws exceptions: None

    Return value: None.

#.  Procedure ``gss::pr_logMsg(sp_message,ep_messageImportance)``

    Writes ``sp_message`` to the action log file provided the importance of the message ``ep_messageImportance`` 
    is greater than or equal to message filter level ``ep_tracingFilterlevel`` 

    Arguments:

    #.  ``sp_message`` An input string parameter.

    #.  ``ep_messageImportance`` An optional element parameter with range ``s_MessageLevels`` and default ``'trace'``.

    Throws exceptions: None.

    Return value: None.

#.  Procedure ``gss::pr_enter(sp_procEnterTimestamp,p_procEnterMemoryInUse,ep_messageImportance,sp_logDetail)``

    Log the entry of a procedure, including when the procedure was entered and how much memory was in use.
    In addition, it stores the entry time and the memory in use at entry in the output arguments ``sp_procEnterTimestamp`` and ``p_procEnterMemoryInUse``.

    The techniques used by ``gss::pr_enter`` and ``gss::pr_leave`` are laid out in :doc:`Tracing<../497/497-tracing-procedures>`.

    Arguments:

    #.  ``sp_procEnterTimestamp`` An output parameter that contains the encompassing procedure entry time according to timezone ``'UTC'``

    #.  ``p_procEnterMemoryInUse`` An output parameter that contains the amount of memory in use upon when the encompassing procedure was entered.

    #.  ``ep_messageImportance`` optional default ``'debug'`` 
        The importance of logging the entry of the encompassing procedure.  
        For procedures that are actions, it is recommended to use the value ``'info'``.

    #.  ``sp_logDetail`` optional, default: empty

    Throws exceptions: None

    Return value: None.

#.  Procedure ``gss::pr_leave(sp_procEnterTimestamp,p_procEnterMemoryInUse,ep_messageImportance,sp_logDetail)``

    #.  ``sp_procEnterTimestamp`` An input parameter that contains the encompassing procedure entry time according to timezone ``'UTC'``

    #.  ``p_procEnterMemoryInUse`` An input parameter that contains the amount of memory in use upon when the encompassing procedure was entered.

    #.  ``ep_messageImportance`` optional default ``'debug'``
        The importance of logging the leaving of the encompassing procedure.  
        For procedures that are actions, it is recommended to use the value ``'info'``.

    #.  ``sp_logDetail``  optional, default: empty

    Throws exceptions: None

    Return value: None.




Recommended use
----------------

Important to what has been done, is a list of actions.
Here an action is defined as: An AIMMS procedure that is invoked via the WebUI.
Examples of invocations of procedures from the WebUI are:

#.  Button press

#.  Uponchange procedure of a data item

#.  A menu entry, via an item menu, widget menu, primary action, or secondary action

#.  Status bar procedure

#.  Page property: Action upon load

#.  Page property: Action upon leave

By having a complete log of actions of an end-user; one can try to replay what happened.

For actions, please follow the following template:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 3,10

    Procedure pr_actionTemplate {
        Body: {
            pr_enter(sp_gssTime, p_gssMiU, ep_messageImportance: 'info');
            block 
                ! Call procedure to do the actual work.
            onerror ep_err do
                gss::pr_appendError( ep_err );
                errh::MarkAsHandled( ep_err );
            endblock ;
            pr_leave(sp_gssTime, p_gssMiU, ep_messageImportance: 'info');
        }
        Comment: "Sample action procedure";
        DeclarationSection gss_logging_declarations {
            StringParameter sp_gssTime;
            Parameter p_gssMiU;
        }
        DeclarationSection error_reference_declaration {
            ElementParameter ep_err {
                Range: errh::PendingErrors;
            }
        }
    }











