SENSAI Skill: Workflow Panel
============================

.. meta::
   :description: The SENSAI skill aimms-workflow-panel replaces the WinUI navigation of a converted AIMMS application by a WebUI workflow panel, built from the page tree.
   :keywords: SENSAI skill, workflow panel, WorkflowSteps, WorkflowDefinitions, WinUI to WebUI, navigation buttons

Introduction
------------

WinUI applications often navigate with buttons that open another page. After :doc:`conversion <711-winui-to-webui>`, these buttons are still on the WebUI pages, even though WebUI offers a better way: a workflow panel, a step navigation on the left side of the page.

The SENSAI skill ``aimms-workflow-panel`` creates such a workflow panel from the page tree of your application, and moves the navigation buttons out of the way. It works on a group of pages: a parent page you choose, and the pages below it.

.. TODO: State where the skill file is published, and how to add it to SENSAI (personal skill, or skills folder in the project).

What the skill does
-------------------

#. It shows the page tree, and asks you which page is the parent page, and which title the workflow gets.
#. The child pages of the parent page become the steps of the workflow. Their own child pages become child steps.
#. It adds the string parameters ``WorkflowDefinitions`` and ``WorkflowSteps`` to the model, and sets the workflow in the application settings of ``webui.json``.
#. It moves the buttons that only open another page to **Unassigned widgets** of their page. Buttons that run a model procedure stay where they are. For buttons it cannot classify, it asks you.
#. It checks the result, and reports what it changed and what you still need to verify in the WebUI.

The skill shows a preview of the steps first, and asks you to confirm before it changes anything. No widget is deleted: a button in **Unassigned widgets** can be dragged back to the page in the WebUI editor.

Applying the skill
------------------

Ask SENSAI in the chat in AIMMS Developer:

.. code-block:: none

   Apply skill aimms-workflow-panel.

Before it changes anything, the skill checks whether the project is tracked by git. If it is, it asks you to commit open changes first, and offers to commit its result at the end. If it is not, it keeps backup copies of ``webui.json`` and the model ``.ams`` file.

After the skill has finished, open the WebUI on one of the workflow pages, and check that the workflow panel shows all steps, and that the pages still work.

Limits
------

* A workflow panel has one level of child steps. Pages deeper in the tree are not shown in the panel; the skill reports them.
* AIMMS recommends at most 10 top-level steps, and at most 10 child steps per step.
* Empty grid columns left behind by the moved buttons are not removed. Ask SENSAI to remove them in a next step.
* The ``home`` page is not a workflow step. The workflow panel is not shown there.

Tested with AIMMS 26.8 and WebUI library 26.13.

.. seealso::

   * :doc:`711-winui-to-webui`
   * :doc:`711-skill-buttons-to-secondary-action`
   * :doc:`../309/309-workflow-panel-element-and-string-parameters`

.. spelling:word-list::

   WinUI
   WebUI
   webui
   aimms
   SENSAI
   json
