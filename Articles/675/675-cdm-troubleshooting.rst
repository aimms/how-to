CDM Troubleshooting
==========================================================

Issues with CDM Version Upgrades
---------------------------------------

**Issue:** When upgrading from earlier versions of CDM (e.g., 25.1.x) to newer releases (25.5.x or higher), users may experience:

-   Unexpected behavior when adding or committing set elements across branches.
-   Elements that appear in the local AIMMS session but are not committed to the CDM database.
-   Errors during checkout of snapshots, such as:

    .. code-block:: none 
        
        packet buffer exhausted (8000, not continuing)

-   Snapshot checkout failing on first attempt but succeeding on the second run.
-   Inconsistent behavior depending on the CDM version used.

**Cause:** These issues may be related to:

-   Changes in the API methods and RPC format introduced in newer CDM versions.
-   Older CDM service instances still running, leading to mismatched RPC formats when applications connect.
-   Specific bugs in earlier releases of the 25.5.x line (fixed in subsequent maintenance versions).

**Solution:** Please follow the steps below.

#.  Upgrade to the latest CDM release:
    -   Many issues (e.g., element handling and checkout errors) were resolved in CDM 25.5.2.1 and 25.6.2.1.
    -   Always test against the most recent patch release of the target version.

#. Ensure no legacy CDM services are active:
    -   CDM services from older application sessions may remain running up to 5 minutes after the last connected session ends.
    -   If multiple versions of the application are running with the same external service name, they may connect to an incompatible CDM service.

#. Adapt the release process
    -   To avoid RPC mismatches, consider:
        -   Using a different CDM service name for the new release.
        -   Ensuring all older application sessions are terminated before deploying a new version.
    -   Plan for a short delay (~5 minutes) to guarantee that old services are no longer active.

#. Verification
    -   Re-run the checkout or commit procedure after confirming only the new CDM service is active.
    -   If errors persist, test in a controlled local setup (SQLite/MySQL) to check whether the issue is environment-specific. 

.. note::
    
    - Future versions of AIMMS/CDM will make it possible to check active services directly in the portal or via API.
    - When encountering similar issues, always confirm the exact AIMMS and CDM version combination in use.
    - If reproducing locally is not possible, the issue may be tied to the PRO environment configuration rather than the CDM software itself.

.. spelling:word-list::

    aimmsdb