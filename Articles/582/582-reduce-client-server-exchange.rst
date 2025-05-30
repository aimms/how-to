
Reduce Exchange Between Client Session and Solver Session
==========================================================

.. meta::
   :description: How to reduce the overhead of creating and communicating cases between the client session and the solver session.
   :keywords: client, server, session

As shown in :doc:`../33/33-pro-deploy-app`, part of the overhead in solving is creating a case and transferring it, once from the client session to the solver session and once back.
Especially, for short solves, the overhead can be relatively large.
To reduce this overhead, we'll reduce the number of identifiers that need to be passed between these sessions.

The Identifiers Needed by the Solver Session
----------------------------------------------

There are three groups of identifiers needed by the solver session that can be provided by the client session:

#. The sets and parameters used in defining mathematical program instances to be solved

#. The sets and parameters used in pre-processing and post-processing these mathematical programs

#. The subset of :aimms:set:`AllIdentifiers` that contains those identifiers that comprise the solution; in other words those identifiers that need to be passed back to the client session.

.. sidebar:: Constructing the subset of constraints.

    Assuming we've declared all constraints pertaining to the Flowshop model declared in the section ``Flowshop Mathematical Program Declarations``, how can we construct the corresponding subset without explicitly enumerating all these constraints? 
    
    In other words, these are the identifiers declared in the section ``Flowshop Mathematical Program Declarations`` that are also constraints. 
    
    In algebraic terms, this is the intersection of the sets, *identifiers declared in the section Flowshop Mathematical Program Declarations* and the predeclared set :aimms:set:`AllConstraints`.
    
    In AIMMS this is expressed as ``AllConstraints * Flowshop_Mathematical_Program_Declarations`` because section names can be used as sets of identifiers declared in that section.
    
    A collateral benefit is that if you want to temporarily disable a constraint, you'll only have to drag it to a section outside the section of constraints that form the optimization problem.
    

In the declaration below, the variables and constraints that make up the mathematical program ``FlowShopModel`` are the sets ``sFlowshopConstraints`` and ``sFlowshopVariables``. 

    .. code-block:: aimms

        MathematicalProgram FlowShopModel {
            Objective: TimeSpan;
            Direction: minimize;
            Constraints: sFlowshopConstraints;
            Variables: sFlowshopVariables;
        }
        Set sFlowshopConstraints {
            SubsetOf: AllConstraints;
            Definition: AllConstraints * Flowshop_Mathematical_Program_Declarations;
        }
        Set sFlowshopVariables {
            SubsetOf: AllVariables;
            Definition: AllVariables * Flowshop_Mathematical_Program_Declarations;
        }

To determine which identifiers make up the mathematical program instance we need to know which identifiers are referenced in the variable and constraint definitions. We use the function :any:`ReferencedIdentifiers` for this as follows:

    .. code-block:: aimms

        sInputIds := 
            ! Identifiers from mathematical program:
            sFlowshopConstraints + sFlowshopVariables + data { TimeSpan } + 
            ! Identifiers from the section where the solution process is coded:
            Solution_Work_horses ;                                        

        sInputIds := 
            sInputIds + ! For bounds on variables, scaling factors, etc
            ! Ensure that all identifiers referenced in these variables 
            ! and constraints are in the input case. 
            ReferencedIdentifiers( sInputIds, AllAttributeNames, recursive: 1 ) +
            ! Stash list of identifiers to be returned. 
            data { sOutputIds } ; 

Now we only to construct the set of output identifiers, i.e. those identifiers that should be in the case send from the solver session to the client session.  The client session only needs the Gantt Chart starts and durations. So that is simply:

    .. code-block:: aimms
     
        sOutputIds := data { pGCJobStart, pGCJobDuration };
  
Now we have constructed the sets of identifiers that need to be passed from the client session to the solver session (``sInputIds``) and the set of identifiers to be passed from the solver session to the client session (``sOutputIds``), we need to pass this information to AIMMS Cloud, such that ``pro::DelegateToServer`` correctly handles this. For this purpose, AIMMS Cloud declares the following sets: ``pro::ManagedSessionInputCaseIdentifierSet`` and ``pro::ManagedSessionOutputCaseIdentifierSet``.

#. The ``pro::ManagedSessionInputCaseIdentifierSet`` needs to be assigned **before** calling the procedure ``pro::DelegateToServer``, for instance as follows:

    .. code-block:: aimms
     
        pro::ManagedSessionInputCaseIdentifierSet := sInputIds ;

#. The ``pro::ManagedSessionOutputCaseIdentifierSet`` needs to be assigned **during** the execution of the solver session. For instance as follows:

    .. code-block:: aimms
     
        if pro::DelegateToServer( waitForCompletion: 1, 
            completionCallback: 'pro::session::LoadResultsCallBack' ) then  
            return 1;
        endif ;

        prDoSolve();
        pro::ManagedSessionOutputCaseIdentifierSet := sOutputIds ;

Summary
-------

In this article, we've shown how to reduce the overhead of creating and communicating cases between the client session and the solver session.

Further Reading
----------------

#. See also the section on input and output case definitions in `AIMMS Cloud documentation <https://documentation.aimms.com/pro/appl-state.html#id1>`_

#. The descriptions of the functions :any:`ReferencedIdentifiers` and :any:`SectionIdentifiers`.

You can download the example: 
:download:`2a. Flow Shop - ReducedCases <downloads/2a. Flow Shop - ReducedCases.zip>`.








