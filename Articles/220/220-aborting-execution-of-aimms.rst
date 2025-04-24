Interrupting Execution During a Single Statement
================================================

.. meta::
   :description: How to interrupt a long-running assignment in AIMMS.
   :keywords: interrupt, execution, long-running, AIMMS, performance, troubleshooting

Introduction
----------------
In AIMMS, long-running executions can be interrupted using the keyboard shortcut ``CTRL + Shift + S``. However, this shortcut only works between separate executed statements, between generated constraints, or during solver iterations within a solve statement.

For cases where execution is stuck within a single statement, the AIMMS Interrupt Tool provides a more powerful way to interrupt the process.

Why Use the AIMMS Interrupt Tool?
----------------------------------
Consider the following assignment:

.. code-block:: aimms
   :linenos:

   someParameter1(i, j, k, l) := if someParameter2(i, j, k, l) <= 2 then 1 endif ;

If the number of index combinations is very large (e.g., billions of possibilities), this statement can take a long time to execute. 
The ``CTRL + Shift + S`` shortcut cannot interrupt this process because it is a single assignment. However, the AIMMS Interrupt Tool can be used to halt execution in such cases.

Key benefits of the AIMMS Interrupt Tool:

- **Interrupts long-running statements** that the shortcut cannot.

- **Prevents data loss** by allowing you to stop execution without closing AIMMS.

- **Identifies slow-performing statements** in large projects.

Using the AIMMS Interrupt Tool
------------------------------
Follow these steps to use the AIMMS Interrupt Tool:

#. **Download** the `AIMMS Interrupt Tool <https://download.aimms.com/aimms/download/data/AIMMSInterruptTool/AimmsInterrupt.exe>`_.
#. **Run** ``AimmsInterrupt.exe`` from the Windows Start Menu. This places an icon in the Windows system tray.
#. **Monitor AIMMS processes** by clicking the tray icon. This displays a list of all AIMMS processes on your computer that can be interrupted.

.. image:: images/screenshot_aimmsinterrupt.png
   :align: center

|

Best Practices & Considerations
-------------------------------

- After interrupting execution, **verify parameter integrity**. If an assignment statement was stopped midway, the affected parameter might contain incomplete or inconsistent data.
- **Optimize large assignments** by breaking them into smaller chunks or using iterative processing techniques to minimize long execution times.
- Use **profiling tools** within AIMMS to identify bottlenecks before execution gets stuck.

By integrating the AIMMS Interrupt Tool into your workflow, you can manage long-running executions more effectively and improve the performance of your AIMMS projects.
