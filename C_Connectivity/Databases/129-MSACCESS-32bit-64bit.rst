Install Side-by-Side Drivers
================================

.. meta::
   :description: How to set up 32-bit and 64-bit Microsoft Access Drivers in parallel for AIMMS applications.
   :keywords: 32, 64, access, driver

.. note::

	This article was originally posted to the AIMMS Tech Blog on October 27, 2014 by Ovidiu Listes.



.. sidebar:: Connecting to 32 or 64 bit drivers

    .. image:: ../../Resources/C_Connectivity/Images/129/32_bit_vs_64_bit.jpg

 
Some years ago, before Microsoft Office 2010, life was – in some sense – easier for developers: Office was 32-bit, period. In our days, since the release of Microsoft Office 2010, things are a bit more complicated, as users can now have a machine with a 64-bit native version of Office installed as well. This means, for instance, that a 32-bit application using an ODBC driver to connect to an Access database might not work anymore, since the 32-bit ODBC driver might not exist on a machine with a 64-bit Office installation. In such a case, even though the user has a valid Office installation on his or her machine, the application may still display an error regarding the installation or the registration of the proper drivers on the local machine.

In order to address such problems, Microsoft released a redistributable named *"Microsoft Access Database Engine 2010 Redistributable"*. This redistributable provides a 32-bit or a 64-bit version of the Microsoft Access Database Engine, which can be downloaded from the Microsoft Download Center:

`Download drivers <http://www.microsoft.com/en-us/download/details.aspx?id=13255>`_
 
So, for example, if you have a 32-bit application using a 32-bit ODBC driver on a machine with a 64-bit installation of Office 2010, you will need to install the 32-bit version of the Microsoft Access Database Engine as well.

Please note that launching the installation of a Microsoft Access Database Engine in the usual way, on a machine with an Office installation architecture different from the current one (e.g. 32-bit on 64-bit), may cause the installation to fail. To have it run properly, you need to launch it from a command line with the "**/passive**" argument specified:

* To install the Microsoft Access Database Engine 32-bit on a machine running Office 2010 64-bit:

.. code-block:: none

    > AccessDatabaseEngine.exe /passive


* To install the Microsoft Access Database Engine 64-bit on a machine running Office 2010 32-bit:

.. code-block:: none

    > AccessDatabaseEngine_X64.exe /passive

In order to use the new driver from your AIMMS project for connecting to an Access database, you need to adjust the Data Source Name (.dsn) file associated with your Access database. Typically that .dsn file contains something like:

.. code-block:: none

    [ODBC]
    DRIVER=Microsoft Access Driver (*.mdb)
    DBQ=MyDB.mdb
    DriverId=25
    FIL=MS Access
    SafeTransactions=0

This should be changed to look like (where the changes are highlighted):

.. code-block:: none

    [ODBC]
    DRIVER=Microsoft Access Driver (*.mdb<mark>, *.accdb</mark>)
    DBQ=<mark>.</mark>MyDB.mdb
    DriverId=25
    FIL=MS Access
    SafeTransactions=0

    
In order to use the new driver from your AIMMS project for reading CSV files by using ODBC, you need to adjust the .dsn file associated with your (sub -) folder containing the .csv files. Typically that .dsn file contains something like:

.. code-block:: none

    [ODBC]
    DRIVER=Microsoft Text Driver (*.txt; *.csv)
    DBQ=DATA
    UserCommitSync=Yes
    Threads=3
    SafeTransactions=0
    PageTimeout=5
    MaxScanRows=16
    MaxBufferSize=2048
    FIL=text
    DriverId=27

    
In this example, Data is the subfolder of your AIMMS Project directory containing the .csv files.

For the new driver this should be changed to look like (where the changes are highlighted):

.. code-block:: none

    [ODBC]
    DRIVER=Microsoft Access Text Driver (*.txt<mark>,</mark> *.csv)
    DBQ=<mark>.</mark>DATA
    UserCommitSync=Yes
    Threads=3
    SafeTransactions=0
    PageTimeout=5
    MaxScanRows=16
    MaxBufferSize=2048
    FIL=text
    DriverId=27
    Extensions=txt,csv,tab,asc

    
Please note that the above is applicable to Office 2010 as well as to Office 2013. In case of Office 2013, the installation steps above should suffice. However, when Office 2010 32-bit is natively installed and the user tries to install the 64-bit version of the Microsoft Access Database Engine, he or she might still get an error regarding the registration of the proper drivers on the local machine. Should such a situation occur, please try the following workaround:

* Check the 64-bit registry key "HKEY_LOCAL_MACHINESOFTWAREMicrosoftOffice14.0CommonFilesPaths" **before** installing the 64-bit version of the Microsoft Access Database Engine 2010 redistributable.
* If it does not contain the "mso.dll" registry value, then you will need to rename or delete the value **after** installing the 64-bit version of the Microsoft Access Database Engine 2010 redistributable on a system with a 32-bit version of MS Office installed.
* Use the "/passive" command line parameter to install the redistributable, e.g. "C:directory pathAccessDatabaseEngine_x64.exe" /passive
* Delete or rename the "mso.dll" registry value, which contains the path to the 64-bit version of MSO.DLL (and should not be used by 32-bit MS Office versions).
* Now you can start a 32-bit MS Office application without the "re-configuring" issue.

Note that the "mso.dll" registry value will already be present if a 64-bit version of MS Office is installed. In this case the value should not be deleted or renamed.



.. include:: ../../includes/form.def



