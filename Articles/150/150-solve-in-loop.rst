Solve in a Loop
==================

.. meta::
   :description: Shows how to iteratively solve multiple problem instances from Excel input files using a for loop in AIMMS, reading data and storing results in each iteration.
   :keywords: solve loop, batch solve, Excel input, for loop, while loop, iterative solve, math program, AXLL library, case file

This article provides an example of how to solve several instances of a problem at once, using a loop. 

If the input data is being loaded from an external source (like an Excel file), you should iterate through each external source, read in the data, solve the math program and store the output. The input data can also be loaded from an AIMMS case file or AIMMS identifiers with an index for the iteration. 

The structure of execution usually follows this format:

#. Define the collection of inputs
#. Process each input in a loop

The example project and Excel input files can be downloaded from the links below. 

:download:`AIMMS project download <downloads/MultiRunExcel.zip>` 

:download:`Excel inputs download <downloads/ExcelInputs.zip>` 

Logic of the iterative operation
-------------------------------------

The flow of a procedure to solve a math program multiple times is shown on the right. These operations can be done using any iterative operator like :any:`for` or :any:`while`. The loop starts by selecting the first input file from the list of files to be iterated through. 

When using a :any:`while` loop, you must initialize the iterator before the loop block is written. This is not necessary when using a :any:`for` loop because it uses a set index in AIMMS.

.. figure:: images/flow-logic.png
   :align: center
   :scale: 60 %

   Logic of the iterative operation

In the example, we use a :any:`for` loop:

.. code-block:: aimms

   for i_fn do
      sp_Workbook := sp_BatchExcelInputFolder + sp_InputFileNames(i_fn);
      pr_ExecuteSingleRun(sp_Workbook);
   endfor;

In the attached example, go to section ``Iterative Solve`` to find the procedure ``pr_ExecuteBatch``. This procedure contains some additional error handling statements to ensure the proper working of this example.

Running the Loop on PRO or Cloud
---------------------------------

``pro::DelegateToServer`` does not delegate a solve statement. It delegates the *entire procedure it sits in* -
everything after the call, however many solves and post-processing steps, runs as one PRO job in the order
written:

.. code-block:: aimms

   if not ProjectDeveloperMode() then
      if pro::DelegateToServer( waitForCompletion       :  1,
                                completionCallback      :  'pro::session::LoadResultsCallBack' )
      then
         return 1;
      endif;
   endif;

   solve model1;
   pr_postProcessing;
   solve model2;

Where the delegate block sits relative to the loop therefore decides how the work is distributed.

Placing it **inside the per-instance procedure**, called from the ``for`` loop with ``waitForCompletion: 0``,
makes each instance its own job, and all of them launch at once - the parallel case.

Placing it **outside the loop**, wrapping the loop itself, makes the whole loop one delegated procedure, so
the instances run one after another inside a single job.

There is no third option. Delegating each instance as a separate job while forcing those jobs to run
sequentially is not supported.



