Setting Options
================

The behaviour of the AIMMS engine and its solvers can be tailored via extensive list of options. 
In this article we list the various ways to set these options. 

Persistence of options
----------------------

When a project is saved, option settings are saved to the file ``MainProject/Settings/Options.txt``.
This is a text file with the format

.. code-block:: none

    <option-name> <value>

Lines starting with an ``*`` are comment.

With a text editor you can change these settings, but AIMMS offers a dialog for this, which detailed next.

The project options dialog
--------------------------

The Project Options dialog can be reached via the AIMMS Menu - Settings - Project Options.

Initially it looks like this:

.. image:: images/ProjectOptions.PNG

Selecting the non-default options, and then, for instance the Appearance - Default UI option, the following help appears:

.. image:: images/OptionHelp.PNG

The project options dialog is set up such that invalid values for options cannot be entered this way.
It is a valuable tool for those options that you want to change from its default persistently.

The OPTION statement
--------------------

The option statement is a traditional way of specifying the values of options. 
An example is:

.. code-block:: aimms

    OPTION MIP_relative_optimality_tolerance := 1e-013;
    
Note that after executing this statement the corresponding option setting may appear in the file ``MainProject/Settings/Options.txt`` upon project save and thereby made persistent unexpectedly.

The OPTION statement is documented in AIMMS The language reference, section "The OPTION and PROPERTY statements".


The procedures ``OptionSetValue`` and ``OptionSetString``
-----------------------------------------------------------

A slightly more modern way of setting option values is via the procedures ``OptionSetValue`` and ``OptionSetString``.
An equilavent to the option statement in the previous section is:

.. code-block:: aimms

    OptionSetValue( "MIP_relative_optimality_tolerance", 1e-013);
    
The advantage of this method over the option statement is that there is more flexibility; both arguments can be any valid expression.
However, the major drawback of the option statement, namely that changed settings might be made persistent unexpectedly in the file ``MainProject/Settings/Options.txt``, also applies to this method.

The procedures operating on options are documented in AIMMS The function reference, Chapter "Option manipulation".

The block where statement
--------------------------

The block where statement changes the value of an option temporarily, changes are reverted when the engine encounters the endblock statement.
Following our example, the syntax is as follows:

.. code-block:: aimms

    block where MIP_relative_optimality_tolerance := 1e-013;
        <statements>
    endblock ;

As the setting is reverted at the endblock statement, you do not have to worry that the setting is made persistent unexpectedly.

The block statement is documented in AIMMS The language reference, section "The BLOCK statement".

.. include:: /includes/form.def

 











