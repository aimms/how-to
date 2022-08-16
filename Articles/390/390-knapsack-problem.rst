Knapsack Problem
==================
.. meta::
   :keywords: Knapsack, Knapsack, Integer Programming, Binary Integer Programming, Cover Inequalities, Network Object
   :description: This example introduces a knapsack problem.

.. image:: https://img.shields.io/badge/AIMMS_4.86-ZIP:_Employee_Scheduling-blue
   :target: https://github.com/aimms/employee-scheduling/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_4.86-Github:_Employee_Scheduling-blue
   :target: https://github.com/aimms/employee-scheduling

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-webui-44/updated-employee-scheduling-example-1291
   
Story
-----

This example introduces a knapsack problem. The example considers a data set of 16 items which can be included in the knapsack. The objective is to maximize the cumulated value of the items. The number of items is restricted by the maximum weight that can be carried in the knapsack. 


Mathematical Model
------------------

In the classical knapsack problem, each item can be chosen only once. This example also considers variants of the problem in which the number of equal items is unlimited or limited to certain integers. 

+-----+--------------------------------------------+-------------------------------------------+
|       Knapsack Problem                                                                       |
+=====+============================================+===========================================+
+ **Sets and indices:**                                                                        |
+-----+--------------------------------------------+-------------------------------------------+
+     | :math:`I`, :math:`i \in I`                 | items                                     |
+-----+--------------------------------------------+-------------------------------------------+
| **Parameters:**                                                                              |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`rI_{i} \in \mathbb{I}`              | minimum quantity                          |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`rA_{i} \in \mathbb{I}`              | maximum quantity                          |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`V_{i} \in \mathbb{R}`               | item price                                |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`W_{i} \in \mathbb{R}`               | item weight                               |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`mW \in \mathbb{R}`                  | knapsack weight                           |
+-----+--------------------------------------------+-------------------------------------------+
| **Variables:**                                                                               |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`X_{i} \in \{rI_{i}..rA_{i}\}`       | quantity of items placed                  |
+-----+--------------------------------------------+-------------------------------------------+
| **Constraints:**                                                                             |
+-----+--------------------------------------------+-------------------------------------------+
|  1  | :math:`\sum_{i} X_{i} * W_{i} \leq mW `    | satisfy weight                            |
+-----+--------------------------------------------+-------------------------------------------+
| **Minimize:**                                                                                |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`\sum_{i} (X_{i} * V_{i})`           | total cost                                |
+-----+--------------------------------------------+-------------------------------------------+

Language 
--------

In this section a few highlights of the use of the AIMMS Language in the application are pointed out.

Solve
~~~~~~~~~
In this section a few highlights of the use of the AIMMS Language in the application are pointed out.

Solve Unbounded
~~~~~~~~~~~~~~~~~~~~~~~~~~
In this section a few highlights of the use of the AIMMS Language in the application are pointed out.

Solve Bounded
~~~~~~~~~~~~~~~~~~~~~~~~~~
In this section a few highlights of the use of the AIMMS Language in the application are pointed out.


WebUI Features
--------------

On inputs page, there is a 'hidden' feature. If you click with the right button on the table, a small menu will appear with `CRUD <https://pt.wikipedia.org/wiki/CRUD>`_ options for that set. 

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Combination Chart Widget <https://documentation.aimms.com/webui/combination-chart-widget.html>`_

- `Item Actions <https://documentation.aimms.com/webui/widget-options.html#item-actions>`_

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Side Panel <https://documentation.aimms.com/webui/side-panels-grd-pages.html#side-panel-grid-pages>`_

- `Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Compact Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Multiselect Widget <https://documentation.aimms.com/webui/selection-widgets.html>`_ 

- `Slider Widget <https://documentation.aimms.com/webui/slider-widget.html#slider-widget>`_ 


UI Styling
----------

For this project, we used a main css file named ``colors.css``, please check it out directly on the folder. Below there are the css files you will find with comments on what they change. 

