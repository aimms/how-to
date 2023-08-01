
.. meta::
   :description: How to use OAuth2 for API authorization in DEX.
   :keywords: aimms, data, exchange, api, authorization, security, oauth
   

Using the AIMMS Cloud Azure Data Lake Storage for integrating data
===================================================================

.. image:: https://img.shields.io/badge/DEX_2.1.2.5-Minimum_DEX_Version-brightgreen
.. image:: https://img.shields.io/badge/AIMMS_2.50.1-Minimum_AIMMS_Version_PRO-brightgreen

:download:`OAuth example download <download/OAuth-Example.zip>`

Every AIMMS Cloud comes with an Azure Data Lake Gen2 storage account (ADLS). The Data Exchange Library (DEX) `provides functions to easily communicate with it <https://documentation.aimms.com/dataexchange/dls.html>`__, allowing you to import and export data onto/from the storage account. This route makes it easier to e.g. share exported data with external sources, or to import external data. On this how-to page you can find a functional example flow of its usage.

In this article we will be demonstrating the following flow:

#. **First we will create a SAS token**, which is necessary for a secured communication with the ADLS, and create a dedicated container to import/export data from and to;

#. then we will **create a parquet file and save it on the ADLS**, as an example on how to export data;

#. lastly we will **map data from a parquet file on the ADLS onto our AIMMS model** so it can be used within the application.


Prerequisites
--------------

#. You need to have the Data Exchange Library installed. Visit `this article <https://documentation.aimms.com/general-library/getting-started.html>`__ for instructions on how to do this.


Creating a SAS token
------------------------------------------------------

Using a SAS token is the easiest way to access containers and files on the ADLS. There are two ways to obtain a SAS token:

#. Within the AIMMS Cloud, DEX will automatically extract the storage account name and access key of the Data Lake Storage account associated with your AIMMS Cloud account and place them in the parameters :any:`dex::dls::StorageAccount` and :any:`dex::dls::StorageAccessKey`. No additional implementation is required.

#. When developing on your desktop, you can provide a storage account name and access key to any Data Lake Storage account manually by providing values for the string parameter dex::dls::StorageAccount and dex::dls::StorageAccessKey via the file api-init/Data_Lake_Storage.txt.



You can see in the section at the bottom left that we've added two redirect URI's; one for usage from the AIMMS Cloud (URI 1) and one for usage from a locally opened AIMMS PRO (URI 2), so the connection should work both from a local connection as well as from an AIMMS app uploaded to the cloud. 

If we take a look at the setup within AIMMS we see the following:

.. code-block:: aimms
    
		!empty UserInfo_Data, just to make sure we start off clean
		empty dex::oauth::UserInfo_Data;

		!load data into an APIClient we name 'Google'
		dex::oauth::APIClients := data { Google };
		
		!set data for 'Google'
		dex::oauth::APIClientStringData('Google',dex::oauth::apidata) :=$ data { 
			authorizationEndpoint : "https://accounts.google.com/o/oauth2/v2/auth", 
			tokenEndpoint : "https://oauth2.googleapis.com/token", 
			openIDEndpoint : "https://www.googleapis.com/oauth2/v3/userinfo",
			clientId : "xxxxxxxxxxxxxxxxxxxx.apps.googleusercontent.com", 
			clientSecret : "xxxxxx-xxxxxxxxxxxxxxx", 
			scope: "openid profile";
		}

The authorization endpoint, token endpoint and open ID endpoint should be provided by the API that requires authentication. The client ID and client secret are, in this example, provided by Google's OAuth 2.0 authentication system (as shown in the screenshot above). 

Now, when we open the example project either locally or as an app uploaded on our cloud, we are able to run the procedure (in the WebUI by clicking the related button). The underlying procedure in AIMMS is:

.. code-block:: aimms
    
		InitializeOAuthClients;
		dex::oauth::GetUserInfo('Google');

This will first send us to the Google authentication screen, where we will have to select the profile to authenticate with:

.. image:: images/google_step3.png
   :align: center

After that we will receive the message:

.. image:: images/google_step4.png
   :align: center

When this request has processed, you will see the requested data is provided:

.. image:: images/google_step5.png
   :align: center


Implementing the Authorization Code flow with Azure
------------------------------------------------------

For Azure, the `OAuth 2.0 authentication flow <https://learn.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow>`__ is kind of similar to the one from Google, but of course set up from a different context. In this case, we can find the App Registrations in the Azure Active Directory within the `Azure Portal <https://learn.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow>`__. Once you've created the registration of the app, you will receive the necessary details:

