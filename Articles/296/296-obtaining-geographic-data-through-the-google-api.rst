Request Geographic Data with Google Maps API
==================================================================================================

.. meta::
   :description: Using Google Maps API to return geographic data to an AIMMS project.
   :keywords: google, 

In this article, we will learn how to use the HTTP Library to obtain data from a Google map API. Those data will be distances between different places.


Use Case
--------

Let's say we're planning a road trip in Europe and we want to visit some very famous places:

* The Atomium in Brussels, Belgium

* The Eiffel tower in Paris, France

* The AIMMS office in Haarlem, Netherlands

* The Colosseum in Roma, Italy

* The Alhambra in Granada, Spain

We'll use the Distance Matrix API from Google Maps to calculate important information for our trip. We want to know:

* Distances between locations

* Travel time from one location to another


Prerequisites
--------------

Before we begin, there are a couple things you'll need to have in place.

* Install the HTTP library, please read `AIMMS Documentation: Adding the HTTP Client Library <https://documentation.aimms.com/httpclient/library.html#adding-the-http-client-library-to-your-model>`_.

* Get a Google API key. Get one from `Google Maps Platform: Get an API Key <https://developers.google.com/maps/documentation/geolocation/get-api-key>`_.


API requirements
-----------------------------------------------
Specifically, we need information about the authentication required and the request format.

.. removed image (images/MandatoryParameters.PNG)

As indicated in `Google Maps Developer Guide: Required Parameters <https://developers.google.com/maps/documentation/distance-matrix/intro#required-parameters>`_, these are required parameters for the request:

* ``origins`` — The starting point for calculating travel distance and time. You can supply one or more locations separated by the pipe character (|), in the form of an address, latitude/longitude coordinates, or a place ID.
* ``destinations`` — One or more locations to use as the finishing point for calculating travel distance and time. The options for the destinations parameter are the same as for the origins parameter, described above.
* ``key`` — Your application's API key. This key identifies your application for purposes of quota management.

Specify these parameters by putting them at the end of the request URL: 

.. removed image (images/RequestFormat.PNG)
    
As indicated in `Google Maps Developer Guide: Distance Matrix Requests <https://developers.google.com/maps/documentation/distance-matrix/intro#DistanceMatrixRequests>`_ the format should look like this:

.. raw:: html

    <p><style="background:gray"><font color="blue">https://maps.googleapis.com/maps/api/distancematrix/</font><font color="red">outputFormat</font><font color="blue">?</font><font color="red">parameters</font></style></p>


Note that while the Google Maps API supports both JSON and XML outputs, AIMMS only supports XML, so ``outputFormat`` will be ``xml``.

With the mandatory parameters, the URL would look like this:

.. raw:: html

    <p><font color="blue">https://maps.googleapis.com/maps/api/distancematrix/xml?</font><font color="red">destinations=</font><font color="black">DESTINATIONS</font><font color="blue">&</font><font color="red">key=</font><font color="black">KEY</font><font color="blue">&</font><font color="red">origins=</font><font color="black">ORIGINS</font></p>

The API contains plenty of other optional parameters, such as the distance unit or the method to calculate travel duration. Learn about the available options for your request in the `Google Maps Developer Guide <https://developers.google.com/maps/documentation/distance-matrix/intro>`_.

Creating the request
-----------------------------------------------

You can see an example request in the code below.

.. code-block:: aimms
    :linenos:
    
    APIkey:="YOUR_API_KEY";
    responseFile:="Output.xml";
    TotalParameters:=DATA{origins,destinations,key,mode,language,region,avoid,units,arrival_time,departure_time,traffic_model,transit_mode,transit_routing_preference};
    !Set the Locations
    ListOrigins:="Eiffel Tower, Pari | The atomium in Brussels | AIMMS Haarlem | Coliseum, Roma Italy | The alhambra, granada spain";
    ListDestinations:="Eiffel Tower, Paris | The atomium, Brussels | AIMMS Haarlem | Coliseum, Roma Italy | The alhambra, granada spain";
    !Set the parameters we want to use
    requestparameters:= DATA{key, origins, destinations};
    requestparameters['key']:=APIkey;
    requestparameters['origins']:=ListOrigins;
    requestparameters['destinations']:=ListDestinations;
    !Set the request
    web::query_format(requestparameters,formattedparameters);
    URL:="https://maps.googleapis.com/maps/api/distancematrix/xml?"+formattedparameters;
    web::request_create(requestId);
    web::request_setMethod(requestId,"GET");
    web::request_setURL(requestId,URL);
    web::request_setResponseBody(requestId,'File',responseFile);
    web::request_invoke(requestId,responsecode);

