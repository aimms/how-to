Load Server Session Results Manually
========================================

.. meta::
   :description: How to load results of a server session on demand in AIMMS PRO.
   :keywords: server, session, results

Introduction
------------

If the ``waitForCompletion`` argument of ``pro::DelegateToServer`` is 0, both the data session and the server session run in parallel. This allows the end user to browse and modify data while a delegated procedure is executed on the server session in the background. However, at the end of execution, the results are loaded back in to the data session without any warning. This unannounced loading of data is not a good UI design. This article presents the approach to manually control this data transfer between the data and server sessions. 

What is happening here?
-----------------------

A typical ``pro::DelegateToServer`` call looks as below:

	.. code-block:: aimms

		if pro::GetPROEndPoint() then
			if pro::DelegateToServer(  
				completionCallback :  'pro::session::LoadResultsCallBack' )  
			then return 1;
			endif ;
		endif ;

Any procedure written after the above call will be executed on the server session and the results are stored as a data casefile with a certain ``RequestID``, a predefined AIMMS identifier. If the `completionCallback` argument is the predefined  procedure ``pro::session::LoadResultsCallBack``, the aforementioned casefile is loaded onto the data session automatically. ``pro::session::LoadResultsCallBack`` has ``RequestID`` as a local input argument.

If you want to manually trigger this loading of results, you need to know the ``RequestID`` of the casefile you want to load, a procedure to load this casefile, and a button to run this procedure. The appraoch needed is summarized below:

#. Retrieve the ``RequestID`` in a string parameter using a simple assignment procedure.

#. Define the `completionCallback` argument of ``pro::DelegateToServer`` call as this procedure to trigger the retrieval. 

#. Create a procedure to load the casefile corresponding to the retrieved ``RequestID`` and link it to a button on the user interface. 

Step 1.  Retrieving the ``RequestID``
-------------------------------------

Create a string parameter ``spSavedRequestID`` to store the ``requestId`` and a binary parameter ``bpResultsAvailable`` to control the visibility of the load button in the user interface. 
 
	.. code-block:: aimms

		StringParameter spSavedRequestID {
			InitialData: "";
		}
		Parameter bpResultsAvailable {
			Range: binary;
			InitialData: 0;
		}

Now, create an assignment procedure ``myLoadResultsCallback`` with a local input argument ``RequestID`` to update the values of these identifiers. 

	.. code-block:: aimms

		Procedure myLoadResultsCallback {
			Arguments: (RequestID);
			Body: {
				spSavedRequestID := RequestID;
				bpResultsAvailable := 1;
			}
			StringParameter RequestID {
				Property: Input;
			}
		}

When run, this procedure simply stores ``RequestID`` in ``spSavedRequestID`` and updates ``bpResultsAvailable`` to 1. This should not be mistaken with the actual loading procedure.

.. Note that is a very quick procedure; just some administration. This administration should not be confused by the load itself, that is why a ``NoSave`` property is set on the enclosing section. 

Step 2. Provide `completionCallback` argument of the ``pro::DelegateToServer`` call
------------------------------------------------------------------------------------

Now, we need to trigger the assignment procedure ``myLoadResultsCallback`` when a solved casefile is available on the server session. This is done by providing ``myLoadResultsCallback`` as the `completionCallback` argument.

	.. code-block:: aimms

		if pro::GetPROEndPoint() then
			if pro::DelegateToServer(  
				completionCallback :  'myLoadResultsCallback',
				waitForCompletion : 0 )  
			then return 1;
			endif ;
		endif ;
		
		
Step 3. Create a procedure and a button to load the data
---------------------------------------------------------

Create a procedure ``prLoadResults`` and link it to a button widget, say ``BtnLoadResults``. The body of ``prLoadResults`` is as follows;

	.. code-block:: aimms

		Procedure prLoadResults {
			Body: {
				pro::session::LoadResultsCallBack(spSavedRequestID);
				spSavedRequestID := "";
				bpResultsAvailable := 0 ;
			}
		}

We are executing the predefined procedure ``pro::session::LoadResultsCallBack`` to load the casefile on the data session, but with our own argument ``spSavedRequestID`` instead of the default argument. After the results are loaded, we also empty the ``spSavedRequestID`` and ``bpResultsAvailable`` to hide the load results button. This last emptying step is not necessary; but best practice - do not show buttons that are not available anyway.

We want to control the visibility of ``BtnLoadResults`` because it makes sense for it to show up only when results are available to load. This appearance acts as a notification for the end user that results are available. The user interface when the results are available, but not yet downloaded looks as follows:

.. image::  images/BB05_WebUI_screen.PNG 

The AIMMS project that does just this, can be downloaded from: :download:`5. Flow Shop - Load Results <downloads/5. Flow Shop - Load Results.zip>`.

Summary
-------

By following the above steps, the end user can control when the casefile resulting from an execution on the server session is loaded onto the data session (or available to view by the end user).

Further opportunities
---------------------

There are now several opportunities for further improvement:

#. End users may want to keep track of the progress of the solution process, especially the gap is interesting. In :doc:`../35/35-web-ui-progress-window` we show how to do this.
   
#. The contents of intermediate solutions can be interesting for the data session at hand. In :doc:`../36/36-intermediate-solution` we show how to copy selected intermediate results from the server session to the data session.

#. Once the solution is "good enough for now", the end user may want to abort the server session.  In :doc:`../34/34-interrupt-server-session` we will show how to interrupt the server session.


.. include:: /includes/form.def
 