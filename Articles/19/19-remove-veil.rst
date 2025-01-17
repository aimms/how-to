﻿Keep WebUI Active During Solve
===============================

.. meta::
   :description: How to keep the AIMMS WebUI interactive while a solve process is running in the background.
   :keywords: solve, webui, ui, veil

Introduction
------------

While solving decision problems using the AIMMS WebUI and AIMMS Cloud, the browser window is made inactive by default to represent a solve process running in the background. The window is active again when the results from the solve session are available for the user. However, for longer running procedures - the user might want to start a background procedure and continue working on other parts of the application. 

This article explains the required steps to keep the AIMMS WebUI interactive while a solve session is going. 

.. image:: images/Solve.gif
   :align: center

The Flowshop Problem As Running Example
---------------------------------------

We continue with the flowshop problem we used as example in the previous article in this series. 


This is a very good illustrative example for this case because the solving time of this problem depends on the number of machines and the number of jobs. For the default 10 Machines and 7 Jobs, you will have results almost instantaneously. If you change the number of jobs to 15 jobs, it will take much longer to solve the problem which can be seen below. 

Keep WebUI Session Active
-------------------------

We can keep the WebUI session active, or in other words - remove the veil by simply modifying the ``waitForCompletion`` argument in the ``pro::DelegateToServer`` statement. It can either be removed or the value set to 0. The code will look something like the following;

.. code-block:: aimms

   if pro::GetPROEndPoint() then
      if pro::DelegateToServer(  
         waitForCompletion  :  0,
         completionCallback :  'pro::session::LoadResultsCallBack' )  
      then return 1;
      endif ;
   endif ;

This modification in the ``waitForCompletion`` argument reduces the inactive time of the browser window during a solve session drastically. There still could be inactive periods, especially during the initialization of the background procedures. The previously illustrated example will now look as follows. 

.. image:: images/NoVeil.gif
   :align: center

The example AIMMS project can be downloaded from :download:`Flow Shop - No Veil <downloads/4. Flow Shop - No Veil.zip>`

Summary
----------

Perhaps a lot to do about almost nothing; but by simply changing the argument ``waitForCompletion`` to 0, or removing it, from the call to ``pro::DelegateToServer``, the data session no longer waits for the solver session to complete, thus allowing the end user to browse and edit the data while the solver session is executing a long running procedure.

Next reading
---------------

**Oops, I see a problem.**  The results are pushed back without any notification – suddenly data is changed on screen.  How can we give control back to the end-users, such that they can determine themselves when the results are shown? See :doc:`../40/40-data-server-load-results`

 



















