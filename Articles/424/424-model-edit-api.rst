Model Edit API
================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Model Edit functions, AIMMS API, using AIMMS externally, C++, integrating AIMMS, runtime libraries
   :description: This example illustrates how the Model Edit functions can be used to build and solve an AIMMS transport model from within an external program, using the AIMMS API. 

Direct download AIMMS Project :download:`Model Edit API.zip <model/Model Edit API.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Model%20Edit%20API

The AIMMS API supports Model Edit Functions, allowing external applications to inspect, modify or even construct AIMMS models. This example illustrates how the Model Edit functions can be used to build and solve an AIMMS transport model from within an external program, using the AIMMS API. 

The external program "Model Edit API.exe" performs the following steps:
(Please note that for the 64-bit version of AIMMS, the executable is named "Model Edit API64.exe".) 

1) It starts up an AIMMS project (named 'Empty AIMMS project').
2) It builds a transport model using this AIMMS project, i.e. it creates 

- sets, parameters, variables, constraints, and a math program;
- a procedure (named 'Solve_Transport') that initializes the data, solves the model, and writes the data (including the solution) to a file.

3) It exports the model to a ``.ams`` file.
4) It writes a log file, in which you can see whether the steps above were successful.

The source of the external program "Model Edit API.exe" is displayed on the Source tab on the demo page and it is contained in the Source directory that comes with this example.

To run the external program:


- make sure that the Bin directory of your AIMMS installation directory is contained in the PATH environment variable on your computer. Make sure that there is no Bin folder of an older AIMMS version in the PATH environment, this will lead to incompatibility problems.
- run the program "Model Edit API.exe", or use the corresponding button on the demo page.

Afterwards, you can inspect the log file, data output file and model file, on the tabs on the Demo Page. In the log file, you can see all actions that the external program has executed. In case there are any errors (during execution), these will show up in the log file as well.

You can inspect the created transport model by importing it, as a runtime library, into the model explorer of the 'Model Edit API' project. To do so, click on the Import Transport Model on the demo page, which calls the procedure 'ImportModelFile' underneath. Once it is imported, you can run the procedure 'Solve_Transport' to solve the transport model.

Notes:

- You can change the PATH environment variable by going to Control Panel, System... For Windows 7, Windows 10 and newer Windows versions: Advanced system settings, Environment Variables, select 'Path', click on Edit, and add the path of the Bin directory (separated by ";").
- AIMMS projects started via the AIMMS API are in end-user mode. By default, warnings are not communicated to end-users. If you do want to check whether AIMMS has any warnings for you, you can set the option Communicate_warnings_to_end_users to 'On'.
- The transport model is not created in the 'Empty AIMMS project'. To add it to that project the model has to be imported as a runtime library, as demonstrated for the 'Model Edit API' project by running the procedure 'ImportModelFile'.

Keywords:
Model Edit functions, AIMMS API, using AIMMS externally, C++, integrating AIMMS, runtime libraries


