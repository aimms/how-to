Libraries Initialization and Termination
============================================

When an AIMMS application is launched, the initialization procedures are executed before all else. 
Likewise, when the app is shut down the termination procedures are executed. 

.. seealso::

    * `Model Initialization and Termination <https://documentation.aimms.com/language-reference/data-communication-components/data-initialization-verification-and-control/model-initialization-and-termination.html#sec-data-init>`_

If you want some action to happen each time you open the app, such as reading data from a database, 
you can integrate this functionality into the one of the AIMMS initialization routines. 
A thoughtful model builder, before coding, may consider the questions:

#.  But which AIMMS initialization procedure (*design*)?  

#.  And what are the consequences of placing such a call in an initialization procedure (*evaluate*)?

#.  And does it make sense to place a data read procedure in the initialization (*evaluate*)?

The purpose of this article to give you a better understanding of the initialization and termination of an AIMMS app.
With this understanding, you can provide an informed answer to the above questions and to the questions below, 
in the context of the app being developed:

#.  Which are the initialization procedures available to an application (*knowledge*)? 

#.  When can you rely on a library to be initialized (*understanding*)?

#.  Does the order in which libraries are declared matter to the initialization and termination of an application (*understanding*)?

#.  Do initialization and termination depend on the way the application is deployed (*knowledge*)?

#.  How to stop a running application (*knowledge*)?

In this article, the above questions are not answered directly. Please use the project below to follow this article's explanations.

    :download:`InitTerm <model/InitTerm.zip>`

The ``pr_visit`` Procedure
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The purpose of the procedure ``pr_visit`` is to mark the visiting procedures. The procedure ``pr_visit`` has the following simple code:

.. code-block:: aimms
    :linenos:

    p_visits += 1 ;
    sp_node := callerNode(1);
    sp_line := formatString("Visit %2i from: %s", p_visits, sp_node);
    pr_log(sp_line);

whereby the procedure ``pr_log`` outputs the line either into the session log or to a local file, depending on how the app is called.
 
Subsequently, a call to ``pr_visit`` is placed at every procedure that might be involved in the initialization or termination.

Running the ``InitTerm`` Application
-------------------------------------

To test, please open the application, click on "solve" button and close AIMMS. The ``visit.txt`` file will be:

.. code-block:: none
    :linenos:
    :emphasize-lines: 12

    Visit  1 from: MainInitialization
    Visit  2 from: a::LibraryInitialization
    Visit  3 from: b::LibraryInitialization
    Visit  4 from: c::LibraryInitialization
    Visit  5 from: d::LibraryInitialization
    Visit  6 from: PostMainInitialization
    Visit  7 from: a::PostLibraryInitialization
    Visit  8 from: b::PostLibraryInitialization
    Visit  9 from: c::PostLibraryInitialization
    Visit 10 from: d::PostLibraryInitialization
    Visit 11 from: pr_startup
    Visit 12 from: pr_openPageWebUI
    Visit 13 from: pr_solve
    Visit 14 from: d::PreLibraryTermination
    Visit 15 from: c::PreLibraryTermination
    Visit 16 from: b::PreLibraryTermination
    Visit 17 from: a::PreLibraryTermination
    Visit 18 from: PreMainTermination
    Visit 19 from: d::LibraryTermination
    Visit 20 from: c::LibraryTermination
    Visit 21 from: b::LibraryTermination
    Visit 22 from: a::LibraryTermination
    Visit 23 from: MainTermination

There are several remarks to be made about this log:

#.  The order in which the libraries are initialized is top to bottom, and in which they are terminated is bottom to top.

    .. tip::

        Consequently, it is practical to put the system libraries ``AimmsProLibrary`` and ``AimmsWebUI`` at the top of the library list. 
        This permits other libraries to use the functionalities of these libraries even in their ``LibraryInitialization`` and ``LibraryTermination`` procedures.
        
#.  On line 13 the procedure ``pr_solve`` is visited.  
#.  Once closed the application, AIMMS initiates the shutdown of the session (lines 14-23).

