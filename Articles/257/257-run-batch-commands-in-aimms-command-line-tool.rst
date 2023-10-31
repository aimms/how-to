Run Batch Commands with AIMMS Command Line Tool
========================================================

.. meta::
   :description:  A brief overview for using AIMMS Command Line Tool.
   :keywords: AimmsCmd, Batch, command line, cmd


In the integrated environment, or on a Linux system, you can control an AIMMS project externally using a command line tool.

To start using the command line tool open the terminal and run ``AimmsCmd ExampleProject.aimms``.

.. note::
    
    If ``AimmsCmd`` program is not part of your environment variables, you may find ``AimmsCmd`` in 
    
    ``C:\Users\<Your_User_Name>\AppData\Local\AIMMS\IFA\Aimms\<AIMMS_version>\Bin\AimmsCmd.exe``

Basic commands available
------------------------
AIMMS command line tool offers a the following commands.

* Assigning values:

    .. code-block:: none

        Let p_Demand := 100;

* Displaying the contents:

    .. code-block:: none

        Display p_Revenue;
 
* Running procedures:

    .. code-block:: none

        Run MainExecution;
        
* Exiting the tool

    .. code-block:: none

        Quit;
        
Running batch commands
----------------------
Instead of running each command one by one, you can also run batch commands by writing all the commands you want to execute in a text file. 

For example, let's say the file ``cmds.txt`` contains the following code.

.. code-block:: none
    :linenos:

    Let p_Demand := 100;
    Display p_Revenue;
    Run MainExecution;
    Quit ;

You can run the following command in the terminal to execute all the commands contained in the file.

.. code-block:: none
    :linenos:

    AimmsCmd ExampleProject.aimms < cmds.txt 

By doing this, all the running results will be displayed on the command window. If you want to redirect the output to a text file, you can add it to the running command as well.

.. code-block:: none
    :linenos:

    AimmsCmd ExampleProject.aimms < cmds.txt > log.txt 
    
.. note:: 
    
    - Windows Powershell doesn't support the ``<`` operator to redirect input. You can get around that using:

        .. code-block:: powershell

            Get-Content cmds.txt | AimmsCmd.exe ExampleProject.aimms > log.txt

    - Calling AIMMS from Python and printing the logs could be done using the following script and the `Download example`_

        .. code-block:: Python

            import subprocess

            path_to_AimmsCmd = 'C:\\Users\\Arthur.AIMMS\\AppData\\Local\\AIMMS\\IFA\\Aimms\\4.75.4.8-x64-VS2017\\Bin\\AimmsCmd.exe'

            command = path_to_AimmsCmd + ' StandAlone.aimms < cmds.txt > log.txt'

            ret = subprocess.call(command, shell=True)

            log_file = open('log.txt','r')
            print(log_file.read())
            log_file.close()

Download example
-----------------
The example model used in this article can be downloaded below:

* :download:`model/StandAlone.zip` 

Further reading
----------------

* :doc:`miscellaneous/calling-aimms/the-aimms-command-line-tool`




