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
+====================+==========+===============+
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
+====================+============+===============+
| AIMMS              | Yes        | AIMMS API     |
+--------------------+------------+---------------+
| .NET               | Indirectly | See FAQ below |
+--------------------+------------+---------------+
| Java               | Indirectly | See FAQ below |
+--------------------+------------+---------------+
| C++ (.dll)         | Yes        | AIMMS API     |
+--------------------+------------+---------------+
 

The following FAQs supplement the information in the table above:

Can I call AIMMS from .NET?
+++++++++++++++++++++++++++++++++++++++
Yes, our AIMMS PRO API has native bindings for .NET, so you can use those to make calls to AIMMS directly.

Can I call AIMMS from C++?
+++++++++++++++++++++++++++++++++++++++
Yes, you can use the AIMMS C++ API.

Can I call AIMMS from Java?
+++++++++++++++++++++++++++++++++++++++
Yes, our AIMMS PRO API has native bindings for Java, so you can use those to make calls to AIMMS directly.

Can I use my ``.NET`` component within AIMMS?
+++++++++++++++++++++++++++++++++++++++++++
No, but you could write your own C++ ``.dll`` that accesses your .NET component through the AIMMS PRO API.

Can I use my C++ ``.dll`` component within AIMMS?
+++++++++++++++++++++++++++++++++++++++++++++
Yes, if the exposed routines follow the AIMMS guidelines this is possible.

Can I use my Java component within AIMMS?
+++++++++++++++++++++++++++++++++++++++++++++++
No, but you could write your own C++ ``.dll`` that accesses your Java component through the AIMMS PRO API.