Database Inspection
====================

.. meta::
   :keywords: Database, Datasource, GetDataSourceProperty, SQLNumberOfColumns, SQLNumberOfTables, SQLColumnData, SQLTableName, SQL Query, Mapping, Database Info
	:description: In this example you can see how several database functions can be used.

Direct download AIMMS Project :download:`Database Inspection.zip <model/Database Inspection.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Database%20Inspection

In this example you can see how the following database functions can be used:

* GetDataSourceProperty 
* SQLNumberOfColumns 
* SQLNumberOfTables
* SQLColumnData
* SQLTableName

In the procedure InspectDatabase in the Database Inspection Section, you can find example calls to these functions. Please note that the following database functions can be used in a similar way:

	- SQLNumberOfViews
	- SQLViewName
	
On the demo page you can inspect the database that comes with this example. You can also add or remove databases via the Add Database or Remove Database buttons. Your newly added databases will be inspected automatically.

After having inspected a database, you can e.g. read in data, based on the information you got from the inspection. In this example, this is done on the View Data page that can be accessed from the Demo Page. On the View Data page you can select a database and a table in that database. The number of entries available in the table will be displayed. If the Auto View check box is checked, the data of the selected table will automatically be read in. Note that this is only done when the number of rows does not exceed the maximum number of rows specified behind the Auto View checkbox. 

When the data is not read in automatically you can, based on the number or rows, decide 

	- to read in the data via the button with the green arrow, or 
	- to add a condition to the read statement first. 
	
A condition can be added by checking the 'where' checkbox and specifying the condition parameters. You can select one of the available comparing operators. Note that LIKE allows you to do a search based on a pattern rather than specifying exactly what is desired. '%' and '_' are the equivalents of the DOS wildcards '*' and '?' respectively.

If you want to learn how to read/write data from/to tables in a database, please see the Transport Model example.

Keywords:
Database, Datasource, GetDataSourceProperty, SQLNumberOfColumns, SQLNumberOfTables, SQLColumnData, SQLTableName, SQL Query, Mapping, Database Info

.. spelling:word-list::

    wildcards