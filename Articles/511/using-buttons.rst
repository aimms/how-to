.. raw:: html
  
  <style>
      h2:before {
        height: 5px;
        width: 80px;
        background: #000081;
        display: block;
        content: '';
        margin-bottom: 14px;
    }
  </style>

Using Buttons
===============

Type of Buttons
-----------------

Buttons come in AIMMS WebUI come in three different varieties: Primary Buttons, Secondary Buttons and Text Buttons.

.. image:: images/PrimaryButton.png

**Primary Buttons** are used for the “happy path”: an action that brings the user closer to their goal. 
Examples are “Accept”, “Ok” or “Continue”. Therefore, ideally only one of these buttons is visible at a time.

.. image:: images/SecondaryButton.png

**Secondary Buttons** are used for less desirable actions, that do not bring the user forward. Examples are “Cancel”, “Disagree” or “Back”.

.. image:: images/TextButton.png

**Text buttons** are the lowest level of buttons and the least intrusive. They are used where space is limited, often within other elements like selection widgets.

Button text
---------------

For consistency, we recommend to write button text in sentence case (capitalize only the first word). There is an exception for words that are always capitalized, for example “Visit Amsterdam”.

.. csv-table::
  :width: 50%
  :widths: 1, 1

  ✅ Do, 🚫 Don’t
  Calculate transport cost,	Calculate Transport Cost
  Visit Amsterdam,	Visit amsterdam
  
Text in buttons should be short and precise, using action words to indicate to the user exactly what the button does.

.. csv-table::
  :width: 50% 
  :widths: 1, 1

  ✅ Do,	🚫 Don’t
  Submit forecast,	Done
  Calculate difference,	Process
  Optimize,	Ok
  
  
Button positioning
---------------------

The convention for button positions in WebUI is to place the Primary Button in the bottom right corner and the Secondary Button to the left of it.

Following this convention ensures a constant experience. Users can predict the purpose of the button based on its position, and spend less time thinking about where to click.


.. image:: images/ButtonPositioning-1024x675.png

.. spelling::
    
	amsterdam