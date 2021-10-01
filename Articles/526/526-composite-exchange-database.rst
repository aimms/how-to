Composite objects in a mathematical programming model exchanged with a database
================================================================================

This is a companion article to :doc:`Identifying Composite Objects in Mathematical Programming Modeling <../526/526-modeling-composite-objects>`.

The two modeling approaches in that article lead to different structuring of the data.
As ODBC databases are frequently used to exchange data with mathematical programming applications,
it is worthwhile to check how to exchange data using the two modeling approaches for composite objects.

Data table declaration in SQLite
---------------------------------

.. code-block:: sql
    :linenos:

    CREATE TABLE "ArcsComponentBased" (
        "arcId" TEXT NOT NULL,
        "from"  TEXT NOT NULL,
        "to"    TEXT NOT NULL,
        "flow unit cost"    REAL,
        PRIMARY KEY("arcId")
    )

As this table is about data of arcs, and arcs are considered to be identifiable objects, the practice is followed 
to have a single column as primary key for this table.


Database writing in the component based approach
------------------------------------------------------

To accomodate the column ``arcId`` in the datase table, an artificial element is created for each valid combination of ``(i_nodeFrom, i_nodeTo)`` and stored in the element parameter ``ep_backRef(i_nodeFrom, i_nodeTo)``. 
With this element parameter, writing to a database table in AIMMS is straightforward.
First the datatable is declared in AIMMS as follows:

.. code-block:: aimms
    :linenos:

    DatabaseTable db_arcs1 {
        DataSource: sp_connectionString;
        TableName: "ArcsComponentBased";
        Mapping: {
            "from"           -->i_nodeFrom,
            "to"             -->i_nodeTo,
            "flow unit cost" -->p_cost1( i_nodeFrom, i_nodeTo ),
            "arcId"          -->ep_backRef(i_nodeFrom, i_nodeTo)
        }
    }

And then the writing to that table is done using the following code:

.. code-block:: aimms
    :linenos:

    Procedure pr_writeComponentBasedToDatabase {
        Body: {
            write to table db_arcs1 ;
        }
    }


Database writing in the reference based approach
------------------------------------------------------

As the reference based approach is closely linked to database design for objects, there is no need to create additional identifiers.

.. code-block:: aimms
    :linenos:

    DatabaseTable db_arcs2 {
        DataSource: sp_connectionString;
        TableName: "ArcsReferenceBased";
        Mapping: {
            "arcId"          -->i_arc,
            "from"           -->ep_arcNodeFrom( i_arc ),
            "to"             -->ep_arcNodeTo( i_arc ),
            "flow unit cost" -->p_cost2( i_arc )
        }
    }

.. code-block:: aimms
    :linenos:

    Procedure pr_writeReferenceBasedToDatabase {
        Body: {
            write to table db_arcs2 ;
        }
    }

Summary
-----------

Exchanging data with databases is pretty straightforward in both the component and reference based approach to handle composite objects.