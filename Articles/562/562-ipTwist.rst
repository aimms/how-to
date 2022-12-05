Using an API with OpenAPI Spec
===============================

An API with an OpenAPI 3.0 spec can be used to generate an AIMMS Library.
This AIMMS Library can subsequently be used to ease interfacing the corresponding service significantly.

This article illustrates interfacing a service with an OpenAPI 3.0 specification. 
As you `know <https://how-to.aimms.com/Articles/561/561-openapi-overview.html>`_ the following architecture is provided:

.. image:: images/client-server-openapi-lib.png
    :align: center

|

The translation of AIMMS data to the format accepted by the server (arrow 2), and 
translating the response provided by the server into AIMMS data (arrow 3) are taken care of by a generated AIMMS library.

The purpose of this article is to illustrate:

#.  initialization of an OpenAPI generated AIMMS library,

#.  make a request to such a library (arrow 1), and

#.  handle a response from such a library (arrow 4).

However, to make this concrete, an example is used, which is presented `here <https://iptwist.com/>`_. Note that to work with this example you will need an API key, from `https://iptwist.com/settings <https://iptwist.com/settings>`_

Preparation
-----------

The preparations needed come prepackaged in the ``LibraryInitialization`` routine of the OpenAPI generated library ``openapi-ipTwist``:

.. code-block:: aimms 
    :linenos:

    ! Read mapping files for this library.
    block
        DirectoryOfLibraryProject("openapi_ipTwist", libFolder);
    onerror err do
        libFolder := "../libs/openapi-ipTwist/";
        errh::MarkAsHandled(err);
    endblock;
    dex::ReadMappings(libFolder, "Generated/openapi-ipTwist", 0);

    ! Read server initialization data (e.g. service URL, API key, OAuth credentials)
    apiInitFile := "../api-init/openapi-ipTwist.txt";
    if FileExists(apiInitFile) then
        read from file apiInitFile;
    endif ;

Selected remarks about this code:

*   Lines 1-8: The mapping files are in the subfolder ``./Mappings/Generated/openapi-ipTwist`` of the library folder.

*   Lines 10-14: Read in ipTwist config information, such as server name and API Key.

Example contents for the ``openapi-ipTwist.txt`` are as follows:

.. code-block:: aimms 
    :linenos:

    ipTwist::api::APIServer :=  "https://iptwist.com" ;
    ipTwist::api::APIKey('X-IPTWIST-TOKEN') := "" ;

There is one server for the service ``ipTwist``, namely ``ipTwist.com``; so the server is specified in the initialization file.
Of course, you can choose to enter your API key directly in this file.
 
Calling the API
---------------

Using the ``openapi_ipTwist`` library, making a request is just as follows:

.. code-block:: aimms 
    :linenos:
    :emphasize-lines: 5

    ! Starting current call.
    ipTwist::api::NewCallInstance(ep_callInstance);

    ! Fill in the data for making the request.
    ipTwist::_Request::ip(ep_callInstance) := sp_myIPAddress ;

    ! Install hook, which will copy the data or handle the error
    ipTwist::api::post_::UserResponseHook := 
        'pr_GeolocateResponseHook' ;

    ! Start the request.
    ipTwist::api::post_::apiCall(ep_callInstance);

Remarks:

#.  Line 2: Each request is an object.  
    The value of this mechanism will be illustrated in another how-to.

#.  Line 5: Here the data of the application is actually copied to the parameters of the ``openapi_ipTwist`` library.
    Highlighted, as this part requires application specific logic.  

#.  Line 8: The library needs to know which procedure should handle the response (arrow 4).

#.  Line 11: Actually starting the request.

#. At ipTwist example, this procedure is called ``pr_GeolocateMakeRequest``.

Handling the Response
----------------------

Using the ``openapi_ipTwist`` library, handling the response is just as follows:

.. code-block:: aimms 
    :linenos:
    :emphasize-lines: 5-12

    switch ipTwist::api::CallStatusCode(ep_callInstance) do

        '200':
            block ! Success, copy data retrieved to application core data structures.
                sp_city         := ipTwist::_Response::city(        ep_callInstance);
                sp_country      := ipTwist::_Response::country(     ep_callInstance);
                sp_countryCode  := ipTwist::_Response::country_code(ep_callInstance);
                p_lat           := ipTwist::_Response::latitude(    ep_callInstance);
                p_lon           := ipTwist::_Response::longitude(   ep_callInstance);
                sp_state        := ipTwist::_Response::state(       ep_callInstance);
                sp_timezone     := ipTwist::_Response::timezone_(   ep_callInstance);
                sp_zip          := ipTwist::_Response::zip(         ep_callInstance);
            endblock ;
            ipTwist::_Response::EmptyInstance(ep_callInstance);
            block ! Use data in core data structures for presentation purposes.
                p_shownLocLatitude(  ep_ipLoc ) := p_lat ;
                p_shownLocLongitude( ep_ipLoc ) := p_lon ;
            endblock ;

        '400','401','402','403','404','405','406','407','408','409','410','411','412','413','414','415','416','417','421','422','423','424','425','426','427','428','429','431','451',
        '500','501','502','503','504','505','506','507','508','510','511':
            raise error formatString("ipTwist::Geolocate(%s) failed (instance: \'%e\', status: %e, error: %e): %s",
                sp_myIPAddress, ep_callInstance, 
                openapi_ipTwist::api::CallStatusCode(ep_callInstance), 
                openapi_ipTwist::api::CallErrorCode(ep_callInstance), 
                fnc_errorFunc( ipTwist::api::CallStatusCode(ep_callInstance) ) );

        default:
            raise error formatString("ipTwist::Geolocate(%s) failed (instance: \'%e\', status: %e, error: %e): %s",
                sp_myIPAddress, ep_callInstance,
                openapi_ipTwist::api::CallStatusCode(ep_callInstance), 
                openapi_ipTwist::api::CallErrorCode(ep_callInstance), 
                "unknown reason" );

    endswitch ;

Remarks:

#.  Lines 6-12: This is where the application logic comes in again. 
    Here we copy the data from the ``openapi_ipTwist`` library into the data structures of the application.

#.  Line 14: After the data is retrieved as needed, the data can be removed from the OpenAPI library.

#.  Lines 16, 17: Use the data now in the core of the app.

#.  Lines 20-26, and 28-33: try to be nice to the end-user by sharing information about a failure.
    By sharing both what the response tries to handle (context information), and the cause of failure provided by the service,
    you will increase the chance that the user is able to handle the failure self, or find the proper point of contact directly.

#.  Line 26: The service provided by ipTwist does not provide a schema for error messages.
    Instead, its OpenAPI spec documents how to handle status codes in case of failure.
    This is why a separate function is built to translate documented status code to explanations.

#. At ipTwist example, this procedure is called ``pr_GeolocateResponseHook``.

Further information:
--------------------

*   `Generating API client code from an OpenAPI specification <https://documentation.aimms.com/dataexchange/openapi-client.html>`_  
    The reference for creating and using OpenAPI generated AIMMS libraries.

*   `API gurus <https://apis.guru/>`_ lists shared Open API specifications, including the one from `ipTwist OpenAPI spec <https://api.apis.guru/v2/specs/iptwist.com/1.0.0/openapi.json>`_ . 

*   `Swagger <https://editor.swagger.io/>`_ provides an editor and viewer for OpenAPI specifications.


.. spelling::
   ipTwist