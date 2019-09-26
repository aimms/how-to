Converting a GAMS model to an AIMMS model
=============================================

To convert a GAMS model to an AIMMS model, you'll need to execute the following steps:

#. Create a new AIMMS project using AIMMS 3.14. Close AIMMS 3.14

    .. image:: images/NewAIMMS313Project.png
        :align: center

#. Copy the GAMS model file next to the .amb file created by AIMMS 3.14.

   We assume here that the GAMS model file has extension ``.gms``, for instance ``trnsport.gms``.  The screenshots of this article are made using this `GAMS model <https://www.gams.com/products/simple-example/>`_.

#. Close AIMMS. Open AIMMS 3.14 again on the existing project

#. Using File - Open Model File, switch the file type to .aim, you can open the GAMS model

    .. image:: images/NewModelAIMTypeFiles.png
        :align: center
        
    You'll have to accept a new model.

    .. image:: images/OkTheAreYouSureNewModel.png
        :align: center
        
    You'll have to ok that the model is converted

    .. image:: images/NotificationModelIsConverted.png
        :align: center
        
    And then choose a name to save it as an AIMMS 3 ``.aim`` file.

    .. image:: images/SaveAs30AimFile.png
        :align: center

#. Convert the AIMMS 3 model from ``.aim`` to AIMMS 3 ``.amb`` format 

    This is what see when you open the project again using AIMMS 3.14

    .. image:: images/ConvertedModelTree.png
        :align: center
        
    .. tip:: Doube click with the ctrl key pressed, on the box before the main model named "aimms 2 upgrade", and again to fully expand the model tree.
        
    Now we need to convert the model to ``.amb`` format as a preparation for the next step.
    AIMMS 3.14 will do this for you, after you make a model change.
    A change you want to make anyway is to add the procedure ``MainTermination`` with the contents:
    
    .. code-block:: aimms
        :linenos:

        return 1;
        
    Save and close the project. You may need to OK the following dialog:
    
    .. image:: images/convertModelFromAim3ToAmb.png
        :align: center

    Ensure you can open this project again using AIMMS 3.14 and look somehting like the following:
    
    .. image:: images/Convert314AimTo314Amb.png
        :align: center
    
#. Convert the AIMMS 3.14 project to AIMMS 4.

    .. image:: images/OpenProjectUsing4-9.png
        :align: center

    As usual a warning should be acknowledged: 

    .. image:: images/OkWarningConversion.png
        :align: center

#. Tailor some default settings

    The AIMMS 3.14 default for data management is to use ``.dat`` files. 
    However, the modern ``.data`` files offer several advantages, and you may as well start using that right away:

    .. image:: images/settingOptionsDataManagerStyle.png
        :align: center

