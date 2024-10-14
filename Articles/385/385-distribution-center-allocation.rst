Distribution Center Allocation
==================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Distribution Center Allocation, Center of Gravity, Minimize Expected Transportation Cost, Maximize Service Level, Excel, Spreadsheet, Network object, GIS, longitude, latitude, supply chain
   :description: This example looks at some common ways to determine the location of distribution centers in a supply chain.

Direct download AIMMS Project :download:`Distribution Center Allocation.zip <model/Distribution Center Allocation.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Application%20Examples/Distribution%20Center%20Allocation

This example looks at some common ways to determine the location of distribution centers. It shows how you can determine the location of one distribution center using a center of gravity approach, the location of one distribution center minimizing the expected transportation cost, and the selection from multiple (candidate) locations to minimize the expected transportation cost or the maximum transportation time.

As inputs we use a set of demand locations with longitude and latitude coordinates and expected demand. We assume the expected transportation cost can be approximated by distance x expected demand and that the transportation time has a linear relation with distance.

The center of gravity approach is commonly used since it is very simple to calculate. It is however not minimizing the expected transportation cost, as is sometimes claimed. It just takes the weighted average of the demand locations to get a suitable location of a distribution center.

The second model uses a mathematical programming formulation to minimize the total expected transportation cost from the distribution center to the demand centers. This approach is useful when minimizing expected transportation cost is the important factor in deciding where to place a distribution center.

The third model makes a selection of one or more distribution centers from multiple candidate locations in such a way that the total expected transportation cost is minimized. 

The fourth model also makes a selection of one or more distribution centers from multiple candidate locations. With this method it is done in such a way that the maximum transportation time (distance) is minimized. This approach can be useful if not the cost, but the service level is the important factor.

The model uses Excel for importing data and contains two different data sets (The Netherlands and US) in two different workbooks. The two data sets are also available as cases from within the AIMMS example.

The results are displayed in a network object using a map as background generated via the OpenStreetMap GIS server.

Disclaimer:
Example uses OpenStreetMap as data source. For licensing and use rights, visit https://www.openstreetmap.org .

Keywords:
Distribution Center Allocation, Center of Gravity, Minimize Expected Transportation Cost, Maximize Service Level, Excel, Spreadsheet, Network object, GIS, Calculate distance based on longitude and latitude

