Gantt Chart
===========

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Gantt Chart, Overlapped Bars, Composite Table, Tooltips
   :description: This AIMMS project illustrates the basic use of the Gantt chart object in AIMMS.

Direct download AIMMS Project :download:`Gantt Chart.zip <model/Gantt Chart.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Gantt%20Chart

This AIMMS project illustrates the basic use of the Gantt chart object in AIMMS. It illustrates both the identifier declarations that are typical for displaying data in a Gantt chart, and AIMMS' capabilities to interpret this data with respect to a given reference date.

By duplicating the row index in the domain identifier used to determine whether a task is displayed in the Gantt chart for a particular tuple in the domain, and using one of these indices in the identifiers for start time and duration and the other to identify the row domain, AIMMS allows you to move tasks vertically within the Gantt chart. Without such a duplication of the row index, vertical movement would not be possible, as simply copying the remaining task data to the new tuple, may lead to a loss of tasks.

Tasks that have been moved vertically, can be recognized by the fact that both indices in the domain identifier are different. An example procedure in the model recognizes such tasks, and moves all task data to an appropriate tuple for which both indices are the same again.

For a certain row, if there are tasks overlapping each other, by setting the option "Show overlapping bar with a different vertical offset", the overlapped tasks would be displayed with different vertical offset.

Both the Composite Table and Gantt Chart have tooltips set up to show when the mouse hovers over a specific row, cell, or bar.

Keywords:
Gantt Chart, Overlapped Bars, Composite Table, Tooltips



