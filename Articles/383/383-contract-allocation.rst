Contract Allocation
=========================
.. meta::
   :keywords: Semi-continuous variables, Mixed Integer Programming model, MIP, combinationchart, table, colors, css
   :description: This AIMMS project illustrates the use of a semi-continuous variable.

.. image:: https://img.shields.io/badge/AIMMS_4.85-ZIP:_Contract_Alocation-blue
   :target: :download:`Contract Allocation.zip <model/Contract Allocation.zip>`

.. image:: https://img.shields.io/badge/AIMMS_4.85-Github:_Contract_Alocation-blue
   :target: :download:`Contract Allocation.zip <model/Contract Allocation.zip>`

.. image:: https://img.shields.io/badge/UI-WebUI-sucess


Story
-----

In this model we have a set of contracts, where every contract represents an amount of commodity that has to be supplied. The objective is to determine which of the producers will take care of which contract such that the total costs are minimal, under the following conditions:


- The demand for every contract is met.

- The amount supplied by each producer does not exceed the total amount available for supply.

- If a producer supplies a part of a contract then this contribution has a given minimal size.

- There is a minimal number of suppliers for every contract. 

- The total cost associated with all the deliveries is minimal.


Mathematical Model
------------------

This AIMMS project illustrates the use of a semi-continuous variable. A semi-continuous variable is either zero or within a certain range. This type of variables can be used in conditions like, whenever there is a transport this transport has a minimum size. 

+-----+------------------------------------------------------+-------------------------------------------+
|       Contract Allocation Problem                                                                      |
+=====+======================================================+===========================================+
+ **Sets and indices:**                                                                                  |
+-----+------------------------------------------------------+-------------------------------------------+
+     | :math:`P`, :math:`p \in P`                           | Producers                                 |
+-----+------------------------------------------------------+-------------------------------------------+
+     | :math:`C`, :math:`c \in C`                           | Contracts                                 |
+-----+------------------------------------------------------+-------------------------------------------+
| **Parameters:**                                                                                        |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`M_{p} \in \mathbb{R_{+}}`                     | minimal delivery                          |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`A_{p} \in \mathbb{R_{+}}`                     | available capacity                        |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`S_{c} \in \mathbb{R_{+}}`                     | contract size                             |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`N_{c} \in \mathbb{R_{+}}`                     | minimal number of contributors            |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`T_{p,c} \in \mathbb{R_{+}}`                   | delivery cost by p for c                  |
+-----+------------------------------------------------------+-------------------------------------------+
| **Variables:**                                                                                         |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`X_{p,c} \in \{M_{p}..10000\}`                 | amount of comodity delivered by p to c    |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`Y_{p,c} \in \{o..1\}`                         | p produce to c                            |
+-----+------------------------------------------------------+-------------------------------------------+
| **Constraints:**                                                                                       |
+-----+------------------------------------------------------+-------------------------------------------+
|  1  | :math:`\forall p: \sum_c X_{p,c} \leq A_{p}`         | production capacity for p                 |
+-----+------------------------------------------------------+-------------------------------------------+
|  2  | :math:`\forall c: \sum_p X_{p,c} \geq S_{c}`         | demand fulfillment for c                  |
+-----+------------------------------------------------------+-------------------------------------------+
|  3  | :math:`\forall c: \sum_p X_{p,c} \geq N_{c}`         | minimal number of contributors to c       |
+-----+------------------------------------------------------+-------------------------------------------+
|  4  | :math:`\forall p, c: X_{p,c} \geq M_{p} * Y_{p,c}`   | if p delivers to c                        |
+-----+------------------------------------------------------+-------------------------------------------+
| **Minimize:**                                                                                          |
+-----+------------------------------------------------------+-------------------------------------------+
|     | :math:`\sum_{p,c} T_{p,c} * X_{p,c}`                 | The number of matches                     |
+-----+------------------------------------------------------+-------------------------------------------+

Language 
--------

In this example we used 10 northwestern states for the contracts and 5 cities from that region for the producers. To import the data into our model, we are currently using DEX library through Excel (``NothWesternStates.xlsx``). 
You can add more data freely without changing the sheets structure.  

.. aimms:procedure:: pr_importExcelData

This procedure will add and read the ``xml`` mapping available. Take a look at ``Mapping/Inputs.xml``.

.. code-block:: aimms
   :linenos:

   dex::AddMapping(
      mappingName :  "inputs", 
      mappingFile :  "Mappings/inputs.xml");

   dex::ReadFromFile(
      dataFile         :  "NothWesternStates.xlsx", 
      mappingName      :  "inputs", 
      emptyIdentifiers :  1, 
      emptySets        :  1, 
      resetCounters    :  1);

   ep_actualContract := first(i_contract);
   ep_actualProducer := first(i_producer);

.. seealso::
   To understand in depth check out DEX documentation.

WebUI Features
--------------

On input page, 
The results are displayed in a combination chart (stacked bar chart).

The following WebUI features are used:

- Text Widget

- Image Widget

- Workflow

- Table Widget

- Button Widget

- Side Panel

- Compact Scalar Widget

- Combination Chart Widget

- List Widget



UI Styling
----------
For this project, we used a main css file named ``colors.css``, this . 

buttons.css
^^^^^^^^^^^

.. code-block:: css
   :linenos:

   .theme-aimms .aimms-widget .ui-button {
         background-color: var(--primary);
   }

buttons.css
^^^^^^^^^^^

.. code-block:: css
   :linenos:

   /*This changes the background-color of the button widget*/
   .theme-aimms .aimms-widget .ui-button {
         background-color: var(--primary);
   }
