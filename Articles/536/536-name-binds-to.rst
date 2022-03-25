Exchanging data for a flexible set of properties
=================================================

When creating a diet, care is taken to adhere to limits on selected ingredients.  
The actual selection of ingredients depends on the person.
For instance, some people need restrictions on salt and some people need restrictions on refined sugar. And so on.
Also, the availability of products, and their fractions of these ingredients, varies over time and location.

Thus the core information used to create a diet depends on a matrix that looks like this:

.. image:: images/ExcelFoodNutrientTable.png
    :align: center

Whereby the collection of

#.  foods, and

#.  nutrients

Vary.

As derived in `AIMMS The modeling guide, chapter 10 <https://documentation.aimms.com/aimms_modeling.html>`_ this information is used in constraint formulations like:

.. math:: 

    \forall n: lb_n \leq \sum_f fr_{f,n} * srv_f \leq ub_n

where :math:`lb` is a lower bound, :math:`fr` is the fraction (table above), :math:`srv` is the number of serving, and :math:`ub` is the upperbound.

The purpose of this article is illustrate the use of `Data Exchange Library <https://documentation.aimms.com/dataexchange/index.html>`_, and in particular the mapping attribute `name-bind-to <https://documentation.aimms.com/dataexchange/mapping.html#the-name-binds-to-attribute>`_, to flexibly and compactly specify the reading of the above type of tables.

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