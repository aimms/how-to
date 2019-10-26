How to Use AIMMS with Gurobi
================================

.. should this be - How to use AIMMS with your On-Premise Gurobi Installation ? 

You can connect your on-premise `Gurobi <https://www.gurobi.com/>`_ installation to AIMMS and use Gurobi as the solver for your math programs. 

Gurobi offers two kinds of on-premise licenses:

#. Single machine (named user or unlimited user)
#. Compute server

.. is this the right way of communicating ? I don't want to describe Gurobi's products, they actually have 3 kinds of licenses single machine named user, single machine unlimtied user and compute server. Our process is the same for the first two, and different for the third one. So, how do we list that out ?

This article outlines the process to connect AIMMS with different types of Gurobi licenses. 

Single machine
-----------------

#. Locate the gurobi.lic file (usually, `C:\\gurobi\\gurobi.lic`)
#. Set the environment variable GRB_LICENSE_FILE to point to the exact location of this file

   .. image:: grb_license.png
      :align: center
#. In the Solver Configuration dialog box in AIMMS, click on Add and select the dll for the appropriate Gurobi version (`libgrb**.dll`)

   .. image:: solver_config.png
      :align: center

Compute server
------------------


