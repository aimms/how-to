Modeling styles for using reference elements
==============================================================

This is a companion article to :doc:`../526/526-modeling-composite-objects`.
That article and other companion articles show the advantages of the reference element approach.
The modeling style used for the reference element approach in those articles is not the only possible modeling style for the reference element approach.

When there are multiple modeling styles, you want to select the best style for your application; hoping to avoid unnecessary changes to your application later on.
To help you make a good choice regarding the modeling style for the reference element approach, 
this article compares a few modeling styles with respect to:

#.  **Modeling clarity**.

    Clarity of the model is crucial, as this will allow you to communicate the reality being modeled with the stakeholders more easily, and thereby gain their trust in the results of your application.

    There is clarity in the declaration of the composite object itself, but more importantly, 
    there is clarity in the use of composite objects in the application, 
    for instance in the formulation of the constraints. 

    That is why the clarity will be evaluated by discussing the consequence of style when formulating a stock balance; similar to what is presented in :doc:`../526/526-modeling-composite-objects`

#.  **Modeling flexibility**. 

    Flexibility in the application is important; how much effort is needed to update an application when a change in the structure is made? Regarding composite objects, a potential change is adding a component to the composite object - does it become necessary to edit every piece of source where the composite object is used?

    In this article, a mode of transportation is added to the composite object arc. 
    We will then discuss, for each modeling style, how much effort is needed for every reference to the composite object arc.

#.  **Execution efficiency**.

    The snappiness of the application when making small changes, the overall wait time when searching for a good solution; are in part determined by how the assignments and constraints are formulated.

    In this article, a small model is used to measure the relative efficiency of the three styles. 
    As a bonus, the component based approach is also part of these measurements.


The various modeling styles are presented in the next section, and then each of the evaluation criteria presented above is used to evaluate in a separate section. Finally, we present a brief summary and discussion.

The modeling styles
---------------------

This article details three different modeling styles:

#.  **The multiple element parameter style**; this is the style used in the main article and companion articles. 
    Using this style, arcs are declared as follows:

    .. code-block:: aimms
        :linenos:

        Set s_arcIds {
            Index: i_arc;
        }
        ElementParameter ep_arcNodeFrom {
            IndexDomain: i_arc;
            Range: s_nodes;
            Comment: "Return the node from which the arc 'i_arc' flows";
        }
        ElementParameter ep_arcNodeTo {
            IndexDomain: i_arc;
            Range: s_nodes;
            Comment: "Return the node to which the arc 'i_arc' flows";
        }

    There is an element parameter for every component in the composite object.

#.  **The multiple binary parameter style**; this is a small variation of the element parameter style. 
    Using this style, arcs are declared as follows:

    .. code-block:: aimms
        :linenos:

        Set s_arcIds {
            Index: i_arc;
        }
        Parameter bp_arcIsFromNode {
            IndexDomain: (i_arc,i_node);
            Range: binary;
            Comment: "The arc 'i_arc' flows out of node 'i_node'";
        }
        Parameter bp_arcIsToNode {
            IndexDomain: (i_arc,i_node);
            Range: binary;
            Comment: "The arc 'i_arc' flows towards node 'i_node'";
        }

    There is a two-dimensional binary parameter for every component in the composite object.

#.  **The single encompassing binary parameter style**; this style is similar to relational tables.
    Using this style, arcs are declared as follows:

    .. code-block:: aimms
        :linenos:

        Set s_arcIds {
            Index: i_arc;
        }
        Parameter bp_arcRelation {
            IndexDomain: (i_arc,i_fromNode,i_toNode);
            Range: binary;
            Comment: "The arc 'i_arc' flows out of 'i_fromNode' towards 'i_toNode'";
        }

    There is one entry in this parameter for every arc.
    The binary parameter has an index for the arc and for every component in the arc. 
    It is reminiscent of the component based approach; as it can be viewed as the component based approach extended with a reference element.

Recap running example
-----------------------------

This article continues the running example presented in  :doc:`../526/526-modeling-composite-objects`.
In particular, the following declarations will be used:


