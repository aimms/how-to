ROGO solver using constraint programming
========================================


.. note:: Under Construction / Draft status - please do not hesitate to use the form at the end of this article to ask for clarification where needed.



.. image:: ../Resources/C_Mathematical_Modeling/Images/138/ROGO.logo_.png

The ROGO puzzle, `rogopuzzle <http://www.rogopuzzle.co.nz>`_, challenges players to find a good path on a board, pick up treasures, and avoid pitfalls. This puzzle, and its corresponding iPhone app, were originally developed in New Zealand. In this post, I'll explain a method for solving ROGO puzzles using constraint programming in AIMMS.

.. figure:: ../Resources/C_Mathematical_Modeling/Images/138/rogo1.gif

    Intro 6 Solution animated


Different model formulations were tried
---------------------------------------

I started modeling this problem in a straightforward manner: ``an x(i) in { 1 .. noCols }`` and ``y(i) in { 1 .. noRows }`` variable for each element ``i`` in the path. However, avoiding multiple visits of the same cell and enforcing the path would result in constraints with a lot of "OR" conditions. So my next approach was to number each cell, such that only a single variable was needed to identify a cell, albeit with a larger range. Avoiding multiple visits can then be enforced by using a ``cp::AllDifferent``. A Deterministic finite automaton (DFA) was used to model the sequence of states that would result in a path. The use of DFA's in constraint programming was introduced by Gilles Pesant in 2002. The DFA is implemented via a table constraint. A drawback of this approach was the additional variable for the events of a DFA, which did not contribute to the objective. My final approach was a variation on the DFA: eliminating this variable for events for efficiency.

Model preparation
-----------------

In a procedure, we prepare by numbering all cells, including treasures and pitfalls, row wise and by creating both an element parameter and relation to link the row and column number with the cell number. The preparation is completed by constructing the set of allowed transitions. Consider, as an example, Intro 6, the cell no 4 in row 1, colum 4 with treasure value 2. From this cell, you can go west and east, making transitions (4,3) and (4,5) allowed. The cell no 13 in row 2, column 4 is a pitfall, making (4,13) not an allowed transition.

Enforcing a cycle
-----------------

.. code-block:: aimms

    CONSTRAINT:
       identifier   :  cDoStep
       index domain :  s
       definition   :  (evVisitCell(s--1),  evVisitCell(s)) in sTransitions
   
This table constraint ensures that two successive cells in a path are allowed by the transitions. The circular lag operator, notation ``--``, results in the previous element of ``s``. With this operator, the previous of the first element is the last element, thus closing the path.

Avoiding multiple visits
------------------------

.. code-block:: aimms

    CONSTRAINT:
       identifier :  cDifferentCells
       definition :  cp::AllDifferent(s,evVisitCell(s))

This constraint ensures that we do not visit the same cell twice.

A bit of search reduction
-------------------------

.. code-block:: aimms

    CONSTRAINT:
       identifier :  cStartInCellWithValue
       definition :  evVisitCell(first(s)) in sCellsWithValues
   
If we want to give the objective some value, at least one cell should have value. As the sequence might begin at any place, we may as well start in a cell with value. This reduces the search, as we rule out the first cell without value.

The AIMMS application

[attachments include="4130"]

This example requires AIMMS 3.13 FR1 or later. It comes with a case for each of the introductory ROGO puzzles. Psst, there is a transparent button on the ROGO logo; clicking it will open the puzzle site.

Reference
------------

Gilles Pesant "A Regular Language Membership Constraint for Finite Sequences of Variables" in "Principles and Practice of Constraint Programming - CP", 2004, pp. 482-495

Update Januari 14, 2013
-------------------------

Hakan Kjellerstrand recently posted a better symmetry breaking rule `here <http://www.hakank.org/constraint_programming_blog/2011/01/rogo_grid_puzzle_in_answer_set_programming_clingo_and_minizinc.html>`_: the idea is that the first element in the path has the lowest cell number. For the introductory problems presented at the ROGO site, I compared the performance of these two symmetry breaking rules.

+-------------+------------------+----------------------+
| Instance    | first with value | first lowest cell no |
+=============+==================+======================+
| Intro 1     |   0.14           |   0.17               |
+-------------+------------------+----------------------+
| Intro 2     |   0.19           |   0.22               |
+-------------+------------------+----------------------+
| Intro 3     |   0.22           |   0.26               |
+-------------+------------------+----------------------+
| Intro 4     |   0.20           |   0.33               |
+-------------+------------------+----------------------+
| Intro 5     |   1.62           |   0.30               |
+-------------+------------------+----------------------+
| Intro 6     |   2.50           |   0.31               |
+-------------+------------------+----------------------+
| Intro 7     |   8.66           |   0.47               |
+-------------+------------------+----------------------+
| Intro 8     |   60             |  60                  |
+-------------+------------------+----------------------+

For the first four ROGO examples, there is hardly any difference. For ROGO puzzles 5, 6 and 7, the symmetry breaking rule of Hakan performs better. For ROGO puzzle 8 both formulations found the optimal value but were not able to prove optimality within 60 seconds. In short, Hakan's symmetry breaking rule performs better than mine.


.. include:: ../includes/form.def


