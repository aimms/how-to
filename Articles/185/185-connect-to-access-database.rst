.. warning:: 
   Please note that the documentation you are currently viewing is for an older version of our technology. 
   While it is still functional, we recommend upgrading to our latest and more efficient option to take advantage of all the improvements we've made.

Connection String for Access Database Files
============================================

.. meta::
   :description: How to connect to a MS Access database file via the ODBC connection string.
   :keywords: MS ACCESS, ODBC, connection string

AIMMS has the possibility to retrieve/store data from/into any ODBC datasource. You could provide a System/User/File DSN (in case of ODBC) pointing to the database as an input to AIMMS. You could also dynamically create a connection string instead of using a DSN file. 

This article shows how to generate a connection string to connect to an Access database via the ODBC layer. 

Advantages of the Connection String
------------------------------------------

When you deploy a project where the end-user can specify an Access database file, you have three options:

* generate the DSN files based on the user's choice
* let the user choose the specific Access database file via the file selection wizards of the ODBC driver
* generate the connection string within AIMMS itself, based on the file that the user selected

One advantage of creating a connection string instead of using DSN files is that you don't have to include the database password in the plain-text DSN file if your database requires a password; you can keep this password hidden in the code of your project.

These connection strings can be used in the *Data Source* attributes of all the database related identifiers AIMMS (e.g. tables, database procedures).

Implementation of the Procedure
-------------------------------------

To create a connection string for a given Access database file (``.mdb`` or ``.accdb``), the procedure ``CreateAccessFileConnectionString`` below uses the intrinsic AIMMS function :aimms:func:`SQLCreateConnectionString`. 

The procedure will first query which ODBC drivers are available, and follow with these actions:

1. try to find the driver that supports the Office 2007 and later databases (i.e. the Access files with extension ``.accdb``) 
2. if the driver cannot be found, search for the driver that supports ``.mdb`` files. 
3. if that driver cannot be found, raise an error

The procedure requires two arguments:

* ``AccessDatabaseFile``: Input argument denoting which database to use,
* ``ConnectionString``: Output argument in which the connection string is stored.

Furthermore, the procedure has the following two local identifiers:

.. code-block:: aimms

    Set sODBCDrivers {
        Index: iOdbcDrivers;
    }
    ElementParameter epODBCDriver {
        Range: sODBCDrivers;
    }

.. code-block:: aimms
    :linenos:

    !Find all ODBC drivers
    while LoopCount <= SQLNumberOfDrivers( 'ODBC' ) do
        SetElementAdd(
            Setname : sODBCDrivers , 
            Elempar : epODBCDriver , 
            Newname : SQLDriverName( 'ODBC' , Loopcount) ) ;
    endwhile ;
  
    !First try to find the driver that handles both Access 2007 and up 
    !drivers (i.e. .accdb)
    epODBCDriver := first( iOdbcDrivers | 
                 FindString(iOdbcDrivers,"*.accdb" ));
  
    !if that does not exist, then try to find the driver that 
    !handles .mdb files
    if not epODBCDriver then
        epODBCDriver := first( iOdbcDrivers | 
                                 FindString(iOdbcDrivers,"*.mdb"));
    endif ;
  
    !If we also could not find this driver, we have a problem...
    if not epODBCDriver then
        raise error "Could not find MS Access ODBC driver!" ;
    endif ;
  
  
    !Now we can create the connection string based on the name of the
    !driver, the name of the database. We provide the DefaultDir as
    !an additional argument to ensure that paths relative to the
    !prj file will work also. If you provide an absolute path as the
    !first argument, the ODBC layer will discard the DefaultDir information.
    ConnectionString := SQLCreateConnectionString(
        DatabaseInterface              :  'odbc',
        DriverName                     :  epODBCDriver,
        DatabaseName                   :  AccessDatabaseFile,
        AdditionalConnectionParameters :  ";DefaultDir=.\\") ;

If you already know which driver to use as input for the argument *DriverName*, you can directly use the ConnectionString function. 

Example Download
----------------------
        
You can import the ``.ams`` file into a section of your model. Select an empty Section, and in the menu ``Edit`` → ``Import...`` the following ``.ams`` file.

:download:`CreateAccessFileConnectionString.ams <downloads/CreateAccessFileConnectionString.ams>`

.. note:: Please do not forget to close the connection using the intrinsic procedure :aimms:func:`CloseDataSource` especially when long sessions may occur after data reading.
