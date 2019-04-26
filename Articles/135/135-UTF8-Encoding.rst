Set Encoding Format
====================

.. meta::
   :description: How to configure character encoding in your project such as UTF8 and ASCII.
   :keywords: encode, character, format, UTF, ASCII, unicode




A text is a sequence of characters. A text file contains such a text whereby the characters are represented by numbers. The mapping between these characters in a text and these numbers in a file is called an encoding. Confusion arises when people or programs communicate using different encodings. 

For instance, a text file containing the following bytes (hexadecimal): "48 65 6c 6c 6f 20 57 6f 72 6c 64 2e", would be interpreted using the IBM284 encoding, a Spanish variant of EBCDIC, as: ``çÁ%%?ï?Ê%À``, but using UTF16LE encoding, it would be interpreted as ``效汬⁯潗汲``. Finally, using an ASCII encoding, it would be interpreted as: ``Hello World``.

The historically prevailing encoding is ASCII. For the values 1..127, the ASCII encoding specifies whether this value represents a control character, an English alphabet character, a digit, or some frequently used punctuation character. However, as characters are stored in 8-bit bytes, the values 128 .. 255 are free and these are used at different locales for different purposes. These locale specific extensions of ASCII are also called code pages. As a consequence, the characters displayed of an ASCII file containing some of the numbers 128 .. 255, depend on the active code page selected. Though ASCII reduced the confusion around different encodings significantly, it did not remove it. In addition, without extensions, it was not able to represent the more than sixty thousand distinct characters known around the globe.

To solve this problem, the `UNICODE consortium <http://www.unicode.org>`_ enumerated the characters into numbers called code points. A sequence of code points is subsequently stored to file using an encoding. Examples of such encodings are UTF16LE, widely used on Windows, UTF32LE, widely used on Linux, and UTF8, the currently preferred encoding and typically used in XML files.

As more and more software vendors recognize the value of the `UTF8 <http://en.wikipedia.org/wiki/UTF-8>`_ encoding, UTF8 is becoming the dominant encoding worldwide. AIMMS follows this trend. However, not all software vendors are doing so, nor have all programs in use today been switched over to UTF8. In order to communicate with these legacy programs, AIMMS allows communication with them, using the attribute "encoding" of files and the optional argument "encoding" of ``FileRead``.

As a first example, the statement

.. code-block:: aimms

    decodedText := FileRead( "HelloWorld.txt", selectedEncoding );
    
would read the file ``HelloWorld.txt`` using the encoding selected via the element parameter ``selectedEncoding``.

As a second example, using the declaration:

.. code-block:: aimms

    FILE:
       identifier :  smallTextFile
       name       :  "GoodByeWorld.txt"
       device     :  Disk
       encoding   :  selectedEncoding
       mode       :  replace
   
the statements

.. code-block:: aimms

    put smallTextFile ;
    put decodedText ;
    putclose ;

will write a text using the selected encoding.

Actually, the interpretations of the byte sequence shown in the introduction of this article were generated using the above statements.

 


.. include:: /includes/form.def


