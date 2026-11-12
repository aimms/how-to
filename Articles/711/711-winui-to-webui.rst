Converting a WinUI Application to WebUI
=======================================

.. meta::
   :description: Convert an AIMMS WinUI application to WebUI: run the WinUI_to_WebUI converter, then refine each page iteratively by hand, with SENSAI, or with SENSAI skills, and store every step.
   :keywords: WinUI to WebUI, AIMMS WebUI, WinUI migration, conversion process, SENSAI, SENSAI skills, workflow panel, secondary actions, page actions

Introduction
------------

Today, most applications run on a server, and people use them through a web browser. That is why AIMMS introduced WebUI more than a decade ago. Since then, AIMMS has supported both WinUI and WebUI side by side. Future AIMMS releases will no longer support WinUI, so now is the time to convert existing applications to WebUI.

Converting an application combines three things:

* **Traditional programming.** The ``WinUI_to_WebUI`` converter does the bulk of the work. It converts every WinUI page automatically into a WebUI page, with the same widgets and the same layout.
* **Artificial intelligence.** SENSAI, the AI built into AIMMS, handles the routine refinements, such as replacing the WinUI navigation by a workflow panel, and tidying up each page.
* **Human judgement.** You, as the developer, keep the final say: you decide what looks right and what your users need.

This way, you can convert an existing WinUI application to WebUI quickly, without losing control over the result.

This article describes the conversion process. How to run the converter itself, and which options it has, is described in :doc:`711-operating-winui-to-webui-converter`.

Prerequisites
-------------

* AIMMS Developer 26.8. The converter opens the project with the latest installed AIMMS 26.8.x.
* ``WinUI_to_WebUI.exe``. See :doc:`711-operating-winui-to-webui-converter`.
* Access to an AIMMS Cloud with SENSAI activated, to make changes with SENSAI. Without SENSAI, you can still convert the application and make all changes by hand. See :doc:`../714/714-hello-world-sensai-apps` for how to connect AIMMS Developer to SENSAI.
* The SENSAI skills ``aimms-workflow-panel`` and ``buttons-to-secondary-action``, to apply these skills. See :ref:`w2w-skills`.
* Git, recommended, to store each step. A backup copy of the project folder per step also works.

.. TODO: Add the download link of the Data Reconciliation example, in its original WinUI version and its converted WebUI version.

The Conversion Process
----------------------

A conversion consists of a single automatic step, followed by an iterative refinement:

#. **Apply the converter** to the whole project.

#. **Iterate** until the WebUI application is ready:

   #. **Evaluate** the current WebUI pages. Compare them with the original WinUI pages, and decide what to improve next.

   #. **Apply a change**, to a single page or to a group of pages, by hand or with SENSAI. See :ref:`w2w-kinds-of-change`.

   #. **Store** the result: commit it to git, or make a backup copy of the project folder.

Each iteration refines the result of the previous one. Because you store every step, you can always compare the page before and after a step, and go back when a change does not work out.

Before you start
~~~~~~~~~~~~~~~~

Put the project under source control before you run the converter, for example in a git repository, and commit the WinUI version. If you do not use source control, copy the whole project folder. Both the converter and SENSAI modify files in the project folder, such as the model ``.ams`` files and ``webui.json``.

When you use git, check that the project files are really tracked. A folder listed in ``.gitignore`` is not stored by git, so git cannot restore it. Run ``git status`` in the project folder: it should not report the project files as ignored, and after a commit it should report nothing to commit.

Step 1: Apply the converter
~~~~~~~~~~~~~~~~~~~~~~~~~~~

Run ``WinUI_to_WebUI`` in the project folder, as described in :doc:`711-operating-winui-to-webui-converter`. The converter:

* converts all WinUI pages into WebUI pages, with the same widgets and the same layout,
* adds the identifiers the WebUI pages need to a generated module in the main model and in each library, and
* writes a conversion log, ``<project>_conversion.md``, which lists the objects it could not convert.

Open the project in AIMMS, check that the model compiles, and open the WebUI. Commit this first converted version. It is the starting point for all later steps.

.. warning::

   Running the converter again on the whole project rebuilds ``webui.json`` from scratch, and discards all changes made afterwards, by hand or with SENSAI. To reconvert a single page later, use the ``--page`` option. It leaves all other pages unchanged.

Step 2: Evaluate the current interface
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Open the converted pages in the WebUI, and compare them with the original WinUI pages. Typical points to look for:

* WinUI navigation, such as buttons that only open another page. WebUI offers a workflow panel and a menu for this. This point concerns a group of pages, often the whole application.
* Buttons that start an action. In WebUI, these usually fit better in the page actions menu at the bottom right of the page.
* Empty grid rows and columns, and widgets that use the screen space poorly.
* Text that does not fit, such as table headers that are cut off.
* Objects listed in the conversion log as not converted.

Pick one improvement at a time. Small steps are easier to review and easier to undo. Start with the changes that concern a group of pages, such as the workflow panel, and then refine the pages one by one.

Step 3: Apply changes
~~~~~~~~~~~~~~~~~~~~~

Apply the improvement you chose. The next section describes the kinds of change you can make.

Step 4: Store the changes
~~~~~~~~~~~~~~~~~~~~~~~~~

When you are satisfied with the change, store it, and describe what you changed:

.. code-block:: none

   git add -A
   git commit -m "Run reconciliation page: replace navigation buttons by workflow panel"

