.. IMAGES


.. |add-email-client-lib| image:: /Images/104-using-email-client-library/add-email-client-lib.png


.. BEGIN CONTENT

Using the Email Client Library
==============================


.. Part 1

Overview
---------

The Email Client library provides you with an API to create and send emails from within your model. The library does not require any programs to be installed, and can be used both client- and server-side. It is also cloud-compatible.

You can use this feature, for example, to send emails containing the results of your optimization model to other users in your organization.

Supported features:

* template files 
* placeholders in templates (to be replaced by variable strings specified in your project)
* downloadable file attachments 
* related attachments (such as images contained in HTML templates)


.. End Part 1

.. Part 2

Adding the library
--------------------

To add the Email Client library to your model, go to *File > Library Manager* and click *Add Library from Repository*. 

Select *EmailClient* in the dialog.

|add-email-client-lib|

.. note::

    Installing the Email Client library caches it on your local machine and adds a reference to it in your AIMMS application. The library source itself is not added to your model. When you start the app, AIMMS checks whether the library is cached on your computer, and downloads it from the AIMMS Library Repository if needed.

.. End Part 2

.. Part 3

Creating and sending the email
-------------------------------
In model explorer view, use the following commands to create and send the message. 

Set the SMTP server and create the email message with recipients::

    ! Set the STMP server
    email::SetServer("smtp.company.com", 25, _ConnectionType: email::ConnectionTypeStartTLS);

    ! Create the email and set the sender
    email::NewMail("Test mail", "Test Sender", "testsender@example.com", messageId);

    ! Add recipients
    email::AddRecipientTo(messageId, "Test Recipient", "testrecipient@example.com");

Specify an STMP server which you are allowed to access (i.e., your corporate mail server, or an SMTP server associated with an e-mail account you hold).

Define values for placeholders to replace in the templates used to create the actual email message::

    ! Define values for placeholders to replace in the templates used to create the actual email message referring to values defined in Declaration and use for loop if needed for set of data
    PlaceHolderValues := data { CUSTOMERNAME(c), CUSTOMERNUMBER(c), INVOICENUMBER(c)};

.. note::

    Replacements are done one-by-one, in order. Avoid using a place holder value that contains the entire name of another value. For example, ``CUSTOMER, CUSTOMERNAME`` Would result in ``123, 123NAME`` and no replacements would occur for ``CUSTOMERNAME``.

The ``PlaceHolderValues`` must be defined in the declaration if using a set. Otherwise you can define the data in strings here.

(show an example of the loop)

Create the body of the email message from text and HTML templates and placeholder values, and add any images contained in the HTML body as related attachments::

    ! Create the body of the email message from text and HTML templates and placeholder values
    email::SetMessageFromFile(messageId, "EmailTemplate.txt", "EmailTemplate.html", PlaceHolderValues);

    ! Add images contained in HTML body as related attachments
    email::AddRelatedAttachment(messageId, "EmailTemplate_files/image001.jpg", "image001.jpg");
    email::AddRelatedAttachment(messageId, "EmailTemplate_files/image002.png", "image002.png");

If you are using an HTML template, it's best to include the txt file as a backup because HTML is not supported by all email clients. (Read more about using templates in your message in :ref:`ref-x`.)

Specify any other file attachments::

    ! Add a file attachment to the email message
    email::AddFileAttachment(messageId,"EmailTemplate_files/document.pdf");

Send the email and close the message::

    ! Send the email via the specified SMTP server
    email::SendMail(messageId, ErrorMessage);

    ! Close the email message
    email::CloseMail(messageId);


For a full list of commands available, read the `Email Client API <https://manual.aimms.com/emailclient/index.html>`_.

.. End Part 3

Related issues:
---------------

* :ref:`ref-x` (Templates article)
*  `AIMMS Documentation: Email Client Library <https://manual.aimms.com/emailclient/index.html>`_ 

.. END CONTENT

.. include:: ../includes/form.def

.. author: Jessica Valasek Estenssoro
.. checked by: --
.. updated: --