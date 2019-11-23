Title
===============================
.. meta::
    :description: When you want to run PRO on something different then the SYSTEM account and you want Active Directory logins to work, you need to setup the Server Principal Name for that account. 
    :keywords: 

If you do NOT do that, the conversation between a client and the PRO server will look like:

1.	Client UserA tries to obtain a ticket for server your-pro-server.domain.com from the AD server.
2.	The AD server creates the ticket for UserA with encryption for the SYSTEM account for server your-pro-server.domain.com
3.	The PRO server, which runs as MyServiceAccount, receives this ticket and tries decrypting it using its MyServiceAccount credentials, and fails
4.	The PRO server reports back to the client that it should continue negotiating use potential different negotiation protocols
5.	The client does not have any other ways to negotiate and automatically tries one or two times again (and subsequently fails)

In order to fix this, you need to tell the AD server that when receiving a request for a ticket for your-pro-server.domain.com it should return a ticket that is decryptable by the MyServiceAccount account. In order words, you need to set the Server Principal Name for the your-pro-server.domain.com to MyServiceAccount.

You can do so using the following command (provided you have the appropriate rights to do so) from within the domain:
setspn ``–a HTTP/your-pro-server.domain.com MyServiceAccount``

For your specific hostname and service account that would be come:
setspn ``-a HTTP/dalapp468.na.xom.com xsihemreaimmsprod``

I hope this will make things work for you. If not, please report back with the log files of the server.
