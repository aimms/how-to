Miller-Tucker-Zemlin formulation
================================

There are multiple ways to eliminate subtours from a Capacitated Vehicle Routing Problem (CVRP). There exists an example of an AIMMS library that solves a CVRP. In that library there are three different formulation options (so far). All options contain different ways of formulating subtour elimination constraints. The second formulation is called the **Explicit Miller-Tucker-Zemlin** formulation and will be explained in this article. 

- this is a link to the library
- this is a link to the article that explains the library 
- this is a link to the article that explains the first formulation 

Formulation
-----------

A CVRP can be formulated as a linear integer programming model. In the article about the Explicit Dantzig-Fulkerson-Johnson formulation, the full formulation of a CVRP is given. However, the Miller-Tucker-Zemlin formulation uses a different way to eliminate subtours. 

.. image:: images/subtour.png
   :scale: 35%
   :align: center


Explicit Miller-Tucker-Zemlin
-----------------------------

The Miller-Tucker-Zemlin formulation uses an extra variable to eliminate subtours. This variable ( :math:`u_{ik}` ) basically gets higher each time a new costumer is visited with vehicle k. If vehicle k travels from node i to node j, :math:`u_{jk}` should always be higher than :math:`u_{ik}`. This way it won't be possible to return to a previously visited node and create a subtour.


(the node already has a value for i (that must be lower))



-> oke ik heb besloten wel gewoon u_ik te gebruiken. Je kan het dan namelijk ook uitleggen als u is een teller die omhoog gaat iedere keer als een nieuwe bezoeker wordt bezocht (met een bepaalde vehicle)



AIMMS
-----












