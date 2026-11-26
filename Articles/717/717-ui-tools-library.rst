UI Tools Library
================

.. image:: https://img.shields.io/badge/Zip-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/ui-tools-library/releases/latest/download/ui-tools-library.zip

.. image:: https://img.shields.io/badge/Repository-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/ui-tools-library

.. image:: https://img.shields.io/badge/AIMMS-26.8-white?style=for-the-badge&labelColor=009B00&color=00D400

.. image:: https://img.shields.io/badge/WebUI-26.10-white?style=for-the-badge&labelColor=009B00&color=00D400

.. meta::
   :description: The AIMMS toolkit library libUITools helps to convert WinUI applications to WebUI: a progress procedure that works in both WinUI and WebUI, and a notification manager with a WebUI page.
   :keywords: libUITools, uit, WinUI to WebUI, DialogProgress, SetProgressMessage, pr_DialogProgress, notifications, pr_addNotification, notification manager, toolkit

Introduction
------------

``libUITools`` is a small AIMMS library, with prefix ``uit``, for applications that move from WinUI to WebUI. It contains two tools:

* **Progress reporting in WinUI and WebUI.** The procedure ``uit::pr_DialogProgress`` has the same arguments as :aimms:func:`DialogProgress`, and works in both user interfaces. This way, the WinUI version of the application keeps working while you convert it.
* **A notification manager.** Instead of showing a message dialog per finding, the model adds a notification to a list with ``uit::pr_addNotification``. The library has a WebUI page that shows the list, where the user can mark notifications as handled and open the related pages.

The conversion process itself is described in :doc:`../718/718-winui-to-webui`.

Instructions
------------

Adding the library to your project
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The download contains the library only: its ``Project.xml``, the model file ``libUITools.ams``, and the WebUI page of the notification manager.

#. Download the zip file, and unpack it in your project folder. This gives a folder ``ui-tools-library``.
#. In AIMMS Developer, add that folder as a library with :menuselection:`File > Library Manager > Add Library from Disk`.

When your project is in git, you can add the repository as a git submodule instead, so that updates of the library are easy to take over.

Trying the example
~~~~~~~~~~~~~~~~~~

The project `717-ui-tools-test <https://github.com/aimms/717-ui-tools-test>`_ shows both tools in a small model, with a copy of the library:

#. Download the project as a `zip file <https://github.com/aimms/717-ui-tools-test/archive/refs/heads/main.zip>`_, open ``UIToolsTest.aimms``, and open the WebUI.
#. On the **home** page, click ``button_test_progress`` to see the progress message, and ``button_test_notification`` to add a few notifications.
#. Open the page **NotificationHandling**, below **libUITools** in the page tree, to see the notifications.

Reporting progress in WinUI and WebUI
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The library decides which user interface to address with two flags:

* ``uit::bp_useWebUI``: 1 when the application runs in the WebUI. Its initial value is 1.
* ``uit::bp_useWinUI``: defined as ``1 - uit::bp_useWebUI``.

The procedure ``uit::pr_DialogProgress( message, percentage )``:

* in WinUI mode, calls ``DialogProgress( message, percentage )``,
* in WebUI mode, calls ``webui::SetProgressMessage`` with the message, the percentage, and a bar of ten blocks, for example ``Solving 40% [████______]``.

The percentage is optional. When you leave it out, the WebUI shows the message with ``0%`` and an empty bar.

To use it, replace each call ``DialogProgress(`` in your model by ``uit::pr_DialogProgress(``. This is a typical routine change to ask SENSAI for:

.. code-block:: none

   In all procedures of the model and its libraries, replace each call to DialogProgress by a call to uit::pr_DialogProgress, with the same arguments.

As long as the application also runs in WinUI, set the flag to match the user interface. For example, set it in the procedure that runs when the WebUI loads its first page: in the WebUI, open the page settings of the home page, and set the procedure upon load:

.. code-block:: aimms

   Procedure pr_openHomePage {
       Body: {
           uit::bp_useWebUI := 1 ;
           webui::OpenPage( 'first_page' );
       }
   }

and set ``uit::bp_useWebUI := 0`` in the startup procedure of the WinUI version.

You can also use the flags for other code that differs between WinUI and WebUI.

Notifications
~~~~~~~~~~~~~

Add a notification with one call per finding:

.. code-block:: aimms

   uit::pr_addNotification(
       sp_message       : "Capacity of plant P1 is negative",
       ep_severity      : 'warning',
       bp_preventsSolve : 1,
       ep_page1         : 'plant_data',
       ep_page2         : '');

Each notification has:

* ``sp_message``: the message. This is the only required argument.
* ``ep_severity``: ``info`` (the default), ``warning`` or ``error``.
* ``bp_preventsSolve``: 1 when not handling the notification likely prevents a successful solve. The default is 0.
* ``ep_page1`` and ``ep_page2``: up to two pages, from ``webui::AllPageIds``, where the user can fix the cause. The second page is useful when two related items are not on the same page.

The library also keeps, for each notification, whether it is handled, when it was created, and when it was last marked as handled.

The notifications live only during the session. The library is not saved in a case, so the list is empty when the application starts again, or when a case is loaded.

The library page **NotificationHandling** shows the notifications in a table:

* The page actions **Show all** and **Show unhandled** filter the table.
* The actions of a row toggle the notification between handled and not handled, delete it, or open its first or second page. The column *handled* cannot be edited directly in the table: use the row action **toggle handled**, which also records the date.

To start a check from an empty list, call ``uit::pr_clearNotifications`` first. Otherwise, running the same check again adds the same notifications a second time:

.. code-block:: aimms

   Procedure pr_checkData {
       Body: {
           uit::pr_clearNotifications ;
           if p_capacity( 'P1' ) < 0 then
               uit::pr_addNotification(
                   sp_message  : "Capacity of plant P1 is negative",
                   ep_severity : 'warning' );
           endif ;
       }
   }

Opening the notifications page
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The page is part of the library, below the page **libUITools** in the page tree. Its slug is ``uit::notificationhandling``. Make it easy to reach, for example:

* as a step in a workflow panel, or
* by opening it after a data check:

  .. code-block:: aimms

     webui::OpenPage( 'uit::notificationhandling' );

For when to use notifications instead of dialogs, see :ref:`w2w-tips-notifications`.

Minimal Requirements
--------------------

* AIMMS 26.8 and the WebUI library 26.10 or newer.
* `AIMMS Community license <https://www.aimms.com/platform/aimms-community-edition/>`_ is sufficient for working with this library.

Release Notes
-------------

`v1.0 <https://github.com/aimms/ui-tools-library/releases/tag/1.0>`_ (26/11/2026)
   First release as a toolkit library: ``uit::pr_DialogProgress``, and the notification manager with ``uit::pr_addNotification`` and ``uit::pr_clearNotifications``.

.. seealso::

   * :doc:`../718/718-winui-to-webui`
   * :doc:`../711/711-operating-winui-to-webui-converter`
   * `WebUI library <https://documentation.aimms.com/webui/library.html>`_

.. spelling:word-list::

   libUITools
   uit
   WinUI
   WebUI
   webui
   SENSAI
   submodule
