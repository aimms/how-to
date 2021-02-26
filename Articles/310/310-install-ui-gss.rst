Install UI of GuardServerSession Library
=========================================

This article is a brief set of instructions to create the User Interface for working with library GuardServerSession.

After adding the library to your project, you can start building a User Interface to that library.

#.  Create page ``GSS Page``

    Properties: 
    
    #.  Maxcolumns: 12
    
    #.  Action Upon Load: ``gss::pr_openPageErrorWarningProfiler``
    
    If you have a multi page application, you probably want to add this page to the group of "developer" or "control" pages in your application.


#.  On that page create widgets:

    #.  Table ``ErrorWarningMessageTable``, 8 cols, 2 rows, titled: ``gss::sp_titleErrorWarningMessagesTable``

        Contents: 

        #.  ``gss::ep_shownJobErrorSeverity``

        #.  ``gss::sp_shownJobErrorMoments``

        #.  ``gss::sp_shownJobErrorMessages``

        Pivoting: 

        #.  Rows: ``gss::i_jobErrorMessageNumber``

        #.  Cols: <Identifiers>

        Store focus: ``gss::i_shownJobErrorMessageNumber`` to ``gss::ep_errorWarningSelectedNessage``

        Widget Extensions:

        #.  Widget actions: ``gss::sp_widgetActionMessageList``

        #.  Item actions: ``gss::sp_itemActionMessageList``

    #.  Table ``JobProfilerData``, 4 cols 2 rows titled: ``sp_titleProfilerOverview``

        Contents: ``gss::p_shownJobProfilerData``

        Store Focus:

        #.  IndexIdentifiers --> ``gss::ep_profilerDataSelectedIdentifier``

        Identifier Settings > Set slicing per index

        #.  ``gss::i_lineNumber`` --> fixed element ``'0'``
        
        Widget Extensions:

        #.  Widget actions: ``gss::sp_widgetActionIdentifierProfiler``
        
        #.  Item actions: ``gss::sp_itemActionIdentifierProfiler``

    #.  Table ``ErrorWarningStack``, 8 cols, 2 rows, titled: ``gss::sp_titleStackMessage``

        Contents:

        #.  ``gss::ep_shownJobErrorNodes``

        #.  ``gss::ep_shownJobErrorAttributes``

        #.  ``gss::p_shownJobErrorLines``

        Identifier settings:

        For all three, slice type index ``gss::i_jobErrorMessageNumber`` to element parameter ``gss::ep_errorWarningSelectedMessage``

        no decimals: 0

        Store Focus:

        #.  ``gss::i_stackPosition`` --> ``gss::ep_stackPos``

        Widget Extensions:

        #.  Widget actions: ``gss::sp_widgetActionMessageStack``

        #.  Item actions: ``gss::sp_itemActionMessageStack``

    #.  Table ``LineBasedProfilerData``, 4 cols, 2 rows, titled: ``gss::sp_titleProfilerDetail``

        Contents: 

        ``gss::p_shownJobProfilerData``

        Identifier Setttings - Set slicing per index

        #.  index ``gss::IndexIdentifiers`` --> element parameter ``gss::ep_profilerDataSelectedIdentifier``

    #.  scalar ``shownSession`` 8 cols, 1 row

        Contents: ``ep_shownSession``

    #.  upload ``UploadErrorWarningData``, 4 columns, 1 row.

        procedure: ``gss::pr_uploadErrorData``

        title: "Upload Error Warning Data"

    #.  Download ``DownloadModelLog``, 4 columns, 1 row.

        procedure: ``gss::pr_downloadModelLog``

        title: ``gss::sp_downloadModelLogTitle``

    #.  Download ``DownloadErrorReport``, 4 columns, 1 row.

        procedure: ``gss::pr_downloadErrorReport``

        title: ``"Download error report shown session"``

    #.  Download ``DownloadErrorWarningData``, 4 columns, 1 row.

        procedure: ``pr_downloadErrorData``

        title: ``"Download error data shown session"``

#.  To visually guide the adaption of the width of the columns of the tables, please run:
    
    #.  ``gss::pr_artificialDataGSSPage`` to have an artificial warning message and 
    
    #.  ``gss::pr_openPageErrorWarningProfiler`` to ensure the GSS Page tables are filled properly.
    
    And then start manually adapting the column widths such that the contents fit neatly.
    
#.  Feedback step: now the page should look as follows:

    .. image:: images/gss-page-design.png
        :align: center


#.  Status bar

    The status bar should be set to ``gss::sp_messageStatusBar``, or to a string parameter that contains this parameter.










































