Export Code to Another Project
==================================

.. meta::
   :description: How to reuse parts of your code in another AIMMS model.
   :keywords: import, export, link, share, reuse, re-use

When building AIMMS models, you may often find components you’d like to reuse in other projects. 
For highly reusable components, consider creating an AIMMS library or module. Refer to :doc:`creating-and-managing-a-model/the-model-explorer/creating-and-managing-models` for more details.

However, if you only need to export or import identifiers quickly, AIMMS provides a straightforward export/import feature.

Exporting a Section
-------------------

To begin exporting, first organize all identifiers you wish to export into a single section within your current AIMMS model. Then, you have two export methods:

1. Select the section in the model tree and choose :menuselection:`Edit > Export...`.
2. Use the source attribute of the section identifier to save it to a separate file.

The first method is simpler, but the second allows for easier code sharing across projects. 
By completing the second method’s steps, AIMMS will store all identifiers in the designated `.aim` or `.ams` file rather than in the main project file.

To export a section to an `.ams` file:

1. Highlight the section in the model editor.
2. Go to the AIMMS Menu and select :menuselection:`Edit > Export...`.

To make a section shareable across projects, follow these steps:

1. Open the attribute window of the section you wish to export.
2. Click the wizard button next to the source file attribute.
3. Choose :menuselection:`Write` from the menu.
4. Select the desired storage location for the `.ams` file.
5. Name the file, then click :menuselection:`Save`.

Importing a Section
--------------------

To import an `.ams` file into your model:

1. Create a new section to hold the imported identifiers.
2. Select this section identifier in the model tree.
3. In the Edit menu, choose :menuselection:`Edit > Import...`.
4. Select the `.ams` file you wish to import.

AIMMS will display a dialog showing identifiers to be imported and any conflicts with existing identifiers.

.. note::
    
    If you use the source file attribute for a section, AIMMS stores its contents in a separate file unless “Remove (keep subtree)...” is selected.
    Note that if changes are made to the `.ams` file outside AIMMS (e.g., in a text editor or version control system) while the project is open, 
    they won’t be reflected in AIMMS until the project is closed and reopened.
