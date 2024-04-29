Publishing, Running and Deleting apps on the Cloud
===================================================

This article provides an example of Python code that can be used to publish a project, run a specific task on it and delete the published project.

Use case?
--------------
"When using a CI/CD pipeline" you may want to test the app as follows:

* Publish

* Run scenario

* Cleanup (including removing the app)

Below, you will find a code example of how to achieve this.

.. code-block:: python

    import requests
    import time
    import os
	
	 
    url = "https://YOURURL-test.aimms.cloud/pro-api/v1/"
	# Tasks require the use of V2 of the PRO-API
	url_tasks = "https://YOURURL-test.aimms.cloud/pro-api/v2/" 
    url_scope = "applications"
    project_name = 'YOURPPROJECTNAME'
    project_version = time.strftime("%Y%m%d%H%M%S") #Creating a unique name for publishing

    # Publishing
    file_location = './PROJECTNAME.aimmspack'
    files = [
        ('file', ('PROJECTNAME.aimmspack',
                  open(file_location, 'rb'),
                  'application/octet-stream'))
    ]
    headers = {
        'apiKey': 'YOURAPIKEY'
    }
    payload = {'metadata': '{"name": "' + project_name + '", "description": "YOUR DESCRIPTION", "projectVersionId": "' +
                project_version + '", ' '"aimmsVersionId": "4.91.2.8-linux64-x64-cm_vc141", "projectCategory": "YOUR CATEGORY" }'}
    # replace 4.91.2.8-linux64-x64-cm_vc141 with the version you would like to use 

    url_publish = url + '/' + url_scope
    response_publish = requests.request("POST", url_publish, headers=headers, data=payload, files=files)

    print(response_publish.text)

    # Runnning task
    url_scope = 'tasks'
    url_taskname = 'runScenarioTest' #Replace with the taskname used in the AIMMS project
    url_run = url_tasks + '/' + url_scope + '/' + project_name + '/' + project_version + '/' + url_taskname + '/'

    response_submit = requests.request("POST", url_run, headers=headers)

    print(response_submit.text)
    sp_id = response_submit.json()['id']

    url_poll = url_tasks + '/' + url_scope + '/' + sp_id + '/'

    state = ""
    print("state:")
    while state != "completed": #loop to track state of task until done
        time.sleep(10)
        response_poll = requests.get(url_poll, headers=headers)
        state = response_poll.json()['state']
        print(state)

    url_response = url_tasks + '/' + url_scope + '/' + sp_id + '/' + 'response'
    response_task = requests.get(url_response, headers=headers)
    print(response_task.text)


    # Cleaning up
    url_scope = "applications"
    url_delete = url + '/' + url_scope + '/' + project_name + '/' + project_version + '/'

    response_delete = requests.request("DELETE", url_delete, headers=headers)

    print(response_delete.text)
