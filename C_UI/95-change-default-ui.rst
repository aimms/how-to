
.. IMAGES

.. |project-options| image:: /Images/95-change-default-ui/project-options.jpg
.. |appearance-options| image:: /Images/95-change-default-ui/appearance-options.jpg
.. |change-ui| image:: /Images/95-change-default-ui/change-ui.jpg



.. BEGIN CONTENT

Enable Page Manager
======================

.. meta::
   :description: How to enable Page Manager for WinUI in your AIMMS project.
   :keywords: winui, page, manager

How to enable Page Manager for WinUI in your AIMMS project.

Changing the default UI to enable Page Manager
-----------------------------------------------

When you create an AIMMS project, you must select a default UI. If you choose WebUI (preselected setting) then you cannot access the Page Manager for WinUI until you change the default UI to WinUI.

Follow the steps below to switch the default UI to WinUI:

1. Go to *Settings > Project Options*.
    
|project-options|

2. In *AIMMS Options*, expand the *Project* directory and select *Appearance*.
    
|appearance-options|

3. Select *Default UI* in the top panel and select the *WinUI* radio button in the bottom panel. Click *Apply* and close the *Options* window.
    
|change-ui|

Save your project to save this setting.

You can open *Page Manager* with the ``F9`` key or from the menu in *Tools > Page Manager*.


.. END CONTENT

.. include:: ../includes/form.def

.. author: Jessica Valasek Estenssoro
.. checked by: -Khang Bui
.. updated: November 1, 2018