Transfer Single-User License to Other Machine
================================================
.. meta::
   :description: How to transfer an AIMMS license for use on a different computer.
   :keywords: license, transfer, move, switch

An AIMMS Developer license can only be used on one machine at a time. 
There may be situations where you need to use AIMMS on a different machine, either temporarily or permanently. 
This article explains how to transfer an AIMMS license for use on a different computer.

Reach out to our support team at support@aimms.com if you do not have access to the machine on which the 
AIMMS license is currently installed on, or if you encounter any issues in the below process.

.. important::
   * This applies to Personal Nodelock activated licenses as AIMMS Developer licenses are typically installed through the Personal Nodelock method.
   * For Free Academic license users, make sure that the license activation process is done within your university network.

Deactivate on Current Machine
--------------------------------
	
1. In AIMMS, head to :menuselection:`Tools > License > License Configuration…`.

.. image:: images/LicenseConfigurationLocation.png
   :align: center

|

2. Select your developer license and click :menuselection:`Deactivate...`.

.. image:: images/LicenseDeactivation.png
   :align: center

|

3. Follow the deactivation wizard and if successful, the following message will be displayed:

.. image:: images/SuccessfulDeactivation.png
   :align: center

|
	
Install on Second Machine
----------------------------

4. Open AIMMS and if no licenses have already been installed, the prompt for configuring a license will appear. 
Click :menuselection:`Continue...`.

.. image:: images/NoValidLicense.png
   :align: center

|

5. Follow the installation steps for :menuselection:`Install a Single User AIMMS License`.

   * Choose the first option for the regular AIMMS Developer license.
   
   |

   .. image:: images/InstallAimmsLicense.png
      :align: center

   |
      
   *  Input your license number. If you received a license file from us, you can use the file name which is a 12 digit number.
   
   |

   .. image:: images/InputLicense.png
      :align: center

   |

   *  Choose the activation code option unless an AIMMS dongle was provided for your license.
   
   |

   .. image:: images/LicenseProtection.png
      :align: center

   |

   *	 Input your Activation Code. Choosing to save the activation code within your registry makes it easier to move the license back and forth.
   
   |

   .. image:: images/InputActivationCode.png
      :align: center

   |

   *  Choose the Personal Nodelock method if this license was activated as one before. If the license was activated as a Machine Nodelock, you can only choose that option for future activations.
   
   |
   
   .. image:: images/NodelockProtection.png
      :align: center

   |

If successful, you will receive the following message:
   
.. image:: images/SuccessfulActivation.png
   :align: center
