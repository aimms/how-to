Identifying Composite Objects in Mathematical Programming Modeling
================================================================== 

Most real world objects referenced in a model are identified by their name or natural number in a sequence.
Two examples are: 

*   Locations: a unique name of that location suffices to identify the object at hand.

*   Periods: a period number in a sequence of periods suffices to identify the object at hand.

In an AIMMS model, these objects are referenced by elements in sets: the set of locations and the set of periods respectively.
These sets contain atomic elements; there is no further structure to these elements.

In contrast, an arc is identified by its components: its source and destination locations.

Compound sets are no longer available in AIMMS, so how to conveniently model composite objects such as arcs in an AIMMS model?
In this how-to article, two approaches will be discussed that both provide an answer to this question.  

Both approaches will be illustrated by a single running example, which will be introduced next.

The running example
--------------------

We want to develop a time space network: a network with flows over arcs for discrete time periods.
Each arc has a from node and a to node.
The flow over an arc has a cost, possibly 0.
There is a stock balance for each node, as each node has incoming arcs (perhaps 0), and outgoing arcs (perhaps 0), and a min and a max on the amount of inventory (both possibly 0).

Let's first specify some identifiers that are independent of the case selected:

Common identifiers
^^^^^^^^^^^^^^^^^^

In this sub section, the identifiers common to both cases are briefly introduced.

