How to read and write CSV files?
================================

CSV files are a de facto standard for communication of data. 
AIMMS provides the libraries ``DataLink`` and ``CSVProvider`` for the serializing of CSV files.
In this article an example is provided for reading data from a CSV file.

Architecture view
-----------------

The DataLink and CSVProvider libraries cooperate to present the following data view:

.. image::  ../Resources/C_Deployment/Images/DataLinkDataView.png 

The DataLink column maps to an AIMMS set or an AIMMS parameter.
This DataLink column is linked to a CSV column.

A group of columns make up for a table in the DataLink view, and such a table corresponds to a file in the CSVProvider view.

A group of DataLink tables make up for a database in the DataLink view, and such a database corresponds to a datasource in the CSVProvider view. 

Getting some data
-----------------

Looking at the above picture, we need to provide details in several steps:

#. Provide a name for the mapping between the DataLink view and the CSVProvider view.

#. Provide the names of the tables in the DataLink view that correspond to the filenames in the CSVProvider view. Note that in the CSVProvider view, the file suffix ``.csv`` is automatically appended.

#. Provide a mapping between AIMMS Sets and Parameters to columns in .csv files.

#. File convention details such as separator characters, and width/precision of numeric data.

#. Putting it all together in a read or write call.

We will discuss each of these steps in detail below.

Detail specification data source mapping
++++++++++++++++++++++++++++++++++++++++

The DataLink library provides two procedures for managing data source mappings:

#. ``dl::RemoveDataSourceMapping("someMapping")``. When the data link mapping ``someMapping`` exists, it will be removed from the data structures of the DataLink library. Otherwise this procedure does nothing; it is harmless to call this procedure two times in a row with the same argument.

#. ``dl::AddDataSourcemapping( "someMapping", ... )`` will create a mapping. 

Detail specification tables and files
+++++++++++++++++++++++++++++++++++++

The table names in the DataLink view are the same as the filenames in the CSVProvider view, with the note that these files have suffix ``.csv``.
The AIMMS set ``dl::DataTables`` contains a list of these table names. For instance, adding the table ``fourRows`` to the list of tables is done as follows:

    .. code-block:: aimms

        dl::DataTables += {'fourRows'} ; ! komma/fourRows.csv is the file we're gonna read from.


Detail specification identifiers and columns
++++++++++++++++++++++++++++++++++++++++++++

For each set and parameter in the AIMMS model, we need to specify in which DataLink table aka CSV file it corresponds and to which column in that table.
We do this using the following 4-dimensional table:

    .. code-block:: aimms

        DataMap(
                    dl::dt,    ! Table name, should be present in dl::DataTables.
                    dl::idn,   ! Full AIMMS identifier name, enclose in '' iff it is declared in a library or module.
                    dl::cn,    ! Column number
                    dl::dn     ! Domain number
                               !     For domain sets: equal to the Column number
                               !     For parameters : equal to 0.
                    ! contents: the names of the columns in the CSV files.
        ) := data {
            ( fourRows, sLocs, 1, 1 ) : "location",
            ( fourRows, sProd, 2, 2 ) : "product",
            ( fourRows, pDem , 3, 0 ) : "demand", 
            ( fourRows, spCmt, 4, 0 ) : "comment"
        };

As you can see from the above example, the column numbers are increasing and correspond to the *column number in the DataLink view*. 

#. When reading a .csv file, the header line of the .csv file, in combination with the value of each element in the DataMap, is used to determine the column numbers in the CSV Provider view. Thus the column numbers in the DataMap are not necessarily the same as the column numbers in the CSVProvider view. 

#. When writing a .csv file, these two column numberings happen to be same.
        
Detail specification File convention
++++++++++++++++++++++++++++++++++++

The communication attributes are specified via a string parameter indexed using ``dl::rwattr``.
The following attributes are supported:

#. ``DataProvider``.  This attribute is mandatory.  For the CSVProvider use: ``csvprov::DataLink``.

#. ``ContainsHeader``. This attribute is mandatory. Its value must be "yes".

#. ``Separator``. This attribute is optional.  The default is ",".  A frequently used alternative is ";".

#. ``Width``. This attribute is optional. This attribute controls the width when writing numeric data to a CSV file.

#. ``Precision``. This attribute is optional. This attribute controls the precision when writing numeric data to a CSV file.

Example:

    .. code-block:: aimms

        spCommunicationAttributes := 
              { 'DataProvider' : csvprov::DataLink , 
                'ContainsHeaders' : "yes",
                'Separator' : ";"
              };

Detail specification for actual read and write calls
++++++++++++++++++++++++++++++++++++++++++++++++++++

using the above detail specifications, we are now ready to actually read from or write to CSV files. 
The following example is hopefully self-explanatory.

    .. code-block:: aimms

        dl::DataRead("semi",                  ! reading from data source "semi" - because we use CSVProvider this data source is a folder.
                "TheMapping" ,                ! using relation "TheMapping" between folder "semi" and AIMMS identifiers.
                spCommunicationAttributes);   ! Technicalities on how to communicate.
                
Download example: :download:`project <../Resources/C_Deployment/Downloads/dlcsv.zip>`

                
                
