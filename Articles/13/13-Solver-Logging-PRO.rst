Retrieve Solver Log Files in AIMMS PRO
==========================================

.. meta::
   :description: How to get log files from a particular solver in AIMMS PRO.
   :keywords: log, logfile, solver


In :doc:`13-Solver-Logging-IDE`, we explain how to adapt the project such that solver log files are available for inspection. 
A solver session runs somewhere on an AIMMS PRO server, and as a user, you do not have direct access to those files. 
However, with a few small steps, which are explained in this article, you can access these files as well.

#. Clarify the solver used in a AIMMS PRO solver session (job).

#. Copy the solver log file to a place on AIMMS PRO Storage

#. Download the solver log file 

Each of these steps are discussed in some more detail below:

Clarify the solver used in an AIMMS PRO solver session
------------------------------------------------------

As an AIMMS PRO solver session doesn't have a UI, it doesn't have a progress window either. For apps published using a WinUI, you can still use the request manager to obtain progress information. Something similar is not available for WebUI applications (yet).

Some progress information can be obtained via setting the option ``major_messages`` to ``on``, the ``session.log`` file will contain lines like:

    .. code-block:: none

        09:21:47,138 0x7f55877fe700 [INFO] {AIMMS.gui.vm-prog} Calling CPLEX 12.8 for MIP of 15 rows, 13 columns (4 integer) and 52 non-zeros.
        09:21:47,138 0x7f55877fe700 [INFO] {AIMMS.gui.vm-prog} Calling CPLEX 12.8 to solve MIP FlowShopModel minimize TimeSpan.
        09:21:47,169 0x7f55877fe700 [INFO] {AIMMS.gui.vm-prog} There is 0 Kb in use by CPLEX 12.8.
        
Alternatively, you can obtain this information via ``AIMMS.solver`` logger by setting the log option of ``AIMMS Sessions`` to ``Debug`` in the ``log management`` page of tab ``configuration`` of the AIMMS PRO portal. If this option is even set trace, you will also get the "reason to interrupt" of a solve statement. Beware; setting this option to trace typically leads to large amounts of trace - in addition, changing this option also leads to increased tracing for other applications.

The solver used, determines the name of the solver log file. Now let's get that solver log file saved directly after a solve.        

Copy the solver log file to a place on AIMMS PRO Storage
--------------------------------------------------------

The solver log file is created when the solve is finished.  This log file is created in the so-called pro temp folder. 
To save it, we will need to copy it from that folder to AIMMS PRO storage:

    .. code-block:: aimms
        :linenos:
        :emphasize-lines: 6-10

        Empty AllVariables;
        GenerateData();

        solve FlowShopModel  ;

        ! Copy the solver status file from the pro temp folder to AIMMS PRO Storage.
        sp_SolverLogFilename := formatString("%e.sta", CurrentSolver('MIP'));
        sp_shortSolverLogFilename := "solver.log" ;
        sp_ProTempLocation := pro::PROTempFolder + "/" + sp_SolverLogFilename ;
        sp_userLocation := "userdata/" + pro::PROEnvironment + "/" + pro::PROUserName + "/" + pro::ModelName + "/" + sp_shortSolverLogFilename ;
        pro::SaveFileToCentralStorage( sp_ProTempLocation, sp_userLocation );

        prepInterface;        

Here the solver session has saved the solver log file to a location in AIMMS PRO storage that is reserved for the current user.
On lines 6-10 were added just to save the log file to PRO storage.

Now let the data session of that user actually download the log file to his own device.        

Download the solver log file 
-----------------------------

Create a download button and use the following procedure.
The part not highlighted is taken literally from `Webui documentation <https://documentation.aimms.com/webui/download-widget.html#download-widget>`_ .

    .. code-block:: aimms
        :linenos:
        :emphasize-lines: 4-5,9-11

        Procedure prDownloadSolverLogFile {
            Arguments: (FileLocation,statusCode,statusDescription);
            Body: {
                sp_shortSolverLogFilename := "solver.log" ;
                FileLocation := sp_shortSolverLogFilename ;

                sp_FileLoc := webui::GetIOFilePath( sp_shortSolverLogFilename );

                sp_userLocation := "userdata/" + pro::PROEnvironment + "/" + pro::PROUserName + "/" + pro::ModelName + "/" + sp_shortSolverLogFilename;

                pro::RetrieveFileFromCentralStorage( sp_userLocation, sp_FileLoc );

                if FileExists(sp_FileLoc) then
                    statusCode := webui::ReturnStatusCode('CREATED');
                    statusDescription := "Nice" ;
                else
                    statusCode := webui::ReturnStatusCode('ERROR');
                    statusDescription := "Better luck next time" ;
                endif ;
            }
            StringParameter FileLocation {
                Property: Output;
            }
            Parameter statusCode {
                Property: Output;
            }
            StringParameter statusDescription {
                Property: Output;
            }
            StringParameter sp_SolverLogFilename;
            StringParameter sp_FileLoc;
            StringParameter sp_userLocation;
        }

On lines 4-5 we specify the file name assuming the use of CPLEX 12.9, on lines 9-11 we actually copy from the PRO storage to the PRO temp folder.
        
By pressing the download button, the user will retrieve the log file from PRO storage and download it to his/her download folder.
        
Summary
-------
With a few easy to copy extensions to your model, it becomes easy to get an overview of the solution process that takes place on the AIMMS PRO server.

The example used to construct this article: 
:download:`Flow Shop – with download log file <downloads/FlowShop with Download log file.zip>`.

