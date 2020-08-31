:orphan:

Deploying internationalization of time referencing in existing projects
=========================================================================

This is a follow up of article :doc:<../362/362-internationalizing-time-referencing>`, which is called the parent article below.

AIMMS was originally developed for applications inside an organization.  The data to be presented, was to be presented according to the local timezone, including perhaps daylight time saving. 
However, organizations are becoming more globally oriented; and thus there is a need for a more global view.
to be specific:

* AIMMS projects created using AIMMS 4.73 and older use default time reference timezone ``'local'``.

* AIMMS projects created using AIMMS 4.74 and newer use default time reference timezone ``'UTC'``.

By making a modification using AIMMS 4.74 or newer to your model, you implicitly upgrade the model to AIMMS 4.74. This will not change the default way of referencing to time; still the timezone ``'local'`` is used. You can easily verify this by checking the option ``Use UTC forcaseandstartenddate``; it will be ``off``.

In this article, we will discuss how to adapt your model and your existing cases, such that they are prepared for the internationalization of time referencing. 

Running example
----------------

In this article we will reuse the running example from the parent article.

The project and sample data are created using AIMMS 4.73 in Shanghai.  The data entered is as follows:

.. code-block:: aimms

    p_Temperature(i_loc,i_Min) := data { 
        ( Shanghai, '2020-03-03 01:01' ) : 12.350,  
        ( Shanghai, '2020-03-03 04:04' ) : 13.560 
    } ;

And a case Shanghai.data is saved.

We want to upgrade the model and the case, such that time referencing can be done internationally, as described in the parent article.










