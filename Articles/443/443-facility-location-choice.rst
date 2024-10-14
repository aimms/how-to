Facility Location Choice
=========================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

.. meta::
   :keywords: Distribution System Design, Integer Program, Mathematical Reformulation, Mathematical Derivation, Customized Algorithm, Benders decomposition, Auxiliary Model, Constraint Generation
   :description: This example considers the problem of selecting distribution centers along with their associated customer zones.

Direct download AIMMS Project :download:`Facility Location Choice.zip <model/Facility Location Choice.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Modeling%20Book/Facility%20Location%20Choice

This example considers the problem of selecting distribution centers along with their associated customer zones.  The mathematical model uses a simple mixed-integer linear programming formulation and can be easily solved by using a standard solver for small and medium datasets.  

However, when working this type of problem with a larger datasets, you may consider re-examining your approach to the problem. One option is to decompose the problem into several smaller subproblems that are solved sequentially rather than simultaneously. One such approach, namely Benders' decomposition, is illustrated in this example.

Details about this example can be read in Chapter 22 of `AIMMS Optimization Modeling <https://documentation.aimms.com/aimms_modeling.html>`_. An electronic version of this book is available through the 'Help' menu. (Note: the coordinates are adjusted to the map and therefore differ from the book.)

Reference: Geoffrion, A.M., and G.W. Graves, Multi-commodity distribution system design by Benders decomposition, Management Science 20, 822-844 (1974).

Keywords:
Distribution System Design, Integer Program, Mathematical Reformulation, Mathematical Derivation, Customized Algorithm, Benders decomposition, Auxiliary Model, Constraint Generation

