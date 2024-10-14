Distributed Solver Sessions
============================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Parallel Solver Session, Cutting Stock example, GMP, Indexed Page object, Progress Window, FileView, External Procedure
   :description: This project illustrates AIMMS' capabilities for solving two or more optimization programs in parallel by using distributed solver sessions.

Direct download AIMMS Project :download:`Distributed Solver Sessions.zip <model/Distributed Solver Sessions.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Distributed%20Solver%20Sessions

This project illustrates AIMMS' capabilities for solving two or more optimization programs in parallel by using distributed solver sessions. Ideally this example should be run on a multi-processor machine.

This example is based on the Cutting Stock example.

The problem can be summarized as follows: how to cut long rolls of material (referred to as raws) into smaller rolls of a prescribed size (referred to as finals), given a demand for each of the finals. A full description of the Cutting Stock problem can be found in the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ Guide.

While in the Cutting Stock example raws of only one size are considered, this example takes into account raws of two (or potentially more) sizes. This Extended Cutting Stock problem is tackled by the adjusted column generation scheme described in Chapter 20 of the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ guide.

The pattern generation approach is implemented both using conventional math programs (that require regeneration every time) and using generated math programs (GMPs) that allow for direct updates on the generated instances. In the former case the pattern generation sub-models corresponding to different raw sizes are solved sequentially, while in the latter case, the sub-models are solved in parallel by using different solver sessions.

The results page illustrates the solution both in tabular and graphical form given the raw size, as well as the prescribed finals and their demands. The graphical representation uses an Indexed Page Object which retrieves information about each raw from a separate single raw page (scrolling can be used in the Indexed Page Object in order to see all raws). For parallel execution useful information is displayed in the Progress Window as well as in an additional output file.

Keywords:
Parallel Solver Session, Cutting Stock example, GMP, Indexed Page object, Progress Window, FileView, External Procedure



