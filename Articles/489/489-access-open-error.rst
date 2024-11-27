Sudden Microsoft Access Reporting: "Make Sure this is a Valid Datasource"
==========================================================================

Symptom
-----------

As of September 2020 some customers are reporting, from a project that used to work smoothly:

.. code-block:: none

    Opening table ....   
    from data source "DRIVER={Microsoft Access Driver (*.mdb, *.accdb ....  
    make sure this is a valid datasource.

Diagnose
---------

Using the ODBC data sources (64 bit) app, try to create a system dsn to the Microsoft Access database file.
When the error message: 

.. code-block:: none

   The setup routines for the Microsoft Access Driver (*.mdb, *.accdb) ODBC driver could not be loaded due to system error code 1114: 
   A dynamic link library (DLL) initialization routine failed. 
   (C:\Program Files\Microsoft Office\root\VFS\ProgramFilesCommonX64\Microsoft Shared\Office16\ACEODBC.DLL).
   
appears, this indicates an installation error.


Fix
-------

The following tip from `stackoverflow <https://stackoverflow.com/questions/63911262/microsoft-access-driver-specified-driver-could-not-be-loaded-due-to-system-erro>`_ 
may help: :menuselection:`Settings > Add/Remove Programs > Select "Microsoft Access Database Engine"` and select :menuselection:`Modify`.




