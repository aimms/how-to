Different methods for Reading columns of data from EXCEL
===========================================================

.. meta::
   :description: EXCEL data can be read via the libraries AimmsXLLibrary and via datalink using the xlsprovider. How do these methods compare?
   :keywords: excel, AimmsXLLibrary, library, axll, xlsprovider, datalink

AIMMS provides various methods for reading EXCEL data. 

#. A prepackaged module of procedures and functions, using the prefix ``spreadsheet::``.  
 
#. AimmsXLLibrary, a system library of functions and procedures.

#. Datalink with the provider xlsprovider, two libraries from the AIMMS repository of libraries. 

Clearly, there is overlapping functionality here. Which method for EXCEL communication is the best method for your application, depends on your application. So let's compare some features of these libraries.

Example used
-------------

To make this comparison, we'll read from two spreadsheets:

.. image:: images/data1.PNG

.. image:: images/data2.PNG

As you can see, these two spreadsheets look a lot like each other, more or less the same data, but different column order, and one sheet has one more column that the other.
This is what we see in practice often; various analysts that provide the necessary data in spreadsheet, but not necessarily adhering to the same column order.

The name manager in both sheets covers for the difference in columns: 

.. image:: images/CommonNameManager.PNG

The data is read in to the following parameters:

.. code-block:: aimms

    DeclarationSection Potential_EXCEL_input_data {
        Set s_SKU {
            SubsetOf: Integers;
            Index: i_sku;
        }
        Set s_Vendors {
            Index: i_Vendor;
        }
        Parameter p_price {
            IndexDomain: (i_sku,i_vendor);
        }
        Parameter p_maxavail {
            IndexDomain: (i_sku,i_Vendor);
        }
        Parameter p_amtPerPackage {
            IndexDomain: (i_sku,i_Vendor);
        }
    }


The ``spreatsheet::`` functions and procedures 
----------------------------------------------

The ``spreadsheet::`` module include functions and procedures to:

#. Create and close work books

#. Helper functions to create ranges

#. Support for exchanging data between ranges on an EXCEL sheet with scalar, one-dimensional and multi-dimensional AIMMS parameters. Note that these ranges can be named ranges.

The code to read EXCEL data using looks as follows:

.. code-block:: aimms

    empty p_price, p_maxavail, p_amtPerPackage ;

    spreadsheet::RetrieveTable(
        workbook                :  sp_dataFilename,
        parameter               :  p_price, 
        DataRange               :  "Price",     
        RowsRange               :  "colrange",  
        sheet                   :  "Sheet1",
        automaticallyExtendSets :  1 );

    spreadsheet::RetrieveTable(
        workbook                :  sp_dataFilename,
        parameter               :  p_maxAvail, 
        DataRange               :  "maxAvail",     
        RowsRange               :  "colrange",  
        sheet                   :  "Sheet1",
        automaticallyExtendSets :  1 );

    spreadsheet::RetrieveTable(
        workbook                :  sp_dataFilename,
        parameter               :  p_amtPerPackage, 
        DataRange               :  "amtPerPackage",     
        RowsRange               :  "colrange",  
        sheet                   :  "Sheet1",
        automaticallyExtendSets :  1 );

    spreadsheet::CloseWorkBook(sp_dataFilename,0);

Note the following:

#. The implementation of these functions uses EXCEL itself, and therefore this module is less suited for application development; it would require the presence of EXCEL on the client computer or AIMMS PRO Server. 

#. This library supports running EXCEL macros.

#. We can abstract here from the column order because the name manager of EXCEL took care of providing consistent names in the named ranges. When this is not provided, the above code would become signficant more complicated.

#. This library doesn't make any assumptions about the placing of data in columns.

#. For more information about these functions, see: `AIMMS The Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_: Chapter "Spreadsheet Functions".
 

The AXLL functions and procedures
---------------------------------

The ``AXLL::`` system library includes functions and procedures to:

#. Create and close work books

#. Helper functions to create ranges

