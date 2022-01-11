
.. meta::
   :description: How to set up data exchange within your AIMMS application.
   :keywords: aimms, data, exchange

How to use the Data Exchange Library for extracting files and data 
====================================================================

In `this how-to <https://how-to.aimms.com/Articles/528/528-how-to-set-up-data-exchange-basics.html>`__ we've learned how to set up the Data Exchange Library (DEX) so we can integrate data from a JSON-file into identifiers based on a predefined mapping file. Now, let's pretend this JSON-file is available somewhere on the internet and we want to retrieve its data for processing using the DEX. In `this how-to <https://how-to.aimms.com/Articles/294/294-Online-XML-HTTP-library.html>`__, we've done the same thing with the HTTP Library. We will describe the differences between the two methods as we will go through the steps.


Prerequisites
--------------

#. Make sure you have the Data Exchange Library installed and that it is updated to the most recent version. Visit `this article <https://documentation.aimms.com/general-library/getting-started.html>`__ for instructions on how to do this.

#. If you want the full procedure to work, make sure you have the mapping file ready and placed in your project and correctly implemented in the procedure. See this how-to for more instructions. 

#. Just as a reminder, the data file that we will implement has JSON-formatted data that looks like this:

.. code-block:: json

    [
		{
			"city": "Qal eh-ye Now",
			"city_ascii": "Qal eh-ye",
			"country": "Afghanistan",
			"iso2": "AF",
			"iso3": "AFG",
			"lat": 34.98300013,
			"lng": 63.13329964,
			"pop": 2997.0,
			"province": "Badghis"
		},
		{
			"city": "Chaghcharan",
			"city_ascii": "Chaghcharan",
			"country": "Afghanistan",
			"iso2": "AF",
			"iso3": "AFG",
			"lat": 34.5167011,
			"lng": 65.25000063,
			"pop": 15000.0,
			"province": "Ghor"
		}
	]

.. note::

        All functions from the DEX Library are referenced and described on `this page <https://documentation.aimms.com/dataexchange/api.html>`__.
		

Difference HTTP / DEX
--------------------------

Let's look at the setup to retrieve a file using the HTTP Library. This block of code comes from the HttpFoodIntegrated example as can be found in `this how-to <https://how-to.aimms.com/Articles/294/294-Online-XML-HTTP-library.html>`__, in which we retrieve a publicly available XML-file:

