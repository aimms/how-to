Nested Solve
============

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Nested solve, Network Flow, GMP, cut, callback, CallbackAddCut, MIP, Document Viewer
   :description: This example illustrates the nested solve that can be done in AIMMS using GMP callback.

Direct download AIMMS Project :download:`Nested Solve.zip <model/Nested Solve.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Nested%20Solve

This example illustrates the nested solve that can be done in AIMMS using GMP callback. CPLEX is needed to run this example, although the CallbackAddCut callback procedure can also be called when solving mixed integer programs with Gurobi.

The problem discussed in this example is a single commodity uncapacitated fixed charge network flow problem (UFC):  
given a directed graph, demands at the nodes, and fixed and variable costs on the arcs, find a set of arcs and a feasible flow in the resulting network that minimizes the total cost. 

CPLEX uses a branch-and-cut algorithm to solve a MIP problem. In the branch-and-cut algorithm, CPLEX solves a series of continuous subproblems. The first problem that CPLEX solves is the continuous relaxation of the original MIP problem. If the solution to the relaxation has one or more fractional variables, CPLEX will try to find cuts. Cuts are constraints that cut away part of the feasible region of the LP-relaxation.

The addition of cuts usually reduces the solution time needed to solve a MIP. Depending on the problem, CPLEX tries to find cuts from several classes of cuts. For some MIP problems the branch-and-cut algorithm could be speed up considerably if cuts from other - problem depending - classes of cuts could be found by CPLEX. The UFC problem is an example of such MIP problem.

This example contains two instances that are considered as being hard to solve.

AIMMS provides a callback procedure that can be used to find cuts yourself and pass them to CPLEX. In this example a special type of (valid) inequalities for the UPC problem is used to find and add cuts: the simple dicut inequalities. Finding a violated simple dicut inequality is not easy and we do that by solving another MIP problem; the so-called separation problem for simple dicut inequalities. To solve the separation problem inside the CPLEX callback another solver (XA or CBC) is used. In that case we have a 'recursive solve'.

Solving both instances with the second approach takes several minutes.

The UFC problem used in this example is based on the work of:
Francisco Ortega, Laurence Wolsey
A branch-and-cut algorithm for the single-commodity, uncapacitated, fixed-charge network flow problem.
Networks 41 (2003), no. 3, 143-158. 

This paper is displayed on an AIMMS page using a document viewer. 

Keywords:
Nested solve, Network Flow, GMP, cut, callback, CallbackAddCut, MIP, Document Viewer

