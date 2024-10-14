Inventory Control
==================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Linear Program, Stochastic Program, Multi-Stage, Control-State Variables, Mathematical Derivation
   :description: This example emphasizes on a multi-period inventory control problem with uncertain demand.  

Direct download AIMMS Project :download:`Inventory Control.zip <model/Inventory Control.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Modeling%20Book/Inventory%20Control

This example emphasizes on a multi-period inventory control problem with uncertain demand.  

The volume of production is decided before the actual demand is known at the beginning of each period.  The objective of this example is to minimize overall costs to obtain maximum profit.  This type of problem can be categorized as a multi-stage stochastic optimization model.

The scenario of this inventory control example takes place within a beer company that serves two different kinds of beer (i.e. light and regular).  The company will need to decide on how much beer to bottle during a particular week for each type.  There is a limit on the overall bottling capacity.  The cost to bottle and store each kind of beer is proportional to the amount of beer that is either bottled or stored.  Moreover, there is a minimum amount of storage required at the end of the last period.

The figure below shows the various events and probabilities needed to solve this example. A node in the tree refers to a state of the system. The label associated with each arc is the event description. The fraction associated with each arc is the corresponding event probability. 

Details about this example can be read in Chapter 17 of the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ book. An electronic version of this book is available through the 'Help' menu.

Keywords:
Linear Program, Stochastic Program, Multi-Stage, Control-State Variables, Mathematical Derivation

