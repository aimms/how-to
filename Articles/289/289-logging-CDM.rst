Log CDM
===============

.. meta::
   :description: This article explains how to enable logging when using CDM service.
   :keywords: CDM, log, client, server

CDM logging can be turned on for both the client and the service.

.. image:: images/search-property-256.png

CDM Logging in the client
--------------------------

.. image:: images/client-128.png

Copy the file ``CDMLogConfig.cfg`` from the installation folder of the ``CDMService`` to the project folder.
*Uncomment line 17 of this file*.

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 17
    
    # PRO global log configuration
    log4j.rootLogger=INFO, file

    # Write appender to file (max 50MB, at most 10 backup files)
    log4j.appender.file=org.apache.log4j.RollingFileAppender
    log4j.appender.file.file=CDMLog.xml
    log4j.appender.file.ImmediateFlush=true
    log4j.appender.file.layout=org.apache.log4j.xml.XMLLayout
    log4j.appender.file.MaxFileSize=50MB
    log4j.appender.file.MaxBackupIndex=10

    # Special tracing for some loggers
    log4j.logger.ARMI=INFO
    log4j.logger.AIMMS=WARN
    #log4j.logger.CDMService=TRACE
    #log4j.logger.CDMDB=TRACE
    log4j.logger.CDM=TRACE

..    log4j.logger.CDMService=TRACE
..    log4j.logger.CDMDB=TRACE

The output file ``CDMLog.xml`` is in the project folder.

CDM logging in the server
--------------------------

.. image:: images/server-128.png

The ``CDMServiceLogConfig.cfg`` set the loggers of ``CDMService`` and ``CDMDB`` to ``TRACE`` (the highlighted lines):

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 15,16
    
    # PRO global log configuration
    log4j.rootLogger=INFO, file

    # Write appender to file (max 50MB, at most 10 backup files)
    log4j.appender.file=org.apache.log4j.RollingFileAppender
    log4j.appender.file.file=/cdm/logs/CDMServiceLog.xml
    log4j.appender.file.ImmediateFlush=true
    log4j.appender.file.layout=org.apache.log4j.xml.XMLLayout
    log4j.appender.file.MaxFileSize=50MB
    log4j.appender.file.MaxBackupIndex=10

    # Special tracing for some loggers
    log4j.logger.ARMI=INFO
    log4j.logger.AIMMS=WARN
    log4j.logger.CDMService=TRACE
    log4j.logger.CDMDB=TRACE

The output file ``CDMServiceLog.xml`` is in the folder ``/cdm/logs``.

