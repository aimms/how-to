Secure File Access
====================

.. meta::
   :description: Modeling for secure apps deployed on AIMMS Cloud. Securing file access in AIMMS PRO Storage.
   :keywords: secure, storage, access

In this article, we will cover granting and denying access to files in AIMMS PRO Storage.

AIMMS PRO Storage organizes access per entity, whereby an entity is a user or a group of users. 
We start with retrieving the set of entities and the group or user names associated with them. 
This can be done via the following call to system library AimmsProGUI:

.. code-block:: aimms

    guipro::PopulateEntitySets( addUniversalSets:1 ); 

This call fills the set ``guipro::PROEntity`` and the string parameters ``guipro::PROUserName`` and ``guipro::PROGroupName.``

AIMMS PRO Storage regulates file access per entity by assigning authorization strings to files and folders. Here an entity is a user or a group.
An authorization string is a sequence of atomic authorization strings. An atomic authorization strings has the following format:

``"#%i%s%e"`` whereby:

* The ``%i`` is an integer code for the access type, which is a combination (addition) of the following values:

    * 1 Execution access for objects (files/apps) and browse access for buckets (folders)

    * 2 Write access

    * 4 Read access

* The ``%s`` is either a ``"+"`` to indicate that access is allowed, or a ``"-"`` to indicate that access is denied. An access denial overrules all access granted.

* The %e is an element in the set ``guipro::PROEntity``, see above.

For example, on my AIMMS Cloud the group 'planners' correspond to entity '1408', 
and my friend Theo corresponds to entity '1792'. 
Thus, to allow read/write access to Theo and read access to a planner for the file at hand, I use the following authorization string: ``"#4+1408" + "#6+1792"``. 
To give Theo and the planners access to the ``data.txt`` input file, I should copy it as follows to AIMMS PRO Storage:

.. code-block:: aimms

    pro::SaveFileToCentralStorage("c:\\Inputs\\data.txt", "pro:/publicdata/myapp/input/data.txt", "#4+1408" + "#6+1792" );

Note that when the third optional argument is not filled in, the file copied gets the access rights of the folder it is put in.

In the AIMMS PRO UI Library, there are also helper functions to parse and build authorization strings, 
called ``DeconstructAuthorizationString`` and ``ConstructAuthorizationString`` respectively. 
These two functions are easy to use front-ends for the above atomic operations.


.. seealso:: 

    * :doc:`../120/120-pro-user-groups`: covers authorization of AIMMS Cloud users. 
    * :doc:`../117/117-Uploading-and-Downloading-files`: covers how to transfer files.
    * `WebUI Library <https://documentation.aimms.com/webui/>`_
    * `AIMMS PRO On-Premise <https://documentation.aimms.com/pro/index.html#pro-platform>`_