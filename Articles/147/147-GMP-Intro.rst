Overview: Generated Mathematical Programs
==========================================

.. meta::
   :description: Getting started with Generated Mathematical Programs (GMP) in AIMMS.
   :keywords: gmp, mathematic, program


In the simplest form solving a Mathematical Program identifier is done by using the intrinsic :ref:`solve statement <solve>` of AIMMS:

.. code-block:: aimms

    solve MathProgram ;

For the majority of the AIMMS modelers, this suffices for their needs. 
Whenever you want to have more advanced control over what happens, you have to start working with Generated Mathematical Programs (GMP). 
With GMP's, you have full control over the constraint matrix: you can edit coefficients and add new constraints and variables.

The initial transition from using only standard Mathematical Program identifiers to using GMP functions is very easy and boils down to introducing an additional element parameter and changing the original solve statement into two statements: one for generating the GMP and one for solving the GMP. The element parameter you need is the following:

.. code-block:: aimms

    ElementParameter ep_GenMathProgram {
        Range: AllGeneratedMathematicalPrograms;
    }

The original one-line solve statement now needs to be converted to the following two lines:

.. code-block:: aimms

    ep_GenMathProgram := gmp::Instance::Generate(MathProgram) ;
    gmp::Instance::Solve(ep_GenMathProgram ) ;


The first line generates the GMP and the second line solves this GMP. 
With these two minor additions, you have moved to using GMP's. 
For more information about what you can do with the GMP's, you can take a look at :doc:`optimization-modeling-components/implementing-advanced-algorithms-for-mathematical-programs/index` 
and :doc:`algorithmic-capabilities/the-gmp-library/index`.

Additional Information
----------------------- 

The exact workings of :any:`GMP::Instance::Solve` statement can actually be emulated in a couple of lower-level GMP statements. 
If you look at the Language Reference, you will see that you can emulate its behavior with the following calls:

.. code-block:: aimms

    ! Create a solver session for ep_GenMathProgram, which will create an element
    ! in the set AllSolverSessions, and assign the newly created element
    ! to the element parameter session.
    ep_session := GMP::Instance::CreateSolverSession(ep_GenMathProgram);

    ! Copy the initial solution from the variables in AIMMS to
    ! solution number 1 of the generated mathematical program.
    GMP::Solution::RetrieveFromModel(ep_GenMathProgram,1);

    ! Send the solution stored in solution 1 to the solver session
    GMP::Solution::SendToSolverSession(ep_session, 1);

    ! Call the solver session to actually solve the problem.
    GMP::SolverSession::Execute(ep_session);

    ! Copy the solution from the solver session into solution 1.
    GMP::Solution::RetrieveFromSolverSession(ep_session, 1);

    ! Store this solution in the AIMMS variables and constraints.
    GMP::Solution::SendToModel(ep_GenMathProgram, 1);





