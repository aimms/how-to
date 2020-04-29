Explicit Dantzig-Fulkerson-Johnson formulation
==============================================

There is a library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**). In that library there are four different formulation options of the problem. The first formulation is called the **Explicit Dantzig-Fulkerson-Johnson** formulation and will be explained in this article.

- this is a link to the library 
- this is a link to the article that explains the library


Formulation CVRP
----------------

A CVRP can be formulated as a linear integer programming model. The total distance of the route, where all costumers demands are met, should be minimized. The binary variable :math:`x_{ijk}` has a value of :math:`1` if the arc from node i to node j is in the optimal route and is driven by vehicle k. The variable :math:`d_{ij}` discribes the distance from node i to node j. There are n nodes (depot = 1) and p vehicles. The objective function can be formulated as follows:

.. math:: Min \sum_{k = 1}^{p}{\sum_{i = 1}^{n}{\sum_{j = 1, i \neq j}^{n}{d_{ij}x_{ijk}}}}

Every node should be entered and left once (expect for the depot) and by the same vehicle. The depot should be left and entered once by each vehicle. :math:`q_{i}` describes the demand of each costumer and :math:`Q` is the capacity of the vehicles. The sum of the demands of all costumers that vehicle k will serve, shouldn't exceed the capacity of vehicle k. All these constraints can be formulated as follows:

.. math:: \sum_{k = 1}^{p}{\sum_{i = 1, i \neq j}^{n}{x_{ijk}}} = 1  \qquad \forall j \in \{2,...,n\}
.. math:: \sum_{j = 2}^{n}{x_{1jk}} = 1 \qquad \forall k \in \{1,...,p\}
.. math:: \sum_{i = 1, i \neq j}^{n}{x_{ijk}} = \sum_{i = 1}^{n}{x_{jik}} \qquad \forall j \in \{1,...,n\}, \enspace k \in \{1,...,p\}
.. math:: \sum_{i = 1}^{n}{\sum_{j = 2}^{n}{q_{j} x_{ijk}}} \leq Q \qquad \forall k \in \{1,...,p\}
.. math:: x_{ijk} \in \{0,1\} \qquad \forall k \in \{1,...,p\},\enspace i,j \in \{1,...,n\}, \enspace i \neq j

* The constraints (1) ensure that every node is entered once. The constraints (3) denote that every node is entered and left by the same vehicle, the same amount of times. So if every node is entered once, it is also left once.
* The constraints (2) denote that the depot is left once by vehicle k. In combination with constraints (3) it is made sure that the depot is also entered once by vehicle k.

All these constraints are formulated in the ``Common Constraints and Variables`` section in the CVRP Library.

However, a route that satisfies all these constraints could still be infeasible. Namely when the route contains a subtour (see image). 

.. image:: images/subtour.png
   :scale: 35%
   :align: center


Subtour Elimination Constraints 
-------------------------------

The Dantzig-Fulkerson-Johnson formulation uses subsets to eliminate subtours. If a subset contains three nodes and there are three arcs between those nodes, it would create a subtour. Therefore, there should always be less arcs between nodes in a subsets than nodes in that subset. This can be formulated as follows:

.. math:: \sum_{k = 1}^{p}{\sum_{i,j \in S}{x_{ijk}}} \leq |S|-1 \qquad S \subset \{2,...,n\}, \enspace 2 \leq |S| \leq n - 2

A tour that passes the depot is not a subtour. That is why S is a subset of all nodes from 2 to n. Which is the set of all costumers. It is not possible to form a subtour with 1 node, so the cardinality of S should be at least 2.


AIMMS 
-----
In the CVRP library this formulation is implemented in the section: ``Explicit Dantzig Fulkerson Johnson Section``. In order to create constraints about subsets, the subsets should be generated first. This happens in the procedure ``Create_Subsets``. The body of this procedure is as follows:

.. code-block:: aimms
	:linenos:

	empty s_CostumerSubset, s_SubsetNumber, p01_Subsets;
	
	
	repeat
			!add subset (if it contains at least two cities)
			if card(i_selectedCostumer) >= 2 and card(i_selectedCostumer) <= 
			card(s_Nodes) - 2 then
				s_SubsetNumber += card(s_SubsetNumber ) + 1 ;
				ep_LastSubsetNumber := last(s_SubsetNumber);
				p01_Subsets( i_SelectedCostumer, ep_LastSubsetNumber ) := 1;
			endif;
	
			break when s_CostumerSubset = s_Costumers;
	
		block !generate next subset (using binary counting)
			ep_lastUnselectedCostumer := last( i | not ( i in s_CostumerSubset ) );
			for i | i > ep_lastUnselectedCostumer do
				s_CostumerSubset -= i ;
			endfor ;
			s_CostumerSubset += ep_lastUnselectedCostumer ;
		endblock ;
	
	endrepeat ;


Every possible subset of ``s_Nodes`` is checked using binary counting. All subsets without the depot and with a minimum of two nodes will be created. A number is then added to the set ``s_SubsetNumber``. The binary parameter ``p01_Subsets`` indicates which nodes are in that subset. 

For example, if there are 5 nodes (i)

- **line 15 - line 21**: 	The next subset (``s_CostumerSubset``) is generated using binary counting.
- **line 6  - line 11**: 	If ``s_CostumerSubset`` contains at least two nodes, then that subset is added.
- **line 13**: 				The procedure should stop when ``s_CostumerSubset`` contains all costumers. Because with binary counting, all the following subsets would contain the depot. 



Constraints
^^^^^^^^^^^

.. code-block:: aimms 

	sum((i, j) | p01_Subsets(i, s) and p01_Subsets(j, s), v01_x(i, j, k) ) 
	<= sum( i, p01_Subsets(i,s) ) - 1




note: realize that it takes a lot of time to generate all subsets!