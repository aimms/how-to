The action log
===============

The action log is like any other log, it logs what happened.

It uses the techniques laid out in :doc:`Tracing<../497/497-tracing-procedures>` 

Most important is the logging of actions.  Here an action is defined as: An AIMMS procedure that is invoked via the WebUI.
Examples of invocations of procedures from the WebUI are:

#.  Button press

#.  Uponchange procedure of a data item

#.  A menu entry, via an item menu, widget menu, primary action, or secondory action

#.  Status bar procedure

#.  Page property: Action upon load

#.  Page property: Action upon leave

By having a complete log of actions of an end-user; one can try to replay what happened.

The action log is stored in .actionLog files and at the end of a solver session saved at `/userdata/<env>/<user>/<appname>/<appver>/tracings/<session-id>.actionLog`

.. code-block:: aimms
    :linenos:

    Set s_messageLevels {
        Index: i_messageLevel;
        Definition: {
            data { trace, debug, info, warn, error, fatal } ;
        }
        Comment: "The message levels for tracing";
    }







