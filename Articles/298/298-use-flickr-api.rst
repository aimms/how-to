Obtain photos using the Flickr API
=====================================================

.. meta::
   :description: Using the Flickr API to obtain images.
   :keywords: xml, http library, Flickr, images, API

**Summary**

.. role:: raw-html(raw)
    :format: html

In this article, we'll learn how to use the Flickr REST API. The process to deal with a REST API in AIMMS is really similar from one to another, and the main differences are to be find in the documentation. Here, the goal is to show you a concrete example of this process using a complex documentation.

**Use case**

This use case is inspired by  an `idratherbewriting tutorial <https://idratherbewriting.com/learnapidoc/docapis_flickr_example.html>`_.

Flickr is an image and video hosting social network with a database of millions of photos.
Our mission will be to extract the photos from `this Flickr gallery <https://www.flickr.com/photos/flickr/galleries/72157647277042064/>`_.

To do so, we'll need the `AIMMS http client library <https://documentation.aimms.com/httpclient/library.html#adding-the-http-client-library-to-your-model>`_.

Searching the documentation
---------------------------------------------

The `flickr API documentation <https://www.flickr.com/services/api/>`_  is quite complete but can be tricky to explore, and requires to dig for informations.


To begin, we need to identify what we're searching for. The main informations we need are the URL format where we can address our request and the authentication system to use.

In the left column of this documentation, we have different topics available in the *read this first* section, including `the URL section <https://www.flickr.com/services/api/misc.urls.html>`_ , the `overview <https://www.flickr.com/services/api/misc.overview.html>`_ and the `user authentication section <https://www.flickr.com/services/api/auth.oauth.html>`_.

Reading the **user authentication topic**, we learn that the main authentication system used for this API is a complex OAuth protocol, but some methods can still be used with a simple API key, and that's what we'll use here.

.. image:: flickr/overview.PNG
    :align: center

The **overview** section gives us the format to apply for using a request : An URL endpoint followed by specified parameters ``method``, ``api_key`` and additional parameters (depending on the method) must be used.

.. Note:: To obtain your API key, please follow `this tutorial <https://www.flickr.com/services/apps/create/apply/>`_  and selecting a non-commercial key. Don't worry, the way you fill the fields doesn't matters in our case. Please keep your API key accessible, we'll need it in what's coming next.

Finally, the **URL section** gives us the process to create a photo URL.This photo URL is what we need to download an image using a get request :

:raw-html:`<font color="blue">https://farm</font>`:raw-html:`<font color="red">{farm-id}</font>`:raw-html:`<font color="blue">.staticflickr.com/</font>`:raw-html:`<font color="red">{server_id}</font>`:raw-html:`<font color="blue">/</font>`:raw-html:`<font color="red">{id}</font>`:raw-html:`<font color="blue">_</font>`:raw-html:`<font color="red">{secret}</font>`:raw-html:`<font color="blue">.jpg</font>`


To access a photo, we need different informations : ``the farm_id``, ``the server_id``,the ``id`` and the ``secret``.

.. Note: please note that the farm ID is no longer to be specified, the server will find out the farm id itself if you don't write it.

We're now searching for these informations about the gallery photos.

On the right column of the documentation `main page <https://www.flickr.com/services/api/>`_, we have a list of methods supported by the Flickr API.
If we search for the Galleries related methods, we can find the method `flickr.galleries.getPhotos <https://www.flickr.com/services/api/flickr.galleries.getPhotos.html>`_.
given a ``gallery_id`` and an ``api_key``, this method doesn't return the gallery photos like is named makes suppose, but rather return all the informations we need to create the different urls to access the gallery photos.


But before to be able to use the **flickr.galleries.getPhotos** method, we need the ``id`` of the gallery.
After exploring the different methods available,we can find the `flickr.urls.lookupGallery <https://www.flickr.com/services/api/flickr.urls.lookupGallery.html>`_ that, given the URL address of a gallery, return informations about this gallery, including its ``id``.

We can finally design the process:

#. Create a request to obtain the gallery ``id`` using **flickr.urls.lookupGallery**
#. Create a request to obtain gallery photos using **flickr.galleries.getPhotos**
#. Construct the photos URLs and do a **GET request** to obtain the files.

Getting the gallery ``id``.
---------------------------------------------

The code for this request is the following one.
 
