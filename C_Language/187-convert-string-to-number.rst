Convert string to numerical value
=====================================
This article explains how to convert a string representation of a number to a numerical value.

If a number may be stored in a string parameter or an element in AIMMS, you can't operate on the numerical value it represents. You can use the AIMMS intrinsic function ``val``.

The example illustrates how to use the ``val`` function:

.. code-block:: aimms

 mySet := { '3' , 'a' } ;
 myStringParameter := "5" ;


 myParameter := val(myStringParameter) ;
 !This will result in a dialog stating the value of the parameter is 5
 DialogMessage("Value of myParameter = " + myParameter) ;


 !Set the element parameter to the first element of the set (i.e. '3')
 myElementParameter := first(mySet) ;
 myParameter := val(myElementParameter) ;
 !This will result in a dialog stating the value of the parameter is 3
 DialogMessage("Value of myParameter = " + myParameter) ;


 !Set the element parameter to the last element of the set (i.e. 'a')
 myElementParameter := last(mySet) ;
 !The following statement will now result in an error that the value
 !of 'a' can't be computed
 myParameter := val(myElementParameter) ;

To ensure the execution is not halted when the string or element you are trying to convert does not represent a number (like the last case in the above example), you can use :doc:`error handling functions <191-handle-errors-and-warnings>`.


Note that if you make your set a subset of the predefined set ``Integers`` you don't have to use the ``val`` function for conversion, but you can directly use an index or an element parameter of this set as a number.

Related Topics
--------------
* `AIMMS Function Reference <https://download.aimms.com/aimms/download/manuals/AIMMS_func.pdf>`_.