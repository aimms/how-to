Minimizing objective containing a min-operator==============================================
.. meta::   :description: Some modeling tricks using min operator   :keywords: Linear Programming modeling, min, tricks.. note::    This article was originally posted to the AIMMS Tech Blog.
       <link>https://berthier.design/aimmsbackuptech/2012/03/23/minimizing-objective-containing-a-min-operator/</link>
       <pubDate>Fri, 23 Mar 2012 15:01:28 +0000</pubDate>
       <guid isPermaLink="false">http://blog.aimms.com/?p=860</guid>
During the last couple of months I saw the exact same question from different customers, namely that they wanted to minimize an objective that contained a binary min operator. For example, the minimization objective contained the following term: 
.. code-block:: aimms    :linenos:
    50 * min( p_par, v_var). 
The idea of using this binary min operator was to introduce a ceiling to the contribution of a variable to the objective. Recently, I also saw a similar topic being discussed by `Erwin Kalvelagen <https://yetanothermathprogrammingconsultant.blogspot.com/>`_ in his `max tricks blog <https://yetanothermathprogrammingconsultant.blogspot.com/2012/02/max-tricks.html>`_ post. If you want to keep your problem linear, you cannot make use of the min operator directly in a constraint, so you will have to work around it with some modelling. You can do this introducing a new variable min_param_var, that will be forced to have a value equal to the binary min-operator by using additional constraints. This new variable will replace the original ``min( p_par, v_var)`` term in the objective (and thus will have coefficient of 50 in objective in the above example).
Furthermore, we also need a new binary variable param_smaller_than_var, that will get the value 1 if the value of the parameter is smaller than the value of the variable and 0 otherwise.
Finally, we also need to add two constraints and a big-M to the model to ensure the newly introduced variables get the correct values. The value of the big-M should be a sufficiently large value, which in this particular case is ``max( p_par, v_var.upper )``.
The 2 constraints you will have to add are:
#.  :math:`\mathrm{v\_min\_param\_var} \ge \mathrm{p\_par} * \mathrm{v\_param\_smaller\_than\_var}`
#.  :math:`\mathrm{v\_min\_param\_var} \ge \mathrm{v\_var} - \mathrm{v\_param\_smaller\_than\_var} * M`
If the variable ``v_min_param_var`` has a positive component in the objective, the solver will try to minimize the value of the variable ``v_min_param_var`` and the above constraints will ensure that the variable ``v_param_smaller_than_var`` will automatically get the value 1 if ``p_par <= v_var`` and 0 otherwise.
By filling in some values for the parameter and the variable in the above constraints, you can verify that the binary variable must indeed get the correct value to ensure that the ``v_min_param_var`` variable will get the smallest value possible.
Additional information: In case you are minimizing an objective that contains a binary max operator, you can use a similar approach. However, in this case you do not need the big-M, but only one additional variable ``v_max_param_var`` and two constraints:
#. :math:`\mathrm{v\_max\_param\_var} \ge \mathrm{p\_par}`
#. :math:`\mathrm{v\_max\_param\_var} \ge \mathrm{v\_var}` .. include:: /includes/form.def