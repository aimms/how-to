Write Selected Database Tables
==============================

.. meta::
   :description: Use existing AIMMS function to selectivly write to database tables whose data has changed.
   :keywords: database table, odbc, runtime library, model editing, AIMMS Language, execution efficiency

.. sidebar:: Don't write on these tables. Thanks.

    .. figure:: /Resources/C_Language/Images/157/Billiard_hall.JPG
    
            Picture by: Dmitry G.

The operation of writing to databases in AIMMS is tuned for performance. 

In an application with several database table, it is not very efficient to write all database tables. Much more efficient is to skip writing to those tables where data has not changed.

To do so, you can use a combination of the function ``ReferencedIdentifiers``, the construct ``DatachangeMonitor``, and runtime libraries.

Example of database tables
--------------------------

Here is an example with two database tables. The mechanism works the same for any number of database tables.

.. code-block:: aimms
   :linenos:

   DeclarationSection Database_table_declarations {
      DatabaseTable db_ab {
         DataSource: "data\\abc.dsn";
         TableName: "TableAB";
         Mapping: {
               "NamesA" -->i_a,
               "NamesB" -->i_b,
               "Vals1"  -->p_AB1( i_a, i_b ),
               "vals2"  -->p_AB2( i_a, i_b )
         }
      }
      DatabaseTable db_bc {
         DataSource: "data\\abc.dsn";
         TableName: "TableBC";
         Mapping: {
               "NamesB" -->i_b,
               "NamesC" -->i_c,
               "Vals3"  -->p_BC1( i_b, i_c )
         }
      }
   }

.. sidebar:: ReferencedIdentifiers

   .. code-block:: aimms
      :linenos:

      ReferencedIdentifiers(
            searchIdentSet ! (input) subset of AllIdentifiers
            searchAttrSet  ! (input) subset of AllAttributeNames
            recursive )    ! (optional) numerical expression

      * Line 2: the identifiers to be searched; here, a singleton set containing just the database table to be investigated

      * Line 3: the attributes to be searched; here, we search all attributes

      * Line 4: We will do a recursive search, because we also want to write defined parameters when the data of one of the constituents of its definition is changed.

* Writing to table db_ab saves the data of i_a, i_b, p_AB1, p_AB2. In other words, when the data of i_a, i_b, p_AB1, or p_AB2 is changed, we want to write to table db_ab.

* Writing to table db_bc saves the data of i_b, i_c, p_BC1. Again, when the data of i_b, i_c, p_BC1 is changed, we want to write to table db_bc.

This was the original procedure to write the data:

.. code-block:: aimms
   :linenos:

    Procedure pr_OriginalDatabaseWriteProcedure {
      Body: {
               write to table db_ab;
               write to table db_bc;
      }
   }

However, we want to change it to something like this (in pseudo code):

.. sidebar:: DatachangeMonitors

    Datachange monitors track whether data of a selection of identifiers was changed since the last time checked.

    A datachange monitor consists of three components:

    #. A name - for sake of identification.

    #. A reference to an AIMMS set - by having a reference, a data change monitor can even monitor dynamic subsets of ``AllIdentifiers``.
   
    #. An internal component that maintains for each identifier and the referenced set the number of assignments since the last reset.

    The AIMMS function reference describes the procedures operating on datachange monitors in detail:
   
    * ``DataChangeMonitorHasChanged`` - returns 1 if the data of at least one identifier, or the data of the reference set itself, has changed.

    * ``DataChangeMonitorCreate`` - creates a new datachange monitor name and resets

    * ``DataChangeMonitorReset`` - resets a datachange monitor and links it to the same or another reference set

    * ``DataChangeMonitorDelete`` - allows for cleanup

.. code-block:: none
   :linenos:

    Procedure pr_TargetDatabaseWriteProcedure {
        Body: {
            if a set or parameter referenced in db_ab is changed then
                write to table db_ab;
            endif ;

            if a set or parameter referenced in db_bc is changed then
                write to table db_bc;
            endif ;
        }
    }

To avoid coding errors and maintenance issues from doing this manually, AIMMS has the following facilities:

* The predeclared function ``ReferencedIdentifiers`` (see sidebar) examines portions of AIMMS code and returns the identifiers referenced. 

