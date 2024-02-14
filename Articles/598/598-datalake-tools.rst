DataLake Tools
=================

.. meta::
   :keywords: datalake, azure, sas token, container, data integration, file system, file management
   :description: This article is for developers who want to integrate the Azure Data Lake Storage as part of their AIMMS account.

.. image:: https://img.shields.io/badge/AIMMS_24.1-ZIP:_DataLake Tools-blue
   :target: https://github.com/aimms/datalake-tools/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/AIMMS_24.1-Github:_DataLake Tools-blue
   :target: https://github.com/aimms/datalake-tools

.. image:: https://img.shields.io/badge/AIMMS_Community-Forum-yellow
   :target: https://community.aimms.com/aimms-pro-cloud-platform-43/datalake-tools-1520

.. image:: images/data-lake.png
   :scale: 30
   :align: right
   :alt: DataLake Tools

Introduction 
-------------
Every AIMMS Cloud account is by default equipped with an Azure Data Lake Storage Gen2 (ADLS). 
You can use this storage account to store all types of files that you want to integrate with your AIMMS application. 
You can also use it to store files generated based on the output of your optimizations, as to make them available to an external source.

The `Data Exchange Library <https://documentation.aimms.com/dataexchange/api.html#data-lake-storage-file-systems>`__ offers functions to easily interact with the ADLS.  

This toolkit offers you the possibility to easily create both Account SAS Token and Container SAS Token, which is needed for authentication if you want to access the ADLS from an external source.

Instructions 
-------------

This chapter is divided into three sections:

#. Application Preparation
#. Understanding 'Account SAS Token' Page
#. Understanding 'Container SAS Token' Page


Application Preparation
~~~~~~~~~~~~~~~~~~~~~~~
To use this application, you can either:

**Use it on the cloud:** Download this toolkit and upload it to your cloud. From there, there is no further need for authentication - the Data Exchange library will automatically extract the storage account name and access key of the Data Lake Storage account associated with your AIMMS Cloud account. 
Make sure to create the ``aimmspack`` with ``api-init`` folder unchecked. Read more about the ADLS for the AIMMS Cloud `here <https://documentation.aimms.com/dataexchange/dls.html>`_. 

**Use it locally:** Download this toolkit and open ``api-init/Data_Lake_Storage.txt``. Add your ``StorageAccount`` replacing ``"add_name_here"`` string. Open the project. 

If you prepared the application correctly, by opening the application, you will see the 'Storage Account' name on the right bottom of your page. Without a 'Storage Account' name, nothing from this point on will work. 

Understanding 'Account SAS Token' Page
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
In the next subsection, the functionality of 'Account SAS Token' page will be detailed.

.. image:: images/account-sas-token.png
    :align: center

|

To create a Account SAS Token, please input the required information on Inputs table. Click on the button below it.
A new Account SAS Token will be generated and will appear on the bottom.


Understanding 'Container SAS Token' Page
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
In the next subsection, the functionality of 'Container SAS Token' page will be detailed.

.. image:: images/container-sas-token.png
    :align: center

|

To create a Container SAS Token, please input the required information on Inputs table. Note that a File System is required, but you can always fetch the latest File Systems by the 'Refresh File System' button. Once everything is set, click on the button below it.
A new Container SAS Token will be generated and will appear on the bottom.

Minimal Requirements
--------------------

To work with this toolkit, you will need a PRO Cloud account with an Azure Data Lake Storage. 
You can check if your Cloud account is equipped with a Data Lake by launching this app to your PRO Cloud and checking if 'Storage Account' has value. This can be found at the right bottom of any page. 
If you do not see a name here, please contact `AIMMS Support <support@aimms.com>`_.


Release Notes
--------------------

`v1.1 <https://github.com/aimms/datalake-tools/releases/tag/1.1>`_ (08/02/2024)
   Both Account SAS Token and Container SAS Token are available to be created.

`v1.0 <https://github.com/aimms/datalake-tools/releases/tag/1.0>`_ (25/08/2023)
   First version launched!

.. spelling:word-list::

   github
   datalake
   aimmspack
