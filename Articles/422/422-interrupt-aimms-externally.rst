Interrupt AIMMS Externally
===========================

.. meta::
   :keywords: AIMMS Component, AIMMSCOM
   :description: How to interrupt an AIMMS session from outside AIMMS.

Direct download AIMMS Project :download:`Interrupt AIMMS Externally.zip <model/Interrupt AIMMS Externally.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Interrupt%20AIMMS%20Externally

In this example it is demonstrated how an AIMMS session can be interrupted from outside AIMMS. This is especially useful in situations where AIMMS (or AIMMSCOM) is controlled programmatically without any user interface, which means that the normal short cut for interruption (<ctrl><shift><s>) is not available.

.. note:: The AIMMSCOM functionality has been deprecated in favor of the AIMMS API and AIMMS PRO API!

The call to interrupt the execution can be given from any application on this machine, like a separate program or via a separate thread in the controlling application (in case AIMMS/AIMMSCOM is controlled externally). In this example, we have chosen to interrupt the AIMMS session via an external application.

Once you have clicked on the button "Start Execution", AIMMS will increase "Counter" until it becomes 30. This can be seen in the data object on this page. The execution can be interrupted by executing the program "InterruptProgram.exe" that can be found in the "Interrupt AIMMS project" folder. This folder can be opened via the Open Explorer button on the left bar before you click on the "Start Execution" button.



Keywords:
AIMMS Component, AIMMSCOM