* The construct ``DatachangeMonitor`` (see sidebar) checks a given set of AIMMS identifiers for changed values.

* Runtime libraries, that is AIMMS code generated in the model that can be activated in the same session. 
   Each database table is monitored separately, so you need a separate monitor for each table. 
   We need runtime libraries because each monitor has a reference to a set, not the value of a set. 
   
By automating the use of ``ReferencedIdentifiers`` and ``DatachangeMonitors`` we avoid maintenance problems.

Example of runtime library 
----------------------------

Code writing runtime libraries are a bit abstract.

Before trying to understand a procedure that creates a runtime library, let's take an example of code created by such a procedure:

.. code-block:: aimms
   :linenos:

    LibraryModule RuntimeLibraryDatachangeMonitorsForDatabaseTablesToBeWritten {
        Prefix: rldmfdttbw;
        DeclarationSection Datachange_monitor_names_and_sets {
            Set MonitorSet_db_ab {
                SubsetOf: AllIdentifiers;
                Definition: data { s_A, s_B, p_AB1, p_AB2 };
            }
            Set MonitorSet_db_bc {
                SubsetOf: AllIdentifiers;
                Definition: data { s_B, s_C, p_BC1 };
            }
        }
        Procedure pr_InitDatachangeMonitors {
            Body: {
                DataChangeMonitorCreate("DatachangeMonitor_db_ab",MonitorSet_db_ab,1);
                DataChangeMonitorCreate("DatachangeMonitor_db_bc",MonitorSet_db_bc,1);
            }
        }
        Procedure pr_WriteTablesWhenDataChanged {
            Body: {
                if DataChangeMonitorHasChanged("DatachangeMonitor_db_ab") then
                          write to table db_ab;
                          p01_dbWritten('db_ab') := 1;
                          DataChangeMonitorReset( "DatachangeMonitor_db_ab", MonitorSet_db_ab );
                endif;
                
                if DataChangeMonitorHasChanged("DatachangeMonitor_db_bc") then
                          write to table db_bc;
                          p01_dbWritten('db_bc') := 1;
                          DataChangeMonitorReset( "DatachangeMonitor_db_bc", MonitorSet_db_bc );
                endif;
            }
        }
    }
    
An explanation of the contents for the database table ``db_ab`` follows below. In addition, the library shows how the repetition is done for subsequent tables such as ``db_bc``.

* line 1: The name of the runtime library. Here, unique and descriptive of the purpose in Camel Case.
    
* line 2: The prefix. Here, acronym of the runtime library name in lower case.

* line 6: The sets and parameters referenced in the first database table, constructed using the function ``ReferencedIdentifiers``.

* line 4-7: A set declaration and definition for the identifiers referenced in the first table. 

* line 15: Create a datachange monitor for table ``db_ab`` using the set ``MonitorSet_db_ab``.

* line 21: Check if data is changed for table ``db_ab``.

* line 22: Perform the actual write action.

* line 23: Mark the table as written.

* line 24: Reset the data change monitor.


Create the runtime library 
-----------------------------------

