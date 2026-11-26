Operating WinUI_to_WebUI
========================

.. meta::
   :description: Run the WinUI_to_WebUI command-line tool to convert the WinUI pages of an AIMMS project into WebUI pages and widgets, choose its options, and review the conversion log.
   :keywords: WinUI to WebUI, WinUI_to_WebUI, AIMMS WebUI, WinUI migration, page conversion, WebUI widgets, conversion log, generated identifiers, command-line options

.. image:: https://img.shields.io/badge/Zip-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/winui-to-webui/releases/latest/download/winui-to-webui.zip

.. image:: https://img.shields.io/badge/Repository-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/winui-to-webui

Introduction
------------

Moving an existing application from WinUI to WebUI by hand means rebuilding every page, widget and layout, and adding the identifiers each WebUI page needs. The ``WinUI_to_WebUI`` converter does this work from the command line. It reads the WinUI pages of the AIMMS project in the current folder and writes the WebUI pages and widgets back into the same folder.

The converter adds the identifiers the WebUI pages need to a generated module in the main model and in each library. It also writes a conversion log with warnings and the objects it could not convert, so you know what to finish by hand.

This article is the operating guide for the converter. Running the converter is only the first step of a conversion. How to refine the result page by page, by hand and with SENSAI, is described in :doc:`../718/718-winui-to-webui`.

.. grid:: 1 1 2 2
   :gutter: 2

   .. grid-item::

      **Before:** the WinUI page.

      .. image:: ../718/images/718-example-1-winui.png
         :alt: A WinUI page of the Data Reconciliation application, with navigation buttons, settings, run buttons and a table.

   .. grid-item::

      **After:** the WebUI page written by the converter, with the same widgets and the same layout.

      .. image:: ../718/images/718-example-2-converter.png
         :alt: The same page in the WebUI after the conversion, with the same widgets and layout, and the page tree on the left.

|

The pages come from the Data Reconciliation example in :doc:`../718/718-winui-to-webui`.

Instructions
------------

Setup
~~~~~

#. Download the zip file from the `releases page <https://github.com/aimms/winui-to-webui/releases>`_ and unpack it. It contains ``WinUI_to_WebUI.exe``, and the README and usage notes. Put the executable in a folder on your ``PATH``, or call it with its full path, for example ``C:\Tools\WinUI_to_WebUI.exe``.

   .. note::

      The executable is not signed. The first time you run it, Windows SmartScreen may warn about an unrecognized app. Select :menuselection:`More info > Run anyway` to continue.

#. Make a backup of your project. The tool modifies files inside your project folder: it adds generated identifiers to your model and writes the WebUI output directly into the project. Commit the project to source control, or copy the whole folder, before you run it.

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

Progress is printed to the console. Lines that start with ``Warning:`` report non-fatal issues, for example a ``PageManager.xml`` that could not be parsed. They do not stop the conversion. A fatal problem prints an error and exits with a non-zero code. See :ref:`w2w-errors`.

Reviewing the result
~~~~~~~~~~~~~~~~~~~~

Review the changes in your source control diff, or compare against your backup.

* ``MainProject\WebUI\webui.json`` holds the converted pages. Each library with pages gets an equivalent file.
* ``<project>_conversion.md`` is the conversion log. It lists warnings, objects that were not converted, and the choices made per object.

The conversion log is a to-do list for the finishing touch. It has one section per page, in the order of the page tree. Below each page, every object that needs your attention is a checklist item, with what was not converted and where it is. Mark an item done with ✅ when you have handled it, or declined with ✘. The last line counts the objects the converter ignored on purpose, such as lines and rectangles that only decorate WinUI pages.

An excerpt of the log of the Data Reconciliation example:

.. code-block:: md

   ## 15 Page 'Data Reconciliation Application > Reconciliation > Run reconciliation model' with slug run_reconciliation_model

   * [ ] The action GotoPage in action sequence is not yet converted to a corresponding action for WebUI
         see also https://documentation.aimms.com/user-guide/aimms-ide/Aimms/Page%20Manager/Page%20Objects/Button/Actions/index.html
         Location: on page 'Run reconciliation model' purpose button_action_sequence of 'MainProject'.

   ...

   During the conversion 0 lines, 171 rectangles, 38 navigation objects, and 0 Data Page buttons were ignored.

To read it:

* The heading gives the page number, its place in the page tree, and its WebUI slug, so you can open the page directly.
* ``GotoPage`` actions are navigation. They are typically replaced by a workflow panel, for example with the :ref:`SENSAI workflow panel skill <w2w-skill-workflow-panel>`.
* ``Location`` names the page, the kind of object, and the component (``MainProject`` or a library) it belongs to.

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

By default the tool rebuilds the entire ``webui.json`` of every component from scratch. This discards manual edits made in the WebUI editor, and changes made with SENSAI. To iterate on one page without disturbing the rest of an already converted WebUI, pass ``--page``:

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
#. Otherwise, the Network Object becomes a diagram.

