Refreshing an Image widget without changing the file name
=========================================================

In earlier versions, a widget on a page would be refreshed if new assignments or definition updates were applied to any of the identifiers used in the widget, *even if the actual values of those identifiers were not actually changed*. With the improvement, a widget will *only* be refreshed if the data of the identifiers in the widget *has actually changed*.

This improvement will prevent many unnecessary refreshes of widgets directly showing the data of such identifiers. However, with the image widget this improvement will cause a problem when the contents of an image file has changed and the same image file name is assigned to the identifier holding the image name of the image widget. In the old situation, because of the re-assignment of the same value, this would trigger a refresh of the widget, causing the new content of the image file to be displayed. With the improvement, the widget will not be refreshed, as the image file name remains identical.

There are two ways to address this problem:

#. Change the name of the image file every time its contents changes (but you potentially will have to manage the removal of old files that are no longer used if you are running the WebUI app for a long time)
#. Add a query parameter to the file name assigned to the identifier feeding the image widget, for example ``ImageName.jpg?ID=ImageID``, where ``ImageID`` changes each time the image is updated. This will cause a refresh of the image widget, causing the new content of the image to be downloaded.
   
    The below code is one way you can achieve that:

    .. code:: 
        
        DisplayImage := FormatString("ImageName.jpg?id=%n", ImageID); ImageID += 1;

    where ``DisplayImage`` is the string parameter used in the image widget and ``ImageID`` is a parameter. Each time the image is updated, for example using an upload widget to replace ``ImageName.jpg`` on disk with a new image, the widget automatically gets updated.The above mentioned code is called in the upload procedure.

    Please open this :download:`AIMMS project <model/ImageSwitching.zip>` to view the example.

If you are switching between a set of maybe two or three images, you could use defined query parameters for each image. This prevents downloading the image each time the image is updated.

For example, if you update the image widget with either an image of "OK" or "ERROR", you can use ``DisplayImage := "ImageName.jpg?id=OK";`` and ``DisplayImage := "ImageName.jpg?id=ERROR";`` for the respective images.
