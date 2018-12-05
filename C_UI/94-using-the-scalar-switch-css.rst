To use on/off switches we developed .css code, both for the table widget and for the scalar widget.

A checkbox will become a switch under the following conditions:
The identifier needs to be an identifier with “Binary” as range.
The widget needs to contain “Switch” in the name
The condition on the name of the widget allows you to control if a binary identifier should be shown as a switch or a checkbox.
 
From a UI design perspective, they are not equivalent. See for instance here https://uxplanet.org/checkbox-vs-toggle-switch-7fc6e83f10b8
Where they state: Tapping a toggle switch is a two-step action: selection and execution, whereas checkbox is just selection of an option and its execution usually requires another control.

The .css code can be found in the folder "MainProject\WebUI\resources\css" of the enclosed example project.

Please let us know whether this resolves issue 19 of the Jumbo List "Improve Showing the Engineering data", translated to "Do not use a checkbox for showing the engineering data, but use a modern on-off toggle (or something similar)."