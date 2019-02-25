Send and Receive Messages
==============================

.. meta::
   :description: How to use queues to pass messages from the server session to the data session.
   :keywords:  queue, message

.. https://gitlab.aimms.com/Chris/aimms-how-to/issues/80
.. Nirvana project 0006

.. sidebar:: ListeningConnectionId

    .. image:: images/159915.svg

This article is a follow up of :doc:`80-schedule-jobs`

In the context of a coherent collection of AIMMS data and server sessions, there should be an option to communicate between these sessions.
AIMMS PRO provides the concept of queue for this; a session can create a queue and start listening to it. In addition, that session can communicate the name of that queue such that other sessions can also listen to it, or can put messages on it.

This article provides a simple example based on  :doc:`80-schedule-jobs`  The essence of this latter article is that server jobs are regularly created. Now suppose we want to monitor the server sessions created and their execution; then these server sessions should have a way of communicating with the WebUI data session. There are several steps needed to realize this:

#. The queue should be created and listened to by the WebUI data session as illustrated here:

    .. code-block:: aimms

        ! Create a new queue, with full access for everyone.
        if not sp_DataSessionMessageQueue then

            ! Create the queue
            sp_AuthorizationString := "#7+16777087";
            pro::messaging::CreateQueue(sp_AuthorizationString,sp_DataSessionMessageQueue);

            ! ... and start listening to it.
            pro::messaging::AddQueueToConnection(sp_DataSessionMessageQueue, pro::ListeningConnectionId);
        endif ;

#. The name of the queue should be communicated to all the server sessions. In the example this is achieved by passing the name of the queue as a procedure argument.

#. The server sessions should pass messages to the data session.  Only for the first server session, `pro::clientQueueID` is valid reference to a queue for the data session. This is why all these server sessions post their messages on the queue created in the first step.

#. When these server sessions pass their message, it should be stored and displayed in the data session.  

A potential implementation can be downloaded :download:`here <downloads/ReceiveMessages.zip>` .

To operate this app, just press the button and watch messages appear.

.. include:: /includes/form.def
