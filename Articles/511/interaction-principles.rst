.. raw:: html
  
  <style>
      h2:before {
        height: 5px;
        width: 80px;
        background: #000081;
        display: block;
        content: '';
        margin-bottom: 14px;
    }
  </style>

Interaction principles
==========================

The AIMMS UX toolkit gives a lot of freedom to design applications. 
However, we recommend to follow the interaction principles described on this page for the benefit of end users. 
This will make future AIMMS apps easier to use and the suites of apps more consistent.

Interface elements
----------------------


App developers have the following main interaction elements at their disposal:

- The Canvas, populated by Widgets: **display** information and **input** data.
- Menu bar: **navigate** between pages.
- Action menu: **trigger** page-specific actions
- Side panel: **contextual pages** in the entire app (global)
- Workflow: block that allows clustering of pages in **consecutive steps**.

Principles about Buttons
----------------------------

Buttons were commonly used for many purposes in the UX 1.0. Now, new features are available with more specific 
purposes to provide consistency and reduce clutter. Removing buttons from the canvas by using these new features makes your app more responsive and predictable.

To trigger a navigation action, it is best to use a menu bar item or workflow. To trigger an Action (e.g. Solve or Commit Changes), 
you can use the `Action Menu <https://documentation.aimms.com/webui/page-settings.html#page-actions>`_. 
To modify something in a widget, use the `Widget Actions <https://documentation.aimms.com/webui/widget-header.html#widget-actions-widget-action>`__.

This ensures the canvas delivers a clear and focused display and input of data.

For more information on buttons, see the chapter on :doc:`interaction-elements`


Principles about Pages
----------------------------

The UX toolkit offers the flexibility to create an unlimited number of pages in a hierarchical tree structure.

We aim to use no more than 3 levels of hierarchy and up to 40 pages for an application. More pages will confuse the user, and will make the menu bar very large. 
If more than 25 pages are needed, consider if this can be solved by splitting the app into two applications.

For more information on the menu and pages, see the chapter on the :doc:`webui/app-menu-bar`.