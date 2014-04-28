package models;

import play.db.ebean.Model;

import java.util.LinkedList;
import java.util.List;


public class Rule
        extends Model
{
    public String ruleId;

    public String Name;

    public String Type = "Rule";

    public String Description;

    public String isoLang = "en";

    public String Status;

    public List<PrimitiveInst> primitives = new LinkedList<PrimitiveInst>();

}
