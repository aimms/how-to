Determine Data Ranges in Excel Files
================================================

When exchanging data with Excel files, you might need to provide the cell ranges as inputs to AIMMS AXLL functions. 
As the AIMMS developer you may not always know these ranges beforehand. This is typically the case when end users are uploading Excel files as input in the AIMMS app. 
We discuss some strategies on how to handle such situations. 

We include a :download:`modified version <exampleNFL.zip>` of the exampleNFL project used in :doc:`../85/85-using-axll-library`.

Sufficiently large ranges
---------------------------

You can provide a sufficiently range and use the appropriate arguments to instruct AIMMS to traverse in the Excel file till it encounters an empty cell. For example, if you have set elements in "A2:A33" and corresponding parameter data in "D2:D33", you can use the AXLL read procedures as below. If you are expecting empty cells in the set range, change the argument 'SkipEmptyCells' to 1 so that the entire range is traversed. 

.. code-block:: aimms

    axll::ReadSet(
        SetReference              : sTeams , 
        SetRange                  : "A2:A50" , 
        ExtendSuperSets           :  1, 
        MergeWithExistingElements :  0, 
        SkipEmptyCells            :  0);

    axll::ReadList(
        IdentifierReference    : spStadiumName , 
        RowHeaderRange         : "A2:A50" , 
        DataRange              : "D2:D50" , 
        ModeForUnknownElements :  0, 
        MergeWithExistingData  :  0);

This works for most cases but you will lose data if it extends beyond the sufficiently large guesstimate provided or there could be mixups if two different data ranges are in the same column(row) separated by an empty row or a header row. 

AXLL Functions
----------------

In AIMMS 4.70.4 and above, you have four new functions in the AXLL library to determine the boundaries of data in an Excel sheet. You can use these functions to find the last used row number or column number and coupled with other functions, you can construct the final cell range. For example, the below code will return ``spSetRange`` as "A2:A33" which you can then provide as an argument to ``axll::ReadSet``. 

.. code-block::aimms

    pRowNum := axll::LastUsedRowNumber;
    spSetRange := FormatString("A2:A%n", pRowNum);

If there is more data in a different column, then spSetRange will be a larger range than expected. That then becomes a case of using a sufficiently large range described in the previous section.

Similarly, ``axll::LastUsedColumnNumber`` will return the number of the last column in which there is data and you can use ``axll::ColumnName`` to convert it. For example, the below code will return ``spDataRange`` as "A2:AG33" in the Distances sheet:: 

    pRowNum := axll::LastUsedRowNumber;
    pColNum := axll::LastUsedColumnNumber;
    axll::ColumnName(pColNum, spColName);
    spDataRange := FormatString("A2:%s%n", spColName, pRowNum);

Like the previous method, using these functions is not straightforward if you have multiple data ranges in the same column/row separated by empty cells. 

Named ranges
---------------

Another way is to create "Named Ranges" in the input Excel templates. You can then provide "NameofRange" as input to AXLL function arguments like 'RowRange' or 'DataRange' instead of "A2:A10". However, the end user will need to update the named ranges if they add new rows or columns. A table name is automatically expanded but AXLL functions do not recognize Excel tables. 

Summary
---------

Some other techniques to determine the consecutive ranges include

    #. Using ``axll::ReadSet`` with a sufficiently large range and using :aimms:func:`errh::Message` functions to retrieve the cell in which reading stopped from the warning message generated. 
    #. Using a ``while`` loop in combination with ``axll::ReadSingleValue`` and stop the loop when an empty cell is encountered. 

With careful design of the Excel templates for input, you can avoid needing to use these custom procedures. We recommend that you

    #. Separate out data into different sheets based on the index domains of the corresponding AIMMS identifiers. 
    #. Don't mix list type data with matrix type data. 

Following these recommendations will mitigate the drawbacks discussed above and will allow you to model your data read/write procedures using the already available functions in AIMMS.