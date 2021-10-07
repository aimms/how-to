Multi Start
===========

.. meta::
   :keywords: Nonlinear Programming, NLP, Multistart Algorithm, GMP, Module
   :description: This example illustrates how a multistart algorithm can improve the reliability of any local NLP solver, by calling it with many starting points.

Direct download AIMMS Project :download:`Multi Start.zip <model/Multi Start.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Multi%20Start

A multistart algorithm can improve the reliability of any local NLP solver, by calling it with many starting points. A single call to a NLP solver can fail or result in a local optimum, but multiple calls from the widely spaced starting points provided by a multistart algorithm have more chances of finding a better solution.

In a pure multistart algorithm many local searches will converge to the same local minimum. The AIMMS multistart algorithm tries to identify such searches to group them into clusters, to reduce computational effort. These clusters are updated (and become larger) whenever a starting point is found that leads to a local solution that has already been found before. 

A function with many local maxima is used in this example. The actual shape of the function can be changed via "Generate Function" button. By clicking "Find Maximum" button, the multi-start algorithm is called to find the maximum solution. Then the "Sample Points View" page shows start points and the solution each reaches; and the "Cluster View" page shows each cluster and its range. Please note depending on the NLP algorithm, two close start points can lead to different optimal solutions, which may cause the clusters to overlap with each other. 

The Multistart module can be added via menu "Settings" - "Install System Module...". More details of the algorithm can be found in :doc:`optimization-modeling-components/advanced-methods-for-nonlinear-programs/index`.

Keywords:
Nonlinear Programming, NLP, Multistart Algorithm, GMP, Module

