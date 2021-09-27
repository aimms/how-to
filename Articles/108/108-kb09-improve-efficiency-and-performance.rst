Analyze Long Execution Time
======================================

.. meta::
    :description: There are several types, each with its own use.
    :keyword: compound set, relation, set, database, calendar

You can find the origin of large execution times with the AIMMS profiler. To decrease the execution time, you could use a faster solver, or handle the cause of the large execution time.


Profiler
----------

In general it is good to have a look at the execution time of your model during development, even if it doesn't seem to be a problem for you at the moment. 
It is possible that it will become a problem when using larger data sets, or that your customer will have problems because they work on slower computers.

For checking on execution time AIMMS is equipped with a profiler. You can activate the profiler by selecting Profiler from the Tools – Diagnostic Tools menu. 
When the profiler is active there is a Profiler menu item and there are extra columns in the attribute windows of the identifiers. 
Now, when you run a procedure, for each statement that is executed the execution time is written in the extra columns. 
You can also see an overview of the execution times by selecting Results Overview from the Profiler menu.

For those statements that have a large execution time you should try to decrease the execution time. Maybe some statements are unnecessarily executed dense. 
Adding domain restrictions may reduce execution time in two ways. First, less tuples need to be calculated, and second, less tuples need to be generated in a math program. 
For more information about decreasing execution times, read the AIMMS Language Reference, part Sparse Execution. 

.. seealso:: 

  - :doc:`creating-and-managing-a-model/debugging-and-profiling-an-aimms-model/the-aimms-profiler`

When solving a MIP problem causes the long execution time, please take a look at the knowledge base article :doc:`../../Articles/108/108-kb07-speed-up-mip-solve`.

Change/Upgrade solver
---------------------

Another way that can decrease the computational time is to use a better solver or to use a newer version of the current used solver. 




