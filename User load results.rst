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