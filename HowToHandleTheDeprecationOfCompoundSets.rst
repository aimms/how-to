How to Handle the Deprecation of Compound sets
==============================================

:author: Chris Kuip

.. note:: This document and corresponding AIMMS :download:`project <Resources/Other/CompoundSets/Downloads/DeprecateCompoundSets.zip>` with running example and utility library (prefix dcsu) is actively being worked on. Your timely feedback is much appreciated as working your feedback may help others facing the same problem. Thanks, Chris.

Announcement
------------ 

The AIMMS Product Owner announced the deprecation of compound sets as follows: Besides prioritizing and building new functionality, it is important for us, as a software company, to deprecate functionality. For instance, some functionality may become obsolete, there may be better or similar alternatives that are easier to maintain, or its usage may not be high enough. Deprecating something is always hard, but it is necessary to simplify our work and make room for innovation and improvements on crucial features. With this in mind, we decided to deprecate the use of Compound Sets.

What are compound sets in AIMMS?
--------------------------------

Consider several one dimensional sets: :math:`S_1, S_2, ..., S_n`. As you know, a relation :math:`R` is a subset of a Cartesian product: :math:`S_1 \times S_2 \times ... \times S_n` with :math:`n \geq 2`.

In AIMMS, a relation :math:`R` is **transformed** to a compound set, say :math:`C`, when its attribute form contains the declaration of an index, say :math:`c` or an element parameter, say :math:`e`

Once a compound set is formed, it allows for the selection of components in tuples via tags. Let's assume :math:`C` is declared with the tags :math:`(T_1, T_2, ..., T_n)`, then selecting component :math:`i` of tuple :math:`c` is obtained by the notation :math:`c.T_i`.

Further terminology: One-dimensional sets that are not compound sets are called atomic sets. Examples of atomic sets are sets containing names and subsets of the set Integers. To declare a relation, only atomic sets are allowed in the subset of attribute of that relation.

How to plan for the adaptation of your model?
---------------------------------------------

In order to plan the adaptation of your projects, we provide the following time line:

#. The first release of AIMMS IDE after July 1, 2018, does not provide the attributes 'index' or 'parameter' in the attribute form of relations. This step prevents the creation of new compound sets.

#. The first release of AIMMS IDE after January 1, 2019, (6 months later) warns the model builder against existing compound sets.  

#. The first release of AIMMS IDE after July 1, 2019 (again 6 months later) issues a non-critical error message in developer mode for compound sets created. (Nb. Here a non-critical error is an error message after which AIMMS is still able to present data and execute procedures).  

#. The first release of AIMMS IDE after January 1, 2020 (again 6 months later), will definitely not be able to work when compound sets are present in the application.

This timeline increases the urgency for adaptation slowly when the time nears that AIMMS is no longer able to support compound sets.


Why deprecate compound sets?
----------------------------

There are several technical reasons to deprecate compound sets:

#. Most AIMMS applications used in production read data from relational databases. The compound set concept is not present in relational databases. So by creating a model based on compound sets, you will later on need to create additional mappings and code to populate identifiers defined over compound sets.

#. As indicated above by the product owner, by maintaining support of compound sets, we would hinder the innovation of other new components of AIMMS, for instance CDM and WebUI.

#. The initial design of compound sets dates back to the late 90's. At the time it was deemed that compound sets should be created automagically based on root sets and the use of an index or parameter in the set declaration. This may give an application a 'surprising' behavior: 

	#. when the root sets of two conceptually different compound sets collide, then numbering and ordering of elements is different from the numbering and ordering of elements expected.
	
	#. when an index or element parameter is added to the declaration of a relation, suddenly the performance of the appliction may degrade (see also next point).

#. Last but not least is that the present implementation of compound sets is outdated. As you may know, at AIMMS, the execution engine is rewritten to allow for parallel execution of multi-dimensional statements but this parallel engine does not handle compound sets. The vintage implementation of compound sets, also dating back to the late 90's, suffers from some serious efficiency pitfalls. 

This article
------------

Instead of continuing to let applications deploying compound sets suffer from the above disadvantages, this article describes a rewrite procedure.
This rewrite procedure is designed to make minimal changes to your application and at the same time:

#. Avoid the pitfalls due to the "automagic" design of compound sets (mentioned in point 3 above).

#. Allow to deploy the efficiency improvements already implemented in the new parallel execution engine.

Before we continue, let's determine the scope of the work first.  Compound set data is present in:

#. The model itself, obviously.

#. Cases already saved for the model; for instance the scenarios you are actively working on.

#. Screens, WebUI or WinUI pages, may reference data over compound sets.

Therefore we need a global procedure that handles the transition of the model from one with compound sets, to an equivalent one without compound sets, taking into account the potential presence of cases and screens. The overall procedure is depicted below:

.. image::  Resources/Other/CompoundSets/Images/DeprecateCompoundSets.png 

Some remarks on the above picture:

#. In blue are the stages of the APP.

#. Each sentence that begins with "manually" involves actions from the model builder.

#. Some steps are just pressing a button.

#. The yellow oval represents the model preparation and data saving phase.

#. The green oval represents the model finishing and data restoring phase.


The remainder of this article will explain:

How to find the compound sets already created in your application? A first step is to identify the compound sets in your application. To do this before January 1, 2019, we provide a small utility that identifies these sets.

Once the compound sets are identified, the application can be adapted by handling one compound set after another.
We will describe a procedure that takes into account:


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

Further information
-------------------

To obtain further information on the deprecation of compound sets, please send an e-mail to support@aimms.com.

