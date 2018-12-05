.. IMAGES

.. |project-options| image:: /Images/95-change-default-ui/project-options.jpg
.. |appearance-options| image:: /Images/95-change-default-ui/appearance-options.jpg
.. |change-ui| image:: /Images/95-change-default-ui/change-ui.jpg


.. BEGIN CONTENT

Make an On/Off Switch for Widgets
=================================

How to create an on/off toggle switch for widgets in your AIMMS project.

Creating an On/Off switch using CSS
-----------------------------------------------

To use on/off switches we developed CSS code, both for the table widget and for the scalar widget.

A checkbox will become a switch under the following conditions:
The identifier needs to be an identifier with ``Binary`` as range.
The widget needs to contain ``Switch`` in the name.
The condition on the name of the widget allows you to control if a binary identifier should be shown as a switch or a checkbox.
 
From a UI design perspective, they are not equivalent. 
Tapping a toggle switch is a double-duty action: selection and (instant) execution. 
A checkbox is just selection of an option, and its (pending) execution requires another control (i.e., clicking a button) to verify.

The CSS code can be found in the folder "MainProject\WebUI\resources\css" of the enclosed example project.


:download: /Resources/UI/Downloads/ScalarSwitchExample.zip
:download: /Resources/UI/Downloads/scalar-switch.css

Related topics
-----------------
* **AIMMS Documentation:** `Widget Options <https://manual.aimms.com/webui/widget-options.html>`_
* **AIMMS Documentation:** `Scalar Widget <https://manual.aimms.com/webui/scalar-widget.html>`_

.. END CONTENT

.. include:: ../includes/form.def