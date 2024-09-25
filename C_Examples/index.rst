Examples
===========

If you have any questions about AIMMS in general or about how to get the most out of AIMMS for your problem, you can ask
them on our `community <https://community.aimms.com/>`_.

This section contains several types of AIMMS examples:

* Examples from the AIMMS Modeling Book
* Functional examples demonstrating particular AIMMS features
* AIMMS-style application examples including an end-user UI
* Practical examples, including references to the articles on which each example is based

Each example is stored in a separate folder containing:

* An ``about.txt`` or ``description.txt`` file that briefly describes the model or functionality illustrated in the example
* An ``.aimms`` file which listing the main project and all library folders included in the example
* An ``.ams`` file containing the model source describing the AIMMS model

The examples projects can only be opened in AIMMS 4.0 (or higher) through the ``.aimms`` file. To get an overview of the 
underlying optimization model you can also directly view the ``.ams`` model source file.

All examples are `on github <https://github.com/aimms/examples>`_ and can be downloaded in one :download:`AIMMS examples master file <https://github.com/aimms/examples/archive/master.zip>`. Or, you can clone the entire repository.

You can browse our examples by category:

.. grid:: 3
   :gutter: 5

   .. grid-item-card:: Contract Allocation
      :img-top: ../Articles/383/images/project.gif
      :link: https://how-to.aimms.com/Articles/383/383-contract-allocation.html

      Project assigns producers to contracts to minimize total costs, ensuring demand is met, supply limits aren't exceeded, minimal contributions are respected, and each contract has a minimum number of suppliers.

   .. grid-item-card:: Employee Scheduling
      :img-top: ../Articles/387/images/project.gif
      :link: https://example.com

      Test.

   .. grid-item-card:: Knapsack
      :img-top: ../Articles/390/images/project.png
      :link: https://how-to.aimms.com/Articles/390/390-knapsack-problem.html

      Problem where the goal is to maximize the total value of items placed in the knapsack, subject to a weight limit.

   .. grid-item-card:: Reindeer Pairing
      :img-top: ../Articles/387/images/project.gif
      :link: https://example.com

      Test.

.. toctree::
   :maxdepth: 1
   :hidden:
   
   /Articles/545/545-summary-examples-features
   demo-applications/index
   functional-applications/index
   legacy-applications/index
   
