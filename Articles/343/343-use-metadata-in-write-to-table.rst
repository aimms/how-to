Write to a Database Efficiently
=====================================

.. meta::
    :description: Methods of writing data to a database and schema.
    :keywords: database, db, write, efficient, performance, foreign key, schema

This article explores a heuristic for writing to databases safely and efficiently.

When AIMMS writes data to a database table, it uses structural information of the database table to determine a safe and efficient strategy for writing.

In this article we discuss:

*  An example project

*  Foreign key relations

*  Available strategies

*  Database schema design best practices


Example Project Description
------------------------------------

We are maintaining orders to be delivered.  Each order is identified by (the key columns):

*   A customer

*   A product

*   A delivery date

In addition, we maintain the following information:

*   For each customer, its address and name

*   For each product, its name, its color, package size (height, width, depth).

This is represented in the following database schema.

.. image:: images/343-database-schema.png
    :align: center

As you can see from this schema, both a customer and a product have an ``Id``.  The table ``Orders`` refers to these ``Id`` s. In mathematics we have the following relations:

*   :math:`\forall {\tt CustomerId}: {\tt CustomerId} \in \{Customers.Id\}` 

    In words: For each ``CustomerId``, it should be in the set of all ``Id`` in the table ``Customers``.

*   :math:`\forall {\tt ProductId}: {\tt ProductId} \in \{Product.Id\}` 

    In words: For each ``ProductId``, it should be in the set of all ``Id`` in the table ``Products``.

Foreign Keys Relations
------------------------

The relations mentioned in the previous section are examples of consistent relations in a database. 

It is important that such relations between tables are maintained. For instance, if there is a ``CustomerId`` or a ``ProductId`` in the table ``Orders`` that is not in the table ``Customers`` or in the table ``Products`` respectively, then we will not be able to fulfill that order because we do not know where to deliver, or what to deliver!

These relations can be enforced in a database using **Foreign Keys**. 

When such a Foreign Key relation is specified as a constraint in a database, and we insert an order without a matching Product, we will get an error message like:

.. code-block:: none

    Error writing to database table "db_OrdersNew": ODBC[1452] : HY000 [MySQL][ODBC 8.0(w)
    Driver][mysqld-5.6.44-log]Cannot add or update a child row: a foreign key constraint fails
    (`howto343b`.`orders`, CONSTRAINT `fk_order_product` FOREIGN KEY (`ProductId`) 
    REFERENCES `products` (`Id`)).

Based on the above example, we now introduce the following existing database terminology:

*   The constraint discussed above is known as a **Foreign Key**. 
    In our example, the table ``Orders`` has two foreign keys: 
    
    *   A value in its column ``customerId`` should match a value in the column ``Id`` of the table ``Customers``.
    
    *   A value in its column ``ProductId`` should match a value in the column ``Id`` of the table ``Products``.

*   The tables ``Customers`` and ``Products`` are **parent tables** of these foreign keys.

*   The table ``Orders`` is the **child table** of these foreign keys.

.. note:: Note that the definition of Foreign Keys given in 
          `Wikipedia <https://en.wikipedia.org/wiki/Foreign_key>`_ allows for more complex relations.
          For instance, non-key columns in parent tables can be referenced.
          Also, the key referenced may consist of multiple columns.
          In this article, we restrict ourselves to just a single column, and in the parent table that is the key column.

When a customer is removed from the table ``Customers``, when there are still orders for that customer, we will get an inconsistency as well.

Given the above foreign constraints, there are at least two options available to the database when attempting to delete a customer with existing orders:

#.  Forbid that deletion and issue an appropriate error message.

#.  Realize that deletion, and delete the corresponding orders as well. 
    This is known as **cascading** deletes.

.. tip:: It is generally considered to be good practice to enforce all Foreign Key relations; as it will enhance the quality of the data. Note that by enforcing these constraints in the database, any application that reads and modifies data in that database needs to adhere to these constraints. This is also true for AIMMS applications!


Available Strategies
--------------------

An AIMMS ``write to table`` statement will delete, update and insert some rows in a database table.
This can be implemented using the SQL statements ``DELETE``, ``UPDATE``, and ``INSERT``. 

A. Strategy A:

    1.  Determine the rows that are already in the database table

    #.  Delete the existing rows no longer relevant

    #.  Update the existing rows still relevant with new data

    #.  Insert the new rows together with their data.

B. Strategy B:

    1.  Delete all old rows in the table

    #.  Insert rows as there is information

Clearly, Strategy A looks more complicated and time-consuming. 
It can be more time consuming, as it needs to read a potentially large amount of data before the table is actually modified. 
To understand why this strategy is still needed, we need to take a close look at their behavior in combination with foreign keys.


Choice of Strategy and Consequences for Safety and Efficiency
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Let's get back to the strategies introduced at the beginning of the previous section.

As an example, consider the operation to change the address of a customer, using the two strategies:

*   Using Strategy B, we first delete that customer and then recreate it using an insert statement.
    By doing a delete first in the presence of orders for that customer, depending on the type of foreign constraint, this will either be forbidden or it will lead to cascading deletes of orders. 
    Either way, that is **undesired behavior** for an operation like changing the address.

*   Using Strategy A, in the end, the only modification is done is just a SQL UPDATE of that customer. 
    There is no effect on the table ``Orders``, which is desired. 
    Therefore, even though this strategy may be less efficient, it is safe.

