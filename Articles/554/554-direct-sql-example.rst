DirectSQL Example
===================

Not all operations on ODBC databases can be done using the ``read from table`` and ``write to table`` statements.
More flexibility is offered using the intrinsic procedure :aimms:procedure:`DirectSQL`.

The following small example illustrates an alternative for ``Write to Table ... in insert mode``: 

.. code-block:: aimms 
    :linenos:

    Procedure pr_fillTable {
        Body: {
            for ( i_a, i_d ) do
                DirectSql( sp_datasource,
                    formatString("INSERT INTO AD (a,d,p,s,e) values ('%e','%e',%i,'%s','%e')",
                        i_a, i_d, p_data(i_a,i_d), sp_data(i_a, i_d), ep_data(i_a, i_d) ) );
            endfor ;
        }
    }

Remarks:

* line 4: Call to ``DirectSQL`` using an existing ODBC connection.

* line 5: A SQL INSERT statement following the `SQL syntax <https://www.w3schools.com/sql/sql_insert.asp>`_ . 
  ``AD`` is the name of a table here.

* line 6: To pass numeric data (``p_data``), string data (``sp_data``), and element data (``ep_data``), 
  just fill in the data using :aimms:func:`FormatString`.
  
Download:

    :download:`AIMMS 4.88 project download <model/ibds.zip>` 
