Comparing schedules from scenarios
===================================

.. Purpose - view and study schedules from different scenarios.

We want to compare schedules from different scenarios.

As basis for our running example we use  :doc:`this flowshop model <../42/42-data-session-changes>`.
This model is extended with the following:

User story:
------------

#.  Select cases of interest, first via data manager

    .. image:: images/SelectingCaseViaDataManager.png
        :align: center
        
    For every case, you are interested, press the Compare case button. 
    Before a case can be compared, there should be an active case, so start with selecting an active case.

#.  Select cases you actually want to show, via scalar widget

    .. image:: images/SelectingCasesForTopBottomPresentation.png
        :align: center
        
    We want to select a case shown for the data shown in the top Gantt Chart, 
    and then one to be shown in the bottom Gantt Chart.

#.  Compare schedules visually

    .. image:: images/SchedulesFromTwoScenarios.png
        :align: center
        
    And we want to see something like this, such that we visually can compare different schedules.


Developer Steps to achieve result:
-----------------------------------

#.  We collect the cases we want to compare, at least two cases and show the cases selected in a scalar widget.

#.  Cache the information about the schedules in the model

#.  In the WebUI we create two Gantt Chart widgets

    #.  The Gantt info is based on the cached info stored in second step
    
    #.  We slice this information per Gantt Chart using the element parameters from the first step.

Step 1: Collect cases and select two specific ones
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

AIMMS provides the predeclared set ``CurrentCaseSelection`` for all cases to be compared.
It is declared as follows:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 4

    Set CurrentCaseSelection {
        SubsetOf: AllCases;
        Text: "The set of cases selected from within the GUI";
        Index: IndexCurrentCaseSelection;
    }

Note the index ``IndexCurrentCaseSelection``; we will use this index below to declare data over all cases.

This set is filled using the dialog of the "Data Manager" tool of the AIMMS WebUI.
Once a user filled this set, he can select the case for the schedule shown in the top Gantt Chart, 
and similarly, the case for the schedule shown in the bottom Gantt Chart.

This is simply a matter of element parameters selecting a value in  ``CurrentCaseSelection``.
The scalar widget, for elements in ``AllCases`` shows the bare case name; so we don't have to do the number to name conversion ourselves here.

The element parameters are declared as follows:

.. code-block:: aimms
    :linenos:

    ElementParameter ep_ReferenceTopCase {
        Range: CurrentCaseSelection;
    }
    ElementParameter ep_ReferenceBottomCase {
        Range: CurrentCaseSelection;
    }

As we do not want to show such identifier names in the user interface, we use translation table ``MainProject\WebUI\resources\identifier-translation.properties`` with the following contents:


.. code-block:: none
    :linenos:

    ep_ReferenceTopCase = Select Schedule for top
    ep_ReferenceBottomCase = Select Schedule for bottom

Step 2: Cache the data for showing schedules
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The WebUI requires identifier references for the start and duration of the Gantt Charts; that is data that is stored in the model. Thus we need to cache data from the cases selected to the model. The AIMMS modeling languages provides the so-called dot-notation to refer to data in cases as follows:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 3,7

    Parameter p_case_GCJobStart {
        IndexDomain: (IndexCurrentCaseSelection,j,m);
        Definition: IndexCurrentCaseSelection.p_GCJobStart(j, m);
    }
    Parameter p_case_GCJobDuration {
        IndexDomain: (IndexCurrentCaseSelection,j,m);
        Definition: IndexCurrentCaseSelection.p_GCJobDuration(j, m);
    }

Here we see the index ``IndexCurrentCaseSelection`` again, varying over all cases in ``CurrentCaseSelection``. 
In the definition of these two parameters it is followed by a "."; hence the name dot-notation. 
The "." is then followed by an ordinary identifier reference.

Using this definition, AIMMS will fill the parameters ``p_case_GCJobStart`` and ``p_case_GCJobDuration`` with the schedules stored in the case files.

Step 3: Create and show two Gantt Chart widgets
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Now we create the two Gantt Chart widgets, both with Gantt data:

* **Start**: ``p_case_GCJobStart``

* **Duration**: ``p_case_GCJobDuration``

Using identifier settings on both these parameters, the index ``IndexCurrentCaseSelection`` is sliced using the element parameters ``ep_ReferenceTopCase`` and ``ep_ReferenceBottomCase`` for the top and bottom Gantt Chart widgets respectively. 

This should give the desired result as shown at the end of our user story.

Download :download:`example model here <model/Flow Shop - Comparing schedules.zip>`

Further reading
-------------------------------

#.  Section "Case referencing from within the language" in Chapter "Case Management" in AIMMS The User's Guide.

#.  AIMMS The Language Reference, search for "dot notation" and "case referencing"

#.  `The WebUI widget <https://documentation.aimms.com/webui/gantt-chart-widget.html#gantt-chart-widget>`_

