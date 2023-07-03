Hiding Widget Header
=========================

.. meta::
   :description: how to hide widget header.
   :keywords:  header, widget, hide, css, custom

This article illustrates how to hide the widget header using ``css``. Please use the `Employee Scheduling <https://how-to.aimms.com/Articles/387/387-employee-scheduling.html>`_ example to experiment with this feature.

Example
--------

To hide default values using ``css``, ie. from :numref:`figure-01-591` to :numref:`figure-02-591`: 

.. grid:: 2

    .. grid-item-card::  

        .. _figure-01-591:

        .. figure:: images/before.png
            :align: center

            With no css code.


    .. grid-item-card::  

        .. _figure-02-591:

        .. figure:: images/after.png
            :align: center

            With css custom code.


You need the following ``css`` code snippet.

.. code-block:: text
    :linenos:

    .aimms-widget[data-widget\.uri="gnt_shiftGantt"] .awf-dock.top{
        display: none;
    }

**Remarks:**

* ``gnt_shiftGantt`` is the name of the widget. 

On `Employee Scheduling <https://how-to.aimms.com/Articles/387/387-employee-scheduling.html>`_ example you will find this ``css`` file under ``./MainProject/WebUI/resources/stylesheets`` named as ``custom.css``. 
This code snippet can be used to hide the widget header per widget, however, note that while the ``css`` file is in place, 
the settings button of your widget will also disappear. 
To edit the widget, comment the ``css`` lines and refresh your page. 


