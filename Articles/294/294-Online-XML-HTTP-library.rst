.. warning:: 
   Please note that the documentation you are currently viewing is for an older version of our technology. 
   While it is still functional, we recommend upgrading to our latest and more efficient option to take advantage of all the improvements we've made.
   See `DEX <https://documentation.aimms.com/dataexchange/index.html>`_.

Extract XML File from a Server with the HTTP Library
=====================================================

.. meta::
   :description: Using the AIMMS HTTP library to extract an XML file.
   :keywords: xml, http library


In this article, we will create an HTTP request to extract a file from a server without an identification system using the AIMMS HTTP library.

We'll use an example XML file from `W3Schools <https://www.w3schools.com/xml/simple.xml>`_.

Definitions
-----------
An **HTTP request** is used to communicate with servers. The following information is needed for the request:

    * **Method** : The action you want the server to do. The most common methods are:

        * GET (obtaining an object)
        
        * POST (sending an object to the server)
        
        * DELETE (deleting an object from the server)
 
 
    * **URL** : The address of the server where the request should be addressed. 


    * **Headers** : The headers are specification of the request. For example, specifying the type of file you want from the server. You can find a list of possible Headers for your request in this `Wikipedia article <https://en.wikipedia.org/wiki/List_of_HTTP_header_fields>`_.


    * **Body (optional)** : Used to store data you want to send to the server, for example in a POST method request.

Extracting the XML file from the server
---------------------------------------------
At the end of this tutorial, we'll have the required XML file.

After installing the HTTP library, the procedure we'll use follows these steps:

#. Create the HTTP request
#. Specify headers
#. Set ``SP_OutputFile``
#. Invoke the request



The code should look like this:

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


Let's break down the process in more detail.

 
Installing the HTTP Library 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Our first step will be to install the HTTP library in AIMMS.

#. From the menu, go to *File > Library Manager*
#. Click *Add library from repository*
#. Select *HTTPClient*.

Creating the request
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Before we create our HTTP request, we will need some parameters to store information about the request.
  
Create three string parameters :``SP_requestURL``, ``SP_requestId``, and ``SP_OutputFile``.

Once these objects are created, we can start coding the following in a procedure.

.. code-block:: aimms
    :linenos:

    !SP_requestURL will define the aim of the request and SP_OutputFile the answer file's destination.
    SP_requestURL:="https://www.w3schools.com/xml/simple.xml";
    SP_OutputFile:="Output.xml";
    web::request_create(SP_requestId);
    web::request_setURL(SP_requestId,SP_requestURL);
    web::request_setMethod(SP_requestId, "GET");

    
The ``request_create`` function creates a request and gives it an identification number which is stored in ``SP_requestId``.
Then, set the URL for the request using ``request_setURL`` and the request method to GET using ``request_setMethod``.

Specifying headers
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

If you want to specify headers in your request, you can create a string parameter ``SP_HttpHeaders(`` indexed over ``web::httpHeader``.

.. code-block:: aimms
    
    StringParameter SP_HttpHeaders( {
        IndexDomain: web::httpHeader;
    }


Using the ``request_getHeaders`` procedure, we can extract the default settings for this request and store them in ``SP_HttpHeaders``.

.. code-block:: aimms

    web::request_getHeaders(SP_requestId, SP_HttpHeaders);

The ``web::HttpHeader`` index contains the following elements: 

	* **Accept** specifies the file format we want from the server. If not specified, it accepts every kind of data.	

	* **Accept-Encoding** indicates to the server what kind of compression you support. In AIMMS, it should always be "identity" (default value) which means that no compression is allowed.			

	* **Authorization** contains identification information required to connect to the server. The identification can also be done through a parameter in the URL address, depending on the server security.

	* **Cache-Control**  specifies directives for caching mechanisms in both requests and responses.

	* **Content-Length**  indicates the size of the request body sent to the server in bytes.

	* **Content-Type** indicates the real type of the resource sent in the request body.

	* **Transfer-Encoding** tells about the form of encoding used to safely transfer the answer body to the user.
   
	* **Location** is an answer Header and shouldn't be specified. In case of redirection, store the URL where the request must be redirected.


For this example we'll set ``Accept`` to XML in order to show the process.

Now that we have access to these headers, we need to change their values and set them back to the request.

.. code-block:: aimms
    :linenos:

    SP_HttpHeaders(['Accept'] := "application/xml";
    web::request_setHeaders(SP_requestId, SP_HttpHeaders();

Here, we tell the server we only want XML files.

Setting the SP_OutputFile
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: aimms

    web::request_setResponseBody(SP_requestId, 'File', SP_OutputFile);

The ``request_setResponseBody`` second argument can either be ``'File'`` (to get the response body) or ``'None'`` (to ignore the response body).
The response body represents the data the server gives in response to your request. Use this method to specify where the data should be stored.

When you send a request to a server, it gives you back an answer containing a status code. Here, this code is stored in a parameter called ``P_responseCode``.

.. note::

    Learn more about these status codes at `REST API Tutorial (external link) <https://www.restapitutorial.com/httpstatuscodes.html>`_.

Invoking the request
^^^^^^^^^^^^^^^^^^^^^^^^^^
We're finally ready to send our request using the ``web::request_invoke`` procedure.

.. code-block:: aimms

    web::request_invoke(SP_requestId,P_responseCode);


Congratulations, you should now have your XML file stored as ``output.xml`` in the project folder!

If you now want to link the XML data with AIMMS, please follow the tutorial :doc:`../293/293-extracting-data-from-XML` .

Example project
------------------

You can download the example AIMMS project below: 

* :download:`HttpFood.zip <download/HttpFood.zip>` 

* A variation, whereby the data from a JSON file is read to AIMMS identifiers: :download:`HttpFoodIntegrated.zip <download/HttpFoodIntegrated.zip>` 


Related Topics
---------------
* **AIMMS How-To**: :doc:`../293/293-extracting-data-from-XML`
* **AIMMS Documentation**: `HTTP client library <https://documentation.aimms.com/httpclient/index.html>`_
* **AIMMS Documentation**: `Add a library to your model <https://documentation.aimms.com/httpclient/library.html#adding-the-http-client-library-to-your-model>`_
* **AIMMS Documentation**: `Data exchange library <https://documentation.aimms.com/dataexchange/index.html>`_


References
------------------------------------

* `TutsPlus, about HTTP <https://code.tutsplus.com/tutorials/http-the-protocol-every-web-developer-must-know-part-1--net-31177>`_
* `Mozilla, about HTTP Headers <https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers>`_






