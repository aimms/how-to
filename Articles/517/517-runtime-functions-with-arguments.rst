Runtime functions with arguments
==================================

On the one hand AIMMS functions and procedures use local identifiers to hold the values of the arguments.

On the other hand the model editor functions that form the basis of runtime libraries operate on elements of :aimms:set:`AllIdentifiers`; 
these function create, modify, and delete corresponding global identifiers in the model. 
In other words, these functions do not operate on local identifiers.

This short how-to presents a way to add local identifiers to AIMMS procedures and functions that are created at runtime.
To create local identifiers, including procedure and function arguments, first create a global declaration section, 
and, when finished, move the declaration section below the procedure or function being created.

To show what happens:
----------------------

For instance, to create a variant of Sqrt, do the following steps: 

#.  Create the function, including specifications for the attributes ``body`` and ``arguments``.  

#.  Create a declaration section with a parameter ``a``.  The parameter ``a`` gets property input.

    The model explorer for the runtime library now looks as follows.

    .. image:: images/before-decl-sec-move.png
        :align: center

#.  A call to ``me::move`` to move the declaration section below the function looks as follows:

    .. code-block:: aimms
        :linenos:

        ! Now the trick: move the declaration section local to the function.
        if not me::Move(
                runtimeId :  ep_sqrtDeclLocalFunction, 
                parentId  :  ep_sqrtFunction, 
                pos       :  0) then
            raise error "Unable to move declaration section local to function: " + CurrentErrorMessage ;
        endif ;

    The runtime library in the model explorer then looks as follows:

    .. image:: images/after-decl-sec-move.png
        :align: center
        
    .. caution:: The elements in :aimms:set:`AllIdentifiers` that used to reference the declaration section and the arguments, are no longer valid and should not be used after this statement.

#.  Finish the creation by calling the AIMMS compiler using ``me::compile``, the runtime library in model explorer looks as follows:

    .. image:: images/after-me-compile.png
        :align: center
        
    Note that the model explorer now shows the argument of the function.

#.  Subsequently, we can use the ``APPLY`` operator to call the function created.

    .. code-block:: aimms

        p_res := apply( ep_sqrtFunction, p_inp );

You can download AIMMS 4.79 example project :download:`here <model/rtlfnc.zip>`

See also:
----------

#.  `Runtime Libraries and the Model Edit Functions <https://documentation.aimms.com/language-reference/advanced-language-components/model-structure-and-modules/runtime-libraries-and-the-model-edit-functions.html>`_

#.  Instead of using functions, use macros: :doc:`Use Formulas as Data <../131/131-Formulas-as-Data>`

    *   A call to a macro inside a constraint may involve variables.

    *   With functions, assignment statements, if-then-else, while loops, and for loops can be used.




