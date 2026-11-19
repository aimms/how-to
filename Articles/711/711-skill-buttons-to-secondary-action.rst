SENSAI Skill: Buttons to Secondary Actions
==========================================

.. meta::
   :description: The SENSAI skill buttons-to-secondary-action moves the buttons of a WebUI page of a converted AIMMS application to the page actions menu.
   :keywords: SENSAI skill, secondary actions, page actions, secondarypageactions, WinUI to WebUI, buttons

Introduction
------------

On WinUI pages, buttons that start an action, such as solving the model or printing a report, are placed on the page itself. After :doc:`conversion <711-winui-to-webui>`, they are still there. In WebUI, these actions usually fit better in the page actions menu at the bottom right of the page, the secondary actions.

The SENSAI skill ``buttons-to-secondary-action`` moves some or all buttons of a single page to that menu. To handle a group of pages, ask SENSAI to apply the skill to each of them.

.. TODO: State where the skill file is published, and how to add it to SENSAI (personal skill, or skills folder in the project).

What the skill does
-------------------

#. It lists the buttons of the page, with the procedure each button runs.
#. It asks which buttons to move, if you did not say so, and chooses an icon for each of them.
#. It moves the chosen buttons to **Unassigned widgets** of the page.
#. It adds a string parameter, by default ``sp_<page>_secondary_actions``, to the model, with a display text, icon, procedure and state for each action, and sets it as the secondary actions of the page in ``webui.json``.
#. It checks the result, and reports what it changed and what you still need to verify in the WebUI.

The skill shows a preview first, and asks you to confirm before it changes anything. No widget is deleted: a button in **Unassigned widgets** can be dragged back to the page in the WebUI editor.

Applying the skill
------------------

Ask SENSAI in the chat in AIMMS Developer, for example:

.. code-block:: none

   Apply skill buttons-to-secondary-action to page "Run reconciliation model", for all buttons.

Before it changes anything, the skill checks whether the project is tracked by git. If it is, it asks you to commit open changes first, and offers to commit its result at the end. If it is not, it keeps backup copies of ``webui.json`` and the model ``.ams`` file.

After the skill has finished, check in the WebUI that the buttons are gone from the page, that the page actions menu shows the new actions with the right icons, and that each action runs its procedure.

Limits
------

* A secondary action has a display text, an icon, a procedure and a state (active, inactive or hidden). Other button options, such as a title or visibility bound to model data, are not carried over. The skill reports such buttons, so you can decide how to handle them.
* A button without a procedure, for example one that only opens another page, cannot become a secondary action. Use :doc:`711-skill-aimms-workflow-panel` for navigation buttons.
* The WebUI shows at most 10 secondary actions per page. AIMMS recommends about five.

Tested with AIMMS 26.8 and WebUI library 26.13.

.. seealso::

   * :doc:`711-winui-to-webui`
   * :doc:`711-skill-aimms-workflow-panel`
   * :doc:`../295/295-convert-page-actions`

.. spelling:word-list::

   WinUI
   WebUI
   webui
   aimms
   SENSAI
   json
