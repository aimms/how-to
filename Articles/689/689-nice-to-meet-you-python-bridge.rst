Nice to meet you Python Bridge
==============================

The Python bridge enables AIMMS applications to leverage the power of Python libraries.

There are, however, a few small steps needed to get you on your way.
These steps are illustrated in this how-to.

example
-------

The example we're using is simple; given a square matrix, can we obtain the transpose via Python?

Step 1. Add the toml file
--------------------------

Under the hood, `uv <https://docs.astral.sh/uv/>`_ is to manage the use of Python and the virtual environment.

A minimal ``pyproject.toml`` file is as follows:

.. code-block:: none
    :linenos:
    :emphasize-lines: 9,10

    [project]
    name = "nice-to-meet-you-transpose-matrix"
    version = "0.1.0"
    description = "Good to see you again (after aimmspy)"
    requires-python = "==3.13.*"
    dependencies = [
        "aimmspy>=25.2.1.12"
    ]
    [tool.uv] 
    python-preference = "managed"
    
Action: ``uv add polars`` will add several libraries to the virtual environment, as can be seen from the following log.

.. code-block:: none
    :linenos:
    :emphasize-lines: 1,3

    PS .... meetyou> uv add polars
    Using CPython 3.13.5
    Creating virtual environment at: .venv
    Resolved 9 packages in 175ms
    Installed 8 packages in 603ms
     + aimmspy==25.2.1.12
     + numpy==2.4.2
     + pandas==3.0.0
     + polars==1.30.0
     + pyarrow==20.0.0
     + python-dateutil==2.9.0.post0
     + six==1.17.0
     + tzdata==2025.3

Besides creating and updating the virtual environment ``.venv``, uv adapted the ``pyproject.toml`` file to the following:

.. code-block:: none
    :linenos:
    :emphasize-lines: 8

    [project]
    name = "nice-to-meet-you-transpose-matrix"
    version = "0.1.0"
    description = "Good to see you again (after aimmspy)"
    requires-python = "==3.13.*"
    dependencies = [
        "aimmspy>=25.2.1.12",
        "polars>=1.30.0",
    ]
    [tool.uv] 
    python-preference = "managed"
    
uv added line 8: ``"polars>=1.30.0"``; indicating that this library should be loaded.

Step 2. Add standard python code
---------------------------------

in ``singleton.py`` the relation between the AIMMS model and the Python code is stated.

- singleton 

.. code-block:: Python
    :linenos:
    :emphasize-lines: 1,2,9,10

    from aimmspy.project.project import Project, Model
    from aimmspy.model.enums.data_return_types import DataReturnTypes

    from typing import TYPE_CHECKING
    if TYPE_CHECKING:
        from model_stub import Model

    project : Project = Project(
        exposed_identifier_set_name = "AllIdentifiers",
        data_type_preference = DataReturnTypes.POLARS
    )

    my_aimms : Model = project.get_model("model_stub.py")

Remarks:

*   line 1,2: Import essentials from aimmspy.

*   line 9: Permit the Python scripts to exchange data  with all identifiers declared in the AIMMS model.

*   line 10: Indicate which Python library is used for exchanging data of multi-dimensional identifiers with the AIMMS model.
             Currently available are DICT, ARROW, PANDAS, and POLARS.
             In this example, we choose ARROWS.

Step 3. Add specific Python code
-----------------------------------

After all this generic setup; we move on to more specific parts of the example at hand.  
How does the Python code to transpose look like?

.. code-block:: Python
    :linenos:
    :emphasize-lines: 9,15

    from singleton import my_aimms

    def transpose_matrix():
        """
        Transposes matrix p to pt.
        """

        # Get the dataframe from AIMMS.
        p_df = my_aimms.p.data()

        # Define new order and column names
        pt_df = p_df.rename({'j': 'i', 'i': 'j', 'p': 'pt'})

        # Send data back to AIMMS.
        my_aimms.pt.assign(pt_df)

Remarks:

*   Line 9: copy the data from AIMMS parameter ``p`` into Python Polars Dataframe ``p_df``

*   Line 15: and copy the data from Polars Dataframe ``pt_df`` into the AIMMS parameter ``pt``

Step 4. Prepare AIMMS project
-------------------------------------
 
4a: Add pyaimms repository library
Add repository library pyaimms

.. image:: images/addrepolib.png
    :align: center

Step 5. Make Python functions available to your AIMMS project.
--------------------------------------------------------------

.. code-block:: Python
    :linenos:

    py::run_python_script("transpose.py");

This will scan the Python script ``transpose.py``

Step 6. Code to execute a single Python call
-------------------------------------------------

.. code-block:: Python
    :linenos:

    py::run_python_statement("transpose_matrix()");
    
This statement will execute the Python function ``transpose_matrix`` once.

Step 7. Run a single Python procedure
-------------------------------------------------

After running the workhorse from the WebUI:

.. image:: images/transposeresult.png
    :align: center


Summary
----------------


References
-----------------


