PRO Cloud Analytics
====================

.. meta::
   :description: Introduces the PRO Cloud Analytics AIMMS application, which reads the session and task CSV exports of the AIMMS Cloud portal and turns them into usage, workload and performance analysis.
   :keywords: AIMMS Cloud, PRO, session data, tasks table, usage analysis, performance, run time, queue time, CSV export, Data Exchange Library, WebUI

.. image:: https://img.shields.io/badge/Zip-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/pro-cloud-analytics/archive/refs/heads/main.zip

.. image:: https://img.shields.io/badge/Repository-white?style=for-the-badge&logo=github&labelColor=000081&color=1847c9
   :target: https://github.com/aimms/pro-cloud-analytics

.. image:: https://img.shields.io/badge/AIMMS-26.3-white?style=for-the-badge&labelColor=009B00&color=00D400

.. image:: https://img.shields.io/badge/WebUI-26.11.1.1-white?style=for-the-badge&labelColor=009B00&color=00D400

.. image:: https://img.shields.io/badge/AimmsDEX-26.2.2.1-white?style=for-the-badge&labelColor=009B00&color=00D400
  
Introduction
-------------
Every AIMMS Cloud account records what runs on it, and the portal lets you export two of those
tables as CSV: the sessions and the tasks. On their own they are long lists of rows. Read
together they answer the questions that come up once an account grows: which apps are actually
being used, by whom, how long the work takes, and where the time goes.

This toolkit reads both exports exactly as the portal writes them, with no editing, renaming or
cleaning up beforehand, and turns them into a month by month picture of the account.

Two exports are understood:

* **session_data:** one row per session, with the kind of session (``webui``, ``solve``,
  ``verify``), the app and its version, the user, the environment, the queue, launch and run
  times, and the CPU and memory measurements the platform recorded.

* **tasks_table:** one row per task, that is, per REST API service call, with the app, the
  service, the user, the state, the queue and run times and the return code.

Sessions and tasks stay separate tables in the model, but the application counts them together
as **runs**, split by kind. A task is simply a fourth kind alongside ``webui``, ``solve`` and
``verify``, so a single chart can answer "how much ran last month" without leaving half the work
out.

Instructions
-------------

This chapter is divided into five sections:

#. Application Preparation
#. Export Before the Portal Forgets
#. Understanding the 'Import' Page
#. Understanding the Usage Pages
#. Understanding the 'Performance' Page

Application Preparation
~~~~~~~~~~~~~~~~~~~~~~~
Download the toolkit, open ``PROCloudAnalytics.aimms`` and start the WebUI. Or, if you prefer, create an aimmspack and deploy it in our AIMMS Cloud.

What you do need are the two exports. In the AIMMS Cloud portal, export the session table and
the task table as CSV for the period you want to look at. Keep them as the portal writes them,
because the application expects the original column names.

The application holds **one export of each kind** at a time. Uploading a session file replaces
the sessions that were loaded before and leaves the tasks alone; uploading a task file replaces
the tasks. This keeps the picture consistent: what you see always comes from exactly one session
export and one task export.

Export Before the Portal Forgets
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
The portal does not keep sessions forever. **By default, sessions older than 30 days are removed
automatically**, and once they are gone there is nothing left to export. This application can
only read what the portal still holds.

That retention time is a setting, not a fixed limit. An administrator can change it under
`Configuration > Retention Settings <https://documentation.aimms.com/cloud/newportal-configuration.html#retention-settings>`__
in Account Settings. It applies to WebUI and Solve sessions, and only terminated sessions are
eligible for removal.

This has a direct consequence for how you use this toolkit. A year-over-year comparison is not
something you can produce on demand from a fresh export, because the data for last year is
already gone. If you want that kind of history, either raise the retention time before you need
it, or export on a schedule and keep the CSV files yourself.

