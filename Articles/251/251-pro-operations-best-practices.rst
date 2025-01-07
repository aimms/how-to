Best Practices for Operating AIMMS PRO
====================================================

.. meta::
   :description: How to professionally operate your AIMMS PRO system.
   :keywords: AIMMS PRO, backup, recovery

     
As AIMMS applications move from desktops to PRO platforms and start to play a larger role for a larger number of users, it is important to adopt the best practices for operating server-based business applications.

This article presents common best practices of application management as they apply to operating and maintaining AIMMS PRO platforms. These practices address how to deal with challenges like bugs, accidental project deletion, and hardware failures.

AIMMS PRO platform facilitates rapid prototyping of optimization apps and maximum uptime. The two most likely uptime disruptions are software updates and critical issues such as hardware failures or accidental file deletion. 

In the context of AIMMS PRO, software updates fall into two categories: 

#. underlying software updates  
#. new or updated apps 

Below are some best practices to deal with both. 

Using a Version Control System
---------------------------------------

.. image:: images/multicast-128.png
   :align: right
   :scale: 75

In AIMMS 4 and above, the entire model is saved in text files and can thus be stored in a version control system (VCS). This has numerous advantages:

#. The entire history of a model remains available and one can always roll-back to earlier versions or differentiate between proven production version and development versions with new functionality.

#. Multiple developers can work effectively and reliably on one model simultaneously, with branching and merging to ensure everybody can work effectively and proven changes can be shared and deployed easily.

We recommend the use of a distributed VCS like Git. With Git you can have the entire repository on your local machine, commits are initially just stored locally, and you can branch and merge locally without making those branches public. This aspect is just a matter of installing the Git-related software and starting to use it.

To be able to share, Git also supports the capability to pull/push some or all branches to a central repository. If other people also pushed to the branch(es) you are pushing to, you first have to pull in those commits, after which merging (and possibly conflict resolution) takes place locally, and you can push the merged branch back to the central repository. For infrastructure needs, you would need to consult your IT (and to make it easy: there are virtual appliances that set up a central Git server with authentication and authorization). 

Alternatively, there are companies like Atlassian, GitHub, GitLab, and others which offer hosted solutions where you get private repositories hosted in the cloud, with integrated issue tracker support (where issues can be linked to commits and vice versa).

See :doc:`../151/151-version-control-aimmspack-backup` for a more in-depth explanation on the use of Version Control systems with AIMMS.


Operating a Test Environment
----------------------------------------

.. image:: images/test-tube-128.png
   :align: right
   :scale: 75

All large IT applications operate change procedures and test and/or acceptance environments in addition to their production environment in order to prevent disruptions to their users and operations from changes and updates. Typically the test environment serves to test changes before these are applied in production. The acceptance environment is used for testing the migration to the production environment. For less critical applications, the acceptance environment is often left out.

.. Best practices in this field are well documented and can be obtained from any IT Operations team. Typical aspects to consider include testing, approval, authorization, announcements and roll-back.

We recommend you to operate at least a test environment that closely resembles your AIMMS PRO production environment, i.e. same PRO and PRO-Package versions and similar or the same data sources, same operating system, same hardware (maybe less powerful). In mission-critical applications we recommend adding an acceptance environment, closely matching your production installation, solely for testing/practicing your updates prior to updating production environments.

If your AIMMS applications connect to other systems, you need to decide whether to connect your AIMMS PRO test environment to the production installations of these systems or to the test environments. Rigorous testing can probably work well with input data from production systems, but results are probably best written to a test system.

For AIMMS PRO, we can identify two types of changes that require testing prior to production deployment:

#. New or updated AIMMS PRO applications; even though PRO enables instant global release of new or updated applications, one should test these thoroughly before the updated app is published. 

#. Changes, updates, patches, etc. to the underlying software such as the AIMMS software, the operating system, virtualization software, etc. can cause disruptions to the proper functioning of the applications. So these changes need to be tested prior to applying them to the production environment.

Operating a Maintenance Window
-------------------------------------------

.. image:: images/wrench-128.png
   :align: right
   :scale: 75

If your AIMMS PRO installation is used by a large number of app developers and end users, you may want to operate a maintenance window. This is a fixed time window, e.g. each Monday 20:00-0:00, in which the production system may be taken down for software updates and other maintenance. Users can then plan for this.

Disaster Recovery
--------------------

.. image:: images/error-128.png
   :align: right
   :scale: 75

Depending on the nature of your PRO applications you need to take appropriate measures to deal with 'disasters' such as server, disk or connection failure. It is best to refer to you IT department for appropriate solutions. Some issues to consider:

#. Recovery of accidentally deleted AIMMS projects: app developers may accidentally delete their project files. We believe that the use of a version control system, such as Git, is the best 'first-line-of-defense' against this. Use of automated daily or even more frequent back-ups is an alternative.

#. Recovery from disk failure: this is typically handled by restoring a back-up. The amount of work lost will depend on the back-up interval. Distributed version control systems like Git are an alternative to be resilient against disk failures.

#. Recovery from server failure: this is typically done with back-up servers, 'hot standby' or 'cold standby'. The recovery times will depend on equipment and procedures. In addition, the new PRO 'scale-out' functions may already offer resilience against server failure as any machine in the PRO cluster can take on the job management automatically.

#. Recovery from data-center incidents such as fire: this is dealt with by having multiple data centers, again either 'hot standby' or 'cold standby' or even mirrored.

There is obviously a trade-off between the level of resilience and time required to recover and costs. Each business will have to make its own trade-offs here.


