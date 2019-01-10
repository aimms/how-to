
.. IMAGES

.. |maintenance-mode| image:: /Images/105-adding-named-user-licenses/maintenance-mode.png
.. |registered-users-menu| image:: /Images/105-adding-named-user-licenses/registered-users-menu.png
.. |add-new-users| image:: /Images/105-adding-named-user-licenses/add-new-users.png
.. |client-section| image:: /Images/105-adding-named-user-licenses/client-section.png
    :scale: 60 %


.. BEGIN CONTENT

Register Named Users in Network License Manager
==================================================

.. meta::
   :description: How to register users to access the AIMMS license server.
   :keywords: license, network, user, name, register

How to register Named Users for a Network License in the Network License Manager.

Adding named users
--------------------
Users can access an AIMMS Network License while connected to the network from any machine, if they are logged in with credentials that are registered in the AIMMS Network License Manager as Registered Users. 

An administrator can add new users by following the steps below.

1. 
    In AIMMS Network License Manager, go to *Maintenance > Maintenance mode*.

    |maintenance-mode|

    |

2. 
    Click to select your license in the License window, and go to *Maintenance > License > Registered Users*.

    |registered-users-menu|

    |

3. 
    A Registered License Users dialog appears. Enter new user information and click *Add*.

    |add-new-users|

    The username should match the user's Windows or Linux username.

    |

4. Go to *Maintenance > Maintenance mode* to exit *Maintenance mode*.

|
    
Your new user(s) can now connect to the license server.

When connected, their details will appear in the *Client* section of the Network License Manager.

    |client-section|


Related topics
---------------

.. *  AIMMS Knowledge: :doc:`106-install-network-license`

*  AIMMS Documentation: `Network License Server Manual <https://download.aimms.com/aimms/download/data/LicenseServer/AIMMS_net.pdf>`_ 
*  AIMMS Documentation: `License Server Installation and Configuration <https://manual.aimms.com/pro/license-server.html>`_ 

.. END CONTENT

.. include:: ../includes/form.def

.. author: Jessica Valasek Estenssoro
.. checked by: -Khang Bui
.. updated: October 26, 2018