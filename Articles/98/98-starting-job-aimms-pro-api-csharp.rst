Starting AIMMS Job via the AIMMS PRO API using C#
===================================================

Prerequisites
--------------

#. Get a C# environment for developing applications, here we assume you're using Visual Studio. If you don't have a Visual Studio installed, you may get one `here <https://visualstudio.microsoft.com/vs/express/>`_.

#. Get AIMMS PRO API via AIMMS PRO Portal

   #. Log on to your AIMMS PRO

   #. Tab help - getting started
   
   #. Download AIMMS PRO API

   #. Move it to a convenient location, and unzip.
 

Running the example
-------------------

#.  First publish the example application

    You can find the example model in ``.\pro-api-complete\examples\AimmsModel\PROApiExampleApplication.aimms``.
    Create an aimmspack, say ``PROApiExampleApplication.aimmspack`` and publish it on your AIMMS PRO system, for instance using the name ``PROApiExampleApplication`` and version ``1``. 

    #.  Adapt application details presented on lines 14 - 30.
    
        .. images/AdaptingConnectionDetailsCS.png
    
        * Line 14, PRO_ENDPOINT: this might also be "wss://your-cloud-name.cloud.aimms.com" 
        
            #. when connection encrypted, start with wss (cloud systems are always encrypted).
            
            #. when connection not encrypted, start with ws
        
        * Lines 32-34, PRO_ENVIRONMENT, USERNAME, and USER_PASSWORD should've been supplied by your AIMMS PRO administrator.
        
        * Lines 38-39, PRO_APPLICATION_NAME, PRO_APPLICATION_VERSION, the name and version of the app as it is published.

#.  Build the application using Visual Studio Menu - Build - Build

#.  Run the application.

#.  The output is the same as in :doc:`98-starting-job-aimms-pro-api-java` and not repeated here.


.. include:: /includes/form.def


