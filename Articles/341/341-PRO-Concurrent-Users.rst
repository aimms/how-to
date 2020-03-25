Understanding Concurrent Usage of AIMMS PRO/Cloud
===============================

.. meta::
   :description: Understanding how concurrent users/usage works on AIMMS PRO/Cloud with WebUI
   Keeping your Decision Support application responding while letting it do long-running searches for the best solution.
   :keywords: concurrent users, concurrent usage, AIMMS PRO, WebUI, Cloud 
   responding applications, interactive, Decision Support, AIMMS PRO

This article intends to help you understand how concurrent usage works within AIMMS PRO on the cloud.

Introduction
--------------------

Online platforms like PRO that are hosted on the cloud usually have some policies in place that dictate how many concurrent users are allowed to use the service at a time and for how long due to having a large number of users running the service. It is also sometimes unclear what a single concurrent user is and how it may affect other users trying to run applications on the PRO platform. 

This document explains what a single concurrent user means and how that may affect other users and clarifies if/what policies affect users logging onto PRO. 

**Concurrent User Definition**

A single concurrent user is defined as one or more (different) apps that have been launched and are still active in WinUI or WebUI from within a single PRO portal session. 
Keep in mind that if a user logs in to PRO with the same username from two different browsers at the same time with applications running concurrently, then this counts as two concurrent user usage.

**Concurrent Users Policies**

* There is no policy on the number of concurrent users that can be logged into the portal at any moment of time. As a result there is no queue. 
  (Note: The number of concurrent users is restricted by the number of concurrent user licenses you have)

* There is no timeout or any other policy that would auto log out a user. However, an admin can manually kick a user off a session to free up a license.  

**Concurrent User Licenses**

When a user is unable to launch a session due to an insufficient number of concurrent user licenses, a message of ‘There are no free seats available’ will be displayed. 

WebUI
--------------------

**Idle Session**

An idle session is when all browser tabs are closed or any other reason causing a severed connection to our cloud database (e.g. internet connection loss). 

.. note::

   There is a timeout policy that terminates an idle session after 5 minutes. 
   Related: :doc:`Keep WebUI Session Active<../19/19-remove-veil>`

**Terminating a Session**

It is possible to terminate a session based on logic defined by the AIMMS developer using the AIMMS modeling language, which can free up licenses. 