.. code-block:: aimms
   :linenos:

   Procedure pr_CreateAndInitializeRuntimeLibraryForTableWriteManagement {
      Body: {
         ! Initialize writing the library.
         sp_runtimePrefix := "rldmfdttbw" ;
         if ep_runtimeLib then
               me::Delete( ep_runtimeLib );
         endif ;
            ep_runtimeLib := me::CreateLibrary( "RuntimeLibraryDatachangeMonitorsForDatabaseTablesToBeWritten", sp_runtimePrefix);
         ep_runtimeDecl := me::Create("Datachange monitor names and sets",'declaration', ep_runtimeLib);
         
         ! The set of database tables that are to be managed via data change monitors
         s_SelectedDatabaseTables := AllDatabaseTables * Database_table_declarations ;
         
         ! initialize the text for the runtime procedures
         sp_bodyInitProc := "" ;
         sp_bodyWriteProc := "" ;
         
         for i_db do ! for each database table to be handled
         
               ! Initialization per table.
               sp_bodyLineWrite := "" ;
               sp_bodyLineInit := "" ;
         
               ! Determine the collection of identifiers referenced in the database table.
               s_singleTon := i_db ;
               s_RefdIds := ReferencedIdentifiers(
                     searchIdentSet :  s_singleTon, 
                     searchAttrSet  :  AllAttributeNames, 
                     recursive      :  1) 
                        * ( AllVariables + AllParameters + AllSets );
               s_ReferencedIdentifiersByDatabaseTables(i_db) := s_RefdIds ;
         
               ! Create the data change monitor code
               sp_set := "data " + s_RefdIds ;
                ep_datachangeMonitorSet(i_db):= me::Create(FormatString("MonitorSet_%e", i_db), 'set', ep_runtimeDecl);
                me::SetAttribute(ep_datachangeMonitorSet(i_db),'subset of', "AllIdentifiers");
                me::SetAttribute(ep_datachangeMonitorSet(i_db),'definition', sp_set);
               sp_datachangeMonitorName( i_db ) := formatString( "DatachangeMonitor_%e", i_db );
         
               ! To initialize a data change monitor, we'll only have to call DataChangeMonitorCreate.
               sp_bodyLineInit  := formatString("DataChangeMonitorCreate(\"DatachangeMonitor_%e\",MonitorSet_%e,1);\n",i_db,i_db);
               sp_bodyInitProc += sp_bodyLineInit ;
         
               ! To write to a database table, but only when data is changed, we need to 
               ! 1) check DataChangeMonitorHasChanged, 
               ! 2) actually write,
               ! 3) register the writing, and 
               ! 4) Reset the data change monitor.
               sp_bodyLineWrite := formatString("if DataChangeMonitorHasChanged(\"DatachangeMonitor_%e\") then\n",i_db);
               sp_bodyLineWrite += formatString("          write to table %e;\n",i_db); ;
               sp_bodyLineWrite += formatString("          p01_dbWritten(\'%e\') := 1;\n",i_db); ;
               sp_bodyLineWrite += formatString("          DataChangeMonitorReset( \"DatachangeMonitor_%e\", MonitorSet_%e );\n",i_db,i_db) ;
               sp_bodyLineWrite += formatString("endif;\n\n") ;
               sp_bodyWriteProc += sp_bodyLineWrite ;
         
         endfor ;
         
            ep_InitProc := me::Create( "pr_InitDatachangeMonitors", 'procedure', ep_runtimeLib);
         me::SetAttribute( ep_InitProc, 'body', sp_bodyInitProc);
         
         ep_WriteProc := me::Create( "pr_WriteTablesWhenDataChanged", 'procedure', ep_runtimeLib);
         me::SetAttribute( ep_WriteProc, 'body', sp_bodyWriteProc);
         
         me::Compile(ep_runtimeLib);
         apply(ep_InitProc);
      }
      Set s_Singleton {
         SubsetOf: AllIdentifiers;
      }
      Set s_RefdIds {
         SubsetOf: AllIdentifiers;
      }
   }
    
Notes:

* ``sp_bodyLineInit``, ``sp_bodyInitProc`` we collect the text for the datachange monitor initialization procedure.

* ``sp_bodyLineWrite``, ``sp_bodyWriteProc`` we collect the text for the write procedure, as illustrated in the previous section.

Call to write the database tables
-------------------------------------

.. code-block:: aimms
   :linenos:

   Procedure pr_SaveModifiedDatabaseTables {
      Body: {
         p01_dbWritten(i_db) := 0 ;
         apply( ep_writeProc);
      }
      Comment: "Write the database tables by calling a runtime created procedure.";
   }

Essentially just an apply statement of the procedure we created above.

Example project
-----------------

Download the attached project for an example.

*  :download:`AIMMS project </Resources/C_Language/Images/157/WriteOnlyAFewDatabaseTables.zip>` 


Related Topics
----------------

* `AIMMS The Language Reference <https://documentation.aimms.com/_downloads/AIMMS_ref.pdf>`_: Section "Runtime Libraries and the Model Edit Functions"

* `AIMMS The Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_: 

   * Chapter "Data Change Monitor Functions"   
   
   * Function "ReferencedIdentifiers"



.. include:: /includes/form.def






















