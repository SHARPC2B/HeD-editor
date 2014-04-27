package edu.asu.sharpc2b.hed.api;


import edu.asu.sharpc2b.templates.Template;

import java.util.Map;
import java.util.Set;

public interface TemplateStore {

    public Set<String> getTemplateIds( String category );

    public Template getTemplateInfo( String templateId );

    public byte[] instantiateTemplate( String templateId, String name, Template source );

    public Template getTemplateInstanceForNamedExpression( String id );
}
