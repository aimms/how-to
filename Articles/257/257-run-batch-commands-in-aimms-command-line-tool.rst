Run batch commands in AIMMS Command Line Tool========================================================.. meta::   :description:  A summary of commands available in AimmsCmd   :keywords: AimmsCmd, Batch processing, AIMMS Command Line Tool.. note::    This article was originally posted to the AIMMS Tech Blog.
.. <link>https://berthier.design/aimmsbackuptech/2012/06/22/aimms-command-line-tool/</link>
.. <pubDate>Thu, 21 Jun 2012 23:58:59 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1453</guid>
In the integrated environment or under Linux system, AIMMS supports a command line tool through which you can control an AIMMS project externally. In the command window, you can start the AIMMS command line tool by running:
.. code-block:: none    :linenos:
    AimmsCmd ExampleProject.aimms
AIMMS command line tool offers a set of commands, for example,
#. Assigning values:
    .. code-block:: none        :linenos:        Let Demand := 100;
#. Displaying the contents:    .. code-block:: none        :linenos:        Display Supply; 
#. Running procedures:
    .. code-block:: none        :linenos:        Run MainExecution ;        #. Exit    .. code-block:: none        :linenos:        Quit ;        
Instead of running each command one by one, you can also run batch commands by writing all the commands you want to execute in a text file. For example, the contents of file "cmds.txt" are:
.. code-block:: none    :linenos:
    Let p_Demand := 100;
    Display p_Revenue;
    Run MainExecution;    Quit ;
By running the following command, you can have them all executed.
.. code-block:: none    :linenos:
    AimmsCmd ExampleProject.aimms < cmds.txt 
By doing this, all the running results will be displayed on the command window. If you want to redirect the output to a text file, you can add it to the running command as well... code-block:: none    :linenos:
    AimmsCmd ExampleProject.aimms < cmds.txt > log.txt     .. note:: Windows powershell doesn't support the ``<`` operator to re-direct input. You can get around that using:    .. code-block:: none        :linenos:        Get-Content cmds.txt | AimmsCmd.exe StandAlone.aimms > log.txtThe example used can be downloaded :download:`here <model/StandAlone.zip>` Further reading:----------------See AIMMS The User's guide, Section "The Aimms command line tool"... include:: /includes/form.def
