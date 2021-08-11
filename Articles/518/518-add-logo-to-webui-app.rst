:orphan:

Add logo to WebUI app
======================

This short article illustrates adding a logo to an AIMMS WebUI application.

Please download :download:`The AIMMS 4.81 sample application <model/truckMaker.zip>` to experiment with the example.

As provided the top bar of the app looks as follows:

.. image:: images/left-upper-with-icon.png
    :align: center

To achieve this, the following two files:

#.  ``icon.png``

#.  ``icon.css``

have been added to the folder ``<project>\MainProject\WebUI\resources\css``.
Of course you should replace ``icon.png`` by an image file of your liking. 
The contents of the ``icon.css`` are presented below and then discussed.

.. code-block:: css
    :linenos:
    :emphasize-lines: 7

    .theme-aimms header h1 .pages .app-name {
        margin-top:5px; /* this fixes the alignment with the icon */
    }

    .theme-aimms header h1 .pages .app-name::before {
        content: ' ';
        background: url(icon.png) no-repeat center/contain;
        float: left;
        width: 23px;
        height: 22px;
        margin-left: 0px;
        margin-right: 7px;
        vertical-align: middle;
        transform: translateY(-3px);
    }

Some remarks about the above code, in case you want to adopt and adapt the above ``.css`` file for your application:

#.  Lines 1-3: This code is needed such that the space taken up for the icon is nicely aligned with other elements presented in the top bar of the application. 
    We advise against changing these lines.

#.  On line 7: you can fill in the name of the image of your logo. Acceptable file format is ``.png``.

#.  Lines 9,10 define the size of the icon on screen. 

    For instance, halving the width results in:

    .. image:: images/halving-width.png
        :align: center
        
    Note that aspect ratio of the image is kept.
    
    You probably want to set the width to <image-width>/(<image-height>/22) of the image of your logo and keep the height as in the example ``.css`` file.

#.  Line 11 specifies the space between the Menu-icon and the app-logo.  Increasing that to 80px looks as follows:

    .. image:: images/increasing-margin-left.png
        :align: center

#.  Line 12 specifies the space between the logo and the app-name.  Increasing that to 87px looks as follows:

    .. image:: images/increasing-margin-right.png
        :align: center

#.  Line 13 specifies that image should be vertically aligned in the middle of the available space. The options are: 

    #.  ``middle`` (advised)

        .. image:: images/as-delivered-icon.png
            :align: center

    #.  ``top`` 

        .. image:: images/verticle-align-top.png
            :align: center

    #.  ``bottom``
    
        .. image:: images/verticle-align-bottom.png
            :align: center

    As you can see, when the image nicely fits the region it is presented in, the difference, if any, is hardly noticeable.

#.  Line 13: Examples of the numerous transformations supported by the `transform property <https://www.w3schools.com/cssref/css3_pr_transform.asp>`_ are rotating, scaling, and moving. 

 
.. Questions:
.. Lines 8,9: Is it correct that aspect ratio is kept? 
..            Should the advise therefore be to actually obtain width of image and compute line... as ...
..            To retain aspect ratio use... / To ignore the aspect ratio use...
.. Line 13: good ref?
