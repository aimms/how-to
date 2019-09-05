HTTP client library overview
==================================================================================================

AIMMS provides multiple tools to connect your models with other applications. Following this idea, we developed the HTTP client library.

HTTP request are used to communicate with servers without using web navigators. We can then call services or download online documents.

In the How-To section from AIMMS documentation, you'll find a list of articles detailing process and tutorials for using the HTTP client library. These articles also deals with notions related to HTTP request and have for purpose to give you inspiration for your own applications.
In this overview, we'll present the different articles available and their content.

Basic application
------------------------------------------------------------------------------------------

To understand the use of the HTTP client library, the first thing to try is to download a file from the web using AIMMS.
The process is explained in the :doc:`../294/294-Online-XML-HTTP-library`

Consider this article as an introduction to the HTTP client library. You'll find there basic explanations about HTTP requests and how to formulate them in AIMMS.


REST APIs
------------------------------------------------------------------------------------------

HTTP requests can also be used to call APIs.
API( standing for *application programming interface*) are interface allowing the use of an application through programming. `REST APIs <https://searchapparchitecture.techtarget.com/definition/RESTful-API>`_ uses HTTP requests to communicate. Hence, we can use services from this REST APIs directly in our model using the HTTP client library .

Each REST API has its own specifications and a particular way to address it. So, the first thing to do before to use an API is to search for a documentation. In this documentation, you'll find what shape your request should take and what an API is able to do.

Several examples have been detailed in the documentation. Let's review them.

Flickr
^^^^^^^^^^^^^^^^^^^^^^

.. image:: images/flickr.png

Flickr is a social network platform where users can post their photos. The Flickr API then allows the user to explore the photos and reactions (comments, like...) available on Flickr. The main difficulty for using this API lays in its very dense documentation that can sometimes be tricky to explore.

You'll find a use example in : :doc:`../298/298-use-flickr-api`

Google APIs
^^^^^^^^^^^^^^^^^^^^^^

.. image:: images/google.png

Google provides various usefull APIs, inlcuding Google Map related APIs allowing us to get easily geographic data. Two AIMMS articles are detailing how to use it.

* **Distance Matrix** : The distance Matrix API gives access to distances and durations from locations to other. Using this API, you'll need to extract data from the answer file using a complex XML mapping. The process is described in :doc:`../296/296-obtaining-geographic-data-through-the-google-api`
* **Geocoding** : Using this Google Map API, you'll be able to retrieve coordinates given places address or name. Using this API, you'll need to deal with XSD file manipulation. You can find a step-by-step tutorial in :doc:`../302/302-get-google-maps-coordinates`

IBM APIs
^^^^^^^^^^^^^^^^^^^^^^

.. image:: images/ibm.png

IBM is a famous american compagny providing hardware and software. They also provides a whole variety of API with for example AI and data science services.

* **Speech To Text IBM API** : With this API, you'll be able to transform any audio file into a script text file. The process is developed in :doc:`../300/300-ibm-api-speech-to-text`
* **Image recognition API**  : Given an image file, this API is able to send back the result of a machine learning recognition algorithm. You can find a tutorial in :doc:`../301/301-Image-Recognition`

Related notions
------------------------------------------------------------------------------------------

Using APIs requires sometimes to deal with side notions you'll need to manipulate. Some of these notions are discussed in the **How-To** articles.

XML,XSD and AXM Files
^^^^^^^^^^^^^^^^^^^^^^

Most of the time, after your request, the API will send you back a file containing the data you're asking for.
**XML** is a file format designed to transport data and will often be used for this answer.


Each XML file can have a specific structure which is detailed in a **schema** file : The **.XSD** file.
It happens to not be the case, but the big majority of APIs will send you XML files respecting the same structure, hence having a same XSD schema file corresponding.

.. tip:: If it's not the case, the process to deal with that kind of API's XSD is detailed in : :doc:`../302/302-get-google-maps-coordinates`

In order to **retrieve data** from the XML file, we'll use the **XML schema mapping tool**. This AIMMS tool, given an XSD file, allows us to create links between AIMMS identifiers and XML data. This tool then generate an **AXM** file containing all the links you've created.
The process to retrieve data from an XML file is explained in : :doc:`../293/293-extracting-data-from-XML`

You can also find a more complex mapping example in : :doc:`../296/296-obtaining-geographic-data-through-the-google-api`

JSON files
^^^^^^^^^^^^^^^^^^^^^^

Another popular format file for the data transfer is JSON. Sadly, JSON files aren't yet supported in AIMMS. Hence, when an API send you back JSON files, you'll need to transform this JSON file into an XML file.
To do so, you can follow the process detailed in : :doc:`../283/283-convert-json-to-xml`

IBM APIs are using JSON files, so you can also find example in : :doc:`../300/300-ibm-api-speech-to-text` and :doc:`../301/301-Image-Recognition`.

CURL requests
^^^^^^^^^^^^^^^^^^^^^^

`CURL <https://en.wikipedia.org/wiki/CURL>`_ is a command-line tool allowing to send HTTP request easily and using a specific syntax. This syntax can't be used in AIMMS.
You'll sometimes encounter in documentation request examples in CURL. To understand how to use these examples in AIMMS, you can check an example in : :doc:`../301/301-Image-Recognition`

Related topics.  
------------------------------------------------------------------------------------------

* **AIMMS How-To**: :doc:`../294/294-Online-XML-HTTP-library`
* **AIMMS How-To**: :doc:`../293/293-extracting-data-from-XML`
* **AIMMS How-To**: :doc:`../298/298-use-flickr-api`
* **AIMMS How-To**: :doc:`../296/296-obtaining-geographic-data-through-the-google-api`
* **AIMMS How-To**: :doc:`../302/302-get-google-maps-coordinates`
* **AIMMS How-To**: :doc:`../296/296-obtaining-geographic-data-through-the-google-api`
* **AIMMS How-To**: :doc:`../300/300-ibm-api-speech-to-text`
* **AIMMS How-To**: :doc:`../283/283-convert-json-to-xml`