.. code-block:: aimms
    :linenos:
    
    SP_requestURL:="https://www.w3schools.com/xml/simple.xml";
    SP_OutputFile:="Output.xml";
    
    !Create the request and set the URL
    web::request_create(SP_requestId);
    web::request_setURL(SP_requestId,SP_requestURL );
    web::request_setMethod(SP_requestId, "GET");
    
    !header
    web::request_getHeaders(SP_requestId, SP_HttpHeaders();
    SP_HttpHeaders(['Accept'] := "application/xml";
    web::request_setHeaders(SP_requestId, SP_HttpHeaders();
    
    !Set the Output file and invoke the request.
    web::request_setResponseBody(SP_requestId, 'File', SP_OutputFile);
    !invoke method
    web::request_invoke(SP_requestId,P_responseCode);

As you can see, we have to build the request using three different methods (create, setURL and setMethod). After that we have to set the header separately on lines 10, 11 and 12 before we specify that we expect an output file as response on line 15. On line 18 we invoke the request and from line 21 we will check the response. The result of running the procedure is that the file 'Output.xml' is being placed in the project folder.

Using the DEX, this same procedure can be shortened to using only three methods. For readability we've set every parameter on a separate line and first we will try to retrieve the same file as in the HTTP example:

.. code-block:: aimms
    :linenos:
    
    dex::client::NewRequest(
	"getFile",
	"https://www.w3schools.com/xml/simple.xml",
	'DEXCallback',
	responsefile:"Output.xml"
	);


	dex::client::PerformRequest(
	"getFile"
	);

	dex::client::WaitForResponses(
	1000
	);

On line 1 we build the newRequest by setting the needed parameters (name of the request, the URL, the callback method and the optional parameter for the responseFile since we want to retrieve a file). An important note to make here is that we need to set up a callback procedure for the response to be stored and processed in. In our example we've simply copied the prototype for ``dex::EmptyCallback`` as it is available in the library, pasted it into our main project and gave it a more logical name. Running the procedure 'pr_dexRequest' will now give the same result as the previously described procedure: Output.xml is placed in the project folder.



Retrieving & processing JSON
------------------------------

Now that we've seen how to retrieve a file, let's try to retrieve and process the JSON-file with the cities in it in the project that we've also used for `this how-to <https://how-to.aimms.com/Articles/528/528-how-to-set-up-data-exchange-basics.html>`__. Here we see the procedure, where we read the JSON-file from a folder called 'data' within the project folder:

.. code-block:: aimms
    :linenos:
    
    dex::AddMapping(
	"WorldCitiesMapping",
	"Mappings/Generated/worldCities-TableWorldCities-JSON-Sparse.xml",
	);


	dex::ReadFromFile(
	"data/simplemaps-worldcities-basic-short.json",
	"WorldCitiesMapping",
	1,
	1,
	1,
	);

We have made the JSON-file available :download:`here <download/simplemaps-worldcities-basic.json>` so we can use this URL to access the file directly. The easiest implementation would therefore be to use the direct URL in the ``dex::ReadFromFile``:

.. code-block:: aimms
    :linenos:
    
    dex::AddMapping(
	"WorldCitiesMapping",
	"Mappings/Generated/worldCities-TableWorldCities-JSON-Sparse.xml",
	);


	dex::ReadFromFile(
	"<url comes here>",
	"WorldCitiesMapping",
	1,
	1,
	1,
	);


Reading from saved file
^^^^^^^^^^^^^^^^^^^^^^^^^^^

Let's pretend this is not possible or not desirable and we want to retrieve the file first before we use it in ``dex::ReadFromFile``. For this to work we can use the same procedure as described in the previous paragraph (including adding the callback procedure) and will set the parameters according to our needs:

.. code-block:: aimms
    :linenos:
    
    dex::AddMapping(
	"WorldCitiesMapping",
	"Mappings/Generated/worldCities-TableWorldCities-JSON-Sparse.xml"
	);

	dex::client::NewRequest(
	"getFile",
	"<url comes here>",
	'DEXCallback',
	responsefile:"Output.json"
	);


	dex::client::PerformRequest(
	"getFile"
	);

	dex::client::WaitForResponses(
	1000
	);

	dex::ReadFromFile(
	"Output.json", 
	"WorldCitiesMapping", 
	1, 
	1, 
	1
	);
	
You will see that running this procedure loads the data from the JSON into the identifiers.

Reading from memory stream
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Another way to do this is by using the file as a `memory stream <https://documentation.aimms.com/dataexchange/api.html#memory-streams>`_, with the advantage that the file isn't saved locally and you also have the option to delete the memory stream after the request is closed (using '##' instead of '#'). If you are using the singular # (thus not deleting automatically), you can re-use the example given in the previous paragraph and simply add the hashtag before the name of the responsefile:

.. code-block:: aimms
    :linenos:
    
    dex::AddMapping(
	"WorldCitiesMapping",
	"Mappings/Generated/worldCities-TableWorldCities-JSON-Sparse.xml"
	);

	dex::client::NewRequest(
	"getFile",
	"<url comes here>",
	'DEXCallback',
	responsefile:"#Output.json"
	);


	dex::client::PerformRequest(
	"getFile"
	);

	dex::client::WaitForResponses(
	1000
	);

	dex::ReadFromFile(
	"#Output.json", 
	"WorldCitiesMapping", 
	1, 
	1, 
	1
	);
 
If you want to use the memory stream that will delete itself after the request however, you will need to place the ``dex::ReadFromFile`` within the created callback procedure. This way you ensure that the request (and the file in the memory stream) still exists at the moment of processing it.



.. spelling::

    DEX
	JSON-file
	setURL
	setMethod
	newRequest
	responseFile
	responsefile
	dexRequest
	hashtag
	