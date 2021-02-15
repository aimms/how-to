Optimal Power Flow (OPF)
=============================

In this example, we will see how the problem of Optimal Power Flow (OPF) in a power system can be implemented and solved in AIMMS. In the following, we will first review the OPF problem, and then we will see its implementation in AIMMS.

.. note:: For the simpler problem of Economic Dispatch see {}.

.. note:: For more information about the OPF problem modelling and data format used here, see MATPOWER's documentation `here <https://matpower.org/docs/MATPOWER-manual-7.1.pdf>`_.

OPF problem
--------------------------
OPF is one of the most important problems to be solved in power systems. Essentially, the goal in OPF is to minimise an objective function (normally the total generation cost) while considering constraints of the power system. In the following, first the OPF formulation is explained, and then its implementation in AIMMS is described.

OPF Formulation
--------------------

OPF is an optimisation problem. The objective function, as mentioned above, is normally the total cost of generation, and can be defined as below:


.. math::
	f(x)= \displaystyle\sum_{i=1}^{ng} f^{\{ i\}}_P (P^{\{ i\}}_g) \label{objective} \tag{1} 

where :math:`x` is the vector of the optimisation variables, which are bus powers and voltages:

.. math::

	x={[P_g, Q_g, V_a, V_m]}^T \label{x} \tag{2} 
	
There are of course some constraints in this optimisation problem: active and reactive power balance equations for all buses, limits on transmission line, and also upper and lower limits for the vector of state variables x. These constraints are shown in equations below respectively:

.. math:: 

	S_{bus}(x)+S_d-S_g=0  \label{PowerBalance} \tag{3}
	
where

.. math:: 

	\begin{align}
		S_{bus}(X)=[V]{I_{bus}}^*=[V]{{Y}_{bus}}^*V^*  \label{con_s} \tag{4} \\
		({P_f}^{\{ i\}}(x))^2+{\left({Q_f}^{\lbrace i\rbrace}(x)\right)}^2 \leq {\left({S}^{\lbrace i\rbrace}_{{L}}\right)}^2 \label{con_Pf} \tag{5}	\\
		{\left({P_t}^{\lbrace i\rbrace}(x)\right)}^2+{\left({Q_t}^{\lbrace i\rbrace}(x)\right)}^2 \leq {\left({S}^{\lbrace i\rbrace}_{{L}}\right)}^2 \label{con_Pt} \tag{6} 		
	\end{align}
	

The optimisation problem can therefore be written as below:

.. math::
	\begin{align}
		\text{minimize } f(x)	\\
		\text{subject to } (\ref{con_s})-(\ref{con_Pt})
	\end{align}
	
	
OPF Implementation in AIMMS
-------------------------------	
OPF implementation for the IEEE 14-bus system can be downloaded from :download:`here <opf.zip>`.

The project consists of different parts, as explained below.

**1- Sets:** we have buses, generators and branches in our power system. These are defined and shown using sets. For example, the set of all buses is shown in the figure below.

.. image:: figures/set1.png
    :align: center
	
**2- Case data:** all information about the system can be added here. For example, for adding :math:`r_s` values of branch data, a new parameter rs is added and the index domain is defined as ``l`` which is previously defined as the index for lines (in Sets). Figure below shows has this can be done:

.. image:: figures/rs.png
    :align: center


.. |current_data_icon| image:: figures/current_data.png
	
Now if you click on "Current data" (|current_data_icon|), the :math:`r_s` values can be easily added as shown below. Note that because we have defined the domain for :math:`r_s` as ``l``, therefore AIMMS automatically asks for rs values for all branches in our system.


 .. image:: figures/rs_data.png
    :align: center
