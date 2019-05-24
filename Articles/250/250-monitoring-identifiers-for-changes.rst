Monitoring identifiers for changes===================================.. meta::   :description: Tracking whether or not an identifier in a collection has changed   :keywords: monitor, track, identifier change.. note::    This article was originally posted to the AIMMS Tech Blog.
.. <link>https://berthier.design/aimmsbackuptech/2013/04/10/monitoring-identifiers-for-changes/</link>
.. <pubDate>Wed, 10 Apr 2013 09:06:23 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=2829</guid>
.. sidebar:: Monitor    .. image:: images/monitor.jpg    Whenever you changed the values of some identifiers and close your AIMMS project, the default behavior of AIMMS is that it will ask you whether you want to save the unchanged data. This behavior depends on which data categories and case type are currently active.
There can be situations where it is useful to not have this check based on a case type / data category, but just check whether a give subset of all identifiers in your model have been changed since the last time you checked. The data change monitor functionality   allows you to achieve exactly this. 
The intrinsic functions that have been introduced for the data change monitor functionality are the following:
#. ``DataChangeMonitorCreate``: To create a new monitor and get a reference to it, 
#. ``DataChangeMonitorDelete``: To delete an existing monitor by its reference,
#. ``DataChangeMonitorReset``: Reset a monitor indicated by a reference,
#. ``DataChangeMonitorHasChanged``: Check whether values have changed.
To create a new data change monitor you will need to provide an ID for this monitor and a subset of the predefined set ``AllIdentifiers`` containing the identifiers you want to monitor for changes. In the example code below, ``s_WatchedIdentifiers`` is a subset of ``AllIdentifiers`` and ``sp_DataChangeMonitorID`` is a string parameter.
.. code-block:: aimms    :linenos:    !The identifiers we are interested in checking for changes    s_WatchedIdentifiers := data { 'p_myParameter1' , 'p_myParameter2' , 
                            'p_myDefinedParameter' } ;  
    !Create a name that will be the ID for this data change monitor
    sp_DataChangeMonitorID := "Monitor parameters" ; 
To actually create the data change monitor, you have to use the intrinsic function ``DataChangeMonitorCreate`` as shown below:
.. code-block:: aimms    :linenos:
    !Create the actual Data Change Monitor with the ID denoted
    !by the string parameter DataChangeMonitorID and monitor the
    !changes of identifiers present in the set WatchedIdentifiers
    DataChangeMonitorCreate(
        ID                   : sp_DataChangeMonitorID  , 
        monitoredIdentifiers : s_WatchedIdentifiers , 
        ExcludeNoneSaveables : 1) ; 
The third argument of the function indicates whether you want to exclude the identifiers that have the ``NoSave`` property. Identifiers that have this property will not be stored in cases.
If the ID you provide for your data change monitor already exists, the above call with result in an error. Therefore, it might be good practice to ensure you always delete a possibly pre-existing monitor with the same name before you create the monitor. You can do this with the example code below:
.. code-block:: aimms    :linenos:
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
After you have created the data change monitor, you can now query it with the intrinsic function ``DataChangeMonitorHasChanged`` to check if the data monitored by this monitor has changed. An example of this is shown in the code below:
.. code-block:: aimms    :linenos:
    !Now modify the data
    p_myParameter2 := 3.14 ; 
    !And check if the data is indeed changed. You should see this
    !DialogMessage appear
    if DataChangeMonitorHasChanged(sp_DataChangeMonitorID) then
        DialogMessage("Data changed (2) - Should show dialog") ; 
    endif ;
Note that the data change monitor functions also work to monitor defined identifiers for changes. However, you have to keep in mind that AIMMS might not have yet re-calculated the definition of a parameter (i.e. in case you have not used the explicit update statement or have not accessed the data of the identifier yet). This means that if you have the two parameters.. code-block:: aimms    :linenos:
    Parameter p_myParameter2;
    Parameter p_myDefinedParameter {        Definition: 2*p_myParameter2;    }
and you are monitoring ``p_myDefinedParameter`` for changes the following code will not show a dialog message:.. code-block:: aimms    :linenos:
    p_myParameter2 := 1998 ; 
    !you might expect the monitor to indicate here that the data has changed.
    !However, as explained above, the data change monitor does not evaluate
    !definitions, so as long as the identifier myDefinedParameter has not been
    !updated (either explicitly with update statement or implicitly by accessing
    !its data), the datachange monitor will not indicate any changes
    if DataChangeMonitorHasChanged(sp_DataChangeMonitorID) then
        DialogMessage("Data defined parameter changed - Should not show dialog!");
    endif ;
Only after the data of parameter ``p_myDefinedParameter`` has been accessed (e.g. by showing it in a GUI or using it in an assignment statement) or you have explicitly instructed AIMMS to recalculate the definition with the update statement, will the monitor see the changed data:
.. code-block:: aimms    :linenos:
    !Explicitly update the parameter, causing an evaluation of the definition
    update p_myDefinedParameter ; 
    !Now the data of myDefinedParameter has changed (because of the update
    !statement and the DataChangeMonitor will indicate a change also. This
    !means that you should see the dialogmessage pop up
    if DataChangeMonitorHasChanged(sp_DataChangeMonitorID) then
        DialogMessage("Data defined parameter changed - Should show dialog");
    endif ;
Code snippets can be downloaded :download:`here <model/DataChangeMonitorExample.zip>` .. include:: /includes/form.def