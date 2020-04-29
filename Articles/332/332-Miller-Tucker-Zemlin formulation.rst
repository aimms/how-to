Miller-Tucker-Zemlin formulation
================================

There is a library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**). In that library there are four different formulation options of the problem. The **Miller-Tucker-Zemlin** formulation will be explained in this article.

- this is a link to the library
- this is a link to the article that explains the library 

Formulation
-----------

A CVRP can be formulated as a linear integer programming model. The four different formulations in the CVRP Library all have the same objective function. Most of the constraints are also the same for all formulations. These constraints are located in the section ``Common constraints and variables``. The article about the Explicit-Dantzig-Fulkerson-Johnson formulation elaborates on those constraints. The difference in formulations is the way that subtours are eliminated. 

An optimal route that satisfies all 



.. image:: images/subtour.png
   :scale: 35%
   :align: center


Miller-Tucker-Zemlin
--------------------

The Miller-Tucker-Zemlin formulation uses an extra variable to eliminate subtours. This variable ( :math:`u_{ik}` ) basically gets higher each time a new costumer is visited with vehicle k. If vehicle k travels from node i to node j, :math:`u_{jk}` should always be higher than :math:`u_{ik}`. This way it won't be possible to return to a previously visited node and create a subtour.


(the node already has a value for i (that must be lower))


-> Miller-Tucker-Zemlin formulering zorgt er ook voor dat de capacity van 



AIMMS
-----












