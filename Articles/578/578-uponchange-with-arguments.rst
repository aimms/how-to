Uponchange with Arguments
=========================

This article illustrates how to use ``uponchange`` annotation with arguments. This can be benefitial  the project is on WebUI and block-editing is already availble on the current AIMMS version.   
Running a procedure after a change in data, being string parameter, parameter or element parameter, 

Note that this is a WebUI feature only. Changes on WinUI or directly on the AIMMS IDE will not trigger the ``uponchange`` procedure. 

Step 1
~~~~~~~~~

Create a procedure with the following input arguments. Set both identifiers' index domain to the same index domain of the identifier to be triggered. 
This case, a generic ``i_i``, ``i_j`` and ``i_k`` is used. 

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

Everytime that the user change

Step 2
~~~~~~~~~


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
- sp_whatHasChanged is used to clarify that only data selected will be changed. 

.. seealso::
    More documentation about `UponChange annotation <https://documentation.aimms.com/webui/widget-options.html#additional-identifier-properties>`_.

