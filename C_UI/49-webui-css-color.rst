.. BEGIN CONTENT

Change Widget Styles with CSS
===================================
.. meta::
   :description: How to change colors with CSS in AIMMS WebUI widgets.
   :keywords: css, widget, webui, color, line, chart
   
.. todo:: Clarify difference between AIMMS set elements and HTML elements.

This article uses the Line Chart as an example to show how CSS styling can be applied to WebUI widgets.

In AIMMS WebUI, you can customize the appearance of elements using CSS. 

The general format for naming and styling elements in CSS is shown in the example below.

.. image:: /Images/49-webui-css-color/css-format.png

Use as many of the selectors (such as ``.tag-barchart`` or ``.annotation-HomeSum``) as needed to specify when to apply the rule in the declaration (between curly brackets). 

Using more selectors makes the rule more specific. Using fewer selectors makes the rule more general.

Styling to highlight information
--------------------------------

First the question: why would we want to add styling? Consider the following image of two lines in a line chart. 

.. image:: /Images/49-webui-css-color/Linechart-no-styling.png

I don't know about you, but I have to look twice to see that the lines have a different color. So to make it easier for the viewer of such charts, I may want to add styling to highlight differences.

.. image:: /Images/49-webui-css-color/Linechart-identifier-styling.png

This is achieved using the following css code:

.. code-block:: css
    :linenos:

    .tag-linechart[data-widget\.uri="Linechart_HomeAway_IdentifierStyling"] path.annotation-p_HomeSum{
            stroke: blue;
    }

    .tag-linechart[data-widget\.uri="Linechart_HomeAway_IdentifierStyling"] circle.annotation-p_HomeSum{
            stroke: blue;
            fill: blue;
    }

    .tag-linechart[data-widget\.uri="Linechart_HomeAway_IdentifierStyling"] path.annotation-p_AwaySum{
            stroke: green;
    }

    .tag-linechart[data-widget\.uri="Linechart_HomeAway_IdentifierStyling"] circle.annotation-p_AwaySum{
            stroke: green;
            fill: green;
    }

Line 1, the first selector, consists of the following portions:

#. ``.tag-linechart`` The widget type

#. ``[data-widget\.uri="Linechart_HomeAway_IdentifierStyling"]`` The identification of the individual widget.

#. ``path`` This is the element `type that is styled in the line chart. The other element type is the ``circle``.

#. ``.annotation-p_HomeSum`` This selects element within the line chart that are used to display the identifier ``p_HomeSum``. 

Styling options for Linecharts
------------------------------

The tables below reference some of the CSS selectors and properties that apply to Line Chart Widgets.

+-------------------------------------------------+-------------------------------------------------+
|CSS Selector                                     | Widget/Element Description                      |
+=================================================+=================================================+
| .tag-linechart                                  | defines the widget type (line chart)            |
+-------------------------------------------------+-------------------------------------------------+
| .tag-linechart[data-widget\.uri="*widgetname*"] | defines a widget by name                        |
+-------------------------------------------------+-------------------------------------------------+
| .circle                                         | defines the element (data points)               |
+-------------------------------------------------+-------------------------------------------------+
| .path                                           | defines the element (lines between data points) |
+-------------------------------------------------+-------------------------------------------------+
| .annotation-*identifiername*                    | defines an identifier represented in the widget |
+-------------------------------------------------+-------------------------------------------------+

You'll also need to know the *properties* that can be controlled to customize the appearance of elements.


+-------------------------------------------+-----------------------------------------------+
|CSS Property                               | Element Description                           |
+===========================================+===============================================+
| background: *color*                       | controls the background color of lines/points |
+-------------------------------------------+-----------------------------------------------+
| stroke: *color*                           | controls the stroke color of lines/points     |
+-------------------------------------------+-----------------------------------------------+
| fill: *color*                             | controls the fill color of lines/points       |
+-------------------------------------------+-----------------------------------------------+
| stroke-width: *line width in pixels*      | controls the line thickness                   |
+-------------------------------------------+-----------------------------------------------+
| stroke-dasharray: *dash length in pixels* | makes a dashed line, controls dash length     |
|                                           | (only applies to lines in a line chart)       |
+-------------------------------------------+-----------------------------------------------+


Identifying elements
----------------------

You need to know the *selectors* that the CSS file uses to identify particular widgets and elements in your WebUI page. 

A quick way to discover this is to right-click the widget in your web browser and click *Inspect*. You will see the source HTML code and CSS rules related to this element. This can help you to edit or write new rules in the CSS for particular elements. For instance, on a circle, you may get:

.. image:: /Images/49-webui-css-color/AnnotatinInfoCircle.png


Identifying circles by identifier, does not allow us to select a sub selection of circles.  
To facilitate this, AIMMS provides the option to add annotations to identifiers displayed in the WebUI.   

