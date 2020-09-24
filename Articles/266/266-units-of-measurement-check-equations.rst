Check Computations with Units of Measurement
=============================================

.. meta::
   :description: Formula checking using units of measurement is illustrated.
   :keywords: Units of Measurement, formula consistency

.. note::

    This article was originally posted to the AIMMS Tech Blog.


.. image:: images/Eureka.gif

A classical method to check equations is to use units of measurement.

The following equation, intended to model that a ship is kept afloat, is slightly wrong:

.. code-block:: aimms
    :linenos:

    MaxWeight * WaterDensity <= ShipVolume

Checking with units of measurement, you can quickly recognize the mistake; it should have been written:


.. code-block:: aimms
    :linenos:
    
    MaxWeight <= ShipVolume * WaterDensity

But do you want to check this for all the expressions in your
model? Probably not. The good news is that you can leave this kind of
checking up to AIMMS. Let's find out how.

You start by declaring the units of measurement, in this case:

.. code-block:: aimms
    :linenos:

    Quantity SI_Mass {
        BaseUnit: kg;
        Comment: "Expresses the value for the amount of matter.";
    }
    Quantity SI_Volume {
        BaseUnit: m3 = m^3;
        Comment: "Expresses the value of solid content.";
    }
    Quantity SI_Length {
        BaseUnit: m;
        Comment: "Expresses the value of a distance.";
    }

Next you declare the identifiers, in this case:

.. code-block:: aimms
    :linenos:

    Variable v_MaxWeight {
        Range: nonnegative;
        Unit: kg;
    }
    Parameter p_WaterDensity {
        Unit: kg/m^3;
    }
    Variable v_ShipVolume {
        Range: nonnegative;
        Unit: m^3;
    }

When you then enter the constraint:

.. code-block:: aimms
    :linenos:

    Constraint c_KeepFloating {
        Unit: kg;
        Definition: v_MaxWeight * p_WaterDensity <= v_ShipVolume;
    }


you will get the following warning from AIMMS:

.. code-block:: none

    Warning: The units associated with the expression (v_MaxWeight * p_WaterDensity)[kg^2/m^3], and with the expression (v_ShipVolume)[m^3] are not commensurate.
