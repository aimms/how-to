Create Local Solver Configuration
===============================================

.. meta::
   :description: Changing solver configuration per project
   :keywords: Solver, configuration, Project

.. note::

    This article was originally posted to the AIMMS Tech Blog.


.. <link>https://berthier.design/aimmsbackuptech/2013/03/20/using-project-local-solver-configuration/</link>
.. <pubDate>Wed, 20 Mar 2013 14:09:30 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1578</guid>

.. sidebar:: Preferences

    .. image:: images/preferences.png

..  <![CDATA[<img src="http://techblog.aimms.com/wp-content/uploads/sites/5/2013/03/preferences.png" alt="preferences" width="128" height="128" class="alignright size-full wp-image-2755" />In an earlier post <a href="http://blog.aimms.com/2012/08/change-default-solver-used-for-each-type-of-mathematical-program/" title="Change default solver used for each type of mathematical program">Change default solver used for each type of mathematical program</a> 
               


Normally, the list of solvers available via the solver configuration
screen is the same for all projects opened with the same version of
AIMMS. In this article, we'll see how to overrule the solvers that
are available for your current project.

You can change the default solver for each type of mathematical program via the
solver configuration screen (accessible from the *Settings*
menu). You can temporarily overrule this default
by modifying the ``CurrentSolver`` element
parameter or with a ``where`` clause with the solve statement.
Read more details about how to do so in the article :doc:`../178/178-change-default-solver`. 

You can overrule the default list of solvers for a specific project by
providing a plain text file named ``solvers.slv``.

An example of what it may contain is the following:

.. code-block:: aimms
    :linenos:

    ! Format AIMMS380
    ! solver name     /  dll name               /  model types for which solver is the default
    CPLEX 10.1        /  libcpx101.dll          / 
    CPLEX 12.4        /  libcpx124.dll          / 
    CPLEX 12.5        /  libcpx125.dll          /  LP MIP QP MIQP QCP MIQCP

Any line starting with a ! is considered a comment line. The different
columns give the name of the solver, the DLL to use, and for which type
of mathematical programs this solver is the default solver. If you save
the above into a file named ``solvers.slv`` in your project directory, the
next time you start this project you will only see the three CPLEX
solvers in the solver configuration screen.

Alternatively, open the *solver configuration* screen and press the
*export* button to create a ``solvers.slv`` file in your current project
directory. A notification appears when the solver
configuration has been successfully exported to a local solver
configuration file.

This local solver configuration ensures that your project always uses a specific version of a solver,
regardless of whether your end user has this solver version in their
solver configuration.

If a ``solvers.slv`` file is present in your project directory, any changes
you make in the solver configuration screen is saved in this ``solvers.slv``
file and not in the global configuration for your current AIMMS version.

Requirements
--------------
The end user must have a license including rights to use this solver, and the solver DLL must be installed. 

