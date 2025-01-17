.. IMAGES

.. |add-email-client-lib| image:: images/add-email-client-lib.png

.. FILES ATTACHED


.. BEGIN CONTENT

Using Email Client Library
==============================

.. meta::
   :description: An introduction to the Email Client library with an example project.
   :keywords: email, e-mail, template


An introduction to the Email Client Library, please use the following project to follow this article:  

    :download:`EmailLibraryDemo.zip <downloads/EmailLibraryDemo.zip>`

Overview
--------

The `Email Client Library <https://documentation.aimms.com/emailclient/index.html>`_ provides you with an API to create and send emails from within your AIMMS project. The library does not require any external programs to be installed and can be used from both AIMMS Developer and AIMMS PRO On-Premise. It is also available on AIMMS Cloud.

You can use this feature, for example, to send emails containing the results of your optimization model to other users in your organization.

Supported features:

* template files with related attachments (such as images)
* placeholders in templates (to be replaced by data specified in AIMMS identifiers)
* downloadable file attachments 

.. _ref-add-email-lib:

Adding the Library
--------------------

Add ``EmailClient`` to your project from the AIMMS Library Repository using the :menuselection:`Library Manager`.

.. seealso::
    * :doc:`../84/84-using-libraries`.

|add-email-client-lib|

.. note::

    Installing the Email Client library caches it on your local machine and adds a reference to it in your AIMMS application. The library source itself is not added to your model. When you start the app, AIMMS checks whether the library is cached on your computer, and downloads it from the AIMMS Library Repository if needed.

.. End add library

.. _ref-create-email:

Creating and Sending the Email
-------------------------------
You will need to declare string parameters for the library to store the message ID and any error messages. 

.. code-block:: aimms

    StringParameter messageID;
    StringParameter ErrorMessage;

In a procedure, use the statements below to connect to an SMTP server, create a new email and set the sender and recipient information.

.. code-block:: aimms
    :linenos:

    !Set the SMTP server
    email::SetServer("smtp.company.com", 25);

    !Create new email with the specified subject, sender's name and email.
    email::NewMail("Test mail", "Test Sender", "testsender@example.com", messageID);

    !Add recipient information
    email::AddRecipientTo(messageID, "Test Recipient", "testrecipient@example.com");

    !Insert statements to create body of the email, discussed in next sections

    !Send the email via the specified SMTP server
    email::SendMail(messageID, ErrorMessage);

    !Close the email message
    email::CloseMail(messageID);

Specify an SMTP server which you are allowed to access (i.e., your corporate mail server, or an SMTP server associated with an email account you hold). The ``email::SetServer`` function has additional arguments to let you provide authentication information if required by the SMTP server. 

You can add multiple recipients to the same email by repeating the ``email::AddRecipientTo`` statement, or use ``email::AddRecipientCC`` or ``email::AddRecipientBCC`` to CC or BCC additional recipients. 

.. seealso::
    * `Email Client API <https://documentation.aimms.com/emailclient/api.html>`_

.. End create email

.. _ref-use-placeholders:

Using Placeholders in an Email Message Template
-------------------------------------------------

You can replace predefined strings in your template with data from AIMMS identifiers. These replaceable strings are called *Placeholders*. You need to declare a set whose elements are the placeholders that you want to replace and declare a parameter indexed over this set. The values from this indexed parameter will replace the placeholder strings in the email body. 

.. code-block:: aimms

    Set Placeholders {
        Index: i;
        Definition: data { CustomerName, CustomerNumber };
    }

    StringParameter PlaceholderValues {
        IndexDomain: i;
    }

The function ``email::SetMessageFromFile`` creates the body of the email from the templates you specify and replaces the placeholders with the values specified in ``PlaceholderValues``:

.. code-block:: aimms
    :linenos:

    ! Create the body of the email message from text and HTML templates and placeholder values
    email::SetMessageFromFile(messageId, "EmailTemplate.txt", "EmailTemplate.html", PlaceholderValues);

.. note::

    Replacements are done one-by-one, in order. Avoid using a placeholder value that contains the entire name of another value. For example, using the placeholders ``CUSTOMER, CUSTOMERNAME`` would result in the replacement (e.g.) ``123, 123NAME`` and no replacements would occur for ``CUSTOMERNAME``.

.. End place holders

.. _ref-add-attachment:

Adding Images and Attachments
-----------------------------

Add any images contained in the HTML body as related attachments:

.. code-block:: aimms
    :linenos:

    ! Add images contained in HTML body as related attachments
    email::AddRelatedAttachment(messageID, "EmailTemplate_files/image001.jpg", "image001.jpg");
    email::AddRelatedAttachment(messageID, "EmailTemplate_files/image002.png", "image002.png");

To specify any other file attachments:

.. code-block:: aimms
    :linenos:

    ! Add a file attachment to the email message
    email::AddFileAttachment(messageID,"EmailTemplate_files/document.pdf");


.. seealso::
    * :doc:`../111/111-creating-email-templates`
    * `Email Client Library <https://documentation.aimms.com/emailclient/index.html>`_ 
    * `Email Client API <https://documentation.aimms.com/emailclient/api.html>`_
    * :doc:`../84/84-using-libraries`

.. END CONTENT