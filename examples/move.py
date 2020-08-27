import glob
import os
import shutil
import pdb

files = [f for f in glob.glob('**\Description.rst', recursive=True)]
files2 = [f for f in glob.glob('**\description.rst', recursive=True)]
 
files.extend(files2)

newName = []
for f in files:
    newName.append(f.lower().replace(' ','-').replace('\\description.rst','.rst'))
    shutil.copyfile(f, newName[-1])
