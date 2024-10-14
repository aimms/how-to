Cutting Stock
=============

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Cutting Stock, Algorithmic approach, GMP, Callback function, Heuristic, Gantt Chart, AIMMS API 
   :description:    This project illustrates AIMMS' capabilities to implement an algorithmic approach to find an optimal solution to a problem by repeatedly solving two optimization programs.

Direct download AIMMS Project :download:`Cutting Stock.zip <model/Cutting Stock.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Modeling%20Book/Cutting%20Stock

This project illustrates AIMMS' capabilities to implement an algorithmic approach to find an optimal solution to a problem by repeatedly solving two optimization programs until no further improvement is possible anymore.

The problem tackled in this demo is a cutting stock problem: how to cut long rolls of material (referred to as raws) into smaller rolls of a prescribed size (referred to as finals), given a demand for each of the finals.

The straightforward way to tackle the problem would be to generate all possible patterns of cutting finals from raws, and generate a MIP model to find the optimal pattern choices. The problem here, however, is that the number of possible patterns grows explosively, making it virtually impossible to solve the MIP for larger numbers of finals. 

However, parts of the branch-and-bound tree can be cut off when using the AIMMS heuristic callback in the GMP library. In this example the LILE heuristic is used to provide CPLEX with integer solutions, after which large parts of the branch-and-bound tree can be cut off. The possible cutting patterns are generated recursively in an external procedure using the AIMMS API.

The LILE heuristic approach can be of advantage when the model needs to be solved over and over again using the same set of finals, but with different demand scenarios. When the set of finals is large or different for each solve, the below described alternative approach might work better, because not all patterns need to be generated.

An alternative way to tackle the problem is to start with a small number of patterns, and generate new patterns on the basis of shadow prices returned by the solver. In this way, only a subset of useful patterns will be generated for the final MIP model. In practice, this will lead to very good solutions, although optimality is not necessarily guaranteed. The solution is optimal, however, if the gap between the integer and linear objective function value is less than one.

The pattern generation approach is implemented using conventional math programs that require regeneration every time and using generated math programs (GMPs) that allow for direct updates on the generated problem.

The demo page illustrates the solution both in tabular and graphical (not when all patterns are generated) form given the raw size, as well as the prescribed finals and their demands. 

Details about this Cutting Stock problem can be read in Chapter 20 of `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ book.

Keywords:
Cutting Stock, Algorithmic approach, GMP, Callback function, Heuristic, Gantt Chart, AIMMS API





