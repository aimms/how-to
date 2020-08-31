Gate Assignment problem
=========================
.. meta::
   :keywords: Mixed Integer Programming, Linear Programming, Column Generation, GMP, Gate Assignment
   :description: In this example we will demonstrate how to formulate a set-covering model that is solved by using column generation within an AIMMS project.

Direct download AIMMS Project :download:`Sudoku <https://download.aimms.com/aimms/download/examples/GateAssignment.zip>`

Go to the example on GitHub:
https://github.com/aimms/examples/tree/master/Application%20Examples/Gate%20Assignment

At an airport every day a large number of aircraft arrive. After they arrive, they need to be refueled, replenished, all the waste has to be taken off-board and also all the passengers must disembark the aircraft. After some time on the ground, the new passengers embark the aircraft, after which it will take off to its destination.

While the aircraft is on the ground, it needs to be assigned a place where it can stand, the so-called stand or gate. Determining which aircraft is assigned to which gate is the Gate Assignment Problem. Since aircraft hardly ever arrive/depart on time, we must create an assignment plan for the upcoming day, based on currently available flight-schedule information in such a way that a small deviation from the scheduled arrival/departure of any of the flights does not result in an infeasible assignment plan. We would like to create an assignment plan that is robust against such small deviations during the actual day of operations.

In this example we will demonstrate how this problem of finding a robust plan for the gate assignment problem, can be formulated as a set-covering model that is solved by using column generation within an AIMMS project. We will also demonstrate how to use the Generated Mathematical Problem functionality provided by AIMMS in combination with Column Generation.

You may read Technical Report by G. Diepen et al. for details about the model and techniques used in this example. You can also refer to PhD thesis of Guido Diepen for the theory and additional problems.

Keywords:
Mixed Integer Programming, Linear Programming, Column Generation, GMP, Gate Assignment