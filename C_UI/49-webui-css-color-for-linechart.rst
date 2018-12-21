.. BEGIN CONTENT

Change Line Styles in Line Charts
===================================
.. meta::
   :description: How to change colors in line charts with CSS in AIMMS WebUI widgets.
   :keywords: css, widget, webui, color, line, chart

In Line Chart widgets for AIMMS WebUI, default styles are applied to data in your chart. You can customize each line separately using CSS. 

Identifying elements
----------------------

You need to know the annotation name for each of the different lines on your chart (based on the identifier name). 

To discover this, right-click the widget in your web browser and click *Inspect* to view the source HTML code and CSS rules related to this element. You can also find the information in the widget options (*Gear icon - Settings > Wrench icon - Advanced*)

In the CSS you will see .tag names for different widgets to identify their type. 

Properties in ``.widgetdiv .tag-linechart .line`` are applied to all the lines in the widget. 

Styling elements
----------------

The CSS code for styling lines in line charts is like this:: 

    /* lines are red */
    .widgetdiv .tag-linechart[data-widget\.uri="Line Chart"] .line{
        stroke-width:2;
        background:red;
        stroke:red;
    }

    /* data points are red */
    .widgetdiv .tag-linechart[data-widget\.uri="Line Chart"] .point{
        background:red;
        stroke:red;
        fill:red;
    }

    /* AwaySum has dashed line; others will have default solid line */
    .tag-linechart[data-widget\.uri="Line Chart"] .annotation-AwaySum{
        stroke-dasharray: 4;
    }

All the lines in the linechart widget named ``Line Chart`` are red, but only the line for the identifier ``AwaySum`` is dashed. 



Example
---------

Here is an example with a line chart and a bar chart, each containing two parameters, ``HomeSum`` and ``AwaySum``. 

Below are the CSS rules that apply to these elements. ::

    .annotation-AwaySum{

        background:red;

        stroke: red;

        fill:red;

        stroke-dasharray: 4;

    }



This defines the color for ``AwaySum`` in all of the widgets in your project as red. The stroke-dasharray will be applied only where possible i.e., in line chart only but not in bar chart. ::

    .tag-linechart .annotation-HomeSum{

        background: green;

        stroke: green;

        fill: green;

    }



This defines color of ```HomeSum`` as green in all the line chart widgets. If you have multiple line chart widgets containing ``HomeSum``, then all of them will be green. ::

    .tag-barchart[data-widget\.uri="BlueHomeSum"] .annotation-HomeSum{

        background: blue;

        stroke: blue;

        fill: blue;

    }


In the examples above, ``[data-widget\.uri="BlueHomeSum"]`` refers to the name of the widget and ``.annotation-HomeSum`` refers to the name of the data identifier itself. 

This snippet defines the color of the line ``HomeSum`` as blue only in the barchart widget with name ``BlueHomeSum``.


So, general rule is as follows. (Replace the text in italics with your project data.) ::

.tag-*widgettype*[data-widget\.uri="*WidgetName*"] .annotation-*IdentifierName*{

    *attribute1*: *value1*;

    *attribute2*: *value2*;

}


