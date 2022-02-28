
.. meta::
   :description: How to set up data exchange within your AIMMS application.
   :keywords: aimms, data, exchange

Dealing with different data types in the DEX
=============================================

The Data Exchange Library (DEX) allows mapping to and from tree-based data formats in the following supported formats:

* JSON
* XML
* Table-based CSV
* Table-based Excel
* Parquet 

You can find more documentation about the DEX and formats on `this documentation page <https://documentation.aimms.com/dataexchange/standard.html>`__.

In this how-to article we will explain how to implement the usage of these data formats in mapping files and, if applicable, format-specific requirements. The examples make clear that each mapping closely follows the structure of the file being described. Thus, if you know the format of the file to map, creating a corresponding mapping file for the Data Exchange Library is rather straightforward.

We will show examples of mapping data into your AIMMS identifiers, but note that you can also write data from AIMMS identifiers into a specified datafile by using the function :any:`dex::WriteToFile`. An example of this is given for the Excelfile.

Prerequisites
--------------

#. To be able to map any data into your AIMMS model, you need to have the Data Exchange Library installed. Visit `this article <https://documentation.aimms.com/general-library/getting-started.html>`__ for instructions on how to do this.

#. A mapping file is necessary for mapping, preferably saved in a folder called 'Mappings'. Visit `this article <https://documentation.aimms.com/dataexchange/mapping.html>`__ to read more about mappings and how to write one for your specific situation. Note that you can also `generate a mapping file automatically for your project <https://documentation.aimms.com/dataexchange/standard.html#creating-your-own-annotation-based-formats>`__. 


Helpful remarks & frequent errors
-----------------------------------

* In general, your sources' data model should match 100% with the AIMMS model and translate as such in the mappingfile. This means that every element that you put into your mappingfile, should on an individual level match with the paired AIMMS identifier type. For example: an array should go into an indexed parameter.
* Following the aforementioned, make sure that your parameter in AIMMS is set to the parameter type to align with the data as it will be mapped to AIMMS. 
* Mind that no errors or warnings will be given if the "name=" element is written incorrectly in a tag in your mappingfile - the data will simply not map in this case.
* The order of rows and columns should always be aligned with the order of the indices of the parameter.
* A common known error is *"The maps-to attribute 'x' for node 'y' refers to an non-existing identifier"*. In this case it is helpful to check if you have written the "maps-to" element correctly, including a possible index.
* Another commonly known error is *"The dimension of the maps-to attribute x for node y does not coincide with the specified numbers of indices"*. In this case the most probable cause is that the element is referring to an AIMMS-identifier that should have at least one index defined but where no index can be found (like for an indexed parameter), where the index is not properly named in the mappingfile or where more indices are expected than defined (or the other way around).


JSON mapping (importing data, one-dimensional identifier)
----------------------------------------------------------

Suppose the following JSON-formatted data, saved in a folder called 'data' with name 'data.json':

.. code-block:: json

    {
        "country": "The Netherlands",
        "population": 17.440000,
        "array": [
            {
                "city": "Amsterdam",
                "lat": 52.34996869
            },
            {
                "city": "The Hague",
                "lat": 52.08003684
            },
            {
                "city": "Rotterdam",
                "lat": 51.9199691
            }
        ]
    }

This JSON-file holds an object with three children, one of which is an array holding multiple structurally identical objects, bound to an index ``city``. A matching mappingfile, stored in a folder called 'Mappings' with name 'JSONMapping.xml', could look like: 

.. code-block:: xml

	<AimmsJSONMapping>
        <ObjectMapping>
            <ValueMapping name="population" maps-to="population"/>
            <ValueMapping name="country" maps-to="countries"/>
            <ArrayMapping name="array">
                <ObjectMapping>
                    <ValueMapping name="city" binds-to="city"/>
                    <ValueMapping name="lat" maps-to="lat(city)"/>
                </ObjectMapping>
            </ArrayMapping>
        </ObjectMapping>
    </AimmsJSONMapping>

Note the start- and ending tags ``AimmsJSONMapping`` specific for JSON-formatted data. The ``ValueMapping`` is used for the children and the ``ArrayMapping`` holds its own ``ValueMapping`` tags for its elements. 

The procedure to read data into the model in AIMMS will be:

.. code-block:: aimms
    
    dex::AddMapping(
	"JSONMapping",
	"Mappings/JSONMapping.xml"
	);

	dex::ReadFromFile(
	"data/data.json", 
	"JSONMapping", 
	1, 
	1, 
	1
	);

Your model will look like this:

.. image:: images/jsonandxml_example.png
   :scale: 70
   :align: center



XML Mapping (importing data, one-dimensional identifier)
----------------------------------------------------------

Assume the following XML-formatted data, stored in a folder 'data' with the name 'data.xml':

.. code-block:: xml

    <RootObject>
        <country>The Netherlands</country>
        <population>17.440000</population>
        <array>
            <lat city="Amsterdam">52.34996869</lat>
            <lat city="The Hague">52.08003684</lat>
            <lat city="Rotterdam">51.9199691</lat>
        </array>
    </RootObject>

It describes an XML file with an object with three children, one of which is another object holding multiple structurally identical values, bound to an index ``city``. A matching mappingfile, stored in a folder called 'Mappings' with name 'XMLMapping.xml', could look like: 

