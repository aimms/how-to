Investigate state server session
===================================

.. meta::
   :description: The state of the server session may be unexpected, and up for inspection
   :keywords: data, state, server session

As an end user, you cannot directly inspect the data used by the data session or by the server session. 
As a model builder, you may need to investigate in detail what happens during these sessions.

One way is by creating a data snapshot by creating case files or text files. This article shows how to do this with text files.

Prerequisites
-------------

#. Download the example project: :download:`../310/model/FlowShop.zip`. (Also used in :doc:`../310/310-investigate-behavior-pro-job`.)

#. Copy the library ``Library GuardServerSession``.

#. Add the library to your project using the AIMMS Library Manager.

Using the library
------------------

The library  ``Library GuardServerSession``, includes the procedure ``gss::pr_SaveState`` with two arguments:

#.  ``sp_Label`` The value of this string parameter is used as the folder name; this folder name is referred to as the output folder below.

#.  ``s_SelectedIdentifiers`` This is a subset of ``AllSymbols``. For each identifier that contains data, a corresponding file is created in the output folder, containing one element per line.

.. note:: If your app is running on an AIMMS PRO platform, or if it has a connection to an AIMMS PRO platform, the files created will also be copied to AIMMS PRO storage, as a subfolder of ``/Userdata/<env>/<userName>/<appName>/<appVersion>``.

An example call is:

.. code-block:: aimms
    :linenos:

    gss::pr_SaveState(
        sp_Label              :  "ThirdSaveLabel", 
        s_SelectedIdentifiers :  Superfluous_Data);

Related Topics
----------------

* :doc:`../323/323-pro-storage-folder`
* :doc:`../310/310-investigate-behavior-pro-job`
* :doc:`../13/13-Solver-Logging-PRO`