.. code-block:: aimms
    :linenos:

    SP_responseFile:="Output.xml";
	SP_APIkey:="Your_api_key";
    SP_requestparameters:={
        'method': "flickr.urls.lookupGallery",
        'api_key': SP_APIkey,
        'url': "https://www.flickr.com/photos/flickr/galleries/72157647277042064/"
    };
    web::query_format(SP_requestparameters,SP_formattedparameters);
    SP_URL:="https://www.flickr.com/services/rest/?"+SP_formattedparameters;
    
    !set the request
    web::request_create(SP_requestId);
    web::request_setMethod(SP_requestId,"GET");
    web::request_setURL(SP_requestId,SP_URL);
    web::request_setResponseBody(SP_requestId,'File',SP_responseFile);
    web::request_invoke(SP_requestId,P_responsecode);
    
    !extract data from the XML.
    READXML("Output.xml","GetGallery.axm");
    
**The HTTP request**

.. Note:: This article hasn't for purpose to explain how to formulate an HTTP request in AIMMS and will not detail the code. If you want to know more about the subject, please follow the article :doc:`../294/294-Online-XML-HTTP-library`.

Our goal is to use the ``flickr.urls.lookupGallery`` method from the API using a GET request and to extract the gallery ``id`` from the answer file.
Let's check what the `Flickr documentation <https://www.flickr.com/services/api/flickr.urls.lookupGallery.html>`_ has to tell us about this method.

.. image:: ./flickr/lookupGallery.PNG 

Two arguments are required that are an API key and an URL. You should now have access to your own API key, and the URL of the gallery is the following one: **https://www.flickr.com/photos/flickr/galleries/72157647277042064/**

.. Note:: You can extract photos from any gallery by setting the ``SP_url`` to the url of the wanted gallery.


For this request, you'll need sereval objects:

.. image:: flickr/GalleryObjects.PNG

.. code-block:: aimms
    :linenos:

    Set S_requestparam {
        Index: I_rp;
        InitialData: {
            DATA{api_key,method,url};
        }
    }
    StringParameter SP_requestId {
    
    }
    StringParameter SP_requestparameters {
        IndexDomain: I_rp;
    }
    Parameter P_responsecode {
    
    }
    StringParameter SP_APIkey {
    
    }
    StringParameter SP_MethodName{
    
    }
    StringParameter SP_URL {
    
    }
    StringParameter SP_responsefile{
    
    }
    StringParameter SP_formattedparameters {
    
    }

We can specify the direction for the outputfile using the ``SP_OutputFile`` string parameter.

.. code-block:: aimms
    :linenos:
    
    SP_responseFile:="Output.xml";

The parameters of the request must be put at the end of the endpoint URL **https://www.flickr.com/services/rest/?** after being formatted by the ``web::query_format`` method.

.. code-block:: aimms
    :linenos:
    
    SP_requestparameters:={
        'method': "flickr.urls.lookupGallery",
        'api_key': SP_APIkey,
        'url': "https://www.flickr.com/photos/flickr/galleries/72157647277042064/"
    };

    web::query_format(SP_requestparameters,SP_formattedparameters);
    SP_URL:="https://www.flickr.com/services/rest/?"+SP_formattedparameters;

We can then set our request as usual.

.. code-block:: aimms
    :linenos:
    
    web::request_create(SP_requestId);
    web::request_setMethod(SP_requestId,"GET");
    web::request_setURL(SP_requestId,SP_URL);
    web::request_setResponseBody(SP_requestId,'File',SP_responseFile);
    web::request_invoke(SP_requestId,P_responsecode);

We should now have our XML file accessible at the root of the project,or wherever it is specified to go.


**Extracting data**

.. Note:: This article hasn't for purpose to explain how to extract data from an XML file in AIMMS and the process will not be detailed here, but feel free to check this step-by-step article: :doc:`../293/293-extracting-data-from-XML`

With this new XML file, we can now create an XSD file in order to use the XML schema mapping tool from AIMMS.

To generate this XSD file, you can use an online generator such as the one provided by `FreeFormatter <https://www.freeformatter.com/xsd-generator.html>`_, or you can :download:`download this one <./download/GetGallery.zip>`. Once it's obtained, just put the xsd file at the root of your project.

Now create a string parameter ``SP_GalleryID`` made for containing the gallery id information and using the XML schema mapping tool, map it to the ``rsp/gallery/id`` element of the xml file.
Be carefull to not map it to the ``rsp/gallery/Gallery_id`` element which isn't the one we'll use.

.. image:: flickr/mapping1.PNG

.. Warning:: don't forget to set every ``read-filter`` attribute of unused elements from the XML schema mapping tools to "0".

