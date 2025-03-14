Measure Execution Time
==========================

.. meta::
   :description: How to measure efficiency of procedures with StopWatch function.
   :keywords: efficient, time, execute, stopwatch, watch, clock

There are situations where you would like to know the duration of execution of an AIMMS code-block. 
In AIMMS Developer, you can do this easily by using the built-in `Profiler <https://documentation.aimms.com/user-guide/creating-and-managing-a-model/debugging-and-profiling-an-aimms-model/the-aimms-profiler.html>`_, 
accessed through :menuselection:`Tools > Diagnostic Tools > Profiler`. 
This profiler will provide you with information about how long each statement in an execution took, 
as well as how long the evaluation of the definition of a parameter took. 

You can also start the profiler using the function :aimms:func:`ProfilerStart`. 
It is common to make a call to this function in the initialization procedures so that you can always 
have the profiler available while developing an AIMMS application.

In addition, you can query the profiler using the procedure :aimms:func:`ProfilerCollectAllData`.

However, using the `AIMMS Profiler <https://documentation.aimms.com/user-guide/creating-and-managing-a-model/debugging-and-profiling-an-aimms-model/the-aimms-profiler.html>`_ has two rather 
minor disadvantages when measuring execution time:

#.  The AIMMS Profiler cannot be active when the AIMMS Debugger is active.

#.  The AIMMS Profiler does not support directly the measuring of a code fragment, 
    or the measuring of a piece of code executed when some condition is true.

When fine control of measured execution time is needed, the `time functions of AIMMS can be used <https://documentation.aimms.com/functionreference/elementary-computational-operations/time-functions/>`_. 
Based on those functions a `Stopwatch Library <https://how-to.aimms.com/Articles/574/574-stopwatch-library.html>`_ was created. 

Example
-------

The `Stopwatch Library <https://how-to.aimms.com/Articles/574/574-stopwatch-library.html>`_ is used on `Traveling Salesman <https://how-to.aimms.com/Articles/397/397-traveling-salesman.html>`_ 
to measure the execution time of each type of solve. 
The `Traveling Salesman <https://how-to.aimms.com/Articles/397/397-traveling-salesman.html>`_ procedures that use stopwatch functions are:

* ``pr_findInitialTour``

* ``pr_improveTourCyclic``

* ``pr_improveTourSimultaneous``

* ``pr_solveModel``

* ``pr_solveRelaxedModel``

And the method to use is simple:

.. code-block:: aimms
   :linenos:

   stopwatch::pr_start() ;
   
   ! Any code

   p_elapsedTime := stopwatch::fnc_elapsed();

.. seealso:: 
   * :doc:`development-support/profiler-and-debugger/index`
   * :doc:`creating-and-managing-a-model/debugging-and-profiling-an-aimms-model/the-aimms-profiler`
