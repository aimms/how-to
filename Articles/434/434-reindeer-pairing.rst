Reindeer Pairing
=================
.. meta::
   :keywords: Stable marriage problem, network object, constraint programming, channel constraint, if-then constraint
   :description: This AIMMS project is an illustration of the stable marriage problem.

.. image:: https://img.shields.io/badge/AIMMS_4.88-ZIP:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_4.88-Github:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-webui-44/uptaded-contract-allocation-example-1253

Story
-----

This AIMMS project is an illustration of the stable marriage problem (STEM). 
Every Christmas Santa is as always busy with preparations before his longer-than-7.5-million-kilometer trip around the world. 
One of the many things he has to worry about is how to pair up his reindeer in front of the sleigh. 
We all know that Rudolf goes right in front of everyone else because of his shiny nose, but what about his other eight four-legged friends? 
The traditional Christmas carols tell us that the reindeer are typically arranged in four pairs, front to back, as follows:

#. Dasher, Dancer
#. Prancer, Vixen
#. Comet, Cupid
#. Donner, Blitzen

Therefore, we are going to assume that this is an arrangement that works pretty well (after all it’s been working since 1823). However Santa kept thinking: "Are there other good ways to pair up my reindeers?”. 
Santa was kind enough to provide the following lists of pairing preferences for each of his reindeer. 
The names in each list are sorted in decreasing order of pairing preference. The lefties appear in bold, while the righties appear in italic.

#. **Dasher**: *Dancer, Cupid, Vixen, Blitzen*
#. **Prancer**: *Vixen, Blitzen, Dancer, Cupid*
#. **Comet**: *Cupid, Dancer, Blitzen, Vixen*
#. **Donner**: *Blitzen, Vixen, Dancer, Cupid*
#. *Dancer*: **Prancer, Comet, Dasher, Donner**
#. *Vixen*: **Dasher, Donner, Prancer, Comet**
#. *Cupid*: **Prancer, Dasher, Comet, Donner**
#. *Blitzen*: **Comet, Prancer, Donner, Dasher**

What Santa would like to know is whether or not there are other good pairings in addition to the traditional one. 
If so, he can add some variety to his line-up and the reindeer won’t get so bored by galloping side-by-side with the same companion every year.

.. note::
   The problem formulation is originally stated elegantly at `Tallys Yunes' blog <http://orbythebeach.wordpress.com/2011/12/20/how-should-santa-pair-up-his-reindeer/>`_.
   And more information about the reindeers personalities `here <https://www.santarules.com/2018/10/reindeer-names-personalities/>`_! 

Mathematical Model
------------------

This AIMMS project illustrates the use of a `Contraint Programing <https://en.wikipedia.org/wiki/Constraint_programming>`_ (CP) model. 

+-----+------------------------------------------------------+-------------------------------------------+
|       Reindeer Pairing Problem                                                                         |
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

Page Layout
~~~~~~~~~~~~


DirectSQL
~~~~~~~~~~
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


Multiple Solutions
~~~~~~~~~~~~~~~~~~~

In this example we used 10 northwestern states for the contracts and 5 cities from that region for the producers. To import the data into our model, we are currently using DEX library through Excel (``NothWesternStates.xlsx``). 
You can add more data freely without changing the sheets structure.  

WebUI Features
--------------

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Scalar (and Compact) Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `Button Widget <https://documentation.aimms.com/webui/button-widget.html>`_

UI Styling
----------
Below there are the ``css`` files you will find with comments on what they change. 

.. tab-set::
    .. tab-item:: colors.css

      .. code-block:: css
         :linenos:

         :root {
            --primaryLight: #FDFCEF;
            --primary: #C7EDE6;
            --primaryDark: #1DC1A3;
            --primaryDarker: #127260;
            --secondary: #EE3E54;
            --secondary2: #e9818d;


            --bg_app-logo: 15px 50% / 50px 50px no-repeat url(/app-resources/resources/images/santa_logo.png);
            --spacing_app-logo_width: 65px;

            --color_border-divider_themed: var(--primaryDark);
            --color_text_edit-select-link: var(--primaryDarker);
            --color_text_edit-select-link_hover: var(--primary);
            --color_bg_edit-select-link_inverted: var(--secondary);

            --color_bg_button_primary: var(--primaryDark);
            --color_text_button_primary: white;
            --border_button_primary: 1px solid var(--primaryDark);

            --color_bg_button_primary_hover: var(--primary);
            --color_text_button_primary_hover: var(--primaryDarker);
            --border_button_primary_hover: 1px solid var(--primaryDarker);

            --color_text_button_secondary: var(--secondary);
            --border_button_secondary: 1px solid var(--secondary);
            --color_text_button_secondary_hover: var(--primaryDarker);
            --border_button_secondary_hover: 1px solid var(--primaryDarker);

            --color_bg_widget-header: var(--primaryDarker);
            --border_widget-header: 3px solid var(--primaryDark);
         }
    
    .. tab-item:: table.css

      .. code-block:: css
         :linenos:

         .tag-table.focused .cell.focus-cell {
            box-shadow: inset 0 0 0 2px var(--primaryDark);
         }

    .. tab-item:: body.css

      .. code-block:: css
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

    .. tab-item:: textColor.css

      .. code-block:: css
         :linenos:

         /*Link color*/
         .ql-snow a {  
            color: var(--primaryDarker);
         }

         /*Change checkbox color*/
         input.boolean-cell-editor-contents {
            accent-color: var(--primaryDarker);
         }

         .aimms-widget .ui-button {
            text-transform: uppercase;
         }

         /*Changing tittle to be uppercase*/
         .title-addon,
         .ui-dialog .ui-dialog-title,
         .page-container__dialog-header .title{
            text-transform: uppercase;
            text-shadow: 1px 1px 0px var(--primaryDarker);
            color: whitesmoke;
         }

         .tag-table .grid-viewport .cell:not(.flag-readOnly), html:not(.using-touch) .tag-table .grid-viewport .cell:not(.flag-readOnly) {
            color: var(--primaryDarker);
         }

         .widget-menu-container .widget-menu-items-wrapper .widget-menu-item .title {
            color: var(--primaryDarker);
         }

    .. tab-item:: workflow.css

      .. code-block:: css
         :linenos:

         /*Change color of the active step*/
         .workflow-panel .step-item.current,
         .workflow-panel.collapse .step-item.current {
            box-shadow: inset 0.3125rem 0 0 var(--primaryDark);
         }

         /*Change color of the titles*/
         .workflow-panel .step-item.active.complete .title, 
         .workflow-panel .step-item.active.incomplete .title {
            color: var(--primaryDarker);
         }

         /*Change color of the icons*/
         .workflow-panel .step-item.active.complete .icon, 
         .workflow-panel .step-item.active.incomplete .icon {
            color: var(--primaryDarker);
            border: 1px solid var(--primaryDarker);
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
  

Minimal Requirements
--------------------   

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example.

.. spelling::