.. tab-set::
    .. tab-item:: icon.css

      .. code-block:: css
         :linenos:

         :root {
            --bg_app-logo: 15px 50% / 30px 30px no-repeat url(/app-resources/resources/images/knapsack-logo.png);
            --spacing_app-logo_width: 45px;
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

    .. tab-item:: textColor.css

      .. code-block:: css
         :linenos:

         /*Change table text color*/
         .tag-table .grid-viewport .cell:not(.flag-readOnly), 
         html:not(.using-touch) .tag-table .grid-viewport .cell:not(.flag-readOnly) {
            color: var(--primaryDark);
         }

         /*Change scalar text color*/
         .tag-scalar .kpi .value {
            color: var(--primaryDark);
         }

         .tag-slider .slider-value {
            color: var(--primaryDark);
         }

    .. tab-item:: pageAction.css

      .. code-block:: css
         :linenos:

         .page-action-v2 .page-action-menu,
         .page-action-v2 .page-action-menu.open {
            background: var(--primaryDark);
         }

         .page-action-v2 .page-action-menu:hover,
         .page-action-v2 .page-action-menu:hover {
            background: var(--primary);
         }

         .page-action-v2 .page-action-holder .page-action-item .page-action-icon, 
         .page-action-v2 .page-action-holder .page-action-item .page-action-letter {
            background-color: var(--primaryDark);
         }

         .page-action-v2 .page-action-holder .page-action-item .page-action-icon:hover, 
         .page-action-v2 .page-action-holder .page-action-item .page-action-letter:hover {
            background-color: var(--primary);
         }

    .. tab-item:: sidePanel.css

      .. code-block:: css
         :linenos:

         /*Change color after tab click*/
         .sidepanel-container .sidepanel-tab.active {
            background-color: var(--primaryDark);
         }

         /*Change letter color on hover*/
         .sidepanel-container .sidepanel-tab.active:hover {
            color: white;
         }

         /*Change icon color*/
         .sidepanel-container .sidepanel-tab .sidepanel-icon,
         .sidepanel-container .sidepanel-tab:hover {
            color: var(--primaryDark);
         }

         /*Change color after all tabs*/
         .sidepanel-container .sidepanel-tabs-container:after {
            background: var(--primaryDark);
         }

         /*Change the color below sidepanel tabs*/
         .sidepanel-container {
            background-color:   rgb(249, 249, 249);
         }

         .sidepanel-active .sidepanel-container {
            background-color:   rgba(249, 249, 249, 0);
         }

    .. tab-item:: table.css

      .. code-block:: css
         :linenos:

         .tag-table.focused .focus-cell {
            box-shadow: inset 0 0 0 1px var(--primaryDark);
         }

         .tag-table .cell.flag-number input{
            text-align: center;
         }

         /*Change checkbox color*/
         input.boolean-cell-editor-contents {
            accent-color: var(--primaryDark);
         }

    .. tab-item:: combinationChart.css

      .. code-block:: css
         :linenos:

         /*Change color of togglelegend of the combination chart*/
         .togglelegend-button svg{
            fill: var(--primaryDark);
         }

         .togglelegend-button-active:hover svg g, .togglelegend-button-active svg g {
            fill: var(--primary);    
         }       

    .. tab-item:: header.css

      .. code-block:: css
         :linenos:

         .theme-aimms header.tag-application {
            border-bottom: 2px solid var(--primaryDark);
         }

    .. tab-item:: multiselect.css

      .. code-block:: css
         :linenos:

         .tag-multiselect-widget .searchable-list li.active .checkbox:before{
            border: 1px solid var(--primary);
            background: var(--primary);
         }
         .awf-select-actions>div {
            color: var(--primary);
         }        

    .. tab-item:: body.css

      .. code-block:: css
         :linenos:

         /*Add logo on the background*/
         .scroll-wrapper--pagev2 .page-container {
            content: " ";
            background: url(img/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain;
         }

         .widgetdiv .awf-dock.top {
            border-bottom: 2px solid var(--primaryDark);
            background: var(--primaryLight);
         }

Minimal Requirements
--------------------   

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example.
