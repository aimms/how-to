Create a static AIMMS Library from a runtime library
=====================================================

As you know, runtime libraries offer enormous flexibility.
However, there are situations whereby the same runtime library is generated over and over again.
In these situations it is more efficient to generate the runtime library once and persist it.
The persisting only needs to rerun, when there is a change in the input for the runtime library.

This article provides an example of generating and persisting such a library on the one hand, and 
using it in another application.

The Story
-------------

In the database, occasionally table columns are added/removed/changed (type) to tables, or even tables are added/removed.
To facilitate easy reading of these tables, identifiers are created as follows:

* For a column that is a primary key, 

  * a set is created using the same name, but prefixed with ``s_``
  
  * an index is created using the same name, but prefixed with ``i_``

* For a column that is not a primary key,

  * a parameter is created using the same name, but prefixed with

    * ``p_`` if it is a numerical column, and

    * ``sp_`` otherwise.

  * In addition, the index domain consists of the indices made up from the primary key.

A module is created for every table, in order to avoid name clashes.

Example database table
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Insert image here.

Example generated code
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Insert AIMMS code here.

The runtime library
--------------------

Generating the runtime library uses:

* `SQLNumberOfTables <https://documentation.aimms.com/functionreference/data-management/database-functions/sqlnumberoftables.html>`_ and 
`SQLTableName <https://documentation.aimms.com/functionreference/data-management/database-functions/sqltablename.html>`_ to obtain the collection of tables.

* `SQLNumberOfColumns <https://documentation.aimms.com/functionreference/data-management/database-functions/sqlnumberofcolumns.html>`_ and `SQLColumnData <https://documentation.aimms.com/functionreference/data-management/database-functions/sqlcolumndata.html>`_ to obtain the `column characteristics <https://documentation.aimms.com/functionreference/predefined-identifiers/language-related-identifiers/alldatacolumncharacteristics.html>`_: name, data type, and whether it is a primary key.

Persisting the library
--------------------------


Using the library
-------------------

