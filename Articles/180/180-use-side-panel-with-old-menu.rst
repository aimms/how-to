.. belongs in WebUI/CSS

Using Sidepanels with the Old WebUI Theme
=============================================

Sidepanels were released in AIMMS version 4.64.1. If you are using the previously shared application specific resources (ASR) to retain the old theme of WebUI (AIMMS 4.58 and lower), links to the sidepanel pages will be also displayed in the top navigation menu. 


Adapting ASR for sidepanels
----------------------------------

When using sidepanels with ASR, duplicated links appear in the top navigation menu. 

To hide these duplicated links:

#. Go to the ASR folder in your project files.

#. Edit the *header-override.css* file (or another CSS file in that folder).

#. Add the code snippet shown below. 

.. code-block:: css

	.menu .sidepanel, header .menu .sidepanel li.hasChildren ul li{

                display: none !important;

	}

The links will disappear from the top menu. 

.. image:: images/merged.png
   :align: center


Sidepanels added as subpages
----------------------------

We recommend not to add sidepanel pages as subpages. If a sidepanel page is added as a subpage, an arrow is displayed to indicate the presence of subpages to your end users, but the arrow will not expand as expected.
 
.. image:: images/image003.jpg
   :align: center

Related Topics
------------------

* `AIMMS Documentation: Sidepanels <https://documentation.aimms.com/webui/page-manager.html#id6>`_




