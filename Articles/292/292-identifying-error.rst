Identify Error
===================

.. meta::
   :description: A trick for how to troubleshoot errors occurring during definition evaluation.
   :keywords: case, error, definition

When creating a case file, for instance during a ``pro::DelegateToServer``, errors may occur during the evaluation of the definitions.
Here we show a little trick to identify the set or parameter that has the definition in which this error occurred.

.. code-block:: aimms
    :linenos:

    Procedure pr_CheckDefinitions {
        Arguments: (s_TestedIds);
        Body: {
            for  i_ti | ( ( i_ti in AllDefinedSets ) or (i_ti in AllDefinedParameters ) ) do
                block
                    s_OneId := {} ;
                    s_OneId += i_ti ;
                    update s_OneId ;
                onerror ep_err do
                    sp_msg := FormatString("Definition evaluation error of %e with message: \"%s\"",
                        i_ti, errh::Message(ep_err));
                    errh::Adapt(ep_err,message:sp_msg);
                endblock ;
            endfor ;
        }
        StringParameter sp_msg;
        Set s_OneId {
            SubsetOf: AllIdentifiers;
        }
        ElementParameter ep_err {
            Range: errh::PendingErrors;
        }
        Set s_TestedIds {
            SubsetOf: AllIdentifiers;
            Index: i_ti;
            Property: Input;
        }
    }

Remarks about the above code:

* line 6,7: the singleton set is constructed.

* line 8: the definition is evaluated.

* line 10-12: the message is adapted such that the identifier at hand is included in the message.

A sample call is below:

.. code-block:: aimms
    :linenos:

    util::pr_CheckDefinitions(pro::ManagedSessionInputCaseIdentifierSet);
    
Here we check all definitions of the sets and parameters that are passed in input case to the solver session.
Note that by default in modern versions of AIMMS and of AIMMS PRO most if not all parameter definition are avoided anyway.

Example download
------------------
You can download the section (``.ams`` file) that contains this procedure and sample below: :download:`CheckDefinitions.zip <download/CheckDefinitions.zip>` 


To use it, you'll need to :doc:`Import a section to your project <../145/145-import-export-section>`.  