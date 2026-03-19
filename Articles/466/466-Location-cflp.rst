Location: Capacitated facility location problem (CFLP)
========================================================

.. warning::
   This article references outdated technology and is provided for historical purposes only. 
   It is not recommended to use this information as a primary source for current projects or documentation. Please refer to the latest documentation for up-to-date information.

Direct download AIMMS Project :download:`CFLP.zip <model/CFLP.zip>`

.. Go to the example on GitHub: https://github.com/aimms/examples/tree/master/Practical%20Examples/Location/CFLP

Problem type:
MIP (medium: instances capa, capb and capc; small: rest)

Keywords:
Benders Decomposition, GMP, Reading numbers from text file, Warehouse location

Description:
Capacitated facility location problems deal with locating an undetermined
number of facilities in order to serve customers, at minimum cost. The
potential facility locations and the customer zones are considered fixed
points in a network.

The data is provided in text files obtained from the OR-Library by J.E.
Beasley (from the set Capacitated warehouse location). External functions,
that read integer and double numbers from a text file, are used to read
the data.

References:
Wentges, P., Accelerating Benders' decomposition for the capacitated facility
location problem, Mathematical Methods of Operations Research 44 (2), 1996,
pp. 267-290.

OR-Library: http://people.brunel.ac.uk/~mastjjb/jeb/info.html

Note: The facility location problem is also known as the warehouse location
problem

.. meta::
   :keywords: Capacitated facility location, CFLP, Benders decomposition, GMP, warehouse location, MIP, text file input
   :description: Solves the capacitated facility location problem to site facilities serving customers at minimum cost using Benders decomposition via the GMP library and OR-Library text-file data.

.. spelling:word-list::

	capa
	capb
	capc