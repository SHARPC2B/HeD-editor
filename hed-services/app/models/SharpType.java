package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.db.ebean.Model;

/**
 * Single "Model" parent for Sharp objects.
 *
 * User: rk Date: 9/3/13
 */
public class SharpType
        extends Model
{

    public String mtype;

    public String name;

    SharpType()
    {
        super();

        this.mtype = getClass().getSimpleName();
    }

    protected ObjectNode addToJson(final ObjectNode json)
    {
        return json;
    }

}
