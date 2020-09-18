Install the Network License on a client computer
===================================================
.. meta::
   :description: How to activate an AIMMS Network License with the Network License Server.
   :keywords: license, network, install, setup


The AIMMS Network License Server allows you to install several AIMMS licenses for concurrent usage.


Installing the Network License Server
-------------------------------------

A network license is administrated by the Network License Server on a server computer.

1. Download the **Network License Server** from `AIMMS Downloads <https://www.aimms.com/support/downloads/#aimms-other-download>`_.

2. Run the `AimmsLicenseServer...msi` installation file and follow the prompts in the Installation Wizard.

    

Installing the Network License on a client computer
---------------------------------------------------
Prerequisites:

* Make sure you are connected to the same network as the server where AIMMS Network License Server is installed.
* Install AIMMS (downloads of AIMMS can be found on our `Downloads <https://www.aimms.com/support/downloads/>`_ page).

#. Start AIMMS.

#. If no license is installed, a dialog will open automatically. If you previously installed a license, use the menu *Tools > License > License Configuration* and click *Install License* to specify a new license.

#. Select *Connect to the AIMMS License Server on your LAN* and click *Next*.

#. Type the name or IP address of the server computer (only specify a profile when you have multiple profiles) and click *Next*.

#. Click *Finish*.

Your new license is installed.

If you previously installed another license, move the **new license** to the top with the *Move Up* button and tick the checkbox. The next time you start up AIMMS this license will be used.

In case of any problems during installation, activation, or running AIMMS, please contact `AIMMS Support <mailto:support@aimms.com>`_.

Related topics
----------------

* :doc:`106-install-network-license`
* :doc:`../304/304-transfer-license-server`