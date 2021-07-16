Optimize Execution Time
=======================

.. meta::
   :description: How to improve efficiency of executing procedures in AIMMS projects.
   :keywords: execute, solve, long, time, duration


The time spent by AIMMS applications can be divided into AIMMS execution time ( including evaluation parameters with definition, executing procedures, generate matrix for solvers, etc), the time spent by solvers, and the I/O time.  Here are some coding tricks that help you improve AIMMS execution time.


1. Avoid :any:`for` Loop
--------------------------

Use bulk execution of assignment as much as possible. If a :any:`for` loop is necessary, try to minimize calculation inside the loop. For example,

.. code-block:: aimms

    for (i,j) do
             A(i,j) := B(i,j) + C(i,j)
    endfor;

can be written as the following bulk statement

.. code-block:: aimms

    A(i,j) := B(i,j) + C(i,j);

2. Pay attention to index order
--------------------------------

When declaring a parameter with multiple indices, usually index with small cardinality goes first and running index goes last. For example, in the following statement, ``k`` is used as running index:

.. code-block:: aimms

    A(i,j) := Sum[(k), D(i,j,k)];

Another thing to keep in mind is to put the indices in same order. For example the following statement

.. code-block:: aimms

    isActive(p,t,s):= 1 $ (t >= Begin(p,s) and t < (Begin(p,s)+Duration(t,s)));

runs much faster than

.. code-block:: aimms

    isActive(p,s,t):= 1 $ (t >= Begin(p,s) and t < (Begin(p,s)+Duration(t,s)));

3. Use index domain condition
-----------------------------

Domain condition puts restriction on the indices and thus reduces memory and time consumption. 
Use it whenever possible. 
The usage of index domain can be found on related posts. 
One thing to be careful when using domain condition is to avoid sub index expression.

A sub index expression is the expression depend on fewer indices than the entire expression. For example, in the following statement,

.. code-block:: aimms

    F(i,k) := G(i,k) * Sum[j | A(i,j) = B(i,j), H(j)]

the entire expression depends on indices ``(i,j,k)``, but expression ``Sum[j | A(i,j) = B(i,j), H(j)] only has (i,j)``. 
During calculating the value of ``F(i,k)``,  AIMMS will evaluate the result of sum term for each combination of ``(i,k)``, although the its result will be the same of all ``k``. 
To avoid unnecessary evaluation for k, the one statement can be separated into two statements:

.. code-block:: aimms

    FP(i) := Sum[j | A(i,j) = B(i,j), H(j)] ;
    F(i,k) := G(i,k) * FP(i) ;

Another example, although domain condition is added, the following statement is still inefficient:

.. code-block:: aimms

    sum[(t,s,i,j,k) | ElementPara(i,j) = k, …]

Since ``ElementPara(i,j) = k`` is a sub index expression, AIMMS will create a temporary identifier index over ``(t,s,i,j,k)`` to evaluate the condition over the full domain. And comparison operation is a dense operation, thus the calculation needs to go over every (t,s,i,j,k). The result will be time and memory consuming.

The problem can be solved by introducing a new parameter SumCondition(i,j,k) and having

.. code-block:: aimms

    SumCondition(i,j,k) := (ElementPara(i,j) = k);
    sum[(t,sc,i,j,k) | SumCondition(i,j,k), …];

These are some general rules. 

In practice, lots of the performance improvements are done on by trial and error.  
And AIMMS diagnostic tools, such as Debugger, Profiler, and Identifier Cardinalities Viewer can help with identifying the performance bottleneck. 
You can refer to  `AIMMS The Users's Guide <https://documentation.aimms.com/_downloads/AIMMS_user.pdf>`_    for how to use them. 
And more insights of AIMMS execution engine can be found in chapters :doc:`sparse-execution/the-aimms-sparse-execution-engine/index` and :doc:`sparse-execution/execution-efficiency-cookbook/index`.








