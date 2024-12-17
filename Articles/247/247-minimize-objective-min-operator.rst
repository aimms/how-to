Minimize Objective Containing a ``min`` Operator
==================================================


.. meta::
   :description: Some modeling tricks using min operator
   :keywords: Linear Programming, modeling, min, minimize


This article explains how to minimize an objective which contains a binary ``min`` operator. 

The idea of using this binary ``min`` operator was to introduce a ceiling to the contribution of a variable to the objective.

For example, let's take a minimization objective containing the following term: 


.. code-block:: aimms
    :linenos:

    50 * min( p_par, v_var). 


Adding Parameters and Variables
-------------------------------
If you want to keep your problem linear, you cannot use the ``min`` operator directly in a constraint, so you will have to work around it with some modelling. You can do this introducing a new variable ``min_param_var``, that will be forced to have a value equal to the binary ``min`` operator by using additional constraints. This new variable will replace the original ``min( p_par, v_var)`` term in the objective (and thus will have coefficient of 50 in objective in the above example).

We also need a new binary variable ``param_smaller_than_var``, with the value ``1`` if the value of the parameter is smaller than the value of the variable, and ``0`` otherwise.

BigM and Constraints
----------------------
Finally, we need to add two constraints and a `Big M <https://en.wikipedia.org/wiki/Big_M_method>`_ to the model to ensure the newly introduced variables get the correct values. The value of the Big M should be a sufficiently large value, which in this particular case is ``max( p_par, v_var.upper )``.

The two constraints to add are:

#.  :math:`\mathrm{v\_min\_param\_var} \ge \mathrm{p\_par} * \mathrm{v\_param\_smaller\_than\_var}`

#.  :math:`\mathrm{v\_min\_param\_var} \ge \mathrm{v\_var} - \mathrm{v\_param\_smaller\_than\_var} * M`

If the variable ``v_min_param_var`` has a positive component in the objective, the solver will try to minimize the value of the variable ``v_min_param_var`` and the above constraints will ensure that the variable ``v_param_smaller_than_var`` will automatically get the value ``1`` if ``p_par <= v_var``, and ``0`` otherwise.

By filling in some values for the parameter and the variable in the above constraints, you can verify that the binary variable must indeed get the correct value to ensure that the ``v_min_param_var`` variable will get the smallest value possible.

Adaptation for a ``max`` Operator
-----------------------------------
In case you are minimizing an objective that contains a binary ``max`` operator, you can use a similar approach. However, in this case you do not need the Big M, but only one additional variable ``v_max_param_var`` and these two constraints:

#. :math:`\mathrm{v\_max\_param\_var} \ge \mathrm{p\_par}`

#. :math:`\mathrm{v\_max\_param\_var} \ge \mathrm{v\_var}`


Further Reading
---------------
`Erwin Kalvelagen <https://yetanothermathprogrammingconsultant.blogspot.com/>`_ discussed a similar topic in his `max tricks <https://yetanothermathprogrammingconsultant.blogspot.com/2012/02/max-tricks.html>`_ blog post. 


.. spelling:word-list::
    
    BigM