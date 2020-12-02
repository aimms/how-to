echo on

rem Modify this to select the proper AIMMS Version.
set AIMMSVERSION=4.76.7.12-x64-VS2017

rem Select the latest AIMMS version
rem Get the latest AIMMS release installed by pushing/popping both drive and path, 
rem and then switching to the folder of installed AIMMS versions.
rem Assumption: the users folder is on the C drive.
rem set DRIVEUSEDATSTART=%~d0%
rem pushd
rem c:
rem cd %localappdata%\AIMMS\IFA\Aimms\
rem for /f "usebackq delims=|" %%f in (`dir /b `) do set AIMMSVERSION=%%f
rem popd
rem %DRIVEUSEDATSTART%

set AIMMSEXECUTABLE="%localappdata%\AIMMS\IFA\Aimms\%AIMMSVERSION%\Bin\aimms.exe"

rem Assuming there is precisely one .aimms file in the current folder, 
rem the following command will select that .aimms file and start it with the AIMMSEXECUTABLE selected.
rem Logging is turned on by --logcfg LoggerConfig.xml on the command line.
for /f "usebackq delims=|" %%f in (`dir /b *.aimms`) do %AIMMSEXECUTABLE% --logcfg LoggerConfig.xml "%%f"

rem Assuming here LoggerConfig.xml still writes to the fixed folder c:\temp,
rem we move the create logfile from this folder to the current folder.
rem Because of this fixed location, we can only debug one AIMMS project at a time!
copy c:\temp\aimms-log.xml log
del c:\temp\aimms-log.xml

pause

