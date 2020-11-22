Playing with the legend widget
===============================

.. https://colorspace.r-forge.r-project.org/articles/hcl_palettes.html
.. http://tsitsul.in/blog/coloropt/
.. https://seaborn.pydata.org/tutorial/color_palettes.html 
.. https://carto.com/carto-colors/ (premium)
.. https://colorbrewer2.org/#type=sequential&scheme=BuGn&n=3 The classic site for creating color schemes.
.. https://medialab.github.io/iwanthue/ Generates anything you want, but make sure you know what you want ;-)
.. https://lisacharlotterost.de/2016/04/22/Colors-for-DataVis/ nice overview of various color palette sites.

This article plays with annotations to relate:

#.  The colors documented in the legend widget, to 

#.  The colors of the objects shown in graphical widgets such as the bar chart, the bubble chart and the Gantt chart.

The running example
---------------------

The example used is just an abstract example with no relation to a reality and :download:`can be downloaded here <model/barlegend.zip>`

In this example there are three indices: ``i_a``, ``i_b`` and ``i_c``.
There are also three legend widgets, corresponding to each of the indices, but only one of these three is visible.

#.  Using legend A. With this legend we focus on the aspect ``A`` of the data for visual differentiation.

    .. image:: images/color-on-index-i-a.png
        :align: center

#.  Using legend B. With this legend we focus on the aspect ``B`` of the data for visual differentiation.

    .. image:: images/color-on-index-i-b.png
        :align: center
        
    As you can see, index ``i_b`` is used as the stacking index in the bar chart.

#.  Using legend C

    .. image:: images/color-on-index-i-c.png
        :align: center
        
    As you can see, index ``i_c`` covers some aspect of a bubble or a job in the Gantt chart.

The .css file
-------------

In the ``colors.css``, stored in the folder ``MainProject/WebUI/resources/css`` contains three sections.
In the first section, each hexadecimal code in a list is given a name, as follows:

.. code-block:: css
    :linenos:

    --planning-qualitative-color-01: #8dd3c7 ;

Next, we color the areas of the graphical data visualization widgets, bar chart, bubble chart, and Gantt chart; according to the guide lines in the `WebUI documentation <https://documentation.aimms.com/webui/css-styling.html#widgets-and-css-properties-supported-for-annotations>`_.

.. code-block:: css
    :linenos:

    .annotation-qualitative01{
            fill: var(--planning-qualitative-color-01);
    }

Same for the legend widget, except that the background of the squares is used.

.. code-block:: css
    :linenos:

    .aimms-widget.tag-legend-widget .annotation-qualitative01{
            background: var(--planning-qualitative-color-01); 
    }



Defining the annotations
------------------------

When you opened the ``colors.css`` file in a text editor, you'll see that there are essentially twelve colors available in the palette given. We do not count color 0, as that is just broken white. So  we use 

.. code-block:: aimms
    :linenos:

    Parameter p_noCoiorsInPalette {
        Definition: 12;
    }
    
in the following.


Now we can map a position is the set of interest onto an annotation as follows:

.. code-block:: aimms
    :linenos:

    StringParameter sp_annotA {
        IndexDomain: i_a;
        Definition: {
            formatString("qualitative%02i",
                if mod(ord(i_a),p_noCoiorsInPalette) 
                then mod(ord(i_a),p_noCoiorsInPalette) 
                else p_noCoiorsInPalette endif)
        }
    }

And similarly, for aspects B and C.
By using binary parameters, say ``bp_coloringAccordingToA``, ``bp_coloringAccordingToB``, and ``bp_coloringAccordingToC`` to select the aspect we want the legend to focus on, we can subsequently construct a annotation string parameter for the bar chart as follows:

.. code-block:: aimms
    :linenos:

    StringParameter sp_annotDat {
        IndexDomain: (i_a,i_b,i_c);
        Definition: {
            if bp_coloringAccordingToA then sp_annotA(i_a)
            elseif bp_coloringAccordingToB then sp_annotB(i_b)
            else sp_annotC(i_c)
            endif
        }
    }


Note that, if our data parameter of interest has an index domain condition, we should use the same index domain condition in the annotations string parameter as well.

The widgets
-----------

The legend
^^^^^^^^^^^^

There are three legend widgets, for each of the aspects ``A``, ```B``, and ``C`` one. Their contents is a binary parameter, respectively ``bp_a(i_a)``, ``bp_b(i_b)``, and ``bp_c(i_c)``. Note that each of these binary parameters has a corresponding annotation string parameter.  These annotation, are linked to a CSS rule, as specified above.

The bar chart
^^^^^^^^^^^^^^

The bar chart is indexed over ``i_a``, and ``i_c``; but is stacked over ``i_b``. The annotation string parameter used is specified above.

The bubble and Gantt charts
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The bubbles shown form a circle. The Gantt forms is a simple schedule.

In the running example, the annotations identifier happen to be the same, and are declared as follows:

.. code-block:: aimms
    :linenos:

    StringParameter sp_annotGC {
        IndexDomain: (i_a,i_b);
        Definition: {
            if bp_coloringAccordingToA then sp_annotA(i_a)
            elseif bp_coloringAccordingToB then sp_annotB(i_b)
            else sp_annotC(ep_aspectC(i_a, i_b))
            endif
        }
    }

wich is very similar to ``sp_annotDat(i_a,i_b,i_c)``, except that aspect ``C`` depends on aspects ``A`` and ``B``.

Here ``ep_aspectC(i_a, i_b)`` is defined as:

.. code-block:: aimms
    :linenos:

    ElementParameter ep_aspectC {
        IndexDomain: (i_a,i_b);
        Range: s_c;
        Definition: Element( s_c, mod( ord(i_a)+ord(i_b), card(s_c) ) + 1 );
    }

This latter definition is just to have some data relating aspect ``C`` to ``A`` and ``B``.



