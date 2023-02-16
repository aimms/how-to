Traveling Salesman
======================

.. meta::
   :description: This example illustrates some of AIMMS control flow statements by means of the traveling salesman 2-opt heuristic.
   :keywords: Algorithm, 2-opt heuristic, traveling salesman problem, GMP, Lazy constraint callback, subtour elimination constraints.

.. image:: https://img.shields.io/badge/AIMMS_4.88-ZIP:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_4.88-Github:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-webui-44/uptaded-contract-allocation-example-1253


Story
-----

The Traveling Salesman Problem (TSP) is the problem of finding the best route given a set of locations and distances between those locations.

It is widely researched for `several reasons <https://en.wikipedia.org/wiki/Travelling_salesman_problem>`_ including:

#.  They are notoriously hard to solve to proven optimality. 

#.  There is a wide range of practical applications.

#.  There are various ways of solving TSP problems.

The purpose of this example application is to illustrate some ways of solving a TSP. 


Mathematical Model
------------------

In this example you will find both heuristic and MIP model for comparison. 

Heuristic
^^^^^^^^^^^^

This example illustrates some of AIMMS control flow statements by means of the traveling salesman 2-opt heuristic. In the model tree, you will find some declarations to define the problem. 
In addition, you will find:

- A procedure and some declarations to compute and visualize an initial tour constructed by starting at some city and successively selecting the next city as the closest city not yet part of the tour.

- A procedure and some declarations to compute and visualize an improved tour constructed by repetitively swapping those two arcs in the tour by means of the 2-opt heuristic that give the largest overall distance improvement, until no further improvement is possible or the iteration limit is reached.

- A procedure and some declarations to compute and visualize an improved tour constructed by repetitively swapping the next arc in the (modified) tour with that neighbor arc which gives the largest distance improvement, until the iteration limit is reached or a full cycle over the tour gives no further improvement.


MIP
^^^^

This AIMMS project illustrates the use of a semi-continuous variable. A semi-continuous variable is either zero or within a certain range. 
This type of variables can be used in conditions like, whenever there is a transport this transport has a minimum size. 

In this example the (symmetric) Traveling Salesman Problem (TSP) is formulated using subtour elimination constraints. 
The amount of subtour elimination constraints is exponential, and therefore they are added using a lazy constraint callback. 
Lazy constraints are constraints that should be satisfied by any solution to the problem, but they are not generated upfront. 
The lazy constraint callback checks whether the incumbent solution found by the solver contains subtours. 
If yes, then subtour elimination constraints are added that forbid these subtours. If not, then the incumbent solution forms a true solution of the TSP problem, as it contains only one tour.

+-----+------------------------------------------------------+-------------------------------------------+
|       Contract Allocation Problem                                                                      |
+=====+======================================================+===========================================+
+ **Sets and indices:**                                                                                  |
+-----+------------------------------------------------------+-------------------------------------------+
+     | :math:`S`, :math:`i,j \in S`                         | Cities                                    |
+-----+------------------------------------------------------+-------------------------------------------+
| **Parameters:**                                                                                        |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`C_{i,j} \in \mathbb{R_{+}}`                   | Distance between cities                   |
+-----+------------------------------------------------------+-------------------------------------------+
| **Variables:**                                                                                         |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`X_{i,j} \in \{0..1\} \forall i,j: i>j`        | Travel from i to j, or visa versa         |
+-----+------------------------------------------------------+-------------------------------------------+
| **Constraints:**                                                                                       |
+-----+------------------------------------------------------+-------------------------------------------+
|  1  | :math:`\forall j: \sum_i X_{i,j} + X_{i,j} = 1`      |                                           |
+-----+------------------------------------------------------+-------------------------------------------+
| **Minimize:**                                                                                          |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`\sum_{i,j} C_{i,j} * X_{i,j}`                 | Total distance traveled                   |
+-----+------------------------------------------------------+-------------------------------------------+

Language 
--------

