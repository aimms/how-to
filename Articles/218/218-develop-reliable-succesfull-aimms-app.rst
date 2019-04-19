How to create a reliable successful AIMMS application?
===========================================================

.. meta::
   :description: A checklist for managing the development of an AIMMS application
   :keywords: project management, application complexity, model complexity, data management, source code management, 


This is a checklist to develop a reliable successful AIMMS application. 

#. Understand the application complexity
 
    #.	Who is your audience and what is the type of purpose? Is your app a study, to be used once? Is your app a strategic app, only to be used now and then? Or is your app an operational app to be used often by a large audience?
    
    #.	Are the tasks that the model needs to perform properly described? For instance, when building a S&OP app, is the demand forecast a given, or can it be operated upon?
    
    #.	Are the tasks that the users need to execute properly described?

#. Understand the modeling complexity

    #. Which OR techniques are you going to use? (LP, MIP, NLP, MINLP)?
    
    #. Did you consider doing a literature research to find an efficient formulation?
    
    #. What are the units of measurement to be used in the application?
    
    
#. Design data management early on

    Design your data management approach. How do you manage your data? Where is input data coming from? Where do you store results or scenarios? Which data is *owned* by the application to be developed, which data is *owned* by other applications.

    #. When you use data files (XML, XLSX, CSV, or case files), how do you manage different versions? 

    #. When you use an application database, how do you handle structural changes?

#. Use source code management / version control

    Source code management and version control are essential tools for project management, quality control and collaboration. These allow you to easily and reliably react to your first feedback or collaborate as a team on one application or make intermediate releases. 
    
    You may want to go further and differentiate between Development and Master/Release branches. 
    
    And you may want use a ticketing system integrated with the source code repository. 
    
    Are your design documents part of your source code?

#. Determine at the start: single-user or multi-user

    Determine how many users and/or roles will collaborate with the same data and same application, concurrently and/or sequentially. This will influence your:

    #. data management choice (multi-user often requires application database and concurrent use greatly benefits from CDM), 
    #. need for role and permission differentiation, 
    
    #. user-interface page flows and page designs, etc. 

    #. Capacity sizing (# concurrent users, solves, peak times)

#. Estimate early resource requirements

    Early in the development start measuring memory requirements and solve durations. Also find out about likely number of concurrent users, concurrent solves and usage patterns over time (day, week, month, etc.). Together these will drive subscription sizing and, if on-premise, hardware sizing.

#. Test non-functionals early on

    Early on in the development start testing with real-life data set (sizes) to catch performance and scaling challenges early on. Include early testing on AIMMS PRO or AIMMS Cloud Platform, again to catch potential problems early on.

#. Consider automated testing

    Consider developing automated tests, for example using AIMMS’ Unit Test library. This will pay off in increased innovation speed and reduced maintenance effort, because you can quickly and accurately re-test models after changes.

#. Work incrementally with end-user feedback

    Keep your feedback loops short by giving regular demos to end users, starting early in the project. This will increase user/client happiness and reduce project effort and cost. This requires that you develop the user interface and the model concurrently. Maybe even start with the user interface and then build the model underneath. 

#. Consider End user documentation requirements

    How can each end user relate his tasks to the features and screens offered by your application?
    

.. include:: /includes/form.def

    
.. #. Do not abuse AIMMS functionality
.. 
..     We often see AIMMS app development projects run into trouble when AIMMS functionality is used beyond its intended use. This holds for AIMMS as a whole, use it for decision support apps, and for the functions inside AIMMS. When in doubt how to best achieve a desired result reach out to AIMMS User Support. 
.. 
