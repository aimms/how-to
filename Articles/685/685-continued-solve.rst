Implementing Continued Solves
===============================

.. image:: https://img.shields.io/badge/Zip-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/685-continued-solve/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/Repository-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/685-continued-solve

.. image:: https://img.shields.io/badge/AIMMS-25.5-white?style=for-the-badge&labelColor=009B00&color=00D400

.. meta::
    :keywords: AIMMS, GMP, Generated Mathematical Program, MIP, Continued Solve, solver status, optimality gap, CPLEX, Gurobi, optimization search
    :description: Learn how to use the AIMMS GMP library to resume interrupted MIP solves. Preserve search progress, adjust tolerances, and reduce total solve time.

In complex Mixed-Integer Programming (MIP) applications, modelers often face a trade-off between solution quality and computational time. 
While many data instances solve quickly, "tough" instances may fail to reach a desired optimality gap within a strict time limit.

Standard AIMMS ``solve`` statements regenerate the mathematical program and restart the solver search from scratch if called a second time. 
**By utilizing the Generated Mathematical Program (GMP) library, you can take programmatic control over the solution process.** 
This allows you to resume an existing search with updated tolerances or limits, 
preserving the progress made during the initial solve and reducing total execution time.

This article demonstrates how to resume an interrupted MIP solve by reusing the existing Generated Mathematical Program (GMP) instance. Please download the example project to follow along this article.

Prerequisites
-------------

*   A Mixed-Integer Mathematical Program (MIP) formulated in AIMMS.
*   A solver that supports continued search (e.g., CPLEX or Gurobi).
*   Familiarity with solver limits (Time Limits and Optimality Gaps).

The GMP Advantage
------------------------------

To continue a solve, you must shift from using the standard `solve statement <https://documentation.aimms.com/language-reference/optimization-modeling-components/solving-mathematical-programs/index.html>`_ 
to `advanced methods <https://documentation.aimms.com/language-reference/optimization-modeling-components/implementing-advanced-algorithms-for-mathematical-programs/index.html>`_ 
using the `GMP <https://documentation.aimms.com/functionreference/algorithmic-capabilities/the-gmp-library/index.html>`_ functions. 

This approach decouples the model generation from the solution process. By keeping the generated instance in memory, the solver maintains its internal state—including the best-known bounds and the search tree—between successive calls.

Example Workflow: "Solve and Adjust"
------------------------------------

The following pattern demonstrates how to attempt a strict solve and, upon reaching a time limit, relax the optimality tolerance to find a "good enough" solution without starting over.

.. code-block:: aimms 
    :linenos:

    ! 1. Generate the GMP instance from the symbolic Mathematical Program
    myGMP := GMP::Instance::Generate( FlowShopModel );

    ! 2. Execute the initial solve
    GMP::Instance::Solve( myGMP );

    ! 3. Check if the solver stopped due to a time or resource limit
    if GMP::Solution::GetSolverStatus( myGMP, 1 ) = 'ResourceInterrupt' then
        
        ! 4. Relax the Relative Optimality Tolerance (e.g., to 4%)
        GMP::Instance::SetOptionValue( myGMP, "MIP Relative Optimality Tolerance", 0.04 );
        
        ! 5. Extend the Time Limit (e.g., to an additional 600 seconds)
        GMP::Instance::SetOptionValue( myGMP, "Time Limit", 600 );
        
        ! 6. Solve again: the solver resumes the previous search
        !    NOTE: No regeneration of the GMP instance takes place here
        GMP::Instance::Solve( myGMP );
        
    endif;

Core Functions Explained
^^^^^^^^^^^^^^^^^^^^^^^^^^

*   `GMP::Instance::Generate <https://documentation.aimms.com/functionreference/algorithmic-capabilities/the-gmp-library/gmp_instance-procedures-and-functions/gmp_instance_generate.html>`_ : Creates a memory-resident representation of your model. 
    Unlike a standard solve, this instance persists until it is explicitly deleted.
*   `GMP::Solution::GetSolverStatus <https://documentation.aimms.com/functionreference/algorithmic-capabilities/the-gmp-library/gmp_solution-procedures-and-functions/gmp_solution_getsolverstatus.html>`_ : Retrieves the status of the last solve attempt. 
    A status of ``ResourceInterrupt`` typically indicates the time limit was reached before the gap was closed.
*   `GMP::Instance::SetOptionValue <https://documentation.aimms.com/functionreference/algorithmic-capabilities/the-gmp-library/gmp_instance-procedures-and-functions/gmp_instance_setoptionvalue.html>`_: Modifies solver settings specifically for this generated instance. 
    These changes are applied directly to the solver's current environment.
*   Continued Search: Because the ``myGMP`` object remains in memory, 
    for solvers that support search continuation (such as CPLEX and Gurobi), 
    the second Solve call resumes the existing search and heuristic results.

Conclusion  
----------------

Using the GMP library to manage the solution process provides several advantages over traditional modeling methods:

*   **Efficiency**: Eliminates the overhead of regenerating the matrix for subsequent solves.
*   **State Retention**: Allows the solver to keep the branch-and-bound tree, preventing the loss of progress when adjusting parameters.
*   **Granular Control**: Enables sophisticated "Solve-and-Adjust" logic where the modeler can programmatically respond to different solver outcomes.

By implementing this pattern, you ensure that your optimization engine spends 
its time searching for solutions rather than repeating work it has already performed.

.. admonition:: Acknowledgements

    The author would like to acknowledge Marcel Hunting for pointing out the power of GMP, 
    namely to give the Modeler control over the solution process.
