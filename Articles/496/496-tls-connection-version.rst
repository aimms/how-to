Error Message "tlsv1 alert protocol version"
===========================================================

Symptom
--------

An error message with text: 

.. code-block:: none

    SSL_connect:SSL_ERROR_SSL_336032814:SSL routines:SSL23_GET_SERVER_HELLO:tlsv1 alert protocol version
    
This indicates an error in setting up a connection whereby there is a mismatch in the transport layer security protocol.


Explanation
------------

The `transport layer security (TLS) <https://en.wikipedia.org/wiki/Transport_Layer_Security>`_ is designed to provide communication security.
When a server secures its connection using TLS 1.3, the client needs to be able to handle TLS 1.3. 

Various versions of TLS, including the advantages of version 1.3, are further detailed in:

#.  By SSL in their `blog <https://www.ssl.com/blogs/need-know-tls-1-3>`_

#.  And more extensively in `this blog <https://en.wikipedia.org/wiki/Transport_Layer_Security>`_


Solution
--------

The component in AIMMS Developer and in AIMMS PRO On-Premise that handles the transport layer security is upgraded by incorporating TLS 1.3. 
To make use of this upgrade, you will need to take the following actions:

#.  Switch to AIMMS Developer 4.76.4 or later, see `this AIMMS Developer release note <https://documentation.aimms.com/release-notes.html#aimms-4-76-4-release-october-28-2020-build-4-76-4-11>`_

#.  When you publish your applications on AIMMS PRO On-Premise, you will need AIMMS PRO On-Premise version 2.36.2 or later, see `this AIMMS PRO On-Premise release note <https://documentation.aimms.com/pro-release-notes.html#aimms-pro-2-36-2-release>`_


Please Note
-----------

#.  Up till AIMMS Developer 4.76.3 and AIMMS PRO On-Premise 2.36.1, TLS 1.2 was supported.  At the time of writing this article, TLS 1.2 is still

    #.  the most `widely used transport layer security protocol <https://www.sans.org/reading-room/whitepapers/vpns/paper/39715>`_

    #.  considered to be `reasonably okay <https://www.howsmyssl.com/>`_

    There is a clear trend of increasing adoption of TLS 1.3.

#.  Servers securing their connection using TLS 1.2 are still supported by AIMMS PRO On-Premise 2.36.2 and AIMMS Developer 4.76.4. 
    In short, when a client supports TLS 1.3, it also support TLS 1.2.

#.  WinUI applications published on AIMMS PRO On-Premise may also be affected by organizations enforcing TLS 1.2 or TLS 1.3, 
    see :doc:`Evolution of AIMMS using Transport Layer Security<../520/520-evolution-of-transport-layer-security>`

.. spelling:word-list::

    tlsv