Let's continue our example, by asking ourselves in which seasons there was a significant, say more than 200, difference between home and away goals.

#. Add a string parameter (``yourAnnotation``) to your declaration with the desired annotation name (``whatever``) for its definition. 

We can model this by the string parameter: 

.. code-block:: aimms

    StringParameter sp_SignificantDiff {
        IndexDomain: i_Season;
        Definition: {
            if abs( p_AwaySum(i_Season) - p_HomeSum(i_Season) ) > 200 then
                "SignificantDiff"
            else
                ""
            endif
        }
    }
    
#. Then, in the attribute form of the identifier, add an annotation with ``webui::AnnotationsIdentifier`` and reference the string parameter you created. 

You'll also need to assign *annotations* to data identifiers you want to style in the CSS.

.. image:: /Images/49-webui-css-color/css-annotation.png

    
.. code-block:: aimms

    Parameter p_AwaySum {
        IndexDomain: i_Season;
        Comment: "Yearly sum of goals playing away";
        webui::AnnotationsIdentifier: sp_SignificantDiff;
    }

#. Reference the element in your style sheet, with a selector (``.annotation-whatever``). 

.. code-block:: css

    .tag-linechart[data-widget\.uri="Linechart_HomeAway_ElementStyling"] circle.annotation-p_HomeSum.annotation-SignificantDiff{
            stroke: red;
            fill: red;
    }

    .tag-linechart[data-widget\.uri="Linechart_HomeAway_ElementStyling"] circle.annotation-p_AwaySum.annotation-SignificantDiff{
            stroke: red;
            fill: red;
    }

.. image:: /Images/49-webui-css-color/Linechart-element-styling.png


See `Data-Dependent Styling <https://manual.aimms.com/webui/folder.html#data-dependent-styling>`_ for more details.


.. Styling elements
.. ----------------
.. 
.. Here is an example with a line chart and a bar chart, each containing two parameters, ``HomeSum`` and ``AwaySum``. 
.. 
.. With default styling, the WebUI page looks like this:
.. 
.. .. image:: /Images/49-webui-css-color/css-default.png
.. 
.. As you can see, ``HomeSum`` is represented in pink and ``AwaySum`` is represented in purple in both widget types.
.. 
.. Let's say you prefer more contrast, and want to make the ``AwaySum`` green. You can apply a general rule:
.. 
.. .. code-block:: css
.. 
..     .annotation-AwaySum{
..         background: green;
..         stroke: green;
..         fill: green;        
..     }
.. 
.. In the example above, ``.annotation-AwaySum`` refers to the *annotation* of the data identifier. 
.. 
.. This snippet defines the color for ``AwaySum`` in all of the widgets in your project as green. If you have multiple widgets containing ``AwaySum``, then data for ``AwaySum`` will always be green regardless of the widget type, as you can see in the image below. 
.. 
.. .. image:: /Images/49-webui-css-color/css-general.png
.. 
.. Now you've decided that pink doesn't look good on a line chart. Now you can apply a specific rule:
.. 
.. .. code-block:: css
.. 
..     .tag-linechart .annotation-HomeSum{
..         background: black;
..         stroke: black;
..         fill: black;
..     }
.. 
.. In the example above, ``.tag-linechart`` refers to the widget type and ``.annotation-HomeSum`` refers to the annotation of the data identifier.
.. 
.. This snippet defines the color for ``HomeSum`` as black in all the line chart widgets.
.. 
.. .. image:: /Images/49-webui-css-color/css-specific.png
.. 
.. Now you'd like to make another line chart named ``DashHomeSum`` where there should be a dashed line. The next example is even more specific:
.. 
.. .. code-block:: css
.. 
..     .tag-linechart[data-widget\.uri="DashHomeSum"] .annotation-HomeSum{
..         stroke-dasharray: 4;
..     }
.. 
.. In the example above, ``[data-widget\.uri="BlueHomeSum"]`` refers to the name of the widget and ``.annotation-HomeSum`` refers to the annotation of the data identifier. 
.. 
.. This snippet defines the line for ``HomeSum`` as dashed only in the bar chart widget with name ``DashHomeSum``.
.. 
.. .. image:: /Images/49-webui-css-color/css-more-specific.png


:download:`AIMMS project download <../Resources/Other/CompoundSets/Downloads/DeprecateCompoundSets.zip>` 


Related topics
-----------------
* **AIMMS Documentation:** `Widget Options <https://manual.aimms.com/webui/widget-options.html>`_
* **AIMMS Documentation:** `Line Chart Widget <https://manual.aimms.com/webui/line-chart-widget.html>`_
* **AIMMS Documentation:** `CSS Styling <https://manual.aimms.com/webui/folder.html#css-styling>`_

.. END CONTENT


.. include:: ../includes/form.def