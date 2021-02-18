Job transparancy
==================

A solver session (job) in AIMMS PRO runs headless; there is no UI or developer environment that enables direct interfacing with that job.
In this section, several tools are discussed to analyze behavior and errors occurring in both data session and solver sessions.

The `GuardServerSession` library, with prefix `gss` offers other methods to analyze data session and solver sessions.
This library provides the following methods:

#.  :doc:`Errors as data<../310/310-errors-as-data>`

    The end-user is a specialist in the application domain of the application. 
    That application is built by specialists with other skills, including AIMMS and Operations Research.
    Data for that application is provided by specialists with yet other skills.
    Many error messages can be tackled by the end-user; but not all. 
    For the remaining messages other specialists need to be consulted.
    This article is about sharing the error messages between the end-user and the other specialists in that team.

#.  :doc:`Tracing<../497/497-tracing-procedures>`  

    In the previous article, the stack of procedures was presented; which is an in-depth answer to the question: what was happening during the occurrence of the error.
    In this article, we are discussing the tracing of procedures during a data session or a solver session. 
    Essentially, this is a wide answer to the question: how did we get to the situation whereby the error could occur.
    Like the previous article; essential here is the sharing of information between the end-user and the other specialists involved.

#.  :doc:`Profiling<../310/310-investigate-behavior-pro-job>` 

    Profiling statements and procedures provides insights into the runtimes of these statements and procedures. 
    Consequently, insight is provided in the actual bottlenecks of the application.
    An end-user on the other hand; typically just expresses which actions seem to take unneccessry long.
    By now, you'll get the drift of this series of articles; the `GuardServerSession` library 
    provides a means for sharing concrete profiling information between the end-user and other team members.

#.  :doc:`State serializing<../321/321-state-server-session>`

    The state of a session is mostly comprised by the values of the sets, parameters, and variables. 
    To compare a stable version of your project to a version that is in development, at some point you may want to compare the data of 
    some sets, parameters, and variables. This article discusses how to do this.

A User Interface for the `GuardServerSession` library can be built using these :doc:`instructions<../310/310-install-ui-gss>`

`Debugging PRO-enabled Projects <https://documentation.aimms.com/pro/debugging-pro.html>`_ 
is achieved by connecting  AIMMS Developer to AIMMS PRO. 
The data session, or even the server session, is then actually run in AIMMS Developer; the circumstances are similar, but not exactly the same.
In addition, this form of analysis requires a reproducible example, and does not provide any "after the fact" information.





 





