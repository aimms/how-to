Retrieve Solver Log Files in AIMMS Developer
==============================================

.. meta::
   :description: How to get log files for a particular solver in AIMMS IDE.
   :keywords: log, logfile, solver


Solvers can share information about the solution process up to a very detailed level.
To avoid overhead in generating information that is not inspected anyway, the AIMMS IDE defaults to not sharing any information.

Get started with log files
---------------------------

The first option is to set the option ``solver_listing_messages`` to ``all``. 
By just setting this option, a solver will generate a small log file with the name ``<solver name>.sta`` in the folder ``log``.
The first solve of an AIMMS session will create the file, subsequent solves will append to the file. 
To generate a log file, we used the :any:`/Articles/388/388-flow-shop` example with 14 jobs and 20 machines and solved it using CPLEX 12.8 on my desktop.

.. literalinclude:: resources/0_CPLEX_12.8.sta 
    :name: cplex 12.8.sta
    :language: none
    :linenos: 
    
.. todo::
      Does CBC requires the CBC specific option "status file"?
      How about other solver, is there a special additional setting needed as well?
    
Additional information and example
----------------------------------

For a MIP problem, a typical first setting to add is the option ``MIP display`` to ``Display each nth node``.
Thus I obtained a 95 line log file ``log\cplex 12.8.sta``.
For the sake of brevity, I only show the first and last five lines here.

.. literalinclude:: resources/1_CPLEX_12.8.sta 
    :name: cplex 12.8.sta (extended)
    :language: none
    :linenos: 
    :lines: 2-6
    
Last five lines:
    
.. literalinclude:: resources/1_CPLEX_12.8.sta 
    :name: cplex 12.8.sta (extended continued)
    :language: none
    :linenos: 
    :lineno-start: 90
    :lines: 90-94
    

Overview of options to try out first for logging
------------------------------------------------

This section presents, based on problem type and solver, a list of first options to be set for more information. 
This is not a list of options to improve performance, but a first step to obtain more information about the solution process so analysis of the solution process can be started.
    
LP Problems solved using Barrier
++++++++++++++++++++++++++++++++

+------------+------------------------------+-----------------------+
| Solver     | Option                       | Value                 |
+============+==============================+=======================+
| CPLEX      | Barrier display              | Normal                |
+------------+------------------------------+-----------------------+
| GUROBI     | Output file                  | yes                   |
+------------+------------------------------+-----------------------+
| CBC        | Status File                  | File                  |
+------------+------------------------------+-----------------------+

LP Problems solved using SIMPLEX
++++++++++++++++++++++++++++++++

+------------+------------------------------+-----------------------+
| Solver     | Option                       | Value                 |
+============+==============================+=======================+
| CPLEX      | Simplex display              | At refactorizations   |
+------------+------------------------------+-----------------------+
| GUROBI     | Output file                  | yes                   |
+------------+------------------------------+-----------------------+
| CBC        | Status File                  | File                  |
+------------+------------------------------+-----------------------+

MIP Problems
++++++++++++

+------------+------------------------------+-----------------------+
| Solver     | Option                       | Value                 |
+============+==============================+=======================+
| CPLEX      | MIP display                  | Display each nth node |
+------------+------------------------------+-----------------------+
| ODH-CPLEX  | Status display               | Yes                   |
+------------+------------------------------+-----------------------+
|            | MIP display                  | Display each nth node |
+------------+------------------------------+-----------------------+
| GUROBI     | Output file                  | yes                   |
+------------+------------------------------+-----------------------+
| CBC        | Status File                  | File                  |
+------------+------------------------------+-----------------------+

.. note:: For ODH-CPLEX, setting the option ``MIP display`` requires setting the option ``Status display`` to ``Yes``.

NLP Problems
++++++++++++

+------------+--------------------------------+-----------------------+
| Solver     | Option                         | Value                 |
+============+================================+=======================+
| BARON      | Keep results file              | On                    |
+------------+--------------------------------+-----------------------+
| CONOPT     | Status file display            | Iteration log         |
+------------+--------------------------------+-----------------------+
| IPOPT      | Status file                    | Yes                   |
+------------+--------------------------------+-----------------------+
| KNITRO     | Status file display            | Summary               |
+------------+--------------------------------+-----------------------+
| SNOPT      | Print output file              | On                    |
+------------+--------------------------------+-----------------------+


Further reading
---------------

The interpretation of log files is discussed in:

* LP  at ``https://pdfs.semanticscholar.org/b01f/ad44c20c372fdda95cbfb980c0d37302de07.pdf``
* MIP at ``https://pubsonline.informs.org/doi/10.1287/educ.2014.0130``


