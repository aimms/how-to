The action log
===============

The action log is like any other log file, it contains the output of tracing statements.
The ``GuardServersession`` library manages these logfiles.

The action log is stored in .actionLog files in the subfolder tracings of the project folder. 
At the end of a solver session saved in AIMMS PRO storage at `/userdata/<env>/<user>/<appname>/<appver>/tracings/<session-id>.actionLog`


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

    .. note:: This parameter is part of the input case sent from the data session to a solver session.

#.  Procedure ``gss::pr_setTracinglevel(ep_newLev)``

    Use this procedure to set the `gss::ep_tracingFilterlevel`

    Arguments:

    #.  Element parameter ``ep_newLev``, with range ``gss::s_messageLevels``

    Throws exceptions: None

    Return value: None.

#.  Procedure ``gss::pr_logMsg(sp_message,ep_messageImportance)``

    Writes ``sp_message`` to the action log file provided the importance of the message ``ep_messageImportance`` 
    is greater than or equal to message filter level ``ep_tracingFilterlevel`` 

    Arguments:

    #.  ``sp_message`` An input string parameter.

    #.  ``ep_messageImportance`` An optional element parameter with range ``s_MessageLevels`` and default ``'trace'``.

    Throws exceptions: None

    Return value: None.

#.  Procedure ``gss::pr_enter(sp_procEnterTimestamp,p_procEnterMemoryInUse,ep_logLev,sp_procEnterContextMessage)``

    Log the entry of a procedure, including when the procedure was entered and how much memory was in use.
    In addition, it stores the entry time and the memory in use at entry in the output arguments ``sp_procEnterTimestamp`` and ``p_procEnterMemoryInUse``.

    The techniques used by ``gss::pr_enter`` and ``gss::pr_leave`` are laid out in :doc:`Tracing<../497/497-tracing-procedures>`.

    Arguments:

    #.  ``sp_procEnterTimestamp`` An output parameter that contains the encompassing procedure entry time according to timezone ``'UTC'``

    #.  ``p_procEnterMemoryInUse`` An output parameter that contains the amount of memory in use upon when the encompassing procedure was entered.

    #.  ``ep_logLev`` optional default ``'debug'`` 
        The importance of logging the entry of the encompassing procedure.  
        For procedures that are actions, it is recommended to use the value ``'info'``.

    #.  ``sp_procEnterContextMessage`` optional, default: empty

    Throws exceptions: None

    Return value: None.

#.  Procedure ``gss::pr_leave(sp_procEnterTimestamp,p_procEnterMemoryInUse,ep_logLev,sp_msg)``

    #.  ``sp_procEnterTimestamp`` An input parameter that contains the encompassing procedure entry time according to timezone ``'UTC'``

    #.  ``p_procEnterMemoryInUse`` An input parameter that contains the amount of memory in use upon when the encompassing procedure was entered.

    #.  ``ep_logLev`` optional default ``'debug'``
        The importance of logging the leaving of the encompassing procedure.  
        For procedures that are actions, it is recommended to use the value ``'info'``.

    #.  ``sp_procEnterContextMessage``  optional, default: empty

    Throws exceptions: None

    Return value: None.

    Example tracing output (together with pr_Leave): 

    .. code-block:: none
        :linenos:

        Enter pr_workSolve() at 2021-02-24 15:38:23:51 [43.766 Mb] in use
        Leave pr_workSolve() at 2021-02-24 15:38:24:54 [49.070 Mb] in use. Duration is 1.030 [seconds] and memory increase is 5.305 Mb.



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
            pr_enter(sp_gssTime, p_gssMiU, ep_logLev: 'info');
            block 
                ! Call procedure to do the actual work.
            onerror ep_err do
                gss::pr_appendError( ep_err );
                errh::MarkAsHandled( ep_err );
            endblock ;
            pr_leave(sp_gssTime, p_gssMiU, ep_logLev: 'info');
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











