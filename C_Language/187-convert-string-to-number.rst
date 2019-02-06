Converting string to numerical value
=====================================

There are various situations where you will have a number that is stored in a string parameter or an element in AIMMS. Although this string looks like the number, you can't directly use the numerical value it represents. You will need the val intrinsic function of AIMMS to convert the string representation of the number to a numerical value again.



A small example of how you use the val function is given below::

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

To ensure the execution is not halted when the string or element you are trying to convert does not represent a number (like the last case in the above example), you can make use of the error handling functions introduced with AIMMS 3.10.

Note that if you make your set a subset of the predefined set Integers you don't have to use the val function for conversion, but you can directly use an index or an element parameter of this set as a number.