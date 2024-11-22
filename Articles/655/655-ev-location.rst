EV Charging Location
====================

.. meta::
   :keywords: EV charging station optimization, Particle Swarm Optimization, urban EV infrastructure, electric vehicle charging, AIMMS, WebUI, sustainable transportation, cost-effective charging, urban planning, EV infrastructure model
   :description: Optimize electric vehicle (EV) charging station placement and sizing with Particle Swarm Optimization to enhance accessibility, minimize costs, and support sustainable urban infrastructure.

.. image:: https://img.shields.io/badge/AIMMS_24.5-ZIP:_EV_Charging_Locations-blue
   :target: https://github.com/aimms/ev-charging-locations/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.5-Github:_EV_Charging_Locations-blue
   :target: https://github.com/aimms/ev-charging-locations

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-support-updates-67/ev-charging-location-example-1793

.. image:: images/project-1920-high.gif
    :align: center

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

The problem was proposed as part of the `15th Annual AIMMS-MOPTA Optimization Modeling Competition <https://coral.ise.lehigh.edu/~mopta2023/competition>`_.

Mathematical Model
------------------
In the context of electric vehicle (EV) charging station optimization, the algorithm employs Particle Swarm Optimization (PSO) to explore potential 
locations and sizes for charging stations. Each "particle" in the swarm represents a possible configuration, and through iterative adjustments based 
on individual and collective experiences, the algorithm converges towards optimal solutions. The approach effectively navigates complex, non-linear 
search spaces to enhance accessibility and minimize costs in EV infrastructure planning. 

Objective
~~~~~~~~~~~~~~~~~~~~~~
Each charging station has a construction cost and maintenance cost. EVs incur a driving cost when traveling to and from a station, 
as well as a charging cost for each unit of charge that is consumed. Penalty costs are added for EVs that fall out-of-range from their nearest charger. 
The objective is to position and size the number of charging stations within the given continuous search space at the lowest cost.

Constraints
~~~~~~~~~~~~~~~~~~~~~~
Several constraints must be applied to ensure that a practical solution can be found. Each station may contain a maximum of eight chargers. 
No more than one vehicle may wait for a charger at any given time and vehicles may not exceed their range to reach a station. 
The demand for chargers is governed by the probability of an EV visiting a station. The demand for chargers in a region can be estimated by 
taking the expected value of the probability of visiting a station multiplied by the number of vehicles in that region.

+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|                                                       EV Charging Location Model                                                                                                         |
+=====+=============================================================+======================================================================================================================+
| **Sets and indices:**                                                                                                                                                                    |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`s`, :math:`s \in Stations`                           | Stations                                                                                                             |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`i`, :math:`i \in Individuals`                        | Individuals                                                                                                          |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`g`, :math:`g \in Generations`                        | Generations                                                                                                          |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`l`, :math:`l \in EV Locations`                       | EV Locations                                                                                                         |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
| **Important Problem Parameters:**                                                                                                                                                        |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`D_{l,i} \in \mathbb{R_{+}}`                          | Demand for Location :math:`l` for Indivdual :math:`i`: ``p_demand``                                                  |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`C_{m} \in \mathbb{R_{+}}`                            | Maintenance Cost :math:`C_{m}`: ``p_maintenanceCost``                                                                |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`C_{c} \in \mathbb{R_{+}}`                            | Construction Cost :math:`C_{c}`: ``p_constructionCost``                                                              |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`C_{d} \in \mathbb{R_{+}}`                            | Driving Cost per mile :math:`C_{d}`: ``p_drivingCostPerMile``                                                        |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`C_{h} \in \mathbb{R_{+}}`                            | Charging Cost per hour :math:`C_{h}`: ``p_chargingCostPerHour``                                                      |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`Q_{max} \in \mathbb{Z_{+}}`                          | Maximum chargers per station :math:`Q_{max}`: ``p_maxNumberChargersPerStation``                                      |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`R \in \mathbb{R_{+}}`                                | Mean vehicle range :math:`R`: ``p_meanVehicleRange``                                                                 |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
| **Important Model Parameters:**                                                                                                                                                          |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`\omega \in \mathbb{R_{+}}`                           | Inertia component :math:`\omega`: ``p_inertiaComponent``                                                             |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`c_{1} \in \mathbb{R_{+}}`                            | Cognitive component :math:`c_{1}`: ``p_cognitiveComponent``                                                          |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`c_{2} \in \mathbb{R_{+}}`                            | Social component :math:`c_{2}`: ``p_socialComponent``                                                                |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
| **Variables:**                                                                                                                                                                           |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`P_{i,s}^{x} \in \mathbb{R}`                          | :math:`x`-Position of station :math:`s` in individual :math:`i`: ``p_currentSolutionX``                              |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`P_{i,s}^{y} \in \mathbb{R}`                          | :math:`y`-Position of station :math:`s` in individual :math:`i`: ``p_currentSolutionY``                              |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`V_{i,s}^{x} \in \mathbb{R}`                          | :math:`x`-Velocity of station :math:`s` in individual :math:`i`: ``p_currentSolutionVelocityX``                      |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`V_{i,s}^{y} \in \mathbb{R}`                          | :math:`y`-Velocity of station :math:`s` in individual :math:`i`: ``p_currentSolutionVelocityY``                      |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`B_{i,s}^{x} \in \mathbb{R}`                          | :math:`x`-Position of the best local solution for station :math:`s` in individual :math:`i`: ``p_bestLocalSolutionX``|
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`B_{i,s}^{y} \in \mathbb{R}`                          | :math:`y`-Position of the best local solution for station :math:`s` in individual :math:`i`: ``p_bestLocalSolutionY``|
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`G_{s}^{x} \in \mathbb{R}`                            | :math:`x`-Position of the best global solution for station :math:`s`: ``p_bestGlobalSolutionX``                      |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+
|     | :math:`G_{s}^{y} \in \mathbb{R}`                            | :math:`y`-Position of the best global solution for station :math:`s`: ``p_bestGlobalSolutionY``                      |
+-----+-------------------------------------------------------------+----------------------------------------------------------------------------------------------------------------------+


