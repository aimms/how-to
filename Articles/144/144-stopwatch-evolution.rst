:orphan:

Vintage way to Measure Execution Time
======================================


The option Use_UTC_forcaseandstartenddate is introduced in AIMMS 4.74.

.. code-block:: aimms

    Section Stopwatch_support {
        Quantity SI_Time_Duration {
            BaseUnit: s;
            Conversions: tick->s : #-># / 100;
            Comment: "Expresses the value for the duration of periods.";
        }
        StringParameter sp_StartTime {
            Comment: "Time the stopwatch was started";
        }
        Parameter p_ElapsedTime {
            Unit: s;
            Comment: {
                "Time that has elapsed since the stopwatch was started. 
                The value for this is updated by the StopStopwatch procedure."
            }
        }

        StringParameter sp_RunTime;

        Procedure pr_StartStopWatch {
            Body: {
                !Use the CurrentToString AIMMS function to store the current time
                !in YYYY-MM-DD HH:MM:SS:TT format
                sp_StartTime := CurrentToString( "%c%y-%m-%d %H:%M:%S:%t" );
            }
            Comment: "Set the starttime of the stopwatch";
        }
        Procedure pr_StopStopWatch {
            Body: {
                !Using the CurrentToMoment AIMMS function, we can ask for the number
                !of ticks that have elapsed at the moment since the given StartTime
                !(which was stored by calling the StartStopwatch procedure).
                !Please note that we do not actually 'stop' the stopwatch, but only
                !store the time elapsed.
                p_ElapsedTime := CurrentToMoment( [tick],  sp_StartTime );
            }
            Comment: "Determine how many ticks have elapsed since the start of the stopwatch";
        }
    }
