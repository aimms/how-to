File Merge
===========

.. meta::
   :keywords: Linear Program, Network Program, Simplex Method, Column Generation, Mathematical Derivation, Customized Algorithm
   :description: This problem deals with merging two statistical database files.

Direct download AIMMS Project :download:`File Merge.zip <model/File Merge.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Modeling%20Book/File%20Merge

This problem deals with merging two statistical database files; the Income Data File and the Population Data File. Because of the large sample sizes, the problem can be solved with an algorithmic evaluation approach that controls the size of the network, and systematically considers a subset of all columns at each iteration.

There are two methods of solving this problem:

1. Simplex method
2. Algorithmic method

The simplex method solves the problem by using matrix-vector notations for the underlying linear algebra. During the simplex iteration, basic and non-basic variables are switched until the reduced cost vectors become non-negative. The resulting current basic variable is the optimal solution that contains the minimum distance of the family size.

The algorithmic method contains a sequence of smaller sub-models which solves the overall file merge problem. The construction of each sub-model is based on evaluating the reduced cost of all variables. The recorded distances are computed during runtime and variables are either chosen to be candidates or ignored, resulting in controlled sample sizes during iterations.

The example reflects both methods plus a combination of the two.

Details about this example can be read in Chapter 19 of the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ book. An electronic version of this book is available through the 'Help' menu.

Keywords:
Linear Program, Network Program, Simplex Method, Column Generation, Mathematical Derivation, Customized Algorithm

