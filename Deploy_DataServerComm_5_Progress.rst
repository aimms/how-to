How to communicate progress info from the server session to the data session?
================================================================================

Introduction
------------

While a solve procedure is running, we are curious about the estimated time left for completion - are we going to get the results back soon or is there enough time to go grab a cup of coffee? In essence, we want to keep track of the progress of the solve procedure. We can do this by using the Progress Window (Ctrl + P) in the Developer mode, and this article will show you how to present this progress information to the end user of a WebUI application.

Analysis
--------

In the context of the running example: the Flowshop model, we are passing information through three levels of execution:

#. The solver execution, as part of the server session. The solver passes status information on, on a regular basis, as part of the time callback mechanism.

#. The AIMMS execution as part of the server session, called via the time callback mechanism of the solver, and retrieves relevant information.

#. The AIMMS execution as part of the data session. Here a procedure is called, and information passed via arguments, from the server session to the data session.

.. image:: Resources/AIMMSPRO/Deploy_DataServerComm_3_RemoveVeil/Images/ThreeLevelsOfExecution.png

The implementation of the information stream represented by the two upper arrows will be discussed in the next section. 
The bottom two arrows (Incumbent Callback and Intermediate Solution) will be discussed in a separate article.

Implementation
--------------

There are two steps to communicate the information from the first to the third level. 
Let's discuss each of these two steps in more detail.

Step 1. (Time callback) From Solver (level 1) to server session (level 2)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Step 1A illustrates how to prepare the mathematical program FlowShopModel, such that the MIP solver will pass progress information on a regular basis to the model.

Step 1A, before the solve:

	.. code-block:: none

		FlowShopModel.CallbackTime := 'NewTimeCallback';
		option progress_time_interval := 1 ;

Step 1B, during the solve, the procedure ``NewTimeCallback`` is called. 
In our example, the progress information consists of the MIP solution progress information: best bound and incumbent.
The body simply passes the best bound and incumbent to the actual working procedure:

	.. code-block:: none

		UpdateGapToClient(FlowShopModel.bestbound,FlowShopModel.Incumbent);
		
Step 2. (Progress Update) From server session (level 2) to data session (level 3)	
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To give this answer an extra dimension, we are storing each of the observations in the following data structure 

	.. code-block:: none

		Set Observations {
			Index: iObs;
			Parameter: epLastObservation;
		}
		Parameter pBestBound {
			IndexDomain: iObs;
		}
		Parameter pIncumbent {
			IndexDomain: iObs;
		}
		
and visualize that in the following curve widget:

 .. image:: Resources/AIMMSPRO/Deploy_DataServerComm_5_Progress/Images/CurveMIP_GAP.PNG

To implement the communication from the server session to the data session, the server session uses a call similar to ``pro::DelegateToServer``, but one that goes in the other direction, namely to the client: ``pro::DelegateToClient()``.
The procedure that contains the actual work, namely ``UpdateGapToClient``, is specified as follows:

	.. code-block:: none

		Procedure UpdateGapToClient {
			Arguments: (bb,icb);
			Body: {
				if pro::GetPROEndPoint() then
					if pro::DelegateToClient(flags: pro::PROMFLAG_LIVE) then
						return 1; 
					endif ;
				endif ;
				SetElementAdd( Observations, epLastObservation, FormatString("%i", card(Observations)+1) );
				pBestBound(epLastObservation) := if mapval(bb)  then 0 else bb  endif ;
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

	*. 0	the execution is in the client process – we can use the values of the arguments for our progress reporting purposes.
	
	*. 1	the execution is in the server process – we might as well stop, progress info is not relevant as this process doesn’t have a UI to the user.

#.	On line 2 as well, we see the argument flags: ``pro::PROMFLAG_LIVE``. ``Pro::DelegateToClient`` is part of the family of procedures all starting with ``pro::DelegateTo``.  These procedures all have a flags argument, and this argument modifies the way messages are treated. The default way of message treatment is to store the message in the database of AIMMS PRO, and re-transmit the message when the receiving process connects again. The advantage is here that the message is guaranteed to arrive, provided the limits on the queue are respected. The queue has the following limits: there are at most three messages per second, and the queue length stores at most three messages. There are functions that can increase these limits to 20 messages per second and storing a hundred messages in the queue. The disadvantage is that the message takes memory space and for progress type of messages, it might be superfluous or confusing to retransmit the message. Messages are not handled before previously sent messages are handled or canceled; the message queue is a FIFO (First In First Out). The flags argument of ``PRO::DelegateToClient`` modifies this message behavior as follows:

	#.	``pro::PROMFLAG_LIVE``. The message is not stored in the database and there can only be one LIVE message at any one time.
	
	#.	``pro::PROMFLAG_PRIORITY``. The message gets priority over the other messages in the message queue. Also, when a procedure is running in the receiving process, the message invokes a procedure that is ran in between statements of the current procedure.

#.	The remaining code, the last three lines of ``UpdateGapToClient`` is only executed in the data session. In our example, this code just registers another data point in the gap curve. Caveats:

	#. Calling ``pro::DelegateToClient`` frequently will consume significant resources of the PRO platform. That is why there is a limit and the number of calls per second. This limit is by default 3 but can be altered in the server session via the function ``pro::messaging::SetMaxMessagesPerSecond``.
	
	#. Passing large data structures via the messaging mechanism also consumes significant resources. That is why the array size of these arguments is limited to 1000 elements. If you need to pass a significant amount of information back to the client process, you are advised to store this information in a case saved on ``pro::storage`` and pass the name of that case to the client process instead of passing it via the arguments. This is illustrated in `How to retrieve intermediate results from a server session to the data session <https://how-to.aimms.com/RetrieveIntermediateResults.html>`_ .
	
The user interface when the results are downloaded now looks as follows:

.. image:: Resources/AIMMSPRO/Deploy_DataServerComm_3_RemoveVeil/Images/BB06_WebUI_screen.png 

The AIMMS project that does just this, can be downloaded from: :download:`6. Flow Shop - Progress Communication <Resources/AIMMSPRO/Deploy_DataServerComm_3_RemoveVeil/Downloads/6. Flow Shop - Progress Communication.zip>`.

Summary
-------

By using the procedure ``pro::DelegateToClient`` we can set up communicating information from the server session to the data session. The usual use case of passing progress information is illustrated in this answer.

Continued reading
-----------------

Now that end users know the state of the solution process, they also want to interrupt it when they see that further improvements are not worth waiting for. This is handled in `How to interrupt a solve while WebUI is active during a solve <https://how-to.aimms.com/Deploy_DataServerComm_7_Interrupt.html>`_ .



.. include:: includes/form.def
 