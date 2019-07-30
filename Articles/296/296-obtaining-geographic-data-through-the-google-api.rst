Obtain geographic data through a Google API
==================================================================================================

In this article, we will learn how to use the HTTP Library to obtain data from a Google map API. Those data will be distances between different places.
To know how to install the HTTP library, please follow `this tutorial <https://documentation.aimms.com/httpclient/library.html#adding-the-http-client-library-to-your-model>`_ .

**Use Case**

Let's say we're planning a road trip in Europe and we want to visit some very famous places.
Those places will be:

* The Atomium in Brussels, Belgium.

* The Eiffel tower in Paris, France.

* The AIMMS office in Haarlem, Netherlands.

* The Coliseum in Roma,Italy.

* The Alhambra in Granada,Spain.

To plan our trip, We want to know the distances between the different places and the time to spend on the road from one destination to another.
For that, we'll be using the distance Matrix API from Google Map.

.. Note:: In order to complete this article, you'll need to obtain your own Google API key. To do so, please follow `this reference <https://developers.google.com/maps/documentation/geolocation/get-api-key>`_.

Exploring the documentation
-----------------------------------------------
Same as for every other API, the first step to use an API is to read its `documentation <https://developers.google.com/maps/documentation/distance-matrix/intro>`_. The informations we're searching for are the authentication required and the request format.

The required parameters for the request are indicated in this paragraph:

.. image:: images/MandatoryParameters.PNG
    :align: center
    
This Google API only requires an API key to be used, and this key is to be specified as a parameter of the request,and the way to specify these parameters is by putting them at the end of the request URL: 

.. image:: images/RequestFormat.PNG
    :align: center
.. role:: raw-html(raw)
    :format: html
    
.. todo:: 
    #. In case you created this picture as a screenshot from existing GOOGLE API documentation, please share where you got it from.
    
    #. In case you created this picture yourself: In the above picture, JSon is recommended. However, AIMMS doesn't currently support JSon, so how can we recommend it. Please change to JSON: .... (Currently not supported directly by AIMMS), xml: (Supported by AIMMS). 
    
AIMMS supports XML but not JSON, then the xml format will here be preferred.
So, an example of URL should look like that:

:raw-html:`<font color="blue">https://maps.googleapis.com/maps/api/distancematrix/xml?</font>`:raw-html:`<font color="red">destinations=</font>`:raw-html:`<font color="black">{DESTINATIONS}</font>`:raw-html:`<font color="blue">&</font>`:raw-html:`<font color="red">key=</font>`:raw-html:`<font color="black">{KEY}</font>`:raw-html:`<font color="blue">&</font>`:raw-html:`<font color="red">origins=</font>`:raw-html:`<font color="black">{ORIGINS}</font>`:raw-html:`<font color="blue">&</font>`...

Those parameters are mandatory but the API contains plenty of optional parameters that can be really interesting. You can for example make specification about the travel mode (driving,walking...), the roads you want to avoid, the distance unit to use or the model to calculate travel duration. Feel free to check every available options for your request in the `Google Maps API documentation <https://developers.google.com/maps/documentation/distance-matrix/intro>`_

Creating the request
-----------------------------------------------

The code is the following one.

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

Most of this code is common for every HTTP request sent using the HTTP Client Library, and will not be reviewed in this article. If you want to learn more about how to formulate an HTTP request in AIMMS, please feel free to check the AIMMS article :doc:`../294/294-Online-XML-HTTP-library`.

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
    


The set ``TotalParameters`` will contains every possible parameters available for the API, required or optional.

To respect the API documentation, we create two strings storing the different origins and destinations separated by "|" characters. The choice to use locations names instead of coordinates is arbitrary. We want the total distance matrix between our places, so let's put every locations in ListOrigins and ListDestinations. 

.. code-block:: aimms
    :linenos:
    
    !Set the Locations
    ListOrigins:="Eiff Tower, Paris | The atomium is in Brussels | AIMMS Haarlem | Roma Italy Coliseum | The alhambra, granada spain";
    ListDestinations:="Eiffel Tower, Pari | The atomium, Brussels | AIMMS Haarlem | Coliseum, Roma Italy | The alhambra, granada spain";

The more observant among you will notice some errors in the name of places and variations in the format I've put the location names. In fact, The main advantage with Google APis is their flexibility, and these errors will not cause any problem for Google Map to guess what I'm searching for.

.. todo::  When talking about advantage, and doing other comparisons, it is required for scientific articles to be explicit as to what you are comparing to.  For "How To" articles, it is not required, but I think it is good practice. Anyway, I do not see the purpose of a comparison here. Perhaps you can reformulate to something like: "Google API helps you here because it is resilient against typos".

.. Warning:: The API is able to send back data for a **maximum of 100 travel per request** and the origins and destinations should not contains more than 25 places, so be sure to not exceed these limits.  **number of travels = number of origins x number of destinations**, `see <https://developers.google.com/maps/documentation/distance-matrix/usage-and-billing#distance-matrix>`_