Without git, copy the project folder, and give the copy a name that describes the step.

When you are not satisfied, go back to the previous version, and try another way. With git, put all changes since the last commit aside, including new files:

.. code-block:: none

   git stash push --include-untracked -m "attempt: workflow panel"

The project is then back at the last commit. The changes are not lost: ``git stash list`` shows them, and ``git stash pop`` brings them back. Without git, restore the backup copy of the project folder.

Then return to Step 2 for the next improvement, or the next page.

.. _w2w-kinds-of-change:

Kinds of Change
---------------

There are three ways to make a change. A manual change applies to a single page. A change made with SENSAI, with or without a skill, applies to a single page or to a group of pages, for example all pages below a parent page.

Manual changes
~~~~~~~~~~~~~~

Some changes take human judgement, and are best made by hand, on a single page, in the WebUI editor or in the model. For example:

* combining related values, such as a model status and an error measure, in one KPI widget,
* moving buttons to the page actions menu, and
* widening table columns, so that the headers are fully visible.

Asking SENSAI for an improvement
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

For routine changes, give SENSAI a simple instruction in the SENSAI chat in AIMMS Developer, for a single page or for a group of pages. For example:

.. code-block:: none

   On page "Run reconciliation model", eliminate the empty columns and rows.

.. code-block:: none

   On all pages, move the "This Section" widgets to Unassigned widgets.

When an instruction concerns a group of pages, check a few of those pages before you store the result.

Moving a widget to **Unassigned widgets** removes it from the page without deleting it. You can drag it back in the WebUI editor later.

Review what SENSAI changed, in the WebUI and in the git diff, before you store it.

.. _w2w-skills:

Asking SENSAI to apply a skill
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

A SENSAI skill is a reusable set of instructions for a recurring task. A skill makes the result predictable: SENSAI follows the same procedure every time, checks its own work, and reports what it changed. Two skills are available for converted applications:

``aimms-workflow-panel``
   Works on a group of pages: a chosen parent page and the pages below it. It creates a WebUI workflow panel, a step navigation on the left side of the page, in which the child pages of the parent page become the steps. The skill also moves the navigation buttons of these pages to **Unassigned widgets**, so the workflow panel replaces them. This frees the left column, and the pages can use the full width.

``buttons-to-secondary-action``
   Works on a single page. It moves some or all buttons of the page to the page actions menu at the bottom right of the page (the secondary actions). The buttons move to **Unassigned widgets**, and the skill creates the string parameter and page setting the page actions need. To handle a group of pages, ask SENSAI to apply the skill to each of them.

When you make the same manual change on several pages, consider asking SENSAI to turn it into a skill of your own. See :doc:`../714/714-hello-world-sensai-apps`.

.. TODO: State where the skills are published (for example the sensai/skills folder of the winui-to-webui repository) and how to add them to SENSAI.

To apply a skill, ask SENSAI in the chat, for example:

.. code-block:: none

   Apply skill aimms-workflow-panel.

.. code-block:: none

   Apply skill buttons-to-secondary-action to page "Run reconciliation model", for all buttons.

The workflow panel skill asks you which page is the parent page, and which title the workflow gets. Both skills show a preview first, and ask you to confirm before they change anything. Neither deletes a widget. Both check whether the project is tracked by git. If it is, they ask you to commit open changes first, and offer to commit their result. If it is not, they keep backup copies of the files they change. Read the report SENSAI gives at the end: it lists the changed files, and what you still need to verify in the WebUI.

.. tip::

   Do not save from the WebUI editor while SENSAI is editing ``webui.json``. The WebUI rewrites that file when it saves, and may overwrite the changes of SENSAI.

Example
-------

The Data Reconciliation application shows the process on a single page, **Run reconciliation model**.

.. TODO: Add a screenshot for each step below.

#. **The original WinUI page.** On the left are the navigation buttons. On the right are an introduction text, the settings and buttons to run the model, and a table of observed errors.

#. **After the converter.** ``WinUI_to_WebUI`` converted the page to a WebUI page with the same widgets and the same layout. The WinUI navigation came along as well, even though WebUI does not need it.

#. **After the workflow panel skill.** SENSAI applied the skill ``aimms-workflow-panel``. A workflow panel now shows all steps of the application in one place. The navigation buttons moved to **Unassigned widgets**, and the page uses the full width.

#. **After asking SENSAI to tidy up.** SENSAI eliminated the empty columns and rows, and moved the "This Section" widgets to **Unassigned widgets**. The page uses the screen space better.

#. **After manual changes.** The model status and the observed error are combined in one KPI widget, the buttons are moved to the page actions menu, and the table columns are widened, so the headers are fully visible.

The result is a clean WebUI page that does the same as the original WinUI page.

Summary
-------

To convert a WinUI application to WebUI, run the ``WinUI_to_WebUI`` converter once on the whole project, and then refine the result page by page. In each iteration, evaluate the pages, apply one change to a single page or a group of pages, by hand, with SENSAI, or with a SENSAI skill, and store the result. The converter does the bulk of the work, SENSAI handles the routine refinements, and you keep the final judgement.

.. seealso::

   * :doc:`711-operating-winui-to-webui-converter`
   * :doc:`../714/714-hello-world-sensai-apps`
   * `SENSAI documentation <https://documentation.aimms.com/sensai/index.html>`_

.. spelling:word-list::

   WinUI
   WebUI
   webui
   aimms
   SENSAI
   json
   KPI
   reconvert
