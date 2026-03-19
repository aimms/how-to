Empty WebUI After Upgrade
=========================

.. meta::
   :description: Explains why a WebUI appears empty after upgrading from AIMMS 4.59 or older directly to 4.68 or newer, and lists the intermediate migration steps required to retain the WebUI.
   :keywords: WebUI upgrade, serialization, AIMMS 4.59, AIMMS 4.68, Map V2, UX theme, single file, migration, WebUI specification

Symptom
----------

After upgrading an AIMMS Project developed in AIMMS 4.59 or older, directly to AIMMS 4.68 or newer, the WebUI appears empty.

Explanation
------------

Considerable thought and refactoring work has gone into serialization of the WebUI during the 4.60 to AIMMS 4.68 and newer in several steps; to make the serialization of the WebUI specification:

#.  More robust

#.  More git friendly, and

#.  Much faster

Solution
---------

In the article on the :doc:`evolution of the WebUI<../333/333-update-webui-version>` several steps are described. By at least executing the following steps, the WebUI created is retained.

.. seealso::
    
    * `Switch to Map V2 <https://how-to.aimms.com/Articles/333/333-update-webui-version.html#aimms-4-61-map-v2>`_
    * `Filtering and new UX Theme <https://how-to.aimms.com/Articles/333/333-update-webui-version.html#aimms-4-66-widget-filtering>`_
    * `Serialize WebUI specification with a single file <https://how-to.aimms.com/Articles/333/333-update-webui-version.html#aimms-4-67-serialize-webui-specification-with-a-single-file>`_



