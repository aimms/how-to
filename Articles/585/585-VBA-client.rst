VBA client
================

Micosoft Office applications are used all over place; and they can invoke services via REST API.

.. note:: The `AIMMS COM <https://documentation.aimms.com/deprecation-table.html#:~:text=AIMMS%20COM%20is%20considered%20%E2%80%98old%E2%80%99%20architecture>`_ interface is Deprecated. Here an alternative is presented.

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

In this section, a simple example of an end user interface of a VBA app that deploys the CountTheStars interface as implemented by an AIMMS app.

Sheet1
^^^^^^^^^^

* Cell B1 will contain the status of the request

* Cell B2 will contain the number of stars

* There are two buttons, to be executed in sequence:

  * Make a CSV file from Sheet2.
  
  * Execute the ``CountTheSTars`` service.

Sheet2
^^^^^^^^^^^^^^^^^^

This sheet contains sample input data.


VBA
----------

There are three routines, detailed in separate subsection

Submit
^^^^^^^^^^^^^^^^^^^^

In this sub section, we'll handle submitting a request for executing a task using VBA.

Poll
^^^^^^^^^^^^^^^^^^^^

Receive
^^^^^^^^^^^^^^^^^^^^

References
---------------

#.  `Getting started with VBA in Office <https://learn.microsoft.com/en-us/office/vba/library-reference/concepts/getting-started-with-vba-in-office>`_

#.  The `VBA JSON library <https://github.com/VBA-tools/VBA-JSON>`_

#.  `Youtube video Use Excel VBA to Read API Data <https://www.youtube.com/watch?v=KZeYKZJzQIk>`_












