Watch Wonderful WebUI Widgets
==============================

This article briefly documents the pages of the :download:`Watch WebUI Widgets <model/WatchWebUIWidgets.zip>` app. 

.. simplemaps references: 
.. https://simplemaps.com/data/world-cities

Welcome
-------

Notes the purpose of the app, the required version of AIMMS, and lists the available pages.

Map
---

Example
^^^^^^^^

OxyGem is a Oxygen distillation and bottling company in Germany.  
It has separate locations for 

#.  distillation, 

#.  pumping, and 

#.  bottling. 

Its network functioned well for years; usually demand could be met. Lately, however, due to the corona crisis, the demand for oxygen tanks has significantly grown, and nowadays there is usually unmet demand. This is a nasty and unexpected surprise to management. Unexpected because there is a significant surplus capacity at their distillation locations.

Being always treated as a friend, you're now asked to explain the unmet demands and advise on overcoming this problem.
Your partner is good at making AIMMS applications, so she created a WebUI in front of an optimization problem, to help you analyze this issue for OxyGem.

Your tools are as follows:

#.  **The widget actions**:  You can find the hamburger menui in the right upper corner of the map widget:

    .. image:: images/map-widget-menu.png
        :align: center

    It provides two actions:
    
    #.  Initialize: initialize the network and randomize the demand.
    
    #.  Solve: optimize the flows over the existing network
    
    .. tip::

        For the AIMMS application developer:

        #.  In the sample application, look for ``sp_mapWidgetActions``

        #.  `Documentation: configuring widget action menu <https://documentation.aimms.com/webui/widget-options.html#configuring-widget-actions>`_

#.  **The app settings**:



#.  **The item menus**:


In addition, the app auto colors:

* In red, those bottling stations that do not have enough oxygen to meet demand

* In orange, those pumping and distillation locations that are at their capacity.


