LocationIQ Integration with AIMMS
=====================================

:download:`AIMMS 25.9 project download <model/ht684LocationIQ.zip>` 

The legacy AIMMS function 
`GeoFindCoordinates <https://documentation.aimms.com/functionreference/system-interaction/environment-functions/geofindcoordinates.html>`_ 
is constrained by its reliance on Nominatim, 
which enforces strict rate limits, typically permitting at most one GPS coordinate request per second. 
This low limit can significantly impede performance for batch geocoding tasks.

To implement this in AIMMS, we need an external service to translate addresses into coordinates. 
While this article uses LocationIQ as a primary example, it is one of many available options.

The choice of geocoding provider often depends on specific needs regarding data coverage, 
pricing, or terms of service. For the purposes of this demonstration, 
we utilize LocationIQ due to its ease of setup and robust documentation. 
Users requiring different datasets or higher rate limits may consider various alternatives; 
fortunately, since AIMMS handles HTTP requests generically, 
the implementation steps remain largely the same regardless of the backend service chosen.

.. note:: 

    **Alternative Geocoding Providers**

    While this guide uses LocationIQ, the following services offer similar REST API functionality 
    that can be integrated with AIMMS:

    *   `Google Maps Platform <https://mapsplatform.google.com>`_
    *   `Mapbox <https://www.mapbox.com>`_ 
    *   `OpenCage <https://opencagedata.com>`_
    *   `Bing Maps <https://www.microsoft.com/maps>`_
    *   `HERE Technologies <https://www.here.com>`_
    *   `TomTom <https://developer.tomtom.com>`_
    *   `Nominatim (OpenStreetMap) <https://nominatim.org>`_

`LocationIQ <https://locationiq.com/>`_ provides a robust, high-performance alternative, 
offering faster access and significantly higher rate limits, 
even on its free tier, making it suitable for high-volume geocoding operations.

Through AIMMS Dex (`Data Exchange Library <https://documentation.aimms.com/dataexchange/index.html>`_), 
the LocationIQ services are easily accessed and managed asynchronously.

To integrate, you must first obtain an access key and then structure your AIMMS application 
to construct the API URL, execute the HTTP request, and subsequently parse the JSON feedback.

Geocoding service choice
------------------------

Interaction
--------------

Obtaining Access Token
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

Your Access Token is required for authentication with the LocationIQ API. 
This token uniquely identifies your application and is used to monitor your usage limits
and `obtained at <https://my.locationiq.com/dashboard#accesstoken>`_

.. image:: images/LocationIQ-dashboard.png
    :align: center


Specify Access Token
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

Once you have your token, you must store it within your AIMMS application. 
In the provided AIMMS App, the token is entered on the dedicated configuration page,
which stores the value in the scalar parameter ``sp_accessToken``.

In the enclosed AIMMS App, open the page ``liq::accesskey``

.. image:: images/libLocationIQ-ask-accesskey.png
    :align: center


Get coordinates from an Address
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The Geocoding endpoint (/v1/search) is used to convert a human-readable address string into geographic coordinates 
(Latitude and Longitude). This process is known as forward geocoding.

.. image:: images/ask-address-to-det-GPS-coords.png
    :align: center

AIMMS Client definition
-------------------------

Call service: Constructing the API Request
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The API call is constructed as a standard HTTP GET request. 
The URL must contain the required parameters, including the Access Token, 
the address query (q), and the desired format (format=json). 
The parameter ``_sp_reg`` may be used to specify the regional endpoint (e.g., ``us1`` or ``eu1``).

Create the URL according to LocationIQ documentation:

.. code-block:: aimms 
    :linenos:

    _sp_url := 
        formatString("https://%s.locationiq.com/v1/search?" , _sp_reg)+
        formatString("key=%s&",sp_accessToken)+
        formatString("q=%s&format=json&",_sp_query);

Make the call using `dex::client::NewRequest <https://documentation.aimms.com/dataexchange/api.html#dex-client-NewRequest>`_. 
he asynchronous nature of the call is managed by specifying a callback procedure (``_ep_callback``) 
that will process the response upon completion.

A request tag is added, which allows the subsequent waiting mechanism 
to filter and only wait for events related to this specific request, 
improving application responsiveness.

.. code-block:: aimms 
    :linenos:

    dex::client::NewRequest(
        theRequest    :  _sp_theRequest, 
        url           :  _sp_url, 
        callback      :  _ep_callback, 
        httpMethod    :  'GET', 
        requestFile   :  "", 
        responseFile  :  sp_libfolder + "/data/getLocation.json", 
        traceFile     :  "", 
        requestOffset :  0, 
        requestSize   :  0);
    dex::client::AddRequestTag(_sp_theRequest, _sp_theRequest);
    dex::client::PerformRequest(_sp_theRequest);

Response file 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The LocationIQ Geocoding API returns a JSON array containing one or more matching location objects. 
Each object provides detailed information, including the calculated latitude and longitude, 
a bounding box, a human-readable ``display_name``, and metadata like ``osm_id``. 
The response is saved to the specified responseFile.

.. code-block:: json 
    :linenos:
    
    [
        {
            "boundingbox": [
                "53.0625606",
                "53.1235639",
                "4.7043896",
                "4.8031591"
            ],
            "class": "boundary",
            "display_name": "De Koog, Texel, North Holland, Netherlands",
            "icon": "https://locationiq.org/static/images/mapicons/poi_boundary_administrative.p.20.png",
            "importance": 0.653932315897569,
            "lat": "53.0998817",
            "licence": "https://locationiq.com/attribution",
            "lon": "4.7626457",
            "osm_id": "2730421",
            "osm_type": "relation",
            "place_id": "157813526",
            "type": "administrative"
        },
        { "...":"..." }
    ]


