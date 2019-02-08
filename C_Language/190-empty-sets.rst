Remove elements from a set
==========================

This article explains the possible behaviors the ``empty`` statement.

To remove all the elements from a set, you can use the ``empty`` statement in AIMMS. 

The behavior of the empty statement depends on the set that you provide as an argument:

* The set is not a subset of ``AllIdentifiers`` -
    The empty statement will remove all elements from the given set.
* The set is a subset of ``AllIdentifiers`` -
    The empty statement will empty all the identifiers that are in the given subset.

Let's assume the following two identifiers:

.. code-block:: aimms

 SET:
   identifier :  NormalSet

 SET:
   identifier :  ActiveVariables
   subset of  :  AllVariables


As you can see, it holds that ``ActiveVariables `\subseteq` AllVariables `\subseteq` AllIdentifiers`` because the predefined set ``AllVariables`` is defined in AIMMS to be a subset of ``AllVariablesConstraints``, which in turn is a subset of ``AllIdentifiers``. You can verify this by opening the attribute window of these predefined sets.

This means that the ``empty`` statement behaves differently for ``NormalSet`` and ``ActiveVariables``, as explained below:

.. code-block:: aimms

 !This will remove all elements from the set NormalSet 
 empty NormalSet ; 

 !This will clear the values of all variables in the subset ActiveVariables
 !After the empty statement, the set itself will still contain elements!
 empty ActiveVariables ;

 !This will actually remove all elements from the set ActiveVariables 
 ActiveVariables := {} ; 