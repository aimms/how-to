Using project local solver configuration=============================================.. meta::   :description: Changing solver configuration per project   :keywords: Solver configuration, Project, Solver selection.. note::    This article was originally posted to the AIMMS Tech Blog.
.. <link>https://berthier.design/aimmsbackuptech/2013/03/20/using-project-local-solver-configuration/</link>
.. <pubDate>Wed, 20 Mar 2013 14:09:30 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1578</guid>.. sidebar:: Preferences    .. image:: images/preferences.png
..  <![CDATA[<img src="http://techblog.aimms.com/wp-content/uploads/sites/5/2013/03/preferences.png" alt="preferences" width="128" height="128" class="alignright size-full wp-image-2755" />In an earlier post <a href="http://blog.aimms.com/2012/08/change-default-solver-used-for-each-type-of-mathematical-program/" title="Change default solver used for each type of mathematical program">Change default solver used for each type of mathematical program</a>                :doc:`In an earlier post <../178/178-change-default-solver>`, I showed how you can modify the default solver that AIMMS will use for solving each type of mathematical program.
I did not only demonstrate how you can modify these defaults via the
solver configuration screen (which you can access via the Settings
menu), but also showed how you could temporarily overrule this default
by modifying the contents of the predefined CurrentSolver element
parameter or by using a where clause with the solve statement.
Normally, the list of solvers available via the solver configuration
screen is the same for all projects opened with the same version of
AIMMS. In this post, I will show how you can overrule the solvers that
are available for your current project.

You can overrule the default list of solvers for a specific project by
providing a solvers.slv file. This file is just a plain text file and
some example content is the following:.. code-block:: aimms    :linenos:
    ! Format AIMMS380
    ! solver name     /  dll name               /  model types for which solver is the default
    CPLEX 10.1        /  libcpx101.dll          / 
    CPLEX 12.4        /  libcpx124.dll          / 
    CPLEX 12.5        /  libcpx125.dll          /  LP MIP QP MIQP QCP MIQCP
Any line starting with a ! is considered a comment line. The different
columns give the name of the solver, the DLL to use, and for which type
of mathematical programs this solver is the default solver. If you save
the above into a file named solvers.slv in your project directory, the
next time you start this project you will only see the three CPLEX
solvers in the solver configuration screen.
Another way of creating a solvers.slv file in your current project
directory is by opening the solver configuration screen and press the
export button. This should give you a notification that the solver
configuration has been successfully exported to a local solver
configuration file.
The advantage of using this local solver configuration is that you can
ensure that your project is always using a specific version of a solver,
regardless whether your end-user has this solver version in his/her
solver configuration.
If a ``solvers.slv`` file is present in your project directory, any changes
you make in the solver configuration screen is saved in this solvers.slv
file and not in the global configuration for your current AIMMS version.
Note that the license of the end-user will have to still allow the usage
of this solver. Furthermore, the specific solver DLL must also be
present in the installation of the end-user. 