Rest API
^^^^^^^^^^^^^^^^^^^^


Case management.
^^^^^^^^^^^^^^^^^^^^

* Two cases supplied

* You can create your own case, or adapt an existing case.


Haversine
^^^^^^^^^^

Ref haversine formula.

#.  `Haversine code <https://rosettacode.org/wiki/Haversine_formula>`_

#.  :doc:`previous article<../153/153-external-haversine>`


ScheduleAt
^^^^^^^^^^

Note precise up to 1 second.

AIMMS Procedure :aimms:procedure:`ScheduleAt`

Stopwatch Library
^^^^^^^^^^^^^^^^^^

To compare the execution time for each solve, 

WebUI Features
--------------

**Please ref article 572.**

This project you will find many 'hidden' and interesting features, for example, by right clicking on any node, you will be able to delete it specificly. 
The status bar here is used to let the user know when the iteration run is in progress. The "Help" side panels document some of those features.   

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Combination Chart Widget <https://documentation.aimms.com/webui/combination-chart-widget.html>`_

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Side Panel <https://documentation.aimms.com/webui/side-panels-grd-pages.html#side-panel-grid-pages>`_

- `Scalar (and Compact) Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Dialog Page <https://documentation.aimms.com/webui/dialog-pages.html>`_ 


UI Styling
----------
Below there are the css files you will find with comments on what they change. 

