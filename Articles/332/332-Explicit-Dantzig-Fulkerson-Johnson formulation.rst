Explicit Dantzig-Fulkerson-Johnson formulation
==============================================
The library in AIMMS that solves a **Capacitated Vehicle Routing Problem** (**CVRP**) contains four different formulation options. The formulations have different methods of eliminating subtours. In this article the Explicit Dantzig-Fulkerson-Johnson formulation is discussed. 


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