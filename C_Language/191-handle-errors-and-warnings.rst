Handle errors and warnings
=====================================
When you created an AIMMS project and let others use it, unfortunately, things hardly go as you planned. Sometimes you will have users providing the wrong kind of input for your model resulting in AIMMS throwing errors. Before AIMMS 3.10, you could only suppress these errors and check the predefined string parameter CurrentErrorMessage, which would be set by most of the intrinsic AIMMS functions.

This approach however did not let you really catch the errors and act based on the information of the error. With the introduction of AIMMS 3.10, we added error handling routines to AIMMS, allowing you to not only catch errors, but also raise your own errors.



Error handling in AIMMS can be done at two levels: you can set a global error handler procedure for your project, which will be called whenever an error occurs anywhere in your code, or you can set an error handler for a specific subset of your code that is within a block statement in AIMMS (comparable to try-catch blocks in languages like C/Java/Python). In this blog post, I will show how to use this latter approach of setting an error handler for the statements within a block statement.

All of the error handling related functionality in AIMMS lives in the scope of the predefined module ErrorHandling with the prefix errh::. The first and most important step for catching errors in AIMMS is to introduce a new element parameter that has the range errh::PendingErrors. This element parameter can be declared locally for the procedure or globally in any declaration section.

Suppose you want to calculate the division of two parameters for which the user provided the values and display the result, you can do this with the following code::

 result := parameter1 / parameter2 ; 
 DialogMessage("Result of division = " + result ) ; 

Now if the user provides the value 0 for parameter2, the execution of the first statement will result in a division-by-zero error in AIMMS, halting all further execution including the second statement. Because the execution is halted, you don't have any possibility to provide some user-friendly feedback to your user about what he/she did wrong.

Fortunately, by using the error handling, you can catch this division-by-zero error and act on it by telling the user what went wrong. You will first have to introduce an element parameter err that has the range set to errh::PendingErrors. Now the following code shows how to catch errors in AIMMS::

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
 endblock ; 