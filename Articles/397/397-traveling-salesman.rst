Traveling Salesman
======================

.. meta::
   :description: This example illustrates some of AIMMS control flow statements by means of the traveling salesman 2-opt heuristic.
   :keywords: Algorithm, 2-opt heuristic, network object, traveling salesman problem, GMP, Progress Window.

Direct download AIMMS Project :download:`Traveling Salesman.zip <model/Traveling Salesman.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Application%20Examples/Traveling%20Salesman

This example illustrates some of AIMMS control flow statements by means of the traveling salesman 2-opt heuristic. In the model tree, you will find some declarations to define the problem. In addition, you will find

- A procedure and some declarations to compute and visualize an initial tour constructed by starting at some city and successively selecting the next city as the closest city not yet part of the tour.

- A procedure and some declarations to compute and visualize an improved tour constructed by repetitively swapping those two arcs in the tour by means of the 2-opt heuristic that give the largest overall distance improvement, until no further improvement is possible or the iteration limit is reached.

- A procedure and some declarations to compute and visualize an improved tour constructed by repetitively swapping the next arc in the (modified) tour with that neighbor arc which gives the largest distance improvement, until the iteration limit is reached or a full cycle over the tour gives no further improvement.

When you run the procedures, the information in the progress window is updated by the functions in the GMP Progress Window library.

Keywords:
Algorithm, 2-opt heuristic, network object, traveling salesman problem, GMP, Progress Window

