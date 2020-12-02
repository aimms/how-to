Duplicate entry superfluous error message using MySQL ODBC driver
====================================================================

.. https://gitlab.aimms.com/aimms/customer-tickets/-/issues/3487
.. https://aimms.odoo.com/web#id=35554&view_type=form&model=helpdesk.ticket&action=275&active_id=3&menu_id=163

.. CREATE DATABASE `odoo35554` /*!40100 DEFAULT CHARACTER SET latin1 */;
.. CREATE TABLE `variants` (
..   `var` varchar(45) NOT NULL,
..   `db1` double DEFAULT NULL,
..   `nm1` varchar(45) DEFAULT NULL,
..   PRIMARY KEY (`var`)
.. ) ENGINE=InnoDB DEFAULT CHARSET=latin1;


Symptom
-------

When writing to a MySQL database table, unexpectedly an error message pops up containing ``Duplicate entry``, for instance the following:

.. code-block:: none

    Error writing to database table "db_vari": ODBC[1062] : 23000 [MySQL][ODBC 8.0(w) Driver][mysqld-5.6.44-log]Duplicate entry 'a' for key 'PRIMARY'

Filled in portions of this error message are:

#.  ``db_vari`` An AIMMS identifier of type database table.

#.  ``'a'`` Here "a" is the name of the element supposedly to contain duplicate information.

One possible explanation
--------------------------

The article `Write to a database efficiently <https://how-to.aimms.com/Articles/343/343-use-metadata-in-write-to-table.html>`_ explains that there are two strategies available to AIMMS for writing to a table:

* Strategy **A**: A safe way, by first updating and perhaps inserting.

* Strategy **B**: A quick way, by cleaning the table and then inserting.
 
Strategy **A** is preferred when avoiding cascading deletes is important.

In more detail strategy **A** issues:

#.  an update statement for each record to be written, followed by 

#.  an insert statement, when that update statement failed.

The default behavior of most ODBC drivers is that the return value of the UPDATE statement is the number of rows whereby there is a match in the data of primary columns.

The default behavior of MySQL ODBC driver, however, is that the return value of the MySQL UPDATE statement reflects the number of records adapted. If the data in the database is the same as the data to be written via the UPDATE statement, then this statement has a return value of 0.

AIMMS will interprete the return value of 0, as there are 0 rows matching the primary and issue a subsequent INSERT statement assuming that the failure is due to absence of the data. This is where the "duplicate entry" error message comes from.

Other explanations
------------------

There are also other scenarios and write strategies in the AIMMS Database interface that rely on 
the SQL Update statement to return the number of found rows instead of the number of affected rows.
As these require even more intricate explanations, they are not included here.


Resolution
-----------

The UPDATE statement of MySQL can be configured to return the number of rows found, instead of the number of rows affected, like the other ODBC drivers. Depending on the type of the ODBC connection you are using, you can adapt the behavior of the UPDATE statement as follows:

#.  Using SQLCreateConnectionString

    .. code-block:: aimms
        :linenos:
        :emphasize-lines: 8

        sp_connStr := SQLCreateConnectionString(
            DatabaseInterface               :  'ODBC' ,
            DriverName                      :  sp_driverMySQL,
            ServerName                      :  sp_server,
            DatabaseName                    :  sp_database,
            UserId                          :  sp_user,
            Password                        :  sp_pwd,
            AdditionalConnectionParameters  :  "FOUND_ROWS=1" );

    By adding as additional connection parameter the phrase ``FOUND_ROWS=1``.

#.  Using a .dsn file

    .. code-block:: none
        :linenos:
        :emphasize-lines: 7

        [ODBC]
        DRIVER=MySQL ODBC 8.0 Unicode Driver
        UID=<your user name>
        PWD=<something you'd like to keep to yourself>
        DATABASE=odoo35554
        PORT=3306
        FOUND_ROWS=1

    The line "FOUND_ROWS=1" is added to the normal .dsn file connecting to a MySQL data source.

#.  Using a named data source

    .. image:: images/use-found-rows.png
        :align: center

    When creating/configuring the connection, please make sure the tick ``Return matched rows instead of affected rows`` in the tab ``Cursors/Results`` is checked.



