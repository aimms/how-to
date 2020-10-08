:orphan:

Convert JSON to XML
=====================

.. meta::
   :description: This article provides a custom AIMMS library for JSON to XML conversion.
   :keywords: xml, json, convert, extract
   
.. tip:: If you are reading this because you want to exchange data in an AIMMS model with a JSON file, you are probably better of using the `Data Exchange <https://documentation.aimms.com/dataexchange/index.html>`_ library.

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

Extracting data.
-----------------
Extracting data from this XML requires a quite specific process. You can see in the image above that each element has an element name in the format **json:Object-Type**.
When you'll generate your XSD file, this notation will not be there anymore and you'll only have **Object-Type**.

Hence, by generating the .AXM file using the XML schema mapping tool with that XSD, the AXM will neither have this notation.
So, the  ``XMLREAD()`` method will not be executed properly because AXM and XML will have different element names.

In order to solve this situation, you can follow this process:

* Generate your XSD file as usual.
* Create your mapping using the XML schema mapping tool
* Edit your AXM file at the root of your project by transforming every element name **ElementMapping name="(OBJECT-TYPE)"** into **ElementMapping name="json:(OBJECT-TYPE)"**

.. Warning:: be careful to transform only element names and not attributes names.

If you want more details about the general steps to extract data from an XML file you can check this article : :doc:`../293/293-extracting-data-from-XML`.


Related Topics
-----------------

* AIMMS How-To: :doc:`../293/293-extracting-data-from-XML`
* AIMMS Documentation: `Collaborative Project Development <https://download.aimms.com/aimms/download/manuals/AIMMS3UG_OrganizingProjectInLibraries.pdf>`_

