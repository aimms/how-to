Aborting execution of AIMMS
============================.. meta::   :description: How to interrupt a long running assignment.   :keywords: interrupt, long running .. note::	This article was originally posted to the AIMMS Tech Blog.
..       <link>https://berthier.design/aimmsbackuptech/2012/01/03/aborting-execution-of-aimms/</link>
..       <pubDate>Tue, 03 Jan 2012 14:31:26 +0000</pubDate>
..       <dc:creator><![CDATA[]]></dc:creator>
..       <guid isPermaLink="false">http://blog.aimms.com/?p=583</guid>
..       <description></description>
..       <content:encoded>
With AIMMS it is possible to interrupt long running executions by pressing the keyboard shortcut **CTRL-Shift-S**. However, this requires multiple statements to be executed and/or generation of multiple constraints because this shortcut key only works between two statements executed that are executed or constraints that are generated. The only exception to this is the solve statement in AIMMS, although this is a single statement, you can still interrupt it with this keyboard shortcut as most solvers allow user interrupt between solver iterations.
A more powerful tool to interrupt is ``AimmsInterrupt``. You can download this tool `here <http://download.aimms.com/aimms/download/data/AIMMSInterruptTool/AimmsInterrupt.exe>`_This tool is more powerful than the original CTRL-Shift-S shortcut, as it is even able to interrupt long running statements.

For example, the assignment
.. code-block:: aimms
   someParameter1(i,j,k,l) := if someParameter2(i,j,k,l) <= 2 then 1 endif ; 
potentially can take a lot of time in case there are billions of combinations possible with the 4 indices. If AIMMS is executing the above assignment statement, you are not able to interrupt it with the CTRL-Shift-S shortcut (as it is a single statement). However, with the AimmsInterrupt tool, you are able to interrupt this long-running assignment statement.
After you start AimmsInterrupt from the Start Menu, it will add a tray icon to the windows tray bar. When you click on this AIMMS icon, you will get a list of all the AIMMS processes on your computer that can be interrupted as can be seen in the picture on the below.
.. image:: images/screenshot_aimmsinterrupt.pngPlease keep in mind that after you interrupted execution, you cannot rely on the data of parameter someParameter, because the assignment statement was not finished.
The AimmsInterrupt tool is particular useful if somehow you made a mistake and the execution of an assignment statement or evaluation ofthe definition of a parameter takes forever and you did not yet save your project. Another useful case is when you are working on a largeproject and it is not clear which statement and/or constraint requires an unexpectedly large amount of time.
.. include:: /includes/form.def