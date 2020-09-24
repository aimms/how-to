:orphan:

How to connect AIMMS with Python
============================================

.. meta::
   :description: Integrating (data science) models built in Python with your AIMMS applications
   :keywords: python, integration, data science, machine learning, connectivity

To run a Python model from AIMMS you need to deploy your py model as a REST API. This and this describe how to call some APIs using the HTTP library. In a similar fashion, we will use the HTTP library and DataExchange library to call a py model from AIMMS. We show one method of deploying a py model as an API using tools like Flask and docker.
.

Applications for decision support have two characteristics:

* A lot of information for the user

* The search for an optimal solution may take a long time


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

Further details are in :doc:`share intermediate solutions<../36/36-intermediate-solution>`

Interrupting the solver session
-------------------------------

When the solver session is running too long or doesn't make sufficient progress, we'd like to stop it.
The data session can :doc:`interrupt the solver session<../34/34-interrupt-server-session>`.

Passing new data to the solver session
--------------------------------------

Data changes can be sent to the solver session, for instance, to send more or less progress information to the data session.
See :doc:`share data changes<../42/42-data-session-changes>`

