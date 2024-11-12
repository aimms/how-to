``NBest`` Operator
============================

.. meta::
   :description: Selecting the best few elements from a set according to some criterion.
   :keywords: NBest, sort, set, selecting

In addition to the ``first`` and ``last`` functions, which return first and last elements of a set, you can use the ``NBest`` operator to obtain the first :math:`n` elements of a set. This is based on criteria you provide to the ``NBest`` operator as an additional argument.

Let's take for example a transportation problem, where you have a variable ``Transport(i,j)`` denoting how much is transported from factory ``i`` to customer ``j``. You may want to know the three customers ``j`` to which the most amount is transported per factory ``i``. Let's use the ``NBest`` operator to get these elements.

The first argument for the ``NBest`` operator is the binding index; the result of the operator is a subset of elements from this binding index. 

The second argument is the sort criteria you want to use. Note that a higher value returned by this sort-criteria indicates a better value, i.e. ``NBest`` will sort the elements from largest to smallest. 

The third argument is the number ``n``, indicating how many elements you are interested in (in this example, :math:`n = 3`).

Note that you need to add a subset identifier to store the results (``LargestTransportCities`` in this case) and to set the *Order by* attribute to *user*. 

In the code below, ``LargestTransportCities`` is an indexed subset that is indexed over all factories ``i`` and is a subset of the set ``Customers``::

   LargestTransportCities(i) := NBest( j, Transport(i,j), 3 );

For each factory ``i``, the above indexed subset ``LargestTransportCities(i)`` will contain the three customers to which the most amount is transported.


.. seealso::

   * :doc:`non-procedural-language-components/set-set-element-and-string-expressions/set-expressions`
   * `Function Last <https://documentation.aimms.com/functionreference/elementary-computational-operations/set-related-functions/last.html>`_
   * `Function First <https://documentation.aimms.com/functionreference/elementary-computational-operations/set-related-functions/first.html>`_




