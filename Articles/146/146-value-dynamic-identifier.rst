Retrieve Value of Dynamic Identifier
======================================

.. meta::
   :description: How to use Model Edit functions to retrieve the value of a dynamic identifier.
   :keywords: model, edit, me

      .. note::

	This article was originally posted to the AIMMS Tech Blog.


With the addition of Model Edit Functions (MEF), a lot of things that were previously impossible to do with AIMMS became possible.

One simple example of something that previously was not possible is to 'dereference' an element parameter with range ``AllIdentifiers``, to get the value of the identifier that was denoted by the element parameter if this identifier was not scalar. In case the identifier that is referred to is scalar, you could use the ``ScalarValue`` intrinsic function (see `AIMMS The Function Reference <https://documentation.aimms.com/_downloads/AIMMS_func.pdf>`_).

For example, if you had an element parameter ``ep_MySelectedSourceIdentifier`` with range ``AllIdentifiers`` that points to indexed identifier ``p_SourceParameter1``, it was not possible to get the actual value of the ``p_SourceParameter1`` for a given element via this element parameter.

With MEF, it now is possible to create a procedure at runtime that retrieves the value of the identifier denoted by the element parameter. To keep this example easy, we demonstrate how you can do this for a scalar identifier, although an intrinsic function for this already exists. You can easily extend the example below to work on indexed identifiers also.

For the example, we consider the following parameters and element parameters:

.. code-block:: aimms

    DeclarationSection __unnamed {
        Parameter p_SourceParameter1;
        Parameter p_SourceParameter2;
        ElementParameter ep_SelectedSourceIdentifier {
            Range: AllIdentifiers;
        }
        Parameter p_TargetValue;
    }

     
For actually creating and referring to the runtime identifiers, we need some additional element and string parameters:

.. code-block:: aimms

    DeclarationSection MEF_Declarations {
        ElementParameter ep_RuntimeLibrary {
            Range: AllIdentifiers;
        }
        ElementParameter ep_RuntimeProcedure {
            Range: AllIdentifiers;
            Default: 'pr_DummyProcedure';
        }
        StringParameter sp_RuntimeProcedureBody;
    }
     
The ``ep_RuntimeProcedure`` element parameter must have the default attribute set, as we must run the procedure later on via the apply statement, which requires the default attribute to be set.

Now we can create a procedure, say ``pr_GetValueOfIdentifer`` that will get two arguments, the source identifier and the target identifier. This procedure will then first create a runtime procedure that does the assignment of the value of the source identifiers to the target identifier, and then execute this procedure with the apply statement. The code of the procedure is the following:

.. code-block:: aimms

    !If there already exists an identifier with the name
    !RuntimeLibrary, we must delete it first
    if "RuntimeLibrary" in AllIdentifiers then
        me::Delete('RuntimeLibrary') ;
    endif ;
    
    !Now we create the runtime library
    ep_RuntimeLibrary := me::CreateLibrary( libraryName : "RuntimeLibrary" , prefixName  : "rtl") ;
    
    !Now we create the runtime procedure
    ep_RuntimeProcedure := me::Create(
        name     : "prRuntimeProcedure" ,
        newType  : 'Procedure' ,
        parentId : ep_RuntimeLibrary ,
        pos      : 0 ) ;
    
    !Now that we have the runtime procedure identifier, we can
    !create the body for this procedure
    !
    !What we would like to have as the code for this procedure is :
    !   ep_Target := ep_Source
    !In the current procedure we know the name of the identifier
    !denoted by epSource and epTarget, so we can create the body as follows:
    sp_RuntimeProcedureBody := ep_Target + " := " + ep_Source + " ; \n" ;
    
    !Now set the body of the runtime procedure
    me::SetAttribute(
        runtimeId : ep_RuntimeProcedure ,
        attr      : 'body' ,
        txt       : sp_RuntimeProcedureBody ) ;
    
    !Now compile the runtime library
    me::Compile( ep_RuntimeLibrary ) ;
    
    !And run the procedure via the apply statement
    apply(ep_RuntimeProcedure) ;

Please note that this procedure has two arguments, ``ep_Source`` and ``ep_Target``, both of which are element parameters with range ``AllIdentifiers``. Also, the earlier mentioned additional element and string parameters for MEF could be local identifiers for this procedure.

The above procedure can now be called with the following example code:

.. code-block:: aimms

    p_SourceParameter1 := 5 ;
    ep_SelectedSourceIdentifier := 'p_SourceParameter1' ;

    pr_GetValueOfIdentifer(ep_SelectedSourceIdentifier,'p_TargetValue');

    dialogmessage("Value = " + p_TargetValue) ;

Keep in mind that the above code does not do any error checking. This means that you could try to assign the value of a string parameter to a numerical parameter, which would result in a runtime error. Other possibilities are that the body of the runtime procedure contains a syntax error, in which case the me::compile statement will result in an error. Please see the `AIMMS The Language Reference <https://documentation.aimms.com/_downloads/AIMMS_ref.pdf>`_ (section "Raising and handling warnings and errors") for more information on handling such errors with the AIMMS error handling.

A complete project containing the above source can be :download:`downloaded <downloads/ValueDynamicIdentifier.zip>` 

.. include:: /includes/form.def