Most of this code is common for every HTTP request sent using the HTTP Client Library, and will not be reviewed in this article. Read more about how to formulate an HTTP request in AIMMS in :doc:`../294/294-Online-XML-HTTP-library`.

To begin, you'll need to create some objects for setting up the request:

.. image:: images/RequestObjects.PNG

.. code-block:: aimms
    :linenos:
    
    Set TotalParameters {
        Index: tp;
        DATA:{origins,destinations,key,mode,language,region,avoid,units,arrival_time,departure_time,traffic_model,transit_mode,transit_routing_preference};
    }
    StringParameter requestId {
    
    }
    StringParameter requestparameters {
        IndexDomain: tp;
    }
    Parameter responsecode {
    
    }
    StringParameter APIkey {
    
    }
    StringParameter ListOrigins {
    
    }
    StringParameter URL {
    
    }
    StringParameter responseFile{
    
    }
    StringParameter formattedparameters {
    
    }
    


The set ``TotalParameters`` contains all possible parameters available for the API, required or optional.

Following the API documentation, we create two strings storing the different origins and destinations separated by "|" characters. The choice to use location names instead of coordinates is arbitrary. We want the total distance matrix between locations, so each location is in ``ListOrigins`` and ``ListDestinations``. 

.. code-block:: aimms
    :linenos:
    
    !Set the Locations
    ListOrigins:="Eiff Tower, Paris | The atomium is in Brussels | AIMMS Haarlem | Roma Italy Coliseum | The alhambra, granada spain";
    ListDestinations:="Eiffel Tower, Pari | The atomium, Brussels | AIMMS Haarlem | Coliseum, Roma Italy | The alhambra, granada spain";

.. tip::

    There is no need to be meticulous in these location definitions. Purposely, we've included some errors and format variations in the location names to illustrate that Google Maps can guess the corrections to make in most cases. 

.. todo: When talking about advantage, and doing other comparisons, it is required for scientific articles to be explicit as to what you are comparing to.  For "How To" articles, it is not required, but I think it is good practice. Anyway, I do not see the purpose of a comparison here. Perhaps you can reformulate to something like: "Google API helps you here because it is resilient against typos".

.. important:: 

    The API can has tiers of billing plans with various usage limits. See `Google Maps Developer Guide: Usage and Billing <https://developers.google.com/maps/documentation/distance-matrix/usage-and-billing#distance-matrix>`_


.. code-block:: aimms
    :linenos:
    
    !Setting the TotalParameters set
    TotalParameters:=DATA{origins,destinations,key,mode,language,region,avoid,units,arrival_time,departure_time,traffic_model,transit_mode,transit_routing_preference};

    !Set the parameters we want to use
    requestparameters:= DATA{key, origins, destinations};

    requestparameters := { key: APIkey, origins : ListOrigins, destinations : ListDestinations } ;
    

.. replaced line 8 from previous lines shown below according to "todo" suggestion
    requestparameters['key']:=APIkey;
    requestparameters['origins']:=ListOrigins;
    requestparameters['destinations']:=ListDestinations;


Using ``requestparameters`` we can specify which parameters to use and assign them values. Here, we only specify the required parameters, but you can add optional parameters in the same way.

Now that we have set up our parameters, we will translate them into a URL using the ``query_format`` method and to put the resulting string ``formattedparameters`` at the end of our URL.

.. code-block:: aimms
    :linenos:
    
    web::query_format(requestparameters,formattedparameters);
    URL:="https://maps.googleapis.com/maps/api/distancematrix/xml?"+formattedparameters;
    
Once this URL is generated, you just have to send it a GET request and by executing the complete code, you'll be able to download the response XML file at the root of your project, or at any destination specified in ``OutputFile``.

.. MOHAN please check: Once this URL is generated, send it with a ``GET`` request. 
.. When you execute the procedure, you can download the response XML file at the root of your project, or at any destination specified in ``OutputFile``.

Mapping the data 
-----------------------------------------------

We have access to the XML response file. We now need to map the data from this XML file into AIMMS using the XML schema mapping tool.

By taking a look at the XML file, we can find the important data to export in AIMMS.    
    
The ``origin_address`` and ``destination_address`` are elements that contain the exact locations guessed by Google using your request location strings. For each travel, there is a ``duration`` and a ``distance`` folder containing ``value`` and ``text`` elements. The ``value`` contains the duration (or distance) as a number while the ``text`` contains it as a string.
    
Now that we know what we want from the XML file, let's continue.

First, we'll need an XSD file. This XSD file provides the structure for an XML file.

