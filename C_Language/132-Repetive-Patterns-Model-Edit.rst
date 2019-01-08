Repeat Data with Model Query and Model Edit
===========================================

.. meta::
   :description: How to duplicate patterns with Model Query and Model Edit functions
   :keywords: model, edit, query, structure, pattern

      .. note::

	This article was originally posted to the AIMMS Tech Blog on May 8, 2014 by Chris Kuip.

.. sidebar:: Der Maler - Honoré Daumier - from Rheims Museum of Fine Arts

    .. image:: ../Resources/C_Language/Images/132/1280px-Honore_Daumier_008.jpg

    
When constructing AIMMS models, we are usually able to handle repetition and structure by adding indexes. For instance, if we have built a model for the conversion process of a single machine, we do not have to duplicate the relevant model code when given an extra machine. Instead, we can use an extra index over a set of machines. However, there are situations where adding an extra index is not an option. This blog post will provide an example of such a situation, illustrating how the issue can be tackled using the AIMMS Model Query and Model Edit functions.  **Duplicating data with the AIMMS Model Edit and Model Query functions** The example discussed in this blog post deals with the specification of data maintained per product. Note the differences in dimension and index domain of the following identifiers containing the data of the products, ``pr`` is an index in the set ``Products``:

* ``P1(pr)``
* ``P2(mat, pr)``
* ``P3(f, pr, t)``
* ``P4(pr)``

We have the data for product 'pr1,' and we need to specify the data for product 'pr2.' The data for product 'pr2' should be the same as that of product 'pr1,' but with a different value for ``P4``. So we first need to create a copy of the input data we have on 'pr1' (``existingPr``) for 'pr2' (``newPr``) as follows in AIMMS:

.. code-block:: aimms

    CopyProductDataProcedure body:
    P1(newPr) := P1(existingPr);
    P2(mat,newPr) := P2(mat,existingPr);
    P3(f,newPr,t) := P3(f,existingPr,t);
    P4(newPr) := P4(existingPr) ;

And then specify the differences:

.. code-block:: aimms

    P4(newPr) := 333 ;

When we start maintaining additional information per product, say ``P5(pr,reg)``, this added identifier requires us to visit the ``CopyProductDataProcedure`` code again. Hence, we need to add a similar line:
<pre>P5(newPr,reg) := P5(existingPr,reg);</pre>
Here we have identified a maintenance burden: to introduce a new identifier for products, we need *to remember* to extend the code of procedure ``CopyProductDataProcedure``. Note that the difference in dimension and index domains of the involved identifiers prohibits the capturing of this structure by introducing an extra index. *This ends our problem statement. Let us now try to tackle it in a generic manner.* There is a clear pattern, for each identifier referencing a product ``pr``, there is an assignment statement, and the product index ``pr`` within each statement is replaced by the new element and the existing element respectively. We will generate AIMMS code following this pattern using *model query and model edit functions*. Before we begin, however, a kind of pre-amble is needed. The model edit functions only operate on so-called runtime identifiers which are created inside a runtime library. In our example, we only need a single procedure. Therefore, we create the runtime library and its single procedure in the following two lines:

.. code-block:: aimms

    e_lib  := me::CreateLibrary("LibCreateCopyOfElement", "cce" );
    e_proc := me::Create("ProcCreateCopyOfElement", 'procedure', e_lib);

Creating a runtime identifier results in a new element in the set ``AllIdentifiers``. These new elements are assigned to the element parameters ``e_lib`` and ``e_proc``. These element parameters are used reference the created library and procedure below. The body of the procedure is just some text that is compiled by the AIMMS compiler. Our job is to piece that text together. The trick is to know when to create a piece of text and what piece of text to create. "When to create a piece" is determined by considering each identifier from some subset of identifiers, and considering each argument in that identifier. This results in the following nested for/while loop:

