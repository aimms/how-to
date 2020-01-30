Start a Job via PRO API using C#
===================================================

.. meta::
    :description: Starting an AIMMS job via the AIMMS PRO API using C#.
    :keywords: C#, pro api

Prerequisites
--------------

#. Get a C# environment for developing applications. Here we assume you're using Visual Studio. 

    If you don't have Visual Studio installed, you may get it on Microsoft's website: `Install Visual Studio <https://visualstudio.microsoft.com/vs/express/>`_.

#. Get `AIMMS PRO API <https://documentation.aimms.com/pro/api.html>`_ via AIMMS PRO Portal

   a. Log into AIMMS PRO

   #. Go to *Help > getting started*
   
   #. Download AIMMS PRO API

   #. Move it to a convenient location, and unzip the archive
 

Running the example
-------------------

#.  First publish the example application.

    You can find the example model in ``.\pro-api-complete\examples\AimmsModel\PROApiExampleApplication.aimms``.
    Create an aimmspack, say ``PROApiExampleApplication.aimmspack``, and publish it on your AIMMS PRO system, for instance using the name ``PROApiExampleApplication`` and version ``1``. 

    Adapt application details presented on lines 14 - 30.
    
        .. image:: images/AdaptingConnectionDetailsCS.png
    
        * Line 14, ``PRO_ENDPOINT``: this might also be ``wss://your-cloud-name.cloud.aimms.com`` 
        
            #. when connection encrypted, start with ``wss`` (cloud systems are always encrypted).
            
            #. when connection not encrypted, start with ``ws``
           
        
        * Lines 32-34, ``PRO_ENVIRONMENT``, ``USERNAME``, and ``USER_PASSWORD`` should have been supplied by your AIMMS PRO administrator.
        
        * Lines 38-39, ``PRO_APPLICATION_NAME``, ``PRO_APPLICATION_VERSION``, the name and version of the app as it is published.

#.  Build the application using *Visual Studio Menu > Build > Build*.

#.  Run the application.

Output
------

The output is the same as when using Java. See :doc:`98-starting-job-aimms-pro-api-java`.

Further reading
-----------------

See also the `manual <https://manual.aimms.com/pro/api.html>`_





