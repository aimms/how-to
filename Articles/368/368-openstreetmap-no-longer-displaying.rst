Openstreetmap images are no longer displayed
=============================================

Diagnosis
---------

Without any software change, but with increased usage of the app, the images that used to be rendered using OpenstreetMap are no longer visible.

Explanation
------------

OpenstreetMap is a service, and with limited use, it is free of charge. 
When you use it heavily, the service needs to be paid for.

The AIMMS WinUI provides software to deploy GIS services, including OpenstreetMap, Yahoo, and Bing but does not provide the services themselves.

More information can be found in the help of AIMMS via: Aimms Menu > Help > Contents and Index, and then search for GIS.

Solution
---------

#.  Contact a GIS service provider and obtain a subscription.

#.  Switch to AIMMS WebUI, and publish your app on the AIMMS Cloud; this will ensure that your GIS information will be obtained.  See also `AIMMS 4.73 new features <https://www.aimms.com/english/developers/downloads/product-information/new-features/>`_ search for Omniscale.