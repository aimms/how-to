AIMMS Server app
=================

It describes the implementation of the services used:

The AIMMS app that provides the service: :download:`AIMMS 4.95 server project <model/CountTheStars.zip>` 

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

* Lines 3-4: 
    When the procedure is invoked as a task, the string parameter  ``dex::api::RequestAttribute`` is available. 
    Here it is used the name of the input file and output file to local string parameters.

* Line 6: Call the workhorse (see sub section below).

**Similar** procedures define the same service, but use other data formats, such as CSV, Excel, Parquet, and XML.
In addition, there are similar procedures to generate an input file.

implementing service
----------------------

We are assuming here that you have developed a service; to count the number of `*` in a dictionary of lines.

.. code-block:: aimms 
    :linenos:
    :emphasize-lines: 3,5

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
            
            FileGetSize(
                filename :  sp_input, 
                fileSize :  _p_inputSize);
            p_noStars := fnc_numberOfStars( sp_lines );
            pr_log(formatString("pr_actuallyCountStarsJson(%s size: %i, %s) returns %i",
                sp_input,  _p_inputSize, sp_output, p_noStars ) );
            
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
        Parameter _p_inputSize;
    }

remarks:

* Line 2: the arguments denote the name of the input and output files.

* Lines 6-10: reading of input

* Line 15: the actual computation is a simple function call.

* Lines 20-24: writing the output

.. tip:: The procedure `ProfilerStart <https://documentation.aimms.com/functionreference/development-support/profiler-and-debugger/profilerstart.html>`_ is called in ``MainInitialization`` enabling tracking task invocations, and task performance.

 