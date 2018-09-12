How to get solver log files using AIMMS PRO?
======================================================

With :doc:`AIMMS IDE<BasicSolverLoggingIDE>`, the log files from solvers are obtained directly.
Obtaining these log files when the solve is executed on AIMMS PRO is less direct; it takes the following steps:

#. Clarify the solver used in a AIMMS PRO server session (job).

#. Copy the solver log file to a place on AIMMS PRO Storage

#. Download the solver log file 

Each of these steps are discussed in some more detail below:

Clarify the solver used in an AIMMS PRO server session
------------------------------------------------------

As an AIMMS PRO server session doesn't have a UI, it doesn't have a progress window either. 
However, by setting the option ``major_messages`` to ``on``, the ``session.log`` file will contain lines like:

    .. code-block:: none

        09:21:47,138 0x7f55877fe700 [ERROR] {AIMMS.ProgressSupport} Calling CPLEX 12.8 for MIP of 15 rows, 13 columns (4 integer) and 52 non-zeros.
        09:21:47,138 0x7f55877fe700 [ERROR] {AIMMS.ProgressSupport} Calling CPLEX 12.8 to solve MIP FlowShopModel minimize TimeSpan.
        09:21:47,169 0x7f55877fe700 [ERROR] {AIMMS.ProgressSupport} There is 0 Kb in use by CPLEX 12.8.

The solver used, determines the name of the solver log file.  Now let's get that solver log file saved directly after a solve.        
        
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
        sp_SolverLogFilename := "CPLEX 12.8.sta" ;
        sp_ProTempLocation := pro::PROTempFolder + "/" + sp_SolverLogFilename ;
        sp_userLocation := "userdata/" + pro::PROEnvironment + "/" + pro::PROUserName + "/" + pro::ModelName + "/" + sp_SolverLogFilename ;
        pro::SaveFileToCentralStorage( sp_ProTempLocation, sp_userLocation );

        prepInterface;        
        
Here the server session has saved the solver log file to a location in AIMMS PRO storage that is reserved for the current user.
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
                sp_SolverLogFilename := "CPLEX 12.8.sta" ;
                FileLocation := sp_SolverLogFilename ;
                
                sp_FileLoc := webui::GetIOFilePath( sp_SolverLogFilename );
                
                sp_userLocation := "userdata/" + pro::PROEnvironment + "/" + pro::PROUserName + "/" + pro::ModelName + "/" + sp_SolverLogFilename;
                
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

On lines 4-5 we specify the file name assuming the use of CPLEX 12.8, on lines 9-11 we actually copy from the PRO storage to the PRO temp folder.
        
By pressing the download button, the user will retrieve the log file from PRO storage and download it to his/her download folder.
        
Summary
-------
With a few easy to copy extensions to your model, it becomes easy to get an overview of the solution process that takes place on the AIMMS PRO server.

.. "D:\u\s\How to\gitlab\master\Resources\C_Solvers\BasicLogging\FlowShop with Download log file.zip"

:download:`Flow Shop – with download log file <../Resources/C_Solvers/BasicLogging/FlowShop with Download log file.zip>`.

.. include:: ../includes/form.def