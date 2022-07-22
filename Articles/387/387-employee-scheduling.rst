Employee Scheduling
==========================

.. meta::
   :keywords: Semi-continuous variables, Mixed Integer Programming model, MIP, combinationchart, table, colors, css
   :description: This AIMMS project illustrates the use of a semi-continuous variable.

.. image:: https://img.shields.io/badge/AIMMS_4.86-ZIP:_Employee_Scheduling-blue
   :target: https://github.com/aimms/employee-scheduling/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_4.86-Github:_Employee_Scheduling-blue
   :target: https://github.com/aimms/employee-scheduling

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-webui-44/uptaded-contract-allocation-example-1253

Story
-----

In this example an optimal employee assignment is determined for a chain of restaurants. Each restaurant has different demands concerning the number of employees available and their skills. The objective of the optimization is to find the best placement of staff at the lowest cost, while taking into account shortage of skilled workforce.

The employees work in shifts and each position is primarily fulfilled by skilled employees. Assigning a less experienced staff member to a task will have impact on the overall cost. 


Mathematical Model
------------------

The mathematical programming model for this example is a variation of the `assignment problem <https://en.wikipedia.org/wiki/Assignment_problem>`_.
The similarity is that people are assigned to tasks, the differences are 

* that unmet demand is taken into account, 

* multiple shifts, and 

* the same skill is needed at different restaurants.

+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|       Employee Scheduling Problem                                                                                                     |
+=====+=====================================================================================+===========================================+
+ **Sets and indices:**                                                                                                                 |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
+     | :math:`E`, :math:`e \in E`                                                          | employees                                 |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
+     | :math:`R`, :math:`r \in R`                                                          | restaurants                               |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
+     | :math:`K`, :math:`k \in K`                                                          | skills                                    |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
+     | :math:`H`, :math:`h \in H`                                                          | shifts                                    |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
| **Parameters:**                                                                                                                       |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|     | :math:`D_{r,h,k} \in \mathbb{I}`                                                    | demand of employees in the restaurants    |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|     | :math:`eD_{e,h} \in \{0..1\}`                                                       | employees' availability for shifts        |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|     | :math:`eH_{e,k} \in \{0..1\}`                                                       | employees' skills                         |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|     | :math:`eP_{r,e,h,k} \in \mathbb{R}`                                                 | job preferences of the employees          |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
| **Variables:**                                                                                                                        |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|     | :math:`X_{r,e,h,k} \in \{0..1\}`                                                    | employees' work schedule                  |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|     | :math:`Y_{r,h,k} \in \mathbb{R_{+}}`                                                | unfulfilled positions                     |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
| **Constraints:**                                                                                                                      |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|  1  | :math:`\forall r,h,k: \sum_e X_{r,e,h,k} = D_{r,h,k} - Y_{r,h,k}`                   | satisfy demand                            |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|  2  | :math:`\forall e,h: \sum_{r,k} X_{r,e,h,k} \leq eD_{e,h}`                           | assignment satisfies shifts               |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|  3  | :math:`\forall r,e,h,k: X_{r,e,h,k} \leq eH_{r,e,h,k}`                              | assignment satisfies skills               |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|  4  | :math:`\forall e: \sum_{r,h,k} X_{r,e,h,k} \leq 1`                                  | maximum of one shift                      |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
| **Minimize:**                                                                                                                         |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+
|     | :math:`\sum_{r,h,k} Y_{r,h,k} * 1000 + \sum_{r,e,h,k} X_{r,e,h,k} * eP_{r,e,h,k}`   | total cost                                |
+-----+-------------------------------------------------------------------------------------+-------------------------------------------+

Language 
--------

In this section a few highlights of the use of the AIMMS Language in the application are pointed out.

Structure
~~~~~~~~~~~~~~~~~~~~~~~~~~

This application structured its data nicely by using sections nested, as can be seen in the next screenshot.
Each section at the leaf level contains a few identifiers which helps finding your way around the application.

.. image:: images/model-explorer-section-structure.png
    :align: center

This structuring is meaningful; the structure can be used in the application, 
for instance in a mathematical program declaration like:

.. code-block:: aimms 
    :linenos:

    MathematicalProgram mp_minimizeCost {
        Objective: v_totalCost;
        Direction: minimize;
        Constraints: s_employeeAssignmentConstraints;
        Variables: s_employeeAssignmentVariables;
        Type: Automatic;
    }

The set ``s_employeeAssignmentConstraints`` is constructed by intersecting the declarations inside the section ``Math_model`` with the predeclared :aimms:set:`AllConstraints`. 

.. code-block:: aimms 
    :linenos:

    Set s_employeeAssignmentConstraints {
        SubsetOf: AllConstraints;
        Definition: AllConstraints * Math_Model;
    }

Such structuring eases grouping related constraints and variables together into a mathematical program; or rather,
to work with multiple groups of constraints and variables and thereby defining multiple multiple mathematical programs in a single application.

Database 
~~~~~~~~~~~~~~~~~~~~~~

The Employee Scheduling example persists its data in a database, a SQLite database.

Connecting to the database
""""""""""""""""""""""""""""""

A SQLite database is just a file, and authentication is not needed, so a connection string can be build as follows:

.. code-block:: aimms 
    :linenos:

    StringParameter sp_connectionString {
        Definition: {
            SQLCreateConnectionString (
                DatabaseInterface              :  'odbc',
                DriverName                     :  "SQLite3 ODBC Driver",
                ServerName                     :  "", 
                DatabaseName                   :  "inputs.db", !The path of your database
                UserId                         :  "", 
                Password                       :  "", 
                AdditionalConnectionParameters :  "") ;
        }
    }

