Using the NBest operator
============================================

When working with sets in AIMMS, two functions that are very useful are the first and last functions. As the names of these two functions already give away, they return you the first element of the set and the last element of a set, respectively.

Sometimes, you are not interested in just the first or last element, but in the first couple of elements of a set (based on some sort of order). Another set related function, that is less known, is the NBest operator in AIMMS. This operator allows you to achieve just this, namely obtain the first n elements of a set based on some sort criteria, which must be provided to the NBest operator as an additional argument.



In case of a transportation problem, where you have a variable Transport(i,j) denoting how much is transported from factory i to customer j, one possibly interesting fact might be knowing the three customers j to which the most amount is transported per factory i. By using the NBest operator this can be easily achieved within AIMMS.

The first argument for the NBest operator is the binding index and the result of the operator is a subset of elements from this binding index. The second argument is the sort criteria you want to use. Note that a higher value returned by this sort-criteria indicates a better value, i.e. NBest will sort the elements from largest to smallest. The third argument is the number n, indicating the number of elements you are interested in (i.e. best n).

In the code below, LargestTransportCities is an indexed subset that is indexed over all factories i and is a subset of the set Customers.

  LargestTransportCities(i) := NBest( j, Transport(i,j), 3 );

For each factory i, the above indexed subset LargestTransportCities(i) will contain the three customers to which the most amount is transported.

Note that the NBest operator requires the subset in which the results are stored (LargestTransportCities in this case) to have the "Order by" attribute set to "user". If you happen to forget setting this, AIMMS will give you a compilation error. For more information about the "order by" attribute and sorted sets, please see the chapter Set Expressions in the AIMMS Language Reference.