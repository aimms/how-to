import re, shutil, tempfile
import os, sys, stat
import subprocess
import shutil
import platform
import argparse

def executeWithOutput(args):
    proc = subprocess.Popen(args)
    while proc.returncode is None:
        proc.poll()
       
    print proc.returncode

def getAIMMSCmdLinux(version, folder):
    digit = re.split('\.', version)
    curlArgs  = ["curl"]
    curlArgs += ["https://download.aimms.com/aimms/download/data/%s.%s/%s.%s/Aimms-%s-installer.run" % (digit[0], digit[1], digit[2], digit[3], version)]
    curlArgs += ["--output", "aimms-%s-installer.run" % (version)]
    executeWithOutput(curlArgs)

    aimms = os.path.join(os.getcwd(), "aimms-%s-installer.run" % (version))
    os.chmod(aimms, stat.S_IRWXU)

    runArgs  = [aimms]
    runArgs += ["--noexec"]
    runArgs += ["--target"]
    runArgs += [folder]
    executeWithOutput(runArgs)
    os.remove(aimms)

    lnArgs  = ["ln"]
    lnArgs += ["-s"]
    lnArgs += ["%s/Bin" % folder]
    lnArgs += ["%s/Lib" % folder]
    executeWithOutput(lnArgs)
    
def getAIMMSCmdWindows(version, arch, folder):
    digit = re.split('\.', version)
    if not os.path.exists(folder):
       os.makedirs(folder)

    digits = re.split('\.', version)
    curlArgs  = ["curl"]
    curlArgs += ["https://download.aimms.com/aimms/download/data/%s.%s/%s.%s/Aimms-%s-%s.exe" % (digit[0], digit[1], digit[2], digit[3], version, arch)]
    curlArgs += ["--output", "Aimms-%s-%s.exe" % (version, arch)]

    executeWithOutput(curlArgs)

    previousDir = os.getcwd()
    os.chdir(folder)
    
    runArgs  = ["C:\\Program Files\\7-zip\\7z.exe"]
    runArgs += ["x"]
    runArgs += ["%s\\Aimms-%s-%s.exe" % (previousDir, version, arch)]
    runArgs += ["-bb0"]
    executeWithOutput(runArgs)
    
    os.remove("Bin\\libaimms3.dll")
    os.rename("Bin\\Comp\\libaimms3.dll", "Bin\\libaimms3.dll")
    
    os.chdir(previousDir)

def runProjectTests(aimmsversion, arch, projectfolder, project, suites):
    aimmsfolder = os.path.join(os.getcwd(),"aimms",aimmsversion)

    isLinux = 0
    if (platform.system() == 'Linux'):
        isLinux = 1
        
    if (not os.path.isdir(aimmsfolder)):
        if (isLinux):
            getAIMMSCmdLinux(aimmsversion, aimmsfolder)
        else:
            getAIMMSCmdWindows(aimmsversion, arch, aimmsfolder)

    if (isLinux):
        aimmsCmdArgs  = [os.path.join(aimmsfolder, "Bin", "AimmsCmd")]
    else:
        aimmsCmdArgs  = [os.path.join(aimmsfolder, "Bin", "AimmsCmd.exe")]
    
    if (suites):
        aimmsCmdArgs += ["--aimmsunit::RunTestSuites"]
        aimmsCdmArgs += [suites]
    else:
        aimmsCmdArgs += ["--aimmsunit::RunAllTests"] 
        aimmsCmdArgs += ["1"]

    aimmsCmdArgs += ["--as-server"]
    if (not projectfolder or projectfolder == "."):
        aimmsCmdArgs += [os.path.join(os.getcwd(), project)]
    else:
        aimmsCmdArgs += [os.path.join(os.getcwd(), projectfolder, project)]
    
    proc = subprocess.Popen(aimmsCmdArgs, stdin=subprocess.PIPE)
    
    proc.stdin.write("run aimmsunit::TestRunner;");
    proc.stdin.write("quit;")
    proc.stdin.close()

    while proc.returncode is None:
        proc.poll()
   
    if (os.path.isfile("%s/log/AimmsUnit.failed" % projectfolder) or not os.path.isfile("%s/log/AimmsUnit.succeeded" % projectfolder)):
        print "\n****************************************************" 
        print "\n***** Unit tests of project %s failed:" % project
        print "\n****************************************************\n" 
        with open("%s/log/AimmsUnit.xml" % (projectfolder), 'r') as fin:
            print fin.read()
        return 0
    
    print "\n+++++ Unit tests of project %s succeeded" % project
    return 1

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("aimmsversion")
    parser.add_argument("arch")
    parser.add_argument("projectfolder")
    parser.add_argument("project")
    parser.add_argument("--suites")
    
    args = parser.parse_args()
    
    return runProjectTests(args.aimmsversion, args.arch, args.projectfolder, args.project, args.suites)

if __name__ == "__main__":
    if not main(): sys.exit("AIMMS Unit Test failed")