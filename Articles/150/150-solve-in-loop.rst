Solve in Loop
==============

.. meta::
   :description: How to solve several instances at once using a loop.
   :keywords: loop, solve


I often get the question how to solve several instances in one go.
In this article I provide a simple example.

The structure of execution is typically something ilke:

#. Determine the collection of inputs
#. For each input:
    #. Work that input
    
Let's discuss these steps in detail:

Determine the collection of inputs
----------------------------------

When you have multiple input files, you'll probably want to determine the folder name.
For this you can use the predeclared funtion ``DirectorySelect``. Example code is:

.. code-block:: aimms

    ret := DirectorySelect(
        directoryname :  sp_BatchExcelInputFolder, 
        directory     :  ".", 
        title         :  "Please select folder to read the .xlsx input files from.");

Obtaining the collection of input files
------------------------------------------
        
With the directory name, the collection of input files can be obtained using ``DirectoryGetFiles``.

.. code-block:: aimms

    DirectoryGetFiles(
        directory :  sp_BatchExcelInputFolder, 
        filter    :  "*.xlsx", 
        filenames :  sp_InputFileNames, 
        recursive :  1);

Processing each of the input files
------------------------------------

.. code-block:: aimms

    for i_fn do
        sp_Workbook := sp_BatchExcelInputFolder + sp_InputFileNames(i_fn);
        pr_ExecuteSingleRun( sp_Workbook );
    endfor ;

Example used:
-------------

:download:`Excel inputs download <downloads/ExcelInputs.zip>` 

:download:`AIMMS project download <downloads/MultiRunExcel.zip>` 

.. include:: /includes/form.def

