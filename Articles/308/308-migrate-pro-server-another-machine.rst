Migrate PRO Server to Another Machine
===============================================================

.. meta::
   :description: How to migrate your PRO server to another machine.
   :keywords: PRO, Server, migrate, move, transfer

This article explains how to migrate your PRO server to a new machine. We will call these servers the original machine (source host) and the new machine (target host).

This can be broken down into five stages, discussed in detail below.

1. Stop the PRO services.
#. Backup the PRO configuration on the original machine.
#. Install PRO on the new machine.
#. Configure PRO on the new machine.
#. Restart the PRO services.

.. note::
   
   For instructions to migrate the license server, please see :doc:`../304/304-transfer-license-server`.

Stop the Services
-----------------------------------------------

Stop the AIMMS PRO Services from within the **AIMMS PRO Configurator**.

.. image:: images/1_img.png
   :aling: center

Backup the Configuration
-----------------------------------------------

1.	Go to :menuselection:`Backup Management` and click :menuselection:`Backup Now`.

.. image:: images/2_img.png
   :aling: center

2.	Go to the tab :menuselection:`PRO Configuration` and copy all settings in a text document, so that during PRO configuration on the new machine you can enter these settings again.
 
.. image:: images/3_img.png
   :aling: center

Install PRO on the New Machine
--------------------------------------------------------

1.	On the new machine, create the AIMMS PRO data folder at the desired target location; by default, the AIMMS PRO data folder is at ``C:\ProgramData\AimmsPRO``.

2.	Copy the subfolders ``Backup`` and ``Data`` from the original machine to the new machine.

3.	Install **the same version** of PRO onto the new machine.

.. important::

   It is very important that you install exactly the same version, because normal upgrades are likely to fail when migrating. After the migration is successful you can upgrade to another version of PRO.

Configuring the New Installation
--------------------------------------------------------

1.	Once the installation is done, you should be redirected to the page AIMMS PRO Configurator. If you changed the username/password/schema for the database user, please enter the same here.

.. image:: images/4_img.png
   :aling: center

2.	Go to the tab :menuselection:`PRO Configuration` and enter the values that you saved from the original configuration, as detailed above.

3.	Go to the tab :menuselection:`Backup Management`, select the backup you made earlier, and click :menuselection:`Restore Now`.

Restarting the Services
---------------------------------

Go to :menuselection:`Start/Stop Services` and start the service.

You have now migrated your PRO server!

.. seealso::

   * :doc:`../304/304-transfer-license-server`
   * `AIMMS PRO Server Administration <https://documentation.aimms.com/pro/admin.html>`_

