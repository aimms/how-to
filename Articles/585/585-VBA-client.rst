Using AIMMS Services with a VBA client
========================================

Many people are used to Excel to model their business problems and 
these Excel workbooks may contain the data required to instantiate an optimization problem.
In addition, Excel comes with Visual Basic for Applications (VBA) permitting via the  
WinHttp.WinHttpRequest.5.1 library to leverage REST API services.

The power of AIMMS is to model and solve optimization problems.
In a client-server architecture, on the AIMMS Cloud, this power is leveraged using AIMMS PRO REST API service.

This article explains how to use Excel and VBA to leverage the optimization power of AIMMS.

.. note:: 

    This article also provides an alternative for the deprecation of ``aimmscom``.

    Using AIMMS as an optimization tool inside an Excel workbook used to be facilitated 
    via the Microsoft COM interface, see also 
    `AIMMS COM <https://documentation.aimms.com/deprecation-table.html#:~:text=AIMMS%20COM%20is%20considered%20%E2%80%98old%E2%80%99%20architecture>`_ .


Preparation
--------------

Preparing to create a VBA Client of your own using AIMMS PRO Rest API Tasks, you will have to:

#.  Enable some libraries, via the Visual Basic Editor (Alt F11), Tools > References, most importantly:

    * `Microsoft WinHTTP Services, version 5.1 <https://learn.microsoft.com/en-us/windows/win32/winhttp/about-winhttp>`_
    
    * `Microsoft Scripting Runtime <https://learn.microsoft.com/en-us/previous-versions/office/developer/office2000/aa155438(v=office.10)>`_
    
    .. image:: images/vba-client-selected-tools-references.png
        :align: center

#.  Download and `install The VBA JSON library <https://github.com/VBA-tools/VBA-JSON#installation>`_.


Workbook
----------

In this section, a simple example of an end user interface of a VBA app that deploys the CountTheStars 
interface as implemented by an AIMMS app.


FrontEnd
^^^^^^^^^^

.. image:: images/ExcelFrontEndSheet.png
    :align: center

* Cell B1 will contain the status of the request

* Cell B2 will contain the number of stars

* There is one button:

  * Execute the ``CountTheSTars`` service.

RequestDataSheet
^^^^^^^^^^^^^^^^^^

This sheet contains sample input data.

.. image:: images/ExcelRequestDataSheet.png
    :align: center


VBA
----------

There are three steps, each detailed in separate subsection

Initiate
^^^^^^^^^^^^^^^^^^^^

In this sub section, we'll handle submitting a request for executing a task using VBA.

Monitor
^^^^^^^^^^^^^^^^^^^^

Receive result
^^^^^^^^^^^^^^^^^^^^

References
---------------

#.  `Getting started with VBA in Office <https://learn.microsoft.com/en-us/office/vba/library-reference/concepts/getting-started-with-vba-in-office>`_

#.  The `VBA JSON library <https://github.com/VBA-tools/VBA-JSON>`_

#.  `Youtube video Use Excel VBA to Read API Data <https://www.youtube.com/watch?v=KZeYKZJzQIk>`_












