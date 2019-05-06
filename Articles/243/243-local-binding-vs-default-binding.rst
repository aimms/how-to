Local Binding vs. Default Binding ====================================.. meta::   :description: index binding can be redefined.   :keywords: index binding, FOR statement, iterative operator.. note::
.. <link>https://berthier.design/aimmsbackuptech/2012/09/13/local-binding-vs-default-binding/</link>
.. <pubDate>Thu, 13 Sep 2012 00:18:17 +0000</pubDate>.. <guid isPermaLink="false">http://blog.aimms.com/?p=1889</guid>
.. image:: images/rootSetSubset.png
Assume there are two sets:
.. code-block:: aimms    :linenos:
    Set s_RootSet {        Index: i_rs;    }
and
.. code-block:: aimms    :linenos:
    Set s_SubSet {        Index: i_ss;    }
What is the difference between expression ``( i in MySubSet )`` and ``( i \| i in MySubSet )`` ?
The main difference is that when you use ``(i in MySubSet)``, AIMMS will handle ``i`` as an index in ``s_Subset``. We say ``i`` is used as local binding in this case. When you write ``(i \| i in MySubSet)``, AIMMS will still consider ``i`` as an index of its original set ``s_RootSet``. We say ``i`` is used as default binding in this case. In general, using local binding and default binding doesn't make difference, but when element ordering is used, such as, ``ord(i)``, ``i+1`` and ``i–1``, it will have an impact. For instance, the following statement:
.. code-block:: aimms
    Parameter1(i in MySubSet) := ord(i); 
is the same as
.. code-block:: aimms
    Parameter1(i | i in MySubSet) := ord(i,MySubSet );          
While
.. code-block:: aimms    Parameter1(i | i in MySubSet) := ord(i);
is the same as:
.. code-block:: aimms
    Parameter1(i | i in MySubSet) := ord(i,RootSet );
So there is a difference between the two only if you use some functions that are relative to the set. A complete explanation can be found in the section "Index Binding" of AIMMS Language Reference.
.. include:: /includes/form.def