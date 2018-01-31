How do I use this, man ??
==============================

1/ Install Python 2.7, because Python is life
--------------------------------------------------

1. Double click the ``python-2.7.14.msi`` file In the folder **Documentation Tools** , 
2. Python 2.7 will be installed in ``C:\Python27``, because Python is life
3. Install the Sphinx package (+ all depedencies) using the command "``python -m pip install sphinx``" from the ``C:\Python27\Scripts`` path (using a command prompt, we are a software company)

.. code-block:: c
   :emphasize-lines: 3,5
   
	#include <windows.h>
	#include <math.h>
	#define DLL_EXPORT(type) __declspec(dllexport) type WINAPI
	#define DLL_EXPORT_PROTO(type) extern "C" __declspec(dllexport) type WINAPI

	DLL_EXPORT_PROTO(double) Square(double a, double b, double c, double x)
	{
		double Result ;
		Result = a*x*x + b*x + c;
		return  Result;
	}

.. warning::

	Chris, isn't it wonderful ?
	
.. seealso::

	* well, this http://example.com/

.. note::

		You can also develop your own color box of course, with css... once again, but now we are prepared :)