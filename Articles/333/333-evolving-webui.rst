Evolving WebUI
===============

Based on new insights and customer demands the AIMMS WebUI technology keeps evolving.
As a consequence, WebUI's of applications developed using older versions of AIMMS need 
to be adapted before they can be used with modern versions of AIMMS. 

This article mentions the changes made to functionality of AIMMS WebUI and how to make use of these changes in your applications.

.. The WebUI was introduced in AIMMS 4.3

Before you start upgrading your project
---------------------------------------

Please store a copy of your project in a safe place before you start upgrading your project.
A good practice is using a source code management system.

Determining the version of AIMMS your project uses
--------------------------------------------------

To know which changes may affect your project, it is important to know which version of AIMMS was used to maintain your project.

Every AIMMS project contains a ``.aimms`` file in the root folder.  An example is:

.. code-block:: XML
    :linenos:
    :emphasize-lines: 2

    <?xml version="1.0"?>
    <References AIMMS_Version="4.39.2.1069 (x64)">
        <MainProject Path="MainProject" />
        <Library System="true" Path="AimmsPro" />
        <Library System="true" Path="AimmsWebUI" />
    </References>

On line 2, the AIMMS version is refreshed when a change is made to the model text or the WinUI page manager.
Double clicking this file will open AIMMS 4.39 if it is available on your system, or the latest AIMMS version if it is not.

.. note:: A change **only** in the WebUI of your project does not affect this version number. 
          Therefore is it good practice to make a small change to the model text whenever you are upgrading your project to a newer version of AIMMS.
          I am in the habit of always adding or removing a space to ``MainTermination`` when I start using a newer version of AIMMS on one of my projects. 

AIMMS 4.16 Sets in selection widget
-----------------------------------

AIMMS 4.16 is the last version where selection widget could contain AIMMS sets as contents (and pressing ``select all`` in the selection widget):

.. image:: images/Aimms416FilteringUsingSetInSelectionWidget.png
    :align: center

With AIMMS 4.17, you will need to add a zero one parameter, for instance something like:

.. code-block:: aimms
    :linenos:

    Parameter p01_Selector {
        IndexDomain: sn;
        Range: binary;
    }

Initializing this parameter in ``MainInitialization`` is advised, for instance as:

.. code-block:: aimms
    :linenos:

    p01_Selector(sn) := 0 ;

And then specifying such a parameter in the contents  (and pressing ``select all`` in the selection widget).

.. image:: images/Aimms417FilteringUsingSetInSelectionWidget.png
    :align: center

**Downloads:**

*   :download:`Before, using AIMMS 4.16 <model/AIMMS-4-16-set-selection-widget/AIMMS-4.16/abc416.zip>`

*   :download:`After, using AIMMS 4.17 <model/AIMMS-4-16-set-selection-widget/AIMMS-4.17/abc417.zip>`

AIMMS 4.19 Starting browser
---------------------------

AIMMS 4.19 is the last version of AIMMS where the model developer had to manually start the browser useing something like: ``localhost:12001/Aimms-WebUI/home``.

With AIMMS4.20 onwards, starting the WebUI browser would start the browser directly.

True, no change is made to the project, but it did change the interaction of the model developer.

AIMMS 4.39 WebUI folder position
--------------------------------

AIMMS 4.39 is the last version of AIMMS where the WebUI folder was located as a sub-folder of the project folder.
AIMMS 4.40, the WebUI is a sub-folder of the folder ``MainProject``.

.. image:: images/MoveWebUIFolder.png
    :align: center

When you are using a source code management system, you will want to 
remove the ``WebUI`` folder from the repository before the upgrade and 
add the folder ``MainProject\WebUI`` afterwards!

**Downloads:**

*   :download:`Before, using AIMMS 4.39 <model/AIMMS-4-39-folder-position/AIMMS-4.39/app439.zip>`

*   :download:`After, using AIMMS 4.40 <model/AIMMS-4-39-folder-position/AIMMS-4.40/app440.zip>`


AIMMS 4.45 Element Text
-----------------------

See also `presentation of element names <https://documentation.aimms.com/webui/folder.html#element-text>`_

The names of elements in a table can be displayed with more elaborate names; for instance, when initials of people are used in a set, but you want to see in the tables their actual names.

AIMMS 4.45 is the last version of AIMMS that supports the javascript identifier ``ElementTextMap`` often found in files with names such as ``elementText.js`` or  ``element-text-map.js`` in the folder ``.\MainProject\WebUI\resources\javascript\``.

Such a file looks like:

.. code-block:: javascript
    :linenos:
    :emphasize-lines: 2

    ElementTextMap = {
         "i_sn" : "sp_elaborateNames",
    };

