Empty WebUI after upgrade
=========================

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

In the article on the :doc:`evolution of the WebUI<../333/333-update-webui-version>` several steps are described.  By at least executing the following steps, the WebUI created is retained.

#.  Switch to Map V2  

#.  `Filtering and new UX Theme <https://how-to.aimms.com/Articles/333/333-update-webui-version.html#aimms-4-66-filtering-and-new-ux-theme>`_

#.  `Serialize WebUI specification with a single file <https://how-to.aimms.com/Articles/333/333-update-webui-version.html#aimms-4-67-serialize-webui-specification-with-a-single-file>`_



