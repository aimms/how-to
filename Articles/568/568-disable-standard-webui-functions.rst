Disable Standard WebUI Functions
================================

Here are listed ``css`` code on how to hide standard features of WebUI. Note that this **cannot** be done by widget, only by project. 

Download Image 
---------------

To hide the Download button:

.. code-block:: aimms 
    :linenos:

    .widget-menu__items-wrapper .widget-menu__item[title="Download Image"] {
        display: none;
    }

Add Filter Rule
------------------

To hide the Add Filter Rule button:

.. code-block:: aimms 
    :linenos:

    .popup-menu .filter-items .filter-icon {
        display: none;
    }

Search Button
-------------

.. code-block:: aimms 
    :linenos:

    .widgetdiv .awf-dock-button .search-support-content {
        visibility: hidden;
    }






