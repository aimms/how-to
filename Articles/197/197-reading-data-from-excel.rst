:orphan:

Reading columns of data from EXCEL
====================================

.. meta::
   :description: EXCEL data can be read via the libraries AimmsXLLibrary and datalink using the xlsprovider.
   :keywords: excel, AimmsXLLibrary, library, axll, xlsprovider, datalink

.. Named ranges not supported by axll
.. datalink/xlsprovider : column feature
..                      : self support column selection

This depends on the application at hand, and how sheets containing data are formatted.

Overview of methods to exchange data
-------------------------------------

AIMMS provides three methods of reading data from EXCEL:

#. A prepackaged module of procedures and functions, using the prefix ``spreadsheet``. 
   This library behind these procedures communicate via EXCEL itself, and therefore this module less suited for application development; 
   it would require the presence of EXCEL on the client computer or AIMMS PRO Server. 
   Please check: `AIMMS The Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_: Chapter "Spreadsheet Functions".

#. AimmsXLLibrary
   This library does not require EXCEL for reading ".xlsx" files; but reads directly from EXCEL workbooks/sheets.
   The data can be exchanged with any range on a sheet in a workbook, making it suitable to communicate between AIMMS and any EXCEL sheet.
   See also :doc:`../122/122-AXLL-Library`  

#. Datalink with the provider xlsprovider.
   When an EXCEL sheet is organized as parallel columns of data, with a row on top containing the column data descriptions, then such a sheet is suited to be used by Datalink. This may sound restrictive upon first reading, but it is actually a common format to store data in EXCEL workbooks.
   See also :doc:`../csv/read-write-csv` and `XLSProvider for datalink <https://documentation.aimms.com/datalink/providers.html#xlsprovider>`_
 
Further reading
---------------

* `AIMMS The Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_: Chapter "Spreadsheet Functions"

* :doc:`/Articles/122/122-AXLL-Library`

* `Datalink library <https://documentation.aimms.com/datalink/index.html>`_ and `xlsprovider <https://documentation.aimms.com/datalink/index.html>`_

.. include:: /includes/form.def