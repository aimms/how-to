Railway Stock
====================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: XML, network object, colors, compound set, calendar, time, tabbed page
   :description: This model illustrates the use of compound sets.

Direct download AIMMS Project :download:`Railway Stock.zip <model/Railway Stock.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Application%20Examples/Railway%20Stock


.. note:: Compound sets have been deprecated. See :doc:`../../../Articles/109/109-deprecate-compound-sets-overview`

"The Dutch Railways" (de Nederlandse Spoorwegen) performs a daily schedule on the line Amsterdam-Vlissingen (and back). Based on the estimated number of passengers there is a predication of the number of trains needed on every segment. A segment is a part of the line, between two station at which the trains can be coupled or decoupled. The goal is to find out the minimal number of trains that are needed to operate this schedule. This train schedule will run daily, so an extra condition is that the number of trains at the start of the schedule is equal to the number of trains at the end of the schedule for every station.

This model illustrates the use of compound sets. A compound set is the Cartesian product of a number of simple sets, or a subset thereof. The most important compound set in this model is equal to the Cartesian product of a set indicating a place and a set indicating a time. The elements of the compound set in this example are those combinations of time and place at which something happens: a depart or arrival of a train. For instance, one element is (Amsterdam,23:38) because at 23:38 the last train enters Amsterdam station.

Trains will never disappear. This means that the number of trains that go into a station together with the trains that were already present must be equal to the number of trains that leave the station plus the number of trains that will stay at that station. This conservation of flow holds for every node in the compound set, this is the main equation of the underlying model. 

Keywords:
XML, network object, colors, compound set, calendar, time, tabbed page

