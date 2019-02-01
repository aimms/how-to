.. IMAGES

.. |SetMappingDeclarations| image:: /Images/109-convert-compound-sets/SetMappingDeclarations.png


Prepare for the Deprecation of Compound Sets
==============================================

.. meta::
   :description: Procedure for adapting projects to remove compound sets.
   :keywords: compound, set, convert, adapt, deprecate

.. note:: We are actively updating this topic during the deprecation stages. Your feedback is welcome and appreciated, as it may help others facing the same issue.

.. _Section_Summary:
    
Summary
-------
AIMMS will deprecate compound sets **soon after January 1, 2020**.

The functionality of compound sets can be achieved with a :term:`set mapping<Set mapping>`. 

This document provides a process to replace the compound sets with a set mapping.

* :ref:`Section-Identify-Compound-Set`

* :ref:`Section-Conversion-Procedure`

* :ref:`Section-Terminology`

For an overview of the rationale and timeline for deprecating compound sets, read **AIMMS Knowledge:** :doc:`109-deprecate-compound-sets-overview`

.. _Section-Identify-Compound-Set: 

Identifying compound sets in your application
---------------------------------------------

A compound set is defined as one of these:

* It is a subset of a Cartesian product with an index or element parameter declared in its attribute form.

* It is a subset of another compound set.

We provide a library with tools to identify compound sets based on these characteristics. 

To identify compound sets in your application, 

#. Download the attached :download:`AIMMS project download <../../Resources/Other/CompoundSets/Downloads/DeprecateCompoundSets.zip>` 

#. Copy the ``DeprecateCompoundSetUtilities`` library to your AIMMS project.

#. Run the procedure ``dcsu::prIdentifyCompoundSets``. This tests for compound sets, according to the following rules:

    * A set whose string in the ``subset of`` attribute has a comma, and has defined the attribute ``index`` or the attribute ``parameter``. (These are compound root sets.)

    * A set with a compound set as its domain set. (These are not compound root sets.)

#. The procedure fills the sets ``dcsu::sCompoundRootSets``, ``dcsu::sCompoundSets``, and ``dcsu::sCompoundSetsThatAreNotRootSets``. Using these results, you may continue to the conversion procedure below.


.. _Section-Conversion-Procedure:

Replacing compound sets with set mapping
---------------------------------------------------

.. sidebar:: Background: Idea behind conversion procedure
     
    In the conversion process, :term:`compound data` identifier ``P`` has compound indexes in its index domain, while its shadow ``P_Shadow`` has the corresponding :term:`set mapping` indexes in its index domain. This is an **atomic shadow identifier** as it has only :term:`atomic index` es, some of which are set mapping indexes.

    The ``dcsu`` library caches atomic :term:`shadow parameter` s in a runtime library while the compound data identifiers are transformed to atomic data identifiers. Additionally, there are temporary procedures in that runtime library to copy the data from the compound data identifiers to the atomic shadow parameters and later from the atomic shadow parameters to the transformed atomic data identifiers.

This conversion procedure explains how to convert compound sets to set mappings in your application. This ensures that your model will function in the same way but without compound sets.

.. note::
    The conversion procedure contains a multitude of steps, and you may wonder whether this is necessary?

    To determine the **scope** that this conversion procedure needs to handle, 
    note that compound data is present in AIMMS Cases and compound data identifiers 
    are present in both WinUI and WebUI pages of that AIMMS application. 
    AIMMS cases cannot be edited manually.
    The format of both WinUI and WebUI pages are designed for fast serialization instead of for human editing. 
    Obviously, this conversion procedure should not overlook the need to adapt the model itself.
    
    The multitude of steps are too gradually transform the information in cases, pages, and model.

|