For every data line, on the left we see the name of an index, here ``i_sn``, and on the right the name of a string parameter, here ``sp_elaborateNames``. To convert such a line, we open the set that is the range of the index, here ``s_someElements``, and specify the string parameter as annotation ``webui::ElementTextIdentifier``.  The declaration of the set becomes:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 3 

    Set s_someElements {
        Index: i_sn;
        webui::ElementTextIdentifier: sp_elaborateNames;
    }

Once this conversion is complete, I recommend to remove the corresponding ``.js`` from your project; some file transfer mechanisms don't approve of ``.js`` files in folders or ``.zip`` files.

**Downloads:**

*   :download:`Before, using AIMMS 4.45 <model/AIMMS-4-45-element-text/AIMMS-4.45/abc445.zip>`

*   :download:`After, using AIMMS 4.46 <model/AIMMS-4-45-element-text/AIMMS-4.46/abc446.zip>`


AIMMS 4.49 Annotations
-----------------------

`Data dependent styling <https://documentation.aimms.com/webui/css-styling.html#data-dependent-styling>`_ uses annotations identifiers. 

Up  to and including AIMMS 4.49, the annotation identifier associated with identifier ``X``, needed to be called ``X_annotations``.  From AIMMS 4.50 onwards, the annotation identifier associated with identifier ``X`` can be specified using the annotation attribute ``webui::AnnotationsIdentifier``. This permits the reuse of a single annotations identifiers by multiple other identifier and more freedom in naming identifiers.

In our example, we use in AIMMS 4.49:

.. code-block:: aimms
    :linenos:

    Parameter p_associatedValues {
        IndexDomain: i_sn;
    }
    StringParameter p_associatedValues_annotations {
        IndexDomain: i_sn;
    }

In AIMMS 4.50 we can use:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 3,5

    Parameter p_associatedValues {
        IndexDomain: i_sn;
        webui::AnnotationsIdentifier: sp_associatedValuesAnnotations;
    }
    StringParameter sp_associatedValuesAnnotations {
        IndexDomain: i_sn;
    }

Not only did we add an annotation to the declaration of ``p_associatedValues``, but we also took the opportunity to change the name of the annotations identifier, such that it could adhere to our naming conventions.


**Downloads:**

*   :download:`Before, using AIMMS 4.49 <model/AIMMS-4-49-annotation/AIMMS-4.49/abc449.zip>`

*   :download:`After, using AIMMS 4.50 <model/AIMMS-4-49-annotation/AIMMS-4.50/abc450.zip>`



AIMMS 4.60 Map V1
-----------------------

The map widget was replaced with a new map widget in AIMMS 4.61.
The map v1 widget was available up to AIMMS 4.60.

In our example we use the following declarations:

.. code-block:: aimms
    :linenos:

    Set s_Customers {
        Index: i_Customer, i_CustomerFrom, i_CustomerTo;
    }
    Set s_LatLon {
        Index: i_LatLon;
        Definition: data { Latitude, Longitude };
    }
    Parameter p_Coords {
        IndexDomain: (i_Customer,i_LatLon);
    }
    Parameter p01_TravelArc {
        IndexDomain: (i_CustomerFrom, i_CustomerTo);
        Range: binary;
    }

Where the widget looks as follows, including its specification:

.. image:: images/mapv1.png
    :align: center

To obtain the above image, we:

#.  Specify a two dimensional parameter, whereby the second dimension is over a set with two elements, ``p_Coords`` in the example.

#.  Specify the first index as the node index, ``i_Customer``.

#.  Add a layer for points.

#.  Add a layer for arcs, with contents: ``p01_TravelArc``.

To obtain an equivalent map v2 arc, we:

.. image:: images/mapv2.png
    :align: center

Using the following steps:

#.  Create two one-dimensional parameters over the node set for the latitude and longitude respectively, for instance as follows:


    .. code-block:: aimms
        :linenos:

        Parameter p_Latitude {
            IndexDomain: i_Customer;
            Definition: p_Coords(i_Customer, 'Latitude');
        }
        Parameter p_Longitude {
            IndexDomain: i_Customer;
            Definition: p_Coords(i_Customer, 'Longitude');
        }

#.  Create a new widget of the same size, of type map

#.  In this new widget we add a node set, with specification 

    *   ``index:`` : ``i_Customer``

    *   ``Latitude`` : ``p_Latitude(i_Customer)``

    *   ``Longitude`` : ``p_Longitude(i_Customer)``

#.  Next, we add an arc set as follows:
    
    .. image:: images/mapv2arcset.png
        :align: center

Some advantages of map V2 widgets over map v1 widgets are:

* Support for multiple node sets

* Support for multiple arc sets

* Zooming and center control

**Downloads:**

*   :download:`Before, using AIMMS 4.60 <model/AIMMS-4-60-map-v1/AIMMS-4.60/ShowGeocdes460.zip>`

*   :download:`After, using AIMMS 4.61 <model/AIMMS-4-60-map-v1/AIMMS-4.61/ShowGeocdes461.zip>`










