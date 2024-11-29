Adapt Model when Linked Database Table is Modified
======================================================

.. meta::
   :description: This article shows an example of adapting your model when adding columns to a linked database table.
   :keywords: odbc, connect, link, database, db, table, adapt, column

AIMMS uses ODBC to connect to database tables. 
When database table columns are linked to AIMMS identifiers, adding columns to the table may require you to adapt the model.
In this article, we'll show an example of how to adapt for changes in the database table definition.

The Basic Model and Table
--------------------------

.. image:: images/basicDatabaseTable.PNG
    :align: center

|

The schema of our database is represented above.

.. image:: images/basicAimmsModel.PNG
    :align: center

|

The schema is captured by the AIMMS Database Table as shown above.

:download:`AIMMS project download <loadportdata - basic.zip>` 

The Added Column is a Derived Column
------------------------------------

Now let's add the column ``regionNM`` to the database table as a derived column. The database schema then looks as follows:

.. image:: images/derivedExtensionDatabaseTable.PNG
    :align: center

|

.. note:: For the sake of efficiency, AIMMS caches the knowledge of the table structure of the tables it is connected to.
          When these table structures are changed while AIMMS is still open, the caches need to be updated.  
          The easiest way to do this is to close the project and open it again.

The extended structure is then captured in the AIMMS model as follows:

.. image:: images/derivedExtensionAimmsModel.PNG
    :align: center

|

You can adapt the database table ``lpdata`` by using the wizard at the mapping and add the added link.
When the region data is not used in the model, you can even ignore this step.

:download:`AIMMS project download <loadportdata - ExtendedWithDerived.zip>` 


The Added Column is a Key Column
--------------------------------

This is more interesting. 
Following our running example, instead of making ``regionNM`` a derived column, 
it's made a key column as shown in the following schema:

.. image:: images/KeyExtensionDatabaseTable.PNG
    :align: center

|

There are two approaches to handling this in the AIMMS model:

Approach 1: the Model Selects Only Data of a Single Region
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: aimms
    :linenos:

    DatabaseTable db_lpdata {
        DataSource: "data\\lpdata.dsn";
        TableName: "lptable";
        Mapping:  "regionNM" --> s_Regions;
    }

The statement below will read all regions. 

.. code-block:: aimms

    read from table db_lpdata ; ! this will fill s_Regions      
   
Subsequently, we will select a single region. This can be done via a statement like:

.. code-block:: aimms

    ep_SelectedRegion := first( s_Regions ); ! Just select one region.

This is an alternative to selecting the region via the user interface. 

For the selected region, the data mapping becomes:

.. code-block:: aimms
    :linenos:

    DatabaseTable db_lpdataID {
        IndexDomain: i_reg;
        DataSource: "data\\lpdata.dsn";
        TableName: "lptable";
        Mapping: {
            "loadport" --> i_lpn,
            "lpsize"   --> p_lpsize1(i_lpn),
            "regionNM" --> i_reg
        }
    }
    
The actual read statement becomes:

.. code-block:: aimms

    read from table db_lpdataID(ep_SelectedRegion); ! Read in the data for the selected region.

Approach 2: the Model Aggregates the Data over All Regions
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The data mapping becomes:

.. code-block:: aimms
    :linenos:

    DatabaseTable db_lpdataAgg {
        DataSource: "data\\lpdata.dsn";
        TableName: "lptable";
        Mapping: {
            "loadport" --> i_lpn,
            "regionNM" --> i_reg,
            "lpsize2"   --> p_lpsize2(i_lpn,i_reg)
        }
    }

And the data is read and aggregated as follows:

.. code-block:: aimms

    read from table db_lpdataAgg ; ! this will fill  p_lpsize2

    p_lpsizeAgg(i_lpn) := sum( i_reg, p_lpsize2(i_lpn, i_reg) ); ! Aggregate over all regions.

:download:`AIMMS project download <loadportdata - ExtendedWithKey.zip>` 

.. seealso::

    * :doc:`../343/343-use-metadata-in-write-to-table`
    * :doc:`../539/539-which-odbc-drivers`
    * :doc:`../554/554-direct-sql-example`
    * `Databases and Data Connection free e-learning course <https://elearning.aimms.com/course/databases-data-connection>`_
    * `Employee Scheduling Example <https://how-to.aimms.com/Articles/387/387-employee-scheduling.html>`_