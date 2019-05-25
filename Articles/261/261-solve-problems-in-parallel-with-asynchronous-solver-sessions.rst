:orphan:Solve problems in parallel with asynchronous solver sessions==================================================================
.. meta::   :description: How to customize syntax highlighting colors and other Editor display options used in the AIMMS IDE.   :keywords: Syntax, highlighting, identifier, color, display, editor, settings.. note::    This article was originally posted to the AIMMS Tech Blog.
.. <link>https://berthier.design/aimmsbackuptech/2012/05/18/solve-problems-in-parallel-with-asynchronous-solver-sessions/</link>
.. <pubDate>Fri, 18 May 2012 13:41:39 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1266</guid>

               <![CDATA[In this article, I will explain how to use the asynchronous solver sessions that are in AIMMS in order to solve multiple mathematical programs simultaneously, each using its own CPU core. The idea for this article came from a <a title="Comments" href="http://blog.aimms.com/2012/05/using-model-tree-structure-information-in-your-model/#comments">comment by Will Usher</a> on my previous article. He was interested in an example that shows how to use asynchronous solver sessions in AIMMS.

.. raw:: html

   <!--more-->

[caption id="attachment_1275" align="alignright" width="290"] Only one
worker emptying the queue[/caption]

The current engine of AIMMS cannot make use of the multiple cores that
exist in most modern computers. This means that even if your computer
has multiple cores, AIMMS itself will only use one of these cores when
executing your project. However, some of the solvers provide support for
using multiple cores themselves. This means that if you are solving a
large LP or MIP problem, a solver like CPLEX or Gurobi can make use of
multiple cores in your computer to solve your problem.

Unfortunately, if you need to solve a large number of smaller problems,
using multiple cores for the solver probably will not give you a lot of
improvement on the total solving time. This means that if you have 4
cores in your computer, you probably will see that only one core is
busy, while the other three are not doing anything.

[caption id="attachment_1276" align="alignleft" width="290"] Multiple
worker simultaneously emptying the queue[/caption]

In order to prevent the situation depicted above (i.e. only one core
working and three cores not doing anything at all), you can instruct
AIMMS that a solve must be executed asynchronously by using the concept
of asynchronous solver sessions. This will result in the situation where
the actual solve of each problem is done in a separate thread, meaning
you can get to 100% CPU usage by starting as many solver sessions as you
have cores in your machine (i.e. the situation depicted on the left).

Please note that in general, it does not mean that if you use N
simultaneous solver sessions the time needed to solve all the problems
is divided by N. The reason for this is that there is some overhead
required for synchronizing the different threads (especially if you want
to copy data from a solver session back into your model). In most of the
cases, you should still see an improvement in the time required to solve
all the problems with multiple solver sessions compared to solving all
problems sequentially.

To use the asynchronous solver sessions, you will first have to create a
GMP for your problem as also explained in the earlier article Using GMP
functions instead of regular solve statement. Instead of just using
gmp::Instance::Solve, you will now have to manually create a Solver
Session first and instruct AIMMS to start executing this Solver Session.

.. raw:: html

   <pre lang="aim">!Set input parameters corresponding to situation 1  (e.g. max number of jobs)
   maxNumberOfJobs := 10 ; 
   !Generate the GMP for situation 1
   epGMP_Situation1 := gmp::Instance::Generate(
               MP   : MathProgram , 
               name : "situation 1" ) ; 

   !Set input parameters corresponding to situation 2  (e.g. max number of jobs)
   maxNumberOfJobs := 15 ; 
   !Generate the GMP for situation 2
   epGMP_Situation2 := gmp::Instance::Generate(
               MP   : MathProgram , 
               name : "situation 2" ) ; 
               

   !Now create the solver sessions that can be used
   !to actually solve the problems
   epSolverSession1 := gmp::Instance::CreateSolverSession(
               GMP    : epGMP_Situation1 , 
               Name   : "Solver session situation 1" ) ; 
               
   epSolverSession2 := gmp::Instance::CreateSolverSession(
               GMP    : epGMP_Situation2 , 
               Name   : "Solver session situation 2" ) ; 
               
               
   !Instruct AIMMS to execute both solver sessions asynchronously
   gmp::SolverSession::AsynchronousExecute(
               solverSession : epSolverSession1 ) ; 
   gmp::SolverSession::AsynchronousExecute(
               solverSession : epSolverSession2 ) ; 
               


   !As long as there are still solver sessions, keep on checking for the
   !next one that is finished and do something with the results
   while card(AllsolverSessions) do
       !Wait for any of the solver sessions to be finished. The solver
       !session that is actually finished will be returned by the
       !WaitForSingleCompletion function
       epFinishedSolverSession := gmp::SolverSession::WaitForSingleCompletion(
                       solSesSet : AllSolverSessions  ) ; 
       
       !Do something with the result, e.g. display the objective
       pFoundObjective := gmp::SolverSession::GetObjective(
                      solverSession : epFinishedSolverSession ) ; 

       !Based on the name of the solver session, you can see which situation
       !was finished solving.
       display epFinishedSolverSession, pFoundObjective
       
       
       !This solver session is finished. We do not need it anymore, so 
           !we can delete it
       gmp::Instance::DeleteSolverSession(
               solverSession : epFinishedSolverSession ) ; 
   endwhile ; 
   </pre>

The above example just shows how you can make use of two static
sessions. If you want to make use of a variable number of parallel
solver sessions, you will have to keep track of them somehow.

I have modified the original FlowShop example that comes with your AIMMS
installation to demonstrate how the
gmp::SolverSession::AsynchronousExecute can be used to solve multiple
scenarios by making use of multiple solver sessions, each running on its
own CPU. Also, the number of simultaneous sessions can be varied,
allowing you to see the effect of using multiple sessions on the time
required for solving all scenarios. You can download the modified
example below. Please note that you will need AIMMS 3.11 or newer to
open this project. [attachments include="4180"]

Important remarks: Not all solvers can be executed asynchronously.
Please check the documentation for the
gmp::SolverSession::AsynchronousExecute function in the AIMMS Function
Reference of your installation to see which solvers can be executed
asynchronously.

Besides a supported solver, your license also needs to support starting
a solver multiple times simultaneously. A typical commercial license
will only allow one simultaneous session to be started per solver,
unless you bought additional solver sessions. You can see how many
sessions your license allows for each solver by selecting your license
in the License Configuration (Menu > Tools > License > License
Configuration). If a solver can be started multiple times simultaneously
according to the selected license, this number will be printed after the
name of the solver in the license details on the right.

If you have a free AIMMS Academic License, the main solvers like CPLEX
and Gurobi will allow 32 simultaneous solver sessions. The 30 day AIMMS
Trial License will allow two sessions to be started simultaneously for
these solvers.]]> <[CDATA[]]> 1266 0 0 0.. include:: /includes/form.def
