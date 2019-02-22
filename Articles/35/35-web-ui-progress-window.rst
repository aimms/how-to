Use the Progress Window in WebUI
================================

.. meta::
   :description: How to track progress of a solve procedure in AIMMS WebUI.
   :keywords: progress, solve, procedure

Introduction
------------

While a solve procedure is running, we are curious about the estimated time left for completion - are we going to get the results back soon or is there enough time to go grab a cup of coffee? In essence, we want to keep track of the progress of the solve procedure. We can do this by using the Progress Window (Ctrl + P) in the Developer mode, and this article will show you how to present this progress information to the end user of a WebUI application.

Approach
--------

The approach we take here involves passing information through three levels of execution.

.. image:: /Resources/AIMMSPRO/Deploy_DataServerComm_3_RemoveVeil/Images/ThreeLevelsOfExecutionTimeCB.png

#. The solver execution on the server session. 
    The solver passes on status information periodically, as part of the time callback mechanism.

#. The AIMMS execution on the server session.
    Retrieves the information provided by the solver in the previous step, also as part of the time callback mechanism.

#. The AIMMS execution on the data session. 
    Execute a procedure to retrieve the information from the server session to the data session and display it in the WebUI widgets.

The implementation of the information stream represented by the two arrows will be discussed in the next section. 
 
Implementation
--------------

The AIMMS project for the current running example with the steps implemented can be downloaded from: :download:`6. Flow Shop - Progress Communication </downloads/6. Flow Shop - Progress Communication.zip>`.

The Gap curve linechart widget in the below image is updated every second with the gap between the bestbound and incumbent objective value of the mathematical program in the project. 

.. image:: /images/BB06_WebUI_screen.PNG 

You can implement the same in your project by communicating the data from the solver (level 1) to the data session (level 2), which is done in two steps as explained in detail below. 



Step 1. From Solver (level 1) to server session (level 2)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Step 1A Instruct the solver to provide the progress information periodically. 

We use the mathematical program suffix ``CallbackTime`` for this purpose. In the context of the current running example, the mathematical program is FlowShopModel and the user defined procedure, ``NewTimeCallback`` is assigned to the ``CallbackTime``.

The predefined ``option`` statement lets you alter the project options in a procedure. We set the interval to update the progress information as 1 second by referring to the ``progress_time_interval`` option. You can also change this option by, Settings -> Project Options -> AIMMS -> Progress Options

If included before the solve statement in your project, the procedure ``NewTimeCallback`` is executed every 1 second. 

    .. code-block:: aimms

        FlowShopModel.CallbackTime := 'NewTimeCallback';
        option progress_time_interval := 1 ;

Step 1B Retrieve the information passed on by the solver to the AIMMS server session.

In our example, we want to display only the best bound and incumbent objective value of the MIP. So, the body of ``NewTimeCallback`` consists of a procedure with two arguments - FlowShopModel.bestbound and FlowShopModel.Incumbent. You can retrieve values of any of the mathematical program suffices which are listed and explained in chapter "Mathematical Program Suffices" of `AIMMS The Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_.

    .. code-block:: aimms

        UpdateGapToClient(FlowShopModel.bestbound,FlowShopModel.Incumbent);
        
.. sidebar:: How flags affect the messages between AIMMS Sessions

    An AIMMS Session processes the messages on its queue one after another, typically by executing the procedure that is contained in the message. These queues are stored in the AIMMS PRO database. The ``delegate`` family of functions, including ``pro::DelegateToServer`` and ``pro::DelegateToClient``, place messages at the end of the queues of other sessions. The flags argument of these functions alter the behavior of these messages. The flags used most often are:
    
    ``pro::PROMFLAG_LIVE``
     
    The message is not stored in the database. As such it more efficient and lighter than ordinary messages. When an AIMMS Session connects to a queue after a live message is invoked, it will not see that live message; which is desired for progress and status updates.  
    
    ``pro::PROMFLAG_PRIORITY``
    
    The message gets priority over the other messages in the message queue. Also, when a procedure is running in the receiving process, the message invokes a procedure that is ran in between statements of the current procedure.
        
Step 2. From server session (level 2) to data session (level 3)   
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

As we are only passing small amounts of data and executing some simple arithmetic, the procedure ``UpdateGapToClient`` can be executed on the data session i.e., on the end user's browser. To do this, we use the call ``pro::DelegateToClient``. This is very similar to the earlier used call, ``pro::DelegateToServer`` and the difference is evident as their names suggest - in ``pro::DelegateToClient``, we are delegating a procedure to the client (or data) session and in the other one, we are delegating a procedure to the server session.

This procedure contains two arguments as input parameters, bb and icb which take on the values of the bestbound and Incumbent suffices specified in the previous step.

    .. code-block:: aimms

        Procedure UpdateGapToClient {
            Arguments: (bb,icb);
            Body: {
                if pro::GetPROEndPoint() then
                    if pro::DelegateToClient(flags: pro::PROMFLAG_LIVE) then
                        return 1; 
                    endif ;
                endif ;
                
            }
            Parameter bb {
                Property: Input;
            }
            Parameter icb {
                Property: Input;
            }
        }

In our running example, the body of this procedure contains other data manipulation statements to update a set of observations and calculate the gap percentage between the bestbound and incumbent objective value. These statements are not discussed in this article.

Further reading
---------------
Some closing remarks about the ``UpdateGapToClient`` procedure to give you a better understanding of what is happening. 

#.  The ``pro::DelegateToClient`` code is protected by ``pro::GetPROEndPoint()`` to make the procedure executable on Developer mode too. This IF statement is optional but is generally recommended as it allows for a smooth development and debugging workflow.

#.  The second IF statement containing ``pro::DelegateToClient`` checks and returns if there is an active data session available. The statements in the body of the procedure are executed on the data session only if this IF statement returns a TRUE or 1 status. 

You can read more about the ``pro::PROMFLAG_LIVE`` and other flag arguments in a separate article

.. here, `What are the pro flag arguments and why use them? <Insert article link here>`_.

Now that end users know the state of the solution process, they might want to interrupt it when they see that further improvements are not worth waiting for. The article  :doc:`../34/34-interrupt-server-session` shows you how to do it.

.. include:: /includes/form.def
 