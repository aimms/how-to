Single-User License Troubleshooting
=====================================

.. meta::
   :description: Resolving AIMMS license update issue regarding the nodelock file.
   :keywords: license, error, nodelock, update, read

Error: Update failed - The nodelock file is read-only
-----------------------------------------------------

**Issue:** You are unable to update your license because the nodelock file is read-only. 

.. image:: images/nodelock-readonly.png
    :align: center

|

**Cause:** Windows has locked the file as read-only, blocking the update process. 

**Solution:** In Windows File Explorer, navigate to ``C:\ProgramData\AIMMS\Nodelocks``. Right-click the ``.lock`` file for the relevant license number and select ``Properties``. 

Uncheck the box ``read-only`` and click ``OK``. 

.. image:: images/uncheck-read-only.png
    :align: center

|

If the error occurs again, follow the steps below to completely reset the license activation.

1.  In AIMMS, go to :menuselection:`Tools > License > License Configuration` and click :menuselection:`Deactivate`.

.. image:: images/license-config.png
    :align: center

.. image:: images/deactivate.png
    :align: center

|

2.  Click :menuselection:`Yes` in the :menuselection:`Deactivate Current License` dialog that appears.

.. image:: images/deactivate-yes.png
    :align: center

|

3.  Follow the prompts in the :menuselection:`Deactivation Wizard`. A success notification appears. Exit AIMMS.

.. image:: images/deactivate-success.png
    :align: center

|    
    
.. note::
    If you cannot deactivate the license due to the nodelock, contact `AIMMS Support <mailto:support@aimms.com>`_ and our support team can deactivate the license for you. Meanwhile, you may continue the steps below for an emergency activation which allows you to use your license for 7 days.

4.  In Windows File Explorer, navigate to the license files location at ``C:\ProgramData\AIMMS`` (or ``C:\ProgramData\Paragon Decision Technology`` for older AIMMS versions).

Delete the relevant license files in the ``\Licenses`` and ``\Nodelocks`` subfolders.

.. image:: images/program-data.png
    :align: center

|    

5.  Restart AIMMS and activate the license again.

