Overview: Set, Subset, Index and Element Parameter
====================================================

.. meta::
   :description: How to use sets, subsets, indices, element parameters in AIMMS models.
   :keywords: set, subset, index, element, parameter

.. figure:: /Images/121-set-index-element-parameter/set-god.png
   :scale: 10 %
   :align: center

.. note::

	This article was originally posted to the AIMMS Tech Blog on September 23, 2016 by Deanne Zhang.

.. |god| image:: /Images/121-set-index-element-parameter/set-god.png
         :scale: 10 %

Do you know Set is a god of the desert, storms, disorder, violence and foreigners in ancient Egyptian? So it is totally possible that his disturbance power influenced mathematical world, which is considered employing set theory as foundation system. From 5th century Greek in west India in the east to today's modern society, there are struggles, debates, paradoxes around set theories. You can find lots of readings about them, or if reading history sounds boring, there will be a little fun video to watch at the end of this blog.

Set in AIMMS can also be confusing for new AIMMS users. We often get questions regarding how to use set, subset, indices, mappings, etc. While working on my own Fantasy Football project, I think it might be helpful to share how I started building one of my models to clarify some of the ambiguity by this example.

Fantasy Football is a game where people can choose their team rosters by participating in a draft in which all players of a real football league are available. I participated this year. I named my team Naqada. As my team manager, I want to optimize my draft total fantasy points by the end of 2016 season, considering the projection of players and constraints in my team and in the league. Before I jump to my algorithm, first, I am going to acquire player's information to my model. Fortunately, there are tons of data available at various sources. The following is a slice of 2015 historical data:

+-------------------------------+------------------+----------------------+----------------------+
| Player                        | Position         | Team                 |  Fantasy Points      |
+===============================+==================+======================+======================+
| Cam Newton                    |  QB              |  CAR                 |  389.08              |
+-------------------------------+------------------+----------------------+----------------------+
| Tom Brady                     |  QB              |  NE                  |  343.7               |
+-------------------------------+------------------+----------------------+----------------------+
| Russell Wilson                |  QB              |  SEA                 |  336.26              |
+-------------------------------+------------------+----------------------+----------------------+
| Devonta Freeman               |  RB              |  ATL                 |  243.4               |
+-------------------------------+------------------+----------------------+----------------------+
| Antonio Brown                 |  WR              |  PIT                 |  242.2               |
+-------------------------------+------------------+----------------------+----------------------+
| Julio Jones                   |  WR              |  ATL                 |  239.1               |
+-------------------------------+------------------+----------------------+----------------------+


We can see the information of each player. For example, Russel Wilson, the Quarterback(QB) from my favorite team Seattle Seahawks (SEA), has fantasy points of 336.26. In order to get his points in, it is natural to create a parameter to hold Russel Wilson's point.

.. image:: /Resources/C_Language/Images/121/Screenshot_13.png

And I also want to have fantasy points for each of the players, so I will create a list of parameters to hold each value.

.. image:: /Resources/C_Language/Images/121/Screenshot_14.png

 
It will soon become too cumbersome to continue because there are hundreds players in the draft. We don't want to repeat the same thing for each of the player. So here Set comes. Let's create a set, called ``Players``. It contains all the players I will consider in the draft. And I put ``p`` as index. An Index, or sometimes called running index, can refer to any element in the set.

.. image:: /Resources/C_Language/Images/121/Screenshot_15.png

Then I create a parameter called ``FantasyPoints(p)``, "(p)" means FantasyPoints has index domain over ``p``, representing fantasy points for each player.

.. image:: /Resources/C_Language/Images/121/Screenshot_16.png

With Set ``Players`` and Parameter ``FantasyPoints(p)``, we can easily hold the fantasy points for all players.

Next I want to have each player's position in my model. In our sample data, we see Quarterbacks(QB), Running Backs(RB) and Wide Receivers(WR). So I will create a set, called ``ositions``, with index ``ps``.

.. image:: /Resources/C_Language/Images/121/Screenshot_17.png

Each of the player is at one of the positions. In order to hold such information in AIMMS, there are two alternative ways

* Using parameter mapping a player and his position
* Using element parameter containing value of positions for each player

Let's implement both. I name the parameter ``PlayerAtPosition(p,ps)``, and specify ``binary`` in the range.

.. image:: /Resources/C_Language/Images/121/Screenshot_20.png

And we will assign value 1 (box checked) if a player palys the position.

.. image:: /Resources/C_Language/Images/121/Screenshot_21.png

Now Parameter ``PlayerAtPosition`` use numerical value (0/1) to indicate if a player belongs to position. Next we will create an element parameter ``PositionOfPlayer`` to take value of the positions for each player.

.. image:: /Resources/C_Language/Images/121/Screenshot_22.png

A mandatory attribute of element parameter is the "``Range``". It must be a set name in the model, because element parameter is defined as an element of a set. And you probably have already noticed, that parameter has "Range" attribute as well, but it is not mandatory to fill in. It is because Parameter in AIMMS has default range, which is the set of real numbers. And you can overwrite the range to a subset of real number, for example, binary for Parameter PlayerAtPosition as we did earlier.

Now we fill in the value of PositionOfPlayer.

.. image:: /Resources/C_Language/Images/121/Screenshot_23.png


You may wonder which option is better. It usually depends on multiple things, for example, input file format, how this parameter or element parameter will be used in the model, and your modeling preferences. In general, making it an element parameter is easier for reading from Excel or Database, since there is usually no extra numerical column for the mapping value. Parameter, at the other hand, can be directly used as domain condition, which might reduce some computational run time. For example, if we want to calculate the average fantasy points for all Quarterbacks. I can have either

.. code-block:: aimms

    average(p|PlayerAtPosition(p, 'QB'),FantasyPoints(p))

or

.. code-block:: aimms

    average(p|PositionOfPlayer(p) = 'QB',FantasyPoints(p))

When implementing with element parameter, there is an extra equal = operator involved ``(PositionOfPlayer(p) = 'QB')``. In this case, it is not a big deal, as the operator is fast, but for a large model, you would want to avoid doing the same operating again and again, by transferring the element parameter to parameter first, and using the parameter elsewhere. This can be done by the following statement.

.. code-block:: aimms

    PlayerAtPosition(p, ps) := (PositionOfPlayer(p)=ps);

Or in another case, you already have the Parameter but you would like to create the element parameter.

.. code-block:: aimms

    PositionOfPlayer(p) := First(ps|PlayerAtPosition(p, ps));

Here AIMMS operator "``First``" is used to find the first position the player plays at. This operator depends on the assumption that each player only plays at one position.  In another situation, where I want to know all the Quarterback players, statement ``First(p|PlayerAtPosition(p, ps))`` won't help me since there are more than one player plays Quarterback. In this case I need create a set ``AllQuarterbacks``, make it a ``subset`` of Players, index ``allqb`` and define it as the following.

.. image:: /Resources/C_Language/Images/121/Screenshot_26.png
 
With this definition, AIMMS automatically pops the data.

.. image:: /Resources/C_Language/Images/121/Screenshot_27.png



With this subset and its index ``allqb``, it will be easier if I want to calculate the average fantasy points of all Quarterbacks:

.. code-block:: aimms

    average(allqb,FantasyPoints(allqb))

We briefly covered set, index, subset, element parameter and mapping in this post. AIMMS also supports set operations such as intersection and union, and there are other usage of set too. You can refer to AIMMS Language Reference for more information.

Thank you for reading this blog, and here is a video on some fun facts about set. What do you think?
`Ted about Set <https://www.youtube.com/watch?v=UPA3bwVVzGI>`_


.. include:: /includes/form.def



