Transform an audio file into Text : an introduction to IBM APIs
==================================================================================================

This article contains an overview of what you need to know about IBM APIs, and a short example using the Text-To-Speech API.

IBM APIs
-----------------------------------------------

IBM is an American company known for its hardware and software, but IBM also provides a lot of APIs on its `cloud <https://cloud.ibm.com/apidocs>`_. Those API forms a large panel of services with different offers, free or not, depending on the API and the use case.
By clicking on an API name, you'll be redirected to its documentation where you will find all the specification for this API. 

If you want to know more about an API, you can also search the name of your API in the IBM `catalog <https://cloud.ibm.com/catalog>`_. 

.. image:: images/apiinfo.png

You'll then find informations about the API, what it is used for, pricing and using the **Create** button, you'll be redirected to a **Getting started** tutorial.
By clicking on the **Manage** page, you'll be able to get your API key and an URL endpoint for calling the API.

**The HTTP request**

To use these APIs, you will need to formulate HTTP requests, and the documentation provides you some requests examples for each API.
But those request examples are written using different client libraries (CURL,.NET,Go...), hopefully they can also be written in the HTTP client library from AIMMS.
if you want to see a complete example about formulating CURL requests in AIMMS, you can check this article : :doc:`../301/301-Image-Recognition`

**Authentication**

To access these APIs, it's required to authenticate in the request.

.. image:: images/authentication.png

The system used is Identity and Access Management (`IAM <https://cloud.ibm.com/docs/services/watson?topic=watson-iam>`_) authentication, a token-based system. The authentication is then done through the ``Authentication`` request header using a token or an API key. If you use an API key, you must follow the format of a `basic authentication <https://en.wikipedia.org/wiki/Basic_access_authentication>`_

**Answers**

The result sent back from a IBM API will most likely be a JSON file.
JSON is a file format used to transfer data, but AIMMS doesn't support JSON.
So, You'll need to convert this JSON file into an XML file if you want to be able to extract those data into AIMMS.

Short example
-----------------------------------------------
We will here use the Speech-To-Text API from IBM. By sending an audio file, we'll be able to obtain the script of this video.

Prerequisites  
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

* Obtain your own API key  by creating an IBM account and click on **sign up to Create** in `this page <https://cloud.ibm.com/catalog/services/speech-to-text>`_
* Download the audio file :download:`here <download/Space Shuttle Enterprise.zip>` and unzip it at the root of your project folder.
* install the AIMMS HTTP Client library following `this tutorial <https://documentation.aimms.com/httpclient/library.html#adding-the-http-client-library-to-your-model>`_ .

Example project
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

You can find the completed project example :download:`here <download/SpeechToText.zip>` but you'll still need to specify your own API key.

The Speech-to-text conversion
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The finale code is the following one:

.. code-block:: aimms
    :linenos:
    
    ! indicate source and destination file
    SP_requestFileName := "Space Shuttle Enterprise.mp3";
    SP_responseFileName := "answer.json";
    SP_apikey:="YOUR_API_KEY";


    !given on the IBMCloud website
    SP_requestURI := "https://gateway-lon.watsonplatform.net/speech-to-text/api/v1/recognize?continuous=true";

    web::request_create(SP_requestId);
    web::request_setURL(SP_requestId, SP_requestURI);
    web::request_setMethod(SP_requestId, "POST");
    web::request_getHeaders(SP_requestId, SP_myHttpHeaders);
    !Authentication for the server
    web::base64_encode( "apikey" + ":" + SP_apikey, SP_authorization);
    SP_myHttpHeaders[ 'Authorization' ] := "Basic " + SP_authorization;
    web::request_setHeaders(SP_requestId, SP_myHttpHeaders);
    web::request_setRequestBody(SP_requestId, 'File', SP_requestFileName);
    web::request_setResponseBody(SP_requestId, 'File', SP_responseFileName);
    web::request_getOptions(SP_requestId,SP_Coption);
    SP_Coption['requestTimeout'] := "50"; 
    web::request_setOptions(SP_requestId, SP_Coption);
    web::request_invoke(SP_requestId, P_responseCode);


and for that, we'll need to set those identifiers:

.. code-block:: aimms
    :linenos:

    Parameter P_responseCode;
    StringParameter SP_Coption {
        IndexDomain: op;
    }
    Set S_Clientop {
        Index: op;
    }
    StringParameter SP_requestId;
    StringParameter SP_requestURI;
    StringParameter SP_myHttpHeaders {
        IndexDomain: web::httpHeader;
    }
    StringParameter SP_responseFileName;
    StringParameter SP_requestFileName;
    StringParameter SP_apikey;
    StringParameter SP_authorization;

In this article, we'll not develop every part of this code because most of it is common for every HTTP request in AIMMS, but if you want to know more about it, you can check this article :doc:`../294/294-Online-XML-HTTP-library` .

**The authentication header**

Based on the instruction for a `basic authentication <https://en.wikipedia.org/wiki/Basic_access_authentication>`_, we need to set our `Authentication` header to *basic username:password*. Here, the username is "apikey" and the password the key value. Both of these strings must be base64-encoded.
To do so, we use the following code:
 
.. code-block:: aimms
    :linenos:

    SP_apikey:="YOUR_API_KEY";

    !getting the headers
    web::request_getHeaders(SP_requestId, SP_myHttpHeaders);
    
    !encoding the string "apikey : {API_KEY}" in base64
    web::base64_encode( "apikey" + ":" + SP_apikey, SP_authorization);

    !setting the Authorization header to "basic"+ encoded string
    SP_myHttpHeaders[ 'Authorization' ] := "Basic " + SP_authorization;

    !set back the new header for the request
    web::request_setHeaders(SP_requestId, SP_myHttpHeaders);

**The options**

Beside the headers, there is another way to set characteristics for the request: the options.
By looking at the `HTTP client library documentation <https://manual.aimms.com/httpclient/api.html>`_ we learn that the options contains only one option to set that is the ``request Timeout``.
In some case like here, the API treatment can be too long for the request Timeout to be respected. In that case, you can set more time for the request to execute using this option.

.. code-block:: aimms
    :linenos:
    
    web::request_getOptions(SP_requestId,SP_Coption);
    SP_Coption['requestTimeout'] := "50"; 
    web::request_setOptions(SP_requestId, SP_Coption);

By executing the complete code you should be able to retrieve your JSON file in the ``SP_responseFileName`` direction or at the root of your project.


JSON to XML
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

We now have access to our data in the format JSON and we want them in an XML file.
For that, download :download:`this archive <download/JSONXML.zip>`. Then, extract the two folders at the root of your project and install the library by installing the XMLJSON folder using the library manager.
This process is detailed in the following article : :doc:`../301/301-Image-Recognition`

This library contains 2 methods allowing the conversion between JSON and XML:

* ConvertFromJsonToXML
* ConvertFromXMLToJson

Then, to get your xml file you have to execute the following code:

.. code-block:: aimms
    :linenos:
    
    jxml::ConvertFromJsonToXML("Answer.json","Answer.xml");

If you want to extract the data from your newly created XML file, you can check how to :doc:`../293/293-extracting-data-from-XML`

Related topics. 
-----------------------------------------------

* **AIMMS How-To**: :doc:`../294/294-Online-XML-HTTP-library`
* **AIMMS How-To**: :doc:`../301/301-Image-Recognition`
