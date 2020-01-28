Data for Optimization Libaries
==============================

An AIMMS library can be reused in other projects. To solve an optimisation problem in a library we're dealing with two abstraction mechanisms:

* On the one hand, procedures are used in which the sets transferred via the arguments have different meanings.
* On the other hand, we can declare variables and constraints as globals with a fixed index domain within the library and by working with subsets of AllConstraints and AllVariables locally within that library.

These two abstraction mechanisms do not work together naturally.
This article explains how we can bring these two abstraction mechanisms together using element parameters. The project example (Transport Problem Library) is based on the well-known **Transport Problem**. 

The Transport Problem searches for the best way to transport goods from a couple of sources to sinks. 

.. image:: images/TransportProblem.png
   :scale: 50 %


Transferring data
-----------------
A Transport Problem can be solved locally inside the library. The following **input data** is declared in the main model: ``s_Sources``, ``s_Sinks``, ``p_Supply``, ``p_Demand`` and ``p_TransportCost``.  This data must be transferred into the library in order for the Transport Problem to be solved. Then, the following **output data** must be transferred back to the main model: ``p_TotalTransportCost`` and ``p_Transport``. 

Tranferring the data is done by the procedure ``pr_SolveTransportProblem`` and happens in two steps (see image):
 
1. Data from ``Main Transport`` must be provided to the procedure ``pr_SolveTransportProblem`` inside the library.
2. Data must be transferrerd from ``pr_SolveTransportProblem`` to the global ``Declaration`` of the ``Transport Library``, where the Transport Problem can be solved.

.. image:: images/data.png
   :scale: 50 %




Step 1
^^^^^^

Firstly, the procedure ``pr_SolveTransportProblem`` should contain local arguments. They can be added by pressing the wizard next to arguments. It is important to select the correct type and property of the arguments. 
(also something about index)
 See image:

.. image:: images/Arguments.png
	:align: center
	:scale: 60%

Now it is possible to call the procedure from the main model. If you provide the input and output arguments in the right order, they will be linked to the local arguments. The procedure can now be called as follows:
	
.. code-block:: aimms

	pl::pr_SolveTransportProblem(s_Sources, s_Sinks, p_Supply, p_Demand, p_TransportCost, 
	p_TotalTransportCost, p_Transport);



Step 2
^^^^^^
The next step is to correctly transfer the data to the global declaration of the library. 

In the main model there is a parameter (``s_Supply``) that describes the supply of the sources (``s_Sources``). When transferring the data, the supplies must be linked to the correct sources. This is done with element mapping, see the following code:


.. code-block:: aimms
   :linenos:

	For i_locSo do
		SetElementAdd(s_libSources, ep_new1, i_locSo);
		ep_map1(ep_new1) := i_locSo;
	EndFor;
	p_libSupply(i_libSo) := p_locSupply( ep_map1(i_libSo));


- line 1: ``i_locSo`` is the index of ``s_locSources``.
- line 2: Add the elements to ``s_libSources``.
- line 3: Map the local sources set the library sources set.
- line 5: Link supplies to the right sources.



The rest of the input data is transferred in a similar way. Then the Transport Problem is calculated and the output data is transferred back like this:

.. code-block:: aimms

	p_locTotalTransportCost := v_libTotalTransportCost;
	p_locTransport(ep_map1(i_libSo), ep_map2(i_libSi)) := 
		v_libTransport(i_libSo,i_libSi);











