
.. Deploy_DataServerComm_8_PassNewData.rst

Communicate data changes from the data session to the server session
====================================================================

Introduction
------------

In previous articles it was shown how to communicate :doc:`progress <Deploy_DataServerComm_5_Progress>` and :doc:`data <Deploy_DataServerComm_6_Retrieve>`, from the server session to the data session. In addition, we can :doc:`interrupt <Deploy_DataServerComm_7_Interrupt>` by sending a signal from the data session to the server session.  In this article we will send data from the data session to a running server session. 

Use case: We continue with the Flowshop application. Even though the progress information obtained from the server is very interesting, we decide to wait for a longer period the solve and do not want to waste computation time with progress or incumbent updates. Therefore, we switch the progress and incumbent updates off in the server session.

Implementation
--------------

.. image:: ../Resources/AIMMSPRO/Deploy_DataServerComm_3_RemoveVeil/Images/DataChangesToServerSession.png

Once a solver session is started, you can send additional information using the predefined procedure ``pro::DelegateToServer()``, provided you pass it the queue it already is listening to in the call. When ``pro::DelegateToServer`` is passed a message queue it will not start a new job, but add the enclosing procedure, here ``prPassProgressSupplied``, as a message to the existing queue. This lets the indicated procedure be run as part of the current job.  As you know, a job running on a server is essentially an AIMMS procedure running. In order to let the message start immediately, it should be given priority.

This is illustrated in the following code of ``prPassProgressSupplied``. 

    .. code-block:: aimms

        Procedure prPassProgressSupplied {
            Arguments: (pwbs);
            Body: {
                if pro::GetPROEndPoint() then
                    locSessionQueue := pro::session::CurrentSessionQueue();
                    if pro::DelegateToServer(requestQueue: locSessionQueue,
                            flags: pro::PROMFLAG_LIVE + 
                                     pro::PROMFLAG_PRIORITY) then
                        return 1;
                    endif ;
                endif ;
                
                ! The following code will only be executed server side.
                pProgressWillBeSupplied := pwbs ;
                                
            }
            StringParameter locSessionQueue;
            Parameter pwbs {
                Property: Input;
            }
        }
        
Note the use of the flag ``pro::PROMFLAG_PRIORITY`` ; this flag indicates that this procedure call should be executed now, and the execution of the running procedure is temporarily suspended.

The user interface when the problem is being solved now looks as follows:

.. image::  ../Resources/AIMMSPRO/Deploy_DataServerComm_3_RemoveVeil/Images/BB08_WebUI_screen.PNG 

The AIMMS project that does just this, can be downloaded from: :download:`9. Flow Shop - Control <../Resources/AIMMSPRO/Deploy_DataServerComm_3_RemoveVeil/Downloads/9. Flow Shop - Control.zip>`.

Summary
-------

Like small data updates to the client session from the server session are facilitated by pro::DelegateToClient, in a similar way small data changes from the client session to the server session are facilitated by pro::DelegateToServer, provided:

#. The queue to which the server session listens is specified in the call

#. The Priority flag ``pro::PROMFLAG_PRIORITY`` is used.


Further reading
---------------

Information about `AIMMS PRO <https://documentation.aimms.com/pro/index.html>`_


.. include:: ../includes/form.def
 