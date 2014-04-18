package models;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class SharpType extends play.db.ebean.Model {

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
