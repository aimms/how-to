Power Systems Planning - Methods and Applications
======================================================

In this document a brief overview of methods and applications of planning in power systems is outlined. Normally, power system planning is required for continuous safe and reliable operation of electricity grids as well as energy networks. In electric power systems, depending on the planning timescale, we can have both long-term planning problems concerning investment in infrastructure and procurement of additional capacity, and short-term planning problems concerning the optimisation of the operation of already existing infrastructure and assets within an electricity network. In this section we only consider short-term planning problems which come in two forms depending on their timescale and required outcome: 

#. The *Unit Commitment* (UC) Problem: which concerns with finding an optimum set of committed generators in the system to maintain the system operational security by ensuring enough generation capacity is available to meet demand within a pre-specified timescale. In mathematical terms, the UC problem is essentially a non-linear mixed integer optimisation problem but normally we solve this as a mixed integer linear programme by ignoring voltage constraints and linearising the objective function. 

#. The *Optimal Power Flow* (OPF) Problem: which concerns with finding an optimum generation schedule of committed generators in the system within a pre-specified timescale. The main difference between the OPF and UC problem is that the OPF only concerns with identifying and optimising (for cost) the generation schedule of already committed generators whereas the UC problem concerns with identifying the committed generators in the first place. Similar to the UC problem, the OPF at its core is a non-linear optimisation problem but we can solve the OPF again by ignoring voltage constraints as a linear programme. There may or may not be integer variables in an OPF problem but that mostly depends on the nature of the problem. 

Normally we say that the *UC* and *OPF* problems belong to the class of *Short Term Optimum Operations Planning* problems in power systems planning jargon.

A graphical representation of the various timescales of different power systems planning problems is shown below:

Figure of the timescale

.. image::

Unit Commitment
----------------

In this section, we give a standard formulation for the Unit Commitment problem as a mixed integer linear programme. 

.. note:: You can use this formulation to formulate and solve your own example in AIMMS. We will publish an accompanying simple example based on this formulation soon. 

Objective Function
^^^^^^^^^^^^^^^^^^^^^^

As stated above, the aim of the UC problem is to ensure there is enough generation *committed* at each point in time within a set planning timescale. The objective function for the UC problem is defined in its most general form as below:

	.. math::
		\begin{align} 
			\min_{\Phi} \sum_{t \in T}\sum_{g \in G} (C_{g}^{SU}(u_{g,t})+C_{g}^{SD}(u_{g,t})+f_g(P_{g,t})+C_{g}^{0}(u_{g,t})+\delta_{g}^{+}(P_{g,t})+				\delta_{g}^{-}(P_{g,t})) \label{UC_objective1} \tag{1}
		\end{align}

In equation (\ref{UC_objective1}) the following are defined:

* :math:`\Phi=(P_{g,t},u_{g,t},V_{a}^{n})` is the set of all state variables in which :math:`P_{g,t}` is the active power output of generator *g* at time *t*, :math:`u_{g,t}` is the 	binary decision variable for starting up or shutting down of generator *g* at time *t*, and :math:`V_{a}^{n}` is the voltage angle for node *n*
* :math:`G` and :math: `T` are the sets of all generators and time steps respectively. 
* :math:`C_{g}^{SU}` and :math:`C_{g}^{SD}` are the cost functions for starting up or shutting down for generator unit *g*
* :math:`f_g` is the fuel cost function for generator unit *g*   
* :math:`C_{g}^{0}` is the running cost function for standby generators 
* :math:`\delta_{g}^{+}` and :math:`\delta_{g}^{-}` are the ramping up and ramping down cost functions of generator unit *g*
		
Constraints
^^^^^^^^^^^^^^^^^^

Ignoring voltage magnitude constraints we can formulate the constraints as such:


Optimal Power Flow
--------------------

In this section, we give a standard formulation for the Optimal Power Flow problem as a nonlinear programming problem. 

.. note:: You can use this formulation to formulate and solve your own example in AIMMS. A simple example for solving an OPF for a small system can be seen in (give reference to the OPF page).

Objective Function
^^^^^^^^^^^^^^^^^^^^^^


Constraints
^^^^^^^^^^^^^^^^^^

	
	***************************
	