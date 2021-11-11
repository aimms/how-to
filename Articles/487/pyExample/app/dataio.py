import json

def dataForCluster(fileName:str):
    """takes a json file and reads numClusters as int, latitude, longitude as float and also returns a tuple of lat, lon"""

    data = json.load(open(fileName))

    numClusters = int(data['numClusters'])
    lat = data['latitude']
    lon = data['longitude']
    myCluster = []

    for i in range(len(lat)):
        myCluster.append([float(lat[i])])
        myCluster[i].append(float(lon[i]))

    return numClusters, myCluster

def dataFromDict(dic:dict):
    """ takes the request.get_json dictionary and returns numClusters as int, latitude, longitude as float and also returns a tuple of lat, lon"""

    data = dic

    numClusters = int(data['numClusters'])
    lat = data['latitude']
    lon = data['longitude']
    myCluster = []

    for i in range(len(lat)):
        myCluster.append([float(lat[i])])
        myCluster[i].append(float(lon[i]))

    return numClusters, myCluster, lat, lon


def dataForAIMMS(dict):
    """ takes a dictionary as input and writes it out to a json file clusters.json"""

    with open("clusters.json", "w") as outfile:
        json.dump(dict, outfile)
