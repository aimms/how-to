.. IMAGES


.. |pending-status-info| image:: /Images/89-academic-license/pending-status-info.png
.. |interrupt-sequence| image:: /Images/89-academic-license/interrupt-sequence.png
.. |confirm-interrupt-sequence| image:: /Images/89-academic-license/confirm-interrupt-sequence.png

.. BEGIN CONTENT

Resolve License Issues
=========================

.. meta::
   :description: Resolving AIMMS license issues regarding personal nodelock, pending activation, and IP subnet.
   :keywords: license, error, nodelock, activation, ip, subnet

.. TOC

* :ref:`ref-personal-nodelock`
* :ref:`ref-pending-activation`
* :ref:`ref-ip-subnet`

.. End TOC

.. Part 1

.. _ref-personal-nodelock:

Error: This license can only be activated using a personal nodelock
--------------------------------------------------------------------

Issue:
^^^^^^
You cannot activate your Academic License with machine nodelock.  

Cause:
^^^^^^
You must select personal nodelock when activating an AIMMS Academic License. You must be connected to your school network.

Solution:
^^^^^^^^^
Go *Back* in license setup and select *Personal Nodelock*. 

Ensure that you are connected to your university network. If working remotely, connect to a VPN service provided by your university (if available).

.. End Part 1

.. Part 2

.. _ref-pending-activation:

Error: Pending Activation
---------------------------

Issue:
^^^^^^
You cannot activate your Academic License because you selected machine nodelock with offline activation.  

Cause:
^^^^^^
During setup you must select personal nodelock when activating an AIMMS Academic License. You must be connected to your school network. If you have chosen machine nodelock and offline activation, your license will show the status *Error, Pending Activation*.

Solution:
^^^^^^^^^
#. 
    Click *Pending Status Info* to cancel your license configuration. 

    |pending-status-info|

#. 
    Click *Interrupt Sequence* in the window that appears. Click *Yes* to confirm.

    |interrupt-sequence|
    |confirm-interrupt-sequence|

    Your license status returns to normal.  

.. mine shows "error" in status, not "normal"

3. 
    Try to activate your license again.

    Ensure that you are connected to your university network. If working remotely, connect to a VPN service provided by your university (if available).

.. note:: If an IP subnet error occurs, see :ref:`ref-ip-subnet` below.

.. End Part 2

.. Part 3

.. _ref-ip-subnet:

Error: The IP can only be activated from within a specified IP subnet
----------------------------------------------------------------------
Issue:
^^^^^^
You cannot activate your Academic License because your IP subnet does not match your university according to our database.

Cause:
^^^^^^
Some universities have multiple subnet ranges that are not recorded in our database, so an IP subnet error may occur even while you are connected to your university network.

Solution:
^^^^^^^^^^
Ensure you are connected to your university network and try activation again.

If the IP subnet error occurs even when connected to your university network, contact `AIMMS Support <mailto:support@aimms.com>`_ and provide the following information:

* Your name
* Email address used for registration
* License number
* Activation code
* Academic domain
* IP subnet - Go to an IP checker such as `checkip.dyndns.org <http://checkip.dyndns.org/>`_ or `whatismyipaddress.com <https://whatismyipaddress.com/>`_ and copy the public IP address you see there.

Our support team can then verify the information and update your license activation range to resolve the error.

.. End Part 3

Related issues:
---------------

* :ref:`ref-update-failed` 

.. END CONTENT

.. include:: /includes/form.def

.. author: Jessica Valasek Estenssoro
.. checked by: Khang Bui
.. updated: October 8, 2018