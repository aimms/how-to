Remove Elements from a Set
==========================

.. meta::
  :description: Understanding the behavior of the empty statement in AIMMS.
  :keywords: AIMMS, set, empty statement, remove elements, clear values


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
 
Key Takeaways
--------------

- The ``empty`` statement removes elements from general sets but only clears values for subsets of predefined sets.

- To fully remove elements from a subset of :aimms:set:`AllIdentifiers`, assign an empty set ``{}`` to it explicitly.

- Use ``empty`` carefully when dealing with predefined sets to avoid unintended behavior.
