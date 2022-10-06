Contract Allocation
=========================
.. meta::
   :keywords: Semi-continuous variables, Mixed Integer Programming model, MIP, combinationchart, table, colors, css
   :description: This AIMMS project illustrates the use of a semi-continuous variable.

.. image:: https://img.shields.io/badge/AIMMS_4.85-ZIP:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_4.85-Github:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-webui-44/uptaded-contract-allocation-example-1253


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


We also use create a page action and a dialog for users to export the results to several different DEX supported formats (Excel, JSON, CSV, etc.). 
You can add more identifiers to be exported by using the DEX annotations.

.. aimms:procedure:: pr_openExportPage

This procedure will generate all the possible mappings in DEX based on current identifier DEX annotations. Details on how to setup annotations can be found `here <https://how-to.aimms.com/Articles/528/528-how-to-set-up-data-exchange-basics.html#generate-mapping-file>`_. It will also initialize identifiers used in our dialog and open that dialog page.

.. code-block:: aimms
   :linenos:

   !Generating all possible mappings
   dex::GenerateDatasetMappings();

   !Selecting the Excel mapping as initial value
   if not ep_selectedMapping then
       ep_selectedMapping 
       :=  First(i_generatedMappings | FindString(
               SearchString  :  i_generatedMappings, 
               Key           :  "excel", 
               CaseSensitive :  0, 
               WordOnly      :  0, 
               IgnoreWhite   :  0));
   endif;

   !Defining Dialog and actions - Only done required
   s_actions:= data { Done };
   ep_pageId := 'export_page';

   !Opening dialog page - no action on done - webui::NoOp1 does nothing
   webui::OpenDialogPage(
       pageId  :  ep_pageId, 
       title   :  "Export data", 
       actions :  s_actions, 
       onDone  :  'webui::NoOp1');
    


.. aimms:procedure:: pr_exportExcelData

This procedure will that will write the file and provide it for download using the `download widget <https://documentation.aimms.com/webui/download-widget.html>`_.

.. code-block:: aimms
   :linenos:

   ! writing the output file locally
   dex::WriteToFile(
       dataFile    :  sp_FileName, 
       mappingName :  ep_selectedMapping, 
       pretty      :  1);

   ! we want to download a file
   FileLocation := sp_FileName;

   ! we store the location of the file in string parameter FinalLocation
   FinalLocation := webui::GetIOFilePath(FileLocation);

   ! checking if the previous write statement was successful or not
   if FileExists(FinalLocation) then
      ! if successful, statusCode is set to 'CREATED' which will trigger the download widget to show the Get button
      StatusCode := webui::ReturnStatusCode('CREATED');
      ! displaying the status message as Ready to download exported data! instead of the default "File ready to download"
      StatusDescription := "Ready to download exported data!";
   else    !if previous write statement was not successful
      ! setting the statusCode to 'ERROR' and the download widget will not show the Get button anymore
      statusCode := webui::ReturnStatusCode('ERROR');
      !displaying a custom error message
      statusDescription := "Something went wrong when creating the file.";
   endif;


.. seealso::
   To understand in depth check out `DEX documentation <https://documentation.aimms.com/dataexchange/index.html>`_.

WebUI Features
--------------

On input page, if you click around the graphs, a highlighted cell will appear identifying the last clicked element. The results are displayed in a combination chart (stacked bar chart).

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Combination Chart Widget <https://documentation.aimms.com/webui/combination-chart-widget.html>`_

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Side Panel <https://documentation.aimms.com/webui/side-panels-grd-pages.html#side-panel-grid-pages>`_

- `Compact Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `List Widget <https://documentation.aimms.com/webui/list-widget.html#list-widget>`_ 

- `Download Widget <https://documentation.aimms.com/webui/download-widget.html>`_ 

- `Dialog Page <https://documentation.aimms.com/webui/dialog-pages.html>`_ 
 

UI Styling
----------
For this project, we used a main css file named ``colors.css``, please check it out directly on the folder. Below there are the css files you will find with comments on what they change. 

.. tab-set::
    .. tab-item:: icon.css

      .. code-block:: css
         :linenos:

         :root {
            --bg_app-logo: 15px 50% / 30px 30px no-repeat url(/app-resources/resources/images/budgeting.png);
            --spacing_app-logo_width: 45px;
         }

    .. tab-item:: workflow.css

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

         /*Link color*/
         .ql-snow a {  
            color: var(--primaryDark);
         }

         /*Change table default text color*/
         .tag-table .grid-viewport .cell.flag-default, 
         html:not(.using-touch) .tag-table .grid-viewport .cell.flag-default {
            color: white;
         }

    .. tab-item:: body.css

      .. code-block:: css
         :linenos:

         /*Add image on the background*/
         .scroll-wrapper--pagev2 .page-container {
            content: " ";
            background: url(img/RightBackground.png) rgb(249, 249, 249) no-repeat left/contain;
         }

    .. tab-item:: header.css

      .. code-block:: css
         :linenos:

         .theme-aimms header.tag-application {
            border-bottom: 2px solid var(--primary);
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

    .. tab-item:: sidePanel.css

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
   
    .. tab-item:: button.css

      .. code-block:: css
         :linenos:

         /*Change color of the busy button*/
         .veil-msg.state-busy .ui-button {
            background-color: var(--primary);
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
   
    .. tab-item:: table.css

      .. code-block:: css
         :linenos:

         .tag-table.focused .focus-cell {
            box-shadow: inset 0 0 0 1px var(--primaryDark);
         }



Minimal Requirements
--------------------   

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example.