.. tab-set::
    .. tab-item:: annotations.css

      .. code-block:: css
         :linenos:

         .annotation-node-done{
            fill: var(--secondary);
         }
         .annotation-node-running{
            fill: var(--secondary2);
         }
    
    .. tab-item:: body.css

      .. code-block:: text
         :linenos:

         /*Add logo on the background*/
         .scroll-wrapper--pagev2 .page-container {
            content: " ";
            background: url(img/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain;
         }

         /*Changing tittle to be uppercase*/
         .title-addon {
            text-transform: uppercase;
            text-shadow: 2px 2px 0px var(--primaryDark);
            color: whitesmoke;
         }
         
         /*Changing button font*/
         .ui-widget, .ui-widget button, .ui-widget input, .ui-widget select, .ui-widget textarea {
            font-family: var(--font_headers),Montserrat,Roboto,Arial,Helvetica,sans-serif; 
         }

         /*Changing button size*/
         .aimms-widget[data-widget\.uri="btn_addThisCity"]  .ui-button,
         .aimms-widget[data-widget\.uri="btn_addByCountry"]  .ui-button,
         .aimms-widget[data-widget\.uri="btn_addByLimity"]  .ui-button  {
            width: 40px;
         }


    .. tab-item:: colors.css

      .. code-block:: css
         :linenos:

         :root {
            --primaryLight: #00A0C8;
            --primary: #0082AA;
            --primaryDark: #0A5078;
            --secondaryDarker: #A00028;
            --secondary: #C80A50;
            --secondary2: #DC9600;

            --bg_app-logo: 15px 50% / 50px 50px no-repeat url(/app-resources/resources/images/traveling.png);
            --spacing_app-logo_width: 65px;

            --color_border-divider_themed: var(--primary);
            --color_text_edit-select-link: var(--primaryDark);
            --color_text_edit-select-link_hover: var(--primaryLight);
            --color_bg_edit-select-link_inverted: var(--secondary);

            --color_bg_button_primary: var(--primaryLight);
            --color_text_button_primary: white;
            --border_button_primary: 1px solid var(--primaryLight);

            --color_bg_button_primary_hover: var(--primaryLight);
            --color_text_button_primary_hover: var(--primaryDark);
            --border_button_primary_hover: 1px solid var(--primaryDark);

            --color_text_button_secondary: var(--secondary);
            --border_button_secondary: 1px solid var(--secondary);
            --color_text_button_secondary_hover: var(--primaryDark);
            --border_button_secondary_hover: 1px solid var(--primaryDark);

            --color_bg_widget-header: var(--primaryDark);
            --border_widget-header: 3px solid var(--primary);
         }


    .. tab-item:: sidePanel.css

      .. code-block:: css
         :linenos:

         /*Changing label color*/
         .tag-label>.label {
            background: var(--primary);
         }

         .sidepanel-container .sidepanel-tab.active {
            background-color: var(--primaryDark);
         }

         .sidepanel-container .sidepanel-tab .sidepanel-icon, 
         .sidepanel-container .sidepanel-tab:hover {
            color: var(--primaryDark);
         }

         .sidepanel-container .sidepanel-tab.active{
            color: var(--color_bg_widget-canvas,#fff);
         } 

         .sidepanel-container .sidepanel-tab {
            height: 150px;
         }


    .. tab-item:: textColor.css

      .. code-block:: css
         :linenos:

         /*Link color*/
         .ql-snow a {  
            color: var(--primaryDark);
         }

         /*Change checkbox color*/
         input.boolean-cell-editor-contents {
            accent-color: var(--primaryDark);
         }

         .aimms-widget .ui-button {
            text-transform: uppercase;
         }

         /*Changing tittle to be uppercase*/
         .title-addon,
         .tag-label>.label,
         .ui-dialog .ui-dialog-title,
         .page-container__dialog-header .title,
         .sidepanel-header .side-panel__header-text{
            text-transform: uppercase;
            text-shadow: 1px 1px 0px var(--primaryDark);
            color: whitesmoke;
         }

         .tag-table .grid-viewport .cell:not(.flag-readOnly), html:not(.using-touch) .tag-table .grid-viewport .cell:not(.flag-readOnly) {
            color: var(--primaryDark);
         }

         .widget-menu-container .widget-menu-items-wrapper .widget-menu-item .title {
            color: var(--primaryDark);
         }


    .. tab-item:: widgetAction.css

      .. code-block:: css
         :linenos:

         .widgetdiv .awf-dock-button .chrome-button.active, .widgetdiv .awf-dock-button .chrome-button.open {
            background-color: var(--primaryDark);
            color: #fff;
         }

         .widget-menu-container .widget-menu-items-wrapper .widget-menu-item:hover {
            background: var(--primaryDark);
         }


    .. tab-item:: workflow.css

      .. code-block:: css
         :linenos:

         /*Change color of the active step*/
         .workflow-panel .step-item.current,
         .workflow-panel.collapse .step-item.current {
            box-shadow: inset 0.3125rem 0 0 var(--primary);
         }

         /*Change color of the titles*/
         .workflow-panel .step-item.active.complete .title, 
         .workflow-panel .step-item.active.incomplete .title {
            color: var(--primaryDark);
         }

         /*Change color of the icons*/
         .workflow-panel .step-item.active.complete .icon, 
         .workflow-panel .step-item.active.incomplete .icon {
            color: var(--primaryDark);
            border: 1px solid var(--primaryDark);
         }


Minimal Requirements
--------------------   

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient to run the Heuristics, call the Rest API and check the available scenarios. 
However, to run the MIP problem, you will need to buy a Developer License. 

You will also need an API key from `PositionStack api <https://positionstack.com>`_. To receive an free API key to test, please sign up to the free plan `here <https://positionstack.com/signup>`_. 


References
-----------

#.  Generalization of TSP to `Vehicle Routing Problem <https://how-to.aimms.com/C_Developer/Sub_Mathematical_Modeling/Sub_VRP/index.html>`_

#.  `Solve with Lazy Constraints <https://how-to.aimms.com/Articles/126/126-TSP-Lazy-Constraints.html#solve-with-lazy-constraints>`_ - Marcel Hunting. 

#. Applegate, D.L., R. E. Bixby, V. Chvátal, and W. J. Cook, The Traveling Salesman Problem: A Computational Study, Princeton University Press, Princeton, 2007

.. seealso:: Here you will find several euclidean TSP instances from TSPLIB at: http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/

.. spelling:word-list::

   primaryDark
   haversine
   ddab
   bg


