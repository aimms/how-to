How do I change the account AIMMS PRO is running on?
=====================================================

By running a process using a particular account, resources can be acquired that are particular to that account. To make AIMMS jobs use such an account, we need to make the AIMMS PRO service run using that account. This article provides a detailed and verifiable step by step procedure to achieve just this.

.. note:: since the end goal is that you’re running on another account then the system account, it might be possible that this new account does not allow you access to resources you previously could; please consult with your system administrator if this is the case.


Start situation
---------------

AIMMS jobs are run on an AIMMS PRO server with the name ``AimmsPROSession.exe``. 

.. image:: images/AimmsPROSession1.png
    :align: center

The default account for these jobs is ``SYSTEM``.

Stop AIMMS PRO services
------------------------

Log in as an admin on the AIMMS PRO service and stop the services

.. image:: images/StartStopServices1.png
    :align: center


Start Services app
----------------------------------------

Open up the services window, e.g. by hitting the windows button and typing ‘Services’

.. image:: images/StartServices1.png
    :align: center


Select AIMMS PRO 2 service
-----------------------------------------

.. image:: images/SelectAIMMSPROService1.png
    :align: center
    
Select properties
-----------------------------------------

.. image:: images/SelectProperties0.png
    :align: center

Actually change account
-----------------------------------------

.. image:: images/PropertyTabLogOn0.png
    :align: center
    
Here you can fill in a service account, see also: `service account <https://docs.microsoft.com/en-us/windows/security/identity-protection/access-control/service-accounts>`_

Start the service again
------------------------

Log in again as an admin on the AIMMS PRO service and stop the services

.. image:: images/StartServices1b.png
    :align: center


Verify via Services
----------------------------------------

Open up the services window, e.g. by hitting the windows button and typing ‘Services’

.. image:: images/StartServices1b.png
    :align: center

Verify by starting job
----------------------------------------

Starting the same job again, and opening up the task manager, details tab:

.. image:: images/AimmsPROSession1b.png
    :align: center

The account used for AIMMS jobs is now ``chris``.








