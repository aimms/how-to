Set Stop Criteria for Math Programs 
==============================================

.. meta::
   :description: Early stopping of the solution process of linear programming models.
   :keywords: GAP, iteration limit, time limit, MIP Relative Optimality Tolerance, maximum number of nodes


When you solve a Mathematical Program in AIMMS, the default behavior is that AIMMS will let the solver solve the problem to (local) optimality. There might be situations where you want to stop the solve procedure after a set time period, or once a solution within x % of the best value is available. This article will show you how to apply different stop criteria to a ``solve`` statement in AIMMS.


Available Stop Criteria
---------------------------

There are a number of options that you can set to control the behavior of a solve procedure. Some of these options depend on the solver and some depend on the type of problem (e.g. you do not have an optimality gap as a stop criteria when solving a LP). The following are some examples:

#. Iteration limit: Limit the number of iterations the solver is allowed to perform.
#. Time limit: Limit the number of seconds the solver is allowed to work on solving the problem.
#. MIP Absolute Optimality Tolerance: The solve procedure stops if the solver can guarantee that the current best solution is within MIP Absolute Optimality Tolerance of the global optimum. This stop criteria is valid only for MIP and MIQP problems.
#. MIP Relative Optimality Tolerance: The solve procedure stops if the solver can guarantee that the current best solution is within 100 x MIP Relative Optimality Tolerance percent of the global optimum. This stop criteria is valid only for MIP and MIQP problems.
#. Maximal Number of Nodes: This option sets the maximum number of nodes solved before the algorithm terminates without reaching optimality. This stop criteria only is valid for the CPLEX solver.

Please check the help for the stop criteria options to see for which solver and which problem type they are applicable.

Setting Stop Criteria
-------------------------

All of these stop criteria are options in your AIMMS project. This means that there are multiple ways of modifying them:

* Modify the option in your Project Options. This will result in the same settings holding for all solve statements. You will most of these options in *Settings -> Project options.. -> Solvers general -> Stop criteria*

* Use the ``OptionSetValue`` or ``OPTION`` functions to modify the values of the options in a procedure. ``OptionSetValue`` will let you change the value for a single option while the ``OPTION`` statement lets you change multiple options in a single statement. 

.. code-block:: aimms

   OptionSetValue(OptionName : "time_limit" , NewValue   : 10 );
   option time_limit := 10, MIP_Relative_Optimality_Tolerance := 0.1

* Use the ``where`` keyword in a ``solve`` statement to modify an option for that particular solve only. For example, the solve process triggered by the below statement will be terminated after 10 seconds of execution or when the MIP_Relative_Optimality_Tolerance = 0.1, whichever happens first. 

.. code-block:: aimms

   solve MathProg where MIP_Relative_Optimality_Tolerance := 0.1, time_limit := 10 ;
   



