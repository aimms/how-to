Python Client
================

To create a service, it helps to have a client available that can test that service.

In this article, a python client is presented to illustrate using 
:doc:`this machine scheduling service<../567/567-aimms-service>`.

The client has the following steps:

#.  Submit the task.

#.  Poll the task until it is finished.

#.  Obtain task response.

#.  Report the solution.

These steps are detailed in the next sections. Please use the following project to follow this article:

    :download:`Python 3.10 script <model/main.py>` 

Submit Task
------------

According to the `AIMMS PRO REST API <https://documentation.aimms.com/cloud/rest-api.html#aimms-pro-rest-api>`_ , 
a post to the service end point ``/tasks/{app}/{ver}/{service}`` is required. 
Easily achieved using python as follows:

.. code-block:: python 

    url_submit = sp_server + '/' + sp_api + '/' + sp_scope + '/' + sp_app + '/' + sp_ver + '/' + sp_service

The Python string variables used are:

.. code-block:: python 
    :linenos:

    sp_server = 'https://chriskuip.aimms.cloud'
    sp_api = 'pro-api/v1'
    sp_scope = 'tasks'
    sp_app = 'MachineSchedulingMIPTimeIndexedSolver'
    sp_ver = '007'
    sp_service = 'equalParallelMachineTimeIndexedMIP'

Remarks:

#.  Line 1: The URL of the AIMMS Cloud.

#.  Line 2: The fixed part of the path indicating the AIMMS PRO REST API is used.

#.  Line 3: The scope used.

#.  Line 4: The application name.

#.  Line 5: The application version.

#.  Line 6: The service offered by the application.

Then we need to post the request, which is accomplished using the a POST request with the `Requests <https://requests.readthedocs.io/en/latest/>`_ package as follows:

.. code-block:: python 
    :linenos:

    Headers = readJson('apikey.txt')
    requestDict = readJson('data/3Par15act.json')

    response_submit = requests.post(url_submit, json=requestDict, headers=Headers)

The string variables used are defined as follows:

#.  ``Headers`` contains the API key in the format: ``{"apiKey": "11111111111111111111111111111111111111111111"}``.  
 
#.  ``requestDict`` contains the request body and is the actual input for the task.

Polling Task Status
-------------------

.. code-block:: python 
    :linenos:

    # Get the response id
    sp_id = response.json()['id']

    # Wait until the task is finished, polling every second
    url_poll = sp_server + '/' + sp_api + '/' + sp_scope + '/' + sp_id

    status = ""
    print("STATUS:")
    while status != "finished":
        time.sleep(1)
        response = requests.get(url_poll, headers=Headers)
        status = response.json()['status']
        print(status)

Remarks:

#.  Line 2: ``sp_id`` is the id of the task, and takes the form of a `GUID <https://en.wikipedia.org/wiki/Universally_unique_identifier>`_ string.

#.  Line 5: The service end point to poll for the status is: ``/tasks/{id}``

#.  Line 10: A ``GET`` on this service end point.

#.  Line 11: The values ``status`` can take on are enumerated `here <https://documentation.aimms.com/dataexchange/rest-server.html#service-end-points-exposed>`_


Obtain Task Response
---------------------

.. code-block:: python 
    :linenos:

    # Finished. Obtain the final result.
    url_response = sp_server + '/' + sp_api + '/' + sp_scope + '/' + sp_id + '/' + 'response'

    response = requests.get(url_response, headers=Headers)
    return json.loads(response.text)

Remarks:

#.  Line 2: A get on the service end point ``/task/{id}/response`` is used to obtain the task response.


.. seealso::

    #.  :doc:`../567/567-aimms-service` 

    #.  `More on the new REST service for 'Tasks' <https://community.aimms.com/product-updates/more-on-the-new-rest-service-for-tasks-1354>`_

    #.  `REST Service for running solve jobs and other asynchronous jobs <https://community.aimms.com/product-updates/rest-service-for-running-solve-jobs-and-other-asynchronous-jobs-1345>`_

    #.  Various Python packages: `Requests <https://requests.readthedocs.io/en/latest/>`_, `Pandas <https://pandas.pydata.org/>`_, `Plotly <https://plotly.com/python/>`_


.. spelling:word-list::

    api










