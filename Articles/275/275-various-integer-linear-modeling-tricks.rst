Various (Integer) Linear Modeling Tricks
===========================================

.. meta::
   :description: Various resources about integer and linear modeling tricks for efficiency.
   :keywords: resources, integer, linear, modeling, efficiency


Modeling problems with an (integer) linear program sometimes requires some experience to recognize certain structures 
in the problem description that can be formulated in a linear way. 

At the Naval Postgraduate School, Gerald Brown and Robert Dell developed a list of formulettes, and made these 
available Formulating Integer Linear Programs: A Rogues Gallery from ``pubsonline.informs.org/doi/pdf/10.1287/ited.7.2.153`` .
This article demystifies the art of formulating linear and integer linear programs. 
This is achieved by introducing formulettes, which consist of a verbal description and 
the constraints and variables that model this verbal description.

The first simple example of a formulette they provide is the following:

.. code-block:: none  
  
    For each unit of :math:`X_1`, there must be at least 5 units of :math:`X_2`

which can be modeled with

.. math:: 5X_1 \le X_2

The document contains a lot more of such formulettes of varying complexity.

Furthermore, in our book :download:`Optimization Modeling <https://documentation.aimms.com/_downloads/AIMMS_modeling.pdf>` 
you can also find various modeling tricks in the chapters 6 ``Linear Programming Tricks`` and 7 ``Integer Programming Tricks``. 
The Optimization Modeling book can either be found online via the link above, or you can find it under the Help menu after you started AIMMS.


.. spelling:word-list::

    formulettes
    formulette