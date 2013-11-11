package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.util.List;

/**
 * User: rk Date: 9/15/13
 */
public class ElementInst<E>
        extends SharpInst
{
    public ElementType<E> type;

    //    public E value;
    public Object value;

    //=============================================================================================

    protected SharpType getSharpType()
    {
        return this.type;
    }

    public ElementType getElementType()
    {
        return (ElementType) getSharpType();
    }

    public void verify()
    {
        errors.clear();

//        System.out.println( "ElementType " +
//                                    ( name == null ? "null" : name ) + " generic type E value = "
////                                    + E.class.getName() );
//                                    + ( value == null ? "null" : value.getClass().getName() ) );

        verify_valueCount();

        if ( getElementType().isNumericType() )
        {
            verify_numberInRange();
        }
    }

    void verify_valueCount()
    {
        int cnt = getValueCount();

        if ( cnt < getElementType().minSelectionCount )
        {
            addError( "Too few values specified, required: " + getElementType().minSelectionCount +
                              ", actual: " + cnt );
        }
        else if ( cnt > getElementType().maxSelectionCount )
        {
            addError( "Too many values specified, required: " + getElementType().maxSelectionCount +
                              ", actual: " + cnt );
        }
    }

    void verify_numberInRange()
    {
        if ( getElementType().isNumericType() )
        {
            Number v = asNumber( this.value );
            Number minv = asNumber( getElementType().minValue );
//            Number maxv = asNumber( this.maxValue );

            if ( v.doubleValue() < minv.doubleValue() )
            {
                addError( "The current value is less than the minimum value of: " +
                                  getElementType().minValue );
            }
        }

    }

//    private Integer minSelectionCount ()
//    {
//        return getElementType().minSelectionCount;
//    }
//
//    private Integer maxSelectionCount ()
//    {
//        return getElementType().maxSelectionCount;
//    }

//    private E minValue ()
//    {
//        return getElementType().minValue;
//    }

//    private Integer maxSelectionCount ()
//    {
//        return getElementType().maxSelectionCount;
//    }

    int getValueCount()
    {
        if ( value == null )
        {
            return 0;
        }
        else if ( value instanceof List )
        {
            return ( (List<E>) value ).size();
        }
        else
        {
            return 1;
        }
    }

    Number asNumber(Object o)
    {
        if ( o instanceof Number )
        {
            return (Number) o;
        }
        else
        {
            return null;
        }
    }

    protected ObjectNode addInstInfoToJson(final ObjectNode json)
    {
        super.addInstInfoToJson( json);

//        this.value = Integer.valueOf( 42);
        json.put( "value", Json.toJson( this.value ) );
        json.put( "name", this.type.name );

        return json;
    }

}
