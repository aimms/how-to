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

Running the Loop on AIMMS Cloud
---------------------------------

``pro::DelegateToServer`` does not delegate **only** a solve statement. Its unit of delegation is a
**procedure**, and by default that is the procedure the call sits in. For example:

.. code-block:: aimms

   if not ProjectDeveloperMode() then
      if pro::DelegateToServer(
            waitForCompletion       :  1,
            completionCallback      :  'pro::session::LoadResultsCallBack' )
      then
         return 1;
      endif;
   endif;

   solve model1;
   pr_postProcessing;
   solve model2;

The server runs the whole procedure, so it runs ``model1``, then ``pr_postProcessing``, then ``model2``.
With ``waitForCompletion: 1`` the client blocks until that finishes.

Two lines in that fragment are easy to copy without knowing what they are for.

``if not ProjectDeveloperMode()`` keeps the same procedure usable in both places. Locally there is no
server to delegate to, so the guard skips the delegation and the body runs on the spot. Without it the
procedure only works when published.

``return 1`` stops the client. The call returns as soon as the request has been queued, and the client
still has the rest of the procedure ahead of it. Returning is what prevents the client from also running
the solves locally, duplicating on your machine the work the server was just asked to do.

Where the Call Goes
~~~~~~~~~~~~~~~~~~~~

The placement of the call, not an argument, decides the shape of the run.

**Outside the loop**, wrapping the loop itself, makes the whole loop one delegated procedure. The
instances run one after another inside a single job. This is the answer to "solve consecutively on the
cloud", and it is what the AIMMS team recommends when the runs have to be ordered.

**Inside the per-instance procedure**, called from the ``for`` loop with ``waitForCompletion: 0``,
submits each instance as its own job. Note what this does and does not mean: the requests are *queued*,
and the server executes each one when the resources it needs are free. How many actually run side by side
is up to the server, not to your loop.

If you need separate jobs *and* an order between them, that is what ``completionCallback`` is for: the
callback fires when the server finishes a request, which is the point at which the next one can be
submitted. It is more moving parts than a single delegated loop, so reach for it only when the jobs
genuinely have to be separate.

If the delegated unit should not be the enclosing procedure, name it with the ``procedureName`` argument,
which accepts any procedure currently on the execution stack.

One Input Case for Many Runs
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

By default PRO saves the whole application state before every request. For a loop over scenarios that
share their input data, that means saving the same state again for each one, which the AIMMS documentation
describes as considerable overhead in both space and time.

The ``inputCase`` argument avoids it. Pass one case reference to be used by every request and identify the
individual scenario through the arguments of the delegated procedure call instead:

.. code-block:: aimms

   if pro::DelegateToServer(
         inputCase          :  sp_sharedInputCase,
         procedureName      :  'pr_runScenario',
         waitForCompletion  :  0 )
   then
      return 1;
   endif;

It accepts either the URL of a case in PRO Central Storage or the id of an input case created by an
earlier ``pro::DelegateToServer`` call.

That split between data and arguments is not merely a preference. A delegated call travels as a PRO
message, and the documentation is explicit that such a procedure should carry adjustment parameters
rather than data, because **the cardinality of each argument has to stay below 1000 elements**. Exceeding
it behaves differently depending on where you run:

* on the AIMMS Cloud, AIMMS raises an error and the delegated procedure is aborted
* on a PRO platform on premise, AIMMS writes a warning to the PRO log files and carries on

The on-premise behaviour is the one to watch, since a loop can keep running while quietly logging that
its arguments were too large. Pass a scenario identifier and let the case carry the data.

.. note::

   ``waitForCompletion: 1`` is convenient but it blocks on a queue. A request waits until the server has
   resources for it, so a synchronous loop can spend most of its time waiting rather than solving. The
   AIMMS documentation recommends redesigning around an asynchronous workflow when requests are numerous
   or slow, which a batch loop usually is.

.. seealso::

   * `Advanced Usage of pro::DelegateToServer <https://documentation.aimms.com/pro/pro-delegate-adv.html>`__



