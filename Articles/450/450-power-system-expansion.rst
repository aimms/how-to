Power System Expansion
=======================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Linear Program, Stochastic Program, Two-Stage, Control-State Variables, What-If Analysis, Benders Decomposition
   :description: This example implements a power system expansion model with uncertain electricity demand, covering a single time period. 

Direct download AIMMS Project :download:`Power System Expansion.zip <model/Power System Expansion.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Modeling%20Book/Power%20System%20Expansion

This example implements a power system expansion model with uncertain electricity demand, covering a single time period. The problem is to determine new power plant design capacities in order to meet an increase in electricity demand. Yet, the future demand is uncertain and it is modeled by means of several demand scenarios. 

This example illustrates the AIMMS support for handling uncertainty in input data through such methods as scenario analysis and stochastic programming. 

The scenario analysis approach starts with a deterministic model and examines its sensitivity to changes in the values of uncertain parameters. This is also referred to as what-if analysis and is essentially a manual technique for dealing with uncertainty. 

The stochastic programming approach captures the input data associated with an entire what-if analysis into a single model formulation. It looks for a capacity design that optimizes the overall expected costs taking all scenarios into account (each scenario with a certain weight). 

The stochastic model can be either:

- built explicitly as a separate symbolic model within the AIMMS project, or
- automatically generated starting from the deterministic formulation. 

The second approach is based on the special stochastic programming features offered by AIMMS through the GMP library. 

Both modeling approaches are illustrated in this project.

Details about this example can be read in Chapter 16 of the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ book.

Keywords:
Linear Program, Stochastic Program, Two-Stage, Control-State Variables, What-If Analysis, Benders Decomposition



