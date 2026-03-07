Nice to meet you: PYAIMMS from Python Bridge
================================================

.. image:: images/pyaimmsviewbridge.png
    :align: center
    :scale: 50 %

Download here :download:`AIMMS 25.9 project download <model/meetyou.zip>`

The Python bridge allows AIMMS applications to call Python code and exchange data with Python libraries.
This how-to demonstrates how to configure the bridge, manage dependencies, and transfer multi-dimensional identifiers between AIMMS and Python.
The example uses a simple matrix transpose to illustrate the integration steps rather than the Python logic itself.

The concept
-------------

#.  Environment management

    AIMMS uses ``uv`` to create and manage a project-specific Python environment automatically.

#.  Connection layer

    The ``aimmspy`` library provides a typed access to exposed AIMMS identifiers.

#.  Data exchange model

    Multi-dimensional identifiers are transferred as long-format DataFrames:
    
    #.  Each index becomes a column.
    
    #.  The value column is named after the identifier.

Example: Demonstrating Data Exchange
-------------------------------------------------- 

Goal: Given a square matrix :math:`P(i,j)` in AIMMS, compute its transpose :math:`P^T(j,i)` using the Python Polars.

The matrix transpose is used only to illustrate how multi-dimensional data is transferred and manipulated in Python.


Requirements
---------------------

* Python 3.10 or later

* AIMMS 25.4 or later

Steps taken with the Python files
----------------------------------

Step 1. Add the toml file
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

`uv <https://docs.astral.sh/uv/>`_ manages the Python versions and virtual environments automatically.

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
        "aimmspy>=25.3.1.8"
    ]
    [tool.uv] 
    python-preference = "only-managed"

Download here :download:`sample pyproject.toml <model/pyproject.toml>`

The ``pyproject.toml`` file defines the dependencies.

Remarks: 

*   Line 5: PYAIMMS is available from Python 3.10 onwards; here we choose Python 3.13.

For instance, to add the Python library ``Polars``, execute the command:

.. code-block:: bash
    :linenos:

    uv add polars

This creates or updates the ``.venv`` environment and adds the dependency:

.. code-block:: none
    :linenos:
    :emphasize-lines: 3

    dependencies = [
        "aimmspy>=25.3.1.8",
        "polars>=1.30"
    ]
    

Placement
""""""""""""""

Place this file in the project folder, next to the ``.aimms`` file.

Step 2. Define the Connection (pyaimms_bridge.py)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

This file defines the Python–AIMMS connection.

.. code-block:: Python
    :linenos:
    :emphasize-lines: 5,6,9

    from aimmspy.project.project import Project, Model
    from aimmspy.model.enums.data_return_types import DataReturnTypes

    project : Project = Project(
        exposed_identifier_set_name = "AllIdentifiers",
        data_type_preference = DataReturnTypes.POLARS
    )

    aimms_model : Model = project.get_model("model_stub.py")

Remarks:

*   line 5: Exposes all identifiers in the AIMMS model to Python.

*   line 6: Specifies the data format used for multi-dimensional identifiers.
    Available options are DICT, ARROW, PANDAS, and POLARS. In this example, we choose POLARS.

*   line 9: The file ``model_stub.pyi`` is the auto-generated bridge file.

    * This provides typed access to exposed identifiers.
    
    * It is regenerated when the model changes.
    
    * It should not be edited manually.

This example uses a singleton (pyaimms_bridge.py) to maintain a persistent connection during the session.

Placement
""""""""""""""

Place this file in the ``PythonScripts`` subfolder.

Step 3. Write the Logic (transpose.py)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

In AIMMS, a multi-dimensional parameter such as ``P(i,j)`` is defined over index domains ``i`` and ``j``, 
which can be interpreted mathematically as row and column indices of a matrix.

When transferred to Python via the bridge, the parameter is represented as a long-format DataFrame. 
In this representation:

*   Each AIMMS index becomes a column in the DataFrame.

*   The parameter values appear in a column named after the identifier (e.g., ``P``).

For example, ``P(i,j)`` is transferred as:

.. code-block:: none

   i   j  p
   1   1  11
   1   2  12
   ...

Thus, although ``i`` and ``j`` represent row and column indices conceptually, 
they are both columns in the Python DataFrame representation.

Similarly, the output long-format DataFrame:

.. code-block:: none

   i   j  pt
   1   1  11
   2   1  12
   ...

Transposing a matrix corresponds to swapping the index columns ``i`` and ``j``.
The Python implementation:

.. code-block:: Python
    :linenos:
    :emphasize-lines: 9,15

    from PythonScripts.pyaimms_bridge import aimms_model

    def transpose_matrix():
        """
        Transposes matrix p to pt.
        """

        # Get the DataFrame from AIMMS.
        p_df = aimms_model.p.data()

        # Swap index dimensions and rename the value column.
        pt_df = p_df.rename({'j': 'i', 'i': 'j', 'p': 'pt'})

        # Send data back to AIMMS.
        aimms_model.pt.assign(pt_df)

Remarks:

*   Line 9: Retrieves parameter ``p`` as a Polars DataFrame.

*   Line 12: Swaps the index columns and renames the value column.

*   Line 15: Assigns the result to parameter ``pt`` in AIMMS.

Placement
""""""""""""""

This file is placed in the sub-folder ``PythonScripts``.

Steps taken in the AIMMS project
---------------------------------------------------------------

Step 1. Prepare AIMMS project
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 
Add the pyaimms repository library via the Library Manager in AIMMS Developer:

.. image:: images/addrepolib.png
    :align: center

This makes the ``py::`` procedures available in the AIMMS model.


Step 2. Create the link from the AIMMS model to the Python function
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: Python
    :linenos:

    py::run_python_script("PythonScripts/transpose.py");

This command loads the Python module into the interpreter so its functions 
can be executed from AIMMS via ``py::run_python_statement``.
In the example project provided, this procedure is called during initialization.

Step 3. Execute the Python function from within AIMMS model
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. code-block:: Python
    :linenos:

    py::run_python_statement("transpose_matrix()");

This executes the Python function ``transpose_matrix``.
In the example project, this call is part of procedue ``pr_transpose``. 

Step 4. Trigger the Python Procedure from the WebUI
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The procedure ``pr_transpose`` is activated by pressing the button ``♘`` in the WebUI:

.. image:: images/transposeresult.png
    :align: center


Summary
----------------

This how-to covers: 

*   Manage dependencies with uv.

*   Connect via aimmspy using a singleton pattern to ensure a single shared connection between AIMMS and Python.

*   Transfer data using DataFrames (Polars, Pandas, etc.).

*   Call Python logic directly from AIMMS procedures.


References
-----------------

*   :doc:`Hello to the World of AIMMSPY from Python-Bridge <../679/679-hello-world-aimmspy>`

*   `PYAIMMS reference documentation <https://documentation.aimms.com/python-bridge/pyaimms/pyaimms.html>`_

.. spelling:word-list::

    aimmspy
    pyaimms
    toml
    uv