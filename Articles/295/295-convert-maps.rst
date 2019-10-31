Upgrade Old WebUI Maps
========================
.. meta::
   :description: A brief guide to upgrading Maps in your WebUI app.
   :keywords: deprecation, webui, map, widget, convert, upgrade, update

Overview 
---------

In AIMMS 4.61 we released a new version of the Map widget. 

The new version gives several benefits over the previous version, including the following capabilities:

* Use multiple arc sets and multiple node sets.
* Hide the labels on the arcs.
* Display the arcs as straight lines instead of curved ones. 


Timeline
----------

When using AIMMS 4.61 and higher, existing Map widgets in your apps will remain the old version (V1) but any new Map widgets added are in the new version (V2).

AIMMS releases after **the end of November 2019** will not support projects using the `older version <https://manual.aimms.com/webui/map-widget-old-style.html>`_ of this feature.

To use AIMMS versions released after that date, you will need to be sure that you have upgraded any pages in your WebUI app using Map widgets.

Upgrading your project
-----------------------

The upgrade for this feature is manual, but quite simple. 

#. Open your AIMMS project in AIMMS 4.61 or later.
#. Replace old Map widgets in your WebUI pages with new Map widgets. 

For V2, you must separate the Latitude and Longitude into two separate identifiers declared in the model. These were contained in one identifier in V1.
All other identifiers that you used with V1 will work for V2.

Please refer to `AIMMS Documentation: Maps Widget <https://manual.aimms.com/webui/map-widget.html>`_ for further details about this widget.