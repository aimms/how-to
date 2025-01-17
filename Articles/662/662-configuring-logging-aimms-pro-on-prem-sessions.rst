Configuring the logging of sessions ran on a AIMMS PRO On-Premise
====================================================================

This article contains an example of configuring the logging of an application that is published on an AIMMS PRO On-Premise.

.. note:: 

    The AIMMS log files are created by AIMMS staff and designed to be interpreted by AIMMS staff. 
    The meaning of log entries may not be obvious. 
    An error or warning message in the log file does NOT necessarily indicate a problem in the application. 


Running example
------------------

A transport problem with two bottling locations of fresh water and two distribution centers.
:download:`AIMMS project download <model/configurePremBottledWater.zip>` 


Packed
-------

In the .aimmspack, also a ``LoggerConfig.xml`` file is enclosed.  Some parts that are specific to logging for AIMMS PRO on Prem sessions are:

The appender used.

.. code-block:: xml
    :linenos:

    <appender name="logfile" class="org.apache.log4j.rolling.RollingFileAppender">
        <rollingPolicy class="org.apache.log4j.rolling.FixedWindowRollingPolicy">
            <param name="FileNamePattern" value="${AIMMSPRO_DATADIR}/Log/Sessions/%X{projectName}-%X{projectVersion}-%X{startupMode}-%X{timeStamp}-%X{sessionId}-%i.log"/>
            <param name="MinIndex" value="1"/>
            <param name="MaxIndex" value="10"/>
        </rollingPolicy>
        <triggeringPolicy class="org.apache.log4j.rolling.SizeBasedTriggeringPolicy">
            <param name="MaxFileSize" value="50MB"/>
        </triggeringPolicy>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d{ISO8601} %t [%p] {%c} %m%n"/>
        </layout>           
    </appender>

Important in the LoggerConfig.xml file:

#.  Line 3: File naming, such that not only the 

#.  Line 7: Pattern - especially the %d{ISO8601} such that dates can be matched.

When reporting back on an issue, including a moment when the incident happened, this information is essential.

A second part that is essential is that the above described appender, is indeed activated.

.. code-block:: xml
    :linenos:

    <root>
        <level value="info" />
        <appender-ref ref="logfile" />
    </root> 

This test
---------

In the ``LoggerConfig.xml`` file of the enclosed example the following logger is added:

.. code-block:: xml
    :linenos:

    <logger name="AIMMS.Trace.Procedure">
        <!--- Tracing entering and leaving AIMMS Procedures.
              Put this logger to trace if you need this information.
        -->
        <level value="trace"/>
    </logger>

This logger will trace starting and finishing of AIMMS procedures. An excerpt from the log file is:

.. code-block:: none
    :linenos:

    2020-11-14 16:24:03,259 0x00003268 [DEBUG] {AIMMS.Trace.Procedure} Starting Procedure  pr_ME1
    2020-11-14 16:24:03,259 0x00003268 [DEBUG] {AIMMS.Trace.Procedure} Starting Procedure  pr_ME2
    2020-11-14 16:24:03,261 0x00003268 [DEBUG] {AIMMS.Trace.Procedure} Finishing Procedure pr_ME2
    2020-11-14 16:24:03,261 0x00003268 [DEBUG] {AIMMS.Trace.Procedure} Finishing Procedure pr_ME1
    2020-11-14 16:24:03,261 0x00003268 [DEBUG] {AIMMS.Trace.Procedure} Finishing Procedure MainExecution

Also provided
--------------

The following ZIP file contains a ``LoggerConfig.xml`` that can be added to any project.  You will need to add the loggers you deem necessary, or are advised to you.

:download:`AIMMS project download <model/loggerConfigOnPremise.zip>` 

