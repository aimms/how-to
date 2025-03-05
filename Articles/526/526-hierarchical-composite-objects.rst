Hierarchical composite objects
========================================

Can a component in a composite object be a composite object itself? 
Is it possible to hierarchically construct composite objects and use the AIMMS Language which is an algebraic language?

The component based approach supports this by recursively enumerating all components in all declarations over the sets of composite objects. This article will not present a worked example as the details drag on and on.

The reference element based approach permits this.  
This article is a companion article to :doc:`../526/526-modeling-composite-objects` and refines the running example introduced there.

The :download:`AIMMS 4.82 project download <model/TimeSpaceNetworkHierachical.zip>`


The refinement of the running example
--------------------------------------

The running example refers to 6 locations that are also nodes: ParisP, ParisD, Stockholm, Madrid, Berlin, and Rome.
The first two nodes are located in Paris, one being a Production and the other a Distribution node.

Each node actually has two components: the location and the facility type. 
In the companion articles, the arcs were modeled as two components: a from and a to node.
So why don't we structure the arcs and nodes as follows:

.. image:: images/composite-objects.png
    :align: center

Declaring and using the node type
---------------------------------

The node type is declared in AIMMS as follows:

.. code-block:: aimms
    :linenos:

    Set s_locations {
        Index: i_loc;
    }
    Set s_nodeTypes {
        Index: i_nt;
        Definition: data { Production, Distribution };
    }
    StringParameter sp_shortTypeName {
        IndexDomain: i_nt;
        Definition: data { Production : "PR",  Distribution : "DC" };
    }
    Set s_nodeIds {
        Index: i_node, i_nodeFrom, i_nodeTo;
    }
    ElementParameter ep_nodeLocation {
        IndexDomain: i_node;
        Range: s_locations;
    }
    ElementParameter ep_nodeType {
        IndexDomain: i_node;
        Range: s_nodeTypes;
    }
    
With these declarations, we can define the set of ``s_productionNodes`` as follows:

.. code-block:: aimms
    :linenos:

    Set s_productionNodes {
        SubsetOf: s_nodeIds;
        Definition: {
            { i_node | ep_nodeType(i_node) = 'Production' }
        }
    }

Declaring and using the arc type
---------------------------------

The arc type is declared as before, namely as follows:

.. code-block:: aimms
    :linenos:

    Set s_arcIds {
        Index: i_arc;
        webui::ElementTextIdentifier: sp_arcName( i_arc );
    }
    ElementParameter ep_arcNodeFrom {
        IndexDomain: i_arc;
        Range: s_nodeIds;
    }
    ElementParameter ep_arcNodeTo {
        IndexDomain: i_arc;
        Range: s_nodeIds;
    }
    
With the declaration of both nodes and arcs, we can select the arcs coming from a production location as follows:

.. code-block:: aimms
    :linenos:

    Set s_arcsComingFromProduction {
        SubsetOf: s_arcIds;
        Index: i_pa;
        Definition: {
            { i_arc | ep_nodeType( ep_arcNodeFrom(i_arc) ) = 'Production' }
        }
    }

Text input data
------------------

Part of the input for this model can be presented as AIMMS Composite tables as follows:

.. code-block:: aimms
    :linenos:

    Composite table:
        i_node    ep_nodeLocation(i_node)  ep_nodeType(i_node)  p_initialStock(i_node)  p_productionCap(i_node)
    !   ------    -----------------------  -------------------  ----------------------  -----------------------
        node-1    Paris                    Production                               10                        7
        node-2    Paris                    Distribution
        node-3    Stockholm                Production                                9                        7
        node-4    Madrid                   Distribution
        node-5    Berlin                   Distribution
        node-6    Rome                     Distribution
        ;

    Composite table:
        i_arc     ep_arcNodeFrom(i_arc)  ep_arcNodeTo(i_arc)  p_cost(i_arc)
    !   ------    ---------------------  -------------------  -------------
        arc001    node-1                 node-2
        arc002    node-1                 node-3                           3
        arc003    node-1                 node-4                           4
        arc004    node-1                 node-5                           5
    ...    
    ;

Reporting the node and arc names
--------------------------------

Clearly, as we have to look up the interpretation of a node name, it is not immediately clear what an arc is.
This can be improved in the reporting, as the screenshot of a WebUI widget of the solution shows below:

.. image:: images/hierarchy-list.png
    :align: center

This is achieved using the following report naming of nodes and arcs (i.e. the element text annotation):

In the node name definition, we assume that the decision maker / end user knows which facility type is used for each location, 
except when there are multiple facilities in one location.

.. code-block:: aimms
    :linenos:

    StringParameter sp_nodeName {
        IndexDomain: i_node;
        Definition: {
            if p_noNodesPerLocation(ep_nodeLocation( i_node)) = 1 then
                formatString("%e", ep_nodeLocation( i_node) )
            else
                formatString("%e (%s)", ep_nodeLocation( i_node), 
                    sp_shortTypeName( ep_nodeType( i_node ) ) )
            endif
        }
    }

Once we have a clarifying node name, we can use that node name in the arc name as follows:

.. code-block:: aimms
    :linenos:

    StringParameter sp_arcName {
        IndexDomain: i_arc;
        Definition: {
            formatString( "%s %s %s", 
                sp_nodeName( ep_arcNodeFrom( i_arc ) ), 
                character( 10230 ), ! Long right arrow (unicode char).
                sp_nodeName( ep_arcNodeTo(   i_arc ) ) )
        }
    }

Comparing deprecated compound sets and the reference element based approach
------------------------------------------------------------------------------

An advantage of the reference element based approach:
The reference element based approach allows for hierarchical construction of objects as illustrated in this article.
This was not offered in the now deprecated compound sets.

A functionality of the deprecated compound sets:
The deprecated compound sets allowed to declare per composite object but also to use the component based approach in expressions.

.. code-block:: aimms
    :linenos:

    Set s_nodes {
        Index: i_node, i_nodeFrom, i_nodeTo;
    }
    Set s_arcs {
        SubsetOf: (s_nodes, s_nodes);
        Tags: (afrom, ato);
        Index: i_arc;
    }
    Variable v_flow {
        IndexDomain: i_arc;
        Range: free;
    }
    Parameter p_totFlowCompBased {
        Definition: sum( (i_nodeFrom, i_nodeTo), v_flow(i_nodeFrom, i_nodeTo) );
    }
    Parameter p_totFlowRefBased {
        Definition: sum( i_arc, v_flow(i_arc) );
    }
    Parameter p_totInFlowCompBased {
        IndexDomain: i_node;
        Definition: sum( i_nodeFrom, v_flow(i_nodeFrom, i_node) );
    }
    Parameter p_totInFlowRefBased {
        IndexDomain: i_node;
        Definition: sum( i_arc | i_arc.ato = i_node, v_flow(i_arc) );
    }

On lines 14 and 21 the component based approach is used in using ``v_flow``.
On lines 17 and 25 the reference element based approach is used in using ``v_flow``.

Whether or not mixing the component and reference element based approach is an advantage is debatable. 
In :doc:`../526/526-modeling-composite-objects` it is shown that using the reference element based approach is clearer.  
Even when selecting arcs using element parameters or tags to refer to the components of a composite object.
