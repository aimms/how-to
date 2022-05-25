Contract Allocation
=========================
.. meta::
   :keywords: Semi-continuous variables, Mixed Integer Programming model, MIP, bar Chart, table, colors
   :description: This AIMMS project illustrates the use of a semi-continuous variable.

.. image:: https://img.shields.io/badge/AIMMS_4.85-ZIP:_Contract_Alocation-blue
   :target: :download:`Contract Allocation.zip <model/Contract Allocation.zip>`

.. image:: https://img.shields.io/badge/AIMMS_4.85-Github:_Contract_Alocation-blue
   :target: :download:`Contract Allocation.zip <model/Contract Allocation.zip>`

.. image:: https://img.shields.io/badge/UI-WebUI-sucess

In this model we have a set of contracts, where every contract represents an amount of commodity that has to be supplied. The objective is to determine which of the producers will take care of which contract such that the total costs are minimal, under the following conditions:


- The demand for every contract is met.

- The amount supplied by each producer does not exceed the total amount available for supply.

- If a producer supplies a part of a contract then this contribution has a given minimal size.

- There is a minimal number of suppliers for every contract. 

- The total cost associated with all the deliveries is minimal.

In this example we used **10 northwestern states** for the contracts and **5 cities** from that region for the producers.

The results are displayed in a combination chart (stacked bar chart).

This AIMMS project illustrates the use of a semi-continuous variable. A semi-continuous variable is either zero or within a certain range. This type of variables can be used in conditions like, whenever there is a transport this transport has a minimum size. 

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

*Keywords: Semi-continuous variables, Mixed Integer Programming model, MIP, combinationchart, table, colors, css.*


