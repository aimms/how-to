Conventions Used in the Examples Provided
==========================================

This article just enumerates some of the conventions used in the associated example apps.

Steering the HTTP Request
-------------------------

To test the example apps, also on different systems, configuration of an HTTP request is arranged via a few files
in the folder ``Config``. This folder should be located next to the project folder of the client application.
The three example clients all use this config folder to steer the HTTP requests made. 

The parameters:

Common Configuration Parameters
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The configuration is based on:

#.  ``service`` stored in ``service.txt``. This string parameter contains the name of the service. 

#.  ``useCloud`` stored in ``using_AIMMS_cloud.txt``. This binary parameter is:

    #.  ``0``: if ``localhost``   is to be used for the service.
    
    #.  ``1``: if the AIMMS Cloud is to be used for the service.

Using the Service on ``localhost``
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

When using the service on ``localhost``, there is only one additional parameter relevant, namely the port number:

#.  ``port`` stored in ``port.txt``.  This integer parameter contains the port number.  

Using the Service on the AIMMS Cloud
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

#.  ``cloud`` stored in ``cloud.txt``. This string parameter is a string which contains the name of an AIMMS Cloud, for instance ``chriskuip.aimms.cloud``.

#.  ``apiKey`` stored in ``apikey.txt``. This string parameter identifies and authorizes the user of the service.
    See `how to obtain an apiKey <https://documentation.aimms.com/cloud/rest-api.html#api-keys-and-scopes>`_.

#.  ``app`` stored in ``app.txt``.  This string parameter contains the name of the app when published on an AIMMS Cloud, here ``CountTheStars``.

#.  ``ver`` stored in ``ver.txt`` This string parameter contains the version of the app at hand, for instance ``1.0.1.0``,


Methods Implemented by the Example Server App
-----------------------------------------------

In the example server app, there are two base methods implemented:

* **countStars:** count the number of stars in a list of strings.

* **generateStars:** generate a list of string with asterisks in it.

Each of these base methods can use five different de facto standards for data exchange:

* CSV

* Json

* Parquet

* Excel

* XML

An actual method is by concatenating the base method with the data format.  For instance, 
``countStarsJson`` is base method ``countStars`` exchanging data using data format ``Json``.

Next
-----------

:doc:`../585/585-AIMMS-server-app`

.. spelling:word-list::

    apiKey
    de
    facto

