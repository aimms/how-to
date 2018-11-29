.. IMAGES

.. |image| image:: /Images/folder-matching-your-document-name/image.png

.. |image-options| image:: /Images/folder-matching-your-document-name/image-options.png
   :scale: 100 %
   :alt: Your image alt text
   :target: <https://www.url.com>_



.. BEGIN CONTENT

A Lovely Title (level 1)
=========================

A sentence describing what is in this article. (like a meta-title)

"A handy reST example file showing most of the functions you'll need and the preferred AIMMS formatting and style conventions."

.. TOC (optional, if you want to refer to some sections below)

 * :ref:`ref-unique-name-1`
 * :ref:`ref-unique-name-2`

.. End TOC


.. _ref-unique-name-1:

A very nice heading (level 2)
------------------------------

Describe the topic or task to be accomplished. "This article provides some background information about writing in reST."

Explain the reason for the task being accomplished, or any background info needed to introduce the topic. "We use Sphinx for our documentation, and build HTML files from reST. Here are some tips for writing beautiful docs in reST."

.. _ref-unique-name-2:

Lists (level 3, subheading)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Now we can dive into some kind of procedure with a numbered list.

1. steps can be 
2. easily 
3. numbered

Sometimes you need to list things with bullets.

Reasons to use unordered lists:

* Lists are easier to read than long sentences
* Bullet points make you look sooo organized and professional
* Asterisks are cool

Images (level 3, subheading)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Remember the images we named in the top of the document? Let's use a substitution to put an image down here.

|image-options|

Notes (level 3, subheading)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^
.. note::

    Here is some useful information.

.. warning::
	
	Here is some very important information!

Styling text (level 3, subheading)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Here's how to mark, e.g., a *UI Element* in italics. Open the *Library Manager*.

Here's how to mark ``code`` inline. Enter ``some code``.

Here's a block of example code in monospace font::

    !Set the SMTP server
    email::SetServer("smtp.company.com", 25);

    !Create new email with the specified subject, sender's name and email.
    email::NewMail("Test mail", "Test Sender", "testsender@example.com", messageID);

Links and downloads (level 3, subheading)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

You can reference an external link

`This external link title <www.example.com/title.html>`_.

Or use the doc role to reference another filename in the repo and a link will be created.

For details read :doc:`article-name`.

You can attach an example project or other file download. 

This is a relative path. Here ``..`` represents going up 2 levels from the current document. (one period = one level)

:download:`exampleproject.zip <../Resources/Other/article-name/example-project.zip>`


Related topics (level 2 heading)
---------------------------------

*  AIMMS Knowledge: :doc:`article-name` (any file in repo can be linked by its unique file name)
*  AIMMS Documentation: `Email Client Library <https://manual.aimms.com/emailclient/index.html>`_ (link to file or article outside this repo)

.. END CONTENT

.. include:: ../includes/form.def

.. updated: October 31, 2018