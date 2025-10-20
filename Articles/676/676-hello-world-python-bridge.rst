Hello to the World of Python-Bridge
==========================================

This document provides a **Hello World** introduction to the AIMMS Python-Bridge, 
focusing on the core workflow where the Python application drives the communication.

In this "Python-in-the-lead" approach, the Python script will:

* Open and manage an AIMMS application instance.
* Exchange data (read and write) with the AIMMS model.
* Execute AIMMS procedures.

:download:`AIMMS 25.7 project and Python 3.13 script download <model.zip>` 

Prerequisites and Setup
-------------------------

To keep this tutorial simple and ensure reproducibility, 
we focus on using a specific Python version within an isolated virtual environment.

Tools Installation
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

If you haven't already, please install the recommended dependency management tools:

#.  ``pyenv`` (for managing Python versions): `pyenv <https://github.com/pyenv/pyenv>`_ (for Linux/macOS) or `pyenv-win <https://pypi.org/project/pyenv-win/>`_ (for Windows).
    
#.  ``uv`` (for fast package and environment management): `uv <https://docs.astral.sh/uv/>`_.

Prepare Environment
^^^^^^^^^^^^^^^^^^^^^^^^^^^

Start a PowerShell or terminal, navigate to the directory containing the Python ``main.py`` file, and 
execute the following commands:

.. code-block:: bash
    :linenos:

    pyenv install 3.13
    pyenv local 3.13
    uv init
    uv venv
    .\venv\activate

    
Remarks:

* Lines 1-2: Set Python **3.13** as the interpreter for the current directory.
* Lines 3-4: Initialize the project and create a new virtual environment (``.\venv``).
* Line 5: Activate the newly created virtual environment.

Install Dependencies
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: bash

    uv pip install -r requirements.txt


Remarks:

* By convention, ``requirements.txt`` enumerates the Python libraries necessary to install for the project at hand.
    
AIMMS Model
-----------------------

The accompanying AIMMS model, ``hello.aimms``, is intentionally simple. It defines one input array (``p_A``) 
and calculates the scalar sum of its elements, storing the result in a parameter (``p_B``).

.. figure:: images/aimms-model-tree.png
    :align: center

|

AIMMS Model Explorer showing input parameter ``p_A`` and output parameter ``p_B``.

.. figure:: images/aimms-main-execution.png
    :align: center

|

AIMMS procedure ``MainExecution`` containing a single assignment statement.

Python Script
--------------------

The Python script uses the ``aimmspy`` library to control the AIMMS session.

.. code-block:: python
    :linenos:

    # Import ingredients from aimmspy.
    from aimmspy.project.project import Project, Model
    from aimmspy.utils import find_aimms_path

    # Initialize the AIMMS project
    project = Project(

        # path to the AIMMS Bin folder  
        aimms_path=find_aimms_path("25"), 

        # Path to the AIMMS project file (../hello.aimms).
        #aimms_project_file=projectfile,
        aimms_project_file = "..\\AIMMS\\hello.aimms" 
        
        # licensing url
        # When needed, add your licensing URL here.
    )
    aimms_model : Model = project.get_model(__file__)

    # Send data to the AIMMS model
    hello_world_dict = { "hello" : 1, "world" : 2 }
    aimms_model.p_a.assign( hello_world_dict )

    # Run an AIMMS procedure
    aimms_model.MainExecution()

    # Get results back and print.
    hello_world_result = aimms_model.p_b.data()
    print(f"Hello world: sum is {hello_world_result}")

Expected Output
^^^^^^^^^^^^^^^^^^^

When the script is executed, the AIMMS session opens, data is exchanged, and 
the result is returned to Python:

.. code-block:: none
    :linenos:

    C:\Users\ChrisKuip\AppData\Local\AIMMS\IFA\Aimms\25.7.7.4-x64-VS2022\Bin --as-server "..\AIMMS\hello.aimms"
    Hello world: sum is 3.0

