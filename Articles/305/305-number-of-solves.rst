Number of running and pending solver sessions
=============================================

.. meta::
   :description: The number of pending and running jobs may influence the decision to add more jobs.
   :keywords: deployment, AIMMS PRO, jobs, queue, queueing, solving

.. RetrieveSessionList
.. ListActiveSessions
.. ListSessionByStatus
.. ListSessionSinceDate
.. pro::sessionmanager::ListActiveSessions
.. pro::sessionmanager::ListSessionByStatus
.. pro::sessionmanager::ListSessionSinceDate
.. pro::sessionmanager::RetrieveSessionList
.. 

One potential consideration to start a new server session, 
is whether there are other server sessions started on that AIMMS PRO server.
In this article, we will illustrate how to obtain the number of sessions running at an AIMMS PRO server.

Interpretations
----------------

There are at least two interpretations on the number of server sessions:

#. The number of AIMMS PRO sessions actually running

#. The number of AIMMS PRO sessions not yet finished. 
   This latter number is called the number of *active* sessions in this article.

Use ``pro::sessionmanager::ListSessionSinceDate``
------------------------------------------------------
   
To obtain the number of server session actually running, we use the following code, 
where ``p_NoRun`` contains the number of server sessions actually running.

.. code-block:: aimms
    :linenos:

    p_ret := pro::Initialize();
    if not (p_ret) then return 0; endif;

    if pro::GetPROEndPoint() then
        sp_SinceDate :=  fnc_CurrentToStringDelta(-24*60*60); ! Assuming jobs older than a day are no longer interesting.

        p_AllUsers := 1 ;
        if (p_AllUsers) then
            sp_SessionModelId := "";
            sp_SessionModelVersion := "";
        else
            sp_SessionModelId := pro::ModelName; ! Retrieve sessions for all versions of the model
            sp_SessionModelVersion := ""; ! don't restrict to a specific ModelVersion
        endif;

        ! Load raw session list from PROT
        p_ret := pro::sessionmanager::ListSessionSinceDate(
            dateStr           :  sp_SinceDate, 
            SessionList       :  S_SessionList, 
            ClientQueue       :  sp_ClientQueue, 
            WorkerQueue       :  sp_WorkerQueue, 
            CurrentStatus     :  p_CurrentStatus, 
            CreateTime        :  sp_CreateTime, 
            userEnvironment   :  sp_UserEnv, 
            UserName          :  sp_UserName, 
            proj              :  sp_Application, 
            clientRef         :  sp_OriginalCasePath, 
            descr             :  sp_RequestDescription, 
            proc              :  sp_RequestProcedure, 
            timeOut           :  p_RunTimeOut, 
            inputDataVersion  :  sp_VersionID, 
            outputDataVersion :  sp_ResponseVersionID, 
            logFileVersion    :  sp_MessageLogVersionID, 
            ErrorMessage      :  sp_ErrorMessage, 
            modelStatus       :  p_ActiveStatus, 
            ErrorCode         :  p_ErrorCode, 
            AllUsers          :  p_AllUsers, 
            projectID         :  sp_SessionModelId, 
            projectVersion    :  sp_SessionModelVersion);   
        if not (p_ret) then return 0; endif;
        p_NoCreated      := count( i_sess | p_CurrentStatus(i_sess) = pro::PROTS_CREATED      );
        p_NoQueued       := count( i_sess | p_CurrentStatus(i_sess) = pro::PROTS_QUEUED       );
        p_NoInitializing := count( i_sess | p_CurrentStatus(i_sess) = pro::PROTS_INITIALIZING );
        p_NoReadyToRun   := count( i_sess | p_CurrentStatus(i_sess) = pro::PROTS_READY        );
        p_NoRun          := count( i_sess | p_CurrentStatus(i_sess) = pro::PROTS_RUNNING      );
    else
        p_ret := 0 ;
    endif;

    return p_ret ;


Aggregate number of unfinished sessions
-------------------------------------------

Based on the above, aggregating the number of unfinished sessions is simple:

.. code-block:: aimms
    :linenos:

    p_DerivedActiveJobs(ep_obs) := 
        p_ObservedCreatedJobs(      ep_obs ) +
        p_ObservedQueuedJobs(       ep_obs ) +
        p_ObservedInitializingJobs( ep_obs ) +
        p_ObservedReadyToRunJobs(   ep_obs ) +
        p_ObservedRunningJobs(      ep_obs ) ;

Trying out yourself
-------------------

To permit you to try out this example code yourself, two applications can be downloaded from this article:

#. :download:`Start several jobs <model/FlowShopMultipleSolves.zip>` 

#. :download:`Show active jobs <model/CountRunningJobs.zip>` 

To experiment with these apps, you should download and publish them both on an AIMMS PRO. 
Subsequently start both apps, and press the start button of both apps.

You'll then see how ``CountRunningJobs`` monitors multiple jobs:

.. image:: images/monitoring.png
    :align: center

You can copy the procedure ``pr_CountRunningJobs`` in ``CountRunningJobs`` to 
determine the number of running jobs or the number of active jobs in your own application.


.. warning:: 

    There is a race condition here. When two users at the same time:
    
    #. Ask for the number of running jobs, both returning 0
    
    #. Subsequently submit a jobs
    
    There may still be one job waiting for the other. 
    To detect this circumstance, the number of active jobs is one more than the number allowed to run in parallel.
    To detect, whether the waiting job is your job, you can test for ``pro::session::CurrentSessionStatus``
    It is possible to kill that waiting job as explained in :doc:`this article<../34/34-interrupt-server-session>`










