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

    public IsoLang isoLang = new IsoLang();

    public String Status;

    public List<PrimitiveInst> primitives = new LinkedList<PrimitiveInst>();

}
