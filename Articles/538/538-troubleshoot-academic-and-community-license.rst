:orphan:
Academic and community licenses: No logon response from server
===========================================================================

Your situation
-------------------------

If you have requested an  `Academic <https://licensing.cloud.aimms.com/license/academic.htm>`_ or `Community <https://licensing.cloud.aimms.com/license/community.htm?utm_source=website&utm_medium=footer>`_ license on our website you have most probably received an e-mail from AIMMS Licensing containing instructions on how to install AIMMS and use your license. In this e-mail there should also be a unique license URL you will need in order to be able to open AIMMS.  

In some cases however while launching AIMMS on your computer you get the error message below:

.. image:: Images/no-connection-error-message.png
    :align: center

There are a few possible reasons for you to be getting the above error:

1. Wrong or misspelled License URL
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

How to verify it?
""""""""""""""""""""""""""""

Find the last e-mail you received from AIMMS Licensing with subject "Your AIMMS Academic/Community Edition account has been verified" and look for the License URL. Does it completely match the URL in the field "License URL" in the AIMMS Launcher?

.. image:: Images/URL-misspelled.png
    :align: center

Solution
"""""""""""""""""""""""""""

Make sure the you have copied correctly the whole link from your e-mail into the License URL field of the Launcher.

Be careful: in case this is not your first AIMMS academic or community edition license it is possible that the License URL field contains the URL of your previous (expired) license. In this case please make sure that the correct URL has been pasted in the field.  


2. Expired license
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

How to verify it?
""""""""""""""""""""""

The academic/community license expires if unused for 6 months in a row. If you are not sure when you have used AIMMS for the last time please contact support@aimms.com so we can check that for you.

Solution
""""""""""

If your license has expired you need to reapply on our website for a new `academic <https://licensing.cloud.aimms.com/license/academic.htm>`_ or `community <https://licensing.cloud.aimms.com/license/community.htm?utm_source=website&utm_medium=footer>`_ license.

3. Outgoing network issue
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Please make sure that you have excluded issues 1 and 2 as possible reasons for the error.

The connection (web-socket connection) between the computer you are running AIMMS on and our license server may not be established due to security mechanisms in place in the network or on the computer you are using. For example corporate networks have these issues as well as a lot of university networks (like proxy servers, firewalls, virus scanners or restricted web socket connections whatsoever). 

How to verify it?
""""""""""""""""""""""

You can start by testing if the issue is: 

- with your network -> try changing the network with and alternative network or even a mobile Hot spot -> if one of the alternatives works there may be a network security mechanism in place.
- with your computer -> try changing the computer -> if the alternative computer works for you there may be something blocking the web-socket connection on your machine.

Solution
""""""""""

Depending on who owns the network or machine you are using please contact the relevant IT department and request for allowing the usage of web-socket connections to ``licensing.cloud.aimms.com``.
 
 