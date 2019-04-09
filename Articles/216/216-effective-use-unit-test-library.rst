Introduction to Test Driven Development using the AIMMSUnitTest library
==========================================================================

This article uses the following elements from the reknown software methodology "Test Driven Development":

#. Listen carefully to question from stakeholders

    #. Specify requirements detailed
    
    #. Specify prototype of functions and procedures to be created
    
    #. What are small examples
    
    #. What are your edge cases, including error cases, performance
    
    #. Continue adding until you agree on ‘done’
    
    This is a group experience.
    
#. Implement requirements as unit tests and **see them fail**!

#. Develop cycle

    #. Code actual procedure
    
    #. Execute tests and **see them pass**!
    
    #. Refactor until performance is acceptable.
    
Preparation
-----------

#. In an existing project:

#. Add repository library AIMMSUnitTest

#. Create the new library that holds the actual code.

#. Create another new library containing tests on the actual code.

#. Make sure all tests can be run, for instance by specifying ``MainExecution`` as follows:

.. code-block:: aimms
    :linenos:

    Procedure MainExecution {
        Body: {
            EnvironmentSetString("aimmsunit::RunAllTests","1");
            aimmsunit::TestRunner;
        }
    }

    
Example: implement "mean"
-------------------------
 
#. Specify requirements:

    #. The mean should return the average value of a series of numbers
    
    #. Prototype SelfDefinedMean (As both ``mean`` and ``average`` are key words in AIMMS, we need to come up with a new name):
    
        .. code-block:: aimms
            :linenos:

            Function SelfDefinedMean {
                Arguments: (p);
                Body: {
                    raise error "Not implemented yet";
                    SelfDefinedMean := 0 ;
                }
                Parameter p {
                    IndexDomain: i;
                    Property: Input;
                }
                Set S {
                    Index: i;
                }
            }
    
        This is dummy implementation, a function that meets the prototype requirements, but will obviously fail.
        Having a dummy implementation allows us to code the tests as follows:
    
    #. first test: Mean( 3, 5, 13 ) = 7 
    
        .. code-block:: aimms
            :linenos:

            Procedure pr_Test_Small_Example {
                Body: {
                    S := data { a, b, c };
                    P := data { a: 3, b : 5, c: 13 };
                    r := ml::SelfDefinedMean( P(i));
                    aimmsunit::AssertTrue("The average of 3, 5, and 13 is 7.", r=7);
                }
                Comment: "first test: Mean( 3, 5, 13 ) = 7";
                aimmsunit::TestSuite: MeanSuite;
                Set S {
                    Index: i;
                }
                Parameter P {
                    IndexDomain: i;
                }
                Parameter r;
            }
    
        Note that the ``aimmsunit::AssertTrue`` statement is **after** the call to ``ml::SelfDefinedMean``.
    
    
    #. Edge case test: an empty series of numbers
    
        .. code-block:: aimms
            :linenos:

            Procedure pr_Test_Empty_List {
                Body: {
                    aimmsunit::AssertThrow("The average of an empty list cannot be computed.");
                    S := data { };
                    P := data { };
                    r := ml::SelfDefinedMean(P(i));
                }
                Comment: "Edge case, empty list.";
                aimmsunit::TestSuite: MeanSuite;
                Set S {
                    Index: i;
                }
                Parameter P {
                    IndexDomain: i;
                }
                Parameter r;
            }
            
        Note:

        #. That the ``aimmsunit::AssertThrow`` statement is **before** the call to ``ml::SelfDefinedMean``.
        
        #. That the annotation ``aimmsunit::TestSuite: MeanSuite`` is added to the test function. 
           As you may know, such an annotation can be added by:
        
            #. click add anotation in the attribute window
            
            #. select aimmsunit::TestSuite
            
            #. type in the name of the suite. In this introduction, we only use one suite: ``MeanSuite``.
   
#. Run tests

    With the above implementation of ``ml::SelfDefinedMean``, our tests will fail.
    Example result in file: ``log/AimmsUnit.xml``

    .. code-block:: xml
        :linenos:

        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <testsuites>
          <testsuite id="1" name="MeanSuite" timestamp="2019-04-09T08:26:10" tests="2" errors="2" time="0.002">
            <testcase name="tml::pr_Test_Small_Example" time="0.001">
              <error message="Not implemented yet."/>
            </testcase>
            <testcase name="tml::pr_Test_Empty_List" time="0.001">
              <error message="Not implemented yet."/>
            </testcase>
          </testsuite>
        </testsuites>

    There are several remarks about this file:

    #. On line 3, which suite and which tests are run, also important, the number of tests that failed. 
       The good news is that all tests failed, so we can start coding.

    #. On lines 4 - 9, we see the details of the failure of our two tests.

