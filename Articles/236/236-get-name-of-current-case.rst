Get Name of an AIMMS Case
===============================

.. meta::
   :description: Explains how to retrieve the name of the current or any AIMMS case using the CaseFileURL function and the AllCases predefined set.
   :keywords: case management, CaseFileURL, AllCases, CurrentCase, data management, case name, case-related functions, element parameter

This article explains how to get the name of the current case, or more generally, any case denoted by an element in the predefined set :any:`AllCases`. You can use the case-related AIMMS functions to achieve this.

In AIMMS you have the option to store the data for all or some identifiers in so-called AIMMS cases. Within the AIMMS Language there is support for saving and loading cases. 

All of these functions work with an element parameter in the predefined set :any:`AllCases`, which is a subset of the set :any:`Integers`. 


As the predefined set :any:`AllCases` is a subset of :any:`Integers`, you can't get the name of the current (or any other case) directly. You will have to use the string parameter :any:`CaseFileURL` provided in the AIMMS Language to obtain the name of a case referenced by an element in the set :any:`AllCases`.

In the following code, ``ep_Case`` denotes an element parameter in the set :any:`AllCases`.

.. code-block:: aimms
    :linenos:

    !CurrentCase is predefined by AIMMS and denotes current case
    ep_Case := CurrentCase ;  
    if not CaseFileURL(ep_Case) then
        raise error "Could not obtain case name corresponding to case " + ep_Case ; 
    endif ; 

If successful, the above call will store the name of the case referred to by the element parameter in the string parameter that is provided as the second argument.

.. seealso::

    * :doc:`fr:predefined-identifiers/case-management-related-identifiers/index` 
    * :doc:`fr:data-management/case-management/index` 

 



