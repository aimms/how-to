.. include:: includes/icons.def

How to retrieve intermediate results from a server session to the data session?
===============================================================================

Introduction
------------

During a long running solver session (job), we may compute intermediate results that we want to show to the end user as soon as they are available, for the following use cases:

#. Actually, the job submitted contains multiple decision problems, all of which need to be solved in one batch. Why wait for providing the solution of the first decision problem, while the job is already working on the second decision problem?

#. The optimization of a significant Mixed Integer Problem will compute several intermediate incumbents, and perhaps these incumbents are worth visualizing and further study.

#. By showing intermediate solutions, the end user may decide that the last shown solution suffices and the search in the job can be stopped.

The AIMMS Pro system allows the solver session to call back to the data session to pass intermediate results, as presented in `How to display solve progress info in WebUI  <https://how-to.aimms.com/ProgressWindowServerSession.html>`_. However, there is a limit on the amount of data to be passed back in one call. Unlike passing progress information, we typically like to retain these intermediate results. Therefore we have the question: 
How to retrieve intermediate results from a server session to the data session?

Approach

In this answer, we will discuss the above question in such a way that the end user can manage the number of intermediate solutions available.

Let's start with an example that is being used elsewhere in `How To <https://how-to.aimms.com>`_  as well.

.. warning:: **Todo** URL for end result "remove veil" - "share progress info" needs to be inserted here.

A copy of the flowshop model that resulted from that answer is used as the starting example for this answer: :download:`Flow Shop - before <Resources/AIMMSPRO/RetreiveIntermediateResults/Downloads/Flow Shop - before.zip>`.

.. comment:: Flowshop model - before sharing intermediate results with data session.
.. comment:: Flow Shop - share intermediate - after
.. comment:: Flow Shop - share intermediate - before


We follow here more or less the flow as the "progress" answer:

Summary
-------

By providing the intermediate answers via AIMMS cases, we can retain those answers and allow the end user to select the ones he actually wants to view.






