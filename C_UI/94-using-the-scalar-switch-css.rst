
.. BEGIN CONTENT

Display an On/Off Switch in Widgets
===================================
.. meta::
   :description: How to create an on/off toggle switch with CSS in AIMMS WebUI widgets.
   :keywords: css, widget, webui, binary, switch, toggle, slider, selector

Identifiers with ``binary`` range are displayed as checkboxes in table and scalar widgets of AIMMS WebUI, by default. You can render on/off switches instead of checkboxes using CSS. 

.. image:: /Images/94-using-the-scalar-switch-css/binary-switch.png
    :align: center
	
Creating a binary switch
--------------------------------------
To render on/off switches in your AIMMS project, follow the steps below.

   1.  Create the location ``MainProject\WebUI\resources\css`` in your project. You need to create the folders ``resources`` and ``css`` manually.
   
   2. Download the example CSS file for the appropriate widget type:
   
		 :download:`scalar-binary-switch.css <../Resources/UI/Downloads/css/scalar-binary-switch.css>`
		 
		 :download:`table-binary-switch.css <../Resources/UI/Downloads/css/table-binary-switch.css>`
		    
   3. Copy the downloaded CSS file(s) to ``MainProject\WebUI\resources\css`` in your project.
   
Based on the code contained in the CSS file(s), WebUI renders a switch for all identifiers with a binary range in a scalar and/or pivot table widget.


Customizing the binary switch
-----------------------------------------------
You can modify the CSS used to make the switch in many ways. For example, to create the switch for pivot table widgets, to change the color of the switch, or apply the switch rules only to some widgets.

To customize the behavior of the CSS for the switch, follow the guidelines below.

Table vs. scalar widget
^^^^^^^^^^^^^^^^^^^^^^^
In the ``scalar-binary-switch.css`` file, ``.tag-scalar .boolean-value-editor`` identifies all scalar widgets with a binary range. 

In the ``table-binary-switch.css`` file, ``.tag-table .boolean-value-editor`` identifies all scalar widgets with a binary range. 

Color of switch
^^^^^^^^^^^^^^^^^^^^^
The color of the switch can be modified by editing the background color defined in the CSS snippet below. 

.. code-block:: css
    
    .tag-scalar .boolean-value-editor input:checked + span {
	    background: #004bff !important;
    }

The default AIMMS blue color is #004bff. You can replace this color code in the CSS snippet.

Filter based on name
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
To display checkboxes in some cases and switches in others, you can define rules for that behavior based on the widget name. 

The AIMMS project attached below contains CSS files with filters for both types of widgets.

:download:`filtered-binary-switch-example.zip <../Resources/UI/Downloads/filtered-binary-switch.zip>`

The additional tag ``[data-widget\.uri*="Switch"]`` in all snippets identifies widgets containing "Switch" in their names, as shown in the example below.

.. code-block:: css

    .tag-scalar[data-widget\.uri*="Switch"] .boolean-value-editor input {
    }

This "filter" replaces the checkboxes with switches only when ``Switch`` is in the widget name. The filter is **case sensitive**.



Last Updated: December 11, 2018 in AIMMS Version 4.61.3.0

Related topics
-----------------
* **AIMMS Documentation:** `Widget Options <https://manual.aimms.com/webui/widget-options.html>`_
* **AIMMS Documentation:** `Scalar Widget <https://manual.aimms.com/webui/scalar-widget.html>`_
* **AIMMS Documentation:** `Developing Custom Widgets <https://manual.aimms.com/webui/own-widgets.html>`_

.. END CONTENT


.. include:: ../includes/form.def



