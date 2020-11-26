Local Binding vs. Default Binding 
====================================

.. meta::
   :description: A comparison of two kinds of index binding.
   :keywords: index, binding, for statement, iterative operator

.. image:: images/rootSetSubset.png

In this article we will compare two ways of defining index binding: local binding and default binding.

Assume there are two sets:

.. code-block:: aimms
    :linenos:

    Set s_RootSet {
        Index: i_rs;
    }

and

.. code-block:: aimms
    :linenos:

    Set s_SubSet {
        Index: i_ss;
    }

Let's compare the expressions ``( i in MySubSet )`` and ``( i \| i in MySubSet )``.

When you use ``(i in MySubSet)``, AIMMS will handle ``i`` as an index in ``s_Subset``. We say ``i`` is used as local binding in this case. 

When you write ``(i \| i in MySubSet)``, AIMMS will still consider ``i`` as an index of its original set ``s_RootSet``. We say ``i`` is used as default binding in this case. 

In general, using local binding and default binding doesn't make a difference, but when element ordering is used, such as, ``ord(i)``, ``i+1`` and ``i–1``, it will have an impact. For instance, the following statement:

.. code-block:: aimms

    Parameter1(i in MySubSet) := ord(i); 

is the same as

.. code-block:: aimms

    Parameter1(i | i in MySubSet) := ord(i,MySubSet );          

while

.. code-block:: aimms

    Parameter1(i | i in MySubSet) := ord(i);

is the same as

.. code-block:: aimms

    Parameter1(i | i in MySubSet) := ord(i,RootSet );

So there is a difference between the two only if you use certain functions that are relative to the set. A complete explanation can be found in the section "Index Binding" of `AIMMS Language Reference <https://documentation.aimms.com/aimms_ref.html>`_.


