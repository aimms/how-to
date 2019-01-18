Change the Frequency of Progress Updates 
=========================================

.. meta::
   :description: How to change the frequency of updates to the progress window.
   :keywords: progress, update, solve

.. note::

	This article was originally posted to the AIMMS Tech Blog on April 1, 2015 by Marcel Hunting.

.. sidebar:: Progress Window

    .. image:: ../Resources/C_Solvers/Images/127/pw1.png


The progress window, which can be opened by pressing CTRL-P, allows you to monitor AIMMS during compilation, execution and solving. For example, while solving a MIP problem, AIMMS will display the number of iterations and nodes, the best bound and the best solution in the progress window. So far, progress updates during a solve have been based on the number of iterations used by the solver. By default, the progress window is updated every 100 iterations. This frequency is controlled by the general solvers option **Progress Solution**.

If progress updates are done very frequently then it can have a negative influence on the performance. If progress updates are rare, it can take minutes before the progress window is updated again. As a side effect, AIMMS can become seemingly non-responsive, as progress updates are often the only times during a solve in which the solver will give the control back to AIMMS for a brief moment, such that AIMMS can update pages, check for interrupts, etc. (The control is also given back to AIMMS if a callback procedure is called, or during function or derivative evaluations while solving a nonlinear problem.)

Basing progress updates on the number of **iterations** has some **drawbacks**. First, the number of iterations completed per second during a solve depends heavily on the problem, solver and algorithm used. Doing a progress update every 100 iterations may be fine for a small or medium sized problem but is likely not sufficient for a large problem. Second, during some steps of the solving process the number of iterations might not change. For example, during the preprocessing step or the calculation of an irreducible infeasible set (IIS). In that case we would not see any progress updates if we would base them only on iterations. Third, for some solvers it is not possible to base progress on the number of iterations. For example, BARON bases progress on the number of nodes, while CP Optimizer uses branches, choice points or failures instead of iterations.

In AIMMS 4.6 progress updates will, by default, be based on elapsed **time**. The main advantages are that (1) it is supported by all solvers, (2) it can be used even if the iterations count is not changing during some phase of the solving process, and (3) it is problem and solver independent, meaning that you do not have to change any progress option setting if you build a new model or switch solvers. The new option will be named **Progress Time Interval**. The old Progress Solution option will still be available but it will become inactive by default. It will be possible to combine both options such that progress updates are based on time and iterations.

Note that even after this change there is no guarantee that you will see progress updates regularly all the time. Doing progress window updates requires that the solver passes updates to AIMMS, which does not always happen.


.. include:: ../includes/form.def