Finally, by using the ``READXML`` method, we can get the gallery ID we're searching for.
 
.. code-block:: aimms
    :linenos:
    
    READXML("Output.xml","NAME_OF_YOUR_XSD.axm");
    

Getting Photos informations.
---------------------------------------------

We now want to create another procedure to get all the ``id`` informations we need about the gallery photos.
For that, we'll use the **flickr.galleries.getPhotos** method from the flickr API. The code for this procedure is the following one.

.. code-block:: aimms
    :linenos:
    
    SP_responseFile:="Output2.xml";
    SP_APIkey:= "Your_api_key";
    SP_MethodName:="flickr.galleries.getPhotos";
    SP_requestparameters:= {
        'method' : SP_MethodName,
        'api_key' : SP_APIkey,
        'gallery_id' : SP_GalleryID
    };

    web::query_format(SP_requestparameters,SP_formattedparameters);
    SP_URL:="https://www.flickr.com/services/rest/?"+SP_formattedparameters;
    web::request_create(SP_requestId);
    web::request_setMethod(SP_requestId,"GET");
    web::request_setURL(SP_requestId,SP_URL);
    web::request_setResponseBody(SP_requestId,'File',SP_responseFile);
    web::request_invoke(SP_requestId,P_responsecode);
    READXML("Output2.xml","NAME_OF_YOUR_XSD.axm");
    
**Set the HTTP request**

.. image:: flickr/Getphotos.PNG

This request takes as parameters an ``api_key`` and a ``gallery_id``, and we want from the answer the ``farm`` ID, the ``server ID``, the ``ID`` and the ``secret`` for each photo in the gallery.
But before to extract these data, we need to get the XML file containing these informations from an HTTP request.

The process is almost the same as in the last request, the only thing changing here is the DATA of the set S_requestparam and the method used:

You need to create these objects: 

.. image:: flickr/getphotosObjects.PNG


.. code-block:: aimms
    :linenos:
    
    Set S_requestparam {
        Index: I_rp;
        Definition: {
            DATA{api_key,method,gallery_id};
        }
	}
    StringParameter SP_requestId {
    
    }
    StringParameter SP_requestparameters {
        IndexDomain: I_rp;
    }
    Parameter P_responsecode {
    
    }
    StringParameter SP_APIkey {
    }
    StringParameter SP_MethodName{
    
    }
    StringParameter SP_URL {
    
    }
    StringParameter SP_responsefile{
    
    }
    StringParameter SP_formattedparameters {
    
    }

And then to execute this code:

 .. code-block:: aimms
    :linenos:
    
    SP_responseFile:="Output2.xml";
    SP_APIkey:= "Your_api_key";
    SP_MethodName:="flickr.galleries.getPhotos";
    SP_requestparameters:= {
        'method' : SP_MethodName,
        'api_key' : SP_APIkey,
        'gallery_id' : SP_GalleryID
    };

    web::query_format(SP_requestparameters,SP_formattedparameters);
    SP_URL:="https://www.flickr.com/services/rest/?"+SP_formattedparameters;
    web::request_create(SP_requestId);
    web::request_setMethod(SP_requestId,"GET");
    web::request_setURL(SP_requestId,SP_URL);
    web::request_setResponseBody(SP_requestId,'File',SP_responseFile);
    web::request_invoke(SP_requestId,P_responsecode);
    
You should now have access to the XML answer file in the direction ``SP_responseFile``, and you can generate your second XSD file or :download:`download it <./download/GetPhotos.zip>`.

**Extracting data**

Before to extract the data from the xml using the AIMMS XML schema mapping tool, you need to create objects to contain these informations :

.. image::flickr/getidsObjects.PNG

.. code-block:: aimms
    :linenos:
    
     Set S_Photos {
        Index: I_p;
    }
    StringParameter SP_farm(I_p) {
        IndexDomain: I_p;
    }
    StringParameter SP_server(I_p) {
        IndexDomain: I_p;
    }
    StringParameter SP_id(I_p) {
        IndexDomain: I_p;
    }
    StringParameter SP_secret(I_p) {
        IndexDomain: I_p;
    }

Using the XML mapping tool, you then need to realize the following mapping:

* ``rsp/photos/photo/title`` **binds-to** ``S_Photos``.
* ``rsp/photos/photo/id`` **maps-to** ``SP_Id``.
* ``rsp/photos/photo/farm`` **maps-to** ``SP_farm``.
* ``rsp/photos/photo/server`` **maps-to** ``SP_server``.
* ``rsp/photos/photo/secret`` **maps-to** ``SP_secret``.

