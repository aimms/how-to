Error Handling
=================

.. meta::
   :keywords: Error Handling, Error Handler
   :description: This example illustrates the use of the error handling functionality in AIMMS.

Direct download AIMMS Project :download:`Error Handling.zip <model/Error Handling.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Error%20Handling

This example illustrates the use of the error handling functionality in AIMMS. Customized error handling is useful in situations where there is a clear distinction between the application developer and the end-user. The end-user has little or no knowledge of AIMMS and we do not want to confront them with AIMMS error messages directly. The error handling functionality of AIMMS can be subdivided into two parts, local and global handling of errors, this example illustrates both.

It can be that certain parts of your application are especially sensitive to errors. For example when using a free format data source like Excel or text files, it is possible that your end-users do not adhere to your specific data input formats. It is often too cumbersome, or even impossible, to exactly check the input files before reading. The 'Local Error Handling' page shows how you can use local error handling to catch any errors occurring during the reading of data from a text file and give a clear explanation to the end-user.

Besides these specific areas where local error handling might be useful, it is also possible that (in another area of your application) an unexpected error occurs. This example contains a generic library called 'Error Handler' that can be used in any AIMMS application to offer a user friendly way for end-users to report any unexpected AIMMS errors to the application developer. The usage of the library is described in the library node in the model tree. The 'Global Error Handling' page contains buttons to trigger various execution errors. For each execution error an error handler dialog will be opened that allows you to generate an e-mail. The e-mail contains specific information about the error, the reporter, and the AIMMS session that is used.

For a full description of the AIMMS Error Handling functionality, please see :doc:`procedural-language-components/execution-statements/raising-and-handling-warnings-and-errors` and :doc:`system-interaction/error-handling-functions/index`.

Keywords:
Error Handling, Error Handler

.. tip::
    
    You may refer to our online training about error handling `following this link <https://aimms.getlearnworlds.com/course/error-handling>`__.