.. code-block:: xml

    <AimmsXMLMapping>
    <ElementObjectMapping name="RootObject">
        <ElementValueMapping name="country" maps-to="countries"/>
        <ElementValueMapping name="population" maps-to="population"/>
        <ElementObjectMapping name="array">
            <ElementValueMapping name="lat" maps-to="lat(city)">
                <AttributeMapping name="city" binds-to="city"/>
             </ElementValueMapping>
        </ElementObjectMapping>
    </ElementObjectMapping>
	</AimmsXMLMapping> 
    
Note the start- and ending tags ``AimmsXMLMapping`` specific for XML-formatted data. Following the XML-structure of the datafile, the ``ElementValueMapping`` is used for the children and the ``EllementObjectMapping`` holds its own ``ElementValueMapping`` tags for elements. 

AIMMS procedure to read data:

.. code-block:: aimms
    
    dex::AddMapping(
	"XMLMapping",
	"Mappings/XMLMapping.xml"
	);

	dex::ReadFromFile(
	"data/data.xml", 
	"XMLMapping", 
	1, 
	1, 
	1
	);

With result:

.. image:: images/jsonandxml_example.png
   :scale: 70
   :align: center



CSV mapping (importing data, n-dimensional identifier)
---------------------------------------------------------

Let's work with the following CSV-formatted data, in which we can see multiple rows, each consisting of multiple named columns:

.. code-block:: xml
    
    country,city,lat,long
    The Netherlands,Amsterdam,52.34996869
    The Netherlands,The Hague,52.08003684
    The Netherlands,Rotterdam,51.9199691
    Belgium,Antwerpen,51.22037355

Let's assume this file is saved in a folder 'data' and called 'data.csv'.

The related mappingfile, in which the repetitive structure of multiple rows and their multiple named column leaf-nodes are being bound to ``country`` and ``city``, or to multi-dimensional identifiers over these two indices, would look like this:

.. code-block:: xml

    <AimmsCSVMapping>
        <RowMapping name="table1">
            <ColumnMapping name="country" binds-to="country"/>
            <ColumnMapping name="city" binds-to="city"/>
            <ColumnMapping name="lat" maps-to="lat(country,city)"/>
        </RowMapping>
    </AimmsCSVMapping>

The procedure in AIMMS:

.. code-block:: aimms
    
    dex::AddMapping(
	"CSVMapping",
	"Mappings/CSVMapping.csv"
	);

	dex::ReadFromFile(
	"data/data.csv", 
	"CSVMapping", 
	1, 
	1, 
	1
	);

With result:	

.. image:: images/csv_example.png
   :scale: 70
   :align: center


Excel mapping (exporting data)
-------------------------------

Assume the following mapping for an Excelfile, identifiable with the start- and ending tags of ``AimmsExcelMapping``:

.. code-block:: xml

    <AimmsExcelMapping>
        <SheetMapping name="Table1">
            <RowMapping name="row">
                <ColumnMapping name="country" binds-to="country"/>
                <ColumnMapping name="city" binds-to="city"/>
                <ColumnMapping name="lat" maps-to="lat(country,city)"/>
                <ColumnMapping name="long" maps-to="long(country,city)"/>
            </RowMapping>
        </SheetMapping>
    </AimmsExcelMapping>

Just like the previous examples this mappingfile can be used to map data into AIMMS identifiers, but any mappingfile can also be used to generate a datafile - so the other way around. This mapping will create somewhat the same table as in the CSV example, but will now output the table to an Excel workbook with a sheet called ``Table1``. 

To do so we need to also use the :any:`dex::ReadAllMappings` (or :any:`dex::ReadMappings`) to read the ExcelMapping into the model so we can use it in the :any:`dex::WriteToFile`. The full procedure looks like this:

.. code-block:: aimms
    
    dex::ReadAllMappings();

	dex::WriteToFile(
	"output.xls",
	"ExcelMapping",
	1
	);

The output:

.. image:: images/excel_example.png
   :scale: 70
   :align: center

A single Excel mapping can contain mappings for multiple sheets.


Parquet mapping
------------------------

Look at the following mapping for a Parquet format:

.. code-block:: xml

    <AimmsParquetMapping>
        <RowMapping name="table1">
            <ColumnMapping name="country" binds-to="country"/>
            <ColumnMapping name="city" binds-to="city"/>
            <ColumnMapping name="lat" maps-to="d1(i,j)"/>
        </RowMapping>
    </AimmsParquetMapping>

Just like the CSV format the Parquet format describes a repetitive table node i.e. a repetitive structure of multiple rows, each consisting of multiple named column leaf-nodes. The only difference with the CSV mapping is the root node of the mapping, which should be ``AimmsParquetMapping``.

The parquet format is popular in python where it is used to save and load pandas dataframes. Suppose the above mapping was used to write data into file *filefromdex.parquet*. Then we could print it in python (with *pyarrow* and *pandas* installed) using the code below. 

.. code-block:: python

    import pandas as pd
    import pyarrow.parquet as pq

    table = pq.read_table("filefromdex.parquet")
    df = table.to_pandas()
    print(df)

This could then print:

.. code-block:: xml

           country  		city 		lat     
    0      The Netherlands   	Amsterdam 	52.34996869
    1      The Netherlands   	The Hague 	52.08003684
    2      The Netherlands   	Rotterdam  	51.9199691
    3      Belgium   		Antwerp  	51.22037355

Here we see in the top row the names from the ``ColumnMapping`` of the mapping. In the left column are the row numbers added by python. The other columns are data read from file *filefromdex.parquet*.



.. spelling::

    dex
	mappingfile
	datafile
	JSON-formatted
	JSON-file
	XML-structure
	XML-formatted
	parquet
	parquetfile
	pyarrows
	dataframes