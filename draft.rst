Add a library to your project
=============================

.. Definition

In the context of the AIMMS environment, a library is a project that is structured to be invoked from other AIMMS projects. 

Each library project in AIMMS contains a subset of project files. You can divide a large project into smaller sub-projects, enabling multiple developers to collaborate easily by sharing parts which are relevant to the entire project.

Read more about libraries in the `AIMMS User Manual <https://download.aimms.com/aimms/download/manuals/AIMMS3UG_OrganizingProjectInLibraries.pdf>`_.

.. Procedure

To add a new libary within a project, go to ``File > Library manager``.

Select a library type:

    * New library
        Name your library and specify location in the *Library* tab. Customize subfolder or model name in the *Optional Naming* tab.

    * Existing library
        Browse to select a saved library folder.

    * System library
        Browse pre-installed AIMMS libraries.

    * Repository library
        Browse existing AIMMS libraries on the cloud. (Requires internet connection.)


.. note:: AIMMS assigns a default library prefix when you create a new library project. To view and edit a library's prefix, open the library manager. Use the prefix to identify the library when accessing the library objects.

.. END DOCUMENT