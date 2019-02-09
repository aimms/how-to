Solve MINLP problems with AOA
==============================

.. Chris: Should have refernce to latest comparison

For solving Mixed Integer Nonlinear Programming (MINLP) problems AIMMS offers, besides the solvers BARON and KNITRO, the AIMMS Outer Approximation algorithm, or AOA for short.

There exist two versions of the AOA algorithm in AIMMS. The old version is available as a solver which calls the module OuterApproximation and was developed before GMP functionality was added to AIMMS. The new version uses GMP functions and has been implemented in the module GMPOuterApproximation. You can install this system module via *Menu > Settings > Install System Module* and select the GMP Outer Approximation Module to be installed. GMP-AOA is not a solver and cannot be called using the normal �solve� statement. Instead you should use:

.. code-block:: aimms

 ! First we must generate the GMP for our MathProgram.
 myGMP := GMP::Instance::Generate( myMP ) ;

 ! The GMP is passed as argument to the main procedure of GMP-AOA.
 GMPOuterApprox::DoOuterApproximation( myGMP );

An example can be found in this ZIP file.

:download:`/Resources/192-solve-minlp-with-outer-approximation/alan.zip`

There are several reasons why you should use GMP-AOA instead of old AOA. First, the GMP-AOA algorithm offers more possibilities to customize the algorithm to your needs, for example by using functions from the GMP library.

Second, the GMP version can be used in combination with the nonlinear presolver which may reduce the size of the model and tighten the variable bounds which likely help the AOA algorithm to find a better solution or improve its performance. From AIMMS 3.12 onwards GMP-AOA by default starts by calling the nonlinear presolver.

Third, for non-convex problems AOA might sometimes have difficulties in finding a good feasible solution. In that case it might help to combine the AOA with the multi-start algorithm. The way to do this has been explained in a white paper that describes GMP-AOA. This paper is available from our web site:

http://www.aimms.com/downloads/white-papers

Old AOA cannot be combined with the nonlinear presolver nor the multi-start algorithm.

.. note::
    
     In the special case that the MINLP problem contains only convex quadmratic and/or second-order cone constraints also linear solvers like CPLEX or GUROBI can be used.