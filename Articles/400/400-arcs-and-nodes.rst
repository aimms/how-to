Arcs and Nodes
===============

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Network model, Network object, Node and Arc formulation, SetElementAdd function
   :description: This example illustrates a simple network model with a GUI in which an end-user can graphically insert, delete and modify both nodes and arcs in the network.

Direct download AIMMS Project :download:`Arcs and Nodes.zip <model/Arcs and Nodes.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Arcs%20and%20Nodes

This example illustrates a simple network model with an associated user interface in which an end-user can graphically insert, delete and modify both nodes and arcs in the network.

The model contains two mathematical programming formulations:


- a formulation which uses the ARC and NODE language constructs of AIMMS
- a straight LP formulation which is equivalent to the ARC and NODE formulation

You can view both formulations within the Model Explorer.

Within the user interface, you can select either formulation to verify that the formulations yield identical solutions.

The support for adding, deleting and modifying nodes and arcs is provided by a number of simple procedures within the model, which add elements to and delete elements from sets, and modify arc-related data. These procedures are subsequently linked to the right-mouse pop up menu of the network object. This pop up menu is defined within the Menu Builder tool.

The demo page also contains a number of examples of how objects can be linked through the use of element parameters. By selecting, for example, a line in the composite table containing all ARC data, numerous other objects will be updated to highlight data for the currently selected ARC. In the same way, clicking in the network flow object will update the fields on the left of the demo page.

In addition, the demo illustrates the use of model-defined coloring and thickness of elements in the interface. Through the element parameters NodeColor and ArcColor, the NODES and ARCS in the network flow object are colored to reflect their status. Thickness of ARCS is (partially) determined by the flow computed by (either) optimization model.

Keywords:
Network model, Network object, Node and Arc formulation, SetElementAdd function