Sets
"""""

We start with a set of discrete time periods and a set of locations.

.. code-block:: aimms
    :linenos:

    Set s_timePeriods {
        SubsetOf: Integers;
        Index: i_tp;
    }
    Set s_nodes {
        Index: i_node, i_nodeFrom, i_nodeTo;
    }

Remarks on the above code:

#.  Line 1: As usual in mathematical programming modeling, we discretize time to a discrete set of periods.

#.  Line 2: As numbers are sufficient, the set is made a subset of the set ``Integers``; thereby ensuring that the elements are ordered as expected.

#.  Line 5: The nodes are locations, and only the name of each location is needed to identify a node.

#.  Line 6: In some parts of the model, multiple references to the set ``s_nodes`` with overlapping scope are needed, so multiple indices are declared for this set.

Parameters and variables
""""""""""""""""""""""""""

Data and variables are defined over these sets as usual, for instance to track stock over time there is an initial stock and a variable modeling stock:

.. code-block:: aimms
    :linenos:

    Parameter p_initialStock {
        IndexDomain: i_node;
        Comment: "Stock at the beginning of the first period";
    }
    Variable v_stock {
        IndexDomain: (i_tp,i_node);
        Range: [p_nodeLower(i_node),p_nodeUpper(i_node)];
        Comment: "Stock at *end* of period i_tp";
    }

Remarks on the above code:

#.  When at a location no stock is kept, the parameters ``p_nodeLower``, and ``p_nodeUpper`` are both 0.

To model composite objects such arcs, there are two modeling approaches.  
The first one, that uses the components of a composite object directly, is introduced in the next section.

Component based Approach: Identifying composite objects via their components
---------------------------------------------------------------------------------

In this section the socalled **Component based Approach** for identifying composite objects in 
mathematical programming applications is illustrated via its use in the running example.

By identifying a composite object via its components, we need to:

#.  Refer explicitly to the components, in our running example these components are the from node and the to node.

#.  Limit in the code to the existing combinations.

This is best explained by looking at the example:

.. code-block:: aimms
    :linenos:

    Parameter bp_arcs {
        IndexDomain: (i_nodeFrom,i_nodeTo);
        Range: binary;
        Comment: "1 iff an arc from i_nodeFrom to i_nodeTo exists.";
    }

Here the components of an arc are the ``i_nodeFrom`` and the ``i_nodeTo``. 
An arc only exists if ``bp_arcs(i_nodeFrom, i_nodeTo)`` is 1. 

To model the unit cost of flowing through an arc, we write the following:

.. code-block:: aimms
    :linenos:

    Parameter p_cost1 {
        IndexDomain: (i_nodeFrom,i_nodeTo) | bp_arcs(i_nodeFrom, i_nodeTo) ;
        Comment: "Cost to transport over the arc defined by i_nodeFrom and i_nodeTo.";
    }

On line 2, the collection of arcs is described by the indexing expression ``(i_nodeFrom,i_nodeTo)`` 
limited to only the existing ones by the index domain condition ``bp_arcs(i_nodeFrom, i_nodeTo)``.

This is repeated in the modeling of the decision variable how much is flowing through an arc, per period, as follows:

.. code-block:: aimms
    :linenos:

    Variable v_flow1 {
        IndexDomain: (i_tp, i_nodeFrom, i_nodeTo) | bp_arcs(i_nodeFrom, i_nodeTo);
        Range: nonnegative;
        Comment: "Flow out of i_nodeFrom into i_nodeTo during period i_tp provided arc (i_nodeFrom, i_nodeTo) exists.";
    }

Note that the above formulation permits a transport with 0 cost over an existing arc.

Based on the above declarations, a stock balance for each node, time period, can be written as follows:

.. code-block:: aimms
    :linenos:

    Constraint c_stockBalance1 {
        IndexDomain: (i_tp, i_node);
        Definition: {
            v_stock(i_tp,i_node) ! Stock at end of period i_tp
                =
                if i_tp = first( s_timePeriods ) then  
                    p_initialStock(i_node) ! Stock at beginning of first period
                else
                    v_stock( i_tp - 1, i_node ) ! Stock at end of previous period
                endif 
                +
                sum( i_nodeFrom, 
                    v_flow1(i_tp, i_nodeFrom, i_node) ) ! Total flow into i_node during period i_tp
                -
                sum( i_nodeTo, 
                    v_flow1(i_tp, i_node, i_nodeTo) ) ! Total flow out of i_node during period i_tp
        }
    }

Selected remarks about the above code, especially lines 12, 13 and 15,16:

#.  On the one hand, the index ``i_node`` that is given scope in the index domain of the constraint (line 2), is elegantly used in  ``v_flow1(i_tp, i_nodeFrom, i_node)`` and in ``v_flow1(i_tp, i_node, i_nodeTo)`` to select only the flows over the arcs that go into, respectively out of the node ``i_node``.

#.  On the other hand, from these expressions (``v_flow1(i_tp, i_nodeFrom, i_node)`` and ``v_flow1(i_tp, i_node, i_nodeTo)``), it is not immediately clear that only the flows of the existing arcs are considered; one needs to check the index domain condition of the flow variable to verify that.

Similar remarks can be made for the contribution to the objective of the flow cost:

.. code-block:: aimms
    :linenos:

    Variable v_obj1 {
        Range: free;
        Definition: {
            sum( (i_tp, i_nodeFrom, i_nodeTo), 
                v_flow1(i_tp, i_nodeFrom, i_nodeTo) * p_cost1( i_nodeFrom, i_nodeTo ) )
        }
    }

.. note:: Clearly approach 1 is an existing approach of modeling composite objects.

Reference based Approach: Identifying composite objects via a reference element
-------------------------------------------------------------------------------------------

In this section a second modeling technique for identifying composite objects is illustrated using reference elements.
Arcs can be enumerated by numbering them and putting these numbers in a separate set:

.. code-block:: aimms
    :linenos:

    Set s_arcIds {
        Index: i_arc;
    }

With the set of arc ids, we can specify per arc, where it comes from and where it goes to via element parameters:

.. code-block:: aimms
    :linenos:

    ElementParameter ep_arcNodeFrom {
        IndexDomain: i_arc;
        Range: s_nodes;
    }
    ElementParameter ep_arcNodeTo {
        IndexDomain: i_arc;
        Range: s_nodes;
    }

To transport one unit over an arc has a certain cost:

.. code-block:: aimms
    :linenos:

    Parameter p_cost2 {
        IndexDomain: (i_arc);
        Comment: "Cost to transport one unit over arc i_arc taking into account its ep_arcNodeFrom and its ep_arcNodeTo";
    }

Now we can declare the flow variable as follows:

.. code-block:: aimms
    :linenos:

    Variable v_flow2 {
        IndexDomain: (i_tp,i_arc);
        Range: nonnegative;
    }

Note that the index domain specification of ``v_flow2`` is more compact than the index domain specification of ``v_flow1``.

The stock definition starts out to be the same, but the contributing parts (inflow and outflow) are slightly different:

.. code-block:: aimms
    :linenos:

    Constraint c_stockBalance2 {
        IndexDomain: (i_tp,i_node);
        Definition: {
            v_stock(i_tp,i_node) ! Stock at end of period i_tp
                =
                if i_tp = first( s_timePeriods ) then
                    p_initialStock(i_node)
                else
                    v_stock( i_tp - 1, i_node ) ! Stock at end of previous period
                endif 
                +
                sum( i_arc | ep_arcNodeTo(i_arc) = i_node, 
                    v_flow2(i_tp, i_arc ) ) ! Total flow into i_node during period i_tp
                -
                sum( i_arc | ep_arcNodefrom(i_arc) = i_node, 
                    v_flow2(i_tp, i_arc ) ) ! Total flow out of i_node during period i_tp
        }
    }
    
Selected remarks about the above code, especially lines 12, 13 and 15,16:

#.  As the variable ``v_flow2`` is not indexed over nodes, but over arcs, we can not filter the arcs simply by referencing the ``i_node`` in the arguments of ``v_flow2``.

#.  However, we are explicit that the ``v_flow2`` is over existing arcs and are explicit about filtering only those arcs that go into the node, out of the node ``i_node`` by using the conditions ``ep_arcNodeTo(i_arc) = i_node`` and ``ep_arcNodefrom(i_arc) = i_node`` respectively.

Finally, the contribution of the flow cost to the objective is more concise than in the first approach:

.. code-block:: aimms
    :linenos:

    Variable v_obj2 {
        Range: free;
        Definition: sum( (i_tp, i_arc), v_flow2( i_tp, i_arc ) * p_cost2( i_arc ) );
    }

.. note:: Approach 2 is closely related to existing practice in the design of some databases, whereby each row a unique number is assigned and the data of the row is accessed via that identification number.

A brief comparison of the two approaches
-----------------------------------------

Neither approach is really novel:

#.  The first approach can be seen as an outgrowth of consistent use of the index domain condition in parameters and variables.

#.  The second approach has clear roots in the design of databases.

Advantage of the first approach: it is close to existing modeling practices; and when ordering of the composite objects and selecting one or more specific objects is not relevant to the application, it works out fine.

Advantage of the second approach: 

#.  it leads to more concise modeling, especially when the components are not relevant to the definition at hand. This is illustrated by comparing the variable definitions of ``v_obj1`` and ``v_obj2``.

#.  expressions that involve a selecting a subset of composite objects (for instance the subset of arcs going into a selected node), can be explicitly formulated as such (by using the index i_arc), instead of relying on the reader to remember that in the index domain condition the restriction is added that it is defined over that set of composite objects (restricting to bp_arc(i_nodeFrom,i_nodeTo)).

Related articles
--------------------

This how to article is the first in a group of small articles.

#.  To illustrate that the concepts presented are an extension of existing practices, 
    we illustrate that the relation between the mathematical programming modeling techniques and existing database design practices.
    See :doc:`/Articles/526/526-composite-exchange-database`

#.  To illustrate that the concepts presented can be used throughout the modeling language, 
    the use of element parameters and indexed sets
    See :doc:`/Articles/526/526-language-leverages-composite-objects`


References
------------

#. `The difference between composite and compound <https://wikidiff.com/composite/compound>`_

