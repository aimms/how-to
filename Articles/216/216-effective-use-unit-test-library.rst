Test Driven Development using the AIMMSUnitTest Library
==========================================================================

This article uses the following elements from the popular software methodology `Test Driven Development <https://en.wikipedia.org/wiki/Test-driven_development>`_

#. Gather requirements from stakeholders
   
   * What are small examples ?
   
   * What are the edge cases, including error cases, performance ?
    
#. Implement requirements as unit tests and **see them fail!**

#. Development

   #. Write the code for the functions
   
   #. Execute tests and **see them pass!**
   
   #. Refactor until performance is acceptable.
#. Repeat
    
Unit tests in AIMMS projects
---------------------------------

Prepare your AIMMS project with the below steps to declare unit tests.

#. Add repository library ``AIMMSUnitTest``

#. Create a new library that holds the actual code

#. Create another new library containing tests on the actual code

#. Make sure all tests can be run, for instance by specifying ``MainExecution`` as below:

   .. code-block:: aimms

      Procedure MainExecution {
         Body: {
            EnvironmentSetString("aimmsunit::RunAllTests","1");
            aimmsunit::TestRunner;
         }
      }

    
Example: implement "mean"
---------------------------
 
Prototype the requirements
""""""""""""""""""""""""""""""

#. The mean should return the average value of a series of numbers

#. Prototype ``SelfDefinedMean`` (As both ``mean`` and ``average`` are key words in AIMMS, we need to come up with a new name):

   .. code-block:: aimms

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

This is a dummy implementation, a function that meets the prototype requirements, but will obviously fail. Having a dummy implementation allows us to code the tests as below. 

Write the tests
"""""""""""""""""""

#. Small example test: Mean( 3, 5, 13 ) = 7 

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

   Note that the ``aimmsunit::AssertTrue`` statement (line 6) is **after** the call to ``ml::SelfDefinedMean``.


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
      
   Note that the ``aimmsunit::AssertThrow``(line 2) statement is **before** the call to ``ml::SelfDefinedMean``.

The annotation ``aimmsunit::TestSuite: MeanSuite`` is added to the test function. As you may know, such an annotation can be added by:
   
   #. click add annotation in the attribute window
   
   #. select aimmsunit::TestSuite
   
   #. type in the name of the suite. In this example, we only use one suite: ``MeanSuite``
   
Now, run the tests and with the above implementation of ``ml::SelfDefinedMean``. They will fail as expected. Example result in file: ``log/AimmsUnit.xml``

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

   #. On line 3, which suite and which tests are run, also important, the number of tests that failed. All the tests failed as expected (`errors ="2"`) and we can start coding the function now.

   #. In lines 4 - 9, we see the details of the failure of our two tests. As the function hasn't been implemented yet, it raised an error message in both the tests. 

Code the function
""""""""""""""""""""

Mean is calculated by dividing the sum of the records by the count of records. This is implemented in the code below: 

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

Running the test now gives the following results:

.. code-block:: xml
   :linenos:

   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <testsuites>
      <testsuite id="1" name="MeanSuite" timestamp="2019-04-09T09:31:16" tests="2" time="0.002">
      <testcase name="tml::pr_Test_Small_Example" time="0.001"/>
      <testcase name="tml::pr_Test_Empty_List" time="0.001"/>
      </testsuite>
   </testsuites>
        
The log indicates that both the tests passed without any issue. So, everything is good to go. Or is it? 
    
Fix a bug
"""""""""""

However, soon one of our stakeholders comes with the question 

.. pull-quote::

   Why does ``ml::SelfDefinedMean(3, 5, 0, 12)`` return 6.67 instead of 5 ?

Apparently, our set of requirements does not consider all edge cases and now we will iterate on this by adding another requirement and test: 

.. pull-quote::
   
   0 is a possible observation, and should count in the number of observations. So, ``SelfDefinedMean(3, 5, 0, 12) = 5``
    
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

Running the test suite again gives the below result:

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
    
Our unit test reproduces the bug. See `failures="1"` in line 3. Notice the difference between failures and errors in the test report. Clearly, the mistake in the above implementation is that we divided by ``card(P)`` - the cardinality of the parameter which only counts non default values instead of ``card(S)`` - the cardinality of the set which counts all the indices. So, the function is updated as below:

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

Running the test suite now should give the below result which indicates that the problem was fixed. 
    
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
        
All the previously written tests (before this latest change) were also automatically run, saving us time and effort. The example project can be downloaded below:

:download:`AIMMS project download <downloads/mean/mean.zip>` 

Related
--------------------

* **AIMMS Documentation:** `Unit Test Library <https://documentation.aimms.com/unit-test/index.html>`_
* :doc:`../84/84-using-libraries`

.. include:: /includes/form.def

