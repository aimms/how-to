Oil Well Drilling
==================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Model Edit Functions, Error Handling, source code generation
   :description: This example illustrates the use of AIMMS Model Edit functions in combination with the Error Handling functionality. 

Direct download AIMMS Project :download:`Oil Well Drilling.zip <model/Oil Well Drilling.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Functional%20Examples/Oil%20Well%20Drilling

This example illustrates the use of AIMMS Model Edit functions in combination with the Error Handling functionality. The Model Edit functions allow the end-user of an application to enter (proprietary) formulations to the model at runtime. This requires the end-user to have a basic understanding of the AIMMS language, but Error Handling features will be used to handle and present the errors in an appropriate format to the end-user.

An oil company is considering setting up an oil well on a land that potentially has oil underneath. If the company decides to set up the oil well, they also need to determine the optimal drill depth and the hole diameter. These factors determine the cost of drilling, the amount of oil gathered if there is, and the pay off of the drilling. In order to evaluate the potential of the land, the company can consult with an oil survey firm to do a survey. This, of course, has an associated cost. Consequently, the first decision is to consult  the survey firm and then depending on the result the company needs to decide to drill or not along with drill-depth and hole-diameter.

The company wants to evaluate different alternatives in modeling the cost, pay off and the probability of the outcome given the survey result, positive or negative. In the Formulations page, an experts can define the expressions for these parameters using "d" for drill depth and "h" for hole diameter. The users can check their formulations or try default formulations before applying them into the model. The objective of the model is to maximize the expected profit and this model can be solved by clicking on the "Solve" button.

On the "Solutions" page, the optimal decisions at every stage are reports as well as the expected profit.

Keywords:
Model Edit Functions, Error Handling, source code generation

