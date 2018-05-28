How to Handle the Deprecation of Compound sets
==============================================

:author: Chris Kuip

.. note:: This document and corresponding AIMMS :download:`project <Resources/Other/CompoundSets/Downloads/DeprecateCompoundSets.zip>` with running example and utility library (prefix dcsu) is actively being worked on. Your timely feedback is much appreciated as working your feedback may help others facing the same problem. Thanks, Chris.

.. _Section-Announcement:

Announcement
------------ 

The AIMMS Product owner announced the deprecation of compound sets in the AIMMS Newsletter of May 2018.
This document provides clarity of the why and when. More importantly, it hopes to help with the how of deprecating compound sets from your application. 

.. Kim: should people be offered to register for the AIMMS news letter here?

.. _Section-Def-Compound:

What are compound sets in AIMMS?
--------------------------------

Consider several one dimensional sets: :math:`S_1, S_2, ..., S_n`. As you know, a relation :math:`R` is a subset of a Cartesian product: :math:`S_1 \times S_2 \times ... \times S_n` with :math:`n \geq 2`.

In AIMMS, a relation :math:`R` is **transformed** to a compound set, say :math:`C`, when its attribute form contains the declaration of an index, say :math:`c` or an element parameter, say :math:`e`

Once a compound set is formed, it allows for the selection of components in tuples via tags. Let's assume :math:`C` is declared with the tags :math:`(T_1, T_2, ..., T_n)`, then selecting component :math:`i` of tuple :math:`c` is obtained by the notation :math:`c.T_i`.

..Section-When:

How to plan for the adaptation of your model?
---------------------------------------------

.. Aka the "when" of this article. 

In order to plan the adaptation of your projects, we provide the following time line:

#. The first release of AIMMS IDE after July 1, 2018, does not provide the attributes 'index' or 'parameter' in the attribute form of relations. This step prevents the creation of new compound sets.

#. The first release of AIMMS IDE after January 1, 2019, (6 months later) warns the model builder against existing compound sets.  

#. The first release of AIMMS IDE after July 1, 2019 (again 6 months later) issues a non-critical error message in developer mode for compound sets created. (Nb. Here a non-critical error is an error message after which AIMMS is still able to present data and execute procedures).  

#. The first release of AIMMS IDE after January 1, 2020 (again 6 months later), will definitely not be able to work when compound sets are present in the application.

This timeline increases the urgency for adaptation slowly when the time nears that AIMMS is no longer able to support compound sets.


.. _Section-Why:

Why deprecate compound sets?
----------------------------

.. Aka the "why" of this article. 

There are several technical reasons to deprecate compound sets:

#. Most AIMMS applications used in production read data from relational databases. The compound set concept is not present in relational databases. So by creating a model based on compound sets, you will have to create additional code that converts the current identifiers in your model to identifiers that can be used to exchange data with relational databases.

#. At AIMMS, by maintaining support of compound sets, we would hinder the innovation of other new components of AIMMS, including CDM and WebUI.

#. The initial design of compound sets dates back to the late 90's. At the time it was deemed that compound sets should be created automagically based on root sets and the use of an index or parameter in the set declaration. This may give an application a 'surprising' behavior: 

    #. when the root sets of two conceptually different compound sets collide, then numbering and ordering of elements is different from the numbering and ordering of elements expected.
    
    #. when an index or element parameter is added to the declaration of a relation, suddenly the performance of the application may degrade (see also next point).

#. Last but not least is that the present implementation of compound sets is outdated. As you may know, at AIMMS, the execution engine is rewritten to allow for parallel execution of multi-dimensional statements but this parallel engine does not handle compound sets. The vintage implementation of compound sets, also dating back to the late 90's, suffers from some serious efficiency pitfalls. 

.. _Section-Terminology:

Terminology
-----------

Some terminology is introduced in this section that will be used in this article to clarify the work that needs to be done.

*   One-dimensional sets that are not compound sets are called **atomic** sets. 
    Examples of atomic sets are sets containing names, calendars and subsets of the set Integers. 
    To declare a relation, only atomic sets are allowed in the subset of attribute of that relation.
    
*   An **atomic** index is an index in an atomic set. A **compound** index is an index in a compound set.   
    
