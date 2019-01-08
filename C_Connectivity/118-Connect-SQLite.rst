.. include:: ../includes/icons-sub.def

Linking an SQLite Database to a Project
=========================================

.. meta::
   :description: Directions to link a project in AIMMS with an SQLite database in Windows 10.
   :keywords: sql, sqlite, database

.. note::

    This article was originally posted to the AIMMS Tech Blog on October 25, 2018 by Arthur d'Herbemont.

Say you have a wonderful AIMMS application, that needs to link a Database Table identifier |db| (an AIMMS identifier) to one SQL data base of yours. Your amazing SQL data base is, however, an SQLite database. Thus, to be able to read it, AIMMS needs the appropriate "driver", which enables it to read your SQLite database. This blogpost presents how to:

#. Install the SQLite driver,

#. Connect your SQLite database  and

#. Verify that you can access it through AIMMS.


1 – Installation of the SQLite driver
---------------------------------------

SQLite driver
^^^^^^^^^^^^^^

To install SQLite ODBC driver, please refer to the following website, and download ``sqliteodbc.exe`` or ``sqliteodbc_w64.exe``, depending on your AIMMS' configuration (and NOT your computer's configuration):

http://www.ch-werner.de/sqliteodbc

Then just run the .exe and follow the instructions.

.. note:: This driver is open source under a BSD-type license, you may read the `license terms <http://www.ch-werner.de/sqliteodbc/license.terms>`_ to be sure.

Verification of the installation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To verify that the driver is well installed, please open the "Administrative Tools" of your computer (type "administrative tools" in windows search bar). You should see a window like this :

.. image:: ../Resources/C_Connectivity/Images/118/2odbc.png

Then open the "ODBC Data Sources (64-bit)" (or 32-bit) and reach the "Drivers" tab. Normally, you should find 3 new drivers, named **SQLite ODBC (UTF-8)**, **SQLite ODBC** and **SQLite3 ODBC**. The window may include other drivers (as Microsoft Access for example) :

.. image:: ../Resources/C_Connectivity/Images/118/3odbc.png

Congrats! The installation is successful.


Download an SQLite database sample to test
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

In order to test our installation, you could use your own generated SQLite database, or download a sample on this website: `example db <http://www.sqlitetutorial.net/download/sqlite-sample-database>`_

(click on the "SQLite Sample Database" download link). The downloaded database is named *chinook.db*, and we will use it in the rest of the tutorials.


Connect your SQLite database to an AIMMS identifier
---------------------------------------------------

Generate the connection file
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

As you may already know, to read one database AIMMS needs to know, among others, the name of the driver it should use and the location of your SQLite database. There are 3 different ways to give those indications to an AIMMS database identifier:

* By generating a **.dsn file**, that you will store somewhere on your computer (almost equivalent to a text file .txt) and link it to your AIMMS identifier,
* By generating a **system .dsn file**, that your computer will store for you in a specific place,
* By creating a** connection string**, that will have the same role as a .dsn file, but directly written into the AIMMS application. This appears to be particularly useful when, for instance, a password is needed to read a certain database, and you don't want to re-write it each time you open your application or the database.

Generate a .dsn file connected to your SQLite database
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Every database has its own .dsn file, that's why you need to generate it for each of your database. In addition to that, every ODBC driver has a different .dsn file structure.

To generate the appropriate .dsn file from your SQLite ODBC driver, please select the tab "File DSN" from the ODBC Data Sources administrator (the one that we opened just before to check that the installation was completed). Then click the "Add" button in the upper right of the window. You should see this pop up window:

.. image:: ../Resources/C_Connectivity/Images/118/4odbc.png

Select the driver you want to use (in our case, SQLite3 ODBC Driver) and click "Next".

At this point, the computer is asking you the name of the .dsn file you want to create. Name it as you wish. As you may see below, I choose to name it "test".

.. image:: ../Resources/C_Connectivity/Images/118/5odbc.png

After that, you will have access to the 'SQLite ODBC Driver Connect', which finally ask you to browse and select your SQLite database. As explained in <a href="#Download">Download an SQLite database sample to test</a>, my database is named *chinook.db*, you just need to browse and select it.

.. image:: ../Resources/C_Connectivity/Images/118/8odbc.png

After clicking the OK button, you should see a new .dsn file (named *test* in my case) in the file explorer of your ODBC Data Source Administrator.

Then you may go directly to the <a href="#Fill">Fill out AIMMS Database Table identifier</a> in order to complete the connection with your AIMMS database.

