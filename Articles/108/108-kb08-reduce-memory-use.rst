Reduce Memory Usage
======================

.. meta::
    :description: Memory is a limited resource, care may be needed for large apps
    :keyword: MemoryInUse, GetMemoryUsed, GMP, Solver Session, identifier, data

AIMMS uses computer memory for several tasks, including storing generated mathematical programs and data in sets, parameters, variables and constraints.

Solving very large problems can result in running out of memory.

When you run your model, the following error message might occur:

        ``Memory limit exceeded``

Other symptoms of reaching the memory limits of your machine can be:

* It takes a long time to load or save a case.
* It takes a long time to open a page.
* AIMMS is running very slow.
* Your computer is thrashing (excessive swapping), which you can see at the Processes tab of your Task Manager.

Approaches
----------
There are two approaches to handling this problem:

* Increase the available (virtual) memory. A disadvantage of virtual memory is that your computer needs to swap files from the virtual RAM to the real RAM. 

* Reduce the memory requirements of your application.

Reducing memory requirements
-----------------------------

First, check the amount of memory in generated mathematical programs. 

The predeclared set ``AllGeneratedMathematicalPrograms`` contains the names of the generated mathematical programs. You can use the intrinsic function ``GMP::Instance::GetMemoryUsed`` for each of the elements as follows:

.. code-block:: aimms
    :linenos:

    p_MemInUseGMP := sum( indexGeneratedMathematicalPrograms, 
        gmp::Instance::GetMemoryUsed( indexGeneratedMathematicalPrograms ) );

You can use ``GMP::Instance::Delete`` to delete generated mathematical programs as follows:

.. code-block:: aimms
    :linenos:

    ep_GMP := first(AllGeneratedMathematicalPrograms) ;
    while firstGMP <> '' do
            gmp::Instance::Delete(firstGMP) ;
            ep_GMP := first(AllGeneratedMathematicalPrograms) ;
    endwhile ;

Next, check the amount of memory in sets, parameters, variables and constraints. 

You can use the diagnostic tool "Identifier Cardinalities" to obtain an overview. If you have a lot of inactive data, you can reduce memory by using the ``CleanUp`` statement. AIMMS creates permutations of the data of identifier on a need be basis. It may make sense to use a ``Rebuild`` statement to remove permutations that became obsolete. 

For those identifiers that use a lot of memory, decrease the memory usage:

* Specify (tight) domain restrictions where possible.
* Delete identifiers that were only added for testing purposes.
* Avoid saving identifiers in a case that do not need to be saved, using the ``NoSave`` property, or by specifying and using case content types that do not include these identifiers.
* Use expressions instead of large identifiers on pages that take a long time to open, because then the expressions are only evaluated when necessary. This is especially useful when only a slice of a multi-dimensional identifier is shown.

Preventing memory problems
---------------------------
Throughout model development, you can prevent memory problems by using the Identifier Cardinalities tool to check regularly for identifiers with many non-zeros.