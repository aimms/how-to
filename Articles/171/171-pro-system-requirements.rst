Size AIMMS PRO Server Hardware
==================================================

.. meta::
   :description: Estimating equipment size for AIMMS PRO Server
   :keywords: AIMMS PRO, server, memory, requirements

`The AIMMS PRO documentation <https://documentation.aimms.com/pro/system-requirements.html>`_ details the minimum system requirements for client-side and server-side machines used for AIMMS PRO. However, the memory and CPU requirements of the back-end server where all the solve procedures are executed is heavily dependent on the size of your AIMMS model(s).

This article will help you determine the system requirements of the server-side machine.

.. note::

   For WinUI applications, the data session runs on the end user's computer and so, you do not need to consider the computational resources required by the data session when sizing your server equipment. However, be sure to consider the requirements of websockets which are utilized by WinUI applications. 

Key Components
------------------

The key components to consider for a WebUI application are 

#. **Peak memory usage of the data session:**
    Can be monitored using the Windows Task Manager or the AIMMS Progress Window (the `Memory Used` metric)

#. **Peak memory usage of the solver session:**
    Can be monitored using AIMMS Progress Window (the `Peak Mem` metric)

#. **Average CPU usage of a data session:**
    Can be monitored using the Windows Task Manager

#. **Average CPU usage of the solver session:**
    Can be monitored using the Windows Task Manager

#. **Maximum number of concurrent data sessions**
#. **Maximum number of concurrent solver sessions** 

The memory and CPU usage of a single data session multiplied by the maximum number of data sessions gives the peak resource requirement by the data sessions. Likewise for the solver sessions. In addition to the data and solver sessions, the AIMMS PRO Server installation has its own requirements of 3 GB memory and 1 core of CPU. 

.. math::

    memory\_required(GB) \geq peak\_memory\_data\_session * number\_data\_sessions \\ + peak\_memory\_solver\_session * number\_solver\_sessions + 3 \\
    
.. math::

    cores\_required \geq avg\_cpu\_usage\_data\_session * number\_data\_sessions \\ + avg\_cpu\_usage\_solver\_session * number\_solver\_sessions + 1

Example
-------------

Let us consider a typical application for 20 concurrent data sessions and 2 concurrent solver sessions with metrics as below: 

.. code-block:: none

    peak_memory_data_session = 0.5 GB
    peak_memory_solver_session = 2 GB
    avg_cpu_usage_data_session = 0.2 core
    avg_cpu_usage_solver_session = 1 core
    max_number_data_sessions = 20
    max_number_of_solver_sessions = 2

Substituting these numbers in the above equations, we get: 

.. math:: 

    memory\_required \geq 0.5 GB * 20 + 2 GB * 2 + 3 = 17 GB
    
.. math:: 

    cores\_required  \geq 0.2 * 20 + 1 * 2 + 1 = 7

Summary
-----------

Based on the calculations, a server machine for the above example use case will need at least 17 GB memory and 7 cores. You have to remember that this is one possible configuration and not necessarily the best one. Having fewer concurrent solver sessions than concurrent data sessions will result in a waiting time for some of the users. Depending on the runtime of the solve procedure and the acceptable waiting times for your users, choose the number of concurrent solver sessions that you want to purchase. The server size will also increase with each additional solver session added to the configuration. 

.. seealso::
    
    * `Queueing on AIMMS PRO <https://documentation.aimms.com/pro/config-sections.html#queue-priority-settings>`_

.. todo:: 

   Discuss effect of parallel threading on solve runs and the additional memory / cores required to do this ? Perhaps in a separate article ?

   https://www-01.ibm.com/support/docview.wss?uid=swg21653811
   



