Conventions used in the examples provided
==========================================

This article just enumerates some of the conventions used in the associated example apps.

Steering the HTTP request
-------------------------

To test the example apps, also on different systems, configuration of an HTTP request is arranged via a few files
in the folder ``Config``. This folder should be located next to the project folder of the client application.
The three example clients all use this config folder to steer the HTTP requests made. 

The parameters:

Common configuration parameters:
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The configuration is based on:

#.  ``service`` stored in ``service.txt``. This string parameter contains the name of the service. 

#.  ``useCloud`` stored in ``using_AIMMS_cloud.txt``. This binary parameter is:

    #.  ``0``: if ``localhost``   is to be used for the service.
    
    #.  ``1``: if the AIMMS Cloud is to be used for the service.

Using the service on ``localhost``
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

When using the service on ``localhost``, there is only one additional parameter relevant, namely the port number:

#.  ``port`` stored in ``port.txt``.  This integer parameter contains the port number.  

Using the service on the AIMMS Cloud
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

#.  ``cloud`` stored in ``cloud.txt``. This string parameter is a string which contains the name of an AIMMS cloud, for instance ``chriskuip.aimms.cloud``.

#.  ``apiKey`` stored in ``apikey.txt``. This string parameter identifies and authorizes the user of the service.
    See https://documentation.aimms.com/cloud/rest-api.html#api-keys-and-scopes to obtain an apiKey.

#.  ``app`` stored in ``app.txt``.  This string parameter contains the name of the app when published on an AIMMS cloud, here ``CountTheStars``.

#.  ``ver`` stored in ``ver.txt`` This string parameter contains the version of the app at hand, for instance ``1.0.1.0``,


Methods implemented by the example server App
-----------------------------------------------

In the example server app, there are two base methods implemented:

* **countStars** Count the number of stars in a list of strings.

* **generateStars** Generate a list of string with asterisks in it.

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

