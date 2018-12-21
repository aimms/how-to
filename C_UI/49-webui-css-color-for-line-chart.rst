.. BEGIN CONTENT

Change Line Styles in Line Charts
===================================
.. meta::
   :description: How to change colors in line charts with CSS in AIMMS WebUI widgets.
   :keywords: css, widget, webui, color, line, chart

In Line Chart widgets for AIMMS WebUI, default styles are applied to data in your chart. You can customize elements in each widget separately using CSS. 

The general format is shown in the example below.

.. image:: /Images/49-webui-css-color-for-line-chart/css-format.png

Use as many of the selectors (such as ``.tag-barchart``, ``.point``, or ``.annotation-HomeSum``) as needed to identify which combination to apply the rule in the declaration (between curly brackets) to. 

Using more selectors makes the rule more specific. Using fewer selectors makes the rule more general.


Identifying elements
----------------------

You need to know the *selectors* that the CSS file uses to identify particular wigets and elements in your WebUI page. You'll also need to know the *properties* that can be controlled to customize the appearance of elements.

A quick way to discover this is to right-click the widget in your web browser and click *Inspect*. You will see the source HTML code and CSS rules related to this element. This can help you to edit or write new rules in the CSS for particular elements

The tables below reference CSS selectors and properties that apply to Line Chart Widgets.

+-------------------------------------------------+-------------------------------------------------+
|CSS Selector                                     | Widget/Element Description                      |
+=================================================+=================================================+
| .tag-linechart                                  | defines the widget type (line chart)            |
+-------------------------------------------------+-------------------------------------------------+
| .tag-linechart[data-widget\.uri="*widgetname*"] | defines a widget by name                        |
+-------------------------------------------------+-------------------------------------------------+
| .point                                          | defines the element (data points)               |
+-------------------------------------------------+-------------------------------------------------+
| .line                                           | defines the element (lines between data points) |
+-------------------------------------------------+-------------------------------------------------+
| .annotation-*identifiername*                    | defines an identifier represented in the widget |
+-------------------------------------------------+-------------------------------------------------+


+-------------------------------------------+-------------------------------------------+
|CSS Property                               | Element Description                       |
+===========================================+===========================================+
| background: *color*                       | controls the background color             |
+-------------------------------------------+-------------------------------------------+
| stroke: *color*                           | controls the stroke color                 |
+-------------------------------------------+-------------------------------------------+
| fill: *color*                             | controls the fill color                   |
+-------------------------------------------+-------------------------------------------+
| stroke-width: *line width in pixels*      | controls the line thickness               |
+-------------------------------------------+-------------------------------------------+
| stroke-dasharray: *dash length in pixels* | makes a dashed line, controls dash length |
|                                           | (only applies to lines in a line chart)   |    
+-------------------------------------------+-------------------------------------------+


Styling elements
----------------

Here is an example with a line chart and a bar chart, each containing two parameters, ``HomeSum`` and ``AwaySum``. 

With default styling, the WebUI page looks like this:

.. image:: .. image:: /Images/49-webui-css-color-for-line-chart/css-default.png

Below is an example of a general CSS rule that applies to all widget elements representing the ``AwaySum`` identifier. ::

    .annotation-AwaySum{

        background:red;

        stroke: red;

        fill:red;        

    }

In the example above, ``.annotation-AwaySum`` refers to the name of the data identifier. 

This snippet defines the color for ``AwaySum`` in all of the widgets in your project as red. If you have multiple widgets containing ``AwaySum``, then data for ``AwaySum`` will always be red regardless of the widget type. 

.. image:: /Images/49-webui-css-color-for-line-chart/css-general.png

The example below is more specific::

    .tag-linechart .annotation-HomeSum{

        background: green;

        stroke: green;

        fill: green;

    }

In the example above, ``.tag-linechart`` refers to the widget type and ``.annotation-HomeSum`` refers to the name of the data identifier.

This snippet defines color of ``HomeSum`` as green in all the line chart widgets. If you have multiple line chart widgets containing ``HomeSum``, then all of them will be green. 

.. image:: /Images/49-webui-css-color-for-line-chart/css-specific.png

The next example is even more specific::

    .tag-linechart[data-widget\.uri="BlueHomeSum"] .annotation-HomeSum{

        background: blue;

        stroke: blue;

        fill: blue;

        stroke-dasharray: 4;

    }

In the example above, ``[data-widget\.uri="BlueHomeSum"]`` refers to the name of the widget and ``.annotation-HomeSum`` refers to the name of the data identifier. 

This snippet defines the color of the line ``HomeSum`` as blue only in the barchart widget with name ``BlueHomeSum``.

.. image:: /Images/49-webui-css-color-for-line-chart/css-more-specific.png




Related topics
-----------------
* **AIMMS Documentation:** `Widget Options <https://manual.aimms.com/webui/widget-options.html>`_
* **AIMMS Documentation:** `Line Chart Widget <https://manual.aimms.com/webui/line-chart-widget.html>`_
* **AIMMS Documentation:** `Developing Custom Widgets <https://manual.aimms.com/webui/own-widgets.html>`_

.. END CONTENT


.. include:: ../includes/form.def