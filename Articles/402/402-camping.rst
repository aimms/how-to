Camping
=======

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Constraint Programming, callback function
   :description: In this AIMMS project, some aspects of Constraint Programming (CP) are illustrated.

Direct download AIMMS Project :download:`Camping.zip <model/Camping.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Camping


In this AIMMS project, some aspects of Constraint Programming (CP) are illustrated. In the various constraints, CP functions like :any:`cp::Count` and :any:`cp::Sequence` are used on the element variable identifier type. Furthermore, some if-then constructs are used in the constraints. Please note that you need the CPOptimizer solver in your solver configuration in order to solve the model.

The goal of the model is to place as much tents as possible on a variably sized camping site. On the camping site, a number of trees can be randomly placed. Tents cannot be placed on spots where a tree stands. Furthermore, you can specify the minimal space around each tent, in which no other tents should exist. This minimal space is respected both horizontally and diagonally. You can also specify a number of shadow spots. In this model, a shadow spot for a tent is a spot with a tree directly south of a tent. Finally, you can specify the relation that should be used for the number of shadow spots. For example, using '=' means that the number of shadow spots in the solution should be equal to the number specified. You can also specify other relational operators, like '<' and '<>'.

The model uses a callback function, in order to visualize the solving process. With every new incumbent, the camping site is updated to show the new situation.

You can clear all tents or trees from the current grid by clicking on 'Clear Tents' or 'Clear Trees'. Use the button 'Place Random Trees' to place the specified number of trees randomly. By clicking on a spot in the camping site itself, you can toggle the spot between containing a tree or not.

Keywords:
Constraint Programming, callback function


