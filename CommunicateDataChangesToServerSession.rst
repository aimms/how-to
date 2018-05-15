CommunicateDataChangesToServerSession.rst

Communicate data changes from the data session to the server session
====================================================================

Once a solver session is started, you can send additional information using ``pro::DelegateToServer()``, provided you pass it the queue it already is listening to in the call; when ``pro::DelegateToServer`` is passed a message queue it will not start a new job, but add the enclosing procedure as a message to the existing queue. This is illustrated in the following code of ``prPassProgressSupplied``. 

	.. code-block:: none

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

The AIMMS project that does just this, can be downloaded from: :download:`9. Flow Shop - Control <Resources/AIMMSPRO/RemoveVeil/Downloads/8. Flow Shop - Control.zip>`.

 