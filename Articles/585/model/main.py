# This is a sample Python script.
# It is to demo and test the service 'countStars' implemented in AIMMS and published on an AIMMS Cloud

import requests
import time
import json

# Create the URL
using_AIMMS_cloud = 0
port = '8080'
app = 'CountTheStars'
ver = '1.0.0.1'
service_name = 'countStars'
if using_AIMMS_cloud:
    cloud_file = open("../ConnectionInformation/cloud.txt", "r")  # The cloud name is not stored in this project.
    cloud = cloud_file.readline()

    headers_file = open("../ConnectionInformation/apikey.txt", "r")  # The API key is not stored in this project.
    headers_apikey = headers_file.readline()
    Headers = {"apiKey": headers_apikey}
else:
    Headers = {}
    cloud = ""


# The URL prefix is the fixed part of the URL's used.
# On Cloud, we need to include the name of the cloud,
# On localhost, we need the port number (8080 unless specified otherwise).
url_prefix = ''
if using_AIMMS_cloud:
    url_prefix = f"https://{cloud}/pro-api/v1/tasks/"
else:
    url_prefix = f"http://localhost:{port}/api/v1/tasks/"

# To submit a task a POST is required to the URL for submitting a task.
# On Cloud, we also need to specify the app, version, and service name.
# On localhost, only the service name is needed.
url_submit = ''
if using_AIMMS_cloud:
    url_submit = url_prefix + app + '/' + ver + '/' + service_name
else:
    url_submit = url_prefix + service_name
print(f"URL submit task: {url_submit}")

# Create the input json.
no_stars = 50
no_lines = 56
line = ''
for i in range(0, no_stars):
    line = line + '*'
lines = [line]
for i in range(1, no_lines):
    lines.append(line)
dict_lines = {"lines": lines}
json_lines = json.dumps(dict_lines)

# Kick off the task
submit_response = requests.post(url_submit, data=json_lines, headers=Headers)
print(f"Submit response code: {submit_response.status_code}")

# Get the response id
task_id = submit_response.json()['id']
print("Task id: " + task_id)


# Wait until the task is finished, polling every five seconds
url_poll = url_prefix + task_id
print("URL for status updates: " + url_poll)

status = ""
print("Task status:")
while status != 'finished' and status != 'finished with errors':
    time.sleep(5)
    poll_response = requests.get(url_poll, headers=Headers)
    status = poll_response.json()['status']
    print("    " + status)

# Finished. Obtain the final result.
url_task_response = url_prefix + task_id + '/response'
print(f"URL Task Response: {url_task_response}")

task_response = requests.get(url_task_response, headers=Headers)
print(f"task response code: {task_response.status_code}")
print(f"task response json: {task_response.json()}")
