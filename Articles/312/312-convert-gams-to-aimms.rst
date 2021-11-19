Convert a GAMS Model or AIMMS 3 model to an AIMMS 4 Model
==============================================================
.. meta::
    :description: How to open and save a GAMS model as an AIMMS project.
    :keywords: GAMS, AIMMS 3, convert

You can convert GAMS files using an older version of AIMMS.

To convert a GAMS model to an AIMMS model, follow this procedure:

You can follow this procedure to convert a GAMS file to an AIMMS project:

#. Download AIMMS 3.14 and convert the .gms file to a .aim file.

#. Download current AIMMS version and convert the old AIMMS project to a modern one using ``.ams`` file.

#. Open the newly converted project in current version of AIMMS.

These steps are discussed in further detail below.

This article uses an `example GAMS model <https://www.gams.com/products/gams/gams-language#the-gams-language-at-a-glance>`_.

Converting GMS to AIM file
----------------------------

A.  If you do not have AIMMS 3.14 installed, then:

    1.  Ensure that the path ``C:\ProgramData\Paragon Decision Technology`` is linked to ``C:\ProgramData\AIMMS``.
        You can do this via the MS DOS command (with elevated rights / running as administrator):
        
        .. code-block:: none

            mklink /D "C:\ProgramData\Paragon Decision Technology" C:\ProgramData\AIMMS

    #.  Download and install :download:`AIMMS 3.14 <https://download.aimms.com/aimms/download/data/3.14/5.53/Aimms-3.14.5.53-x86.exe>`  
    
    After creating and restarting your project with AIMMS 3.14, it should look like:

        .. image:: images/NewAIMMS313Project.png
            :align: center

#.  Copy the GAMS model file next to the ``.amb`` file created by AIMMS 3.14.

    We assume here that the GAMS model file has extension ``.gms``, for instance ``trnsport.gms``.


#.  Close AIMMS. Open AIMMS 3.14 again on the existing project.

#.  Go to *File > Open Model File* and switch the file type to ``.aim``. Now you can open the GAMS model.

    .. image:: images/NewModelAIMTypeFiles.png
        :align: center
        
    Accept a new model.

    .. image:: images/OkTheAreYouSureNewModel.png
        :align: center
        
    Confirm that the model is converted.

    .. image:: images/NotificationModelIsConverted.png
        :align: center
        
    Enter a name and save it as an AIMMS 3 ``.aim`` file.

    .. image:: images/SaveAs30AimFile.png
        :align: center

Converting AIM to AMB file
--------------------------

A.  Open the project again using AIMMS 3.14.

    .. image:: images/ConvertedModelTree.png
        :align: center   

    (Double-click with the ``Ctrl`` key pressed on the box before the main model named "aimms 2 upgrade", and again to fully expand the model tree.)
    
    AIMMS 3.14 will automatically convert the AIMMS 3 model from ``.aim`` to AIMMS 3 ``.amb`` format, after you make a model change.

#.  Add the procedure ``MainTermination`` with the contents:
    
    .. code-block:: aimms

        return 1;
        
#.  Save and close the project. Confirm the following dialog:
    
    .. image:: images/convertModelFromAim3ToAmb.png
        :align: center

#.  Open this project again using AIMMS 3.14 and it should look something like the following:
    
    .. image:: images/Convert314AimTo314Amb.png
        :align: center

Converting AIMMS 3 to current AIMMS version
-------------------------------------------
A.  Downlaod and install the :download:`AIMMS version 4.9.4 <https://download.aimms.com/aimms/download/data/4.9/4.68/Aimms-4.9.4.68-x64.exe>`.

#.  Open the project with the AIMMS version 4.9.4.

    .. image:: images/OpenProjectUsing4-9.png
        :align: center

    Acknowledge the warning. 

    .. image:: images/OkWarningConversion.png
        :align: center

#.  Now you can switch the ``.dat`` files (used with AIMMS 3.14) to the ``.data`` files offered with newer versions of AIMMS. 
    
    Go to :menuselection:`Settings → Project Options... → Options with nondefault value` :

    .. image:: images/settingOptionsDataManagerStyle.png
        :align: center

    For more information about why to do this, read :doc:`../314/314-from-dat-to-data`.

.. topic:: AIMMS Support

    If you need further help, contact AIMMS Support at `support@aimms.com <mailto:support@aimms.com>`_.