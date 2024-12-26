Check if Variable Values Satisfy Constraints
==============================================

.. meta::
   :description: Before solving a mathematical program, you can check whehter the current values satisfy some or all of the constraints.
   :keywords: validation, initial value, checking mathematical programming instance


This article explains how to check whether a given combination of value assignments for the variables in your model satisfy all the constraints to produce a feasible solution. 

You could add an assignment constraint for each of your variables that fixes the variable to the given value and then solve the model again. If the solver returns with the status infeasible, you know that these variable values do not satisfy all constraints.

Or, better yet, use the GMP function :any:`GMP::Solution::Check`.

Suppose we have the following simple model:

.. math:: \begin{align*} &\mathrm{min} \quad x \\[3pt] &\mathrm{s.t.} \quad \begin{array}[t]{r c r c r} x & + & y & \geq & 5 \\ x & & & \geq &  0 \\  & & y & \geq &  0 \end{array} \end{align*}


and we want to check whether the assignment :math:`x=1\ ,\ y=1` is actually a feasible solution. 

First, add identifiers for the required variables ``(x,y,obj)`` and constraint to an AIMMS project. 

Then, add a math program identifier that links these variables and constraints. 

Finally, use the following code to check whether the assignment :math:`x=1\ ,\ y=1` is a feasible solution:

.. code-block:: aimms
    :linenos:
    
    !We want to check if x=1, y=1 is a feasible solution for our MathProgram

    !First we must generate the GMP for our MathProgram
    generatedMP := GMP::Instance::Generate(
       MP   : MathProgram ) ; 

    !Then we must assign the values we want to check to the variables
    x := 1 ; 
    y := 1 ; 

    !Now retrieve the values from the variables in the model to
    !solution number 1 of this generated math program.

    GMP::Solution::RetrieveFromModel(
        GMP      : generatedMP , 
        solution : 1 ) ; 


    !We can now use the GMP::Solution::Check to check solution
    !number 1.
    GMP::Solution::Check(
        GMP       : generatedMP ,   !The Math program we want to use
        solution  : 1 ,         !The solution number we want to check
        numInfeas : numberOfInfeasibilities , !store # of infeasibilities 
        sumInfeas : sumOfInfeasibilities ,    !store sum infeasibilities
        skipObj   : 1 ) ;       !Objective can be skipped

    !Now we can check the number of infeasibilities. If there are no 
    !infeasibilities, it means the variables values satisfy all constraints
    if numberOfInfeasibilities then 
        message := "Variable values do not satisfy all constraints:\n"  ; 
        message += "Number of infeasibilities = " + numberOfInfeasibilities + "\n" ; 
        message += "Sum of infeasibilities = " + sumOfInfeasibilities ; 
        dialogMessage( message ) ; 
    else
        dialogMessage("Variable values are feasible for all constraints") ; 
    endif ; 


After running the above code, you will get the message that assignment :math:`x=1\ ,\ y=1` does not satisfy all the constraints.


The example code above is available in a complete AIMMS project, attached below.

:download:`Check-Solution.aimmspack <downloads/Check-Solution.aimmspack>`



