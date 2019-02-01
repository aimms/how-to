Exchange Data with External Source
========================================

.. meta::
   :description: How to link data from an external data source in AIMMS models.
   :keywords: link, exchange, external, import, source

      .. note::

	This article was originally posted to the AIMMS Tech Blog on January 21, 2014 by Chris Kuip.

.. meta::
   :description: 
   :keywords:

      .. note::

	This article was originally posted to the AIMMS Tech Blog on - by -.

.. sidebar:: Make connection

    .. image:: ../../Resources/C_Language/Images/133/5.10_Road_sign.gif
        :align: center


Data exchange is an essential part of every application. AIMMS supports various industry standards for data exchange, such as ODBC for databases, XML Files and spreadsheets. But what if the data is not stored according to one of these standards? In order to read data from an arbitrary data source, AIMMS offers access to self-developed or third party functions. This blog post provides an overview of the steps you need to take to create a data exchange link between a proprietary data format and AIMMS. The process is illustrated by using a concrete modeling exercise from the Constraint Programming example library CSPLIB.  

The example
-----------

The constraint programming example library, http://www.csplib.org, provides several concrete constraint programming modeling exercises, where, given a particular exercise, several input files are provided. Each such input file is a sequence of numbers. Consider the first modeling exercise: the famous car sequencing problem. The input format for the car sequencing problem, quoting the CSPLIB, is defined as:


.. code-block:: none

    * First line: number of cars; number of options; number of classes.
    * Second line: for each option, the maximum number of cars with that option in a block.
    * Third line: for each option, the block size to which the maximum number refers.
    * Then for each class: index no.; no. of cars in this class; for each option, whether or not this class requires it (1 or 0).


In "Solving the car-sequencing problem in constraint logic programming," M. Dincbas et al.* provide the following example:

.. code-block:: none

    10 5 6
    1 2 1 2 1
    2 3 3 5 5
    0 1 1 0 1 1 0
    1 1 0 0 0 1 0
    2 2 0 1 0 0 1
    3 2 0 1 0 1 0
    4 2 1 0 1 0 0
    5 2 1 1 0 0 0


To input the data into an AIMMS application, we want external functions that:

* Open and close a text file, say openFileHandle and closeFileHandle.
* Get an integer from a file, say getInt.

Creating a DLL for external functions:
--------------------------------------

To declare functions in the DLL as *callable*, you will need the following macro:

.. code-block:: cpp

    #ifdef __cplusplus
    #define DLL_EXPORT_PROTO(type) extern "C" __declspec(dllexport) type WINAPI
    #else
    #define DLL_EXPORT_PROTO(type) extern __declspec(dllexport) type WINAPI
    #endif

C++ Functions declared using this macro can be called from outside the DLL in which they are implemented. Just put this macro in a header file. For the getInt function, you can use this macro to declare it as follows:


.. code-block:: cpp

    // Upon success, 0 is returned and the result is stored in i.
    // hndl is a file handle obtained via the function openFileHandle.
    DLL_EXPORT_PROTO(int) getInt( int hndl, int *i );

and subsequently the implementation as follows:

.. code-block:: cpp

    DLL_EXPORT_PROTO(int) getInt( int hndl, int *i )
    {
        ... // The actual implementation can be found in
            // the visual studio solution provided below.
    }

Note that the DLL_EXPORT_PROTO macro needs to be repeated in the header of the implementation. Once a set of callable functions is available, we want to use these functions in an AIMMS project. We do this in two steps, in the first step we declare the external procedure in AIMMS, and in the second step we wrap this in an ordinary procedure, easing the syntax and simplifying error handling. 

Step 1, the external procedure call
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: aimms

    ExternalFunction getInt {
        Arguments: (hndl,anint);
        DllName: DLL_Filename;
        ReturnType: integer;
        BodyCall: getInt(integer : hndl,integer : anint);
        Parameter hndl {
            Property: Input;
        }
        Parameter anint {
            Property: Output;
        }
    }

    
Let's check each of the attributes of this external function.

* First, the file in which the DLL is stored. Here, ``DLL_Filename`` is a string parameter that is defined based on the running platform, 32 or 64 bits (See also http://blog.aimms.com/2012/07/get-platformarchitecture-information-in-aimms/)
* Second, the return type, which is typically an ``int`` or a ``double``.
* Third, the character encoding - we compiled it using wide chars (by defining the preprocessor macro UNICODE) which corresponds to the UTF16-LE encoding on Windows. See also http://www.unicode.org/.
* Fourth and most importantly, the body call. We know the name of the C++ function to be called, as we have developed the library ourselves. However, when a DLL is supplied to you, you can check the available functions using depends.exe from http://www.dependencywalker.com. In addition, you will need to map the arguments. More information about this can be found in the AIMMS Language Reference (starting in the paragraph "The BODY CALL attribute").

Step 2, wrapping it in an AIMMS procedure
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: aimms

    Procedure int {
        Arguments: (hndl,anint);
        Body: {
            rc := getInt(hndl,anint);
            if not rc then
                  ErrorMessage( msg );
                  raise error msg ;
            endif ;
        }
        Parameter hndl {
            Property: Input;
        }
        Parameter anint {
            Property: Output;
        }
        StringParameter msg;
        Parameter rc;
    }


By wrapping the external procedure in an ordinary procedure, we are able to define the error handling as we see fit. Now this procedure can be used to input the data. The following code fragment is taken from the function ReadData of the accompanying example:

.. code-block:: aimms

    ! First line: number of cars; number of options; number of classes.
    pti::int(instanceFileHandle, nbCars);
    pti::int(instanceFileHandle, nbOptions);
    pti::int(instanceFileHandle, nbClasses);
    ! Second line: for each option, the maximum number of cars
    ! with that option in a block.
    for o do
        pti::int(instanceFileHandle,maxCarsPerBlock(o));
    endfor ;

As you can see from this example, the "int" procedure in the PlainTextInput library, with prefix "pti", is used to retrieve the integer values of the input sequentially. The AIMMS car sequencing application and the Visual Studio 2010 project that creates the PlainTextInput DLL are provided in the following zip file: [attachments include="4088"]Using Visual Studio you can build the DLL's. Using AIMMS 3.14 FR2 or later, either the 32 bits or 64 bits version, you can solve car sequencing problems using inputs specified according to the input format defined by CSPLIB. 

Further reading
---------------

* The AIMMS Language Reference documents how arguments can be passed via external functions in Chapter "External Procedures and Functions"
* The AIMMS Language Reference documents how AIMMS can be called back from within the C++ functions in Chapter "The AIMMS Programming Interface", also allowing sparse data exchange.
* M. Dincbas, H. Simonis, and P. van Hentenryck. Solving the car-sequencing problem in constraint logic programming. In Y. Kodratoff, editor, Proceedings ECAI-88, pp. 290–295, 1988


.. include:: ../../includes/form.def


