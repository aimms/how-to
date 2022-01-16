Measure Execution Time
==========================

.. meta::
   :description: How to measure efficiency of procedures with StopWatch function.
   :keywords: efficient, time, execute, stopwatch, watch, clock

..   .. note::

.. 	This article was originally posted to the AIMMS Tech Blog.

.. .. sidebar:: Stopwatch

..     .. image:: images/icons8-stopwatch-512.png
..             :align: center

There are situations where you would like to know the duration of execution of an AIMMS code-block. 
In AIMMS Developer, you can do this easily by using the built-in Profiler, accessed through `Tools -> Diagnostic Tools -> Profiler`. 
This profiler will provide you with information about how long each statement in an execution took, as well as how long the evaluation of the definition of a parameter took. More information about the profiler can be found in :doc:`creating-and-managing-a-model/debugging-and-profiling-an-aimms-model/the-aimms-profiler`.

You can also start the profiler programmatically using the function :aimms:func:`ProfilerStart`. 
It is common to make a call to this function in the initialization procedures so that you can always have the profiler available while developing an AIMMS application.
See all related functions in `AIMMS Function Reference - Development Support <https://documentation.aimms.com/functionreference/development-support/profiler-and-debugger/index.html>`_

However, the profiler functionality is not available in WebUI or when running an app from AIMMS PRO. 
To be able to give the end-users information about runtime, you can create a custom stopwatch functionality in your project.
.. When you are working as an AIMMS developer, one of the tools you have for this is the AIMMS profiler.

Custom stopwatch
-------------------
 
One way to do this is described in this article. 

.. When running in End-user mode, the profiler is not available. To still be able to give the end-user feedback on how much time certain steps took, you can create a 'stopwatch' in AIMMS code. This can be achieved by introducing the following identifiers into your model:

Introduce the below identifiers in your project. You can also just import the :download:`Stopwatch_support section <downloads/stopwatch_support.ams>`. 
See :doc:`../145/145-import-export-section`

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

.. note::

    If your model already contains ``SI_Time quantity``, just update it so that the units second and tick (1/100\ :sup:`th`\  of one second) are defined (either as conversion, or as base unit).
    Also, delete the ``SI_Time_Duration`` imported in the ``stopwatch_support`` section. 

Example project
------------------

Download the :download:`example AIMMS Project <downloads/Stopwatch.zip>` 

``MainExecution`` in this procedure shows the usage of the procedures built in the ``stopwatch_support`` section.

.. code-block:: aimms
    :linenos:

    pr_StartStopwatch ;
    pr_LongRunningProcedure  ;
    pr_StopStopwatch ;
    sp_RunTime := formatString("Execution of procedure took %n seconds", p_ElapsedTime );

In this procedure, we use functions from the ``Stopwatch_support`` section to measure the time it took to execute ``pr_LongRunningProcedure``. Line-4 is simply constructing a message using the stopwatch results. 

.. tip::

    Instead of storing the message in a string parameter, update the text displayed in the WebUI Status Bar to communicate this information to your end users. 
    See `WebUI Status Bar <https://documentation.aimms.com/webui/status-bar.html>`_





