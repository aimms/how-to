Print Pages
===========

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Print page, print template, project user files
   :description: In this example the use of print templates is illustrated.

Direct download AIMMS Project :download:`Print Pages.zip <model/Print Pages.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Print%20Pages

In this example the use of print templates is illustrated. Print templates can be used to create paper reports. Paper reports containing the main model results are an indispensable part of any modeling application. The reports in AIMMS are created using print pages, a child page below a print template.

There are often tables that are larger than one page. These tables can be printed over several pages using adjustable split lines. 
In that case AIMMS can determine the pages needed to print the complete table, filling up a whole page, before starting on a new page. 
For many objects the user can describe how that object should be printed. Some object you may want to see on every page (for example a bitmap object containing the company logo), 
while other objects you may want to "spread over pages" and let AIMMS decide on how may pages it needs to display all data (for example with composite tables). 
A button will never be printed on a print page, it is only visible on screen.

(The action behind the "Print buttons" is such that nothing is sent to the printer, unless explicitly selected in a dialog box. Normally, the output is sent to a postscript file (``*.ps``).)

All the data in this example is taken from: U.S. Census Bureau, Statistical Abstract of the United States: 1999.

Keywords:
Print page, print template, project user files


