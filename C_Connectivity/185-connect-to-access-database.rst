Connect to Access database file
================================

.. meta::

 :description: How to connect to a MS Access database file via the ODBC connection string.


AIMMS has the possibility to retrieve/store data from/into any ODBC or OLEDB datasource. You could provide a UDL file (in case of OLEDB) or a System/User/File DSN (in case of ODBC).

This article shows how to generate a connection string to connect to an Access database via the ODBC layer. (However, you can use a similar approach to connect to any database, also via the OLEDB layer.)

When you deploy a project where the end-user can specify an Access database file, you have three options:
* generate the DSN files based on the user's choice
* let the user choose the specific Access database file via the file selection wizards of the ODBC driver
* generate the connection string within AIMMS itself, based on the file that the user selected

One advantage of creating a connection string instead of using DSN files is that you don't have to include the database password in the plain-text DSN file if your database requires a password; you can keep this password hidden in the code of your project.

These connection strings can be used in the *Data Source* attributes of all the database related identifiers AIMMS (e.g. tables, database procedures).



To create a connection string for a given Access database file (.mdb or .accdb), the procedure ``CreateAccessFileConnectionString`` below uses the intrinsic AIMMS function ``SQLCreateConnectionString``. 

The procedure will first query which ODBC drivers are available, and follow with these actions:

1. try to find the driver that supports the Office 2007 and later databases (i.e. the Access files with extension .accdb) 
2. if the driver cannot be found, search for the driver that supports .mdb files. 
3. if that driver cannot be found, raise an error

The procedure requires two arguments:

* ``AccessDatabaseFile``: Input argument denoting which file to use,
* ``ConnectionString``: Output argument in which the connection string is stored.

Furthermore, the procedure needs the following two local identifiers:
.. code-block:: aimms

  SET:
   identifier :  sODBCDrivers
   index      :  iOdbcDrivers ;

  ELEMENT PARAMETER:
   identifier :  epODBCDriver
   range      :  sODBCDrivers ;

The source for the procedure is then as follows:

.. code-block:: aimms

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

You can import the AIM file into a section of your model. 

.. See "Importing a section from another AIMMS project" for more information about importing the .aim file in your project. (I think this post is gone, needs to be reproduced perhaps?)

:download:`CreateAccessFileConnectionString.aim <../Resources/185-connect-to-access-database/CreateAccessFileConnectionString.aim>`