and execute:

.. code-block:: aimms
    :linenos:

    READXML("Output2.xml","NAME_OF_YOUR_XSD.axm");
    
you should now have a set ``S_Photos`` containing photo titles of the gallery and having for parameters the ``id``, ``farm`` id, ``server`` id and ``secret`` of a photo.

Getting the photos.
---------------------------------------------

We know from the `documentation <https://www.flickr.com/services/api/misc.urls.html>`_ the format a photo URL must have. This URL is different from the one displayed on your browser when you select the photo. This isn't the url of the page where we can find the photo but the url of the photo itself.

:raw-html:`<font color="blue">https://farm</font>`:raw-html:`<font color="red">{farm-id}</font>`:raw-html:`<font color="blue">.staticflickr.com/</font>`:raw-html:`<font color="red">{server_id}</font>`:raw-html:`<font color="blue">/</font>`:raw-html:`<font color="red">{id}</font>`:raw-html:`<font color="blue">_</font>`:raw-html:`<font color="red">{secret}</font>`:raw-html:`<font color="blue">.jpg</font>`

The idea is now, for each photo contained in the gallery, to set a get request to the corresponding URL in order to obtain the photo.
For that, we need some new objects:

.. image:: flickr/photoObjects.PNG


.. code-block:: aimms
    :linenos:
    
    StringParameter SP_requestId {
    
    }
    Parameter P_responsecode {
    
    }
    StringParameter SP_URL {
    
    }
    StringParameter SP_responsefile{
    
    }

The code of this procedure is the following one.

.. code-block:: aimms
    :linenos:
    
    for p in S_Photos Do
        !set direction for the photos
        SP_OutputFile:="MainProject/WebUI/resources//images/"+SP_id(I_p)+".jpg";
        !create URLs
        SP_URL:="https://farm"+SP_farmId(p)+".staticflickr.com/"+SP_serverId(p)+"/"+SP_Id(p)+"_"+SP_secretId(p)+".jpg";
        !send request
        web::request_create(SP_requestId);
        web::request_setMethod(SP_requestId,"GET");
        web::request_setURL(SP_requestId,SP_URL);
        web::request_setResponseBody(SP_requestId,'File',SP_OutputFile);
        web::request_invoke(SP_requestId,P_responsecode);
    endfor;

The choice to set the names of photo files using the ``SP_id(I_p)`` parameter is arbitrary. The result is that every file has for name the id of the concerned photo in flickr. It is convenient because by using titles of photos for example, we could have problem with special characters not supported.
Also, the choice of the destination **MainProject/WebUI/resources//images/** refers to the use of `WebUI image widget <https://manual.aimms.com/webui/image-widget.html>`_.

Congratulation, we finally reached our goal!

Beside the use of the flickr API, the main lesson from this article is that API documentation can sometimes be tricky to use, and will not offer you an easy answer for your problem. But APIs are a powerful tool, and you shouldn't be afraid of exploring documentation to get what you want, but the best way to search a documentation is by ,at first, know what you're searching for.
And, after some efforts, we can finally use those photos in AIMMS:

.. image:: flickr/final.PNG 
    :align: center


Going further
---------------------------------------------

The flickr API also allows you to search for photos using tags with the `flickr.photos.search method <https://www.flickr.com/services/api/flickr.photos.search.html>`_ .
It'll then send you back a list of photos identified by those tags with all the IDs you need to recreate their url.
And by mapping into aimms these data and making a get request to the newly created urls, you can get the photos.
You will find the related code in the example project.

.. Note: please note that you can only specify 20 tags at the same time, and the answer will contains only one page of results (max 500 photos).

Example project
------------------

Please download the :download:`AIMMS project <download/Flickr Project.zip>` 

Related Topics
------------------------------------
* **AIMMS How-To**: :doc:`../294/294-Online-XML-HTTP-library`
* **AIMMS How-To**: :doc:`../293/293-extracting-data-from-XML`
* **AIMMS How-to**: :doc:`../296/296-obtaining-geographic-data-through-the-google-api`
* **AIMMS manual**: `WebUI image widget tutorial <https://manual.aimms.com/webui/image-widget.html>`_

References
^^^^^^^^^^^^^^^^
* `Flickr API documentation <https://www.flickr.com/services/api/>`_
* `idratherbewriting tutorial <https://idratherbewriting.com/learnapidoc/docapis_flickr_example.html>`_



