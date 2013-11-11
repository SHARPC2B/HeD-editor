package models;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.util.LinkedList;
import java.util.List;

/**
 * User: rk Date: 9/15/13
 */
public class ParameterInst
        extends SharpInst
{
    public ParameterType type;

    public Boolean useLiteral;

    public String selectedExpression;

    public List<ElementInst<?>> elements = new LinkedList<ElementInst<?>>();

    //=============================================================================================

    protected SharpType getSharpType()
    {
        return this.type;
    }

    public void verify()
    {
        errors.clear();

        for ( ElementInst e : elements )
        {
            e.verify();
        }
//        return errors.isEmpty();
    }

    protected ObjectNode addInstInfoToJson(final ObjectNode json)
    {
        super.addInstInfoToJson( json);


        ArrayNode parts = JsonNodeFactory.instance.arrayNode();
        for ( ElementInst p : this.elements )
        {
            parts.add( p.toJsonCore() );
        }
        json.put( "elements", parts );
        json.put( "useLiteral", this.useLiteral );
        json.put( "selectedExpression", this.selectedExpression );
        json.put( "key", this.type.key );

        return json;
    }

}
