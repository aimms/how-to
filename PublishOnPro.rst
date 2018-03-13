How to publish and share an AIMMS application on the AIMMS PRO Platform? 
========================================================================

Introduction
------------------ 
When you want to share the AIMMS application you developed with your colleagues, they will need an AIMMS developer system and license installed on their Windows PC to be able to use the application. The AIMMS PRO Platform enables you to share your AIMMS applications without your colleagues having AIMMS installed on their PC, and even let the end users access the application in their web browser, via AIMMS WebUI. 

Running Example

We choose the popular flowshop problem as an illustrative example because the time required to solve this problem depends on the number of machines and the number of jobs. This enables us to have longer solving times as reqiored, and thereby illustrate keeping the WebUI window interactive for the user. The question is how to order the machines and how to order the jobs such that the total time required to process all jobs is minimal. More details about the problem can be found here, https://en.wikipedia.org/wiki/Flow_shop_scheduling. 

The example AIMMS project can be downloaded from :download:`1. Flow Shop – WebUI – Dev version <Resources/AIMMSPRO/RemoveVeil/Downloads/1. Flow Shop - WebUI - Dev version.zip>`.

You can open this project in the AIMMS Development environment, open the WebUI, and enter the number of machines and jobs before pressing the "Start solving the problem" button. After some time, the solution is presented in a Gantt Chart.

Publishing on the AIMMS PRO platform

To publish an application on the AIMMS PRO platform, the procedure containing the Solve statement in the model needs to be modified first. In our example, the "Start solving the problem" button is linked to the procedure "prDoSOlve".

Perform the following steps:

#.	In the model: create a new procedure, named: ``prSolve``
#.	Give it the following body:

	.. code-block:: None

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

We choose the popular flowshop problem as an illustrative example because the time required to solve this problem depends on the number of machines and the number of jobs. This enables us to have longer solving times as reqiored, and thereby illustrate keeping the WebUI window interactive for the user. The question is how to order the machines and how to order the jobs such that the total time required to process all jobs is minimal. More details about the problem can be found here, `Go here <https://en.wikipedia.org/wiki/Flow_shop_scheduling>`_