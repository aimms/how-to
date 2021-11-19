# To add a new cell, type '# %%'
# To add a new markdown cell, type '# %% [markdown]'
# %%
import dataio
from sklearn.cluster import KMeans

def mykMeans(input:dict):
    """ takes the input file name and runs kmeans, returns a dictionary with lat, lon and clusters of the centroids"""

    numClusters, coordData, lat, lon = dataio.dataFromDict(input)

# %%
    myCluster = KMeans(numClusters)
    myCluster.fit(coordData)
# %%
    myCentroids = myCluster.cluster_centers_.tolist()
    outClusters = myCluster.labels_.tolist()

    outLat, outLon = [], []

    for i in range(len(myCentroids)):
        outLat.append(myCentroids[i][0])
        outLon.append(myCentroids[i][1])

    dataOut = {
        'lat'       : outLat,
        'lon'       : outLon,
        'clusters'  : outClusters
    }

    return dataOut


