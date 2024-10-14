Telecommunications: Filter design
===================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

Direct download AIMMS Project :download:`FilterDesign.zip <model/FilterDesign.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Telecommunications/FilterDesign

Problem type:
SOCP (small)

Keywords:
FIR, conic programming, second-order cone, SOCP.

Description:
Minimax dB linear phase low-pass filter design. The problem is to find the
filter weights for a finite impulse response (FIR) filter. This model forms
an approximation by transforming the frequency variable (omega) into discrete values resulting
in a second-order cone programming (SOCP) problem. SOCP problems can be
solved by CPLEX and Gurobi.

References:
Lobo, M.S., L. Vandenberghe, S. Boyd, H. Lebret, Applications of Second-order
Cone Programming, Linear Algebra and its Applications 284(1-3) 1998, pp. 193-228

.. meta::
   :keywords: FIR, conic programming, second-order cone, SOCP.


