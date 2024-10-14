Database Interface Generation
==============================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Database, Datasource, GetDataSourceProperty, SQLNumberOfColumns, SQLNumberOfTables, SQLColumnData, SQLTableName, Mapping, Database Info, Model Edit Functions
	:description: This example shows you how the Model Edit functions can be used to generate a link to one or more database tables for your projects.

Direct download AIMMS Project :download:`Database Interface Generation.zip <model/Database Interface Generation.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Database%20Interface%20Generation

This example shows you how the Model Edit functions can be used to generate a link to one or more database tables for your projects. Normally, this is quite a cumbersome process, since all tables and columns need corresponding AIMMS identifiers of the right types. Using the Model Edit functions, this process can now be done much faster using the GUI on the Demo Page. The whole functionality has been put into an AIMMS library, such that you can easily use it in your own models. To do so, copy both the folders below to your own project directory, and add both the Example Style library and the Database Interface Generation library to your model, using the Library Manager of AIMMS (available in the File menu).

	- _Example Style Library
	- Database Interface Generation
	
The example also offers you the possibility to export the generated interface as a ``.ams`` file. In that case, you can use this example to generate the database interface(s) for your own model(s).
	
Keywords:
Database, Datasource, GetDataSourceProperty, SQLNumberOfColumns, SQLNumberOfTables, SQLColumnData, SQLTableName, Mapping, Database Info, Model Edit Functions

.. meta::
   :keywords: Database, Datasource, GetDataSourceProperty, SQLNumberOfColumns, SQLNumberOfTables, SQLColumnData, SQLTableName, Mapping, Database Info, Model Edit Functions