Change Thousand and Decimal Separators in WebUI
=================================================

.. meta::
   :description: Explains how AIMMS WebUI reads the browser language to set thousand and decimal separators, and how to apply a new separator by changing the browser language and reloading WebUI.
   :keywords: thousand separator, decimal separator, number formatting, browser language, locale, WebUI, refresh, reload

AIMMS WebUI uses your browser's language setting to determine which characters
to use as the thousand separator and the decimal separator.
For example, English uses ``.`` as the decimal separator and ``,`` as the thousand separator,
while Dutch uses ``,`` and ``.`` respectively.

How WebUI Reads Number Formatting
-----------------------------------

When a WebUI session starts, it reads the browser's current language setting
and uses it to initialize number formatting, date/time formatting, and locale bundles.
These settings are kept for the duration of that session and are not updated
if the browser language changes while WebUI is running.

How to Change the Separators
------------------------------

To apply a different set of separators, follow these steps:

#. Change the language in your browser settings to the desired locale.
#. Restart the browser.
#. Close WebUI and AIMMS completely.
#. Reopen AIMMS and WebUI.

.. note::

   Simply restarting the browser without closing AIMMS is not sufficient.
   When a browser reopens and restores a tab, WebUI may reuse the locale
   configuration from the previous session instead of re-reading the updated
   browser settings. Closing AIMMS and WebUI completely ensures a clean session start.

.. seealso::

   * :doc:`../362/362-multi-timezone`: covers locale-aware date and time formatting in WebUI, which is driven by the same browser language setting.
   * :doc:`../135/135-UTF8-Encoding`: covers character encoding formats, relevant when working with locale-specific characters in AIMMS data.
