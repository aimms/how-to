Centering Cells in Table Widget
================================

.. meta::
   :description: how to center cell table widget.
   :keywords:  center, table, widget, cell, css, custom

This article illustrates how to center cells in Table Widget using ``css``. Please use the `Employee Scheduling <https://how-to.aimms.com/Articles/387/387-employee-scheduling.html>`_ example to experiment with this feature.

Centering String Parameters
----------------------------
To center string parameter cells using ``css``, i.e. from :numref:`figure-01-608` to :numref:`figure-02-608`: 

.. grid:: 2

    .. grid-item-card::  

        .. _figure-01-608:

        .. figure:: images/string-before.png
            :align: center

            With no css code.


    .. grid-item-card::  

        .. _figure-02-608:

        .. figure:: images/string-after.png
            :align: center

            With css custom code.


You need the following ``css`` code snippet.

.. code-block:: css
    :linenos:

    /*Centering cells*/
    .tag-table .cell.flag-string input{
        text-align: center;
    }

Centering Parameters
---------------------
To center parameter cells using ``css``, i.e. from :numref:`figure-03-608` to :numref:`figure-04-608`: 

.. grid:: 2

    .. grid-item-card::  

        .. _figure-03-608:

        .. figure:: images/parameter-before.png
            :align: center

            With no css code.


    .. grid-item-card::  

        .. _figure-04-608:

        .. figure:: images/parameter-after.png
            :align: center

            With css custom code.


You need the following ``css`` code snippet.

.. code-block:: css
    :linenos:

    /*Centering cells*/
    .tag-table .cell.flag-number input{
        text-align: center;
    }

Centering Element Parameters
-----------------------------
To center element parameter cells in the Table Widget using ``css``, you need the following ``css`` code snippet.

.. code-block:: css
    :linenos:

    /*Centering cells*/
    .tag-table .cell.flag-string .cell-wrapper{
        text-align: center;
    }


Combining Everything
---------------------

To combine all ``css`` classes mentioned above and center all type of cells, you need the following ``css`` code snippet.

.. code-block:: css
    :linenos:

    /*Centering cells*/
    .tag-table .cell.flag-string .cell-wrapper, 
    .tag-table .cell.flag-number input,
    .tag-table .cell.flag-string input{
        text-align: center;
    }


On `Employee Scheduling <https://how-to.aimms.com/Articles/387/387-employee-scheduling.html>`_ example you will find this ``css`` file under ``./MainProject/WebUI/resources/stylesheets`` named as ``custom.css``. 


.. spelling:word-list::
    
    i.e.

