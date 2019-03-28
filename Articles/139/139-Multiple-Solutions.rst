Generate Multiple Solutions with CP Optimizer
=================================================

.. meta::
   :description: How to produce and retrieve multiple solutions in AIMMS.
   :keywords: multiple, solution, solve

.. note::

	This article was originally posted to the AIMMS Tech Blog.

Some of the solvers in AIMMS, including the CP Optimizer solver for Constraint Programming problems, support not only returning a single (optimal) solution, but also a pool of feasible solutions.

Some time ago, `Hakank's Home Page <http://www.hakank.org>`_ has done a thorough `A first look at AIMMS + CP (AIMMS with Constraint Programming) <http://www.hakank.org/constraint_programming_blog/2012/11/a_first_look_at_aimmscp_aimms_with_constraint_programming.html>`_. One of the remarks posted at the end was that it was not completely clear how to obtain multiple solutions from the CP Optimizer solver.

In this post we will show how to instruct the solver to create additional solutions and retrieve them to AIMMS after the solver execution is finished. We will use the N-Queens problems from Hakan's article as an example. 

:download:`Download example project <downloads/NQueens_converted.zip>` 

Solution storage limit
---------------------------

In the case CP Optimizer, you can influence the number of solutions that are created (and stored in the GMP solution repository) via the option ``solution_storage_limit``, which is an option specific to CP Optimizer. Find it under *Tools -> Project Options -> Specific solvers -> CPoptimizer -> General*.

By setting this option to a very large value, you can instruct the solver to store all solutions to the problem in the GMP solution repository. You can either change this setting explicitly in the project settings, or you can use the block statement to temporary use alternative project settings.  For example:

.. code-block:: aimms
   :caption: ``MaxNumberOfSolutions`` is a parameter with a very large initial value

   block where solution_storage_limit := MaxNumberOfSolutions  ;
      solve NQueensPlan;
   endblock;

After the solve is finished, you can use the AIMMS function ``GMP::Solution::SendToModel`` to transfer the solution values from the GMP solution repository to the variables in your project. This approach does require that for every variable X in your problem, you introduce a new parameter X2 that has the same index domain as X with an index added for indexing the solutions. In our example, this parameter is ``XValueInSolution``.

.. code-block:: aimms

   !Get the number of solutions
   NumberOfSolutions := gmp::Solution::Count('NQueensPlan') ;

   while LoopCount <= NumberOfSolutions do
      !For each solution that is stored in the GMP solution repository retrieve it
      gmp::Solution::SendToModel( 'NQueensPlan', LoopCount ) ;
   
      !Now store the values for this solution in a parameter that is
      !also indexed over the number of solutions.
      XValueInSolution( LoopCount , i ) := x(i) ;
   endwhile ;

Visualization of results
---------------------------

Another small modification made to this project is that it graphically shows the locations of the queens on a chessboard. This allows to quickly compare different solutions, as depicted below:

.. figure:: images/nqueens-solution.png
   :align: center

   Graphical representation of NQueens solution

Further reading
------------------

Other solvers that directly support working with a solution pool are Baron and CPLEX. For Baron, you can influence the behavior with the project setting ``number_of_best_solutions`` and for CPLEX, you can modify the behavior by setting the project setting ``do_populate`` to the value 1.

* :doc:`/Articles/177/177-alternative-mip-solutions-with-cplex` 

Note that with solvers that don't directly support a solution pool but do support the Incumbent callback, you can manually create the solution repository by storing each solution found by using the Incumbent callback.

.. include:: /includes/form.def


