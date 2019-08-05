Create an External Function with Visual Studio
================================================

.. meta::
   :description: Example using Haversine to create an external function with Microsoft Visual Studio.
   :keywords: external, function, visual, studio, dll

.. .. image:: images/icons8-puzzle-512.png

`Haversine code <https://rosettacode.org/wiki/Haversine_formula>`_ in various computer languages is publicly available to compute the distance between locations.
In this article, we use it as an illustration of how to create an external function using `Microsoft Visual Studio <https://visualstudio.microsoft.com/>`_.

Creating a Visual Studio Project
--------------------------------

A Visual Studio Project facilitates incremental building of software components in various computer languages. 
Here we just use it to create a ``.dll`` containing external functions for Haversine.  


.. image:: images/01NewProjectDLL.PNG
   :align: center
   :scale: 70

After opening Visual Studio, in order:

#. New Project  - To create a new project

#. Visual Studio C++ - Specializing to project to C++

#. A Win32 - Further specializing the project to Win32 (which covers both 32 and 64 bit executables)

#. A generic Win32 project (not a console)

#. Specify destination folders
    
#. Make sure we can change further settings

    .. image:: images/02NewProjectDLL.PNG
      :align: center

#. We want a DLL

    .. image:: images/03NewProjectDLL.PNG
      :align: center

#. The default configuration is not correct

    .. image:: images/04NewProjectDLL.PNG
      :align: center

#. We want a new configuration with 64-bit

    .. image:: images/06NewProjectDLL.PNG
      :align: center

#. You may want to turn off precompiled headers:

    #. Select your project, use the "Project -> Properties" menu and 
    
    #. go to the "Configuration Properties -> C/C++ -> Precompiled Headers" section, 
    
    #. then change the "Precompiled Header" setting to "Not Using Precompiled Headers" option.
    
Coding the function
-------------------

.. code-block:: cpp
    :linenos:

    // HaversineDLL.cpp : Implements the exported Haversine function for the DLL application.

    #ifdef _MSC_VER
    #define WIN32_LEAN_AND_MEAN
    #include <windows.h>
    #endif 
    #define _USE_MATH_DEFINES
    #include <math.h>

    #ifdef __cplusplus
    #define DLL_EXPORT_PROTO(type) extern "C" __declspec(dllexport) type WINAPI
    #else
    #define DLL_EXPORT_PROTO(type) extern __declspec(dllexport) type WINAPI
    #endif

    DLL_EXPORT_PROTO(double) Haversine(double lat1, double lon1, double lat2, double lon2);

    static double toRadians(double angle)
    {
        return M_PI * angle / 180.0;
    }
    DLL_EXPORT_PROTO(double) Haversine(double lat1, double lon1, double lat2, double lon2)
    {
        double R = 6372.8; // In kilometers
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);
        lat1 = toRadians(lat1);
        lat2 = toRadians(lat2);

        double a = sin(dLat / 2) * sin(dLat / 2) + sin(dLon / 2) * sin(dLon / 2) * cos(lat1) * cos(lat2);
        double c = 2 * asin(sqrt(a));
        return R * 2 * asin(sqrt(a));
    }


* Line 18-21: Helper function, convert angle from degrees to radians.

* Line 22: Function declaration, use C calling convention

* Line 24 - 32: based on `Haversine code <https://rosettacode.org/wiki/Haversine_formula>`_

Building using Visual Studio
----------------------------

#. Select release

#. Select x64

#. Press Build

Verifying the build
-------------------------------------------------

We need to verify that the .dll's built are valid 32 bit and 64 bit .dll's. 
To do this, we use a nifty free of charge utility named depends.exe.  You may download the x86 and x64 from this `website <http://www.dependencywalker.com/>`_.
Installing is just unzipping. Then start the executable and browse the .dll

#. ``depends22_x86\depends.exe`` for the **32 bit** dll: ``<HaversineDLL>\release\HaversineDLL.dll``.  

    .. image:: images/32BitsDependsCheck.PNG
      :align: center

    ..

    * Missing functions in MSVCR120.dll may be reported, but those are covered when starting AIMMS. 
    
    * Important are the functions exported, as highlighted in the red rectangle; at least Haversine needs to be there.  32 bits Win32 will prefix using an ``_`` and postfix using the @ sign and the number of bytes passed over the stack.  The Haversine functions passes four doubles, so the postfix @32 is to be expected.

    
#. ``depends22_x64\depends.exe`` for the **64 bit** dll: ``<HaversineDLL>\x64\release\HaversineDLL.dll``.  

    .. image:: images/64BitsDependsCheck.PNG
      :align: center
    ..

    * Missing functions in MSVCR120.dll may be reported, but those are covered when starting AIMMS. 
    
    * Important are the functions exported, as highlighted in the red rectangle; at least Haversine needs to be there.  64 bits Win32 will not use prefix, nor postfix.
    

Testing the DLL's
-------------------

Create a separate AIMMS project just for testing.

#. Copy the dll's into the AIMMS project directory.

   * Copy the ``<HaversineDLL>\release\HaversineDLL.dll`` to the AIMMS project subfolder ``external\Windows\x86``

   * Copy the ``<HaversineDLL>\x64\release\HaversineDLL.dll`` to the AIMMS project subfolder ``external\Windows\x64``


#. Declare the external function in AIMMS.

   .. code-block:: aimms
      :linenos:

      ExternalFunction fnc_Haversine {
         Arguments: (latFrom,lonFrom,latTo,lonTo);
         DllName: "external\\Windows\\x64\\HaversineDLL.dll";
         ReturnType: double;
         BodyCall: Haversine(scalar: latFrom, scalar: lonFrom, scalar: latTo, scalar: lonTo);
         Parameter latFrom {
               Property: Input;
         }
         Parameter lonFrom {
               Property: Input;
         }
         Parameter latTo {
               Property: Input;
         }
         Parameter lonTo {
               Property: Input;
         }
      }

#. Test the external function in AIMMS.

   .. code-block:: aimms
      :linenos:

      Procedure MainExecution {
         Body: {
               p_DistNashvilleLosAngeles := fnc_Haversine(36.12, -86.67, 33.94, -118.40);
               
               p_dist1(i_slocFrom, i_slocTo) := fnc_Haversine( p_Latitude(i_slocFrom), p_Longitude(i_slocFrom), p_Latitude(i_slocTo), p_Longitude(i_slocTo) );
               
               display p_DistNashvilleLosAngeles ;
         }
      }

The resulted Listing file is as below with the expected value. 
            
.. code-block:: none

    p_DistNashvilleLosAngeles := 2887.260 ; 

Good performance; my desktop requires less than 0.3 seconds to fill a 274 X 274 distance matrix.

Downloads
------------

*  :download:`Visual Studio project <downloads/VSCPP.zip>` 

*  :download:`AIMMS project <downloads/CrowDistExternal.zip>` 





