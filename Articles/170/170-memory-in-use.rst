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

.. figure:: /images/GateAssignmentMemoryInUse.PNG

    Data cardinalities tool for the Gate Assignment problem in the AIMMS Examples repo.
    
Tips and tricks:

* The open door tip is to reconsider whether you really need the identifiers in the top list. All too often, some of the identifiers in the top list were defined parameters, where this parameter is actually not used anymore in the application.

* When these identifiers are about intermediate computations, use the **Empty** statement to return the occupied memory for re-use when their values are no longer of use.

* A generic tip is to use minimal index domains, especially for identifiers that are used for intermediate computations. This includes variables and constraints, but it doesn't include parameters for visualization or data exchange. Minimal index domains are constructed using the following two principles:

    * Use indices over subsets
    
    * Use tight index domain conditions
    
    More about this in AIMMS The language reference, see reference below.


Analyzing memory used for identifiers
-------------------------------------------------
Several functions can be used to check memory use for identifiers in your project.

* ``Card()``  returns the number of elements, both active and inactive, for the identifier between the parentheses.

* ``ActiveCard()``, returns only the number of active elements for the identifier between the parentheses. 

* ``IdentifierMemory()``, returns the memory in use for the identifier between the parentheses.

Tips and tricks:

In addition, to the tips and tricks mentioned in the previous section, note that the **Rebuild** statement allows to return the memory occupied by "permutations".

Analyzing of memory used for GMPs
-----------------------------------------

The function ``GMP::Instance::GetMemoryUsed`` returns the memory used by a generated mathematical program (GMP). 
As illustrated in the following code block, by visiting all elements in the set ``AllGeneratedMathematicalPRograms`` 
you'll know how much memory AIMMS reserved for the GMPs.

.. code-block:: aimms
    :linenos:

    Section Memory_In_Use_of_Mathematical_Programs {
        Parameter p_MemInUseMPs {
            IndexDomain: IndexGeneratedMathematicalPrograms;
        }
        Parameter p_TotMemInUseMPs {
            Definition: sum( IndexGeneratedMathematicalPrograms, p_MemInUseMPs(IndexGeneratedMathematicalPrograms) );
        }
        Procedure pr_OverviewMemoryInUseMathematicalPrograms {
            Body: {
                for IndexGeneratedMathematicalPrograms do
                    p_MemInUseMPs(IndexGeneratedMathematicalPrograms) := gmp::Instance::GetMemoryUsed(IndexGeneratedMathematicalPrograms);
                endfor ;
                block where single_column_display := 1;
                    display p_TotMemInUseMPs, p_MemInUseMPs;
                endblock ;
            }
        }
        Procedure pr_DeleteAllGeneratedMathematicalPrograms {
            Body: {
                while card( AllGeneratedMathematicalPrograms ) do
                    gmp::Instance::Delete( first( AllGeneratedMathematicalPrograms ) );
                endwhile ;
            }
        }
    }

Running the above procedure, will generate the following in the listing file for the Nest Solve example in the AIMMS Example library.
    
.. code-block:: none

    p_TotMemInUseMPs := 1.378 ;


    p_MemInUseMPs := data 
    { NetworkFlowModel     : 0.429,
      DicutSeparationModel : 0.949 } ;

Tips and tricks:

* To reclaim the memory occupied by a GMP you can use the procedure gmp::Instance::Delete() as illustrated above by the procedur e ``pr_DeleteAllGeneratedMathematicalPrograms()``. 
  However, when the mathematical program will be solved again, then having the corresponding GMP already in memory may save significant time. 
  
* The general tip to reduce memory by working with minimal index domain also applies to the GMP's generated; this will avoid superfluous rows and columns, thus saving memory and time to generate mathematical programs.

* Often times there are many variables for which the solution is obvious; decision variables from the past, filled tanks cannot be filled further, built distribution centers need not be build again, and so on. You can model these variables by setting their nonvar attribute to 1.  See also the option `Eliminate novar columns`

Minimizing memory used for element spaces
------------------------------------------

AIMMS maintains a mapping between elements(strings) and numbers per root set. This mapping is the *element space*. Because the element space of the set ``Integers`` is very small, as this is just an arithmetic operation without the need for additional memory. When your elements are integers, the corresponding set should be a subset of the set ``Integers``.



Related Topics
--------------------

* `AIMMS User's Guide <https://documentation.aimms.com/_downloads/AIMMS_user.pdf>`_: Section "Observing identifier cardinalities"

* `AIMMS The Language Reference <https://documentation.aimms.com/_downloads/AIMMS_ref.pdf>`_: Chapter "Execution Efficiency Cookbook", Section "Reducing the number of elements"

* `AIMMS Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_: 

    * Function MemoryInUse   
    
    * Function Card
    
    * Function GMP::Instance::GetMemoryUsed
    
* :doc:`../134/134-Monitoring-Memory-Use`


.. include:: /includes/form.def