Understanding the 'Import' Page
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
The 'Import' page has one upload box per export type. The two files have different columns,
which is why each one has its own box rather than the application guessing from the contents.

'Currently loaded' names the two files that are in the model right now, with the number of rows
each of them brought in. It is the quickest way to check that you are looking at the period you
think you are looking at. 'Clear all data' empties both.

Understanding the Usage Pages
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Three pages answer how much is being used and by whom. They differ only in what they group by,
so the same question can be asked from three angles.

Monthly
^^^^^^^^^^^^^^^^^^^^^^
Volume month by month: runs and hours, both split by kind, plus the monthly total stacked by app.
Months are the unit rather than days because a single day says little about how an environment is
used, while a month shows the shape of the year.

Apps
^^^^^^^^^^^^^^^^^^^^^^
Which apps carry the load. The first widget is a tabbed one: the same figures **by app** and **by
app version**, because multiple versions of an app are often live at the same time and the
difference matters when you are deciding what to retire.

Users
^^^^^^^^^^^^^^^^^^^^^^
Who is using them: runs and hours per user, and each user month by month.

Understanding the 'Performance' Page
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Where the time goes. Queue, launch and run time are parts of the same total, so they are
**stacked**: the height of the bar is the whole wait, and the split tells you whether the time
went into waiting for a slot or into the work itself. The longest run rides on top as a line, so
a single slow outlier does not distort the averages below it.

The same breakdown is shown per app and per service, and the monthly trend shows one line per
kind. The task success rate per service sits next to it, since a service whose rate drops is
usually worth looking at before its run time is.

Minimal Requirements
--------------------

To work with this toolkit you need AIMMS 26.3 or later, and the two CSV exports from the AIMMS
Cloud portal.

If your portal export has different column names than the ones the mappings expect, reading will
fail with a message naming the file. The mappings are plain XML in the ``Mappings`` folder and can
be adjusted.

Expected Columns
~~~~~~~~~~~~~~~~
These are the columns the two mappings read. A column the portal exports but the mapping does not
list is simply ignored, so an export with extra columns is not a problem; a **missing** column is.
Note that the two exports do not share a naming convention: sessions use ``snake_case`` and tasks
use ``camelCase``, which is why each one needs its own mapping.

**session_data**, keyed on ``name``:

.. list-table::
   :widths: 22 78
   :header-rows: 1

   * - Group
     - Columns
   * - Identification
     - ``kind``, ``description``, ``account_name``, ``environment_name``, ``user_name``,
       ``app_name``, ``app_version``, ``state``
   * - Timestamps
     - ``created_at``, ``running_at``, ``finished_at``
   * - Durations
     - ``queue_time``, ``launch_time``, ``run_time``
   * - Limits
     - ``cpu_soft_limit``, ``memory_soft_limit``, ``cpu_hard_limit``, ``memory_hard_limit``
   * - Consumption
     - ``cpu_consumption``, ``memory_consumption``
   * - Peaks
     - ``peak_cpu_usage``, ``peak_memory_usage``, ``peak_cpu_utilization``,
       ``peak_memory_utilization``

**tasks_table**, keyed on ``id``:

.. list-table::
   :widths: 22 78
   :header-rows: 1

   * - Group
     - Columns
   * - Identification
     - ``appName``, ``appVersion``, ``serviceName``, ``userName``, ``userEnvironment``,
       ``state``
   * - Timestamps
     - ``createdAt``, ``runningAt``, ``endedAt``
   * - Durations
     - ``queueTime``, ``runTime``
   * - Outcome
     - ``returnCode``, ``errorMessage``
   * - Link
     - ``sessionId``

The ``client_metadata`` column of the session export is deliberately not mapped, since it holds
JSON rather than a single value.

Release Notes
--------------------

`v1.0 <https://github.com/aimms/pro-cloud-analytics/releases/tag/1.0>`_ (26/08/2026)
   First version launched!

.. spelling:word-list::

   github
   webui
