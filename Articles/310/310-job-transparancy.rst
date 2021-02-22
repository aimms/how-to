Incident handling for organizations
=============================================

Thanks to extensive checking and clear reporting of incidents; end-users can handle several incidents themselves.
For other incidents, they may need to turn to their colleagues; asking these colleagues questions about the incidents similar to the following:

#.  `Do you know, why I was not able to read the data?`

    The causes are several, for instance: it might be a reconfiguration of the database, a broken internet connection, or something else entirely.

    The colleague that helps the end-user with this question, may very well benefit from clear error messages.

#.  `Do you know, why I had to wait a lot longer before the solution appeared?`

    Again, the causes are several, for instance: slow internet connection used to obtain data, 
    the solution algorithm needed significantly more time for this instance, or something else entirely.

    The colleague that helps the end-user with this question, may very well benefit from profiling information.

#.  `I cannot explain the results I'm now seeing. Can you?`

    It is pointless to even begin to enumerate the potential causes.

    The colleague that helps the end-user with this question, may very well benefit from a log of the actions taken by the end-user.

Together, the error information, the profiling information, and the action log will be called the session history in the below.
The purpose of the session history is to reduce the time and effort for resolving the incidents that occurred during the session and 
which the end-user didn't handle.

The information flow around the application now looks as follows:

.. image:: images/session-history.png
    :align: center

The `GuardServerSession` library, with prefix `gss`, is designed, among others, to work with session histories.
This library provides the following methods:

#.  :doc:`Error and Profiling Results as Data<../310/310-errors-as-data>`

    The error handling procedures of the `GuardServerSession` library turn incidents into data that is stored in AIMMS' sets and parameters.
    The function :aimms:func:`ProfilerCollectAllData` does the same for profilerdata.
    This article discusses:

    #.  the data flow of this information, and how it can be shared, and

    #.  how the application needs to be adapted to retrieve this information.

#.  :doc:`Tracing<../497/497-tracing-procedures>`  

    The action logs are stored in files with the extension `.actionLog`.
    This articles discusses:

    #.  where the `.actionLog` files are store in AIMMS PRO Storage, and

    #.  how the application needs to be adapted to generate these logs.

A User Interface for the `GuardServerSession` library can be built following these :doc:`instructions<../310/310-install-ui-gss>`






 





