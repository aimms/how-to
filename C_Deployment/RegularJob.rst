How To Schedule a Job regularly?
==================================

Hi, 

Many a client has asked me how to schedule a job regularly, for instance each night.
They are usually surprised that this is not a standard feature of AIMMS PRO and grind their teeth when I explain they can use the AIMMS PRO api to do this.
Thanks to Marcel Roelofs, I've been able to construct a small example on how to do this from within the AIMMS language instead.

Selected remarks about this approach:
- Advantage: modelers stay inside their comfort zone: the AIMMS language
- Advantage: by having an example, it is easy to tune to needs
- Beware: AIMMS PRO scans every minute for scheduled jobs; so if you have as waiting time two minutes exactly, it will be scheduled with three minutes in between
- Beware: Once a job is terminated before it actually started; the sequence is broken.

To operate the enclosed example:
- Create an .aimmspack, publish on your favorite AIMMS PRO system.
- Launch it 
- Press the only button
- Close it.
- Go to the portal and the jobs tab.
- Wait for about 10 minutes and refresh screen in between and afterwards.
You'll see the jobs created, started and finished one at a time.

With kind regards,

Chris.