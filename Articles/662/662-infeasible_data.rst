
Infeasible Data
==============================

.. warning::
   This example requires AIMMS 25.1 or higher.

.. meta::
   :keywords: Capacitated warehouse location, WebUI, Infeasible data
   :description:    This project illustrates AIMMS' capabilities to find data causing an infeasibility in a mathematical model.

Direct download AIMMS Project :download:`CWL.zip <model/CWL.zip>`

The capacitated warehouse location problem is the problem of locating a
number of warehouses which have to service a set of customers, at minimum
cost, where each customer has an associated demand and there are constraints
on the total demand that can be met from a warehouse.

This model is infeasible. The new procedure GMP::Instance::GetInfeasibleData
is used to find the cause of the infeasibility. To run the project, first run
the procedure MainExecution to solve the model (which will be infeasible) and
next run the procedure FindInfData to find the infeasible data. The procedure
FindInfData fills a HTML message explaining the cause of the infeasibility,
and it fills the .SuspicionLevel suffix of all parameters involved in the
infeasibility with values 'High', 'Medium' or 'Low'; it remains empty if the
parameter is not involved.

You can also run this project using the WebUI by pressing 'WebUI' in the menu
bar (after opening the AIMMS project). This will open a web page. Press the
'Solve' button to solve the model (it calls the procedure MainExecution), and
press the 'FindInexactData' button to call the procedure FindInfData to find the
infeasible data. A text message will be displayed and several data cells will
be colored. To color the cells, the
`webui::AnnotationsIdentifier <https://documentation.aimms.com/webui/widget-options.html#webui-annotationsidentifier>`_
attribute of the parameters Capacity, Demand and AllocationCost is used. The
colors are specified in the file 'backgroundcolor.css' in the following subfolder:
MainProject\\WebUI\\resources\\css.

Please note that the `webui::ElementTextIdentifier <https://documentation.aimms.com/webui/multi-language.html#element-text>`_
attribute of the set Locations translates the elements of this set in the WebUI.
It is also used to translate the elements appearing in the HTML message.


Keywords:
Capacitated warehouse location, WebUI, Infeasible data

.. seealso::
   * Section `Explainability <https://documentation.aimms.com/language-reference/optimization-modeling-components/implementing-advanced-algorithms-for-mathematical-programs/managing-generated-mathematical-program-instances.html#explainability>`__
     in the Language Reference
   * Procedure `GMP::Instance::GetInfeasibleData <https://documentation.aimms.com/functionreference/algorithmic-capabilities/the-gmp-library/gmp_instance-procedures-and-functions/gmp_instance_getinfeasibledata.html>`__
     in the Function Reference

.. below are spelling exceptions only for this document

.. spelling:word-list::

    SuspicionLevel
    webui
    AnnotationsIdentifier
    webui::AnnotationsIdentifier
    ElementTextIdentifier
    webui::ElementTextIdentifier
    backgroundcolor