When the table at hand is a parent table in a Foreign Key constraint, then the safe Strategy A is preferred. Otherwise, the efficient Strategy B is preferred. 

AIMMS uses the knowledge of whether Foreign Keys are present or not based on the values of two options: ``Database_foreign_key_handling`` and ``Database_string_valued_foreign_keys``, according to the following table:



.. csv-table:: Foreign key presence
    :header: "Presence", "Strategy", "Advantage", "Consquence when assumption invalid"
    :widths: 8, 10, 10, 40
    
    "Yes", "A",  "Safe", "Less efficient strategy used"
    "No", "B", "Efficient", "Data loss"

Are Foreign Keys Constraints Active on the Table to be Written?
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

When writing to a table it is important to know whether the table at hand is used in a Foreign Key constraint:

#.  As a parent table, see strategy discussion above.

    When the option ``database_foreign_key_handling``  is set to:
    
    * ``'check'`` The ODBC function `SQLForeignKeys <https://docs.microsoft.com/en-us/sql/odbc/reference/syntax/sqlforeignkeys-function>`_ is used to determine this.

    * ``'ignore'`` AIMMS assumes that the table is not used as a parent table in a Foreign Key constraint.

    * ``'assume'`` AIMMS assumes that the table is used as a parent table in a Foreign Key constraint.
    
    The default of the option ``database_foreign_key_handling`` is ``'check'``.

#.  As a child table, if so, empty strings are written as NULL's. 
    So this information is only relevant if your database schema has string valued keys.

    When the option ``database_string_valued_foreign_keys``  is set to:

    * ``'check'`` The ODBC function `SQLForeignKeys <https://docs.microsoft.com/en-us/sql/odbc/reference/syntax/sqlforeignkeys-function>`_ is used to determine this.

    * ``'ignore'`` AIMMS assumes that the table is not used as a child table in a Foreign Key constraint.

    * ``'assume'`` AIMMS assumes that the table is used as a child table in a Foreign Key constraint.

    The default of the option ``database_foreign_key_handling`` is ``'check'`` up to AIMMS 4.73 and is ``'ignore'`` for AIMMS 4.74 and upwards.


Pros and Cons of the Setting 'check'
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The setting ``'check'`` has some clear advantages in terms of ease of use:

    *   It does not require the model builder to dive into the concept of Foreign Keys.  

    *   Nor does it require the model builder to check the schema whether any of the tables at hand 
        are used as a parent table in a Foreign Key constraint.  

On the other hand, obtaining metadata via the ODBC function `SQLForeignKeys <https://docs.microsoft.com/en-us/sql/odbc/reference/syntax/sqlforeignkeys-function>`_ from a database can be rather time-consuming. 
This depends on the database vendor and the complexity of the database schema. 
Thereby this initial overhead can be significant in the overall performance if there are several tables to be written, and for each table only one or a few rows to be persisted.

Database Schema Design Best Practices
------------------------------------------------

In this section, a practice is suggested to safely and efficiently write the data to the application database. 

The good practice of specifying Foreign Keys is assumed but limited to single keys in Parent Tables.
Writing strategies in the presence of more complex Foreign Keys is a topic for another article.

To do so, we divide the schema into two layers of metadata:

#.  The key data,

#.  The attribute data 

Key Data
^^^^^^^^^^^^

The "key data" are tables that correspond to the sets and one-dimensional parameters declared over these sets. These tables are present as Parent Tables in Foreign Key relations. Examples are:

*   Assets, a parameter can indicate:

    * Type,

    * Location

*   Periods,

*   Customers

*   Products

When these tables have derived columns, they can also appear as child table in a Foreign Key.
In our Customer-Order example, both tables ``Customers`` and ``Orders`` are key tables. 
You may recall that in the Foreign Key of that example, ``Customers`` is the parent table, and ``Orders`` is the child table.

When writing to these tables, it is important that data of other tables remain intact and that the writing operations succeed whenever possible. 
This is achieved by setting the option ``Database_foreign_key_handling`` to ``'Assume'`` and therefore have the required safe writing strategy.

In our example, tables are best written to with the options ``Database_foreign_key_handling`` and ``Database_string_valued_foreign_keys`` set to ``'Assume'`` and ``'Ignore'`` respectively, as follows:

.. code-block:: aimms
    :linenos:

    block where database_foreign_key_handling := 'assume',
                database_string_valued_foreign_keys := 'ignore'  ;

        write to table db_Customers ;
        write to table db_Products ;

    endblock ;

The following remarks apply to this code;

* By using a block statement, the options are only set in the respective code portion, and the remainder of the application is left untouched.  
  See article :doc:`setting options <../208/208-setting-options>`

* As integer keys are used in our example, the option ``database_string_valued_foreign_keys`` can be set to ``'ignore'``.

* Efficiency is less important than correct behaviour, so we remove this from our considerations.


Attribute Data
^^^^^^^^^^^^^^^^^^

The actual data, for instance, how much of which product is bought by which customer and when.
These tables can be a part of foreign key constraints only as child tables. 
It is, therefore, safe to use efficient Strategy B for writing to these tables.

.. code-block:: aimms
    :linenos:

    block where database_foreign_key_handling := 'ignore',
                database_string_valued_foreign_keys := 'ignore'  ;
        
        write to table db_Orders ;

    endblock ;

.. note::

    Like key tables, the foreign keys of these tables only refer to keys in key tables.

.. seealso::    

    * :doc:`../344/344-sparse-execution-for-write-to-table`

