Solve in parallel with Asynchronous Solver Sessions
==================================================================

.. meta::
   :description: How to solve mathematical programs in parallel using asynchronous solver sessions.
   :keywords: asynchronous, solve, mathematical program, solver session, simultaneous

.. note::

    This article was originally posted to the AIMMS Tech Blog.


In this article, we explain how to use the asynchronous solver sessions in AIMMS to solve multiple mathematical programs simultaneously, each using its own CPU core. 


Why use asynchronous solves
---------------------------------------


The current engine of AIMMS does not make use of the multiple cores that
exist in most modern computers. This means that even if your computer
has multiple cores, AIMMS only uses one to
execute your project. 

However, some solvers support using multiple cores. This means that if you are solving a
large LP or MIP problem, a solver like CPLEX or Gurobi use
multiple cores in your computer to solve your problem.

Unfortunately, if you need to solve a large number of smaller problems 
using multiple cores, the solver probably will not much 
improve the total solving time. This means that if you have 4
cores in your computer, you probably will see that only one core is
busy, while the other three are not doing anything.

In order to prevent such a situation, you can instruct
AIMMS to execute a solve asynchronously (or simultaneously) by using the concept
of asynchronous solver sessions. This way the each problem is solved in a separate thread, and
you can get to 100% CPU usage by starting as many solver sessions as you
have cores in your machine.

Please note that in general, it does not mean that if you use N
simultaneous solver sessions the time needed to solve all the problems
is divided by N. The reason for this is that there is some overhead
required for synchronizing the different threads (especially if you want
to copy data from a solver session back into your model). In most of the
cases, you should still see an improvement in the time required to solve
all the problems with multiple solver sessions compared to solving all
problems sequentially.


How to implement asynchronous solves
--------------------------------------

To use the asynchronous solver sessions, first create a
GMP for your problem as explained in :doc:`../147/147-GMP-Intro`. You will manually create a Solver
Session (instead of using ``GMP::Instance::Solve``) and instruct AIMMS to start executing this Solver Session.

.. code::

   !Set input parameters corresponding to situation 1  (e.g. max number of jobs)
   maxNumberOfJobs := 10 ; 

   !Generate the GMP for situation 1
   ep_GMP_Situation1 := gmp::Instance::Generate(
               MP   : MathProgram , 
               name : "situation 1" ) ; 

   !Set input parameters corresponding to situation 2  (e.g. max number of jobs)
   maxNumberOfJobs := 15 ; 

   !Generate the GMP for situation 2
   ep_GMP_Situation2 := gmp::Instance::Generate(
               MP   : MathProgram , 
               name : "situation 2" ) ;         

   !Now create the solver sessions that can be used
   !to actually solve the problems

   ep_SolverSession1 := gmp::Instance::CreateSolverSession(
               GMP    : ep_GMP_Situation1 ,
               Name   : "Solver session situation 1" ) ; 
               
   ep_SolverSession2 := gmp::Instance::CreateSolverSession(
               GMP    : ep_GMP_Situation2 , 
               Name   : "Solver session situation 2" ) ; 
               
   !Instruct AIMMS to execute both solver sessions asynchronously

   gmp::SolverSession::AsynchronousExecute(
               solverSession : ep_SolverSession1 ) ; 

   gmp::SolverSession::AsynchronousExecute(
               solverSession : ep_SolverSession2 ) ; 

   !As long as there are still solver sessions, keep on checking for the
   !next one that is finished and do something with the results

   while card(AllsolverSessions) do

       !Wait for any of the solver sessions to be finished. The solver
       !session that is actually finished will be returned by the
       !WaitForSingleCompletion function

       ep_FinishedSolverSession := gmp::SolverSession::WaitForSingleCompletion(
                       solSesSet : AllSolverSessions  ) ;   

       !Do something with the result, e.g. display the objective
       p_FoundObjective := gmp::SolverSession::GetObjective(
                      solverSession : ep_FinishedSolverSession ) ; 

       !Based on the name of the solver session, you can see which situation
       !was finished solving.
       display ep_FinishedSolverSession, p_FoundObjective

       !This solver session is finished. We do not need it anymore, so 
       !we can delete it
       gmp::Instance::DeleteSolverSession(
               solverSession : ep_FinishedSolverSession ) ; 

   endwhile ; 

The above example shows how you can use two static
sessions. If you want to use a variable number of parallel
solver sessions, note that you will have to keep track of them somehow.

Example download
---------------------

We have modified the original FlowShop example that comes with your AIMMS
installation to demonstrate how the
``GMP::SolverSession::AsynchronousExecute`` can be used to solve multiple
scenarios with multiple solver sessions. You can change the number of simultaneous sessions
to see how using multiple sessions affects the time
required for solving all scenarios. 

You can download the modified example below.  

:download:`FlowShop.zip <downloads/FlowShop.zip>`

After opening the project, go to "*Open Demo Page*", then "*Multiple Scenarios Parallel*". You may check the code in the Section "*Solve Scenarios parallel with Multiple SolverSessions*"

.. warning:: 

    Not all solvers can be executed asynchronously.
    Please check the documentation for the
    ``GMP::SolverSession::AsynchronousExecute`` function in the AIMMS Function
    Reference of your installation to see which solvers can be executed
    asynchronously.

Licensing limitations
----------------------

Besides a supported solver, your license also needs to support starting
a solver multiple times simultaneously. A typical commercial license
will only allow one simultaneous session to be started per solver,
unless you bought additional solver sessions. You can see how many
sessions your license allows for each solver by selecting your license
in the License Configuration ( :menuselection:`Menu > Tools > License > License Configuration` ). 
If a solver can be started multiple times simultaneously
according to the selected license, this number will be printed after the
name of the solver in the license details on the right.

.. note::

    If you have a free AIMMS Academic License, the main solvers like CPLEX
    and Gurobi will allow 32 simultaneous solver sessions. The 30 day AIMMS
    Trial License will allow two sessions to be started simultaneously for
    these solvers.

