Monitoring Memory Usage
=========================

.. meta::
   :description: How to analyze and monitor application memory usage.
   :keywords: card, cardinality, memory

      .. note::

	This article was originally posted to the AIMMS Tech Blog.


Analytic applications may involve a lot of data and subsequently a lot of computer memory. AIMMS hides the technicalities related to memory management from the model developer. These technicalities include, for instance, the allocation and deallocation of memory for individual data items. Still, the memory usage of applications created with AIMMS grows as the amount of data related to these applications grows. At some point during model development, the memory usage of your application becomes interesting. AIMMS offers tools to monitor and investigate the memory usage of your application. This blog post will delve into some of these tools.  One of the tools you can use to monitor the memory usage of your application is the Identifier Cardinality tool (available via the AIMMS menu Tools - Diagnostic Tools - Identifier Cardinalities). Even with small sample data sets, this tool helps you identify candidates worthwhile of investigation; in particular those with:

* high density, say more than 10%
* high dimension, say three or more
* index domain referencing sets that grow when data sets grow

Consider the following screen shot of the Identifier Cardinalities tool for a Gate Assignment example.

.. figure:: /images/Data-Cardinalities-2.png

    Data Cardinalities


The parameter "BothFlightsPossibleOnGate" has a density of 30%, and it will grow quadratically with the number of flights handled for the gate assignment. When such an identifier is located, you can choose to handle it according to your application's needs and requirements. The set AllGeneratedMathematicalPrograms is another handy tool you can use to monitor memory use. When this set grows, there are an increasing number of generated mathematical programs managed by your application, and you may want to reclaim the memory they occupy using the intrinsic function gmp::instance::delete. The third, and last, tool discussed in this blog post is the intrinsic function ``MemoryInUse``. This function returns the number of Mbs of memory in use by your application as reported by the operating system. An extended example is provided below. In this example, a procedure is repeatedly executed and we sample the memory usage of the application after every n executions. The pattern for the memory monitor procedure is:

.. code-block:: none

    Register memory usage at first iMeasurePoint.
    for ( iMeasurePoint | except the first ) do

         Iterate the procedure of interest N times.
         Register memory usage at iMeasurePoint.

    endfor ;


When the registered memory usage continues to increase, there can be a memory leak. Memory leaks commonly occur when the cardinality of an identifier increases. Use the function Card to identify the identifier(s) that keep increasing their size. Another possible cause is that AIMMS only reclaims memory of strings and sets that are elements of indexed sets whenever AIMMS checks whether they are still in use. Such a memory reclaim process is also known as garbage collection in computer science literature. AIMMS will reclaim that memory at selected moments, but you can enforce this by executing the REBUILD statement. In the enclosed example, a small mathematical program is solved 10000 times, registering the occupied memory every 100 times. This results in a flat memory usage curve:


.. figure:: /images/Used-memory.png

    Memory used
    
.. "D:\u\s\How to\gitlab\master\Resources\C_Language\Images\134\MemoryUsageTest_converted.zip"

The example can be replayed via the AIMMSpack presented below.  :download:`AIMMS project download </downloads/MemoryUsageTest_converted.zip>`  


.. include:: /includes/form.def