*   A **set mapping** is a collection of identifiers that together mimic a single compound set. Set mappings are introduced in this article to replace compound sets. A set mapping consists of:
    
    * A **set mapping set** is an atomic set with elements that look like elements from a compound set. 

    * A **set mapping index** is an atomic index in a set mapping set. 

    * A **set mapping relation** is a relation that contains the same set of tuples as a compound set.

    * A **set mapping parameter** is an parameter that contains the data to handle the "tags" functionality of a compound set.
        
*   The parameters, variables and constrains of an application contain the data of that application. A **compound data identifier** is a parameter, variable or constraint, such that at least one index in the index domain of that identifier is a compound index. For the purposes of this article, **compound data** is the data of a compound data identifier.

*   Consider a parameter ``A``, then a **shadow** parameter, say ``A_Shadow``, is a parameter that holds the same data as ``A``.
    In the context of this article, *same data* should be interpreted as, that there is a clear one-to-one correspondence between the elements of ``A`` and ``A_Shadow`` such that the values of the corresponding elements are the same. 
    As an example, consider a compound data identifier ``P`` having compound indices in its index domain, and a shadow ``PS`` having the corresponding set mapping indices in its index domain.

*   A **screen definition** is a serialized representation of a screen. 
    The point and click types of UI provided by AIMMS, both WinUI and WebUI, store these **screen definitions** as text files within an AIMMS project.
    
.. _Section-Scope:

Scope of the work
-----------------

To determine the scope of the work involved in adapting an AIMMS application, note that compound data is present in **AIMMS Cases** and compound data identifiers are present in the **AIMMS Screen definitions** of that AIMMS application. As you know AIMMS cases cannot be edited manually.  In addition, the format of screen definitions is designed for fast serialization instead of for human editing. In addition, the model needs to be adapted such that compound sets are no longer used. 

The overall deprecation procedure is depicted below:

.. image::  Resources/Other/CompoundSets/Images/DeprecateCompoundSets.png 



.. _Section-Conversion-Procedure:

The conversion procedure proposed in this article
-------------------------------------------------

The conversion procedure consists of the following conversion steps:

#. Create backups of your application and cases.

#. Start with adding the library ``DeprecateCompoundSetUtilities`` to your application.

#. Create the Set Mapping declarations and copy these declarations to your main model.

#. For each case: create a new case without compound data but with shadow data for the compound data identifiers. We call these cases, the shadow cases.

#. Adapt the model such that compound sets are no longer needed. 

#. Move the compound indices to the corresponding set mapping sets. This step is essential such that screen definitions can be retained unaltered.

#. For each shadow case, copy that shadow case back to the original case.

#. Remove the library ``DeprecateCompoundSetUtilities`` from your application.


Running example
---------------

In the remainder we will use a running example that contains:

#. One dimensional sets :math:`S, T, U`, with indices respectively :math:`i, j, k`.

#. A relation :math:`R` that is subset of the Cartesian product :math:`S \times T \times U`.

#. A compound set :math:`C` with index :math:`h` defined as :math:`\{ (i, j, k) | (i, j, k) \in R \}`. The tags of this compound set are :math:`(TS,TT,TU)`

#. A compound subset :math:`D \subset C` with index :math:`g`. Note that :math:`D` inherits its tags from :math:`C`.

#. A parameter :math:`P` declared over the index for the compound set: :math:`P_h`

#. A parameter :math:`P1` declared over the index for the compound subset: :math:`P1_g`

#. A parameter :math:`Q` declared over the indices for the one dimensional sets: :math:`Q_{i,j,k}`

#. A parameter :math:`Q1` declared over the index :math:`i`: :math:`Q1_i`

In the following sections, some of these steps will be discussed in more detail:

Conversion step 1: Make a backup
--------------------------------

The importance of creating backups cannot be overemphasized as it is easily overlooked.

Conversion step 2: Copy the utility library
-------------------------------------------

For now: the download provides an example app, mimicking the running example used in this article. This example app also contains the utility library ``DeprecateCompoundSetUtilities``. Please copy the library from that example and use it in your application.

Conversion step 3: Create Set Mapping
-------------------------------------

In this conversion step a set mapping is created for each compound set in your model.

#. Please open the WinUI page: ``Deprecate Compound Set Control Page`` of the library ``DeprecateCompoundSetUtilities``, and press button ``Create Set Mapping Declarations``. This action will create a section named ``set mapping declarations``.  This section is created at the wrong place in the model tree, namely in the runtime library ``CompoundSetMappingRuntimeLibrary``. You should copy this section to your main model in the following steps:

