Working with Wonderful WebUI Widgets
====================================

Purpose
-------

AIMMS is designed for building bespoke interactive optimization applications.
To let you experience such an application, the :download:`Wonderful WebUI Widgets <model/WonderfulWebUIWidgets.zip>` application provides examples demonstrating how optimization can support end-users, like analysts or planners, in their daily tasks. Each example takes just a few minutes to explore.

The ``Wonderful WebUI Widgets`` application requires AIMMS 4.84 or higher, and it works with the `AIMMS Community Edition <https://licensing.cloud.aimms.com/license/community.htm>`_ license.

Feedback on the ``Wonderful WebUI Widgets`` application can be shared on the `AIMMS Community <https://community.aimms.com/>`_ or via `AIMMS User Support <support@aimms.com>`_.

The following sections provide instructions for each page within the application.

Welcome
-------

The Welcome page introduces the ``Wonderful WebUI Widgets`` application and provides an overview of the available pages. The page includes a navigation workflow on the left to help you move between different examples.

Map
---

**Story**

OxyGem, a German oxygen distillation and bottling company, supplies oxygen tanks for industries like welding, diving, and healthcare. The company operates three main facilities:

#.  |oxygem-distillation| distillation,

#.  |oxygem-pumping| pumping, and 

#.  |oxygem-bottling| bottling.

These facilities have operated efficiently for years, meeting demand under normal circumstances. However, due to the COVID-19 pandemic, demand has increased significantly, occasionally exceeding the network's capacity and resulting in unmet demand. Management seeks advice on optimizing the network to better cater to this increased demand.

.. |oxygem-distillation| image:: images/oxygem-distillation.png
.. |oxygem-pumping| image:: images/oxygem-pumping.png
.. |oxygem-bottling| image:: images/oxygem-bottling.png

**Operating the Map Widget**

The map widget presents a network of connections with nodes representing OxyGem facilities. Node size reflects production capacity or demand at each location. Key tools include:

- **Widget Actions**: Found in the hamburger menu in the upper-right corner:

  - *Initialize*: Resets the network and randomizes demand.
  - *Solve*: Optimizes flow across the network.

  .. image:: images/map-widget-menu.png
      :align: center

- **Hovering**: Hovering over nodes displays a tooltip with details about the location, as shown below:

  .. image:: images/map-widget-tooltip.png
      :align: center

  .. tip:: Hovering over bottling locations with an orange background reveals unmet demand information.

- **Control Panel**: Adjust parameters like unmet demand costs, production unit capacity, and pipe capacity via the side panel.

- **Item Menu**: Accessed by left-then-right clicking on a node. Adjust capacities for distillation and pumping locations or demand for bottling locations.

  - The map auto-colors bottling stations with unmet demand in red, and those operating at capacity in orange.

  .. tip:: Start by optimizing flow over the network to identify bottlenecks.

Gantt
-----

Levram operates three production lines—King Kong, Hercules, and Antman. Each line specializes in handling specific order types, as follows:

- *King Kong*: Suited for large orders.
- *Hercules*: Handles orders of medium size.
- *Antman*: Specializes in smaller orders.

Orders must be processed in full without interruptions. Occasionally, production lines must pause for maintenance or inspections, which are scheduled manually.

Due to an upcoming inspection, production lines must halt briefly, and the Gantt page aids in planning this while minimizing disruption.

**Gantt Page Features**

The Gantt Chart page displays tasks and maintenance schedules for each production line. Key features include:

- **Page Actions**: Located in the lower right menu:
  
  - *Pacman*: Invokes the optimization algorithm to schedule tasks.
  - *New*: Opens a dialog to create a new task.

- **Hovering**: Displays tooltips with task details.

  .. image:: images/gantt-widget-tooltip.png
      :align: center

- **Item Menu**: Accessed by left-then-right clicking on a task, enabling options to:

  - Move tasks to the earliest possible position (manual tasks only).
  - Move tasks to their deadline (manual tasks only).
  - Switch scheduling type between manual and automated.
  - Delete tasks.
  - Edit task properties.

  .. image:: images/edit-gantt-menu.png
      :align: center

Pure Combi
----------

This example models a scenario where you need to transport bags of fruit (30 kg total) and a 20 kg display stand to a market. Several scouts offer to help carry the fruit, each with a specific carrying capacity, totaling 30 kg across all scouts. Any unassigned bags will need to be carried by you.

**Page Operations**

The Pure Combi page opens with all bags assigned to you, displaying the scouts' remaining carrying capacity.

  .. image:: images/opening-combi-chart.png
      :align: center

Key functions:

- **Right-click Assignment**: Assign bags to scouts by right-clicking on them in the column chart and selecting the scout's name.

  .. image:: images/combi-right-click-to-assign.png
      :align: center

- **Optimize**: Use the optimizer (from page actions) to optimally assign bags, reducing your load.

  .. image:: images/optimal-assignment-combi-chart.png
      :align: center

  - The optimizer aims to assign all bags to scouts, eliminating the need for you to carry any.

- **Reset**: Click the broom icon in the lower right to reset assignments.

  By assigning specific bags and optimizing, you can explore different solutions. The app may leave you with a load if some scouts can't carry certain bags.

  .. image:: images/combi-widget-joey-grabs.png
      :align: center

Hints and solution checks are available through side panels, as shown below:

  .. image:: images/combi-widget-joey-grabs-optimize.png
      :align: center

The following screenshot shows an alternate starting scenario with more than 10 kg of fruit still assigned to you:

  .. image:: images/bad-starting-combi-widget.png
      :align: center

.. spelling:word-list::

    greyed