Generate a .dsn system file connected to your SQLite database
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To generate the appropriate .dsn System file from your SQLite ODBC driver, please reach the tab "System DSN" from the ODBC Data Sources administrator (the one that we've opened just before to check that the installation was completed).

* click the "Add" button in the upper right of the window.
* Select SQLite3 ODBC Driver as shown below.

 
.. image:: ../Resources/C_Connectivity/Images/118/7odbc.png


When the SQLite3 configuration window pops up :

* define the System DSN file name (I choose to name it *chinook SQLite3*),
* define the location of your SQLite database (*db* in my case) :
* Click OK

.. image:: ../Resources/C_Connectivity/Images/118/8odbc.png

You should now see that there is a new System DSN file in the System DSN tab:

.. image:: ../Resources/C_Connectivity/Images/118/9odbc.png

Congrats! You may go directly to the <a href="#Fill">Fill out AIMMS Database Table identifier</a> in order to complete the connection with your AIMMS database.

Create a connection string
^^^^^^^^^^^^^^^^^^^^^^^^^^

A connection string is an AIMMS string parameter |sp| that you could fill out thanks to a procedure. This procedure should use the '*SQLCreateConnectionString*' function. Let's build that connection string as follows :

* Create an AIMMS string parameter |sp| named '*ConnectionString*' .
* Check, Commit and Close.
* Create a new procedure |proc|  named '*WriteTheConnectionString*' (the name is not important)
* Double click on procedure's icon and write the following code in its body field:

.. code-block:: aimms

    ConnectionString := SQLCreateConnectionString (
        DatabaseInterface              :  'odbc',
        DriverName                     :  "SQLite3 ODBC Driver",
        DatabaseName                   :  "C:\\Users\\Arthur.AIMMS\\Documents\\SQLite\\sqlite-dll-win64-x64-3150000\\chinook.db", !The path of your database
        AdditionalConnectionParameters :  "") ; 


As you may see, this function fills out your string parameter with a "coded" string that will be read by your AIMMS datasource table identifier. This function allows you to define a user name and a password as well, by default empty, to access your SQLite database.

For more details on *SQLCreateConnectionString* function syntax, please right click on  *SQLCreateConnectionString*  in the body field and select the 'help on' item  

* Finally, you should run the procedure '*WriteTheConnectionString*', in order to fill out your String parameter.

Fill out an AIMMS Database Table identifier
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Let's start a super simple new AIMMS project, containing only one Database Table  named "Table1" :

.. image:: ../Resources/C_Connectivity/Images/118/10aimms.png

* Create a new Database Table ,
* Specify 'Table1',
* Activate the **Data Source** wizard,


+-------------------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| Link    Type                  |        Action                                                                                                                                 |
+===============================+===============================================================================================================================================+
| Link with a .dsn file         | * Choose the **Select File Data Source…** command in the menu that pops up,                                                                   |
|                               | * Select your DSN file ("*dsn*" in our case).                                                                                                 |
|                               | * Press the **Save**                                                                                                                          |
+-------------------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| Link with a System .dsn file  |  * Choose the **Select User/System Data Source…** command in the menu that pops up,                                                           |
|                               |  * Select your DSN System file ("*chinook SQLite3*" in our case).                                                                             |
|                               |  * Press the **Save**                                                                                                                         |
+-------------------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| Link with a connection string |  * Choose the **Select String Parameter/Connection String…** command in the menu that pops up,                                                |
|                               |  * Select the String Parameter |sp| you've just created (named '*ConnectionString'* in our case                                               |
+-------------------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+



Verify the database link
-------------------------

Once you have linked the data source, you are now ready and able to select a table from this source. Please, execute the following steps:

* Activate the **Table Name** wizard,
* Choose the **Select Table/Query Name...** command from the pop-up menu,
* You should see table names from your database… if not, please see the instructions bellow.

.. warning::
    
    if you receive the following error message when trying to link with a connection string: 

    .. image:: ../Resources/C_Connectivity/Images/118/11aimms.png

    |
     
    Your connection string might be empty. Please check if '*ConnectionString*' parameter is empty by accessing its data (right click on its icon and choose **Data…**). It should be filled out with the following string :

    .. code-block:: none

        DRIVER={SQLite3 ODBC Driver};DATABASE=C:\Users\Arthur.AIMMS\Documents\SQLite\sqlite-dll-win64-x64-3150000\chinook.db;

Summary
----------

In this article we installed the SQLite driver, and linked our SQLite database to an AIMMS database table identifier  |db| that we now may further use in our AIMMS application. We presented 3 different ways to link the database, namely the .dsn file, the system .dsn file and the connection string. We finally concluded by verifying that we were able to read our SQLite database through our AIMMS  database table identifier.

For further reading, please refer to the following related links :

AIMMS materials
--------------------

* `AIMMS The Language Reference <https://documentation.aimms.com/_downloads/AIMMS_ref.pdf>`_: Chapter 27 - Communicating With Databases

* `AIMMS Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_: Function SQLCreateConnectionString   

Other related websites
----------------------

* `SQLite ODBC Driver <http://www.ch-werner.de/sqliteodbc/>`_
* `SQLite Website <https://sqlite.org/>`_


.. include:: ../includes/form.def



