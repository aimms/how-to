Vessel Scheduling
=========================
.. meta::
   :keywords: Semi-continuous variables, Mixed Integer Programming model, MIP, combinationchart, table, colors, css
   :description: This AIMMS project illustrates the use of a semi-continuous variable.

.. image:: https://img.shields.io/badge/AIMMS_24.5-ZIP:_Vessel_Scheduling-blue
   :target: https://github.com/aimms/vessel-scheduling/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.5-Github:_Vessel_Scheduling-blue
   :target: https://github.com/aimms/vessel-scheduling

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/

|
   
Story
-----

In this practical example, an efficient plan is developed for delivering oil tankers already coupled with cargoes. 
Prior to optimization, all possible routes are generated following data import. 
Route creation considers each cargo's loading window to ensure timely delivery. 
Subsequently, each cargo is assigned to either a time-chartered or voyage-chartered vessel within the model. 
The objective is to minimize costs for combinations of cargoes and routes.

The model operates under several assumptions and constraints. 
It assumes each ship can carry only one cargo at a time and that all vessels head directly to the loading port once the time horizon begins. 
Constraints include: each cargo being transported by only one vessel, and charter vessels being assigned to only one route at a time.

**Reference:** Gustavo Diz, Luiz Felipe Scavarda, Roger Rocha, Silvio Hamacher (2014) Decision Support System for PETROBRAS Ship Scheduling. Interfaces 44(6):555-566.

Mathematical Model
------------------

This AIMMS project illustrates the use of a semi-continuous variable. A semi-continuous variable is either zero or within a certain range. This type of variables can be used in conditions like, whenever there is a transport this transport has a minimum size. 

+-----+------------------------------------------------------+-------------------------------------------+
|       Contract Allocation Problem                                                                      |
+=====+======================================================+===========================================+
+ **Sets and indices:**                                                                                  |
+-----+------------------------------------------------------+-------------------------------------------+
+     | :math:`P`, :math:`p \in P`                           | Producers                                 |
+-----+------------------------------------------------------+-------------------------------------------+
+     | :math:`C`, :math:`c \in C`                           | Contracts                                 |
+-----+------------------------------------------------------+-------------------------------------------+
| **Parameters:**                                                                                        |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`M_{p} \in \mathbb{R_{+}}`                     | minimal delivery                          |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`A_{p} \in \mathbb{R_{+}}`                     | available capacity                        |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`S_{c} \in \mathbb{R_{+}}`                     | contract size                             |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`N_{c} \in \mathbb{R_{+}}`                     | minimal number of contributors            |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`T_{p,c} \in \mathbb{R_{+}}`                   | delivery cost by p for c                  |
+-----+------------------------------------------------------+-------------------------------------------+
| **Variables:**                                                                                         |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`X_{p,c} \in \{0\} \cup \{M_{p}..10000\}`      | amount of commodity delivered by p to c   |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`Y_{p,c} \in \{0..1\}`                         | p produce to c                            |
+-----+------------------------------------------------------+-------------------------------------------+
| **Constraints:**                                                                                       |
+-----+------------------------------------------------------+-------------------------------------------+
|  1  | :math:`\forall p: \sum_c X_{p,c} \leq A_{p}`         | production capacity for p                 |
+-----+------------------------------------------------------+-------------------------------------------+
|  2  | :math:`\forall c: \sum_p X_{p,c} \geq S_{c}`         | demand fulfillment for c                  |
+-----+------------------------------------------------------+-------------------------------------------+
|  3  | :math:`\forall c: \sum_p X_{p,c} \geq N_{c}`         | minimal number of contributors to c       |
+-----+------------------------------------------------------+-------------------------------------------+
|  4  | :math:`\forall p, c: X_{p,c} \geq M_{p} * Y_{p,c}`   | if p delivers to c                        |
+-----+------------------------------------------------------+-------------------------------------------+
| **Minimize:**                                                                                          |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`\sum_{p,c} T_{p,c} * X_{p,c}`                 | The number of matches                     |
+-----+------------------------------------------------------+-------------------------------------------+

Language 
--------

Talk about butons on homepage.

WebUI Features
--------------

On input page, if you click around the graphs, a highlighted cell will appear identifying the last clicked element. The results are displayed in a combination chart (stacked bar chart).

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Scalar (and Compact) Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Item Actions <https://documentation.aimms.com/webui/widget-options.html#item-actions>`_

- `Widget Actions <https://documentation.aimms.com/webui/widget-options.html#widget-actions>`_

- `Side Panel <https://documentation.aimms.com/webui/side-panels-grd-pages.html#side-panel-grid-pages>`_

- `Slider Widget <https://documentation.aimms.com/webui/slider-widget.html>`_

- `Button Widget <https://documentation.aimms.com/webui/button-widget.html>`_ 

