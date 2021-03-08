Install GSS interface for end users
=====================================

This article is a companion article to :doc:`Incident Handling for Organizations<../310/310-incident-handling-for-organizations>` and to 
:doc:`The GSS End User interface for end users<../310/310-gss-ui-for-end-users>`.

In this article it is explained how to create the dialog page in which the end user can submit an incident report.

#.  Create a dialog page, preferably below the page GSS Session History Management, named ``GSS Incident report``.
    The set ``webui::AllDialogPages`` should now contain ``gss_incident_report``.

    If not, you may want to change the webui.json file by replacing the new element you see with ``gss_incident_report``.

#.  The size of the dialog should be ``medium``.

#.  On this page, create a scalar widget, named  ``IncidentSummary``

    #.  size: 4 columns, 2 rows.

    #.  contents: ``gss::sp_incidentSummarySuppliedByEndUser``.

    In the miscellaneous properties tab, set:

    #.  Contents.labels.visible to 0

    #.  Enable Multi-line to 1

    #.  Title to ``gss::sp_incidentSummaryTitle``