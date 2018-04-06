How to load the results of a server session at a convenient moment?
===================================================================

Introduction
------------

When the ``waitForCompletion`` argument of ``pro::DelegateToServer`` is 0, both the data session and the server session run in parallel. This allows the end user to browse and modify data while the server session is running in the background. However, at the end of the server session, the results are loaded back in to the data session. This may happen unannounced which is not a good user interface design. This article shows you how to control the loading back of server session results manually. 

Why is this happening?
----------------------

Let's take a look at the typical ``pro::DelegateToServer`` call:

	.. code-block:: none

		if pro::GetPROEndPoint() then
			if pro::DelegateToServer(  
				completionCallback :  'pro::session::LoadResultsCallBack' )  
			then return 1;
			endif ;
		endif ;

So what is actually happening at the end of ``pro::DelegateToServer(...)`` on server session side? Then the callback procedure, here ``pro::session::LoadResultsCallBack``, is called data session side. This procedure just loads the data from the output case created by the server session,identified by its argument ``requestId``. 

.. The above paragraph seems incomplete, especially the first sentence. It is not clearly explaining the backgroun process in words. Also, requestId is not mentioned anywhere before, and is used in an explanation

Approach taken
--------------

If we capture this ``requestId``, we can let the user control when to load the data of that particular case file. To do this, follow the below steps

#. Stash the ``requestId`` in a string parameter via a simple administration procedure

#. Ensure that this administration procedure is invoked by ``pro::DelegateToServer(...)``

#. Create a procedure that loads the data

#. Link that procedure to a button.

In more detail, the steps are as follows:

Step 1. Stash the ``requestID``
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This is achieved by writing our own callback procedure for ``pro::DelegateToServer(...)``. Let's call this callback procedure: ``myLoadResultsCallback`` and implement it as follows:
 
	.. code-block:: none

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

Note that is a very quick procedure; just some administration. This administration should not be confused by the load itself, that is why a ``NoSave`` property is set on the enclosing section. 

Step 2. Ensure that this administration procedure is invoked by ``pro::DelegateToServer(...)``
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

And then change the call to ``pro::DelegateToServer`` by using the above callback procedure.

	.. code-block:: none

		if pro::GetPROEndPoint() then
			if pro::DelegateToServer(  
				completionCallback :  'myLoadResultsCallback' )  
			then return 1;
			endif ;
		endif ;
		
		
Now that we’ve saved the ``requestId``, we need to use it when the user presses the button to load the case.


Step 3. Create a procedure that loads the data
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Create a button widget, say ``BtnLoadResults``, and put the procedure ``prLoadResults`` behind it.

	.. code-block:: none

		Procedure prLoadResults {
			Body: {
				pro::session::LoadResultsCallBack(spSavedRequestID);
				spSavedRequestID := "";
				bpResultsAvailable := 0 ;
			}
		}
		
Step 4. Link that procedure to a button
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

As this button only makes sense when results are available, but not downloaded yet, we control it's visibility via ``bpResultsAvailable``. The user interface when the results are available, but not yet downloaded looks as follows:

Resulting app
-------------

The inferface of the resulting app now looks as follows on AIMMS PRO:

.. image::  Resources/AIMMSPRO/RemoveVeil/Images/BB05_WebUI_screen.png 

The AIMMS project that does just this, can be downloaded from: :download:`5. Flow Shop - Load Results <Resources/AIMMSPRO/RemoveVeil/Downloads/5. Flow Shop - Load Results.zip>`.

Summary
-------

The answer provided by the server session, via the solution case, can be loaded at a moment convenient to the end user. This just requires a bit of administration and an additional button. 

Further opportunities
---------------------

There are now several opportunities for further improvement:

#. End users may want to keep track of the progress of the solution process, especially the gap is interesting. In `How to display solve progress info in WebUI  <https://how-to.aimms.com/ProgressWindowServerSession.html>`_ we show how to do this.
   
#. The contents of intermediate solutions can be interesting for the data session at hand. In `How to retrieve intermediate results from a server session to the data session <https://how-to.aimms.com/RetrieveIntermediateResults.html>`_ we show how to copy selected intermediate results from the server session to the data session.

#. Once the solution is "good enough for now", the end user may want to abort the server session.  In `How to interrupt a solve while WebUI is active during a solve <https://how-to.aimms.com/StopSolveWithoutVeil.html>`_ we will show how to interrupt the server session.

