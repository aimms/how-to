How do I remove the veil while a server session is running?
===========================================================

Section Introduction
--------------------

Decision problems may take a significant time to solve. Aided by solution algorithms, AIMMS is designed to exchange data with data sources, formulate the problem and solve these problems, AIMMS WebUI is designed to present both the input and output data, and to define the interaction with the end-user, and AIMMS PRO is designed to aid the deployment of these applications.

However, for long running procedures, the AIMMS WebUI will drop a veil over the screen to indicate that user interaction is blocked. When this veil drops down, end users do not feel in control anymore. So the question then becomes: "How do I remove the veil while a server session is running?".

The answer presented here aims for a decision support app, that even in the presence of long running procedures to find good/best answers to decision problems, will:

#. Does not, or hardly, block the end user by dropping a veil, 

#. Keeps the end-user posted of the progress in finding a solution to the decision support problem at hand,

#. Allows the end-user to stop the search process, and

#. Steer the search process based on information already found.

These design goals are achieved by recognizing several hurdles, and presenting a building block to tackle each hurdle. Essentially, this allows us to divide/conquer the overall app transformation into several manageable steps.

We need a point to start the transformations; our running example.

Section 1 The flowshop problem as running example
-------------------------------------------------

The decision problem we chose as an example is the flowshop problem.
In the flowshop problem several jobs need to be handled. 
Each job needs to pass each available machine. 
The time required to solve this decision support problem depends both on the number of machines and on the number of jobs. 
The question is how to order the machines and how to order the jobs such that the total time required to process all jobs is minimal. 
This problem is detailed in https://en.wikipedia.org/wiki/Flow_shop_scheduling. 

The difficulty of a flowshop problem is indicated by the number of machines and the number of jobs. To avoid the intricacies of data handling we generate input using a random generator. This leads to the following simple interface:

.. image::  Resources/AIMMSPRO/RemoveVeil/Images/BB01_WebUI_screen.png 

where we only need to enter the number of machines and jobs and press the "Start solving the problem" button.

The AIMMS project that does just this, can be downloaded from: :download:`1. Flow Shop – WebUI – Dev version <Resources/AIMMSPRO/RemoveVeil/Downloads/1. Flow Shop - WebUI - Dev version.zip>`.

You can open this project in the AIMMS Development environment, open the WebUI, and press the solve button. After some time, the solution is presented in a Gantt Chart. You can play around with the number of Jobs and the number of Machines – be careful – a lot of machines and jobs will explode the required running(read your waiting) time.

If you want to know more about the flowshop modeling problem, please consult: https://en.wikipedia.org/wiki/Flow_shop_scheduling 

**Oops, I see a problem.**  When I want to share my example with my fellows, they all need an AIMMS developer system and license on their Windows PC to run it.

Section 2 Delegate
------------------ 

In this section, we’ll tackle the hurdle of publication – making an app available to the end-users. The approach I take is to use the AIMMS PRO platform because it integrates nicely with the WebUI interface developed.
To publish on an AIMMS PRO platform, we need to adapt the procedure that contains the solve statement.
Let’s start with the “Solve” button – this is linked to the procedure prDoSolve.

Perform the following steps:

#.	In the model: create a new procedure, named: ``prSolve``
#.	Give it the following body:

	.. code-block:: C

		if pro::DelegateToServer( waitForCompletion: 1,
			  completionCallback: 'pro::session::LoadResultsCallBack' )
			then return 1;
		endif;
		prDoSolve();


#.	Link the widget ``BtnSolve`` in the WebUI to the new procedure ``prSolve``.

When we publish this application, we can indeed solve the problem on the PRO server. 

The interface to the end-user looks almost the same, as you can see from the screen shot below.

.. image::  Resources/AIMMSPRO/RemoveVeil/Images/BB02_WebUI_screen.png 

Some of the minor differences are highlighted in the above screenshot. 
The two major differences are:

#. For the end-users there is no need for an AIMMS Developer license, including solver license, to run the application.
#. The end-users can run the app on almost any device, including tablets, Linux PC's and Windows PC's.

The AIMMS project that does just this, can be downloaded from: :download:`2. Flow Shop - Delegated <Resources/AIMMSPRO/RemoveVeil/Downloads/2. Flow Shop - Delegated.zip>`.

So the first hurdle is taken – we can share our application with our fellows!

**Oops, I see a problem.**  I want to continue working on the app itself using my development environment. Now, the system asks whether I want to solve on a PRO system, while I am clearly not connected to that PRO system – I just want to be able to test and debug my application.

Section 3 Re-enable development testing
---------------------------------------

When an app is developed, all solver licenses are incorporated in the developer license – so we do not need to worry about licensing. This implies that during development we have only one AIMMS session, and this session handles both the interaction with the user and the communication with the solver.

When an app is published, it uses a separate license to solve and a separate license to just use AIMMS and its WebUI. This implies that there are at least two AIMMS processes involved when a mathematical program is being solved.  The first one, the server session, communicates with the solver. The second one, also called the data session or client session, handles the interaction with the end-user. 

At several places in our application we need to be aware of this difference in the number of AIMMS sessions open.

#.	When invoking a solve

#.	When communicating information from the solver back to the end-user.

Therefore, we need a means to distinguish whether or not we are connected to an AIMMS PRO system.  For this purpose, I use the function ``PRO::GetPROEndPoint()``; The primary purpose of this function is to return the URL of the connected AIMMS PRO system, or the empty string if it is not connected – but we use it to just check whether or not we are connected. Therefore we change the body of prDoSolve as follows.

	.. code-block:: C

		if pro::GetPROEndPoint() then
			if pro::DelegateToServer( waitForCompletion  :  1,
				completionCallback :  'pro::session::LoadResultsCallBack' )  
			then return 1;
			endif ;
		endif ;

