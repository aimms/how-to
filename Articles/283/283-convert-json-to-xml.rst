:orphan:

Convert JSON to XML
=====================

History
--------

In 2018, a library to exchange data between the JSON and XML formats was developed to permit data exchange between services that provide
their data only in JSON format and AIMMS that allowed data exchange via XML. Nowadays, the `Data Exchange Library <https://documentation.aimms.com/dataexchange/index.html>`_ is provided making the original use case for this XML JSON conversion library obsolete.

Current available tools
--------------------------

There are several use cases for working with JSON and XML files:

#.  Convert a JSON file to an XML file and subsequently use the AIMMS predeclared functions :aimms:procedure:`ReadXML` or :aimms:procedure:`ReadGeneratedXML` to read the data from the XML file into AIMMS identifiers.
    If that is your use case, you are likely better off using the `Data Exchange Library <https://documentation.aimms.com/dataexchange/index.html>`_

#.  Convert an XML file generated using :aimms:procedure:`WriteXML` or :aimms:procedure:`GenerateXML` to a JSON file.
    If that is your use case, you are likely better off using the `Data Exchange Library <https://documentation.aimms.com/dataexchange/index.html>`_

#.  You want to convert a JSON file to an XML file, but you do not want to exchange the data in those files with AIMMS identifiers in your model.
    Perhaps the library from 2018 is still useful for you: :doc:`XML JSON conversion article<../283/283-convert-json-to-xml-original>`



