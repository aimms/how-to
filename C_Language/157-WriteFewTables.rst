:orphan:

How to write a few database tables
==================================

.. meta::
   :description: The combo of Datachange monitors and runtime libraries allows to make a selection of database tables that actually need rewriting
   :keywords: database table, odbc, runtime library, model editing, AIMMS Language, execution efficiency

.. sidebar:: Don't write on these tables. Thanks.

    .. figure:: ../Resources/C_Language/Images/157/Billiard_hall.JPG
    
            Author: Dmitry G.

   
Writing to databases is tuned for performance. Even when writing just a few rows, there is some time needed to setup a connection, actually write and commit the transaction to the database. Applications keep acquiring functionality, and subsequently the number of database tables in large applications may become significant. Having worked on an application with more than 100 database tables, where saving the data often consisted of writing the same data for most of the tables, we sought a solution whereby the writing of database tables is skipped for those tables where the data is not changed. 

Background: DatachangeMonitors
-----------------------------------------------------

Datachange monitors track whether or not the data of a selection of identifiers was changed, since the last time checked. So what is a datachange monitor?

A datachange monitor consists of three components:

#. A name - for sake of identification.

#. A reference to an AIMMS set - by having a reference, a data change monitor can even monitor dynamic subsets of AllIdentifiers (yes there are use cases of this feature).

#. An internal component that maintains for each identifier and the referenced set the number of assignments since the last reset.

The AIMMS function reference describes the procedures operating on datachange monitors in detail. In short:

* ``DataChangeMonitorHasChanged`` - returns 1 if the data of at least one identifier, or the data of the reference set itself, has changed.

* ``DataChangeMonitorCreate`` - create a new datachange monitor name and resets

* ``DataChangeMonitorReset`` - reset a datachange monitor and links it to the same or other reference set

* ``DataChangeMonitorDelete`` - allows for cleanup!

Runtime library needed
------------------------

Each database table is monitored separately, which means we want a separate monitor for each table. Because each monitor has a reference to a set, instead of just the value of a set, we need to resort to runtime libraries.




The enclosed example shows how to do this.

*  :download:`AIMMS project <../Resources/C_Language/Images/157/WriteOnlyAFewDatabaseTables.zip>` 


Summary
-------

The combo of Datachange monitors and runtime libraries allows to make a selection of database tables that actually need rewriting.

.. include:: ../includes/form.def






















