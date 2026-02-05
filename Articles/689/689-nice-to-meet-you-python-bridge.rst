Nice to meet you Python Bridge
==============================

The Python bridge enables AIMMS applications to leverage the power of Python libraries seamlessly. 
To integrate Python into your application, follow these steps to set up your environment and exchange data.

Example Scenario
--------------------

Goal: Given a square matrix $P(i,j)$ in AIMMS, obtain the transpose $PT(j,i)$ using the Python Polars library.

Step 1. Add the toml file
--------------------------

AIMMS uses `uv <https://docs.astral.sh/uv/>`_ to manage Python versions and virtual environments automatically.

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

The ``pyproject.toml`` files registers the dependencies.

For instance, to add the Python library ``Polars``, execute the command:

.. code-block: bash
    :linenos:

    uv add polars

This will create / update the virtual environment ``.venv``, and change the depencies list to:

.. code-block:: none
    :linenos:
    :emphasize-lines: 3

    dependencies = [
        "aimmspy>=25.2.1.12",
        "polars>=1.30.0"
    ]
    

Step 2. Define the Connection (singleton.py)
-----------------------------------------------

This file defines how AIMMS and Python talk to each other.

.. code-block:: Python
    :linenos:
    :emphasize-lines: 9,10

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

*   line 9: Permit the Python scripts to exchange data  with all identifiers declared in the AIMMS model.

*   line 10: Indicate which Python library is used for exchanging data of multi-dimensional identifiers with the AIMMS model.
             Currently available are DICT, ARROW, PANDAS, and POLARS.
             In this example, we choose POLARS.

Step 3. Write the Logic (transpose.py)
---------------------------------------

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
 
Add the pyaimms repository library via the Library Manager in AIMMS Developer:

.. image:: images/addrepolib.png
    :align: center

Step 5. Create the link from the AIMMS model to the Python function
-------------------------------------------------------------------

.. code-block:: Python
    :linenos:

    py::run_python_script("transpose.py");

This will scan the Python script ``transpose.py`` make the function ``transpose_matrix`` available.

Step 6. Execute the Python function from within AIMMS model
--------------------------------------------------------------

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

In this how-to the following is covered:

*   Manage dependencies with uv.

*   Connect via aimmspy in a singleton pattern.

*   Transfer data using DataFrames (Polars, Pandas, etc.).

*   Call Python logic directly from AIMMS procedures.


References
-----------------

*   :doc:`Hello to the World of AIMMSPY from Python-Bridge <../679/679-hello-world-python-bridge>`

*   `PYAIMMS reference documentation <https://documentation.aimms.com/python-bridge/pyaimms/pyaimms.html>`_

