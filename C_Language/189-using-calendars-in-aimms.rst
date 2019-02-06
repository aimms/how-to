Using calendars in AIMMS
========================
For a lot of problems you will need to work with some time based notion during the modeling. For example, with a transportation problem the main decision could be how much to transport from each depot to each customer for every day in a given time horizon.

Of course, it is possible to just create a set Days in AIMMS and give this exactly the same number of elements as there are days in the time horizon you are considering. However, this would require you to manually keep track of which of these elements are working days and which ones are weekend days. Also, you would have to take care of the fun details regarding leap years. Fortunately, in AIMMS you have the possibility to work with a Calendar, which makes working with time related things a lot easier.



When declaring calendars, you will have to provide some additional information like the unit of your calendar (e.g. hours / days / weeks) and when it starts and stops. Furthermore, just like regular sets, you can declare an index which can then be used in the index domain of other identifiers. An example of the declaration of a calendar is shown in the screenshot below. 

.. image
	calendar_attributes

Note that the start and end date do not necessarily need to be provided as hard-coded strings (as shown in the screenshot), but can also be provided via string parameters. The format for these start and end dates is fixed (i.e. YYYY-MM-DD HH:MM:SS). However, the elements of the calendar will be formatted according to the timeslot format attribute. You can see the result of this formatting by viewing the data of the calendar.

Using calendars like this just makes it easy to declare the right number of elements for a given time period, but does not differ too much from using regular sets. However, one of the nice advantages of using calendars instead of sets is that you can make use of the TimeSlotCharacteristic procedure to get more specific information for each of the elements. For example, you can use TimeSlotCharacteristic to get information about which day of the week or the month a timeslot corresponds to. The first argument you have to provide to this procedure is a timeslot (i.e. element of a calendar), while the second argument is an element from the set TimeSlotCharacteristics denoting which particular characteristic you are interested in.

So if I have a calendar that has unit days and I am interested in obtaining the day in the week, month, and year, I can create the following three parameters::

 PARAMETER:
   identifier   :  DayInWeek
   index domain :  iDay
   definition   :  TimeslotCharacteristic(
                           Timeslot       : iDay ,
                           Characteristic : 'weekday')

 PARAMETER:
   identifier   :  DayInMonth
   index domain :  iDay
   definition   :  TimeslotCharacteristic(
                           Timeslot       : iDay ,
                           Characteristic : 'monthday')

 PARAMETER:
   identifier   :  DayInYear
   index domain :  iDay
   definition   :  TimeslotCharacteristic(
                           Timeslot       : iDay ,
                           Characteristic : 'yearday')

If you show the data for these three parameters in a pivot table, you will see the following:

.. image
  timeslotcharacteristic-example

As you can see the 24th of April 2013 is the 114th day of the year, the 24th day of the month, and the third day of the week (i.e. Wednesday). Using the characteristic 'weekday' will actually allow you to easily create a subset of your calendar for all the week days (or weekend days). For example, the subset of all days in the calendar that represent weekend days can be constructed as follows::

	iDay | TimeslotCharacteristic(iDay ,'weekday') >= 6

You can view the data of the predefined set TimeSlotCharacteristics to see which other characteristics for timeslots are available.