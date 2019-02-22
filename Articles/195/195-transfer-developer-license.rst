.. |doc| image:: /images/icons/Documentation.png
.. |aimmsIcon| image:: /images/icons/favicon.png
			:scale: 15 %

.. |aimms| image:: /images/aimms-logo-s-rgb.png
				:scale: 10 %
				:target: https://aimms.com/
.. |LicenseConfigurationLocation| image:: /images/LicenseConfigurationLocation.png
.. |LicenseDeactivation| image:: /images/LicenseDeactivation.png
.. |SuccessfulDeactivation| image:: /images/SuccessfulDeactivation.png
.. |NoValidLicense| image:: /images/NoValidLicense.png
.. |InstallAimmsLicense| image:: /images/InstallAimmsLicense.png
.. |InputLicense| image:: /images/InputLicense.png
.. |LicenseProtection| image:: /images/LicenseProtection.png
.. |InputActivationCode| image:: /images/InputActivationCode.png
.. |NodelockProtection| image:: /images/NodelockProtection.png
.. |SuccessfulActivation| image:: /images/SuccessfulActivation.png

				
				
.. This text will not be shown 				
	.. figure:: /images/aimms-logo-s-rgb.png
					:scale: 70 %
					:align: center
					:target: https://aimms.com/

Transfer a Developer License to Another Machine
================================================

.. meta::
   :description: How to transfer an AIMMS license for use on a different computer.
   :keywords: license, transfer, move, switch

An AIMMS developer license can only be used on one machine at a time. There may be situations where you need to use |aimms| on a different machine, either temporarily or permanently. This article explains how to transfer an AIMMS license for use on a different computer. [#]_ [#]_

1.	First deactivate your license from the machine it is currently installed on
-------------------------------------------------------------------------------------
	
	a. In AIMMS, head to Tools –> License -> License Configuration…
	
		|LicenseConfigurationLocation|
	
	b. Select your developer license and click 'Deactivate...'
	
		|LicenseDeactivation|
	
	c. Follow the Deactivation wizard and if successful, the following message will be displayed:
	
		|SuccessfulDeactivation|


	.. [#] *This applies to Personal Nodelock activated licenses as AIMMS Developer Licenses are typically installed through the Personal Nodelock method.*
	.. [#] *For Free Academic license users, make sure that the license activation process is done within your university network*
	
2.  Install your AIMMS license on the other machine
----------------------------------------------------	
	a.  Open AIMMS and if no licenses have already been installed, the prompt for configuring a license will appear. Click 'Continue...'
	
		|NoValidLicense|
	b.  Follow the installation steps for 'Install a Single User AIMMS License'
	
		i.  Choose the first option for the regular AIMMS Developer License
		
			|InstallAimmsLicense|
			
		ii.  Input your license number
		
			|InputLicense|

                .. note::
                                
                    If your received a license file from us, you may actually use the file name, which should consist of a 12 digit number.
            

		iii.  Choose the activation code option unless an AIMMS dongle was provided for your license.
		
			|LicenseProtection|
		
		iv.	 Input your Activation Code.  Choosing to save the activation code within your registry makes it easier to move the license back and forth.
		
				|InputActivationCode|
		
		v.  Choose the Personal Nodelock method if this license was activated as one before. If the license was activated as a Machine Nodelock, you can only choose that option for future activations.
		
			|NodelockProtection|
			
		vi.	If successful, you will receive the following message:
		
			 |SuccessfulActivation|

   
.. note::

	If you encounter any issues with the license transfer process, please contact support at support@aimms.com

:Author: Khang Bui
:Version: Last Updated March 04,2018

.. include:: /includes/form.def