#. Support for exchanging data between ranges on an EXCEL sheet with scalar, one-dimensional and multi-dimensional AIMMS parameters. Note that these ranges can be named ranges.

The code to read EXCEL data using looks as follows:

.. code-block:: aimms

    empty p_price, p_maxavail, p_amtPerPackage ;
    axll::OpenWorkBook( sp_dataFilename );
    axll::SelectSheet("Sheet1");

    axll::ReadList(
        IdentifierReference    :  p_price, 
        RowHeaderRange         :  "colrange",  
        DataRange              :  "Price",     
        ModeForUnknownElements :  1, 
        MergeWithExistingData  :  0);

    axll::ReadList(
        IdentifierReference    :  p_maxAvail, 
        RowHeaderRange         :  "colrange",  
        DataRange              :  "maxAvail",     
        ModeForUnknownElements :  1, 
        MergeWithExistingData  :  0);

    axll::ReadList(
        IdentifierReference    :  p_amtPerPackage, 
        RowHeaderRange         :  "colrange",  
        DataRange              :  "amtPerPackage",          
        ModeForUnknownElements :  1, 
        MergeWithExistingData  :  0);

    axll::CloseAllWorkBooks();

Note the following:

#. The implementation of these functions that accesses the workbook directly without the need to use EXCEL. This makes this library more suited for application development. 

#. This library doesn't support running EXCEL macros.

#. We can abstract here from the column order because the name manager of EXCEL took care of providing consistent names in the named ranges. When this is not provided, the above code would become signficant more complicated.

#. This library doesn't make any assumptions about the placing of data in columns.

#. For more information about these functions, 
   see also :doc:`../85/85-using-axll-library` and :doc:`../122/122-AXLL-Library` .


Datalink with the provider xlsprovider.
---------------------------------------

The xlsprovider uses a simple strategy to understand the structure of the data in an EXCEL worksheet.
The top most row with data is assumed to be the header, containing the names of the columns. 
You have to map these names onto identifier names in the AIMMS model.
Then datalink can read by making the xlsprovider scan the worksheet row by row and use the mapping to send the data to the appropriate identifiers.


The code to read the EXCEL data looks as follows:

.. code-block:: aimms

    dl::DataTables += 'Sheet1' ;
    SKUMapping(dl::dt,dl::idn,dl::cn,dl::dn) := data {
       ( 'Sheet1', 's_SKU'           , 1, 1 ) : "SKU",
       ( 'Sheet1', 's_Vendors'       , 2, 2 ) : "Vendor",
       ( 'Sheet1', 'p_price'         , 3, 0 ) : "Price",
       ( 'Sheet1', 'p_maxavail'      , 4, 0 ) : "maxAvail",
       ( 'Sheet1', 'p_amtPerPackage' , 5, 0 ) : "amtPerPackage"
    };

    dl::RemoveDataSourceMapping("SKUData");
    dl::AddDataSourceMapping(
        MapName        :  "SKUData", 
        DataMap        :  SKUMapping, 
        ColDepend      :  dl::DependEmpty, 
        TableAttribute :  dl::TableAttributesEmpty, 
        ColAttribute   :  dl::ColAttributeEmpty);
    sp_readAttribute :=  { 'DataProvider': xlsprovider::DataLink };
    dl::DataRead(
        DataSource     :  sp_dataFilename, 
        MapName        :  "SKUData", 
        ReadAttributes :  sp_readAttribute);

Note the following:

#. The implementation of this library accesses the workbook directly without the need to use EXCEL. This makes this library more suited for application development. 

#. This library doesn't support running EXCEL macros.

#. We can abstract here from the column order because the functionality in the datalink library; independent of the name manager.

#. This library assumes that the data is presented column wise on the sheet; this library is less suited for data that is scattered on a EXCEL sheet.

#. See also :doc:`../csv/read-write-csv` and `XLSProvider for datalink <https://documentation.aimms.com/datalink/providers.html#xlsprovider>`_

You can download the example from :download:`AIMMS project download <ReadingExcelData.zip>` 


.. include:: /includes/form.def