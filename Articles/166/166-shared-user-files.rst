:orphan:

Sharing is rewarding
===========================

.. meta::
   :description: This article shows how to create an application whereby users of that application can share files with each other.
   :keywords: cloud, sharing files, PRO storage, identify users

.. sidebar:: Sharing ideas

    .. figure:: images/icons8-cloud-user-group-512.png
    


When you want to share results derived using an AIMMS decision support application, you may want to share these in a shared folder such as AIMMS PRO Storage. This article discusses an example that shows 

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
    ! Examples of entities are the users and the groups.
    guipro::PopulateEntitySets( addUniversalSets:1 );

    ! Get the "users" group(s) and the "domain users" group(s) into an AIMMS set.
    ! Note that s_UserEntities is a subset of the set guipro::PROEntity 
    s_UserEntities :=  { guipro::PRO_ENT | 
        ( StringToLower( guipro::PROEntityName( guipro::PRO_ENT ) ) = "users"        ) or 
        ( StringToLower( guipro::PROEntityName( guipro::PRO_ENT ) ) = "domain users" )    } ;

* Line 3: This call to AIMMS Cloud, via AIMMS PRO UI, is needed to retrieve the number / name / property list of all AIMMS Cloud entities. AIMMS Cloud entity is a group or an individual.

* Line 7 - 9: AIMMS set notation to find those groups that are called ``users`` or ``domain users``.

So what is the code to open up for our peers?
-----------------------------------------------------

Now that we identified the groups in which our peers can be found, we can construct an authorization string that permits a user in any of these two groups access.

.. code-block:: aimms
    :linenos:

    sp_AuthUsersRWX := "" ;
    for i_UE do ! For each user group:
        sp_AuthUsersRWX += formatString("#7+%e", i_UE );
    endfor ;
    
In 

Resource for this article
-------------------------

The enclosed example shows how to do this.

*  :download:`AIMMS project <downloads/SharingIsRewarding.zip>` 

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

* :doc:`../120/120-pro-user-groups` 

* :doc:`../117/117-Uploading-and-Downloading-files` 

* :doc:`../115/115-Securing-File-Access` 



