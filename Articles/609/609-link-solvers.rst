How to Link a Solver to AIMMS
=============================

If you have a license for a solver and your AIMMS license has the link to that solver (a so-called 'link-only' license), you can add that solver to your AIMMS. 

Currently, AIMMS provides links to the following solvers:

* CPLEX
* GUROBI
* PATH
* SNOPT
* MINOS

General Procedure
-----------------

Before linking a solver to AIMMS, ensure a solver license is valid and installed correctly. You should be able (and allowed) to run the solver stand-alone.

Start AIMMS. Go to :menuselection:`Settings > Solver Configuration` and add the solver.

Test if the solver is added correctly.


Linking CPLEX to AIMMS
------------------------

1. Make sure your CPLEX license is installed correctly, and you can run CPLEX stand-alone.

2. Go to :menuselection:`Settings > Solver Configuration` and add the CPLEX solver ``libcpx*.dll``. 

Linking GUROBI to AIMMS
-----------------------

1. Make sure your GUROBI license is installed correctly, and you can run GUROBI stand-alone.

2. Set the environment variable ``GRB_LICENSE_FILE`` to point to the exact location of the license file. 
For example, if the license file ``gurobi.lic`` is in the directory ``C:\Gurobi``, then set ``GRB_LICENSE_FILE`` to ``C:\Gurobi\gurobi.lic``.

3. Go to :menuselection:`Settings > Solver Configuration` and add the GUROBI solver ``libgrb*.dll``.

Linking PATH to AIMMS
-------------------------

1. Make sure your PATH license is installed correctly, and you can run PATH stand-alone.

2. Copy the file ``path*.dll`` to the subdirectory ``Solvers`` in AIMMS.

3. Go to :menuselection:`Settings > Solver Configuration` and add the PATH solver ``libpath*.dll``.

Linking SNOPT or MINOS to AIMMS
-----------------------------------

Go to :menuselection:`Settings > Solver Configuration` and add the solver ``libsnopt*.dll`` or ``libminos.dll``.
(You don't have to copy a solver DLL file to the installation directory of AIMMS because the AIMMS installation already includes these files.)