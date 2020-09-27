:orphan:

How to connect AIMMS with Python
============================================

.. meta::
   :description: Integrating (data science) models built in Python with your AIMMS applications
   :keywords: python, integration, data science, machine learning, connectivity

The usage of both optimization and machine learning algorithms in decision support applications is growing steadily. One example is to use a forecasting model to predict the expected demand and provide that as an input to a MIP model. AIMMS is optimized for the development of apps based on MIP models and in this article, we will show you how to empower your AIMMS apps with machine learning models built in Python. This will let you leverage the availability of libraries like `scikit-learn <https://scikit-learn.org/stable/index.html>`_, `NumPy <https://numpy.org/>`_ and others in AIMMS as well. 

Overview
-----------

:doc:`The HTTP Library section </C_Developer/Sub_Connectivity/sub_http/index>` contains examples like :doc:`Call Google Maps API <../296/296-obtaining-geographic-data-through-the-google-api>` and :doc:`Call IBM Image Recognition API <../301/301-Image-Recognition>` which show how to call web services in AIMMS. We will use the same principles to call a Python model from AIMMS by floating the Python model as a web service. There are many packages available for this purpose and we will use `Flask <https://flask.palletsprojects.com/en/1.1.x/>`_ in our example which you can :download:`download here <pyExample.zip>`.

The example contains a k-Means clustering model built in Python using scikit-learn which is floated as a REST API using Flask (`app`) and an AIMMS project (`aimmsModel`) which uses the `HTTP <https://documentation.aimms.com/httpclient/index.html>`_ and `DataExchange <https://documentation.aimms.com/dataexchange/index.html>`_ libraries to call the clustering model. We visualize this set up in the below image.

.. image:: flow.png
    :align: center

#. A clustering model ``mykMeans`` which takes coordinate data (latitude and longitude) as input and outputs center of gravity is built in file `app/kmeansClust.py`.
#. This function is floated as an API which responds to a GET call using Flask in file `app/main.py`.
#. AIMMS procedures ``prWriteJSON`` and ``prReadJSON`` use the DataExchange library and mapping files in `aimmsModel/apiCalls` to exchange data between AIMMS and the API using JSON files
#. Procedure ``prCallAPI`` uses the HTTP library functions to call the API floated in step 2.

Installing prerequisites
----------------------------

We assume you have Python3 installed already. This example requires the packages ``scikit-learn`` and ``Flask`` as outlined in the file `app/requirements.txt`. You can install these packages by using the commandline prompt::

    pip install -r requirements.txt --user

.. tip:: It is usually recommended to create a virtual environment so that your local Python installation is not changed. So use ``py -3 -m venv env`` to start a virtual environment. 

Another useful tool to have is `Postman <https://www.postman.com/downloads/>`_ which lets you send API requests. We will use this tool to test our API.

The Python model
------------------

`app/kmeansClust.py` has the function ``mykMeans`` which takes in the number of clusters ``numClusters`` and latitude-longitude data ``coordData`` and fits a 
`KMeans model <https://scikit-learn.org/stable/modules/generated/sklearn.cluster.KMeans.html#sklearn.cluster.KMeans>`_. The input data is retrieved from a JSON file by the function ``dataForCluster`` in `app/dataio.py`.

.. literalinclude:: pyExample/input.json
    :language: JSON
    :lines: 1-5, 166-169, 330-333
    :caption: Note that only some rows are displayed here

``dataForCluster`` simply transforms the arrays ``latitude`` and ``longitude`` into an array of tuples.

.. literalinclude:: pyExample/app/dataio.py
    :language: python
    :lines: 9-15
    :lineno-start: 9

In `app/main.py`, we use Flask modules and methods to float ``mykMeans`` as a web routine.

.. literalinclude:: pyExample/app/main.py
    :language: python
    :lines: 1-11, 18-19
    :linenos:

Running locally 
""""""""""""""""""

If you run the `app/main.py` in terminal (using ``python main.py``), Flask will start a local web server and the function ``mykMeans`` can be called as an API using the url http://localhost:8000/ or http://0.0.0.0:8000/ .

You can now use the Postman app to call this API by pasting the contents of `input.json` in the Body attribute as shown below. It will return the output of ``mykMeans`` as a JSON object.

.. image:: postman.png
    :align: center

The AIMMS model
------------------

The AIMMS project `aimmsModel` has the identifiers ``pLatitude(iLoc)``, ``pLongitude(iLoc)`` and ``pNumClusters`` which we need to write in a format similar to `input.json`. 

Data I/O
""""""""""""
``prWriteJSON`` creates the input file as the Python model expects and ``prReadJSON`` retrieves the results into AIMMS. ``prWriteJSON`` uses the mapping file `aimmsModel/apiCalls/outMap.xml` to create this `input.json` file. 

.. code-block:: aimms

    spOutFile := "input.json";
    spMapName := "outMap";
    spMapFile := "apiCalls//outMap.xml";

    dex::AddMapping(spMapName , spMapFile);

    dex::WriteToFile(
	    dataFile    : spOutFile , 
	    mappingName : spMapName , 
	    pretty      :  1);

.. note:: It is not necessary that the `pretty` argument for ``dex::WriteToFile`` is set to 1 but it helps with readability of the json file, which is particularly helpful during development.

.. literalinclude:: pyExample/aimmsModel/apiCalls/outMap.xml
    :language: xml
    :lines: 1-6
    :linenos:
    :emphasize-lines: 3-5

``ObjectMapping`` initializes a key-value tree inside which ``ValueMapping`` is the first node which holds the scalar parameter ``pNumClusters``. To write out indexed AIMMS identifiers, we can use the ``ArrayMapping`` like in line #4 which writes out ``pLatitude`` as an array value to the key `latitude`.

Similarly, ``prReadJSON`` will use the mapping file `aimmsModel/apiCalls/inMap.xml` to load the output of ``mykMeans`` into AIMMS identifiers ``pCluster(iLoc)``, ``pCenLat(iCentroid)`` and ``pCenLon(iCentroid)``.

.. literalinclude:: pyExample/aimmsModel/apiCalls/inMap.xml
    :language: xml
    :lines: 1-8
    :linenos:
    :emphasize-lines: 4, 7

Note the difference between the first mapping (for ``pCluster``) and the remaining two. ``iterative-existing=1`` is added to the map of ``pCluster`` because the elements ``iLoc`` already exist, whereas for the other two - we are letting the data exchange library create new elements in the set ``sCentroids``.

Calling the API
"""""""""""""""""""

Now we simply use the HTTP library functions to make a GET call to the API created in the previous section as shown in procedure ``prCallAPI``.

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 8-11
    :lineno-start: 2

    !starting request
    web::request_create(requestId : spReqID );
    web::request_setURL(spReqID, spURL);
    web::request_setMethod(spReqID, "GET");
    !as we want to send data in a file. Set 2nd argument to 'None' if you only need to pass a scalar value
    web::request_setRequestBody(spReqID, 'File', spOutFile);
    !as we want to send a json file and default expectation is a txt file
    web::request_getHeaders(spReqID, spRequestHeaders);
    spRequestHeaders['Content-Type'] := "application/json";
    web::request_setHeaders(spReqID, spRequestHeaders);
    web::request_setResponseBody(spReqID, 'File', spInFile);

Make sure that the url in ``spURL`` includes the appropriate `http://` or `https://` prefix. 