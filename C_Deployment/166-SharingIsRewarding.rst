:orphan:

Sharing is rewarding
===========================

.. meta::
   :description: This article shows how to create an application whereby users of that application can share files with each other.
   :keywords: cloud, sharing files, PRO storage, identify users

.. sidebar:: Sharing ideas

    .. figure:: ../Resources/C_Deployment/Images/166/Women_Inspired_Health_and_Community_2016_45.jpg
    
            Picture by: http://projectpinkblue.org/

When you want to share results derived using an AIMMS decision support application, you may want to share these in a shared folder such as AIMMS PRO storage. This article discusses an example that shows 

* how to create the authorization string,

* a potential folder structure

* save a file, and

* load that file by another user.

With whom are we sharing
--------------------------

In this section we determine with whom we are sharing our files.

.. code-block:: aimms
    :linenos:

    ! Ensure all entities are known.
    guipro::PopulateEntitySets( addUniversalSets:1 );

    ! Get the "users" group(s) and the "domain users" group(s).
    s_UserEntities :=  { guipro::PRO_ENT | 
        ( StringToLower( guipro::PROEntityName( guipro::PRO_ENT ) ) = "users"        ) or 
        ( StringToLower( guipro::PROEntityName( guipro::PRO_ENT ) ) = "domain users" )    } ;

* Line 2: This call to AIMMS PRO, via AIMMS PRO UI, is needed to retrieve the number / name / property list of all AIMMS PRO entities. AIMMS PRO entity is a group or an individual.

* Line 5 - 7: AIMMS set notation to find those groups that are called ``users`` or ``domain users``.

So what is the code to open up for our peers?
-----------------------------------------------------

Now that we identified the groups in which our peers can be found, we need to determine 

.. code-block:: aimms
    :linenos:

    sp_AuthUsersRWX := "" ;
    for i_UE do ! For each user group:
        sp_AuthUsersRWX += formatString("#7+%e", i_UE );
    endfor ;

Resource for this article
-------------------------

The enclosed example shows how to do this.

*  :download:`AIMMS project <../Resources/C_Language/Images/157/WriteOnlyAFewDatabaseTables.zip>` 

Summary
---------

In this article we showed how to 

* create a permissive authorization string

* create a folder structure on PRO storage using that authorization string

* save a file in this folder structure

* load that file by another user

Further reading
----------------

More information can be found in the following references.

* :doc:`120-pro-user-groups` 

* :doc:`117-Uploading-and-Downloading-files` 

* :doc:`115-Securing-File-Access` 

.. include:: ../includes/form.def

