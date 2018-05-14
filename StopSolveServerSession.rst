How to interrupt the server session?
==================================== 

Introduction
------------

Assuming we have visibility of the server session via `progress <https://how-to.aimms.com/ProgressWindowServerSession.html>`_ or `intermediate results <https://how-to.aimms.com/RetrieveIntermediateResults.html>`_, we may decide to interrupt the server session. This answer takes you through the steps to interrupt a solve process or to interrupt a server session from the client side. 

.. the actual difference between the solver and the server session is not clear. 
.. Handled, distinction made later.

.. Changed the last sentence from "This raises the question "How to stop the solver or server session?" to avoid repetition as the question is already the title of the page.

Background
-----------



Implementation
--------------

Interrupting the server session is very direct and can be done by simply calling the AIMMS PRO library procedure, ``pro::client::StopExecution`` . However,  you will need to know

.. please explain what are the different message queues, and what should the user know about them ? 


#.	to which message queue is the server process listening? In our example, we have only one server session, and the message queue - a string, can be obtained by using ``pro::session::CurrentSessionQueue()``. 

#.	whether you want to interrupt execution of the procedure, or just of a currently executing solve statement (if any).

The mechanism is illustrated in the code snippet below. Here we interrupt only a solve statement.

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


.. a brief explanation in words for the procedure would be good. This is only giving a copy - paste example and not teaching me anything

You can do this from within a data session by linking this procedure to a button widget, such as ``BtnInterruptSolve`` in our example.

.. note:: If you want to stop the server session instead of the solver, you will need to replace ``pro::AIMMSAPI_INTERRUPT_SOLVE`` with ``pro::AIMMSAPI_INTERRUPT_EXECUTE`` in the above code.

Now, the user interface when the problem is being solved looks as follows.

.. image::  Resources/AIMMSPRO/RemoveVeil/Images/BB07_WebUI_screen.png 

The example AIMMS project with the above demonstrated procedures can be downloaded from :download:`7. Flow Shop - Interrupt <Resources/AIMMSPRO/RemoveVeil/Downloads/7. Flow Shop - Interrupt.zip>`.

Summary
-------

The direct support for interruptions makes interrupting a solver or a server session in AIMMS PRO very easy.

Further reading
---------------

An interruption is only one type of communication to a server session.  A generic way to communicate data changes from the data session to the server session is provided `here <https://how-to.aimms.com/CommunicateDataChangesToServerSession.html>`_ .
