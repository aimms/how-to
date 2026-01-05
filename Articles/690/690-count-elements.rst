
Counting Elements in a Set using AIMMS
================================================

In AIMMS, counting elements within a set is a fundamental operation used for data validation, 
calculating averages, or defining constraints. While there are multiple ways to achieve this, 
the two most common programmatic approaches are using the ``sum`` operator and the 
``count`` operator.

Using the Sum Operator
------------------------------

The ``sum`` operator is the most flexible aggregation tool in AIMMS. To use it for counting, 
you effectively sum the value "1" for every element that meets your criteria.

**Syntax:**

.. code-block:: aimms

    VariableOrParameter := sum( index | condition, 1 );

**Example:**
If you have a set ``Products`` with index ``p`` and you want to count how many products 
have a stock level below 10:

.. code-block:: aimms

    p_LowStockCount := sum( p | p_StockLevel(p) < 10, 1 );

* **Pros:** Highly familiar to those used to mathematical modeling; easy to pivot from a count to a total (by changing the '1' to a parameter).
* **Cons:** Slightly more verbose than dedicated counting functions.


Using the Count Operator
------------------------------

The ``count`` operator is a specialized version of the sum operator. It is designed 
specifically for tallying elements, making the code more readable and expressive.

**Syntax:**

.. code-block:: aimms

    VariableOrParameter := count( index | condition );

**Example:**
Using the same product scenario, the code becomes cleaner:

.. code-block:: aimms

    p_LowStockCount := count( p | p_StockLevel(p) < 10 );

* **Pros:** Improved readability; explicitly states the intent of the code (counting).
* **Cons:** Only used for counting; cannot be used to aggregate non-unit values.


Comparison Table
------------------------------

+-----------------+-------------------------------+--------------------------------+
| Feature         | ``sum`` Method                | ``count`` Method               |
+=================+===============================+================================+
| **Primary Use** | General aggregation           | Specifically for counting      |
+-----------------+-------------------------------+--------------------------------+
| **Logic**       | Adds '1' for each match       | Increments a counter           |
+-----------------+-------------------------------+--------------------------------+
| **Readability** | Moderate                      | High                           |
+-----------------+-------------------------------+--------------------------------+


Using the ``Card`` Function
------------------------------

If you do not need to apply a condition and simply want the total size of a set, 
the ``Card`` (cardinality) function is the most efficient method:

.. code-block:: aimms

    p_TotalSize := Card(Products);

Conclusion  
------------------------------

* Use **sum** if you are performing complex math or might change the logic to a total later.
* Use **count** for better readability when the goal is a simple tally.
* Use **Card** for the total size of a set without filters.