
.. BEGIN CONTENT

Make an On/Off Switch for Widgets
=================================

How to create an on/off toggle switch for WebUI widgets in your AIMMS project.

Creating an On/Off switch using CSS
-----------------------------------------------
Identifiers with a binary range appear as checkbox options in table or scalar widgets of AIMMS WebUI, by default.

You can render on/off switches instead of checkboxes with CSS.

Based on the CSS code snippets (contained in the attached files), WebUI renders a switch in a table or pivot table widget under these conditions:

* The identifier range is binary.
* The name contains ``Switch`` in the string.

.. topic:: To add this functionality to your project, follow the steps below.

    1. Download the example project containing CSS files:

        :download:`ScalarSwitchExample.zip<../Resources/UI/Downloads/ScalarSwitchExample.zip>`

    2. Save the CSS files in the folder ``MainProject\WebUI\resources\css`` to the same location in your project. If the location does not exist, create the folders manually.

|
In the CSS code snippets contained in the attached files, ``[data-widget\.uri*="Switch"]`` identifies the string ``Switch`` in the name of the widgets.
    
This could be changed if you prefer, but must be changed in all instances of ``[data-widget\.uri*="Switch"]``.


Related topics
-----------------
* **AIMMS Documentation:** `Widget Options <https://manual.aimms.com/webui/widget-options.html>`_
* **AIMMS Documentation:** `Scalar Widget <https://manual.aimms.com/webui/scalar-widget.html>`_
* **AIMMS Documentation:** `Developing Custom Widgets <https://manual.aimms.com/webui/own-widgets.html>`_
.. END CONTENT

.. include:: ../includes/form.def