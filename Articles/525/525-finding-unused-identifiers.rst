.. |search-icon| image:: images/search-icon.png

.. |search-all-icon| image:: images/search-all-icon.png

Finding unused identifiers
============================

Applications that are around for a while clearly have value to the organization.
However, some identifiers such as sets, parameters, variables, and procedures in that application may no longer be used, because:

#.  They were created for analyzing and testing a particular circumstance. 
    Once the circumstance is handled, the corresponding identifiers can be removed.

#.  As the application evolves, some functionality may become obsolete.

There are some disadvantages of having obsolete identifiers in the application:

#.  It may confuse your successor, as he/she wonders why an obsolete identifier was added.

#.  For a defined parameter or set, it may take up a significant amount of memory and thereby also 
    increase the size of cases.

Significant applications have a multitude of identifiers.  
Therefore, it is not easy to identify those identifiers that are obsolete.

In this article, a small library is presented that helps identify the identifiers that are no longer used.
This library consists of three parts, each checking the use of identifiers in:

#.  the model.

#.  the WinUI.

#.  the WebUI.

But before presenting the library itself, lets introduce the example, 
and how the library is going to be used,
to put the code presented into context.

Example used in this article
-----------------------------

The code illustrated in this article is based on an artificial model whereby:

#.  A little bit of code is in the main model.

#.  A few objects are created on a WinUI page.

#.  A few widgets are created on a WebUI page.

To :download:`this AIMMS 4.81 project download <model/fi.zip>` 

Most of the code is in a small library ``refIds`` that can be copied to your project.
In addition, the zip file contains the file ``fu.ams``, which is an example of using the library.

It is easy to copy a library and a section, as explained in these two references:

*   Adding a library to your project: `Library projects and the library manager <https://documentation.aimms.com/user-guide/introduction-to-aimms/collaborative-project-development/library-projects-and-the-library-manager.html>`_ 

*   Adding code from a section: :doc:`../145/145-import-export-section`

Using the library in your application
-------------------------------------

An AIMMS application consists of at least a model, and may also contain a WinUI user interface, and may contain a WebUI user interface.

To search for identifiers in these three major software components is different.
That is why the library ``refIds`` has a procedure that has 

#.  As input a set of identifiers for which we would like to know whether they are used or not.

#.  As output a binary parameter that indicates whether or not it is used in the model.

#.  As output a binary parameter that indicates whether or not it is used in the WinUI interface.

#.  As output a binary parameter that indicates whether or not it is used in the WebUI interface.

Creating the set of identifiers that need to be checked for usage
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

^An example of creating the input set is as follows:

.. code-block:: aimms
    :linenos:

    s_setSearched := 
        ( Main_fi - Test_section_find_identifiers ) 
        * 
        ( AllProcedures + AllParameters + AllSets ) ;
        
Remarks:

#.  Line 2, the sections to be included and excluded; you may want to add application specific libraries here.
    You don't want system libraries such as AIMMS PRO and AIMMS WebUI to be included here; these libraries are read only anyway.
   
#.  Lines 3-4: the selection of identifier types.  
    Likely you don't want to include all identifier types, but if  you do, just omit this intersection.

Creating the subset of obsolete identifiers
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Once the use of an identifier in the model, the WinUI, and the WebUI is determined; 
it is straightforward to 
determine the collection of unused identifiers, as illustrated below:

.. code-block:: aimms
    :linenos:

    s_unused := { i_ci | 
        ( not bp_isUsedInModel( i_ci ) ) and 
        ( not bp_isUsedInWinUI( i_ci ) ) and 
        ( not bp_isUsedInWebUI( i_ci ) )     };

Localizing an identifier
^^^^^^^^^^^^^^^^^^^^^^^^^^

By clicking on the model explorer, you make sure that the icon bar of the AIMMS IDE is focused on supporting model development. 

#.  To find the declaration in the model of a particular identifier, use the icon |search-icon| or use the AIMMS Menu command ``Edit > Find...``.

    Then type the name of an identifier you want to find, for instance ``p_sqr`` in the example model:

    .. image:: images/search-dialog.png
        :align: center

    Press the declaration button, and then the model explorer will open at the declaration of the identifier searched:

    .. image:: images/model-tree-at-searched-identifier.png
        :align: center
        
    An alternative is to use the identifier info dialog invoked by the hotkey F11.

#.  To find all references of an identifier, use the icon |search-all-icon| or use the AIMMS Menu Command ``Edit > Find All...``.
    follow the dialog to obtain all references of that identifier in the model.


Are identifiers used in the model?
----------------------------------

In this section, the AIMMS code is discussed that checks whether identifiers are used in the model.

The predeclared function :any:`ReferencedIdentifiers` is key to finding which identifiers are referenced by 
a certain identifier.  
Once this information is stored, it can be transposed, and thus the identifiers that are used in the model can be identified.  See the following code fragment:

.. code-block:: aimms
    :linenos:

    s_attributesSearched := AllAttributeNames - data { interface, comment } ;
    for IndexIdentifiers do
        _s_helper := { IndexIdentifiers } ;
        _s_referencedIdentifiers := 
            ReferencedIdentifiers(
                searchIdentSet :  _s_helper, 
                searchAttrSet  :  s_attributesSearched, 
                recursive      :  0);
        _s_addedSets := {} ;
        for _i_ri | IdentifierType( _i_ri ) = 'index'  do
            _ep_referencedSet := indexRange( _i_ri ) ;
            if _ep_referencedSet in AllIdentifiers then
                _s_addedSets += _ep_referencedSet ;
            endif ;
        endfor ;
        _s_referencedIdentifiers += _s_addedSets ;
        is_uses( IndexIdentifiers ) := _s_referencedIdentifiers ;
    endfor ;
    bp_uses( i_fromId, i_toId ) := i_toId in is_uses( i_fromId );
    bp_isUsedInModel( i_ci ) := exists( i_fromId | bp_uses( i_fromId, i_ci ) );
    
There are some remarks regarding the above code:

#.  Line 1: Referencing an identifier in the attributes ``interface`` or ``comment`` 
    does not make the identifier "used" in the application.

#.  Line 2: Even though the procedure that does the checking only reports on a limited number of identifiers,
    the searching for an identifier use is over all the identifiers. 

#.  Lines 4-8: Key in the search is the AIMMS intrinsic function :any:`ReferencedIdentifiers`.

#.  Lines 9-16: When an index is used, the usual range of that index is the set in which it is declared. 
    Thus using an index, the corresponding set is considered as being used as well.

#.  Line 17: Firstly, we store per identifier, a set of identifiers that it uses.

#.  Line 19: The indexed set is converted to a binary parameter, a referenced identifier incidence matrix.

#.  Line 10: For each of the identifiers of interest, we check whether it is present in the referenced identifier incidence matrix.

Caveats
^^^^^^^^^^^^^^

#.  The above code omits identifiers that are used as an element in the set :any:`AllIdentifiers`.
    For instance, in the following declaration of an element parameter, the procedure ``pr_myProc`` is used 
    to determine the signature of procedures that can be assigned to element parameter ``ep_someProc`` and 
    subsequently be applied.

    .. code-block:: aimms
        :linenos:

        ElementParameter ep_someProc {
            Range: AllProcedures;
            Default: 'pr_myProc'
        }

#.  The identifiers referenced in the annotation are not found.  For instance:

    .. code-block:: aimms
        :linenos:

        Parameter p_a {
            webui::UponChangeProcedure: pr_uponChangeA;
        }

    Finding the references of ``p_a`` will not find ``pr_uponChangeA``, 
    because ``webui::UponChangeProcedure`` is not an element of ``AllAttributeNames``.
    
.. See also customer ticket 4364


Are identifiers used in the WinUI?
----------------------------------

In this section, the AIMMS code is discussed that checks whether identifiers are used in the WinUI interface, if any.

To mark all identifiers used on WinUI pages, the functions :any:`PageGetAll` and :any:`PageGetUsedIdentifiers` can be used. This is straightforward implemented in the code below:

.. code-block:: aimms
    :linenos:

    empty bp_isUsedInWinUI ;
    _bp_pga := PageGetAll( _s_pages );
    if _bp_pga then
        for _i_pg do
            sp_pageName := formatString( "%e", _i_pg );
            _bp_pgui := PageGetUsedIdentifiers( sp_pageName, s_identifiersOnPage );
            if not _bp_pgui then
                raise warning "Obtaining identifiers from page " + sp_pageName + " failed: " + CurrentErrorMessage ;
            endif ;
            bp_isUsedInWinUI( i_iop ) := 1;
        endfor ;
    endif ;

Remarks:

#.  Line 10: The index ``i_iop`` is an index in the set ``s_identifiersOnPage``.

Are identifiers used in the WebUI?
----------------------------------

In this section, the AIMMS code is discussed that checks whether identifiers are used in the WebUI interface, if any.

There are no special functions to visit all identifiers in the WebUI.  
However, since AIMMS 4.67, the WebUI is presented in just one file. 
So by reading that file, and subsequently searching for the presence of each identifier of interest in that file, we can determine whether or not that identifier is used in the WebUI.

.. code-block:: aimms
    :linenos:

    empty bp_isUsedInWebUI ;
    _sp_fn := ".\\MainProject\\WebUI\\webui.json" ;
    if FileExists( _sp_fn ) then
        _sp_webuiText := FileRead( _sp_fn );
        bp_isUsedInWebUI( i_ci ) := 1 $ FindString( _sp_webuiText, i_ci, caseSensitive: 0, wordOnly: 1 );
    endif ;

Caveat
^^^^^^
As the above code is based on a simple text search, when an identifier is used in a title displaying data from other identifiers, then this may lead to a false positive.
