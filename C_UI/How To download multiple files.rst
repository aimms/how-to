How to download multiple files ?
=========================================================================

A download widget in the WebUI is able to point only a unique file name. Thus if you need to download multiple different files, you would need multiple download widgets. However, you may use ZIP files (or equivalent compressed format, such as TAR files on Linux). The question is, how to automatically generate a zip file out of several files thanks to AIMMS, such that your end user would be able to download it from the End user mode (WebUI) ? In developer mode, on PRO or on the AIMMS Cloud ?

.. seealso::
    
    https://techblog.aimms.com/2017/02/22/execute-a-handy-function-to-run-an-executable-program-from-aimms/

.. contents:: Steps we will take
    :local:

    
|

In developer mode
++++++++++++++++++

As you may know, AIMMS is capable of executing any executable program available on its running environment through the :code:`Execute` function. AIMMS running environment may refer to your computer when using AIMMS in developer mode, your server computers when you are using AIMMS PRO and AIMMS computers when you are on the AIMMS Cloud. 

Calling the ``Execute`` intrinsic function
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

You may download the ``7za.exe`` executable from https://www.7-zip.org/download.html, and put it in your project folder. Say you have several files to download in a folder ``FilesToDownload`` in your project folder. As described in https://sevenzip.osdn.jp/chm/cmdline/syntax.htm, you may use the ``Execute`` function as follows. 

.. code-block:: none

    Model Main_TarFiles {
        
        StringParameter TestPara {
            InitialData: "Waiting...";
        }
        
        Procedure MainExecution {
            Body: {

                Execute("7za.exe", "a archive2.zip .\FilesToDownload\*", wait: 1); !On windows
                TestPara := "Ready to test Existence";

            }
        }

    

As you may see, we asked AIMMS to execute a program called "7za.exe" located in the project folder, provided some arguments:

    * ``a`` = `add` command
    * ``archive2.zip`` = the archive path. This will create the archive file in the project folder
    * ``.\FilesToDownload\*`` = the folder to add (any files or sub folder) regardless of their name (because we specified ``*`` at the end)

* As a 3rd argument of the ``Execute`` function, we asked AIMMS to wait until the called program, ``7za.exe``, as finished his job.

* We finished by assigning a string parameter to `"Ready to test Existence"`, notifying us about the end of the zipping process.

You may verify that the archive was created in the project folder.

.. note::

    * If you are running on Windows 10, you may directly use the zip program already installed. Since this zip program is in your PATH, you may access it by calling "zip" in the ``Execute`` function. Thus the ``Execute`` call will look like ``Execute("zip", "archive2.zip .\FilesToDownload\*", wait: 1);`` 
    
    * On Linux, you may also directly use ``Execute("tar", "cvf archivedossier.tar FilesToDownload/", wait: 1);``
    

Configure WebUI download widget
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

You may now open your WebUI, and insert a download widget that you will link with the following typical download procedure, pointing at your newly created ``archive2.zip`` file:

.. code-block:: none

    Procedure DownloadTarArchive {
        Arguments: (FileLocation,StatusCode,StatusDescription);
        Body: {

            FileLocation := "archive2.zip";

            StatusCode := webui::ReturnStatusCode('CREATED');
            StatusDescription := "Nice.";
        }
        StringParameter FileLocation {
            Property: Output;
        }
        Parameter StatusCode {
            Property: Output;
        }
        StringParameter StatusDescription {
            Property: Output;
        }
    }

.. seealso::
    
    https://manual.aimms.com/webui/download-widget.html


If my project is on PRO
++++++++++++++++++++++++

Knowing how works the ``Execute`` function, you may call any executable program in your system PATH, or by specifying the absolute path on your server, such as: ``C:\Program Files (x86)\MyProgram\MyProgram.exe``. However, mind to create the archive somewhere the download procedure may access. in the following example, my PRO server is a Linux machine.

I will thus simply improve my **MainExecution** procedure as follows:

.. code-block:: none

    if ProjectDevelopermode then
        execute("7za.exe", "a archive2.zip .\FilesToDownload\*", wait: 1); !On windows
        TestPara := "Ready to test Existence";
    else
        execute("tar", "cvf archivedossier.tar FilesToDownload/", wait: 1); !On Linux
        TestPara := "Ready to test Existence";
    endif;

.. note:: 

    The ``ProjectDeveloperMode`` intrinsic function detects if a project is in developer or end-user mode (when opened on PRO, a project is automatically in end-user mode)
    
As well as my **Download** procedure:

.. code-block:: none

    if projectDeveloperMode then
        FileLocation := "archive2.zip";
    else
        FileCopy("archivedossier.tar", webui::GetIOFilePath("archivedossier.tar"));
        FileLocation := webui::GetIOFilePath("archivedossier.tar");
    endif;

    StatusCode := webui::ReturnStatusCode('CREATED');
    StatusDescription := "Nice.";

As you may have noticed, when on PRO server, we took care to copy the archive file created in the project folder in the "PRO-temp" folder, where the download widget will be able to access the file and make the EndUser download it in his browser.

Et voila! 

.. note::

    You may use this implementation also on AIMMS Cloud, since AIMMS Cloud computers are operating on Linux.

Downloadable example
+++++++++++++++++++++

Please find the AIMMS example project attached here :download:`DownloadMultipleFiles.zip<..\\Resources\\UI\\Downloads\\DownloadMultipleFiles.zip>`