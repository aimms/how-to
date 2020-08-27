Finding multiple solutions for the dice problem
================================================

Download the example from GitHub:
https://github.com/aimms/examples/tree/master/Practical%20Examples/MultipleSolutions/Dice

Problem type:
MIP (small)

Keywords:
Multiple solutions, Incumbent callback, Solution pool, GMP

Description:
This example demonstrates several approaches to get multiple solutions when
solving a MIP problem. These approaches are:

- Using the incumbent (solver) callback;
- Using the solution pool (only supported by CPLEX);
- Repeatedly solve the problem forbidding the previous solutions.

In the dice problem a set of three dice has to be designed by assigning an
integer number to each face such that on average die 1 beats die 2, die
2 beats die 3, and die 3 beats dice 1. The goal is to maximize the number
of total wins on average. The dice problem has many solutions.

References:
Bosch, R.A., Mindsharpener - Monochromatic Squares, OPTIMA Newsletter 71 (2004),
Mathematical Optimization Society, pp. 6-7

.. meta::
   :keywords: Multiple solutions, Incumbent callback, Solution pool, GMP


