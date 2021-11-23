Element after Last
====================================

.. meta::
   :description: How to use lead and lag operations to create infinite sets.
   :keywords: cycle, cyclic, empty, next, last

      .. note::

	This article was originally posted to the AIMMS Tech Blog.

Consider a stock balance ``v_Stock(i_t+1) = v_Stock(i_t) + v_Production(i_t) - p_Demand(i_t)``. Via ``t+1`` we refer to the next element in a set. Using such an expression as an argument of a variable, as in ``S(t+1)``, we refer to the stock of the next period. The meaning of an expression like ``t+1`` needs to be well-defined, including when ``t`` equals the last element in the set ``H``. This brings us to the question in the title of this post.

Consider the situation with an index ``i``, such that :math:`i \in S=\{1..5\} \subset M=\{1..7\} \subset {\tt{}Integers} \subset \mathbb{Z}`. To denote the next element in a set, the ``+1`` notation is used. Clearly, for ``i=3, i+1=4``, but when ``i=5``, what should ``i+1`` be?

Let me answer this question, by considering a few other examples as well, and then come back to how you may handle this in the stock balance.

A set is a collection of unique elements. Among others, elements can be names and numbers. Let me give two additional examples:

#. The pre-declared set ``AllAbbrWeekdays = { Mon, Tue, Wed, Thu, Fri, Sat, Sun }`` with index ``IndexAbbrWeekdays`` and subset ``WorkingDays = { Mon, Tue, Wed, Thu, Fri }`` with index ``wd`` ; When ``wd = 'Fri' ;`` what should ``wd+1`` be?

#. The user declared set ``PowerUnits = {LargePowerUnitWest, SmallPowerUnitWest, LargePowerUnitEast, SmallPowerUnitEast}`` with subset ``LargePowerUnits = { LargePowerUnitWest, LargePowerUnitEast }``. The index ``pu`` is in ``PowerUnits`` and the index ``lpu`` is in ``LargePowerUnits``. When ``lpu='LargePowerUnitEast'``, what should ``lpu+1`` be?

In addition to the ``+1`` operation, used to refer to the next element, we are also able to refer to the next element cycle, whereby the element after the last in the set is the first element in the next set, using the operation ``++1``. Similarly, the operations ``-1`` and ``--1`` refer to the previous, respectively the previous cycle, element. Of course the ``1`` can be replaced by another integer value.

Now, I will answer the above questions in reverse order:

* ``lpu=last(LargePowerUnits), lpu+1`` is the empty element. Even though there is another power unit in the super set, it is not large. If you want to obtain the next element with respect to the set ``PowerUnits``, you can use the index ``pu``, and the expression becomes ``pu+1``.
* ``wd=last(WorkingDays), wd+1`` is the empty element. Even though there is a Saturday after Friday, in this example Saturday is not a working day. If you view the set ``WorkingDays`` as a cyclic set, you may want to use the ``++1`` operation instead, and the expression becomes ``wd++1``.
* ``i=last(S), i+1``, is the empty element, even though ``S`` is a subset of :any:`Integers`. For the same reasons as above and with the similar remedies.

In mathematics, these elements correspond to integer numbers in the infinite set :math:`\mathbb{Z}`. Thus "+1" operation on an integer element resulting in the empty element may come as a surprise. However, when only considering finite sets, as AIMMS does, which may consist of names and numbers, this allows for a consistent definition of the next and previous operators.

.. sidebar:: Using index domain condition instead of other set

    The time consuming details are in the indexing of this constraint. This constraint does not hold for the last period in the planning horizon ``H`` with index ``t in H``; when ``t=last(H)``, then ``t+1`` is the empty element and ``S(t+1)=0`` because the empty element is not in the domain of ``S``. Constructing a subset ``HBLSet`` of the horizon containing all elements but the last will not help, as ``HBLIndex`` in ``HBLSet``, when ``HBLIndex=last(HBLSet)``, the ``HBLIndex+1`` is the empty element. A simpler solution, on the other hand, will work; exclude the last element in the horizon via the condition in the index domain. 

The above definitions become interesting when applied to indexing, for instance in constraints. As in the introductory example, consider a simple stock balance constraint with a decision variable representing stock ``v_Stock(t)`` registered at the beginning of a period, a parameter representing demand ``p_Demand(t)`` registered during a period and a decision variable representing production ``v_Production(t)`` registered during a period. The definition of this stock balance: ``v_Stock(i_t+1) = v_Stock(i_t) + v_Production(i_t) - p_Demand(i_t)``. 


The ``StockBalance`` becomes:

.. code-block:: aimms

    Constraint C_StockBalance {
        IndexDomain: i_t | i_t < last( s_Horizon );
        Definition: v_Stock(i_t+1) = v_Stock(i_t) + v_Production(i_t) - p_Demand(i_t);
    }
  
As an aside, the "+1" and "-1" operations discussed above are also known as lead and lag operations, respectively.





