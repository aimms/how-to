Modifying Session Timeout Duration for WebUI Apps
==================================================

.. meta::
   :description: Explains how to extend the idle session timeout for AIMMS WebUI applications by configuring the webui-check-alive-settings.txt file.
   :keywords: WebUI, session timeout, idle timeout, webui-check-alive-settings, data session, configuration, AIMMS Cloud

WebUI sessions have a default timeout timer that will end an idle data session after 300 seconds (5 minutes) 
with checks being made every 10 seconds.

To change this setting you can add a ``.txt`` file in the project's main folder with the name ``webui-check-alive-settings.txt``.
In this file you can specify the timeout timer with the format "time before ending session(seconds)" "interval checks(seconds)".

For example: ``600 10`` would terminate the session after 10 minutes with checks made every 10 seconds.

Ending a Session Explicitly
----------------------------

The timer is not the only way a session ends. To close one from the model:

* **WebUI apps**: ``pro::sessionmanager::FinishSession``
* **WinUI apps**: :any:`ExitAimms`

Note that after a logout a WebUI session stays active for a further five minutes before it is deactivated.

.. seealso::

   * :doc:`../351/351-app-initialization-termination-with-libraries`
