Remove Elements from a Set
==========================

.. meta::
  :description: Explains how the empty statement behaves differently for regular sets versus subsets of predefined sets such as AllIdentifiers in AIMMS, why a defined set cannot be emptied at all, and how to clear every set in a section except a named few.
  :keywords: empty statement, set, AllIdentifiers, AllSets, AllVariables, AllVariablesConstraints, remove elements, clear values, predefined sets, subset, defined set, section name, exclude by name


The ``empty`` statement in AIMMS is used to clear the contents of a set or reset values of identifiers within a subset of predefined sets. 
Its behavior depends on the type of set being emptied:

* The set is not a subset of :aimms:set:`AllIdentifiers`: the empty statement will remove all elements from the given set.
* The set is a subset of :aimms:set:`AllIdentifiers`: the empty statement will empty all the identifiers that are in the given subset.

Example: Clearing a Regular Set vs. a Subset of AllVariables 
----------------------------------------------------------------

Let's assume the following two identifiers:

.. code-block:: aimms

  Set NormalSet;

  Set ActiveVariables {
    SubsetOf: AllVariables;
  }


As you can see, it holds that ``ActiveVariables`` :math:`\subseteq` :aimms:set:`AllVariables` :math:`\subseteq` :aimms:set:`AllIdentifiers` because the predefined 
set :aimms:set:`AllVariables` is defined in AIMMS to be a subset of :any:`AllVariablesConstraints`, which in turn is a subset of :aimms:set:`AllIdentifiers`. 
You can verify this by opening the attribute window of these predefined sets.

This means that the ``empty`` statement behaves differently for ``NormalSet`` and ``ActiveVariables``, as explained below:

.. code-block:: aimms
  :linenos:

  !This will remove all elements from the set NormalSet 
  empty NormalSet ; 
  
  !This will clear the values of all variables in the subset ActiveVariables
  !After the empty statement, the set itself will still contain elements!
  empty ActiveVariables ;
  
  !This will actually remove all elements from the set ActiveVariables 
  ActiveVariables := {} ; 
 
A Defined Set Cannot Be Emptied At All
---------------------------------------

There is a third case. If a set was declared with a ``Definition`` attribute, its contents are computed from
that definition rather than stored as data the model owns, and neither form above applies:

.. code-block:: aimms

  empty MySet ;

fails with

.. code-block:: none

  The defined domain set "MySet" cannot be emptied

This is not a defect. A defined set is recomputed automatically whenever the identifiers its definition
depends on change, so there is no independently stored content for a procedural statement to clear.

If what you need is a set that can be reset and repopulated between runs - to keep elements from a previous
execution out of the next one, for instance - declare it **without** a ``Definition`` attribute and populate
it explicitly, with :any:`SetElementAdd` or an assignment. A set's contents can only be assigned or reset
procedurally when it is not a defined set.

Empty Every Set Except a Named Few
-----------------------------------

Clearing every set you declared, except one or two, is a subset construction rather than a list. The
second case above is what makes it work: ``empty`` applied to a subset of :aimms:set:`AllIdentifiers`
empties the identifiers that the subset contains.

First capture the scope:

.. code-block:: aimms

  Set s_declaredSets {
    SubsetOf : AllIdentifiers;
    Index    : i_sds;
  }

  s_declaredSets := MySection * AllSets;

:aimms:set:`AllSets` is the predeclared subset of :aimms:set:`AllIdentifiers` holding every declared set
in the project. The name of a ``Section`` node is also available as an implicit subset of
:aimms:set:`AllIdentifiers`, containing every identifier declared beneath that node, so intersecting the
two restricts the result to the sets declared in that section.

Use a section rather than the whole model. That is what keeps the statement from reaching sets that
belong to a library you did not write.

Then filter out the exceptions:

.. code-block:: aimms

  Set s_setsToEmpty {
    SubsetOf : s_declaredSets;
  }

  s_setsToEmpty := s_declaredSets - { 's_keepThisOne' };

The elements of :aimms:set:`AllIdentifiers` are the identifier names themselves, so a set difference
against an element literal is enough. Add further exceptions inside the braces. The literal has to name
an identifier that exists, otherwise AIMMS rejects it at compile time.

And clear them in one statement:

.. code-block:: aimms

  empty s_setsToEmpty ;

Naming the exceptions, rather than the sets to clear, is almost always the intent: a set added next
month is then cleared automatically. Listing the sets to clear does the opposite, and the omission
surfaces later as data surviving a reset that should have removed it.

.. seealso::

  * :doc:`../713/713-reclaiming-memory-between-data-instances`

Key Takeaways
--------------

- The ``empty`` statement removes elements from general sets but only clears values for subsets of predefined sets.

- To fully remove elements from a subset of :aimms:set:`AllIdentifiers`, assign an empty set ``{}`` to it explicitly.

- A set with a ``Definition`` attribute cannot be emptied by any means; drop the definition if the set has to be procedurally editable.

- To clear many sets at once, intersect a ``Section`` name with :aimms:set:`AllSets` and subtract the exceptions, then pass the result to ``empty``. Naming the exceptions keeps new sets covered automatically.

- Use ``empty`` carefully when dealing with predefined sets to avoid unintended behavior.

.. spelling:word-list::

  procedurally