Data Feed
===========

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: External Procedure, AIMMS API, table, Linear Program
   :description: This project illustrates how an independent data feed can pass data to and/or run procedures within an AIMMS project. 

Direct download AIMMS Project :download:`DataFeed.zip <model/DataFeed.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/DataFeed

This project illustrates how an independent data feed can pass data to and/or run procedures within an AIMMS project. Such independent data feeds typically occur in financial applications, where independent data feeds make available new stock and option prices whenever these have been changed.

In this project, an independent data feed is implemented on top of a simple transport model. The data feed is implemented through a separate thread of execution in an external DLL, and is started from within the AIMMS model. 

The data feed uses the AIMMS API (or rather the Aimms Dense Library (ADL) built on top of the AIMMS API) to regularly pass new supply and demand data for the transport model and/or to re-solve the model for the newly supplied data.

The source code of the data feed DLL is provided with the example project. From it you can learn how to start a new thread of execution within an external DLL. Also, it illustrates how the data feed thread can obtain exclusive control over the AIMMS session, or, alternatively, how the thread is blocked when the end-user is modifying data or running a procedure from within the end-user interface. By grabbing the exclusive control, you can prevent other independent parties from interrupting your actions.

Keywords:
External Procedure, AIMMS API, table, Linear Program




