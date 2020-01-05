Responding applications
===========================

.. meta::
   :description: Keeping your Decision Support application responding while letting it do long running searches for a best solution.
   :keywords: responding applications, interactive, Decision Support, AIMMS PRO


In this overview article, a responding application is an application that keeps responding and displaying requested information in the context of long running tasks.
The AIMMS PRO platform offers to separate the long running task from the task of providing information to the user.
To adapt an application to become a responding application, there are several steps to take.


We call the session that responds to the user the **data session**. 
We call a separate session to perform a long running computation a **solver session**.

We will discuss each of these hurdles briefly, and provide a reference for more information. 

#.  First, from the main application, we need to separate session to handle a long running task.  
    For more information see: :doc:`creating task<../33/33-pro-deploy-app>`

#.  The AIMMS IDE is used to develop applications, but the applications are deployed on AIMMS PRO. 
    In the article :doc:`develop multi-platform applications<../32/32-pro-develop-app>` we describe how to model for the combination.

#.  To make an application responding, we shouldn't wait for the solver session to complete. 
    More about this in :doc:`remove veil<../19/19-remove-veil>`

#.  The user may be surprised/overwhelmed when the results computed in the solver session come inmediately in.
    To handle the results from the solver session at the convenience of the user, see
    :doc:`load results upon request<../40/40-data-server-load-results>`

#.  The solver session can send messages to the data session to keep the user informed of the progress. 
    How to send and receive such messages is discussed in :doc:`share progress information<../35/35-web-ui-progress-window>`

#.  Intermediate solutions found can be shared efficiently between the solver and data session using AIMMS cases. 
    These cases are stored using AIMMS PRO storage.
    Further details are in :doc:`share intermediate solutions<../36/36-intermediate-solution>`

#.  When the solver session is running too long, or doesn't make sufficient progress, we'd like to stop it.
    The data session can :doc:`interrupt the solver session<../34/34-interrupt-server-session>`.

#.  Data changes can be sent to the solver session, for instance to send more or less progress information to the data session.
    See :doc:`share data changes<../42/42-data-session-changes>`

