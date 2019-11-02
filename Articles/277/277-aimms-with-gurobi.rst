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

Gurobi's compute server offering lets you designate one (or more) remote server machines as the workers to the client machines (on which AIMMS is running). All client machines need Gurobi Optimizer installed on them and a Gurobi client license file. 

.. again, not sure if we need to explain someone else's product. Could replace this with - If you have a Gurobi compute server license, the instructions depend on whether you are using AIMMS on a Gurobi client machine or the server itself. 

Client on a different machine
"""""""""""""""""""""""""""""""

#. Create a Gurobi client license by following these `instructions on Gurobi's website <https://www.gurobi.com/documentation/8.1/remoteservices/license_file.html>`_ 
#. Place this file in the folder `C:\\gurobi\\`

The next steps are same as for a single machine license described in the previous section. 

Client is same as server machine
"""""""""""""""""""""""""""""""""""

If you are running AIMMS on the same machine as Gurobi remote services, you will need to create a separate client license file again as described in `this link on Gurobi's website <https://www.gurobi.com/documentation/8.1/quickstart_windows/creating_a_compute_server_.html>`_

#. Place this license file in a different location than `C:\\gurobi\\`, say `C:\\Users\\Name\\Documents\\gurobi.lic` 
#. Set the environment variable GRB_LICENSE_FILE to the file in `C:\\Users\\Name\\Documents\\`

Set Gurobi as the default solver in the Solver Configuration dialog like in previous steps. 


AIMMS PRO with Gurobi 
----------------------------------

The instructions remain the same for AIMMS Developer and AIMMS PRO. Depending on whether AIMMS PRO server is a client to Gurobi server or they are on the same machine, create the appropriate license files and set the environment variables. 

You will need to set Gurobi as the solver to use before creating the AIMMSPACK file and publish this with the appropriate license profile.



