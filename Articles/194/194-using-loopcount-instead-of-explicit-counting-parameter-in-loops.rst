Using LoopCount instead of explicit counting parameter in loops ====================================================================
.. meta::   :description: Repetition sometimes needed, iterative construct preferred, loop counts reduce need of coding.   :keywords: Iterative, repetition, loop count, sum, for, while, parameter.. note::    This article was originally posted to the AIMMS Tech Blog.
.. <link>https://berthier.design/aimmsbackuptech/2012/04/11/using-loopcount-instead-of-explicit-counting-parameter-in-loops/</link>
.. <pubDate>Wed, 11 Apr 2012 12:18:14 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1126</guid>
There are cases where you want to execute some set of statements in an AIMMS procedure a couple of times. If you want to execute the statements :math:`n` times, the trivial (but not very smart) way would be to just copy the statements :math:`n` times.
A better approach, which I guess is used most often, is to use a repetitive loop (for, while, repeat) and declare a new local parameter for this procedure that will be used to keep track of how many iterations have been done in the loop. To execute a block of statements :math:`n` times, you will first have to initialize this counter parameter to 0 or 1 (depending on your preference to start counting at 0 or 1) and then use a while-loop with a termination criteria to execute the statements repetitively. Furthermore, you must also ensure that you increment the value of your local counter parameter with one in each iteration. So if you declare the additional parameter ``p_LoopCounter``, the required code will look like:
.. code-block:: aimms    :linenos:
    !Initialize the p_LoopCounter to the value 1
    p_LoopCounter := 1 ; 
    while p_LoopCounter <= 3 do
        DialogMessage("Current iteration " + p_LoopCounter ) ; 
        ! And increment the counter by one
        p_LoopCounter += 1 ; 
    endwhile ; 
An even easier approach (but probably far less known) that does not require you to declare any temporary local parameters, is to make use of the predefined ``LoopCount`` operator in AIMMS. With repetitive loops like while, for, and repeat, this ``LoopCount`` operator will automatically be incremented each time the loop is run. Using the ``LoopCount`` operator, the above code could be transformed into the following:
.. code-block:: aimms    :linenos:
    while LoopCount <= 3 do
        dialogMessage("Current iteration " + LoopCount) ; 
    endwhile ; 

If you have multiple nested loops, you can name each of these loops by using a loop string in the statement. You can access the value of a ``LoopCount`` corresponding to one particular of the nested loops by providing the loop string name as an argument to the LoopCount. If you do not provide any loop string, the ``LoopCount`` operator returns the value of the closest repetitive loop. If you run the example code below, you will see that when not providing a loop string, the value returned by the ``LoopCount`` operator is the same as the ``LoopCount`` for the inner-loop because this is the closest repetitive loop... code-block:: aimms    :linenos:
    while LoopCount <= 3 do "outer-loop"
        while LoopCount <= 4 do "inner-loop" 
            dialogMessage(   "Outer : " + LoopCount("outer-loop") + "\n"
                           + "Inner : " + LoopCount("inner-loop") + "\n"
                           + "No string : " + LoopCount ); 
        endwhile ; 
    endwhile ; 
