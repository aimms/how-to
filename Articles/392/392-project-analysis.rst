Project Analysis
===================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Project Planning, Critical Path, Trade Off, Network Object, 2-D Chart
   :description: The model describes a project planning problem where several activities need to be done in a certain order.

Direct download AIMMS Project :download:`Project Analysis.zip <model/Project Analysis.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Application%20Examples/Project%20Analysis

The model describes a project planning problem. In order to finish a project, a number of activities needs to be done in a certain order. Each activity can either be finished within normal time at a normal cost, or be crashed within shorter time at a higher cost. A project manager needs to find the minimize cost to complete whole project within a required deadline.

An activity-on-node (AON) project network is used to represent the problem. Each activity is represented by a node and the arcs show the precedence relationships between activities.  In particular, the node for each activity with immediate predecessors has an arc coming in from each of these predecessors.  Through the project network, the project duration can be estimated by the length of the longest path, or the critical path. The project duration equals to the length of the critical path. Thus by crashing activities on the critical path, we can reduce the duration of the project. 

This example provides a solution to which activities can be crashed in order to meet the project deadline at a minimum cost. A sensitivity analysis is also available to find the best trade off between time and cost.

Keywords:
Project Planning, Critical Path, Trade Off, Network Object, 2-D Chart


