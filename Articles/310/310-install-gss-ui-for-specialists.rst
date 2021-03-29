Install UI of GuardServerSession Library
=========================================

This article is a companion article to :doc:`Incident Handling for Organizations<../310/310-incident-handling-for-organizations>` and to 
:doc:`The GSS User Interface for specialists<../310/310-gss-ui-for-specialists>`. 
It contains a set of instructions to create the user interface for specialists working with the ``GuardServerSession`` library. 

After adding the library to your project, you can start building a user interface to that library in WebUI. 

The rest of this article details the WebUI page ``GSS Session History Management`` in this project and how you can replicate this functionality in your application. 

Sidepanel
----------

In this section, we create a sidepanel page first. This sidepanel will be used in the actual UI for specialists.

Create a side panel page with name ``sp_controls``. 
Check the ``MainProject/WebUI/webui.json`` file; the page ``sp_controls`` should have "slug": ``sp_controls_1``.  
If not, the slug needs to be renamed at two places in the ``webui.json`` file (with AIMMS closed).

The status bar of your app should be set to ``gss::sp_messageStatusBar``, or to a string parameter that contains this information.


Widgets on sidepanel
^^^^^^^^^^^^^^^^^^^^^^

We start adding widget to the side panel as follows:

#.  Scalar widget ``shownSession``, titled: ``Selection Session & Type``

    Contents: 

    #.  ``gss::ep_shownSession``

    #.  ``gss::sp_shownSessionDescription``

    Miscellaneous property "Enable multi line": 1.

#.  Upload widget ``UploadErrorWarningData``, titled "Upload Error Warning data":

    procedure: ``gss::pr_uploadErrorData``

#.  Download widget ``DownloadModelLog``, titled ``gss::sp_titleDownloadActionLog``:

    procedure: ``gss::pr_downloadActionLog``

#.  Download widget ``DownloadErrorReport``, titled "Download error report for current session": 

    procedure: ``gss::pr_downloadErrorReport``


#.  Download widget ``DownloadErrorWarningData``, titled "Download error data for current session":

    procedure: ``gss::pr_downloadErrorData``
    
Main UI for specialists
------------------------

The page ``GSS Session History Management`` has ``Action Upon Load`` set to ``gss::pr_openPageErrorWarningProfiler``.

.. tip::
    
    If you have a multi-page application, you might want to add this page to the group of "developer" or "control" pages in your application.

Make the page a grid page and give it standard layout 5, 
see `Grid Layout for beautiful and consistent looking applications <https://community.aimms.com/aimms-webui-44/grid-layout-for-beautiful-and-consistent-looking-applications-728>`_

The page extensions > side panels property should be set to ```gss::sp_sidePanel``.

Add the following Widgets
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Below are the widgets recommended to build a user interface for the ``GuardServerSession`` library.

#.  Table ``ErrorWarningMessageTable``, titled: ``gss::sp_titleErrorWarningMessagesTable``

    #. Contents: 

        #.  ``gss::ep_shownJobErrorSeverity``
        #.  ``gss::sp_shownJobErrorMoments``
        #.  ``gss::sp_shownJobErrorMessages``

    #. Pivoting: 

        #.  Rows: ``gss::i_jobErrorMessageNumber``
        #.  Cols: ``<Identifiers>``

    #. Store focus: 

        ``gss::i_shownJobErrorMessageNumber`` to ``gss::ep_errorWarningSelectedNessage``

    #. Widget Extensions:

        #. Widget actions: ``gss::sp_widgetActionMessageList``

        #. Item actions: ``gss::sp_itemActionMessageList``

    Move this widget to Area A of the grid page

#.  Table ``JobProfilerData``, titled: ``sp_titleProfilerOverview``

    #. Contents: 

        #.  ``gss::p_shownJobProfilerHits``

        #.  ``gss::p_shownJobProfilerGross``

        #.  ``gss::p_shownJobProfilerNetto``

    #. Store Focus:

        ``IndexIdentifiers`` --> ``gss::ep_profilerDataSelectedIdentifier``

    #. Identifier Settings > Set slicing per index

        ``gss::i_lineNumber`` --> fixed element ``'0'``
    
    #. Widget Extensions:

        #.  Widget actions: ``gss::sp_widgetActionIdentifierProfiler``
    
        #.  Item actions: ``gss::sp_itemActionIdentifierProfiler``

    Move this widget to Area B of the grid page


#.  Table ``ErrorWarningStack``, titled: ``gss::sp_titleStackMessage``

    #. Contents:

        #.  ``gss::ep_shownJobErrorNodes``
        #.  ``gss::ep_shownJobErrorAttributes``
        #.  ``gss::p_shownJobErrorLines``

    #. Identifier settings:

        For all three, slice type index ``gss::i_jobErrorMessageNumber`` to element parameter ``gss::ep_errorWarningSelectedMessage``

    #. no decimals: 0

    #. Store Focus:

        ``gss::i_stackPosition`` --> ``gss::ep_stackPos``

    #. Widget Extensions:

        #.  Widget actions: ``gss::sp_widgetActionMessageStack``
        #.  Item actions: ``gss::sp_itemActionMessageStack``

    Move this widget to Area C of the grid page

#.  Table ``LineBasedProfilerData``, titled: ``gss::sp_titleProfilerDetail``

    #. Contents: 

        #.  ``gss::p_shownJobProfilerHits``

        #.  ``gss::p_shownJobProfilerGross``

        #.  ``gss::p_shownJobProfilerNetto``

    #. Identifier Settings - Set slicing per index

        index ``IndexIdentifiers`` --> element parameter ``gss::ep_profilerDataSelectedIdentifier``

    Move this widget to Area D of the grid page


Testing
-----------

To visually guide the adaption of the width of the columns of the tables, please run:
    
    #.  ``gss::pr_artificialDataGSSPage`` to have an artificial warning message and 
    
    #.  ``gss::pr_openPageErrorWarningProfiler`` to ensure the ``GSS Session History Management`` tables are filled properly.
    
    And then start manually adapting the column widths such that the contents fit neatly.








































