:orphan:.. raw:: html

   <title>
                   Determine where each identifier is used in your model       </title>

::

       <link>https://berthier.design/aimmsbackuptech/2011/12/01/determine-where-each-identifier-is-used-in-your-model/</link>
       <pubDate>Thu, 01 Dec 2011 15:03:20 +0000</pubDate>
       <dc:creator><![CDATA[]]></dc:creator>
       <guid isPermaLink="false">http://blog.aimms.com/?p=177</guid>
       <description></description>
       <content:encoded>
               <![CDATA[Over time, AIMMS projects tend to grow larger. During such an evolution of a project, some identifiers can become obsolete.

With larger models, often the question is where each identifier is used
in the model, or more important, if it is still used in the model. In
this article, we want to provide you with a way to determine which of
the identifiers in your model might be obsolete.

The goal is to create the indexed subset sIdentfiersUsing, which
contains a subset of AllIdentifiers for each identifier declared in your
model.

.. raw:: html

   <pre lang="aim">SET:
      identifier   :  sIdentifiersUsing
      index domain :  iRegularIdentifier
      subset of    :  AllIdentifiers

   SET:
      identifier :  sRegularIdentifiers
      subset of  :  AllIdentifiers
      index      :  iRegularIdentifier
      definition :  { indexIdentifiers |
                               IdentifierType(IndexIdentifiers) &lt;&gt; 'Section'
                           and IdentifierType(IndexIdentifiers) &lt;&gt; 'Module'
                           and IdentifierType(IndexIdentifiers) &lt;&gt; 'LibraryModule'
                           and IdentifierType(IndexIdentifiers) &lt;&gt; 'declaration'
                    }</pre>

As you can see, the set sIdentifiersUsing is indexed over a subset of
AllIdentifers, namely all the identifiers except for the Sections,
Modules, Libraries, and declaration sections.

To fill the sIdentifiersUsing set with the identifiers that are using
the identifier iRegularIdentifier, we make use of the intrinsic
procedure ReferencedIdentifiers (see Function Reference) in the
following procedure:

.. raw:: html

   <pre lang="aim">PROCEDURE
     identifier :  prDetermineReferenced

     DECLARATION SECTION 

       SET:
          identifier :  sReferencedIdentifiers
          subset of  :  AllIdentifiers
          index      :  iReferencedIdentifier ;

       SET:
          identifier :  sIdentifierSingleton
          subset of  :  AllIdentifiers ;

     ENDSECTION  ;

     body       :
       !First empty the set
       empty sIdentifiersUsing ;

       for iRegularIdentifier do
           !For each non {section/module/library/declaration} identifier in your
           !model, create a singleton set, containing only this identifier
           !(needed because ReferencedIdentifiers must have a set as argument
           sIdentifierSingleton := iRegularIdentifier ;

           !Retrieve all the identifiers that are referenced in all attributes
           !of the current identifier
           sReferencedIdentifiers := ReferencedIdentifiers(
                                                   sIdentifierSingleton,
                                                   AllAttributeNames
                                                   )
                                      * sRegularIdentifiers ;

           !We now know which identifiers are referenced by iRegularIdentifer.
           !For each of these identifiers, we must add a link that it is used by
           !iRegularIdentifier
           for iReferencedIdentifier do
               sIdentifiersUsing( iReferencedIdentifier ) += iRegularIdentifier ;
           endfor ;
        endfor ;
   ENDPROCEDURE  ;</pre>

Please note that if the above procedure indicates that an identifier is
not used anymore, this only means it does not have a reference within
your model anymore.

We have created an .aim file of a section containing the above
identifiers, which you can download via the following link: [attachments
include="4086"] After downloading the above file, please follow the
Importing instructions in the blog post Exporting a section and
importing it in another AIMMS project to import this .aim file into your
project.

Additional information: Even if an identifier is not used anymore in
your model, it could still be present on pages in your project. With a
combination of the two functions PageGetAll and PageGetUsedIdentifiers
(See the Page functions in the Function Reference) you can first obtain
a list of all the pages in your model, then query per page all the
identifiers used on the page, and finally check if the selected
identifier is one of them. This approach will give you per identifier
the list of pages it is used on.

Alternatively, if you are only interested in the binary question "is an
identifier used on any page yes or no?", you can also make use of the
new function IdentifierGetUsedInformation that has been introduced in
AIMMS 3.12 FR2. This function also allows you to determine if there
exists a reference to the identifier in any of the case types or
menus.]]> <[CDATA[]]> 177 0 0 0
