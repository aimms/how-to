
.. IMAGES

.. |project-options| image:: /images/project-options.jpg
.. |appearance-options| image:: /images/appearance-options.jpg
.. |change-ui| image:: /images/change-ui.jpg



.. BEGIN CONTENT

How to Enable the Page Manager in Recent Versions of AIMMS 
================================================================

.. meta::
   :description: How to enable *Page Manager* for WinUI in your AIMMS project.
   :keywords: winui, page, manager

In AIMMS versions 4.40.1 and higher, WebUI is the default user interface. When creating a new project, AIMMS gives you the option to select a default UI and if WinUI is not selected, the *Page Manager* will not be available in your project. This article outlines how to re-enable the *Page Manager* in your AIMMS project. 

.. image:: /images/95-selecting-default-ui.png
   :align: center
   :alt: Setting the default UI when creating a new project

Re-Enabling the Page Manager
-----------------------------------------------

Follow the steps below to switch the default UI from WebUI to WinUI and re-enable the *Page Manager*

#. Go to *Settings > Project Options*.
    
   .. image:: /images/project-options.jpg
      :align: center

#. In *AIMMS Options*, expand the *Project* directory and select *Appearance*.
    
   .. image:: /images/appearance-options.jpg
      :align: center

#. Select *Default UI* in the top panel and select the *WinUI* radio button in the bottom panel. Click *Apply* and close the *Options* window.
    
   .. image:: /images/change-ui.jpg
      :align: center

Alternately, you can also click on the option *Options with nondefault value* to see the *Default UI* option there. 

.. image:: /images/nondefault-value.png
   :align: center

Save your project to save this setting. You can now open *Page Manager* with the ``F9`` key or from the menu in *Tools > Page Manager*.


.. END CONTENT

.. include:: /includes/form.def