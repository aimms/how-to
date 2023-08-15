
.. meta::
   :description: How to use OAuth2 for API authorization in DEX.
   :keywords: aimms, data, exchange, api, authorization, security, oauth
   

Use the AIMMS Cloud Azure Data Lake Storage for integrating data
===================================================================

.. image:: https://img.shields.io/badge/DEX_2.1.2.5-Minimum_DEX_Version-brightgreen
.. image:: https://img.shields.io/badge/AIMMS_2.50.1-Minimum_AIMMS_Version_PRO-brightgreen

Every AIMMS Cloud comes with an Azure Data Lake Gen2 storage account (ADLS). The Data Exchange Library (DEX) `provides functions to easily communicate with it <https://documentation.aimms.com/dataexchange/dls.html>`__, allowing you to import and export data onto/from the storage account. This route makes it easier to e.g. share exported data with external sources, or to import external data.

The process that will be demonstrated in this article will be:
* creating a new container on the ADLS;
* transferring a file to that newly generated container (making it externally available, or to be re-used again in the same or other AIMMS apps);
* retrieving that same file again.

There are three possible flows to achieve this:

#. **Using our ADLS toolkit**, which can, when uploaded to the cloud, handle all these tasks by simply pressing the related buttons.

#. **Using the ADLS-DEX route**, in which we will use DEX functions specifically made for the ADLS to handle the flow.

#. **Using the native DEX functions**, which are the 'native' steps to be taken.

Only flow 2 will be discussed in detail below. If you want to follow flow 1, please refer to the related toolkit page.

Prerequisites
--------------

#. You need to have the Data Exchange Library installed. Visit `this article <https://documentation.aimms.com/general-library/getting-started.html>`__ for instructions on how to do this.

#. It is also good to understand the structure of an Azure Data Lake Storage. You can refer to `this page to learn more about it <https://learn.microsoft.com/en-us/azure/storage/blobs/data-lake-storage-namespace>`__, but important to know for this article is to look at the storage as if it's a file explorer. The 'folders' are referred to as containers or file systems. It is possible to create new folders within other folders, creating so called paths. Files are always uploaded into a specified container. Files are also referred to as 'blobs' in the context of a Data Lake storage.

Flow 1: the ADLS-DEX route
---------------------------

There are multiple DEX functions available to easily achieve what we want as they are created specifically for usage with ADLS. In this route you don't have to worry about the creation of a SAS token for authentication (see below) or the arguments you'll need to input, as the functions themselves will take care of everything.

2.1 Creating a new container
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Creating a new container (or, as called on the ADLS, 'file system') is easily done:

.. code-block:: aimms
    
		!create a unique name for the container
		dex::schema::CreateUUID(testFS);
		testFS := "fs-" + testFS;
		
		!create the container with the above defined name
		dex::dls::CreateFileSystem(testFS);

Without errors, the container will be created with the given name. 

1.2 Upload a file
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Now we can continue with uploading a file from AIMMS to that newly created container on the ADLS:

.. code-block:: aimms
    
		dex::dls::UploadFile(
			testFS, 
			"DLSData/Azure Data lake v1/Bill Of Material.parquet",
			"test"
		);

The arguments provided are:

* the name of the container we want to upload to (which in this example is still the value in 'testFS');
* the local path of the file to upload;
* optional: a string parameter holding the path prefix of the location within the file system/container to which the file must be uploaded. If this path does not exist yet, it will automatically be created.

If you are unsure what the file system on the ADLS looks like, you can use:

* :any:`dex::dls::ListFileSystems()` to obtain the currently existing file systems (or: containers) on the ADLS;
* :any:`dex::dls::ListFiles()` to obtain the currently existing files within a given file system, including path(s) when applicable.

Note that it is also possible, with :any:`dex::dls::UploadFiles()`, to upload a set of files. If there are any subfolders within that set, these will automatically be created within the storage.

Without errors, the will be uploaded as specified. 

1.3 Download a file
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Now let's download that same file from the ADLS:

.. code-block:: aimms
    
		dex::dls::DownloadFile(
			testFS, 
			"Bill Of Material.parquet", 
			"downloads"
		);

The arguments provided are: 
- the name of the container we want to download from (which in this example is still the value in 'testFS');
- the path of the file (including the file name, or only the file name if it is in the main container) within the file system on the ADLS to download;
- optional: string parameter holding the local directory to which the file must be downloaded. In our example it is to the folder 'downloads' in the project folder.

Without errors, the file will be downloaded as specified. Now you can use a `DEX mapping to map the data in the file onto your AIMMS model <https://documentation.aimms.com/dataexchange/mapping.html>`__. 

DEX native functionalities
-------------------------------------

The ADLS-DEX-functions used in the above flow are built with DEX-native functions. If you are interested in learning more about the underlying functionalities, you can access the functions by right clicking on the procedure and select the Attributes.

.. spelling:word-list::

    dex
    mappingfile
    mappingfiles
    mappingname
    datafile
    JSON-formatted
    JSON-file
    XML-structure
    XML-formatted
    parquet
    parquetfile
    pyarrows
    dataframes
    Excelfile
    AIMMS-identifiers
	authorizationEndpoint
	tokenEndpoint
	openIDEndpoint
	tenantID
	SAS
	ADLS
	blobs
	blob
	DEX-native
	ADLS-DEX-functions
	testFS