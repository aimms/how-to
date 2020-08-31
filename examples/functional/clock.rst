Clock
=======
.. meta::
   :keywords: Network Object, ScheduleAt, CurrentToString, MomentToString
   :description: In this example a clock is created in a network object and is updated every second. 

Direct download AIMMS Project :download:`Clock <https://download.aimms.com/aimms/download/examples/Clock.zip>`

Go to the example on GitHub:
https://github.com/aimms/examples/tree/master/Functional%20Examples/Clock

In this example a clock is created. This clock is drawn in a network object (with the old AIMMS logo) and is updated every second. 

To perform this update we use the function ScheduleAt. This function can be used to run a specific procedure at a specific moment in time.

If you want to run a procedure at regular intervals, as we have done, then you can re-schedule the procedure from within the scheduled procedure itself.

Keywords:
Network Object, ScheduleAt, CurrentToString, MomentToString

