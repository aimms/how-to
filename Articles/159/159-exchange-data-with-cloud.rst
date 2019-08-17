

.. |basic-structure| image:: images/image1-basic-structure.png
.. |cloud-app-db| image:: images/image2-cloud-app-db.png
.. |on-premise-db| image:: images/image3-on-premise-db.png
.. |pro-api| image:: images/image4-pro-api.png
.. |web-service| image:: images/image5-web-service.png
.. |more-details| image:: images/image6-more-details.png

Exchange data with the AIMMS Cloud Platform
===========================================

.. meta::
   :description: How to set up a framework for sending data via the AIMMS Cloud platform.
   :keywords: cloud, exchange, api, configure, network

This document aims to introduce you to the options for exchanging data with the AIMMS Cloud Platform.

Introduction
-------------
Most AIMMS applications import data from other systems and/or export
data to other systems, beyond manual file upload into the app. For
on-premise AIMMS installations that exchange data with other on-premise
systems this can all be arranged using the standards, procedures and
custodians of that on-premise environment. 

This document describes the
options for exchanging data between on-premise systems and AIMMS apps
running on the AIMMS Cloud Platform. It is focused on ‘hands-free’ exchange excluding manual uploads and downloads.

|basic-structure|

*Figure 1 – Exchanging data between on-premise systems and AIMMS apps
running in the AIMMS Cloud Platform*

In this context the ‘application database’ is the database from/to which
the AIMMS project reads/writes data (not to be confused with the AIMMS
PRO database where apps, users and configuration of the AIMMS PRO
instance are stored). This database is a paid option for the AIMMS Cloud
Platform service.

Option Overview
---------------

We support four options for exchanging data between on-premise systems
and AIMMS apps running on the AIMMS Cloud Platform. Each option has its
application, no universal recommendation can be given.

1. Via a connection with the Application Database that can form part of
      a customer’s set-up in the AIMMS Cloud Platform.

#. Via a connection directly to an on-premise database

#. Via the AIMMS PRO API, using the commands to upload and download
      files to/from PRO Storage.

#. Via a web-service offered on the internet from the on-premise IT
      infrastructure with which the AIMMS apps interact using the AIMMS
      http-client library.

Please carefully consider latency when choosing an option; in particular Option 2 might hurt the responsiveness of apps.



Option 1: Via a connection with the Application Database
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Here the Application Database in the AIMMS Cloud Platform acts as
decoupling connection point. On-premise systems can exchange data with
this database (currently MySQL only), for example via syncing or via
import or export components. AIMMS does not
offer such components as there are many components available for that purpose, both
commercially and as open-source.


|cloud-app-db|

*Figure 2 – Exchanging data via the application database in the AIMMS Cloud Platform*

.. note::

   See https://dev.mysql.com/doc/index-connectors.html for details how to connect to MySQL databases.

All traffic takes place via a VPN tunnel that is initiated from the
on-premise environment, so there is no need to permit incoming
connections with associated security risks. This VPN (Virtual Private
Network) tunnel offers high information security because it creates a
virtual private connection by encapsulating and encrypting all data
packets.

More details of the relevant parts of the AIMMS Platform architecture
can be found in :ref:`deployment-exchange-data-more-details`.

Option 2: Via a connection directly to an on-premise database
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Here the AIMMS application will directly interact with an on-premise database, reading and/or writing data using the built-in AIMMS commands. 
Again all traffic takes place via a VPN tunnel that is initiated from the on-premise environment, so there is no need to permit incoming connections with associated security risks. This VPN (Virtual Private Network) tunnel offers high information security because it creates a virtual private connection by encapsulating and encrypting all data packets.

|on-premise-db| 

*Figure 3 - Exchanging data directly with an on-premise database*

Option 3: Via the AIMMS PRO API
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The AIMMS PRO API allows direct interaction with the PRO Back End and
includes functions to upload or download files to/from AIMMS PRO
Storage. AIMMS PRO Storage is the file system to which the AIMMS Apps
running on AIMMS PRO have access. The on-premise environment will
require a custom application (Java or .net) to access the AIMMS PRO API
for this purpose.


|pro-api|

*Figure 4 – Exchanging data via the AIMMS PRO API*

In order to get data files from the on-premise systems to an AIMMS app
running on the AIMMS Cloud Platform, the on-premise custom application
will need to upload the appropriate file(s) to the appropriate folder in
the AIMMS PRO Storage. The developer of the AIMMS app will then use
AIMMS functions to read these data files into the app.

The PRO API shares information security measures with the AIMMS PRO
Portal such as lock-out on repeated logon failures, https encryption,
optional IP range filtering and security event logging. In addition, the
communications are secured by temporary tickets that can be seen as
security tokens.

Option 4: Via a web-service
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

With the HTTPS Client Library for AIMMS an app developer can add
functionality to an AIMMS app to interact with a web-service, for
example retrieving data or submitting data. The on-premise environment
needs to be extended with appropriate web services that are exposed to
the internet, custom programmed for the application.


|web-service|

*Figure 5 – Exchanging data via custom web services*



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

.. _deployment-exchange-data-more-details:

More details on AIMMS Cloud Platform architecture
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The following diagram depicts the part of the
**AIMMS Cloud Platform** architecture relevant for exchanging data. The
application databases are placed in customer-specific VPCs that include
gateways to receive incoming VPN connections.

|more-details|

*Figure 6 – Simplified diagram showing the relevant parts of the AIMMS Cloud Platform architecture*


Related Topics
----------------
* **AIMMS Documentation:** `AIMMS Cloud Platform <https://manual.aimms.com/cloud/>`_





