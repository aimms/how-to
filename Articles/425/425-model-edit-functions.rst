Model Edit Functions
======================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Model Edit Functions, me::, Runtime Libraries, Excel
   :description: This example illustrates how you can use the Model Edit Functions to develop functionality that could not be developed in AIMMS without these functions. 

Direct download AIMMS Project :download:`Model Edit Functions.zip <model/Model Edit Functions.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Model%20Edit%20Functions

Runtime libraries and the AIMMS Model Edit Functions permit applications to adapt to modern flexibility requirements on the model; at runtime you can create identifiers and use them subsequently. This example illustrates how you can use the Model Edit Functions to develop functionality that could not be developed in AIMMS without these functions. 

On each demo page, a different functionality is shown. Details on these functionalities are given on the demo pages. Below you can see which functionalities, implemented with the Model Edit Functions, are illustrated in this example. 

- Emptying a group of identifiers for the indices having a certain value. The user can specify which parameters should be emptied and for which values of the indices the parameters should be emptied.

- Assigning data from one parameter to another. The user can specify the source and destination parameter, and the values of (some of) the indices that should be fixed (in case the parameters have different dimensions).

- Reading data tables from an Excel sheet where the information is in an arbitrary order. The user can specify the row, column and data ranges, and to which AIMMS indices the row/column ranges should match. Furthermore, the user can specify to which AIMMS parameter the data should be assigned.

More information about the Model Edit Functions can be found in :doc:`advanced-language-components/model-structure-and-modules/runtime-libraries-and-the-model-edit-functions`.

Keywords: 
Model Edit Functions, me::, Runtime Libraries, Excel.