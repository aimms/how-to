How to Write a Few Database Tables
=====================================

.. meta::
   :description: The combo of ReferencedIdentifiers, Datachange monitors and runtime libraries allows to make a selection of database tables that actually need rewriting
   :keywords: database table, odbc, runtime library, model editing, AIMMS Language, execution efficiency

.. .. sidebar:: Don't write on these tables. Thanks.

..     .. figure:: ../Resources/C_Language/Images/157/Billiard_hall.JPG
    
..             Author: Dmitry G.

.. .. figure:: ../Resources/c_Language/Images/157/Billiard_hall.JPG
..    :scale: 75 %

<<<<<<< HEAD
..    Don't write on these tables, thanks. 

Writing to databases is tuned for performance.  Even when writing just a few rows, there is some time needed to setup a connection, actually write and commit the transaction to the database. Applications keep acquiring functionality, and subsequently the number of database tables in large applications becomes significant. Having worked on an application with more than 100 database tables, where saving the data often consisted of writing the same data for most of the tables, we sought a solution whereby the writing of database tables is skipped for those tables where the data is not changed. 
=======

Writing to databases is tuned for performance. 
Even when writing just a few rows, there is some time needed to setup a connection, actually write and commit the transaction to the database. 
Applications keep acquiring functionality, and subsequently the number of database tables in large applications becomes significant. 
Having worked on an application with more than 100 database tables, where saving the data often consisted of writing the same data for most of the tables, we sought a solution whereby the writing of database tables is skipped for those tables where the data is not changed. 
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393

Example
----------

To illustrate the mechanism, we created a small example with just two database tables. The mechanism works the same for applications with dozens of database tables. The example project can be downloaded :download:`from here <../Resources/C_Language/Images/157/WriteOnlyAFewDatabaseTables.zip>` 

.. code-block:: aimms
<<<<<<< HEAD
=======
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
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393

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

<<<<<<< HEAD

The original procedure that writes the data looks like this:

.. code-block:: aimms

   Procedure pr_OriginalDatabaseWriteProcedure {
      Body: {
         write to table db_ab;
         write to table db_bc;
      }
   }

* ``write to table db_ab`` saves the data of i_a, i_b, p_AB1, p_AB2. In other words, when the data of i_a, i_b, p_AB1, or p_AB2 is changed, we want to write to table db_ab.
=======
   .. code-block:: aimms
      :linenos:

      ReferencedIdentifiers(
            searchIdentSet ! (input) subset of AllIdentifiers
            searchAttrSet  ! (input) subset of AllAttributeNames
            recursive )    ! (optional) numerical expression

      * Line 2: the identifiers to be searched, in our example this will be a singleton set containing just the database table to be investigated

      * Line 3: the attributes to be searched, we search all attributes

      * Line 4: We will do a recursive search, because we also want to write defined parameters, when the data of one of the constituents of its definition is changed.
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393

* ``write to table db_bc`` saves the data of i_b, i_c, p_BC1. Again, when the data of i_b, i_c, p_BC1 is changed, we want to write to table db_bc.

Writing to Databases More Efficiently
------------------------------------------

To only write those tables whose data has been changed, we want to update the write procedure to something like (in pseudo code):

.. code-block:: aimms
<<<<<<< HEAD

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

However, we do not want to do this change manually because:
=======
   :linenos:

    Procedure pr_OriginalDatabaseWriteProcedure {
      Body: {
               write to table db_ab;
               write to table db_bc;
      }
   }
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393

#. Cut / Copy / Paste typically leads to coding errors.

#. It is a maintenance problem; if your colleague adds a column and corresponding AIMMS identifier to the database table, would he/she remember to make the corresponding update in the procedure that writes the data?

To avoid these problems, AIMMS has the below features:

#. ``ReferencedIdentifiers``

   This predeclared examines portions of AIMMS code and returns the identifiers referenced. 

#. Data Change Monitors
   
   Given a set of AIMMS identifiers, has any one of them changed value?

<<<<<<< HEAD
#. Runtime libraries
   
   AIMMS code generated in the model that can be activated in the same session. Each database table is monitored separately, which means we want a separate monitor for each table. Because each monitor has a reference to a set, instead of just the value of a set, we need to resort to runtime libraries. By automating the use of ReferencedIdentifiers and data change monitors, we avoid the maintenance problem mentioned above.

ReferencedIdentifiers
"""""""""""""""""""""""""

The syntax of ``ReferencedIdentifiers`` is as below. Read more in the `AIMMS Function Reference <http://images.aimms.com/aimms/download/manuals/aimms3fr_modelquery.pdf>`_

.. code-block:: aimms
   :linenos:
=======
    #. A reference to an AIMMS set - by having a reference, a data change monitor can even monitor dynamic subsets of AllIdentifiers (yes there are use cases of this feature).
   
    #. An internal component that maintains for each identifier and the referenced set the number of assignments since the last reset.

    The AIMMS function reference describes the procedures operating on datachange monitors in detail. In short:
   
    * ``DataChangeMonitorHasChanged`` - returns 1 if the data of at least one identifier, or the data of the reference set itself, has changed.
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393

   ReferencedIdentifiers(
      searchIdentSet ! (input) subset of AllIdentifiers
      searchAttrSet  ! (input) subset of AllAttributeNames
      recursive )    ! (optional) numerical expression

#. Line 2: the identifiers to be searched, in our example this will be a singleton set containing just the database table to be investigated

#. Line 3: the attributes to be searched, we search all attributes

<<<<<<< HEAD
#. Line 4: We will do a recursive search, because we also want to write defined parameters, when the data of one of the constituents of its definition is changed.

