Model a Rounded Variable
=======================================
.. meta::
   :description: How to model a variable as the closest integer to another variable or parameter.
   :keywords: round, mixed, integer

There might be cases where you need to model a variable ``var1`` as the closest integer to another variable or parameter ``value``. Essentially, a constraint like

.. code-block:: aimms
    
    var1 = Round(value)

However, using the :aimms:func:`Round` function in a constraint is not allowed in a mixed integer program and results in an error message, especially if ``value`` is also a variable.

.. code-block:: none

    Constraint programming constraints cannot be used in combination with real valued variables, only with integer valued variables, 
    element valued variables, and activities.

We can avoid such errors by declaring ``var1`` as an integer variable and using two auxiliary non-negative variables in a target constraint as shown below

.. code-block:: aimms
    
    var1 = value + aux1 - aux2 

Depending on the direction of your math program, you add (minimize) or subtract (maximize) the sum ``aux1+aux2`` to the objective function. This ensures that only one of ``aux1`` or ``aux2`` will take a non-zero value. 

The possible situations are:

    #. ``value`` is integer: ``aux1 = aux2 = 0``
    #. ``Round(value) = Ceil(value)``: ``aux2 = 0`` and ``aux1 = Ceil(value) - value``
    #. ``Round(value) = Floor(value)``: ``aux1 = 0`` and ``aux2 = value - Floor(value)``


Example
---------

Let ``value = 2.3``, whose closest integer value is 2. So we want ``var1 = 2``. This is situation 3 from above, so our constraint will result in
    
.. code-block:: aimms
    
    var1 = 2.3 + 0 - 0.3 = 2