Sets
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

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


Parameters and variables
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Data and variables are defined over these sets as usual, for instance,to track stock over time there is an initial stock and a variable modeling stock:

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

Set with reference elements
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Arcs can be enumerated by numbering them and putting these numbers in a separate set:

.. code-block:: aimms
    :linenos:

    Set s_arcIds {
        Index: i_arc;
    }

Variable declared over set with reference elements
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: aimms
    :linenos:

    Variable v_flow {
        IndexDomain: (i_tp,i_arc);
        Range: nonnegative;
    }


Modeling clarity
--------------------

In this section, we evaluate the formulation of a stock balance per node, where materials are flowing in to and out of that node, production increases stock, and demand decreases stock.

Clarity: The multiple element parameter style
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The stock balance is the same as presented in :doc:`../526/526-modeling-composite-objects`.

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 12,15

    Constraint c_stockBalance {
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
                    v_flow( i_tp, i_arc ) ) ! Total flow into i_node during period i_tp
                -
                sum( i_arc | ep_arcNodefrom(i_arc) = i_node, 
                    v_flow( i_tp, i_arc ) ) ! Total flow out of i_node during period i_tp
                +
                v_production(i_tp, i_node)
                -
                p_demand(i_tp, i_node)
        }
    }

Lines 12 and 15 are emphasized, as here the composite structure of arcs plays a role.
On line 12, the condition ``ep_arcNodeTo(i_arc) = i_node`` clearly states that a flow *to* ``i_node`` is considered an inflow for ``i_node``.

Clarity: The multiple binary parameter style
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The same stock balance, but now using the binary parameter style:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 12,15

    Constraint c_stockBalance {
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
                sum( i_arc | bp_arcIsToNode(i_arc,i_node), 
                    v_flow( i_tp, i_arc ) ) ! Total flow into i_node during period i_tp
                -
                sum( i_arc | bp_arcIsFromNode(i_arc,i_node), 
                    v_flow( i_tp, i_arc ) ) ! Total flow out of i_node during period i_tp
                +
                v_production(i_tp, i_node)
                -
                p_demand(i_tp, i_node)
        }
    }

On line 12, the condition ``bp_arcIsToNode(i_arc,i_node)`` clearly represents the same condition as the condition ``ep_arcNodeTo(i_arc) = i_node`` above, but comes across as a translation towards a more efficient representation for the computer.


Clarity: The single encompassing binary parameter style
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 12,15

    Constraint c_stockBalance {
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
                sum( (i_arc,i_fromNode) | bp_arcRelation(i_arc,i_fromNode,i_node), 
                    v_flow( i_tp, i_arc ) ) ! Total flow into i_node during period i_tp
                -
                sum( (i_arc,i_toNode) | bp_arcRelation(i_arc,i_node,i_toNode), 
                    v_flow( i_tp, i_arc ) ) ! Total flow out of i_node during period i_tp
                +
                v_production(i_tp, i_node)
                -
                p_demand(i_tp, i_node)
        }
    }

On line 12, the condition becomes: ``bp_arcRelation(i_arc,i_fromNode,i_node)`` which is slightly more detail as in the previous style.  More importantly, however, the summation operator has an extra index, which makes understanding the reason behind the condition less easy.


Modeling flexibility
---------------------

Flexibility is tested here by changing the structure of an arc. Each arc gets an additional mode of transport.

.. code-block:: aimms
    :linenos:

    Set s_transportModes {
        Index: i_tMode;
    }

Flexibility: The multiple element parameter style
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The arcs are extended with an additional element parameter, as follows:

.. code-block:: aimms
    :linenos:

    ElementParameter ep_arcTransportMode {
        IndexDomain: i_arc;
        Range: s_transportModes;
        comment: "Return the mode of transport used to flow over the arc 'i_arc'";
    }

Regarding the stock balance; the formulation stays the same. 

Note, however, if different modes of transport are allowed between two nodes, there are now multiple arcs between those nodes, thereby increasing the number of inflow arcs and the number of outflow arcs for a particular node.

