Creating an AIMMS Server app
=============================

There are several advantages to setting up a client-server architecture:

* Independent developments - clients can be developed independently from the server

* Scalability - the AIMMS Cloud permits to run several tasks in parallel

AIMMS is designed to create Decision Support Applications, thereby a good choice to 
specify the actual implementation of a service. 
Besides actually implementing decision support, to make a service out of your application, 
you want to take care of the following aspects:

* which services should defined,

* how to implement the services,

* testing the services, and

* documenting the services. 

This article assumes you are familiar with 

* Client-server architecture - see also :doc:`AIMMS OpenAPI Overview<../561/561-openapi-overview>`

* Using the AIMMS Cloud platform, including using applications and publishing applications - 
  see also `AIMMS Cloud documentation <https://documentation.aimms.com/cloud/index.html>`_

The application that is used to illustrate various concepts, is just counting the asterisks in a list of strings. 
As such it is a "Hello World" type of application - 
just illustrating technology and getting you started to with that technology.


Functionalities of the application
----------------------------------

Let's start by describing the functionalities of the application:

#.  It counts the number of asterisks in its input.

#.  Generate an input file with some asterisks in it.

The input can be in one of the de facto data standards for providing data:

#.  CSV

#.  Json

#.  Parquet

#.  Excel

#.  XML

For each of the services, the data format of the output is the same as the data format of the input 
unless there are errors. If there are errors, the data format is always a json file.

For generating sample input files, the output can be in any of the above formats.

As a service is an exact specification of both functionality and input/output data formats, we have ten services to implement.
Only one service will be detailed in this article, the others are small variations on that. 
The details of the other services can be found in the downloads.


Defining the service
------------------------

A service is defined by associating a service name with an AIMMS procedure, as illustrated below:

.. code-block:: aimms 
    :linenos:
    :emphasize-lines: 3,4,10

    Procedure pr_countTheStarsJson {
        Body: {
            _sp_inp := dex::api::RequestAttribute( 'request-data-path'  ) ;
            _sp_out := dex::api::RequestAttribute( 'response-data-path' ) ;
            
            pr_actuallyCountStarsJson( _sp_inp, _sp_out );
            
            return 1;
        }
        dex::ServiceName: countStarsJson;
        StringParameter _sp_inp;
        StringParameter _sp_out;
    }

Remarks:

* Line 10: The annotation ``dex::ServiceName`` associates the procedure ``pr_countTheStars`` with the service ``countStars``.

* Lines 3-4:  When the procedure is invoked as a task, the string parameter  ``dex::api::RequestAttribute`` 
    is available. Here it is used the name of the input file and output file to local string parameters.

* Line 6: Call the workhorse (see sub section below).

**Similar** procedures define the same service, but use other data formats, such as CSV, Excel, Parquet, and XML.
In addition, there are similar procedures to generate an input file.

Implementing a service
----------------------

We are assuming here that you have developed a service; to count the number of asterisks in a dictionary of lines.

.. code-block:: aimms 
    :linenos:

    Procedure pr_actuallyCountStarsJson {
        Arguments: (sp_input,sp_output);
        Body: {
            empty s_lineNumbers ;
            
            dex::ReadFromFile(
                dataFile         :  sp_input, 
                mappingName      :  "starsJSON", 
                emptyIdentifiers :  0, 
                emptySets        :  0, 
                resetCounters    :  0);
            
            p_noStars := fnc_numberOfStars( sp_lines );
            
            ! write response body
            dex::WriteToFile(
                dataFile    :  sp_output, 
                mappingName :  "countedJSON", 
                pretty      :  0);
            
            ! Application specific return code.
            return 1;
        }
        StringParameter sp_input {
            Property: Input;
        }
        StringParameter sp_output {
            Property: Input;
        }
    }

remarks:

* Line 2: The arguments denote the name of the input and output files.

* Lines 6-11: Reading of input

* Line 13: The actual computation is a simple function call.

* Lines 16-19: Writing the output

.. tip:: The procedure `ProfilerStart <https://documentation.aimms.com/functionreference/development-support/profiler-and-debugger/profilerstart.html>`_ is called in ``MainInitialization`` enabling tracking task invocations, and task performance.

Testing the service
-----------------------

There are three types of tests:

#. In the server app itself, also called unit tests.

#. On the machine of the AIMMS app developer, using a client app for this purpose.

#. On the AIMMS Cloud, and using a client app for this purpose.

Performing unit tests
^^^^^^^^^^^^^^^^^^^^^^

An example of a unit test is the following:

.. code-block:: aimms 
    :linenos:

    Procedure pr_testCountJson {
        Body: {
            dex::AddMapping(
                mappingName :  "starsJSON", 
                mappingFile :  "Mappings/starsJSON.xml");
            dex::AddMapping(
                mappingName :  "countedJSON", 
                mappingFile :  "Mappings/countedJSON.xml");

            ! Call the procedure that does the actual implementation.
            pr_actuallyCountStarsJson("data/data.json", "data/noStars.json");

            ! Verify that the output file has the expected contents.
            _sp_jsonContents := FileRead( "data/noStars.json" );
            aimmsunit::AssertTrue(
                descr :  "Expected outcome json", 
                expr  :  _sp_jsonContents = "{\"count\":28.0}", 
                cont  :  0);
        }
        aimmsunit::TestSuite: CountStarsUnitTests;
        StringParameter _sp_jsonContents;
    }

Such unit tests verify that the server application still has the verified behavior.

More about unit tests can be found at:

#.  https://documentation.aimms.com/unit-test/index.html

#.  https://how-to.aimms.com/Articles/216/216-effective-use-unit-test-library.html

Facilitating tests with client apps
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

To facilitate testing by client apps of the service, the service will need to be:

#.  On local host:

    To develop the AIMMS service itself, in AIMMS Developer the service can be started using 
    `dex::api::StartAPIService <https://documentation.aimms.com/dataexchange/api.html#dex-api-StartAPIService>`_.
    See also https://documentation.aimms.com/dataexchange/rest-server.html#activating-the-rest-service.

#.  In the AIMMS Cloud:

    After publishing an app ``app`` with version ``ver`` on the AIMMS Cloud, 
    the service is started when a POST request of the above form is made; 
    there is no need to call ``dex::api::StartAPIService`` from within the service app.

Actual Testing using clients will be discussed in the accompanying articles.

Documenting the service
--------------------------

For each service, we need to specify its:

#.  Functionality. IIn the running example this would be: 

    The service ``countStarsJson`` counts the number of asterisks in a list of strings.

#.  Expected input / request body. In the running example this would be:

    The expected input is a json file with one member named "lines" and has as value an array of strings.

#.  Output / response body to be expected. In the running example this would be:

    The output to be expected is a json file with one member named count, and value the number of asterisks.


Summary
--------

Using the AIMMS language is a good way to define a service atop of a Decision Support application.

With the DataExchange library, defining the interface is essentially a matter of 

#.  Selecting input and output formats and linking the contents of these data files to identifiers in the AIMMS application

#.  Selecting the procedure to run 

It is good practice to implement unit tests and provide good and detailed documentation of your  services.

.. spelling:word-list::

    facto