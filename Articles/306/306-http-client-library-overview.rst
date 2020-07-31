Overview: HTTP Client Library
==================================================================================================

.. meta::
   :description: An overview of the HTTP Client Library and a guide to a few use cases.
   :keywords: http, client, library, requests, api

In this article you'll find an overview of the `HTTP Client Library <https://documentation.aimms.com/httpclient/index.html>`_ itself, plus a guide to existing How-To tutorials for using the HTTP Client Library. These articles cover some possible use cases for HTTP requests to give you guidance and inspiration for your own applications.

Basic overview
------------------------------------------------------------------------------------------
AIMMS provides multiple tools to connect your models with other applications. 

The HTTP Client Library provides built-in functionality to submit HTTP requests from AIMMS.

HTTP requests are used to communicate directly with servers to call services or download online documents.

Downloading a file from the web 
------------------------------------------------------------------------------------------

The most basic use of the HTTP Client Library is to download a file from the web using AIMMS.
The process is explained in :doc:`../294/294-Online-XML-HTTP-library`.

You'll find basic explanations about HTTP requests and how to formulate them in AIMMS.


REST APIs
------------------------------------------------------------------------------------------

HTTP requests can also be used to call APIs (application programming interface).

APIs allow the use of an application through programming. `REST APIs <https://searchapparchitecture.techtarget.com/definition/RESTful-API>`_ use HTTP requests to communicate. Hence, we can use services from REST APIs directly in our model using the HTTP Client Library.

Each REST API has its own specifications and format requirements. You'll need to refer to the documentation for that API to learn what functionality the API offers and what information is required for your request.

Several examples have been detailed in the documentation. Let's review them.

Flickr APIs
^^^^^^^^^^^^^^^^^^^^^^

.. image:: images/flickr.png

Flickr is a social network platform where users can post their photos. The Flickr API allows you to explore the photos and reactions such as comments and likes. 

You'll find an example in :doc:`../298/298-use-flickr-api`.

Google APIs
^^^^^^^^^^^^^^^^^^^^^^

.. image:: images/google.png

Google provides various useful APIs, including Google Maps APIs which allow you to retrieve geographic data.

* **Distance Matrix** : The Distance Matrix API gives access to distances and durations between locations. Using this API, you'll need to extract data from the answer file using a complex XML mapping. The process is described in :doc:`../296/296-obtaining-geographic-data-through-the-google-api`.
* **Geocoding** : Using the Google Maps API, you can retrieve coordinates based on address or place name. This API deals with XSD files. You can find a step-by-step tutorial in :doc:`../302/302-get-google-maps-coordinates`.

IBM APIs
^^^^^^^^^^^^^^^^^^^^^^

.. image:: images/ibm.png

IBM provides a variety of APIs, including AI and data science services.

* **Speech To Text IBM API** : With this API, you'll be able to transform any audio file into a script text file. The process is described in :doc:`../300/300-ibm-api-speech-to-text`
* **Image recognition API**  : Given an image file, this API is able to send back the result of a machine learning recognition algorithm. You can find a tutorial in :doc:`../301/301-Image-Recognition`

Related processes
------------------------------------------------------------------------------------------

Using APIs sometimes requires the use of other supporting files and tools. We cover how to deal with some of the most common ones: XML, XSD, and AXM (a related, AIMMS-specific file); JSON files; and CURL.

XML, XSD and AXM Files
^^^^^^^^^^^^^^^^^^^^^^

Usually, after your request, the API will send back a file containing the data you requested.
**XML** is a file format designed to transport data and will often be used for this answer.


Each XML file can have a specific structure which is detailed in a **schema** file, the **XSD** file.
Often, an API will send you XML files in the same structure, and based on the same XSD schema file.

.. tip:: 

   An example of an API that does not follow the same XSD, and how to handle it, is detailed in :doc:`../302/302-get-google-maps-coordinates`.

In order to **retrieve data** from the XML file, we'll use the **XML schema mapping tool**. This AIMMS tool, given an XSD file, allows us to create links between AIMMS identifiers and XML data. This tool then generates an **AXM** file containing all the links you've created.
The process to retrieve data from an XML file is explained in :doc:`../293/293-extracting-data-from-XML`.

You can also find a more complex mapping example in :doc:`../296/296-obtaining-geographic-data-through-the-google-api`.

JSON files
^^^^^^^^^^^^^^^^^^^^^^

Another popular format file for data transfer is JSON. 
There are two ways to work with JSON files:

#.  You can convert them to an XML file as detailed in :doc:`../283/283-convert-json-to-xml`. Subsequently, you can read the XML file as outlined in the section above.

#.  You can use the `Data Exchange Library <https://documentation.aimms.com/dataexchange/index.html>`_ to read the JSON file directly.

IBM APIs use JSON files, so you can also find examples in :doc:`../300/300-ibm-api-speech-to-text` and :doc:`../301/301-Image-Recognition`.

CURL requests
^^^^^^^^^^^^^^^^^^^^^^

`CURL <https://en.wikipedia.org/wiki/CURL>`_ is a command-line tool which allows you to send HTTP requests easily. However, it requires a specific syntax that can't be used in AIMMS.
In some documentation, examples of requests are given in CURL. You can find out how to use these examples in AIMMS in :doc:`../301/301-Image-Recognition`.

Related topics
------------------------------------------------------------------------------------------

* **AIMMS** `HTTP library documentation <https://documentation.aimms.com/httpclient/index.html>`_
* **AIMMS** `Data Exchange library documentation <https://documentation.aimms.com/dataexchange/index.html>`_
* **AIMMS How-To**: :doc:`../294/294-Online-XML-HTTP-library`
* **AIMMS How-To**: :doc:`../293/293-extracting-data-from-XML`
* **AIMMS How-To**: :doc:`../298/298-use-flickr-api`
* **AIMMS How-To**: :doc:`../296/296-obtaining-geographic-data-through-the-google-api`
* **AIMMS How-To**: :doc:`../302/302-get-google-maps-coordinates`
* **AIMMS How-To**: :doc:`../296/296-obtaining-geographic-data-through-the-google-api`
* **AIMMS How-To**: :doc:`../300/300-ibm-api-speech-to-text`
* **AIMMS How-To**: :doc:`../283/283-convert-json-to-xml`