.. note::

    If there is a number jump just before ``PostMainInitialization``, this implies that a startup case is loaded when opening AIMMS.

Deployment Dependent Actions Taken 
------------------------------------

The following lists which actions are taken by application start or application finish:

+----------------------------+-----------+-------+--------+----------+----------+
|                            | Developer | WebUI | Solver | AimmsCmd | runonly  |
+============================+===========+=======+========+==========+==========+
| MainInitialization         |  +        |  +    |    +   |    +     |    +     |
+----------------------------+-----------+-------+--------+----------+----------+
| LibraryInitialization      |  +        |  +    |    +   |    +     |    +     |
+----------------------------+-----------+-------+--------+----------+----------+
| Load of startup case       |  +        |  +    |    +   |    +     |          |
+----------------------------+-----------+-------+--------+----------+----------+
| PostMainInitialization     |  +        |  +    |    +   |    +     |    +     |
+----------------------------+-----------+-------+--------+----------+----------+
| PostLibraryInitialization  |  +        |  +    |    +   |    +     |    +     |
+----------------------------+-----------+-------+--------+----------+----------+
| Startup procedure          |  +        |  +    |    +   |    +     |          |
+----------------------------+-----------+-------+--------+----------+----------+
| Page open  of WebUI page   |           |  +    |        |          |          |
+----------------------------+-----------+-------+--------+----------+----------+
| Page close of WebUI page   |           |  +    |        |          |          |
+----------------------------+-----------+-------+--------+----------+----------+
| Read input case            |           |       |    +   |          |          |
+----------------------------+-----------+-------+--------+----------+----------+
| Write output case          |           |       |    +   |          |          |
+----------------------------+-----------+-------+--------+----------+----------+
| PreLibraryTermination      |  +        |  +    |    +   |    +     |    +     |
+----------------------------+-----------+-------+--------+----------+----------+
| PreMainTermination         |  +        |  +    |    +   |    +     |    +     |
+----------------------------+-----------+-------+--------+----------+----------+
| LibraryTermination         |  +        |  +    |    +   |    +     |    +     |
+----------------------------+-----------+-------+--------+----------+----------+
| MainTermination            |  +        |  +    |    +   |    +     |    +     |
+----------------------------+-----------+-------+--------+----------+----------+

The columns of the above table are:

#.  AIMMS Developer: the actions taken by AIMMS Developer by opening and closing the ``InitTerm`` application.

#.  WebUI: the actions taken by a WebUI application published on AIMMS Cloud by opening and closing the ``InitTerm`` application.

    .. tip:: Many users appreciate a quick start of an application as this will as this will put them at ease knowing that the system is up and running.
             In addition, they appreciate progress information during a long running data read procedure, perhaps via 
             `webui::SetProgressMessage <https://documentation.aimms.com/webui/library.html#setprogressmessage>`_.
             Please consider placing long running data read procedures as an explicit action to be invoked by the user.

    .. note:: A published WebUI app can be closed in two ways:

        #.  By running the procedure ``pro::sessionmanager::FinishSession()``.

        #.  By closing all browser tabs, and waiting a minute.

#.  Solver: A solver session submitted by ``pro::delegateToServer``.

    #.  The input case is the case constructed by the data session (WinUI or WebUI) to provide input to the solver session.

    #.  The output case is the case constructed by the solver session to pass the results to the data session.

    .. tip:: It may be redundant to read in data in an initialization procedure of a solver session when that data is also provided in the input case.

#.  AimmsCmd: Running the ``InitTerm`` application via AimmsCmd and just opening and closing it.

#.  runonly: Running ``aimms.exe`` with the --run-only command line argument (using AIMMS 4.87.4 or newer).

.. seealso::

    * `Model Initialization and Termination <https://documentation.aimms.com/language-reference/data-communication-components/data-initialization-verification-and-control/model-initialization-and-termination.html#sec-data-init>`_
    * :doc:`miscellaneous/calling-aimms/aimms-command-line-options`

.. spelling:word-list::

    runonly