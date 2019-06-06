Change Syntax Highlighting and Display Options
==========================================================

.. meta::
   :description: How to customize syntax highlighting colors and other Editor display options used in the AIMMS IDE.
   :keywords: Syntax, highlighting, identifier, color, display, editor, settings

.. note::

    This article was originally posted to the AIMMS Tech Blog.

.. <link>https://berthier.design/aimmsbackuptech/2012/06/18/changing-colors-for-different-identifier-types-in-aimms-ide/</link>
.. <pubDate>Mon, 18 Jun 2012 13:02:10 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1211</guid>

..  <![CDATA[[caption id="attachment_1428" align="alignright" width="362" caption="Display variables in green"]<img src="http://techblog.aimms.com/wp-content/uploads/sites/5/2012/06/variables_in_green.png" alt="Display variables in green" title="Display variables in green" width="362" height="151" class="size-full wp-image-1428" />[/caption]In an <a href="http://blog.aimms.com/2012/05/displaying-line-numbers-in-the-editor-of-the-aimms-ide/" title="Displaying line numbers in the editor of the AIMMS IDE">earlier blog article</a> I already showed that you can toggle the display of line numbering in the AIMMS IDE editor by changing one of the IDE editor settings.

AIMMS uses a default syntax highlighting theme to help you quickly interpret a definition visually in the AIMMS Editor. 

You can customize the colors displayed for types of identifiers in the editor. 

For example, in the image below, the variable ``Assignments`` is highlighted green. 

.. figure:: images/variables_in_green.png

    Display variables in green

To change these editor settings:

1. Go to *Settings > Editor Settings*. A dialog appears.

2. Select an identifier type in the Style Type section on the left, and edit its display settings in the right and top sections.

.. figure:: images/editor-settings-style-identifier.png

    Editor settings for identifiers


In addition to colors, you can customize styling to display identifier types in bold, italic, or underline.

You can also change the styling of comments, keywords, and other elements. In the *Style Type* section select *AIMMS* from the drop-down menu.

.. figure:: images/editor-settings-style-aimms.png

    Editor settings for AIMMS elements

You can also import/export your style settings as .xml to share with others in your organization. Use the *Import* and *Export* buttons in the *Editor Settings* dialog.

AIMMS' in-house Editor settings (as seen in How-To articles and User Support training materials) are available for download in the file below. 

:download:`AIMMS Editor Settings <downloads/EditorSettingsCommunity.xml>`.



Related Topics
--------------

* :doc:`../232/232-display-line-numbers`



