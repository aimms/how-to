GIS Support
===========

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: GIS, Network Object, Bitmap, Arcs, Nodes
   :description: This AIMMS project illustrates how to display map data obtained from a Geographical Information System (GIS) in AIMMS.

Direct download AIMMS Project :download:`GIS Support.zip <model/GIS Support.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/GIS%20Support

This AIMMS project illustrates how to display map data obtained from a Geographical Information System (GIS) in AIMMS.
 
AIMMS supports various types of GIS sources:


- OGC Web Map Service (WMS)
- OGC Web Feature Service (WFS)
- Microsoft Bing Maps
- OpenStreetMap
- locally stored ESRI shape files, the format as used by ArcGIS and ArcView
- locally stored feature data stored in OGC Geography Markup Language (GML) format
- locally stored map image files
 
In AIMMS, the display of GIS are available as part of the page network object. A GIS map can be displayed as a (dynamic) background image of a network. In this example, three demo pages are available. 

Demo page 1: A GIS map obtained from two WMS sources. Arcs and Nodes are drawn on top of the GIS background image. The arcs are drawn based on the result of optimizing a simple transport model. Some selection objects are created to allow users to hide/display some GIS layers.

Demo page 2: illustrates how the network object serves as a map viewer object (i.e. a network object without any nodes and arcs being specified). The GIS data is imported from a WMS source as well as from several local GML files and ESRI files. The second demo page also illustrates the possibility to control the layer color (for the GML layers) from within your AIMMS model.

Demo page 3: illustrates how AIMMS is able to use maps provided by OpenStreetMap.
 
Disclaimer: 
All usage of map data is subjective to license terms set forth by the holders of this map data (Microsoft, OpenStreetMap, ESRI etc.). The use of these sources in any of our examples is meant for illustrative purposes only and does not imply any usage rights for AIMMS license holders. It is the user's responsibility to be compliant with the licenses agreement of the respective providers when using map data sources.
 
Keywords:
GIS, Network Object, Bitmap, Arcs, Nodes



