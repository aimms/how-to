Miscellaneous: Asynchronous solver sessions
===========================================

Direct download AIMMS Project :download:`AsynchronousSolverSessions.zip <model/AsynchronousSolverSessions.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Miscellaneous/AsynchronousSolverSessions

Problem type:
MIP (medium)

Keywords:
Parallel solver sessions, asynchronous, GMP, Threads

Description:
This example demonstrates how asynchronous solver sessions can be used
to solve multiple instances of the FlowShop problem in parallel. For
each solver session you can specify the amount of threads to be used by
the solver. That way you can, e.g., solve two instances in parallel by
using two solver sessions whereby each solver session uses 4 threads.

Note:
Multiple threads for MIP problems are only supported by CPLEX and Gurobi

.. meta::
   :keywords: Parallel solver sessions, asynchronous, GMP, Threads
