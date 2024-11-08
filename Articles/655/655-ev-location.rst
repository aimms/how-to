EV Charging Location
=========================

.. meta::
   :keywords: EV charging station optimization, Particle Swarm Optimization, urban EV infrastructure, electric vehicle charging, AIMMS, WebUI, sustainable transportation, cost-effective charging, urban planning, EV infrastructure model
   :description: Optimize electric vehicle (EV) charging station placement and sizing with Particle Swarm Optimization to enhance accessibility, minimize costs, and support sustainable urban infrastructure.

.. image:: https://img.shields.io/badge/AIMMS_24.5-ZIP:_EV_Charging_Locations-blue
   :target: https://github.com/aimms/ev-charging-locations/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.5-Github:_EV_Charging_Locations-blue
   :target: https://github.com/aimms/ev-charging-locations

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/

|
   
Story
-----
This example tackles the growing challenge of optimally placing and sizing electric vehicle (EV) 
charging stations in urban environments. With EV adoption rising, particularly in the United States, 
the need for accessible and cost-effective charging infrastructure is critical. Many EV owners rely on home charging, but the availability of well-positioned public charging stations supports market growth and alleviates "range anxiety," where drivers worry about running out of power before reaching a charger.

The goal is to maximize accessibility and minimize infrastructure costs, taking into account location density, 
demand patterns, and geographical constraints. To address this, the example uses the Vulture algorithm, a Particle Swarm Optimization (PSO) 
method adept at solving non-linear, non-convex problems in continuous spaces. By effectively planning charging station deployment, 
urban planners can enhance EV infrastructure, helping cities advance toward sustainability goals and facilitating the broader shift to cleaner transportation.

Mathematical Model
------------------
In the context of electric vehicle (EV) charging station optimization, the algorithm employs Particle Swarm Optimization (PSO) 
to explore potential locations and sizes for charging stations. Each "particle" in the swarm represents a possible configuration, 
and through iterative adjustments based on individual and collective experiences, the algorithm converges towards optimal solutions. 
The approach effectively navigates complex, non-linear search spaces to enhance accessibility and minimize costs in EV infrastructure planning. 

**Objective**
Each charging station has a construction cost and maintenance cost. EVs incur a driving cost when traveling to and from a station, 
as well as a charging cost for each unit of charge that is consumed. Penalty costs are added for EVs that fall out-of-range from their nearest charger. 
The objective is to position and size the number of charging stations within the given continuous search space at the lowest cost.

**Constraints**
Several constraints must be applied to ensure that a practical solution can be found. There is an upper limit s_max on the number of stations that may 
be constructed. Each station may contain a maximum of eight chargers. No more than one vehicle may wait for a charger at any given time and vehicles 
may not exceed their range to reach a station. The demand for chargers is governed by the probability of an EV visiting a station. The demand for chargers 
in a region can be estimated by taking the expected value of the probability of visiting a station multiplied by the number of vehicles in that region.

+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|                                                       EV Charging Location Model                                                                     |
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
|     | :math:`D_{v,r} \in \{ 0, 1 \}`                              | Route :math:`r` used by vessel :math:`v`: ``p_def_domainAllocateVesselToRoute``  |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`CR_{c,r} \in \{ 0, 1 \}`                             | Cargo :math:`c` on route :math:`r`: ``p_def_cargoesOnRoute``                     |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`IC_{v} \in \mathbb{R_{+}}`                           | Idle cost for vessel :math:`v`: ``p_def_idleCostVesselNotUsed``                  |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`SC_{c} \in \mathbb{R_{+}}`                           | Cost cargo :math:`c` handled on spot market: ``p_spotCostVessel``                |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`T_{r} \in \mathbb{R_{+}}`                            | Cost executing route :math:`r`: ``p_def_operationalCostPerRoute``                |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
| **Variables:**                                                                                                                                       |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`a_{(v,r)|D_{v,r}} \in \{ 0, 1 \}`                    | allocate vessel :math:`v` to route :math:`r`: ``v_allocateVesselToRoute``        |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`s_{c} \in \{0..1\}`                                  | cargo :math:`c` is left to the spot market: ``bv_cargoOnCharteredVessel``        |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------+
|     | :math:`i_{v} \in \{0..1\}`                                  | vessel :math:`v` remains idle: ``v_idleVessel``                                  |
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


