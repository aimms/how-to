Use Multiple Indices for a Set
========================================

.. meta::
   :description: One set can have several indices running over it.
   :keywords: indices, set, index, algebraic notation

.. note::

    This article was originally posted to the AIMMS Tech Blog.

.. <link>https://berthier.design/aimmsbackuptech/2012/10/05/using-multiple-indices-for-one-set/</link>
.. <pubDate>Fri, 05 Oct 2012 12:41:07 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1845</guid>

.. figure:: images/index.jpg
    :align: center

    Multiple indices for a set
    
You can use different indices to refer to the elements of the same set.

Suppose we have a set, ``Cities``, and a parameter, ``Distance``. We want to define ``Distance`` over 2 dimensions (``from`` and ``to``) for the same set, ``Cities``. 

If you didn't know that AIMMS can handle multiple indices for a set, you might create two new subsets ``CitiesFrom`` and ``CitiesTo``, containing all elements of the set ``Cities``, and each with its own index.

Luckily, you can just assign multiple indices to one set. 

When you create a new set, you can directly add multiple indices to it by typing all of them in the index attribute field, separated by commas (e.g. ``city, cityFrom, cityTo``). 

After the initial assignment of indices, you can't edit in the ``index`` attribute of the set anymore. It will be grayed out when you open the attribute window.

.. figure:: images/Cities-attribute-window.png
    :align: center

    Set attribute window with grayed out index attribute

Instead, use the ``index`` attribute wizard by clicking the magic wand icon just left of the input field. The wizard appears, as shown below.

.. figure:: images/Cities-index-attribute-wizard.png
    :align: center

    Wizard for index attribute of set

Alternatively, you can manually add an ``index`` identifier to your model tree for each index you want to add. For these ``index`` identifiers, you will have to set the ``range`` attribute to the corresponding set as shown below: 

.. figure:: images/demo-explicit-index.png
    :align: center

    Explicit index identifier for set

