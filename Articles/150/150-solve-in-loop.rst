Solve in a Loop
==================

.. meta::
   :description: How to solve several instances at once using a loop.
   :keywords: loop, solve

A question we often hear from our users is how to solve several instances of a problem in one go. We provide a simple example in this article to demonstrate how to solve a math program multiple times, changing the input data in each iteration. 

If the input data is being loaded from an external source(like an Excel file), you will need to iterate through each external source, read in the data, solve the math program and store the output. The input data can also be available in the form of an AIMMS case file or AIMMS identifiers with an index for the iteration. 

The AIMMS project below solves a simple transportation problem iteratively, each time using an Excel file from the zipped folder provided below. 

:download:`AIMMS project download <downloads/MultiRunExcel.zip>` 

:download:`Excel inputs download <downloads/ExcelInputs.zip>` 

Logic of the iterative operation
-------------------------------------

The flow of a procedure to solve a math program multiple times is shown on the right. These operations can be done using any iterative operator like ``for`` or ``while``. The loop starts with selecting the first input file from the list of files to be iterated through. When using a ``while`` loop, you will need to initialize the iterator yourself before the loop block is written. This is not necessary in the case of a ``for`` loop as it works using a set index in AIMMS.

.. figure:: images/flow-logic.png
   :align: right
   :scale: 80 %

   Logic of the iterative operation

.. code-block:: none
   :caption: for loop example

   for i in set_Iterator do

      select input_file(i);

      read from input_file(i);

      solve math_program;

      write output to input_file(i);

   endfor;

.. code-block:: none
   :caption: while loop example

   file_to_select := 1;

   while file_to_select <= number_of_files do

      #select the first file
      select input_file(file_to_select); 

      read from input_file(file_to_select);

      solve math_program;

      write output to input_file(file_to_select);

      #increase iterator file_to_select to next step
      file_to_select =+ 1; 
   
   endwhile;

In the attached example, see the procedure ``pr_ExecuteBatch`` in the section ``Iterative Solve``. This procedure contains some additional error handling statements to ensure the proper working of this example. 

Comment below or reach out in our community at community.aimms.com if you have any questions. 


.. include:: /includes/form.def

