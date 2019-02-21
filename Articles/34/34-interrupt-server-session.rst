Interrupt the Server Session
=============================

.. meta::
   :description: A method for stopping a solve process when "cancel" is not available.
   :keywords: solve, cancel, interrupt, webui, veil


Introduction
------------

We saw how to remove the "Busy" veil during a solve process in a :doc:`previous article<19-remove-veil>` to let the user be in control of the application while a procedure execution is running in the background. However, we also lose the ability to interrupt this background process which was available as a "Cancel" button on the veil. This article will guide you on how to interrupt a procedure executing in the server session when the veil has been disabled.

Implementation
--------------

.. image::  /images/QueuesBetweenDataAndServerSession.png

Interrupting the server session is very direct and can be done by using the AIMMS PRO library procedure, ``pro::client::StopExecution`` which requires you to specify two input arguments.  

#. ``queueID`` to which message queue is the server process listening? As we have only one server session in our example, the current server session queue can be obtained as a string parameter by using ``pro::session::CurrentSessionQueue()``.  

#. ``intType`` short for interruption type, to specify whether you want to interrupt the execution of the entire procedure, or just a currently executing solve statement (if any). The difference between these two is that with a solve statement, the solver session is also invoked by the server session. If there is no solve statement in a procedure, it is executed only on the AIMMS server in the background. 

This mechanism is illustrated in the code snippet below. Here, we interrupt only a solve statement by using the predefined identifier ``pro::AIMMSAPI_INTERRUPT_SOLVE``. To interrupt the entire procedure execution, simply replace ``pro::AIMMSAPI_INTERRUPT_SOLVE`` with ``pro::AIMMSAPI_INTERRUPT_EXECUTE`` in the below code. 

    .. code-block:: aimms

        Procedure prInterruptSolve {
            Body: {
                if pro::GetPROEndPoint() then
                    pro::client::StopExecution(               ! 1 - The procedure that makes an interrupt.
                        pro::session::CurrentSessionQueue(),  ! 2 - The queue leading to the server session.
                        pro::AIMMSAPI_INTERRUPT_SOLVE );      ! 3 - Interrupt SOLVE, not entire server session.
                endif ;
                pSolveInterruptable := 0 ; ! Button BtnInterrupSolve becomes invisible.
            }
        }

Now, you can link this procedure to a button in your WebUI application to be able to interrupt a solve procedure running in the backround.

The example AIMMS project with the above demonstrated procedure implemented can be downloaded from :download:`8. Flow Shop - Interrupt </downloads/8. Flow Shop - Interrupt.zip>`.

Further reading
---------------

An interruption is only one type of communication to a server session.  A generic way to communicate data changes from the data session to the server session is provided in :doc:`42-data-session-changes`.


.. include:: /includes/form.def
