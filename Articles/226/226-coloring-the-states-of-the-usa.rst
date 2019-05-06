Coloring the states of the USA================================
.. meta::   :description: Using Constraint Programming to determine a nice coloring of the states of the USA.   :keywords: Constraint Programming, network coloring.. note::    This article was originally posted to the AIMMS Tech Blog.
.. <link>https://berthier.design/aimmsbackuptech/2012/12/05/coloring-the-states-of-the-usa/</link>
.. <pubDate>Wed, 05 Dec 2012 14:28:44 +0000</pubDate>.. <guid isPermaLink="false">http://blog.aimms.com/?p=2303</guid>.. figure:: images/United_States_orthographic.png    Location United States of America
Map coloring is a very nice introductory problem to constraint programming; Given a graph :math:`G=(V,E)` with vertices :math:`V` and edges :math:`E` in between, what is the minimum number of colors needed such that two adjacent vertices, :math:`v1` and :math:`v2`, have different colors? One obvious application of this problem is in the construction of maps with two adjacent regions that can easily be distinguished by their colors. Creating a map coloring application has two distinct aspects; the problem itself, and the presentation of the solution. Both are described in this article.

The CP Problem----------------
First, we want to solve instances of this problem. From theory it is known that four colors suffice to solve the problem, and we have a set of 4 colors named ``selectedColors``. The element variable ``stateColor(s)``, with range ``selectedColors``, represents the color of each state. The constraint that two adjacent states have a different color, is modeled directly as:.. code-block:: aimms    :linenos:
    ElementVariable stateColor {        IndexDomain: (s);        Range: SelectedColors;    }
    Constraint AdjacentStatesHaveDifferentColors {        IndexDomain: (s1,s2)|s1 < s2 and s2 in AdjacentStates(s1);        Definition: stateColor(s1) <> stateColor(s2);    }
That's it; the constraint programming problem is described by one element variable and one constraint.

The solution presentation-------------------------
AIMMS offers an easy way to present the solution; just right click on the variable and select data. The pivot table presented clearly contains the solution, but at a glance, does not enable one to see whether or not this solution is correct. For instance, Alaska and Hawaii are not presented in the solution because they are not adjacent to another state, and therefore are not part of the problem passed to the solver.
In order to get a solution that can be more easily inspected, we should actually draw the map.
Luckily, the AIMMS GIS Support example already contains a lot of GIS data about the states of the USA in the folder "US States GML Files", so I was able to copy this folder as a first step.
Next, you should create a network object without any nodes or arcs; we are interested in its background. Initially, to view the entire world, the visible area and bounds are set to: left: -180, right: 180, top 90, bottom -90. More appropriate bounds can be set later on. In addition, remove the check on equal X and Y scale, otherwise these bounds will continue to be adapted to the actual aspect ratio of the object on screen.
On the background tab of the network properties, after selecting "From GIS source" for background, we can specify the GIS data of each state. The trick here is to specify each state as a separate layer with its own GIS source, named stateSource(s), with type "GML" taken from file StateGMLFile(s). GIS layers can overlap, they just happen not to in this example. In the appearance of a layer, we can fill in the color: which coincides with the element variable of the CP problem.
Once this works, do not forget to adapt the visible area to your liking.
More information on GIS and network objects can be found in Help - contents and index - contents tab - page manager - page objects - Network - GIS.
A nice added touch is in making the page re-sizable. I use the following trick; put your page back in edit mode, and from the view tab, select resize edit mode. Click on the button "New horizontal split line" and place it near the bottom or top of the page. Now you have a page that makes effective use of laptop screens and desktop screens alike.
 The model can be downloaded :download:`here <model/State-coloring-of-USA.zip>`

To run this example, just press ``F6`` to see the following solution:
.. figure:: images/State-Coloring-of-the-USA-solution.png    Solution State Coloring of the USA
