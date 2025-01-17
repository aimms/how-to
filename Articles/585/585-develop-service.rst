Develop, Test, and Deploy an AIMMS Service
============================================

.. Should be introduction, and just provide overview.
.. Should introduce two applications:
.. 1. CountTheStars
.. 2. FlowshopDatabase

:download:`Apps used to illustrate the development of services <model/apps.zip>`


Developing a useful and robust REST API service requires that it is carefully developed, tested, and deployed.
This article is an introduction to a series of articles that provide guidance in the development of such a service in AIMMS.

Selected aspects important to developing any service are:

#.  **Implementation**: Coding and testing the service.  Which we will do using an AIMMS project. 

#.  **Usage**: There are two ways in which a service is used:

    #.  For its very purpose - a client app that uses the service. Discussions about purpose are outside the scope of this series of articles.
    
    #.  For testing.

    As a REST API service can be used by various separate clients, different software systems can be used to develop the various clients involved.

#.  **Deployment**: Which computer hosts the service? Can it be run in parallel? Is the usage of the service secure?
    The effort required to answer such questions depends on whether the deployment is on AIMMS Cloud or AIMMS PRO On-Premise.

Before we dive into the details of the above, let me introduce the background against which we illustrate the above.

Story
------

The service developed is intentionally trivial; count the number of ``*``'s in a couple of lines.
This is a "Hello World" type of story - it illustrates the basic techniques.
An additional advantage of this story is that it is trivial to test whether the response 
given by the service is correct.

Note that a service implemented in AIMMS can also implement an interesting optimization model; 
however, optimization applications are outside the scope of this article series.

Overview of Associated Articles
--------------------------------

There are two parts of this series of articles:

#.  In the first part we discuss the service itself.

#.  In the second part clients in various languages are presented, and 
    clients with different purposes.

First Part: Developing the Service 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

#.  :doc:`../585/585-development-architecture` Development architecture: an overview of the applications involved and 
    their interactions.

#.  :doc:`../585/585-example-conventions` A small side note containing the conventions used in the examples.  
    In addition, it explains how each of the client apps presented in this series can be configured.

#.  :doc:`../585/585-AIMMS-server-app` 
    Coding and internal testing of the service is discussed here.
    In addition, we discuss how to deploy the service in two ways.

Second Part: Available Client Implementations
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Once a service is available it can be used. In this part, we present several clients to the same service:

#.  **Python**: See :doc:`../585/585-Python-client` A small `Python <https://www.python.org/>`_ app is shown. 
    This app also illustrates the elegance of the `Python requests <https://pypi.org/project/requests/>`_ library.

#.  **VBA**: See :doc:`../585/585-VBA-client` A small Visual Basic for Applications 
    (`VBA <https://learn.microsoft.com/en-us/office/vba/library-reference/concepts/getting-started-with-vba-in-office>`_) 
    app inside an Excel workbook is shown. 
    The VBA app uses the win HTTP 5.1 library.

.. #.  **AIMMS**: See :doc:`../585/585-AIMMS-client` 
..     The AIMMS Client App presented illustrates selected stress tests for the service at hand.
    
References
------------

#.  AIMMS PRO REST API method documentation:

    #. Obtain a .json file describing the `OpenAPI 3.0 specification of AIMMS PRO REST API <https://documentation.aimms.com/cloud/rest-api.html#aimms-pro-rest-api>`_ for your AIMMS Cloud environment.

    #. Import that .json file using the online `Swagger Editor <https://editor-next.swagger.io/>`_

#.  The `AIMMS DataExchange library <https://documentation.aimms.com/dataexchange/index.html>`_, in particular `Providing REST APIs <https://documentation.aimms.com/dataexchange/rest-server.html>`_.

Next
-----------

:doc:`../585/585-development-architecture`