.. code-block:: aimms
    :linenos:
    
    !Setting the TotalParameters set
    TotalParameters:=DATA{origins,destinations,key,mode,language,region,avoid,units,arrival_time,departure_time,traffic_model,transit_mode,transit_routing_preference};

    !Set the parameters we want to use
    requestparameters:= DATA{key, origins, destinations};

    requestparameters['key']:=APIkey;
    requestparameters['origins']:=ListOrigins;
    requestparameters['destinations']:=ListDestinations;
    

.. todo:: line 8-11 above should be written as:

    .. code-block:: aimms
        :linenos:
        
        requestparameters := { key: APIkey, origins : ListOrigins, destinations : ListDestinations } ;


Using ``requestparameters`` we can specify what parameters do we want to use and affect them their values. Here, we only want to specify the required parameters, but if you want to add an optional parameter, you just have to add it to the data list of requestparameters and set it a proper value.
Now that we have set up our parameters, we just have to transcript them into an URL-friendly language using the ``query_format`` method and to put the resulting string ``formattedparameters`` at the end of our URL.

.. code-block:: aimms
    :linenos:
    
    web::query_format(requestparameters,formattedparameters);
    URL:="https://maps.googleapis.com/maps/api/distancematrix/xml?"+formattedparameters;
    
Once this URL is generated, you just have to send it a GET request and by executing the complete code, you'll be able to download the response XML file at the root of your project, or at any destination specified in ``OutputFile``.

The data mapping. 
-----------------------------------------------

We have access to the XML response file. We now need to map the data from this XML file into AIMMS using the XML schema mapping tool.
If you want a detailed tutorial about XML Mapping in AIMMS please check :doc:`../293/293-extracting-data-from-XML`.

By taking a look at the XML file, we can find the important data to export in AIMMS.    
    
The ``origin_address`` and ``destination_address`` are elements that contains the exact locations guessed by Google using your request location strings. for each travel, there is a ``duration`` and a ``distance`` folder containing ``value`` and ``text`` elements. The ``value`` contains the duration(or distance) as a number while the ``text`` contains it as a string.
    
Now that we know what we want from the XML file, let's continue.
First, we'll need to get an .XSD file. This XSD file act like a plan for an XML file.
To generate this XSD file, you can use an online generator such as the one provided by `FreeFormatter <https://www.freeformatter.com/xsd-generator.html>`_. Once it's generated, just put the xsd file at the root of your project.
You can now use the XML schema mapping tool.

.. image:: images/InitialMapping.PNG
    :align: center

When you select an element, the mapping tool gives you access to a list of attributes.

* ``Binds-to`` : links an index with an element having an attribute ``occurrence`` equals to "once", and that is not in the root node. It also links the parent node from the bound element and any child elements to the index, so their data can be used as parameters of the index based on their ``maps-to`` attribute.
* ``Maps-to``  : links an element's value to an AIMMS identifier (e.g., sets, parameters).

Here, we can't binds the elements ``origin_address`` or ``destination_address`` to a set because of their ``occurrence`` attribute equals to "optional/many" and their position in the root node.
Meanwhile, if we binds an index to the ``status`` element from the Element folder, we would not be able to map the ``origin_address`` to a parameter of this index.
Then, with this XML, we can't create two set containing the name of places and having for parameters Distance(origin,destination) and Duration(origin,destination).


Instead, we will use the virtual attributes. These attributes aren't part of the XML file but can be simulated. They will act like an enumeration of the same elements. For example, for the second ``destination_address`` element in the XML, the virtual attribute will have 2 for value. Then, we will use 2 as the ID for the 2nd destination : The atomium.
Given the structure of the XML, the virtual attribute for ``row`` will also corresponds to the origin ID for the travel while the ``element`` virtual attribute will corresponds to the travel destination ID.

Here is a simplified schema of the XML:

.. image:: images/SimplifiedXML.PNG
    :align: center

The idea is then to create 4 sets: 2 for origins( ``OriginId`` and ``departure``) and 2 for destinations(``DestinationId`` and ``arrival``). A pair of set will contains the same information (an enumeration that will be used as ID), but in the first one, each number will have for parameter the name of the corresponding place, while in the 2nd one they will have for parameters ``time(origin,destination)`` and ``distance(origin,destination``.
Hence, we have a way to identify the  location numbers using the strings, and to get the distances from one location number to another with the other set parameters.

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
    
Congratulation! you have access to the Google map API data in your AIMMS model.

.. image:: images/FinalTable.PNG

Example project
------------------

Please download the :download:`AIMMS project download <DistanceAPIproject.zip>` 

Related topics.
-----------------------------------------------
* **AIMMS How-To**: :doc:`../294/294-Online-XML-HTTP-library`
* **AIMMS How-To**: :doc:`../293/293-extracting-data-from-XML`

