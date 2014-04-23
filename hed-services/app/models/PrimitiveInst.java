package models;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.LinkedList;
import java.util.List;

/**
 * User: rk Date: 9/15/13
 */
public class PrimitiveInst
        extends SharpInst
{

    public String id;

    public PrimitiveTemplate type;

    public List<ParameterInst> parameterInsts = new LinkedList<ParameterInst>();

    //=============================================================================================

    protected SharpType getSharpType()
    {
        return this.type;
    }

    public void verify()
    {
        errors.clear();

        for ( ParameterInst p : parameterInsts )
        {
            p.verify();
        }
//        return errors.isEmpty();
    }


}
