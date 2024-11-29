.. |sp| image:: /Images/icons/StringParameter.png
.. |db| image:: /Images/icons/database.png
.. |proc| image:: /Images/icons/proc.png

Link an SQLite Database to a Project
=========================================

.. meta::
   :description: Directions to link a project in AIMMS with an SQLite database in Windows 10.
   :keywords: sql, sqlite, database, link, connect


Say you have a wonderful AIMMS application that needs to link a Database Table identifier |db| (an AIMMS identifier) to your amazing SQL database that is, however, an SQLite database. To be able to read it, AIMMS needs the appropriate driver. This article presents how to:

#. Install the SQLite driver

#. Connect your SQLite database

#. Verify that you can access it through AIMMS


Install the SQLite Driver
---------------------------------------

SQLite Driver
^^^^^^^^^^^^^^

To install SQLite ODBC driver, please refer to the following website, and download ``sqliteodbc.exe`` or ``sqliteodbc_w64.exe``, depending on your AIMMS' configuration (and NOT your computer's configuration):

`SQLite ODBC Driver <http://www.ch-werner.de/sqliteodbc>`__ 

Then just run the ``.exe`` and follow the instructions.

.. note:: This driver is open source under a BSD-type license. You may read the `license terms <http://www.ch-werner.de/sqliteodbc/license.terms>`_ for details.

Verify the Installation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To verify that the driver is properly installed, please open the "Administrative Tools" of your computer (type "administrative tools" in Windows search bar). You should see a window like this :

.. image:: images/2odbc.png
    :align: center

Then open the "ODBC Data Sources (64-bit)" (or 32-bit) and reach the :menuselection:`Drivers` tab. Normally, you should find 3 new drivers, named **SQLite ODBC (UTF-8)**, **SQLite ODBC** and **SQLite3 ODBC**. The window may include other drivers (as Microsoft Access for example) :

.. image:: images/3odbc.png
    :align: center

Congrats! The installation is successful.

.. _download-sqlite-db-sample:

Download an SQLite Database Sample to Test
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

In order to test our installation, you could use your own generated SQLite database, or download a sample `here <https://www.sqlitetutorial.net/wp-content/uploads/2018/03/chinook.zip>`_. 
The downloaded database is named ``chinook.db``, and we will use it in the rest of this tutorial.

Connect your SQLite Database to an AIMMS Identifier
---------------------------------------------------

Generate the Connection File
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To read one database, AIMMS needs to know the name of the driver it should use and the location of your SQLite database. There are 3 different ways to give those indications to an AIMMS database identifier:

* By generating a **.dsn file**, that you will store somewhere on your computer (almost equivalent to a text file .txt) and link it to your AIMMS identifier
* By generating a **system** ``.dsn`` **file**, that your computer will store for you in a specific place
* By creating a **connection string**, that will have the same role as a ``.dsn`` file, but directly written into the AIMMS application. This appears to be particularly useful when, for instance, a password is needed to read a certain database, and you don't want to rewrite it each time you open your application or the database.

Generate a ``.dsn`` File Connected to your SQLite Database
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Every database has its own ``.dsn`` file, so you need to generate one for each of your databases. In addition to that, every ODBC driver has a different ``.dsn`` file structure.

To generate the appropriate ``.dsn`` file from your SQLite ODBC driver, please select the tab :menuselection:`File DSN` 
from the ODBC Data Sources administrator (the one that we opened just before to check that the installation was completed). 
Then click the :menuselection:`Add` button in the upper right of the window. You should see this pop-up window:

.. image:: images/4odbc.png
    :align: center

Select the driver you want to use (in our case, SQLite3 ODBC Driver) and click :menuselection:`Next`.

At this point, the computer is asking you the name of the ``.dsn`` file you want to create. Here, it is named ``test``.

.. image:: images/5odbc.png
    :align: center

After that, you will have access to the 'SQLite ODBC Driver Connect', which finally asks you to browse and select your SQLite database. 
In this example, the database is named ``chinook.db``.

.. image:: images/8odbc.png
    :align: center

After clicking :menuselection:`OK`, you should see a new ``.dsn`` file (named ``test`` here) in the file explorer of your ODBC Data Source Administrator.

Then you may go directly to the section fill-out-db-table-id_ in order to complete the connection with your AIMMS database.

