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

    public void addError(final String s) {
        errors.add( s );
    }


    protected abstract SharpType getSharpType();



}
