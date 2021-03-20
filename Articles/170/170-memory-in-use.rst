Investigate Memory in Use
============================

.. meta::
   :description: Techniques to investigate memory in use.
   :keywords: memory, virtual memory, MemoryInUse, identifiers, mathematical programming instance


Analytic applications may involve a lot of data and subsequently a lot of computer memory. AIMMS hides the technicalities related to memory management from the model developer. These technicalities include, for instance, the allocation and deallocation of memory for individual data items. Still, the memory usage of applications created with AIMMS grows as the amount of data related to these applications grows. At some point during model development, the memory usage of your application becomes interesting. AIMMS offers tools to monitor and investigate the memory usage of your application. We will discuss a couple of the tools available in AIMMS to investigate memory usage. 

Memory functions
-----------------------------

The function :aimms:func:`MemoryInUse` returns the amount of memory used by AIMMS in Mb. :aimms:func:`MemoryInUse` calls a system function to find out how much memory is used by executing a procedure. 

Several other functions are available to check the memory used by specific identifiers in your project.

* :aimms:func:`Card()`  returns the number of elements, both active and inactive, for the identifier between the parentheses.

* :aimms:func:`ActiveCard()`, returns only the number of active elements for the identifier between the parentheses. 

* :aimms:func:`IdentifierMemory()`, returns the memory in use for the identifier between the parentheses.

.. tip::

   The :any:`rebuild <rebuild>` statement allows to return the memory occupied by permutations.

Memory used by AIMMS identifiers 
-----------------------------------

One of the tools you can use to monitor the memory usage of your application is the `Identifier Cardinality` tool (available in the *Tools* menu: *Tools -> Diagnostic Tools -> Identifier Cardinalities*). Even with small sample data sets, this tool helps you identify candidates worthwhile of investigation; in particular those with:

* high density, say more than 10%
* high dimension, say three or more
* index domain referencing sets that grow when data sets grow

Consider the below screenshot of the identifier cardinalities for a typical gate assignment problem. The parameter ``BothFlightsPossibleOnGate`` has a density of 30%, and it will grow quadratically with the number of flights handled for the gate assignment. When such an identifier is identified, you can choose to handle it according to your application's needs and requirements. 

.. image:: images/identifier-cardinalities.PNG
   :align: center

Tips and tricks:

* The open door tip is to reconsider whether you really need the identifiers in the top list. All too often, some of the identifiers in the top list were defined parameters, where this parameter is actually not used anymore in the application.

* When these identifiers are about intermediate computations, use the **Empty** statement to return the occupied memory for re-use when their values are no longer of use.

* A generic tip is to use minimal index domains, especially for identifiers that are used for intermediate computations. This includes variables and constraints, but it doesn't include parameters for visualization or data exchange. Minimal index domains are constructed using the following two principles:

   * Use indices over subsets
   
   * Use tight index domain conditions

Analyzing memory used by GMPs
-----------------------------------------

The function :aimms:func:`GMP::Instance::GetMemoryUsed` returns the memory used by a generated mathematical program (GMP). The set :aimms:set:`AllGeneratedMathematicalPrograms` is another handy tool you can use to monitor memory use. The larger the size of this set, the more the number of generated mathematical programs managed by your application, and you may want to release the memory they occupy using the intrinsic function :aimms:procedure:`GMP::Instance::Delete`. 

You can retrieve the memory used by all the math programs in :aimms:set:`AllGeneratedMathematicalPrograms` by declaring a parameter over the index ``IndexGeneratedMathematicalPrograms`` and using the :aimms:func:`GMP::Instance::GetMemoryUsed` function in a ``for`` loop as illustrated in lines 6-12 in the below code. Lines 13-18 consist a procedure to delete all the GMPs in your project. 

.. code-block:: aimms
   :linenos:

   Section Memory_In_Use_of_Mathematical_Programs {
      Parameter p_MemInUseMPs {
         IndexDomain: IndexGeneratedMathematicalPrograms;
      }
   
      Procedure pr_OverviewMemoryInUseMathematicalPrograms {
         Body: {
               for IndexGeneratedMathematicalPrograms do
                  p_MemInUseMPs(IndexGeneratedMathematicalPrograms) := GMP::Instance::GetMemoryUsed(IndexGeneratedMathematicalPrograms);
               endfor ;
         }
      }
      Procedure pr_DeleteAllGeneratedMathematicalPrograms {
         Body: {
               while card( AllGeneratedMathematicalPrograms)  do
                  GMP::Instance::Delete( first( AllGeneratedMathematicalPrograms ) );
               endwhile ;
         }
      }
   }

.. tip::
 
   Often times there are many variables for which the solution is obvious; decision variables from the past, filled tanks cannot be filled further, built distribution centers need not be build again, and so on. You can model these variables by setting their ``nonvar`` suffix to 1.  See also the option `Eliminate nonvar columns`

Minimizing memory used for element spaces
--------------------------------------------

AIMMS maintains a mapping between elements (strings) and numbers per root set. This mapping is the *element space*. The element space of the set :aimms:set:`Integers` is very small as this is just an arithmetic operation without the need for additional memory. When your elements are integers, making the corresponding set a subset of :aimms:set:`Integers` helps you in reducing the element space. 


Related Topics
--------------------

* `AIMMS User's Guide <https://documentation.aimms.com/_downloads/AIMMS_user.pdf>`_: Section "Observing identifier cardinalities"

* `AIMMS The Language Reference <https://documentation.aimms.com/_downloads/AIMMS_ref.pdf>`_: Chapter "Execution Efficiency Cookbook", Section "Reducing the number of elements"

* `AIMMS Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_
    
* :doc:`../134/134-Monitoring-Memory-Use`



