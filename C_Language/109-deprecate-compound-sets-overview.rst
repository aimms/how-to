Overview: Deprecation of Compound Sets
========================================

.. meta::
   :description: An overview of the rationale and timeline for deprecating compound sets.
   :keywords: compound, sets


.. sidebar:: Announcing deprecation compound sets

    .. image:: ../Resources/C_Language/Images/109/hoorn.png


    
.. _Section-Announcement:

Overview
---------

In May 2018 AIMMS announced a plan to deprecate compound sets in stages. Support for compound sets will be fully deprecated **after January 1, 2020**. 

To find out how to identify and remove compound sets from your model without losing functionality, read **AIMMS Knowledge:** :doc:`109-convert-compound-sets`.

This article provides the following background information: 

* :ref:`Section-What`

* :ref:`Section-Why`

* :ref:`Section-When`


.. _Section-What:

Definition of compound sets in AIMMS
------------------------------------

As you may know, a *relation* :math:`R` is a subset of a Cartesian product: :math:`S_1 \times S_2 \times ... \times S_n` with :math:`n \geq 2`, and :math:`S_1, S_2, ..., S_n` being one-dimensional sets.

In AIMMS, a *compound set*, :math:`C`, is a relation, :math:`R`, whose attribute form contains the declaration of an index, :math:`c` or an element parameter, :math:`e`. Thus a compound set is a one-dimensional set, and can be ordered.

A compound set allows the use of tags to select components in tuples. Let's assume :math:`C` is declared with the tags :math:`(T_1, T_2, ..., T_n)`. Then you may select component :math:`i` of tuple :math:`c` using the notation :math:`c.T_i`.



.. _Section-Why:

Reason to deprecate compound sets
---------------------------------

There are several technical reasons to deprecate compound sets.

* AIMMS created compound sets for root sets with an index or parameter in the set declaration. **This causes unexpected behavior in some cases**: 

    * When the root sets of two conceptually different compound sets collide, then numbering and ordering of elements is different than expected.
    
    * When an index or element parameter is added to the declaration of a relation, the application's performance may degrade.
    
* Compound sets are implemented differently than other sets and indexing mechanisms. This makes it expensive to support compound sets, especially for components such as:

    * Database tables and procedures exchanging data with `relational databases <https://en.wikipedia.org/wiki/Relational_database>`_
    
    * `WebUI <https://documentation.aimms.com/webui/index.html>`_

    * `Datalink <https://documentation.aimms.com/datalink/index.html>`_
    
    * `CDM <https://documentation.aimms.com/cdm/index.html>`_


You can replace the compound sets with a *set mapping*, which is a one-dimensional root set and a relation.

This conversion procedure is designed to make minimal changes to your application while:

* Maintaining original functionality

* Improving efficiency

* Producing more predictable behavior

Read **AIMMS Knowledge:** :doc:`109-convert-compound-sets` for the procedure to replace compound sets in your model.


.. _Section-When:

Timeline to adapt your model
----------------------------

**The deprecation of compound sets is scheduled after January 1, 2020.** 

The deprecation follows a staged timeline:

+-----------------+-------------+-------------------------------------------------------------------------+
| Release after   | Version     | Description of phase/stage                                              |
+=================+=============+=========================================================================+
| July 1, 2018    | AIMMS 4.56  | Attibutes 'index' or 'parameter' not available for relations.           |
|                 |             | (This prevents creating new compound sets.)                             |
+-----------------+-------------+-------------------------------------------------------------------------+
| January 1, 2019 | ...         | Error message warns against existing compound sets.                     |
+-----------------+-------------+-------------------------------------------------------------------------+
| July 1, 2019    | ...         | Non-critical error message in developer mode for compound sets created. |
|                 |             | (AIMMS can still present data and execute procedures.)                  |
+-----------------+-------------+-------------------------------------------------------------------------+
| January 1, 2020 | ...         | Application will not work when compound sets are present.               |
+-----------------+-------------+-------------------------------------------------------------------------+