.. image:: images/azure_step1.png
   :align: center

The secret can be found (or created, if none exists yet) under 'Certificates & secrets', or by simply clicking on the link next to 'Client credentials' in the above screenshot. Redirect URI's should be added under 'Authentication':

.. image:: images/azure_step2a.png
   :align: center

The correct scope(s) for the request should be added in the 'API permissions' section. Since for the Authentication Code Flow we will retrieve the user data from the logged in user, we don't need admin consent and the User.Read permission should be sufficient:

.. image:: images/azure_step2.png
   :align: center

In the request we'll also add the 'offline_access' scope as defined by the documentation so we get a refresh token for extended access to resources. 
If we take a look at the setup within AIMMS we see the following:

.. code-block:: aimms

		!empty UserInfo_Data, just to make sure we start off clean
		empty dex::oauth::UserInfo_Data;

		!load data into an APIClient we name 'MSACF'
		dex::oauth::APIClients := data { MSACF };
		
		!set data for 'MSACF'
		dex::oauth::APIClientStringData('MS',dex::oauth::apidata) :=$ data { 
			authorizationEndpoint : "https://login.microsoftonline.com/[tenantID]/oauth2/v2.0/authorize", 
			tokenEndpoint : "https://login.microsoftonline.com/[tenantID]/oauth2/v2.0/token", 
			openIDEndpoint : "https://graph.microsoft.com/v1.0/me",
			clientId : "xxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx", 
			clientSecret : "xxxxxxxxxxxxxxxxxxxx", 
			scope: "offline_access https://graph.microsoft.com/User.Read"
		};

The same arguments as the previous example should be provided, but of course with different data. Note that the tenantID should be provided in both the authorizationEndpoint and tokenEndpoint.
We also perform the same request but with a different argument because we changed the name of the client:

.. code-block:: aimms
    
		InitializeOAuthClients;
		dex::oauth::GetUserInfo('MSACF');

Now, when we open the example project either locally or as an app uploaded on our cloud, we are able to run the procedure and/or use the button in the WebUI to retrieve the requested user data. 


Implementing the Client Credentials flow with Azure
------------------------------------------------------

The Client Credentials Code flow requires a slightly different setup to work. You can reuse the client that was set up for the Authorization Code Flow, but we need an additional API Permission within the Azure portal:

.. image:: images/azure_step2c.png
   :align: center

In AIMMS, we will work with the :any:`dex::client::NewRequest` functionality. We first create the client:

.. code-block:: aimms
    
		!read mappings
		dex::ReadAllMappings;

		!empty UserInfo_Data, just to make sure we start off clean
		empty dex::oauth::UserInfo_Data;

		!create client
		dex::oauth::APIClients := data { MS };
		dex::oauth::APIClientStringData('MS',dex::oauth::apidata) :=$ data { 
			tokenEndpoint : "https://login.microsoftonline.com/[tenantID]/oauth2/v2.0/token", 
			clientId : "xxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx", 
			clientSecret : "xxxxxxxxxxxxxxxxxxxx", 
			scope: "https://graph.microsoft.com/.default"
		};

Note that you should input the tenant ID into to tokenEndpoint.
The scope has changed to the .default graph scope. We also left out the authorizationEndpoint (as we will now use a bearer) and the openIDEndpoint. 
Now we can create the request and add the bearer token:

.. code-block:: aimms

	!first create the request
	dex::client::NewRequest(
		"getUser",
		"https://graph.microsoft.com/v1.0/users/[identifier]",
		'Callback',
		responsefile:"Output.json",
		tracefile:"Trace.xml"
	);

	!add bearer token
	dex::oauth::AddBearerToken('MS', "getUser");

As you can see we've added a reference to a Callback procedure, necessary for the request to be handled properly but which will also be used to map the retrieved results onto a string parameter (or catch any possible error and show the related message).
We are also tracing the request of which we store the results in a file called Trace.xml. The actual response will be in Output.json. Both of these files can be accessed if you run the procedure(s) locally. Now we are ready to perform the request:

.. code-block:: aimms

	!perform the request
	dex::client::PerformRequest(
		"getUser"
	);

	!wait for response
	dex::client::WaitForResponses(
		1000
	);

	!close request properly
	dex::client::CloseRequest(
		"getUser"
	);

If the request was performed successfully, the response data is now in Output.json. Then we use a DEX-mapping to map the retrieved data onto the same parameters that we used for the previous requests as to be able to show it correctly in the WebUI.

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
	ADLS