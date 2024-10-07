Geolocation AbstractAPI
==============================

.. meta::
   :keywords: aimms, api, rest api, library, ip, openapi, abstractapi, geolocation
   :description: Discover accurate IP geolocation with AbstractAPI: pinpoint any IP address worldwide!

.. image:: https://img.shields.io/badge/AIMMS_24.5-ZIP:_Abstract_API-blue
   :target: https://github.com/aimms/ip-twist/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.5-Github:_Abstract_API-blue
   :target: https://github.com/aimms/ip-twist

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/math-or-optimization-modeling-39/using-an-api-with-openapi-specification-1368

.. image:: images/project-1920-high.gif
    :align: center

|

Story
----------


In this functional example, you will see a IP Locator. The site `geolocation.abstractapi <https://app.abstractapi.com/api/ip-geolocation/tester/>`_ provides a service for WhoIs GeoLocating; translating an IP Address to a location on the globe. 
For instance, the IP Address ``111.111.111.111`` will return Latitude 33.169, Longitude 129.72; which is in Tokyo, Japan. Add your IP address and confirm where you are!

Language
-----------

Operating the Application
~~~~~~~~~~~~~~~~~~~~~~~~~~

To operate this example, you first need to obtain an API key, from `Getting API key abstract api geolocation <https://app.abstractapi.com/api/ip-geolocation/tester>`_

.. image:: images/obtain-api-key.png
    :align: center

| 

Then you can start the AIMMS Project, press the GeoLocate button on the lower right of the page "Find IP". 

.. image:: images/geolocateButton.png
    :align: center

| 

You will be asked for an API key first. Then the IP address provided will be GeoLocated.

.. image:: images/tokyo.png
    :align: center

|

Using the API
~~~~~~~~~~~~~

While using REST APIs, you will need to **Prepare**, **Call the API** and **Handle the Response**. All three steps are detailed `here <https://how-to.aimms.com/Articles/562/562-geolocation-abstractapi.html>`_.  

WebUI Features
---------------

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Map Widget <https://documentation.aimms.com/webui/map-widget.html#map-widget>`_ 


UI Styling
---------------

Below described all UI modifications done on this example trough ``css`` files which can be found beneath ``MainProject/WebUI/resourses/stylesheets``. 

.. tab-set::
    .. tab-item:: custom.css

        .. code-block:: css
            :linenos:

            /*Changing tittle to be uppercase*/
            .title-addon,
            .ui-dialog .ui-dialog-title {
                text-transform: uppercase;
                text-shadow: 2px 2px 0px var(--primary);
            }
    

    .. tab-item:: theming.css

        .. code-block:: css
            :linenos:

            :root {
                --secondaryLight: #7DEBF5;
                --secondary: #3DD9EB;
                --secondaryDark: #00B3D7;
                --primaryLight: #F55376;
                --primary: #EB0000;
                --primaryLightest: #FA91AD;
                
                --bg_app-logo: 15px 50% / 30px 30px no-repeat url(/app-resources/resources/images/geoapi.png);
                --spacing_app-logo_width: 45px;
                --color_border_app-header-divider: var(--secondaryDark); /*line color after header*/
                --color_bg_app-canvas: url(/app-resources/resources/images/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain; /*background color*/

                --color_border-divider_themed: var(--primaryLight);
                --color_text_edit-select-link: var(--primaryLight);
                --color_text_edit-select-link_hover: var(--primary);
                --color_bg_edit-select-link_inverted: var(--secondary);
                --color_bg_button_primary: var(--primary);
                --color_bg_button_primary_hover: var(--primaryLightest);
                --color_text_button_secondary: var(--secondary);
                --border_button_secondary: 1px solid var(--secondary);
                --color_text_button_secondary_hover: var(--primary);
                --border_button_secondary_hover: 1px solid var(--primary);
                --color_bg_widget-header: var(--primary);
                --border_widget-header: 3px solid var(--primaryLightest);

                --color_text_widget-header:   whitesmoke;;

                /*---------------------------------------------------------------------
                        WORKFLOW
                ----------------------------------------------------------------------*/
                /* Header text*/
                --color_workflow-header: #505767;
                    
                /* Step background and content (text, icon) colors for the 4 states*/
                /*current + current with error*/
                --color_bg_workflow_current: var(--secondaryDark);
                --color_workflow_current: var(--color_text_inverted);
                --color_bg_workflow_error-current: #d1454b;

                /*active*/
                --color_bg_workflow_active: #e6edff;
                --color_workflow_active: var(--secondaryDark);
                
                /*inactive*/
                --color_bg_workflow_inactive: #dde0e8;
                --color_workflow_inactive: #b0b5c2;
                
                /*error*/
                --color_bg_workflow_error: #f9e9e9;
                --color_workflow_error: #d1454b;
                
                /* Child indentation, border colors */
                --spacing_workflow-child-indent: 1rem;
                --color_workflow-item-divider: var(--secondaryDark);
                
                /* Icon background, border, for non-error state */
                --color_bg_workflow-icon: #ffffff;
                --color_workflow-icon-border: var(--secondaryDark);

            }

    .. tab-item:: annotations.css

        .. code-block:: css
            :linenos:                        

            .annotation-blue{
            /*fill changes*/
                fill: var(--secondaryDark);
                fill-opacity: .6;
            }

Minimal Requirements
----------------------

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example. 
However, you will need API key, to receive an free API key to test, please access `abstractapi <https://app.abstractapi.com/api/ip-geolocation/tester>`_ and sign up. 

Release Notes
--------------------

`v1.0 <https://github.com/aimms/abstract-api/releases/tag/1.0>`_ (24/09/2024)
	First logged version of this application. 

.. seealso::
    * :doc:`../581/581-static-lib-from-runtime-lib`
    * :doc:`../545/545-summary-examples-features`

.. spelling:word-list::

   