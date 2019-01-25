Develop Multi-Platform Applications
====================================

.. meta::
   :description: Tips for developing flexible AIMMS applications for use on both AIMMS PRO and AIMMS IDE.
   :keywords: platform, pro


After publishing an app on the AIMMS PRO server, it is common to continue development of that application for updates using the AIMMS IDE. 
When we test our app from within the AIMMS IDE, we get the following dialog:

.. image:: ../Resources/AIMMSPRO/FlexibleDevelopment/Images/useprosession.PNG
	:align: center

.. this image should be centered, but that is CSS properties for now. 

Clicking ``No`` avoids AIMMS PRO, but this soon becomes annoying. To avoid this dialog, many developers use the function ``ProjectDeveloperMode()``. This function returns a 0 (false) or 1 (true) for the question - "Is the current instance in developer mode (AIMMS IDE) or in end user mode?"   You can read more on ``ProjectDeveloperMode`` `here <http://download.aimms.com/aimms/download/manuals/AIMMS_func.pdf>`_.

.. code-block:: aimms

   if not ProjectDeveloperMode() then
      if pro::DelegateToServer( waitForCompletion  :  1,
         completionCallback :  'pro::session::LoadResultsCallBack' )  
      then return 1;
      endif ;
   endif ;

The disadvantage of using ``ProjectDeveloperMode()`` is that it disallows the combination of AIMMS PRO and the AIMMS IDE for `AIMMS PRO debugging <https://manual.aimms.com/pro/debugging-pro.html>`_. What we actually want, is to connect to AIMMS PRO, when a connection is available, and otherwise use our own machine to solve the mathematical program.
 
To check whether we are connected to an AIMMS PRO session or not, we use the function ``PRO::GetPROEndPoint()``. The function ``PRO::GetPROEndPoint()`` returns the URL, or the 'end point', of the AIMMS PRO server the AIMMS instance is running on. If the current AIMMS instance is not running on an AIMMS PRO server, an empty string is returned.

We change the body of the ``prSolve`` procedure by replacing the test ``not ProjectDeveloperMode()`` with the test ``PRO::GetPROEndPoint()`` below.

.. code-block:: aimms

   if pro::GetPROEndPoint() then
      if pro::DelegateToServer( waitForCompletion  :  1,
         completionCallback :  'pro::session::LoadResultsCallBack' )  
      then return 1;
      endif ;
   endif ;

		
The ``pro::DelegateToServer`` statement is executed only if the current AIMMS instance is connected to an AIMMS PRO server. If not, it is skipped and the remaining procedure is executed in the AIMMS IDE. This way, we avoid the annoying dialog mentioned above, but still enable `AIMMS PRO debugging <https://manual.aimms.com/pro/debugging-pro.html>`_ when needed.

The AIMMS project for running Flowshop example can be downloaded from: :download:`3. Flow Shop - Enable development testing <../Resources/AIMMSPRO/Deploy_DataServerComm_3_RemoveVeil/Downloads/3. Flow Shop - Enable development testing.zip>`.

The next article in this AIMMS PRO series shows how to keep the end user interface active while a solve procedure is in process. Please read  :doc:`Deploy_DataServerComm_3_RemoveVeil`.

.. include:: ../includes/form.def