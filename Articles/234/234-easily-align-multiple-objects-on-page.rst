Easily align multiple objects on page=====================================
.. meta::   :description: Taking care of alignment in your user interface   :keywords: User Interface, Windows, alignment.. note::    This article was originally posted to the AIMMS Tech Blog.
.. <link>https://berthier.design/aimmsbackuptech/2013/01/25/easily-align-multiple-objects-on-page/</link>
.. <pubDate>Fri, 25 Jan 2013 13:56:23 +0000</pubDate>.. <guid isPermaLink="false">http://blog.aimms.com/?p=2560</guid>

.. figure:: images/objects-aligned.png    :align: center    Nicely aligned objects
When creating a WinUI Graphical User Interface for your AIMMS project you typically want to nicely align the different objects that are present on a page.
For example, the different buttons you have on one page should all be the same size. Furthermore, if you have multiple buttons, you might also want to have them nicely spread horizontally (i.e. have the same amount of spacing between all of the buttons) as displayed in the picture on the left.
Of course, the trivial approach for this would be that you determine the pixels where each of the objects should be placed yourself. To achieve this, you can use the position/size details displayed in the statusbar of AIMMS whenever you have selected an object on a page that is in developer mode.
Fortunately, there also exists a much easier approach in AIMMS: you can select multiple objects on a page and then right click any of them to get the align possibility in the context menu as displayed in the picture below:.. figure:: images/objects-not-aligned.png    :align: center    Aligning objects
For example, to reach the situation depicted in the first picture of this post, we want all buttons to be top-aligned. By selecting Top from the context menu, AIMMS will move all objects to the same level as the top-most object.
By selecting the same width and same height, the result will be that all selected objects will be resized to the largest width and height, respectively.
Finally, the desired situation is that the buttons are evenly spaced. By selecting spread horizontally, AIMMS will try to place the objects such that there is the same amount of spacing between each two neighboring objects. 
.. include:: /includes/form.def