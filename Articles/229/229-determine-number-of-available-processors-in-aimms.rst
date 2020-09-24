:orphan:Determine number of available processors in AIMMS==================================================
.. meta::   :description: Determine the number of CPUs available on the current computer.   :keywords: CPU's available, Windows, Linux.. note::    This article was originally posted to the AIMMS Tech Blog.
.. figure:: images/HowManyCores.jpg    Number of cores in computerThere are situations where you would like to know the number of CPU cores you have available in your computer. 
For example in the project that I worked out in the earlier blog post Solve problems in parallel with asynchronous solver sessions, I showed how you can start up multiple asynchronous solver sessions in AIMMS to solve multiple different problems simultaneously (if your license allowed for this).
In such cases, you typically do not want to have more simultaneous sessions running than the number of cores present in your computer. The operating system (Windows or Linux) provides information that allows you to find the number of cores.
Another example is that you could use this information to limit the number of parallel threads of solvers like CPLEX and GUROBI to some value that depends on the actual number of cores that is available.
When running your project under Windows, you can query the information about the number of available CPU cores via the environment variable NUMBER_OF_PROCESSORS. You can use EnvironmentGetString function of AIMMS to get this information as follows:When running your project under Linux
.. code-block:: none   grep "^processor" /proc/cpuinfo  | wc -l | tr -d '\n' > /tmp/numcpus.txt
   PROCEDURE
     identifier :  CheckNumberOfCPUs

     DECLARATION SECTION 

       STRING PARAMETER:
          identifier :  psNumCPUs ;

     ENDSECTION  ;

     body       :  
       if EnvironmentGetString("NUMBER_OF_PROCESSORS", psNumCPUs) then
           if val(psNumCPUs) <= 0 then
               raise error "Could not determine number of processors" ; 
           endif ;
           DialogMessage("Number of processors = " + psNumCPUs) ; 
       else
           raise error "Could not determine number of processors" ; 
       endif ; 
   ENDPROCEDURE  ;
   </pre>

If you are running under Linux, there is no general environment variable that is set. However, you can obtain the number of processors by parsing the information found in the file /proc/cpuinfo. You can determine the number by counting the number of lines that start with the text processor, which can be done with the following shell command:



You can use the execute statement in AIMMS to execute the above statement (and instruct the shell to write the number of processors in the file /tmp/numcpus.txt as follows:

.. raw:: html

   <pre lang="aim">
   execute("grep \"^processor\" /proc/cpuinfo  | wc -l | tr -d '\\n' > /tmp/numcpus.txt", wait:1) ;
   numCPUs := val(FileRead("/tmp/numcpus.txt")) ;
   </pre>

Note that the backslash of \\n in the tr command needs to be escaped in the AIMMS string. The purpose of the tr command is to remove the trailing \\n (=newline) character of the file numcpus.txt. Without using this tr command, obtaining the numerical value of the contents of the file with the val command will value because the contents of the file are actually "2\n" .
