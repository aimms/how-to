Convert WinUI to WebUI
==========================

.. meta::
   :description: Convert the WinUI pages of an AIMMS project into WebUI pages and widgets with a command-line tool, and review a log of everything it could not convert.
   :keywords: WinUI to WebUI, AIMMS WebUI, WinUI migration, page conversion, WebUI widgets, conversion log, generated identifiers, AIMMS project migration

.. image:: https://img.shields.io/badge/Zip-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/winui-to-webui/releases/latest/download/winui-to-webui.zip

.. image:: https://img.shields.io/badge/Repository-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/winui-to-webui

Introduction
------------

Moving an existing application from WinUI to WebUI by hand means rebuilding every page, widget and layout, and adding the identifiers each WebUI page needs. The WinUI to WebUI converter does this work from the command line. It reads the WinUI pages of the AIMMS project in the current folder and writes the WebUI pages and widgets back into the same folder.

The converter adds the identifiers the WebUI pages need to a generated module in the main model and in each library. It also writes a conversion log with warnings and the objects it could not convert, so you know what to finish by hand.

.. TODO: Add a short before and after screenshot of one converted page.

Instructions
------------

Setup
~~~~~

#. Download the zip file from the `releases page <https://github.com/aimms/winui-to-webui/releases>`_ and unpack it.

   .. TODO: Confirm the exact contents of the release zip and where to place WinUI_to_WebUI.exe.

#. Make a backup of your project. The tool modifies files inside your project folder. Commit the project to source control, or copy the whole folder, before you run it.

#. Check that the project folder contains exactly one ``.aimms`` file.

Running a conversion
~~~~~~~~~~~~~~~~~~~~

#. Open a terminal (Command Prompt or PowerShell).

#. Go to the folder that contains your ``.aimms`` file:

   .. code-block:: none

      cd C:\path\to\your\project

#. Run the tool:

   .. code-block:: none

      WinUI_to_WebUI

The tool does the following:

* It detects the ``.aimms`` file in the current folder.
* It adds the ``AimmsPro`` system library and the WebUI repository library, if the project does not reference them yet.
* It converts all WinUI pages and writes the WebUI output for the main model and for every library that has pages.
* It adds the identifiers the WebUI pages need to a generated module in each component.

The generated module in the main model is ``GeneratedInterfaceIdentifiersMain`` with prefix ``giim``. In each library it is ``GeneratedInterfaceIdentifiersLibrary`` with prefix ``giil``.

During the run, the tool temporarily copies the ``ModelEditAimmsPython`` library into the project and removes it again at the end. That library wraps model editing functions such as ``me::Create`` and ``me::SetAttribute`` in procedures like the one below.

.. code-block:: aimms
   :linenos:

   Procedure pr_getDim {
       Body: {
           _ep_id := StringToElement( AllIdentifiers, sp_id, create: 0);
           if _ep_id then
               p_dim := IdentifierDimension( _ep_id );
           else
               p_dim := -1 ;
               raise warning formatString("Dimension of undeclared \"%s\" unknown", sp_id);
           endif ;

           return 1 ;
       }
       ElementParameter _ep_id {
           Range: AllIdentifiers;
       }
   }

Progress is printed to the console. Lines that start with ``Warning:`` report non-fatal issues, for example a ``PageManager.xml`` that could not be parsed. They do not stop the conversion. A fatal problem prints an error and exits with a non-zero code.

Reviewing the result
~~~~~~~~~~~~~~~~~~~~

Review the changes in your source control diff, or compare against your backup.

* ``MainProject\WebUI\webui.json`` holds the converted pages. Each library with pages gets an equivalent file.
* ``<project>_conversion.md`` is the conversion log. It lists warnings, objects that were not converted, and the choices made per object.

.. TODO: Add an excerpt of a real conversion log and explain how to read it.

Options
~~~~~~~

Run ``WinUI_to_WebUI --help`` for the built-in summary.

.. list-table::
   :header-rows: 1
   :widths: 35 65

   * - Option
     - Effect
   * - ``--page <name>``
     - Convert a single page and leave every other page in ``webui.json`` untouched.
   * - ``--colors``
     - Convert WinUI data coloring. Off by default.
   * - ``--network=auto|map|diagram|ignore``
     - How to convert Network Objects. Default ``auto``.
   * - ``--diagram-spread <factor>``
     - Spacing of nodes in a converted network diagram. Default ``2.5``.
   * - ``--share-layouts``
     - Let pages with the same custom grid shape share one layout.
   * - ``--newcolorset``
     - Repoint every element parameter ranged over ``AllColors``, not only the ones used for coloring.