- `Status Bar <https://documentation.aimms.com/webui/status-bar.html>`_

- `Map Widget <https://documentation.aimms.com/webui/map-widget.html>`_

- `Combination Chart Widget <https://documentation.aimms.com/webui/combination-chart-widget.html>`_

- `Tabbed Widget <https://documentation.aimms.com/webui/tabbed-widget.html>`_

- `Multiselect Widget <https://documentation.aimms.com/webui/selection-widgets.html>`_ 

- `Gantt Chart Widget <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_

- `CSS Annotations <https://documentation.aimms.com/webui/css-styling.html#data-dependent-styling>`_


UI Styling
----------
Below there are the css files you will find with comments on what they change. 

.. tab-set::
   .. tab-item:: theming.css

      .. code-block:: css
         :linenos:

         :root {
            --primaryDark: #DA2063;
            --primaryDarker: #FF4940;
            --secondary90Transparent: #ff4a4023;
            --secondary: #2E324F;

            --bg_app-logo: 15px 50% / 40px 40px no-repeat url(/app-resources/resources/images/schedule.png); /*app logo*/
            --spacing_app-logo_width: 60px;
            --color_border_app-header-divider: var(--secondary); /*line color after header*/

            --color_workflow-item-divider: var(--secondary90Transparent); /*workflow step divider color*/
            --color_bg_workflow_current: var(--primaryDark); /*bg color when step is selected*/
            --color_workflow_active: var(--primaryDark); /*font and icon color when step is active*/
            --color_workflow-icon-border: var(--primaryDark); /*round border of the step*/
            --color_bg_workflow_active: #ff4a400e;;

            --color_bg_app-canvas: url(/app-resources/resources/images/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain; /*background color*/
            --color_bg_widget-header: linear-gradient(90deg, rgba(255,73,64,0.75) 0%, rgba(218,32,99,0.75)  100%); /*widget header background color*/
            --border_widget-header: 2px solid var(--secondary); /*line color after widget header*/

            --color_text_widget-header: var(--secondary); 
            --color_text_edit-select-link: var(--primaryDark);

            --color_bg_button_primary: var(--primaryDark);
            --color_bg_button_primary_hover: var(--primaryDarker);
         }


   .. tab-item:: annotation.css

      .. code-block:: css
         :linenos:

         /*Hide checkbox contents of delete and edit annotations*/
         .annotation-edit-element input.boolean-cell-editor-contents,
         .annotation-delete-element input.boolean-cell-editor-contents{
            visibility: hidden;
            display: block;	
         }

         .annotation-edit-element {
            background: white url(img/pencil.png) no-repeat 50%/contain; 
            background-size: auto 70% ;
         }

         .annotation-delete-element {
            background: white url(img/minus.png) no-repeat 50%/contain; 
            background-size: auto 50% ;

         }

         .annotation-NotInUse,
         .annotation-DeliveringPort,
         .annotation-VisibleLocations{
            fill: #FE493F;
            background: #FE493F !important;
         }

         .annotation-InUse,
         .annotation-LoadingPort{
            fill: #9E3869;
            background: #9E3869 !important;
         }

         .annotation-not-fulfilled{
            background: #ffc21b2c;
         }

         .annotation-highlight-cell {
            background: var(--secondary90Transparent);
         }

   .. tab-item:: custom.css

      .. code-block:: css
         :linenos:

         /*Centering cells*/
         .tag-table .cell.flag-string .cell-wrapper,
         .tag-table .cell.flag-number input,
         .tag-table .cell.flag-string input{
            text-align: center;
         }

         .tag-slider .slider-value {
            color: var(--color_text_edit-select-link);
         }

         .widget-menu__item .title {
            color: var(--color_text_app-footer);
         }

         .ql-snow a {
            color: var(--color_text_edit-select-link) !important;
         }

         input.boolean-cell-editor-contents {
            accent-color: var(--primaryDark) /*boolean color*/
         }

         .react-contextmenu .react-contextmenu-item .display-text {
            color: inherit;
         }

         .aimms-widget[data-widget\.uri="scl_EditAddElements"] .awf-dock.top,
         .aimms-widget[data-widget\.uri="msl_selecRoutes"] .awf-dock.top,
         .aimms-widget[data-widget\.uri="MappingCargoesWithCollors_1"] .awf-dock.top,
         .aimms-widget[data-widget\.uri="Vessel loading_1"] .awf-dock.top{
            display: none;
         }

         .status-message:hover,
         .status-message.clickable:hover .status-display-text { 
            background-color: #ffcdcb2d;
            color: #505767;
         }


Minimal Requirements
--------------------   

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example.


Release Notes
--------------------   

`v1.0 <https://github.com/aimms/contract-allocation/releases/tag/1.0>`_ (15/08/2024)
	First version of this application. 

.. spelling:word-list::

   primaryDark
   ddab
   bg
   