Monitor Identifiers for Changes
===================================

.. meta::
   :description: Tracking whether or not an identifier in a collection has changed.
   :keywords: monitor, track, identifier, change

.. note::

    This article was originally posted to the AIMMS Tech Blog.

.. <link>https://berthier.design/aimmsbackuptech/2013/04/10/monitoring-identifiers-for-changes/</link>
.. <pubDate>Wed, 10 Apr 2013 09:06:23 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=2829</guid>

.. sidebar:: Monitor

    .. image:: images/monitor.jpg
    
By default, AIMMS asks you whether you want to save changes to data when you close your project. This behavior depends on which data categories and case type are currently active.

You can also use the data change monitor functions to check when there are changes in a given subset of identifiers in your model since the last time you checked, based on a data category and case type.

Functions of data change monitor
--------------------------------

The data change monitor has the following intrinsic functions:

* ``DataChangeMonitorCreate``: Create a new monitor and get a reference to it.

* ``DataChangeMonitorDelete``: Delete an existing monitor by its reference.

* ``DataChangeMonitorReset``: Reset a monitor indicated by a reference.

* ``DataChangeMonitorHasChanged``: Check whether values have changed.

Creating data change monitor
----------------------------
To create a new data change monitor, first provide it an ID and a subset of the predefined set ``AllIdentifiers`` containing the identifiers you want to monitor for changes. 

In the example code below, ``s_WatchedIdentifiers`` is a subset of ``AllIdentifiers`` and ``sp_DataChangeMonitorID`` is a string parameter.

.. code-block:: aimms
    :linenos:

    !The identifiers we are interested in checking for changes
    s_WatchedIdentifiers := data { 'p_myParameter1' , 'p_myParameter2' , 
                            'p_myDefinedParameter' } ; 
 
    !Create a name that will be the ID for this data change monitor
    sp_DataChangeMonitorID := "Monitor parameters" ; 

Then, use the intrinsic function ``DataChangeMonitorCreate`` as shown below:

.. code-block:: aimms
    :linenos:

    !Create the actual Data Change Monitor with the ID denoted
    !by the string parameter DataChangeMonitorID and monitor the
    !changes of identifiers present in the set WatchedIdentifiers
    DataChangeMonitorCreate(
        ID                   : sp_DataChangeMonitorID  , 
        monitoredIdentifiers : s_WatchedIdentifiers , 
        ExcludeNoneSaveables : 1) ; 

The third argument of the function indicates whether you want to exclude identifiers with the ``NoSave`` property. Identifiers that have this property will not be stored in cases.

It's a good practice to delete any pre-existing monitor with the same name before you create the monitor, to avoid errors. You can do this with the example code below:

.. code-block:: aimms
    :linenos:

    !Make sure that we delete any data change monitor with this name
    !if it already exists. The function DataChangeMonitorDelete will
    !return 1 in case of success, 0 in case it did not find a monitor
    !with the given ID and -1 for all other errors.
    p_retVal := DataChangeMonitorDelete( sp_DataChangeMonitorID )  ;
 
    if p_retVal = 1 then
        raise warning "Deleted existing data change monitor with ID \""
                      + sp_DataChangeMonitorID  + "\"" ;
    endif ;
 
    if p_retVal = -1 then
        raise error "Error while deleting data change monitor with ID \""
                    + sp_DataChangeMonitorID +"\"\n"
                    + "CurrentErrorMessage = " + CurrentErrorMessage ;
    endif ;

Checking the data for changes
-----------------------------
After you create the data change monitor, you can query it with the intrinsic function ``DataChangeMonitorHasChanged`` to check if the data monitored by it has changed. See the example below:

.. code-block:: aimms
    :linenos:

    !Now modify the data
    p_myParameter2 := 3.14 ;
 
    !And check if the data is indeed changed. You should see this
    !DialogMessage appear
    if DataChangeMonitorHasChanged(sp_DataChangeMonitorID) then
        DialogMessage("Data changed (2) - Should show dialog") ; 
    endif ;

Note that the data change monitor functions also work to monitor defined identifiers for changes. However, AIMMS might not recalculate the definition of a parameter if you have not used the explicit update statement or have not accessed the data of the identifier yet. 

Let's take the two parameters below:

.. code-block:: aimms
    :linenos:

    Parameter p_myParameter2;
    Parameter p_myDefinedParameter {
        Definition: 2*p_myParameter2;
    }

If you are monitoring ``p_myDefinedParameter`` for changes, the following code will not show a dialog message:

.. code-block:: aimms
    :linenos:

    p_myParameter2 := 1998 ;
 
    !you might expect the monitor to indicate here that the data has changed.
    !However, as explained above, the data change monitor does not evaluate
    !definitions, so as long as the identifier myDefinedParameter has not been
    !updated (either explicitly with update statement or implicitly by accessing
    !its data), the datachange monitor will not indicate any changes
    if DataChangeMonitorHasChanged(sp_DataChangeMonitorID) then
        DialogMessage("Data defined parameter changed - Should not show dialog!");
    endif ;

The monitor detects the changed data only after the data of parameter ``p_myDefinedParameter`` has been accessed (e.g. by showing it in a GUI or using it in an assignment statement) or you have explicitly instructed AIMMS to recalculate the definition with the update statement.  See below:

.. code-block:: aimms
    :linenos:

    !Explicitly update the parameter, causing an evaluation of the definition
    update p_myDefinedParameter ;
 
    !Now the data of myDefinedParameter has changed (because of the update
    !statement and the DataChangeMonitor will indicate a change also. This
    !means that you should see the dialogmessage pop up
    if DataChangeMonitorHasChanged(sp_DataChangeMonitorID) then
        DialogMessage("Data defined parameter changed - Should show dialog");
    endif ;

Download example
-----------------
You can download code snippets used in this article from the link below: 

* :download:`DataChangeMonitorExample.zip <model/DataChangeMonitorExample.zip>` 



