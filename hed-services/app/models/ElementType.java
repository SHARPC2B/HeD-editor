package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: rk Date: 8/13/13 Package: models
 *
 * From HeD file literalexpression.xsd
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementType<E>
        extends SharpType implements Cloneable
{
    private static final List<String> NUMERIC_TYPES = Arrays.asList( "Decimal", "Integer" );
//    public String id;

//    public String mtype = getClass().getSimpleName();

//    public String name;

    public String label;

    public String description;

    public String valueType;

    public String widgetType;

    public List<E> selectionChoices;

    public E initialValue;

    public E value;

    public String searchService;

    public Integer minSelectionCount;

    public Integer maxSelectionCount;

    public E minValue; // = new BigDecimal( 0 );

    //========================================================================================


    public ElementType<E> clone() {
        ElementType t = new ElementType<E>();
        t.name = this.name;
        t.mtype = this.mtype;
        t.label = this.label;
        t.description = this.description;
        t.valueType = this.valueType;
        t.widgetType = this.widgetType;
        t.selectionChoices = this.selectionChoices != null ? new ArrayList<E>( this.selectionChoices ) : null;
        t.initialValue = this.initialValue;
        t.value = this.value;
        t.searchService = this.searchService;
        t.minSelectionCount = this.minSelectionCount;
        t.maxSelectionCount = this.maxSelectionCount;
        t.minValue = this.minValue;
        return t;
    }

    boolean isMultiValued()
    {
        return this.maxSelectionCount > 1;
    }

    boolean isRequired()
    {
        return this.minSelectionCount > 0;
    }

    boolean isNumericType()
    {
        return isNumericType( this.valueType );
    }

    private boolean isNumericType(final String valueType)
    {
        return NUMERIC_TYPES.contains( valueType );
    }

    public ElementInst<E> createInst()
    {
        ElementInst<E> inst = new ElementInst<E>();
        inst.type = this;
//        inst.id = ModelHome.createUUID();

        inst.value = this.initialValue;

        return inst;
    }

    protected ObjectNode addToJson(final ObjectNode json)
    {
        json.put( "name", name );
        json.put( "label", label );
        json.put( "description", description );
        json.put( "valueType", valueType );
        json.put( "widgetType", widgetType );
        json.put( "selectionChoices", Json.toJson( selectionChoices ) );
        json.put( "initialValue", Json.toJson( initialValue ) );
        json.put( "searchService", searchService );
        json.put( "minSelectionCount", minSelectionCount );
        json.put( "maxSelectionCount", maxSelectionCount );
        json.put( "minValue", Json.toJson( minValue ) );

        return json;
    }

}
