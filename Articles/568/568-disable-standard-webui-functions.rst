Disable Standard WebUI Functions
================================

This is an overview of ``css`` code that allows you to hide standard features of WebUI, found in almost every widget header.

Most of these either be hidden for the whole project (by using the examples directly) or you could target a specific widget/type, for which examples are shown below too.

Please be aware that *hiding* these items does not *disable* them. A knowledgeable end-user could undo the effect of your css customization very easily, when knowing what to look for; hiding is *not* a security measure.

Add Filter Rule
------------------

To hide the Add Filter Rule button (globally only):

.. code-block:: css 
    :linenos:

    .popup-menu .filter-items .filter-icon {
        display: none;
    }

.. grid:: 2

    .. grid-item-card::  With css

        .. image:: images/after_filter.png
            :align: center

    .. grid-item-card::  Without css

        .. image:: images/before_filter.png
            :align: center

Search Button
-------------

To hide it globally:

.. code-block:: css 
    :linenos:

    .awf-dock-button .search-support-content {
        visibility: hidden;
    }

You could prefix this selector like this, to single out widgets by widget URI:

.. raw:: html

    <div class="highlight-CSS notranslate"><div class="highlight"><pre>
        [data-widget\.uri="Cost Savings Table"] .awf-dock-button .search-support-content {
            visibility: hidden;
        }
    </pre></div>
    </div>

Of course, replace the uri with your own, correct, uri.

.. grid:: 2

    .. grid-item-card::  With css

        .. image:: images/after_search.png
            :align: center

    .. grid-item-card::  Without css

        .. image:: images/before_search.png
            :align: center    

Download Image,  Upload or Download Excel / Download CSV
--------------------------------------------------------

To hide one of the 4 Download/Upload items, globally:

.. code-block:: css
    :linenos:

    .widget-menu__item--upload-xlsx,
    .widget-menu__item--upload-xlsx + [role=separator] {
        display: none;
    }

Where ``upload-xlsx`` can be exchanged with ``download-xlxs``, ``download-csv`` and ``download-image``. ``help-me`` is a menu item that only App Developers might see, but it can be hidden too.

Not all items will have a separator following them, but if there is one, the above css will also hide it. Remove the second selector if you need to keep the separator.

These buttons can also be hidden for a specific widget URI, or a specific widget type. In that case, prefix the code like this:

.. code-block:: css
    :linenos:

    [data-owner-widget-uri="Gross revenue"] .widget-menu__item--download-xlsx,
    [data-owner-widget-uri="Gross revenue"] .widget-menu__item--download-xlsx + [role=separator] {
        display: none;
    }

or for a type:

.. code-block:: css
    :linenos:

    [data-owner-widget-type="table"] .widget-menu__item--download-xlsx,
    [data-owner-widget-type="table"] .widget-menu__item--download-xlsx + [role=separator] {
        display: none;
    }

.. grid:: 2

    .. grid-item-card::  With css

        .. image:: images/after_download.png
            :align: center

    .. grid-item-card::  Without css

        .. image:: images/before_download.png
            :align: center

.. grid:: 2

    .. grid-item-card::  With css

        .. image:: images/after_upload.png
            :align: center

    .. grid-item-card::  Without css

        .. image:: images/before_upload.png
            :align: center    

