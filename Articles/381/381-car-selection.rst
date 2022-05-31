Car Selection
=====================

.. meta::
   :keywords: Project User Files, Mixed Integer Programming model, MIP, Matching Problem, Network object, Nodes and arcs, Bitmap
   :description: In this AIMMS project the use of pictures as nodes in a network is illustrated.

Direct download AIMMS Project: :download:`Car Selection <model/CarSelection.zip>`
test.

Story
---------

This example matches people to cars. 
Not every person can be matched to every car, for a variety of reasons:

#.  The car owner may impose limits on the drivers allowed.

#.  The drivers may not be willing to drive every car.

A matching needs to be found, such that as many as possible people can be driving at the same time.

Mathematical program
---------------------

This model is an example of a matching problem, matching people to cars. 
The mathematical formulation is as follows:

+-----+--------------------------------------------+----------------------------+------+
|       Car assignment problem                                                         |
+=====+============================================+============================+======+
+ Sets and indices:                                                                    |
+-----+--------------------------------------------+----------------------------+------+
+     | :math:`C`, :math:`c \in C_a`               | Cars                              |
+-----+--------------------------------------------+----------------------------+------+
+     | :math:`P`, :math:`p \in P`                 | Persons                           |
+-----+--------------------------------------------+----------------------------+------+
| Parameters:                                                                          |
+-----+--------------------------------------------+----------------------------+------+
|     | :math:`A_{c,p} \in \{0..1\}`               | a 1 permits                | 1.   |
+-----+--------------------------------------------+----------------------------+------+
| Variables:                                                                           |
+-----+--------------------------------------------+----------------------------+------+
|     | :math:`X_{c,p}|A_{c,p} \in \{0..1\}`       | 1 if and only if chosen    | 2.   |
+-----+--------------------------------------------+----------------------------+------+
| Constraints:                                                                         |
+-----+--------------------------------------------+----------------------------+------+
|     | :math:`\forall c: \sum_p X_{c,p} \leq 1`   | At most one person per car | 3.   |
+-----+--------------------------------------------+----------------------------+------+
|     | :math:`\forall p: \sum_c X_{c,p} \leq 1`   | At most one car per person | 4.   |
+-----+--------------------------------------------+----------------------------+------+
| Maximize:                                                                            |
+-----+--------------------------------------------+----------------------------+------+
|     | :math:`\sum_{c,p} X_{c,p}`                 | The number of matches      | 5.   |
+-----+--------------------------------------------+----------------------------+------+



Remarks:

#.  The :math:`A_{c,p}` is the set of arcs, according to which assignment is permitted.

#.  The :math:`X_{c,p}` are the variables. The notation :math:`X_{c,p}|A_{c,p}` 
    limits the variables to only those arcs that are permitted.

#.  This constraint ensures that a car is not driven by more than one person.

#.  This constraint ensures that a person does not drive more than one car.

#.  The objective is to make as many combinations of (person, car) as possible.

See also:

*   `Matching (graph theory) <https://en.wikipedia.org/wiki/Matching_(graph_theory)>`_

*   `Assignment problem <https://en.wikipedia.org/wiki/Assignment_problem>`_

User Interface
--------------

In this AIMMS project the use of pictures as nodes in a network is illustrated. 
These pictures are stored inside the project as so-called project user files. 
Project user files can only be reached in developer mode, through the menu bar command: Tools > Project User Files.

The results are illustrated in a network object, with pictures of the cars as nodes.

The number of cars and the number of participants can be changed by the user, where the possible combinations are generated randomly. 
New possible combinations will be determined when the number of participants or cars is changed, or by pushing the "generate possible pairs" button.

Keywords:
Project User Files, Mixed Integer Programming model, MIP, Matching Problem, Network object, Nodes and arcs, Bitmap


