
.. BEGIN CONTENT

Display an On/Off Switch in Widgets
===================================

Identifiers with ``binary`` range are displayed as checkboxes in table and scalar widgets of AIMMS WebUI, by default. You can render on/off switches instead of checkboxes with CSS. By placing the below .css file in the ``MainProject\WebUI\resources\css`` folder, all the scalar widgets with binary identifiers will display on/off switches. Note that the ``resources\css`` folders are not present by default and you will need to create these folders inside the WebUI folder. 

.. image:: /Images/94-using-the-scalar-switch-css/binary-switch.png
    :align: center
    :scale: 60

:download: `binary-switch.css<../Resources/UI/Downloads/css/scalar-binary-switch.css>`

Customizing the Binary Switch
-----------------------------------------------

If you notice the CSS code snippets in the ``binary-switch.css`` file, all of them start with ``.tag-scalar .boolean-value-editor``. This instructs AIMMS WebUI to modify the default appearance of all scalar widgets with ``binary`` range. You can extend this to identifiers in table widgets by duplicating the ``binary-switch.css`` file and replacing ``.tag-scalar`` with ``.tag-table``.

The color of the switch can be modified by editing the below snippet in the css file. The default AIMMS blue color is #004bff.

.. code-block:: css
    
    .tag-scalar .boolean-value-editor input:checked + span {
	    background: #004bff !important;
    }

By default, these css files will replace all the checkboxes in your UI to on/off switches. You might want to display checkboxes in some places and switches in others. In order to do that, you will need to add an additional tag ``[data-widget\.uri*=Switch]`` in the css snippets as below. 

.. code-block:: css

    .tag-scalar[data-widget\.uri*=Switch] .boolean-value-editor input {
    }

This will replace the checkboxes with switches only in the scalar widgets that contain "Switch" in their names. The example project below contains two table and scalar widgets each with the checkboxes replaced only in the ones with "Switch" in their names. This could be changed if you prefer, but must be changed in all instances of ``[data-widget\.uri*=Switch]``.

:download: `filtered-binary-switch-example<../Resources/UI/Downloads/binary-switch.zip>`

.. Note:: 

    The filter "Switch" is case sensitive. ``[data-widget\.uri*=Switch]`` is equivalent to ``[data-widget\.uri*="Switch"]``. However, you will need to use the "" if the filter name contains a space. 

Last Updated: December, 2018 in AIMMS Version 4.61.3.0

Related topics
-----------------
* **AIMMS Documentation:** `Widget Options <https://manual.aimms.com/webui/widget-options.html>`_
* **AIMMS Documentation:** `Scalar Widget <https://manual.aimms.com/webui/scalar-widget.html>`_
* **AIMMS Documentation:** `Developing Custom Widgets <https://manual.aimms.com/webui/own-widgets.html>`_
.. END CONTENT

.. include:: ../includes/form.def