How to Handle the Deprecation of Compound sets
==============================================

Announcement
------------ 

The AIMMS Product Owner announced the deprecation of compound sets as follows: Besides prioritizing and building new functionality, it is important for us, as a software company, to deprecate functionality. For instance, some functionality may become obsolete, there may be better or similar alternatives that are easier to maintain, or its usage may not be high enough. Deprecating something is always hard, but it is necessary to simplify our work and make room for innovation and improvements on crucial features. With this in mind, we decided to deprecate the use of Compound Sets.

What are compound sets in AIMMS?
--------------------------------

Consider several one dimensional sets: :math:`S_1, S_2, ..., S_n`. As you know, a relation :math:`R` is a subset of a Cartesian product: :math:`S_1 \times S_2 \times ... \times S_n`. 

In AIMMS, a relation :math:`R` is **transformed** to a compound set, say :math:`C`, when its attribute form contains the declaration of an index, say :math:`c`. 

Once a compound set is formed, it allows for the selection of components via tags. Let's assume :math:`C` is declared with the tags :math:`(T_1, T_2, ..., T_n)`, then selecting component :math:`i` of tuple :math:`c` is obtained by the notation :math:`c.T_i`.



Why deprecate compound sets?
----------------------------

There are several technical reasons to deprecate compound sets:

#. Most AIMMS applications used in production read data from relational databases. The compound set concept is not present in relational databases. So by creating a model based on compound sets, you will later on need to create additional mappings and code to populate identifiers defined over compound sets.

#. As indicated above by the product owner, by maintaining support of compound sets, we would hinder the innovation of other new components of AIMMS, for instance CDM and WebUI.

#. The initial design of compound sets dates back to the late 90's. At the time it was deemed that compound sets should be created automagically based on root sets and the use of an index or parameter in the set declaration. This may give an application a 'surprising' behavior: 

	#. when the root sets of two conceptually different compound sets collide, then numbering and ordering of elements is different from the numbering and ordering of elements expected.
	
	#. when an index or element parameter is added to the declaration of a relation, suddenly the performance of the appliction may degrade (see also next point).

#. Last but not least is the present but outdated implementation of compound sets. As you may know, at AIMMS, the execution engine is rewritten to allow for parallel execution of multi-dimensional statements but this parallel engine does not handle compound sets. The vintage implementation of compound sets, dating back to the 90's, suffers from some serious efficiency pitfalls. 

This article
------------

Instead of continuing to let applications deploying compound sets suffer from the above disadvantages, this article describes a rewrite procedure.
This rewrite procedure is designed to make minimal changes to your application and at the same time:

#. Avoid the pitfalls due to the "automagic" design of compound sets (mentioned in point 3 above).

#. Allow to deploy the efficiency improvements already implemented in the new parallel execution engine.

The remainder of this article will explain:

#. How to find the compound sets already created in your application?

#. How to create a so-called set mapping with which you can adapt you model?

#. How to replace the use of tags with element parameters?

#. How to replace the compound indices?

#. How to handle 'ordering'?

Running example
---------------

In the remainder we will use a running example that contains:

#. One dimensional sets :math:`S, T, U`, with indices respectively :math:`i, j, k`.

#. A relation :math:`R` that is subset of the Cartesian product :math:`S \times T \times U`.

#. A compound set :math:`C` with index :math:`h` defined as :math:`\{ (i, j, k) | (i, j, k) \in R \}`.

#. A parameter :math:`P` declared over the index for the compound set: :math:`P_h`

#. A parameter :math:`Q` declared over the indices for the one dimensional sets: :math:`Q_{i,j,k}`

#. A parameter :math:`Q1` declared over the index :math:`i`: :math:`Q1_i`


How to find the compound sets already created in your application?
------------------------------------------------------------------

A compound set is 

#. it is a subset of a Cartesian product in conjunction with an index or element parameter declared in its attribute form, or

#. a subset of another compound set.

To test for the first type of compound set, we test as follows:




The attribute ``subset of`` of a compound set contains either another compound set or  


How to create a so-called mapping set with which you can adapt you model?
-------------------------------------------------------------------------

How to replace the use of tags with element parameters? 
-------------------------------------------------------

How to replace the compound indices?
------------------------------------

How to handle 'ordering'?
-------------------------


Summary
-------

The functionality of compound sets described in the overview "Summary of what you can do with compound sets" can be achieved with a set mapping. 
In this "How to" article, we've described how you can replace the compound sets with a set mapping. As a set mapping is in essence no more than a normal one-dimensional root set and a relation, existing parallelized technology can be deployed to efficiently handle the statements involved. 

Further information
-------------------
Questions: support@aimms.com

