Contract Allocation
=========================
.. meta::
   :keywords: Semi-continuous variables, Mixed Integer Programming model, MIP, combinationchart, table, colors, css
   :description: This AIMMS project illustrates the use of a semi-continuous variable.

.. image:: https://img.shields.io/badge/AIMMS_4.85-ZIP:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_4.85-Github:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation

.. image:: https://img.shields.io/badge/UI-WebUI-sucess

.. note::
   AIMMS Community license is sufficient for working with this example.

Story
-----

In this model we have a set of contracts, where every contract represents an amount of commodity that has to be supplied. The objective is to determine which of the producers will take care of which contract such that the total costs are minimal, under the following conditions:


- The demand for every contract is met.

- The amount supplied by each producer does not exceed the total amount available for supply.

- If a producer supplies a part of a contract then this contribution has a given minimal size.

- There is a minimal number of suppliers for every contract. 

- The total cost associated with all the deliveries is minimal.


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
|     | :math:`X_{p,c} \in \{0\} \union \{M_{p}..10000\}`    | amount of commodity delivered by p to c   |
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

In this example we used 10 northwestern states for the contracts and 5 cities from that region for the producers. To import the data into our model, we are currently using DEX library through Excel (``NothWesternStates.xlsx``). 
You can add more data freely without changing the sheets structure.  

.. aimms:procedure:: pr_importExcelData

This procedure will add and read the ``xml`` mapping available. Take a look at ``Mappings/inputs.xml``.

.. code-block:: aimms
   :linenos:

   dex::AddMapping(
      mappingName :  "inputs", 
      mappingFile :  "Mappings/inputs.xml");

   dex::ReadFromFile(
      dataFile         :  "NothWesternStates.xlsx", 
      mappingName      :  "inputs", 
      emptyIdentifiers :  1, 
      emptySets        :  1, 
      resetCounters    :  1);

   ep_actualContract := first(i_contract);
   ep_actualProducer := first(i_producer);

.. seealso::
   To understand in depth check out `DEX documentation <https://documentation.aimms.com/dataexchange/index.html>`_ .

WebUI Features
--------------

On input page, if you click around the graphs, a highlighted cell will appear identifying the last clicked element. The results are displayed in a combination chart (stacked bar chart).

The following WebUI features are used:

- Text Widget

- Image Widget

- Workflow

- Table Widget

- Combination Chart Widget

- Page Actions 

- Side Panel

- Compact Scalar Widget

- List Widget

UI Styling
----------
For this project, we used a main css file named ``colors.css``, please check it out directly on the folder. Below there are the css files you will find with comments on what they change. 

**workflow.css**

.. code-block:: css
   :linenos:

   /*Change color of the active step*/
   .workflow-panel .step-item.current {
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
   }

**textColor.css**

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

**body.css**

.. code-block:: css
   :linenos:

   /*Add image on the background*/
   .scroll-wrapper--pagev2 .page-container {
      content: " ";
      background: url(img/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain;
   }

**header.css**

.. code-block:: css
   :linenos:

   /*Add logo*/
   .theme-aimms header.tag-application .pages > .app-name::before {
      content: " ";
      background: url(img/budgeting.png) no-repeat center/contain;
      float: left;
      width: 30px; /*image size*/
      height: 30px; 
      margin-right: 7px; /*space between logo and tittle*/
      margin-top: -8px; /*move the logo down*/
   }

   .theme-aimms header.tag-application .pages > .app-name {
      margin-top: 12px; /*move tittle down to be centered in the menu area*/
   }

   .theme-aimms header.tag-application {
      border-bottom: 2px solid var(--primary);
   }

**combinationChart.css**

.. code-block:: css
   :linenos:

   /*Change color of togglelegend of the combination chart*/
   .togglelegend-button svg{
      fill: var(--primaryDark);
   }

**sidePanel.css**

.. code-block:: css
   :linenos:

   /*Change color after tab click*/
   .sidepanel-container .sidepanel-tab.active {
      background-color: var(--primary);
   }

   /*Change letter color on hover*/
   .sidepanel-container .sidepanel-tab.active:hover {
      color: white;
   }

   /*Change icon color*/
   .sidepanel-container .sidepanel-tab .sidepanel-icon,
   .sidepanel-container .sidepanel-tab:hover {
      color: var(--primary);
   }

   /*Change color after all tabs*/
   .sidepanel-container .sidepanel-tabs-container:after {
      background: var(--primary);
   }

   /*Change the color below sidepanel tabs*/
   .sidepanel-container {
      background-color: rgba(249, 249, 249, 0.438)
   }

