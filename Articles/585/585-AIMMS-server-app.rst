:orphan:
Implement AIMMS Server app
===========================

This article is a sub article of :doc:`main article<../585/585-develop-service>`
It describes the implementation of the service used:

The service
------------

The AIMMS app that provides the service: :download:`AIMMS 4.94 server project <model/CountTheStars.zip>` 

implementing service
^^^^^^^^^^^^^^^^^^^^^^

We are assuming here that you have developed a service; to count the number of `*` is a dictionary of lines.

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

definition of service
^^^^^^^^^^^^^^^^^^^^^^

A service is defined by associating a service name with an AIMMS procedure, as illustrated below:

.. image:: images/service-asscociate-proc.png
    :align: center

Remarks:

* The annotation ``dex::ServiceName`` associates the procedure ``pr_countTheStars`` with the service ``countStars``

* Lines 1-2: copy the name of the input file and output file to local string parameters.

* Line 6: Call the workhorse (see sub section above).