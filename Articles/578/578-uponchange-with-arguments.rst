Uponchange with Arguments
=========================

This article illustrates how to use ``uponchange`` annotation with arguments. This can be beneficial when there are block-editing and consistency check per value. 

Note that this is a WebUI feature only. Changes on WinUI or directly on the AIMMS IDE will not trigger the ``uponchange`` procedure.

Step 1
~~~~~~~~~

Create a procedure with the following input arguments. Set both identifiers' index domain to the same index domain of the identifier to be triggered. 
This case, a generic ``i_i``, ``i_j`` and ``i_k`` is used. Everytime that the user change one or multiple values, this procedure will be triggered.

.. code-block:: aimms

    Procedure pr_triggersOnUponChange {
        Arguments: (bp_in_hasChanged,p_in_oldValue);
        Parameter bp_in_hasChanged {
            IndexDomain: (i_i,i_j,i_k);
            Range: binary;
            Property: Input;
        }
        Parameter p_in_oldValue {
            IndexDomain: (i_i,i_j,i_k);
            Property: Input;
        }
    }

Remarks:

- ``p_in_oldValue`` is a copy of the triggered identifier, so if the triggered one is a parameter, this will be also, same for other types of identifier.
- ``bp_in_hasChanged`` is a binary argument that will return which values where changed.
- Both arguments can be names as you wish, but always the first will return if it was changed and the second the old value of the identifier.


Step 2
~~~~~~~~~

When adding arguments to the ``uponchange`` procedure, consistency checks are possible, but you can also only let the customer knows what was just changed. 

.. code-block:: aimms
    :linenos:

    empty sp_whatHasChanged;

    sp_whatHasChanged := FormatString("Number of values changed: %n.\n", card(bp_in_hasChanged));

    for ((i_i,i_j,i_k) | bp_in_hasChanged(i_i,i_j,i_k)) do
        sp_whatHasChanged 
        +=  FormatString("p_anyData(%e,%e,%e) changed from %n to %n.\n",
            i_i,
            i_j,
            i_k,
            p_in_oldValue(i_i, i_j, i_k),
            p_anyData(i_i, i_j, i_k));
    endfor;

Remarks:

- ``sp_whatHasChanged`` is used to clarify that only data selected was changed. 

.. seealso::
    
    * `UponChange annotation <https://documentation.aimms.com/webui/widget-options.html#additional-identifier-properties>`_.

