package models;

import play.db.ebean.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * User: rk Date: 9/15/13
 */
public class Rule
        extends Model
{
    public String ruleId;

    public String typeName = "Rule";

    public List<PrimitiveInst> primitives = new LinkedList<PrimitiveInst>();

//    public PrimitiveInst createPrimitive()
//    {
//        return null;
//    }

}
