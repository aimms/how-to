:orphan:


Animation with colors in AIMMS 
=====================================



In this Article, we will show how you can use the user color related
functions UserColorAdd and UserColorDelete in AIMMS to create an
animation of the heat map that is displayed on the right.

The goal is to create an animation that will morph the picture such that
each point that starts out as green ends up as red and vice versa.

On the left you can see a small screen recording of the final animation we
want to achieve. In order to do this, I have created 765 (3x255) user
colors in a specific palette that fades from red to green to red to
green.

By letting each point take the next color in every iteration of the
animation, you end up with an animation. You can generate user colors
programmatically with the intrinsic procedure :any:`UserColorAdd`. This
procedure takes 4 arguments: the first one is the name of the new user
color and the other three arguments are the values for the
Red/Green/Blue components (all values between 0 and 255).



Note that a call to UserColorAdd will fail in case there already exists
a color with the given name. Therefore, I use the following code that
uses UserColorDelete to delete the user color with the names I need
before I start adding them:

.. code-block:: aimms
    :linenos:

    !Delete any color that we need if it already exists.
    while LoopCount <= pNumColors do
        if stringtoElement(AllColors, "color-" + (loopcount) , 0) then
            UserColorDelete( "color-" + (loopcount) ) ;
        endif ;
    endwhile ;



To create a a range of colors that fades from red to green, we have to
start with only red and no green and keep on increasing the green
components while decreasing the red component.

You can achieve this in AIMMS code with the LoopCount operator as follows:

.. code-block:: aimms
    :linenos:

    !Original palette uses red for the lowest value and green for the highest value
    !This means that the Red component of the color decreases and the Green component
    !of the color increases
    while LoopCount &lt;= 255 do
        UserColorAdd(
            color_name : "color-" + loopCount , 
            red        : 255 - Loopcount , 
            green      : LoopCount  , 
            blue       : 0 ) ; 
    endwhile ;


For an example of how this palette looks, you can open the project (see
link below) and click on the button "Color demo page". You will see a
palette that looks similar to:

I have implemented all of the above in a modified version of the 3D
Chart example (which comes with the default installation of AIMMS). You
can download my modified version from the link below. Note that this
project requires AIMMS 3.13 to run. 

.. todo:: missing: [attachments include="4105"


