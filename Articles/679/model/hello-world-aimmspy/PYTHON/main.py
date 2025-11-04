# This is a minimal aimmspy Python script.
# The purpose of this script is to introduce the aimmspy part of the Python bridge,
# Reference information about aimmspy can be found at: https://documentation.aimms.com/aimmspy/aimmspy.html


# Don't forget to activate the venv: .venv\Scripts\activate.bat or .venv\Scripts\Activate.ps1
# First time use: pip install -r requirements.txt


# Import ingredients from aimmspy.
from aimmspy.project.project import Project, Model
from aimmspy.utils import find_aimms_path


# Initialize the AIMMS project
project = Project(

    # path to the AIMMS Bin folder  
    aimms_path=find_aimms_path("25"), 

    # Path to the AIMMS project file (../hello.aimms).
    #aimms_project_file=projectfile,
    aimms_project_file = "..\\AIMMS\\hello.aimms"

    # licensing url
    # When needed, add your licensing URL here.
)
aimms_model : Model = project.get_model(__file__)


# Send data to the AIMMS model
hello_world_dict = { "hello" : 1, "world" : 2 }
aimms_model.p_a.assign( hello_world_dict )


# Run an AIMMS procedure
aimms_model.MainExecution()


# Get results back and print.
hello_world_result = aimms_model.p_b.data()
print(f"Hello world: sum is {hello_world_result}")