.. code-block:: aimms

    for someIdent do ! For each identifier 'someIdent' to be processed:
      dim := IdentifierDimension(someIdent);
      outerArgPos := 1;
      while outerArgPos &lt;= dim do ! Consider each argument
        domInd := DomainIndex(someIdent, outerArgPos );
        domSet := IndexRange( domInd );
        if domSet = eElemRange then ! If the argument range matches the set.
        ! ... actually create a piece of text.
        endif ;
        outerArgPos += 1 ;
      endwhile ;
    endfor ;

In order to know which piece of text to create, we first need to write a sample text, and then follow it as an example. The example text is a simple AIMMS assignment of the following form:

.. code-block:: aimms

    idName(i,'newElement',j) := idName(i,'existingElement',j);

A token is a single character or a small group of characters belonging together, for instance a comma, a parenthesis, a number, a name, or an operator such as ":=". By following each token in the above assignment, and generalizing a bit, we come to the following AIMMS code. This code, in turn, will generate the requested AIMMS statements. First the left hand side:

.. code-block:: aimms

    bodyLine := someIdent + "(";
    innerArgPos := 1 ;
    while innerArgPos &lt; outerArgPos do
      domIndInner := DomainIndex( someIdent, innerArgPos );
      bodyLine += domIndInner + "," ;
      innerArgPos += 1;
    endwhile ;
    bodyLine += "'" + newElement + "'"   ;
    innerArgPos := outerArgPos + 1 ;
    while innerArgPos &lt;= dim do
      domIndInner := DomainIndex( someIdent, innerArgPos );
      bodyLine += "," + domIndInner ;
      innerArgPos += 1;
    endwhile ;
    bodyLine += ")" ;

The assignment token:

.. code-block:: aimms

    bodyLine += " := " ;

The right hand side of the assignment, which is somewhat similar to the left hand side:

.. code-block:: aimms

    bodyLine += someIdent + "(";
    innerArgPos := 1 ;
    while innerArgPos &lt; outerArgPos do
        domIndInner := DomainIndex( someIdent, innerArgPos );
        bodyLine += domIndInner + "," ;
        innerArgPos += 1;
    endwhile ;
    bodyLine += "'" + existingElement + "'" ;
    innerArgPos := outerArgPos + 1 ;
    while innerArgPos &lt;= dim do
        domIndInner := DomainIndex( someIdent, innerArgPos );
        bodyLine += "," + domIndInner ;
        innerArgPos += 1;
    endwhile ;
    bodyLine += ");" ;

Finishing up the line, and adding it to the body text:

.. code-block:: aimms

    ! the n will generate a newline in the generated text.
    bodyLine += "n" ;

    ! Add the assignment statement to the procedure body.
    s_textOfProcBody += bodyLine ;

    Now that we have the body text in ``s_textOfProcBody``, we actually want to assign this text to the procedure:

.. code-block:: aimms

    ok := me::SetAttribute( e_proc, 'body', s_textOfProcBody );

Once the procedure is created and given its body text, we use the AIMMS compiler to check the text and generate executable code:

.. code-block:: aimms

    ok := me::compile( e_lib );

Here we compile the entire library, not just a single procedure. Note that the given example is relatively simple; only one runtime identifier is created. Normally, there are multiple runtime identifiers created, and the compilation of the library will ensure that they are all compiled. Once we have executable code, we can execute the generated procedure by an APPLY statement:

.. code-block:: aimms

    apply( e_proc );
    

A complete AIMMS model that provides the data duplication code, as a library, is available here: :download:`AIMMS project download <../Resources/C_Language/Images/132/SomeApplication_converted.zip>`  

This completes the data duplication example. It illustrates the use of Model Query and Model Edit functions in the reduction of application maintenance costs. Other uses of Model Query and Model Edit functions include:

* creating ad-hoc queries to explain model results, and
* enabling modeler – end-user cooperative development.

Moreover, Model Query and Model Edit functions form a major building block when treating formulas as data. This, however, is a topic for another blog post. More information about Runtime libraries and Model Edit functions can be found in the Language Reference of AIMMS, section "Runtime Libraries and the Model Edit Functions." Another example of the use of Model Edit functions was written in the post <a title="Getting value of a dynamic identifier" :doc:`146-value-dynamic-identifier`.


.. include:: ../includes/form.def