Data Change Monitors
"""""""""""""""""""""""

So how do we know the model or user changed data that is to be saved to a database table?

Data change monitors track whether or not the data of a selection of identifiers was changed since the last time checked. So what is a data change monitor?
=======
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
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393

A data change monitor consists of three components:

#. A name - for sake of identification.

#. A reference to an AIMMS set - by having a reference, a data change monitor can even monitor dynamic subsets of AllIdentifiers (yes there are use cases of this feature).

#. An internal component that maintains for each identifier and the referenced set the number of assignments since the last reset.

The `AIMMS Function Reference <http://images.aimms.com/aimms/download/manuals/aimms3fr_datamonitor.pdf>`_ describes the procedures operating on data change monitors in detail. In short:

* ``DataChangeMonitorHasChanged`` - returns 1 if the data of at least one identifier, or the data of the reference set itself, has changed.

* ``DataChangeMonitorCreate`` - create a new data change monitor name and resets

* ``DataChangeMonitorReset`` - reset a data change monitor and links it to the same or other reference set

* ``DataChangeMonitorDelete`` - allows for cleanup

The Runtime Library Created
------------------------------

Because of its abstraction, understanding the code writing runtime libraries is not easy to understand. It helps us to first have an example of code created by such a procedure, before trying to understand a procedure that creates a runtime library. So here is the runtime library created for our example:

.. code-block:: aimms
   :linenos:

    LibraryModule RuntimeLibrarydata change monitorsForDatabaseTablesToBeWritten {
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
        Procedure pr_Initdata change monitors {
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
    
An explanation of the contents for database table db_ab follows below, in addition, the library shows how the repetition is done for subsequent tables such as db_bc.

* line 1: The name of the runtime library, usually something long to be unique and descriptive of the purpose. Using camelCase is recommended. 
    
* line 2: The prefix, usually capital letters of the runtime library name (in camelCase) in lower case.

* line 6: The sets and parameters referenced in the first database table, constructed using the function ``ReferencedIdentifiers``.

* line 4-7: A set declaration and definition for the identifiers referenced in the first table. 

* line 15: A data change monitor is created for table ``db_ab`` using the set ``MonitorSet_db_ab``.

* line 21: Check if data is changed for table ``db_ab``.

* line 22: Perform the actual write action.

* line 23: Only for illustrative purposes of this example we mark the table as written.

* line 24: Reset the data change monitor.

So how do we create a runtime library as above?


Creating the Runtime Library
-----------------------------------

.. code-block:: aimms
<<<<<<< HEAD
=======
   :linenos:
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393

   Procedure pr_CreateAndInitializeRuntimeLibraryForTableWriteManagement {
      Body: {
         ! Initialize writing the library.
         sp_runtimePrefix := "rldmfdttbw" ;
         if ep_runtimeLib then
               me::Delete( ep_runtimeLib );
         endif ;
<<<<<<< HEAD
         ep_runtimeLib := me::CreateLibrary( "RuntimeLibrarydata change monitorsForDatabaseTablesToBeWritten", sp_runtimePrefix);
=======
            ep_runtimeLib := me::CreateLibrary( "RuntimeLibraryDatachangeMonitorsForDatabaseTablesToBeWritten", sp_runtimePrefix);
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393
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
<<<<<<< HEAD
               ep_data change monitorset(i_db):= me::Create(FormatString("MonitorSet_%e", i_db), 'set', ep_runtimeDecl);
               me::SetAttribute(ep_data change monitorset(i_db),'subset of', "AllIdentifiers");
               me::SetAttribute(ep_data change monitorset(i_db),'definition', sp_set);
=======
                ep_datachangeMonitorSet(i_db):= me::Create(FormatString("MonitorSet_%e", i_db), 'set', ep_runtimeDecl);
                me::SetAttribute(ep_datachangeMonitorSet(i_db),'subset of', "AllIdentifiers");
                me::SetAttribute(ep_datachangeMonitorSet(i_db),'definition', sp_set);
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393
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
         
<<<<<<< HEAD
         ep_InitProc := me::Create( "pr_Initdata change monitors", 'procedure', ep_runtimeLib);
=======
            ep_InitProc := me::Create( "pr_InitDatachangeMonitors", 'procedure', ep_runtimeLib);
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393
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
    
Most of the components of this long procedure have been explained in the previous section.

* ``sp_bodyLineInit``, ``sp_bodyInitProc`` collect the text for the data change monitor initialization procedure.

* ``sp_bodyLineWrite``, ``sp_bodyWriteProc`` collect the text for the write procedure, as illustrated in the previous section.

Writing to the Database Tables
-------------------------------------

.. code-block:: aimms
<<<<<<< HEAD
=======
   :linenos:
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393

   Procedure pr_SaveModifiedDatabaseTables {
      Body: {
         p01_dbWritten(i_db) := 0 ;
         apply( ep_writeProc);
      }
      Comment: "Write the database tables by calling a runtime created procedure.";
   }
<<<<<<< HEAD
=======

Essentially just an apply statement of the procedure we created above.

Resource for this article
-------------------------

The enclosed example shows how to do this.
>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393

Essentially just an ``apply`` statement of the procedure we created above.


Summary and Further Reading
------------------------------

The combination of ``ReferencedIdentifiers``, data change monitors and runtime libraries allows to make a selection of database tables that actually need rewriting.

* `AIMMS The Language Reference <https://documentation.aimms.com/_downloads/AIMMS_ref.pdf>`_: Section "Runtime Libraries and the Model Edit Functions"

* `AIMMS The Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_: 

   * Chapter "Data Change Monitor Functions"   
   
   * Function "ReferencedIdentifiers"
<<<<<<< HEAD
=======



.. include:: ../includes/form.def




















>>>>>>> 7034a76f2d4ddbbd9f20d63942992269cb0a4393


.. include:: ../includes/form.def