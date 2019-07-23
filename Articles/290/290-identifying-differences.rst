Identifying differences
=======================

Recently I helped a customer to identify the cause of data differences in his model after a refactorization. 
I used code like the below:

.. code-block:: aimms
    :linenos:

    Procedure pr_TraceValues {
        Arguments: (s_someIds,sp_outputFoldername);
        Body: {
            block where
                single_column_display := 1, ! Each element on a single line; it is easy to identify single element differences.
                listing_number_precision := 6 ; ! Increase this if you want to identify potential numerical causes.
            
                ! Ensure the output folder exists.
                if not DirectoryExists( sp_outputFolderName ) then
                    DirectoryCreate( sp_outputFolderName );
                endif ;
            
                ! Write each identifier to a separate output file.
                !
                ! To do so, we need to put each identifier in consecutively in a singleton set
                ! and then we can use the write statement.
                !
                ! :: cannot be part of a filename, so replace it with __.
                for i_sid do
            
                    ! Fill the single ton set with the identifier at hand.
                    s_oneId := {};
                    s_oneId := i_sid ;
            
                    ! Construct the name of the output file.
                    sp_outputBase := formatString("%e",i_sid);
                    sp_outputBase := FindReplaceStrings( sp_outputBase, ":", "_" );
                    sp_outputFilename := formatString("%s\\%s", sp_outputFolderName, sp_outputBase);
            
                    ! Actually write the identifier
                    write s_oneId to file sp_outputFilename ;
            
                endfor ;
            
            endblock ;
        }
        Set s_someIds {
            SubsetOf: AllIdentifiers;
            Index: i_sid;
            Property: Input;
        }
        Set s_oneId {
            SubsetOf: AllIdentifiers;
        }
        StringParameter sp_outputFoldername {
            Property: Input;
        }
        StringParameter sp_outputBase;
        StringParameter sp_outputFilename;
    }

A sample calls of this procedure is:

.. code-block:: aimms
    :linenos:

    Procedure pr_ExampleTraceValues {
        Body: {
            s_outputIds := AllUpdatableIdentifiers * ( mod1 + mod2 );
            pr_TraceValues( s_outputIds, "myModuleData" );
        }
        Set s_outputIds {
            SubsetOf: AllIdentifiers;
        }
    }
    
During the support session, I used such a call on both versions of the project and then compared the output folders using `WinMerge <winmerge.org>`_ . This utility quickly identifies which files are different, and for two differing files, what are the differences between those files.

When definitions of sets and parameters haven't changed, I just output a subset of ``AllUpdatableIdentifiers``; these are the sets and parameters without definition.

When I'm interested in the sets and parameters related to a particular mathematical program, such as the one below:

.. code-block:: aimms
    :linenos:

    Module Mod3 {
        Prefix: m3;
        Set s_myVars {
            SubsetOf: AllVariables;
            Definition: AllVariables * Mod3;
        }
        Set s_myCons {
            SubsetOf: AllConstraints;
            Definition: AllConstraints * Mod3;
        }
        Variable v_obj {
            Range: free;
        }
        MathematicalProgram mp_Mine {
            Objective: v_obj;
            Direction: minimize;
            Constraints: s_myCons;
            Variables: s_myVars;
            Type: Automatic;
        }
    }
    
Then I can use the following to output all identifiers making up the mathematical program like this:  

.. code-block:: aimms
    :linenos:

    s_outputIds := data { v_obj } + m3::s_myVars + m3::s_myCons ;
    s_outputIds += ReferencedIdentifiers( s_outputIds, AllAttributeNames, 1 );
    pr_TraceValues( s_outputIds, "myMPData" );

Note that in the above code, I include the variables because the bound information is essential to the mathematical program.  
Note that I also include the constraints; this may be obsolete unless you are interested in the shadow prices as well.

The section that contains this procedure and sample can be downloaded from :download:`AIMMS section download <download/TracingValues.zip>` 

How to add such a section to your model can be found :doc:`here<../145/145-import-export-section>`  

