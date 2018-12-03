
.. IMAGES

.. |axll-workflow| image:: /Images/85-using-axll-library/axll-workflow.png

.. CONTENT

Using the AIMMS Excel Library
===================================

.. Overview

The AimmsXLLibrary can communicate with Excel files in server environments where Excel is not installed.

This library is especially useful when building WebUI apps for AIMMS PRO in a server environment.

The procedure follows the basic workflow illustrated below.

|axll-workflow|

.. note::

    Functions included with the AXLL library have a prefix ``axll``.

Importing data from Excel files
-------------------------------

.. Procedure

1. **Add the AIMMSXLLibrary Library.**

    Go to *File > Library manager*.

    Click *Add System library...* and select *AIMMSXLLibrary*. Click *OK*.

2. **Create a procedure.**

    Create a procedure to import data from the Excel document. For example, ``ReadData``.

    a. **Open the Excel file:**

        Use ``axll::OpenWorkBook`` to open your workbook. 

        *Tip:* If you send a command to open a workbook which is already open, AIMMS raises an error. You can use an ``IF`` block to check whether a workbook is open or not, and open if it is closed or otherwise select the open workbook.

        .. code-block:: aimms

                WorkBookName := "zipcode-database.xlsx";
                if axll::WorkBookIsOpen(WorkBookName) then
                    axll::SelectWorkBook(WorkBookName);
                else
                    axll::OpenWorkBook(WorkBookName);
                endif;
        
    b. **Specify a sheet of the workbook.**

            Use ``axll::SelectSheet`` to specify which sheet of the workbook to pull data from.

            .. code-block:: aimms

                sp_Sheet := "Example";
                axll::SelectSheet(SheetName : sp_Sheet );

    c. **Import data from the Excel file to your AIMMS project.**

            Use the appropriate ``axll::`` function to read data from the Excel sheet.

            The ReadTable statement will be:

            .. code-block:: aimms

                axll::ReadTable(
                    IdentifierReference :  MyValue,
                    RowHeaderRange      :  "A8:D18",
                    ColumnHeaderRange   :  "E4:K7",
                    DataRange           :  "E8:K18");

            Use ``axll::ReadSet`` to read in data for the set ``sStates``.

            .. code-block:: aimms

                axll::ReadSet(
                    SetReference    :  sStates,
                    SetRange        :  "D2:D42523",
                    ExtendSuperSets :  1);

            Use ``axll::ReadList`` to read in data ``ZipCodeState(z)``, which holds the state name that each zip code belongs to.

            .. code-block:: aimms

                axll::ReadList(
                    IdentifierReference :  ZipCodeState(z),
                    RowHeaderRange      :  "A2:A42523",
                    DataRange           :  "D2:D42523");

    d. **Close the Excel file.**

        Use ``axll::CloseWorkBook`` to close the workbook. ::

            axll::CloseWorkBook(WorkBookName);

3. **Run the procedure.**

    Run the procedure to import the data to your model. You can use the imported data for further operations and analysis.


.. Example

Practical Example
------------------

For a practical example, read **AIMMS Tech Blog:** `How to use the AIMMS Excel Library <https://techblog.aimms.com/2016/06/07/how-to-use-the-aimms-excel-library/>`_

.. END CONTENT



.. include:: ../includes/form.def