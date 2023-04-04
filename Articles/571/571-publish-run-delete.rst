Publishing, Running and Deleting apps on the Cloud
===================================================

This article provides an example of Python code that can be used to publish a project, run a specific task on it and delete the published project.

Use case?
--------------
When using creating a CI/CD pipeline, it may be useful or required that a task be run on the AIMMS Cloud. The below example show the general steps required to achieve this. We utilize a random version number in order to avoid conflicts with already published apps. We delete the published application because the focus of the code is to run a task, not to publish the application for end-users.

.. code-block:: python

    import requests
    import time
    import os

    url = "https://YOURURL.aimms.cloud/pro-api/v1/"
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
    url_run = url + '/' + url_scope + '/' + project_name + '/' + project_version + '/' + url_taskname + '/'

    response_submit = requests.request("POST", url_run, headers=headers)

    print(response_submit.text)
    sp_id = response_submit.json()['id']

    url_poll = url + '/' + url_scope + '/' + sp_id + '/'

    status = ""
    print("STATUS:")
    while status != "finished": #loop to track status of task until done
        time.sleep(1)
        response_poll = requests.get(url_poll, headers=headers)
        status = response_poll.json()['status']
        print(status)

    url_response = url + '/' + url_scope + '/' + sp_id + '/' + 'response'
    response_task = requests.get(url_response, headers=headers)
    print(response_task.text)


    # Cleaning up
    url_scope = "applications"
    url_delete = url + '/' + url_scope + '/' + project_name + '/' + project_version + '/'

    response_delete = requests.request("DELETE", url_delete, headers=headers)

    print(response_delete.text)
