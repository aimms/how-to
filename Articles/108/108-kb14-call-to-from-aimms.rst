Overview: Linking to AIMMS
===================================
This article provides information on possible interaction of AIMMS with other applications.

There are two possible situations:

* Calling AIMMS from different languages/applications
* Calling different applications/languages from AIMMS

Calling AIMMS
-------------
To call AIMMS from different languages/applications, your options are as follows:

+--------------------+----------+---------------+
| Call AIMMS from .. | Possible | Method        |
+--------------------+----------+---------------+
| AIMMS              | Yes      | AIMMS API     |
+--------------------+----------+---------------+
| .NET               | Yes      | AIMMS PRO API |
+--------------------+----------+---------------+
| Java               | Yes      | AIMMS PRO API |
+--------------------+----------+---------------+
| C++ (.dll)         | Yes      | AIMMS API     |
+--------------------+----------+---------------+
 
Calling from AIMMS
---------------------
To call different applications/languages from AIMMS, your options are as follows: 

+--------------------+------------+---------------+
| Call .. from AIMMS | Possible   | Method        |
+--------------------+------------+---------------+
| AIMMS              | Yes        | AIMMS API     |
+--------------------+------------+---------------+
| .NET               | Indirectly | See FAQ below |
+--------------------+------------+---------------+
| Java               | Indirectly | See FAQ below |
+--------------------+------------+---------------+
| C++ (.dll)         | Yes        | AIMMS API     |
+--------------------+------------+---------------+
 
Linking FAQ
------------
The following FAQ lists may ease the interpretation of the table above:

Q: "Is it possible to call AIMMS from .NET ?"
A: "Yes, our AIMMS PRO API has native bindings for .NET, so you can use those to make calls to AIMMS directly."

Q: "Is it possible to call AIMMS from C++ ?"
A: "Yes, you can use the AIMMS C++ API."

Q: "Is it possible to call AIMMS from Java ?"
A: "Yes, our AIMMS PRO API has native bindings for Java, so you can use those to make calls to AIMMS directly."

Q: "Can I use my .NET component within AIMMS ?"
A: "No, but you could write your own C++ .dll that accesses your .NET component through the AIMMS PRO API."

Q: "Can I use my C++ dll component within AIMMS ?"
A: "Yes, if the exposed routines follow the AIMMS guidelines this is possible."

Q: "Can I use my Java component within AIMMS ?"
A: "No, but you could write your own C++ .dll that accesses your Java component through the AIMMS PRO API."