Section 7 Let the end user interrupt
------------------------------------

Interrupting the server process is easy; that can be done by calling the procedure ``pro::client::StopExecution`` in the PRO library; you’ll only need to know to:

#.	which message queue the server process is listening. In our example, we only have one server session, and the message queue, a string, can be obtained by ``pro::session::CurrentSessionQueue()``. 

#.	Whether you want to interrupt execution of the procedure, or just of a currently executing solve statement (if any).

The mechanism is illustrated in the procedure below. In our example we only interrupt the solve.

	.. code-block:: None

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

You’ll only need to link this procedure to a button widget, such as ``BtnInterruptSolve`` in our example.

The user interface when the problem is being solved now looks as follows:

.. image::  Resources/AIMMSPRO/RemoveVeil/Images/BB07_WebUI_screen.png 

The AIMMS project that does just this, can be downloaded from: :download:`7. Flow Shop - Interrupt <Resources/AIMMSPRO/RemoveVeil/Downloads/7. Flow Shop - Interrupt.zip>`.

**Oops, I see a problem.**  We did a lot of work in communicating information between client and server session above. This will introduce overhead. This overhead is worthwhile if there is someone watching and interacting with the solver, but if no one bothers; then why do it? 

Section 8 communicate control changes to the server session
-----------------------------------------------------------

Once a solver session is started, you can send additional information using ``pro::DelegateToServer()``, provided you pass it the queue it already is listening to in the call; when ``pro::DelegateToServer`` is passed a message queue it will not start a new job, but add the enclosing procedure as a message to the existing queue. This is illustrated in the following code of ``prPassProgressSupplied``. 

	.. code-block:: None

		Procedure prPassProgressSupplied {
			Arguments: (pwbs);
			Body: {
				if pro::GetPROEndPoint() then
					locSessionQueue := pro::session::CurrentSessionQueue();
					if pro::DelegateToServer(requestQueue: locSessionQueue,
							flags: pro::PROMFLAG_LIVE + 
									 pro::PROMFLAG_PRIORITY) then
						return 1;
					endif ;
				endif ;
				pProgressWillBeSupplied := pwbs ;
			}
			StringParameter locSessionQueue;
			Parameter pwbs {
				Property: Input;
			}
		}
		
Note the use of the flag ``pro::PROMFLAG_PRIORITY`` ; this flag indicates that this procedure call should be executed during the running of the existing procedure.

The user interface when the problem is being solved now looks as follows:

.. image::  Resources/AIMMSPRO/RemoveVeil/Images/BB08_WebUI_screen.png 

The AIMMS project that does just this, can be downloaded from: :download:`8. Flow Shop - Control <Resources/AIMMSPRO/RemoveVeil/Downloads/8. Flow Shop - Control.zip>`.

**Oops, I see a problem.**  They are selling my favorite ice cream over there. I cannot resist...