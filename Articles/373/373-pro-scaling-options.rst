Scaling AIMMS PRO
==================

.. meta::
   :description: Scaling AIMMS PRO for volume, geography, or power.
   :keywords: PRO, scale, concurrent, memory, processing

This document describes how to scale AIMMS PRO. We refer to two types of PRO installations:

* AIMMS PRO On-Premise: PRO installations self-managed by the client, not managed by AIMMS
* AIMMS Cloud Platform: PRO installations managed by AIMMS on cloud technology, using a custom version of PRO

Summary of scaling options
----------------------------
Broadly speaking, there are three scaling scenarios. The table below summarizes the options for scaling your PRO installation based on the primary need scenario. 

+-------------------------------------------------------------------------+-----------------------------------------------------------------------------------------------------------+---------------------------------------------------------------------------------+
| Need                                                                    | Options for PRO On-Premise                                                                                | Options for PRO on Cloud                                                        |
+-------------------------------------------------------------------------+-----------------------------------------------------------------------------------------------------------+---------------------------------------------------------------------------------+
| Volume: Serve more concurrent users and/or more concurrent solves       | Scale vertically, changing to a larger server                                                             | Standard feature, instantly available, to at least hundreds of concurrent users |
|                                                                         | Scale horizontally, multiple PRO installations, for example per user group, per region or per application |                                                                                 |
+-------------------------------------------------------------------------+-----------------------------------------------------------------------------------------------------------+---------------------------------------------------------------------------------+
| Geography: Serve geographically spread users with minimum latency       | Multiple AIMMS PRO installations, geographically spread to optimize latency                               | Multiple accounts across different geographies, each maintained separately      |
|                                                                         |                                                                                                           | Standard feature of future version of AIMMS Cloud Platform                      |
+-------------------------------------------------------------------------+-----------------------------------------------------------------------------------------------------------+---------------------------------------------------------------------------------+
| Power:  Run larger models requiring more memory and/or processing power | Scale vertically, changing to a larger server                                                             | Standard feature, currently up to 15 CPUs/120GB RAM                             |
+-------------------------------------------------------------------------+-----------------------------------------------------------------------------------------------------------+---------------------------------------------------------------------------------+

Scaling for Volume
------------------------

A need for more concurrently running applications. For example, because the number of users is growing, or because the users are running applications more frequently, or users are each running more applications concurrently.  


* Self-managed AIMMS PRO (On-Premise) 

   Upgrade to a larger server and/or run the application database on a separate server. This option is limited by the maximum server size available. 

   If you require more capacity than the largest available server can provide, you can consider setting up multiple AIMMS PRO installations on multiple servers, for example per user group, per region or per app (please note AIMMS PRO Cluster has been discontinued). When operating multiple AIMMS PRO installations, you can consider connecting them all to the same application database, so that all users and apps can work with the same data sources. And you will need to establish a process for maintaining the app collections and user access on each of these instances.   


* AIMMS Cloud Platform 

   Allows you scale to almost any number of concurrently running apps (we have successfully run 400 concurrent sessions of the same app in a single account, using a pool of 10 servers). 

 

Scaling for Geography
------------------------

When your users are spread across multiple geographies you may find that one central AIMMS PRO installation causes some geographies to experience too much latency (delays in internet connections), hurting the user experience and thereby user engagement.


* Self-managed AIMMS PRO (On-Premise) 

   Multiple AIMMS PRO installations on multiple servers, each close to a user group. When operating multiple AIMMS PRO installations, you can consider connecting them all to the same application database, so that all users and apps can work with the same data sources. And you will need to establish a process for maintaining the app collections and user access on each of these instances. 

 

* AIMMS Cloud Platform 

   At this moment the AIMMS Cloud Platform cannot yet provide geographically mirrored accounts with mirrored app and user collections and mirrored application databases. We expect to provide that in the future. 

   In the meantime, you can operate multiple accounts, one in each geography, and maintaining each separately. 




Scaling for Power
------------------

Some models require larger amounts of memory to run and/or more processing power to bring computation/solve times down.  


* Self-managed AIMMS PRO (On-Premise) 

   Upgrade to a server with more processing power and memory. This option is limited by the maximum server size available. 


* AIMMS Cloud Platform 

   Currently a maximum of 120GB memory and 15 ‘cores’ or ‘vCPUs’ per app session, requiring a custom agreement.





Further guidance
-----------------
This article is to provide general guidance for scaling your installation of PRO. Contact `AIMMS Support <mailto:support@aimms.com>`_ to discuss your unique business goals and options.

Related topics
---------------

* :doc:`../374/374-pro-backup-and-continuity`
* :doc:`../341/341-PRO-Concurrent-Users`