Flexibility: The multiple binary parameter style
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The arcs are extended with an additional binary parameter, as follows:

.. code-block:: aimms
    :linenos:

    Parameter bp_arcUsesTransportMode {
        IndexDomain: (i_arc,i_tMode);
        Range: binary;
        comment: "The arc 'i_arc' uses transport mode 'i_tMode'";
    }

Regarding the stock balance; the formulation stays the same. 

A similar note regarding additional inflow and outflow arcs for a particular node applies here as well.

Flexibility: The single encompassing binary parameter style
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

.. code-block:: aimms
    :linenos:

    Parameter bp_arcRelation {
        IndexDomain: (i_arc,i_fromNode,i_toNode,i_tMode);
        Range: binary;
        comment: "The arc 'i_arc' flows out of 'i_fromNode' towards 'i_toNode' using transport mode 'i_tMode'";
    }

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 12,15

    Constraint c_stockBalance {
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
                sum( (i_arc,i_fromNode,i_tMode) | bp_arcRelation(i_arc,i_fromNode,i_node,i_tMode), 
                    v_flow( i_tp, i_arc ) ) ! Total flow into i_node during period i_tp
                -
                sum( (i_arc,i_toNode,i_tMode) | bp_arcRelation(i_arc,i_node,i_toNode,i_tMode), 
                    v_flow( i_tp, i_arc ) ) ! Total flow out of i_node during period i_tp
                +
                v_production(i_tp, i_node)
                -
                p_demand(i_tp, i_node)
        }
    }

As ``bp_arcRelation(i_arc,i_fromNode,i_node,i_tMode)`` has an additional index, the condition needs to be adapted and the summation index list extended, further obfuscating the interpretation of the condition.

Execution efficiency
-----------------------

When comparing execution efficiency, doing an actual test is a good starting point.
The three styles are tested regarding execution efficiency and also compared to the component based approach.
To make the comparison meaningful, the running example is slightly modified:

*   A set of product groups with index ``i_pg`` is added, 

*   The flow is modeled as a parameter ``p_flow(i_tp, i_pg, i_arc)`` with a flow of 1 for every element.

*   The index ``i_tp`` (time periods) varies over a set with 200 elements, the index ``i_pg`` (product groups) varies over a set of 100 elements, the index ``i_node`` varies over a set with 1000 elements, and the index ``i_arc`` varies over a set with 5000 elements.

This leads to the following comparison of execution times:

.. image:: images/ExecutionEfficiency4b.png
    :align: center

where

#.  The ``p_inflow1`` is computed using the reference element approach with the *multiple element parameter style*

#.  The ``p_inflow2`` is computed using the reference element approach with the *multiple binary parameter style*

#.  The ``p_inflow3`` is computed using the reference element approach with the *single encompassing binary parameter style*

#.  The ``p_inflow4`` is computed using the component approach and selecting from nodes in the summation operator.

#.  The ``p_inflow5`` is computed using the component approach, and leaving the selection of the from nodes to the index domain condition in the declaration of ``p_flowComponent``, thus avoiding a duplicate selection of from nodes.

In this test, the reference element based approach (``p_inflow1``, ``p_inflow2``, and ``p_inflow3``) performs significantly superior to the component based approach (``p_inflow4``, ``p_inflow5``).

In addition, within the reference element based approach, the formulation using *multiple element parameter style* is the fastest, but the timings of the three styles are close.

To play around with this example, you can download the :download:`AIMMS 4.82 project here <model/ArcExecutionEfficiency.zip>`.


Summary and discussion
--------------------------

To summarize: 

From the tests on the reference element approach presented in this article, the style: **The multiple element parameter style** clearly performs best overall.

To discuss:

At the time of writing this article, the reference element based approach to composite objects is not in widespread use; thus the tests presented above are admittedly artificial. The author is looking forward to applications whereby the reference element based approach is actually used in practice, such that a comparison of styles, and perhaps also a comparison to the component based approach can be made that is closer to actual modeling practice.