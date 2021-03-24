echo on

set AIMMSVERSION=4.78.3.18-x64-VS2017

set AIMMSEXECUTABLE="%localappdata%\AIMMS\IFA\Aimms\%AIMMSVERSION%\Bin\aimmscmd.exe"
rem set AIMMSEXECUTABLE="%localappdata%\AIMMS\IFA\Aimms\%AIMMSVERSION%\Bin\aimms.exe"

%AIMMSEXECUTABLE% InitTerm.aimms < RunCmdLine.inp > RunCmdLine.out 2>&1

pause

