.. |image1| image:: /Images/159-exchange-data-with-cloud/image1.png
.. |image2| image:: /Images/159-exchange-data-with-cloud/image2.png
.. |image3| image:: /Images/159-exchange-data-with-cloud/image3.png
.. |image4| image:: /Images/159-exchange-data-with-cloud/image4.png
.. |image5| image:: /Images/159-exchange-data-with-cloud/image5.png

Exchange data with the AIMMS Cloud Platform
===========================================

.. meta::
   :description: How to set up a framework for sending data via the AIMMS Cloud platform.
   :keywords: cloud, exchange, api, configure, network

Introduction
-------------

Most AIMMS applications import data from other systems and/or export
data to other systems, beyond manual file upload into the app. For
on-premise AIMMS installations that exchange data with other on-premise
systems this can all be arranged using the standards, procedures and
custodians of that on-premise environment. This document describes the
options for exchanging data between on-premise systems and AIMMS apps
running on the AIMMS Cloud Platform.

|image1|

*Figure 1 – Exchanging data between on-premise systems and AIMMS apps
running in the AIMMS Cloud Platform*

In this context the ‘application database’ is the database from/to which
the AIMMS project reads/writes data (not to be confused with the AIMMS
PRO database where apps, users and configuration of the AIMMS PRO
instance are stored). This database is a paid option for the AIMMS Cloud
Platform service.

Option Overview
---------------

We support three options for exchanging data between on-premise systems
and AIMMS apps running on the AIMMS Cloud Platform. Each option has its
application, no universal recommendation can be given.

1. Via a connection with the Application Database that can form part of
      a customer’s set-up in the AIMMS Cloud Platform.

2. Via the AIMMS PRO API, using the commands to upload and download
      files to/from PRO Storage.

3. Via a web-service offered on the internet from the on-premise IT
      infrastructure with which the AIMMS apps interact using the AIMMS
      http-client library.

In the past we have supported other options such as a direct connection
between the AIMMS apps in the AIMMS Cloud Platform and on-premise
databases. We no longer support these as these options proved not secure
enough, potentially slow down UI response times because of latency and
proved too demanding for maintenance and support from both sides.




Option 1: Via a connection with the Application Database
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Here the Application Database in the AIMMS Cloud Platform acts as
decoupling connection point. On-premise systems can exchange data with
this database (currently MySQL only), for example via syncing or via
import or export components. AIMMS does not
offer such components as there are many such components available both
commercially and as open-source.


|image2|

*Figure 2 – Exchanging data via the application database in the AIMMS
Cloud Platform*

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

Option 2: Via the AIMMS PRO API
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The AIMMS PRO API allows direct interaction with the PRO Back End and
includes functions to upload or download files to/from AIMMS PRO
Storage. AIMMS PRO Storage is the file system to which the AIMMS Apps
running on AIMMS PRO have access. The on-premise environment will
require a custom application (Java or .net) to access the AIMMS PRO API
for this purpose.


|image3|

Figure 3 – Exchanging data via the AIMMS PRO API

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

Option 3: Via a web-service
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

With the HTTPS Client Library for AIMMS an app developer can add
functionality to an AIMMS app to interact with a web-service, for
example retrieving data or submitting data. The on-premise environment
needs to be extended with appropriate web services that are exposed to
the internet, custom programmed for the application.


|image4|

*Figure 4 – Exchanging data via custom web services*



Knowing the authentication and communication protocol of the bespoke web
service, the developer of the AIMMS app can use the AIMMS HTTPS Client
Library to add a procedure to the app that writes data to this web
services or retrieves data from that web service.

For this option the information security measures are completely in the
hands of the developers of the web service offered from the on-premise
platform.

.. _deployment-exchange-data-more-details:

More details on AIMMS Cloud Platform architecture
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The following diagram depicts the part of the
**AIMMS Cloud Platform** architecture relevant for exchanging data. The
application databases are placed in customer-specific VPCs that include
gateways to receive incoming VPN connections.



|image5|

*Figure 5 – Simplified diagram showing the relevant parts of the AIMMS
Cloud Platform architecture*


Related Topics
----------------
* **AIMMS Documentation:** `AIMMS Cloud Platform <https://manual.aimms.com/cloud/>`_

.. include:: ../../includes/form.def



