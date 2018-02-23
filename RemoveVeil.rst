How do I remove the veil while a server session is running?
===========================================================

Introduction
------------

First, the AIMMS language is good at formulating decision support problems, thus helping AIMMS modelers. Secondly, the AIMMS PRO platform is good at separating the development and publication process from the deployment process – thus helping AIMMS App developers and AIMMS App users. Finally, the AIMMS WebUI is good at presenting input data, and results. 

However, when we integrate all of this, we see long running problems whereby the end-users do not feel in control of the solution process – it happens on a computer far away, they typically do not get feedback about solution process and they have trouble killing it if necessary.

This article provides eight building blocks to the modeler; such that, when judiciously applied – these building blocks transform the application at hand into an application whereby the end-user can follow and manage the solution process while at the same time inspecting and studying data.

The structure of this article is to identify each potential hurdle in the transformation and tackle that hurdle subsequently. Let’s start immediately with that – admittedly a bit artificial – but you’ll soon get the drift.

Oops, I see a problem: what is the problem we want to model actually?

Step 1: Modeling the problem
----------------------------

The decision problem we chose as an example is the flowshop problem: Several jobs need to be handled.  Each job needs to pass each available machine. The processing time depends both on machine and on job.  The question is, how to order the machines and how to order the jobs such that the total time required to process all jobs is minimal. This problem is detailed in https://en.wikipedia.org/wiki/Flow_shop_scheduling. As it is an abstract mathematical problem, we can generate input using a random generator for it given a number of jobs and a number of machines. Thus it is ideal for our purposes as it does not require us to dive into the intricacies of data handling.

The enclosed zip file 1. Flow Shop – WebUI – Dev version contains the basic model formulation. You can open this project in the AIMMS Development environment, open the WebUI, and press the solve button. After some time, the solution is presented in a Gantt Chart. You can play around with the number of Jobs and the number of Machines – be careful – a lot of machines and jobs will explode the required running(read waiting) time.

If you want to know more about the flowshop modeling problem, please consult: https://en.wikipedia.org/wiki/Flow_shop_scheduling 

Oops, I see a problem.  When I want to share my example with my fellows, they all need an AIMMS developer system and license on their laptops.  

























