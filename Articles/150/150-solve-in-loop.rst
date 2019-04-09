Solve in a Loop
==================

.. meta::
   :description: How to solve several instances of Excel inputs at once using a loop.
   :keywords: loop, solve, excel

A question we often hear from our users is how to solve several instances of a problem in one go. This article shows a simple example of how to solve several instances at once, using a loop. If the input data is being loaded from an external source(like an Excel file), you will need to iterate through each external source, read in the data, solve the math program and store the output. The input data can also be available in the form of an AIMMS case file or AIMMS identifiers with an index for the iteration. 

The structure of execution usually follows this format:

#. Define the collection of inputs
#. Process each input in a loop

The AIMMS project below solves a simple transportation problem iteratively, each time using an Excel file from the zipped folder provided below. 

:download:`AIMMS project download <downloads/MultiRunExcel.zip>` 

:download:`Excel inputs download <downloads/ExcelInputs.zip>` 

Logic of the iterative operation
-------------------------------------

The flow of a procedure to solve a math program multiple times is shown on the right. These operations can be done using any iterative operator like ``for`` or ``while``. The loop starts with selecting the first input file from the list of files to be iterated through. When using a ``while`` loop, you will need to initialize the iterator yourself before the loop block is written. This is not necessary in the case of a ``for`` loop as it works using a set index in AIMMS.

.. figure:: images/flow-logic.png
   :align: center
   :scale: 60 %

   Logic of the iterative operation

In the attached example, see the procedure ``pr_ExecuteBatch`` in the section ``Iterative Solve``. This procedure contains some additional error handling statements to ensure the proper working of this example.

.. code-block:: aimms

   for i_fn do
      sp_Workbook := sp_BatchExcelInputFolder + sp_InputFileNames(i_fn);
      pr_ExecuteSingleRun(sp_Workbook);
   endfor;


Comment below or reach out in our community at community.aimms.com if you have any questions. 


.. include:: /includes/form.def

