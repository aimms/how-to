Use alternative MIP solutions with CPLEX solution pool
======================================================

.. meta::
   :description: Provide an equivalent AIMMS model to the AMPL model by Paul Rubin on K best solutions.
   :keywords: solution pool, CPLEX, MIP, model


In his blog post `K Best Solutions <http://orinanobworld.blogspot.com/2012/04/k-best-solutions.html>`_, 
Paul Rubin provides some information on how to obtain the K best solutions for a MIP model. 
One of the approaches he uses is the solution pool functionality of the CPLEX solver. 
In the follow-up post `K Best Solutions in AMPL/CPLEX <http://orinanobworld.blogspot.com/2012/05/k-best-solutions-in-amplcplex.html>`_ 
he provides the AMPL source code that shows how to use the solution pool feature of CPLEX from within AMPL.

In this article, the same binary knapsack problem used by Paul and with this demonstrate how to make use of this solution pool feature 
of the CPLEX solver within AIMMS to get alternative solutions for a MIP problem.

Please note that using this solution pool does not need to provide the actual K best solutions: 
it provides the optimal solution and some sub-optimal alternatives, but from some testing it shows 
that there might exist some better sub-optimal solutions than the K solutions provided by CPLEX.

To be able to use the solution pool feature of CPLEX, you will have to use the Generated Mathematical Programs (GMP) 
functionality in AIMMS because the solutions found by CPLEX will be stored in the solution repository of a GMP. 
You also will have to set some CPLEX specific project settings to instruct CPLEX to generate the solution pool. 
This all can be done with the following code:

.. code-block:: aimms

    !Set the CPLEX options that are related to the solution pool
    OptionSetValue( "pool_replacement_strategy", PoolReplacementStrategy) ;
    OptionSetValue( "pool_intensity", PoolIntensity) ;
    OptionSetValue( "population_limit", PoolLimit) ;
    OptionSetValue( "pool_capacity", k) ;
    OptionSetValue( "do_populate", 1) ;

    !This will solve the MIP problem and because of the do_populate
    !option it will also populate the solution pool based on the
    !values for the other options. AIMMS will store these solutions
    !in the solution repository of the GMP.
    gmp::instance::Solve( epGMP ) ;

    !Setting the pool capacity to a certain value will ensure only
    !that number of solutions are generated.
    while LoopCount <= gmp::Solution::Count( epGMP ) do
        !For each solution, we send the values from the solution
        !repository to the model and store the objective value
        gmp::Solution::SendToModel( epGMP , LoopCount )  ;
        SolutionPoolObjective(LoopCount) := totalValue ;
    endwhile ;

In order to show that the K solution as returned by the solution pool feature do not necessarily are the K best solutions for the MIP problem, 
we also implemented an additional approach that uses the AIMMS function ``GMP::Instance::AddIntegerEliminationRows`` to exclude each found solution and 
solve the problem again. 
By solving the problem K times while excluding the previously found solutions each time, you will exactly get the K best solutions for your MIP problem.

This approach was also suggested in one of the comments on the original post by Paul, and as noted by Paul in his reply, 
the disadvantage of using this approach is that can be a lot slower. 
The reason is that you have to solve the complete problem every time from the beginning after you eliminated a solution.

The following code demonstrates how to make use of the function GMP::Instance::AddIntegerEliminationRows to eliminate the last found solution. 
It will do this K times, each time solving the GMP indicated by the element parameter epGMP and storing the objective value in the parameter IntegerEliminationObjective:

.. code-block:: aimms

    !Solve the problem exactly k times
    while Loopcount <= k do
        gmp::Instance::Solve( epGMP ) ;

        !store the objective that corresponds to the current solution
        IntegerEliminationObjective( loopcount ) := totalValue ;

        !Instruct AIMMS to generate a new constraint that will
        !eliminate the solution at postion 1 of the solutions for
        !this GMP. The elimNo allows you to add these elimination
        !rows incrementally
        GMP::Instance::AddIntegerEliminationRows(
                GMP      :  epGMP, 
                solution :  1, 
                elimNo   :  LoopCount) ; 
    endwhile ;

You can download the full AimmsPack of the project containing the above two approaches from the link below. 
On the page that is displayed after opening the project, you can set some relevant CPLEX solution pool options and see what the effect on the solution pool is. Also, you can compare the results with the actual K best solutions as found by using the integer solution elimination approach to see what the quality of the solution pool is.


:download:`AIMMS project download <../Resources/C_Mathematical_Modeling/Images/177/Find-K-Best-solutions.zip>` 


.. include:: ../includes/form.def


