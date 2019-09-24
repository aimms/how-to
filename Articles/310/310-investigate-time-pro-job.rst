:orphan:

Investigate the time spent by an AIMMS PRO job
==============================================

.. meta::
   :description: An AIMMS PRO job may spent more time than the corresponding procedure client side. Why? How to reduce?
   :keywords: profiling, AIMMS PRO, deployment, execution time, case management

A repeatedly asked question is "why does it take so long for my AIMMS PRO job?". 

As the AIMMS PRO job realizing the delegated procedure runs in a different process, potentially on a different host, the necessary actions taken by ``PRO::DelegateToServer`` are illustrated in the picture below:

.. image:: images/actions-delegate.png
    :align: center

#. Client side: Create a case file to be used as input

#. Copy the case file to the AIMMS PRO server

#. On the AIMMS PRO server, wait for a server license

#. On the AIMMS PRO server, compile and initialize the AIMMS Project

#. On the AIMMS PRO server, read the input case file

#. On the AIMMS PRO server, execute the procedure that invoked the pro::DelegateToServer

#. On the AIMMS PRO server, create a case file with the results

#. Client side: read the case file with results

In this article, we will detail for each of the above actions, how to investigate the time spent.



Client side: Create a case file to be used as input
----------------------------------------------------------

.. pro::ManagedSessionInputCaseIdentifierSet
.. pro::ManagedSessionOutputCaseIdentifierSet
.. pro::ManagedSessionRemoveFromCaseIdentifierSet

The identifiers of the input case created by ``pro::DelegateToServer`` is defined by ``pro::ManagedSessionInputCaseIdentifierSet``, minus the identifiers in ``pro::ManagedSessionRemoveFromCaseIdentifierSet``. However, the delegated job may only require the data of a subset of the identifiers in ``pro::ManagedSessionInputCaseIdentifierSet``.  To measure how long it takes to create an input case, please execute the procedure ``SaveInputCase``, coded below, with the AIMMS profiler on:

.. code-block:: aimms
    :linenos:

    Procedure SaveInputCase {
        Body: {
            s_InputCaseIdentifierSet := 
                pro::ManagedSessionInputCaseIdentifierSet - 
                pro::ManagedSessionRemoveFromCaseIdentifierSet ;
            CaseFileSave(
                url      :  "testcase.data", 
                contents :  s_InputCaseIdentifierSet);
        }
        Set s_InputCaseIdentifierSet {
            SubsetOf: AllIdentifiers;
        }
    }

Remarks about the above code:

#. ``pro::ManagedSessionInputCaseIdentifierSet`` is initialized to ``AllIdentifiers``

#. ``pro::ManagedSessionRemoveFromCaseIdentifierSet`` is initialized to ``AllDefinedParameters``

#. Thus, by default defined parameters are not transferred, but defined sets are. Evaluating the definitions of these sets may still take significant time. If so, the AIMMS profiler will identify the sets that take significant time. Some of these sets can be safely added to ``pro::ManagedSessionRemoveFromCaseIdentifierSet``. For instance, those with dimension 2 or higher, as illustrated by adding the following assignment to ``PostMainInitialization``:

    .. code-block:: aimms
        :linenos:

        pro::ManagedSessionRemoveFromCaseIdentifierSet := AllDefinedParameters + 
                    { indexIdentifiers | indexIdentifiers in AllDefinedSets and 
                        IdentifierDimension(indexIdentifiers) >= 2 };


#. Please see :doc:`reduce client server exchange<../reduce-client-server-exchange/reduce-client-server-exchange>` on tips for assigning ``pro::ManagedSessionInputCaseIdentifierSet`` to just those that are relevant for the job at hand.

.. note:: *Software evolution*: ``pro::ManagedSessionRemoveFromCaseIdentifierSet`` is applied to reduce the set of identifiers transferred since AIMMS 4.59.2. 

Copy the case file to the AIMMS PRO server
----------------------------------------------------------

To transfer items, transfer speed and item size do matter. To reduce the item size is discussed in the previous section. The transfer speed depends on the connection and distance. Clearly when the client and server session are executed on the same host, or the hosts are in the same domain, then transfer speed is high. On the other hand, when those hosts are on different continents, then the transfer speed may very well be low.

To determine the time, obtain the session.log file (een guid name bah) for the server session and search for "duration":


On the AIMMS PRO server, wait for a server license
---------------------------------------------------

On the AIMMS PRO server, compile and initialize the AIMMS Project
-----------------------------------------------------------------------------------

On the AIMMS PRO server, read the input case file
----------------------------------------------------------------------

On the AIMMS PRO server, execute the procedure that invoked the pro::DelegateToServer
-----------------------------------------------------------------------------------------------

On the AIMMS PRO server, create a case file with the results
----------------------------------------------------------------------------

Client side: read the case file with results
-----------------------------------------------------------


This is why I have the following questions to you:

Q1. How much time is spent creating the case input file and how big is it?
    You can check this manually, by executing the procedure CaseFileSave with the AIMMS  Profiler on.
    If the input case is large, and/or takes significant time to create; please consider specifying pro::ManagedSessionInputCaseIdentifierSet before calling pro::DelegateToServer.
    https://how-to.aimms.com/Articles/reduce-client-server-exchange/reduce-client-server-exchange.html provides an example.
   
Q2. How much time is spent during the initialization of the project; are the actions specified in MainInitialization and PostMainInitialization also necessary to be executed server side?

Q3. How long do each of the server side actions take?
    Note 1: that by specifying pro::ManagedSessionOutputCaseIdentifierSet server side, you may be able to reduce the case created server side.
    Note 2: ProfilerCollectAllData, available since 4.68, allows you to collect profiler data server side. 

