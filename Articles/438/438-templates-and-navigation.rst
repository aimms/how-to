Templates and Navigation
========================

.. meta::
   :keywords: Navigation object, navigation menu, resizability, page structure, bitmap, button, rectangle
   :description: This demo illustrates AIMMS' capabilities to use page templates and add an automatic navigational structure for end-user UI.

Direct download AIMMS Project :download:`Templates and Navigation.zip <model/Templates and Navigation.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Templates%20and%20Navigation

This demo illustrates AIMMS' capabilities to

- provide a common interface to your end-user interface through the use of page templates, and 
- add an automatic navigational structure to the end-user interface by structuring your end-user pages in the page tree.

The model associated with this project is empty, as all illustrated functionality is completely part of the graphical user interface of AIMMS.

If you look at the Template Manager (Alt-F9-key), you can see the templates that determine the look-and-feel of all pages in the end-user interface. The highest level template only determines the size of all pages below it. The next levels of templates successively add new objects to higher-level templates. The objects added comprise:

- rectangles,
- bitmaps,
- buttons, and
- navigation objects.

At the lowest level in the template tree you find all end-user pages. The positions of the end-user pages in the template tree determine which template objects will be displayed on each end-user page.

To some of the templates a number of resizability lines have been added, which determine the behavior of the page when it is resized. You can view these resizability lines by double-clicking a template in the Template Manager, and executing the 'View-Resize Edit Mode' menu. The combined resizability effect is that the area marked 'Data Contents' on all but the first page will grow and shrink with the page size.

If you look at the Page Manager (F9-key), you can see a tree containing all end-user pages in the project. The navigation buttons (containing the arrows) at the lower right part of each page have navigation actions associated with them that are directly linked to the structure of the page tree. Both the blue rectangle at the top of each page (displaying the name of the page), and the remaining buttons at the right of the page are instances of 'navigation' objects. They can be configured either to show just the title of a page, or to automatically generate a tree or a number of navigation buttons for every child (or parent) page. 

In addition, the 'Navigation' menu in the menu bar contains an automatically generated menu, containing an entry for every page in the page tree. You can view how the menu is constructed in the Menu Builder tool (Ctrl-F9-key). 

Keywords:
Navigation object, navigation menu, resizability, page structure, bitmap, button, rectangle


