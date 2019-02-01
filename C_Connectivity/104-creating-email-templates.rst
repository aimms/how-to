.. BEGIN CONTENT

Create Email Templates
========================

.. meta::
   :description: How to create and send HTML email templates from AIMMS projects.
   :keywords: email, e-mail, template, html



The Email Client library supports the use of template files in HTML and TXT formats. You can set up templates with placeholders to be replaced by values from identifiers in your AIMMS project. 


Creating the template
----------------------
You can create a simple text template in any text editor. TXT templates may be used alone if no special formatting is needed.

HTML templates can be formatted and may include related attachments (such as images). When using an HTML template, it is recommended to include a TXT template as a backup for email clients that do not support HTML.

To create a formatted HTML template, optionally with images, you can use a word processing application such as MS Word. 

#. Format and compose your email body and add images in the document. 

#. Save the document in your AIMMS project files. Save as ``.html (filtered)``, and save it again as ``.txt``.

#. 
    If you included images, edit the image references in the HTML file.

    If you use MS Word to create the template, your ``*.html`` will be saved with a ``*_files`` folder containing the images you have included. For these images to render in your email sent from AIMMS, you must replace the reference to this folder with ``cid:`` in the HTML source code. 
    
    Change ::

        <img src="document_files/image001.jpg">

    to ::

        <img src="cid:image001.jpg">

You are now ready to send an email using your template. For instructions, read AIMMS Knowledge: :doc:`104-using-email-client-library`.


Related topics:
---------------

*  AIMMS Knowledge: :doc:`104-using-email-client-library`
*  AIMMS Documentation: `Email Client Library <https://manual.aimms.com/emailclient/index.html>`_ 

.. END CONTENT

.. include:: ../includes/form.def

.. author: Jessica Valasek Estenssoro
.. checked by: Mohan Chiriki
.. updated: October 24, 2018