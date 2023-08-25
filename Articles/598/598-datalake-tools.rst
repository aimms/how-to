DataLake Tools
========================
.. meta::
   :keywords: datalake, azure, sas token, container
   :description: This article is for modelers who want to compare profiler runs of deployed applications.

.. image:: https://img.shields.io/badge/AIMMS_4.96-ZIP:_ProfilerRunCompare-blue
   :target: https://github.com/aimms/profiler-run-compare/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_4.96-Github:_ProfilerRunCompare-blue
   :target: https://github.com/aimms/profiler-run-compare

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-developer-12/profilerruncompare-app-1328


Introduction
-------------
For an existing application, especially one that has several features, a small change in data, environment, or code occasionally leads to unexpectedly different runtimes. 
The location in the code of that application, where these differences manifest themselves, is not always obvious. 
In such circumstances, comparing profiler results may be a useful next step to locate the code where the significant differences in runtimes manifest themselves.

Instructions 
-------------

This chapter is divided into three sections:

#. Application Preparation
#. New SAS Token Page
#. Configuring ADLS Page


Application Preparation
~~~~~~~~~~~~~~~~~~~~~~~
To use this application, you can either:

#. Use it on the cloud: 
    To do that, you will need to download the application, 

#. Use it locally:



New SAS Token Page
~~~~~~~~~~~~~~~~~~~~
In the next subsection, we will be comparing the results of runs made.  Doing such a comparison, you don't want to be distracted by differences that have a difference cause than the cause at hand.

For instance, when changing the cloud provider from AWS to Azure, the results should not be fogged by different database contents or different versions of the same application.

.. image:: images/new-sas-token.png
    :align: center

Configuring ADLS Page
~~~~~~~~~~~~~~~~~~~~~~



Minimal Requirements
--------------------   

To work with this toolkit, you will need a PRO Cloud account with a datalake.

.. spelling:word-list::

   github