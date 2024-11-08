:orphan:
Combine Bar Chart and Line Chart
=================================

.. meta::
   :description: What happens when you combine a linechart and barchart? Bline chart, Combination chart. 
   :keywords: linechart, barchart, widget, WebUI, Application Specific Resource, combination-chart

Sometimes you may want to combine a bar and linechart in one widget. There are three ways to combine a line chart with a bar chart:

#.  The `Combination Chart <https://documentation.aimms.com/webui/combination-chart-widget.html>`_ available from AIMMS 4.85 and onwards. Using this widget is the best practice of combining a bar chart and a line chart.

#.  The `Bline chart <https://manual.aimms.com/webui/bar-line-chart-widget.html>`_ available since AIMMS 4.76. 

#.  The Application specific resource (ASR) blinechart.  
    If you are using that ASR, it is good practice to switch to the Combination Chart:

    #.  Functionality is consistent with other widgets

    #.  Supports more than two identifiers

    #.  The widget is maintained over AIMMS versions

    To remove the deprecated blinechart from your project, please remove the folder ``blinechart`` from the folder ``MainProject\WebUI\resources\javascript``.
    