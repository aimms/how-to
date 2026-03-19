.. BEGIN CONTENT

Create Email Templates
========================

.. meta::
   :description: Shows how to create HTML and plain-text email templates with inline images and placeholders for use with the AIMMS Email Client Library.
   :keywords: email template, HTML template, Email Client Library, placeholder, MS Word, image attachment, cid reference, TXT template


The Email Client library supports the use of template files in HTML and TXT formats. You can set up templates with placeholders to be replaced by values from identifiers in your AIMMS project. 


Creating the Template
----------------------
You can create a simple text template in any text editor. TXT templates may be used alone if no special formatting is needed.

HTML templates can be formatted and may include related attachments (such as images). When using an HTML template, it is recommended to include a TXT template as a backup for email clients that do not support HTML.

To create a formatted HTML template, optionally with images, you can use a word processing application such as MS Word. 

#. Format and compose your email body and add images in the document. 

#. Save the document in your AIMMS project files. Save as ``.html (filtered)``, and save it again as :any:`.txt`.

#. 
    If you included images, edit the image references in the HTML file.

    If you use MS Word to create the template, your ``*.html`` will be saved with a ``*_files`` folder containing the images you have included. For these images to render in your email sent from AIMMS, you must replace the reference to this folder with ``cid:`` in the HTML source code. 
    
    Change ::

        <img src="document_files/image001.jpg">

    to ::

        <img src="cid:image001.jpg">

You are now ready to send an email using your template.

.. seealso::
    * :doc:`../104/104-using-email-client-library`
    * `Email Client Library <https://documentation.aimms.com/emailclient/index.html>`_ 

.. END CONTENT