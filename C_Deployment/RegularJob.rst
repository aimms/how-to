How To Schedule a Job regularly?
==================================

Some use cases of jobs executed regularly are:

#. Every night we want to make a plan, to be used the next day.

#. Every ten minutes, we want to reconcile our measurement data.

And I'm sure you can create more examples.

In this How To article, we'll discuss how an AIMMS job can reschedule itself, thus realizing a job scheduled regularly. 

The control we need is the delegation level:

#0. The WebUI session you start via AIMMS PRO has an associated AIMMS data session. This data session has a ``pro::CurrentDelegationLevel()`` of 0; there is no delegation of work yet.

#1. The AIMMS server session started from this AIMMS Data Session has a ``pro::CurrentDelegationLevel()`` of 1; it is the consequence of one delegation.

#2. The AIMMS server session started from the above AIMMS Server session has a ``pro::CurrentDelegationLevel()`` of 1 higher than its predecessor.

This is depicted in 

.. image:: ../Resources/C_Deployment/RegularJob/Images/DelegationLevel.png


The current delegation level for the AIMMS Data session, as you know automatically started with your WebUI session, 

Hi, 

Many a client has asked me how to schedule a job regularly, for instance each night.
They are usually surprised that this is not a standard feature of AIMMS PRO and grind their teeth when I explain they can use the AIMMS PRO api to do this.
Thanks to Marcel Roelofs, I've been able to construct a small example on how to do this from within the AIMMS language instead.

Selected remarks about this approach:
* Advantage: modelers stay inside their comfort zone: the AIMMS language
* Advantage: by having an example, it is easy to tune to needs

.. Caution: 

#. Beware: AIMMS PRO scans every minute for scheduled jobs; so if you have as waiting time two minutes exactly, it will be scheduled with three minutes in between

#. Beware: Once a job is terminated before it actually started; the sequence is broken.


To operate the enclosed example:

#. Create an .aimmspack, publish on your favorite AIMMS PRO system.

#. Launch it 

#. Press the only button

#. Close it.

#. Go to the portal and the jobs tab.

#. Wait for about 10 minutes and refresh screen in between and afterwards.
You'll see the jobs created, started and finished one at a time.

With kind regards,

Chris.