Converting a single page
~~~~~~~~~~~~~~~~~~~~~~~~

By default the tool rebuilds the entire ``webui.json`` of every component from scratch. This discards manual edits made in the WebUI editor. To iterate on one page without disturbing the rest of an already converted WebUI, pass ``--page``:

.. code-block:: none

   WinUI_to_WebUI --page pageName

``pageName`` must match the ``Name`` attribute of the page in ``PageManager.xml``, which is the same name used for its ``Pages\<pageName>.xml`` file. It is not the WebUI slug.

* Only that page's entry and its widgets in ``webui.json`` are replaced. Every other page already on disk stays unchanged, including manual layout and widget edits.
* A component that has pages but no ``webui.json`` yet still gets a fresh base ``webui.json`` with no pages converted into it.
* If the page is not found in any component, the tool exits with an error and does not touch any file.

The tool still processes every page internally to keep the generated identifiers consistent, so ``--page`` does not make the run much faster. It only limits what is written to ``webui.json``.

Converting data coloring
~~~~~~~~~~~~~~~~~~~~~~~~

WinUI data coloring (a table's ``Colors`` or ``Background_Color``, a chart's ``Color``) is not converted by default, because the generated stylesheet also colors widgets that do not use coloring. To convert it, pass ``--colors``:

.. code-block:: none

   WinUI_to_WebUI --colors

This turns each coloring into a ``webui::AnnotationsIdentifier`` on the colored identifier. It creates the ``giim::s_AllColors`` color set with ``p_Red``, ``p_Green``, ``p_Blue`` and ``p_ColorSlug``, and it writes ``WebUI\resources\css\w2w-color.css`` in the main model.

Without ``--colors``, none of this is generated, and a ``w2w-color.css`` left behind by an earlier ``--colors`` run is removed. The option can be combined with ``--page`` and ``--newcolorset``.

Converting network objects
~~~~~~~~~~~~~~~~~~~~~~~~~~

A WinUI Network Object becomes either a WebUI Map widget, with nodes placed by latitude and longitude, or a WebUI Diagram widget, with nodes placed by x and y. Choose with ``--network``:

.. code-block:: none

   WinUI_to_WebUI --network=auto
   WinUI_to_WebUI --network=map
   WinUI_to_WebUI --network=diagram
   WinUI_to_WebUI --network=ignore

The default, ``auto``, decides per Network Object:

#. A GIS background (shapefile layers) in the WinUI object gives a map.
#. Otherwise, node coordinates whose names read as longitude (X) and latitude (Y), for example ``p_Longitude( i )`` or ``Latitude_center( r )``, give a map.
#. .. TODO: The remaining auto rules and the exact meaning of ``ignore`` are cut off in the available usage notes. Complete them from usage.md.

Use ``--diagram-spread`` to change the node spacing of a converted diagram. The default factor is ``2.5``.

.. TODO: Describe ``--share-layouts`` and ``--newcolorset`` in their own subsections, using the full usage.md.

.. TODO: Document building the tool from source (see building.md in the repository) if this article should cover it.

Minimal Requirements
--------------------

* AIMMS 26.8. The tool opens the project with the latest installed AIMMS 26.8.x. You can get a license from the `AIMMS Community <https://www.aimms.com/support/licensing/>`_.
* The WebUI library. A project that already references WebUI keeps its own version. A project without WebUI gets the WebUI repository library 26.13.1 added.
* ``WinUI_to_WebUI.exe``, from the release zip.
* A WinUI project and a backup of it.

.. TODO: Confirm the supported operating system. The usage notes mention Command Prompt and PowerShell only.

Release Notes
-------------

* `Version history <https://github.com/aimms/winui-to-webui/releases>`_

  .. TODO: Add the first release version number and date, and a one-line summary.

.. spelling:word-list::

   WinUI
   WebUI
   webui
   winui
   aimms
   AimmsPro
   ModelEditAimmsPython
   GeneratedInterfaceIdentifiersMain
   GeneratedInterfaceIdentifiersLibrary
   giim
   giil
   PageManager
   pageName
   AllColors
   AnnotationsIdentifier
   AllIdentifiers
   IdentifierDimension
   StringToElement
   formatString
   ElementParameter
   s_AllColors
   p_Red
   p_Green
   p_Blue
   p_ColorSlug
   p_Longitude
   Latitude_center
   pr_getDim
   sp_id
   p_dim
   ep_id
   shapefile
   stylesheet
   w2w
   json
   exe
   PowerShell
   slug