.. BEGIN CONTENT

Change Widget Styles with CSS
===================================
.. meta::
   :description: How to change colors with CSS in AIMMS WebUI widgets.
   :keywords: css, widget, webui, color, line, chart

This article uses the Line Chart as an example to show how CSS styling can be applied to WebUI widgets.

In AIMMS WebUI, you can customize the appearance of elements using CSS. 

The general format for naming and styling elements in CSS is shown in the example below.

.. image:: /Images/49-webui-css-color/css-format.png

Use as many of the selectors (such as ``.tag-barchart`` or ``.annotation-HomeSum``) as needed to specify when to apply the rule in the declaration (between curly brackets). 

Using more selectors makes the rule more specific. Using fewer selectors makes the rule more general.


Identifying elements
----------------------

You need to know the *selectors* that the CSS file uses to identify particular wigets and elements in your WebUI page. You'll also need to know the *properties* that can be controlled to customize the appearance of elements.

A quick way to discover this is to right-click the widget in your web browser and click *Inspect*. You will see the source HTML code and CSS rules related to this element. This can help you to edit or write new rules in the CSS for particular elements.

You'll also need to assign *annotations* to data identifiers you want to style in the CSS.

For every identifier specified in the Contents section of widget options, you can specify additional identifiers to certain properties. For identifier ``X`` you can specify a string parameter ``X_annotations`` to hold a name to be used as a CSS class on associated DOM elements in your model. 

Add a string parameter with the desired name for its definition. Then, in the attribute form of the identifier, add an annotation with ``webui::AnnotationsIdentifier`` and reference the string parameter you created. 

.. image:: /Images/49-webui-css-color/css-annotation.png

See `Data-Dependent Styling <https://manual.aimms.com/webui/folder.html#data-dependent-styling>`_ for more details.

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

.. image:: /Images/49-webui-css-color/css-default.png

As you can see, ``HomeSum`` is represented in pink and ``AwaySum`` is represented in purple in both widget types.

Let's say you prefer more contrast, and want to make the ``AwaySum`` green. You can apply a general rule::

    .annotation-AwaySum{

        background: green;

        stroke: green;

        fill: green;        

    }

In the example above, ``.annotation-AwaySum`` refers to the *annotation* of the data identifier. 

This snippet defines the color for ``AwaySum`` in all of the widgets in your project as green. If you have multiple widgets containing ``AwaySum``, then data for ``AwaySum`` will always be green regardless of the widget type, as you can see in the image below. 

.. image:: /Images/49-webui-css-color/css-general.png

Now you've decided that pink doesn't look good on a line chart. Now you can apply a specific rule::

    .tag-linechart .annotation-HomeSum{

        background: black;

        stroke: black;

        fill: black;

    }

In the example above, ``.tag-linechart`` refers to the widget type and ``.annotation-HomeSum`` refers to the annotation of the data identifier.

This snippet defines the color for ``HomeSum`` as black in all the line chart widgets.

.. image:: /Images/49-webui-css-color/css-specific.png

Now you'd like to make another line chart named ``DashHomeSum`` where there should be a dashed line. The next example is even more specific::

    .tag-linechart[data-widget\.uri="DashHomeSum"] .annotation-HomeSum{

        stroke-dasharray: 4;

    }

In the example above, ``[data-widget\.uri="BlueHomeSum"]`` refers to the name of the widget and ``.annotation-HomeSum`` refers to the annotation of the data identifier. 

This snippet defines the line for ``HomeSum`` as dashed only in the bar chart widget with name ``DashHomeSum``.

.. image:: /Images/49-webui-css-color/css-more-specific.png




Related topics
-----------------
* **AIMMS Documentation:** `Widget Options <https://manual.aimms.com/webui/widget-options.html>`_
* **AIMMS Documentation:** `Line Chart Widget <https://manual.aimms.com/webui/line-chart-widget.html>`_
* **AIMMS Documentation:** `CSS Styling <https://manual.aimms.com/webui/folder.html#css-styling>`_

.. END CONTENT


.. include:: ../includes/form.def