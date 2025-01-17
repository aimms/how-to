In model tracing
==================

In this article, we'll show some model code that explicitly writes to a trace.
This trace is a:

#.  File in the log folder, when the application is started via AIMMS Developer.

#.  Part of the session log, when the application is published on AIMMS PRO On-Premise

#.  File copied to AIMMS PRO Storage, when the application is published on AIMMS Cloud.

Running example
---------------

The running example is based on a transportation problem: Pellets of bottled water are to be shipped from bottling locations to distribution centers. This example is extended with some logging and :download:`can be downloaded here <model/writingToSessionLogBottledWater.zip>` 

Calling the tracing procedure
-----------------------------

The tracing procedure ``pr_logMsg`` just has one scalar input argument: ``sp_msg``.  As this is a scalar input argument, you can enter any string valued expression.

.. code-block:: aimms

    pr_logMsg( sp_fullMsg );
    ! This procedure adds the line sp_fullMsg to the output stream.
    


Implementation of the tracing procedure
---------------------------------------


.. code-block:: aimms
    :linenos:

    Procedure pr_logMsg {
        Arguments: (sp_msg);
        Body: {
            if pro::GetPROEndPoint() then
                pro::management::LocalLogInfo( sp_msg );
            else
                if not p_noLogLinesWritten then
                    if fileExists( sp_traceFilename ) then
                        FileDelete( sp_traceFilename );
                    endif ;
                endif ;
                put f_traceFile ;
                put sp_msg, / ;
                putclose ;
                p_noLogLinesWritten += 1 ;
            endif ;
        }
        StringParameter sp_msg {
            Property: Input;
        }
    }

Remarks:

#.  Line 4,5: Use the AIMMS PRO session log, if the application is published on AIMMS PRO.

#.  When the application is running via AIMMS Developer, a trace file is used. 
    The trace file is declared as follows. 
    The merge mode indicates that this file is appended to when it already exists.

    .. code-block:: aimms
        :linenos:

        File f_traceFile {
            Name: sp_traceFilename;
            Device: Disk;
            Mode: merge;
        }
        
#.  Line 7-11: The trace file is deleted first, when it is about to be written to for the first time.

#.  Line 13: Actually adding content to the trace file.

#.  Line 14: Closing the trace file after every line written; in case of a crash, the trace file still contains the latest contents.

How to integrate
-----------------

The code explained and demoed above is contained in a separate section named: ``Logging`` of download offered above.
See `Export code to another project <https://how-to.aimms.com/Articles/145/145-import-export-section.html>`_ 
for copying the code to your project.



