Multi-timezone applications 
============================


Working together across the globe is becoming more and more normal.
The stake holders of such applications, view their data either in their own local timezone or, 
when viewing the data at the same time, in an agreed upon timezone.

Three aspects:
* The modeling
* The data exchange with environment
* The user interfacing

The running example
----------------------

To provide 24/7 informed support on (near) incidents of expensive equipment, there are three support locations: 
Singapore, Johannesburg, South-Africa, and Sao Paolo, Brazil.  
The experts are certified to handle certain questions; 
Each of these companies has some experts and each expert is certified to advise on one or more of the areas to support.
To reduce the night shifts for each of these experts to be on stand-by a schedule is created
However, being 24/7 on stand by is taxing for each of these companies; so they decided to cooperate, 
such that they can reduce the stand by time in the evening and even more in the night.

Choice for 'agreed upon' timezone
-----------------------------------

In an application handling multiple timezones, the data presentation depends on the timezone at hand.
In other words, the same data needs to be presented in different ways.
When the data is stored using multiple timezone, the data management and conversions become cumbersome to maintain.
To reduce the 

The modeling
--------------

This is a rostering problem, and constraints similar to rostering apply.

Particular to this appliction is the handling of the cost, different costs are associated with different employees executing a particular shift.
In the enclosed application, this cost computation is handled in the section `determining_cost_coefficients`.

The procedure to compute the cost `p_cost(i_Employee,i_workBlock)` consists of four steps:

#.  First compute the shift of each workblock, depending on the timezone. 
    This again, consists of three sub-steps:

    #.  Determine the starting time using the AIMMS intrinsic function `TimeSlotToString` as follows:

        .. code-block:: aimms
            :linenos:
            :emphasize-lines: 4,5

            for indexTimeZones do
                ep_TempForTimeZone := indexTimeZones;
                sp_workblockTimezoneToStartHour(i_workBlock, ep_TempForTimeZone) := 
                    TimeSlotToString("%c%y-%m-%d %H:%M%TZ(ep_TempForTimeZone)", 
                        cal_workBlocks, i_workBlock );
            endfor ;

        On line 4,5 the call to `TimeSlotToString` converts the calendar element `i_workBlock` 
        to the timezone `ep_TempForTimeZone`.
        Line 2 lets this timezone vary over all timezones.

    #.  Once, we have this string, extracting the starting hour from that string is straightforward.

    #.  Based on the starting hour, we determine the shift.

#.  Second, determine the number of certifications; the more certifications, the more expensive the employee, 
    but also the better the employee is able to create new instructive content or execute more advanced analysis.
    
#.  Combine the previous two steps to compute the actual costs for an employee to be on standby during that shift.

    .. code-block:: aimms
        :linenos:

        Parameter p_CostPerShift {
            IndexDomain: i_shift;
            Definition: data { day : 1, evening : 1.25, night: 1.4 };
        }

There are eight four-hour shifts. 
Experts are standby on two consecutive shifts.

The preference for day time working is handled in the cost function: 


However, whether a shift is a day, evening, or night shift does not only depend on the time the shift starts; but also on the timezone.
Consider a shift that start at 00:00 UTC.  
This shift will be daytime in Singapore, but it will be night time in South Africa and evening in Brazil.
Thus the cost of a shift depends on its starting time and its timezone it is **executed**.

Data exchange
--------------

* UTC preferred, but you usually determined outside your control.
* In this example New Zealand Standard Time  is used 
* code, screen shot of both read in data and data viewed in UTC on screen (ref section below).

User interface
--------------

* Four available timezones
* also setting webui timezone, how.
* screen shots.