Generate a ``.dsn`` System File Connected to your SQLite Database
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To generate the appropriate ``.dsn`` system file from your SQLite ODBC driver, please reach the tab :menuselection:`System DSN` from the ODBC Data Sources administrator (the one that we've opened just before to check that the installation was completed).

* click :menuselection:`Add` in the upper right of the window.
* select SQLite3 ODBC Driver as shown below.

 
.. image:: images/7odbc.png
    :align: center

When the SQLite3 configuration window pops up:

* define the System DSN file name (here, *chinook SQLite3*),
* define the location of your SQLite database (*db* in this case):
* click :menuselection:`OK`

.. image:: images/8odbc.png
    :align: center

You should now see that there is a new System DSN file in the :menuselection:`System DSN` tab:

.. image:: images/9odbc.png
    :align: center

Congrats! You may go directly to the `Fill out an AIMMS Database Table identifier`_ in order to complete the connection with your AIMMS database.

Create a Connection String
^^^^^^^^^^^^^^^^^^^^^^^^^^

A connection string is an AIMMS string parameter |sp| that you could fill out thanks to a procedure. This procedure should use the :aimms:func:`SQLCreateConnectionString` function. Let's build that connection string as follows:

* Create an AIMMS string parameter |sp| named ``ConnectionString`` .
* Check, Commit and Close.
* Create a new procedure |proc|  named ``WriteTheConnectionString`` (the name is not important)
* Double click on procedure's name and write the following code in its body field:

.. code-block:: aimms

    ConnectionString := SQLCreateConnectionString (
        DatabaseInterface              :  'odbc',
        DriverName                     :  "SQLite3 ODBC Driver",
        DatabaseName                   :  "C:\\Users\\Arthur.AIMMS\\Documents\\SQLite\\sqlite-dll-win64-x64-3150000\\chinook.db", !The path of your database
        AdditionalConnectionParameters :  "") ; 


As you may see, this function fills out your string parameter with a "coded" string that will be read by your AIMMS datasource table identifier. This function allows you to define a user name and a password as well, by default empty, to access your SQLite database.

For more details on :aimms:func:`SQLCreateConnectionString` function syntax, right-click on :aimms:func:`SQLCreateConnectionString` 
in the body field and select the :menuselection:`Help On` item.

Finally, you should run the procedure ``WriteTheConnectionString``, in order to fill out your string parameter |sp|. 

.. _fill-out-db-table-id:

Fill out an AIMMS Database Table identifier
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Let's start a super simple new AIMMS project, containing only one Database Table named ``Table1`` :

.. image:: images/10aimms.png
    :align: center

* Create a new Database Table,
* Specify ``Table1``,
* Activate the :menuselection:`Data Source` wizard,


+-----------------------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| Link    Type                      |        Action                                                                                                                                 |
+===================================+===============================================================================================================================================+
| Link with a ``.dsn`` file         | * Choose the :menuselection:`Select File Data Source...` command in the menu that pops up,                                                    |
|                                   | * Select your DSN file ("*dsn*" in our case).                                                                                                 |
|                                   | * Press the :menuselection:`Save`                                                                                                             |
+-----------------------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| Link with a system ``.dsn`` file  |  * Choose the :menuselection:`Select User/System Data Source...` command in the menu that pops up,                                            |
|                                   |  * Select your DSN System file (``chinook SQLite3`` in our case).                                                                             |
|                                   |  * Press the :menuselection:`Save`                                                                                                            |
+-----------------------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+
| Link with a connection string     |  * Choose the :menuselection:`Select String Parameter/Connection String...` command in the menu that pops up,                                 |
|                                   |  * Select the String Parameter |sp| you've just created (named ``ConnectionString`` in our case)                                              |
+-----------------------------------+-----------------------------------------------------------------------------------------------------------------------------------------------+



Verify the Database Link
-------------------------

Once you have linked the data source, you are now ready and able to select a table from this source. Execute the following steps:

* Activate the :menuselection:`Table Name` wizard,
* Choose the :menuselection:`Select Table/Query Name...` command from the pop-up menu,
* You should see table names from your database, if not, please see the instructions bellow.

.. warning::
    
    If you receive the following error message when trying to link with a connection string: 

    .. image:: images/11aimms.png
        :align: center

    |
     
    your connection string might be empty. Please check if ``ConnectionString`` parameter is empty by accessing its data (right click on its icon and choose :menuselection:`Data...`). It should be filled out with the following string:

    .. code-block:: none


        DRIVER={SQLite3 ODBC Driver};DATABASE=C:\Users\Arthur.AIMMS\Documents\SQLite\sqlite-dll-win64-x64-3150000\chinook.db;

Example Download
-------------------

You will need the SQLite ODBC driver to be installed to run this example, as described in `Install the SQLite driver`_:

:download:`Database read AIMMS example<model/databaseConnection.zip>`

.. note:: 
    Please `tell us <https://community.aimms.com/aimms-developer-12/how-to-link-an-sqlite-database-to-a-project-47>`_ if you think this example could be improved !

Conclusion
----------

In this article we installed the SQLite driver, and linked our SQLite database to an AIMMS database table identifier |db| that we now may further use in our AIMMS application. We presented 3 different ways to link the database, namely the ``.dsn`` file, the system ``.dsn`` file and the connection string. 
We finally concluded by verifying that we were able to read our SQLite database through our AIMMS database table identifier.

.. seealso::

    * :doc:`data-communication-components/communicating-with-databases/index`
    * :aimms:func:`SQLCreateConnectionString`  
    * `SQLite ODBC Driver <http://www.ch-werner.de/sqliteodbc/>`_
    * `SQLite Website <https://sqlite.org/index.html>`_

