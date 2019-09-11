Migrate PRO Server to Another Machine
===============================================================

.. meta::
   :description: How to migrate your PRO server to another machine in 10 steps.
   :keywords: PRO, Server, migrate


This article explains how to migrate your PRO server to a new machine.

.. note:: 

   This article does not include migrating the license server.

 
1.	Stop the AIMMS PRO Services from within the *AIMMS PRO Configurator*.

.. image:: images/1_img.png

2.	Go to *Backup Management* and click *Backup Now*.

.. image:: images/2_img.png

3.	Go to the tab *PRO Configuration* and copy all settings in a text document, so that during PRO configuration on the  new machine you can enter these settings again.
 
.. image:: images/3_img.png
 
4.	On the new machine, create the AIMMS PRO data folder at the desired target location; by default, the AIMMS PRO data folder is at ``C:\ProgramData\AimmsPRO``.

5.	Copy the subfolders ``Backup`` and ``Data`` from the original machine to the new machine.

6.	Install **the same version** of PRO onto the new machine.

**It is very important that you install exactly the same version, because normal upgrades are likely to fail when migrating. After the migration is successful you can upgrade to another version of PRO.**

7.	Once the installation is done, you should be redirected to the page *AIMMS PRO Configurator*. If you changed the username/password/schema for the database user, please enter the same here.

.. image:: images/4_img.png

8.	Go to the tab *PRO Configuration* and enter the values that you saved from the original configuration in step 3.

9.	Go to the tab *Backup Management*, select the backup you made in step 2, and click *Restore Now*.

10. Go to *Start/Stop Services* and start the service.

You have now migrated your PRO server!
