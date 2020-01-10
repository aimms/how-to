Using the Routing Library 
=========================

**What should be in the article?**

* Short explanation of what a routing problem is 
* Short Explanation of what a library is 
* How to transfer that data to the library
* The meaning of the output arguments
* The different possible formulations of subtour elimination constraints 
  and how to select the one you want to use. 
* Literature / further reading
* eg https://how-to.aimms.com/Articles/126/126-TSP-Lazy-Constraints.html



A **Capacitated Vehicle Routing Problem** (CVRP) deals with the following problem: A set is given with a depot and 
multiple costumers. The distances* between those locations are known. A number of vehicles is available 
to serve the costumers. All costumers have a certain demand and the vehicles have a maximum capacity. The shortest 
route for the vehicles must be found where all costumers get their demand. The vehicles all start and end at the 
depot. 

.. image:: images/CVRP.png
   :scale: 25 %
   

This CVRP Library (link to the library) can be added to any model to solve this problem. This article explains how
to add a library to your model: https://how-to.aimms.com/Articles/84/84-using-libraries.html


- De library wordt met de volgende code aangeroepen:
cvrpl::Solve_CVRP(p_NumberOfVehicles1, p_Distance1, p_Demand1, p_Capacity1, p_TotalDistance1, bp_x1);

- Er zijn 4 input argmunten en 2 output argumenten 
- Deze moet je in je eigen model hebben, maar je mag ze noemen hoe je wilt
- Uitleggen per argument hoeveel argumenten ze hebben (Distance heeft er bijvoorbeeld 2)
- Uitleggen per argument wat het is 

  
   
   
   



- This is a reusable library that requires some input argmunts and gives back some output documents
- It can be added to your model like this: https://how-to.aimms.com/Articles/84/84-using-libraries.html
- 










If you wanna call the library:

cvrpl::Solve_CVRP(p_NumberOfVehicles1, p_Distance1, p_Demand1, p_Capacity1, p_TotalDistance1, bp_x1);


Input:
- amount of vehicles
- distance between the nodes (or cost if you're trying to find the cheapest route instead of the shortest)
- demand of the costumers 
- Capacity of the vehicle -> which is the same for each vehicle 


Output:
- Total distance of the route 
- The route itself x(i,j,k) 





















Solve_CVRP(p_locNrVeh,p_locDist,p_locDemand,p_locCap,p_locTotalDist,p_locX)





























