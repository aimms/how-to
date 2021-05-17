Openstreetmap images missing from network object
=================================================

.. \\chrisk-pc\users\chris\OneDrive - AIMMS B.V\Bugs\From ChrisK-PC\OpenERP\761\DELTA Release v4.0.3 (Model in AIMMS 4.8).
.. Contains example of existing (working).

.. https://aimms.odoo.com/web#id=34585&view_type=form&model=helpdesk.ticket&action=275&active_id=3&menu_id=163 (Romana)
.. https://aimms.odoo.com/web#id=34228&view_type=form&model=helpdesk.ticket&action=275&active_id=3&menu_id=163 
.. https://aimms.odoo.com/web#id=32142&view_type=form&model=helpdesk.ticket&action=275&active_id=3&menu_id=163 (Rutger de mare)
.. https://aimms.odoo.com/web#id=32387&view_type=form&model=helpdesk.ticket&menu_id= (marc wingender)
.. 29687
.. 25445
.. 22033
.. 
.. 
.. https://gitlab.aimms.com/aimms/customer-tickets/-/issues/3097
.. https://gitlab.aimms.com/aimms/customer-tickets/-/issues/2399
.. 
.. https://gitlab.aimms.com/aimms/aimms/-/issues/2078
.. 
.. helpful gitlab tickets: 
..      2323 (contains discussion, doc references)
..      3097 (bing)
..      2399 (reference to example)
..
.. .. not helpful gitlab tickets : 1990 368 626  1230 2367 2078 1724 1948 1824 8 1144 100

.. Notes from AIMMS Source inspection: D:\u\s\aimms\engine\aimms\GIS\libGISCarbonToolsPro\CarbonToolsContent.cs:656
.. virtual earth is bing.

Symptom
---------

The background of a WinUI network widget does not display a map anymore.
The background is provided by OpenstreetMap.

Explanation
------------

OpenstreetMap provides a tile or WMS service free of charge. 
The "free of charge" provision assumes a fair use policy; 
if you are using the application heavily, you will need to take a subscription, see also `OpenstreetMap Acceptable Use Policy <https://wiki.openstreetmap.org/wiki/Acceptable_Use_Policy>`_

Heavy use of other similar services by other providers will also require that you to take a subscription!

The AIMMS WinUI provides software to deploy these GIS services but the AIMMS WinUI does not provide these services themselves.

More information to change to a new service can be found via: 
Aimms Menu > Help > Contents and Index, and then search for GIS.

Selected other services
-------------------------

.. see also: Microsoft Bing Maps Platform APIs Terms Of Use <https://www.microsoft.com/en-us/maps/product>`_ and `Microsoft Bing licensing options <https://www.microsoft.com/en-us/maps/licensing/licensing-options>`_


#.  Bing provides their service free or subscripted.


    .. note:: Nowadays most web services use an apikey to log in, instead of a username/password. The apikey can be filled in in the username field in the Authentication of a GIS Source:

        .. image:: images/BingApiKeyUse.png
            :align: center
            
        Here the `sp_BingApiKey` is a string parameter holding the apikey you received from Bing.
    
#.  Mundialis provides the information together with OpenstreetMap:

    .. image:: images/mundialis.png
        :align: center

    Please respect the copyright of OpenstreetMap by crediting them using the following link: `© OpenStreetMap Contributors <http://www.openstreetmap.org/copyright>`_ 

#.  Yahoo no longer provides a map tile server.  In the AIMMS WinUI, the type "Yahoo" is translated to "OpenstreetMap".

Note that the above list is not exhaustive, see `Wikipedia <https://en.wikipedia.org/wiki/Web_Map_Service>`_


.. 
.. Alternative
.. ---------------
.. 
.. When using the WebUI from the AIMMS Integrated Development Evironment (IDE), then 
.. 
.. #.  https://maps.omniscale.com/en/
.. 
.. #.  https://www.openstreetmap.org/copyright
.. 
.. are used to publish the map.
.. 
.. Switch to AIMMS WebUI, and publish your app on the AIMMS Cloud; 
.. this will ensure that your GIS information will be obtained.  
.. See also `AIMMS 4.73 new features <https://www.aimms.com/english/developers/downloads/product-information/new-features/>`_ 
.. search for Omniscale.
.. 
.. 
.. 
.. ..  `Continued a <https://docs.microsoft.com/en-us/bingmaps/getting-started/bing-maps-dev-center-help/getting-a-bing-maps-key>`_
.. ..
.. ..  `Continued b <https://www.bingmapsportal.com/Announcement?redirect=True>`_ (my account > My keys)
.. ..
.. ..  `Continued c <https://www.bingmapsportal.com/Application>`_


