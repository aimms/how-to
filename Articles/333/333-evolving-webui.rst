Evolving WebUI
===============

Based on new insights and customer demands the AIMMS WebUI technolgy keeps evolving.
As a conquence, WebUI's of applications developed using older versions of AIMMS need to be adapted before they can be used with modern versions of AIMMS. This article mentions the changes made to functionality of AIMMS WebUI and how to make use of these changes in your applications.

The WebUI was introduced in AIMMS 4.3

.. note:: Every change you make to the project should go with at least one change to the model text.
          I always add or remove a space to ``MainTermination``, but making a change to the text of any procedure will do.
          By making a change to the model text, the ``.aimms`` file of your project is adapted to the new version of the WebUI of your project,
          thereby the next time you open the project, the proper version of AIMMS is used to open the WebUI as well.

WebUI folder position
----------------------

AIMMS 4.39 is the last version of AIMMS where the WebUI folder was located as a subfolder of the project folder.
AIMMS 4.40, the WebUI is a subfolder of the folder MainProject.

.. image:: images/MoveWebUIFolder.png
    :align: center

