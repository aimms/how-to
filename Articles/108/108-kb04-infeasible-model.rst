Debug an Infeasible Model
==========================
After executing a solve statement in your model, AIMMS returns with the message that your model is infeasible.

There are several ways to find out why your model is infeasible. This article presents a few methods:

* Math Program Inspector
* Infeasibility Finder
* Constraint listing
* Infeasibility analysis
* AIMMS Presolver


Math Program Inspector
--------------------------
The Math Program Inspector contains a collection of tools that can help you to find the cause of errors in your linear model. See the :doc:`creating-and-managing-a-model/the-math-program-inspector/index`.


Infeasibility Finder
---------------------
You can activate the Infeasibility Finder of the solver. This is an option that writes extra information to the listing file when the model is infeasible. 

The Infeasibility Finder is available for the solvers CPLEX, GUROBI, XA, BARON and CONOPT. (Check the Help on this option for more information.) 

1. Set the following option in AIMMS:

           *Settings > Project Options... > Solvers general > Standard reports > Solution > Infeasibility finder: On*


2. After setting this option, run your model again. 

3. Open the listing file found at *File > Open > Listing File*, or in the log-folder of your AIMMS project (``*.lis``). 


Constraint listing
-------------------
You can create a constraint listing file to see the actual generated constraints and to see which ones are infeasible (by searching on ``****``). 

1. Set the following option in AIMMS:

           *Solvers general > Standard reports > Constraints > Constraint listing: At an infeasible solve*

2. After setting this option, run your model again. 

3. Open the listing file found at *File > Open > Listing File*, or in the log-folder of your AIMMS project (``*.lis``). 


Infeasibility analysis
----------------------
In the chapter Solving Mathematical Programs in the AIMMS Language Reference, there is a section 'Infeasibility analysis'. In this section another way to find infeasibilities is explained. The used method is based on adding excess variables to your model and AIMMS offers support to automatically extend your mathematical program with excess variables during the generation of the matrix for the solver. 

See also :doc:`optimization-modeling-components/solving-mathematical-programs/index`.


AIMMS Presolver
---------------
If your model is nonlinear then you can let the AIMMS Presolver try to detect the cause of the infeasibility. 

1. Set the following options in AIMMS:

           *Solvers general > AIMMS Presolver > Nonlinear Presolve: On*
           *Solvers general > AIMMS Presolver > Display Infeasibility Analysis: On*

2. After setting this option, run your model again. 

3. Open the listing file found at *File > Open > Listing File*, or in the log-folder of your AIMMS project (``*.lis``).  

See also :doc:`optimization-modeling-components/advanced-methods-for-nonlinear-programs/index`.
