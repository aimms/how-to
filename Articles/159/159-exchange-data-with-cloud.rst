.. meta::
   :description: How to set up a framework for sending data via the AIMMS Cloud platform.
   :keywords: cloud, exchange, api, configure, network

Exchange data with the AIMMS Cloud Platform
===========================================

This document aims to introduce you to the options for exchanging data with the `AIMMS Cloud Platform <https://documentation.aimms.com/cloud/>`_.

Using the Azure Data Lake Storage Gen2 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Every AIMMS Cloud account is by default equipped with an Azure Data Lage Storage Gen2. It can be utilized for data integration by using the Azure API's. 
An article describing this functionality in depth `can be found here <file:///C:/Users/Roxanna/Documents/git/how-to/_build/html/Articles/594/594-adls-data-integration.html>`_.
You can find an article about `how to use it for data integration from an external source here <file:///C:/Users/Roxanna/Documents/git/how-to/_build/html/Articles/595/595-accessing-the-azure-datalake-externally.html>`_.


Via an AIMMS Cloud Application Database 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Notify AIMMS that you would like to use the Application Database into your AIMMS Cloud, we will take the first steps to create the database. 
With the database ready to use, your IT team then needs to complete the parametrization and VPN tunnel set-up, as described `in detail here <https://documentation.aimms.com/cloud/db-config.html>`__. 
You can now put in place the writes and reads to and from the application database from your information systems. Feel free to `read more in depth  here <https://how-to.aimms.com/Articles/596/596-mysql-db-cloud.html>`_.

.. _figure-01-159:

.. figure:: images/image2-cloud-app-db.png
   :align: center

   Accessing the application database in the AIMMS Cloud Platform


All traffic takes place via a VPN tunnel that is initiated from the
on-premise environment, so there is no need to permit incoming
connections with associated security risks. This VPN (Virtual Private
Network) tunnel offers high information security because it creates a
virtual private connection by encapsulating and encrypting all data
packets.

Via a Remote Database
^^^^^^^^^^^^^^^^^^^^^^

This case applies when you have a remote database, either in your own data center or elsewhere with a cloud provider. 
Please be aware that you might experience performance degradation because of latency and/or bandwidth limitations, most noticeable in apps that require a lot of database interaction when the user is interacting with the app. 
But in many cases we see this work well. 

Technically there currently are two options: with or without VPN. 

For the VPN route, AIMMS needs to add a load balancer to the VPC on the AIMMS Cloud Platform. 
Your IT team then needs to complete the parametrization and VPN tunnel set-up, as described `here <https://documentation.aimms.com/cloud/db-config.html#adding-a-vpn-connection>`__. 
You can then configure your network access such that the AIMMS apps in the AIMMS Cloud Platform can directly access the required database on your premises, 
in your data center or in the cloud. AIMMS database support functions in our modeling language can be found `here <https://documentation.aimms.com/functionreference/data-management/database-functions/>`__. 

.. _figure-02-159:

.. figure:: images/image3-on-premise-db.png
   :align: center

   Directly accessing a remote database from AIMMS apps


The other route is without a VPN. This probably requires you to whitelist the IP-ranges of the AIMMS Cloud Platform in your firewalls. 
The AIMMS apps will issue commands to the remote database in the same way as when you are using a VPN connection to a remote database.

.. seealso::
   There is a course about **Databases & Data Connection** on our Academy, let's get certified!


Via the AIMMS PRO Storage
^^^^^^^^^^^^^^^^^^^^^^^^^

No need for any initial set-up or configuration. 
In order to get data files from the on-premise systems to an AIMMS app
running on the AIMMS Cloud Platform, the on-premise custom application
will need to upload the appropriate file(s) to the appropriate folder in
the AIMMS PRO Storage. The developer of the AIMMS app will then use
AIMMS functions to read these data files into the app.
Read more on AIMMS PRO Storage `here <https://how-to.aimms.com/Articles/117/117-Uploading-and-Downloading-files.html>`__. 
Programs can be created for writing and reading files to and from AIMMS PRO Storage using Java, C++ or C#. 
Documentation for the PRO API can be found `here <https://documentation.aimms.com/pro/api.html>`__. 

.. _figure-03-159:

.. figure:: images/image4-pro-api.png
   :align: center

   Transferring files to AIMMS PRO Storage using the AIMMS PRO API

The PRO API shares information security measures with the AIMMS PRO
Portal such as lock-out on repeated logon failures, https encryption,
optional IP range filtering and security event logging. In addition, the
communications are secured by temporary tickets that can be seen as
security tokens.

Via a Web Service
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

AIMMS apps can be configured to retrieve data from external web services or post data to those web services. 
The AIMMS app will act as a client here, using the AIMMS HTTP Client Library that supports REST API: :doc:`../306/306-http-client-library-overview`. 
The AIMMS Data Exchange library can be used for format conversions between AIMMS Identifiers and various file formats: https://documentation.aimms.com/dataexchange/index.html.  

.. _figure-04-159:

.. figure:: images/image5-web-service.png
   :align: center

   Using REST API calls to web services to exchange data with an AIMMS app



Knowing the authentication and communication protocol of the bespoke web
service, the developer of the AIMMS app can use the AIMMS HTTPS Client
Library to add a procedure to the app that writes data to this web
services or retrieves data from that web service.

For this option the information security measures are completely in the
hands of the developers of the web service offered from the on-premise
platform.

Examples
^^^^^^^^^
Examples of how our customers have arranged their data exchange:

a.	Every 5 minutes a web-service is called to retrieve the latest data which is then immediately processed by the AIMMS app.
#.	From an on-premise database all new customer orders are written once a day to the cloud-based application database. 
#.	User can hit a button in the AIMMS app that triggers a data import from an on-premise database containing the production planning so that they are working on the latest data.
#.	An AIMMS application writes XML files to AIMMS PRO storage. An on-premise utility retrieves those files once every hour using the PRO API.


More details on AIMMS Cloud Platform architecture
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The following diagram depicts the part of the
**AIMMS Cloud Platform** architecture relevant for exchanging data. The
application databases are placed in customer-specific VPCs that include
gateways to receive incoming VPN connections.

.. _figure-05-159:

.. figure:: images/image6-more-details.png
   :align: center
   
   Simplified diagram showing the relevant parts of the AIMMS Cloud Platform architecture







