Analyze Infeasible Nonlinear Problems
=======================================

.. meta::
   :description: How to evaluate infeasible results to nonlinear problems.
   :keywords: infeasible, nonlinear, debug, iis, presolver

.. note::

	This article was originally posted to the AIMMS Tech Blog on October 20, 2014 by Marcel Hunting.

.. sidebar:: Sherlock Holmes

    .. image:: /Resources/C_Solvers/Images/130/sherlock.png
    	:scale: 50 %
    	:align: center

The AIMMS webinar of August (2014) dealt with "Analyzing infeasible Problems in AIMMS". In case you missed it, the recording can be found `here <https://aimms.com/english/developers/resources/webinars/webinars-demand/analyzing-infeasible-problems-aimms/>`_. As shown in the webinar, one way to investigate an infeasible problem is by calculating an **Irreducibly Inconsistent System** (IIS). An IIS is a subset of all constraints and variables that contains an infeasibility. The "Irreducibly" part implies that the subset is as small as possible. Unfortunately, the IIS could only be calculated for linear (and quadratic) problems. So how about nonlinear problems?

AIMMS 4.1 introduces version 14 of **BARON**. BARON is a solver for solving non-convex optimization problems to global optimality. Version 14 can calculate an IIS for infeasible nonlinear problems including problems with integer variables. To let BARON calculate an IIS, the option *Compute IIS* should be switched on. BARON offers several algorithms for calculating an IIS, the fastest being a heuristic that "only" calculates an IS (as the infeasible system found could possibly be reduced further). BARON 14 also brings significant improvements in the handling of integer problems. Note that finding a global optimum takes more time than finding a local optimum like most nonlinear solvers do.

Another way to analyze an infeasible problem is by using the *Display Infeasibility Analysis* option of the **AIMMS presolver**. This also prints an infeasible set of constraints and variables. In previous versions of AIMMS this set could contain superfluous constraints and variables, but AIMMS 4.1 uses an algorithm to calculate an (almost) irreducible set. Note that the AIMMS presolver cannot always detect that an infeasible problem is indeed infeasible.

Both the Infeasibility Analysis and the IIS, as calculated by BARON, are printed in the listing file.


.. include:: /includes/form.def


