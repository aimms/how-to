Creating StopWatch in AIMMS to time execution
=============================================

.. sidebar:: Stopwatch

    .. image:: ../Resources/C_Language/Images/144/stopwatch-156008_960_720.png

There are situations where you would like to know how long the execution of something in AIMMS took.

When you are working as an AIMMS developer, one of the tools you have for this is the AIMMS profiler. This profiler will provide you with information about how long each statement in an execution took, as well as how long the evaluation of the definition of a parameter took. More information about the profiler can be found in the `AIMMS The User's Guide <https://documentation.aimms.com/_downloads/AIMMS_user.pdf>`_ Chapter "Debugging and Profiling an Aimms Model".

When running in End-user mode, the profiler is not available. To still be able to give the end-user feedback on how much time certain steps took, you can create a 'stopwatch' in AIMMS code. This can be achieved by introducing the following identifiers into your model:


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

If your model already contains the SI_Time quantity, just make sure that the units second and tick (1/100th of one second) are defined (either as conversion, or as base unit).

After you have imported the section, the stopwatch code can be used as follows:

.. code-block:: aimms

    pr_StartStopwatch ;
    pr_LongRunningProcedure  ;
    pr_StopStopwatch ;
    DialogMessage(formatString("Execution of procedure took %n seconds", p_ElapsedTime ) ) ;

When running this code, you will get a dialog window telling you how many seconds the execution of SomeLongLastingProcedure took.

To obtain the above code, please see the instructions in the post :doc:`145-import-export-section` to export the section ``Stopwatch support`` from the example model below and import this ``.ams`` file into your own project. 
If your project already contains the SI_Time quantity, please remove the quantity from the ``.ams`` file after downloading it.

Example project:
:download:`AIMMS project download <../Resources/C_Language/Images/144/Stopwatch.zip>` 


.. include:: ../includes/form.def


