Employee Training
==================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Personnel planning, multi-period LP, integer program, rounding heuristic, probabilistic constraint, control-state variables, WebUI
   :description: Models a six-month airline flight-attendant hiring and training schedule as a multi-period LP/IP, comparing LP rounding heuristics against full integer programming with probabilistic demand constraints.

Direct download AIMMS Project :download:`Employee Training.zip <model/Employee Training.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Modeling%20Book/Employee%20Training

This example illustrates a personnel planning problem and its corresponding multi-period model. An airline company must decide how many flight attendants to hire and train over the next six months. The model includes a (stock) balance constraint which is typical in multi-period models involving state and control type decision variables. A time lag notation is introduced for the backward referencing of time periods. 

Initially, a simplified formulation of the model is solved and it is shown that rounding a fractional linear programming solution can a good alternative to using an integer programming solver. Finally, the full model complete with random variables is considered and an approach based on the use of probabilistic constraints is presented. 

Details about this example can be read in Chapter 8 of the `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_ book. An electronic version of this book is available through the 'Help' menu.

For this example you can use the AIMMS WebUI for the graphical user interface. To use the AIMMS WebUI you should select 'Tools - Start Web UI' from the menu bar. This will open the home page in a browser. 

Keywords:
Linear Program, Integer Program, Control-State variables, Rounding Heuristic, Probabilistic Constraint, WebUI




