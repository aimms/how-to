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
This profiler will provide you with information about how long each statement in an execution took, 
as well as how long the evaluation of the definition of a parameter took. 
More information about the profiler can be found in :doc:`creating-and-managing-a-model/debugging-and-profiling-an-aimms-model/the-aimms-profiler`.

You can also start the profiler using the function :aimms:func:`ProfilerStart`. 
It is common to make a call to this function in the initialization procedures so that you can always 
have the profiler available while developing an AIMMS application.

In addition, you can query the profiler using the procedure :aimms:func:`ProfilerCollectAllData`.

See all related functions in `AIMMS Function Reference - Development Support <https://documentation.aimms.com/functionreference/development-support/profiler-and-debugger/index.html>`_

Using the AIMMS Profiler has two rather minor disadvantages for measuring execution time:

#.  The AIMMS Profiler cannot be active when the AIMMS Debugger is active.

#.  The AIMMS Profiler does not support directly the measuring of a code fragment, 
    or the measuring of a piece of code executed when some condition is true.

.. However, the profiler functionality is not available in WebUI or when running an app from AIMMS PRO. 
.. To be able to give the end-users information about runtime, you can create a custom stopwatch functionality in your project.
.. When you are working as an AIMMS developer, one of the tools you have for this is the AIMMS profiler.

When fine control of measured execution time is needed, the `time functions of AIMMS can be used <https://documentation.aimms.com/functionreference/elementary-computational-operations/time-functions/>`_. 
To illustrate, and ease the use, an :download:`AIMMs 4.87 Example project<downloads/Stopwatch.zip>` and  can be downloaded.

Example project
------------------

Download the :download:`example AIMMS Project <downloads/Stopwatch.zip>` 

``MainExecution`` in this procedure shows the usage of the procedures built in the ``stopwatchLibrary`` library.

.. code-block:: aimms
    :linenos:

    ! Measuring time of some long running procedure.
    stopwatch::pr_start() ;
    pr_LongRunningProcedure  ;
    p_elapsedTime := stopwatch::fnc_elapsed();

    ! Reporting of that time, whereever.
    sp_runTime := formatString("Execution of procedure took %n seconds", p_elapsedTime );
    ! DialogMessage(sp_runTime);

In this procedure, we use functions from the ``stopwatchLibrary`` to measure the time it took to execute ``pr_LongRunningProcedure``. 
Line-6 is simply constructing a message using the stopwatch results. 

stopwatch Library
-------------------

Please :download:`Stopwatch Library<downloads/StopwatchLibrary.zip>` with prefix ``stopwatch`` and add it to your project.
This library exposes a procedure and a function:

#.  ``pr_start()`` Mark "now" as a starting point for measuring wall clock execution time.

#.  ``fnc_elapsed`` Return the wall clock time elapsed in seconds since the last call to ``pr_start()``

History
--------

* In 2011, Guido Diepen developed the first Stopwatch section and blogged about it.

* In 2022, Chris Kuip made a small library out of that section, to

  * ease even further the adding of the functionality to an application, and

  * to cater for multi timezone support, see option Use_UTC_forcaseandstartenddate