#. AIMMS Menu - Edit - Export - to a file, say ``smd.ams``.

#. Select focus on the main model, create a section named ``Set Mapping Declarations``.

#. Select that newly created section and via the AIMMS Menu - Edit - Import - the file created in the first step of this section.

The model explorer should now look something like this:

.. image::  Resources/Other/CompoundSets/Images/SetMappingDeclarations.png 

.. caution:: When you use copy and paste on the section ``Set Mapping Declarations`` of the runtime library, the copied section will still contain references to the runtime indices. A subsequent restart of your application will have compilation errors.

Conversion step 4: Copy the input cases
---------------------------------------

For each case, you will need to press the button: 



How to find the compound sets already created in your application?
------------------------------------------------------------------

Repeating the above, a compound set has one of the following two characteristics:

#. it is a subset of a Cartesian product in conjunction with an index or element parameter declared in its attribute form, or

#. a subset of another compound set.

We can use these characteristics to identify the compound sets. 
To test for a compound set with the first characteristic, we test whether the string in the ``subset of`` attribute has a komma, and whether the attribute ``index`` or the attribute ``parameter`` has content. The sets that have this characteristic are also called compound root sets.
To test for a compound set with the second characteristic, we check for each set whether its domain set is a compound set.

The procedure ``dcsu::prIdentifyCompoundSets`` that does just this, and fills the sets ``dcsu::sCompoundRootSets``, ``dcsu::sCompoundSets``, and ``dcsu::sCompoundSetsThatAreNotRootSets``.

Now that we have compound sets, and divided them in compound root sets and compound subsets, we need to start adapting our application. As compound subsets depend on compound root sets, we cannot start with the root sets.  So let's start providing alternatives for compound subsets first.

How to create a so-called subset compound set mapping with which you can adapt your model?
------------------------------------------------------------------------------------------


How to create a so-called root compound set mapping with which you can adapt your model?
----------------------------------------------------------------------------------------

We will create a set mapping on our running example, with the following steps:

#. We create several identifiers:

    #. We create a one-dimensional set :math:`CM` and give it an index :math:`i_{cm}` and an element parameter :math:`ep_{cm}`.

    #. We create a :math:`3+1` dimensional relation :math:`RM \subset S \times T \times U \times CM`.

    #. We create three element parameters EPTS(i_cm) in S, EPTT(i_cm) in T, and EPTU(i_cm) in U.
    
    #. We create three helper scalar element parameters EPHS in S, EPHT in T, and EPHU in H.
    
#. We copy the data, following steps are parallel per element :math:`h=(h.TS,h.TT,h.TU) \in C`:

    #. Let EPHS be h.TS, EPHT be h.TT, and EPHU be h.TU.

    #. We "copy" the element :math:`h` to :math:`CM` as follows: ``SetElementAdd(CM,ep_cm,formatString("(%e,%e,%e)",EPHS,EPHT,EPHU))``

    #. We copy the corresponding element from :math:`R` to :math:`RM` as follows: add :math:`(EPHS,EPHT,EPHU,ep_{cm})` to :math:`RM`.

    #. We fill :math:`EPTS(ep_{cm})` with :math:`EPHS`, :math:`EPTT(ep_{cm})` with :math:`EPHT`, and :math:`EPTU(ep_{cm})` with :math:`EPHU`.

    
A lot of work needs to be written.  
    


Summary
-------

The functionality of compound sets described in the overview "Summary of what you can do with compound sets" can be achieved with a set mapping. 
In this "How to" article, we've described how you can replace the compound sets with a set mapping. As a set mapping is in essence no more than a normal one-dimensional root set and a relation, existing parallelized technology can be deployed to efficiently handle the statements involved. 

Collateral benefits
-------------------

Instead of continuing to let applications deploying compound sets suffer from the above disadvantages, this article describes a rewrite procedure.
This rewrite procedure is designed to make minimal changes to your application and at the same time:

#. Avoid the pitfalls due to the "automagic" design of compound sets (mentioned in point 3 above).

#. Allow to deploy the efficiency improvements already implemented in the new parallel execution engine.


Further information
-------------------

To obtain further information on the deprecation of compound sets, please send an e-mail to support@aimms.com or fill in the form, including your e-mail address, below:

.. include:: includes/form.def
