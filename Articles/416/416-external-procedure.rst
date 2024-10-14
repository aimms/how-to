External Procedure
=====================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: External procedure, DLL
   :description: This model illustrates a very simple external procedure call, along with the C source of the external function called from within the model.

Direct download AIMMS Project :download:`External Procedure.zip <model/External Procedure.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/External%20Procedure

This model illustrates a very simple external procedure call, along with the C source of the external function called from within the model. This function computes the average of a dense array, which corresponds to the contents of a two-dimensional identifier in the AIMMS model that is passed to it as an argument.

The C source code illustrates how the dense, linear, array must be interpreted as a two-dimensional data structure, given the number of elements for each dimension. It also illustrates how a function can be exported by the DLL in such a manner that AIMMS can find it runtime.

If the sizes of each dimension of a multi-dimensional identifier are rather large, you are strongly advised to pass the data in a sparse manner using the AIMMS API, rather than passing dense arrays. In such cases, data tends to be rather sparse (i.e. not many elements are non-default), and passing dense arrays would require an exuberant amount of memory.

Keywords:
External procedure, DLL


