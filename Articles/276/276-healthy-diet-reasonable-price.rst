A Healthy Diet for a Reasonable Price
========================================

.. meta::
   :description: Compare multi objective approach to approach directly solving objectives
   :keywords: multi objective, diet, CPLEX, WebUI

Trying to loose some weight, a healthy diet means for me that it should be low on calories.
A reasonable price means for me not that the absolute minimum is a must, but I do not want much above it either.
So I have two objectives: minimizing price, and minimizing calories of my diet.
Of course, minimum/maximum requirements on nutrients such as proteins should be met.

Approach
-----------

Use the multi objective feature available since AIMMS 4.65 and CPLEX 12.9. 

.. code-block:: aimms
    :linenos:

    Procedure SolveMultiObj {
        Body: {
            ep_GMP := gmp::Instance::Generate( DietProblem );
            
            p_retcode := GMP::Column::SetAsMultiObjective(
                GMP      :  ep_GMP, 
                column   :  TotalCost, 
                priority :  2, 
                weight   :  1, 
                abstol   :  0, 
                reltol   :  0.1);
            if not p_retcode then raise error "Unable to set TotalCost as an objective" ; endif ;
            
            p_retcode := GMP::Column::SetAsMultiObjective(
                GMP      :  ep_GMP, 
                column   :  TotalCalories, 
                priority :  1, 
                weight   :  1, 
                abstol   :  0, 
                reltol   :  0.0);
            if not p_retcode then raise error "Unable to set TotalCalories as an objective" ; endif ;
            
            GMP::Instance::Solve( ep_GMP );
        }
    }
    
There are several remarks on the above:

#. Because the ``TotalCost`` objective has a higher priority value, it will be solved first.

#. Because the ``reltol`` argument on line 11 has value 0.1, subsequent solves will not increase the total cost by more than 10%.

To be able to compare the multi objective procedure to traditional single objective solves, there are also two solution procedures in this application:

#. ``MainExecution``, a traditional single objective solve minimizing total cost.

#. ``SolveMinCalo``, a traditional single objective solve minimizing total calories.

Results
----------

The application has both a WinUI and a WebUI interface. 
Because I like the WebUI more, I used that one to make the following screenshots.

.. figure:: images/mintotcost.png

    Results after minimizing total cost
    
.. figure:: images/mintotcalo.png

    Results after minimizing total calories
    
.. figure:: images/multiobj.png

    Results after running multi objective
    
The objectives are summarized in the table below:

+--------------------------+-------------+----------------+
|                          | Cost        | Calories       |
+--------------------------+-------------+----------------+
| Minimize total cost      |  21.85      |  2698.40       |
+--------------------------+-------------+----------------+
| Minimize total calories  |  29.80      |  2546.40       |
+--------------------------+-------------+----------------+
| Multi objective          |  23.85      |  2576.40       |
+--------------------------+-------------+----------------+

As you can see, the objective values of the multi objective are not as good as the individual objectives values, but they provide a good balance.

Also interesting is the log that CPLEX provides in the file ``log/cplex 12.9.sta``. 
(Obtaining such a log is discussed in :doc:`../13/13-Solver-Logging-IDE`).

.. code-block:: none
    :linenos:

    Solve problem 'DietProblem' with 9 rows, 15 columns (0 binaries, 9 generals), and 83 nonzeros.
    
    MIP - Integer optimal solution:  Objective = 2.1849999998e+01
    Solution time = 0.09 sec.  Iterations = 20  Nodes = 0
    
    
    Solve problem 'MinCaloDietProblem' with 9 rows, 15 columns (0 binaries, 9 generals), and 83 nonzeros.
    
    MIP - Integer optimal solution:  Objective = 2.5464000000e+03
    Solution time = 0.02 sec.  Iterations = 13  Nodes = 0
    
    
    Solve problem 'DietProblem' with 8 rows, 14 columns (0 binaries, 9 generals), and 73 nonzeros.
    
    Multi-objective solve log . . .
    
    Starting optimization #1 with priority 2.
    
    
    
    Finished optimization #1 with priority 2.
    Objective =  2.1849999998e+01,  Nodes = 0,  Time = 0.05 sec. (0.45 ticks)
    Cumulative statistics:  Nodes = 0,  Time 0.05 sec. (0.45 ticks)
    
    
    Starting optimization #2 with priority 1.
    
    
    
    Finished optimization #2 with priority 1.
    Objective =  2.5764000000e+03,  Nodes = 0,  Time = 0.02 sec. (0.43 ticks)
    Cumulative statistics:  Nodes = 0,  Time 0.06 sec. (0.89 ticks)
    
    
    
    MIP - Multi-objective optimal
    Solution time = 0.08 sec.  Iterations = 43  Nodes = 0

A breakdown of above log:

#. lines 1 - 4 for the first solve (minimize total cost)

#. lines 7 - 10 for the second solve (minimize total calories)

#. lines 13 - 37 for the multi objective solve.

The project can be downloaded :download:`here <model/DietProblem.zip>` 

.. include:: /includes/form.def