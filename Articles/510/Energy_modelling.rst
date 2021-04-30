Power Systems Planning - Methods and Applications
======================================================

In this document a brief overview of methods and applications of planning in power systems is outlined. Normally, power system planning is required for continuous safe and reliable operation of electricity grids as well as energy networks. In electric power systems, depending on the planning timescale, we can have both long-term planning problems concerning investment in infrastructure and procurement of additional capacity, and short-term planning problems concerning the optimisation of the operation of already existing infrastructure and assets within an electricity network. In this section we only consider short-term planning problems which come in two forms depending on their timescale and required outcome: 

#. The *Unit Commitment* (UC) Problem: which concerns with finding an optimum set of committed generators in the system to maintain the system operational security by ensuring enough generation capacity is available to meet demand within a pre-specified timescale. In mathematical terms, the UC problem is essentially a non-linear mixed integer optimisation problem but normally we solve this as a mixed integer linear programme by ignoring voltage constraints and linearising the objective function. 


#. The *Optimal Power Flow* (OPF) Problem: which concerns with finding an optimum generation schedule of committed generators in the system within a pre-specified timescale. The main difference between the OPF and UC problem is that the OPF only concerns with identifying and optimising (for cost) the generation schedule of already committed generators whereas the UC problem concerns with identifying the committed generators in the first place. Similar to the UC problem, the OPF at its core is a non-linear optimisation problem but we can solve the OPF again by ignoring voltage constraints as a linear programme. There may or may not be integer variables in an OPF problem but that mostly depends on the nature of the problem. 

Normally we say that the *UC* and *OPF* problems belong to the class of *Short Term Optimum Operations Planning* problems in power systems planning jargon.

A graphical representation of the various timescales of different power systems planning problems is shown below:

.. _psp-timeline:

.. figure:: figures/system_planning_timeline.png 
   :align:  center
   
   Figure 1 - Timelines for various Power System Planning Problems

.. note:: For a more expansive formulation of the power system optimum operations planning problems we suggest reviewing the MATPOWER - MOST User's Manual [1]


Unit Commitment
----------------

In this section, we give a standard formulation for the Unit Commitment problem. We normally solve this problem as a mixed integer linear programme. However it should be noted that there are nonlinearities in the objective function in form of the binary decision variables. 

.. note:: You can use this formulation to formulate and solve your own example in AIMMS. We will publish an accompanying simple example based on this formulation soon. 


Objective Function
^^^^^^^^^^^^^^^^^^^^^^

As stated above, the aim of the UC problem is to ensure there is enough generation *committed* at each point in time within a set planning timescale. The objective function for the UC problem is defined in its most general form as below:

	.. math::
		\min_{\Phi} \sum_{t \in T}\sum_{g \in G} (C_{g}^{SU}(u_{g}^{t})+C_{g}^{SD}(u_{g}^{t})+f_g(P_{g}^{t})+C_{g}^{0}(u_{g}^{t})+\delta_{g}            		^{+}(P_{g}^{t})+\delta_{g}^{-}(P_{g}^{t}))  
		:label: UC_objective1 
		

In equation :eq:`UC_objective1` the following are defined:

* :math:`\Phi=(P_{g,t},u_{g,t},V_{a}^{n})^T` is the set of all state variables in which :math:`P_{g,t}` is the active power output of generator *g* at time *t*, :math:`u_{g,t}` is the 	binary decision variable for starting up or shutting down of generator *g* at time *t*, and :math:`V_{a}^{n}` is the voltage angle for node *n*
* :math:`G` and :math:`T` are the sets of all generators and time steps respectively. 
* :math:`C_{g}^{SU}` and :math:`C_{g}^{SD}` are the cost functions for starting up or shutting down for generator unit *g*
* :math:`f_g` is the fuel cost function for generator unit *g*   
* :math:`C_{g}^{0}` is the running cost function for standby generators 
* :math:`\delta_{g}^{+}` and :math:`\delta_{g}^{-}` are the ramping up and ramping down cost functions of generator unit *g*
		
Constraints
^^^^^^^^^^^^^^^^^^

The following constraints apply for a DC formulation of the UC problem:

.. math::
	u_{g}^{t}P_{g}^{\min} \leq P_{g}^{t} \leq u_{g}^{t}P_{g}^{\max} 
        :label: UC_gen_const
.. math::        
        P_{g,n}^{t} - P_{d,n}^{t} + \sum_{n \in N} B_{in}(\theta_{i}^{t} - \theta_{n}^{t}) = 0 
	:label: UC_power_balance 
.. math::	
	B_{in}(\theta_{i}^{t} - \theta_{n}^{t}) \leq L_{in}^{\max} 
	:label: UC_line_limit
.. math::	
	\delta_{g}^{\min} \leq P_{g}^{t} - P_{g}^{t-1} \leq \delta_{g}^{\max}
	:label: UC_ramp_limit

The following are defined:

* Equation :eq:`UC_gen_const` is the maximum and minimum allowable active power limit for each generator. 
* Equation :eq:`UC_power_balance` is the Active nodal power balance for each node in the system. Set :math:`N` is defined as the set of all the nodes in the system.
* :math:`P_{d,n}`is the demand at node :math:`n`, whereas :math:`P_{g,n}` is the total generation at node :math:`n`. 
* :math:`B_{jk}` is the susceptance of the transmission line connecting nodes :math:`j` and :math:`k`.
* Equation :eq:`UC_line_limit` is the maximum allowable thermal (capacity) limit for each line in the system in units of MW. :math:`L_{jk}` is the active power transmission limit for the line connecting nodes :math:`j` and :math:`k`. 
* All transmission line elements (susceptance and line limits) are contained in the Set :math:`L`. 
* Equation :eq:`UC_ramp_limit` is the ramping up and down limits on each generator in units of MW/h. 

.. note:: In equations above only the most fundamental equations are given and some details have been omitted (for example provisions of primary frequency response which is required in some regions, countries.). In addition, if we want to run the UC as a multi-period problem then there should be appropriate inter-temporal constraints between each time-step (i.e. generators' status from the previous timestep must carry over to the next timestep). 
	
Optimal Power Flow
--------------------

In this section, we give a standard formulation for the Optimal Power Flow problem as a nonlinear programming problem. 

.. note:: You can use this formulation to formulate and solve your own example in AIMMS. A simple example for solving an OPF for a small system can be seen in (give reference to the OPF page).

Objective Function
^^^^^^^^^^^^^^^^^^^^^^


Constraints
^^^^^^^^^^^^^^^^^^

References
============

[1] Zimmerman, R. D., and Murillo-Sanchez, C. E., 2020, MATPOWER Optimal Scheduling Tool - MOST 1.1 User's Manual
	
	***************************
	