#. Code actual function

    .. code-block:: aimms
        :linenos:

        Function SelfDefinedMean {
            Arguments: (p);
            Body: {
                if card(p) then
                    SelfDefinedMean := sum( i, p(i) ) / card( p );
                else
                    raise error "The average of an empty list cannot be computed." ;
                    SelfDefinedMean := 0 ;
                endif ;
            }
            Parameter p {
                IndexDomain: i;
                Property: Input;
            }
            Set S {
                Index: i;
            }
        }

    Running the tests gives the following results:

    .. code-block:: xml
        :linenos:

        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <testsuites>
          <testsuite id="1" name="MeanSuite" timestamp="2019-04-09T09:31:16" tests="2" time="0.002">
            <testcase name="tml::pr_Test_Small_Example" time="0.001"/>
            <testcase name="tml::pr_Test_Empty_List" time="0.001"/>
          </testsuite>
        </testsuites>
        
    Everything looks ok upon first glance.
    
Fix a bug
---------

#. However, soon one of our stakeholders comes with the question why ``ml::SelfDefinedMean(3, 5, 0, 12)`` returns 6.67 instead of 5.

   Apparently, our set of requirements was not sufficiently careful written, and we will add another requirement and test:
   Last test: 0 is a possible observation, and should count in the number of observations. Ie: SelfDefinedMean(3, 5, 0, 12) = 5
    
    .. code-block:: aimms
        :linenos:

        Procedure pr_Test_Zero_In_Observations {
            Body: {
                S := data { a, b, c, d };
                P := data { a: 3, b : 5, c: 0, d: 12 };
                r := ml::SelfDefinedMean(P(i));
                aimmsunit::AssertTrue("The average of 3, 5, 0, and 12 is 5.", r=5);
            }
            Comment: "third test: Mean( 3, 5, 0, 12 ) = 5";
            aimmsunit::TestSuite: MeanSuite;
            Set S {
                Index: i;
            }
            Parameter P {
                IndexDomain: i;
            }
            Parameter r;
        }

#. And now we run our test suite again with the following results:

.. code-block:: xml
    :linenos:

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <testsuites>
      <testsuite id="1" name="MeanSuite" timestamp="2019-04-09T09:59:31" tests="3" failures="1" time="0.003">
        <testcase name="tml::pr_Test_Small_Example" time="0.001"/>
        <testcase name="tml::pr_Test_Empty_List" time="0.001"/>
        <testcase name="tml::pr_Test_Zero_In_Observations" time="0.001">
          <failure message="The average of 3, 5, 0, and 12 is 5."/>
        </testcase>
      </testsuite>
    </testsuites>
    
#. So our unit test reproduces the bug. 
   Clearly, the mistake in the above implementation is that we divided by the ``card(P)`` instead of the ``card(S)``.
   Now we should fix the actual implementation:

    .. code-block:: aimms
        :linenos:
    
        Function SelfDefinedMean {
            Arguments: (p);
            Body: {
                if card(p) then
                    SelfDefinedMean := sum( i, p(i) ) / card( S );
                else
                    raise error "The average of an empty list cannot be computed." ;
                    SelfDefinedMean := 0 ;
                endif ;
            }
            Parameter p {
                IndexDomain: i;
                Property: Input;
            }
            Set S {
                Index: i;
            }
        }
    
    And with that all unit tests run ok again.
    
    .. code-block:: xml
        :linenos:
    
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <testsuites>
          <testsuite id="1" name="MeanSuite" timestamp="2019-04-09T10:03:07" tests="3" time="0.003">
            <testcase name="tml::pr_Test_Small_Example" time="0.001"/>
            <testcase name="tml::pr_Test_Empty_List" time="0.001"/>
            <testcase name="tml::pr_Test_Zero_In_Observations" time="0.001"/>
          </testsuite>
        </testsuites>

:download:`AIMMS project download <downloads/mean/mean.zip>` 


.. include:: /includes/form.def

