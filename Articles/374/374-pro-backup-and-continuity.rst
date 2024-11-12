:orphan:
Protect AIMMS PRO installation for business continuity
======================================================

.. meta::
   :description: How to back up your AIMMS PRO installation for business continuity.
   :keywords: PRO, backup, standby, business continuity, cluster, disaster recovery

In this document we will describe options to provide business continuity and ensure resilience against hardware or software failure for self-managed installations of AIMMS PRO (On-Premise).


To summarize, there are three approaches:

* Hot standby: spare AIMMS PRO installation, ready to take over instantly 

* Cold standby: spare hardware present, installation and restore prepared 

* Cloud: AIMMS PRO managed by the AIMMS Cloud, which provides this service as a standard feature




Establishing business goals
---------------------------
Many of our customers are looking for ways to ensure business continuity for their AIMMS applications. In our eyes these explorations should start with establishing business goals. 

* What is the desired reliability, often expressed as ‘up-time’, the % of time the application is available (usually measured per month)? 

* What is the maximum acceptable ‘damage’ in case of a failure of (one of the components of) the AIMMS application? This is often expressed in:  

   * RTO, Recovery Time Objective, the target for the maximum downtime, or put differently, the target for the maximum time it takes to have the application available again. For example, you may decide that for a specific application the RTO is 8 business hours, achieved by having replacement hardware on-site within a few hours and restore procedures documented and practiced. 

   * RPO, Recovery Point Objective, the target for maximum data loss, expressed in time. For example, if you make a daily backup, your RPO is 24 hours (in case the failure occurs right after completion of the backup.  

* And you have to consider which risks you will NOT have business continuity plans for, such as severe physical damage to the data center. Of course, in most cases shorter time targets for RPO and RTO will lead to progressively increasing costs. There is a clear trade-off. Once the RTO and RPO goals have been set you can explore the technical solutions.  

We will look at business continuity for both the Self-managed AIMMS PRO setup and the AIMMS Cloud Platform and discuss the characteristics of each setup in turn. 

Self-managed AIMMS PRO: Cold Standby 
-------------------------------------

Cold standby systems are installed once to install and configure the system and data and then turned off until needed, when the primary system becomes unavailable. In that case the cold standby system will be turned on, the backups of the primary system will be restored onto the standby system, network traffic will be rerouted to the standby system and services can be resumed. 

By locating the standby in another data center, you are also protected against the loss of an entire data center, for example because the power supply, cooling or internet connection is lost, or the data center is damaged. 

Operating a cold standby for your AIMMS PRO installation will roughly require:  

* procedures to keep the operating software, AIMMS and AIMMS PRO versions and the standby server up-to-date and, when present, the same for the application database server 

* procedures to restore AIMMS PRO storage, the AIMMS PRO database and, when present, the application database from back-up 

* procedures to switch the user traffic to the standby system 

* procedures to return to the production server, if needed 

Self-managed AIMMS PRO: Hot standby 
-----------------------------------

In contrast, a hot standby system is running and mirroring the primary system. Software versions are kept identical and data is continuously copied to the standby system. In case of a failure of the primary system the hot standby system either takes over automatically or is manually engaged. Of course, much shorter RTOs and RPOs can be achieved using hot standby, but at a cost. 

Operating a hot standby for your AIMMS PRO installation will roughly require: 

* procedures to keep the operating software, AIMMS and AIMMS PRO versions and the standby server up-to-date and, when present, the same for the application database server 

* software and possibly hardware for making sure the hot standby holds an up-to-date copy of the data held by PRO Storage, the AIMMS PRO database and the application database; your RPO will determine how ‘up-to-date’ that needs to be. 

* procedures to switch the user traffic to the standby system 


Self-managed AIMMS PRO: Other options 
-------------------------------------

Of course, there are many more routes to achieve business continuity, such as running production on two identical AIMMS PRO installations, with traffic divided by a load balancer and mechanisms to keep the data identical on both installations. Then you achieve both scaling and business continuity.  

Running self-managed AIMMS PRO on Virtual Machines may make moving the installation to a larger server easier. It may also in some cases simplify disaster recovery. 

 

AIMMS Cloud Platform 
--------------------
The AIMMS Cloud Platform aims to provide an effective and relatively inexpensive way to achieve high uptimes and short RTOs and RPOs as part of your business continuity planning. 

Our Service Level Statement states: 

* uptime target of 99.5% INCLUDING scheduled maintenance  

* RTO of 2 business hours  

* RPO of 15 minutes  

To achieve these targets the AIMMS Cloud Platform is designed to handle failure. For example, we include: 

* use of components and services with very high availabilities (e.g. ‘eleven nines’ for file storage) 

* extensive redundancy of hardware and software services (e.g. at least two of each, spread across multiple data centers) 

* automatic repair/restart in case of failure of hardware or software services 

* database snapshots every 5 minutes 

* detailed monitoring 

If you want to switch to AIMMS PRO managed by the AIMMS Cloud, please contact our `Sales <mailto:sales@aimms.com>`_ team.

Further guidance
-------------------
In this document we tried to provide some guidance for achieving business continuity with your AIMMS PRO. Because every situation is unique, it can be no more than guidance. Your users will have to set the business targets and then your IT team will have to design and implement the solution that meets those targets.

We are always happy to support you in this process, using our knowledge of our product and our experiences over the years.  

Related topics
---------------

* :doc:`../373/373-pro-scaling-options`
* :doc:`../251/251-pro-operations-best-practices`