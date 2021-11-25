Modeling composite objects
================================================================== 

Many sets in AIMMS models refer to atomic objects; there is no further structure to the objects referenced.
Examples are locations and period numbers.

In contrast, an arc is identified by its components: its source and destination locations.
This makes an arc a composite object.

How to model such composite objects in AIMMS, as compound sets are no longer available?

In this how-to article two approaches are presented: the component based approach and the reference element based approach. 
The component based approach is in widespread use and the reference element based approach is inspired by database design.
This article discusses how these approaches are used in the definition of variables and constraints.

The :download:`AIMMS 4.82 project download <model/TimeSpaceNetworkBasic.zip>`

But first the running example:

The running example
--------------------

We want to develop a time space network: a network with flows over arcs for discrete time periods.
Each arc has a from node and a to node.
The flow over an arc has a cost.
There is a stock balance for each node, as each node has incoming arcs, and outgoing arcs, and a min and a max on the amount of inventory.

Let's first specify some identifiers that are independent of the approach selected:

Common identifiers
^^^^^^^^^^^^^^^^^^

In this subsection, the identifiers common to both cases are briefly introduced.

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

#.  Line 1: As usual in mathematical programming modeling, time is modeled as a discrete set of periods.

#.  Line 2: As numbers are sufficient, the set is made a subset of the set ``Integers``; thereby ensuring that the elements are ordered as expected. See also :doc:`../112/112-Integer-properties`

#.  Line 5: The nodes are locations, and only the name of each location is needed to identify a node.

#.  Line 6: In some parts of the model, multiple references to the set ``s_nodes`` are needed.  These references have an overlapping scope. So multiple indices are declared for this set.

Parameters and variables
""""""""""""""""""""""""""

Data and variables are defined over these sets as usual, for instance, to track stock over time there is an initial stock and a variable modeling stock:

.. code-block:: aimms
    :linenos:

    Parameter p_initialStock {
        IndexDomain: i_node;
        Comment: "Stock at the beginning of the first period";
    }
    Variable v_stock {
        IndexDomain: (i_tp,i_node);
        Range: nonnegative;
        Comment: "Stock at end of period i_tp";
    }

To model composite objects such as arcs, there are two modeling approaches.  
The first one, that uses the components of a composite object directly, is introduced in the next section.

Component based approach: Identifying composite objects via their components
---------------------------------------------------------------------------------

In this section the so-called **Component based Approach** for identifying composite objects in 
mathematical programming applications is illustrated via its use in the running example.

.. note:: The approach outlined in this section is an existing approach to modeling composite objects with which you are most likely already familiar. It is stated here to clarify the difference with the reference element approach introduced below.

By identifying a composite object via its components, we need to:

#.  Refer explicitly to the components, in our running example, these components are the from node and the to node.

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
        Range: [0, p_arcCapacity];
        Comment: "Flow out of i_nodeFrom into i_nodeTo during period i_tp.";
    }

Note that the above formulation permits a transport with 0 costs over an existing arc.

Based on the above declarations, a stock balance for each node, time period, can be written as follows:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 12,13,15,16

    Constraint c_stockBalance1 {
        IndexDomain: (i_tp, i_node);
        Definition: {
            v_stock(i_tp,i_node) ! Stock at end of period i_tp
                =
                if i_tp = first( s_timePeriods ) then
                    p_initialStock(i_node)
                else
                    v_stock( i_tp - 1, i_node ) ! Stock at end of previous period
                endif 
                +
                sum( i_nodeFrom, 
                    v_flow1(i_tp, i_nodeFrom, i_node) ) ! Total flow into i_node during period i_tp
                -
                sum( i_nodeTo, 
                    v_flow1(i_tp, i_node, i_nodeTo) ) ! Total flow out of i_node during period i_tp
                +
                v_production(i_tp, i_node)
                -
                p_demand(i_tp, i_node)
        }
    }

Selected remarks about the above code, especially the highlighted lines:

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


Reference element based approach: Identifying composite objects via a reference element
-------------------------------------------------------------------------------------------

In this section, a second modeling technique for identifying composite objects is illustrated using reference elements.
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
    :emphasize-lines: 12,13,15,16

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
                    v_flow2( i_tp, i_arc ) ) ! Total flow into i_node during period i_tp
                -
                sum( i_arc | ep_arcNodefrom(i_arc) = i_node, 
                    v_flow2( i_tp, i_arc ) ) ! Total flow out of i_node during period i_tp
                +
                v_production(i_tp, i_node)
                -
                p_demand(i_tp, i_node)
        }
    }
    
Selected remarks about the above code, especially highlighted lines:

#.  As the variable ``v_flow2`` is not indexed over nodes, but over arcs, we can not filter the arcs simply by referencing the ``i_node`` in the arguments of ``v_flow2``.

#.  However, we are explicit that the ``v_flow2`` is over existing arcs and are explicit about filtering only those arcs that go into the node, out of the node ``i_node`` by using the conditions ``ep_arcNodeTo(i_arc) = i_node`` and ``ep_arcNodefrom(i_arc) = i_node`` respectively.

Finally, the contribution of the flow cost to the objective is more concise than in the first approach:

.. code-block:: aimms
    :linenos:

    Variable v_obj2 {
        Range: free;
        Definition: sum( (i_tp, i_arc), v_flow2( i_tp, i_arc ) * p_cost2( i_arc ) );
    }

.. note:: The approach outlined in this section is closely related to existing practice in the design of some databases, whereby to each row a unique number is assigned and the data of the row is accessed via that identification number.

A brief comparison of the two approaches
-----------------------------------------

Neither approach is really novel:

#.  The first approach can be seen as an outgrowth of consistent use of the index domain condition in parameters and variables.

#.  The second approach has clear roots in the design of databases.

Advantage of the first approach: it is close to existing modeling practices, and when ordering of the composite objects and selecting one or more specific objects is not relevant to the application, it works out fine.

Advantage of the reference element approach: 

#.  It leads to more concise modeling, especially when some components are not relevant to the definition at hand. This is illustrated by comparing the variable definitions of ``v_obj1`` and ``v_obj2``.

#.  Expressions that involve selecting a subset of composite objects (for instance the subset of arcs going into a selected node), can be explicitly formulated as such (by using the index i_arc), instead of relying on the reader to remember that in the index domain condition the restriction is added that it is defined over that set of composite objects (restricting to ``bp_arc(i_nodeFrom,i_nodeTo)``).

Related articles
--------------------

This how to article is the first in a group of small articles. Other articles are:

#.  To illustrate that the concepts presented are an extension of existing practices, 
    we illustrate that the relation between the mathematical programming modeling techniques and existing database design practices.
    See :doc:`/Articles/526/526-composite-exchange-database`.
    
#.  To illustrate that the concepts presented are also applicable in the creation of an end user interface, see :doc:`/Articles/526/526-reporting-data-over-composite-objects`.

#.  To illustrate that the reference element based approach can be used throughout the modeling language, 
    including the use of element parameters, ordered sets, and indexed sets
    see :doc:`/Articles/526/526-language-leverages-composite-objects`

#.  An important advantage of the reference element based approach is that it can be used hierarchically, see :doc:`/Articles/526/526-hierarchical-composite-objects`.

#.  As the last article in this group, different styles implementing the reference element based approach are compared for clarity and flexibility.  In addition, the execution efficiency of the reference element based approach is compared to execution efficiency of the component approach; with results that might surprise you.

#.  As an aside, check `the difference between composite and compound <https://wikidiff.com/composite/compound>`_

