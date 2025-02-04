Connect AIMMS with Gurobi
================================
.. meta::
   :keywords:
   :description: How to use AIMMS with your On-Premise Gurobi Installation.

You can connect your on-premise `Gurobi <https://www.gurobi.com/>`_ installation to AIMMS and use Gurobi as the solver for your math programs. 

Gurobi offers different kinds of on-premise licenses:

#. Single machine (named user or unlimited user)
#. Compute server

This article outlines the process to connect AIMMS for each kind of Gurobi license. 

.. note::
   Your AIMMS license must be configured with a `Gurobi Link` component to be able to connect with your Gurobi installation. 
   Check your license configuration in the AIMMS Tools menu: :menuselection:`Tools > License > License Configuration`.

Single Machine
-----------------

#. Locate the ``gurobi.lic`` file (usually, ``C:\\gurobi\\gurobi.lic``)
#. Set the environment variable ``GRB_LICENSE_FILE`` to point to the exact location of this file

   .. image:: images/grb_license.png
      :align: center

   |

#. In the :menuselection:`Solver Configuration` dialog box in AIMMS, click on :menuselection:`Add`` and select the ``dll`` for the appropriate Gurobi version (``libgrb**.dll``)

   .. image:: images/solver_config2.png
      :align: center

Compute Server
------------------

For a Gurobi Compute Server, there are two types of license files: `server and client <https://support.gurobi.com/hc/en-us/articles/19282145783953-What-is-the-difference-between-a-server-and-client-license-file.html>`_.
If you have a `Gurobi compute server license <https://support.gurobi.com/hc/en-us/articles/13390211932689-How-do-I-set-up-and-use-a-Compute-Server-license.html>`_, follow the instructions depending on whether you are using AIMMS on a Gurobi client machine or the server itself. 

Client on a Different Machine
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

#. Create a Gurobi client license by following the instructions on `Gurobi: License File <https://www.gurobi.com/documentation/11.0/remoteservices/client_license_file.html>`_ 
#. Place this file in the folder ``C:\gurobi\``
#. Set the environment variable ``GRB_LICENSE_FILE`` to point to the exact location of this file (see above).
#. Set Gurobi as the default solver in the :menuselection:`Solver Configuration` dialog like in previous steps (see above).

Client is Same as Server Machine
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

If you are running AIMMS on the same machine as Gurobi remote services, you will need to create a separate client license file again as described  on `Gurobi: Creating a Compute Server <https://support.gurobi.com/hc/en-us/articles/13415510571409-How-do-I-create-a-Compute-Server-client-license.html>`_

#. Place this license file in a different location than ``C:\\gurobi\\``, say ``C:\\Users\\Name\\Documents\\gurobi.lic`` 
#. Set the environment variable ``GRB_LICENSE_FILE`` to the file in ``C:\\Users\\Name\\Documents\\``
#. Set Gurobi as the default solver in the :menuselection:`Solver Configuration` dialog like in previous steps (see above).


AIMMS PRO On-Premise with Gurobi 
----------------------------------

The instructions remain the same for AIMMS Developer and AIMMS PRO On-Premise. Depending on whether AIMMS PRO On-Premise is a client to Gurobi server or they are on the same machine, create the appropriate license files and set the environment variables. 

You will need to set Gurobi as the solver to use before creating the *aimmspack* file and publish this with the appropriate license profile.

.. seealso::

   * :doc:`/Articles/178/178-change-default-solver`
   * :doc:`/Articles/208/208-setting-options`
   * `Gurobi Support on AIMMS Cloud <https://documentation.aimms.com/cloud/gurobi-support.html>`_