Read more about XML Mapping and creating XSD files in :doc:`../293/293-extracting-data-from-XML`.

.. image:: images/InitialMapping.PNG
    :align: center

When you select an element, the mapping tool gives you access to a list of attributes.

* ``Binds-to`` : links an index with an element having an attribute ``occurrence`` equals to "once", and that is not in the root node. It also links the parent node from the bound element and any child elements to the index, so their data can be used as parameters of the index based on their ``maps-to`` attribute.
* ``Maps-to``  : links an element's value to an AIMMS identifier (e.g., sets, parameters).

Here, we can't bind the elements ``origin_address`` or ``destination_address`` to a set because their ``occurrence`` attribute equals "optional/many" and their position in the root node.
Meanwhile, if we bind an index to the ``status`` element from the Element folder, we would not be able to map the ``origin_address`` to a parameter of this index.
Then, with this XML, we can't create two sets containing the name of places and having for parameters ``Distance(origin,destination)`` and ``Duration(origin,destination)``.

.. MOHAN ^


Instead, we will use virtual attributes. These attributes aren't part of the XML file but can be simulated. They will act like an enumeration of the same elements. For example, for the second ``destination_address`` element in the XML, the virtual attribute will have 2 for value. Then, we will use 2 as the ID for the 2nd destination : The atomium.
Given the structure of the XML, the virtual attribute for ``row`` will also corresponds to the origin ID for the travel while the ``element`` virtual attribute will corresponds to the travel destination ID.

Here is a simplified schema of the XML:

.. image:: images/SimplifiedXML.PNG
    :align: center

The idea is then to create 4 sets: 2 for origins( ``OriginId`` and ``departure``) and 2 for destinations(``DestinationId`` and ``arrival``). A pair of set will contains the same information (an enumeration that will be used as ID), but in the first one, each number will have for parameter the name of the corresponding place, while in the 2nd one they will have for parameters ``time(origin,destination)`` and ``distance(origin,destination``.
Hence, we have a way to identify the  location numbers using the strings, and to get the distances from one location number to another with the other set parameters.

.. MOHAN, please check what I wrote below:

.. 1/We'll create 4 sets: 2 for origins( ``OriginId`` and ``departure``) and 2 for destinations(``DestinationId`` and ``arrival``). All the sets will contain ID numbers assigned to each location. In the sets ``OriginId`` and ``DestinationId``, each ID number will have a parameter of the location name. In the sets ``departure`` and ``arrival``, each ID number will have the parameters ``time(origin,destination)`` and ``distance(origin,destination``.

You'll need to create these elements:

.. image:: images/MappingObjects.PNG

.. code-block:: aimms
    :linenos:
    
    Set OriginId {
        Index: Oid;
    }
    Set OriginId {
        Index: Did;
    }
    Set departure {
        Index: d;
    }
    Set arrival {
        Index: a;
    }
    StringParameter destination {
        IndexDomain: Did;
    }
    StringParameter Origin {
        IndexDomain: Oid;
    }
    Parameter Time {
        IndexDomain: d,a;
    }
    Parameter dist {
        IndexDomain: d,a;
    }

Using the XML Mapping Tool, create the following mapping:

.. image:: images/MapSchema.PNG

* ``origin_address`` virtual attribute **binds to** ``Oid``
* ``destination_address`` virtual attribute **binds to** ``Did``
* ``row`` virtual attribute **binds to** ``d``
* ``element`` virtual attribute **binds to** ``a``


* ``Duration/value`` **maps to** ``Time(d,a)``
* ``Distance/value`` **maps to** ``Distance(d,a)``
* ``origin_address`` **maps to** ``origin(Oid)``
* ``destination_address`` **maps to** ``destination(Did)``

.. Warning:: Don't forget to set the attribute ``Read-filter`` to 0 for every unused element or parameter in the mapping. The ``Read-filter`` attribute is accessible by selecting the element or parameter.

Now, you just have to read the XML file data.

.. code-block:: aimms
    :linenos:

    READXML(responseFile,"YOUR_XSD_FILE_NAME.axm");
    
Congratulations! You have access to the Google Maps API data in your AIMMS model.

.. image:: images/FinalTable.PNG

Example project
------------------

You can download the example AIMMS project below: 

* :download:`DistanceAPIproject.zip <DistanceAPIproject.zip>` 

Related Topics
-----------------------------------------------
* **AIMMS How-To**: :doc:`../294/294-Online-XML-HTTP-library`
* **AIMMS How-To**: :doc:`../293/293-extracting-data-from-XML`
* `Google Maps Developer Guide <https://developers.google.com/maps/documentation/distance-matrix/intro>`_. 

