Contract Allocation
=========================
.. meta::
   :keywords: Semi-continuous variables, Mixed Integer Programming model, MIP, combinationchart, table, colors, css
   :description: This AIMMS project illustrates the use of a semi-continuous variable.

.. image:: https://img.shields.io/badge/AIMMS_24.5-ZIP:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.5-Github:_Contract_Alocation-blue
   :target: https://github.com/aimms/contract-allocation

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-webui-44/uptaded-contract-allocation-example-1253

.. image:: images/project.gif
    :align: center

|
   
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

In this example, there are two main ways to import data: by a custom Excel file, and by a pre-defined Excel which is currently on the project's main directory. 
You can choose which one to use on any page trough the Page Action buttons.

For the default data import, you will be importing 10 northwestern states for the contracts and 5 cities from that region for the producers. 
You can add more data freely without changing the sheets structure. 

In this project, you will not find any Upload or Download Widget being used to integrate data, every integration, either import or export, was developed using the ``webui::RequestFileUpload`` and ``webui::RequestFileDownload``. Let's first understand the custom import procedure. 

.. aimms:procedure:: pr_requestCustomExcel

   Since we want to request the file upload via a Page Action, we need a procedure without any arguments, so this procedure will simply call ``webui::RequestFileUpload`` passing the procedure with the read functions. 

   .. code-block:: aimms
      :linenos:

      webui::RequestFileUpload(onDone :  'pr_uploadFile');



.. aimms:procedure:: pr_uploadFile(fname,StatusCode,StatusDescription)

   Here we add the DEX mapping and read from the file the end-user selected. 

   .. code-block:: aimms
      :linenos:
      
      block ! import a custom Excel file 
         ! we store the location of the file in string parameter UploadLocation
         UploadLocation := webui::GetIOFilePath(fname);

         dex::AddMapping("inputs", "Mappings/inputs.xml");

         if dex::ReadFromFile(
            dataFile         :  UploadLocation, 
            mappingName      :  "inputs", 
            emptyIdentifiers :  1, 
            emptySets        :  1, 
            resetCounters    :  1)
         then

            ! displaying the status message, and logging it in the WebUI messages
            sp_loc_message := "File was uploaded and read successfully";
            webui::ShowMessage('info', sp_loc_message);

         endif;       

      onerror ep_err do

         !displaying a custom error message
         sp_loc_message := "Error when reading file " + errh::Message( ep_err );
         webui::ShowMessage('error', sp_loc_message);

         errh::MarkAsHandled(ep_err) ;

      endblock;


For exporting the results Excel, we do something similar:

.. aimms:procedure:: pr_requestResults

   This procedure will generate all the possible mappings in DEX based on current identifier DEX annotations. Details on how to setup annotations can be found `here <https://how-to.aimms.com/Articles/528/528-how-to-set-up-data-exchange-basics.html#generate-mapping-file>`_. 
   We will then make a copy of an empty Excel file to write our information. Then, using ``webui::RequestFileDownload`` to export.

   .. code-block:: aimms
      :linenos:

      dex::GenerateDatasetMappings();

      sp_loc_fileName := "Results.xlsx";

      FileCopy("empty.xlsx", sp_loc_fileName);

      ! writing the output file locally
      dex::WriteToFile(
         dataFile    :  sp_loc_fileName, 
         mappingName :  ep_def_selectedMapping, 
         pretty      :  1);

      sp_loc_IOPath := webui::GetIOFilePath(sp_loc_fileName);

      ! this is required so it works on the cloud
      FileCopy(sp_loc_fileName, sp_loc_IOPath);

      if FileExists(sp_loc_IOPath) then
         webui::RequestFileDownload(sp_loc_IOPath);
         webui::ShowMessage('info',"Export complete.");

      else
         webui::ShowMessage('error',"Something went wrong when creating the file.");
      endif;

.. seealso::
   To understand in depth check out `DEX documentation <https://documentation.aimms.com/dataexchange/index.html>`_.

WebUI Features
--------------

On input page, if you click around the graphs, a highlighted cell will appear identifying the last clicked element. The results are displayed in a combination chart widget.

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Combination Chart Widget <https://documentation.aimms.com/webui/combination-chart-widget.html>`_

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Side Panel <https://documentation.aimms.com/webui/side-panels-grd-pages.html#side-panel-grid-pages>`_

- `Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 

- `CSS Annotations <https://documentation.aimms.com/webui/css-styling.html#data-dependent-styling>`_


UI Styling
----------
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

      .. code-block:: css
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
--------------------   

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this example.


Release Notes
--------------------   

`v1.5 <https://github.com/aimms/contract-allocation/releases/tag/1.5>`_ (24/09/2024)
   Fixing integration problems (import and export) when using the project on AIMMS PRO Portal.

`v1.5 <https://github.com/aimms/contract-allocation/releases/tag/1.5>`_ (20/09/2024)
   Upgrading AIMMS version and WebUI library version.

`v1.4 <https://github.com/aimms/contract-allocation/releases/tag/1.4>`_ (27/02/2024)
   Upgrading AIMMS version, updating theme and fixing Default Data import.

`v1.3 <https://github.com/aimms/contract-allocation/releases/tag/1.3>`_ (09/08/2023)
   Correcting download procedure, adding new options when importing data. 

`v1.2 <https://github.com/aimms/contract-allocation/releases/tag/1.2>`_ (15/06/2023)
   Updated to 4.95 and added dependent styling using annotation on Results page. 

`v1.1 <https://github.com/aimms/contract-allocation/releases/tag/1.1>`_ (15/05/2023)
   Updated to 4.94 and improved Input page for better UX flow. 

`v1.0 <https://github.com/aimms/contract-allocation/releases/tag/1.0>`_ (17/03/2023)
	First logged version with the new workflow structure and colors. 

.. seealso::
   * :doc:`../545/545-summary-examples-features`

.. spelling:word-list::

   primaryDark
   ddab
   bg
   