Relating tables in the database to tables in the AIMMS model
""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

An example of a table declaration in a SQLite database is illustrated in the next image:

.. image:: images/employee-skill-SQLite-table.png
    :align: center

The AIMMS database declaration of the corresponding table is as follows:

.. code-block:: aimms 
    :linenos:

    DatabaseTable db_readEmployeeSkills {
        DataSource: sp_connectionString;
        TableName: "employee_skill";
        Mapping: {
            "employee_id"      -->i_empl,
            "skill_id"         -->i_sk,
            "has_skill"        -->p_employeeHasSkill,
            "skill_preference" -->p_employeeSkillPreference
        }
    }


Once the connection string exists, the data in the tables is read by a database declaration and a read statement.
If the data is changed in the user interface, the data is persisted using similar write statements.

Reading from the database
"""""""""""""""""""""""""""""


As you can see, the column names of the table are used to relate to the AIMMS identifiers at hand.

Transferring the data from the SQLite database to the AIMMS application is done using the following read statement:

.. code-block:: aimms 
    :linenos:

    Procedure pr_readEmployeeSkillDB {
        Body: {
            empty Employee_Skill; 
            
            read from table db_readEmployeeSkills;
        }
    }
    
Note that ``Employee_Skill`` is a declaration section; all identifiers declared in that section will be emptied by the first statement in the procedure ``pr_readEmployeeSkillDB``;

Writing to the database
""""""""""""""""""""""""""


Transferring the data from the AIMMS application to the SQLite database is done using the following write statement:

.. code-block:: aimms 
    :linenos:

    Procedure pr_writeEmployeeSkillDB {
        Body: {
            write p_employeeHasSkill(i_empl, i_sk),
                    p_employeeSkillPreference(i_empl, i_sk)
                to table db_readEmployeeSkills in dense mode;
        }
    }

References for using ODBC
""""""""""""""""""""""""""""""

#.  `Link an SQLite Database to a Project <https://how-to.aimms.com/Articles/118/118-Connect-SQLite.html>`_

#.  `SQLCreateConnectionString <https://documentation.aimms.com/functionreference/data-management/database-functions/sqlcreateconnectionstring.html>`_

#.  Need to go deeper into DB connections? `This course <https://academy.aimms.com/course/view.php?id=37>`_ is perfect for you!

Annotations
~~~~~~~~~~~

On this project `annotation <https://documentation.aimms.com/webui/css-styling.html>`_ is used on the Combination Chart. For this, we added a new css file:

.. code-block:: css
   :linenos:

   .annotation-red {
      fill: var(--secondary);
   }
   .annotation-not-red {
      fill: var(--primaryDark);
   }

Create into a string parameter the logic or define directly with the css class you want. Go to the identifier shown on the `Combination Chart <https://documentation.aimms.com/webui/combination-chart-widget.html>`_ and add that string parameter into ``webui::AnnotationsIdentifier``.  

WebUI Features
--------------

On master page, there are two 'hidden' features. First is that if you click with the right button on either table widgets, a small menu will appear with `CRUD <https://pt.wikipedia.org/wiki/CRUD>`_ options for that set. 
And, the Gantt chant is editable, i.e. you can modify start time and duration directly into the graph!
Both results page have similar functionality, click around into the tables to see different views on the Gantts and graphs. 

The following WebUI features are used:

- `Text Widget <https://documentation.aimms.com/webui/text-widget.html>`_

- `Image Widget <https://documentation.aimms.com/webui/image-widget.html>`_

- `Workflow <https://documentation.aimms.com/webui/workflow-panels.html>`_

- `Table Widget <https://documentation.aimms.com/webui/table-widget.html>`_

- `Gantt Chart Widget <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_

- `Combination Chart Widget <https://documentation.aimms.com/webui/combination-chart-widget.html>`_

- `Item Actions <https://documentation.aimms.com/webui/widget-options.html#item-actions>`_

- `Page Actions <https://documentation.aimms.com/webui/page-menu.html>`_ 

- `Side Panel <https://documentation.aimms.com/webui/side-panels-grd-pages.html#side-panel-grid-pages>`_

- `Scalar Widget <https://documentation.aimms.com/webui/scalar-widget.html>`_ 


UI Styling
----------
For this project, we used a main css file named ``colors.css``, please check it out directly on the folder. Below there are the css files you will find with comments on what they change. 

.. tab-set::
    .. tab-item:: icon.css

      .. code-block:: css
         :linenos:

         :root {
            --bg_app-logo: 15px 50% / 30px 30px no-repeat url(/app-resources/resources/images/schedule.png);
            --spacing_app-logo_width: 45px;
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
            background: linear-gradient(180deg, rgba(255,255,255,1) 20%, var(--primary) 100%);
         }

    .. tab-item:: header.css

      .. code-block:: css
         :linenos:

         .theme-aimms header.tag-application {
            border-bottom: 2px solid var(--primaryDark);
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

         .tag-table .cell.flag-number input{
            text-align: center;
         }

         /*Change checkbox color*/
         input.boolean-cell-editor-contents {
            accent-color: var(--primaryDark);
         }


Minimal Requirements
--------------------   

`AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient and `ODBC instalation <https://how-to.aimms.com/Articles/118/118-Connect-SQLite.html>`_ is necessary for working with this example.

