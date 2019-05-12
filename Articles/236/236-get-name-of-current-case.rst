Get Name of an AIMMS Case
===============================

.. meta::
   :description: Using case-related AIMMS functions to call the name of a case.
   :keywords: Case, compact storage, naming, data management

.. note::

    This article was originally posted to the AIMMS Tech Blog.

.. <link>https://berthier.design/aimmsbackuptech/2012/07/19/get-name-of-current-case/</link>
.. <pubDate>Thu, 19 Jul 2012 11:45:32 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1558</guid>


This article explains how to get the name of the current case, or more generally, any case denoted by an element in the predefined set ``AllCases``. You can use the case-related AIMMS functions to achieve this.

In AIMMS you have the option to store the data for all or some identifiers in so-called AIMMS cases. Within the AIMMS language there is support for saving and loading cases. 

All of these functions work with an element parameter in the predefined set ``AllCases``, which is a subset of the set ``Integers``. 


As the predefined set ``AllCases`` is a subset of ``Integers``, you can't get the name of the current (or any other case) directly. You will have to use the string parameter ``CaseFileURL`` provided in the AIMMS language to obtain the name of a case referenced by an element in the set ``AllCases``.

In the following code, ``ep_Case`` denotes an element parameter in the set ``AllCases`` and ``sp_CaseName`` is a string parameter, which will be used for storing the name.

.. code-block:: aimms
    :linenos:

    !CurrentCase is predefined by AIMMS and denotes current case
    ep_Case := CurrentCase ;  
    if not CaseFileURL(ep_Case) then
        raise error "Could not obtain case name corresponding to case " + ep_Case ; 
    endif ; 

If successful, the above call will store the name of the case referred to by the element parameter in the string parameter that is provided as the second argument.

Please see the sections "Case Management Related Identifiers" and "Case management" in the `AIMMS Function Reference <https://documentation.aimms.com/aimms_func.html>`_ for more information about these functions.

 
.. include:: /includes/form.def