WebUI Features
---------------
The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Map Widget <https://documentation.aimms.com/webui/map-widget.html>`_

- `Combination Chart Widget <https://documentation.aimms.com/webui/combination-chart-widget.html>`_

- `CSS Annotations <https://documentation.aimms.com/webui/css-styling.html#data-dependent-styling>`_

- `Label Widget <https://documentation.aimms.com/webui/label-widget.html>`_ 

UI Styling
------------

Below there are the css files you will find with comments on what they change. 

.. tab-set::
   .. tab-item:: theming.css

      .. code-block:: css
         :linenos:

         :root {
            /*---------------------------------------------------------------------
                  COLORS
            ----------------------------------------------------------------------*/
            --primary: #3DDAB4;
            --primaryDark: #00B569;
            --primary90Transparent: #3ddab33b;


            --bg_app-logo: 15px 50% / 30px 30px no-repeat url(/app-resources/resources/images/budgeting.png); /*app logo*/
            --spacing_app-logo_width: 45px;
            --color_border_app-header-divider: var(--primaryDark); /*line color after header*/
            --color_bg_app-canvas: url(/app-resources/resources/images/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain; /*background color*/
            --border_widget-header: 1px solid var(--primaryDark); /*line color after widget header*/

            --color_bg_button_primary: var(--primaryDark);
            --color_bg_button_primary_hover: var(--primary);
            --color_text_edit-select-link: var(--primaryDark);
            --color_text_edit-select-link_hover:  var(--primary);

            /*---------------------------------------------------------------------
                  WORKFLOW
            ----------------------------------------------------------------------*/
            /* Header text*/
            --color_workflow-header: #505767;
               
            /* Step background and content (text, icon) colors for the 4 states*/
            /*current + current with error*/
            --color_bg_workflow_current: var(--primaryDark);
            --color_workflow_current: var(--color_text_inverted);
            --color_bg_workflow_error-current: #d1454b;

            /*active*/
            --color_bg_workflow_active: #e6edff;
            --color_workflow_active: var(--primaryDark);
            
            /*inactive*/
            --color_bg_workflow_inactive: #dde0e8;
            --color_workflow_inactive: #b0b5c2;
            
            /*error*/
            --color_bg_workflow_error: #f9e9e9;
            --color_workflow_error: #d1454b;
            
            /* Child indentation, border colors */
            --spacing_workflow-child-indent: 1rem;
            --color_workflow-item-divider: var(--primaryDark);
            
            /* Icon background, border, for non-error state */
            --color_bg_workflow-icon: #ffffff;
            --color_workflow-icon-border: var(--primaryDark);
         }

   .. tab-item:: annotation.css

      .. code-block:: css
         :linenos:

         .annotation-bkg-cell {
            background: var(--primary90Transparent);
         }

         .annotation-bkg-cell-default {
            background: var(--primary90Transparent);
         }

         .annotation-bkg-cell-default input{
            color: transparent;
         }

         .annotation-reach-maximum {
            background: rgba(255, 0, 0, 0.438);
         }

         .annotation-reach-minimum {
            background: rgba(255, 255, 0, 0.438);
         }

         .annotation-between {
            background: rgba(0, 128, 0, 0.438);
         }

   .. tab-item:: custom.css

      .. code-block:: none
         :linenos:

         /*Change table default text color*/
         .tag-table .grid-viewport .cell.flag-default, 
         html:not(.using-touch) .tag-table .grid-viewport .cell.flag-default {
            color: white;
         }

         /*Centering cells*/
         .tag-table .cell.flag-string .cell-wrapper, 
         .tag-table .cell.flag-number input,
         .tag-table .cell.flag-string input{
            text-align: center;
         }

Minimal Requirements
----------------------

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example. 

References
-----------



Release Notes
--------------------

`v1.0 <https://github.com/aimms/vessel-scheduling/releases/tag/1.0>`_ (15/08/2024)
	First version of this application. 

.. spelling:word-list::

