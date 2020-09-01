Handle errors and warnings
===========================

.. meta::
   :description: Error handling provides for a way of making your applications robust.
   :keywords: Error handling, throwing exceptions, guarded code, happy flow

.. note::

    This article was originally posted to the AIMMS Tech Blog.

Errors in AIMMS
------------------

Errors might inevitably pop up during the runtime of your production models. Users may provide the wrong kind of input for your model unexpectedly resulting in AIMMS throwing errors that your user may not understand, and that you might not have foreseen. 

AIMMS handles routines that allow you to not only catch existing AIMMS errors, but also raise your customized AIMMS errors.

Error handling in AIMMS can be done at two levels: 

* You can set a global error handler procedure for your project, which will be called whenever an error occurs anywhere in your code. 
* You can set an error handler for a specific subset of your code that is within a block statement in AIMMS (comparable to try-catch blocks in languages like C/Java/Python). 

This article focuses on the second approach.

Example use case
----------------------

In the example below, we will calculate the division of two parameters for user input values and display the result:

.. code-block:: aimms

 result := parameter1 / parameter2 ; 
 DialogMessage("Result of division = " + result ) ; 

Let's assume the user provides the value 0 for ``parameter2``, and execution of the first statement produces a division-by-zero error in AIMMS. That means the execution is halted, including the second statement. 

Handling Errors
----------------

With the error handling, you can catch this division-by-zero error and tell the user what went wrong. 

Error handling functionality in AIMMS is in the scope of the predefined module ``ErrorHandling`` with the prefix ``errh::``. 

First, introduce a new element parameter with range ``errh::PendingErrors``. This element parameter can be declared locally for the procedure or globally in any declaration section.

Once that's done, you can catch errors and provide custom error messages:

.. code-block:: aimms

    block
        result := parameter1 / parameter2 ; 
        DialogMessage("Result of division = " + result ) ; 
   
    onerror err do
        !If any runtime errors occur in the statements executed in the above 
        !block, AIMMS will execute the statements in this error handling block
        
        !Tell the user that there is an error in the input
        DialogMessage("Make sure parameter2 is unequal to 0 to prevent division by 0",
                      "Caught error in input") ; 
        
        !Now tell AIMMS that we have handled the error. If we skip this statement
        !AIMMS will consider the error as unhandled, meaning it will go to the next
        !layer in the list of error handlers. If no error handler marks the error
        !as handled, the result is the same as without error handling: AIMMS will
        !display the error in the error window and halt further execution.
        errh::MarkAsHandled(err , 1) ; 
        
        !If you want, you can use the other functionality in the errh module to
        !get more information about the error, for example the message that would
        !be printed to the error window:
        DialogMessage("Error message = \n" + errh::Message( err ) ) ; 
        
        !You can also show the user whether the error that was caught was an 
        !error or just a warning:
        DialogMessage("Severity = " + errh::Severity( err ) ) ;     
    end block ; 

Now you have full control on what is shown to your user if anything goes wrong.


Related resources
--------------------

.. seealso::

  * :doc:`../265/265-settings-for-errors-and-warnings`

  * :doc:`Error handling example model <../../examples/functional/error-handling/>`
  
  * Chapter "Raising and handling warnings and errors" of the `AIMMS Language Reference <https://download.aimms.com/aimms/download/manuals/AIMMS3LR_ExecutionStatements.pdf>`_

