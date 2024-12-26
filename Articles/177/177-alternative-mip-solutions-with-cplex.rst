Use Alternative MIP Solutions with CPLEX Solution Pool
======================================================

.. meta::
   :description: Provide an equivalent AIMMS model to the AMPL model by Paul Rubin on K best solutions.
   :keywords: solution pool, CPLEX, MIP, model

In his blog post `K-best Solutions <http://orinanobworld.blogspot.com/2012/04/k-best-solutions.html>`_, Paul Rubin provides some information on how 
to obtain the **K-best** solutions for a MIP model. 
One of the approaches he discusses is the solution pool functionality of CPLEX. 
In this article, we demonstrate how to use the solution pool feature of CPLEX in AIMMS using the the same binary knapsack problem used by Paul.

Note that using this solution pool does not necessarily provide the K best solutions. It provides the optimal solution and some sub-optimal alternatives, but testing shows that there might exist some sub-optimal solutions which are better than the K-1 sub-optimal solutions provided by CPLEX. 

Please use the following project to follow this article: 
   
   :download:`K-best Solutions <downloads/Find-K-Best-solutions.zip>` 

On the page that is displayed after opening the project, you can set some relevant CPLEX solution pool options and see what the effect on the solution pool is. You can also compare the results with the actual K best solutions as found by using the integer solution elimination approach to see what the quality of the solution pool is. Both these approaches are discussed below.

Implementing in AIMMS
--------------------------

To use this feature of CPLEX in AIMMS, you will have to use the ``Generated Mathematical Programs (GMP)`` library because the solutions found by CPLEX will be stored in the solution repository of a ``GMP``. 
You will also need to set some CPLEX specific project options to instruct CPLEX to generate the solution pool.  This all can be done with the following code:

.. code-block:: aimms
   :linenos:

    !Set the CPLEX options that are related to the solution pool
 
    OptionSetValue( "pool_replacement_strategy", PoolReplacementStrategy) ;
    OptionSetValue( "pool_intensity", PoolIntensity) ;
    OptionSetValue( "population_limit", PoolLimit) ;
    OptionSetValue( "pool_capacity", k) ;
    OptionSetValue( "do_populate", 1) ;
 
    /*This will solve the MIP problem and because of the do_populate option it will also populate 
    the solution pool based on the values for the other options. AIMMS will store these solutions 
    in the solution repository of the GMP. */
 
    gmp::instance::Solve( epGMP ) ;
 
    /*Setting the pool capacity to a certain value will ensure only that number of solutions are generated. */
 
    while LoopCount <= gmp::Solution::Count( epGMP ) do
 
       /*For each solution, we send the values from the solution repository to the model 
       and store the objective value*/
       gmp::Solution::SendToModel( epGMP , LoopCount )  ;
       SolutionPoolObjective(LoopCount) := totalValue ;
    endwhile;

Retrieving the K-best Solutions
----------------------------------

In order to show that the K solutions returned by the solution pool feature are not necessarily the K best solutions for the MIP problem,  we implemented an additional approach that uses the AIMMS function :any:`GMP::Instance::AddIntegerEliminationRows` to exclude each found solution and solve the problem again. By solving the problem K times while excluding the previously found solutions each time, you will exactly get the K best solutions for your MIP problem.

This approach was also suggested in one of the comments on the original post by Paul, and as noted by Paul in his reply, the disadvantage of using this approach is that can be a lot slower. The reason is that you have to solve the complete problem every time from the beginning after you eliminated a solution.

The following code demonstrates how to make use of the function :any:`GMP::Instance::AddIntegerEliminationRows` to eliminate the last found solution. It will do this K times, each time solving the ``GMP`` indicated by the element parameter ``epGMP`` and storing the objective value in the parameter ``IntegerEliminationObjective``:

.. code-block:: aimms
   :linenos:

    !Solve the problem exactly k times
    
    while Loopcount <= k do
       gmp::Instance::Solve( epGMP ) ;
    
       !store the objective that corresponds to the current solution
       IntegerEliminationObjective( loopcount ) := totalValue ;
    
       /*Instruct AIMMS to generate a new constraint that will eliminate the solution at postion 1 of the
       solutions for this GMP. The elimNo allows you to add these elimination rows incrementally*/
    
       GMP::Instance::AddIntegerEliminationRows(
                GMP      :  epGMP, 
                solution :  1, 
                elimNo   :  LoopCount) ; 
    endwhile ;




