Solve in Loop
==============

.. meta::
   :description: How to solve several instances of Excel inputs at once using a loop.
   :keywords: loop, solve, excel


This article shows a simple example of how to solve several instances at once, using a loop.

The structure of execution usually follows this format:

#. Define the collection of inputs
#. Process each input in a loop
    
Let's discuss these steps in detail:

Defining the input files
----------------------------------

Use the predeclared function ``DirectorySelect`` to define the folder containing your input files, as shown in the code example below:


.. code-block:: aimms

    ret := DirectorySelect(
        directoryname :  sp_BatchExcelInputFolder, 
        directory     :  ".", 
        title         :  "Please select folder to read the .xlsx input files from.");

Use ``DirectoryGetFiles`` to get files from the specified directory name for processing, as shown in the code example below:

.. code-block:: aimms

    DirectoryGetFiles(
        directory :  sp_BatchExcelInputFolder, 
        filter    :  "*.xlsx", 
        filenames :  sp_InputFileNames, 
        recursive :  1);

Processing each of the input files
------------------------------------

Now that input files are specified, you can define how you want to process each of them, as shown in the code example below:

.. code-block:: aimms

    for i_fn do
        sp_Workbook := sp_BatchExcelInputFolder + sp_InputFileNames(i_fn);
        pr_ExecuteSingleRun( sp_Workbook );
    endfor ;

Example
-------------

:download:`Excel inputs download <downloads/ExcelInputs.zip>` 

:download:`AIMMS project download <downloads/MultiRunExcel.zip>` 

.. include:: /includes/form.def

