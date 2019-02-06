Keyboard shortcuts for AIMMS
================================

Although a lot of people prefer to work with the mouse, there is also a big group of people who like to use the keyboard as much as possible. To make life a little bit easier for this group, there are a number of keyboard shortcuts available in AIMMS that allow for quick access to certain features within AIMMS.

In this blog article, I will show the list of shortcuts that are provided by AIMMS.

In AIMMS, the following keyboard shortcuts can be used.

.. insert table

Key	Function
F1	Open AIMMS Help
F2	Rename selected item in a tree (e.g. page, identifier, menu item)
F3	Find and repeat find
F4	Switch between edit mode and end-user mode (for the active page, only in developer mode)
F5	Compile all
F6	Run the MainExecution procedure
Alt+F6	Toggle the AIMMS debugger mode
F7	Save the active page
F8	Open the Model Explorer
Ctrl+F8	Open the Identifier Selector
F9	Open Page Manager
Alt+F9	Open Template Manager
Ctrl+F9	Open Menu Builder
F10	Open Data Manager
Ctrl+F10	Open Data Management Setup
F11	Open Identifier Info dialog
Ctrl+B	Toggle a break point at current line (only available in debugger mode)
Ctrl+D	Open Data Page of the currently selected identifier
Ctrl+F	Open the Find dialog
Ctrl+M	Open Messages Window
Ctrl+P	Open Progress Window
Ctrl+T	View text representation of the selected identifier(s)
Ctrl+Shift+T	View text representation of the whole model
Ctrl+W	Open the Wizard dialog for the currently selected attribute
Ctrl+Space	Name completion (only identifiers declared in the model)
Ctrl+Shift+Space	Name completion (including identifiers predeclared by AIMMS)
Ctrl+Enter	Check, commit, and close current attribute form
Insert	Insert a node (when single insert choice) or Open Select Node Type dialog (when multiple insert choices)


One question I have seen quite often (and also searched for myself) is a way to quickly run a procedure other than the MainExecution as this already has the keyboard shortcut F6. The way I always started a procedure was by right-clicking with the mouse on a procedure in the model explorer and then select Run Procedure from the context menu. One of the global keyboard shortcuts provided by windows is that you can also open a context menu via the keyboard shortcut Shift+F10.

This means that you can run any arbitrary procedure without using the mouse with the following key strokes:

Ctrl+F to open the find dialog
Type the name of the procedure
Press Alt+D to simulate pressing the Declaration button. This will highlight the searched identifier in the model explorer tree
Press Shift+F10 to open the context menu for this procedure
Press the cursor up button to select Run Procedure
Press the Enter key to actually run the procedure
Though the above might seem like a lot of steps, after some practicing you can quickly start any procedure you want this way.

If you know of any more useful keyboard shortcuts (can also be general windows shortcuts that also work within AIMMS, like the above example using Shift+F10) please provide them in a comment!

Addendum: One of my colleagues pointed out that running any arbitrary procedure can also be achieved by using the Procedure item in the Run menu. By pressing Alt+R, followed by P you can open the Run Procedure dialog where you can type the name of the procedure you want to run.

Also, the windows keyboard shortcut Shift+F10 I mentioned, is also available as a direct key on most keyboards, typically located between the Alt and Ctrl keys on the right hand side of the keyboard.