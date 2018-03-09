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

We choose the popular flowshop problem as an illustrative example because the time required to solve this problem depends on the number of machines and the number of jobs. This enables us to have longer solving times as reqiored, and thereby illustrate keeping the WebUI window interative for the user. The question is how to order the machines and how to order the jobs such that the total time required to process all jobs is minimal. More details about the problem can be found here, `Go here <https://en.wikipedia.org/wiki/Flow_shop_scheduling>`_