Email and Sound Support
========================
.. meta::
   :keywords: email, Sound, External procedure
   :description: This example illustrates two external DLLs that allow you to send email messages and play sounds from within your model.

Direct download AIMMS Project :download:`E-mail and Sound Support.zip <model/E-mail and Sound Support.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/E-mail%20and%20Sound%20Support

.. note:: See also the `Email Client Library <https://documentation.aimms.com/emailclient/index.html>`_.

This example illustrates two external DLLs (complete with source) that allow you to
- programmatically generate and send email messages from within a model
- play sounds from within your model

The email interface makes use of the Simple MAPI API provided by Windows in Mapi32.dll. You can only use it if a MAPI compliant email client (such as Outlook) is installed on your computer.

The sound interface makes use of the Win32 PlaySound function. You can use it, for instance, to play ``.wav`` files from within your model upon particular user interaction.

In the respective source directories you will find the C source files of both interfaces to AIMMS, as well as the Visual Studio 2010 projects that were used to build the DLLs providing the interfaces.

If you need email and/or sound support in your AIMMS application you can simply import one or both interface section contained in the directory 'Import Sections' into your own AIMMS model.

If your needs go beyond the provided interface, you can modify and re-compile the provided source code to suit your own needs.

Keywords:
email, Sound, External procedure


