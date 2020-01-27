Data for Optimization Libaries
==============================

An AIMMS library can be reused in other projects. To solve an optimisation problem in a library we're dealing with two abstraction mechanisms:

* On the one hand, procedures are used in which the sets transferred via the arguments have different meanings.
* On the other hand, we can declare variables and constraints as globals with a fixed index domain within the library and by working with subsets of AllConstraints and AllVariables locally within that library.

These two abstraction mechanisms do not work together naturally.
This article explains how we can bring these two abstraction mechanisms together using element parameters based on the well-known **Transport Problem**. 
-> het voorbeeld is gebaseerd op het transport problem, niet het artikel 

-> as you know:
The Transport Problem searches for the best way to transport goods from a couple of sources to sinks. For more information see (link)



.. image:: images/TransportProblem.png
   :scale: 50 %


Description
-----------

.. image:: images/data.png
   :scale: 50 %

-> the input and output instead of the first two and the first three
The data needed in the library to solve the transport problem is declared in the main model (MainTransport). These are the two sets and the first three parameters (..,..,..,..,..). This data is transferrend into the library so the transport problem can be solved locally and two variables are calculated(..,..). The information from these variables is then transferred back to the main model. 



MainTransport -> Library Procedure 
----------------------------------
The procedure inside the library that transfers the data is called pr_SolveTransportProblem. From the main model it should be possible to call this procedure and provide the input and output arguments. In order to enable this, the procedure must have locally declared arguments to which the data is given. Adding arguments to the procedure can be done by pressing the wizard next to arguments in ``pr_SolveTransportProblem``. It is important to correctly select the type and the property of these arguments (see image):

.. image:: images/Arguments.png
   :scale: 50 %

The procedure can now be called from the main model as followed (make sure the arguments are in correct order):

.. code-block:: aimms

	pl::pr_SolveTransportProblem(s_Sources, s_Sinks, p_Supply, p_Demand, p_TransportCost, p_TotalTransportCost, p_Transport);




Library Procedure -> Global Library declaration
-----------------------------------------------
The locally declared 