.. topic:: Overview of the conversion procedure

    :ref:`Step 1: <Step_conversion_Backup>`
    Create backups of your application and cases.

    :ref:`Step 2: <Step_conversion_use_Utility>` 
    Add ``DeprecateCompoundSetUtilities`` library to your application.

    :ref:`Step 3: <Step_conversion_Create_Set_Mapping>` 
    Create :term:`Set Mapping<Set mapping>` with data of compound sets.

    :ref:`Step 4: <Step_conversion_Create_Set_Mapping_declarations>` 
    Create :term:`Set Mapping<Set mapping>` declarations and copy them to your main model.

    :ref:`Step 5: <Step_Conversion_Copy_Input_Cases>` 
    Create a :term:`shadow case<Shadow case>` for each case with shadow data for the compound data identifiers.

    :ref:`Step 6: <Step_Conversion_Adapt_Model>` 
    Adapt the model to remove compound sets.

    :ref:`Step 7 <Step_Conversion_Move_Indexes>` 
    Move compound indexes to the corresponding set mapping sets.

    :ref:`Step 8: <Step_Conversion_Backward_Copy>` 
    Copy each :term:`shadow case<Shadow case>` back to its corresponding original case. 

    :ref:`Step 9: <Step_Conversion_Final>` 
    Remove ``DeprecateCompoundSetUtilities`` library from your application.


|

.. _Step_conversion_Backup:

Step 1: Create backups of your data
++++++++++++++++++++++++++++++++++++++

The importance of creating backups before starting maintenance on your projects cannot be overemphasized.

#. Simply create a physical copy of the project and cases and store this in a safe place.

#. Consider putting the project in a Source Code Management system, if you haven't done so already.  

.. _Step_conversion_use_Utility:

Step 2: Add library DeprecateCompoundSetUtilities
++++++++++++++++++++++++++++++++++++++++++++++++++++

The :download:`AIMMS project download <../../Resources/Other/CompoundSets/Downloads/DeprecateCompoundSets.zip>` provides an example app and utility library ``DeprecateCompoundSetUtilities``. 

Copy the library from that example and add it to your application.


.. _Step_conversion_Create_Set_Mapping:

Step 3: Create Set Mapping
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Use the data from compound sets in your project to create corresponding relations. 
The definition (if any) of a compound set must be suitable for a relation as well. 

Consider the following example:

.. code-block:: aimms

    Set C {
        SubsetOf: (S, T, U);
        Tags: (TS, TT, TU);
        Index: h ;
        Definition: {
            { (i,j,k) | pAllowedElementsC(i,j,k) = 1 }
        }
    }
    Set D {
        SubsetOf: C;
        Index: g ;
        definition: {
            { h | pAllowedElementsD(h.TS, h.TT, h.TU) = 1 }
        }
    }
 
In the example above, the definition of ``C`` can also be used for a relation, :math:`R`, that is a subset of the Cartesian product :math:`S \times T \times U`. The definition of ``D`` cannot be used for a relation, so it must be rewritten:

.. code-block:: aimms

    Set D {
        SubsetOf: C;
        Index: g ;
        definition: {
            { (i,j,k) | pAllowedElementsC(i,j,k) = 1 and pAllowedElementsD(i, j, k) = 1 }
        }
    }
 
The new definition of ``D`` is now based on tuples instead of individual elements and can be used for a relation.


.. _Step_conversion_Create_Set_Mapping_declarations:

Step 4: Create Set Mapping declarations
++++++++++++++++++++++++++++++++++++++++

Now let's create a :term:`set mapping<Set mapping>` for each compound set in your model. Group set mappings according to namespace (main model, library or module).

Open the WinUI page: ``Deprecate Compound Set Control Page`` of the library ``DeprecateCompoundSetUtilities``, and press the button ``Create Set Mapping Declarations``.  A section named ``set mapping declarations`` appears in the main model. 

Sections named ``<prefix> set mapping declarations`` appear in each library/module where compound sets are defined. These sections are created in the runtime library ``CompoundSetMappingRuntimeLibrary`` as runtime libraries are the only place where a library or main model may create new AIMMS code. 

The model explorer should now look something like this:

