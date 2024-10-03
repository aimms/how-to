Knapsack Problem
==================
.. meta::
   :keywords: Knapsack, Knapsack, Integer Programming, Binary Integer Programming, Cover Inequalities, Network Object
   :description: This example introduces a knapsack problem.

.. image:: https://img.shields.io/badge/AIMMS_24.5-ZIP:_Knapsack-blue
   :target: https://github.com/aimms/knapsack/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.5-Github:_Knapsack-blue
   :target: https://github.com/aimms/knapsack

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-webui-44/updated-knapsack-problem-example-1319
   
.. image:: images/project-1920-high.gif
    :align: center

|
   
Story
-----

This example introduces a knapsack problem. The example considers a data set of 16 items which can be included in the knapsack. The objective is to maximize the accumulated value of the items. The number of items is restricted by the maximum weight that can be carried in the knapsack. 


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
|     | :math:`rI_{i}, i \in \mathbb{I}`           | minimum quantity                          |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`rA_{i}, i \in \mathbb{I}`           | maximum quantity                          |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`V_{i} \in \mathbb{R}`               | item price                                |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`W_{i} \in \mathbb{R}`               | item weight                               |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`mW \in \mathbb{R}`                  | maximum knapsack weight                   |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`mI \in \mathbb{I}`                  | maximum knapsack item                     |
+-----+--------------------------------------------+-------------------------------------------+
| **Variables:**                                                                               |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`X_{i} \in \{rI_{i}..rA_{i}\}`       | quantity of items placed                  |
+-----+--------------------------------------------+-------------------------------------------+
| **Constraints:**                                                                             |
+-----+--------------------------------------------+-------------------------------------------+
|  1  | :math:`\sum_{i} X_{i} * W_{i} \leq mW`     | respect knapsack weight capacity          |
+-----+--------------------------------------------+-------------------------------------------+
|  2  | :math:`\sum_{i} X_{i} \leq mI`             | respect knapsack item capacity            |
+-----+--------------------------------------------+-------------------------------------------+
| **Maximize:**                                                                                |
+-----+--------------------------------------------+-------------------------------------------+
|     | :math:`\sum_{i} (X_{i} * V_{i})`           | total accumulated value                   |
+-----+--------------------------------------------+-------------------------------------------+

Language 
--------

In this section a few highlights of the use of the AIMMS Language in the application are pointed out.

Let us start with modifying the type of solve, simply by modifying the bounds of the variables; 
in other words, three types of model can be solved by only one algebraic specification of the model.

Types of Solve
~~~~~~~~~~~~~~
.. aimms:procedure:: pr_solveKnapsackModel

This will solve the classic knapsack problem. Minimal range will be set as 0 and maximum will be set as 1 automatically. 
   
   .. code-block:: aimms
      :linenos:

      p_itemRangeMin(i_item) := 0;
      p_itemRangeMax(i_item) := 1;
      
.. aimms:procedure:: pr_solveKnapsackModelUnBounded

This will solve the knapsack problem as unbounded. Minimal range will be set as 0 and maximum will be set as ``inf`` automatically. 
   
   .. code-block:: aimms
      :linenos:

      p_itemRangeMin(i_item) := 0;
      p_itemRangeMax(i_item) := inf;   

.. aimms:procedure:: pr_solveKnapsackModelBounded

This will solve the knapsack problem with a integer bound. Minimal range will be set as 0 and maximum will be set by the bound value. 
   
   .. code-block:: aimms
      :linenos:

      p_itemRangeMin(i_item) := 0;
      p_itemRangeMax(i_item) := p_itemBound(i_item);

Random Data
~~~~~~~~~~~~
.. aimms:procedure:: pr_randomizeData
   
In order to make the example more playful in therms of feature functionality, you can randomize data at any time. The procedure below is available on Page Actions. 

   .. code-block:: aimms
      :linenos:
      
      empty p_itemValue, p_itemWeight, p_itemBound;

      p_itemValue(i_item) := uniform(0,200)*1[$];
      p_itemWeight(i_item) := uniform(0[lb],p_maxWeightKnapsack/3);
      p_itemBound(i_item) := ceil(uniform(0,10));

.. seealso::
   In `this article <https://how-to.aimms.com/Articles/12/12-generate-random-numbers.html#generate-random-numbers>`_ you will find more details about how to randomize your data. 

Integration
~~~~~~~~~~~~~~
On this example, `AXLL library <https://documentation.aimms.com/aimmsxllibrary/index.html>`_ is used. 
You can check both import and export procedures by looking for these: ``pr_readAll`` and ``pr_writeAll``.


WebUI Features
--------------

On inputs page, there is a 'hidden' feature. If you click with the right button on the table, a small menu will appear with `CRUD <https://pt.wikipedia.org/wiki/CRUD>`_ options for that set. 

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Combination Chart Widget <https://documentation.aimms.com/webui/combination-chart-widget.html>`_

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Side Panel <https://documentation.aimms.com/webui/side-panels-grd-pages.html#side-panel-grid-pages>`_

- `Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Multiselect Widget <https://documentation.aimms.com/webui/selection-widgets.html>`_ 

- `Slider Widget <https://documentation.aimms.com/webui/slider-widget.html#slider-widget>`_ 

- `Status Bar <https://documentation.aimms.com/webui/status-bar.html>`_ 

- `CSS Annotations <https://documentation.aimms.com/webui/css-styling.html#data-dependent-styling>`_

UI Styling
----------

For this project, we used a main css file named ``colors.css``, please check it out directly on the folder. Below there are the css files you will find with comments on what they change. 

.. tab-set::
    .. tab-item:: theming.css

      .. code-block:: css
         :linenos:

         :root {
         /*---------------------------------------------------------------------
               COLORS
         ----------------------------------------------------------------------*/
         --primary: #FF941E;
         --primaryDark: #955511;
         --primaryLight: #fff4e9;

         /*---------------------------------------------------------------------
               LOGO
         ----------------------------------------------------------------------*/
         --bg_app-logo: 15px 50% / 30px 30px no-repeat url(/app-resources/resources/images/knapsack-logo.png);
         --spacing_app-logo_width: 45px;
         --color_border_app-header-divider: var(--primaryDark); /*line color after header*/

         --color_bg_app-canvas: url(/app-resources/resources/images/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain; /*background color*/
         --color_bg_widget-header: #95551141; /*widget header background color*/
         --border_widget-header: 2px solid var(--primaryDark); /*line color after widget header*/

         --color_text_edit-select-link: var(--primaryDark);
         --color_bg_button_primary: var(--primaryDark);
         --color_bg_button_primary_hover: var(--primary);
         --color_text_edit-select-link: var(--primaryDark);


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

    .. tab-item:: annotations.css

      .. code-block:: css
         :linenos:

         .annotation-different {
            background: #ff921e2a;
         }

    .. tab-item:: custom.css

      .. code-block:: css
         :linenos:

         /*Link color*/
         .ql-snow a {  
            color: var(--primaryDark) !important;
         }

         .tag-slider .slider-value {
            color: var(--primaryDark);
         }

         .status-message.clickable:hover .status-display-text{
            color: var(--primaryDark);
         }

Minimal Requirements
--------------------   

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example.

Release Notes
--------------------   

`v1.1 <https://github.com/aimms/knapsack/releases/tag/1.1>`_ (24/09/2024)
   Now you can run the different types of solve on PRO Portal.

`v1.0 <https://github.com/aimms/knapsack/releases/tag/1.0>`_ (20/09/2024)
   First version.