Flow Shop
===========

.. meta::
   :keywords: CallBackNewIncumbent, MIP model, Gantt chart, AsynchronousExecute, GMP, Rest API, Endpoint
   :description: The goal in this example is to find a schedule such that the time to process all the jobs on all the machines is as small as possible.


Direct download AIMMS Project :download:`Flow Shop.zip <model/Flow Shop.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Application%20Examples/Flow%20Shop

In this flow shop problem we have a set of Machines and a set of Jobs. Every job has to be processed on every machine, and the sequence of machines on which a job is processed is the same for every job. The goal is to find a schedule such that the time to process all the jobs on all the machines is as small as possible.

An optimal schedule is determined by the sequence of the jobs on the machines, that is which job is processed first on every machine, which second and so on. Besides this ordering, the start time of the job on the machines determines the optimal schedule.

This model seems rather easy, however the number of binary variables is equal to the square of the number of jobs. This means that the time the solver needs to solve this problem grows rapidly with the increase of the number of jobs.

In complex cases like this one might want to use startvalues to help find an optimal solution faster. This example will illustrate how a (MIP) variable can be initialized by assigning a value to it prior to calling the solve statement.

Additionally, we show how asynchronous solver sessions in AIMMS can be used to solve multiple problems simultaneously. Each of the solver sessions will use a separate CPU, allowing you to make full use of modern multi core computers. Generally speaking, this should result in a speedup when solving lots of problems, which otherwise would have to be solved sequentially. 

The Multiple Scenarios Parallel page will allow you to see the effect of using multiple parallel solver sessions on the time required for solving a set of scenarios. Please note that your license must support the use of multiple simultaneous solver sessions to use asynchronous execute of solver sessions. 

This application also features the option to use AIMMS' Rest API features. 
In order to use this feature you will need to publish this application on your AIMMS Cloud environment, configure the application based on your cloud information and use the "Solve Model using task" button in the demo page. 
The identifiers that need to be updated are: sp_AIMMSazureCloudName, sp_apiKey, sp_appName and sp_appVersion. 

Keywords:
CallBackNewIncumbent, MIP model, Gantt chart, AsynchronousExecute, GMP, AIMMS PRO solver session, AIMMS Cloud task, Rest API