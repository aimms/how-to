Vessel Scheduling
=========================

.. meta::
   :keywords: Decision Support System, Route Optimization, Petrobras Ship Scheduling, Cost-efficient Cargo Delivery, Mathematical Optimization Model, Vessel Allocation Algorithm, WebUI and Python Integration
   :description: In this practical example, an efficient plan is developed for delivering large cargoes using oil tankers.

.. image:: https://img.shields.io/badge/AIMMS_24.5-ZIP:_Vessel_Scheduling-blue
   :target: https://github.com/aimms/vessel-scheduling/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.5-Github:_Vessel_Scheduling-blue
   :target: https://github.com/aimms/vessel-scheduling

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/

|
   
Story
-----

In this practical example, an efficient plan is developed for delivering large cargoes using oil tankers.

The model assumes each ship can carry only one cargo at a time, and once the time horizon begins, 
all vessels head directly to the loading port. 
Upon loading, each vessel proceeds directly to the delivery location within its designated time window.

Constraints include ensuring each cargo is loaded inside the determined time window, each cargo being transported by only one vessel, and each charter vessels being assigned to only one route at a time.

The objective is to minimize costs associated to combinations of cargoes and routes.

**Reference:** Gustavo Diz, Luiz Felipe Scavarda, Roger Rocha, Silvio Hamacher (2014) Decision Support System for 
PETROBRAS Ship Scheduling. Interfaces 44(6):555-566.


Mathematical Model
------------------

To appreciate the complexity of the below mathematical formulation, it is important to note that the number of routes grows
combinatorially with the number of cargos. For instance, with 7 vessels and 20 cargos, the number of routes can
exceed half a million.

+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|                                                       Vessel Scheduling Model                                                                        |
+=====+=============================================================+==================================================================================+
| **Sets and indices:**                                                                                                                                |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`v`, :math:`v \in Vessels`                            | Vessels                                                                          |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`c`, :math:`c \in Cargos`                             | Cargos                                                                           |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`r`, :math:`r \in Routes`                             | Routes                                                                           |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
| **Parameters:**                                                                                                                                      |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`D_{v,r} \in \{ 0, 1 \}`                              | Route :math:`r` used by vessel :math:`v`: ``p_def_domainAllocateVesselToRoute``   |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`CR_{c,r} \in \{ 0, 1 \}`                             | Cargo :math:`c` on route :math:`r`: ``p_def_cargoesOnRoute``                       |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`IC_{v} \in \mathbb{R_{+}}`                           | Idle cost for vessel :math:`v`: ``p_def_idleCostVesselNotUsed``                   |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`SC_{c} \in \mathbb{R_{+}}`                           | Cost cargo :math:`c` handled on spot market: ``p_spotCostVessel``                 |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`T_{r} \in \mathbb{R_{+}}`                            | Cost executing route :math:`r`: ``p_def_operationalCostPerRoute``                |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
| **Variables:**                                                                                                                                       |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`a_{(v,r)|D_{v,r}} \in \{ 0, 1 \}`                    | allocate vessel :math:`v` to route :math:`r`: ``v_allocateVesselToRoute``         |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`s_{c} \in \{0..1\}`                                  | cargo :math:`c` is left to the spot market: ``bv_cargoOnCharteredVessel``         |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`i_{v} \in \{0..1\}`                                  | vessel :math:`v` remains idle: ``v_idleVessel``                                   |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
| **Constraints:**                                                                                                                                     |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|  1  | :math:`\forall c: \sum_r a_{v,r} * CR_{c,r} + s_{c} = 1`    | Cargo on a single vessel, or left to spot market                                 |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|  2  | :math:`\forall v: \sum_r a_{v,r} + i_{v} = 1`               | Each vessel can take only one route, or is idle                                  |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
| **Minimize:**                                                                                                                                        |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`\sum_{v,r} T_{r} * a_{v,r} +`                        | Operational cost                                                                 |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`\sum_{v} IC_{v} * i_{v} +`                           | Unused vessel cost                                                               |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`\sum_{c} SC_{p,c} * S_{c}`                           | Total cost of cargos left to the spot market                                     |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+


