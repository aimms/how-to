:orphan:Animation with colors in AIMMS =====================================

..       <link>https://berthier.design/aimmsbackuptech/2013/04/02/animation-with-colors-in-aimms/</link>
..       <pubDate>Tue, 02 Apr 2013 13:06:06 +0000</pubDate>..       <dc:creator><![CDATA[]]></dc:creator>..       <guid isPermaLink="false">http://blog.aimms.com/?p=2803</guid>
<![CDATA[<img class="alignright size-thumbnail wp-image-2674" src="http://techblog.aimms.com/wp-content/uploads/sites/5/2013/02/screenshot-pivttable-heatmap-150x150.png" alt="screenshot-pivttable-heatmap" width="150" height="150" />In the first post of this year (<a title="Happy New Year" href="http://blog.aimms.com/2013/01/happy-new-year/">Happy New Year</a>) I showed how you can use the network object in AIMMS to create an animation of exploding fireworks. In another recent post (<a title="Using colors for feedback in pivot table" href="http://blog.aimms.com/2013/02/using-colors-for-feedback-in-pivot-table/">Using colors for feedback in pivot table</a>)I showed how you can use the pivot table in AIMMS to display a heat map.
In this new post, I will show how you can use the user color related
functions UserColorAdd and UserColorDelete in AIMMS to create ananimation of the heat map that is displayed on the right.
The goal is to create an animation that will morph the picture such thateach point that starts out as green ends up as red and vice versa.
On the left you can see a small screencast of the final animation we
want to achieve. In order to do this, I have created 765 (3x255) user
colors in a specific palette that fades from red to green to red to
green.
By letting each point take the next color in every iteration of the
animation, you end up with an animation. You can generate user colors
programmatically with the intrinsic procedure ``UserColorAdd``. Thisprocedure takes 4 arguments: the first one is the name of the new usercolor and the other three arguments are the values for theRed/Green/Blue components (all values between 0 and 255).

Note that a call to UserColorAdd will fail in case there already existsa color with the given name. Therefore, I use the following code thatuses UserColorDelete to delete the user color with the names I needbefore I start adding them:
.. code-block:: aimms    :linenos:    !Delete any color that we need if it already exists.    while LoopCount <= pNumColors do        if stringtoElement(AllColors, "color-" + (loopcount) , 0) then            UserColorDelete( "color-" + (loopcount) ) ;        endif ;    endwhile ;

To create a a range of colors that fades from red to green, we have tostart with only red and no green and keep on increasing the greencomponents while decreasing the red component.
You can achieve this in AIMMS code with the LoopCount operator as follows:
.. code-block:: aimms    :linenos:
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

For an example of how this palette looks, you can open the project (seelink below) and click on the button "Color demo page". You will see apalette that looks similar to:
I have implemented all of the above in a modified version of the 3DChart example (which comes with the default installation of AIMMS). Youcan download my modified version from the link below. Note that thisproject requires AIMMS 3.13 to run. .. todo:: missing: [attachments include="4105"
