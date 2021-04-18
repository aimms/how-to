Energy Modelling
=================

..  **********************************
	Comment: The filename may need to be changed to reflect what is discussed here

	Power system optimisation: Unit Commitment
	--------------------------------------------

	In this example, we will see how the problem of Unit Commitment (UC) in a power system can be implemented and solved in AIMMS. In the following, we will first review the UC problem, and then we will see its implementation in AIMMS.

	.. note:: For more information about the classic UC problem see {}.

	UC problem
	--------------------------
	**Introduction to UC problem**x: in a power system it is necessary to have enough generation at each instant to handle to system demand, while minimising the cost. The classic UC problem considers three cost components: fuel cost, startup cost and shutdown cost, as shown in equation

	.. math::
		min F=\Sigma C_{i,t}+C_{st(i,t)}+C_{sd(i,t)}\\
		s.t. 	\Sigma P_{i,t}=P_{D,t}
	
	***************************
	