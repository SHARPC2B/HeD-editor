package models;


import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

/**
 * User: rk Date: 9/26/13
 */
public class HedType
        extends SharpType
{
//    public String id;

//    public String hedTypeName / name;   // e.g., CodeLiteral, PhysicalQuantityLiteral

    public String label;

    public String description;

//    public String hedType;   // one of literalexpression.xsd types.

    public List<ElementType> elements = new ArrayList<ElementType>();

    //========================================================================================

    public ElementType getElement(String name)
    {
        for ( ElementType e : elements )
        {
            if ( name.equals( e.name ) )
            {
                return e;
            }
        }
        return null;
    }

    protected ObjectNode addToJson(final ObjectNode json)
    {
        json.put( "name", name );
        json.put( "label", label );
        json.put( "description", description );
//        json.put( "hedTypeName", hedTypeName );
//        json.put( "hedType", hedType );

//        for ( ElementType elementType : this.elements )
//        {
//            elementType.addInstInfoToJson( json );
//        }
        return json;
    }

}
