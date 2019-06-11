Units of Measurement – Are your computations correct?=========================================================
.. meta::   :description: Formula checking using units of measurement is illustrated.   :keywords: Units of Measurement, formula consistency.. note::    This article was originally posted to the AIMMS Tech Blog.
.. <link>https://berthier.design/aimmsbackuptech/2012/09/18/units-of-measurement-part-1-are-your-computations-correct/</link>
.. <pubDate>Tue, 18 Sep 2012 05:46:31 +0000</pubDate>
.. <guid isPermaLink="false">http://blog.aimms.com/?p=1658</guid>
A classical method employed by physicists to check equations is the use of units of measurement.  Consider the following example: 
.. image:: images/Eureka.gif
The following equation, intended to model that a ship is kept afloat, is slightly wrong:
.. code-block:: aimms    :linenos:
    MaxWeight * WaterDensity <= ShipVolume
Deploying units of measurement, it is easy to recognize the mistake; and
realize it should have been written
.. code-block:: aimms    :linenos:    
    MaxWeight <= ShipVolume * WaterDensity
Question is: do you want to check this for all the expressions in your
model? Probably not. The good news is that you can leave this kind of
checking up to AIMMS. How does this work?
You start by declaring the units of measurement, in this case:
.. code-block:: aimms    :linenos:
    Quantity SI_Mass {        BaseUnit: kg;        Comment: "Expresses the value for the amount of matter.";    }    Quantity SI_Volume {        BaseUnit: m3 = m^3;        Comment: "Expresses the value of solid content.";    }    Quantity SI_Length {        BaseUnit: m;        Comment: "Expresses the value of a distance.";    }
Next you declare the identifiers, in this case:
.. code-block:: aimms    :linenos:
    Variable v_MaxWeight {        Range: nonnegative;        Unit: kg;    }
    Parameter p_WaterDensity {        Unit: kg/m^3;    }    Variable v_ShipVolume {        Range: nonnegative;        Unit: m^3;    }
When you then enter the constraint:
.. code-block:: aimms    :linenos:
    Constraint c_KeepFloating {        Unit: kg;        Definition: v_MaxWeight * p_WaterDensity <= v_ShipVolume;    }

you will get the following warning from AIMMS:
.. code-block:: none
    Warning: The units associated with the expression (v_MaxWeight * p_WaterDensity)[kg^2/m^3], and with the expression (v_ShipVolume)[m^3] are not commensurate.
