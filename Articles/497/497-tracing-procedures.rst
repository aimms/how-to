Tracing procedures
==================

This article is a follow up of :doc:`tracing<497-writing-to-session-log>`.
Two procedures are discussed that show how to trace entering and leaving procedures, such that the reader knows:

#.  When the procedure was entered

#.  When the procedure was finished

#.  How long this took

#.  How much memory was used or freed during its execution.

These latter two purposes are achieved by comparing the state at the beginning and end of the procedure.

Running example
---------------

The running example is based on a transportation problem: Pellets of bottled water are to be shipped from bottling locations to distribution centers. This example is extended with some logging and :download:`can be downloaded here <model/writingToSessionLogBottledWater.zip>` 

How to use
-----------

Suppose you have a procedure to do some work, for instance as follows:

.. code-block:: aimms
    :linenos:

    Procedure pr_doSolve {
        Body: {
            solve mp_transport ;
        }
    }

By adding a few lines, and declarations, the execution of the procedure is traced.  The additions are marked in yellow below.

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 3,5,7,8

    Procedure pr_doSolve {
        Body: {
            pr_enter(sp_enterTime,p_enterMemoryInUse);
            solve mp_transport ;
            pr_leave(sp_enterTime,p_enterMemoryInUse);
        }
        StringParameter sp_enterTime;
        Parameter p_enterMemoryInUse;
    }

Remarks:

#.  Line 3: At the start of a procedure, trace the entering of that procedure.  
    The arguments are used to store the part of the state (memory in use, moment of execution) in local parameters.

#.  Line 5: At the end of the procedure, trace the leaving of that procedure.  In addition, trace how much memory and time has been used.

#.  Line 7,8: Declaration of local parameters.   

.. tip:: As you know, lines of text can be copied. But the AIMMS Developer lets you also copy declarations; just right click on a declaration, keep the right mouse pressed, and move to the target location and select ``Copy Identifier(s)``.

How it is implemented
---------------------

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 4,6,9-13

    Procedure pr_enter {
        Arguments: (sp_procEnterTimestamp,p_procEnterMemoryInUse,sp_procEnterContextMessage);
        Body: {
            sp_procEnterTimestamp := CurrentToString("%c%y-%m-%d %H:%M:%S:%t%TZ('UTC')");
            p_procEnterMemoryInUse := MemoryInUse();
            sp_node := formatString("%e",CallerNode(1));
            sp_fullMsg := formatString("Enter %s(%s) at %s [%.3n Mb] in use", 
                sp_node, sp_procEnterContextMessage, 
                MomentToString(
                    Format        :  "%c%y-%m-%d %H:%M:%S:%t%TZ(ep_traceTimezone)", 
                    unit          :  [s], 
                    ReferenceDate :  sp_procEnterTimestamp, 
                    Elapsed       :  0[s]), 
                p_procEnterMemoryInUse );
            pr_logMsg( sp_fullMsg );
        }
        StringParameter sp_procEnterTimestamp {
            Property: InOut;
        }
        Parameter p_procEnterMemoryInUse {
            Property: InOut;
        }
        StringParameter sp_procEnterContextMessage {
            Property: Optional;
            Comment: {
                "If the traced procedure contains arguments, 
                you may want to summarize these arguments here."
            }
        }
        StringParameter sp_node;
        StringParameter sp_fullMsg;
    }

Remarks:

#.  Line 4: Timestamps are recorded in timezone UTC.  This eases the measurement of duration, as the reference time argument in `StringToMoment <https://documentation.aimms.com/functionreference/elementary-computational-operations/time-functions/stringtomoment.html>`_ is assumed to be in timezone UTC, well, at least since AIMMS 4.76.

#.  Line 6: `CallerNode(1) <https://documentation.aimms.com/functionreference/model-handling/model-query-functions/callernode.html>`_ returns the procedure that called the currently running procedure.

#.  Line 9-13: Only when communicating the timestamps to the log file, the timezone of the timestamp is converted to the local timezone.  See also the initialization of element parameter ``ep_traceTimezone``

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 5-9,14

    Procedure pr_leave {
        Arguments: (sp_procEnterTimestamp,p_procEnterMemoryInUse,sp_msg);
        Body: {
            sp_leavingTime := CurrentToString("%c%y-%m-%d %H:%M:%S:%t%TZ('UTC')");
            p_duration := StringToMoment(
                Format        :  "%c%y-%m-%d %H:%M:%S:%t%TZ('UTC')", 
                Unit          :  [s], 
                ReferenceDate :  sp_procEnterTimestamp, 
                Timeslot      :  sp_leavingTime);
            sp_node := formatString("%e",CallerNode(1));
            p_leaveMemoryInUse := MemoryInUse();
            sp_fullMsg := 
                formatString( "Leave %s(%s) at %s [%.3n Mb] in use. ",
                    sp_node, sp_msg, 
                    MomentToString(
                        Format        :  "%c%y-%m-%d %H:%M:%S:%t%TZ(ep_traceTimezone)", 
                        unit          :  [s], 
                        ReferenceDate :  sp_leavingTime, 
                        Elapsed       :  0[s]), 
                    p_leaveMemoryInUse ) +
                formatString( "Duration is %.3n [seconds] and memory %s is %n Mb.", 
                    p_duration,
                    if p_leaveMemoryInUse >= p_procEnterMemoryInUse then "increase" else "decrease" endif, 
                    abs( p_leaveMemoryInUse - p_procEnterMemoryInUse ) );
            pr_logMsg( sp_fullMsg );
        }
        Parameter p_procEnterMemoryInUse {
            Property: Input;
        }
        StringParameter sp_procEnterTimestamp {
            Property: Input;
        }
        StringParameter sp_leavingTime;
        Parameter p_duration {
            Unit: s;
        }
        Parameter p_leaveMemoryInUse;
        StringParameter sp_msg {
            Property: Optional;
        }
        StringParameter sp_node;
        StringParameter sp_fullMsg;
    }

Remarks:

#.  Line 5-9: Computing the duration. Note that UTC timestamps are compared.

#.  Line 21: The format string to trace how much time and memory was used to execute the procedure.

How to integrate
-----------------

The code explained and demoed above is contained in a separate section named: ``Logging``.
See `Export code to another project <https://how-to.aimms.com/Articles/145/145-import-export-section.html>`_ for copying the code to your project.

