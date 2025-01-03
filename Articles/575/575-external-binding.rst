Reading Slices of Data via ``ExternalBinding`` Mapping Element
====================================================================

Data is sometimes provided in several, similarly structured, ``.json``  files; for instance as a result of multiple DEX Client requests.
Each of these ``.json`` files provides a slice of the data to be worked on.

In this how-to, two ways are presented for handling the slices:

#.  Doing it yourself, via additional AIMMS parameters.

#.  Using the ``ExternalBinding`` element.

Please use the following project to follow this article:

    :download:`Example Download (AIMMS 4.91) <model/DexExternalBinding.zip>` 

Story
-----

We have three ``.json``, each describing some properties of a stream.
These properties are named ``prp1``, ``prp2``, and ``prp3`` in the ``.json`` file.
Their data is to be read in, into the AIMMS identifiers ``p_prop1(i_stream)``,  ``p_prop2(i_stream)``, and ``sp_prop3(i_stream)`` respectively.

Solution by Adding Additional Parameters
-----------------------------------------

Doing it yourself, three additional parameters are added: ``p_prop1_scalar``, ``p_prop2_scalar``, and ``sp_prop3_scalar``
Then we can use the mapping file:

.. code-block:: xml

    <AimmsJSONMapping>
        <ObjectMapping>
            <ValueMapping name="prp1" maps-to="p_prop1_scalar"/>
            <ValueMapping name="prp2" maps-to="p_prop2_scalar"/>
            <ValueMapping name="prp3" maps-to="sp_prop3_scalar"/>
        </ObjectMapping>
    </AimmsJSONMapping>

and the procedure:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 14-19

    Procedure pr_getDataViaScalarReads {
        Body: {
            dex::AddMapping(
                mappingName :  "streamScalarRead", 
                mappingFile :  "Mappings/streamPropertiesScalarMappingJSON.xml");
            
            for i_dataFileNo do
                sp_elemName := SubString(sp_dataFileNames(i_dataFileNo),1,StringLength(sp_dataFileNames(i_dataFileNo))-5);
                SetElementAdd(
                    Setname :  s_streams, 
                    Elempar :  ep_anotherStream, 
                    Newname :  sp_elemName);
            
                dex::ReadFromFile(
                    dataFile         :  "data/" + sp_dataFileNames(i_dataFileNo), 
                    mappingName      :  "streamScalarRead");
                p_prop1(ep_anotherStream) := p_prop1_scalar ;
                p_prop2(ep_anotherStream) := p_prop2_scalar ;
                sp_prop3(ep_anotherStream) := sp_prop3_scalar ;
            
            endfor ;
        }
        StringParameter sp_elemName;
    }

Solution via the ``ExternalBinding`` Element
-----------------------------------------------

By establishing an external binding, we can directly store the data of the slice read in:

.. code-block:: xml

    <AimmsJSONMapping>
        <ExternalBinding binds-to="i_stream" binding="ep_anotherStream"/>
        <ObjectMapping>
            <ValueMapping name="prp1" maps-to="p_prop1(i_stream)"/>
            <ValueMapping name="prp2" maps-to="p_prop2(i_stream)"/>
            <ValueMapping name="prp3" maps-to="sp_prop3(i_stream)"/>
        </ObjectMapping>
    </AimmsJSONMapping>

by using the procedure:

.. code-block:: aimms
    :linenos:
    :emphasize-lines: 14-16

    Procedure pr_getDataViaExternalBinding {
        Body: {
            dex::AddMapping(
                mappingName :  "streamExternalRead", 
                mappingFile :  "Mappings/streamPropertiesExternalMappingJSON.xml");

            for i_dataFileNo do
                sp_elemName := SubString(sp_dataFileNames(i_dataFileNo),1,StringLength(sp_dataFileNames(i_dataFileNo))-5);
                SetElementAdd(
                    Setname :  s_streams, 
                    Elempar :  ep_anotherStream, 
                    Newname :  sp_elemName);

                dex::ReadFromFile(
                    dataFile         :  "data/" + sp_dataFileNames(i_dataFileNo), 
                    mappingName      :  "streamExternalRead");

            endfor ;
        }
        StringParameter sp_elemName;
    }

As you can see, there is no need anymore for the additional parameters: ``p_prop1_scalar``, ``p_prop2_scalar``, and ``sp_prop3_scalar``.

.. seealso::
    * `External bindings <https://documentation.aimms.com/dataexchange/mapping.html#external-bindings-in-mappings>`_