Transport Model
=================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Linear Programming, Network Object, Database Communication
   :description: The basic example illustrates how to formulate and solve a mathematical program in AIMMS.

Direct download AIMMS Project :download:`Transport Model.zip <model/Transport Model.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Application%20Examples/Transport%20Model

The basic example illustrates how to formulate and solve a mathematical program in AIMMS. It contains the declarations and procedures necessary to formulate and solve a simple transportation model.

The objective of the model is to find an optimal distribution of transports from a given set of depots to a given set of customers, such that:
- The demand of every customer is met.
- The amount supplied from each depot does not exceed the total amount available for supply.
- The total cost associated with the totality of transports is minimal.

For illustration this demo uses a dataset containing Dutch cities.

The database example illustrates how to read model data from and write model data to a database. Furthermore, it also illustrates how to use the database tracing options to get debug information about the queries AIMMS sends to the database.
To setup a database link, two actions are required in the model:
- declaring one or more database table objects in your model to set up the link between table columns in the database and identifiers in your model.
- adding READ and WRITE statements to the procedures which perform the data acquisition of your model.
In the declaration form of a database table object, you must provide the ODBC or OLE DB data source name of the database with which you want to connect, a table in that database, and (optionally) the mapping of column names to identifiers in your model. Wizards can help you complete the corresponding attribute fields appropriately. 
The demo Access database provided with the example contains the same data as the basic example.


The expressions example illustrates how to use expressions in the graphical user interface. 

Examples of expressions in this demo are
- The difference between the demand in a city and the supply from one depot, to show which cities are supplied by more than one depot.
- The cost of transporting from one depot to one city.

Open the properties dialog of the composite table to see how the expressions are specified.

The constraint slacks example illustrates how to formulate and solve a mathematical program in AIMMS using constraint slacks. 

Keywords:
Linear Programming, Network Object, Database Communication




