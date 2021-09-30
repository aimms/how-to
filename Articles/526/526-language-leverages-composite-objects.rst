Deploying AIMMS semantics for elements to composite objects
=================================================================

This is a companion article to :doc:`Identifying Composite Objects in Mathematical Programming Modeling <../526/526-modeling-composite-objects>`.

AIMMS set semantics provides extensive functionality on sets and elements, including:

#.  `element parameters <https://documentation.aimms.com/language-reference/non-procedural-language-components/parameter-declaration/index.html>`_,

#.  `element valued functions <https://documentation.aimms.com/language-reference/non-procedural-language-components/set-set-element-and-string-expressions/set-element-expressions.html#intrinsic-functions-for-sets-and-set-elements>`_,

#.  `element valued iterative operators <https://documentation.aimms.com/language-reference/non-procedural-language-components/set-set-element-and-string-expressions/set-element-expressions.html#element-valued-iterative-expressions>`_,

#.  `set valued iterative operators <https://documentation.aimms.com/language-reference/non-procedural-language-components/set-set-element-and-string-expressions/set-expressions.html#iterative-set-operators>`_, and

#.  `indexed sets <https://documentation.aimms.com/language-reference/non-procedural-language-components/set-declaration/set-declaration-and-attributes.html#indexed-sets>`_.


Can these semantics be used in the two approaches to model composite objects?

The component based approach to support AIMMS set semantics for composite objects
----------------------------------------------------------------------------------- 

The component based approach is not sufficient to support the above mentioned functionalities.

The first three functionalities enumerated above result in single elements; so they cannot be used to select multiple components, whereby each component is an element in itself.
The last three functionalities enumerated above result in subsets of a single set of varying length ; so they cannot be used to select multiple components whereby the elements may come from different sets.

The reference based approach to support AIMMS set semantics for composite objects
----------------------------------------------------------------------------------- 

The component based approach is sufficient to support the above mentioned functionalities.

As each reference element is an element in an AIMMS set, and a reference element references a composite object; the reference based approach for composite objects can be used in conjunction with AIMMS set semantics.


.. code-block:: aimms
    :linenos:

    DeclarationSection Declarations_for_set_semantic_support_of_reference_based_approach {
        ElementParameter ep_arbitraryArc {
            Range: s_arcIds;
        }
        ElementParameter ep_maxFlowArc {
            Range: s_arcIds;
        }
        Set s_orderedArcsByFlow {
            SubsetOf: s_arcIds;
            OrderBy: user;
        }
        Set s_outgoingArcs {
            IndexDomain: i_node;
            SubsetOf: s_arcIds;
        }
    }


.. code-block:: aimms
    :linenos:

    Procedure MainExecution {
        Body: {
            ep_arbitraryArc := Element( s_arcIds, round( uniform(1, card( s_arcIds ) ) ) );
            
            ep_maxFlowArc := argMax( i_arc, sum( i_tp, v_flow2(i_tp, i_arc) ) );
            
            s_orderedArcsByFlow := NBest( i_arc, sum( i_tp, v_flow2(i_tp, i_arc) ), 4 );
            
            s_outgoingArcs( i_node ) := { i_arc | ep_arcNodeFrom( i_arc ) = i_node };
        }
    }


