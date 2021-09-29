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

Color
=======

Introduction to colors in WebUI
-------------------------------------

In AIMMS WebUI there are three sets of colors with different purposes.

1. **Primary Colors**
   AIMMS specific colors that contribute to the look and feel of the application. Used for branding, buttons, active states, etc.

2. **Graytones**
   Neutral colors used for interface elements. To achieve a harmonious look, a small amount of Primary Blue is mixed into the gray. When using custom Primary Colors we advise to adjust the Graytones as well with a small percentage of the primary color to achieve a harmonious look.

3. **Data Colors**
   The colors used for data visualization. AIMMS comes out of the box with sets of 9, 11 and 19 colors for use in graphs, charts, etc.
  
  
1. Primary colors
----------------------

These are the colors that are used for actions or for active elements. The primary color is used for the main navigation and buttons. The secondary color is used as hover color or active color.

.. table::
  :widths: 1,1,1
  
  +---------------------------------+----------------------------+----------------------------------+
  | .. image:: images/Dark-Blue.png | .. image:: images/Blue.png | .. image:: images/Light-Blue.png |
  +---------------------------------+----------------------------+----------------------------------+
  | **Dark Blue**                   | **Blue**                   | **Light Blue**                   |
  +---------------------------------+----------------------------+----------------------------------+
  | R0 G0 B129                      | R0 G25 B255                | R192 G249 B255                   |
  +---------------------------------+----------------------------+----------------------------------+
  | #000081                         | #004BFF                    | #C0F9FF                          |
  +---------------------------------+----------------------------+----------------------------------+

.. table::
  :widths: 1,1,1
  
  +----------------------------------+-----------------------------+-----------------------------------+
  | .. image:: images/Dark-Green.png | .. image:: images/Green.png | .. image:: images/Light-Green.png |
  +----------------------------------+-----------------------------+-----------------------------------+
  | **Dark Green**                   | **Green**                   | **Light Green**                   |
  +----------------------------------+-----------------------------+-----------------------------------+
  | R0 G155 B0                       | R0 G212 B0                  | R0 G231 B0                        |
  +----------------------------------+-----------------------------+-----------------------------------+
  | #009B00                          | #00D400                     | #00E700                           |
  +----------------------------------+-----------------------------+-----------------------------------+

.. table::
  :width: 66.666%
  :widths: 1,1
  
  +------------------------------+------------------------------------+
  | .. image:: images/Yellow.png | .. image:: images/Light-Yellow.png |
  +------------------------------+------------------------------------+
  | **Yellow**                   | **Light Yellow**                   |
  +------------------------------+------------------------------------+
  | R255 G193 B0                 | R255 G230 B0                       |
  +------------------------------+------------------------------------+
  | #FFC100                      | #FFE600                            |
  +------------------------------+------------------------------------+
  
2. Graytones
----------------

.. table::
  :widths: 1,1,1
  
  +-------------------------------+------------------------------+------------------------------+
  | .. image:: images/Gray_01.png | .. image:: images/Gray-2.png | .. image:: images/Gray-3.png |
  +-------------------------------+------------------------------+------------------------------+
  | **Gray 01**                   | **Gray 02**                  | **Gray 03**                  |
  +-------------------------------+------------------------------+------------------------------+
  | R0 G10 B34                    | R80 G87 B103                 | R160 G164 B173               |
  +-------------------------------+------------------------------+------------------------------+
  | #000A22                       | #505767                      | #A0A4AD                      |
  +-------------------------------+------------------------------+------------------------------+

.. table::
  :widths: 1,1,1
  
  +------------------------------+------------------------------+------------------------------+
  | .. image:: images/Gray-4.png | .. image:: images/Gray-5.png | .. image:: images/Gray-6.png |
  +------------------------------+------------------------------+------------------------------+
  | **Gray 04**                  | **Gray 05**                  | **Gray 06**                  |
  +------------------------------+------------------------------+------------------------------+
  | R198 G200 B204               | R212 G214 B217               | R225 G226 B230               |
  +------------------------------+------------------------------+------------------------------+
  | #C6C8CC                      | #D4D6D9                      | #E1E2E6                      |
  +------------------------------+------------------------------+------------------------------+

.. table::
  :width: 66.666%
  :widths: 1,1
  
  +------------------------------+------------------------------+
  | .. image:: images/Gray-7.png | .. image:: images/Gray-8.png |
  +------------------------------+------------------------------+
  | **Gray 07**                  | **Gray 08**                  |
  +------------------------------+------------------------------+
  | R237 G239 B242               | R245 G246 B250               |
  +------------------------------+------------------------------+
  | #EDEFF2                      | #F5F6FA                      |
  +------------------------------+------------------------------+

3. Data Colors
----------------

When choosing colors to use for your data, try to use one of these approaches to color schemes for data visualization.

A. *Sequential schemes* are suited to ordered data that progress from low to high. Lightness steps dominate the look of these schemes, with light colors for low data values to dark colors for high data values.

B. *Diverging schemes* put equal emphasis on mid-range critical values and extremes at both ends of the data range. The critical class or break in the middle of the legend is emphasized with light colors and low and high extremes are emphasized with dark colors that have contrasting hues.

C. *Qualitative schemes* do not imply magnitude differences between legend classes, and hues are used to create the primary visual differences between classes. Qualitative schemes are best suited to representing nominal or categorical data.

(Descriptions courtesy of `Colorbrewer <http://colorbrewer2.org/>`_)