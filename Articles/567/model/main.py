import plotly.express as px
import pandas as pd
import requests
import datetime
import time
import json


sp_server = 'https://chriskuip.aimms.cloud'
sp_api = 'pro-api/v1'
sp_scope = 'tasks'
sp_app = 'MachineSchedulingMIPTimeIndexedSolver'
sp_ver = '007'
sp_service = 'equalParallelMachineTimeIndexedMIP'

# function to read a file, and return its contents as a JSON string.
def readJson(fname):
    a_file = open(fname, "r")
    its_content = a_file.read()
    a_file.close()
    return json.loads(its_content)

# Pass the API key in the header
Headers = readJson('apikey.txt')

# Create the input .json
#requestDict = readJson('data/3Par10act.json')
requestDict = readJson('data/3Par15act.json')

# A task corresponds to a procedure in an AIMMS model.
def runTask():
    # Kick off the task
    url_submit = sp_server + '/' + sp_api + '/' + sp_scope + '/' + sp_app + '/' + sp_ver + '/' + sp_service
    response_submit = requests.post(url_submit, json=requestDict, headers=Headers)

    # Get the response id
    sp_id = response_submit.json()['id']

    # Wait until the task is finished, polling every second
    url_poll = sp_server + '/' + sp_api + '/' + sp_scope + '/' + sp_id

    status = ""
    print("STATUS:")
    while status != "finished":
        time.sleep(1)
        response_poll = requests.get(url_poll, headers=Headers)
        status = response_poll.json()['status']
        print(status)

    # Finished. Obtain the final result.
    url_response = sp_server + '/' + sp_api + '/' + sp_scope + '/' + sp_id + '/' + 'response'

    response_task = requests.get(url_response, headers=Headers)
    return json.loads(response_task.text)

resp = runTask()
#resp = readJson('data/3Par15act.solution.json')

problem_status = resp['problemStatus']

if (problem_status == 'Optimal') or (problem_status == 'IntegerSolution'):

    print()

    prb = requestDict['activities']
    sol = resp['solution']
    KPIsList = resp['KPIs']
    KPIs = KPIsList[0]
    dfKPIs = pd.DataFrame(KPIs, index=['KPI'])
    print(dfKPIs.T.to_string())
    print()

    dfPrb = pd.DataFrame(prb, columns=['act', 'processTime', 'dueDate', 'releaseDate'])
    dfSol = pd.DataFrame(sol, columns=['act', 'start', 'resource'])
    dfJoin = dfPrb.join(dfSol, lsuffix='prb', rsuffix='sol')
    dfJoin.fillna(0, inplace=True)

    # prepare columns to be added to the dataframe.
    refday = datetime.date(2022, 2, 2)
    startcol = []
    finishcol = []
    namecol = []
    tardicol = []
    for row in dfJoin.itertuples():
        startcol.append((refday + datetime.timedelta(days=row.start)).strftime("%Y-%m-%d"))
        finishcol.append((refday + datetime.timedelta(days=row.start+row.processTime)).strftime("%Y-%m-%d"))
        tardicol.append('{:.0f}'.format((row.start+row.processTime)-row.dueDate) if row.dueDate < (row.start+row.processTime) else '')
        namecol.append(row.actprb)

    dfJoin['task'] = namecol
    dfJoin['start date'] = startcol
    dfJoin['end date'] = finishcol
    dfJoin['tardiness'] = tardicol

    # Reduce our dataframe to relevant columns only.
    dfJoin = dfJoin[['task', 'start date', 'end date', 'resource', 'tardiness']]

    print(dfJoin.to_string())
    print()

    fig = px.timeline(dfJoin, x_start="start date", x_end="end date", y="resource", color="task")

    # auto arrange reversed, as tasks are listed from the bottom up otherwise.
    fig.update_yaxes(autorange="reversed")

    fig.show()

else:

    print()
    print("Problem not optimal: " + problemStatus)
    print()
