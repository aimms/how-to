Overview: Simple Set, Compound Set, Relation
============================================

.. meta::
    :description: Differences between a simple set, a compound set, and a relation
    :keyword: compount set, relation, set, database


In AIMMS, you cannot read a compound set from a database.

In AIMMS there is one identifier symbol, the set, which can be used for different identifiers: a simple set, a compound set, and a relation. Although all of these are similar, there are some distinctive differences.

* A simple set is a one-dimensional array of elements. A simple set can have an index, but this is not necessary.
* A compound set is a multidimensional array of elements. A compound set has an index.
* A relation is a multidimensional array of elements. A relation has no index.

When reading from a database, a compound primary key cannot be mapped onto a compound index. Therefore, you will not succeed when you try to read a compound set from a database.

Working with compound sets
--------------------------
A compound primary key cannot be mapped onto a compound index, but it can be mapped onto a relation. Therefore, you can read a relation (with the same simple sets as root set as the compound set) from the database and after that you can add the elements to the compound set. For example:


    READ MyRelation FROM TABLE MyDatabaseTable;

    FOR ( ( i, j) | (i, j) in MyRelation ) DO

        MyCompoundSet += {( i, j )};

    ENDFOR;

Here, the indices i and j belong to the compound set MyCompoundSet.

Removing the index from a compound set will not turn it into a relation. You need to delete the set identifier and create it again in order to turn a compound set into a relation.