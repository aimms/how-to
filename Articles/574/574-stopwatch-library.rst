Stopwatch Library
==========================

.. meta::
   :description: How to measure efficiency of procedures with StopWatch function.
   :keywords: efficient, time, execute, stopwatch, watch, clock

.. image:: images/icons8-stopwatch-512.png
   :scale: 30
   :align: right
   :alt: Measure Execution Time

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






