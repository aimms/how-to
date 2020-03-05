Responding applications
===========================

.. meta::
   :description: Keeping your Decision Support application responding while letting it do long running searches for a best solution.
   :keywords: responding applications, interactive, Decision Support, AIMMS PRO

Applications for decision support have two characteristics:

* A lot of information for the user

* The search for an optimal solution may take a long time

We want our application to be designed as a **responding application**.  
A responding application is an application that keeps responding, 
even in the presence of long running searches for an optimal solution.
Essentially, we achieve this by letting our application have two sessions:

#.  The **data session**: this session is responsible for interacting with the user. 
    Such a session only solves small mathematical programming problems.

#.  The **solver session**: this session is responsible for solving the Operations Research problem at hand. 
    Solving an operational research problem may involve solving one or more mathematical programming problems.

Clearly these two sessions need to communicate with each other.

The architecture of the AIMMS PRO platform is designed to facilitate the above.

To adapt an optimization application to become a responding optimization application is a bit like building a house:

#.  When we build a house we start with the structure and the roof; this already keeps the rain away.
    When we start with a responding application, we first separate the search for an optimal solution as a separate solver session.

#.  When we continue with the building of the house, we make sure that the walls, windows, and doors are put in, to keep the cold wind out.
    When we continue with the development of a responding application, 
    we make sure that we don't have to wait for the solution but read it back at our own convenience; 
    that we can work on the application independent from the presence of an AIMMS PRO system.
    
#.  With the walls including windows and doors, and the roof done; we can continue to make it cosy by electricity, plumbing etc.
    When we continue with the development of a responding application, we make sure that the user is informed of progress, 
    that intermediate solutions are saved, and even that we can provide new data to the solver session.

To adapt an application to become a responding application, there are several steps to take.

We will discuss each of these steps briefly, and provide a reference where each step is presented in more detail. 

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
When an AIMMS WebUI application is waiting for a long running procedure, this is indicated to the user by
having a veil is drawn over the screen and showing a busy indicator.
By not waiting for the solver session, we avoid this veil.
More about this in :doc:`remove veil<../19/19-remove-veil>`

User specified load
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

When the solver session is running too long, or doesn't make sufficient progress, we'd like to stop it.
The data session can :doc:`interrupt the solver session<../34/34-interrupt-server-session>`.

Passing new data to the solver session
--------------------------------------

Data changes can be sent to the solver session, for instance to send more or less progress information to the data session.
See :doc:`share data changes<../42/42-data-session-changes>`

