Securing File Access
====================

.. note:: Under Construction / Draft status - please do not hesitate to use the form at the end to ask for clarification where needed.

This blog post is the last in a series of thee to enable AIMMS app developers to model file sharing in a secure manner.  
In the :doc:`120-pro-user-groups`, we covered authorization of AIMMS PRO users, 
and in the :doc:`117-Uploading-and-Downloading-files`, we covered transferring files. 
In this blog post, we will cover granting and denying access to files in AIMMS PRO storage.

AIMMS PRO storage organizes access per entity, whereby an entity is a user or a group of users. 
We start with retrieving the set of entities and the group or user names associated with them. 
This can be done via the following call to system library AimmsProGUI:

.. code-block:: aimms

    guipro::PopulateEntitySets( addUniversalSets:1 ); 

This call will fill the set ``guipro::PROEntity`` and the string parameters ``guipro::PROUserName`` and ``guipro::PROGroupName.``

AIMMS PRO storage regulates file access per entity by assigning authorization strings to files and folders. Here an entity is a user or a group.
An authorization string is a sequence of atomic authorization strings. An atomic authorization strings has the following format:

``"#%i%s%e"`` whereby:

* The ``%i`` is an integer code for the access type, which is a combination(addition) of the following values:

    * 1 Execution access for objects(files/apps) and browse access for buckets(folders)

    * 2 Write access

    * 4 Read access

* The ``%s`` is either a ``"+"`` to indicate that access is allowed, or a ``"-"`` to indicate that access is denied. An access denial overrules all access granted.

* The %e is an element in the set ``guipro::PROEntity``, see above.

For example, on my AIMMS PRO system the group 'planners' correspond to entity '1408', 
and my friend Theo corresponds to entity '1792'. 
Thus, to allow read/write access to Theo and read access to a planner for the file at hand, I use the following authorization string: ``"#4+1408" + "#6+1792"``. 
To give Theo and the planners access to the data.txt input file, I should copy it as follows to AIMMS PRO storage:

.. code-block:: aimms

    pro::SaveFileToCentralStorage("c:\\Inputs\\data.txt", "pro:/publicdata/myapp/input/data.txt", "#4+1408" + "#6+1792" );

Note that when the third optional argument is not filled in, the file copied gets the access rights of the folder it is put in.

In the AIMMS PRO UI library, there are also helper functions to parse and build authorization strings, 
called ``DeconstructAuthorizationString`` and ``ConstructAuthorizationString`` respectively. 
These two functions are easy to use front-ends for the above atomic operations.

Summary:
--------

In this series of three blog posts we've shown:

* the point and click interface available to AIMMS PRO App developers and AIMMS PRO administrators to grant or deny access to AIMMS PRO users and the AIMMS PRO support to query this information, 

* the means available to transfer files between the user environment, the application running environment and the AIMMS PRO storage, and 

* the means available to secure file access in AIMMS PRO storage. 

Together, this allows for building AIMMS PRO apps that require secure file handling.

.. include:: ../includes/form.def
