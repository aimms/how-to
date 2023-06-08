Modifying Session Timeout Duration for WebUI Apps
==================================================

.. meta::
   :description: Extending the timeout timer for WebUI sessions
   :keywords: idle, session, WebUI, timeout, extending, extend

WebUI sessions have a default timeout timer that will end an idle data session after 300 seconds (5 minutes) 
with checks being made every 10 seconds.

To change this setting you can add a ``.txt`` file in the project's main folder with the name ``webui-check-alive-settings.txt``.
In this file you can specify the timeout timer with the format "time before ending session(seconds)" "interval checks(seconds)".

For example: ``600 10`` would terminate the session after 10 minutes with checks made every 10 seconds.
