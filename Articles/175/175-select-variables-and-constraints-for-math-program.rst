Select Constraints and Variables for a Math Program Declaration
=======================================================================

.. meta::
   :description: How to select variables and constraints for a mathematical program.
   :keywords: variables, constraints, mathematical program, AllConstraints, AllVariables, goal, programming, sequential

A sample declaration of a math program is shown below. 

.. code-block:: aimms

   MathematicalProgram Sample_Math_Program {
      Objective: ObjFunc;
      Direction: minimize;
      Constraints: AllConstraints;
      Variables: AllVariables;
      Type: Automatic;
   }

* ``Objective`` specifies which variable is the objective function of the math program. 
* ``Direction`` specifies whether you want to minimize or maximize the objective function. 
* ``Constraints`` specifies which set of constraints should be considered. In this case, ``AllConstraints`` will be considered.
* ``Variables`` specifies which set of variables should be considered. In this case, ``AllVariables`` will be considered.
* ``Type`` specifies what kind of a problem the math program is, e.g., a linear program, an integer program, and so on. The default option ``Automatic`` suffices in most cases and is recommended. 

.. seealso::

    * ``AllConstraints`` and ``AllVariables`` are `Model related predeclared identifier <https://download.aimms.com/aimms/download/manuals/AIMMS3FR_PredeclaredModel.pdf>`_ Sets, containing all constraints and all variables defined in your model.
    * `AIMMS Documentation on Mathematical Programs <https://download.aimms.com/aimms/download/manuals/AIMMS3LR_SolvingMathematicalPrograms.pdf>`_


You may have multiple mathematical program identifiers in the same project, subject to different sets of constraints and variables. 
For example, it can be used in a sequential goal programming problem where the solution of the first problem is provided as input to the second problem. 
We will explore how you can control the constraints or variables imposed on a math program. 

Default Constraints and Variables
----------------------------------------

When you solve a mathematical program (or generate it via `the GMP functions <https://how-to.aimms.com/Articles/147/147-GMP-Intro.html>`_), AIMMS will use the values of the ``Constraints`` and ``Variables`` attributes of the mathematical program identifier to determine which symbolic variables and constraints should actually be considered in the model. 
The default values of ``Constraints`` and ``Variables`` attributes are the predefined sets ``AllConstraints`` and ``AllVariables`` respectively. ``AllConstraints`` contains all the constraints declared in your AIMMS project and similarly, ``AllVariables`` contains all the variables. 

Variables with definition
"""""""""""""""""""""""""""""

For variables with a definition, AIMMS will actually generate both the variable and an additional equality constraint. For example, if you have the variable ``X`` that has ``Y + Z`` in its definition attribute:

.. code-block:: aimms

   Variable X {
      Range: free;
      Definition: Y+Z;
   }

AIMMS will generate:

#. Variable ``X``

#. Equality constraint ``X_definition`` as ``X = Y + Z``

So, any variable with a definition (like ``X``) will appear in both the predeclared sets ``AllConstraints`` and ``AllVariables``. 

Selecting Constraints (Variables) 
-----------------------------------------

To select the constraints (variables) to be applied in a math program, you can create a set as a subset of ``AllConstraints`` (``AllVariables``) and use that set in the declaration of the math program instead of ``AllConstraints`` (``AllVariables``). The below below example shows two sets ``ModelConstraints`` and ``ModelVariables`` being used in the math program ``Sample_Math_Program``. 

.. code-block:: aimms

   Set ModelConstraints {
      SubsetOf: AllConstraints;
      Definition: AllConstraints*Section_or_Declaration_to_Optimize;
   }

   Set ModelVariables {
      SubsetOf: AllVariables;
      Definition: AllVariables*Section_or_Declaration_to_Optimize;
   }

   MathematicalProgram Sample_Math_Program {
      Objective: ObjFunc;
      Direction: maximize;
      Constraints: ModelConstraints;
      Variables: ModelVariables;
      Type: Automatic;
   }

You can either manually select the constraints and variables to be included in these subsets or use the definition like above to include all the constraints and variables present in a particular section or declaration section. 
Using a definition is recommended as it offers scalability - any new constraint or variable added inside that ``Section_or_Declaration_to_Optimize`` will be automatically added to the subset and thereby used in the math program generation. 
You also do not need to worry about selecting variables with a definition in both the subsets. 



