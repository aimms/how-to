Exchanging data for a flexible set of properties
=================================================

On the one hand, optimization applications are made generic by formulating them over a set of ingredients, or a set of properties.
On the other hand, the input is orderly presented in columns, one column for each ingredient or property.

This article presents an example of exchanging such data with an AIMMS application.
The purpose of this article is to illustrate the use of ``name-binds-to`` when reading an ``.xlsx`` or a ``.csv`` file using the Data Exchange library.

Running example
----------------

The running example is based on the :doc:`../441/441-diet-problem` example from `AIMMS The modeling guide <https://documentation.aimms.com/aimms_modeling.html>`_.

A selection of the declarations:

Sets
^^^^^^^^^^^^

#.  Nutriets, index: :math:`n`.  In model: ``i_n`` in ``s_nutrients``

#.  Food types, index: :math:`f`.  In model: ``i_f`` in ``s_foodTypes``

Parameters
^^^^^^^^^^^^

#.  Nutrient value per unit of food, :math:`v_{f,n}`.  In model: ``p_nutrientValuePerUnit(i_f,i_n)``






See also
* Modeling guide, Diet example.
* DEX doc,
* DEX binds to
* 