Automatic Benders’ decomposition
=================================

.. meta::
   :description: How to solve using Benders Decomposition directly supported by AIMMS.
   :keywords: Benders Decomposition, MIP, GMP

.. note::

	This article was originally posted to the AIMMS Tech Blog.

.. <link>https://berthier.design/aimmsbackuptech/2013/01/08/benders-decomposition-aimms-beta-version-available/</link>
.. <pubDate>Tue, 08 Jan 2013 13:52:32 +0000</pubDate>
.. <dc:creator><![CDATA[]]></dc:creator>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=2497</guid>


`Benders’ decomposition <https://en.wikipedia.org/wiki/Benders%27_decomposition>`_ is an approach to solve complicated mathematical programming problems by splitting them into two, and thereby simplifying the solution process by (repeatedly) solving one master problem and one subproblem. If the problem contains integer variables then typically they become part of the master problem while the continuous variables become part of the subproblem. The classic approach of the Benders’ decomposition algorithm solves an alternating sequence of master problems and subproblems. Benders’ decomposition is mostly used for solving difficult MIP problems and stochastic programming problems.

We have developed a generic Benders’ decomposition module in AIMMS that is easy to use. 

The Benders’ decomposition module in AIMMS is implemented as an open
algorithm using GMP functionality. It offers many features. Besides the
classic approach the module contains a modern variant that solves one
master (MIP) problem using solver callbacks. (See also Paul Rubin's blog post `Benders Decomposition Then and Now <https://orinanobworld.blogspot.com/2011/10/benders-decomposition-then-and-now.html>`_.)

To use Benders' decomposition, first install the system module
``GMPBendersDecomposition``. 

To solve a MIP problem
using the classic approach you can use the following:

.. code-block:: aimms
    :linenos:

    ! First we must generate the GMP for our Math Program.
    myGMP := GMP::Instance::Generate( myMP ) ;

    ! The second argument defines the master problem variables.
    GMPBenders::DoBendersDecomposition( myGMP, AllIntegerVariables, 'Classic' );

The third argument (``'Classic'``) of the above procedure specifies the algorithm that
will be used. Other possible values are ``'Modern'``, ``'TwoPhaseClassic'`` and
``'TwoPhaseModern'``. The two-phase algorithm solves a relaxed problem in
the first phase and the original MIP problem in the second phase (using
either the classic or modern approach).

The current implementation of Benders' decomposition in AIMMS has some
limitations. It cannot be used to solve nonlinear problems. Also, the
current implementation does not support multiple subproblems which could
be efficient in case the subproblem has a block diagonal structure. This
implies that the current implementation cannot be used to solve (two
stage) stochastic programming problems with a subproblem for each
scenario.


CPLEX nowadays provides its own Benders Decomposition. See :doc:`../116/116-Benders-CPLEX`



