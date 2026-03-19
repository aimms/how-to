Sets for Configuration
=======================

.. image:: https://img.shields.io/badge/Zip-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/694-sets-for-configuration/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/Repository-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/694-sets-for-configuration

.. image:: https://img.shields.io/badge/AIMMS-25.9-white?style=for-the-badge&labelColor=009B00&color=00D400

.. image:: https://img.shields.io/badge/WebUI-25.9.2.8-white?style=for-the-badge&labelColor=009B00&color=00D400
    
.. meta::
   :keywords: configuration set, element parameters, named constants, refactoring, s_config, DefinesIdentifiers, application logic, best practices, Git diff, hard-coding
   :description: Shows how to declare element parameters as named constants for AIMMS configuration sets so that renaming an option requires only one change instead of modifying procedures and pages throughout the project.

Some sets in AIMMS are not data-driven, but represent configuration 
choices or application logic switches, for example:

*   steps/modes in a solution algorithm (e.g., warm start on/off, leasing allowed on/off),
*   which dataset or view to present in the UI.

If you hard-code element names like 'boat' throughout procedures and pages, 
renaming becomes tedious and causes noisy Git diffs across many files.

.. tip::

    Consider using element parameters as named constants for configuration options.

Represent each configuration option once as an element parameter constant, 
and compare against that constant everywhere else. 
Then renaming an option requires changing only a single definition.

Example: Transport Mode
---------------------------

Using AIMMS' sets and element parameters, there is an easy way 
to enable changing the names of elements in sets for configuration.

This is illustrated in the accompanying example.

Step 1 - Declare the Configuration Set:
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

First, declare the set ``s_config`` that will hold the configuration options:

.. code-block:: aimms 
    :linenos:

    Set s_config {
        Index: i_config;
    }

Step 2 - Declare the "Named Constants" for Each Option:
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Next, declare an element parameter for each configuration option:

.. code-block:: aimms 
    :linenos:

    ElementParameter ep_configCar {
        Range: s_config;
        Definition: 'car';
    }
    ElementParameter ep_configBoat {
        Range: s_config;
        Definition: 'boat';
    }

Step 3 - Define the Set from the Constants:
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

And then add the following definition to ``s_config``:

.. code-block:: aimms 
    :linenos:

    Definition: {
        { ep_configCar, ep_configBoat }
    }

Compile it all and show data of ``s_config``:

.. image:: images/s_config.png
    :align: center

|

Step 4 - Create an Element Parameter to Hold the Choice:
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Next, we are going to introduce the element parameter ``ep_transportVehicle``:

.. code-block:: aimms 
    :linenos:

    ElementParameter ep_transportVehicle {
        Range: s_config;
        InitialData: '';
    }

.. note::

    The initial data is assigned at the beginning; and ``''`` denotes the empty element.
    This indicates that the transport vehicle is not selected.

Such an element parameter will be used throughout the application, 
and can take on values ``'car'`` and ``'boat'``.

We will use it in code like:

.. code-block:: aimms 
    :linenos:
    :emphasize-lines: 4

    if ep_transportVehicle = '' then
        ep_transportVehicle := ep_configCar ;
    endif ;
    if ep_transportVehicle = ep_configBoat then
        display "Going over water" ;
    else
        display "Going via the highway" ;
    endif ;

On line 4, the element parameter is not compared directly against ``'boat'`` but against the element parameter.
By comparing ``ep_transportVehicle`` to ``ep_configBoat`` instead of the string ``'boat'``, your code becomes refactor-safe. 
If you rename ``'boat'`` to ``'vessel'``, you only update one definition, 
and the logic in your procedures remains valid and unchanged in Git.


