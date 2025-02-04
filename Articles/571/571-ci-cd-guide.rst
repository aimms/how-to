Using CI/CD with AIMMS
======================

Wouldn't you like a technology whereby you can develop your application and upon completing it, have an automated process that tests and realizes the deployment in a safe and guaranteed manner?

What is CI/CD?
--------------
CI/CD introduces ongoing automation and continuous monitoring throughout the lifecycle of apps, from integration and testing phases to delivery and deployment. These are desirable characteristics to have in application development and can be implemented with the context of AIMMS. The below description seeks to provide the required steps to create a CI/CD pipeline for your AIMMS projects. Given the ample possibility of platforms to implement your pipeline, our guide is agnostic.

Each pipeline can be setup in a way that better suits your companies needs, such as controlling when a new version is published in testing environment and when it will be published in production. This logic is not described below. We also do not discuss the triggers for each automated step, since this will be adapted to your companies context.

.. note::

   You can find some basic reference material on these topics below:
   
   * https://en.wikipedia.org/wiki/Continuous_integration
   * https://en.wikipedia.org/wiki/Continuous_deployment
   * https://en.wikipedia.org/wiki/Continuous_delivery 


Before you Start
----------------------------

To create your CI/CD process, it is important that the project source code is kept in a repository that can be accessed by your pipeline.

Some of the below steps utilize the AIMMS command line from within your CI/CD server. In these cases, you will need to setup appropriate licenses for this. Please reach out to support@aimms.com if you need assistance.

Some of the below steps utilize the AIMMS Cloud Rest API services available. If you do not have an AIMMS Cloud account yet, please reach out to support@aimms.com or your account manager.

We will seek to present some alternatives (if available) in case one of the above is not applicable for your setup.

CI - Building an AIMMSPACK
----------------------------

Utilizing the AIMMS command line options, you can automate the process of building an aimmspack to be deployed. This is available for both Windows and Linux OS.

Windows example:

.. code-block:: console

    Aimms.exe --export-to myaimmspack.aimmspack  "PROJECTPATH/PROJECTNAME.aimms"

Linux example:

.. code-block:: bash

    AimmsCmd --export-to myaimmspack.aimmspack  "PROJECTPATH/PROJECTNAME.aimms"

More information can be found here at `AIMMS command line options <https://documentation.aimms.com/user-guide/miscellaneous/calling-aimms/aimms-command-line-options.html>`_ .


In order to publish an application to the AIMMS Cloud, you must first generate an aimmspack file from your project. More information can be found at `publishing applications documentation <https://documentation.aimms.com/pro/appl-man.html#publishing-applications>`_ .

.. note::

    Using ``--export-to`` is the equivalent to executing the “Export End User Project” using the last saved configuration of this project. The configuration is saved in the ``/MainProject/Tools/ProjectExport.xml`` file.
    The last saved configuration is by default all files in the project folder.
    You can exclude files via the ExcludedFile element, for instance:
    ``<ExcludedFile>log</ExcludedFile>``
    and you include files that are not in the project folder via the IncludeFile element, for instance files in repository libraries or system libraries:
    ``<IncludedFile>AimmsPROLibrary\AimmsPRO-%AIMMSVERSION%.aim</IncludedFile>``
    The IncludeFile element is rarely used lately.
    You can also find more information in this `command line export how-to <https://how-to.aimms.com/Articles/128/128-Intellectual-Property.html#command-line-export>`_ .


.. note::

    If you do not have a license available on your CI/CD server, you can create a git-hook on your development environment to automate the process of creating the aimmspack locally at every commit and then committing the generated aimmspack to the repository, skipping this step in the pipeline.
    A general overview of this is described in `this community post <https://community.aimms.com/aimms-developer-12/export-to-aimmspack-from-azure-devops-pipeline-807>`_ .
    Please be aware that development environments are usually not fully standardized; this may cause referencing information not available to other machines; hence, the pipeline is not guaranteed to be reproducible anymore.


CI - Unit Test
------------------

An integral part of quality assurance is unit testing. In the context of CI/CD, you may execute previously configured unit tests by using the AIMMS command line options.

You can use the ``--run-only argument`` to specify a procedure to be run within AIMMS (such as the tests).

Windows example:

.. code-block:: console

    Aimms.exe "PROJECTPATH/PROJECTNAME.aimms" --run-only proc_unitTests

Linux example:

.. code-block:: bash

    AimmsCmd "PROJECTPATH/PROJECTNAME.aimms" --run-only proc_unitTests


You can find more information on using the AIMMS unit test library can be found in this guide on `effective use of the unit test library <https://how-to.aimms.com/Articles/216/216-effective-use-unit-test-library.html#test-driven-development-using-the-aimmsunittest-library>`_ and `in our documentation <https://documentation.aimms.com/unit-test/index.html#unit-test-library>`_ .

Your pipeline can verify the log folder within the project to seek information from the generated xml log file regarding errors or warnings and deciding whether to continue with the deployment or not.

.. note::

    Unit testing can be an integral part of your development process. This can be achieved by simply running the unit tests when opening and/or closing the application in an development environment using the PostMainInitialization or PreMainTermination. More information on these topics can be found `in this document <https://documentation.aimms.com/language-reference/data-communication-components/data-initialization-verification-and-control/model-initialization-and-termination.html>`_ .
    You can compare the execution steps that AIMMS takes in each case with this `how to article on initialization and termination <https://how-to.aimms.com/Articles/351/351-app-initialization-termination-with-libraries.html>`_ .


CI - Acceptance Testing
----------------------------

Acceptance testing can go beyond running unit tests and includes UI manipulation to test your WebUI application. This requires additional third-party software to which AIMMS does not provide any direct integration or support towards. There are many options available and a simple search for “Web application testing framework” can provide you with options.


CI - Scenario Testing 
----------------------------

The objective of scenario testing is to load the necessary input data (either a prepared case file or through an integration process), run this scenario, retrieve the results, and validate them. Given that these tests require more time, it is recommended to include them only in necessary steps of your CI/CD pipeline.

How you configure your validation process is equally as important. There are many reasons why a model can produce different results even though no bugs were introduced in a new version:

* If this is a MIP model, you may have different results every run, given how the algorithm works. There are options to avoid this such as the `deterministic mode <https://documentation.aimms.com/platform/solvers/cplex.html#parallel-cplex>`_ .
* If there is a change in the selected solver or version, the results may differ due to changes in the solver algorithm, gap definitions and multi-optimal solutions.
* Reformulation and/or new constraints/variables to the model may impact results.

It may be wise to create broader criterion for success/failure in scenario testing, such as a range for the objective function, certain variable behavior, run time and optimal status.

Using a scenario test can also validate that there are no new bugs causing infeasibility and that the run time of the model has not grown excessively.


CI - Integration Testing 
-------------------------------

Integration testing involves validating that your application within the AIMMS Cloud environment is correctly integrated to other systems/solutions.

Some examples are

* Running specific procedures in AIMMS that import/export data: Does the data import correctly? Does the output data export correctly? Testing networking.

* Executing external calls to the AIMMS Cloud: Am I able to access the AIMMS Cloud and run the application with the desired outcome?

* Validating that the AIMMS Cloud environment is able to publish and run a model:  Am I able to manage the AIMMS applications externally?


CI - Testing within the Cloud Environment
------------------------------------------------

You may choose to conduct the above tests within the cloud environment. To do so, you will be required to publish the application on the cloud and run specific procedures.

The general steps required to achieve this is 

* Publish the application using the AIMMS REST API to your QA/Test environment.  You can find some documentation `here <https://documentation.aimms.com/cloud/rest-api.html#managing-apps>`_ .

* Control which users can access this version via the Rest API

* Run specific tests using the AIMMS REST API based on predefined tasks within your project. You will start the task via the Rest API during the pipeline. Here is the `documentation <https://documentation.aimms.com/cloud/tasks.html#running-tasks>`_ and a `general overview <https://how-to.aimms.com/Articles/561/561-openapi-overview.html>`_ .

We recommend that you do not make the test versions of your application accessible to end-users until they have been validated in the acceptance tests. Hence you may want to delete versions used in tests like the above.

CD - Release
--------------

Depending on how your pipeline is configured, releasing to production may require rebuilding your aimmspack. You can follow the same steps above.

Release and feature notes, as well as version number control (which can not repeat itself within the AIMMS Cloud environment) are also best practices for this.

CD - Deployment on the Cloud
------------------------------

Publishing and deployment of the application on the cloud can be achieved via the AIMMS REST API. Please follow the information on the `app management documentation <https://documentation.aimms.com/cloud/rest-api.html#managing-apps>`_ .

You can control which users can access to each application and version via the Rest API. 

