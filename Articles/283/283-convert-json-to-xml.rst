Convert JSON to XML
=====================

.. meta::
   :description: This article provides a custom AIMMS library for JSON to XML conversion.
   :keywords: xml, json, convert, extract

When you need input data from JSON files, you first need to convert JSON files to XML files to make the data compatible with AIMMS. 

Here we provide a conversion tool in the form of a custom AIMMS library. It contains a conversion AIMMS library between XML and JSON files:

* ConvertFromJsonToXML
* ConvertFromXMLToJson

Procedure
-----------

#. Download :download:`JSONXML.zip <downloads/JSONXML.zip>`.

#. Extract the two folders and put them at the root of your project.

#. Then add this library to your project:

    a. Open the Library Manager.

    .. image:: images/step1.png

    b. Click *Add existing library*.

    .. image:: images/step2.png

    c. Select the ``JSONXML`` folder and click *Select a folder*

    .. image:: images/step3.png

    d. Click *OK* to exit the library manager.

    .. image:: images/step4.png

4. Then by calling the method ``jxml::ConvertFromJsonToXML`` you should be able to obtain your data into an XML file.

Execute the following code to create the XML file:

.. code-block:: aimms
    :linenos:

    jxml::ConvertFromJsonToXML(SP_InputFile,"Answer.xml");

During the conversion, the general structure of the JSON file is conserved. For every JSON element, an XML element is created. 

Note that if the JSON element had a name, then it will be set as a **parameter** of the corresponding XML element.


.. image:: images/conversion.png

To extract the data from your newly created XML file, follow the process described in :doc:`../293/293-extracting-data-from-XML`.


Related Topics
-----------------

* AIMMS How-To: :doc:`../293/293-extracting-data-from-XML`
* AIMMS Documentation: `Collaborative Project Development <https://download.aimms.com/aimms/download/manuals/AIMMS3UG_OrganizingProjectInLibraries.pdf>`_

