Adding a logo to an AIMMS WebUI app
======================================

This short article illustrates adding a logo to an AIMMS WebUI application.

The example provided
---------------------

Please download :download:`The AIMMS 4.81 sample application <model/diversity.zip>` to experiment with the example.
The top bar of this app looks as follows (snapshot taken with a browser zoom of 200%):

.. image:: images/left-upper-with-icon.png
    :align: center

To achieve this, the following two files:

#.  ``diversity-logo.png``

#.  ``icon.css``

have been added to the folder ``<project>\MainProject\WebUI\resources\css``.

The example explained
----------------------

The contents of the ``icon.css`` are presented below and then discussed.

.. code-block:: css
    :linenos:
    :emphasize-lines: 7

    .theme-aimms header h1 .pages .app-name {
        margin-top: 9px; /* this fixes the vertical alignment of the app name with the logo */
    }

    .theme-aimms header h1 .pages .app-name::before {
        content: '';
        background: url(diversity-logo.png) no-repeat center/contain;
        float: left;
        width: 83.2px;
        height: 28px;
        margin-left: 0px;
        margin-right: 7px;
        transform: translateY(-5px);
    }

Some remarks about the above code, in case you want to adopt and adapt the above ``.css`` file for your application:

#.  Lines 1-3: This code is needed such that the app name is nicely aligned vertically with the logo.

#.  On line 7: you can fill in the name of the image of your logo. 
    Examples of acceptable file formats are ``.png``, ``.jpg``, ``.gif``, ``.svg``, and ``.WebP``.

#.  Lines 9,10 define the size of the area on the screen in which to place the logo. 

    For instance, halving the width results in:

    .. image:: images/halving-width.png
        :align: center

    As you can see, the aspect ratio of the image is kept.

    In the example, the dimension of the image is 3533 x 1189. 
    Therefore, by using height 28 and width 3533 / ( 1189 / 28 ) = 83.2 the image is completely filling the area alotted.

#.  Line 11 specifies the space between the Menu-icon and the app-logo.  Increasing that to 80px looks as follows:

    .. image:: images/increasing-margin-left.png
        :align: center

#.  Line 12 specifies the space between the logo and the app-name.  Increasing that to 87px looks as follows:

    .. image:: images/increasing-margin-right.png
        :align: center

#.  Line 13: Examples of the numerous transformations supported by the `transform property <https://www.w3schools.com/cssref/css3_pr_transform.asp>`_ are rotating, scaling, and moving. 

Tips on tweaking
-----------------

#.  It goes without saying that you should replace ``diversity-logo.png`` by an image file containing the logo of choice. 

#.  In AIMMS Developer you can tweak the file ``icon.css`` and then refresh the browser page to see the result without having to restart the project.

#.  You may want to use different browser zooms, for instance 100% and 200%, to evaluate the result.

#.  Lines 2, 10, and 13 all have to do with vertical alignment. We suggest you:

    *   First, select a height (line 10) and a corresponding width (line 9) to determine how large the area is on which you want to present your logo.
        To select the width you probably want to use: <image-width>/(<image-height>/<selected-height>) to create an area which your logo optimally fills.

    *   Second, move the icon up or down using the ``transform > translate`` attribute as illustrated on line 13.

    *   Finally, move the app name up or down relative to the image by tweaking line 2.

Image attribution
------------------

The logo used in the example was created by using a portion of an image.
The original image created by `Shubham Dhage <https://unsplash.com/@theshubhamdhage?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText>`_ as published on `Unsplash <https://unsplash.com/>`_:

.. image:: images/shubham-dhage-qgo7Tt_NWD0-unsplash.jpg
    :align: center

Unsplash is referenced by `Social Media Today <https://www.socialmediatoday.com/marketing/2015-02-27/20-sites-get-free-stock-images-commercial-use>`_ 


