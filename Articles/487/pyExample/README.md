# cvrp-tw-k-means

## Prerequisites

- [Install Python 3](https://www.python.org/downloads/)
- [Install Docker Desktop](https://www.docker.com/products/docker-desktop)

## Create a virtual environment

Create a virtual environment and install required packages.

```
py -3 -m venv env
```

Make sure you are using the Python interpreter of the virtual environment. If using VSCODE, a pop up will appear asking you if you want to switch. You can use below code to install the required libraries (update file when you update your app)

```
pip install -r requirements.txt --user
```

## Create app and run it locally

We will use the folder ```app``` as the root folder for developing our Python model. It contains ```main.py``` which runs the flask app.  

The included dockerfile will pull an Alpine Linux container with an NGINX web server. 

If you run the ```main.py``` in terminal, you will start an instance using Flask's web server. This is useful for quick testing. 

## Call API from AIMMS

The aimmsModel project has a project prCallAPI. 

Make sure your api server is running from previous step. For a deployment server, follow instructions in next step. 

# JSON Maps

Folder aimmsModel/apiCalls has two xml files - inMap and outMap. These are used by procedures ``prReadJSON`` and ``prWriteJSON`` respectively.

<ArrayMapping> nodes for indexed AIMMS identifiers
<ValueMapping> directly for scalar identifiers

Use something like this to write out a dictionary instead

 <ArrayMapping name="coordinates">
            <ObjectMapping>
                <ValueMapping name="i" binds-to="iLocation"/>
                <ValueMapping name="lat" maps-to="pLatitude(iLocation)"/>
                <ValueMapping name="lon" maps-to="pLongitude(iLocation)"/>
            </ObjectMapping>
</ArrayMapping>

"coordinates": [
		{
			"i": "0",
			"lat": 47.601707,
			"lon": -122.185762
		},

Read documentation of the DataExchange library for more details. https://documentation.aimms.com/dataexchange/mapping.html

## Build a docker image

Make sure Docker is running and build the docker image, read more about docker commands on their website. Make sure you run this command while in the folder in which your ```Dockerfile``` is saved. 

```
docker build --pull --rm -f "Dockerfile" -t imageName:latest "."
```

To run this image, run below in commandline. ```containerName``` can be skipped and Docker will generate a random name. 

```
docker run -d -p 8000:8000 --name "containerName" imageName
```

Open a web browser, and navigate to the sample app at ```http://localhost:8000```.

In your terminal window, press Ctrl+C to exit the web server and stop the container.

If you make code changes you can re-run the docker build and run commands above to update the container.
