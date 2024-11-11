Comparing Schedules from Scenarios
===================================

.. Purpose - view and study schedules from different scenarios.

We want to compare schedules from different scenarios. We use a flowshop model example, which you can download from the link below.

:download:`Flow_shop_comparing_schedules <model/Flow_Shop_Comparing_schedules.zip>`

We'll show the result for the end user, and how to achieve it as a developer.

Comparison as End User
-----------------------

#.  Select cases via data manager

    .. image:: images/SelectingCaseViaDataManager.png
        :align: center
        
    Select an active case. For every case to compare, click :menuselection:`Compare case`. 

#.  Select cases to show via scalar widget

    .. image:: images/SelectingCasesForTopBottomPresentation.png
        :align: center
        
    Select a case to show in the top `Gantt Chart <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_, 
    and one to show in the bottom `Gantt Chart <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_.

#.  Compare schedules visually

    .. image:: images/SchedulesFromTwoScenarios.png
        :align: center
        
   The result is a visual comparison of schedules.


Developer Steps
-----------------------------------

#.  Select cases to compare (two or more) and show the selected cases in a scalar widget.

#.  Cache the information about the schedules in the model.

#.  Create two WebUI `Gantt Chart <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_ widgets.

    #.  The Gantt info is based on the cached info stored in second step.
    
    #.  Slice this information per `Gantt Chart <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_ using the element parameters from the first step.

These steps are explained in detail below.

Select Cases to Compare
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Use the predeclared set :any:`CurrentCaseSelection` for all cases you selected to be compared.
Declare as follows:

.. code-block:: aimms
    :emphasize-lines: 4

    Set CurrentCaseSelection {
        SubsetOf: AllCases;
        Text: "The set of cases selected from within the GUI";
        Index: IndexCurrentCaseSelection;
    }

Note the index ``IndexCurrentCaseSelection``; we will use this index below to declare data over all cases.

This set is filled using the dialog of the "Data Manager" tool of the AIMMS WebUI.
Once a user filled this set, he can select the case for the schedule shown in the top `Gantt Chart <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_, 
and similarly, the case for the schedule shown in the bottom `Gantt Chart <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_.

This is simply a matter of element parameters selecting a value in  :any:`CurrentCaseSelection`.
The scalar widget, for elements in :any:`AllCases` shows the case name; so we don't have to do the number-to-name conversion ourselves here.

The element parameters are declared as follows:

.. code-block:: aimms

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

Cache Data for Schedules
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The WebUI requires identifier references for the start and duration of the Gantt Charts; that is data that is stored in the model. Thus we need to cache data from the cases selected to the model. The AIMMS modeling languages uses dot-notation to refer to data in cases as follows:

.. code-block:: aimms
    :emphasize-lines: 3,7

    Parameter p_case_GCJobStart {
        IndexDomain: (IndexCurrentCaseSelection,j,m);
        Definition: IndexCurrentCaseSelection.p_GCJobStart(j, m);
    }
    Parameter p_case_GCJobDuration {
        IndexDomain: (IndexCurrentCaseSelection,j,m);
        Definition: IndexCurrentCaseSelection.p_GCJobDuration(j, m);
    }

Here we see the index ``IndexCurrentCaseSelection`` again, varying over all cases in :any:`CurrentCaseSelection`. 
In the definition of these two parameters it is followed by a "."; hence the name dot-notation. 
The "." is then followed by an ordinary identifier reference.

Using this definition, AIMMS will fill the parameters ``p_case_GCJobStart`` and ``p_case_GCJobDuration`` with the schedules stored in the case files.

Create Gantt Chart Widgets
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Now we create the two `Gantt Chart <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_ widgets, both with Gantt data:

* **Start**: ``p_case_GCJobStart``

* **Duration**: ``p_case_GCJobDuration``

Using identifier settings on both these parameters, the index ``IndexCurrentCaseSelection`` is sliced using the element parameters ``ep_ReferenceTopCase`` and ``ep_ReferenceBottomCase`` for the top and bottom Gantt Chart widgets respectively. 

This should give the desired result as shown at the end of our user story.


.. seealso::

    * :doc:`data-management/case-management/managing-multiple-case-selections`
    * AIMMS The Language Reference, search for "Case referencing" in :doc:`preliminaries/language-preliminaries/lexical-conventions`
    * `Gantt Chart Widget <https://documentation.aimms.com/webui/gantt-chart-widget.html>`_

