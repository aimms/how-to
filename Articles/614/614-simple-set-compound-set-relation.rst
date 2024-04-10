Types of Set
======================== 

.. meta::
    :description: There are different types of set, each with its own use case.
    :keyword: set, simple set, root set, relation, calendar.

A set is a collection of unique elements. In AIMMS, a set is finite. 
When you declare a set in AIMMS, it is one of the following:

* Root set
* Calendar
* Subset
* Relation

Root Set
^^^^^^^^^
A collection of explicit names is called a root set. 

For instance, the set ``s_Weathertypes = { Rainy, Cloudy, Sunny }`` is a root set.

.. code-block:: aimms
    :linenos:

    Set s_WeatherTypes {
        Index: i_WeatherType;
        Parameter: ep_WeatherType;
        Definition: data { Rainy, Cloudy, Sunny };
    }

The elements of this set are ``Rainy``, ``Cloudy``, and ``Sunny``.
Note that the elements of a root set do not need to be specified in the model, they can be read in when the model is running; for instance from a database.

Calendar
^^^^^^^^^
A collection of dates is called a calendar.

A calendar is also a root set.

For instance the calendar ``cal_ThisWeek`` is declared as:

.. code-block:: aimms
    :linenos:

    Calendar cal_ThisWeek {
        Index: i_dayThisWeek;
        Parameter: ep_dayThisWeek;
        Unit: day;
        BeginDate: "2020-02-17";
        EndDate: "2020-02-23";
        TimeslotFormat: "%c%y-%m-%d";
    }

With this declaration, the calendar ``cal_ThisWeek`` contains the elements ``2020-02-17``, ``2020-02-18``, ``2020-02-19``, ``2020-02-20``, ``2020-02-21``, ``2020-02-22``, and ``2020-02-23``.

Note that the ``BeginDate``, ``EndDate``, and ``TimeslotFormat`` need not be explicit strings, but string parameters can be used as well.

Subset
^^^^^^^^^^
A collection of elements, which are also elements of a root set (or calendar), is called a subset. 

For instance, the set ``s_DryWeatherTypes`` is a subset of ``s_WeatherTypes`` and declared as follows:

.. code-block:: aimms
    :linenos:

    Set s_DryWeatherTypes {
        SubsetOf: s_WeatherTypes;
        Index: i_DryWeatherType;
        Parameter: ep_DryWeatherType;
        Definition: data { Cloudy, Sunny };
    }
    
A subset is not a root set. But like root sets, the data for a subset need not be specified in the model, but can be read in at runtime.

Relation
^^^^^^^^^^^^
A collection of tuples, in which each component is an element of another set, is called a relation. 

Observations can be modeled as a relation, for instance as follows:

.. code-block:: aimms
    :linenos:

    Set s_ThisWeeksWeather {
        SubsetOf: (cal_ThisWeek,s_WeatherTypes);
        Definition: {
            data 
            { ( 2020-02-17, Cloudy ), ( 2020-02-18, Sunny  ), 
                ( 2020-02-19, Cloudy ), ( 2020-02-20, Sunny  ), 
                ( 2020-02-21, Rainy  ), ( 2020-02-22, Rainy  ), 
                ( 2020-02-23, Rainy  ) }
        }
    }

In this example, ``( 2020-02-17, Cloudy )`` is a tuple. 
Also ``2020-02-17`` is a component in a tuple, and it is an element of the set ``cal_ThisWeek``.

Simple Sets vs. Relations
--------------------------
A root set, a calendar and a subset are all simple sets. 
A relation is not a simple set.
For a simple set, you can declare: zero, one, or more indices; and zero, one, or more element parameters.


Compound Sets
--------------
A set that is both a relation and a simple set, is called a compound set. 
**Compound sets are no longer supported.** See :doc:`../109/109-deprecate-compound-sets-overview`.

 
