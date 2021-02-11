Job transparancy
==================

A solver session (job) in AIMMS PRO runs headless; there is no UI or developer environment that enables direct interfacing with that job.

`Debugging PRO-enabled Projects <https://documentation.aimms.com/pro/debugging-pro.html>`_ 
is achieved by connecting  AIMMS Developer to AIMMS PRO. 
The data session, or even the server session, is then actually run in AIMMS Developer; the circumstances are similar, but not exactly the same.
In addition, this form of analysis requires a reproducible example, and does not provide any "after the fact" information.

The `GuardServerSession` library, with prefix `gss` offers other methods to analyze data session and solver sessions.
This library provides the following methods:

#.  :doc:`Errors as data<../310/310-errors-as-data>`

#.  :doc:`Tracing<../497/497-tracing-procedures>`  

#.  :doc:`Profiling<../310/310-investigate-behavior-pro-job>` 

#.  :doc:`State serializing<../321/321-state-server-session>`

A User Interface for this library can be built using these :doc:`instructions<../310/310-install-ui-gss>`




 





