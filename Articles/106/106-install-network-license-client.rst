Install the Network License on a Client Computer
===================================================
.. meta::
   :description: How to activate an AIMMS Network License with the Network License Server.
   :keywords: license, network, install, setup


Installing the Network License Server
-------------------------------------

A network license is administrated by the Network License Server on a server computer.

1. Download the Network License Server from `AIMMS Downloads <https://www.aimms.com/support/downloads/#aimms-other-download>`_.

2. Run the ``AimmsLicenseServer...msi`` installation file and follow the prompts in the Installation Wizard.


Installing the Network License on a Client Computer
---------------------------------------------------

.. topic:: Prerequisites

   * Make sure you are connected to the same network as the server where AIMMS Network License Server is installed.
   * Install AIMMS (downloads of AIMMS can be found on our `Downloads <https://www.aimms.com/support/downloads/>`_ page).

1. Start AIMMS.

2. If no license is installed, a dialog will open automatically. If you previously installed a license, use the menu :menuselection:`Tools > License > License Configuration` and click :menuselection:`Install License` to specify a new license.

3. Select :menuselection:`Connect to the AIMMS License Server on your LAN` and click :menuselection:`Next`.

4. Type the name or IP address of the server computer (only specify a profile when you have multiple profiles) and click :menuselection:`Next`.

5. Click :menuselection:`Finish`.

Your new license is installed.

If you previously installed another license, move the **new license** to the top with the :menuselection:`Move Up` button and tick the checkbox. The next time you start up AIMMS this license will be used.

In case of any problems during installation, activation, or running AIMMS, please contact `AIMMS Support <mailto:support@aimms.com>`_.

.. seealso::

   * :doc:`106-install-network-license`
   * :doc:`../304/304-transfer-license-server`
   * `Network License Server Manual <https://download.aimms.com/aimms/download/data/LicenseServer/AIMMS_net.pdf>`_ 
   * `License Server Installation and Configuration <https://documentation.aimms.com/pro/license-server.html>`_ 