Language 
--------

Route Generation
^^^^^^^^^^^^^^^^

Before optimization, all possible routes are generated after data import, taking into account each cargo's loading window to ensure timely delivery.

During the mathematical optimization process, each cargo is then assigned to either a time-chartered or voyage-chartered vessel within the model.

In this example, the vessel scheduling problem is solved by first generating the routes, followed by the mathematical optimization. 
The majority of the time is spent on route generation.

Example
^^^^^^^^^

A single vessel, ``vessel1``, located at Caracas, is to handle two cargos, labeled: ``a1`` and ``a2``.

	#.	``a1`` load in Paramaribo, deliver in Sao Paolo
	
	#.  ``a2`` load in Montevideo, deliver in Rio de Janeiro.

Then there are five potential routes:

	#.	``vessel1_a1_a2``: with actions:

		#.	Sail to Caracas to Paramaribo
		
		#.  Load Cargo ``a1``
		
		#.  Sail to Sao Paolo
		
		#.  Deliver Cargo ``a1``
		
		#.  Sail to Montevideo
		
		#.  Load Cargo ``a2``
		
		#.  Sail to Rio de Janeiro
		
		#.  Deliver Cargo ``a2``

	#. 	``vessel1_a2_a1``: similar as ``vessel1_a1_a2``, just a different order of locations; 
		and thus also different vessel sailing times and cargo pickup moments.
	
	#.  ``vessel1_a1``: ``vessel1`` only handles cargo ``a1``

	#.  ``vessel1_a2``: ``vessel1`` only handles cargo ``a2``

	#.  ``vessel1`` Remains at port Caracas


The route generation procedure is as follows:

#.  For each vessel i, the idle route is generated: ``vessel<i>``.
    Together they initialize the set of just generated routes, ``JG``.
	
#.  Move the set of just generated routes ``JG``, to the set of input routes ``IR``.

#.  For each ``r`` in ``IR``, all cargos ``c`` are considered to be appended for a new route ``r'``.
    A route ``r' = r_c`` is accepted if: 
	
	* ``c`` is not a part of ``r``,
   
    * ``c`` is picked up in its time window, and
	
	* ``c`` is delivered before the end of the horizon.
	
	All routes ``r'`` just generated, form the new set of just generated routes ``JG``.
	If the set ``JG`` is empty, stop, otherwise continue with step 2.

Because a route ``r'`` ends later than route ``r``, this procedure is finite.

In order to determine the cost of a route, careful administration of each leg needs to be done 
(sailing to the loading location, perhaps waiting, sailing to the delivery location).

Python Service
^^^^^^^^^^^^^^

This section is largely based on the how-to articles in `Develop an AIMMS Service <https://how-to.aimms.com/C_Developer/Sub_Connectivity/sub_dataexchange/Sub_Develop_Service/index.html>`_.
Selected differences will be pointed out:

**Produce Service:** The service is named ``solveVesselScheduling``, accepts an Excel workbook as input, and provides as response also an Excel workbook.

**Consume Service:** Only a Python client is provided; and the requests call in that python app uses a ``files`` argument instead of a ``data`` argument.

WebUI Features
---------------

On input page, if you click around the graphs, a highlighted cell will appear identifying the last clicked element. 
The results are displayed in a combination chart (stacked bar chart).

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
------------

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

      .. code-block:: none
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
----------------------

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example. 
To run the Python client, you will need to have Python installed, for this example we used Python 3.11. 


Release Notes
--------------------

`v1.1 <https://github.com/aimms/vessel-scheduling/releases/tag/1.1>`_ (19/09/2024)
	Performance of the route generation procedure was updated. Now you are able to solve using a Python call.

`v1.0 <https://github.com/aimms/vessel-scheduling/releases/tag/1.0>`_ (15/08/2024)
	First version of this application. 

.. spelling:word-list::

   primaryDark
   ddab
   bg
   cargos
   coords
   haversine
   combinatorially