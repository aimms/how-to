Integer set expressions: 2 new properties
=========================================


.. image:: ../Resources/C_Language/Images/112/BlogPicture.jpg

For the past couple of years, we have worked on improving the compiler and execution engine in AIMMS. We've done this in a way that minimizes problems in existing models.  
During this process, we have encountered issues in the existing code that are not in line with the Language Reference. We struggled with the handling of subsets of integers in an expression in particular. 

For example, consider this set: 

.. code-block:: aimms

    Set subSetIntegers { 
       SubsetOf: Integers; 
       Parameter: anInteger; 
       InitialData: data { 0, 1, 4, 7, 8 }; 
    } 

And these two statements:

.. code-block:: aimms

    anInteger := 1; 
    anInteger := anInteger + 3; 

What kind of operator is the + is this situation?
-------------------------------------------------

If we refer to the Language Reference, it seems pretty simple: the left hand side is element-valued and the right hand side is numerical, so the + is a lead operator. Hence, the value of anInteger should become 8.
But, the Language Reference also states that elements in a subset of integers are treated as numerical when needed, as if it was surrounded by the Val() operator. If that would be the rule, then the value of anInteger should become 4.
In the past, AIMMS has tried to be smart in these situations and applied either the lead or the numerical plus depending on the context of the expression in which the + appeared.
Now look at these two statements:

.. code-block:: aimms

    anInteger := 0;
    if anInteger then DialogMessage("It is true!") endif;

Will the dialog ``It is true`` be shown or not? Again, if we refer to the Language Reference,  it states that the logical value of an element parameter is true if it is not empty. That is, if its current value is not the empty element.  Here anInteger has the value '0', so it should be true and the dialog is shown. But, I think that many of us would have expected that the dialog would not be shown in this situation.
 
So what can the modeler do to write exactly what they want? 
------------------------------------------------------------

For this, we introduced two new properties that you can specify for each subset of integers in your model: ``ElementsAreNumerical`` and ``ElementsAreLabels``.
If ElementsAreNumerical is specified, then in every situation where either of the two described ambiguities arise, the element expression is automatically surrounded by the Val() operator. So the statements in the example will be interpreted as:

.. code-block:: aimms

    anInteger := val(anInteger) + 3;
    
and

.. code-block:: aimms

    if val(anInteger) then DialogMessage("It is true!") endif;

If ``ElementsAreLabels`` is specified, then the fact that an element can be treated as a numerical value will only apply when there is no ambiguity. In the first statement this means that the + is interpreted as a lead, and in the second statement the condition will be true.
If neither of the two properties are set, then you will get a warning whenever the new compiler encounters an ambiguity. We recommended that you then make the proper choice between the two properties. If you ignore the warnings, your statement may behave differently in new AIMMS versions, because the coverage of the new compiler increases and a statement that was previously handled by the old compiler is now handled by the new compiler.
We do know that some time ago, AIMMS generated a similar warning for these situations. However, with these two new properties we can now replace the warning with a more scalable solution.  

 
.. include:: ../includes/form.def


