Start a Job via PRO API using Java
===================================================


.. meta::
    :description: Starting an AIMMS job via the AIMMS PRO API using C#.
    :keywords: java, pro api

Prerequisites
-------------

#. Get a Java environment for development, such as `IntelliJ IDEA <https://www.jetbrains.com/idea/>`_ . 
   Here we'll assume you're just using the 64-bit version.

#. Get the latest official JDK from the Oracle 
   website: `Download JDK <https://www.oracle.com/java/technologies/javase-jdk11-downloads.html>`_
   More information about JDK can be found in Oracle's 
   documentation: `JDK Documentation <https://docs.oracle.com/en/java/javase/11/>`_

#. Get `AIMMS PRO API <https://documentation.aimms.com/pro/api.html>`_ **via your AIMMS PRO Portal**.

   a. Log into AIMMS PRO

   #. Go to :menuselection:`Help > Getting Started`
   
   #. Download AIMMS PRO API

   #. Move it to a convenient location, and unzip the archive
   
.. note:: If you are on the AIMMS Cloud, you may directly use ``https://your-cloud-name.cloud.aimms.com/api-library/pro-api-complete.zip`` 
   
Running the Example
-------------------

#.  First publish the example application.

    You can find the example model in ``.\pro-api-complete\examples\AimmsModel\PROApiExampleApplication.aimms``.
    Create an aimmspack, say ``PROApiExampleApplication.aimmspack`` and publish it on your AIMMS PRO system, for instance using the name ``PROApiExampleApplication`` and version ``1``. 

#.  Start the Java example by opening ``.\pro-api-complete\examples\java\pom.xml`` using IntelliJ IDEA.

    a.  You may note the following message: ``Project SDK is not defined.`` 
        
        To repair:

        i. Click on the :menuselection:`Setup SDK` to the right.
        
        #. Select the latest version without subversion, here ``11``.
        
        #. click ``Ok`` in the dialog.  The message ``Project SDK is not defined.`` should disappear.

        .. image:: images/ProjectSDKIsNotDefinedRepairing.PNG

        
    #.  Adapt application details presented on lines 30 - 39 of ``Program.java`` file.
    
        .. image:: images/AdaptingConnectionDetails.png
    
        * Line 30, ``DEFAULT_ENDPOINT``: this might also be ``wss://your-cloud-name.cloud.aimms.com``.
        
            * when connection is encrypted, start with ``wss`` (cloud systems are always encrypted).
            
            * when connection is not encrypted, start with ``ws``.
        
        * Lines 32-34, ``DEFAULT_ENVIRONMENT``, ``DEFAULT_USERNAME``, and ``DEFAULT_PASSWORD`` should have been supplied by your AIMMS PRO administrator.
        
        * Lines 38-39, ``DEFAULT_APPLICATION_NAME``, ``DEFAULT_APPLICATION_VERSION``, the name and version of the app as it is published.

    #.  Now you can start the demo via :menuselection:`IDEA menu > Run > Run`.

.. _pro-api-java-output:

