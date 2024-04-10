Create Responding Applications
===============================

.. meta::
   :description: Keeping your Decision Support application responding while letting it do long-running searches for the best solution.
   :keywords: responding applications, interactive, Decision Support, AIMMS PRO

Applications for decision support have two characteristics:

* A lot of information for the user

* The search for an optimal solution may take a long time

You can design a **responding application** which responds to user interaction, 
even during long-running searches for an optimal solution.

Assign two sessions for the application:

*  **Data session**: allows interaction with the user; only solves small mathematical programming problems.

*  **Solver session**: solves the Operations Research problem; may involve solving one or more mathematical programming problems.

AIMMS PRO platform is designed so that these two sessions can communicate with each other.

To adapt an optimization application to become a responding optimization application requires several stages of development tasks:

#.  Structure: Separate the search for an optimal solution as a separate solver session.

#.  Development: Make sure that you can read the solution at your own convenience, 
    and work on the application-independent from an AIMMS PRO system.
    
#.  Finishing: Add progress status messages, ensure
    that intermediate solutions are saved, and add a way to feed new data to the solver session.

Tasks required in these stages are discussed below in more detail with links to further help. 

Separating sessions
--------------------

The first step is that we are able to

#.  adapt our application such that the data and solver session are separated,

#.  connect to an AIMMS PRO system,

#.  publish our application on that AIMMS PRO system, and

#.  test it by launching the application.

This is described in detail at: :doc:`Deploy an Application on AIMMS PRO <../33/33-pro-deploy-app>` .

Delegate and develop
--------------------

The AIMMS IDE is used to develop applications, but the applications are deployed on AIMMS PRO. 
In the article :doc:`develop multi-platform applications<../32/32-pro-develop-app>` we describe how to model for the combination.

Responding client side
-----------------------

To make an application responding, we should not wait for the solver session to complete. 
When an AIMMS WebUI application is waiting for a long-running procedure, this is indicated to the user by
having a veil is drawn over the screen and showing a busy indicator.
By not waiting for the solver session, we avoid this veil.
More about this in :doc:`remove veil<../19/19-remove-veil>`

User-specified load
-------------------

The user may be surprised/overwhelmed when the results computed in the solver session come immediately in.
To handle the results from the solver session at the convenience of the user, see 
:doc:`load results upon request<../40/40-data-server-load-results>`

Progress information
---------------------

The solver session can send messages to the data session to keep the user informed of the progress. 
How to send and receive such messages is discussed in :doc:`share progress information<../35/35-web-ui-progress-window>`

Passing intermediate solutions
------------------------------

Intermediate solutions found can be shared efficiently between the solver and data session using AIMMS cases. 
These cases are stored using AIMMS PRO storage.
Further details are in :doc:`share intermediate solutions<../36/36-intermediate-solution>`

Interrupting the solver session
-------------------------------

When the solver session is running too long or doesn't make sufficient progress, we'd like to stop it.
The data session can :doc:`interrupt the solver session<../34/34-interrupt-server-session>`.

Passing new data to the solver session
--------------------------------------

Data changes can be sent to the solver session, for instance, to send more or less progress information to the data session.
See :doc:`share data changes<../42/42-data-session-changes>`

