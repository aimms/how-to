How to stop the solver or server session?
=========================================

Introduction
------------

Assuming we have full visibility of the server session via `progress <https://how-to.aimms.com/ProgressWindowServerSession.html>`_ or `intermediate results <https://how-to.aimms.com/RetrieveIntermediateResults.html>`_ , we may decide to stop a solver running in a server session, or to stop the server session entirely. This raises the question: "How to stop the solver or server session?".

Implementation
--------------

Interrupting the server session is easy as the support for this question is quite direct by calling the procedure ``pro::client::StopExecution`` in the AIMMS PRO library; you’ll only need to know:

#.	to which message queue the server process is listening. In our example, we only have one server session, and the message queue, a string, can be obtained by ``pro::session::CurrentSessionQueue()``. 

#.	whether you want to interrupt execution of the procedure, or just of a currently executing solve statement (if any).

The mechanism is illustrated in the code snippet below. Here we only interrupt a solve statement.

	.. code-block:: none

		Procedure prInterruptSolve {
			Body: {
				if pro::GetPROEndPoint() then
					locSessionQueue := pro::session::CurrentSessionQueue();
					pro::client::StopExecution( locSessionQueue,
							 pro::AIMMSAPI_INTERRUPT_SOLVE );
				endif ;
				pSolveInterruptable := 0 ;
			}
			StringParameter locSessionQueue;
		}

You can do this from within a data session by linking this procedure to a button widget, such as ``BtnInterruptSolve`` in our example.

.. note:: if you want to stop the server session instead of just the solver, you'll need to replace the ``pro::AIMMSAPI_INTERRUPT_SOLVE`` with ``pro::AIMMSAPI_INTERRUPT_EXECUTE`` in the above example.

The user interface when the problem is being solved now looks as follows:

.. image::  Resources/AIMMSPRO/RemoveVeil/Images/BB07_WebUI_screen.png 

The AIMMS project that does just this, can be downloaded from: :download:`7. Flow Shop - Interrupt <Resources/AIMMSPRO/RemoveVeil/Downloads/7. Flow Shop - Interrupt.zip>`.

Summary
-------

The direct support for interrupts makes interrupting a solver or a server session in AIMMS PRO quite easy.

Further reading
---------------

An interrupt is only one type of communication to a server session.  A generic way to communicate data changes from the data session to the server session is provided 
`here <https://how-to.aimms.com/CommunicateDataChangesToServerSession.html>`_ .