Variation Operations
~~~~~~~~~~~~~~~~~~~~~~

Each particle (station) has two main attributes: position and velocity. The position corresponds to a potential solution to the optimization problem, and the velocity is a 
vector that determines the direction and magnitude of the particle's movement in the search space. Throughout the optimization process, particles adjust their 
velocities and positions based on their own experiences and those of their neighbors within the swarm. The update procedures are handled in ``pr_updateVariations``.

The velocity update guides particles towards the promising areas in the search space. The respective :math:`x,y` velocity of each particle is updated using the following formula:

:math:`V_{i,s}^{new} = \omega \cdot V_{i,s}^{old} + c_{1} \cdot (B_{i,s} - P_{i,s}^{old}) + c_{2} \cdot (G_{s} - P_{i,s}^{old})`

After calculating the new velocity, the particle updates its :math:`x,y` position in the search space to reflect this new velocity. 
The position update is performed using the following formula:

:math:`P_{i,s}^{new} = P_{i,s}^{old} + V_{i,s}^{new}`

Language
------------------

The EV station assignment algorithm is a critical component in optimizing the EV charging infrastructure. It ensures the allocation of EVs to the most suitable 
charging stations by evaluating proximity, demand, and station capacities. Below are the four main steps in this algorithm:

1. Calculate Distances ``pr_getDistances``:

   *  For each particle (potential station configuration), compute the distances between EV locations and individuals, considering a distance cutoff to filter out far locations.

The following three steps are all contained in the procedure ``pr_getClosest``:

2. Estimate Demand:

   *  Calculate the demand at each location using a function that factors in the number of EVs per location and their range, applying an exponential decay based on the deviation from a mean range value.

3. Initialize Allocation Count:

   *  Reset or initialize the counter that keeps track of station allocations.

4. Assign EVs to Stations:

   *  Iterate over all individuals and locations.
   *  Attempt to assign EVs to the nearest available station that has not exceeded its maximum charger capacity.
   *  Use a threshold velocity to determine if the station's movement is negligible, in which case the assignment remains the same with a certain probability.
   *  If the nearest station cannot accommodate the demand, search for the next closest station.
   *  Update the allocation count for the selected station.
   *  If a suitable station is found, break the loop and continue with the next location.
   *  Set the distance for the allocated station to zero to prevent reassignment in the same iteration (as it falls out of the search domain).
 
The EV station assignment algorithm dynamically assigns vehicles to stations. Once vehicles are assigned to stations, it is possible
to evaluate the objective function, as all costs and penalties can be estimated.

Bringing the PSO and assignment algorithms together, the EV charging location problem is solved by ``pr_runPSOAlgorithm``:
  
.. code-block:: aimms  
   :linenos:

      pr_resetPSO;

      !Initialize the problem
      pr_initializeProblem;

      p_is_first_generation := 1;

      for (i_gen in s_def_generations) do
         
         !Call the subroutine responsible for assignments 
         pr_KNNSubroutine; !This runs pr_getDistances, gets the ranges, and runs pr_getClosest
         
         !Evaluate the cost of the current solution
         pr_evaluateCost;
         
         !Update the variations for the next generation  
         pr_updateVariations;

         !Store the fitness for the current generation by taking the mean of the 
         !total objective cost for all individuals in the generation  
         p_generationalFitness(i_gen) := mean(i_indv, p_totalObjectiveCost(i_indv));
         
         !Update the global best fitness with the best global solution cost
         p_globalBestFitness(i_gen) := p_bestGlobalSolutionCost;

         p_is_first_generation := 0;

      endfor;

      pr_updateDistancesForOutput;

      ui::pr_calculateHistograms;
      ui::pr_calculateSolutionPoints;


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


Release Notes
--------------------

`v1.0 <https://github.com/aimms/ev-charging-locations/releases/tag/1.0>`_ (22/11/2024)
	First version of this application. 

.. spelling:word-list::

