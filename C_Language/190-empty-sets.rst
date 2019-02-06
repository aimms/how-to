Remove elements from a set
==========================

To remove all the elements from a set, you can use the empty statement in AIMMS. However, it is important to keep in mind that this empty statement might not have the desired effect in case the set you want to empty is a subset of the predefined set AllIdentifiers.

The behavior of the empty statement depends on the set that you provide as an argument:

* The set is not a subset of AllIdentifiers. In this case the empty statement will remove all elements from the given set.
* The set is a subset of AllIdentifiers. In this case the empty statement will empty all the identifiers that are in the given subset.

To demonstrate the difference, I will give a small example, for which we need the following two identifiers::

 SET:
   identifier :  NormalSet

 SET:
   identifier :  ActiveVariables
   subset of  :  AllVariables


As you can see, it holds that::

 ActiveVariables `\subseteq` AllVariables `\subseteq` AllIdentifiers

because the predefined set AllVariables is defined in AIMMS to be a subset of AllVariablesConstraints, which in turn is subset of AllIdentifiers again. You can verify this by opening the attribute window of these predefined sets.

This means that the empty statement will have different behavior when used on NormalSet and on ActiveVariables::

 !This will remove all elements from the set NormalSet 
 empty NormalSet ; 

 !This will clear the values of all variables in the subset ActiveVariables
 !After the empty statement, the set itself will still contain elements!
 empty ActiveVariables ;

 !This will actually remove all elements from the set ActiveVariables 
 ActiveVariables := {} ; 