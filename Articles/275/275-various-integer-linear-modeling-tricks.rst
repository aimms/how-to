Various (integer) linear modeling tricks
===========================================

.. meta::
   :description: Various resources about integer and linear modeling tricks for efficiency.
   :keywords: resources, integer, linear, modeling, efficiency

.. note::

    This article was originally posted to the AIMMS Tech Blog.

Modeling problems with an (integer) linear program sometimes requires some experience to recognize certain structures in the problem description that can be formulated in a linear way. 


On the website of the `Naval Postgraduate School <http://faculty.nps.edu/vitae/cgi-bin/vita.cgi>`_, you can find the document :download:`Formulating Integer Linear Programs: A Rogues’ Gallery <http://faculty.nps.edu/dell/docs/Brown_Dell_INFORMS_Transactions_on_Education_January2007.pdf>` that tries to demystify the art of formulating linear and integer linear programs. They do this by introducing formulettes, which consist of a verbal description and the constraints and variables that model this verbal description.

The first simple example of a formulette they provide is the following:


    *For each unit of* :math:`X_1` *, there must be at least 5 units of* :math:`X_2`


which can be modeled with

:math:`5X_1 \le X_2`

The document contains a lot more of such formulettes of varying complexity.

Furthermore, in our book :download:`Optimization Modeling <https://documentation.aimms.com/_downloads/AIMMS_modeling.pdf>` you can also find various modeling tricks in the chapters :download:`Linear Programming Tricks <https://download.aimms.com/aimms/download/manuals/AIMMS3OM_LinearProgrammingTricks.pdf>` and :download:`Integer Programming Tricks <https://download.aimms.com/aimms/download/manuals/AIMMS3OM_IntegerProgrammingTricks.pdf>`. The Optimization Modeling book can either be found online via the link above, or you can find it under the Help menu after you started AIMMS.



.. spelling:word-list::

    formulettes
    formulette