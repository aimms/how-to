Power System Expansion RO
============================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Linear Program, Uncertainty, Robust Optimization, Non-adjustable and Adjustable Decisions, Linear Decision Rules
   :description: This example implements a power system expansion model with uncertain electricity demand, covering a single time period.


Direct download AIMMS Project :download:`Power System Expansion RO.zip <model/Power System Expansion RO.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Power%20System%20Expansion%20RO

This example implements a power system expansion model with uncertain electricity demand, covering a single time period. 
The problem is to determine new power plant design capacities in order to meet an increase in electricity demand. 
Yet, the future demand is uncertain. Initially, the uncertainty in the instantaneous demand is modeled by means of several demand scenarios. 

This example illustrates the AIMMS support for handling uncertainty in input data using Robust Optimization techniques, as discussed in the AIMMS Language Reference.

In a Robust Optimization approach certain constraints are required to hold for every realization of the data within a given uncertainty set.
In this example three choices for the uncertainty set corresponding to the instantaneous power demand are illustrated:

i) the (smallest) box (2-dimensional interval) that contains all the initial scenario values

ii) the (largest) ellipsoid contained in the box from case i)

iii) the convex hull of the scenario values (that is, the smallest polyhedron containing all scenarios)

These sets are modeled by means of the Region (and Dependency) attribute of the corresponding uncertain parameters.

The same effect can be obtained by declaring explicit uncertainty constraints as those provided in the "Auxiliary Uncertainty" declaration section, for illustration, and including the necessary uncertainty constraints in the set of uncertainty constraints to be considered for the generation of the robust model.
 
There is a distinction made between non-adjustable decisions (that is, the design of new capacity) and the adjustable decisions (that is, the capacity allocation and the electricity import decisions). The adjustable decisions are assumed to follow linear decision rules depending on the values of the required electricity parameter.

The approach illustrated here is based on the special Robust Optimization features offered by AIMMS through the GMP library. 

Details about the underlying deterministic model in this example can be read in Chapter 16 of the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ book.

Keywords:
Linear Program, Uncertainty, Robust Optimization, Non-adjustable and Adjustable Decisions, Linear Decision Rules



