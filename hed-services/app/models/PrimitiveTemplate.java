package models;


import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * User: rk Date: 8/13/13 Package: models
 */
public class PrimitiveTemplate
        extends SharpType
{

    @Id
    public String templateId;

    public String key;

    public String category;

    public String group;

    //    public String mtype;
//    public String mtype = getClass().getSimpleName();

//    public String name;

    public String description;

    public String example;

    public List<String> parameterIds = new ArrayList<String>();

    public List<ParameterType> parameters = new ArrayList<ParameterType>();

    //========================================================================================

//    public List<String> errors = new ArrayList<String>();

    public ParameterType getParameter (String id)
    {
        for ( ParameterType e : parameters )
        {
            if ( id.equals( e.key ) )
            {
                return e;
            }
        }
        return null;
    }

    public PrimitiveInst createInst ()
    {
        PrimitiveInst inst = new PrimitiveInst();
        inst.type = this;
        inst.id = ModelHome.createUUID();

        for ( ParameterType pt : this.parameters )
        {
            ParameterInst p = pt.createInst();
            inst.parameterInsts.add( p );
        }

        return inst;
    }


    protected ObjectNode addToJson(final ObjectNode json)
    {
        json.put( "templateId", templateId );
        json.put( "category", category );
        json.put( "group", group );
        json.put( "name", name );
        json.put( "description", description );
        json.put( "example", example );
//        json.put( "expressionChoices", Json.toJson( parameterIds ) );
//        json.put( "elements", Json.toJson( parameters ) );

        return json;
    }

}
