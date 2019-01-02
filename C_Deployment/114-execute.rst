Execute, a handy function to run an executable program from AIMMS
=================================================================

.. sidebar:: Executing another program

    .. image:: ../Resources/C_Deployment/Images/114/exe-file-icon-68130.png

AIMMS provides a good selection of functions, such as arithmetic functions, string manipulation functions, time functions, distribution functions, file functions, and more, for app developers to build prescriptive analytics applications.

However, if you already have another program for certain tasks that you don't want to rebuild in AIMMS or if the functionality you need is not available, you can use the Execute function to call the program from AIMMS.


Unzipping Files Using the Execute Function
------------------------------------------

Here's an example. Let's say you need a functionality to unzip files. In your case, the input files, taken from the archive directory, are currently zipped. You need to unzip them before reading. You have the application, unzip.exe, installed in the "C:\ProgramFiles" for you to use. Then you can use the following code in your AIMMS procedure to unzip the input files:

.. code-block:: aimms

    Execute("C:\\ProgramFiles\\unzip.exe", "D:\\data\\archive\\inputfile.zip");

It works as executing the command line: ``"C:\ProgramFiles\upzip.exe D:\data\archive\inputfile.zip"`` from your AIMMS project directory. Of course, you don't want to see a bunch of files unzipped in your project directory. You can fix that by modifying the statement. Simply add the "-d" option, provided by the unzip.exe, to redirect the upzipped files to a temp folder:

.. code-block:: aimms

    Execute("C:\\ProgramFiles\\unzip.exe", "D:\\data\\archive\\inputfile01.zip -d D:\\temp\\files\\");

Instead of using double quoted strings, you can also use string parameters to generate a command line dynamically based on data in your AIMMS model. You can find more details on the Execute function in the `AIMMS The Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_.

Running Your Own Code
---------------------

You can also use the Execute function to run your own code written in programming languages such as Java, Python, etc, assuming the program is installed in the machine that's running AIMMS. In the case of Java, I recommend you first generate an executable ".jar" file, and use the Execute function to call the program "java" to execute your JAR file. Let's assume we have a "RetrieveData.jar" which takes two arguments, an ID number and a date, to retrieve the related data. To use it, we have the following:


.. code-block:: aimms

    MyCommandLine := FormatString("-jar RetrieveData.jar %s %s",ID, RequestDate);
    Execute("java", MyCommandLine );

where "MyCommandLine", "ID" and "RequestData" are string parameters in AIMMS. "ID" and "RequestDate" take dynamic value at run time, and "MyCommandLine" is generated based on these values.  Please refer to the `Again: AIMMS The Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_ for more information on the "FormatString" function.

Similarly, you can use the Execute function to call Python to run a ".py" file. Please note that in both cases, the environment variables are set properly. Otherwise, you need to specify the full path where Java.exe or Python.exe is installed.

Besides calling the EXE file of an installed program, you can also build your own executable file in various programming languages and tools, and use the Execute function to call it.


.. include:: ../includes/form.def


