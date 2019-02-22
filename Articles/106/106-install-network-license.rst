
.. IMAGES

.. |license-mgr-icon| image:: /images/license-mgr-icon.png
    :scale: 150 %
.. |maintenance-mode| image:: /images/maintenance-mode.png
    :scale: 100 %
.. |license-config| image:: /images/license-config.png
    :scale: 100 %
.. |service-running| image:: /images/service-running.png
    :scale: 100 %
.. |no-license| image:: /images/no-license.png
    :scale: 100 %
.. |license-config-done| image:: /images/license-config-done.png
    :scale: 100 %
.. |rename-profile| image:: /images/rename-profile.png
    :scale: 100 %

    

.. BEGIN CONTENT

Install and Configure a Network License
============================================

.. meta::
   :description: How to set up an AIMMS Network License with the Network License Server.
   :keywords: license, network, install, setup

How to install a Network License with the Network License Server.

Installing the Network License Server
-------------------------------------

A network license is administrated by the Network License Server.

1. Download the **Network License Server** from `AIMMS Downloads <https://aimms.com/english/developers/downloads/download-aimms/aimms-network-license-server>`_.

2. Run the `AimmsLicenseServer...msi` installation file and follow the prompts in the Installation Wizard.

    


Installing the Network License
------------------------------

1. 
    Run the **AIMMS License Manager**.

    |license-mgr-icon|

2. 
    In AIMMS Network License Manager, go to *Maintenance > Maintenance mode*.

    |maintenance-mode|

3. 
    Go to *Maintenance > License > License Configuration*.

    |license-config|

    A *No License* dialog appears.

    |no-license|

4. 
    Follow the License Install Wizard prompts to install your license.

    A. License Number - Enter your license number
    #. License Protection - Select activation code
    #. Activation Code - Enter your activation code
    #. Nodelock Protection - Machine Nodelock is recommended for this license type
    #. Communication Method - If you are connected to the internet, *Online* activation is the fastest and easiest method. If you do not have internet access, choose *Offline* activation and complete activation later.

.. note::

    If you choose *Offline* communication, a request file is generated. You must go to `AIMMS Offline License Activation <https://aimms.com/english/developers/licensing/processing-request-files/>`_ and submit the request file to complete activation.

The license is now installed and appears in the *Network License Manager*.

    |license-config-done|


Configuring license and adding users
------------------------------------

1. 
    You can rename license profiles with nicknames. Single-click twice on the profile name to edit.

    |rename-profile|

2. 
    Add users in *Maintenance > License > Registered Users*. 

    For further instructions, read :doc:`../105/105-adding-named-user-licenses`.

    |

3. 
    Go to *Maintenance > Maintenance mode* to exit *Maintenance mode*.

    The service should start automatically. To double-check, go to *Maintenance > Start the Service*.

    |service-running|

    A dialog confirms "Service is already running."

.. note::

    If a message appears saying "Unable to Start the service," go to *Maintenance > Start the Service*.

Users can access an AIMMS Network License while connected to the network from any machine, if they are logged in with credentials that are registered in the AIMMS Network License Manager as Registered Users.

Related topics
---------------

*  AIMMS Knowledge: :doc:`../105/105-adding-named-user-licenses`
*  AIMMS Documentation: `Network License Server Manual <https://download.aimms.com/aimms/download/data/LicenseServer/AIMMS_net.pdf>`_ 
*  AIMMS Documentation: `License Server Installation and Configuration <https://manual.aimms.com/pro/license-server.html>`_ 

.. END CONTENT

.. include:: /includes/form.def

.. author: Jessica Valasek Estenssoro
.. checked by: -Khang Bui
.. updated: October 30, 2018