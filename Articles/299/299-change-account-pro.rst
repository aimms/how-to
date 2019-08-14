How do I change the account AIMMS PRO is running on?
=====================================================

IMPORTANT: since the end goal is that you’re running on another account then the system account, it might be possible that this new account does not allow you access to resources you previously could; please consult with your system administrator if this is the case.

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










