Rolling Horizon
===============

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Stock Model, Production Planning, Rolling Horizon, Calendar, Submodel
   :description: This example illustrates the use of horizons and calendars. 

Direct download AIMMS Project :download:`Rolling Horizon.zip <model/Rolling Horizon.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Rolling%20Horizon

This example illustrates the use of horizons and calendars. A time-based model is formulated using a calendar.

The model is solved in two ways. The first way is solving it using rolling horizon. The second way is to solve it using the calendar model. 

Rolling horizon is a method in which the model is solved in several submodels. This can be useful when the computational times of the calendar based model are very large. By solving several smaller submodels, the total computational time can be decreased.

The model that is used, is a production - stock model. The model determines how many chocolate bars need to be produced every week and how many should be stored into stock such that the demand is satisfied.

Keywords:
Stock Model, Production Planning, Rolling Horizon, Calendar, Submodel
