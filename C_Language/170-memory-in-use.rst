Investigate memory in use
============================

This article summarizes some techniques to monitor your application's memory use.

Viewing total memory in use
----------------------------

The function ``MemInUse`` returns the amount of memory used by AIMMS in Mb. ``MemInUse`` calls a system function to find out how much memory is used by executing a procedure. 

Getting an overview of memory used for identifiers
----------------------------------------------------

To view details about memory use, use the identifier cardinalities tool:
#. Go to *AIMMS Menu > Tools > Diagnostic tools*.
#. Tick both selection boxes at the bottom bar, and click the header *Mem Usage* to sort by that value. 

This will give you an indication of the identifiers with most memory consumption. 

.. figure:: ../Resources/C_Language/Images/170/GateAssignmentMemoryInUse.PNG

    Data cardinalities tool for the Gate Assignment problem in the AIMMS Examples repo.


Analyzing memory used for identifiers
-------------------------------------------------
Several functions can be used to check memory use for identifiers in your project.

* ``Card()``  returns the number of elements, both active and inactive, for the identifier between the parentheses.

* ``ActiveCard()``, returns only the number of active elements for the identifier between the parentheses. 

* ``IdentifierMemory()``, returns the memory in use for the identifier between the parentheses.

Note that the rebuild statement allows to return the memory occupied by "permutations".

Analyzing of memory used for GMPs
-----------------------------------------

The function ``GMP::Instance::GetMemoryUsed`` returns the memory used by a generated mathematical program (GMP). By visiting all elements in the set ``AllGeneratedMathematicalPRograms`` you'll know how much memory AIMMS reserved for the GMPs.

Minimizing memory used for element spaces
------------------------------------------

AIMMS maintains a mapping between elements(strings) and numbers per root set. This mapping is the *element space*. Because the element space of the set ``Integers`` is very small, as this is just an arithmetic operation without the need for additional memory. When your elements are integers, the corresponding set should be a subset of the set ``Integers``.



Related Topics
--------------------

* `AIMMS User's Guide <https://documentation.aimms.com/_downloads/AIMMS_user.pdf>`_: Section "Observing identifier cardinalities"

* `AIMMS Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_: 

    * Function MemoryInUse   
    
    * Function Card
    
    * Function GMP::Instance::GetMemoryUsed
    
* :doc:`134-Monitoring-Memory-Use`


.. include:: ../includes/form.def
