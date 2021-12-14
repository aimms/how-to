MPS Functions
=============

.. meta::
   :keywords: MPS File, Tuning, MIP, Solving Options, Improving Solutions
	:description: This example demonstrates the functions in AIMMS to solve and tune MPS files. 

Direct download AIMMS Project :download:`MPS Functions.zip <model/MPS Functions.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/MPS%20Functions

This example demonstrates the functions in AIMMS to solve and tune MPS files.

The two GMP functions used in this example are:

* :any:`GMP::Tuning::SolveSingleMPS`
* :any:`GMP::Tuning::TuneMultipleMPS`
		
The first procedure can be used to solve a single MPS file and the latter one is to tune one or more MPS files. 

For the detailed explanations regarding the usage of the procedures, please refer to :any:`algorithmic-capabilities/the-gmp-library/gmp_tuning-procedures-and-functions/`
In order to solve a single MPS file, you need to open the page "Solve Single MPS File". On this page you first select an MPS file, set its MIP GAP, and select a solver among CPLEX, GUROBI, CBC and XA. Press the button "Solve Without Tuning" to run the model. 

You can tune one or a set of MPS files. The page "Tune Single MPS File" allows you to first tune a selected MPS file, and then solve it with tuned options. 

On the page "Tune Multiple MPS Files", you can first select a number of MPS files by clicking on the button "Select MPS Files" multiple times, then tune selected models all together. Finally, solve one of the models with tuned options.

.. note::
	
	The procedure :any:`GMP::Tuning::SolveSingleMPS` is supported by CPLEX, GUROBI, CBC and XA. However, the procedure :any:`GMP::Tuning::TuneMultipleMPS` is only supported by CPLEX.

Keywords:
MPS File, Tuning, MIP, Solving Options, Improving Solutions


