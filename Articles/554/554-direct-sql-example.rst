DirectSQL Example
===================

Not all operations on ODBC databases can be done using the ``read from table`` and ``write to table`` statements.
More flexibility is offered using the intrinsic procedure :aimms:procedure:`DirectSQL`. Please use the `Reindeer Pairing <https://how-to.aimms.com/Articles/434/434-reindeer-pairing.html>`_ example to experiment with this feature.
 
With that, the procedure below illustrates an alternative for ``Write to Table ... in insert mode``:

.. aimms:procedure:: pr_fillDatabase
    
This procedure will first create a connection string to the SQLite database, clean its previous values and then insert the current solve. 

.. code-block:: aimms 
    :linenos:

    sp_loc_datasource
    :=  SQLCreateConnectionString (
            DatabaseInterface              :  'odbc',
            DriverName                     :  "SQLite3 ODBC Driver", !Your local drive
            ServerName                     :  "", 
            DatabaseName                   :  "pairing.db", !The path of your database
            UserId                         :  "", 
            Password                       :  "", 
            AdditionalConnectionParameters :  "");

    !delete all
    DirectSQL(sp_loc_datasource, "delete from possible_pairs;");

    !and fill again
    for (i_sols, i_left) | ep_variousRightPartners(i_sols, i_left) do
        sp_loc_insertCommand 
        :=  FormatString("INSERT INTO possible_pairs (solution,left,right) values ('%e','%e','%e');",
                        i_sols, i_left, ep_variousRightPartners(i_sols, i_left));

        DirectSQL(sp_loc_datasource, sp_loc_insertCommand);
    endfor ;

Remarks:

* line 12: Call to ``DirectSQL`` using an existing ODBC connection to delete previous information on the database.

* line 18: To pass the elements (``ep_variousRightPartners``), just fill in the data using :aimms:func:`FormatString`.

* line 20: A SQL INSERT statement following the `SQL syntax <https://www.w3schools.com/sql/sql_insert.asp>`_ . ``possible_pairs`` is the name of the table of the Reindeer Pairing.
