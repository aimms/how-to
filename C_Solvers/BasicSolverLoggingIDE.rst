How to obtain information from the solver within the AIMMS IDE?
=====================================================================

Solvers can share information about the solution process up to a very detailed level. It is up to you, to ask for the level of detail.
To avoid overhead in generating information that is not inspected anyway, AIMMS IDE defaults to not asking for any information.
This article details how to get some information, and provides cues to get very detailed information. 
It is up to you, as a model developer, using the AIMMS IDE, to limit the information provided to a level of detail that suits you.

AIMMS' generic information
--------------------------

To obtain AIMMS' generic information, please set the options ``major_messages`` and ``time_messages`` both to ``on``.

    .. code-block:: none

        Calling CPLEX 12.8 for MIP of 138 rows, 120 columns (49 integer) and 1214 non-zeros.
        Calling CPLEX 12.8 to solve MIP FlowShopModel minimize TimeSpan.
        There is 0 Kb in use by CPLEX 12.8.
        After 224 iterations the minimum found for TimeSpan is 181.
        Solving the problem required 0.156 seconds.
        
        
Solver extended information in the message window:
--------------------------------------------------

This can be obtaine by sbsequently setting the option ``solver_window_messages`` to ``all``, and you'll get

    .. code-block:: none

        Calling CPLEX 12.8 for MIP of 138 rows, 120 columns (49 integer) and 1214 non-zeros.
        Calling CPLEX 12.8 to solve MIP FlowShopModel minimize TimeSpan.
        Generation required 0.016 seconds.

        Solve problem 'FlowShopModel' with 137 rows, 119 columns (49 binaries, 0 generals), and 1205 nonzeros.

        MIP - Integer optimal solution:  Objective = 1.8100000000e+002
        Solution time = 0.06 sec.  Iterations = 224  Nodes = 0

        Memory in use by CPLEX 12.8.0.0: 0 bytes.
        There is 0 Kb in use by CPLEX 12.8.
        After 224 iterations the minimum found for TimeSpan is 181.
        Solving the problem required 0.063 seconds.

Solver information in a file:
-----------------------------        
        
If you prefer to have the solver output in a file, please set the option ``solver_listing_messages`` to ``all``. 
In our example the output is written to the file ``log\cplex 12.8.sta`` with the following contents:

    .. code-block:: none

        Solve problem 'FlowShopModel' with 137 rows, 119 columns (49 binaries, 0 generals), and 1205 nonzeros.

        MIP - Integer optimal solution:  Objective = 1.8100000000e+002
        Solution time = 0.07 sec.  Iterations = 224  Nodes = 0

Note that each specific solver has logging options to provide more detailed information; just check out the various option settings in the specific solver categories. Typical categories are Logging and Reporting.

        
.. include:: ../includes/form.def