.. include:: includes/icons.def

How to retrieve intermediate results from a server session to the data session?
===============================================================================

Introduction
------------

During a long running solver session (job), we may compute intermediate results that we want to show to the end user as soon as they are available. Consider the following use cases:

#. Actually, the job submitted contains multiple decision problems, all of which are solved in one batch. Why wait for providing the solution of the first decision problem, while the job is already working on the second decision problem?

#. The optimization of a significant Mixed Integer Problem will compute several intermediate incumbents, and perhaps these incumbents are worth visualizing and further study.

#. By showing intermediate solutions, the end user may decide that the last shown solution is sufficient and the search in the job can be stopped.

The AIMMS Pro system allows the solver session to call back to the data session to pass progress information, as presented in `How to display solve progress info in WebUI  <https://how-to.aimms.com/ProgressWindowServerSession.html>`_. There are two important differences with progress information:

#. In the callbacks, there is a limit on the amount of data that can be passed over the arguments back to the data session in one call. This limit is too small to pass any solution of significant size.

#. In the above presented use cases, we typically like to retain these intermediate results for later viewing as well. 

Therefore we have the question: "How to retrieve intermediate results from a server session to the data session?".

Difference with passing progress information
--------------------------------------------

The answer to the above question, will be discussed here as a variation to the  `How to display solve progress info in WebUI  <https://how-to.aimms.com/ProgressWindowServerSession.html>`_ answer; the differences are:

#. The intermediate solutions will be stored such that they can be retrieved upon demand.

#. There is no limit to the amount of information passed back to the data session.

#. The messages about the presence of the intermediate solutions are guaranteed to arrive at the data session; even if the data session is temporarily unavailable.


Approach
--------

The approach taken here is to share each intermediate result as a single case on AIMMS PRO storage, and message the filename from the server session to the data session.

Both the data session and the server session have access to AIMMS PRO storage. The following image illustrates how AIMMS PRO storage is organized:

.. image:: Resources/AIMMSPRO/RetreiveIntermediateResults/Images/Default-folder-layout-of-AIMMS-PRO-Storage.png
	:align: center

We will use the folder ``pro:/UserData/<environment>/<User>/Cases/<app>/`` on AIMMS PRO storage. 

In the remainder of this answer, we follow here more or less the flow as the "progress" answer:

Method used
-----------

In the context of the running example: the flowshop model, we are passing information through three levels of execution:

#. The solver execution, as part of the server session. The solver passes the entire solution that constructs a new incumbent, as part of the incumbent callback mechanism.

#. The AIMMS execution as part of the server session, called via the incumbent callback mechanism of the solver, and retrieves the solution information.

#. The AIMMS execution as part of the data session. Here a procedure is called, and information passed via a case file, from the server session to the data session. On the arguments of the procedure are the relevant filenames only.

Implementation
--------------

There are two steps to communicate the information from the first to the third level. 
Let's discuss each of thes two steps in more detail.

Step 1. From Solver (level 1) to server session (level 2)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

Step 1A illustrates how to prepare the mathematical program FlowShopModel, such that the MIP solver will pass solution information on availability basis to the model.

Step 1A, before the solve:

	.. code-block:: none

		FlowShopModel.CallbackNewIncumbent := 'NewIncumbentCallback';

Step 1B, during the solve, the procedure ``NewIncumbentCallback`` is called. 
In our example, the solution information consists of all variables.

	.. code-block:: none

		Block ! Transfer the solution from the solver to AIMMS
			empty JobSchedule;

			RetrieveCurrentVariableValues(AllVariables);
			TimeSpan := FlowShopModel.Incumbent;
		Endblock ;
		
Convert the solutions in the variables to a solution that is used in the Gantt Chart (GC solution).

	.. code-block:: none

		prepInterface();

The identifiers in the Gantt Chart solution, ``pGCJobStart`` and ``pGCJobDuration``, form a subset of identifiers:
		
	.. code-block:: none

		Set sIncumbentSolutionIdentifiers {
			SubsetOf: AllIdentifiers;
			Definition: data { pGCJobStart, pGCJobDuration };
		}
		
Transfer the GC solution from AIMMS to a case.		

	.. code-block:: none

		Block 
			AllCaseFileContentTypes += 'sIncumbentSolutionIdentifiers' ;
			CurrentCaseFileContentType := 'sIncumbentSolutionIdentifiers' ;
			spCaseFileName := FormatString( "Incumbent%i.data", pIncumbentNumber );
			pIncumbentNumber += 1 ;
			spFullCaseFileName := "data/" + spCaseFileName ;
			CaseFileSave( spCaseFileName, sIncumbentSolutionIdentifiers );
		Endblock ;

Store the case file on PRO storage:		


	.. code-block:: none	

		! Transfer the case from the data folder of the server session to the AIMMS PRO storage user data folder.
		Block ! Transfer the GC solution from AIMMS to a case.
			spFullProStorageName := "pro:/userdata/" + pro::GetPROEnvironment() + 
								"/" + pro::GetPROUserName() + "/Cases/" + pro::ModelName + "/" + spCaseFileName ;
			Pro::SaveFileToCentralStorage(spCaseFileName, spFullProStorageName );
		Endblock ;

The body simply passes the filenames where the solution is stored to the actual working procedure:

	.. code-block:: none

		! Notify the data session that a 
		UpdateIncumbentToClient(spFullCaseFileName, spFullProStorageName);
		
		
Step 2. From server session (level 2) to data session (level 3)	
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	.. code-block:: none	

		Procedure UpdateIncumbentToClient {
			Arguments: (spArgFullProStorageName);
			Body: {
				if pro::GetPROEndPoint() then
					if pro::DelegateToClient(flags: 0) then
						return 1; 
					endif ;
				endif ;
				
				! From here on, only the client (data) session is running.
				
				CaseFileLoad( spArgFullProStorageName );
				
				! Comment out the next line if you want to retain intermediate solutions.
				pro::DeleteStorageFile( spArgFullProStorageName );
				
				noIncumbents+=1 ;
			}
			StringParameter spArgFullProStorageName {
				Property: Input;
			}
		}


A copy of the flowshop model that is the result of this answer: :download:`Flow Shop - share intermediate <Resources/AIMMSPRO/RetreiveIntermediateResults/Downloads/Flow Shop - share intermediate - after.zip>`.


.. comment::Let's start with an example that is being used elsewhere in `How To <https://how-to.aimms.com>`_  as well.
.. comment::.. warning:: **Todo** URL for end result "remove veil" - "share progress info" needs to be inserted here.
.. comment:: Flowshop model - before sharing intermediate results with data session.
.. comment:: Flow Shop - share intermediate - after
.. comment:: Flow Shop - share intermediate - before

.. image:: Resources/AIMMSPRO/RemoveVeil/Images/BB07_WebUI_screen.PNG


Summary
-------

By providing the intermediate answers via AIMMS cases, we can retain those answers and allow the end user to select the ones he actually wants to view.






