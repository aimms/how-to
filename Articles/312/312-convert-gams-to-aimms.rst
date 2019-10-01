Convert a GAMS Model to an AIMMS Model
=============================================
.. meta::
    :description:
    :keywords: gams, convert

To convert a GAMS model to an AIMMS model, you'll need to execute the following steps:

#. Create a new AIMMS project using AIMMS 3.14. Close AIMMS 3.14.

    A.  Download and install AIMMS 3.14 if it isn't available on your system yet.
    
        Navigate to `Download AIMMS <https://www.aimms.com/english/developers/downloads/download-aimms/>`_ and download AIMMS 3.14 using the form at the bottom of that page:
    
        .. image:: images/DownloadAIMMS314.png
            :align: center
    
    #.  After creating and restarting your project with AIMMS 3.14, it should look like:

        .. image:: images/NewAIMMS313Project.png
            :align: center

#. Copy the GAMS model file next to the ``.amb`` file created by AIMMS 3.14.

   We assume here that the GAMS model file has extension ``.gms``, for instance ``trnsport.gms``.  The screenshots of this article are made using this `GAMS model <https://www.gams.com/products/simple-example/>`_.

#. Close AIMMS. Open AIMMS 3.14 again on the existing project

#. Using *File > Open Model File*, switch the file type to ``.aim``, you can open the GAMS model

    .. image:: images/NewModelAIMTypeFiles.png
        :align: center
        
    You'll have to accept a new model.

    .. image:: images/OkTheAreYouSureNewModel.png
        :align: center
        
    Confirm that the model is converted

    .. image:: images/NotificationModelIsConverted.png
        :align: center
        
    And then choose a name to save it as an AIMMS 3 ``.aim`` file.

    .. image:: images/SaveAs30AimFile.png
        :align: center

#. Convert the AIMMS 3 model from ``.aim`` to AIMMS 3 ``.amb`` format 

    This is what see when you open the project again using AIMMS 3.14

    .. image:: images/ConvertedModelTree.png
        :align: center


    (Double-click with the ``Ctrl`` key pressed on the box before the main model named "aimms 2 upgrade", and again to fully expand the model tree.)
        
    Now we need to convert the model to ``.amb`` format as a preparation for the next step.
    AIMMS 3.14 will do this for you, after you make a model change.
    You'll also want to add the procedure ``MainTermination`` with the contents:
    
    .. code-block:: aimms

        return 1;
        
    Save and close the project. Confirm the following dialog:
    
    .. image:: images/convertModelFromAim3ToAmb.png
        :align: center

    Open this project again using AIMMS 3.14 and it should look something like the following:
    
    .. image:: images/Convert314AimTo314Amb.png
        :align: center
    
#. Convert the AIMMS 3.14 project to AIMMS 4.

    If you do not have AIMMS 4.9, download and install it, just like you may have done for AIMMS 3.14 above.

    .. image:: images/OpenProjectUsing4-9.png
        :align: center

    As usual a warning should be acknowledged: 

    .. image:: images/OkWarningConversion.png
        :align: center

#. Tailor some default settings

    The AIMMS 3.14 default for data management is to use ``.dat`` files. 
    However, you can switch to the ``.data`` files offered with newer versions of AIMMS:

    .. image:: images/settingOptionsDataManagerStyle.png
        :align: center

**Related Topics**:

* **AIMMS How-To:** :doc:`../314/314-from-dat-to-data`
