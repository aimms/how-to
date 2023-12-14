Using AIMMS Services with a Python Application
=================================================

The power of AIMMS is to model and solve optimization problems.
In a client-server architecture, on the AIMMS Cloud, this power is leveraged using AIMMS PRO REST API service.
Solving optimization problems may require varying amounts of time, and 
a client of an AIMMS Service needs to initiate, monitor, and retrieve results from such a service.

Python is used in many existing data science projects.

In this article, we will explore how to use AIMMS services with a Python application. 
We will cover the steps a Python client needs to take to interact with the AIMMS Server app effectively. 
Specifically, we will focus on:

#.  Python app setup.

#.  Initiating a task consists of a POST to the URL of the task. 

#.  Monitoring a task. Monitoring a task will let you know whether a task is queued, running, or finished.  

#.  Obtain results. Once a task is finished, we can retrieve its results - typically the solution of an optimization problem.


By the end of this article, you will have a clear understanding of how to 
use AIMMS services efficiently with a Python application. 
You will be able to harness the power AIMMS to perform complex tasks while seamlessly 
integrating them with your Python-based projects.


Python app setup
----------------

For the Python client app, the following libraries are used.

*   **requests**, used to make REST API requests, see https://pypi.org/project/requests/, 

*   **time**, used during monitoring, see https://docs.python.org/3/library/time.html, and 

*   **json**, used to construct json input files, see https://docs.python.org/3/library/json.html

Initiating a task
-------------------

In short, a task is:

*   initiated by POSTing to the corresponding URL (see below), 

*   provided with access to the request and response bodies, 

*   licensed to solve one or more optimization problems, and

*   implemented by executing the AIMMS procedure associated with the service.

The URL to initiate such a task has one of the following two formats:

#.  ``http://localhost::{port}/api/v2/tasks/{service}``

    This will run the task in AIMMS Developer on the machine of the end-user.
    The apiKey header should not be used when using localhost for the AIMMS service.

#.  ``https://{cloud}.aimms.cloud/pro-api/v2/tasks/{app}/{ver}/{service}``

    This will run the task in the AIMMS Cloud.
    An apiKey header is required for using an AIMMS service via the AIMMS Cloud.
    See https://documentation.aimms.com/cloud/rest-api.html#api-keys-and-scopes to obtain an apiKey.

In :doc:`Develop, Test, and Deploy a Service<../585/585-develop-service>` 
it is discussed how to make the service available locally and in an AIMMS Cloud.

    
The response is a json file of the following form:

.. code-block:: json   

    {"id":"39cef818-ad0c-4016-89b5-44411ea33b83"}

This id identifies the task at hand, and will be used in the next sections to query said task.

Monitoring a task
------------------

With the id of a task, we can start monitoring:

Again, we have two formats, depending on running a local service, or deploying a service on the cloud:

#.  ``http://localhost::{port}/api/v2/tasks/{id}``

    When running the service locally.

#.  ``https://{cloud}.aimms.cloud/pro-api/v2/tasks/{id}``

    When running the service via the AIMMS Cloud.

A GET to one of these two URL's will result in a json string of the following form (json pretty printed):

.. code-block:: json

    {
        "id": "c46844d8-d544-476f-a25c-63ef61bd6fba",
        "service": "countStarsJson",
        "starttime": "2023-07-27T10:36:52Z",
        "status": "finished",
        "queuetime": 0.002,
        "runtime": 0.099,
        "returncode": 1
    }

Remarks:

* The id is the identification of the task.

* The service is the service called.

* The start time is in UTC.

* The status ``finished`` means normal completion. Other status values are: ``queued``, ``starting``, ``solving``.

* The queue time is the time between submit and an AIMMS Cloud rest session 
  actually start executing the procedure associated with the service.

* The run time is the run time of the procedure associated with the service.

* The return code is the return value of the procedure associated with the service.

The actual Python code to monitor the task is a straight forward while loop:

.. code-block:: python   

    status = ""
    print("Task status:")
    while status != 'finished' and status != 'finished with errors':
        time.sleep(5)
        poll_response = requests.get(url_poll, headers=Headers)
        status = poll_response.json()['status']
        print("    " + status)

Retrieving results
-------------------

Once the status of a task is ``finished``, its results can be requested.  The URL's are.

#.  ``http://localhost::{port}/api/v2/tasks/{id}/response``

    When running the service locally.

#.  ``https://{cloud}.aimms.cloud/pro-api/v2/tasks/{id}/response``

    When running the service via the AIMMS Cloud.

Doing a GET to this URL is coded as follows in Python:

.. code-block:: python   

    task_response = requests.get(url_task_response, headers=Headers)

and then the results can be retrieved as follows:

.. code-block:: python   

    print(f"task response code: {task_response.status_code}")
    print(f"task response json: {task_response.json()}")


Summary
---------

Using AIMMS services with a Python application opens up a world of possibilities for efficiently processing complex tasks. 
By following the steps outlined in this article, you can initiate tasks in the AIMMS Server app, monitor their progress, 
and retrieve the final results with ease. 
The structured communication between the Python client and AIMMS Server ensures a smooth workflow, 
even for tasks that may require significant processing time.


Next
-----------

:doc:`../585/585-VBA-client`


.. spelling:word-list::

    apiKey



