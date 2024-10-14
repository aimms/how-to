Linked Objects
=================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Linking objects, network object
   :description: This demo illustrates data objects in the graphical user interface can be linked together through the use scalar element parameters.

Direct download AIMMS Project :download:`Linked Objects.zip <model/Linked Objects.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Linked%20Objects

This demo illustrates data objects in the graphical user interface can be linked together through the use scalar element parameters.

The current value of an index associated with particular (multidimensional) data displayed in an object, can be automatically assigned to a scalar element parameter by specifying a Forward Link in the Contents tab of the object's Properties dialog box. A Forward Link is indicated by an arrow '->'.

Whenever a Forward Link has been specified, AIMMS will automatically update the value of the element parameter whenever the focus within the object moves to another value of the associated index. By displaying data that is sliced over this the element parameter in other objects, the data in such objects will by automatically updated as soon as the value of the element parameter has changed because of a different selected value in the original object. Thus, you have effectively created a link between objects.

You can examine how the linkage between various objects on the Example Page is accomplished by observing the Contents tab of the Properties dialog boxes of the respective objects.

Keywords:
Linking objects, network object