We’ve taken our second hurdle and can use the development environment again to test and debug our application in the AIMMS IDE.

For our end-users, there is no change in the user interface.


The AIMMS project that does just this, can be downloaded from: :download:`3. Flow Shop - Enable development testing <Resources/AIMMSPRO/RemoveVeil/Downloads/3. Flow Shop - Enable development testing.zip>`.

Let me check back what my end-users think of this idea.

**Oops, I see a problem.**  My end-users do not mind this step, but point out that they do not feel in control of the solution process as a veil is hiding the application. As they would like to point out kindly – this is weird because the solution process is happening on another machine – why cannot they use the app in the mean time to study data and previous solutions? Although my users pointed it out kindly, I sense that this is a major concern of theirs.

Section 4 remove veil
---------------------

We can remove the veil by simply removing the wait for completion argument, and the pro delegate call becomes something like the following.

	.. code-block:: C

		if pro::GetPROEndPoint() then
			if pro::DelegateToServer(  
				completionCallback :  'pro::session::LoadResultsCallBack' )  
			then return 1;
			endif ;
		endif ;

For our end-users, there is no change in the user interface.

The AIMMS project that does just this, can be downloaded from: :download:`4. Flow Shop - No Veil <Resources/AIMMSPRO/RemoveVeil/Downloads/4. Flow Shop - No Veil.zip>`.

**Oops, I see a problem.**  The results are pushed back without any notification – suddenly data is changed on screen.  How can we give control back to the end-users, such that they can determine themselves when the results are shown?

Section 5 Interactive load of results
-------------------------------------

.. comment Stash request id into a global and re-use upon button press.  
.. comment Make this button only available when such a request id is available.

So what is actually happening at the end of ``pro::DelegateToServer(...)``? It calls the callback procedure specified as its callback argument. In the previous sections, we've used the AIMMMS PRO procedure ``pro::session::LoadResultsCallBack`` for this. And this procedure performs the case load that will change the data.   

When called, the procedure ``pro::session::LoadResultsCallBack`` gets as argument a ``requestId``. This ``requestId`` is subsequently used to do a case load of the results. So if we capture this ``requestId``, we can let the user select the moment of that case load. This is achieved by writing our own callback procedure for ``pro::DelegateToServer(...)``. Let's call this callback procedure: ``myLoadResultsCallback`` and implement it as follows:
 
	.. code-block:: C

		StringParameter spSavedRequestID {
			InitialData: "";
		}
		Parameter bpResultsAvailable {
			Range: binary;
			InitialData: 0;
		}
		Procedure myLoadResultsCallback {
			Arguments: (RequestID);
			Body: {
				spSavedRequestID := RequestID;
				pResultsAvailable := 1;
			}
			StringParameter RequestID {
				Property: Input;
			}
		}
Note that is a very quick procedure; just some administration. This administration should not be confused by the load itself, that is why a NoSave property is set on the enclosing section.

And then change the call to ``pro::DelegateToServer`` by using the above callback procedure.

	.. code-block:: C

		if pro::GetPROEndPoint() then
			if pro::DelegateToServer(  
				completionCallback :  'myLoadResultsCallback' )  
			then return 1;
			endif ;
		endif ;
		
		
Now that we’ve saved the ``requestId``, we need to use it when the user presses the button to load the case.

Create a button widget, say ``BtnLoadResults``, and put the procedure ``prLoadResults`` behind it.

	.. code-block:: C

		Procedure prLoadResults {
			Body: {
				pro::session::LoadResultsCallBack(spSavedRequestID);
				spSavedRequestID := "";
				bpResultsAvailable := 0 ;
			}
		}
		
As this button only makes sense when results are available, but not downloaded yet, we control it's visibility via ``bpResultsAvailable``. The user interface when the results are available, but not yet downloaded looks as follows:

.. image::  Resources/AIMMSPRO/RemoveVeil/Images/BB05_WebUI_screen.png 

The AIMMS project that does just this, can be downloaded from: :download:`5. Flow Shop - Load Results <Resources/AIMMSPRO/RemoveVeil/Downloads/5. Flow Shop - Load Results.zip>`.

**Oops, I see a problem.** End users want to know what the best solution is and what the gap is such that they can assess whether it is worthwhile for them to wait any longer for the solution.

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

Section 7 Let the end user interrupt
------------------------------------

Interrupting the server process is easy; that can be done by calling the procedure ``pro::client::StopExecution`` in the PRO library; you’ll only need to know to:

#.	which message queue the server process is listening. In our example, we only have one server session, and the message queue, a string, can be obtained by ``pro::session::CurrentSessionQueue()``. 

#.	Whether you want to interrupt execution of the procedure, or just of a currently executing solve statement (if any).

The mechanism is illustrated in the procedure below. In our example we only interrupt the solve.

	.. code-block:: C

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

	.. code-block:: C

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

Section Summary
---------------

In this document, we illustrated the modeling process to separate the solver process and make it interactive via the following steps:

#.	Delegate to server, to let a separate process to the long running tasks, including solves.

#.	Do not wait

#.	Allow control over solution read in

#.	Provide progress info

#.	Allow to interrupt

#.	Provide further info to the server session

Together these steps allow for solver session that seem interactive by their two-way communication with the client process.
 

























