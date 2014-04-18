package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.db.ebean.Model;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * User: rk Date: 9/15/13
 */
public abstract class SharpInst
        extends play.db.ebean.Model
{

    public List<String> errors = new ArrayList<String>();

    public void addError(final String s)
    {
        errors.add( s );
    }

//    public String typeName;
//    public SharpType type;

//    public String getTypeName ()
//    {
//        return this.typeName != null ? this.typeName : getTypeNameFromType();
//    }

//    protected String getTypeNameFromType ()
//    {
//        SharpType t = getSharpType();
//
//        return t != null ? t.mtype: null;
//    }

    protected abstract SharpType getSharpType();

//    public abstract ObjectNode toJsonCore();

    public ObjectNode toJsonCore()
    {
        ObjectNode json = Json.newObject();

        addTypeInfoToJson( json );

        addInstInfoToJson( json );

        return json;
    }

//    protected abstract ObjectNode addTypeInfoToJson(final ObjectNode json);

    protected ObjectNode addTypeInfoToJson(final ObjectNode json)
    {
//        json.put( "errors", Json.toJson( errors ) );
        if ( getSharpType() != null )
        {
            getSharpType().addToJson( json );
        }
        return json;
    }

    protected ObjectNode addInstInfoToJson(final ObjectNode json)
    {
        json.put( "errors", Json.toJson( errors ) );

        return json;
    }

}