|SetMappingDeclarations|

Perform the following sequence for **each** ``set mapping declarations`` section.

#. Go to *Edit > Export* to save a file (e.g., ``smd.ams``).

#. Select focus on the main model, library or module and create a section named ``Set Mapping Declarations``.

#. Select that newly created section and go to *Edit > Import* to select the file you saved (e.g., ``smd.ams``).

.. caution:: Do not Copy/Paste the section ``Set Mapping Declarations`` of the runtime library! When you Copy/Paste, the copied section still contains references to the runtime indexes. This causes compilation errors upon restart.

Now is a good time to save the project, exit AIMMS, and create another backup copy of your project.


.. _Step_Conversion_Copy_Input_Cases:

Step 5: Create shadow cases
++++++++++++++++++++++++++++++++++++++++

:term:`Shadow cases<Shadow case>` are cases where the compound data is replaced by atomic shadow data.

You can convert cases with compound data to shadow cases using a tool in the ``DeprecateCompoundSetUtilities`` library.

You can convert multiple cases contained in one folder using the *Folder* option, or convert each case separately using the *File* option.

#. Go to ``Deprecate Compound Set Control Page`` of the ``DeprecateCompoundSetUtilities`` library.

#. In the section labeled *Forward - creating shadow cases*:

    a. Specify the input file/folder (to pull original cases containing compound data).
    #. Specify the output file/folder (to push converted cases containing atomic data). 
    #. Then click the *Copy* button to convert.

.. _Step_Conversion_Adapt_Model:

Step 6: Adapt model to remove compound sets
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


This section shows how to convert models using compound sets to use the set mappings created in :ref:`step 3 <Step_conversion_Create_Set_Mapping>` above.

Example case
^^^^^^^^^^^^^^^

In this conversion step we will use a running example that contains:

* One dimensional sets :math:`S, T, U`, with indexes respectively :math:`i, j, k`.

* A relation :math:`R` that is subset of the Cartesian product :math:`S \times T \times U`.

* A compound set :math:`C` with index :math:`h` defined as :math:`\{ (i, j, k) | (i, j, k) \in R \}`. The tags of this compound set are :math:`(TS,TT,TU)`

* A compound subset :math:`D \subset C` with index :math:`g`. Note that :math:`D` inherits its tags from :math:`C`.

* A parameter :math:`P` declared over the index for the compound set: :math:`P_h`

* A parameter :math:`P1` declared over the index for the compound subset: :math:`P1_g`

* A parameter :math:`Q` declared over the indexes for the one dimensional sets: :math:`Q_{i,j,k}`

* A parameter :math:`Q1` declared over the index :math:`i`: :math:`Q1_i`


Replace use of tags
^^^^^^^^^^^^^^^^^^^
The following Parameter contains a tag referencing a compound set:

.. code-block:: aimms

    Parameter p1 {
        IndexDomain: h;
        Definition: A(h.ts);
    }
        
AIMMS displays the error message: ``The "TS" is not a tag that can be associated with index "h".`` 

You can replace it with a tag referencing a set mapping:

.. code-block:: aimms

    Parameter p1 {
        IndexDomain: h;
        Definition: A(epTag_C_TS(h));
    }



Replace atomic indexes with set mapping index
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Consider the declaration of compound data parameter ``P``:

.. code-block:: aimms

    Parameter P {
        IndexDomain: h;
    }

Then using ``P`` is not allowed in an expression such as:

.. code-block:: aimms

    Parameter PS {
        IndexDomain: (i,j,k);
        Definition: p(i,j,k);
    }
        
It is not allowed, as the automatic mapping between ``h`` and ``(i,j,k)`` is no longer supported.

AIMMS displays a compilation error ``The number of arguments in the parameter "P" is not correct.`` 

You can replace this definition by: 
        
.. code-block:: aimms

    Parameter PS {
        IndexDomain: (i,j,k);
        Definition: sum(h|(i,j,k,h) in sMappingSet_C_Relation,p(h));
    }