Mapping to read it back:
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

There are two indices here, one for the list, and one to identify items themselves.

The AimmsJSONMapping structure instructs the ``dex::ReadFromFile`` function on 
how to convert the received JSON data into AIMMS identifiers.

ArrayMapping: Specifies that the root element (the list [...]) is an array.

ObjectMapping: The objects within the array are mapped iteratively using the AIMMS index ``liq::i_result``.

place_id: The unique identifier for the location is stored in the AIMMS index ``liq::i_placeId``.

``lat`` and ``lon``: The latitude and longitude values are stored in the AIMMS parameters ``liq::p_Latitude`` and ``liq::p_Longitude``, 
indexed by both the request result (``liq::i_result``) and the place ID (``liq::i_placeId``).

.. code-block:: xml 
    :linenos:

    <AimmsJSONMapping>
        <ArrayMapping>
            <ObjectMapping iterative-binds-to="liq::i_result">
                <ValueMapping name="place_id" binds-to="liq::i_placeId"/>
                <ValueMapping name="lat" maps-to="liq::p_Latitude(liq::i_result,liq::i_placeId)"/>
                <ValueMapping name="lon" maps-to="liq::p_Longitude(liq::i_result,liq::i_placeId)"/>
            </ObjectMapping>
        </ArrayMapping>
    </AimmsJSONMapping>

Callback
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 

The callback procedure, specified via ``_ep_callback``, is automatically executed once 
the HTTP request completes. 
It checks the HTTP status code to ensure the request was successful before processing the data.

*   If the ``statusCode`` is 200 (Success):

    * The `dex::ReadFromFile <https://documentation.aimms.com/dataexchange/api.html#dex-ReadFromFile>`_ 
      function uses the defined mapping (``getLocationJSON``) to import the coordinates and identifiers into the AIMMS parameters.
    * The code then typically extracts the latitude and longitude from the first result 
      (``_ep_first_res``) in the returned list and assigns them to global scalar parameters 
      (``p_globLat``, ``p_globLon``) for immediate use.

*   If the statusCode indicates an error (e.g., 401 Unauthorized, 403 Forbidden/Rate Limited), 
    an appropriate error handling section, not detailed here, should be executed to alert 
    the user or log the issue.

The callback routine that captures and transforms the response.

.. code-block:: aimms 
    :linenos:

    if statusCode = 200 then
        dex::ReadFromFile(
            dataFile         :  sp_libfolder + "/data/getLocation.json", 
            mappingName      :  "getLocationJSON", 
            emptyIdentifiers :  0, 
            emptySets        :  0, 
            resetCounters    :  0);
    
        _ep_first_res := first( s_results );
        _ep_first_pid := first( s_placeIds );
        p_globLat := p_latitude(  _ep_first_res, _ep_first_pid );
        p_globLon := p_longitude( _ep_first_res, _ep_first_pid );
    else
        ! Error handling.

Error handling
^^^^^^^^^^^^^^^^^^^^^

Especially with Client Server communication, errors may occur:

*   Network connectivity issues
*   Providing an illegal Access Token
*   Asking location of an address that does not exist

Code that handles such errors is provided below:

.. code-block:: aimms 
    :linenos:

    if statusCode = 0 then
        ! Message from CURL.
        dex::client::GetErrorMessage(errorCode,_sp_curl_message);
        _sp_error_message := formatString("Error obtaining GPS coordinates from LocationIQ for \"%s\", CURL details: \"%s\"", 
            sp_req_address, _sp_curl_message);
    else
        ! Message from Server
        _ep_statusCode := StringToElement(dex::HTTPStatusCodes, formatString("%i", statusCode),create:0);
        if _ep_statusCode then
            dex::ReadFromFile(
                dataFile         :  sp_libfolder + "/data/getLocation.json", 
                mappingName      :  "errorJSON", 
                emptyIdentifiers :  0, 
                emptySets        :  0, 
                resetCounters    :  0);
            _sp_error_message := formatString("Error obtaining GPS coordinates from LocationIQ for \"%s\", status code %e: \"%s\", error code: %i, LocationIQ feedback: \"%s\"", 
                sp_req_address, _ep_statusCode, dex::HTTPStatusCodeDescription(_ep_statusCode), errorCode, sp_locationIQ_error_message );
        else
            ! Shouldn't happen, unknown status/error code. 
            _sp_error_message := formatString("Error obtaining GPS coordinates from LocationIQ for \"%s\", unknown status code: %i, error code: %i", 
                sp_req_address, statusCode, errorCode );
        endif ;
    endif ;
    raise error _sp_error_message ;

Remarks:

*   Lines 3-5: When the statusCode is 0, the server isn't reached and CURL provides error details via `dex::client::GetErrorMessage <https://documentation.aimms.com/dataexchange/api.html#dex-client-GetErrorMessage>`_
*   Lines 10-17: With a valid statusCode, the error message is read back from the server.  
    The filename used for that is the same as for the requested response, but uses a different JSON.
    All the information is put into the error message and returned.

Summary
----------

This article outlines the integration of the LocationIQ API within AIMMS to perform high-performance forward geocoding. 
It demonstrates how to replace the legacy GeoFindCoordinates function with AIMMS Data Exchange (DEX) 
to achieve higher rate limits and asynchronous processing. 
The guide covers obtaining an API access key, constructing RESTful GET requests, 
mapping JSON responses to AIMMS identifiers, and implementing callback procedures to 
handle both successful data retrieval and potential communication errors.

.. spelling:word-list::
    
    geocoding
    dex
    responseFile
    statusCode