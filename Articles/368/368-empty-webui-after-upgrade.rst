Empty WebUI after upgrade
=========================

Diagnosis
----------

When you upgrade an AIMMS Project developed in AIMMS 4.59 or older, directly to AIMMS 4.68 or newer, the WebUI appears empty.

Explanation
------------

Considerable thought and refactoring work has gone into serialization of the WebUI during the 4.60 to AIMMS 4.68 and newer in several steps; to make the serialization of the WebUI specification:

#.  More robust

#.  More git friendly, and

#.  Much faster

Solution
---------

In the article on the :doc:`evolution of the WebUI<../333/333-update-webui-version>` several steps are described.  Please make sure to execute at least the following steps:

#.  `Map V2 <https://how-to.aimms.com/Articles/333/333-update-webui-version.html#aimms-4-61-map-v2>`_

#.  `Filtering and new UX Theme <https://how-to.aimms.com/Articles/333/333-update-webui-version.html#aimms-4-66-filtering-and-new-ux-theme>`_

#.  `Serialize WebUI specification with a single file <https://how-to.aimms.com/Articles/333/333-update-webui-version.html#aimms-4-67-serialize-webui-specification-with-a-single-file>`_