Replace the function Tuple
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The function ```Tuple`` is a predeclared function to create an element in a compound set from elements in the atomic sets that together form the domain of that compound set.

Consider the function: 

.. code-block:: aimms

    epC := Tuple( epS, epT, epU );
        
Here ``epS``, ``epT``, and ``epU`` contain the elements, and Tuple will create a corresponding element in the compound set ``C``, where ``C`` is the range of the element parameter ``epC``.
        
With the deprecation of compound sets, ``Tuple`` is no longer supported , and this should be replaced by:

.. code-block:: aimms

    epC := first( iSMI_C | ( epS, epT, epU, iSMI_C ) in sSetMappingRelation_C );
        
        
.. _Step_Conversion_Move_Indexes:

Step 7: Move compound indexes to set mapping sets
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

To ensure :term:`screen definitions<Screen definition>` are not broken, you must move indexes from the declarations of compound sets to the declaration of the corresponding set mapping set.

To move an index that is declared as part of a set declaration:

#. Delete it using the wizard at the index attribute.

#. Re-create it in the destination set.


.. _Step_Conversion_Backward_Copy:

Step 8: Move shadow cases back to original cases
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

You can convert shadow cases created in :ref:`step 5 <Step_Conversion_Copy_Input_Cases>` back to the original case locations using the same tool in the ``DeprecateCompoundSetUtilities`` library.

You can convert multiple cases contained in one folder using the *Folder* option, or convert each case separately using the *File* option.

#. Go to ``Deprecate Compound Set Control Page`` of the ``DeprecateCompoundSetUtilities`` library.

#. In the section labeled *Backward - creating cases with original identifiers without compound data*:

    a. Specify the input file/folder (to pull cases containing converted data).
    #. Specify the output file/folder (to push to the original case folder location). 
    #. Then click the *Copy* button to convert.


.. _Step_Conversion_Final:

Step 9: Remove the library DeprecateCompoundSetUtilities
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Now that you have removed compound sets from your project, you can remove the library ``DeprecateCompoundSetUtililities``.

.. _Section-Terminology:

Glossary of Terms Used
----------------------

.. glossary::

    Atomic sets
        One-dimensional sets that are not compound sets are called **atomic sets**. 
        Examples of atomic sets are sets containing names, calendars and subsets of the set Integers. 
        To declare a relation, AIMMS only allows atomic sets in the ``subset of`` attribute of that relation.

    Atomic index
        An **atomic index** is an index in an atomic set. A **compound index** is an index in a compound set.   
    
    Set mapping
        A **set mapping** is a collection of identifiers that together provide an alternative for the functionality of a single compound set. 
        
        A set mapping consists of:
        
        * A **set mapping set** is an atomic set with elements that look like elements from a compound set. 

        * A **set mapping index** is an index in a set mapping set. Note that a set mapping index is an atomic index.

        * A **set mapping relation** is a relation that contains the same set of tuples as a compound set.

        * A **set mapping parameter** is an element parameter that contains the data to handle the "tags" functionality of a compound set.

    Compound data         
        A **compound data identifier** is a parameter, variable, or constraint 
        with at least one compound index in its index domain. 
        Thus, **compound data** is the data of a compound data identifier.   

    Screen definition
        A **screen definition** is a serialized representation of a screen. 
        The point and click types of UI provided by AIMMS, both WinUI and WebUI, 
        store these **screen definitions** as text files within an AIMMS project.

    Shadow case
        A case containing the same data references to its corresponding namesake but replacing compound data with atomic set mapping data to allow for the removal of compound sets.

    Shadow parameter
        Consider a parameter ``A``, then a **shadow parameter**, say ``A_Shadow``, is a parameter with the same element values. 



.. topic:: Further support

    For further information on the deprecation of compound sets, contact `AIMMS Support Team <mailto:support@aimms.com>`_.

.. include:: ../../includes/form.def
