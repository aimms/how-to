Investigate state server session
===================================

.. meta::
   :description: The state of the server session may be unexpected, and up for inspection
   :keywords: data, state, server session

As an end user you cannot directly inspect the data used by the data session or by the server session. 
As a model builder, you may need to investigate in detail what happens during these sessions.
One way to doing is this by creating a data snapshot.
You can do this by creating case files, or by creating text files. In this article, we detail how this can be done using text files.

Preparation
-----------

The project used in this example is shared with the companion article :doc:`companion article on investigation behavior AIMMS PRO job <../310/310-investigate-behavior-pro-job>`. It can be downloaded :download:`here <../310/model/FlowShop.zip>`.

This project contains the library ``Library GuardServerSession``. 
Please copy this library from the sample project and then add it to your project using the AIMMS Library Manager.

Usage
-----

The library  ``Library GuardServerSession``, provides, amongst others, the procedure ``gss::pr_SaveState`` with two arguments:

#.  ``sp_Label`` The value of this string parameter is used as the folder name; this folder name is referred to as the output folder below.

#.  ``s_SelectedIdentifiers`` This is a subset of ``AllIdentifiers``. For each identifier that contains data, a corresponding file is created in the output folder, containing one element per line.

.. note:: If your app is running on an AIMMS PRO platform, or if it has a connection to an AIMMS PRO platform, the files created will also be copied to AIMMS PRO storage, as a subfolder of ``/Userdata/<env>/<userName>/<appName>/<appVersion>``.

An example call is:

.. code-block:: aimms
    :linenos:

    gss::pr_SaveState(
        sp_Label              :  "ThirdSaveLabel", 
        s_SelectedIdentifiers :  Superfluous_Data);

Further reading
----------------

See also: 

#.  :doc:`PRO Storage folder operations <../323/323-pro-storage-folder>`

#.  :doc:`Solver status files from AIMMS PRO <../13/13-Solver-Logging-PRO>`











