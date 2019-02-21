:orphan:

How to size your hardware for AIMMS PRO Server
==================================================

`The AIMMS PRO documentation <https://manual.aimms.com/pro/system-requirements.html>`_ details the minimum system requirements for client-side and server-side machines used for AIMMS PRO. However, the memory and CPU requirements of the back-end server where all the solve procedures are executed is heavily dependent on the size of your AIMMS model(s). This article will help you determine the system requirements of the server-side machine.

.. note::

   For WinUI applications, the data session runs on the end user's computer and so, you do not need to consider the computational resources required by the data session when sizing your server equipment. However, be sure to consider the requirements of websockets which are utilized by WinUI applications. 

Key Components
------------------

The key components to consider for a WebUI application are 

#. Peak memory usage of the data session
      * Can be monitored using the Windows Task Manager or the AIMMS Progress Window (the Memory Used metric)

#. Peak memory usage of the solver session
      * Can be monitored using AIMMS Progress Window (the Peak Mem metric)

#. Average CPU usage of a data session
      * Can be monitored using the Windows Task Manager

#. Average CPU usage of the solver session
      * Can be monitored using the Windows Task Manager

#. Maximum number of concurrent data sessions
#. Maximum number of concurrent solver sessions 

The memory and CPU usage of a single data session multiplied by the maximum number of data sessions gives the peak resource requirement by the data sessions. Likewise for the solver sessions. In addition to the data and solver sessions, the AIMMS PRO Server installation has its own requirements of 3 GB memory and 1 core of CPU. 

.. todo::

   Insert equation:

   Memory required >= Peak memory(data session)*max number of data sessions + Peak memory(solver session)*max number of solver sessions + 3 GB for PRO Server 

   CPU cores required >= Average CPU usage(data session)*max number of data sessions + Average CPU usage(solver session)*max number of solver sessions + 1 core for PRO Server

Example
-------------

A typical data session's peak memory usage is usually upwards of 200 mb and a typical solver session can consumer up to a few gb of memory. To let 40 




