Section 6 Communicate progress info to the client
-------------------------------------------------

.. comment Incumbent and time callbacks – both via pro::DelegateToClient

As you know by now, we are working with two processes:

#.	The client process that manages the data of the end-user

#.	The server process that solves the actual problem

To allow viewing of progress information, the server process communicates progress information back to the client process. To realize this, the server process uses a call similar to ``pro::DelegateToServer``, but one that goes in the other direction, namely to the client: ``pro::DelegateToClient()``.

The following calls illustrate how to pass the MIP solution KPI’s bestbound and incumbent back to the client process:

Step 1, before the solve:

	.. code-block:: C

		FlowShopModel.CallbackTime := 'NewTimeCallback';

Step 2, during the solve, the procedure ``NewTimeCallbackwill`` be called. The body simply passes the best bound and incumbent to the actual working procedure:

	.. code-block:: C

		UpdateGapToClient(FlowShopModel.bestbound,FlowShopModel.Incumbent);

The procedure that contains the actual work ``UpdateGapToClient`` is specified as follows:

	.. code-block:: C

		Procedure UpdateGapToClient {
			Arguments: (bb,icb);
			Body: {
				if pro::GetPROEndPoint() then
					if pro::DelegateToClient(flags: pro::PROMFLAG_LIVE) then
						return 1; 
					endif ;
				endif ;
				SetElementAdd( Observations, epLastObservation, FormatString("%i", card(Observations)+1) );
				pBestBound(epLastObservation) := if mapval(bb) then 0 else bb endif ;
				pIncumbent(epLastObservation) := if mapval(icb) then 0 else icb endif ;
			}
			Parameter bb {
				Property: Input;
			}
			Parameter icb {
				Property: Input;
			}
		}

There are several remarks regarding the above code:

#.	The ``pro::DelegateToClient`` code is protected by ``pro::GetPROEndPoint()``; even in developer mode, we observe the gap reduction. 

#.	On line 2, there is the call ``pro::DelegateToClient`` that transfers execution of the currently running procedure from the server session to the client session. 

#.	When this procedure returns:

	#.	the execution is in the client process – we can use the values of the arguments for our progress reporting purposes.
	
	#.	the execution is in the server process – we might as well stop, progress info is not relevant as this process doesn’t have a UI to the user.

#.	On line 2 as well, we see the argument flags: ``pro::PROMFLAG_LIVE``. ``Pro::DelegateToClient`` is part of the family of procedures all starting with ``pro::DelegateTo``.  These procedures all have a flags argument, and this argument modifies the way messages are treated. The default way of message treatment is to store the message in the database of AIMMS PRO, and re-transmit the message when the receiving process connects again. The advantage is here that the message is guaranteed to arrive, provided the limits on the queue are respected. The queue has the following limits: there are at most three messages per second, and the queue length stores at most three messages. There are functions that can increase these limits to 20 messages per second and storing a hundred messages in the queue. The disadvantage is that the message takes memory space and for progress type of messages, it might be superfluous or confusing to retransmit the message. Messages are not handled before previously sent messages are handled or canceled; the message queue is a FIFO (First In First Out). The flags argument of ``PRO::DelegateToClient`` modifies this message behavior as follows:

	#.	``pro::PROMFLAG_LIVE``. The message is not stored in the database and there can only be one LIVE message at any one time.
	
	#.	``pro::PROMFLAG_PRIORITY``. The message gets priority over the other messages in the message queue. Also, when a procedure is running in the receiving process, the message invokes a procedure that is ran in between statements of the current procedure.

#.	The remaining code (the code in the body of the if pSolveInterruptable then statement) is executed in the client session. In our example, this just registers another data point in the gap curve. Caveats:

	#. Calling ``pro::DelegateToClient`` frequently will consume significant resources of the PRO platform. That is why there is a limit and the number of calls per second.  This limit is by default 3 but can be altered in the server session via the function ``pro::messaging::SetMaxMessagesPerSecond``.
	
	#. Passing large data structures via the messaging mechanism also consumes significant resources. That is why the array size of these arguments is limited to 1000 elements. If you need to pass a significant amount of information back to the client process, you are advised to store this information in a case saved on ``pro::storage`` and pass the name of that case to the client process instead of passing it via the arguments. This is not illustrated in the current example.
	
In our example, we also pass information in the incumbent back to the solution process. The same mechanism is used, and not further detailed in this answer.

The user interface when the results are downloaded now looks as follows:

.. image::  Resources/AIMMSPRO/RemoveVeil/Images/BB06_WebUI_screen.png 

The AIMMS project that does just this, can be downloaded from: :download:`6. Flow Shop - Progress Communication <Resources/AIMMSPRO/RemoveVeil/Downloads/6. Flow Shop - Progress Communication.zip>`.

**Oops, I see a problem.**  Now that end users know the state of the solution process, they also want to interrupt it when they see that further improvements are not worth waiting (long) for.