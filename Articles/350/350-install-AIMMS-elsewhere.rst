Install AIMMS perhaps elsewhere
=================================

Figure out APP Luancher version.
see image 

Download latest
https://www.aimms.com/english/developers/downloads/download-aimms/


Create folder D:\Program Files (x86)\AIMMS\IFA\Aimms

Cmd prompt, elevated, nav to c:\Program Files (x86)\AIMMS\IFA

Microsoft Windows [Version 10.0.18363.815]
(c) 2019 Microsoft Corporation. All rights reserved.

C:\WINDOWS\system32>cd "C:\Program Files (x86)\AIMMS\IFA"

C:\Program Files (x86)\AIMMS\IFA>mklink /D Aimms "D:\Program Files (x86)\AIMMS\IFA\Aimms"
symbolic link created for Aimms <<===>> D:\Program Files (x86)\AIMMS\IFA\Aimms

C:\Program Files (x86)\AIMMS\IFA>