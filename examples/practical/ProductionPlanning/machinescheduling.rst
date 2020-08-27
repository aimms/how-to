Machine scheduling
==================
.. meta::
   :keywords: Gantt chart, MIP Gap, Callback procedures, GMP, Calendar
   :description: This machine scheduling problem is formulated as a MIP problem.

Download the example from GitHub:
https://github.com/aimms/examples/tree/master/Practical%20Examples/ProductionPlanning/MachineScheduling

Problem type:
MIP (hard)

Keywords:
Gantt chart, MIP Gap, Callback procedures, GMP, Calendar.

Description:
In this multi-machine scheduling problem activities have to be assigned to
one machine, while taking into account the delivery (or due) dates of the
activities. The objective is to minimize the makespan. The machines are
different. This machine scheduling problem is formulated as a MIP problem.

The project contains one page showing a Gantt chart that displays the
assignment of the activities to the machines. The Gantt chart is updated
whenever the solver finds a new solution. The page also contains a graph
that displays the progress of the optimality gap during the optimization run



