package edu.asu.sharpc2b;


import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.templates.Template;

import java.util.LinkedHashMap;

public interface TemplateInstantiator {

    //TOOD Resolve the hierarchy of SharpExpression and SharpAction to avoid the Object generic
    public LinkedHashMap<String,SharpExpression> instantiateExpression( String name, Template source );

}
