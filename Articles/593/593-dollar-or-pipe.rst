Dollar or Pipe Sign? 
=========================

.. meta::
   :description: how to use the operator $ and the operator |.
   :keywords:  pipe, domain, dollar, condition

In the AIMMS language there is a clear distinction on how to use the operator ``$`` and the operator ``|``. This article illustrates this difference.

Dollar Sign
----------------

The ``$`` is used to impose a condition on a part of an expression as in:

.. code-block:: aimms
   :linenos:  

   A(i) := B(i) $ C(i);

This is semantically the same as: 

.. code-block:: aimms
   :linenos:  

   A(i) := if C(i) then B(i) else 0 endif;


Pipe Sign
----------------

The operator ``|`` is used to restrict an index domain. An index domain can be specified at several places in the model:

*	At the left-hand-side of a multi-dimensional assignment:

.. code-block:: aimms
   :linenos:  
   
   A((i,j) | restriction(i,j)) := rhs(i,j)

*	In an iterative operator:

.. code-block:: aimms
   :linenos:

   sum((i,j) | restriction(i,j), term(i,j) )

*	In the index domain attribute of an identifier:

.. code-block:: aimms
   
   Index domain: (i,j) | restriction(i,j)

*	In a ``for`` statement:
   
.. code-block:: aimms
   :linenos:

   for (i,j) | restriction(i,j) do  
      ! some code
   endfor;      

*	In a constructed set expression:

.. code-block:: aimms
   :linenos:

   MySet := { i | restriction(i) }
   ! or
   MyRelation := { (i,j) | restriction(i,j) }


Discussion
----------------

However, for historical reasons (mainly because we have supported GAMS language for a long time) this theoretical distinction between the two operators was never imposed by AIMMS, 
and you could use ``$`` and ``|`` as if they mean the exact same thing. **You did not get an error if you used them incorrectly.**

In the latest version of AIMMS this has changed. So you now do get a warning when you use ``$`` where a ``|`` was expected, and vice versa (and in future versions this will even be an error).
The reason for this is that in older AIMMS versions there has always been an error in how these operators were interpreted and that the interpretation was not consistent.

Let’s try to explain:
^^^^^^^^^^^^^^^^^^^^^

A very important difference between the two operators is where they appear in the precedence order of operators. Take this example:

.. code-block:: aimms
   :linenos:
   
   A := B $ C + D;  
   ! vs.  
   A := B | C + D;

According to the precedence rules this should be executed similar to:

.. code-block:: aimms
   :linenos:

   A := (B $ C) + D;  
   ! vs. 
   A := B | (C + D);

However, if you try this in an older AIMMS version you will see that the assignment with the ``|`` operator has the exact same result as the assignment with the ``$`` operator.

Of course we could have adapted the `Language Reference <https://documentation.aimms.com/language-reference/index.html>`_ on this, 
and just state there that ``|`` and ``$`` are exactly the same, but there is a funny thing with how this precedence rules behave within an index domain specification:

.. code-block:: aimms
   :linenos:

   A((i,j) | B(i,j) + C(i,j) ) := rhs(i,j);  
   ! vs.  
   A((i,j) $ B(i,j) + C(i,j) ) := rhs(i,j);

Both assignments are accepted in older AIMMS versions (and are semantically the same). 

In other words, when not in an index domain specification both operators behave the same and follow the precedence of the ``|`` operator. 
When not in an index domain specification, so just somewhere in an expression, the two operators also behave as if they are the same but there they follow the precedence of the ``$`` operator.
Because of this inconsistency and the fact that it has never really behaved as the Language Reference states, the new version of AIMMS is now very strict in where to use which operator. 
So, if you have written this in your model:

.. code-block:: aimms
   :linenos:
   
   for i $ x(i) do

The compiler will now give a warning and suggests to replace the ``$`` by a ``|``.

Similarly, if you have written:

.. code-block:: aimms
   :linenos:

   A := B | C + D;

The compiler will give another warning and suggests to replace the ``|`` by a ``$``.  
Please note that you should *not* add parentheses around ``C + D`` as well, 
because then the statement will not behave the same as before (since, as explained, there was no precedence order difference between ``|`` and ``$``).

Certainly for some older models, these new warnings may lead to quite a lot of changes to be made. 
To assist in that the latest version of AIMMS also has a new feature that can make these improvements semi-automatically. 
