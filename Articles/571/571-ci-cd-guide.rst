What is CI/CD?
===============
CI/CD introduces ongoing automation and continuous monitoring throughout the lifecycle of apps, from integration and testing phases to delivery and deployment. These are desirable characteristics to have in application development and can be implemented with the context of AIMMS. The below description seeks to provide the required steps to create a CI/CD pipeline for your AIMMS projects. Given the ample possibility of platforms to implement your pipeline, our guide is agnostic.

Each pipeline can be setup in a way that better suits your companies needs, such as controlling when a new version is published in testing environment and when it will be published in production. This logic is not described below. We also do not discuss the triggers for each automated step, since this will be adapted to your companies context.


Prerequisites / Assumptions
============================

The source code for your project is kept in a repository that can be accessed by your CI/CD pipeline.

There is an AIMMS license available on your CI/CD server.

You are using the AIMMS cloud environment and have the AIMMS Rest API services available.

* Some alternatives may be presented below if one of the above is applicable to you


CI - Building an AIMMSPACK
==========================

Utilizing the AIMMS command line options, you can automate the process of building an aimmspack to be deployed. This is available for both Windows and Linux OS.

Windows example:
.. code-block::

    Aimms.exe --export-to myaimmspack.aimmspack  “PROJECTPATH/PROJECTNAME.aimms”

Linux example:
.. code-block::

    AimmsCmd --export-to myaimmspack.aimmspack  “PROJECTPATH/PROJECTNAME.aimms”

More information can be found here at `AIMMS command line options <https://documentation.aimms.com/user-guide/miscellaneous/calling-aimms/aimms-command-line-options.html>`_ .


In order to publish an application to the AIMMS PRO Cloud server, you must first generate an aimmspack file from your project. More information can be found at `publishing applications documentation <https://documentation.aimms.com/pro/appl-man.html#publishing-applications>`_ .


Observation: Using ``--export-to`` is the equivalent to executing the “Export End User Project” using the last saved configuration of this project.

.. note::

    If you do not have a license available on your CI/CD server, you can create a git-hook on your development environment to automate the process of creating the aimmspack locally at every commit and then committing the generated aimmspack to the repository, skipping this step in the pipeline.
    A general overview of this is described in `this community post <https://community.aimms.com/aimms-developer-12/export-to-aimmspack-from-azure-devops-pipeline-807>`_ .

-----------


