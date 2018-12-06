.. IMAGES

.. |project-options| image:: /Images/95-change-default-ui/project-options.jpg
.. |appearance-options| image:: /Images/95-change-default-ui/appearance-options.jpg
.. |change-ui| image:: /Images/95-change-default-ui/change-ui.jpg


.. BEGIN CONTENT

Make an On/Off Switch for Widgets
=================================

How to create an on/off toggle switch for WebUI widgets in your AIMMS project.

Creating an On/Off switch using CSS
-----------------------------------------------
Table or scalar widgets with a binary range appear in WebUI as checkbox options by default.
To render on/off switches instead, you can use CSS. 
It can be used for both table and scalar widgets.

:download: /Resources/UI/Downloads/scalar-switch.css

Save the CSS file in the folder "MainProject\WebUI\resources\css" of your project.
A checkbox will become a switch under the following conditions:
* The identifier needs to be an identifier with ``Binary`` as range.
* The widget needs to contain ``Switch`` in the name.
* The condition on the name of the widget allows you to control if a binary identifier should be shown as a switch or a checkbox.



show css snippet where "switch" is identified - that could be changed to something else, it's just how we wrote it
.. code-block:: css


:download: /Resources/UI/Downloads/ScalarSwitchExample.zip

Related topics
-----------------
* **AIMMS Documentation:** `Widget Options <https://manual.aimms.com/webui/widget-options.html>`_
* **AIMMS Documentation:** `Scalar Widget <https://manual.aimms.com/webui/scalar-widget.html>`_

.. END CONTENT

.. include:: ../includes/form.def