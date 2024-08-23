Session Argument Processing for Docker Runs
--------------------------------------------

This article discusses a small library designed to ease steering AIMMS runs in docker containers.

The first section presents a why, the second section presents a how.


Why use session argument processing
-----------------------------------

Deploying an AIMMS app via `Docker for AIMMS <https://github.com/aimms/aimms-eo>`_ is `preferred <https://www.docker.com/blog/5-benefits-of-a-container-first-approach-to-software-development/>`_
over deploying using AimmsCmd in a self created environment.

When deploying via AimmsCmd, a frequently observed approach is to put the commands in an input file
and redirect the input to AimmsCmd from such a file. For instance the command:

.. code-block:: none

    AimmsCmd.exe myproj.aimms < inp.txt
    
where the file ``inp.txt`` may look as follows:

.. code-block:: none

    let scalpar := 45.67 ;
    run myproc ;
    quit ;

And this would run the project ``myproj.aimms``, by first setting the scalar parameter ``scalpar`` to 45.67 
and then run the procedure ``myproc``.

Instead of using an input file to steer a run of an AIMMS project, also session arguments can be used
to steer a run.

The library ``SessionArgumentProcessing`` is created to ease the conversion of an input file for AimmsCmd 
to a series of session arguments.

Using session arguments the above command is as follows:


.. code-block:: none

    AimmsCmd.exe myproj.aimms let scalpar 45.67 run myproc
    
or using the ``\\`` character to split a single command:


.. code-block:: none

    Aimms.exe myproj.aimms \
    let scalpar 45.67  \
	run myproc
    
Apart from lack of syntactic sugaring, this command looks very similar to the above steering using AimmsCmd.

How is the session argument parsing implemented?
------------------------------------------------

The library iterates over the session arguments using :aimms:func:`SessionArgument`.
If the argument is:

#.  ``let`` then the next two arguments specify a scalar parameter and a value, for instance ``scalpar`` and ``45.67`` respectively.
    
	This is processed in a runtime library by assigning the body attribute of a procedure the line:
	
	.. code-block:: aimms 

		scalpar := 45.67 ; 
		
	and then executing that runtime procedure, via an `apply <https://documentation.aimms.com/language-reference/procedural-language-components/procedures-and-functions/calls-to-procedures-and-functions.html#the-apply-operator>`_ statement.

#.  ``run`` then the next argument specifies the name of the procedure to run.

	This is processed by using an `apply <https://documentation.aimms.com/language-reference/procedural-language-components/procedures-and-functions/calls-to-procedures-and-functions.html#the-apply-operator>`_ statement.


What is the responsibility of the developer of the AIMMS application?
----------------------------------------------------------------------

An integration specialist, deploying an AIMMS app via Docker, needs to know:

#.  Which scalar parameters can be set in a meaningful way, and

#.  Which procedures can be run in a meaningful way.

This knowledge comes from the application, and can be shared by the application developer.
Clearly, documentation on these scalars and procedures need to be provided by the application developer.

