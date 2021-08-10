Add logo to WebUI app
======================

This short article illustrates adding a logo to an AIMMS WebUI application.

Please :download:`The AIMMS 4.81 sample application <model/truckMaker.zip>` to experiment with the example.

As provided the top bar of the app looks as follows:

.. image:: images/as-delivered-icon.png
    :align: center


Contents of the folder ``<project>\MainProject\WebUI\resources\css`` are the icon file ``icon.png`` and the css file ``icon.css``.

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

In case you want to tweak the .css file for your application:

#.  Lines 1-3: This code is needed such that the space taken up for the icon is nicely aligned with other elements presented in the top bar of the application. 
    We advise against changing these lines.

#.  On line 7: you can fill in the name of the image of your logo. Acceptable file format is ``.png``.

#.  Lines 8,9 define the size of the icon on screen. Do / don't tweak with: ...

#.  To retain aspect ratio use...

#.  Line 10 specifies the space between the Menu-icon and the app-logo.  Increasing that to 80px looks as follows:

    .. image:: images/increasing-margin-left.png
        :align: center

#.  Line 11 specifies the space between the logo and the app-name.  Increasing that to 87px looks as follows:

    .. image:: images/increasing-margin-right.png
        :align: center

#.  Line 12 options are: 

    #.  ``middle`` (advised)
    
    #.  ``top``
    
    #.  ``bottom``
    
#.  Line 13: transform.  The options can be found at: 
