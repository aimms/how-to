Change AIMMS PRO On-Premise Service Account
=====================================================
.. meta::
   :description: How to change the account AIMMS PRO On-Premise is running on.
   :keywords: PRO, account


By running a process using a particular account, you can access resources particular to that account. AIMMS jobs are started on the PRO service using the same account that the PRO service is running on. To specify a different account for AIMMS jobs, we need to run the PRO service on the new account. 

This article explains the procedure to change the PRO account that runs the AIMMS jobs.

.. warning:: 

    When you run on an account that is not the system account, it can affect access to resources and cause errors. In this case, please consult with your system administrator. For instance, this may affect the Single-Sign On. See   `Link to Active Directory for Non-AD Member Servers <https://documentation.aimms.com/pro/ad-man-non-member.html>`_, but you may also want to check   `here <https://docs.vmware.com/en/VMware-Workspace-ONE-UEM/1811/WS1-Kerberos-Constrained-Delegation-Secure-Email-Gateway-V2/GUID-AWT-KCD-ASSIGNDELEGATIONRIGHT.html>`_   or `this one <https://help.sap.com/viewer/e3b264fbc92e4a10b21163d488966b0f/3.1/en-US/d5e69921945345fe910e527fbc1c3f73.html>`_


Overview
--------------------

AIMMS jobs are run on the PRO On-Premise server with the name ``AimmsPROSession.exe``. 

.. image:: images/AimmsPROSession1.png
    :align: center

|

The default account for these jobs is ``SYSTEM``.

To change the account for the service, you'll follow this process:

1. Stop the PRO services.

2. Change the service in Windows Services.

3. Start the PRO services again.

4. Test the job and verify the change in Windows.

We'll go step-by-step in more detail below.

Stopping the Service in AIMMS PRO On-Premise
---------------------------

Before making changes to the service, it is a good idea to stop those services.

Log in as an admin on the PRO service and stop the services.

.. image:: images/StartStopServices1.png
    :align: center


|

Changing the Service in Windows Services
-----------------------------------------
#. Open up the Services app on your Windows system. (You can find it by typing "services" in the Windows menu search.)


#. Select the AIMMS PRO 2 service. By default, the ``SYSTEM`` account is used for this service.

    .. image:: images/SelectAIMMSPROService1.png
       :align: center
    
    
#. Right-click on the process and select :menuselection:`Properties` from the menu.

    .. image:: images/SelectProperties0.png
       :align: center

#. Go to the tab :menuselection:`Log On` and select the account the AIMMS PRO 2 service will use next time it is started.

    .. image:: images/PropertyTabLogOn0.png
       :align: center
    
You can fill in another account, such as a service account. Find out more in `Microsoft Documentation: Service accounts <https://docs.microsoft.com/en-us/windows/security/identity-protection/access-control/service-accounts>`_.

Restarting the Service in PRO On-Premise
-----------------------------------

Log in again as an admin on the AIMMS PRO service and start the services again:

.. image:: images/StartStopServices1b.png
    :align: center

|

Verifying the Change in Windows Services
----------------------------------------

Open up the Services app again.

.. image:: images/SelectAIMMSPROService1b.png
    :align: center

|

The service is now using the new account (in this example, ``chris``).

Verifying the change in Windows Task Manager
----------------------------------------------

Start the same job again. Then open the Windows Task Manager and go to the *Details* tab:

.. image:: images/AimmsPROSession1b.png
    :align: center

|

The account used for this AIMMS job is now ``chris``.

Test Project
----------------

To test for yourself, you can use the simple WinUI model :download:`WaitABit.zip <model/WaitABit.zip>`. 

After publishing and launching this app, just press the huge button to start an AIMMS job that waits for five minutes on the AIMMS PRO On-Premise server.





