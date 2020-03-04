Implicit Dantzig-Fulkerson-Johnson formulation
==============================================

The Dantzig-Fulkerson-Johnson formulation is a formulation that can eliminate subtours from a Capacitated Vehicle Routing Problem (CVRP). In this article (link) this formulation is explained and how to explicitly generate the constraints in AIMMS. However, it is also possible to implicitly generate the costraints. These are called lazy constraints and will save a lot of time. 

There is an AIMMS example of a library that solves a CVRP. There are multiple options of formulations for the problem, including the Implicit Dantzig-Fulkerson-Johnson formulation. In this article that formulation is explained using the library. 


Formulation
-----------

.. math:: \sum_{k = 1}^{p}{\sum_{i,j \in S}{x_{ijk}}} \leq |S|-1 \qquad S \subset \{2,...,n\}, \enspace 2 \leq |S| \leq n - 2



-> this is the formulation
-> uitleggen wat er in de formulation staat
-> het is gebaseerd op subsets...
-> wanneer je deze constraints expliciet genereerd, moeten alle subsets worden gegenereerd (als je een grote set met nodes hebt duurt dit te lang)
-> 



to read more about lazy constraints: link to TSP lazy constraints