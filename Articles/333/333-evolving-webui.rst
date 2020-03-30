Evolving WebUI
===============

Based on new insights and customer demands the AIMMS WebUI technology keeps evolving.
As a consequence, WebUI's of applications developed using older versions of AIMMS need 
to be adapted before they can be used with modern versions of AIMMS. 

This article mentions the changes made to functionality of AIMMS WebUI and how to make use of these changes in your applications.

.. The WebUI was introduced in AIMMS 4.3

Before you start upgrading your project
---------------------------------------

Please store a copy of your project in a safe place before you start upgrading your project.
A good practice is using a source code management system.

Determining the version of your project
---------------------------------------

To know which changes may affect your project, it is important to know which version of AIMMS was used to maintain your project.

Every AIMMS project contains a ``.aimms`` file in the root folder.  An example is:

.. code-block:: XML
    :linenos:
    :emphasize-lines: 2

    <?xml version="1.0"?>
    <References AIMMS_Version="4.39.2.1069 (x64)">
        <MainProject Path="MainProject" />
        <Library System="true" Path="AimmsPro" />
        <Library System="true" Path="AimmsWebUI" />
    </References>

On line 2, the AIMMS version is refreshed when a change is made to the model text or the WinUI page manager.
Double clicking this file will open AIMMS 4.39 if it is available on your system, or the latest AIMMS version if it is not.

.. note:: A change **only** in the WebUI of your project does not affect this version number. 
          Therefore is it good practice to make a small change to the model text whenever you are upgrading your project to a newer version of AIMMS.
          I am in the habit of always adding or removing a space to ``MainTermination`` when I start using a newer version of AIMMS on one of my projects. 

AIMMS 4.16 Sets in selection widget
-----------------------------------

AIMMS 4.16 is the last version where selection widget could contain AIMMS sets as contents (and pressing ``select all`` in the selection widget):

.. image:: images/Aimms416FilteringUsingSetInSelectionWidget.png
    :align: center

With AIMMS 4.17, you will need to a zero one parameter, for instance something like:

.. code-block:: aimms
    :linenos:

    Parameter p01_Selector {
        IndexDomain: sn;
        Range: binary;
    }

Initializing this parameter in ``MainInitialization`` is advised, for instance as:

.. code-block:: aimms
    :linenos:

    p01_Selector(sn) := 0 ;

And then specifying such a parameter in the contents  (and pressing ``select all`` in the selection widget).

.. image:: images/Aimms417FilteringUsingSetInSelectionWidget.png
    :align: center

**Downloads:**

*   :download:`Before, using AIMMS 4.16 <model/AIMMS-3-16-set-selection-widget/AIMMS-4.16/abc416.zip>`

*   :download:`After, using AIMMS 4.17 <model/AIMMS-3-16-set-selection-widget/AIMMS-4.17/abc417.zip>`

AIMMS 4.19 Starting browser
---------------------------

AIMMS 4.19 is the last version of AIMMS where the model developer had to manually start the browser useing something like: ``localhost:12001/Aimms-WebUI/home``.

With AIMMS4.20 onwards, starting the WebUI browser would start the browser directly.

True, no change is made to the project, but it did change the interaction of the model developer.

AIMMS 4.39 WebUI folder position
--------------------------------

AIMMS 4.39 is the last version of AIMMS where the WebUI folder was located as a sub-folder of the project folder.
AIMMS 4.40, the WebUI is a sub-folder of the folder ``MainProject``.

.. image:: images/MoveWebUIFolder.png
    :align: center

When you are using a source code management system, you will want to 
remove the ``WebUI`` folder from the repository before the upgrade and 
add the folder ``MainProject\WebUI`` afterwards!

**Downloads:**

*   :download:`Before, using AIMMS 4.39 <model/AIMMS-4-39-folder-position/AIMMS-4.39/app439.zip>`

*   :download:`After, using AIMMS 4.40 <model/AIMMS-4-39-folder-position/AIMMS-4.40/app440.zip>`


AIMMS 4.45 Element Text
-----------------------

AIMMS 4.45 is the last version of AIMMS that supports the ``elementText.js`` file to adapt 
the `presentation of element names <https://documentation.aimms.com/webui/folder.html#element-text>`_ .

