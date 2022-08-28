echo on

set AIMMSVERSION=4.87.7.5-x64-VS2017

rem set AIMMSEXECUTABLE="%localappdata%\AIMMS\IFA\Aimms\%AIMMSVERSION%\Bin\aimmscmd.exe"
set AIMMSEXECUTABLE="%localappdata%\AIMMS\IFA\Aimms\%AIMMSVERSION%\Bin\aimms.exe"

%AIMMSEXECUTABLE% --run-only MainExecution InitTerm.aimms

pause