Output
-------
    
        In the window ``4: Run`` of IDEA, you'll get the following log:

        .. code-block:: none
            :linenos:
        
            "C:\Program Files\Java\jdk1.8.0_201\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.1\lib\idea_rt.jar=63713:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_201\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_201\jre\lib\rt.jar;C:\u\s\How To\develop\Articles\98\downloads\pro-api-complete\examples\java\target\classes;C:\Users\chris\.m2\repository\com\aimms\pro\java-api\2.30.53821.225\java-api-2.30.53821.225.jar;C:\Users\chris\.m2\repository\org\slf4j\slf4j-log4j12\1.7.5\slf4j-log4j12-1.7.5.jar;C:\Users\chris\.m2\repository\org\slf4j\slf4j-api\1.7.5\slf4j-api-1.7.5.jar;C:\Users\chris\.m2\repository\log4j\log4j\1.2.17\log4j-1.2.17.jar;C:\Users\chris\.m2\repository\commons-cli\commons-cli\1.3.1\commons-cli-1.3.1.jar" com.aimms.proapiexample.Program
            INFO  - main - Engine                     - ARMI 1.6.8.71                                                                                            
            INFO  - main - ConnectionStore            - initiating connection to 'tcp://localhost:63715'
            INFO  - Thread-0 - TCPProxyServer             - startWebSocketProxy( '/127.0.0.1:63720', 'wss://aimms-sandbox.cloud.aimms.com/ws-proxy/backend')
            INFO  - WebSocketClient@2130038760-31 - WebSocketProxy             - onConnectBinary('WebSocketSession[websocket=JettyAnnotatedEventDriver[com.aimms.pro.api.impl.WebSocketProxy@739229c7],behavior=CLIENT,connection=WebSocketClientConnection@e0e48ed{IDLE}{f=Flusher[queueSize=0,aggregateSize=0,failure=null],g=Generator[CLIENT,validating],p=Parser@5e7fe217[ExtensionStack,s=START,c=0,len=0,f=null,p=WebSocketPolicy@1415755[behavior=CLIENT,maxTextMessageSize=65536,maxTextMessageBufferSize=32768,maxBinaryMessageSize=1048576,maxBinaryMessageBufferSize=32768,asyncWriteTimeout=60000,idleTimeout=300000,inputBufferSize=4096]]},remote=WebSocketRemoteEndpoint@4abacde0[batching=true],incoming=JettyAnnotatedEventDriver[com.aimms.pro.api.impl.WebSocketProxy@739229c7],outgoing=ExtensionStack[queueSize=0,extensions=[],incoming=org.eclipse.jetty.websocket.common.WebSocketSession,outgoing=org.eclipse.jetty.websocket.client.io.WebSocketClientConnection]]')
            INFO  - main - Program                    - Current jobs
            INFO  - main - Program                    - App=PROApiExampleApplication 2, Description=BasicScheduleJob, Status=QUEUED, Owner=theo@ROOT, Created=Wed Apr 10 14:27:08 CEST 2019, RunTime=0, QueueTime=-59
            INFO  - main - Program                    - Waiting for job: App=PROApiExampleApplication 2, Description=ScheduleJobAndCheck, Status=QUEUED, Owner=theo@ROOT, Created=Wed Apr 10 14:26:09 CEST 2019, RunTime=0, QueueTime=1
            INFO  - main - Program                    - Waiting for job: App=PROApiExampleApplication 2, Description=ScheduleJobAndCheck, Status=QUEUED, Owner=theo@ROOT, Created=Wed Apr 10 14:26:09 CEST 2019, RunTime=0, QueueTime=2
            INFO  - main - Program                    - Waiting for job: App=PROApiExampleApplication 2, Description=ScheduleJobAndCheck, Status=QUEUED, Owner=theo@ROOT, Created=Wed Apr 10 14:26:09 CEST 2019, RunTime=0, QueueTime=4
            INFO  - main - Program                    - Waiting for job: App=PROApiExampleApplication 2, Description=ScheduleJobAndCheck, Status=QUEUED, Owner=theo@ROOT, Created=Wed Apr 10 14:26:09 CEST 2019, RunTime=0, QueueTime=5
            INFO  - main - Program                    - Waiting for job: App=PROApiExampleApplication 2, Description=ScheduleJobAndCheck, Status=INITIALIZING, Owner=theo@ROOT, Created=Wed Apr 10 14:26:09 CEST 2019, RunTime=0, QueueTime=6
            INFO  - main - Program                    - Waiting for job: App=PROApiExampleApplication 2, Description=ScheduleJobAndCheck, Status=INITIALIZING, Owner=theo@ROOT, Created=Wed Apr 10 14:26:09 CEST 2019, RunTime=1, QueueTime=6
            INFO  - main - Program                    - Ended: App=PROApiExampleApplication 2, Description=ScheduleJobAndCheck, Status=FINISHED, Owner=theo@ROOT, Created=Wed Apr 10 14:26:09 CEST 2019, RunTime=2, QueueTime=6
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - DispatcherThread[13] - JobInteractor              - onHandle: ProcedureCall 'notifyClientWithResults'
            INFO  - main - Program                    - procedureCall: notifyClientWithResults
            INFO  - main - Program                    -  argument[{0}], Parameter:
            INFO  - main - Program                    -   [] : 600.0
            INFO  - main - Program                    -  argument[1], StringParameter:
            INFO  - main - Program                    -   [] : 'ThisIsTheResult'
            INFO  - DispatcherThread[15] - JobInteractor              - Scheduling job finish over 1000 ms
            INFO  - main - Program                    - Waiting for events on job
            INFO  - Thread-21 - JobInteractor              - onFinished
            INFO  - main - Program                    - job Finished
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - TupleValuePair: com.aimms.pro.api.parameter.TupleValuePair@482f8f11
            INFO  - main - Program                    - TupleValuePair: com.aimms.pro.api.parameter.TupleValuePair@1593948d
            INFO  - main - Program                    - TupleValuePair: com.aimms.pro.api.parameter.TupleValuePair@1b604f19
            INFO  - main - Program                    - TupleValuePair: com.aimms.pro.api.parameter.TupleValuePair@7823a2f9
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Requesting progress report
            INFO  - main - Program                    - Waiting for events on job
            INFO  - DispatcherThread[13] - JobInteractor              - onHandle: ProcedureCall 'notifyClientWithProgress'
            INFO  - main - Program                    - procedureCall: notifyClientWithProgress
            INFO  - main - Program                    - Percentage completed = 10.0%, not enough, continueing
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            
            ... repetition removed...
            
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Waiting for events on job
            INFO  - main - Program                    - Requesting progress report
            INFO  - DispatcherThread[14] - JobInteractor              - onHandle: ProcedureCall 'notifyClientWithProgress'
            INFO  - main - Program                    - procedureCall: notifyClientWithProgress
            INFO  - main - Program                    - Percentage completed = 80.0%, enough, so stopping the current tast
            INFO  - DispatcherThread[15] - JobInteractor              - onError: 'while running procedure 'proc_AdvancedInteraction':You have interrupted execution. [error 2014].'
            INFO  - DispatcherThread[15] - JobInteractor              - Scheduling job finish over 1000 ms
            INFO  - main - Program                    - error: while running procedure 'proc_AdvancedInteraction':You have interrupted execution. [error 2014].
            ERROR - main - Program                    - Could not execute program
            java.lang.IllegalStateException: Could not terminate session {"sessionID" : "ba39f084-35f3-4463-b8b9-979cb81f9771", "clientQueueID" : "66b3d082-94a0-49fd-9220-0dce5b1abb96", "workerQueueID" : "44ae81d8-8aa7-48ab-8a13-5660aa17b779"}
                at com.aimms.pro.api.impl.ServiceProvider.terminateSession(ServiceProvider.java:152)
                at com.aimms.pro.api.impl.Job.terminate(Job.java:66)
                at com.aimms.proapiexample.Program.AdvancedInteractLoop(Program.java:327)
                at com.aimms.proapiexample.Program.AdvancedInteraction(Program.java:289)
                at com.aimms.proapiexample.Program.main(Program.java:83)
            Caused by: com.aimms.armi.UserException: Session manager error / Cannot terminate session in state 'Finished' [error 1064]
                at com.aimms.armi.BaseCompletionHandler.userException(BaseCompletionHandler.java:38)
                at com.aimms.armi.core.RemoteInvocationCompletionHandler.executeCompletion(RemoteInvocationCompletionHandler.java:101)
                at com.aimms.armi.core.RemoteInvocationRequest.execute(RemoteInvocationRequest.java:44)
                at com.aimms.pro.armi.api.Api.SessionManagerServiceStub.InterruptSession(SessionManagerServiceStub.java:143)
                at com.aimms.pro.api.impl.ServiceProvider.terminateSession(ServiceProvider.java:147)
                ... 4 more
            INFO  - main - Channel                    - close
            INFO  - main - WebSocketProxy             - close( 1000, 'Connection closed' )
            INFO  - main - WebSocketProxy             - onClose( 1001, 'Shutdown')
            Exception in thread "Thread-22" java.lang.NullPointerException
                at com.aimms.pro.api.impl.ServiceProvider.unsubscribe(ServiceProvider.java:134)
                at com.aimms.pro.api.impl.JobInteractor.unregisterForMessages(JobInteractor.java:271)
                at com.aimms.pro.api.impl.JobInteractor$1.run(JobInteractor.java:348)
                at java.lang.Thread.run(Thread.java:748)

            Process finished with exit code 0
        
        Selected remarks about that log:
        
        * Lines 1-5 are about making the connection.
        
        * We first execute ``BasicScheduleJob`` which starts an AIMMS job after a delay of 60 seconds.  Note the absence of tracing statements in this procedure, so there isn't anything in our log.
        
        * Lines 6-7 are about ``ListAllJobs``, there is currently one job  waiting, the job we've just started (because of its initial delay).
        
        * Lines 8-14 are about ``BasicScheduleJobAndCheck``; we see that the status this jobs moves through the states QUEUED, INITIALIZING, and FINISHED.
        
        * Lines 15-31 are about ``ScheduleJobAndCheckResult``, the procedure ``notifyClientWithResults`` sends two times a result back from AIMMS.
        
        * Lines 33-36 show that the data for an AIMMS Parameter is prepared/passed to AIMMS.
        
        * Lines 37-53 shows another procedure ``proc_AdvancedInteraction`` that acts on the interaction with AIMMS.
        
        * Line 55 logs that via ``notifyClientWithProgress`` a ``stopExecution`` event is sent to AIMMS to stop the execution.
        
        * Lines 56-84 show how this abnormal termination is handled step by step.
    
        
.. seealso::

    * `Manual on PRO API <https://documentation.aimms.com/pro/api.html>`_




   