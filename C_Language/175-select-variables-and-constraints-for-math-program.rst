.. meta::
   :description: How to select variables and constraints for mathematical program.
   :keywords: variables, constraints, mathematical program, AllConstraints, AllVariables

Selecting variables and constraints for Math Program
====================================================

One feature of AIMMS is that you can have multiple mathematical programs in one project, each having its own set of variables and constraints.

``AllConstraints`` and ``AllVariables``
----------------------------------------

When you solve a mathematical program (or generate it via the GMP functions), AIMMS will use the values of the Constraints and Variables attributes of the mathematical program identifier to determine which symbolic variables and constraints should actually be considered in the model. In these Constraints and Variables attributes of the math program identifier, you must provide a subset of the predefined sets AllConstraints and AllVariables, respectively. Each of these sets must contain exactly those symbolic constraints/variables which should be considered when generating the model. 

.. note::

    If you do not provide any values (or leave the default as is), AIMMS by default will use ``AllConstraints`` and ``AllVariables``. This means that all constraints and variables declared in your project will be considered.

Customizing ``AllConstraints`` and ``AllVariables``
-----------------------------------------------------

You can create your own sets for constraints and variables to be considered by introducing two new set identifiers in AIMMS, e.g. ``ModelVariables`` and ``ModelConstraints``. You then make ``ModelVariables`` a subset of the predefined set ``AllVariables`` and make ``ModelConstraints`` a subset of the predefined set ``AllConstraints``. After that, in your math program identifier you should use these two new sets for the Variables and Constraints attributes. Finally, you can now set data for these two new sets, either by filling them in a procedure with data, or using the definition attribute of the set to select which variables/constraints should be part of the subset.

.. code-block:: aimms

    Set ModelVariables {
        SubsetOf: AllVariables;
        Definition: AllVariables*Section_or_Declaration_to_Optimize;
    }

    Set ModelConstraints {
        SubsetOf: AllConstraints;
        Definition: AllConstraints*Section_or_Declaration_to_Optimize;
    }

    MathematicalProgram myProgram {
        Objective: ObjectiveFunction;
        Direction: maximize;
        Constraints: ModelConstraints;
        Variables: ModelVariables;
        Type: Automatic;
    }


Variables with definition
--------------------------

Be careful with variables that have a definition. Please keep in mind that for each variable with a definition in your model, AIMMS will actually generate both the variable and additional equality constraint. For example, if you have the variable ``X`` that has ``Y + Z`` in its definition attribute, AIMMS will actually generate two things:

* Variable ``X``

* Equality constraint ``X_definition`` defined as ``X = Y + Z``

.. warning::

    If you use the above mentioned approach of selecting certain variables and constraints for a mathematical program identifier in AIMMS, please make sure that all defined variables are also in the set of selected constraints. Otherwise, AIMMS would only consider the variable and not its corresponding equality constraint.

.. include:: ../includes/form.def
