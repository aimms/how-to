Exchanging data for a flexible set of properties
=================================================

On the one hand, optimization applications are made generic by formulating them over a set of ingredients, or a set of properties.
Algebraically, a constraint may look like:

.. math:: \forall 

On the other hand, the input is orderly presented in columns, one column for each ingredient or property.




This article presents an example of exchanging such data with an AIMMS application.
The key feature illustrated in this article is ``name-binds-to``.

Running example
----------------

The running example is based on the :doc:`../441/441-diet-problem` example from `AIMMS The modeling guide <https://documentation.aimms.com/aimms_modeling.html>`_.

Sets
^^^^^^^^^^^^

#.  Nutriets, index: :math:`n`

#.  Servings, index: :math:`f`

Parameters
^^^^^^^^^^^^

#.  

Variables
^^^^^^^^^^^^

Constraints
^^^^^^^^^^^^



See also
* Modeling guide, Diet example.
* DEX doc,
* DEX binds to
* 