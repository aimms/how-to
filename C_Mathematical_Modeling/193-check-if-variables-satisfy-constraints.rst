Check if given variable values satisfy all constraints
======================================================

Sometimes you might want to check whether a given combination of value assignments for the variables in your model actually satisfy all the constraints. One possibility is to add an assignment constraint for each of your variables that fixes the variable to the given value and then solve the model again. If the solver returns with the status infeasible, you know that these variables values do not satisfy all constraints and otherwise the values of the variables are a feasible solution for your model.

Another approach to figure out whether the given values for the variables are a feasible solution that requires no modifications to your actual model, is to make use of the GMP function GMP::Solution::Check.

Suppose we have the following simple model:
`\mathrm{min\ }x`
s.t.
`x + y \ge 5`
`x,y \ge 0`

and we want to check whether the assignment `x=1\ ,\ y=1` is actually a feasible solution. We must first add identifiers for the required variables (x,y,obj) and constraint to an AIMMS project. After that, you add a math program identifier that links these variables and constraints. Finally, we can then use the following code to actually check whether the assignment `x=1\ ,\ y=1` is a feasible solution::

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
    GMP       : generatedMP , 	!The Math program we want to use
    solution  : 1 , 		!The solution number we want to check
    numInfeas : numberOfInfeasibilities , !store # of infeasibilities 
    sumInfeas : sumOfInfeasibilities ,    !store sum infeasibilities
    skipObj   : 1 ) ; 		!Objective can be skipped
	

 
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

After running the above code, you will get the message that assignment `x=1\ ,\ y=1` does not satisfy all the constraints.

.. download

You can download the above example code in a complete AIMMS project. Please note that this file can only be opened in AIMMS versions 3.11 and newer.