``map`` and ``diagram`` force that widget for every Network Object in the run. ``ignore`` converts no Network Object at all: they get no widget and take no space in the page's grid. Their number is reported at the end of the conversion log. The choice and its reason are recorded per object in ``<project>_conversion.md``.

To mix choices within one project, combine ``--network`` with ``--page``. Convert the whole project once with the default, then reconvert a page whose Network Object should be the other widget:

.. code-block:: none

   WinUI_to_WebUI --page "Network Tie Flows" --network=map

Keep the following in mind:

* A WebUI Diagram supports one arc set per widget. Only the first arc set of a Network Object is converted. The others are listed in the conversion log.
* Diagram coordinates are rescaled from the WinUI visible area to a drawing space the size of the WinUI object times ``--diagram-spread``, with the Y axis flipped. The initial zoom of the diagram is 1/spread. WebUI draws each node as a labeled box, much larger than the WinUI dot, so the spread gives the nodes room. Use for example ``--diagram-spread 4`` for more room, or ``--diagram-spread 1`` for the WinUI pixel size. The rescaling is done by generated parameters (``p_diagram_x_...`` and ``p_diagram_y_...``) in the generated module.
* GIS background layers, node and arc sizes, shapes and colors are not converted.
* When the WinUI visible area is computed at run time, the bounds of the map are left unset. The conversion log mentions this.

Sharing page layouts
~~~~~~~~~~~~~~~~~~~~

A page whose grid matches one of the standard WebUI layouts uses that standard layout. Every other page gets its own custom grid layout by default, so editing the layout of one page in the WebUI editor never changes another page. To let pages with the same custom grid shape share one layout instead, pass ``--share-layouts``:

.. code-block:: none

   WinUI_to_WebUI --share-layouts

Sharing keeps ``webui.json`` smaller, but a layout edit on one page then shows up on every page that shares that layout.

Repointing ``AllColors`` element parameters
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

WebUI cannot use the predeclared set ``AllColors`` as it is. Normally, the tool repoints the ``Range`` of an identifier ranged over ``AllColors`` to its own generated set ``giim::s_AllColors`` only when a converted widget uses that identifier for data coloring, so only with ``--colors``.

To repoint every element parameter in the project whose ``Range`` is ``AllColors``, including the ones no converted widget references, pass ``--newcolorset``:

.. code-block:: none

   WinUI_to_WebUI --newcolorset

This option can be combined with ``--page``. It is off by default, because it changes identifiers outside what the conversion itself needs.

.. _w2w-errors:

Errors
~~~~~~

.. list-table::
   :header-rows: 1
   :widths: 45 55

   * - Message
     - Cause
   * - ``No .aimms file found in the current directory``
     - Run ``WinUI_to_WebUI`` from the folder that contains the ``.aimms`` file.
   * - ``Multiple .aimms files found``
     - The folder contains more than one ``.aimms`` file. Remove or move the extra files.
   * - ``Page '...' not found in any component of the project``
     - The ``--page`` name does not match any page ``Name`` in a ``PageManager.xml``.
   * - ``Project folder '...' does not exist``
     - Internal error: the detected path no longer exists.

Building from source
~~~~~~~~~~~~~~~~~~~~

The release contains a ready-made ``WinUI_to_WebUI.exe``. To build it yourself, for example to try a change, you need Windows, PowerShell and `uv <https://docs.astral.sh/uv/>`_. From the root of a clone of the repository, run:

.. code-block:: none

   .\scripts\clean_w2w.ps1
   .\scripts\build_w2w.ps1

The executable is written to ``dist\WinUI_to_WebUI.exe``. See `building.md <https://github.com/aimms/winui-to-webui/blob/main/building.md>`_ in the repository for the details, and for running the converter from source without building the executable.

Minimal Requirements
--------------------

* Windows. ``WinUI_to_WebUI.exe`` is a Windows executable. It includes everything it needs, so you do not need to install Python.
* AIMMS Developer 26.8, with a license on the same machine. The converter opens your project with AIMMS. It is tested with AIMMS 26.8.1.2. You can get a license from the `AIMMS licensing page <https://www.aimms.com/support/licensing/>`_.
* The WebUI library. A project that already references WebUI keeps its own version. A project without WebUI gets the WebUI repository library 26.13.1 added.

Release Notes
-------------

`v1.0 <https://github.com/aimms/winui-to-webui/releases/tag/1.0>`_ (26/11/2026)
   First release. Converts the WinUI pages, widgets and layouts of a project to WebUI, with the options ``--page``, ``--colors``, ``--network``, ``--diagram-spread``, ``--share-layouts`` and ``--newcolorset``.

.. seealso::

   * :doc:`../718/718-winui-to-webui`
   * :doc:`../717/717-ui-tools-library`

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
   p_diagram_x
   p_diagram_y
   rescaled
   rescaling
   repoint
   repoints
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
   newcolorset
   colorset
   reconvert
