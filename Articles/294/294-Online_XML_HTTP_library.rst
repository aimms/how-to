Loading an online XML file using the HTTP library
==================================================================================================


**Summary**


In this article, we will learn how to extract a file from a server without identification system using the AIMMS's HTTP library.
To do so, we will need to deal with HTTP request.
In this example, we'll download an XML file from the W3schools website: https://www.w3schools.com/xml/simple.xml


.. note:: An **HTTP request** is used to communicate with servers. Here are some information you could need to understand what's following:

    * **the method** : It represents the action you want the server to do. The more common methods are:

        * GET (obtaining an object),
        
        * POST(sending an object to the server), and 
        
        * DELETE(deleting an object from the server)
 
 
    * **the URL** : it represents the address of the server where the request should be addressed. 


    * **The Headers** : The headers are specification of the request. It can for example be about specifying the type of file you want from the server. You can find a list of possible Headers for your request `there <https://en.wikipedia.org/wiki/List_of_HTTP_header_fields>`_


    * **the body (optional)** : It is used to store data you could want to send to the server, for example for a POST method request.

Extracting the XML file from the server.
---------------------------------------------

At the end of this tutorial, we'll have the required XML file stored into our computer.
The final code should look like this but every part will be detailed in what's following:

.. code-block:: aimms
    :linenos:
    
    requestURL:="https://www.w3schools.com/xml/simple.xml";
    OutputFile:="Output.xml";
    
    !Create the request and set the URL
    web::request_create(requestId);
    web::request_setURL(requestId,requestURL );
    web::request_setMethod(requestId, "GET");
    
    !header
    web::request_getHeaders(requestId, HttpHeaders);
    HttpHeaders['Accept'] := "application/xml";
    web::request_setHeaders(requestId, HttpHeaders);
    
    !Set the Output file and invoke the request.
    web::request_setResponseBody(requestId, 'File', OutputFile);
    !invoke method
    web::request_invoke(requestId,responseCode);
 
Installing the HTTP Library 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Our first step will be to install the HTTP library in AIMMS.
From the "file" section at the upper-left corner of your AIMMS window, follow this path:
**file>Library Manager**
**click on add library from repository>**
**select HTTPClient>select>OK>yes**.

Create the request and set the URL
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

In order to create our request, we will need some parameters. They'll be used to store informations about the request.
  
First, you can create three String parameters :``requestURL``, ``requestId`` and ``OutputFile``.
Once these objects are created, we can start coding.

.. code-block:: aimms
    :linenos:

    !requestURL will define the aim of the request and OutputFile the answer file's destination.
    requestURL:="https://www.w3schools.com/xml/simple.xml";
    OutputFile:="Output.xml";
    web::request_create(requestId);
    web::request_setURL(requestId,requestURL);
    
    
The ``request_create`` function does exactly what its name tells us: it creates a request and gives it an identification number now stocked in ``requestId``.
Then, we are setting the URL for the request using `request_setURL`.

The headers.
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

If you want to specify header  in your request, you can create a string parameter ``HttHeaders`` with for domain ``web::HttpHeaders``.
Using the ``request_getHeaders`` method, we can extract the default settings for this request and stock them into ``HttpHeaders``.

.. code-block:: aimms
    :linenos:

    web::request_getHeaders(requestId, HttpHeaders);

The ``web::HttpHeader`` is composed of the data [Accept, Accept-Encoding, Authorization, Cache-Control, Content-Length, Content-Type, Transfer-Encoding, Location].

Let's review those headers:

	* **Accept** specify the file format we want from the server. If not specified, accept every kind of data.

			

	* **Accept-Encoding** indicate to the server what kind of compression you support. In AIMMS, it should always be "identity" (default value) which means that no compression are aloud.
   
			

	* **Authorization** contains the identification informations required to connect to the server. The identification can also be done through a parameter in the URL address, depending on the server security.



	* **Cache-Control**  specify directives for caching mechanisms in both requests and responses.



	* **Content-Length**  indicates the size of the request body sent to the server in bytes.
    


	* **Content-Type** indicates the real type of the resource sent in the request body.



	* **Transfer-Encoding** tells about the form of encoding used to safely transfer the answer body to the user.


   
	* **Location** is an answer Header and shouldn't be specified. In case of redirection, store the URL where the request must be redirected.


.. note:: Most of these headers doesn't need to be touched and should only be used to specify your demand. Here, we wouldn't usually need to change anything to the default headers, but for this time we'll set the accept header to xml in order to show the process.

Now that we have access to these headers, we need to change their values and set them back to the request.

.. code-block:: aimms
    :linenos:

    HttpHeaders['Accept'] := "application/xml";
    web::request_setHeaders(requestId, HttpHeaders);

Here, we tell the server we only want xml files.

Set the Output file, invoke the request.
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: aimms
    :linenos:

    web::request_setResponseBody(requestId, 'File', OutputFile);

The request_setResponseBody second argument can either be ``'File'``(you want to get the response body) or ``'None'``(the response body will be ignored).
The response body represents the data the server is giving you in response for your request. By using this method, you specify where these data must be stored.

When you send a request to a server, it gives you back an answer containing a code. This response code tells you about the success of the request. `here <https://www.restapitutorial.com/httpstatuscodes.html>`_ you can learn about the meaning of codes.
in Aimms, we will store this code into a parameter called here ``responseCode``.

We're finally ready to send our request using the ``request_invoke method``.

.. code-block:: aimms
    :linenos:

    web::request_invoke(requestId,responseCode);


Congratulation, you should now have your XML file stored as ``output.xml`` in the project folder!

.. note:: If you now want to link the XML data with AIMMS, please follow the tutorial :doc:`../293/293-extracting_data_from_XML` .

The project :download:`AIMMS project download <download/HttpFood.zip>` 



References
------------------------------------


`About HTTP <https://code.tutsplus.com/tutorials/http-the-protocol-every-web-developer-must-know-part-1--net-31177>`_

`About HTTP Headers <https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers>`_

`AIMMS HTTP client library documentation <https://documentation.aimms.com/httpclient/index.html>`_

`add a library to your model: <https://documentation.aimms.com/httpclient/library.html#adding-the-http-client-library-to-your-model>`_

:doc:`../293/293-extracting_data_from_XML`


